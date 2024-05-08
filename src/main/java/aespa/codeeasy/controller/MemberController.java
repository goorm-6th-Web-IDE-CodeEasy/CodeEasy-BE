package aespa.codeeasy.controller;

import aespa.codeeasy.domain.Member;
import aespa.codeeasy.dto.MemberDto;
import aespa.codeeasy.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<?> registerMember(@RequestBody @Valid MemberDto memberDto) {
        try {
            Member registered = memberService.registerNewMemberAccount(memberDto);
            return ResponseEntity.ok(registered);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("회원가입 실패: " + e.getMessage());
        }
    }

//    @GetMapping("/id-check")
//    public ResponseEntity<?> checkMemberId(@RequestParam String memberId) {
//        boolean exists = memberService.isMemberIdExists(memberId);
//        if (exists) {
//            return ResponseEntity.status(409).body("이미 존재하는 아이디입니다.");
//        }
//        return ResponseEntity.ok("사용 가능한 아이디 입니다.");
//    }

    @GetMapping("/nickname-check")
    public ResponseEntity<?> checkNickname(@RequestParam String nickname) {
        boolean exists = memberService.isNicknameExists(nickname);
        if (exists) {
            return ResponseEntity.status(409).body("이미 존재하는 닉네임입니다.");
        }
        return ResponseEntity.ok("사용 가능한 닉네임 입니다.");
    }

    @GetMapping("/jwt-test")
    public String jwtTest() {
        return "jwtTest 요청 성공";
    }
}
