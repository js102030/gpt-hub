package com.gpt_hub.common.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class PerformanceAndSuccessLoggingAspect {

    @Pointcut("within(com.gpt_hub..*)")
    public void applicationPackagePointcut() {
    }

    @Pointcut("within(com.gpt_hub..*)")
    public void withinTargetPackage() {
    }

    @Around("applicationPackagePointcut()")
    public Object logPerformanceAndSuccess(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result;
        try {
            result = joinPoint.proceed();
            log.info("{} 성공, 실행 시간 : {}ms", joinPoint.getSignature(), System.currentTimeMillis() - startTime);
        } catch (Throwable throwable) {
            log.error("{} 실패, 실행 시간 : {}ms, 에러 메시지: {}", joinPoint.getSignature(),
                    System.currentTimeMillis() - startTime, throwable.getMessage());
            throw throwable;
        }
        return result;
    }

    @Before("withinTargetPackage()")
    public void logMethodStart(JoinPoint joinPoint) {
        log.info("시작 : {}", joinPoint.getSignature().toShortString());
    }

    @After("withinTargetPackage()")
    public void logMethodEnd(JoinPoint joinPoint) {
        log.info("종료 : {}", joinPoint.getSignature().toShortString());
    }
}
