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
public class ParserLoggingAspect {

    @Pointcut("execution(java.util.List com.marcomarchionni.ibportfolio.services.ResponseParser.parse*(*))")
    private void responseParseReturnList() {}

    @Pointcut("execution(com.marcomarchionni.ibportfolio.models.* com.marcomarchionni.ibportfolio.services.ResponseParser.parse*(*))")
    private void responseParseReturnEntity() {}

    @AfterReturning(pointcut = "responseParseReturnList()", returning = "resultList")
    public void parseLogging(List<?> resultList) {
        if (resultList.size() > 0) {
            String entityName = resultList.get(0).getClass().getSimpleName();
            log.info(">>>>AOPLOGGING>>>> Parsed " + resultList.size() + " " + entityName + "(s)");
        }
    }

    @AfterReturning(pointcut = "responseParseReturnEntity()", returning = "result")
    public void parseReturnEntity(Object result) {
        String entityName = result.getClass().getSimpleName();
        log.info(">>>>AOPLOGGING>>>> Parsed " + entityName);
    }
}
