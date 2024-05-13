package aespa.codeeasy.controller;

import aespa.codeeasy.domain.CProblem;
import aespa.codeeasy.dto.ProblemDto;
import aespa.codeeasy.repository.CProblemRepository;
import aespa.codeeasy.service.CProblemService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CProblemController {

    private final CProblemService problemService;
    private final CProblemRepository problemRepository;

    @GetMapping("/problem/{problemId}")
    public ResponseEntity<ProblemDto> getProblem(@PathVariable("problemId") Long problemId) {
        Optional<ProblemDto> optionalProblemDto = problemService.getProblem(problemId);
        return optionalProblemDto
                .map(problemDto -> ResponseEntity.ok().body(problemDto))
                .orElse(ResponseEntity.notFound().build());
    }

    //    @PostConstruct
    public void init() {
        CProblem problem = new CProblem();
        problem.setProblemTitle("A+B");
        problem.setProblemContent("문제\n A와 B가 주어질 때 A+B의 값을 구하시오.");
        problem.setProblemInputContent("입력\n A와 B가 주어진다.");
        problem.setProblemOutputContent("출력\n A+B를 출력하시오.");
        problem.setAlgorithm("수학");
        problem.setTier("브론즈5");
        problem.setTimeLimit(1L);
        problem.setMemoryLimit(128L);

        problemRepository.save(problem);
    }
}
