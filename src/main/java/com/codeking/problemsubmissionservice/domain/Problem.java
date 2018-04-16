package com.codeking.problemsubmissionservice.domain;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


/**
 * Problem Entity specifies the problem attributes.
 */
@Document(collection = "problem")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Problem {

    @Id
    private String problemId;

    private String problemTitle;
    private long timeLimit;

    private List<TestCase> testCases;

}
