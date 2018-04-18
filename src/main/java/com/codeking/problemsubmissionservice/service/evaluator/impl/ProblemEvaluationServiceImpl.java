package com.codeking.problemsubmissionservice.service.evaluator.impl;

import com.codeking.problemsubmissionservice.domain.ProgrammingLanguage;
import com.codeking.problemsubmissionservice.domain.TestCase;
import com.codeking.problemsubmissionservice.service.evaluator.api.ProblemEvaluationService;
import com.codeking.problemsubmissionservice.service.evaluator.dto.ProblemEvaluationRequest;
import com.codeking.problemsubmissionservice.service.evaluator.dto.ProblemEvaluationResult;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;

@Service
public class ProblemEvaluationServiceImpl implements ProblemEvaluationService {


    @Override
    public ProblemEvaluationResult evaluateProblem(ProblemEvaluationRequest problemEvaluationRequest) {

        ProgrammingLanguage programmingLanguage = problemEvaluationRequest.getProgrammingLanguage();
        List<TestCase> testCases = problemEvaluationRequest.getProblem().getTestCases();
        long timeLimit = problemEvaluationRequest.getProblem().getTimeLimit();
        Path executableProgramPath = problemEvaluationRequest.getExecutableProgramPath();

        System.out.println(problemEvaluationRequest);

        ProblemEvaluationTemplate problemEvaluationTemplate;
        switch (programmingLanguage) {
            case JAVA:
                problemEvaluationTemplate = new JavaProblemEvaluation();
                break;
            default:
                throw new AssertionError("Unknown Programming Language " + programmingLanguage);
        }
        return problemEvaluationTemplate.evaluateProblem(executableProgramPath, testCases, timeLimit);
    }

}
