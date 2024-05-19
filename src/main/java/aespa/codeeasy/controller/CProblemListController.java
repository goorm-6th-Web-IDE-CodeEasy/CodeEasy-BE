package aespa.codeeasy.controller;

import aespa.codeeasy.dto.CProblemListDto;
import aespa.codeeasy.service.CProblemListService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CProblemListController {

    private final CProblemListService cProblemListService;

    @GetMapping("/problemlist")
    public ResponseEntity getProblemList() {
        List<CProblemListDto> cProblemListDtos = cProblemListService.getAllCProblem();
        return ResponseEntity.ok(cProblemListDtos);
    }
}
