package com.meme.im.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.im.entity.FriendJson;
import com.meme.im.entity.Mine;

public interface LayIMService {

	/**
	 * 获取layim mine结构数据
	 * @param request
	 * @return
	 */
	Mine getMine(HttpServletRequest request);
	
	/**
	 * 获取layim 好友和好友分组结构数据
	 * @param request
	 * @return
	 */
	List<FriendJson> getFriends(HttpServletRequest request);
	
	/**
	 * 分页获取陌生人信息
	 * @param form
	 * @return
	 */
	Object getStrangers(HttpServletRequest request,Form form);
	
	/**
	 * 分页获取陌生人信息
	 * @param form
	 * @return
	 */
	Object getGrouplist(HttpServletRequest request,Form form);
	
	/**
	 * 加好友申请
	 * @param request
	 * @param acceptid
	 * @param applymsg
	 * @return
	 */
	ResultMessage doApplyFriend(HttpServletRequest request,String acceptid,String applymsg);
	
	/**
	 * 查询好友申请通知接口
	 * @param request
	 * @param form
	 * @return
	 */
	Object getApplyFriends(HttpServletRequest request,Form form);
	
	/**
	 * 查询好友加群申请通知接口
	 * @param request
	 * @param form
	 * @return
	 */
	Object getApplyGroups(HttpServletRequest request,Form form);
	
	/**
	 * 删除好友接口
	 * @param request
	 * @param friendid
	 * @return
	 */
	ResultMessage delFriend(HttpServletRequest request,String friendid);

	ResultMessage doApplyGroup(HttpServletRequest request, String acceptid, String applymsg);
}
