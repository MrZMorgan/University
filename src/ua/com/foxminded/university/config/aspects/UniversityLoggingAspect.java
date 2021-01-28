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

    public static final String ARG_MESSAGE = "Check this input to find the error: ";

    @AfterThrowing(pointcut = "execution(* *(..))",
                   throwing = "exception")
    public void afterThrowingExceptionLoggingAdvice(JoinPoint joinPoint,
                                                    Throwable exception) {
        Logger logger = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());
        logger.info(exception.getMessage());

        Object[] methodArgs = joinPoint.getArgs();

        for (Object o : methodArgs) {
            logger.debug(ARG_MESSAGE + o.toString());
        }
    }
}
