package com.codeking.problemsubmissionservice.service.submitter.dto;

import com.codeking.problemsubmissionservice.domain.SubmissionStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * problem submission service result.
 */
@Getter
@Setter
@Builder
@ToString
public class ProblemSubmissionResult {

    private SubmissionStatus submissionStatus;
    private String submissionError;
}
