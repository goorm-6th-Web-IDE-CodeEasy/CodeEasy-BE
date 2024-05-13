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

    public Optional<ProblemDto> getProblem(Long problemId) {
        Optional<CProblem> optionalProblem = problemRepository.findById(problemId);
        return optionalProblem.map(this::mapToDto);
    }

    private ProblemDto mapToDto(CProblem cProblem) {
        ProblemDto problemDto = new ProblemDto();
        problemDto.setProblemTitle(cProblem.getProblemTitle());
        problemDto.setProblemContent(cProblem.getProblemContent());
        problemDto.setProblemInputContent(cProblem.getProblemInputContent());
        problemDto.setProblemOutputContent(cProblem.getProblemOutputContent());
        problemDto.setAlgorithm(cProblem.getAlgorithm());
        problemDto.setTier(cProblem.getTier());
        problemDto.setTimeLimit(cProblem.getTimeLimit());
        problemDto.setMemoryLimit(cProblem.getMemoryLimit());
        return problemDto;
    }

}
