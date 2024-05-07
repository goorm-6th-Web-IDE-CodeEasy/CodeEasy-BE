package aespa.codeeasy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KakaoController {

    @Value("${kakao.client_id}")
    private String client_id;

    @Value("${kakao.redirect_uri}")
    private String redirect_uri;

    @GetMapping("/api/login/kakao")
    public String moveToKakaoLogin() {
        String location =
                "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=" + client_id
                        + "&redirect_uri=" + redirect_uri;
        return "redirect:" + location;
    }
}
