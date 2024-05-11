package aespa.codeeasy.service;

import aespa.codeeasy.domain.DefaultCode;
import aespa.codeeasy.repository.DefaultCodeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DefaultCodeService {

    private final DefaultCodeRepository defaultCodeRepository;

    public Optional<DefaultCode> getDefaultCodeByProblemIdAndLanguage(Long problemId, String language) {
        return defaultCodeRepository.findByProblemIdAndLanguage(problemId, language);
    }
}
