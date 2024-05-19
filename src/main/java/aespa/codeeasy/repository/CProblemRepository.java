package aespa.codeeasy.repository;

import aespa.codeeasy.domain.BasicCode;
import aespa.codeeasy.domain.CProblem;
import aespa.codeeasy.domain.TestCase;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CProblemRepository extends JpaRepository<CProblem, Long> {
    Optional<CProblem> findById(Long problemId);

    @Query("SELECT c.inputParameters FROM CProblem c WHERE c.id = :problemId")
    Optional<String> findInputParametersById(@Param("problemId") Long problemId);

    @Query("SELECT c.outputParameter FROM CProblem c WHERE c.id = :problemId")
    Optional<String> findOutputParameterById(@Param("problemId") Long problemId);

    @Query("SELECT c.basicInputTestCase FROM CProblem c WHERE c.id = :problemId")
    Optional<String> findBasicInputTestCaseById(@Param("problemId") Long problemId);

    @Query("SELECT c.basicOutputTestCase FROM CProblem c WHERE c.id = :problemId")
    Optional<String> findBasicOutputTestCaseById(@Param("problemId") Long problemId);

    @Query("SELECT c.timeLimit FROM CProblem c WHERE c.id = :problemId")
    Long findTimeLimitById(@Param("problemId") Long problemId);

    @Query("SELECT t FROM TestCase t WHERE t.problem.id = :problemId")
    List<TestCase> findTestCasesByProblemId(@Param("problemId") Long problemId);

    @Query("SELECT bc FROM CProblem cp JOIN cp.basicCode bc WHERE cp.id = :problemId")
    BasicCode findBasicCodeByProblemId(@Param("problemId") Long problemId);
}
