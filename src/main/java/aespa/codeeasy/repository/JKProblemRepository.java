package aespa.codeeasy.repository;

//import aespa.codeeasy.domain.Problem;
import aespa.codeeasy.domain.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JKProblemRepository extends JpaRepository<Problem, Long> {
}
