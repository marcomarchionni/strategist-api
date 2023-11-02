package com.marcomarchionni.ibportfolio.logging;

import com.marcomarchionni.ibportfolio.dtos.update.CombinedUpdateReport;
import com.marcomarchionni.ibportfolio.dtos.update.UpdateReport;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

import static com.marcomarchionni.ibportfolio.logging.LoggingUtils.*;

@Aspect
@Component
public class ServiceLoggingAspect {
    @AfterThrowing(value = "com.marcomarchionni.ibportfolio.logging.Pointcuts.serviceClasses()", throwing = "ex")
    public void logSaveAllException(Exception ex) {
        logWarning("Service throws Exception, message: " + ex.getMessage());
    }

    @AfterReturning(pointcut = "com.marcomarchionni.ibportfolio.logging.Pointcuts.servicesClassesReturningReport()",
            returning =
                    "updateReport")
    public void logUpdateReport(JoinPoint joinPoint, UpdateReport<?> updateReport) {
        String methodName = getClassAndMethodName(joinPoint);
        int addedSize = updateReport.getAdded().size();
        int mergedSize = updateReport.getMerged().size();
        int deletedSize = updateReport.getDeleted().size();
        int skippedSize = updateReport.getSkipped().size();

        // Get entity name from first element of any stream
        Stream<?> combinedStream = Stream.concat(Stream.concat(updateReport.getAdded()
                .stream(), updateReport.getMerged()
                .stream()), Stream.concat(updateReport.getDeleted().stream(), updateReport.getSkipped().stream()));
        String entityName = combinedStream.findFirst().map(Object::getClass).map(Class::getSimpleName)
                .map(String::toLowerCase).orElse(
                        "entity");

        // log line
        logReturn(methodName + " report: Added " + addedSize + " Merged " + mergedSize + " Deleted " +
                deletedSize + " Skipped " + skippedSize + " " + entityName + "(s)");
    }

    @AfterReturning(pointcut = "com.marcomarchionni.ibportfolio.logging.Pointcuts.servicesClassesReturningReport()",
            returning =
                    "report")
    public void logCombinedReport(JoinPoint joinPoint, CombinedUpdateReport report) {
        String methodName = getClassAndMethodName(joinPoint);
        logReturn(methodName + " combined report: " + report.toString());
    }

    @AfterReturning(pointcut = "com.marcomarchionni.ibportfolio.logging.Pointcuts.serviceClassesReturningEntities()",
            returning = "entity")
    public void logServiceUpdateStrategyId(Object entity) {
        logReturn("Service returning: " + entity);
    }


    @AfterReturning(pointcut = "com.marcomarchionni.ibportfolio.logging.Pointcuts.serviceClassesReturningList()",
            returning = "entities")
    public void logServiceFind(List<?> entities) {
        logReturn("Service returning " + getEntitiesNumberAndName(entities));
    }
}
