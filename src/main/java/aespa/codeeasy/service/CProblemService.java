package aespa.codeeasy.service;

import aespa.codeeasy.domain.CProblem;
import aespa.codeeasy.dto.ProblemDto;
import aespa.codeeasy.repository.CProblemRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CProblemService {

    private final CProblemRepository problemRepository;

    public ProblemDto getProblem(Long problemId) {
        Optional<CProblem> problem = problemRepository.findById(problemId);
        return null;
    }
}
