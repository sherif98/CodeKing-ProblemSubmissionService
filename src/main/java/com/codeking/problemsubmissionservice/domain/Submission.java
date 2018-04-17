package com.codeking.problemsubmissionservice.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Submission specifies the problem after it has been submitted and evaluated.
 * used as an entity to be stored in database.
 */
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

    private ProgrammingLanguage programmingLanguage;
}
