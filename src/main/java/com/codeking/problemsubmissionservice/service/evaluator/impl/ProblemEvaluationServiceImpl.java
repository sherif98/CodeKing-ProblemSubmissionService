package com.codeking.problemsubmissionservice.service.evaluator.impl;

import com.codeking.problemsubmissionservice.repository.ProblemRepository;
import com.codeking.problemsubmissionservice.service.evaluator.api.ProblemEvaluationService;
import com.codeking.problemsubmissionservice.service.evaluator.dto.ProblemEvaluationRequest;
import com.codeking.problemsubmissionservice.service.evaluator.dto.ProblemEvaluationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProblemEvaluationServiceImpl implements ProblemEvaluationService {

    @Autowired
    private ProblemRepository problemRepository;

    @Override
    public ProblemEvaluationResult evaluateProblem(ProblemEvaluationRequest problemEvaluationRequest) {
        return ProblemEvaluationResult.builder().build();
    }
}
