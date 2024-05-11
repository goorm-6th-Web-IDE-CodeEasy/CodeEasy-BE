package aespa.codeeasy.repository;

//import aespa.codeeasy.domain.Problem;
import aespa.codeeasy.model.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {
}
