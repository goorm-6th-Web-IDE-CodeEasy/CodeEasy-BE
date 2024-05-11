package aespa.codeeasy.controller;

import aespa.codeeasy.controller.request.CodeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/api/code")
public class CodeExecutionController {

    @Autowired
    private CodeExecutionService codeExecutionService;

    @PostMapping("/run")
    public ResponseEntity<?> executeCode(@RequestBody CodeRequest request) {
        if (!isValidLanguage(request.getLanguage())) {
            return ResponseEntity.badRequest().body("Unsupported language.");
        }
        try {
            String result = codeExecutionService.executeCode(request.getCode(), request.getLanguage());
            System.out.println(result);
            return ResponseEntity.ok().body(result);
        } catch (CodeExecutionException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in code execution: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }

    private boolean isValidLanguage(String language) {
        return Arrays.asList("java", "python", "c++", "javascript").contains(language.toLowerCase());
    }
}

class CodeExecutionException extends RuntimeException {
    public CodeExecutionException(String message) {
        super(message);
    }
}