package com.meme.core.validator.parser;

import java.util.Map;

import com.meme.core.mybatis.EntityColumn;
import com.meme.core.validator.AbstractParser;

public class LongParser extends AbstractParser {

	@Override
	public Class<?> getType() {
		return Long.class;
	}

	@Override
	public Map<String,String> validate(EntityColumn column) {
		if(column.getNullable()){
			this.getRules().put("digits", "true");
			this.getRules().put("numberRange", String.valueOf(column.getPrecision()));
		}else{
			if(null!=column.getDefaultValue()){
				this.getRules().put("digits", "true");
				this.getRules().put("numberRange", String.valueOf(column.getPrecision()));
			}else{
				this.getRules().put("required", "true");
				this.getRules().put("digits", "true");
				this.getRules().put("numberRange", String.valueOf(column.getPrecision()));
			}
		}
		return rules;
	}

}
