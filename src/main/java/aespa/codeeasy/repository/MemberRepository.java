package aespa.codeeasy.repository;

import aespa.codeeasy.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByNickname(String nickname);
    Optional<Member> findByMemberId(String memberId);
    Optional<Member> findByEmail(String email);
}
