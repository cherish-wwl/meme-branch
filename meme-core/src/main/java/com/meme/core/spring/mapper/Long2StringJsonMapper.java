package com.meme.core.spring.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;


public class Long2StringJsonMapper extends ObjectMapper {

	private static final long serialVersionUID = 1L;

	public Long2StringJsonMapper() {
		super();
		
		//重写registerModule方法注册模块失效，需要在构造方法内部直接注册模块
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        registerModule(simpleModule);
	}

}