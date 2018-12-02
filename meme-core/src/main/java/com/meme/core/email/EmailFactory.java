package com.meme.core.email;

/**
 * 邮件服务工厂类
 * @author hzl
 *
 */
@Deprecated
public class EmailFactory{

	public Object create(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		Class<?> clazz=Class.forName(className);
		return clazz.newInstance();
	}
}
