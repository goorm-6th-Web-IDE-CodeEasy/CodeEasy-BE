package aespa.codeeasy.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodeRequest {

    private String code;        // 사용자가 제출한 코드
    private String language;    // 프로그래밍 언어 (예: java, python, c++, javascript)
    private Integer timeLimit;  // 선택적: 코드 실행 제한 시간 (초 단위)
    private Integer memoryLimit; // 선택적: 코드 실행 메모리 제한 (메가바이트 단위)

    // 기본 생성자
    public CodeRequest() {
    }

    // 모든 필드를 포함하는 생성자
    public CodeRequest(String code, String language, Integer timeLimit, Integer memoryLimit) {
        this.code = code;
        this.language = language;
        this.timeLimit = timeLimit;
        this.memoryLimit = memoryLimit;
    }

    // 편의를 위한 toString 메서드 (디버깅용)
    @Override
    public String toString() {
        return "CodeRequest{" +
                "code='" + code + '\'' +
                ", language='" + language + '\'' +
                ", timeLimit=" + timeLimit +
                ", memoryLimit=" + memoryLimit +
                '}';
    }
}
