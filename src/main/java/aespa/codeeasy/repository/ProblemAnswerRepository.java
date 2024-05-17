package aespa.codeeasy.repository;

import aespa.codeeasy.domain.ProblemAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProblemAnswerRepository extends JpaRepository<ProblemAnswer, Long> {

    @Query("SELECT e FROM ProblemAnswer e WHERE e.member.id = :id ORDER BY e.date desc")
    List<ProblemAnswer> findRecentProblem(Long id);


    @Query("SELECT COUNT(e) FROM ProblemAnswer e WHERE e.member.id = :id AND e.date = :date")
    int grassInfo(Long id, LocalDate date);

}
