package com.codeking.problemsubmissionservice.service.compiler.dto;

import com.codeking.problemsubmissionservice.domain.ProgrammingLanguage;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CompilationRequest {

    private String code;
    private ProgrammingLanguage programmingLanguage;

}
