package com.codeking.problemsubmissionservice.controller;


import com.codeking.problemsubmissionservice.controller.dto.ErrorResponse;
import com.codeking.problemsubmissionservice.exception.ProblemNotFoundException;
import com.codeking.problemsubmissionservice.exception.SubmissionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(ProblemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse problemNotFound(ProblemNotFoundException exc) {
        String problemId = exc.getProblemId();
        return ErrorResponse.builder()
                .errorMessage(String.format("Can't find Problem with Id %s", problemId))
                .build();
    }


    @ExceptionHandler(SubmissionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse submissionNotFound(SubmissionNotFoundException exc) {
        String submissionId = exc.getSubmissionId();
        return ErrorResponse.builder()
                .errorMessage(String.format("Can't find Submission with Id %s", submissionId))
                .build();
    }

}
