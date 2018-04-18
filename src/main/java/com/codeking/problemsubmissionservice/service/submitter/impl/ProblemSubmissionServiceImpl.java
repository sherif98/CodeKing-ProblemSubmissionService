package com.codeking.problemsubmissionservice.service.submitter.impl;

import com.codeking.problemsubmissionservice.domain.Problem;
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
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import java.io.IOException;
import java.nio.file.Path;

@Service
@Log4j2
@AllArgsConstructor
@NoArgsConstructor
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
                ProblemEvaluationResult problemEvaluationResult = runProgram(problemSubmissionRequest.getProblem(),
                        compilationResult.getExecutableProgramPath(),
                        problemSubmissionRequest.getProgrammingLanguage());

                final String status = problemEvaluationResult.getProblemEvaluationStatus().toString();

                cleanUpDirectory(compilationResult.getExecutableProgramPath());

                return ProblemSubmissionResult.builder()
                        .submissionStatus(SubmissionStatus.valueOf(status))
                        .submissionError(problemEvaluationResult.getExecutorErrorOutput())
                        .build();
            case COMPILATION_ERROR:
                cleanUpDirectory(compilationResult.getExecutableProgramPath());
                return ProblemSubmissionResult.builder()
                        .submissionStatus(SubmissionStatus.COMPILATION_ERROR)
                        .submissionError(compilationResult.getCompilerErrorOutput())
                        .build();
            default:
                throw new AssertionError("unknown compilation status: " + compilationResult.getCompilationStatus());
        }
    }

    private void cleanUpDirectory(Path executableProgramPath) {
        try {
            FileSystemUtils.deleteRecursively(executableProgramPath);
        } catch (IOException e) {
            log.error("Cant clean up directory with path " + executableProgramPath, e);
        }
    }

    private CompilationResult compileProgram(final String code, final ProgrammingLanguage programmingLanguage) {
        final CompilationRequest compilationRequest = CompilationRequest.builder()
                .code(code)
                .programmingLanguage(programmingLanguage)
                .build();
        return compilationService.compile(compilationRequest);
    }

    private ProblemEvaluationResult runProgram(final Problem problem,
                                               final Path executableProgramPath, final ProgrammingLanguage programmingLanguage) {
        final ProblemEvaluationRequest problemEvaluationRequest = ProblemEvaluationRequest.builder()
                .problem(problem)
                .executableProgramPath(executableProgramPath)
                .programmingLanguage(programmingLanguage)
                .build();
        return problemEvaluationService.evaluateProblem(problemEvaluationRequest);
    }
}
