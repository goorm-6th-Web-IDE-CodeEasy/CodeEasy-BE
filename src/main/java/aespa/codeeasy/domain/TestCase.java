package aespa.codeeasy.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TestCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_case_id")
    private Long id;

    @Column(name = "test_case", nullable = false, length = 2000)
    private String testCase;

    @Column(name = "answer", nullable = false, length = 2000)
    private String answer;

    @ManyToOne
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;

}
