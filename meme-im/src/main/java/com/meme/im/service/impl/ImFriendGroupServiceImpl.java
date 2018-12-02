package com.meme.im.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meme.core.base.AbstractService;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.core.mybatis.MapperHelper;
import com.meme.im.dao.ImFriendGroupMapper;
import com.meme.im.pojo.ImFriendGroup;
import com.meme.im.service.ImFriendGroupService;

@Service("ImFriendGroupService")
@Transactional
public class ImFriendGroupServiceImpl extends AbstractService implements ImFriendGroupService {

	@Resource private ImFriendGroupMapper mapper;
	
	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(ImFriendGroup record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(ImFriendGroup record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public ImFriendGroup selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(ImFriendGroup record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(ImFriendGroup record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ImFriendGroup> selectAll(){
		return (List<ImFriendGroup>) MapperHelper.toBeanList(this.mapper.selectAll(ImFriendGroup.class), ImFriendGroup.class);
	}

	public void buildSearchCriterion(Class<?> entityClass, Form form) {
		super.buildSearchCriterion(entityClass, form);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ImFriendGroup> selectByPagination(Form form) {
		this.buildSearchCriterion(ImFriendGroup.class, form);
		this.buildOrderByCriterion(ImFriendGroup.class, form);
		this.buildLimitCriterion(ImFriendGroup.class, form);
		return (List<ImFriendGroup>) MapperHelper.toBeanList(this.mapper.selectByPagination(ImFriendGroup.class,this.getList()), ImFriendGroup.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(ImFriendGroup.class, form);
		return this.mapper.count(ImFriendGroup.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ImFriendGroup> selectByEntity(ImFriendGroup record) {
		return (List<ImFriendGroup>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), ImFriendGroup.class);
	}

	@Override
	public int batchInsert(List<ImFriendGroup> record) {
		return this.mapper.batchInsert(record,ImFriendGroup.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,ImFriendGroup.class);
	}

	@Override
	public int batchUpdate(List<ImFriendGroup> record) {
		return this.mapper.batchUpdate(record,ImFriendGroup.class);
	}

	@Override
	public ResultMessage add(HttpServletRequest request, ImFriendGroup record) {
		ImFriendGroup group=new ImFriendGroup();
		group.setMemberid(Long.valueOf(record.getMemberid().toString()));
		group.setGroupname(record.getGroupname());
		List<ImFriendGroup> list=this.selectByEntity(group);
		if(null!=list&&list.size()>0) return ResultMessage.failed("好友分组已存在");
		this.insertSelective(record);
		
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage edit(HttpServletRequest request, ImFriendGroup record) {
		ImFriendGroup group=new ImFriendGroup();
		group.setMemberid(Long.valueOf(record.getMemberid().toString()));
		group.setGroupname(record.getGroupname());
		List<ImFriendGroup> list=this.selectByEntity(group);
		if(null!=list&&list.size()>0) {
			for(ImFriendGroup g:list){
				if(g.getId().longValue()!=record.getId().longValue()){
					return ResultMessage.failed("好友分组已存在");
				}
			}
		}
		this.updateByPrimaryKeySelective(record);
		
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage delete(List<Object> ids) {
		if(null!=ids&&ids.size()>0){
			this.batchDelete(ids);
		}
		return ResultMessage.defaultSuccessMessage();
	}
}
