package com.meme.im.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.meme.core.base.IService;
import com.meme.core.form.Form;
import com.meme.im.pojo.MemeCrawler;

public interface MemeCrawlerService extends IService<MemeCrawler, Form>{

	
	List<MemeCrawler> selectByPagination(HttpServletRequest request,Form form);
	
	int count(HttpServletRequest request,Form form);
	
	void aTask() throws Exception;

}
