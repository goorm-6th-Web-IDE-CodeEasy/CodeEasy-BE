package aespa.codeeasy.controller;

import aespa.codeeasy.JKglobal.jwt.service.JwtService;
import aespa.codeeasy.dto.FavoriteDTO;
import aespa.codeeasy.service.FavoriteService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/problem/{problemId}/favorite")
    public ResponseEntity<?> addFavorite(@PathVariable Long problemId, HttpServletRequest request) {
        try {
            boolean added = favoriteService.addFavoriteUsingToken(problemId, request);
            if (added) {
                return ResponseEntity.ok("Problem added to favorites successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Problem could not be added to favorites.");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<FavoriteDTO>> getFavoritesByUserId(HttpServletRequest request) {
        List<FavoriteDTO> favorites = favoriteService.getFavoritesByToken(request);
        return ResponseEntity.ok(favorites);
    }



}
