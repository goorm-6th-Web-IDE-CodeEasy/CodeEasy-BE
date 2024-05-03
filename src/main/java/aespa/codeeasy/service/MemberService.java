package aespa.codeeasy.service;

import aespa.codeeasy.domain.Member;
import aespa.codeeasy.dto.MemberDto;
import aespa.codeeasy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository; // 회원 정보를 데이터베이스에서 관리하기 위한 JPA 리포지토리

    /**
     * 사용자가 입력한 회원 아이디가 데이터베이스에 이미 존재하는지 확인합니다.
     * @param memberId 사용자가 입력한 회원 아이디
     * @return 이미 존재하면 true, 그렇지 않으면 false를 반환합니다.
     */
    @Transactional(readOnly = true) // 데이터베이스에서 읽기만 수행되므로, readOnly 옵션을 true로 설정
    public boolean isMemberIdExists(String memberId) {
        return memberRepository.findByMemberId(memberId) != null;
    }

    /**
     * 사용자가 입력한 닉네임이 데이터베이스에 이미 존재하는지 확인합니다.
     * @param nickname 사용자가 입력한 닉네임
     * @return 이미 존재하면 true, 그렇지 않으면 false를 반환합니다.
     */
    @Transactional(readOnly = true) // 데이터베이스에서 읽기만 수행되므로, readOnly 옵션을 true로 설정
    public boolean isNicknameExists(String nickname) {
        return memberRepository.findByNickname(nickname) != null;
    }

    /**
     * 새 회원 계정을 등록합니다. 입력된 회원 아이디나 닉네임이 중복된 경우 예외를 발생시킵니다.
     * @param memberDto 회원 등록 정보를 담고 있는 데이터 전송 객체(DTO)
     * @return 등록된 회원의 엔티티 객체
     * @throws IllegalStateException 아이디 또는 닉네임이 중복되었을 때 발생
     */
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
        member.setPassword(memberDto.getPassword());
        return memberRepository.save(member); // 회원 정보를 데이터베이스에 저장하고 저장된 엔티티를 반환
    }
}

