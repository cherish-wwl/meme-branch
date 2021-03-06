package com.meme.im.service;

import java.util.List;
import java.util.Map;

import com.meme.core.base.CrudService;
import com.meme.core.base.IService;
import com.meme.core.form.Form;
import com.meme.im.form.ImForm;
import com.meme.im.pojo.MemeContent;

public interface MemeContentService extends IService<MemeContent, Form>, CrudService<MemeContent> {

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
	
	List<Map<String,Object>> selectContentSections(Long columnid);
	
	List<Map<String,Object>> selectContentPagination(ImForm form);
	
	Integer countContentPagination(ImForm form);
	
	List<Map<String,Object>> selectByPaginationSectionView(ImForm form);
	
	Integer countSelctionView(ImForm form);
	
	
}