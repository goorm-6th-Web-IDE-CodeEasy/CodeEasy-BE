package aespa.codeeasy.dto;

import java.util.List;
import lombok.Data;

@Data
public class CompileResponseDto {
    private int testCaseCount;
    private int correctCount;

    private List<String> statusList;
    private List<String> dataList;

    public CompileResponseDto(int testCaseCount, int correctCount, List<String> statusList,
                              List<String> dataList) {
        this.testCaseCount = testCaseCount;
        this.correctCount = correctCount;
        this.statusList = statusList;
        this.dataList = dataList;
    }
}
