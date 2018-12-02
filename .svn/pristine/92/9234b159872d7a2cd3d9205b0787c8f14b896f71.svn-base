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
import com.meme.im.dao.VisitLogMapper;
import com.meme.im.pojo.VisitLog;
import com.meme.im.service.VisitLogService;

@Service("VisitLogService")
@Transactional
public class VisitLogServiceImpl extends AbstractService implements VisitLogService {

	@Resource private VisitLogMapper mapper;
	
	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(VisitLog record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(VisitLog record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public VisitLog selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(VisitLog record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(VisitLog record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VisitLog> selectAll(){
		return (List<VisitLog>) MapperHelper.toBeanList(this.mapper.selectAll(VisitLog.class), VisitLog.class);
	}

	public void buildSearchCriterion(Class<?> entityClass, Form form) {
		super.buildSearchCriterion(entityClass, form);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<VisitLog> selectByPagination(Form form) {
		this.buildSearchCriterion(VisitLog.class, form);
		this.buildOrderByCriterion(VisitLog.class, form);
		this.buildLimitCriterion(VisitLog.class, form);
		return (List<VisitLog>) MapperHelper.toBeanList(this.mapper.selectByPagination(VisitLog.class,this.getList()), VisitLog.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(VisitLog.class, form);
		return this.mapper.count(VisitLog.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VisitLog> selectByEntity(VisitLog record) {
		return (List<VisitLog>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), VisitLog.class);
	}

	@Override
	public int batchInsert(List<VisitLog> record) {
		return this.mapper.batchInsert(record,VisitLog.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,VisitLog.class);
	}

	@Override
	public int batchUpdate(List<VisitLog> record) {
		return this.mapper.batchUpdate(record,VisitLog.class);
	}

	@Override
	public ResultMessage add(HttpServletRequest request, VisitLog record) {
		return null;
	}

	@Override
	public ResultMessage edit(HttpServletRequest request, VisitLog record) {
		return null;
	}

	@Override
	public ResultMessage delete(List<Object> ids) {
		return null;
	}
}
