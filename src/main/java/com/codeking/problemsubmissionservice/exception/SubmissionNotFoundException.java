package com.codeking.problemsubmissionservice.exception;


import lombok.Getter;

@Getter
public class SubmissionNotFoundException extends RuntimeException {

    private String submissionId;

    public SubmissionNotFoundException(String submissionId) {
        super(String.format("Can't find submission with Id %s", submissionId));
        this.submissionId = submissionId;
    }
}
