package aespa.codeeasy.controller;

import aespa.codeeasy.service.ProblemService;
import aespa.codeeasy.service.TextToSpeechService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProblemAudioController {

    @Autowired
    private TextToSpeechService textToSpeechService;

    @GetMapping("/problem/{problemId}/problem-sound")
    public ResponseEntity<byte[]> getProblemSound(@PathVariable Long problemId) {
        // 임시로 문제의 텍스트를 작성, 실제로는 데이터베이스에서 문제 내용을 가져와야 함
        String problemText = "문제 내용이 들어갑니다";
        byte[] audioContent = textToSpeechService.convertTextToSpeech(problemText);
        return ResponseEntity.ok()
                .header("Content-Type", "audio/mpeg")
                .body(audioContent);
    }

    @GetMapping("/problem/{problemId}/result-sound")
    public ResponseEntity<byte[]> getProblemResultSound(@PathVariable Long problemId) {
        // 임시로 결과 메시지 작성, 실제로는 결과를 평가하고 메시지를 생성하는 로직 필요
        String resultText = "문제 해결 결과는 성공입니다. 잘 하셨습니다!";
        byte[] audioContent = textToSpeechService.convertTextToSpeech(resultText);
        return ResponseEntity.ok()
                .header("Content-Type", "audio/mpeg")
                .body(audioContent);
    }
}
