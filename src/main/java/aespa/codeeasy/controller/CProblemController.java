package aespa.codeeasy.controller;

import aespa.codeeasy.dto.CompileRequestDto;
import aespa.codeeasy.dto.CompileResponseDto;
import aespa.codeeasy.dto.ProblemDto;
import aespa.codeeasy.service.CProblemService;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CProblemController {

    private final CProblemService problemService;

    @GetMapping("/problem/{problemId}")
    public ResponseEntity<ProblemDto> getProblem(@PathVariable("problemId") Long problemId) {
        Optional<ProblemDto> optionalProblemDto = problemService.getProblem(problemId);
        return optionalProblemDto
                .map(problemDto -> ResponseEntity.ok().body(problemDto))
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/problem/{problemId}/run")
    public ResponseEntity runProblem(@PathVariable("problemId") Long problemId,
                                     @RequestBody CompileRequestDto compileRequestDto) {
        try {
            CompileResponseDto compileResponseDto = problemService.runProblem(problemId, compileRequestDto.getCode(),
                    compileRequestDto.getLanguage());
            return ResponseEntity.ok().body(compileResponseDto);
        } catch (IOException e) {
            return ResponseEntity
                    .status(500) // 상태 코드를 500으로 설정
                    .body("Internal Server Error: " + e.getMessage());
        } catch (InterruptedException e) {
            return ResponseEntity
                    .status(500) // 상태 코드를 500으로 설정
                    .body("Internal Server Error: " + e.getMessage());
        }
    }
}
