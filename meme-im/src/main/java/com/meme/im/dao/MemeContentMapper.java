package com.meme.im.dao;

import java.util.List;
import java.util.Map;


import com.meme.core.base.BaseMapper;
import com.meme.im.form.ImForm;

public interface MemeContentMapper extends BaseMapper{
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
	
	/**
	 * 查询栏目首页分页
	 * @param form
	 * @return
	 */
	List<Map<String, Object>> selectByPaginationSectionView(ImForm form);

	int countSelctionView(ImForm form);
	
	
}