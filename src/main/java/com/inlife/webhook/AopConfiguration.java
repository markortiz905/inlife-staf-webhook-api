package com.inlife.webhook;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Aspect
public class AopConfiguration {

    @Pointcut(
            "execution( * com.inlife.webhook.services.JsonWebhookService.saveClientAsync(..) )"
    )
    public void monitor() { }

    @Bean
    public PerformanceMonitorInterceptor performanceMonitorInterceptor() {
        return new PerformanceMonitorInterceptor(true);
    }

    @Bean
    public Advisor performanceMonitorAdvisor(PerformanceMonitorInterceptor interceptor) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("com.inlife.webhook.AopConfiguration.monitor()");
        return new DefaultPointcutAdvisor(pointcut, interceptor);
    }
}
