package com.codeking.problemsubmissionservice.service.compiler.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProcessResult {
    private int exitStatus;
    private String compilerError;
}
