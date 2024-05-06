package aespa.codeeasy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JKCreateAccessTokenResponse { // 액세스 토큰 생성 응답 DTO
    private String accessToken;
}
