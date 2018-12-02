package com.meme.core.validator;

import java.util.Map;

import com.meme.core.mybatis.EntityColumn;

/**
 * 验证规则解析接口，定义解析策略
 * @author hzl
 *
 */
public interface IParser{

	/**
	 * 判断是否允许当前解析器生成验证规则
	 * @param clazz
	 * @param column
	 * @return
	 */
	boolean accept(Class<?> clazz, EntityColumn column);
	
	/**
	 * 生成验证规则
	 * @param column
	 * @return
	 */
	Map<String,String> parse(EntityColumn column);
	
	/**
	 * 设置当前解析器可解析的java类型
	 * @return
	 */
	Class<?> getType();
}