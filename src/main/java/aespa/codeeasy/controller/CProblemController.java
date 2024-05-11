package aespa.codeeasy.controller;

import aespa.codeeasy.dto.ProblemDto;
import aespa.codeeasy.service.CProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CProblemController {

    private final CProblemService problemService;

    @GetMapping("/problem/{problemId}")
    public ProblemDto getProblem(@PathVariable Long problemId) {
        return problemService.getProblem(problemId);
    }
}
