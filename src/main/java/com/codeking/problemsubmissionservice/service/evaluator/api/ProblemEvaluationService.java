package com.codeking.problemsubmissionservice.service.evaluator.api;

import com.codeking.problemsubmissionservice.service.evaluator.dto.ProblemEvaluationRequest;
import com.codeking.problemsubmissionservice.service.evaluator.dto.ProblemEvaluationResult;


/**
 * Problem Evaluation Service used to run the problem against the available test cases to evaluate the submission.
 */
public interface ProblemEvaluationService {

    /**
     * @param problemEvaluationRequest: problemId: Id of the problem to be evaluated.
     *                                  executableProgramPath: path of the program to be executed.
     *                                  programmingLanguage: programming language of the program.
     * @return ProblemEvaluationStatus:
     * returns an ACCEPTED status in case of all the test cases have been successful.
     * returns a TIME_LIMIT_EXCEEDED status in case of running one of the test cases exceeds the specified time limit.
     * returns a WRONG_ANSWER status in case of the output of one of the test cases returns a wrong result.
     * returns a RUNTIME_ERROR status in case of running one of the test cases throws an exception or suffers from an error while running.
     * executorOutput: executor error output only available in case of RUNTIME_ERROR.
     */
    ProblemEvaluationResult evaluateProblem(ProblemEvaluationRequest problemEvaluationRequest);
}
