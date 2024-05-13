package aespa.codeeasy.repository;

import aespa.codeeasy.domain.CProblem;
import org.springframework.stereotype.Repository;

@Repository
public interface CProblemRepository {
    CProblem findById(Long problemId);
}
