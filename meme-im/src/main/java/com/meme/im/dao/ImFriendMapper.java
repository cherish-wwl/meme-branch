package com.meme.im.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.meme.core.base.BaseMapper;
import com.meme.core.form.Form;

public interface ImFriendMapper extends BaseMapper{
	
	/**
	 * 查询好友列表
	 * @param memberid
	 * @return
	 */
	List<Map<String,Object>> selectFriends(@Param("memberid")Object memberid);
	
	/**
	 * 查询某一会员的某一好友信息
	 * @param memberid
	 * @param friendid
	 * @return
	 */
	List<Map<String,Object>> selectFriend(@Param("memberid")Object memberid,@Param("friendid")Object friendid);
	
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
	int delFriend(@Param("mymemberid")Object mymemberid,@Param("friendid")Object friendid);
}