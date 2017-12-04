package com.fable.insightview.platform.aaServiceForKscc.util;

import com.fable.insightview.platform.common.util.MultipleDataSource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MultipleDataSourceAspectAdvice {

    @Around("execution(* com.fable.insightview.*.*.mapper.KsccSchemeMapper.*(..))")
    public Object doAround(ProceedingJoinPoint jp) throws Throwable {
        MultipleDataSource.setDataSourceKey("mySqlDataSource2");
        return jp.proceed();
    }
}