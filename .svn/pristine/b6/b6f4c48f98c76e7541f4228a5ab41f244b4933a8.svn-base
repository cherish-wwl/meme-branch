package com.meme.core.mybatis;

import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * SelectKeyInject注解切面类，无效
 * @author hzl
 *
 */
@Deprecated
@Aspect
@Component
public class SelectKeyInjectAspectj {

	@Pointcut("@annotation(com.meme.core.mybatis.annotation.SelectKeyInject)")
	public void inject() {
	}
	
	@SuppressWarnings("unchecked")
	@Before("inject()")
	public void before(JoinPoint joinPoint) throws ClassNotFoundException {
		for(int i=0;i<joinPoint.getArgs().length;i++){
			if(joinPoint.getArgs()[i] instanceof Map){
				//为避免报Parameter 'id' not found异常，此处添加空值的id参数，也可在基类BaseMapper添加@SelectKey注解避免报此异常
				((Map<String,Object>)joinPoint.getArgs()[i]).put("id", null);
			}
		}
	}
}
