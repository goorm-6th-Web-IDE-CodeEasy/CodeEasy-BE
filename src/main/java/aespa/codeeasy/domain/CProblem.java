package aespa.codeeasy.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CProblem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String problemTitle;
    private String problemContent;
    private String problemInputContent;
    private String problemOutputContent;

    private String algorithm;
    private String tier;

    private Long timeLimit;
    private Long memoryLimit;

    private String inputParameters;
    private String outputParameter;

    private String basicInputTestCase;
    private String basicOutputTestCase;

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TestCase> testCases;

}
