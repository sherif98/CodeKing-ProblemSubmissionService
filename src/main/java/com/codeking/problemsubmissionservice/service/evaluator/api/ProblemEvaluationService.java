package com.codeking.problemsubmissionservice.service.evaluator.api;

import com.codeking.problemsubmissionservice.service.evaluator.dto.ProblemEvaluationRequest;
import com.codeking.problemsubmissionservice.service.evaluator.dto.ProblemEvaluationResult;

public interface ProblemEvaluationService {

    ProblemEvaluationResult evaluateProblem(ProblemEvaluationRequest problemEvaluationRequest);
}
