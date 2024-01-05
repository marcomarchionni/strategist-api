package com.marcomarchionni.ibportfolio.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import static com.marcomarchionni.ibportfolio.logging.LoggingUtils.*;

@Aspect
@Component
public class FlexServiceClientManagerLoggingAspect {

    @Pointcut("execution(* com.marcomarchionni.ibportfolio.services.fetchers.flexserviceclientmanagers.*.*(..))")
    public void flexServiceClientManagers() {
    }

    @Before("flexServiceClientManagers()")
    public void logMethodCall(JoinPoint joinPoint) {
        String classAndMethodName = getClassAndMethodName(joinPoint);
        String parameterAndValues = getParamNamesAndValues(joinPoint);
        logCall(classAndMethodName + " called with param(s) " + parameterAndValues);
    }
}
