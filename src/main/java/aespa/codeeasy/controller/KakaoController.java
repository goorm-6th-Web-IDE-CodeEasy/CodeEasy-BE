package aespa.codeeasy.controller;

import aespa.codeeasy.dto.KakaoDto;
import aespa.codeeasy.dto.MemberDto;
import aespa.codeeasy.service.MemberService;
import aespa.codeeasy.util.KakaoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class KakaoController {

    private final KakaoUtil kakaoUtil;
    private final MemberService memberService;

    @GetMapping("/api/login/kakao")
    public String moveToKakaoLogin() {
        String location = kakaoUtil.getKakaoUrl();

        return "redirect:" + location;
    }

    @GetMapping("/api/login/oauth2/kakao")
    @ResponseBody
    public ResponseEntity loginByKakao(@RequestParam("code") String code) {
        try {
            String accessToken = kakaoUtil.getAccessToken(code);
            KakaoDto kakaoDto = kakaoUtil.getUserInfoWithToken(accessToken);
            boolean isNewMember = checkMemberRegistration(kakaoDto);

            if (isNewMember) {
                return new ResponseEntity<>(kakaoDto, HttpStatus.CREATED);
            } else {
                return ResponseEntity.ok(kakaoDto);
            }
        } catch (Exception e) {
            log.error("Login or registration failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login or registration failed");
        }
    }

    private boolean checkMemberRegistration(KakaoDto kakaoDto) {
        if (!memberService.isNicknameExists(kakaoDto.getNickname())) {
            MemberDto memberDto = new MemberDto("", kakaoDto.getNickname(), kakaoDto.getEmail(), "");
            memberService.registerNewMemberAccount(memberDto);
            return true;
        }
        return false;
    }
}
