package com.meme.core.util;

import javax.servlet.http.HttpServletRequest;

import com.meme.core.pojo.LoginAccountInfoView;

public class SessionUtil {

	/**
	 * 获取session中的用户实例
	 * @param request
	 * @return
	 */
	public static LoginAccountInfoView getUser(HttpServletRequest request) {
		Object obj=request.getSession().getAttribute(Constants.SESSION_USER);
		if(null!=obj) return (LoginAccountInfoView) obj;
		return null;
	}
}
