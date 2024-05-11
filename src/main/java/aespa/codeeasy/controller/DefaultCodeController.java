package aespa.codeeasy.controller;

import aespa.codeeasy.service.DefaultCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DefaultCodeController {

    private final DefaultCodeService defaultCodeService;

    @GetMapping("/problem/{problemId}/{language}")
    public ResponseEntity<?> getDefaultCode(@PathVariable Long problemId, @PathVariable String language) {
        return defaultCodeService.getDefaultCodeByProblemIdAndLanguage(problemId, language)
                .map(defaultCode -> ResponseEntity.ok(defaultCode.getDefaultCode()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}
