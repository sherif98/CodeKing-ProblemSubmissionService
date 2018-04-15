package com.codeking.problemsubmissionservice.service.compiler.api;

import com.codeking.problemsubmissionservice.service.compiler.dto.CompilationRequest;
import com.codeking.problemsubmissionservice.service.compiler.dto.CompilationResult;

/**
 * Compilation service used to compile all the problems and returns the executable program path if successful.
 */
public interface CompilationService {

    /**
     * @param compilationRequest: code: code to be compiled.
     *                            programmingLanguage: the programming language that the code is written with.
     * @return compilationStatus:
     * returns a COMPILATION_SUCCESS in case of successful compilation.
     * returns a COMPILATION_ERROR in case of failure at compilation.
     * executableProgramPath: the path of the compiled program only available when the compilation status is COMPILATION_SUCCESS.
     * compilerErrorOutput: compiler error output only available when the compilation status is COMPILATION_ERROR.
     */
    CompilationResult compile(CompilationRequest compilationRequest);
}
