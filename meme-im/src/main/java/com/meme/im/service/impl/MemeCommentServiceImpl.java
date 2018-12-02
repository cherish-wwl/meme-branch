package com.meme.im.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.meme.core.base.AbstractService;
import com.meme.core.form.Form;
import com.meme.core.mybatis.MapperHelper;
import com.meme.im.dao.MemeCommentMapper;
import com.meme.im.form.ImForm;
import com.meme.im.pojo.MemeComment;
import com.meme.im.service.MemeCommentService;

@Service("MemeCommentService")
public class MemeCommentServiceImpl extends AbstractService implements MemeCommentService {

	@Resource private MemeCommentMapper mapper;
	
	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(MemeComment record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(MemeComment record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public MemeComment selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(MemeComment record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(MemeComment record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemeComment> selectAll(){
		return (List<MemeComment>) MapperHelper.toBeanList(this.mapper.selectAll(MemeComment.class), MemeComment.class);
	}

	public void buildSearchCriterion(Class<?> entityClass, Form form) {
		super.buildSearchCriterion(entityClass, form);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MemeComment> selectByPagination(Form form) {
		this.buildSearchCriterion(MemeComment.class, form);
		this.buildOrderByCriterion(MemeComment.class, form);
		this.buildLimitCriterion(MemeComment.class, form);
		return (List<MemeComment>) MapperHelper.toBeanList(this.mapper.selectByPagination(MemeComment.class,this.getList()), MemeComment.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(MemeComment.class, form);
		return this.mapper.count(MemeComment.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemeComment> selectByEntity(MemeComment record) {
		return (List<MemeComment>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), MemeComment.class);
	}

	@Override
	public int batchInsert(List<MemeComment> record) {
		return this.mapper.batchInsert(record,MemeComment.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,MemeComment.class);
	}

	@Override
	public int batchUpdate(List<MemeComment> record) {
		return this.mapper.batchUpdate(record,MemeComment.class);
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
