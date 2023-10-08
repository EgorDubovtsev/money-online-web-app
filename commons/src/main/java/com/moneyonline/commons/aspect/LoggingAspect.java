package com.moneyonline.commons.aspect;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class LoggingAspect {
    private static final Gson OBJECT_MAPPER = new Gson();
    @Around("@annotation(com.moneyonline.commons.annotation.Profiling)")
    public Object restRequestLogger(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.debug("Controller call {} with args: {}", proceedingJoinPoint.getStaticPart().getSignature().getName(), OBJECT_MAPPER.toJson(proceedingJoinPoint.getArgs()));
        Object result = proceedingJoinPoint.proceed();
        log.debug("Result of controller call {} : {}", proceedingJoinPoint.getStaticPart().getSignature().getName(), OBJECT_MAPPER.toJson(result));
        return result;
    }
}
