package com.meme.im.dao;

import java.util.List;
import java.util.Map;

import com.meme.core.base.BaseMapper;
import com.meme.core.form.Form;

public interface ImFriendApplyMapper extends BaseMapper {

	/**
	 * 查询好友申请通知
	 * 
	 * @param form
	 * @return
	 */
	List<Map<String, Object>> selectApplyFriendByPagination(Form form);

	/**
	 * 统计好友申请通知查询结果总数
	 * 
	 * @param form
	 * @return
	 */
	int countApplyFriend(Form form);
	
	/**
	 * 查询加群申请通知
	 * 
	 * @param form
	 * @return
	 */
	List<Map<String, Object>> selectApplyGroupByPagination(Form form);
	
	/**
	 * 统计加群申请通知查询结果总数
	 * 
	 * @param form
	 * @return
	 */
	int countApplyGroup(Form form);
}