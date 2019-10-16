package com.hce.core.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hce.core.db.DataSourceHolder;

@Aspect
@Order(11)
@Component
public class ReadOnlyAop {
	@Pointcut("@annotation(com.hce.global.annotation.ReadOnly)")
    public void pointCut() {}

	@Around("pointCut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
		Transactional transactionalAnnotation = AopHelper.getAnnotation(joinPoint, Transactional.class);
		if(transactionalAnnotation==null) {
			boolean stackRoot = false;
			try {
				if(DataSourceHolder.getDetermineCurrentLookupKey()==null) {
					stackRoot = true;
					DataSourceHolder.setSlave();
				}
				return joinPoint.proceed();
			} finally {
				if(stackRoot)
					DataSourceHolder.remove();
			}
		} else {
			return joinPoint.proceed();
		}
	}
}