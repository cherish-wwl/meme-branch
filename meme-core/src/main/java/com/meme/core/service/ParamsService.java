package com.meme.core.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.meme.core.base.CrudService;
import com.meme.core.base.IService;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.core.pojo.Params;

public interface ParamsService extends IService<Params,Form>,CrudService<Params>{

	/**
	 * 查询所有参数配置
	 * @param type
	 * @return
	 */
	List<Params> selectAllParams(String type);
	
	ResultMessage clearRules();
	
	/**
	 * 批量修改配置参数
	 * @param request
	 * @return
	 */
	ResultMessage batchupdate(HttpServletRequest request);
}
