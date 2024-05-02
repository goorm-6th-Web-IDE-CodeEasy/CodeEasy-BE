package aespa.codeeasy.service;

import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private static Integer certificationCode;

    private void createCertificationCode(){
        int certificationCodeLength = 6;

        Random random = new Random();
        StringBuilder builder = new StringBuilder();

        for(int length = 0; length < certificationCodeLength; length++) {
            builder.append(random.nextInt(10));
        }

        certificationCode = Integer.parseInt(builder.toString());
    }

    public void sendEmail(String email) {

    }
}
