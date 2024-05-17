package aespa.codeeasy.controller;

import aespa.codeeasy.util.JwtBlacklistUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogoutController {

    @Autowired
    private JwtBlacklistUtil jwtBlacklistUtil;

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwt = authorizationHeader.substring(7);
            jwtBlacklistUtil.addTokenToBlacklist(jwt);
        }
        return ResponseEntity.ok("로그아웃을 성공적으로 완료했습니다.");
    }

    @PostMapping("/logout-success")
    public ResponseEntity<String> logoutSuccess() {
        return ResponseEntity.ok("로그아웃을 성공적으로 완료했습니다.");
    }
}