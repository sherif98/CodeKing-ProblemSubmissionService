package com.codeking.problemsubmissionservice.controller;


import com.codeking.problemsubmissionservice.controller.dto.ProblemSubmission;
import com.codeking.problemsubmissionservice.controller.dto.UserSubmission;
import com.codeking.problemsubmissionservice.domain.Problem;
import com.codeking.problemsubmissionservice.domain.Submission;
import com.codeking.problemsubmissionservice.event.api.EventDispatcher;
import com.codeking.problemsubmissionservice.event.dto.ProblemEvaluatedEvent;
import com.codeking.problemsubmissionservice.exception.ProblemNotFoundException;
import com.codeking.problemsubmissionservice.exception.SubmissionNotFoundException;
import com.codeking.problemsubmissionservice.repository.ProblemRepository;
import com.codeking.problemsubmissionservice.repository.SubmissionRepository;
import com.codeking.problemsubmissionservice.service.submitter.api.ProblemSubmissionService;
import com.codeking.problemsubmissionservice.service.submitter.dto.ProblemSubmissionRequest;
import com.codeking.problemsubmissionservice.service.submitter.dto.ProblemSubmissionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/submission")
public class ProblemSubmissionController {

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private ProblemSubmissionService problemSubmissionService;

    @Autowired
    private EventDispatcher eventDispatcher;

    @GetMapping("{submissionId}")
    public Submission getSubmissionById(@PathVariable("submissionId") String submissionId) {
        return submissionRepository.findById(submissionId)
                .orElseThrow(() -> new SubmissionNotFoundException(submissionId));
    }

    @PostMapping
    public ProblemSubmissionResult submitProblem(@RequestBody ProblemSubmission problemSubmission) {

        Optional<Problem> problemEntity = problemRepository.findById(problemSubmission.getProblemId());
        problemEntity.orElseThrow(() -> new ProblemNotFoundException(problemSubmission.getProblemId()));

        Problem problem = problemEntity.get();

        String decodedCode = decodeCode(problemSubmission.getCode());
        problemSubmission.setCode(decodedCode);

        ProblemSubmissionResult problemSubmissionResult = submit(problemSubmission, problem);
        dispatchProblemEvaluatedEvent(problemSubmission, problemSubmissionResult);
        persistSubmission(problemSubmission, problemSubmissionResult, problem.getProblemTitle());

        return problemSubmissionResult;
    }


    @GetMapping("/user/{userId}")
    public List<UserSubmission> getUserSubmissions(@PathVariable("userId") String userId) {
        return submissionRepository.findAllByUserId(userId)
                .stream()
                .map(this::createUserSubmission)
                .collect(Collectors.toList());
    }

    private UserSubmission createUserSubmission(Submission submission) {
        return UserSubmission.builder()
                .problemTitle(submission.getProblemTitle())
                .submissionStatus(submission.getSubmissionStatus())
                .submissionId(submission.getSubmissionId())
                .build();

    }

    private ProblemSubmissionResult submit(ProblemSubmission problemSubmission, Problem problem) {
        ProblemSubmissionRequest problemSubmissionRequest = ProblemSubmissionRequest.builder()
                .problem(problem)
                .code(problemSubmission.getCode())
                .programmingLanguage(problemSubmission.getProgrammingLanguage())
                .build();

        return problemSubmissionService.submitProblem(problemSubmissionRequest);
    }

    private void dispatchProblemEvaluatedEvent(ProblemSubmission problemSubmission, ProblemSubmissionResult problemSubmissionResult) {
        ProblemEvaluatedEvent problemEvaluatedEvent = ProblemEvaluatedEvent.builder()
                .userId(problemSubmission.getUserId())
                .problemId(problemSubmission.getProblemId())
                .submissionStatus(problemSubmissionResult.getSubmissionStatus())
                .programmingLanguage(problemSubmission.getProgrammingLanguage())
                .build();

        eventDispatcher.dispatchProblemEvaluatedEvent(problemEvaluatedEvent);
    }

    private void persistSubmission(ProblemSubmission problemSubmission,
                                   ProblemSubmissionResult problemSubmissionResult, String problemTitle) {
        Submission submission = Submission.builder()
                .code(problemSubmission.getCode())
                .programmingLanguage(problemSubmission.getProgrammingLanguage())
                .submissionStatus(problemSubmissionResult.getSubmissionStatus())
                .problemTitle(problemTitle)
                .userId(problemSubmission.getUserId())
                .build();
        submissionRepository.save(submission);
    }

    private String decodeCode(String code) {
        byte[] decode = Base64.getDecoder().decode(code);
        return new String(decode);
    }
}
