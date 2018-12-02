package com.meme.core.validator.parser;


import java.util.Date;
import java.util.Map;

import com.meme.core.mybatis.EntityColumn;
import com.meme.core.validator.AbstractParser;

public class DateParser extends AbstractParser {

	@Override
	public Class<?> getType() {
		return Date.class;
	}

	@Override
	public Map<String,String> validate(EntityColumn column) {
		if(column.getNullable()){
			this.getRules().put("datetime", "true");
		}else{
			if(null!=column.getDefaultValue()){
				this.getRules().put("datetime", "true");
			}else{
				this.getRules().put("required", "true");
				this.getRules().put("datetime", "true");
			}
		}
		
		return this.getRules();
	}

}
