package com.codeking.problemsubmissionservice.domain;


/**
 * status of the problem submission.
 */
public enum SubmissionStatus {
    ACCEPTED,
    WRONG_ANSWER,
    TIME_LIMIT_EXCEEDED,
    COMPILATION_ERROR,
    RUNTIME_ERROR
}
