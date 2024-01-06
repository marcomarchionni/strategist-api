package com.marcomarchionni.ibportfolio.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
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
        String returnTypeName = getReturnTypeName(joinPoint);
        logCall(classAndMethodName + " called with param(s) " + parameterAndValues);
        logOk("Fetching " + returnTypeName + " from Flex Service");
    }

    @AfterReturning("flexServiceClientManagers()")
    public void logMethodReturn(JoinPoint joinPoint) {
        String classAndMethodName = getClassAndMethodName(joinPoint);
        String returnTypeName = getReturnTypeName(joinPoint);
        logReturn(returnTypeName + " returned successfully by " + classAndMethodName);
    }

    @AfterThrowing(value = "flexServiceClientManagers()", throwing = "ex")
    public void logMethodException(JoinPoint joinPoint, Exception ex) {
        String classAndMethodName = getClassAndMethodName(joinPoint);
        String message = ex.getMessage();
        logWarning("Exception thrown by " + classAndMethodName + ". Message: " + message);
    }
}
