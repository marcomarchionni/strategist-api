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

    @Before("com.marcomarchionni.ibportfolio.logging.Pointcuts.serviceAndControllerClasses()")
    public void beforeControllersAndServicesMethods(JoinPoint joinPoint) {
        logClassAndMethodNameHeader(joinPoint);

        String parameterNamesAndValues = getParamNamesAndValues(joinPoint);
        logBody("Params: " + parameterNamesAndValues);
    }

    @AfterThrowing(value = "com.marcomarchionni.ibportfolio.logging.Pointcuts.serviceClasses()", throwing = "ex")
    public void afterThrowingSaveAll(Exception ex) {
        logException("Service throws Exception, message: " + ex.getMessage());
    }

    @AfterReturning("com.marcomarchionni.ibportfolio.logging.Pointcuts.serviceSave()")
    public void afterReturningSaveAll(JoinPoint joinPoint){
        List<?> entityList = (List<?>) joinPoint.getArgs()[0];
        logReturn("Saved " + getEntitiesNumberAndName(entityList));
    }

    @AfterReturning(pointcut = "com.marcomarchionni.ibportfolio.logging.Pointcuts.serviceClassesReturningEntities()", returning = "result")
    public void afterReturningServiceUpdateStrategyId(Object result) {
        logReturn("Service returning: " + result);
    }

    @AfterReturning(pointcut = "com.marcomarchionni.ibportfolio.logging.Pointcuts.controllerUpdateStrategyId()", returning = "result")
    public void afterReturningControllerUpdateStrategyId(Object result) {
        logReturn("Controller Response: " + result);
    }

    @AfterReturning(pointcut = "com.marcomarchionni.ibportfolio.logging.Pointcuts.serviceClassesReturningList()", returning = "resultEntities")
    public void afterReturningServicesFind(List<?> resultEntities) {
        String resultEntitiesNumberAndName = getEntitiesNumberAndName(resultEntities);
        logReturn("Service returning " + resultEntitiesNumberAndName);
    }

    @AfterReturning(pointcut = "com.marcomarchionni.ibportfolio.logging.Pointcuts.controllerClassesReturningList()", returning = "resultEntities")
    public void afterReturningControllersFind(List<?> resultEntities) {
        String resultEntitiesNumberAndName = getEntitiesNumberAndName(resultEntities);
        logReturn("Controller Response: " + resultEntitiesNumberAndName);
    }
}


