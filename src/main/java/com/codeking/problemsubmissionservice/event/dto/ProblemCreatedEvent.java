package com.codeking.problemsubmissionservice.event.dto;

import com.codeking.problemsubmissionservice.domain.TestCase;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProblemCreatedEvent {

    private String problemId;

    private String problemTitle;
    private long timeLimit;

    private List<TestCase> testCases;
}
