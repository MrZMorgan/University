package ua.com.foxminded.university.config.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class UniversityLoggingAspect {

    @AfterThrowing(pointcut = "execution(* *(..))",
                   throwing = "exception")
    public void afterThrowingExceptionLoggingAdvice(JoinPoint joinPoint,
                                                    Throwable exception) {
        Logger logger = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringTypeName());
        logger.info(exception.getMessage());
    }
}
