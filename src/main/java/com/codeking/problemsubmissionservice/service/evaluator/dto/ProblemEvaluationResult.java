package com.codeking.problemsubmissionservice.service.evaluator.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProblemEvaluationResult {

    private ProblemEvaluationStatus problemEvaluationStatus;

}
