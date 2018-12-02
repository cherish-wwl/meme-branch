package com.meme.im.dao;

import java.util.List;
import java.util.Map;
import com.meme.core.base.BaseMapper;
import com.meme.im.form.ImForm;

public interface MemeShopMapper  extends BaseMapper{
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
	
	
}