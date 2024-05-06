package aespa.codeeasy.service;

import aespa.codeeasy.config.JKjwt.JKTokenProvider;
import aespa.codeeasy.domain.JKRefreshToken;
import aespa.codeeasy.repository.JKRefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JKRefreshTokenService { // RefreshToken 엔티티를 관리하는 서비스 클래스
    private final JKRefreshTokenRepository refreshTokenRepository;
    private final JKTokenProvider tokenProvider;

    public JKRefreshToken findByRefreshToken(String refreshToken) { // 리프레시 토큰을 전달받아 해당 리프레시 토큰 정보를 조회하는 메서드
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected token"));
    }

    @Transactional
    public void delete() { // 현재 사용자의 리프레시 토큰 정보를 삭제하는 메서드
        String token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        Long userId = tokenProvider.getUserId(token);

        refreshTokenRepository.deleteByUserId(userId);
    }
}
