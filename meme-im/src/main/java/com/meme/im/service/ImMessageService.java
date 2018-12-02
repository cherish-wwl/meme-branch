package com.meme.im.service;

import java.util.List;
import java.util.Map;

import com.meme.core.base.CrudService;
import com.meme.core.base.IService;
import com.meme.core.form.Form;
import com.meme.im.form.ImForm;
import com.meme.im.pojo.ImMessage;

public interface ImMessageService extends IService<ImMessage, Form>, CrudService<ImMessage> {

	/**
	 * 获取所有未读消息
	 * @param memberid
	 * @return
	 */
	List<Map<String,Object>> selectUnReadMessages(Object memberid);
	
	/**
	 *  获取所有未读群聊消息
	 * @param memberid
	 * @return
	 */
	List<Map<String,Object>> selectUnReadGroupMessages(Object memberid);
	
	/**
	 * 获取和好友对话框的聊天记录
	 * @param friendid 好友id
	 * @param mineid  我的id
	 * @return
	 */
	 List<Map<String,Object>> getFriendMessage(Object friendid, Object mineid, Integer page,String type);
	
	
	/**
	 * 更新未读消息状态
	 * @param msgids
	 * @return
	 */
	int updateReadState(List<Object> msgids);
	
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
