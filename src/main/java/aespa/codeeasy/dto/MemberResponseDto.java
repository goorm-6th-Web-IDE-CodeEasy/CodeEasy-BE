package aespa.codeeasy.dto;

import aespa.codeeasy.domain.Member;

public class MemberResponseDto {
    private Long id;
    private String nickname;
    private String email;

    // 생성자를 통해 Member 엔티티를 받아 필드를 초기화합니다.
    public MemberResponseDto(Member member) {
        this.id = member.getId();
        this.nickname = member.getNickname();
        this.email = member.getEmail();
    }

    // Getter 메소드들
    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    // toString 메소드 (디버깅을 위해 유용할 수 있습니다)
    @Override
    public String toString() {
        return "MemberResponseDto{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
