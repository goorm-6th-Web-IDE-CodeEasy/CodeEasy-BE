package aespa.codeeasy.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    
}
