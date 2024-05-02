package aespa.codeeasy.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberDto {
    private String memberId;
    private String nickname;
    private String email;
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
