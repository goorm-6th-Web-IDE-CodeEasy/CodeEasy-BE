package aespa.codeeasy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FAQController {

    @GetMapping("/faqpage")
    public ResponseEntity<String> getFaqPage() {
        // 실제 어플리케이션에서는 이 정보를 데이터베이스에서 가져오거나,
        // 도움말 관련 데이터를 파일에서 불러와 반환할 수 있습니다.
        String faqContent = "자주 묻는 질문과 대답입니다";
        return ResponseEntity.ok(faqContent);
    }
}
