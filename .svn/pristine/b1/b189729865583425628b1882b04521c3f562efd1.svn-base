package com.meme.im.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.meme.core.base.AbstractService;
import com.meme.core.form.Form;
import com.meme.core.mybatis.MapperHelper;
import com.meme.im.dao.MemeHomePageMapper;
import com.meme.im.form.ImForm;
import com.meme.im.pojo.MemeHomePage;
import com.meme.im.service.MemeHomePageService;

@Service("MemeHomePageService")
public class MemeHomePageServiceImpl extends AbstractService implements MemeHomePageService {

	@Resource private MemeHomePageMapper mapper;
	
	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(MemeHomePage record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(MemeHomePage record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public MemeHomePage selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(MemeHomePage record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(MemeHomePage record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemeHomePage> selectAll(){
		return (List<MemeHomePage>) MapperHelper.toBeanList(this.mapper.selectAll(MemeHomePage.class), MemeHomePage.class);
	}

	public void buildSearchCriterion(Class<?> entityClass, Form form) {
		super.buildSearchCriterion(entityClass, form);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MemeHomePage> selectByPagination(Form form) {
		this.buildSearchCriterion(MemeHomePage.class, form);
		this.buildOrderByCriterion(MemeHomePage.class, form);
		this.buildLimitCriterion(MemeHomePage.class, form);
		return (List<MemeHomePage>) MapperHelper.toBeanList(this.mapper.selectByPagination(MemeHomePage.class,this.getList()), MemeHomePage.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(MemeHomePage.class, form);
		return this.mapper.count(MemeHomePage.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemeHomePage> selectByEntity(MemeHomePage record) {
		return (List<MemeHomePage>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), MemeHomePage.class);
	}

	@Override
	public int batchInsert(List<MemeHomePage> record) {
		return this.mapper.batchInsert(record,MemeHomePage.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,MemeHomePage.class);
	}

	@Override
	public int batchUpdate(List<MemeHomePage> record) {
		return this.mapper.batchUpdate(record,MemeHomePage.class);
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
