package aespa.codeeasy.controller;

import aespa.codeeasy.dto.CodeDto;
import aespa.codeeasy.service.CCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CCodeController {

    private final CCodeService codeService;

    @GetMapping("/problem/{problemId}")
    public CodeDto getProblem(@PathVariable Long problemId) {
        return codeService.getProblem(problemId);
    }
}
