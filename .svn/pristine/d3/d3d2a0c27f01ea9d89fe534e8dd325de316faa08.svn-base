package com.meme.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meme.core.base.AbstractService;
import com.meme.core.dao.OrganDataAuthMapper;
import com.meme.core.form.Form;
import com.meme.core.mybatis.Criterion;
import com.meme.core.mybatis.MapperHelper;
import com.meme.core.mybatis.enums.Operator;
import com.meme.core.pojo.OrganDataAuth;
import com.meme.core.service.OrganDataAuthService;

@Service("OrganDataAuthService")
@Transactional
public class OrganDataAuthServiceImpl extends AbstractService implements OrganDataAuthService {

	@Resource
	private OrganDataAuthMapper mapper;

	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(OrganDataAuth record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(OrganDataAuth record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public OrganDataAuth selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(OrganDataAuth record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(OrganDataAuth record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrganDataAuth> selectAll(){
		return (List<OrganDataAuth>) MapperHelper.toBeanList(this.mapper.selectAll(OrganDataAuth.class), OrganDataAuth.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrganDataAuth> selectByPagination(Form form) {
		this.buildSearchCriterion(OrganDataAuth.class, form);
		this.buildOrderByCriterion(OrganDataAuth.class, form);
		this.buildLimitCriterion(OrganDataAuth.class, form);
		return (List<OrganDataAuth>) MapperHelper.toBeanList(this.mapper.selectByPagination(OrganDataAuth.class,this.getList()), OrganDataAuth.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(OrganDataAuth.class, form);
		return this.mapper.count(OrganDataAuth.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrganDataAuth> selectByEntity(OrganDataAuth record) {
		return (List<OrganDataAuth>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), OrganDataAuth.class);
	}

	@Override
	public int batchInsert(List<OrganDataAuth> record) {
		return this.mapper.batchInsert(record,OrganDataAuth.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,OrganDataAuth.class);
	}

	@Override
	public int batchUpdate(List<OrganDataAuth> record) {
		return this.mapper.batchUpdate(record,OrganDataAuth.class);
	}

	@Override
	public int deleteByMenuids(List<Object> list) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("menuid").operator(Operator.IN).leftValue(list).javaType(Long.class).build());
		return this.mapper.deleteByCriterions(criterions, OrganDataAuth.class);
	}

	@Override
	public int deleteByOrganids(List<Object> list) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("organid").operator(Operator.IN).leftValue(list).javaType(Long.class).build());
		return this.mapper.deleteByCriterions(criterions, OrganDataAuth.class);
	}

}
