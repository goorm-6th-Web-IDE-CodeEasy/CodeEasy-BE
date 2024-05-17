package aespa.codeeasy.service;

import com.google.cloud.texttospeech.v1.*;
import com.google.protobuf.ByteString;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TextToSpeechService {

    public byte[] convertTextToSpeech(String text) {
        try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()) {
            SynthesisInput input = SynthesisInput.newBuilder().setText(text).build();
            VoiceSelectionParams voice = VoiceSelectionParams.newBuilder()
                    .setLanguageCode("ko-KR")  // 한국어로 설정
                    .setSsmlGender(SsmlVoiceGender.FEMALE) // 여성 나레이션
                    .build();
            AudioConfig audioConfig = AudioConfig.newBuilder()
                    .setAudioEncoding(AudioEncoding.MP3) // 오디오 형식을 MP3로 설정
                    .build();
            SynthesizeSpeechResponse response = textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);
            ByteString audioContents = response.getAudioContent();
            return audioContents.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("음성 인식이 실패했습니다", e);
        }
    }
}
