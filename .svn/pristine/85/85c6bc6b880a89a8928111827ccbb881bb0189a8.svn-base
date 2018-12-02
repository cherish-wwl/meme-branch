package com.meme.im.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.meme.core.base.BaseMapper;
import com.meme.im.form.ImForm;

public interface ImMessageMapper extends BaseMapper{
	
	/**
	 * 获取所有未读消息
	 * @param memberid
	 * @return
	 */
	List<Map<String,Object>> selectUnReadMessages(@Param("memberid")Object memberid);
	
	/**
	 * 获取所有未读群聊消息
	 * @param memberid
	 * @return
	 */
	List<Map<String,Object>> selectUnReadGroupMessages(@Param("memberid")Object memberid);
	
	/**
	 * 获取和好友的聊天记录
	 * @param memberid
	 * @return
	 */
	List<Map<String,Object>> selectFriendMessages(@Param("sendid")Object sendid,@Param("acceptid")Object acceptid,@Param("page")Integer page);
	
	/**
	 * 获取群聊的聊天记录
	 * @param memberid
	 * @return
	 */
	List<Map<String,Object>> selectGroupMessages(@Param("memberid")Object memberid,@Param("groupid")Object groupid,@Param("page")Integer page);
	
	
	/**
	 * 更新未读消息状态
	 * @param msgids
	 * @return
	 */
	int updateReadState(@Param("msgids")List<Object> msgids);
	
	List<Map<String,Object>> selectByPaginationView(ImForm form);
	
	int countView(ImForm form);
}