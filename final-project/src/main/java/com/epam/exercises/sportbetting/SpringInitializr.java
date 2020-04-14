package com.epam.exercises.sportbetting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class SpringInitializr {

    public static void main(String[] args) {
        SpringApplication.run(SpringInitializr.class, args);

    }
}
