package com.codeking.problemsubmissionservice.exception;


import lombok.Getter;

@Getter
public class ProblemNotFoundException extends RuntimeException {

    private String problemId;

    public ProblemNotFoundException(String problemId) {
        super(String.format("Can't find problem with Id %s", problemId));
        this.problemId = problemId;
    }
}
