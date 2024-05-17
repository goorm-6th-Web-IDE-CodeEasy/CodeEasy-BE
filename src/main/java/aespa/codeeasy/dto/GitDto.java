package aespa.codeeasy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GitDto {

    private String nickname;
    private String email;

    public GitDto(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }
}
