package ua.com.foxminded.university.config.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class UniversityLoggingAspect {

    public static final String ARG_MESSAGE = "Check this input to find the error: ";
    public static final String SEPARATOR = ", ";
    private Logger logger;

    @Pointcut("execution(* *(..))")
    public void allAddMethods(){}

    @AfterThrowing(pointcut = "allAddMethods()",
                   throwing = "exception")
    public void afterThrowingExceptionLoggingAdvice(JoinPoint joinPoint,
                                                    Throwable exception) {
        logger = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());
        logger.info(exception.getMessage());
    }

    @Before("allAddMethods()")
    public void afterAllMethodsLoggingInputArgsAdvice(JoinPoint joinPoint) {
        logger = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());
        Object[] methodArgs = joinPoint.getArgs();

        for (Object o : methodArgs) {
            logger.debug(ARG_MESSAGE + o.toString() + SEPARATOR + o.getClass().getSimpleName());
        }
    }
}
