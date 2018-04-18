package com.codeking.problemsubmissionservice.domain;


import lombok.*;

/**
 * represents a single test case.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TestCase {

    private String input;
    private String output;
}
