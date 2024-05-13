package aespa.codeeasy.repository;

import aespa.codeeasy.domain.CProblem;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CProblemRepository extends JpaRepository<CProblem, Long> {
    Optional<CProblem> findById(Long problemId);
}
