package ru.gelman.user_crud_service.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@Aspect
@Component
public class LoggedAspect {

    @Pointcut("@annotation(ru.gelman.user_crud_service.annotation.Logged)")
    public void annotatedMethods() {
    }

    @Pointcut("@within(ru.gelman.user_crud_service.annotation.Logged)")
    public void annotatedClasses() {
    }

    @Around("annotatedMethods() || annotatedClasses()")
    public Object methodCallLogging(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getSignature().getDeclaringType().getName();
        String methodName = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        String argTypes = Arrays.stream(joinPoint.getArgs()).map(a -> a.getClass().getSimpleName()).collect(Collectors.joining(", "));
        log.info("Calling method {}.{}({}) with args={}", className, methodName, argTypes, args);
        try {
            Object retValue = joinPoint.proceed();
            log.info("Method execution result: {}", retValue);
            return retValue;
        } catch (Throwable ex) {
            log.error("Method execution failed with exception: {}", ex.toString());
            throw ex;
        }
    }
}
