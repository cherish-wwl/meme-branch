package com.meme.core.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.meme.core.http.ResultMessage;
import com.meme.core.http.entity.RequestEntity;
import com.meme.core.http.request.IRequest;
import com.meme.core.http.request.NetRequest;

/**
 * 地址工具类
 * @author hzl
 *
 */
public class AddressUtil {

	/**
	 * 获取真实IP地址
	 * @param request
	 * @return
	 */
	public static String getRealIPAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * 通过淘宝IP地址库识别地址和运营商
	 * @param ip
	 * @return
	 */
	public static JSONObject ip2Address(String ip){
		IRequest request=new NetRequest();
		RequestEntity entity=new RequestEntity();
		entity.setUrl("http://ip.taobao.com/service/getIpInfo.php?ip="+ip);
		ResultMessage result=request.request(entity);
		if(null!=result&&result.getState().equals("200")){
			return JSONObject.parseObject(result.getData().toString());
		}else {
			return null;
		}
	}
	
	/**
	 * url中获取根域名
	 * @param url
	 * @return
	 */
	public static String getRootDomain(HttpServletRequest request){
		String url=request.getRequestURL().toString();
		String domain=null;
		try {
			String host = new URL(url).getHost().toLowerCase();
			Pattern pattern = Pattern.compile("[^\\.]+(\\.com\\.cn|\\.net\\.cn|\\.org\\.cn|\\.gov\\.cn|\\.com|\\.net|\\.cn|\\.org|\\.cc|\\.me|\\.tel|\\.mobi|\\.asia|\\.biz|\\.info|\\.name|\\.tv|\\.hk|\\.公司|\\.中国|\\.网络|\\.wang)");
			Matcher matcher = pattern.matcher(host);
			while (matcher.find()) {
				domain= matcher.group();
				break;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return domain;
	}
}
