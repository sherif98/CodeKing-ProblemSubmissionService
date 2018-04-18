package com.codeking.problemsubmissionservice.controller.dto;

import com.codeking.problemsubmissionservice.domain.SubmissionStatus;
import lombok.*;

/**
 * dto represents a problem submitted and evaluated by a user.
 * used when retrieving multiple submissions by userId.
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
