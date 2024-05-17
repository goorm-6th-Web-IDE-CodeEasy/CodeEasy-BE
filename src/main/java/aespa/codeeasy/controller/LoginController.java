package aespa.codeeasy.controller;

import aespa.codeeasy.JKglobal.login.service.LoginService;
import aespa.codeeasy.controller.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    @GetMapping("/login")
    public ResponseEntity<String> getLogin() {
        return ResponseEntity.ok("로그인 화면입니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<Void> loginUser(@RequestBody LoginRequest request) throws Exception {
        loginService.login(request);
        return ResponseEntity.ok().build();
    }
}