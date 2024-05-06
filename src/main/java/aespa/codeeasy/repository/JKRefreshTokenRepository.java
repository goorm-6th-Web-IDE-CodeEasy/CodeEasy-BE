package aespa.codeeasy.repository;

import aespa.codeeasy.domain.JKRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JKRefreshTokenRepository extends JpaRepository<JKRefreshToken, Long> { // RefreshToken 엔티티를 관리하는 JpaRepository 인터페이스
    Optional<JKRefreshToken> findByUserId(Long userId);
    Optional<JKRefreshToken> findByRefreshToken(String refreshToken);

    void deleteByUserId(Long userId);
}
