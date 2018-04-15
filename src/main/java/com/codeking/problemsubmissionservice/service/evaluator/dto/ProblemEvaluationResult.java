package com.codeking.problemsubmissionservice.service.evaluator.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * result of problem evaluation service.
 */
@Getter
@Builder
@ToString
public class ProblemEvaluationResult {

    private ProblemEvaluationStatus problemEvaluationStatus;
    private String executorErrorOutput;

}
