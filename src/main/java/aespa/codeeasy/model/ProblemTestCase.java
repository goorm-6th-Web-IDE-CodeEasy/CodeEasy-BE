package aespa.codeeasy.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class ProblemTestCase {
    @Id
    @GeneratedValue
    private Long problemTestCaseId;
    private String input;
    private String output;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    public ProblemTestCase() {

    }

    @Builder
    public ProblemTestCase(Long problemTestCaseId, String input, String output, Problem problem) {
        this.problemTestCaseId = problemTestCaseId;
        this.input = input;
        this.output = output;
        this.problem = problem;
    }
}
