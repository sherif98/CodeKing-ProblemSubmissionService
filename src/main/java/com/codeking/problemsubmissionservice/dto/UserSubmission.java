package com.codeking.problemsubmissionservice.dto;

import com.codeking.problemsubmissionservice.domain.SubmissionStatus;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@ToString
public class UserSubmission {

    private String submissionId;
    private SubmissionStatus submissionStatus;
    private String problemTitle;
}
