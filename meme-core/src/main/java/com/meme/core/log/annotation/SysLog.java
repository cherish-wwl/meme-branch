package com.meme.core.log.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.meme.core.log.LogType;

/**
 * 日志注解类
 * @author hzl
 *
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

	/** 日志操作类型 **/
	LogType[] type();

	/** 日志操作事件 **/
	String event();

	/** 日志操作描述 **/
	String description() default "";
}
