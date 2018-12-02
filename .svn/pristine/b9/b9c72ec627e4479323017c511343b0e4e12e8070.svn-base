package com.meme.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meme.core.base.AbstractService;
import com.meme.core.dao.OrganOperationMapper;
import com.meme.core.form.Form;
import com.meme.core.mybatis.Criterion;
import com.meme.core.mybatis.MapperHelper;
import com.meme.core.mybatis.enums.Operator;
import com.meme.core.pojo.OrganOperation;
import com.meme.core.service.OrganOperationService;

@Service("OrganOperationService")
@Transactional
public class OrganOperationServiceImpl extends AbstractService implements OrganOperationService {

	@Resource
	private OrganOperationMapper mapper;

	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(OrganOperation record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(OrganOperation record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public OrganOperation selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(OrganOperation record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(OrganOperation record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrganOperation> selectAll(){
		return (List<OrganOperation>) MapperHelper.toBeanList(this.mapper.selectAll(OrganOperation.class), OrganOperation.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrganOperation> selectByPagination(Form form) {
		this.buildSearchCriterion(OrganOperation.class, form);
		this.buildOrderByCriterion(OrganOperation.class, form);
		this.buildLimitCriterion(OrganOperation.class, form);
		return (List<OrganOperation>) MapperHelper.toBeanList(this.mapper.selectByPagination(OrganOperation.class,this.getList()), OrganOperation.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(OrganOperation.class, form);
		return this.mapper.count(OrganOperation.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrganOperation> selectByEntity(OrganOperation record) {
		return (List<OrganOperation>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), OrganOperation.class);
	}

	@Override
	public int batchInsert(List<OrganOperation> record) {
		return this.mapper.batchInsert(record,OrganOperation.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,OrganOperation.class);
	}

	@Override
	public int batchUpdate(List<OrganOperation> record) {
		return this.mapper.batchUpdate(record,OrganOperation.class);
	}

	@Override
	public int deleteByMenuids(Object organid,List<Object> list) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("organid").operator(Operator.EQUAL).leftValue(organid).javaType(Long.class).build());
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("menuid").operator(Operator.IN).leftValue(list).javaType(Long.class).build());
		return this.mapper.deleteByCriterions(criterions, OrganOperation.class);
	}

	@Override
	public int deleteByOrganids(List<Object> list) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("organid").operator(Operator.IN).leftValue(list).javaType(Long.class).build());
		return this.mapper.deleteByCriterions(criterions, OrganOperation.class);
	}

	@Override
	public int deleteByPlatformids(Object organid,List<Object> list) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("organid").operator(Operator.EQUAL).leftValue(organid).javaType(Long.class).build());
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("platformid").operator(Operator.IN).leftValue(list).javaType(Long.class).build());
		return this.mapper.deleteByCriterions(criterions, OrganOperation.class);
	}

	@Override
	public int deleteByOperationids(Object organid,List<Object> list) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("organid").operator(Operator.EQUAL).leftValue(organid).javaType(Long.class).build());
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("operationid").operator(Operator.IN).leftValue(list).javaType(Long.class).build());
		return this.mapper.deleteByCriterions(criterions, OrganOperation.class);
	}

}
