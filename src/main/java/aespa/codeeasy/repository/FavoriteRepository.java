package aespa.codeeasy.repository;

import aespa.codeeasy.domain.Favorite;
import aespa.codeeasy.domain.Member;
import aespa.codeeasy.domain.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    Optional<Favorite> findByMemberAndProblem(Member member, Problem problem);

    List<Favorite> findByMember(Member member);
}
