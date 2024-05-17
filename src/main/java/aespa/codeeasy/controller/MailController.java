package aespa.codeeasy.controller;

import aespa.codeeasy.dto.EmailCheckDto;
import aespa.codeeasy.dto.EmailRequestDto;
import aespa.codeeasy.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping("/register/send-certification")
    public ResponseEntity sendEmail(@RequestBody EmailRequestDto emailRequestDto) {
        mailService.sendEmail(emailRequestDto.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/register/certificate-code")
    public ResponseEntity<Map<String,Boolean>> checkCertificationCode(@RequestBody EmailCheckDto emailCheckDto) {
        Boolean isValidCertificationCode = mailService.checkCertificationCode(emailCheckDto.getEmail(),
                emailCheckDto.getCertificationCode());
        Map<String, Boolean> response = new HashMap<>();
        if (isValidCertificationCode) {
            response.put("result", true);
            return ResponseEntity.ok(response); // 200 OK와 함께 true 반환
        } else {
            response.put("result", false);
            return ResponseEntity.ok(response); // 200 OK와 함께 false 반환
        }
    }
}
