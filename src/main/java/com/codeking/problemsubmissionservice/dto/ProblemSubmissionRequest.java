package com.codeking.problemsubmissionservice.dto;

import com.codeking.problemsubmissionservice.domain.ProgrammingLanguage;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class ProblemSubmissionRequest {

    private String code;
    private ProgrammingLanguage programmingLanguage;
    private String userId;
}
