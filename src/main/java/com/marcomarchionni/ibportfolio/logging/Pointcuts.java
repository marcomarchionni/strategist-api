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

    @Pointcut("execution(com.marcomarchionni.ibportfolio.domain.* com.marcomarchionni.ibportfolio.*.*.*(..)))")
    public void returningEntities() {}

    @Pointcut("execution(com.marcomarchionni.ibportfolio.dtos.update.* com.marcomarchionni.ibportfolio.*.*.*(..)))")
    public void returningReport() {
    }

    @Pointcut("controllerClasses() && returningList()")
    public void controllerClassesReturningList() {}

    @Pointcut("controllerClasses() && returningEntities()")
    public void controllerClassesReturningEntities() {}

    @Pointcut("controllerClasses() && returningReport()")
    public void controllerClassesReturningReport() {
    }

    @Pointcut("controllerClasses() && (returningEntities() || returningReport())")
    public void controllerClassesReturningObject() {
    }

    @Pointcut("serviceClasses() && returningList()")
    public void serviceClassesReturningList() {
    }

    @Pointcut("serviceClasses() && returningReport()")
    public void servicesClassesReturningReport() {
    }

    @Pointcut("serviceClasses() && returningEntities()")
    public void serviceClassesReturningEntities() {}

    @Pointcut("execution(* com.marcomarchionni.ibportfolio.services.*.updateStrategyId(..))")
    public void serviceUpdateStrategyId() {}

    @Pointcut("execution(* com.marcomarchionni.ibportfolio.controllers.*.updateStrategyId(..))")
    public void controllerUpdateStrategyId() {}

}
