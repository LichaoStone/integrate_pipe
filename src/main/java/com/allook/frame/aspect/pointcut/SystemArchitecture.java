package com.allook.frame.aspect.pointcut;

import org.aspectj.lang.annotation.Pointcut;
/**
 * 
 * @作者 栗超
 * @时间 2018年5月25日 上午8:54:57
 * @说明
 */
public class SystemArchitecture {
	
	@Pointcut("execution(* *(..))")
	public void anyMethod(){
	}
	
	@Pointcut("execution(public * *(..))")
	public void anyPublicMethod(){
	}
	
	@Pointcut("execution(* com.allook..*.controller..*(..))")
	public void globalControllerLayer(){
	}
	
	@Pointcut("execution(* com.allook..*.service..*(..))")
	public void globalServiceLayer(){
	}
}
