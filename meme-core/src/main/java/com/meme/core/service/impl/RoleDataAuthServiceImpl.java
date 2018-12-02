package com.meme.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meme.core.base.AbstractService;
import com.meme.core.dao.RoleDataAuthMapper;
import com.meme.core.form.Form;
import com.meme.core.mybatis.Criterion;
import com.meme.core.mybatis.MapperHelper;
import com.meme.core.mybatis.enums.Operator;
import com.meme.core.pojo.RoleDataAuth;
import com.meme.core.service.RoleDataAuthService;

@Service("RoleDataAuthService")
@Transactional
public class RoleDataAuthServiceImpl extends AbstractService implements RoleDataAuthService {

	@Resource
	private RoleDataAuthMapper mapper;

	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(RoleDataAuth record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(RoleDataAuth record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public RoleDataAuth selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(RoleDataAuth record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(RoleDataAuth record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoleDataAuth> selectAll(){
		return (List<RoleDataAuth>) MapperHelper.toBeanList(this.mapper.selectAll(RoleDataAuth.class), RoleDataAuth.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoleDataAuth> selectByPagination(Form form) {
		this.buildSearchCriterion(RoleDataAuth.class, form);
		this.buildOrderByCriterion(RoleDataAuth.class, form);
		this.buildLimitCriterion(RoleDataAuth.class, form);
		return (List<RoleDataAuth>) MapperHelper.toBeanList(this.mapper.selectByPagination(RoleDataAuth.class,this.getList()), RoleDataAuth.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(RoleDataAuth.class, form);
		return this.mapper.count(RoleDataAuth.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoleDataAuth> selectByEntity(RoleDataAuth record) {
		return (List<RoleDataAuth>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), RoleDataAuth.class);
	}

	@Override
	public int batchInsert(List<RoleDataAuth> record) {
		return this.mapper.batchInsert(record,RoleDataAuth.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,RoleDataAuth.class);
	}

	@Override
	public int batchUpdate(List<RoleDataAuth> record) {
		return this.mapper.batchUpdate(record,RoleDataAuth.class);
	}

	@Override
	public int deleteByMenuids(List<Object> list) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("menuid").operator(Operator.IN).leftValue(list).javaType(Long.class).build());
		return this.mapper.deleteByCriterions(criterions, RoleDataAuth.class);
	}

	@Override
	public int deleteByOrganids(List<Object> list) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("organid").operator(Operator.IN).leftValue(list).javaType(Long.class).build());
		return this.mapper.deleteByCriterions(criterions, RoleDataAuth.class);
	}

	@Override
	public int deleteByroleids(List<Object> list) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("roleid").operator(Operator.IN).leftValue(list).javaType(Long.class).build());
		return this.mapper.deleteByCriterions(criterions, RoleDataAuth.class);
	}

}
