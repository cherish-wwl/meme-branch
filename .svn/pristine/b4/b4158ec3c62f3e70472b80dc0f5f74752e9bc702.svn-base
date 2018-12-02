package com.meme.im.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meme.core.base.AbstractService;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.core.mybatis.MapperHelper;
import com.meme.core.util.IDGenerator;
import com.meme.im.dao.ImFriendApplyMapper;
import com.meme.im.dao.ImGroupMapper;
import com.meme.im.pojo.ImFriend;
import com.meme.im.pojo.ImFriendApply;
import com.meme.im.pojo.ImFriendGroup;
import com.meme.im.service.ImFriendApplyService;
import com.meme.im.service.ImFriendGroupService;
import com.meme.im.service.ImFriendService;
import com.meme.im.service.ImGroupService;

@Service("ImFriendApplyService")
@Transactional
public class ImFriendApplyServiceImpl extends AbstractService implements ImFriendApplyService {

	@Resource private ImFriendApplyMapper mapper;
	@Resource private ImGroupMapper imGroupmapper;
	@Resource private ImFriendService imFriendService;
	@Resource private ImGroupService imGroupService;
	@Resource private ImFriendGroupService imFriendGroupService;
	
	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(ImFriendApply record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(ImFriendApply record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public ImFriendApply selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(ImFriendApply record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(ImFriendApply record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ImFriendApply> selectAll(){
		return (List<ImFriendApply>) MapperHelper.toBeanList(this.mapper.selectAll(ImFriendApply.class), ImFriendApply.class);
	}

	public void buildSearchCriterion(Class<?> entityClass, Form form) {
		super.buildSearchCriterion(entityClass, form);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ImFriendApply> selectByPagination(Form form) {
		this.buildSearchCriterion(ImFriendApply.class, form);
		this.buildOrderByCriterion(ImFriendApply.class, form);
		this.buildLimitCriterion(ImFriendApply.class, form);
		return (List<ImFriendApply>) MapperHelper.toBeanList(this.mapper.selectByPagination(ImFriendApply.class,this.getList()), ImFriendApply.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(ImFriendApply.class, form);
		return this.mapper.count(ImFriendApply.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ImFriendApply> selectByEntity(ImFriendApply record) {
		return (List<ImFriendApply>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), ImFriendApply.class);
	}

	@Override
	public int batchInsert(List<ImFriendApply> record) {
		return this.mapper.batchInsert(record,ImFriendApply.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,ImFriendApply.class);
	}

	@Override
	public int batchUpdate(List<ImFriendApply> record) {
		return this.mapper.batchUpdate(record,ImFriendApply.class);
	}

	@Override
	public ResultMessage add(HttpServletRequest request, ImFriendApply record) {
		return null;
	}

	@Override
	public ResultMessage edit(HttpServletRequest request, ImFriendApply record) {
		return null;
	}

	@Override
	public ResultMessage delete(List<Object> ids) {
		return null;
	}

	@Override
	public List<Map<String, Object>> selectApplyFriendByPagination(Form form) {
		return this.mapper.selectApplyFriendByPagination(form);
	}

	@Override
	public int countApplyFriend(Form form) {
		return this.mapper.countApplyFriend(form);
	}
	
	@Override
	public List<Map<String, Object>> selectApplyGroupByPagination(Form form) {
		return this.mapper.selectApplyGroupByPagination(form);
	}
	
	@Override
	public int countApplyGroup(Form form) {
		return this.mapper.countApplyGroup(form);
	}

	@Override
	public ResultMessage accept(Long applyid, Boolean isaccept) {
		if(null==applyid) return ResultMessage.failed("请提供好友申请记录编号");
		ImFriendApply apply=this.selectByPrimaryKey(applyid);
		if(null==apply) return ResultMessage.failed("该好友申请记录不存在");
		
		if(apply.getState()==1) return ResultMessage.failed("已同意该好友申请");
		if(apply.getState()==-1) return ResultMessage.failed("已拒绝该好友申请");

		List<Map<String,Object>> send_list=this.imFriendService.selectFriend(apply.getSendid(), apply.getAcceptid());
		List<Map<String,Object>> accept_list=this.imFriendService.selectFriend(apply.getAcceptid(), apply.getSendid());
		if(null!=send_list&&send_list.size()==1&&null!=accept_list&&accept_list.size()==1) return ResultMessage.failed("已添加好友，无需重复添加");
		else{
			//异常清除相关数据重新添加
			if(null!=send_list&&send_list.size()>0){
				for(Map<String,Object> map:send_list){
					this.imFriendService.deleteByPrimaryKey(map.get("fid"));
				}
			}
			if(null!=accept_list&&accept_list.size()>0){
				for(Map<String,Object> map:accept_list){
					this.imFriendService.deleteByPrimaryKey(map.get("fid"));
				}
			}
			this.deleteByPrimaryKey(apply.getApplyid());
		}
		
		Object data=null;
		//添加进好友表
		if(isaccept){
			ImFriendGroup grup=new ImFriendGroup();
			//添加申请方好友记录
			grup.setMemberid(apply.getSendid());
			List<ImFriendGroup> groups=this.imFriendGroupService.selectByEntity(grup);
			if(null!=groups&&groups.size()>0){
				ImFriend friend=new ImFriend();
				friend.setAddtime(new Date());
				friend.setFriendid(apply.getAcceptid());
				friend.setGroupid(groups.get(0).getId());
				long id=IDGenerator.generateId();
				friend.setId(id);
				this.imFriendService.insertSelective(friend);
				//返回好友id
				data=apply.getSendid();
			}
			
			//添加本人好友记录
			grup.setMemberid(apply.getAcceptid());
			groups=this.imFriendGroupService.selectByEntity(grup);
			if(null!=groups&&groups.size()>0){
				ImFriend friend=new ImFriend();
				friend.setAddtime(new Date());
				friend.setFriendid(apply.getSendid());
				friend.setGroupid(groups.get(0).getId());
				long id=IDGenerator.generateId();
				friend.setId(id);
				this.imFriendService.insertSelective(friend);
			}
			
			//更新好友申请表记录状态为同意
			apply.setState(1);
			apply.setReplytime(new Date());
			this.updateByPrimaryKeySelective(apply);
		}else{
			apply.setState(-1);
			apply.setReplytime(new Date());
			this.updateByPrimaryKeySelective(apply);
		}
		
		return ResultMessage.success("操作成功",data);
	}
	
	@Override
	public ResultMessage acceptGroup(Long applyid, Boolean isaccept) {
		if(null==applyid) return ResultMessage.failed("请提供好友加群申请记录编号");
		ImFriendApply apply=this.selectByPrimaryKey(applyid);
		if(null==apply) return ResultMessage.failed("该好友加群记录不存在");
		
		if(apply.getState()==1) return ResultMessage.failed("已同意该加群申请");
		if(apply.getState()==-1) return ResultMessage.failed("已拒绝该加群申请");
		
		
		Map<String,Long> data=new HashMap<String,Long>();
		//添加进好友表
		if(isaccept){
			//添加好友进群记录
			ImFriend friend=new ImFriend();
			friend.setAddtime(new Date());
			friend.setFriendid(apply.getSendid());
			friend.setGroupid(apply.getGroupid());
			long id=IDGenerator.generateId();
			friend.setId(id);
			this.imFriendService.insertSelective(friend);
			//返回好友id
			data.put("friendid", apply.getSendid());
			data.put("groupid", apply.getGroupid());
			//更新好友加群申请表记录状态为同意
			apply.setState(1);
			apply.setReplytime(new Date());
			this.updateByPrimaryKeySelective(apply);
			//更新群人数
			this.imGroupmapper.updateNum(apply.getGroupid());
		}else{
			apply.setState(-1);
			apply.setReplytime(new Date());
			this.updateByPrimaryKeySelective(apply);
		}
		
		return ResultMessage.success("操作成功",data);
	}
}
