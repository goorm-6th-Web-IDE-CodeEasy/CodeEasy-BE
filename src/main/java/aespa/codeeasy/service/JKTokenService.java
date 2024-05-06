package aespa.codeeasy.service;

import aespa.codeeasy.config.JKjwt.JKTokenProvider;
import aespa.codeeasy.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class JKTokenService { // 토큰을 관리하는 서비스 클래스
    private final JKTokenProvider tokenProvider;
    private final JKRefreshTokenService refreshTokenService;
    private final MemberService memberService;

    public String createNewAccessToken(String refreshToken) { // 리프레시 토큰을 전달받아 새로운 액세스 토큰을 생성하는 메서드
        if(!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected token");
        }

        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        Member member = memberService.findById(userId);

        return tokenProvider.generateToken(member, Duration.ofHours(2));
    }
}