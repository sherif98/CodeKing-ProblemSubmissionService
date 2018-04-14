package com.codeking.problemsubmissionservice.dto;

import com.codeking.problemsubmissionservice.domain.SubmissionStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProblemSubmissionResult {

    private SubmissionStatus submissionStatus;
    private String executionResult;
}
