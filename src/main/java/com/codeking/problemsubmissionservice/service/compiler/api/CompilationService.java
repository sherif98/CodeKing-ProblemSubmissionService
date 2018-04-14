package com.codeking.problemsubmissionservice.service.compiler.api;

import com.codeking.problemsubmissionservice.service.compiler.dto.CompilationRequest;
import com.codeking.problemsubmissionservice.service.compiler.dto.CompilationResult;

public interface CompilationService {

    CompilationResult compile(CompilationRequest compilationRequest);
}
