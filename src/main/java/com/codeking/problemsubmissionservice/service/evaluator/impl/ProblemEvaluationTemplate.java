package com.codeking.problemsubmissionservice.service.evaluator.impl;

import com.codeking.problemsubmissionservice.domain.TestCase;
import com.codeking.problemsubmissionservice.service.evaluator.dto.ProblemEvaluationResult;
import com.codeking.problemsubmissionservice.service.evaluator.dto.ProblemEvaluationStatus;
import lombok.extern.log4j.Log4j2;

import java.io.*;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Log4j2
abstract class ProblemEvaluationTemplate {

    /**
     * for each test case you evaluate the program making sure it finishes within
     * time limit and produces correct answer with no runtime errors.
     * if all tests met the previous criteria we return accepted status.
     * else return the status that caused the problem.
     */
    final ProblemEvaluationResult evaluateProblem(Path executablePath, List<TestCase> testCases, long timeLimit) {
        return testCases.stream()
                .map(testCase -> evaluateTestCase(testCase, timeLimit, executablePath))
                .filter(res -> res.getProblemEvaluationStatus() != ProblemEvaluationStatus.ACCEPTED)
                .findFirst()
                .orElseGet(this::acceptedStatus);
    }


    /**
     * create and start a process to execute the program.
     * pass the testcase input to the process.
     * wait for the process to finish until the specified time limit.
     * if exceeded time limit destroy process and return time limit status.
     * get process exit value if value is not zero return runtime error status.
     * get process output if didn't match the test case output return wrong answer status.
     * if passed all previous return accepted status.
     */
    private ProblemEvaluationResult evaluateTestCase(TestCase testCase, long timeLimit, Path executablePath) {
        String fileName = getFileName();
        String command = getRunCommand();
        ProcessBuilder processBuilder = new ProcessBuilder(command, fileName).directory(executablePath.toFile());
        System.out.println(processBuilder.directory());
        try {
            Process runningProcess = processBuilder.start();

            setProcessInput(testCase.getInput(), runningProcess);

            boolean finishedWithinTimeLimit = runningProcess.waitFor(timeLimit, TimeUnit.MILLISECONDS);
            if (!finishedWithinTimeLimit) {
                runningProcess.destroy();
                return timeLimitStatus();
            }

            int exitValue = runningProcess.exitValue();
            if (exitValue != 0) {
                return runTimeErrorStatus(getOutputFromStream(runningProcess.getErrorStream()));
            }

            String output = getOutputFromStream(runningProcess.getInputStream());
            boolean isAnswerCorrect = output.equals(testCase.getOutput());

            if (isAnswerCorrect) {
                return acceptedStatus();
            }
            return wrongAnswerStatus();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            log.fatal(String.format("Can't execute command %s to run file", command), e);
            return runTimeErrorStatus("Error: Compiler Not Available");
        }
    }

    abstract String getFileName();

    abstract String getRunCommand();

    private void setProcessInput(String input, Process runningProcess) {
        PrintStream printStream = new PrintStream(runningProcess.getOutputStream());
        printStream.print(input);
        log.info("program input is " + input);
        printStream.close();
    }

    private String getOutputFromStream(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        return bufferedReader.lines().collect(Collectors.joining("\n"));
    }

    private ProblemEvaluationResult wrongAnswerStatus() {
        return ProblemEvaluationResult.builder()
                .problemEvaluationStatus(ProblemEvaluationStatus.WRONG_ANSWER)
                .build();
    }


    private ProblemEvaluationResult acceptedStatus() {
        return ProblemEvaluationResult.builder()
                .problemEvaluationStatus(ProblemEvaluationStatus.ACCEPTED)
                .build();
    }

    private ProblemEvaluationResult timeLimitStatus() {
        return ProblemEvaluationResult.builder()
                .problemEvaluationStatus(ProblemEvaluationStatus.TIME_LIMIT_EXCEEDED)
                .build();
    }

    private ProblemEvaluationResult runTimeErrorStatus(String executionError) {
        return ProblemEvaluationResult.builder()
                .problemEvaluationStatus(ProblemEvaluationStatus.RUNTIME_ERROR)
                .executorErrorOutput(executionError)
                .build();
    }
}