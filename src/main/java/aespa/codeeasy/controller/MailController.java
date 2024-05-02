package aespa.codeeasy.controller;

import aespa.codeeasy.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping("/register/send-certification")
    public ResponseEntity sendEmail(@RequestParam("email") String email){
        mailService.sendEmail(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
