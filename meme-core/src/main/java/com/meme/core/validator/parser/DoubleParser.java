package com.meme.core.validator.parser;

import java.util.Map;

import com.meme.core.mybatis.EntityColumn;
import com.meme.core.validator.AbstractParser;

public class DoubleParser extends AbstractParser {

	@Override
	public Class<?> getType() {
		return Double.class;
	}

	@Override
	public Map<String,String> validate(EntityColumn column) {
		int intNum=column.getPrecision()-column.getScale();
		int decNum=column.getScale();
		if(column.getNullable()){
			this.getRules().put("number", "true");
			this.getRules().put("float", "["+intNum+","+decNum+"]");
		}else{
			if(null!=column.getDefaultValue()){
				this.getRules().put("number", "true");
				this.getRules().put("float", "["+intNum+","+decNum+"]");
			}else{
				this.getRules().put("required", "true");
				this.getRules().put("number", "true");
				this.getRules().put("float", "["+intNum+","+decNum+"]");
			}
		}
		
		return this.getRules();
	}

}
