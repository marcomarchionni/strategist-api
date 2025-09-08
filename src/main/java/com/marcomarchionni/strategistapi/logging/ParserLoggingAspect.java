package com.marcomarchionni.strategistapi.logging;

import static com.marcomarchionni.strategistapi.logging.LoggingUtils.*;

import java.util.List;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ParserLoggingAspect {

  @Pointcut("within(com.marcomarchionni.strategistapi.services.parsers.*)")
  public void responseParser() {}

  @AfterReturning(pointcut = "responseParser()", returning = "entities")
  public void parseEntities(JoinPoint joinPoint, List<?> entities) {
    String className = getSimpleClassName(joinPoint);
    String entitiesNumberAndName = getEntitiesNumberAndName(entities);
    logOk(className + " parsed " + entitiesNumberAndName);
  }
}
