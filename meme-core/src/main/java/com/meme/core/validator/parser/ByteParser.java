package com.meme.core.validator.parser;

import java.util.Map;

import com.meme.core.mybatis.EntityColumn;
import com.meme.core.validator.AbstractParser;

public class ByteParser extends AbstractParser{

	@Override
	public Class<?> getType() {
		return Byte.class;
	}

	@Override
	public Map<String,String> validate(EntityColumn column) {
		return this.getRules();
	}

}
