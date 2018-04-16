package com.codeking.problemsubmissionservice.service.compiler

import com.codeking.problemsubmissionservice.domain.ProgrammingLanguage
import com.codeking.problemsubmissionservice.service.compiler.api.CompilationService
import com.codeking.problemsubmissionservice.service.compiler.dto.CompilationRequest
import com.codeking.problemsubmissionservice.service.compiler.dto.CompilationResult
import com.codeking.problemsubmissionservice.service.compiler.dto.CompilationStatus
import com.codeking.problemsubmissionservice.service.compiler.impl.CompilationServiceImpl
import spock.lang.Specification
import spock.lang.Subject

@Subject(CompilationService)
class CompilationServiceSpec extends Specification {

    CompilationResult compilationResult

    def "should return compile success status and path of executable when no error happens during compilation"() {
        given:
        def compilationService = new CompilationServiceImpl()
        when:
        compilationResult = compilationService.compile(CompilationRequest
                .builder()
                .programmingLanguage(ProgrammingLanguage.JAVA)
                .code("public class Solution {}")
                .build())
        then:
        compilationResult.compilationStatus == CompilationStatus.COMPILATION_SUCCESS
        compilationResult.compilerErrorOutput.isEmpty()
        !compilationResult.executableProgramPath.toString().isEmpty()
    }

    def "should return compile error status when source code has errors "() {
        given:
        def compilationService = new CompilationServiceImpl()
        when:
        compilationResult = compilationService.compile(CompilationRequest
                .builder()
                .programmingLanguage(ProgrammingLanguage.JAVA)
                .code("public class Solution {")
                .build())
        then:
        compilationResult.compilationStatus == CompilationStatus.COMPILATION_ERROR
        !compilationResult.compilerErrorOutput.isEmpty()
    }

    def cleanup() {
        compilationResult.executableProgramPath.deleteDir()
    }

}
