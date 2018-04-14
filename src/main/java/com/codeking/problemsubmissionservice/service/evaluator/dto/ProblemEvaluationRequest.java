package com.codeking.problemsubmissionservice.service.evaluator.dto;

import lombok.Builder;
import lombok.Getter;

import java.nio.file.Path;

@Getter
@Builder
public class ProblemEvaluationRequest {

    private Path executableProgramPath;
    private String problemId;
}
