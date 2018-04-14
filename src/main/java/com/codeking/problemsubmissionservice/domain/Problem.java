package com.codeking.problemsubmissionservice.domain;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "problem")
@Getter
@Setter
@NoArgsConstructor
@Builder
@ToString
public class Problem {

    @Id
    private String problemId;

    private String problemTitle;
    private long timeLimit;

    private List<TestCase> testCases;

}
