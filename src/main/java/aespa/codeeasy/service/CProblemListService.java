package aespa.codeeasy.service;

import aespa.codeeasy.domain.CProblem;
import aespa.codeeasy.dto.CProblemListDto;
import aespa.codeeasy.repository.CProblemRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CProblemListService {

    private final CProblemRepository cProblemRepository;

    public List<CProblemListDto> getAllCProblem() {
        List<CProblem> cProblems = cProblemRepository.findAll();
        List<CProblemListDto> cProblemListDtos = new ArrayList<>();
        for (CProblem cProblem : cProblems) {
            CProblemListDto cProblemListDto = mapToCProblemListDto(cProblem);
            cProblemListDtos.add(cProblemListDto);
        }
        return cProblemListDtos;
    }

    private CProblemListDto mapToCProblemListDto(CProblem cProblem) {
        CProblemListDto cProblemListDto = new CProblemListDto();
        cProblemListDto.setProblemID(cProblem.getId());
        cProblemListDto.setTitle(cProblem.getProblemTitle());
        cProblemListDto.setTier(cProblem.getTier().toString());
        cProblemListDto.setAlgorithm(cProblem.getAlgorithm().toString());
        cProblemListDto.setRate(getRandomNumber());
        return cProblemListDto;
    }

    private int getRandomNumber() {
        Random random = new Random();
        return random.nextInt(101);
    }

}
