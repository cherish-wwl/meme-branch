package com.meme.im.util;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.meme.core.util.JWTUtil;
import com.meme.core.util.StringUtil;

public class AccountUtil {

	private static final Logger logger = Logger.getLogger("=====账号工具类=====");
	
	public static String getToken(HttpServletRequest request){
		Cookie[] cookies = request.getCookies();
		String token = null;
		if (null != cookies && cookies.length > 0) {
			for (Cookie co : cookies) {
				if (co.getName().equals(IMConstants.COOKIE_TOKEN_KEY)) {
					token = co.getValue();
					break;
				}
			}
		}
		return token;
	}
	
	public static Object getMemberid(String token){
		Map<String, Object> claims = JWTUtil.verifyToken(token);
		Object memberid = null;
		if(null!=claims&&claims.size()>0){
			memberid = claims.get(IMConstants.COOKIE_MEMBER_KEY);
		}
		return memberid;
	}
	/**
	 * 从cookie取当前登录会员id
	 * 
	 * @param request
	 * @return
	 */
	public static Object getMemberid(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		String token = null;
		if (null != cookies && cookies.length > 0) {
			for (Cookie co : cookies) {
				logger.debug("===|||==="+co.getValue());
				if (co.getName().equals(IMConstants.COOKIE_TOKEN_KEY)) {
					token = co.getValue();
					break;
				}
			}
		}
		if(StringUtil.isEmpty(token)) return null;
		logger.debug("======"+token);
		Map<String, Object> claims = JWTUtil.verifyToken(token);
		Object memberid = null;
		if(null!=claims&&claims.size()>0){
			logger.debug("======"+claims.size());
			memberid = claims.get(IMConstants.COOKIE_MEMBER_KEY);
		}
		return memberid;
	}
}
