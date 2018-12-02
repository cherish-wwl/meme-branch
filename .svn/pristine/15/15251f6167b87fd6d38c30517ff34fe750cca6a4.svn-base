package com.meme.core.excel.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * 表节点封装类
 * 
 * @author hzl
 * 
 */
public class TableNode implements Serializable {

	private static final long serialVersionUID = 989713809153062601L;

	/** 表格识别编号(必填) **/
	private String id;
	/** 导出表格标题 **/
	private String title;
	/** 导出表名 **/
	private String sheetName;
	/** 导出列宽 **/
	private Float columnWidth;
	/** 导出行高 **/
	private Float rowHeight;
	/** 导入开始行 **/
	private Integer startRow;
	/** 导入结束行 **/
	private Integer endRow;
	/** 导入开始列 **/
	private Short startColumn;
	/** 导入结束列 **/
	private Short endColumn;

	/** 表字段列表，表字段属性名-表字段属性实体键值对 **/
	private LinkedHashMap<String, FieldNode> fields;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public Float getColumnWidth() {
		return columnWidth;
	}

	public void setColumnWidth(Float columnWidth) {
		this.columnWidth = columnWidth;
	}

	public Float getRowHeight() {
		return rowHeight;
	}

	public void setRowHeight(Float rowHeight) {
		this.rowHeight = rowHeight;
	}

	public Integer getStartRow() {
		return startRow;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

	public Integer getEndRow() {
		return endRow;
	}

	public void setEndRow(Integer endRow) {
		this.endRow = endRow;
	}

	public Short getStartColumn() {
		return startColumn;
	}

	public void setStartColumn(Short startColumn) {
		this.startColumn = startColumn;
	}

	public Short getEndColumn() {
		return endColumn;
	}

	public void setEndColumn(Short endColumn) {
		this.endColumn = endColumn;
	}

	public LinkedHashMap<String, FieldNode> getFields() {
		return fields;
	}

	public void setFields(LinkedHashMap<String, FieldNode> fields) {
		this.fields = fields;
	}
}