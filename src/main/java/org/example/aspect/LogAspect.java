package org.example.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.example.controller.AdminController;
import org.example.translator.TranslationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(AdminController.class);
    private final TranslationService translationService;

    public LogAspect(TranslationService translationService) {
        this.translationService = translationService;
    }

    @Before("execution(* org.example.feignClient.*.*(..))")
    public void feignLogBefore(JoinPoint jp){
        logger.info(String.format(translationService.getTranslation(
                "Invocation of feign client method: %s"), jp.getSignature()));
    }

    @After("execution(* org.example.feignClient.*.*(..))")
    public void feignLogAfter(JoinPoint jp){
        logger.info(String.format(translationService.getTranslation(
                "Execution of feign client method: %s passed successfully"), jp.getSignature()));
    }


    @Before("execution(* org.example.service.*.*(..))")
    public void logBefore(JoinPoint jp){
        logger.info(String.format(translationService.getTranslation(
                "Invocation of method: %s"), jp.getSignature()));
    }

    @After("execution(* org.example.service.*.*(..))")
    public void logAfter(JoinPoint jp){
        logger.info(String.format(translationService.getTranslation(
                "Execution of method: %s passed successfully"), jp.getSignature()));
    }
}

