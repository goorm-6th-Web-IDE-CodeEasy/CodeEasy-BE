package aespa.codeeasy.controller;

import aespa.codeeasy.dto.EmailCheckDto;
import aespa.codeeasy.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping("/register/send-certification")
    public ResponseEntity sendEmail(@RequestParam("email") String email) {
        mailService.sendEmail(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/register/certificate-code")
    public ResponseEntity<Boolean> checkCertificationCode(@RequestBody EmailCheckDto emailCheckDto) {
        Boolean isValidCertificationCode = mailService.checkCertificationCode(emailCheckDto.getEmail(),
                emailCheckDto.getCertificationCode());

        if (isValidCertificationCode) {
            return ResponseEntity.ok(true); // 200 OK와 함께 true 반환
        } else {
            return ResponseEntity.ok(false); // 200 OK와 함께 false 반환
        }
    }
}
