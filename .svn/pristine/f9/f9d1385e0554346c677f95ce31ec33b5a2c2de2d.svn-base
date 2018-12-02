package com.meme.im.service;

import java.util.List;
import java.util.Map;

import com.meme.core.base.CrudService;
import com.meme.core.base.IService;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.im.pojo.ImFriendApply;

public interface ImFriendApplyService extends IService<ImFriendApply, Form>, CrudService<ImFriendApply> {

	/**
	 * 查询好友申请通知
	 * 
	 * @param form
	 * @return
	 */
	List<Map<String, Object>> selectApplyFriendByPagination(Form form);
	
	/**
	 * 查询好友申请通知
	 * 
	 * @param form
	 * @return
	 */
	List<Map<String, Object>> selectApplyGroupByPagination(Form form);

	/**
	 * 统计好友申请通知查询结果总数
	 * 
	 * @param form
	 * @return
	 */
	int countApplyFriend(Form form);
	
	/**
	 * 统计好友申请通知查询结果总数
	 * 
	 * @param form
	 * @return
	 */
	int countApplyGroup(Form form);
	
	/**
	 * 是否同意添加好友
	 * @param applyid
	 * @param isaccept
	 * @return
	 */
	ResultMessage accept(Long applyid,Boolean isaccept);
	
	/**
	 * 是否同意加入该群
	 * @param applyid
	 * @param isaccept
	 * @return
	 */
	ResultMessage acceptGroup(Long applyid,Boolean isaccept);
}
