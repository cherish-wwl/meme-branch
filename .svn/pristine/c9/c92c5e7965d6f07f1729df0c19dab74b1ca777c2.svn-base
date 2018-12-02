package com.meme.im.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meme.core.base.AbstractService;
import com.meme.core.form.Form;
import com.meme.core.mybatis.MapperHelper;
import com.meme.im.dao.MemeWbShopMapper;
import com.meme.im.form.ImForm;
import com.meme.im.pojo.MemeWbShop;
import com.meme.im.service.MemeWbShopService;

@Service("MemeWbShopService")
@Transactional
public class MemeWbShopServiceImpl extends AbstractService implements MemeWbShopService {

	@Resource private MemeWbShopMapper mapper;
	
	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(MemeWbShop memeShop) {
		return this.mapper.insert(memeShop);
	}

	@Override
	public int insertSelective(MemeWbShop memeShop) {
		return this.mapper.insertSelective(memeShop);
	}

	@Override
	public MemeWbShop selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(MemeWbShop record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(MemeWbShop record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemeWbShop> selectAll(){
		return (List<MemeWbShop>) MapperHelper.toBeanList(this.mapper.selectAll(MemeWbShop.class), MemeWbShop.class);
	}

	public void buildSearchCriterion(Class<?> entityClass, Form form) {
		super.buildSearchCriterion(entityClass, form);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MemeWbShop> selectByPagination(Form form) {
		this.buildSearchCriterion(MemeWbShop.class, form);
		this.buildOrderByCriterion(MemeWbShop.class, form);
		this.buildLimitCriterion(MemeWbShop.class, form);
		return (List<MemeWbShop>) MapperHelper.toBeanList(this.mapper.selectByPagination(MemeWbShop.class,this.getList()), MemeWbShop.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(MemeWbShop.class, form);
		return this.mapper.count(MemeWbShop.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemeWbShop> selectByEntity(MemeWbShop record) {
		return (List<MemeWbShop>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), MemeWbShop.class);
	}

	@Override
	public int batchInsert(List<MemeWbShop> record) {
		return this.mapper.batchInsert(record,MemeWbShop.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,MemeWbShop.class);
	}

	@Override
	public int batchUpdate(List<MemeWbShop> record) {
		return this.mapper.batchUpdate(record,MemeWbShop.class);
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
