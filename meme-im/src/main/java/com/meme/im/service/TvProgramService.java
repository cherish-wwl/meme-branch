package com.meme.im.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.meme.core.base.CrudService;
import com.meme.core.base.IService;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.im.form.ImForm;
import com.meme.im.pojo.TvProgram;

public interface TvProgramService extends IService<TvProgram, Form>, CrudService<TvProgram> {

	/**
	 * 分页查询消息
	 * @param form
	 * @return
	 */
	List<Map<String,Object>> selectByPaginationView(ImForm form);
	
	/**
	 * 统计分页查询消息总数
	 * @param form
	 * @return
	 */
	int countView(ImForm form);

	ResultMessage getTvProgram(HttpServletRequest request,HttpServletResponse response,String type);
}