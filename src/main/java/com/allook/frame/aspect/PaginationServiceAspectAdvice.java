package com.allook.frame.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.allook.frame.aspect.annotation.PaginationService;
import com.allook.frame.page.PageContext;

@Aspect
@Component
public class PaginationServiceAspectAdvice {
	private static final Logger logger = Logger.getLogger(PaginationServiceAspectAdvice.class);
	@Around(value = "com.allook.frame.aspect.pointcut.SystemArchitecture.globalServiceLayer() "
			+ "&& @annotation(paginationService)")
	public Object paginationService(ProceedingJoinPoint jp,PaginationService paginationService) throws Throwable {
		logger.info("--paginationService--");
		PageContext context = PageContext.getContext();
		context.setPaginationService(true);
			
		Object o = null;
		try{
			o = jp.proceed();
		}catch(Throwable t){
			throw t;
		}finally{
			context.setPaginationService(false);
		}
		return o;
	}
}
