package com.codeking.problemsubmissionservice.service.compiler.impl;

public class JavaCompilation extends CompilationTemplate {

    private static final String COMPILATION_COMMAND = "javac";
    private static final String FILE_NAME = "Solution.java";

    @Override
    String getCompilationCommand() {
        return COMPILATION_COMMAND;
    }

    @Override
    String getFileName() {
        return FILE_NAME;
    }
}
