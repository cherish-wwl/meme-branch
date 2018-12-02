package com.meme.core.spring.converter;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;

/**
 * 自定义json转换器，所有数据添加双引号，解决json返回页面，js解析长整型数据精度丢失问题
 * @author hzl
 *
 */
public class JsonConverter extends MappingJackson2HttpMessageConverter{

	@Override
	protected void writeInternal(Object object, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
		JsonEncoding encoding = getJsonEncoding(outputMessage.getHeaders().getContentType());
		JsonGenerator generator = this.objectMapper.getFactory().createGenerator(outputMessage.getBody(), encoding);
		try {
	        //允许单引号
	        this.objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
	        //字段和值都加引号
	        this.objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
	        //数字也加引号
	        this.objectMapper.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true);
	        this.objectMapper.configure(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS, true);
	        this.objectMapper.writeValue(generator, object);
	    }catch (JsonGenerationException ex) {
	        throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(), ex);
	    }
	}
}
