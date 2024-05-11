package aespa.codeeasy.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class ProblemFormat {
    @Id
    @GeneratedValue
    private Long problemStandardFormatId;
    private String code;
    @Enumerated(EnumType.STRING)
    private ProblemLanguage language;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    public ProblemFormat() {

    }

    @Builder
    public ProblemFormat(Long problemStandardFormatId, String code, ProblemLanguage language, Problem problem) {
        this.problemStandardFormatId = problemStandardFormatId;
        this.code = code;
        this.language = language;
        this.problem = problem;
    }
}