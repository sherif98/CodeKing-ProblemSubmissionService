package com.codeking.problemsubmissionservice.controller.dto;

import com.codeking.problemsubmissionservice.domain.ProgrammingLanguage;
import lombok.*;

/**
 * dto used when a new submission is submitted.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProblemSubmission {
    private String problemId;
    private String userId;
    private String code;
    private ProgrammingLanguage programmingLanguage;
}
