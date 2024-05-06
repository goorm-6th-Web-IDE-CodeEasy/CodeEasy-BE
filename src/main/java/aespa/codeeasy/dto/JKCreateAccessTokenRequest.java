package aespa.codeeasy.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JKCreateAccessTokenRequest { // 액세스 토큰 생성 요청 DTO
    private String refreshToken;
}