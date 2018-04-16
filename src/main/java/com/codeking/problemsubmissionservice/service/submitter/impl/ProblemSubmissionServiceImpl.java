package com.codeking.problemsubmissionservice.service.submitter.impl;

import com.codeking.problemsubmissionservice.domain.ProgrammingLanguage;
import com.codeking.problemsubmissionservice.domain.SubmissionStatus;
import com.codeking.problemsubmissionservice.service.compiler.api.CompilationService;
import com.codeking.problemsubmissionservice.service.compiler.dto.CompilationRequest;
import com.codeking.problemsubmissionservice.service.compiler.dto.CompilationResult;
import com.codeking.problemsubmissionservice.service.evaluator.api.ProblemEvaluationService;
import com.codeking.problemsubmissionservice.service.evaluator.dto.ProblemEvaluationRequest;
import com.codeking.problemsubmissionservice.service.evaluator.dto.ProblemEvaluationResult;
import com.codeking.problemsubmissionservice.service.submitter.api.ProblemSubmissionService;
import com.codeking.problemsubmissionservice.service.submitter.dto.ProblemSubmissionRequest;
import com.codeking.problemsubmissionservice.service.submitter.dto.ProblemSubmissionResult;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class ProblemSubmissionServiceImpl implements ProblemSubmissionService {


    @Autowired
    private CompilationService compilationService;

    @Autowired
    private ProblemEvaluationService problemEvaluationService;

    @Override
    public ProblemSubmissionResult submitProblem(ProblemSubmissionRequest problemSubmissionRequest) {
        final CompilationResult compilationResult =
                compileProgram(problemSubmissionRequest.getCode(), problemSubmissionRequest.getProgrammingLanguage());

        switch (compilationResult.getCompilationStatus()) {
            case COMPILATION_SUCCESS:
                ProblemEvaluationResult problemEvaluationResult = runProgram(problemSubmissionRequest.getProblemId(),
                        compilationResult.getExecutableProgramPath(),
                        problemSubmissionRequest.getProgrammingLanguage());

                final String status = problemEvaluationResult.getProblemEvaluationStatus().toString();
                return ProblemSubmissionResult.builder()
                        .submissionStatus(SubmissionStatus.valueOf(status))
                        .submissionError(problemEvaluationResult.getExecutorErrorOutput())
                        .build();
            case COMPILATION_ERROR:
                return ProblemSubmissionResult.builder()
                        .submissionStatus(SubmissionStatus.COMPILATION_ERROR)
                        .submissionError(compilationResult.getCompilerErrorOutput())
                        .build();
            default:
                throw new AssertionError("unknown compilation status: " + compilationResult.getCompilationStatus());
        }
    }

    private CompilationResult compileProgram(final String code, final ProgrammingLanguage programmingLanguage) {
        final CompilationRequest compilationRequest = CompilationRequest.builder()
                .code(code)
                .programmingLanguage(programmingLanguage)
                .build();
        return compilationService.compile(compilationRequest);
    }

    private ProblemEvaluationResult runProgram(final String problemId,
                                               final Path executableProgramPath, final ProgrammingLanguage programmingLanguage) {
        final ProblemEvaluationRequest problemEvaluationRequest = ProblemEvaluationRequest.builder()
                .problemId(problemId)
                .executableProgramPath(executableProgramPath)
                .programmingLanguage(programmingLanguage)
                .build();
        return problemEvaluationService.evaluateProblem(problemEvaluationRequest);
    }
}
