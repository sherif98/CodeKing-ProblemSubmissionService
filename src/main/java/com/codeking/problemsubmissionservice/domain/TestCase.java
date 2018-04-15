package com.codeking.problemsubmissionservice.domain;


import lombok.*;

import java.util.List;

/**
 * represents a single test case.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TestCase {

    private List<String> input;
    private List<String> output;
}
