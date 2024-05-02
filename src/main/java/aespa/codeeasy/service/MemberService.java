package aespa.codeeasy.service;

import aespa.codeeasy.domain.Member;
import aespa.codeeasy.dto.MemberDto;
import aespa.codeeasy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member registerNewMemberAccount(MemberDto memberDto) {
        Member member = new Member();
        member.setMemberId(memberDto.getMemberId());
        member.setNickname(memberDto.getNickname());
        member.setPassword(memberDto.getPassword());
        member.setEmail(memberDto.getEmail());
        return memberRepository.save(member);
    }
}
