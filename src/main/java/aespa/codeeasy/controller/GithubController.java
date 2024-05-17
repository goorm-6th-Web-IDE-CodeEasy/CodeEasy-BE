package aespa.codeeasy.controller;

import aespa.codeeasy.JKglobal.jwt.service.JwtService;
import aespa.codeeasy.dto.GitDto;
import aespa.codeeasy.dto.KakaoDto;
import aespa.codeeasy.dto.MemberDto;
import aespa.codeeasy.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class GithubController {

    private final JwtService jwtService;
    private final MemberService memberService;
    // 프론트에서 https://github.com/login/oauth/authorize?client_id={client_id} 로 접속
    @Value("${spring.security.oauth2.client.registration.github.client-id}")
    private String github_client_id;

    @Value("${spring.security.oauth2.client.registration.github.client-secret}")
    private String github_client_secret;

    @GetMapping("/login/github")
    public String loginGithub(){
        return "https://github.com/login/oauth/authorize?client_id=" + github_client_id;
    }

    @GetMapping("/callback")
    public ResponseEntity<GitDto> getUserInfo(@RequestParam String code) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded");

        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("code",code);
        params.add("client_id", github_client_id);
        params.add("client_secret", github_client_secret);

        HttpEntity<MultiValueMap<String,String>> githubToken = new HttpEntity<>(params,httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://github.com/login/oauth/access_token",
                HttpMethod.POST,
                githubToken,
                String.class
        );
        String accessToken = extractAccessToken(response.getBody());


        RestTemplate restTemplate2 = new RestTemplate();
        HttpHeaders httpHeaders2 = new HttpHeaders();
        httpHeaders2.add("Authorization", "Bearer "+ accessToken);
        httpHeaders2.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String,String> params2 = new LinkedMultiValueMap<>();


        HttpEntity<MultiValueMap<String,String>> githubToken2 = new HttpEntity<>(params2,httpHeaders2);

        ResponseEntity<String> response2 = restTemplate2.exchange(
                "https://api.github.com/user",
                HttpMethod.GET,
                githubToken2,
                String.class
        );

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response2.getBody());
        String email = root.path("email").asText();
        String name = root.path("login").asText();
        GitDto gitDto = new GitDto(name, email);
        boolean isNewMember = checkMemberRegistration(gitDto);

        if (isNewMember) {
            return new ResponseEntity<>(gitDto, HttpStatus.CREATED);
        } else {
            return ResponseEntity.ok(gitDto);
        }
    }

    private boolean checkMemberRegistration(GitDto gitDto) {
        if (!memberService.isNicknameExists(gitDto.getNickname())) {
            MemberDto memberDto = new MemberDto(gitDto.getNickname(), gitDto.getEmail(), "");
            memberService.registerNewMemberAccount(memberDto);
            return true;
        }
        return false;
    }

    public String extractAccessToken(String response) {
        String[] parts = response.split("&");
        for (String part : parts) {
            if (part.startsWith("access_token=")) {
                return part.substring("access_token=".length());
            }
        }
        return null;
    }


    @PostMapping("/login/github")
    public String loginWithGitHub() {
        // GitHub 로그인 페이지로 리디렉션
        return "redirect://oauth2/authorization/github";
    }

    @GetMapping("/loginSuccess")
    public String loginSuccess(@AuthenticationPrincipal OidcUser principal) {
        // 로그인 성공 시 사용자 정보를 반환합니다.
        return "로그인을 성공하였습니다: " + principal.getName();
    }

    @GetMapping("/loginFailure")
    public String loginFailure() {
        return "로그인 실패하였습니다.";
    }
}
