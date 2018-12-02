package com.meme.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meme.core.base.AbstractService;
import com.meme.core.dao.OrganMenuMapper;
import com.meme.core.form.Form;
import com.meme.core.mybatis.Criterion;
import com.meme.core.mybatis.MapperHelper;
import com.meme.core.mybatis.enums.Operator;
import com.meme.core.pojo.OrganMenu;
import com.meme.core.service.OrganMenuService;

@Service("OrganMenuService")
@Transactional
public class OrganMenuServiceImpl extends AbstractService implements OrganMenuService {

	@Resource
	private OrganMenuMapper mapper;

	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(OrganMenu record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(OrganMenu record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public OrganMenu selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(OrganMenu record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(OrganMenu record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrganMenu> selectAll(){
		return (List<OrganMenu>) MapperHelper.toBeanList(this.mapper.selectAll(OrganMenu.class), OrganMenu.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrganMenu> selectByPagination(Form form) {
		this.buildSearchCriterion(OrganMenu.class, form);
		this.buildOrderByCriterion(OrganMenu.class, form);
		this.buildLimitCriterion(OrganMenu.class, form);
		return (List<OrganMenu>) MapperHelper.toBeanList(this.mapper.selectByPagination(OrganMenu.class,this.getList()), OrganMenu.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(OrganMenu.class, form);
		return this.mapper.count(OrganMenu.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrganMenu> selectByEntity(OrganMenu record) {
		return (List<OrganMenu>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), OrganMenu.class);
	}

	@Override
	public int batchInsert(List<OrganMenu> record) {
		return this.mapper.batchInsert(record,OrganMenu.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,OrganMenu.class);
	}

	@Override
	public int batchUpdate(List<OrganMenu> record) {
		return this.mapper.batchUpdate(record,OrganMenu.class);
	}

	@Override
	public int deleteByMenuids(Object organid,List<Object> list) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("organid").operator(Operator.EQUAL).leftValue(organid).javaType(Long.class).build());
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("menuid").operator(Operator.IN).leftValue(list).javaType(Long.class).build());
		return this.mapper.deleteByCriterions(criterions, OrganMenu.class);
	}

	@Override
	public int deleteByOrganids(List<Object> list) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("organid").operator(Operator.IN).leftValue(list).javaType(Long.class).build());
		return this.mapper.deleteByCriterions(criterions, OrganMenu.class);
	}

	@Override
	public int deleteByPlatformids(Object organid,List<Object> list) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		if(null!=organid&&Long.valueOf(organid.toString())!=0L){
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			criterions.add(new Criterion.CriterionBuilder().condition("organid").operator(Operator.EQUAL).leftValue(organid).javaType(Long.class).build());
		}
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("platformid").operator(Operator.IN).leftValue(list).javaType(Long.class).build());
		return this.mapper.deleteByCriterions(criterions, OrganMenu.class);
	}
}
