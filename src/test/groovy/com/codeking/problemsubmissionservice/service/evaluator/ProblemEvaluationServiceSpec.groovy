package com.codeking.problemsubmissionservice.service.evaluator

import com.codeking.problemsubmissionservice.domain.Problem
import com.codeking.problemsubmissionservice.domain.ProgrammingLanguage
import com.codeking.problemsubmissionservice.domain.TestCase
import com.codeking.problemsubmissionservice.service.compiler.dto.CompilationRequest
import com.codeking.problemsubmissionservice.service.compiler.dto.CompilationResult
import com.codeking.problemsubmissionservice.service.compiler.impl.CompilationServiceImpl
import com.codeking.problemsubmissionservice.service.evaluator.api.ProblemEvaluationService
import com.codeking.problemsubmissionservice.service.evaluator.dto.ProblemEvaluationRequest
import com.codeking.problemsubmissionservice.service.evaluator.dto.ProblemEvaluationStatus
import com.codeking.problemsubmissionservice.service.evaluator.impl.ProblemEvaluationServiceImpl
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

@Subject(ProblemEvaluationService)
class ProblemEvaluationServiceSpec extends Specification {

    @Shared
    Problem sumOfNumbersProblem

    CompilationResult compilationResult


    def setupSpec() {
        def input1 = "6\n" +
                "1 2 3 4 5 6"
        def testCase1 = TestCase.builder()
                .input(input1)
                .output("21")
                .build()

        def input2 = "2\n" +
                "0 0"

        def testCase2 = TestCase.builder()
                .input(input2)
                .output("0")
                .build()
        sumOfNumbersProblem = Problem.builder()
                .problemTitle("Sum Of Numbers")
                .timeLimit(1000)
                .testCases(Arrays.asList(testCase1, testCase2))
                .build()
    }

    def "should return accepted status when all test cases pass within specified time limit"() {
        given: "code to pass all test cases"
        def code = "import java.util.Arrays;\n" +
                "import java.util.Scanner;\n" +
                "\n" +
                "class Solution {\n" +
                "    public static void main(String[] args) {\n" +
                "        Scanner scanner = new Scanner(System.in);\n" +
                "        int n = scanner.nextInt();\n" +
                "        int[] a = new int[n];\n" +
                "        for (int i = 0; i < n; i++) {\n" +
                "            a[i] = scanner.nextInt();\n" +
                "        }\n" +
                "        System.out.println(Arrays.stream(a).reduce(Integer::sum).getAsInt());\n" +
                "    }\n" +
                "}"
        and: "compilation service to compile the code"
        def compilationService = new CompilationServiceImpl()
        def compilationRequest = CompilationRequest.builder()
                .code(code)
                .programmingLanguage(ProgrammingLanguage.JAVA)
                .build()
        compilationResult = compilationService.compile(compilationRequest)
        and: "problem evaluation service"
        def problemEvaluationService = new ProblemEvaluationServiceImpl()
        def evaluationRequest = ProblemEvaluationRequest.builder()
                .programmingLanguage(ProgrammingLanguage.JAVA)
                .problem(sumOfNumbersProblem)
                .executableProgramPath(compilationResult.getExecutableProgramPath())
                .build()
        when:
        def evaluationResult = problemEvaluationService.evaluateProblem(evaluationRequest)
        then:
        evaluationResult.problemEvaluationStatus == ProblemEvaluationStatus.ACCEPTED
    }

    def "should return time limit status when one of the test cases doesn't finish in the specified time limit"() {
        given: "code to produce time limit exceeded"
        def code = "import java.util.Arrays;\n" +
                "import java.util.Scanner;\n" +
                "\n" +
                "class Solution {\n" +
                "    public static void main(String[] args) {\n" +
                "        Scanner scanner = new Scanner(System.in);\n" +
                "        int n = scanner.nextInt();\n" +
                "        int[] a = new int[n];\n" +
                "        try {\n" +
                "            Thread.sleep(2000);\n" +
                "        } catch (InterruptedException e) {\n" +
                "            e.printStackTrace();\n" +
                "        }\n" +
                "        for (int i = 0; i < n; i++) {\n" +
                "            a[i] = scanner.nextInt();\n" +
                "        }\n" +
                "        System.out.println(Arrays.stream(a).reduce(Integer::sum).getAsInt());\n" +
                "    }\n" +
                "}"
        and: "compilation service to compile the code"
        def compilationService = new CompilationServiceImpl()
        def compilationRequest = CompilationRequest.builder()
                .code(code)
                .programmingLanguage(ProgrammingLanguage.JAVA)
                .build()
        compilationResult = compilationService.compile(compilationRequest)
        and: "problem evaluation service"
        def problemEvaluationService = new ProblemEvaluationServiceImpl()
        def evaluationRequest = ProblemEvaluationRequest.builder()
                .programmingLanguage(ProgrammingLanguage.JAVA)
                .problem(sumOfNumbersProblem)
                .executableProgramPath(compilationResult.getExecutableProgramPath())
                .build()
        when:
        def evaluationResult = problemEvaluationService.evaluateProblem(evaluationRequest)
        then:
        evaluationResult.problemEvaluationStatus == ProblemEvaluationStatus.TIME_LIMIT_EXCEEDED
    }

    def "should return wrong answer status when one of the tests produces wrong answer"() {
        given: "code to pass produce wrong answer by ignoring the first value"
        def code = "import java.util.Arrays;\n" +
                "import java.util.Scanner;\n" +
                "\n" +
                "class Solution {\n" +
                "    public static void main(String[] args) {\n" +
                "        Scanner scanner = new Scanner(System.in);\n" +
                "        int n = scanner.nextInt();\n" +
                "        int[] a = new int[n];\n" +
                "        for (int i = 1; i < n; i++) {\n" +
                "            a[i] = scanner.nextInt();\n" +
                "        }\n" +
                "        System.out.println(Arrays.stream(a).reduce(Integer::sum).getAsInt());\n" +
                "    }\n" +
                "}"
        and: "compilation service to compile the code"
        def compilationService = new CompilationServiceImpl()
        def compilationRequest = CompilationRequest.builder()
                .code(code)
                .programmingLanguage(ProgrammingLanguage.JAVA)
                .build()
        compilationResult = compilationService.compile(compilationRequest)
        and: "problem evaluation service"
        def problemEvaluationService = new ProblemEvaluationServiceImpl()
        def evaluationRequest = ProblemEvaluationRequest.builder()
                .programmingLanguage(ProgrammingLanguage.JAVA)
                .problem(sumOfNumbersProblem)
                .executableProgramPath(compilationResult.getExecutableProgramPath())
                .build()
        when:
        def evaluationResult = problemEvaluationService.evaluateProblem(evaluationRequest)
        then:
        evaluationResult.problemEvaluationStatus == ProblemEvaluationStatus.WRONG_ANSWER
    }

    def "should return run time error status when one of the tests produces an error"() {
        given: "code to pass throw null pointer exception"
        def code = "class Solution {\n" +
                "    public static void main(String[] args) {\n" +
                "        throw new NullPointerException();\n" +
                "    }\n" +
                "}"
        and: "compilation service to compile the code"
        def compilationService = new CompilationServiceImpl()
        def compilationRequest = CompilationRequest.builder()
                .code(code)
                .programmingLanguage(ProgrammingLanguage.JAVA)
                .build()
        compilationResult = compilationService.compile(compilationRequest)
        and: "problem evaluation service"
        def problemEvaluationService = new ProblemEvaluationServiceImpl()
        def evaluationRequest = ProblemEvaluationRequest.builder()
                .programmingLanguage(ProgrammingLanguage.JAVA)
                .problem(sumOfNumbersProblem)
                .executableProgramPath(compilationResult.getExecutableProgramPath())
                .build()
        when:
        def evaluationResult = problemEvaluationService.evaluateProblem(evaluationRequest)
        then:
        evaluationResult.problemEvaluationStatus == ProblemEvaluationStatus.RUNTIME_ERROR
        !evaluationResult.executorErrorOutput.isEmpty()
    }


    def cleanup() {
        compilationResult.executableProgramPath.deleteDir()
    }
}
