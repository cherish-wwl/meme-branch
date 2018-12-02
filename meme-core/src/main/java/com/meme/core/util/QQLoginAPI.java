package com.meme.core.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.meme.core.http.HttpRequestMethod;
import com.meme.core.http.ResultMessage;
import com.meme.core.http.entity.RequestEntity;
import com.meme.core.http.request.IRequest;
import com.meme.core.http.request.SSLNetRequest;
import com.meme.core.util.StringUtil;

/**
 * qq登陆相关API
 * @author hzl
 *
 */
public class QQLoginAPI {
	
	private static final Logger LOG = LoggerFactory.getLogger(QQLoginAPI.class);
	
	/**
	 * 获取网页授权access_token
	 * @param code
	 * @return
	 */
	public static JSONObject getWebAccessToken(String code,String app_id,String app_secret,String redirect_uri){
		StringBuffer url=new StringBuffer();
		url.append("https://graph.qq.com/oauth2.0/token");
		url.append("?");
		url.append("client_id=").append(app_id);
		url.append("&client_secret=").append(app_secret);
		url.append("&code=").append(code);
		url.append("&grant_type=").append("authorization_code");
		url.append("&redirect_uri=").append(redirect_uri);
		
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
			if(null!=res&&StringUtil.isAllNotEmpty(res.toString())&& !res.toString().contains("error")){
				JSONObject obj = new JSONObject();
				String[] split = res.toString().split("\\&");
				for(String str:split) {
					obj.put(str.split("=")[0], str.split("=")[1]);
				}
				return obj;
			}
		}
		return null;
	}
	
	/**
	 * 通过access_token获取openid
	 * @param code
	 * @return
	 */
	public static JSONObject getOpenId(String access_token){
		StringBuffer url=new StringBuffer();
		url.append("https://graph.qq.com/oauth2.0/me");
		url.append("?");
		url.append("access_token=").append(access_token);
		
		RequestEntity entity=new RequestEntity();
		entity.setSsl(true);
		entity.setMethod(HttpRequestMethod.GET);
		entity.setUrl(url.toString());
		entity.getProperties().put("Content-Type", "text/html; charset=UTF-8");
		IRequest sslNetRequest=new SSLNetRequest();
		ResultMessage message=sslNetRequest.request(entity);
		
		if(message.getState().equals("200")){
			Object res=message.getData();
			LOG.info("返回网页获取openid值："+res);
			if(null!=res&&StringUtil.isAllNotEmpty(res.toString()) && !res.toString().contains("error")){
				Pattern pattern = Pattern.compile("\\{.+\\}");
				Matcher matcher = pattern.matcher(res.toString());
				String str = null;
				while (matcher.find()) {
					str= matcher.group();
					break;
				}
				LOG.info("str："+str);
				JSONObject obj = JSONObject.parseObject(str);
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
	public static JSONObject getWebUserinfo(String access_token,String oauth_consumer_key,String openid){
		StringBuffer url=new StringBuffer();
		url.append("https://graph.qq.com/user/get_user_info");
		url.append("?");
		url.append("access_token=").append(access_token);
		url.append("&openid=").append(openid);
		url.append("&oauth_consumer_key=").append(oauth_consumer_key);
		
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
	
	public static void main(String[] args) {
		Pattern pattern = Pattern.compile("\\{.+\\}");
		Matcher matcher = pattern.matcher("callback( {\"client_id\":\"101441953\",\"openid\":\"78D21251D7EE29A876AD91192113FEF8\"} );");
		String str = null;
		while (matcher.find()) {
			str= matcher.group();
			break;
		}
		System.out.println(str);
	}
}