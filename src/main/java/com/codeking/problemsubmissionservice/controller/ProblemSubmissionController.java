package com.codeking.problemsubmissionservice.controller;


import com.codeking.problemsubmissionservice.domain.Submission;
import com.codeking.problemsubmissionservice.dto.UserSubmission;
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

    @GetMapping("{submissionId}")
    public Submission getSubmissionById(@PathVariable("submissionId") String submissionId) {
        return submissionRepository.findById(submissionId)
                .orElseThrow(() -> new SubmissionNotFoundException(submissionId));
    }

    @PostMapping
    public ProblemSubmissionResult submitProblem(@RequestBody ProblemSubmissionRequest problemSubmissionRequest) {
        //TODO sent an event indicating that a new submission is available.
        return problemSubmissionService.submitProblem(problemSubmissionRequest);
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
}
