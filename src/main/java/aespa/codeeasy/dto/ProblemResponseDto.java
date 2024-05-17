package aespa.codeeasy.dto;

import aespa.codeeasy.domain.ProblemRank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ProblemResponseDto {

    private Long problemId;
    private String title;
    private String description;
    private String restriction;
    private String inputOutputExample;
    private ProblemRank problemRank;

}
