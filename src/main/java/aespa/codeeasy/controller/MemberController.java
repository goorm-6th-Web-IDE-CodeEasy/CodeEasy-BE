package aespa.codeeasy.controller;

import aespa.codeeasy.domain.Member;
import aespa.codeeasy.dto.ApiResponseDto;
import aespa.codeeasy.dto.MemberDto;
import aespa.codeeasy.dto.NicknameDto;
import aespa.codeeasy.service.MemberService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/register")
    public ResponseEntity<?> getRegisterMember() {
        return ResponseEntity.ok("register 페이지 입니다.");
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerMember(@RequestBody @Valid MemberDto memberDto) {
        try {
            Member registered = memberService.registerNewMemberAccount(memberDto);
            return ResponseEntity.ok(registered);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("회원가입 실패: " + e.getMessage());
        }
    }

    @GetMapping("/id-check")
    public ResponseEntity<?> checkMemberId(@RequestParam Long memberId) {
        boolean exists = memberService.isMemberIdExists(memberId);
        Map<String, String> response = new HashMap<>();
        if (exists) {
            response.put("message", "이미 존재하는 아이디입니다.");
            return ResponseEntity.status(409).body(response);
        }
        response.put("message", "사용 가능한 아이디 입니다.");
        return ResponseEntity.ok("사용 가능한 아이디 입니다.");
    }

    @GetMapping("/register/nickname-check")
    public ResponseEntity checkNickname(@RequestParam("nickname") String nickname) {
        boolean exists = memberService.isNicknameExists(nickname);
        NicknameDto nicknameDto = new NicknameDto();
        ApiResponseDto apiResponseDto = new ApiResponseDto();
        apiResponseDto.setData(nicknameDto);
        if (exists) {
            nicknameDto.setAvailable(false);
            return ResponseEntity.ok().body(apiResponseDto);
        }
        nicknameDto.setAvailable(true);
        return ResponseEntity.ok().body(apiResponseDto);

    }

    @GetMapping("/jwt-test")
    public String jwtTest() {
        return "jwtTest 요청 성공";
    }

    @GetMapping("/me")
    public ResponseEntity<Member> getCurentUser(@RequestHeader("Authorization") String token) {
        Member member = memberService.getUserFromToken(token);
        return ResponseEntity.ok(member);
    }
}
