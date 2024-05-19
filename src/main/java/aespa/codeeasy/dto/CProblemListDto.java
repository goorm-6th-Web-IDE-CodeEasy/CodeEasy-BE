package aespa.codeeasy.dto;

import lombok.Data;

@Data
public class CProblemListDto {
    private Long problemID;
    private String title;
    private String tier;
    private String algorithm;
    private int rate;
}
