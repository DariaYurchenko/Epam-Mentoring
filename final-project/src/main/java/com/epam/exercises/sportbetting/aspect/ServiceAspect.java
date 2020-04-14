package com.epam.exercises.sportbetting.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Aspect
@Component
@Slf4j
public class ServiceAspect {

    @Around(value = "execution(* com.epam.exercises.sportbetting.service.*.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        if(joinPoint.getArgs().length != 0) {
            IntStream.rangeClosed(1, joinPoint.getArgs().length)
                    .forEach(i -> log.info("Method's parameter " + i  + ": " + joinPoint.getArgs()[i-1].toString()));
        }

        long start = System.currentTimeMillis();
        Object object = joinPoint.proceed();
        long end = System.currentTimeMillis();

        if(object != null) {
            log.info("Returned value: " + object.toString());
        }

        log.info("Time taken by {} is {} ms.", joinPoint, end-start);

        return object;
    }

}
