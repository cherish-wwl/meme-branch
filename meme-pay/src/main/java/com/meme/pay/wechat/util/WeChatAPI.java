package com.meme.pay.wechat.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.meme.core.cache.ParamsCache;
import com.meme.core.http.HttpRequestMethod;
import com.meme.core.http.ResultMessage;
import com.meme.core.http.entity.RequestEntity;
import com.meme.core.http.request.IRequest;
import com.meme.core.http.request.SSLNetRequest;
import com.meme.core.util.StringUtil;

/**
 * 微信相关API
 * @author hzl
 *
 */
public class WeChatAPI {
	
	private static final Logger LOG = LoggerFactory.getLogger(WeChatAPI.class);
	
	public static String validate(HttpServletRequest request,HttpServletResponse response){
		String echostr=request.getParameter("echostr");
		if(checkSignature(request)){
			return echostr;
		}
		return "";
	}
	
	/**
	 * 获取网页授权access_token
	 * @param code
	 * @return
	 */
	public static JSONObject getWebAccessToken(String code,String app_id,String app_secret){
		StringBuffer url=new StringBuffer();
		url.append(WeChatConstants.API_WEB_ACCESS_TOKEN);
		url.append("?");
		url.append("appid=").append(app_id);
		url.append("&secret=").append(app_secret);
		url.append("&code=").append(code);
		url.append("&grant_type=").append("authorization_code");
		
		RequestEntity entity=new RequestEntity();
		entity.setSsl(true);
		entity.setMethod(HttpRequestMethod.GET);
		entity.setUrl(url.toString());
		entity.getProperties().put("Content-Type", "text/html; charset=UTF-8");
		IRequest sslNetRequest=new SSLNetRequest();
		ResultMessage message=sslNetRequest.request(entity);
		
		if(message.getState().equals("200")){
			Object res=message.getData();
			LOG.info("返回网页授权ACCESS_TOKEN值："+res);
			if(null!=res&&StringUtil.isAllNotEmpty(res.toString())){
				JSONObject obj=JSONObject.parseObject(res.toString());
				return obj;
			}
		}
		return null;
	}
	
	/**
	 * 网页授权拉取用户信息
	 * @param access_token
	 * @param openid
	 * @return
	 */
	public static JSONObject getWebUserinfo(String access_token,String openid){
		StringBuffer url=new StringBuffer();
		url.append(WeChatConstants.API_GET_WEB_USER_INFO);
		url.append("?");
		url.append("access_token=").append(access_token);
		url.append("&openid=").append(openid);
		url.append("&lang=").append("zh_CN");
		
		RequestEntity entity=new RequestEntity();
		entity.setSsl(true);
		entity.setMethod(HttpRequestMethod.GET);
		entity.setUrl(url.toString());
		entity.getProperties().put("Content-Type", "text/html; charset=UTF-8");
		IRequest sslNetRequest=new SSLNetRequest();
		
		ResultMessage message=sslNetRequest.request(entity);
		if(message.getState().equals("200")){
			Object res=message.getData();
			LOG.info("返回用户信息数据："+res);
			if(null!=res&&StringUtil.isAllNotEmpty(res.toString())){
				JSONObject obj=JSONObject.parseObject(res.toString());
				return obj;
			}
		}
		return null;
	}
	
	/**
	 * 将请求验证TOKEN的URL返回的参数token、timestamp、nonce拼接成字符串后用sha1方式加密
	 * <br/>检查加密后的字符串是否与返回的参数signature一致
	 * @param request
	 * @return
	 */
	public static boolean checkSignature(HttpServletRequest request) {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr=request.getParameter("echostr");
		LOG.info("token验证返回数据：signature="+signature+",timestamp="+timestamp+",nonce="+nonce+"echostr="+echostr);
		List<String> list = new ArrayList<String>();
		list.add(ParamsCache.get("WEIXIN_ACCESS_TOKEN").getValue());
		list.add(timestamp);
		list.add(nonce);
		Collections.sort(list);
		StringBuffer sb = new StringBuffer();
		for (String s : list) {
			sb.append(s);
		}
		String sha1Str=sha1(sb.toString());
		if(sha1Str.equals(signature)){
			return true;
		}
		return false;
	}

	/**
	 * SHA-1 加密，并转成16进制字符串
	 * @param source
	 * @return
	 */
	public static String sha1(String source) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] b = source.getBytes();
			md.update(b);
			return bytes2Hex(md.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 字节转16进制字符串
	 * @param b
	 * @return
	 */
	public static String bytes2Hex(byte[] b) {
		StringBuffer des = new StringBuffer();
		String tmp = null;
		for (int i = 0; i < b.length; i++) {
			tmp = (Integer.toHexString(b[i] & 0xFF));
			if (tmp.length() == 1) {
				des.append("0");
			}
			des.append(tmp);
		}
		return des.toString();
	}
}