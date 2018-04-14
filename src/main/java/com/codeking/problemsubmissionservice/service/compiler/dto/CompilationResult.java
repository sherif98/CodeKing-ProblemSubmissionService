package com.codeking.problemsubmissionservice.service.compiler.dto;

import lombok.Builder;
import lombok.Getter;

import java.nio.file.Path;

@Builder
@Getter
public class CompilationResult {

    private CompilationStatus compilationStatus;
    private Path executableProgramPath;

}
