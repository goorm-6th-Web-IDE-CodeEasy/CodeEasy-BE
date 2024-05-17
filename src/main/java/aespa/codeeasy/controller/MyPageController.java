package aespa.codeeasy.controller;

import aespa.codeeasy.domain.Member;
import aespa.codeeasy.dto.*;
import aespa.codeeasy.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class MyPageController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/mypage/{id}")
    public ResponseEntity<MypageResponseDto> getMyPage(@PathVariable("id") Long id) {
        return ResponseEntity.ok(memberService.getMemberById(id));
    }

    @PatchMapping("/mypage/{id}/nickname")
    public ResponseEntity<MemberResponseDto> updateNickname(@PathVariable(name = "id") Long id, @RequestBody NicknameUpdateDto request) {
        Member updatedUser = memberService.updateNickname(id, request.getNickname());
        MemberResponseDto memberResponseDto = new MemberResponseDto(updatedUser);
        return ResponseEntity.ok(memberResponseDto);
    }

    @PatchMapping("/mypage/{id}/password")
    public ResponseEntity<?> updatePassword(@PathVariable Long id, @RequestBody PasswordUpdateDto passwordUpdateDto) {
        memberService.updatePassword(id, passwordUpdateDto.getNewPassword());
        return ResponseEntity.ok().body(new MypageCommonResponseDto(id, "패스워드를 성공적으로 수정하였습니다"));
    }

    @DeleteMapping("/mypage/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.ok().body(new MypageCommonResponseDto(id, "성공적으로 회원 탈퇴하였습니다"));
    }

    @GetMapping("/mypage/{id}/grass/{date}")
    public ResponseEntity<GrassResponseDto> checkActivity(@PathVariable Long id, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(memberService.getGrassInfo(id, date));
    }
}
