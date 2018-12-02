package com.meme.im.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.meme.core.cache.DictCache;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.core.pojo.DictItemView;
import com.meme.core.util.IDGenerator;
import com.meme.core.util.StringUtil;
import com.meme.im.entity.FriendJson;
import com.meme.im.entity.Mine;
import com.meme.im.entity.enums.OnlineType;
import com.meme.im.pojo.ImFriendApply;
import com.meme.im.pojo.ImFriendGroup;
import com.meme.im.pojo.ImGroup;
import com.meme.im.pojo.Member;
import com.meme.im.service.ImFriendApplyService;
import com.meme.im.service.ImFriendGroupService;
import com.meme.im.service.ImFriendService;
import com.meme.im.service.ImGroupService;
import com.meme.im.service.LayIMService;
import com.meme.im.service.MemberService;
import com.meme.im.util.AccountUtil;
import com.meme.im.websocket.ServerPoint;
import com.meme.im.websocket.WebSocketServer;

@Service("LayIMService")
@Transactional
public class LayIMServiceImpl implements LayIMService {

	@Resource private MemberService memberService;
	@Resource private ImFriendService imFriendService;
	@Resource private ImFriendApplyService imFriendApplyService;
	@Resource private ImFriendGroupService imFriendGroupService;
	@Resource private ImGroupService imGroupService;
	
	@Override
	public Mine getMine(HttpServletRequest request) {
		Object memberid=AccountUtil.getMemberid(request);
		Member member=this.memberService.selectByPrimaryKey(memberid);
		//密码盐值置空不返回
		member.setAccount(null);
		member.setMpassword(null);
		member.setSalt(null);
		Mine mine=new Mine();
		try {
			BeanUtils.copyProperties(mine, member);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		if(null!=mine.getSex()){
			DictItemView sex=DictCache.getDictItem("SEX", mine.getSex().toString());
			if(null!=sex) mine.setSexText(sex.getDictitemname());
		}
		if(null!=mine.getMtype()){
			DictItemView mtype=DictCache.getDictItem("MEMBER_ACCOUNT_TYPE", mine.getMtype().toString());
			if(null!=mtype) mine.setMtypeText(mtype.getDictitemname());
		}
		if(null!=mine.getState()){
			DictItemView state=DictCache.getDictItem("MEMBER_ACCOUNT_STATE", mine.getState().toString());
			if(null!=state) mine.setStateText(state.getDictitemname());
		}
		return mine;
	}

	@Override
	public List<FriendJson> getFriends(HttpServletRequest request) {
		Object memberid=AccountUtil.getMemberid(request);
		List<Map<String,Object>> list=this.imFriendService.selectFriends(memberid);
		LinkedHashMap<Long, FriendJson> friends=new LinkedHashMap<Long, FriendJson>();
		if(null!=list&&list.size()>0){
			for(Map<String,Object> map:list){
				Long groupid=Long.valueOf(map.get("groupid").toString());
//				Mine mine=new Mine();
//				try {
//					BeanUtils.populate(mine, map);
//				} catch (IllegalAccessException e) {
//					e.printStackTrace();
//				} catch (InvocationTargetException e) {
//					e.printStackTrace();
//				}
//				mine.setAccount(null);
//				mine.setMpassword(null);
//				mine.setSalt(null);
				map.put("account", null);
				map.put("mpassword", null);
				map.put("salt", null);
				ServerPoint serverPoint = WebSocketServer.sessions.get(map.get("id").toString());
				if(serverPoint != null && serverPoint.getSession().isOpen()) {
					map.put("status", OnlineType.online.getType());
				}else {
					map.put("status", OnlineType.offline.getType());
				}
				if(friends.containsKey(groupid)){
					friends.get(groupid).getList().add(map);
				}else{
					FriendJson friendJson=new FriendJson();
					friendJson.setGroupname(map.get("groupname").toString());
					friendJson.setId(groupid);
					friendJson.getList().add(map);
					friends.put(groupid, friendJson);
				}
			}
		}else{
			//无好友时加载好友分组
			ImFriendGroup tmp=new ImFriendGroup();
			tmp.setMemberid(Long.valueOf(memberid.toString()));
			List<ImFriendGroup> groups=this.imFriendGroupService.selectByEntity(tmp);
			if(null!=groups&&groups.size()>0){
				for(ImFriendGroup g:groups){
					FriendJson friendJson=new FriendJson();
					friendJson.setGroupname(g.getGroupname());
					friendJson.setId(g.getId());
					friends.put(g.getId(), friendJson);
				}
			}
		}
		List<FriendJson> jsons=new ArrayList<FriendJson>();
		if(friends.size()>0){
			for(Map.Entry<Long, FriendJson> entry:friends.entrySet()){
				jsons.add(entry.getValue());
			}
		}
		return jsons;
	}

	@Override
	public Object getStrangers(HttpServletRequest request,Form form) {
		JSONObject pagination=new JSONObject();
		
		Object memberid=AccountUtil.getMemberid(request);
		form.setPrimarykey(memberid);
		form.setPagesize(10);
		form.setSort("lastlogin");
		form.setOrder("DESC");
		form.init();
		
		List<Map<String, Object>> list=this.imFriendService.selectStrangersByPagination(form);
		pagination.put("list", list);
		int count=this.imFriendService.countStrangers(form);
		int pages=(count - 1) / form.getPagesize() + 1;
		pagination.put("pages", pages);
		return pagination;
	}
	
	@Override
	public Object getGrouplist(HttpServletRequest request,Form form) {
		JSONObject pagination=new JSONObject();
		
		Object memberid=AccountUtil.getMemberid(request);
		form.setPrimarykey(memberid);
		form.setPagesize(10);
		form.setSort("addtime");
		form.setOrder("DESC");
		form.init();
		
		List<Map<String, Object>> list=this.imGroupService.selectGroupListByPagination(form);
		pagination.put("list", list);
		int count=this.imGroupService.countStrangers(form);
		int pages=(count - 1) / form.getPagesize() + 1;
		pagination.put("pages", pages);
		return pagination;
	}

	@Override
	public ResultMessage doApplyFriend(HttpServletRequest request,String acceptid,String applymsg) {
		if(StringUtil.isEmpty(acceptid)) return ResultMessage.failed("好友编号获取失败，请重试");
		
		//判断验证消息字符长度有无超限
		if(StringUtil.isAllNotEmpty(applymsg)&&applymsg.length()>200) return ResultMessage.failed("验证消息长度过长");
		
		Object memberid=AccountUtil.getMemberid(request);
		
		if(acceptid.equals(memberid.toString())) return ResultMessage.failed("不能添加自己为好友");
		Member member=this.memberService.selectByPrimaryKey(Long.valueOf(acceptid));
		if(null==member) return ResultMessage.failed("此用户不存在，无法添加好友");
		if(member.getMtype()==-1) return ResultMessage.failed("禁止添加临时访客为好友");
		
		ImFriendApply tmp=new ImFriendApply();
		tmp.setSendid(Long.valueOf(memberid.toString()));
		tmp.setAcceptid(Long.valueOf(acceptid));
		tmp.setState(0);
		List<ImFriendApply> list=this.imFriendApplyService.selectByEntity(tmp);
		if(null!=list&&list.size()>0) return ResultMessage.failed("已申请添加好友，请耐心等待对方同意");
		
		long id=IDGenerator.generateId();
		
		ImFriendApply record=new ImFriendApply();
		record.setAcceptid(Long.valueOf(acceptid));
		record.setApplymsg(applymsg);
		record.setApplyid(id);
		record.setApplytime(new Date());
		record.setSendid(Long.valueOf(memberid.toString()));
		record.setState(0);
		record.setType(0);
		int result=this.imFriendApplyService.insertSelective(record);
		
		if(result>0) return ResultMessage.success("申请成功，等待同意");
		else return ResultMessage.failed("申请失败，请刷新重试");
	}
	
	@Override
	public ResultMessage doApplyGroup(HttpServletRequest request,String groupid,String applymsg) {
		if(StringUtil.isEmpty(groupid)) return ResultMessage.failed("群组编号获取失败，请重试");
		
		//判断验证消息字符长度有无超限
		if(StringUtil.isAllNotEmpty(applymsg)&&applymsg.length()>200) return ResultMessage.failed("验证消息长度过长");
		
		Object memberid=AccountUtil.getMemberid(request);
		
		ImGroup imGroup = this.imGroupService.selectByPrimaryKey(Long.valueOf(groupid));
		if(null==imGroup) return ResultMessage.failed("此群组不存在，无法加入该群");
		
		ImFriendApply tmp=new ImFriendApply();
		tmp.setSendid(Long.valueOf(memberid.toString()));
		tmp.setAcceptid(imGroup.getMemberid());
		tmp.setState(0);
		List<ImFriendApply> list=this.imFriendApplyService.selectByEntity(tmp);
		if(null!=list&&list.size()>0) return ResultMessage.failed("已申请加入该群，请耐心等待群主同意");
		
		long id=IDGenerator.generateId();
		
		ImFriendApply record=new ImFriendApply();
		record.setAcceptid(imGroup.getMemberid());
		record.setApplymsg(applymsg);
		record.setApplyid(id);
		record.setApplytime(new Date());
		record.setSendid(Long.valueOf(memberid.toString()));
		record.setState(0);
		record.setType(1);
		record.setGroupid(imGroup.getId());
		int result=this.imFriendApplyService.insertSelective(record);
		
		if(result>0) return ResultMessage.success("申请成功，等待同意");
		else return ResultMessage.failed("申请失败，请刷新重试");
	}

	@Override
	public Object getApplyFriends(HttpServletRequest request, Form form) {
		JSONObject pagination=new JSONObject();
		
		Object memberid=AccountUtil.getMemberid(request);
		form.setPrimarykey(memberid);
		form.setSort("applytime");
		form.setOrder("DESC");
		form.init();
		
		List<Map<String, Object>> list=this.imFriendApplyService.selectApplyFriendByPagination(form);
		pagination.put("list", list);
		int count=this.imFriendApplyService.countApplyFriend(form);
		int pages=(count - 1) / Form.DEFAULT_LIMIT + 1;
		pagination.put("pages", pages);
		return pagination;
	}
	
	@Override
	public Object getApplyGroups(HttpServletRequest request, Form form) {
		JSONObject pagination=new JSONObject();
		
		Object memberid=AccountUtil.getMemberid(request);
		form.setPrimarykey(memberid);
		form.setSort("applytime");
		form.setOrder("DESC");
		form.init();
		
		List<Map<String, Object>> list=this.imFriendApplyService.selectApplyGroupByPagination(form);
		pagination.put("list", list);
		int count=this.imFriendApplyService.countApplyGroup(form);
		int pages=(count - 1) / Form.DEFAULT_LIMIT + 1;
		pagination.put("pages", pages);
		return pagination;
	}

	@Override
	public ResultMessage delFriend(HttpServletRequest request, String friendid) {
		if(StringUtil.isEmpty(friendid)) return ResultMessage.failed("好友编号不能为空");
		Object memberid=AccountUtil.getMemberid(request);
		this.imFriendService.delFriend(memberid, friendid);
		this.imFriendService.delFriend(friendid, memberid);
		return ResultMessage.success("好友删除成功");
	}

}
