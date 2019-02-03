package com.batook.spring.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class MyAspect {
    static final Logger LOGGER = LoggerFactory.getLogger(MyAspect.class);

    @Pointcut("execution(* com.batook.spring.PersonRepository.findCacheByName(..))")
    public void find() {
    }

    @Before("find()")
    public void logBefore(JoinPoint joinPoint) {
        LOGGER.info("finding ... {}", joinPoint.getArgs());
    }
}
