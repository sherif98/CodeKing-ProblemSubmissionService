package com.codeking.problemsubmissionservice.service.compiler.impl;

import com.codeking.problemsubmissionservice.service.compiler.dto.CompilationResult;
import com.codeking.problemsubmissionservice.service.compiler.dto.CompilationStatus;
import com.codeking.problemsubmissionservice.service.compiler.dto.ProcessResult;
import com.google.common.io.Files;
import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@Log4j2
abstract class CompilationTemplate {


    final CompilationResult compile(String code) {

        Path tempDirectoryPath = createTempDirectory();

        String fileName = getFileName();

        Path filePath = createFile(tempDirectoryPath, fileName);

        writeCodeToFile(filePath, code);

        String command = getCompilationCommand();

        ProcessResult processResult = executeCommand(command, fileName, tempDirectoryPath);

        final CompilationStatus compilationStatus;
        if (processResult.getExitStatus() == 0) {
            compilationStatus = CompilationStatus.COMPILATION_SUCCESS;
        } else {
            compilationStatus = CompilationStatus.COMPILATION_ERROR;
        }
        return CompilationResult.builder()
                .compilationStatus(compilationStatus)
                .compilerErrorOutput(processResult.getCompilerError())
                .executableProgramPath(tempDirectoryPath)
                .build();
    }

    abstract String getFileName();

    abstract String getCompilationCommand();


    private ProcessResult executeCommand(String command, String fileName, Path tempDirectoryPath) {
        ProcessBuilder processBuilder = new ProcessBuilder(command, fileName)
                .directory(tempDirectoryPath.toFile());
        try {
            Process compilingProcess = processBuilder.start();
            int exitStatus = compilingProcess.waitFor();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(compilingProcess.getErrorStream()));
            String compilerError = bufferedReader.lines().collect(Collectors.joining("\n"));
            return ProcessResult.builder()
                    .exitStatus(exitStatus)
                    .compilerError(compilerError)
                    .build();
        } catch (IOException | InterruptedException e) {
            log.fatal(String.format("Can't execute command %s to compile file", command), e);
        }
        return ProcessResult.builder().
                exitStatus(-1)
                .compilerError("Error: Compiler Not Available")
                .build();
    }

    private Path createFile(Path tempDirectoryPath, String fileName) {
        Path path = Paths.get(tempDirectoryPath.toString(), fileName);
        try {
            Files.touch(path.toFile());
        } catch (IOException e) {
            log.fatal(String.format("Can't create file in temp directory %s", tempDirectoryPath.toString()), e);
            throw new RuntimeException("Can't create file");
        }
        return path;
    }

    private void writeCodeToFile(Path filePath, String code) {
        try {
            Files.write(code.getBytes(), filePath.toFile());
        } catch (IOException e) {
            log.fatal(String.format("Can't write code to file: %s", filePath.toString()), e);
            throw new RuntimeException("Can't write code to file");
        }
    }


    private Path createTempDirectory() {
        return Files.createTempDir().toPath();
    }

}
