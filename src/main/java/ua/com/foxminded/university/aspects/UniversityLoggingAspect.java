package ua.com.foxminded.university.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class UniversityLoggingAspect {

    public static final String ARG_MESSAGE = "Method input data: ";
    public static final String SEPARATOR = ", ";
    private Logger logger;

    @Pointcut("execution(ua.com.foxminded.university.* *(..))")
    public void allMethods(){}

    @AfterThrowing(pointcut = "allMethods()",
                   throwing = "exception")
    public void afterThrowingExceptionLoggingAdvice(JoinPoint joinPoint,
                                                    Throwable exception) {
        logger = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());
        logger.info(exception.getMessage());
    }

    @Before("allMethods()")
    public void afterAllMethodsLoggingInputArgsAdvice(JoinPoint joinPoint) {
        logger = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());
        Object[] methodArgs = joinPoint.getArgs();

        for (Object o : methodArgs) {
            logger.debug(ARG_MESSAGE + o.toString() + SEPARATOR + o.getClass().getSimpleName());
        }
    }
}
