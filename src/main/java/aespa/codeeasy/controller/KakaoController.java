package aespa.codeeasy.controller;

import aespa.codeeasy.util.KakaoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class KakaoController {

    private final KakaoUtil kakaoUtil;

    @GetMapping("/api/login/kakao")
    public String moveToKakaoLogin() {
        String location = kakaoUtil.getKakaoUrl();

        return "redirect:" + location;
    }

    @GetMapping("/api/login/oauth2/kakao")
    public String loginByKakao(@RequestParam("code") String code) {

        return null;
    }
}
