package aespa.codeeasy.controller;

import jdk.jshell.JShell;
import jdk.jshell.Snippet;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Service
public class CodeExecutionService {

    public String executeCode(String code, String language) throws Exception {
        switch (language.toLowerCase()) {
            case "java":
                return executeJavaCode(code);
            case "python":
                return executePythonCode(code);
            case "c++":
                return executeCppCode(code);
            case "javascript":
                return executeJavaScriptCode(code);
            default:
                throw new CodeExecutionException("Unsupported language.");
        }
    }

    private String executeJavaCode(String code) throws Exception {
        JShell jShell = JShell.create();
        StringBuilder output = new StringBuilder();
        try {
            // 코드를 JShell에 로드하고 실행
            jShell.eval(code).forEach(snippetEvent -> {
                if (snippetEvent.value() != null) {
                    // 결과값을 가진 스니펫의 경우 결과 출력
                    output.append(snippetEvent.value()).append("\n");
                } else if (snippetEvent.exception() != null) {
                    // 예외가 발생한 스니펫의 경우 예외 메시지 출력
                    output.append("Error executing snippet: ").append(snippetEvent.exception().getMessage()).append("\n");
                } else if (snippetEvent.status() == Snippet.Status.REJECTED) {
                    // 스니펫이 실행되지 않은 경우
                    output.append("Snippet rejected: ").append(snippetEvent.snippet().source()).append("\n");
                }
            });

            // 명시적으로 결과를 요구하지 않는 코드(예: 메소드 호출)에 대해
            // 콘솔에 출력되는 내용을 캡처
            jShell.onSnippetEvent(event -> {
                if (event.causeSnippet() == null && event.value() != null) {
                    output.append(event.value()).append("\n");
                }
            });

        } finally {
            jShell.close();
        }
        return output.toString();
    }


    private String executePythonCode(String code) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder("python3", "-c", code);
        return runProcess(builder);
    }

    private String executeCppCode(String code) throws IOException, InterruptedException {
        File sourceFile = File.createTempFile("code-", ".cpp");
        sourceFile.deleteOnExit();
        try (FileWriter writer = new FileWriter(sourceFile)) {
            writer.write(code);
        }

        String executable = sourceFile.getAbsolutePath().replace(".cpp", "");
        Process compile = new ProcessBuilder("g++", "-o", executable, sourceFile.getAbsolutePath()).start();
        compile.waitFor();

        if (compile.exitValue() != 0) {
            try (InputStream errorStream = compile.getErrorStream()) {
                return new String(errorStream.readAllBytes(), StandardCharsets.UTF_8);
            }
        }

        Process execute = new ProcessBuilder(executable).start();
        execute.waitFor();
        try (InputStream inputStream = execute.getInputStream();
             InputStream errorStream = execute.getErrorStream()) {
            if (execute.exitValue() == 0) {
                return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            } else {
                return new String(errorStream.readAllBytes(), StandardCharsets.UTF_8);
            }
        } finally {
            new File(executable).delete(); // Ensure the executable is deleted
        }
    }


    private String executeJavaScriptCode(String code) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder("node", "-e", code);
        return runProcess(builder);
    }

    private String runProcess(ProcessBuilder builder) throws IOException, InterruptedException {
        builder.redirectErrorStream(true);
        Process process = builder.start();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } finally {
            process.waitFor();
        }
    }
}
