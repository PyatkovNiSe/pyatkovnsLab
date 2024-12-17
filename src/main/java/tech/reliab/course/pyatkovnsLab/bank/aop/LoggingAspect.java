package tech.reliab.course.pyatkovnsLab.bank.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Around("execution(* tech.reliab.course.pyatkovnsLab.bank.service.impl.*.*(..))")
    public Object loggingAroundMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        log.info("Вызов метода: {}", methodName);
        try {
            Object result = joinPoint.proceed();
            log.info("Метод {} выполнен успешно", methodName);
            return result;
        } catch (Exception e) {
            log.error("Ошибка в методе {}: {}", methodName, e.getMessage());
            throw e;
        }
    }
}
