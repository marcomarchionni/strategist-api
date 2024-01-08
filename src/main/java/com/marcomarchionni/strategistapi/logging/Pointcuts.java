package com.marcomarchionni.strategistapi.logging;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Pointcuts {

    @Pointcut("within(com.marcomarchionni.strategistapi.services.*)")
    public void serviceClasses() {}

    @Pointcut("within(com.marcomarchionni.strategistapi.controllers.*)")
    public void controllerClasses() {}

    @Pointcut("serviceClasses() || controllerClasses()")
    public void serviceAndControllerClasses() {}

    @Pointcut("execution(java.util.List com.marcomarchionni.strategistapi.*.*.*(..)))")
    public void returningList() {}

    @Pointcut("execution(com.marcomarchionni.strategistapi.domain.* com.marcomarchionni.strategistapi.*.*.*(..)))")
    public void returningEntities() {}

    @Pointcut("execution(com.marcomarchionni.strategistapi.dtos.update.* com.marcomarchionni.strategistapi.*.*.*(..)))")
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

    @Pointcut("execution(* com.marcomarchionni.strategistapi.services.*.updateStrategyId(..))")
    public void serviceUpdateStrategyId() {}

    @Pointcut("execution(* com.marcomarchionni.strategistapi.controllers.*.updateStrategyId(..))")
    public void controllerUpdateStrategyId() {}

}
