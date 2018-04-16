package com.codeking.problemsubmissionservice.dto;

import com.codeking.problemsubmissionservice.domain.SubmissionStatus;
import lombok.*;

/**
 * dto represents a problem submitted and evaluated by a user.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserSubmission {

    private String submissionId;
    private SubmissionStatus submissionStatus;
    private String problemTitle;
}
