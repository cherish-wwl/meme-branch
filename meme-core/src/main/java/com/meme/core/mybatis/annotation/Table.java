package com.meme.core.mybatis.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 注解实体类表名，方便provider获取实体映射的数据表
 * @author hzl
 *
 */
@Target(TYPE) 
@Retention(RUNTIME)
public @interface Table {

	/**表名**/
	String name();
	
	/**主键**/
	String id() default "";
}
