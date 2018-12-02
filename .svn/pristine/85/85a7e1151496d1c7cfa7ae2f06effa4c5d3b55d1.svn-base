package com.meme.core.util;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * 字符串工具类
 * @author hzl
 *
 */
public class StringUtil {

	/**
	 * 判断字符串是否全为空
	 * @param strs
	 * @return
	 */
	public static boolean isAllEmpty(String ...strs){
		for(String s:strs){
			if(!isEmpty(s)) return  false;
		}
		return true;
	}
	
	/**
	 * 判断字符串数组中是否存在一个为空
	 * @param strs
	 * @return
	 */
	public static boolean isOneEmpty(String ...strs){
		for(String s:strs){
			if(isEmpty(s)) return true;
		}
		return false;
	}
	
	/**
	 * 判断字符串数组中是否存在一个不为空
	 * @param strs
	 * @return
	 */
	public static boolean isOneNotEmpty(String ...strs){
		for(String s:strs){
			if(!isEmpty(s)) return true;
		}
		return false;
	}
	
	/**
	 * 判断字符串是否全不为空
	 * @param strs
	 * @return
	 */
	public static boolean isAllNotEmpty(String ...strs){
		for(String s:strs){
			if(isEmpty(s)) return false;
		}
		return true;
	}
	
	/**
	 * 判断字符串是否为空
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s){
		if(null==s||"".equals(s.trim())) return true;
		return false;
	}
	
	/**
	 * 默认使用UTF-8编码字符串
	 * @param value
	 * @return
	 */
	public static String getEncodeString(String value){
		return getEncodeString(value,"UTF-8");
	}
	/**
	 * 获取编码字符串
	 * @param value
	 * @param charset
	 * @return
	 */
	public static String getEncodeString(String value,String charset){
		try {
			if(isAllNotEmpty(value)) return new String(value.getBytes(charset));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 匹配IP地址或者域名
	 * @param request
	 * @param menuurl
	 * @return
	 */
	public static String matchUrl(HttpServletRequest request,String menuurl){
		//正则匹配IP地址
		String ip_regex=".*([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}.*";
		Pattern ip_pattern = Pattern.compile(ip_regex, Pattern.CASE_INSENSITIVE);
		Matcher ip_match = ip_pattern.matcher(menuurl);
		//正则匹配域名
		String domain_regex=".*(\\.com|\\.net|\\.org|\\.biz|\\.cn|\\.cc|\\.wang|\\.com\\.cn|\\.net\\.cn|\\.org\\.cn|\\.asia|\\.tv|\\.top|\\.xin|\\.vip|\\.xyz|\\.mobi|\\.me|\\.co).*";
		//String domain_regex=".*(com|net|org|biz|cn|cc|wang|com\\.cn|net\\.cn|org\\.cn|asia|tv|top|xin|vip|xyz|mobi|me|co).*";
		Pattern domain_pattern = Pattern.compile(domain_regex, Pattern.CASE_INSENSITIVE);
		Matcher domain_match = domain_pattern.matcher(menuurl);
		
		String url=null;
		if(ip_match.matches()||domain_match.matches()){
			url=menuurl;
		}else{
			String basepath=request.getScheme()+"://"+request.getServerName()+(request.getServerPort()!=80?(":"+request.getServerPort()):"")+request.getContextPath()+"/";
			if(StringUtil.isEmpty(menuurl.trim())||menuurl.trim().equals("#")) url="#";
			else url=basepath+menuurl.trim();
		}
		return url;
	}
}
