package aespa.codeeasy.util;

import aespa.codeeasy.exception.FileDeleteException;
import aespa.codeeasy.exception.FolderDeleteException;
import aespa.codeeasy.model.ProblemAnswer;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class JavaCompilerUtil {
    private static final String BASE_PATH = "C:/Users/ADMIN/Desktop/";
    private static final String CLASS_NAME = "Solution";
    private static final String METHOD_NAME = "solution";

    public Object compile(ProblemAnswer problemAnswer) throws FolderDeleteException, FileDeleteException {
        // UUID를 사용하여 고유한 경로 생성
        String uuid = UUID.randomUUID().toString();
        String uuidPath = BASE_PATH + uuid + "/";

        // 폴더 및 파일 생성
        File newFolder = new File(uuidPath);
        File sourceFile = new File(uuidPath + CLASS_NAME + ".java");
        File classFile = new File(uuidPath + CLASS_NAME + ".class");

        ByteArrayOutputStream byteArrayOutputStreamError = new ByteArrayOutputStream();
        PrintStream printStreamErr = System.err;

        try {
            // 새로운 폴더 생성
            boolean isMadeNewFolder = newFolder.mkdir();

            // 에디터에서 가져온 코드를 소스 파일에 작성
            FileWriter writer = new FileWriter(sourceFile);
            writer.append(problemAnswer.getCode());
            writer.close();

            System.setErr(new PrintStream(byteArrayOutputStreamError));

            // 소스 파일 컴파일
            JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
            int compileResult = javaCompiler.run(null, null, null, sourceFile.getPath());

            // 컴파일 중 에러가 발생했을 경우
            if (compileResult != 0) {
                return byteArrayOutputStreamError.toString();
            }

            // 컴파일된 클래스 로드
            URLClassLoader urlClassLoader = URLClassLoader.newInstance(new URL[]{new File(uuidPath).toURI().toURL()});
            Class<?> dynamicClass = Class.forName(CLASS_NAME, true, urlClassLoader);

            // 클래스 인스턴스화 및 반환
            return dynamicClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            System.setErr(printStreamErr);

            // 임시 파일 및 폴더 삭제
            deleteFile(sourceFile);
            deleteFile(classFile);
            deleteFolder(newFolder);
        }
    }

    private void deleteFile(File file) throws FileDeleteException {
        if (file.exists() && !file.delete()) {
            throw new FileDeleteException("파일 삭제를 실패했습니다: " + file.getAbsolutePath());
        }
    }

    private void deleteFolder(File folder) throws FolderDeleteException {
        if (folder.exists() && !folder.delete()) {
            throw new FolderDeleteException("폴더 삭제를 실패했습니다: " + folder.getAbsolutePath());
        }
    }

    public Map<String, Object> run(Object object, Object[] params, String output) {
        Map<String, Object> returnMap = new HashMap<>();

        // 매개변수의 클래스 배열 추출
        Class<?>[] arguments = extractParameterClasses(params);
        for (int i = 0; i < params.length; i++) {
            if (params[i] instanceof Integer) {
                arguments[i] = int.class;
            } else if (params[i] instanceof Long) {
                arguments[i] = long.class;
            } else if (params[i] instanceof Float) {
                arguments[i] = float.class;
            } else if (params[i] instanceof Double) {
                arguments[i] = double.class;
            } else if (params[i] instanceof Boolean) {
                arguments[i] = boolean.class;
            } else {
                arguments[i] = params[i].getClass();
            }
        }

        // 표준 출력 및 오류 스트림 임시 저장
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ByteArrayOutputStream byteArrayOutputStreamError = new ByteArrayOutputStream();

        PrintStream printStreamOut = System.out;
        PrintStream printStreamErr = System.err;

        try {
            // 표준 출력 및 오류 스트림을 임시로 변경
            System.setOut(new PrintStream(byteArrayOutputStream));
            System.setErr(new PrintStream(byteArrayOutputStreamError));

            // Java 메서드 실행
            Map<String, Object> map = MethodExecutionUtil.timeOutCall(object, METHOD_NAME, params, arguments);

            if (((String) map.get("status")).equals("SUCCESS")) {
                returnMap.put("status", map.get("status"));
                returnMap.put("return", map.get("return"));

                String outputStreamError = byteArrayOutputStreamError.toString();
                if (StringUtils.hasText(outputStreamError)) {
                    returnMap.put("message", outputStreamError);
                } else {
                    String outputStream = byteArrayOutputStream.toString();
                    returnMap.put("message", outputStream);
                }
            } else {
                returnMap.put("status", map.get("status"));

                String outputStreamError = byteArrayOutputStreamError.toString();
                if (StringUtils.hasText(outputStreamError)) {
                    returnMap.put("message", outputStreamError);
                } else {
                    returnMap.put("message", "TIME_OUT");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.setOut(printStreamOut);
            System.setErr(printStreamErr);
        }

        return returnMap;
    }

    private Class<?>[] extractParameterClasses(Object[] params) {
        Class<?>[] arguments = new Class[params.length];
        for (int i = 0; i < params.length; i++) {
            arguments[i] = params[i].getClass();
        }
        return arguments;
    }
}