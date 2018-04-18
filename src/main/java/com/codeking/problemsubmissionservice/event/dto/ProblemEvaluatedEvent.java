package com.codeking.problemsubmissionservice.event.dto;

import com.codeking.problemsubmissionservice.domain.ProgrammingLanguage;
import com.codeking.problemsubmissionservice.domain.SubmissionStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProblemEvaluatedEvent {

    private String problemId;
    private String userId;
    private SubmissionStatus submissionStatus;
    private ProgrammingLanguage programmingLanguage;
}
