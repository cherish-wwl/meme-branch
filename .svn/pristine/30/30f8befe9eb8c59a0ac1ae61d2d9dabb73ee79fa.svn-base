package com.meme.core.util;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * property文件读取工具类
 * @author hzl
 *
 */
public class PropertiesUtil {

	public static final String PROP_CONSTANTS_FILE = "constants.properties";

	/**
	 * 根据提供的配置文件读取属性
	 * @param propName
	 * @param propFile
	 * @return
	 */
	public static Object getProperty(String propName, String propFile) {
		Resource resource = new ClassPathResource(propFile);
		Properties props=null;
		try{
			props = PropertiesLoaderUtils.loadProperties(resource);
		}catch (Exception e) {
			return null;
		}
		return props.get(propName);
	}
	
	/**
	 * 默认读取constants.properties文件中的属性值
	 * @param propName
	 * @return
	 */
	public static String getProperty(String propName){
		Resource resource = new ClassPathResource(PROP_CONSTANTS_FILE);
		Properties props=null;
		try{
			props = PropertiesLoaderUtils.loadProperties(resource);
		}catch (Exception e) {
			return null;
		}
		return (String)props.get(propName);
	}

	public static void main(String[] args) throws IOException {
		Double d=Double.valueOf(PropertiesUtil.getProperty("accountprice"));
		if(d!=null)System.out.println(d*Integer.valueOf(333));
	}
}
