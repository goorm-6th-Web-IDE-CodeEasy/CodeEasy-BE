package aespa.codeeasy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainPageController {

    @GetMapping("/mainpage")
    public ResponseEntity<String> getMainPate() {
        String welcomeMessage = "홈 페이지에 오신 것을 환영합니다!";
        return ResponseEntity.ok(welcomeMessage);
    }
}
