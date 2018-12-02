package com.meme.core.excel.entity;

import java.io.Serializable;

/**
 * 表节点封装类
 * 
 * @author hzl
 * 
 */
public class FieldNode implements Serializable {

	private static final long serialVersionUID = -8778232338671064382L;

	/** 实体属性名(必填) **/
	private String property;
	/** 数据表字段名 **/
	private String column;
	/** 导入导出的列名(必填) **/
	private String cnname;
	/** 数据类型，java数据类型全名(必填) **/
	private String type;
	/** 导入导出默认值 **/
	private String defaultValue;
	/** 导入导出处理类全名 **/
	private String handleClass;
	/** excel表格列字母序号 **/
	private String excelColumn;
	/** 数据字典编码，方便名称和编码相互转换 **/
	private String dictgroupcode;
	/** 字段值 **/
	private Object value;
	/** 是否导出此列 **/
	private boolean isExport = true;
	/** 是否导入此列 **/
	private boolean isImport = true;

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getCnname() {
		return cnname;
	}

	public void setCnname(String cnname) {
		this.cnname = cnname;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getHandleClass() {
		return handleClass;
	}

	public void setHandleClass(String handleClass) {
		this.handleClass = handleClass;
	}

	public String getExcelColumn() {
		return excelColumn;
	}

	public void setExcelColumn(String excelColumn) {
		this.excelColumn = excelColumn;
	}

	public String getDictgroupcode() {
		return dictgroupcode;
	}

	public void setDictgroupcode(String dictgroupcode) {
		this.dictgroupcode = dictgroupcode;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public boolean isExport() {
		return isExport;
	}

	public void setExport(boolean isExport) {
		this.isExport = isExport;
	}

	public boolean isImport() {
		return isImport;
	}

	public void setImport(boolean isImport) {
		this.isImport = isImport;
	}
}