package com.meme.core.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;

import com.meme.core.excel.entity.FieldNode;

/**
 * excel导入导出列字段类型解析
 * @author hzl
 *
 */
public interface IParser {

	Object parse(HSSFCell cell,FieldNode node);
	
	/**
	 * 设置解析器可解析的java类型
	 * @return
	 */
	Class<?> getType();
}