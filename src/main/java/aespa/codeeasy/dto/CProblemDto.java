package aespa.codeeasy.dto;

import lombok.Data;

@Data
public class CProblemDto {
    private String problemTitle;
    private String problemContent;
    private String problemInputContent;
    private String problemOutputContent;

    private String algorithm;
    private String tier;

    private Long timeLimit;
    private Long memoryLimit;

    private String basicInputTestCase;
    private String basicOutputTestCase;
}
