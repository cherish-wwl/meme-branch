package com.meme.im.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.meme.core.base.AbstractService;
import com.meme.core.form.Form;
import com.meme.core.mybatis.MapperHelper;
import com.meme.im.dao.MemeLikeMapper;
import com.meme.im.form.ImForm;
import com.meme.im.pojo.MemeLike;
import com.meme.im.service.MemeLikeService;

@Service("MemeLikeService")
public class MemeLikeServiceImpl extends AbstractService implements MemeLikeService {

	@Resource private MemeLikeMapper mapper;
	
	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(MemeLike record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(MemeLike record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public MemeLike selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(MemeLike record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(MemeLike record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemeLike> selectAll(){
		return (List<MemeLike>) MapperHelper.toBeanList(this.mapper.selectAll(MemeLike.class), MemeLike.class);
	}

	public void buildSearchCriterion(Class<?> entityClass, Form form) {
		super.buildSearchCriterion(entityClass, form);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MemeLike> selectByPagination(Form form) {
		this.buildSearchCriterion(MemeLike.class, form);
		this.buildOrderByCriterion(MemeLike.class, form);
		this.buildLimitCriterion(MemeLike.class, form);
		return (List<MemeLike>) MapperHelper.toBeanList(this.mapper.selectByPagination(MemeLike.class,this.getList()), MemeLike.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(MemeLike.class, form);
		return this.mapper.count(MemeLike.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemeLike> selectByEntity(MemeLike record) {
		return (List<MemeLike>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), MemeLike.class);
	}

	@Override
	public int batchInsert(List<MemeLike> record) {
		return this.mapper.batchInsert(record,MemeLike.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,MemeLike.class);
	}

	@Override
	public int batchUpdate(List<MemeLike> record) {
		return this.mapper.batchUpdate(record,MemeLike.class);
	}
	

	@Override
	public List<Map<String, Object>> selectByPaginationView(ImForm form) {
		return this.mapper.selectByPaginationView(form);
	}
	
	@Override
	public int countView(ImForm form) {
		return this.mapper.countView(form);
	}
	
	
}
