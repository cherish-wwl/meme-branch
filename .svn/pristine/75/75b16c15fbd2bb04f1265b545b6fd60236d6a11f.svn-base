package com.meme.im.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.meme.core.base.BaseMapper;
import com.meme.im.form.ImForm;

public interface MemeWbOrderSettlementMapper  extends BaseMapper{
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
	List<Map<String,Object>> doWbOrderQuery(@Param("memberid")Long memberid, @Param("state") Integer state);
	
	
}