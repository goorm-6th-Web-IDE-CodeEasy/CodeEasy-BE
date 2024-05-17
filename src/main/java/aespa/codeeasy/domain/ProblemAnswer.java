package aespa.codeeasy.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Entity
public class ProblemAnswer {
    @Id
    @GeneratedValue
    private Long problemAnswerId;
    private String code;
    @Enumerated(EnumType.STRING)
    private ProblemLanguage language;
    private UUID uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_Id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    private LocalDate date;

    public void modifyCode(String code) {
        this.code = code;
    }

    public ProblemAnswer() {

    }

    @Builder
    public ProblemAnswer(Long problemAnswerId, String code, ProblemLanguage language, UUID uuid, Member member, Problem problem) {
        this.problemAnswerId = problemAnswerId;
        this.code = code;
        this.language = language;
        this.uuid = uuid;
        this.member = member;
        this.problem = problem;
    }

//    public static ProblemAnswer of(ProblemAnswerRequestDto problemAnswerRequestDto) {
//        return ProblemAnswer.builder()
//                .code(problemAnswerRequestDto.getCode())
//                .language(problemAnswerRequestDto.getLanguage())
//                .build();
//    }
}
