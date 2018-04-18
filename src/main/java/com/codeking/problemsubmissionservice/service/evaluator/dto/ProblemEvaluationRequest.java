package com.codeking.problemsubmissionservice.service.evaluator.dto;

import com.codeking.problemsubmissionservice.domain.Problem;
import com.codeking.problemsubmissionservice.domain.ProgrammingLanguage;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.nio.file.Path;

/**
 * request for problem evaluation service.
 */
@Getter
@Builder
@ToString
public class ProblemEvaluationRequest {

    private Path executableProgramPath;
    private ProgrammingLanguage programmingLanguage;
    private Problem problem;
}
