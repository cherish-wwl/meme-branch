package com.meme.im.service;

import java.util.List;
import java.util.Map;

import com.meme.core.base.CrudService;
import com.meme.core.base.IService;
import com.meme.core.form.Form;
import com.meme.im.pojo.ImFriend;

public interface ImFriendService extends IService<ImFriend, Form>, CrudService<ImFriend> {

	/**
	 * 查询好友列表
	 * @param memberid
	 * @return
	 */
	List<Map<String,Object>> selectFriends(Object memberid);
	
	/**
	 * 查询某一会员的某一好友信息
	 * @param memberid
	 * @param friendid
	 * @return
	 */
	List<Map<String,Object>> selectFriend(Object memberid,Object friendid);
	
	/**
	 * 分页获取陌生人信息
	 * @param form
	 * @return
	 */
	List<Map<String,Object>> selectStrangersByPagination(Form form);
	
	/**
	 * 统计查询陌生人总数
	 * @param form
	 * @return
	 */
	int countStrangers(Form form);
	
	/**
	 * 删除好友接口
	 * @param mymemberid 我的会员ID
	 * @param friendid 好友会员ID
	 * @return
	 */
	int delFriend(Object mymemberid,Object friendid);
}
