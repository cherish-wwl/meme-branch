package com.meme.core.spring;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;

import org.springframework.util.StringUtils;


/**
 * java.sql.Timestamp类的属性转换控制器
 * @author hzl
 *
 */
public class TimestampEditor extends PropertyEditorSupport {

	private DateFormat format;

	private Boolean isEmpty;

	private Integer length;
	
	public TimestampEditor(DateFormat format, Boolean isEmpty) {
		this.format = format;
		this.isEmpty = isEmpty;
		this.length = -1;
	}
	
	public TimestampEditor(DateFormat format, Boolean isEmpty, Integer length) {
		this.format = format;
		this.isEmpty = isEmpty;
		this.length = length;
	}
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (this.isEmpty && !StringUtils.hasText(text)) {
			setValue(null);
		}else if (null!=text && this.length >= 0 && text.length() != this.length) {
			throw new IllegalArgumentException("日期字符串转换失败: 字符串长度不足" + this.length);
		}
		else {
			try {
				setValue(new Timestamp(this.format.parse(text).getTime()));
			}catch (ParseException ex) {
				throw new IllegalArgumentException("日期转换失败: " + ex.getMessage(), ex);
			}
		}
	}
	
	@Override
	public String getAsText() {
		Timestamp value = (Timestamp) getValue();
		return (value != null ? this.format.format(value) : "");
	}
	
}
