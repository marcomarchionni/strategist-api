package com.marcomarchionni.ibportfolio.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(* com.marcomarchionni.ibportfolio.services.*.saveAll(java.util.List)) && args(list)")
    private void serviceSaveAll(List<?> list) {}

    @Pointcut("execution(* com.marcomarchionni.ibportfolio.services.*.updateStrategyId(..))")
    private void serviceUpdateStrategyId() {}

    @AfterReturning(value = "serviceSaveAll(list)", argNames = "list")
    public void simpleLogList(List<?> list){
        if ( list.size() > 0 ) {
            String entityName = list.get(0).getClass().getSimpleName();
            log.info(">>>>AOPLOGGING>>>> SimpleLog - saved " + list.size() + " " + entityName + "(s)");
        }
    }

    @AfterReturning(pointcut = "serviceUpdateStrategyId()", returning = "result")
    public void updateStrategyId(Object result) {
        log.info(">>>>AOPLOGGING>>>> StrategyId updated on: " + result);
    }
}
