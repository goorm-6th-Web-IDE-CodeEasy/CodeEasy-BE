package aespa.codeeasy.controller;

import aespa.codeeasy.dto.CProblemDto;
import aespa.codeeasy.dto.CompileRequestDto;
import aespa.codeeasy.dto.CompileResponseDto;
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
    public ResponseEntity<CProblemDto> getProblem(@PathVariable("problemId") Long problemId) {
        Optional<CProblemDto> optionalProblemDto = problemService.getProblem(problemId);
        return optionalProblemDto
                .map(CProblemDto -> ResponseEntity.ok().body(CProblemDto))
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/problem/{problemId}/run")
    public ResponseEntity runProblem(@PathVariable("problemId") Long problemId,
                                     @RequestBody CompileRequestDto compileRequestDto) {

        String code = compileRequestDto.getCode();
        String language = compileRequestDto.getLanguage();
        try {
            CompileResponseDto compileResponseDto = problemService.runProblem(problemId, code, language);
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

    @PatchMapping("/problem/{problemId}/grade")
    public ResponseEntity gradeProblem(@PathVariable("problemId") Long problemId,
                                       @RequestBody CompileRequestDto compileRequestDto) {

        String code = compileRequestDto.getCode();
        String language = compileRequestDto.getLanguage();
        try {
            CompileResponseDto compileResponseDto = problemService.gradeProblem(problemId, code, language);
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
