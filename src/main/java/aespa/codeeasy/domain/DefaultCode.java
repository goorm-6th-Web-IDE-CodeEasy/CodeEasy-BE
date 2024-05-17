package aespa.codeeasy.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class DefaultCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "default_code_id")
    private Long id; // 기본 코드의 고유 식별자

    private String language; // 사용하는 프로그래밍 언어명

    private String defaultCode; // 해당 언어의 기본 코드

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problemId")  // 외래 키 컬럼 이름 지정
    private Problem problem;  // Problem 엔티티에 대한 참조
}
