package aespa.codeeasy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ThemePageController {

    @GetMapping("/themepage")
    public ResponseEntity<String> getThemeSettings() {
        // 실제 애플리케이션에서는 이 정보를 데이터베이스에서 가져오거나,
        // 사용자 설정에 맞게 동적으로 생성할 수 있습니다.
        String themeSettingInfo = "유저 테마 설정 페이지";
        return ResponseEntity.ok(themeSettingInfo);
    }
}
