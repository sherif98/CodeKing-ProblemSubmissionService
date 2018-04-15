package com.codeking.problemsubmissionservice.service.compiler.dto;

import com.codeking.problemsubmissionservice.domain.ProgrammingLanguage;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * request for compilation service.
 */
@Builder
@Getter
@ToString
public class CompilationRequest {

    private String code;
    private ProgrammingLanguage programmingLanguage;

}
