package com.codeking.problemsubmissionservice.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "submission")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Submission {

    @Id
    private String submissionId;

    private String code;
    private SubmissionStatus submissionStatus;

    @Indexed
    private String userId;

    private String problemTitle;
}
