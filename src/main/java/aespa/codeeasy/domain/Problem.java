package aespa.codeeasy.domain;

import aespa.codeeasy.domain.type.Algorithm;
import aespa.codeeasy.domain.type.Tier;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_id")
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 2000)
    private String content;

    @Column(nullable = true, length = 2000)
    private String inputContent;

    @Column(nullable = true, length = 2000)
    private String outputContent;

    @Enumerated(EnumType.STRING)
    private Algorithm algorithm;

    @Enumerated(EnumType.STRING)
    private Tier tier;

    @Column(nullable = false)
    private Long timeLimit;

    @Column(nullable = false)
    private Long memoryLimit;

    @OneToMany(mappedBy = "problem")
    private List<TestCase> testCases;

    public Problem(Long problemId) {
    }
}
