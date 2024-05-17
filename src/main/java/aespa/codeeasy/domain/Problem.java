package aespa.codeeasy.domain;

import jakarta.persistence.*;
import lombok.Builder;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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

    public Long getId() {
        return null;
    }

    public String getDifficulty() {
        return null;
    }
}
