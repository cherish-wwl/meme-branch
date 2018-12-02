package com.meme.core.base;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.meme.core.pojo.LoginAccountInfoView;
import com.meme.core.spring.TimestampEditor;
import com.meme.core.util.Constants;

/**
 * 基类控制器
 * @author hzl
 *
 */
public abstract class BaseController {

	protected Logger logger = Logger.getLogger(getClass());
	/**
	 * 初始化数据绑定，添加date和Timestamp的数据转换
	 * @param request
	 * @param binder
	 */
	@InitBinder
	protected void init(HttpServletRequest request, ServletRequestDataBinder binder){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		SimpleDateFormat datetimeFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		datetimeFormat.setLenient(false);
		binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat,true));
		binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(datetimeFormat,true));
		binder.registerCustomEditor(java.sql.Timestamp.class, new TimestampEditor(datetimeFormat,true));
	}
	
	protected String getBasePath(HttpServletRequest request) {
		String basepath=request.getScheme()+"://"+request.getServerName()+(request.getServerPort()!=80?(":"+request.getServerPort()):"")+request.getContextPath()+"/";
		return basepath;
	}
	
	protected String getRealPath(HttpServletRequest request,String relativPath) {
		return request.getServletContext().getRealPath(relativPath);
	}
	
	protected LoginAccountInfoView getUser(HttpServletRequest request) {
		Object obj=request.getSession().getAttribute(Constants.SESSION_USER);
		if(null!=obj) return (LoginAccountInfoView) obj;
		return null;
	}
}
