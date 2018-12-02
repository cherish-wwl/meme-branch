package com.meme.core.spring.db;

/**
 * 数据源切换，使用新数据源前调用setContextType可切换数据源
 * @author hzl
 *
 */
public abstract class ExpressContextHolder {
	public final static String SESSION_FACTORY_EXPRESS = "express";
	public final static String SESSION_FACTORY_SCHEMA = "schema";

	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

	public static void setContextType(String contextType) {
		contextHolder.set(contextType);
	}

	public static String getContextType() {
		return contextHolder.get();
	}

	public static void clearContextType() {
		contextHolder.remove();
	}
}
