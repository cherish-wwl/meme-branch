package com.meme.core.log;

import java.lang.reflect.Method;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.meme.core.log.annotation.SysLog;
import com.meme.core.service.LogService;
import com.meme.core.util.StringUtil;

/**
 * 日志切面类
 * @author hzl
 *
 */
@Aspect
@Component
public class LogAspectj {

	@Resource
	private LogService logService;

	/**
	 * 日志记录切入点
	 */
	@Pointcut("@annotation(com.meme.core.log.annotation.SysLog)")
	public void log() {
	}

	/**
	 * 切入点方法执行前记录操作日志
	 * @param joinPoint
	 * @throws Exception 
	 */
	@Before("log()")
	public void before(JoinPoint joinPoint) throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
	    Method method = methodSignature.getMethod();
	    SysLog annotaionLog = method.getAnnotation(SysLog.class);
	    
	    String event=annotaionLog.event();
	    String type=null;
	    if(annotaionLog.type()!=null&&annotaionLog.type().length!=0){
	    	type=annotaionLog.type()[0].getType();
	    }
	    String description=null;
	    if(StringUtil.isAllNotEmpty(annotaionLog.description())) description=annotaionLog.description();
	    
		this.logService.log(request, event, type, description);
	}
}
