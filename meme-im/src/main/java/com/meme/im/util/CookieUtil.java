package com.meme.im.util;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.meme.core.util.AddressUtil;

public class CookieUtil {

	/**
	 * 
	 * @param request
	 * @param response
	 * @param kv
	 * @param expiry 为空时，默认14天后过期
	 */
	public static void setCookies(HttpServletRequest request,HttpServletResponse response,Map<String, Object> kv,Integer expiry){
		String domain=AddressUtil.getRootDomain(request);
		if(null!=kv&&kv.size()>0){
			for(Map.Entry<String, Object> entry:kv.entrySet()){
				Cookie cookie=new Cookie(entry.getKey(), entry.getValue().toString());
				//path设为/，使顶级域名和二级域名cookie共享
				if(domain==null) {
					cookie.setDomain(request.getLocalAddr());
					cookie.setPath(request.getContextPath());
				} else {
					cookie.setDomain(domain);
					cookie.setPath("/");
				}
				//默认14天后过期
				if(expiry==null) cookie.setMaxAge(1209600);
				else cookie.setMaxAge(expiry);
				response.addCookie(cookie);
			}
		}
	}
}
