package com.marcomarchionni.ibportfolio.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.marcomarchionni.ibportfolio.logging.LoggingUtils.*;

@Aspect
@Component
public class ParserLoggingAspect {

    @Pointcut("within(com.marcomarchionni.ibportfolio.services.parsing.*)")
    public void responseParser(){}

    @AfterReturning(pointcut="responseParser()", returning = "entities")
    public void parseEntities(JoinPoint joinPoint, List<?> entities) {
        String className = getSimpleClassName(joinPoint);
        String entitiesNumberAndName = getEntitiesNumberAndName(entities);
        logOk( className + " parsed " + entitiesNumberAndName);
    }

}
