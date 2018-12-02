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
import com.meme.core.util.IDGenerator;
import com.meme.im.dao.MemberBgTemMapper;
import com.meme.im.pojo.Member;
import com.meme.im.pojo.MemberBgTem;
import com.meme.im.service.MemberBgTemService;

@Service("MemberBgTemService")
@Transactional
public class MemberBgTemServiceImpl extends AbstractService implements MemberBgTemService {

	@Resource private MemberBgTemMapper mapper;
	
	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(MemberBgTem record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(MemberBgTem record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public MemberBgTem selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(MemberBgTem record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(MemberBgTem record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemberBgTem> selectAll(){
		return (List<MemberBgTem>) MapperHelper.toBeanList(this.mapper.selectAll(MemberBgTem.class), MemberBgTem.class);
	}

	public void buildSearchCriterion(Class<?> entityClass, Form form) {
		super.buildSearchCriterion(entityClass, form);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MemberBgTem> selectByPagination(Form form) {
		this.buildSearchCriterion(Member.class, form);
		this.buildOrderByCriterion(MemberBgTem.class, form);
		this.buildLimitCriterion(MemberBgTem.class, form);
		return (List<MemberBgTem>) MapperHelper.toBeanList(this.mapper.selectByPagination(MemberBgTem.class,this.getList()), MemberBgTem.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(MemberBgTem.class, form);
		return this.mapper.count(MemberBgTem.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemberBgTem> selectByEntity(MemberBgTem record) {
		return (List<MemberBgTem>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), MemberBgTem.class);
	}

	@Override
	public int batchInsert(List<MemberBgTem> record) {
		return this.mapper.batchInsert(record,MemberBgTem.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,MemberBgTem.class);
	}

	@Override
	public int batchUpdate(List<MemberBgTem> record) {
		return this.mapper.batchUpdate(record,MemberBgTem.class);
	}

	@Override
	public ResultMessage add(HttpServletRequest request, MemberBgTem record) {
		record.setTempid(String.valueOf(IDGenerator.generateId()));
		this.insertSelective(record);
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage edit(HttpServletRequest request, MemberBgTem record) {
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

	@Override
	public List<MemberBgTem> getBgTemplates() {
		return this.mapper.getBgTemplates();
	}
}
