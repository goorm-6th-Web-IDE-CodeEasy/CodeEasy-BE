package aespa.codeeasy.dto;

import aespa.codeeasy.model.Problem;
import jakarta.persistence.Lob;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProblemDetailResponseDto {
    private Long problemId;
    private String title;
    @Lob
    private String description;
    @Lob
    private String restriction;
    @Lob
    private String inputOutputExample;
    private String rank;

    public ProblemDetailResponseDto() {

    }

    @Builder
    public ProblemDetailResponseDto(Long problemId, String title, String description, String restriction, String inputOutputExample, String rank) {
        this.problemId = problemId;
        this.title = title;
        this.description = description;
        this.restriction = restriction;
        this.inputOutputExample = inputOutputExample;
        this.rank = rank;
    }

    public static ProblemDetailResponseDto of (Problem problem) {
        return ProblemDetailResponseDto.builder()
                .problemId(problem.getProblemId())
                .title(problem.getTitle())
                .description(problem.getDescription())
                .restriction(problem.getRestriction())
                .inputOutputExample(problem.getInputOutputExample())
                .rank(problem.getProblemRank().name())
                .build();
    }
}
