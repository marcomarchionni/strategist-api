package com.marcomarchionni.strategistapi.logging;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.marcomarchionni.strategistapi.logging.LoggingUtils.getEntitiesNumberAndName;
import static com.marcomarchionni.strategistapi.logging.LoggingUtils.logReturn;

@Aspect
@Component
public class ControllerLoggingAspect {

//    @AfterReturning(pointcut = "com.marcomarchionni.ibportfolio.logging.Pointcuts.controllerUpdateStrategyId()",
//    returning = "entity")
//    public void logControllerUpdateStrategyId(Object entity) {
//        logReturn("Controller Response: " + entity);
//    }

    @AfterReturning(pointcut = "com.marcomarchionni.strategistapi.logging.Pointcuts.controllerClassesReturningObject()"
            , returning = "object")
    public void logControllerReport(Object object) {
        logReturn("Controller Response: " + object);
    }

    @AfterReturning(pointcut = "com.marcomarchionni.strategistapi.logging.Pointcuts.controllerClassesReturningList()"
            , returning = "entities")
    public void logControllerFind(List<?> entities) {
        logReturn("Controller Response: " + getEntitiesNumberAndName(entities));
    }
}
