package aespa.codeeasy.service;

import aespa.codeeasy.domain.Member;
import aespa.codeeasy.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(MemberService.class)
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    private static final String EXISTING_MEMBER_ID = "testUser";
    private static final String NON_EXISTING_MEMBER_ID = "nonExistentUser";
    private static final String EXISTING_NICKNAME = "uniqueNickname";
    private static final String NON_EXISTING_NICKNAME = "nonExistentNickname";

    @BeforeEach
    public void setUp() {
        Member member = new Member();
        member.setMemberId(EXISTING_MEMBER_ID);
        member.setNickname(EXISTING_NICKNAME);
        memberRepository.save(member);
    }

    @AfterEach
    public void tearDown() {
        memberRepository.deleteAll();
    }

    @Test
    void existingMemberIdShouldReturnTrue() {
        boolean result = memberService.isMemberIdExists(EXISTING_MEMBER_ID);
        assertThat(result).isTrue();
    }

    @Test
    void nonExistingMemberIdShouldReturnFalse() {
        boolean result = memberService.isMemberIdExists(NON_EXISTING_MEMBER_ID);
        assertThat(result).isFalse();
    }

    @Test
    void existingNicknameShouldReturnTrue() {
        boolean result = memberService.isNicknameExists(EXISTING_NICKNAME);
        assertThat(result).isTrue();
    }

    @Test
    void nonExistingNicknameShouldReturnFalse() {
        boolean result = memberService.isNicknameExists(NON_EXISTING_NICKNAME);
        assertThat(result).isFalse();
    }
}