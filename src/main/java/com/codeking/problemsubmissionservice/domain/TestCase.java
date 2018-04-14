package com.codeking.problemsubmissionservice.domain;


import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TestCase {

    private List<String> input;
    private List<String> output;
}
