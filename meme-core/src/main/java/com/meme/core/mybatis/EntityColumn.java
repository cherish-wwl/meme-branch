package com.meme.core.mybatis;

import org.apache.ibatis.type.JdbcType;

import com.alibaba.fastjson.JSONObject;

public class EntityColumn {
	/** 字段名 **/
	private String column;
	/** 字段名对应实体类属性 **/
	private String property;
	/** java类型 **/
	private Class<?> javaType;
	/** 字段类型 **/
	private JdbcType jdbcType;
	/** 是否为null **/
	private Boolean nullable;
	/** 默认值 **/
	private Object defaultValue;
	/** 是否自增 **/
	private Boolean isAutoIncrement = false;
	/** 字段长度 **/
	private int columnLength;
	/** 是否为主键 **/
	private Boolean isPrimaryKey=false;

	private int precision;
	/** 精确到小数点后几位，字符串类型时此值为0 **/
	private int scale;

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Class<?> getJavaType() {
		return javaType;
	}

	public void setJavaType(Class<?> javaType) {
		this.javaType = javaType;
	}

	public JdbcType getJdbcType() {
		return jdbcType;
	}

	public void setJdbcType(JdbcType jdbcType) {
		this.jdbcType = jdbcType;
	}

	public Boolean getNullable() {
		return nullable;
	}

	public void setNullable(Boolean nullable) {
		this.nullable = nullable;
	}

	public Object getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Boolean getIsAutoIncrement() {
		return isAutoIncrement;
	}

	public void setIsAutoIncrement(Boolean isAutoIncrement) {
		this.isAutoIncrement = isAutoIncrement;
	}

	public int getColumnLength() {
		return columnLength;
	}

	public void setColumnLength(int columnLength) {
		this.columnLength = columnLength;
	}

	public Boolean getIsPrimaryKey() {
		return isPrimaryKey;
	}

	public void setIsPrimaryKey(Boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	public int getPrecision() {
		return precision;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

}
