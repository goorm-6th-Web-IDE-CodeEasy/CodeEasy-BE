package aespa.codeeasy.dto;

import lombok.Data;

@Data
public class ApiResponseDto<T> {
    private T data;
}
