package com.marcomarchionni.ibportfolio.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.marcomarchionni.ibportfolio.logging.LoggingUtils.*;

@Aspect
@Component
public class MainLoggingAspect {

    @Before("com.marcomarchionni.ibportfolio.logging.Pointcuts.servicesAndControllersMethods()")
    public void beforeControllersAndServicesMethods(JoinPoint joinPoint) {
        logClassAndMethodNameHeader(joinPoint);

        String parameterNamesAndValues = getParamNamesAndValues(joinPoint);
        logBody("Params: " + parameterNamesAndValues);
    }

    @AfterThrowing(value = "com.marcomarchionni.ibportfolio.logging.Pointcuts.allServicesMethods()", throwing = "ex")
    public void afterThrowingSaveAll(Exception ex) {
        logException("Service throws Exception, message: " + ex.getMessage());
    }

    @AfterReturning("com.marcomarchionni.ibportfolio.logging.Pointcuts.serviceSave()")
    public void afterReturningSaveAll(JoinPoint joinPoint){
        List<?> entityList = (List<?>) joinPoint.getArgs()[0];
        logReturn("Saved " + getEntitiesNumberAndName(entityList));
    }

    @AfterReturning(pointcut = "com.marcomarchionni.ibportfolio.logging.Pointcuts.serviceUpdateStrategyId()", returning = "result")
    public void afterReturningServiceUpdateStrategyId(JoinPoint joinPoint, Object result) {
        logReturn("Service updated StrategyId on: " + result);
    }

    @AfterReturning(pointcut = "com.marcomarchionni.ibportfolio.logging.Pointcuts.controllerUpdateStrategyId()", returning = "result")
    public void afterReturningControllerUpdateStrategyId(JoinPoint joinPoint, Object result) {
        logReturn("API Response: " + result);
    }

    @AfterReturning(pointcut = "com.marcomarchionni.ibportfolio.logging.Pointcuts.serviceFind()", returning = "resultEntities")
    public void afterReturningServicesFind(List<?> resultEntities) {
        String resultEntitiesNumberAndName = getEntitiesNumberAndName(resultEntities);
        logReturn("Service returning " + resultEntitiesNumberAndName);
    }

    @AfterReturning(pointcut = "com.marcomarchionni.ibportfolio.logging.Pointcuts.controllerFind()", returning = "resultEntities")
    public void afterReturningControllersFind(List<?> resultEntities) {

        String resultEntitiesNumberAndName = getEntitiesNumberAndName(resultEntities);
        logReturn("API Response: " + resultEntitiesNumberAndName);
    }
}


