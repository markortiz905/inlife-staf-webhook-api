package com.inlife.webhook.aspect;

import com.inlife.webhook.dto.StaffRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Aspect
@Component
public class JsonWebhookServiceAspect {

    @Before(value = "execution(* com.inlife.webhook.services.JsonWebhookService.saveClientAsync(..)) " +
            "and args(request)")
    public void saveClientAsyncBefore(JoinPoint joinPoint, StaffRequestDto request) {
        log.info("saveClientAsync service started  - item_id: " + request.getItem_id());
    }

    @AfterReturning(
            pointcut="execution(* com.inlife.webhook.services.JsonWebhookService.saveClientAsync(..))",
            returning="computableObject")
    public void saveClientAsyncAfter(JoinPoint joinPoint, CompletableFuture<Void> computableObject) {
        log.info("saveClientAsync service ended - " + joinPoint.getSignature());
    }
}