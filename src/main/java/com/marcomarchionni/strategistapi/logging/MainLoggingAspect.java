package com.marcomarchionni.strategistapi.logging;

import static com.marcomarchionni.strategistapi.logging.LoggingUtils.*;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MainLoggingAspect {

  @Before("com.marcomarchionni.strategistapi.logging.Pointcuts.serviceAndControllerClasses()")
  public void logClassMethodNameAndParameters(JoinPoint joinPoint) {
    if (hasParameters(joinPoint)) {
      logCall(
          getClassAndMethodName(joinPoint)
              + " called with param(s) "
              + getParamNamesAndValues(joinPoint));
    } else {
      logCall(getClassAndMethodName(joinPoint) + " called");
    }
  }
}
