package aespa.codeeasy.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class MethodExecutionUtil {
    private final static long TIMEOUT_LONG = 15000;

    public static Map<String, Object> timeOutCall(Object object, String methodName, Object[] params, Class<?>[] arguments) throws Exception {
        Map<String, Object> returnMap = new HashMap<>();

        // TODO arguments가 N개가 있을 때 아래 방식이 아닌 유연한 방식 개발 필요
        Method objectMethod;
        if (arguments.length == 1) {
            objectMethod = object.getClass().getMethod(methodName, arguments[0]);
        } else if (arguments.length == 2) {
            objectMethod = object.getClass().getMethod(methodName, arguments[0], arguments[1]);
        } else {
            objectMethod = object.getClass().getMethod(methodName);
        }

        Callable<Map<String, Object>> task = () -> {
            // 아래 주석 해제시 timeout 테스트 가능
            // Thread.sleep(4000);

            // TODO params가 N개가 있을 때 아래 방식이 아닌 유연한 방식 개발 필요
            Map<String, Object> callableMap = new HashMap<>();
            if (params.length == 1) {
                callableMap.put("return", objectMethod.invoke(object, params));
            } else if (params.length == 2) {
                callableMap.put("return", objectMethod.invoke(object, params[0], params[1]));
            } else {
                callableMap.put("return", objectMethod.invoke(object));
            }

            callableMap.put("status", "SUCCESS");
            return callableMap;
        };

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Map<String, Object>> future = executorService.submit(task);

        try {
            returnMap = future.get(TIMEOUT_LONG, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            returnMap.put("status", "FAIL");
        } finally {
            executorService.shutdown();
        }

        return returnMap;
    }
}