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
import com.meme.im.dao.MemeShopMapper;
import com.meme.im.form.ImForm;
import com.meme.im.pojo.Member;
import com.meme.im.pojo.MemeOrder;
import com.meme.im.pojo.MemeShop;
import com.meme.im.service.MemeShopService;

@Service("MemeShopService")
@Transactional
public class MemeShopServiceImpl extends AbstractService implements MemeShopService {

	@Resource private MemeShopMapper mapper;
	
	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(MemeShop memeShop) {
		return this.mapper.insert(memeShop);
	}

	@Override
	public int insertSelective(MemeShop memeShop) {
		return this.mapper.insertSelective(memeShop);
	}

	@Override
	public MemeShop selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(MemeShop record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(MemeShop record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemeShop> selectAll(){
		return (List<MemeShop>) MapperHelper.toBeanList(this.mapper.selectAll(MemeShop.class), MemeShop.class);
	}

	public void buildSearchCriterion(Class<?> entityClass, Form form) {
		super.buildSearchCriterion(entityClass, form);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MemeShop> selectByPagination(Form form) {
		this.buildSearchCriterion(MemeShop.class, form);
		this.buildOrderByCriterion(MemeShop.class, form);
		this.buildLimitCriterion(MemeShop.class, form);
		return (List<MemeShop>) MapperHelper.toBeanList(this.mapper.selectByPagination(MemeShop.class,this.getList()), MemeShop.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(MemeShop.class, form);
		return this.mapper.count(MemeShop.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemeShop> selectByEntity(MemeShop record) {
		return (List<MemeShop>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), MemeShop.class);
	}

	@Override
	public int batchInsert(List<MemeShop> record) {
		return this.mapper.batchInsert(record,MemeShop.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,MemeShop.class);
	}

	@Override
	public int batchUpdate(List<MemeShop> record) {
		return this.mapper.batchUpdate(record,MemeShop.class);
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
