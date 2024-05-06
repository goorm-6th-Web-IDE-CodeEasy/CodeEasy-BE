package aespa.codeeasy.config.JKoauth;

import aespa.codeeasy.domain.Member;
import aespa.codeeasy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class JKOAuth2UserCustomService extends DefaultOAuth2UserService {
    // OAuth2UserService 인터페이스를 구현한 DefaultOAuth2UserService 클래스를 상속받아 사용자 정보를 가져오는 클래스

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 요청을 바탕으로 유저 정보를 담은 객체 반환
        OAuth2User user = super.loadUser(userRequest);
        saveOrUpdate(user);

        return user;
    }

    // 유저가 있으면 업데이트, 없으면 유저 생성
    private Member saveOrUpdate(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email = (String) attributes.get("email");
        String nickname = (String) attributes.get("name"); // 이름을 가져오고 싶다면 이렇게 사용
        // String nickname = generateRandomNickname(); // 닉네임을 랜덤 생성

        Member member = memberRepository.findByEmail(email)
                .map(entity -> entity.update(nickname)) // 닉네임을 업데이트
                .orElse(Member.builder()
                        .email(email)
                        .nickname(nickname)
                        .tier("bronze")
                        .build());

        return memberRepository.save(member);
    }

    // 랜덤 닉네임 생성 로직
    private String generateRandomNickname() {
        String alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder nicknameBuilder = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 8; i++) {
            char randomChar = alphanumeric.charAt(random.nextInt(alphanumeric.length()));
            nicknameBuilder.append(randomChar);
        }

        return "user-" + nicknameBuilder.toString();
    }


}
