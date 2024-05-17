package aespa.codeeasy.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TestCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    private CProblem problem;

    private String inputTestCase;
    private String outputTestCase;

//    @Override
//    public String toString() {
//        return "TestCase{" +
//                "inputTestCase='" + inputTestCase + '\'' +
//                ", outputTestCase='" + outputTestCase + '\'' +
//                '}';
//    }
}
