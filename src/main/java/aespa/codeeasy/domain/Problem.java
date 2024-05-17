package aespa.codeeasy.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Problem {

    @Id
    @GeneratedValue
    private Long problemId;
}
