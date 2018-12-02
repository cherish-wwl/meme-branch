package com.meme.core.validator.parser;

import java.util.Map;

import com.meme.core.mybatis.EntityColumn;
import com.meme.core.validator.AbstractParser;

/**
 * 整型数据验证规则解析类
 * @author hzl
 *
 */
public class IntegerParser extends AbstractParser{

	@Override
	public Map<String,String> validate(EntityColumn column) {
		if(column.getNullable()){
			this.getRules().put("integer", "true");
			this.getRules().put("numberRange", String.valueOf(column.getPrecision()));
		}else{
			if(null!=column.getDefaultValue()){
				this.getRules().put("integer", "true");
				this.getRules().put("numberRange", String.valueOf(column.getPrecision()));
			}else{
				this.getRules().put("required", "true");
				this.getRules().put("integer", "true");
				this.getRules().put("numberRange", String.valueOf(column.getPrecision()));
			}
		}
		
		return rules;
	}

	@Override
	public Class<?> getType() {
		return Integer.class;
	}
}
