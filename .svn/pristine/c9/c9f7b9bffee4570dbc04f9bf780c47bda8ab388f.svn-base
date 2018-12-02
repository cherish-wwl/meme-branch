package com.meme.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meme.core.base.AbstractService;
import com.meme.core.dao.LoginAccountInfoMapper;
import com.meme.core.form.Form;
import com.meme.core.mybatis.Criterion;
import com.meme.core.mybatis.MapperHelper;
import com.meme.core.mybatis.enums.Operator;
import com.meme.core.pojo.LoginAccountInfo;
import com.meme.core.service.LoginAccountInfoService;

@Service("LoginAccountInfoService")
@Transactional
public class LoginAccountInfoServiceImpl extends AbstractService implements LoginAccountInfoService {

	@Resource
	private LoginAccountInfoMapper mapper;

	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(LoginAccountInfo record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(LoginAccountInfo record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public LoginAccountInfo selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(LoginAccountInfo record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(LoginAccountInfo record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoginAccountInfo> selectAll(){
		return (List<LoginAccountInfo>) MapperHelper.toBeanList(this.mapper.selectAll(LoginAccountInfo.class), LoginAccountInfo.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoginAccountInfo> selectByPagination(Form form) {
		this.buildSearchCriterion(LoginAccountInfo.class, form);
		this.buildOrderByCriterion(LoginAccountInfo.class, form);
		this.buildLimitCriterion(LoginAccountInfo.class, form);
		return (List<LoginAccountInfo>) MapperHelper.toBeanList(this.mapper.selectByPagination(LoginAccountInfo.class,this.getList()), LoginAccountInfo.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(LoginAccountInfo.class, form);
		return this.mapper.count(LoginAccountInfo.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoginAccountInfo> selectByEntity(LoginAccountInfo record) {
		return (List<LoginAccountInfo>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), LoginAccountInfo.class);
	}

	@Override
	public int batchInsert(List<LoginAccountInfo> record) {
		return this.mapper.batchInsert(record,LoginAccountInfo.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,LoginAccountInfo.class);
	}

	@Override
	public int batchUpdate(List<LoginAccountInfo> record) {
		return this.mapper.batchUpdate(record,LoginAccountInfo.class);
	}

	@Override
	public int deleteByOrganids(List<Object> list) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("organid").operator(Operator.IN).leftValue(list).javaType(Long.class).build());
		return this.mapper.deleteByCriterions(criterions, LoginAccountInfo.class);
	}

	@Override
	public int deleteByDepartmentids(List<Object> list) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("deptid").operator(Operator.IN).leftValue(list).javaType(Long.class).build());
		return this.mapper.deleteByCriterions(criterions, LoginAccountInfo.class);
	}

	@Override
	public int deleteByPositionids(List<Object> list) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("postid").operator(Operator.IN).leftValue(list).javaType(Long.class).build());
		return this.mapper.deleteByCriterions(criterions, LoginAccountInfo.class);
	}

}
