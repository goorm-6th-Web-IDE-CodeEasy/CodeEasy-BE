package aespa.codeeasy.controller;

import aespa.codeeasy.dto.JKCreateAccessTokenRequest;
import aespa.codeeasy.dto.JKCreateAccessTokenResponse;
import aespa.codeeasy.service.JKRefreshTokenService;
import aespa.codeeasy.service.JKTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class JKTokenApiController { // 토큰 관련 API를 제공하는 컨트롤러
    private final JKTokenService tokenService;
    private final JKRefreshTokenService refreshTokenService;

    @PostMapping("/api/token")
    public ResponseEntity<JKCreateAccessTokenResponse> createNewAccessToken(@RequestBody JKCreateAccessTokenRequest request) {
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new JKCreateAccessTokenResponse(newAccessToken));
    }

    @DeleteMapping("/api/refresh-token")
    public ResponseEntity deleteRefreshToken() {  // 리프레시 토큰 삭제 API
        refreshTokenService.delete();

        return ResponseEntity.ok()
                .build();
    }
}
