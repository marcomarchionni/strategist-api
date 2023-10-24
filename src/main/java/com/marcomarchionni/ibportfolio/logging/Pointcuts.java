package com.marcomarchionni.ibportfolio.logging;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Pointcuts {

    @Pointcut("within(com.marcomarchionni.ibportfolio.services.*)")
    public void serviceClasses() {}

    @Pointcut("within(com.marcomarchionni.ibportfolio.controllers.*)")
    public void controllerClasses() {}

    @Pointcut("serviceClasses() || controllerClasses()")
    public void serviceAndControllerClasses() {}

    @Pointcut("execution(java.util.List com.marcomarchionni.ibportfolio.*.*.*(..)))")
    public void returningList() {}

    @Pointcut("execution(com.marcomarchionni.ibportfolio.model.domain.* com.marcomarchionni.ibportfolio.*.*.*(..)))")
    public void returningEntities() {}

    @Pointcut("controllerClasses() && returningList()")
    public void controllerClassesReturningList() {}

    @Pointcut("serviceClasses() && returningList()")
    public void serviceClassesReturningList() {}

    @Pointcut("controllerClasses() && returningEntities()")
    public void controllerClassesReturningEntities() {}

    @Pointcut("serviceClasses() && returningEntities()")
    public void serviceClassesReturningEntities() {}

    @Pointcut("execution(* com.marcomarchionni.ibportfolio.services.*.save*(java.util.List))")
    public void serviceSave() {}

    @Pointcut("execution(* com.marcomarchionni.ibportfolio.services.*.updateStrategyId(..))")
    public void serviceUpdateStrategyId() {}

    @Pointcut("execution(* com.marcomarchionni.ibportfolio.controllers.*.updateStrategyId(..))")
    public void controllerUpdateStrategyId() {}
}
