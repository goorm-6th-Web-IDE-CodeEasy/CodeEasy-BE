package aespa.codeeasy.repository;

import aespa.codeeasy.domain.CProblem;

public interface CProblemRepository {
    CProblem findById(Long problemId);
}
