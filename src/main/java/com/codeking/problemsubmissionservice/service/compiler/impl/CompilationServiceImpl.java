package com.codeking.problemsubmissionservice.service.compiler.impl;

import com.codeking.problemsubmissionservice.service.compiler.api.CompilationService;
import com.codeking.problemsubmissionservice.service.compiler.dto.CompilationRequest;
import com.codeking.problemsubmissionservice.service.compiler.dto.CompilationResult;
import org.springframework.stereotype.Service;

@Service
public class CompilationServiceImpl implements CompilationService {


    @Override
    public CompilationResult compile(CompilationRequest compilationRequest) {
        return CompilationResult.builder().build();
    }

}
