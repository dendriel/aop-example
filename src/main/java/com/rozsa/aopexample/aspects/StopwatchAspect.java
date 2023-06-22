package com.rozsa.aopexample.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class StopwatchAspect {
    private static final Logger logger = LoggerFactory.getLogger(StopwatchAspect.class);

    /** PointCut + PointCut Expression */
    @Pointcut(value="@annotation(com.rozsa.aopexample.annotations.Stopwatch)")
    public void stopwatchAnnotation() {}

    /** Advice */
    @Around("stopwatchAnnotation()")
    public Object beginCollecting(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        StopWatch sw = new StopWatch();
        sw.start();

        try {
            /** Target */
            return proceedingJoinPoint.proceed();
        }
        finally {
            sw.stop();
            logger.info("Call to method took {} ms", sw.getTotalTimeMillis());
        }
    }
}
