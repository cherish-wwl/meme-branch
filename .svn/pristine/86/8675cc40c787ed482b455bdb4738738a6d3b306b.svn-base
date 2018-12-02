package com.meme.pay.wechat.util;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import com.meme.core.util.MD5Util;
import com.meme.core.util.StringUtil;

/**
 * 微信支付签名工具
 * @author hzl
 *
 */
public class SignTool {

	public static final String RAN = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	public static final int RAND_LEN = 32;
	
	public static void main(String[] args){
		System.out.println(getRandomString());
	}
	
	public static String getTimeStamp(){
		String time=String.valueOf(System.currentTimeMillis());
		return time.substring(0,time.length()-3);
	}

	/**
	 * 默认取32位随机字符串
	 * 
	 * @return
	 */
	public static String getRandomString() {
		StringBuffer randStr = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < RAND_LEN; i++) {
			int n = random.nextInt(RAND_LEN);
			randStr.append(RAN.charAt(n));
		}
		return randStr.toString();
	}
	
	/**
	 * 取随机字符串
	 * @param len 随机字符串长度
	 * @return
	 */
	public static String getRandomString(int len){
		StringBuffer randStr = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < len; i++) {
			int n = random.nextInt(len);
			randStr.append(RAN.charAt(n));
		}
		return randStr.toString();
	}
	
	/**
	 * 生成签名字符串
	 * @param params 请求参数
	 * @param key 微信支付商户密钥
	 * @return
	 */
	public static String generateCode(Map<String,String> params, String key) {
		StringBuffer code = new StringBuffer();
		
		String[] keys = new String[params.keySet().size()];
		params.keySet().toArray(keys);
		// 参数名字典排序
		Arrays.sort(keys);
		for (int i = 0; i < keys.length; i++) {
			if (i == 0) code.append(keys[i]).append("=").append(params.get(keys[i]));
			else code.append("&").append(keys[i]).append("=").append(params.get(keys[i]));
		}
		if (StringUtil.isAllNotEmpty(keys)) code.append("&key=").append(key);
		//有中文参数需指定MD5字符串编码
		return MD5Util.encodeByMD5(code.toString(),"UTF-8");
	}

	/**
	 * 生成签名字符串
	 * 
	 * @param request
	 * @param key 微信支付商户密钥
	 * @return
	 */
	public static String generateCode(HttpServletRequest request, String key) {
		StringBuffer code = new StringBuffer();
		Enumeration<String> names = request.getParameterNames();
		LinkedHashMap<String, String> parameters = new LinkedHashMap<String, String>();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			String value = request.getParameter(name).trim();
			if (StringUtil.isEmpty(value)) continue;
			parameters.put(name, value);
		}
		String[] keys = new String[parameters.keySet().size()];
		parameters.keySet().toArray(keys);
		// 参数名字典排序
		Arrays.sort(keys);
		for (int i = 0; i < keys.length; i++) {
			if (i == 0) code.append(keys[i]).append("=").append(parameters.get(keys[i]));
			else code.append("&").append(keys[i]).append("=").append(parameters.get(keys[i]));
		}
		if (StringUtil.isAllNotEmpty(keys)) code.append("&key=").append(key);
		return MD5Util.encodeByMD5(code.toString());
	}
}
