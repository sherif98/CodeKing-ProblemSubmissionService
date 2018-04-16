package com.codeking.problemsubmissionservice.service.compiler.impl;

import com.codeking.problemsubmissionservice.service.compiler.api.CompilationService;
import com.codeking.problemsubmissionservice.service.compiler.dto.CompilationRequest;
import com.codeking.problemsubmissionservice.service.compiler.dto.CompilationResult;
import org.springframework.stereotype.Service;

@Service
public class CompilationServiceImpl implements CompilationService {


    @Override
    public CompilationResult compile(CompilationRequest compilationRequest) {
        CompilationTemplate compilationTemplate;
        switch (compilationRequest.getProgrammingLanguage()) {
            case JAVA:
                compilationTemplate = new JavaCompilation();
                break;
            default:
                throw new AssertionError("Unknown Programming Language " + compilationRequest.getProgrammingLanguage());
        }
        return compilationTemplate.compile(compilationRequest.getCode());
    }

}
