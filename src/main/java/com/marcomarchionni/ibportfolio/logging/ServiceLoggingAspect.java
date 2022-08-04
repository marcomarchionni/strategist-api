package com.marcomarchionni.ibportfolio.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.marcomarchionni.ibportfolio.logging.LoggingUtils.*;

@Aspect
@Component
public class ServiceLoggingAspect {
    @AfterThrowing(value = "com.marcomarchionni.ibportfolio.logging.Pointcuts.serviceClasses()", throwing = "ex")
    public void logSaveAllException(Exception ex) {
        logWarning("Service throws Exception, message: " + ex.getMessage());
    }

    @AfterReturning("com.marcomarchionni.ibportfolio.logging.Pointcuts.serviceSave()")
    public void logSaveAllEntities(JoinPoint joinPoint){
        List<?> entityList = (List<?>) joinPoint.getArgs()[0];
        logReturn("Saved " + getEntitiesNumberAndName(entityList));
    }

    @AfterReturning(pointcut = "com.marcomarchionni.ibportfolio.logging.Pointcuts.serviceClassesReturningEntities()", returning = "entity")
    public void logServiceUpdateStrategyId(Object entity) {
        logReturn("Service returning: " + entity);
    }



    @AfterReturning(pointcut = "com.marcomarchionni.ibportfolio.logging.Pointcuts.serviceClassesReturningList()", returning = "entities")
    public void logServiceFind(List<?> entities) {
        logReturn("Service returning " + getEntitiesNumberAndName(entities));
    }
}
