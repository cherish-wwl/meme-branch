package com.meme.core.validator.parser;

import java.util.Map;

import com.meme.core.mybatis.EntityColumn;
import com.meme.core.validator.AbstractParser;

/**
 * 字符串验证规则解析类
 * @author hzl
 *
 */
public class StringParser extends AbstractParser{

	@Override
	public Map<String,String> validate(EntityColumn column) {
		if(column.getNullable()){
			this.getRules().put("byteRangeLength", "[0,"+column.getPrecision()+"]");
		}else{
			if(null!=column.getDefaultValue()){
				this.getRules().put("byteRangeLength", "[0,"+column.getPrecision()+"]");
			}else{
				this.getRules().put("required", "true");
				this.getRules().put("byteRangeLength", "[1,"+column.getPrecision()+"]");
			}
		}
		return this.getRules();
	}

	@Override
	public Class<?> getType() {
		return String.class;
	}
}