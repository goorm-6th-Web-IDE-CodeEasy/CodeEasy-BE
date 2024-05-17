package aespa.codeeasy.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Editor {
    private String code;

    public Editor() {

    }

    @Builder
    public Editor(String code) {
        this.code = code;
    }

//    public static Editor of(ProblemAnswerRequestDTO problemAnswerRequestDTO) {
//        return Editor.builder()
//                .code(problemAnswerRequestDTO.getCode())
//                .build();
//    }
}