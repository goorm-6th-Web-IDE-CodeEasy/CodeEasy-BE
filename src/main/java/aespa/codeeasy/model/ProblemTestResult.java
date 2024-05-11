package aespa.codeeasy.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProblemTestResult {
    private Long ProblemTestCaseNo;
    private String input;
    private String expectedOutput;
    private String actualOutput;
    private String message;
    private String status;

    public ProblemTestResult() {

    }

    @Builder
    public ProblemTestResult(Long problemTestCaseNo, String input, String expectedOutput, String actualOutput, String message, String status) {
        ProblemTestCaseNo = problemTestCaseNo;
        this.input = input;
        this.expectedOutput = expectedOutput;
        this.actualOutput = actualOutput;
        this.message = message;
        this.status = status;
    }
}