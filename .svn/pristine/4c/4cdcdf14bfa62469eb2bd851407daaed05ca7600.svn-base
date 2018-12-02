package com.meme.im.aspectj;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TmpMemberRestrict {

	@Pointcut("@annotation(com.meme.im.aspectj.annotation.TmpMemberFuncRestrict)")
	public void restrict() {
	}
	
	@AfterReturning(value="restrict()",argNames = "retVal",returning = "retVal")
	public void after_orderTimeLine(JoinPoint joinPoint, Object retVal){
		System.out.println("====after=="+retVal.toString());
	}
	
	@Before("restrict()")
	public void before(JoinPoint joinPoint) throws Exception {
		System.out.println("===before===");
	}
	
}
