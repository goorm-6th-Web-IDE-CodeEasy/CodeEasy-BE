package aespa.codeeasy.config.JKjwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties("jwt")
public class JKJwtProperties { // JWT 설정 정보를 담는 클래스
    private String issuer;
    private String secretKey;
}
