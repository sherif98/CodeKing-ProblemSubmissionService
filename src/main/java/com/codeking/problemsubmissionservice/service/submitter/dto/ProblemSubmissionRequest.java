package com.codeking.problemsubmissionservice.service.submitter.dto;

import com.codeking.problemsubmissionservice.domain.Problem;
import com.codeking.problemsubmissionservice.domain.ProgrammingLanguage;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * problem submission service request.
 */
@Getter
@Setter
@Builder
@ToString
public class ProblemSubmissionRequest {

    private String code;
    private ProgrammingLanguage programmingLanguage;
    private Problem problem;
}
