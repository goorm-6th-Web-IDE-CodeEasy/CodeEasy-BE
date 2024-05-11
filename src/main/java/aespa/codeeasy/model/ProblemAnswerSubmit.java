package aespa.codeeasy.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Entity
public class ProblemAnswerSubmit {
    @Id
    @GeneratedValue
    private Long problemAnswerSubmitId;
    private String code;
    @Enumerated(EnumType.STRING)
    private ProblemLanguage language;
    private UUID uuid;
    @Enumerated(EnumType.STRING)
    private ProblemStatus status;
    private Timestamp submitDate;
    private Long problemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_answer_id")
    private ProblemAnswer problemAnswer;

    public ProblemAnswerSubmit() {

    }

    @Builder
    public ProblemAnswerSubmit(Long problemAnswerSubmitId, String code, ProblemLanguage language, UUID uuid, ProblemStatus status, Timestamp submitDate, Long problemId, ProblemAnswer problemAnswer) {
        this.problemAnswerSubmitId = problemAnswerSubmitId;
        this.code = code;
        this.language = language;
        this.uuid = uuid;
        this.status = status;
        this.submitDate = submitDate;
        this.problemId = problemId;
        this.problemAnswer = problemAnswer;
    }
}
