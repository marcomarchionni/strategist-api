package com.marcomarchionni.strategistapi.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.List;


@Slf4j
public class LoggingUtils {

    public static String getClassAndMethodName(JoinPoint joinPoint) {
        return joinPoint.getSignature().toShortString();
    }

    public static String getSimpleClassName(JoinPoint joinPoint) {
        return joinPoint.getSignature().getDeclaringType().getSimpleName();
    }

    public static String getEntitiesNumberAndName(List<?> resultEntities) {

        if (resultEntities.isEmpty()) {
            return "0 entities";
        } else {
            int entityNumber = resultEntities.size();
            String entityName = resultEntities.get(0).getClass().getSimpleName();
            return entityNumber + " " + entityName + "(s)";
        }
    }

    public static boolean hasParameters(JoinPoint joinPoint) {
        return ((CodeSignature) joinPoint.getSignature()).getParameterNames().length > 0;
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

    public static String getReturnTypeName(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        if (signature instanceof MethodSignature) {
            return ((MethodSignature) signature).getReturnType().getSimpleName();
        }
        return "";
    }

    public static void logCall(String string) {
        log.info("---> {}", string);
    }
    public static void logReturn(String string) {
        log.info("<--- {}", string);
    }
    public static void logWarning(String string) {
        log.warn("!!!! {}", string);
    }
    public static void logOk(String string) {
        log.info("++++ {}", string);
    }
}

