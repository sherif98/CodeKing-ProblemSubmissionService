package com.codeking.problemsubmissionservice.controller;


import com.codeking.problemsubmissionservice.controller.dto.ProblemSubmission;
import com.codeking.problemsubmissionservice.controller.dto.UserSubmission;
import com.codeking.problemsubmissionservice.domain.Submission;
import com.codeking.problemsubmissionservice.event.api.EventDispatcher;
import com.codeking.problemsubmissionservice.event.dto.ProblemEvaluatedEvent;
import com.codeking.problemsubmissionservice.exception.SubmissionNotFoundException;
import com.codeking.problemsubmissionservice.repository.SubmissionRepository;
import com.codeking.problemsubmissionservice.service.submitter.api.ProblemSubmissionService;
import com.codeking.problemsubmissionservice.service.submitter.dto.ProblemSubmissionRequest;
import com.codeking.problemsubmissionservice.service.submitter.dto.ProblemSubmissionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/submission")
public class ProblemSubmissionController {

    @Autowired
    private SubmissionRepository submissionRepository;

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
        ProblemSubmissionResult problemSubmissionResult = submit(problemSubmission);

        ProblemEvaluatedEvent problemEvaluatedEvent = ProblemEvaluatedEvent.builder()
                .userId(problemSubmission.getUserId())
                .problemId(problemSubmission.getProblemId())
                .submissionStatus(problemSubmissionResult.getSubmissionStatus())
                .programmingLanguage(problemSubmission.getProgrammingLanguage())
                .build();

        eventDispatcher.dispatchProblemEvaluatedEvent(problemEvaluatedEvent);

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

    private ProblemSubmissionResult submit(ProblemSubmission problemSubmission) {
        ProblemSubmissionRequest problemSubmissionRequest = ProblemSubmissionRequest.builder()
                .problemId(problemSubmission.getProblemId())
                .code(problemSubmission.getCode())
                .programmingLanguage(problemSubmission.getProgrammingLanguage())
                .build();

        return problemSubmissionService.submitProblem(problemSubmissionRequest);
    }
}
