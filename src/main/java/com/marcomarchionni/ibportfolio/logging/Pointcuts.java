package com.marcomarchionni.ibportfolio.logging;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Pointcuts {
    @Pointcut("execution(* com.marcomarchionni.ibportfolio.services.*.*(..))")
    public void allServicesMethods() {}

    @Pointcut("execution(* com.marcomarchionni.ibportfolio.rest.*.*(..))")
    public void allControllersMethods() {}

    @Pointcut("allServicesMethods() || allControllersMethods()")
    public void servicesAndControllersMethods() {}

    @Pointcut("execution(* com.marcomarchionni.ibportfolio.services.*.save*(java.util.List))")
    public void serviceSave() {}

    @Pointcut("execution(* com.marcomarchionni.ibportfolio.services.*.updateStrategyId(..))")
    public void serviceUpdateStrategyId() {}

    @Pointcut("execution(java.util.List com.marcomarchionni.ibportfolio.services.*.find*(..))")
    public void serviceFind() {}

    @Pointcut("execution(java.util.List com.marcomarchionni.ibportfolio.rest.*.find*(..))")
    public void controllerFind() {}

    @Pointcut("execution(* com.marcomarchionni.ibportfolio.rest.*.updateStrategyId(..))")
    public void controllerUpdateStrategyId() {}
}
