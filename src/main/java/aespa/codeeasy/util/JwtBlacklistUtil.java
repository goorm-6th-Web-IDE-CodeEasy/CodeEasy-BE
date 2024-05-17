package aespa.codeeasy.util;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class JwtBlacklistUtil {
    private Set<String> blacklist = new HashSet<>();

    // JWT 토큰을 블랙리스트에 추가
    public void addTokenToBlacklist(String jwt) {
        blacklist.add(jwt);
    }

    // JWT 토큰이 블랙리스트에 있는지 확인
    public boolean isTokenBlacklisted(String token) {
        return blacklist.contains(token);
    }
}

