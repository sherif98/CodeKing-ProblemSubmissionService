package com.codeking.problemsubmissionservice.service.evaluator.impl;

class JavaProblemEvaluation extends ProblemEvaluationTemplate {


    private static final String EXECUTION_COMMAND = "java";
    private static final String FILE_NAME = "Solution";

    @Override
    String getFileName() {
        return FILE_NAME;
    }

    @Override
    String getRunCommand() {
        return EXECUTION_COMMAND;
    }
}
