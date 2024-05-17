package aespa.codeeasy.service;

import aespa.codeeasy.codeCompileUtil.FileExecute;
import aespa.codeeasy.codeCompileUtil.ProjectResult;
import aespa.codeeasy.codeGenerator.CodeGenerator;
import aespa.codeeasy.codeGenerator.CppCodeGenerator;
import aespa.codeeasy.codeGenerator.JavaCodeGenerator;
import aespa.codeeasy.codeGenerator.JavaScriptCodeGenerator;
import aespa.codeeasy.codeGenerator.PythonCodeGenerator;
import aespa.codeeasy.domain.CProblem;
import aespa.codeeasy.domain.TestCase;
import aespa.codeeasy.dto.CompileResponseDto;
import aespa.codeeasy.dto.ProblemDto;
import aespa.codeeasy.repository.CProblemRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CProblemService {

    private final CProblemRepository problemRepository;

    public Optional<ProblemDto> getProblem(Long problemId) {
        Optional<CProblem> optionalProblem = problemRepository.findById(problemId);
        return optionalProblem.map(this::mapToDto);
    }

    private ProblemDto mapToDto(CProblem cProblem) {
        ProblemDto problemDto = new ProblemDto();
        problemDto.setProblemTitle(cProblem.getProblemTitle());
        problemDto.setProblemContent(cProblem.getProblemContent());
        problemDto.setProblemInputContent(cProblem.getProblemInputContent());
        problemDto.setProblemOutputContent(cProblem.getProblemOutputContent());
        problemDto.setAlgorithm(cProblem.getAlgorithm());
        problemDto.setTier(cProblem.getTier());
        problemDto.setTimeLimit(cProblem.getTimeLimit());
        problemDto.setMemoryLimit(cProblem.getMemoryLimit());
        return problemDto;
    }

    public void gradeProblem(Long problemId, String code, String language) {
        //코드 만들기
        String allCode = makeCode(problemId, code, language);

        //시간 제한 가져오기
        Long timeLimit = problemRepository.findTimeLimitById(problemId);

        //테스트 케이스 리스트 가져오기
        List<TestCase> testCases = problemRepository.findTestCasesByProblemId(problemId);
        int testCaseCount = testCases.size();

        //반복문 돌면서 테스트 케이스 실행하기
        for (int index = 0; index < testCaseCount; index++) {
            TestCase testCase = testCases.get(index);
            String rawInputTestCase = testCase.getInputTestCase();
            String[] inputTestCase = rawInputTestCase.split(",");
            String outputTestCase = testCase.getOutputTestCase();


        }
        //실행하면서 결과 저장하기

    }

    public CompileResponseDto runProblem(Long problemId, String code, String language)
            throws IOException, InterruptedException {

        ProjectResult projectResult = setAndRunCode(problemId, code, language);

        String projectStatus = projectResult.getStatus();
        String codeResult = projectResult.getData();

        String basicOutputTestCase = problemRepository.findBasicOutputTestCaseById(problemId).get();

        return getCompileResponseDto(projectStatus, codeResult, basicOutputTestCase);
    }

    private CompileResponseDto getCompileResponseDto(String projectStatus, String codeResult,
                                                     String basicOutputTestCase) {
        List<String> statusList = new ArrayList<>();
        List<String> dataList = new ArrayList<>();

        CompileResponseDto compileResponseDto = new CompileResponseDto(1, 0, statusList, dataList);

        int correctCount = 0;

        if (projectStatus.equals("executed")) {
            statusList.add(projectStatus);
            if (basicOutputTestCase.equals(codeResult)) {
                correctCount++;
                dataList.add("correct answer.");
            } else {
                dataList.add("wrong answer.");
            }

        } else {
            statusList.add(projectStatus);
            dataList.add(codeResult);
        }

        compileResponseDto.setCorrectCount(correctCount);

        return compileResponseDto;
    }

    private ProjectResult setAndRunCode(Long problemId, String code, String language)
            throws IOException, InterruptedException {
        String allCode = makeCode(problemId, code, language);
        // 기본 테스트 코드 넣어서 돌리는 코드 작성해야 함
        String[] basicInputTestCase = getBasicInputTestCase(problemId);
        Long timeLimit = problemRepository.findTimeLimitById(problemId);

        return FileExecute.executeFile(allCode, language, basicInputTestCase, timeLimit);
    }

    private String[] getBasicInputTestCase(Long problemId) {
        String basicInputTestCaseString = problemRepository.findBasicInputTestCaseById(problemId).get();
        String[] basicInputTestCase = basicInputTestCaseString.split(",");
        return basicInputTestCase;
    }

    private CodeGenerator getCodeGenerator(String language) {
        CodeGenerator codeGenerator = null;
        if (language.equals("JAVA")) {
            codeGenerator = new JavaCodeGenerator();
        } else if (language.equals("PYTHON")) {
            codeGenerator = new PythonCodeGenerator();
        } else if (language.equals("CPP")) {
            codeGenerator = new CppCodeGenerator();
        } else if (language.equals("JAVASCRIPT")) {
            codeGenerator = new JavaScriptCodeGenerator();
        }
        return codeGenerator;
    }

    private String makeCode(Long problemId, String code, String language) {
        CodeGenerator codeGenerator = getCodeGenerator(language);

        String[] inputParameters = getInputParameters(problemId);
        String outputParameter = getOutputParameter(problemId);

        String basicCode = codeGenerator.makeBasicCode(inputParameters, outputParameter);

        String allCode = codeGenerator.addCode(code, basicCode);
        return allCode;
    }

    private String[] getInputParameters(Long problemId) {
        String inputParametersOptional = problemRepository.findInputParametersById(problemId).get();
        String[] inputParameters = inputParametersOptional.split(",");
        return inputParameters;
    }

    private String getOutputParameter(Long problemId) {
        return problemRepository.findOutputParameterById(problemId).get();
    }

}
