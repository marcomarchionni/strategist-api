package com.marcomarchionni.ibportfolio.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class LoggingUtils {

    public static void logClassAndMethodNameHeader(JoinPoint joinPoint) {
        logHeader(getClassAndMethodName(joinPoint));
    }

    private static String getClassAndMethodName(JoinPoint joinPoint) {
        return joinPoint.getSignature().toShortString();
    }

    public static String getEntitiesNumberAndName(List<?> resultEntities) {
        if (resultEntities.size() == 0) {
            return "0 entities";
        } else {
            int entityNumber = resultEntities.size();
            String entityName = resultEntities.get(0).getClass().getSimpleName();
            return entityNumber + " " + entityName + "(s)";
        }
    }

    public static String getParamNamesAndValues(JoinPoint joinPoint) {
        String[] paramNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
        Object[] paramValues = joinPoint.getArgs();
        StringBuilder paramNamesAndValues = new StringBuilder();
        for ( int i = 0; i < paramNames.length; i++) {
            paramNamesAndValues.append(paramNames[i]).append(": ").append(paramValues[i]).append(" ");
        }
        return paramNamesAndValues.toString();
    }

    public static void logException(Exception ex) {
        LoggingUtils.logReturn("Exception thrown, message: " + ex.getMessage());
    }

    public static void logHeader(String string) {
        log.info(">>>> " + string);
    }
    public static void logBody(String string) {
        log.info("---- " + string);
    }
    public static void logReturn(String string) {
        log.info("<<<< " + string);
    }
    public static void logException(String string) {
        log.info("!!!! " + string);
    }
}
