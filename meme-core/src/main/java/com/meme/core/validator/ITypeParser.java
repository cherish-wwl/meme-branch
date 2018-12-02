package com.meme.core.validator;

public interface ITypeParser {

	/**
	 * 根据字段类型名返回类
	 * @param type
	 * @return
	 */
	Class<?> parseType(String type);
}