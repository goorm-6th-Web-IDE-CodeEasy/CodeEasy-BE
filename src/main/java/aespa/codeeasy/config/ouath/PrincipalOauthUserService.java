package aespa.codeeasy.config.ouath;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauthUserService extends DefaultOAuth2UserService {
    //    깃허브로 부터 받은 userRequest 데이터에 대한 후처리 되는 함수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("userRequest:" + userRequest);
        System.out.println("getClientRegistration:" + userRequest.getClientRegistration()); //client에 대한 정보들이 받아짐
        System.out.println("getAccessToken:" + userRequest.getAccessToken());
        System.out.println("getAttributes:" + super.loadUser(userRequest).getAttributes()); //유저 정보를 받아옴
        return super.loadUser(userRequest);
    }

}
