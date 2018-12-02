package com.meme.im.service;

import java.util.List;
import java.util.Map;

import com.meme.core.base.IService;
import com.meme.core.form.Form;
import com.meme.im.form.ImForm;
import com.meme.im.pojo.MemeWbOrderSettlement;

public interface MemeWbOrderSettlementService extends IService<MemeWbOrderSettlement, Form>{

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
	
	/**
	 * @param memberid
	 * @param state
	 * @return
	 */
	List<Map<String,Object>> doWbOrderQuery(Long memberid, Integer state);
}