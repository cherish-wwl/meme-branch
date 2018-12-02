package com.meme.im.service.impl;

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
import com.meme.im.dao.ImFriendMapper;
import com.meme.im.pojo.ImFriend;
import com.meme.im.service.ImFriendService;

@Service("ImFriendService")
@Transactional
public class ImFriendServiceImpl extends AbstractService implements ImFriendService {

	@Resource private ImFriendMapper mapper;
	
	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(ImFriend record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(ImFriend record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public ImFriend selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(ImFriend record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(ImFriend record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ImFriend> selectAll(){
		return (List<ImFriend>) MapperHelper.toBeanList(this.mapper.selectAll(ImFriend.class), ImFriend.class);
	}

	public void buildSearchCriterion(Class<?> entityClass, Form form) {
		super.buildSearchCriterion(entityClass, form);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ImFriend> selectByPagination(Form form) {
		this.buildSearchCriterion(ImFriend.class, form);
		this.buildOrderByCriterion(ImFriend.class, form);
		this.buildLimitCriterion(ImFriend.class, form);
		return (List<ImFriend>) MapperHelper.toBeanList(this.mapper.selectByPagination(ImFriend.class,this.getList()), ImFriend.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(ImFriend.class, form);
		return this.mapper.count(ImFriend.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ImFriend> selectByEntity(ImFriend record) {
		return (List<ImFriend>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), ImFriend.class);
	}

	@Override
	public int batchInsert(List<ImFriend> record) {
		return this.mapper.batchInsert(record,ImFriend.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,ImFriend.class);
	}

	@Override
	public int batchUpdate(List<ImFriend> record) {
		return this.mapper.batchUpdate(record,ImFriend.class);
	}

	@Override
	public ResultMessage add(HttpServletRequest request, ImFriend record) {
		return null;
	}

	@Override
	public ResultMessage edit(HttpServletRequest request, ImFriend record) {
		return null;
	}

	@Override
	public ResultMessage delete(List<Object> ids) {
		return null;
	}

	@Override
	public List<Map<String, Object>> selectFriends(Object memberid) {
		return this.mapper.selectFriends(memberid);
	}

	@Override
	public List<Map<String, Object>> selectStrangersByPagination(Form form) {
		return this.mapper.selectStrangersByPagination(form);
	}

	@Override
	public int countStrangers(Form form) {
		return this.mapper.countStrangers(form);
	}

	@Override
	public List<Map<String, Object>> selectFriend(Object memberid,
			Object friendid) {
		return this.mapper.selectFriend(memberid, friendid);
	}

	@Override
	public int delFriend(Object mymemberid, Object friendid) {
		return this.mapper.delFriend(mymemberid, friendid);
	}

}
