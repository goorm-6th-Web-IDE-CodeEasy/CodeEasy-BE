package aespa.codeeasy.repository;

import aespa.codeeasy.domain.DefaultCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DefaultCodeRepository extends JpaRepository<DefaultCode, Long> {
    //Optional<DefaultCode> findByProblemIdAndLanguage(Long problemId, String language);
    Optional<DefaultCode> findByProblem_ProblemIdAndLanguage(Long problemId, String language);

}
