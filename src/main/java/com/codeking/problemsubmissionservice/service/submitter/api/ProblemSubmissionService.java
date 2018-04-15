package com.codeking.problemsubmissionservice.service.submitter.api;

import com.codeking.problemsubmissionservice.service.submitter.dto.ProblemSubmissionRequest;
import com.codeking.problemsubmissionservice.service.submitter.dto.ProblemSubmissionResult;

/**
 * Problem Submission service used to compile the problem and evaluate it through available test cases.
 */
public interface ProblemSubmissionService {

    /**
     * @param problemSubmissionRequest: code: code submitted by the user to be evaluated.
     *                                  programmingLanguage: the programming language that the code is written with.
     *                                  problemId: Id of the problem to be evaluated.
     * @return submissionStatus:
     * returns a COMPILATION_ERROR in case of failure at compilation.
     * returns an ACCEPTED status in case of all the test cases have been successful.
     * returns a TIME_LIMIT_EXCEEDED status in case of running one of the test cases exceeds the specified time limit.
     * returns a WRONG_ANSWER status in case of the output of one of the test cases returns a wrong result.
     * returns a RUNTIME_ERROR status in case of running one of the test cases throws an exception or suffers from an error while running.
     * submissionError: executor error output only available in case of COMPILATION_ERROR or RUNTIME_ERROR.
     */
    ProblemSubmissionResult submitProblem(ProblemSubmissionRequest problemSubmissionRequest);
}
