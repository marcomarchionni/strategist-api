package com.marcomarchionni.ibportfolio.logging;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.marcomarchionni.ibportfolio.logging.LoggingUtils.getEntitiesNumberAndName;
import static com.marcomarchionni.ibportfolio.logging.LoggingUtils.logReturn;

@Aspect
@Component
public class ControllerLoggingAspect {

    @AfterReturning(pointcut = "com.marcomarchionni.ibportfolio.logging.Pointcuts.controllerUpdateStrategyId()", returning = "entity")
    public void logControllerUpdateStrategyId(Object entity) {
        logReturn("Controller Response: " + entity);
    }

    @AfterReturning(pointcut = "com.marcomarchionni.ibportfolio.logging.Pointcuts.controllerClassesReturningList()", returning = "entities")
    public void logControllerFind(List<?> entities) {
        logReturn("Controller Response: " + getEntitiesNumberAndName(entities));
    }
}
