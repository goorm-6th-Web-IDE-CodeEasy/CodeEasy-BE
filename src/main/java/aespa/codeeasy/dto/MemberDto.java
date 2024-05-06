package aespa.codeeasy.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberDto {

    private String memberId;

    @NotBlank(message = "닉네임 입력은 필수입니다.")
    private String nickname;

    @Email(message = "유효하지 않은 이메일입니다.")
    private String email;

    @NotBlank(message = "패스워드 입력은 필수입니다.")
    private String password;

    // 기본 생성자
    public MemberDto() {
    }

    // 모든 필드를 초기화하는 생성자
    public MemberDto(String memberId, String nickname, String email, String password) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }
}