package aespa.codeeasy.service;

import aespa.codeeasy.domain.Member;
import aespa.codeeasy.dto.MemberDto;
import aespa.codeeasy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository; // 회원 정보를 데이터베이스에서 관리하기 위한 JPA 리포지토리
    private final BCryptPasswordEncoder passwordEncoder; // 패스워드 암호화를 위한 PasswordEncoder

    @Transactional(readOnly = true) // 데이터베이스에서 읽기만 수행되므로, readOnly 옵션을 true로 설정
    public boolean isMemberIdExists(String memberId) {
        return memberRepository.findByMemberId(memberId).isPresent(); // Optional 객체를 반환하므로 isPresent() 메서드로 존재 여부를 확인
    }

    @Transactional(readOnly = true) // 데이터베이스에서 읽기만 수행되므로, readOnly 옵션을 true로 설정
    public boolean isNicknameExists(String nickname) {
        return memberRepository.findByNickname(nickname) != null;
    }

    public Member registerNewMemberAccount(MemberDto memberDto) {
        // 아이디 중복 검사
        if (isMemberIdExists(memberDto.getMemberId())) {
            throw new IllegalStateException("아이디 중복."); // 예외 발생
        }
        // 닉네임 중복 검사
        if (isNicknameExists(memberDto.getNickname())) {
            throw new IllegalStateException("닉네임 중복"); // 예외 발생
        }

        // 회원 정보 생성
        Member member = new Member();
        member.setMemberId(memberDto.getMemberId());
        member.setNickname(memberDto.getNickname());
        member.setEmail(memberDto.getEmail());
        String password = passwordEncoder.encode(memberDto.getPassword()); // 패스워드 암호화
        member.setPassword(password);
        return memberRepository.save(member); // 회원 정보를 데이터베이스에 저장하고 저장된 엔티티를 반환
    }

    public Member findById(Long userId) { // 회원 식별자로 회원 정보를 조회하는 메서드
        return memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public Member findByEmail(String email) { // 이메일 주소로 회원 정보를 조회하는 메서드
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }
}

