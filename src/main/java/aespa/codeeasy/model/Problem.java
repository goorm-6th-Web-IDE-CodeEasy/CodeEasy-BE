package aespa.codeeasy.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class Problem {
    @Id
    @GeneratedValue
    private Long problemId;
    private String title;
    private String description;
    private String restriction;
    private String inputOutputExample;
    @Enumerated(EnumType.STRING)
    private ProblemRank problemRank;

    public Problem() {

    }

    @Builder
    public Problem(Long problemId, String title, String description, String restriction, String inputOutputExample, ProblemRank problemRank) {
        this.problemId = problemId;
        this.title = title;
        this.description = description;
        this.restriction = restriction;
        this.inputOutputExample = inputOutputExample;
        this.problemRank = problemRank;
    }
}
