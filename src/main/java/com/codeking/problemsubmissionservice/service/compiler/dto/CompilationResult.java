package com.codeking.problemsubmissionservice.service.compiler.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.nio.file.Path;

/**
 * result of compilation service.
 */
@Builder
@Getter
@ToString
public class CompilationResult {

    private CompilationStatus compilationStatus;
    private Path executableProgramPath;
    private String compilerErrorOutput;
}
