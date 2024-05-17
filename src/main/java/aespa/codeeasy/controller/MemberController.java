package aespa.codeeasy.controller;

import aespa.codeeasy.domain.Member;
import aespa.codeeasy.dto.MemberDto;
import aespa.codeeasy.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    @PostMapping("/id-check")
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

    @PostMapping("/nickname-check")
    public ResponseEntity<?> checkNickname(@RequestParam String nickname) {
        boolean exists = memberService.isNicknameExists(nickname);
        Map<String, String> response = new HashMap<>();
        if (exists) {
            response.put("message", "이미 존재하는 닉네임입니다.");
            return ResponseEntity.status(409).body(response);
        }
        response.put("message", "사용 가능한 닉네임 입니다.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/jwt-test")
    public String jwtTest() {
        return "jwtTest 요청 성공";
    }
}
