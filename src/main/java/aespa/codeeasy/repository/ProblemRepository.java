package aespa.codeeasy.repository;

import aespa.codeeasy.domain.CProblem;
import org.springframework.stereotype.Repository;

@Repository
public class ProblemRepository implements CProblemRepository {
    @Override
    public CProblem findById(Long problemId) {
        return null;
    }
}
