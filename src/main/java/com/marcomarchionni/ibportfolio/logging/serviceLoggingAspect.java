package com.marcomarchionni.ibportfolio.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
@Slf4j
public class serviceLoggingAspect {

    @Pointcut("execution(* com.marcomarchionni.ibportfolio.services.*.*(..))")
    private void allServicesMethods() {}

    @Pointcut("execution(* com.marcomarchionni.ibportfolio.services.*.saveAll(java.util.List))")
    private void saveAll() {}

    @Pointcut("execution(* com.marcomarchionni.ibportfolio.services.*.updateStrategyId(..))")
    private void updateStrategyId() {}

    @Pointcut("execution(java.util.List com.marcomarchionni.ibportfolio.services.*.findWithParameters(..))")
    private void findWithParameters() {}

    @Before("allServicesMethods()")
    public void beforeAllServicesMethods(JoinPoint joinPoint) {
        logClassAndMethodNameHeader(joinPoint);
    }

    @AfterThrowing(value = "allServicesMethods()", throwing = "ex")
    public void afterThrowingSaveAll(Exception ex) {
        logException(ex);
    }

    @AfterReturning("saveAll()")
    public void afterReturningSaveAll(JoinPoint joinPoint){
        List<?> entityList = (List<?>) joinPoint.getArgs()[0];
        logLine("Saved " + getEntitiesNumberAndName(entityList));
    }

    @AfterReturning(pointcut = "updateStrategyId()", returning = "result")
    public void updateStrategyId(JoinPoint joinPoint, Object result) {
        logLine("StrategyId updated on: " + result);
    }

    @AfterReturning(pointcut = "findWithParameters()", returning = "resultEntities")
    public void entityFindWithParameters(JoinPoint joinPoint, List<?> resultEntities) {

        String parameterNamesAndValues = getParamNamesAndValues(joinPoint);
        logLine("Params: " + parameterNamesAndValues);

        String resultEntitiesNumberAndName = getEntitiesNumberAndName(resultEntities);
        logLine("Returning " + resultEntitiesNumberAndName);
    }

    private void logClassAndMethodNameHeader(JoinPoint joinPoint) {
        logHeader(getClassAndMethodName(joinPoint));
    }

    private String getClassAndMethodName(JoinPoint joinPoint) {
        return joinPoint.getSignature().toShortString();
    }

    private String getEntitiesNumberAndName(List<?> resultEntities) {
        if (resultEntities.size() == 0) {
            return "0 entities";
        } else {
            int entityNumber = resultEntities.size();
            String entityName = resultEntities.get(0).getClass().getSimpleName();
            return entityNumber + " " + entityName + "(s)";
        }
    }

    private String getParamNamesAndValues(JoinPoint joinPoint) {
        String[] paramNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
        Object[] paramValues = joinPoint.getArgs();
        StringBuilder paramNamesAndValues = new StringBuilder();
        for ( int i = 0; i < paramNames.length; i++) {
            paramNamesAndValues.append(paramNames[i]).append(": ").append(paramValues[i]).append(" ");
        }
        return paramNamesAndValues.toString();
    }

    private void logException(Exception ex) {
        logLine("Exception thrown, message: " + ex.getMessage());
    }

    private void logHeader(String string) {
        log.info("++++ " + string);
    }
    private void logLine(String string) {
        log.info("---- " + string);
    }


}


