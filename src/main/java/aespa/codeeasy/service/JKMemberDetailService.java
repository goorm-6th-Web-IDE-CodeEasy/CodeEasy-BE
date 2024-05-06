package aespa.codeeasy.service;

import aespa.codeeasy.domain.Member;
import aespa.codeeasy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JKMemberDetailService implements UserDetailsService { // UserDetailsService 인터페이스를 구현한 MemberDetailService 클래스
    private final MemberRepository memberRepository;
    @Override
    public Member loadUserByUsername(String email) { // 사용자 이름(이메일)을 전달받아 해당 사용자 정보를 조회하는 메서드
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException((email)));
    }
}