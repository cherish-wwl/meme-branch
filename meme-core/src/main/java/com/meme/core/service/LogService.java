package com.meme.core.service;

import javax.servlet.http.HttpServletRequest;

import com.meme.core.base.CrudService;
import com.meme.core.base.IService;
import com.meme.core.form.Form;
import com.meme.core.pojo.Log;

public interface LogService extends IService<Log,Form>,CrudService<Log>{

	/**
	 * 日志入库
	 * @param request
	 * @param event
	 * @param type
	 * @param description
	 */
	public void log(HttpServletRequest request,String event,String type,String description);
}
