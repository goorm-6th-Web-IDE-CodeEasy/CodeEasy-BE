package aespa.codeeasy.repository;

import aespa.codeeasy.domain.CProblem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CProblemListRepository extends JpaRepository<CProblem, Long> {
    @Override
    List<CProblem> findAll();
}
