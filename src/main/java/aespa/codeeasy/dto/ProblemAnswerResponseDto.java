package aespa.codeeasy.dto;

import aespa.codeeasy.model.ProblemAnswer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class ProblemAnswerResponseDto {
    private String code;
    private String language;
    private String uuid;

    public ProblemAnswerResponseDto() {

    }

    @Builder
    public ProblemAnswerResponseDto(String code, String language, String uuid) {
        this.code = code;
        this.language = language;
        this.uuid = uuid;
    }

    public static ProblemAnswerResponseDto of(ProblemAnswer problemAnswer) {
        return ProblemAnswerResponseDto.builder()
                .code(problemAnswer.getCode())
                .language(problemAnswer.getLanguage().name())
                .uuid(problemAnswer.getUuid().toString())
                .build();
    }

    public static List<ProblemAnswerResponseDto> listOf(List<ProblemAnswer> problemAnswers) {
        List<ProblemAnswerResponseDto> list = new ArrayList<>();
        for (ProblemAnswer problemAnswer : problemAnswers) {
            list.add(ProblemAnswerResponseDto.of(problemAnswer));
        }

        return list;
    }
}
