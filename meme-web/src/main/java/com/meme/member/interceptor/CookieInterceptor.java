package com.meme.member.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.meme.core.util.IDGenerator;
import com.meme.core.util.JWTUtil;
import com.meme.core.util.StringUtil;
import com.meme.im.util.CookieUtil;
import com.meme.im.util.IMConstants;

/**
 * 此拦截器用于检测cookie是否有token值，如无则识别为临时访客，生成一个临时会员，设置cookie
 * @author hzl
 *
 */
public class CookieInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Cookie[] cookies=request.getCookies();
		String token=null;
		if(null!=cookies&&cookies.length>0){
			for(Cookie co: cookies){
				if(co.getName().equals(IMConstants.COOKIE_TOKEN_KEY)){
					token=co.getValue();
					break;
				}
			}
		}
		if(StringUtil.isEmpty(token)){
			Long tmp_memberid=IDGenerator.generateId();
			
			//token存cookie
			Map<String, Object> info = new HashMap<String, Object>();
			info.put(IMConstants.COOKIE_MEMBER_KEY, tmp_memberid);
			token=JWTUtil.buildToken(info);
			info.clear();
			info.put(IMConstants.COOKIE_TOKEN_KEY, token);
			CookieUtil.setCookies(request, response, info, null);
		}
		return super.preHandle(request, response, handler);
	}

	
}
