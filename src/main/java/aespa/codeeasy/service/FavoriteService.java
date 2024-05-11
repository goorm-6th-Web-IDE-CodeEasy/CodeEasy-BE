package aespa.codeeasy.service;

import aespa.codeeasy.JKglobal.jwt.service.JwtService;
import aespa.codeeasy.domain.Favorite;
import aespa.codeeasy.domain.Member;
//import aespa.codeeasy.domain.Problem;
import aespa.codeeasy.model.Problem;
import aespa.codeeasy.dto.FavoriteDTO;
import aespa.codeeasy.repository.FavoriteRepository;
import aespa.codeeasy.repository.MemberRepository;
import aespa.codeeasy.repository.JKProblemRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final MemberRepository memberRepository;
    private final JKProblemRepository problemRepository;
    private final JwtService jwtService;

    // DTO 변환 메소드
    public FavoriteDTO convertToDTO(Favorite favorite) {
        return new FavoriteDTO(
                favorite.getId(),
                favorite.getMember().getId(),
                favorite.getProblem().getProblemId()
        );
    }

    // 즐겨찾기 추가
    public boolean addFavorite(Long userId, Long problemId) {
        // 사용자와 문제를 데이터베이스에서 찾기
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new IllegalArgumentException("Problem not found with ID: " + problemId));

        // 중복 즐겨찾기 확인
        Optional<Favorite> existingFavorite = favoriteRepository.findByMemberAndProblem(member, problem);
        if (existingFavorite.isPresent()) {
            throw new IllegalStateException("Favorite already exists for this user and problem");
        }

        // 즐겨찾기 객체 생성 및 저장
        Favorite favorite = new Favorite();
        favorite.setMember(member);
        favorite.setProblem(problem);
        favoriteRepository.save(favorite);
        return true;
    }

    // 토큰을 사용한 즐겨찾기 추가
    public boolean addFavoriteUsingToken(Long problemId, HttpServletRequest request) {
        Long userId = extractUserIdFromToken(request);
        return addFavorite(userId, problemId);
    }

    // 사용자 ID로 즐겨찾기 조회
    public List<FavoriteDTO> getFavoritesByUserId(Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        return favoriteRepository.findByMember(member).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 토큰을 사용한 즐겨찾기 조회
    public List<FavoriteDTO> getFavoritesByToken(HttpServletRequest request) {
        Long userId = extractUserIdFromToken(request);
        return getFavoritesByUserId(userId);
    }

    // 토큰에서 사용자 ID 추출
    private Long extractUserIdFromToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid or missing Authorization header");
        }
        return jwtService.extractUserId(token.substring(7))
                .orElseThrow(() -> new IllegalArgumentException("Invalid Token or User not found"));
    }
}

