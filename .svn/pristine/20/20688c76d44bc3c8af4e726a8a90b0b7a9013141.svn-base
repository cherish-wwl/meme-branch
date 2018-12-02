package com.meme.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meme.core.base.AbstractService;
import com.meme.core.dao.LoginAccountRoleMapper;
import com.meme.core.form.Form;
import com.meme.core.mybatis.Criterion;
import com.meme.core.mybatis.MapperHelper;
import com.meme.core.mybatis.enums.Operator;
import com.meme.core.pojo.LoginAccountRole;
import com.meme.core.pojo.LoginAccountRoleView;
import com.meme.core.service.LoginAccountRoleService;

@Service("LoginAccountRoleService")
@Transactional
public class LoginAccountRoleServiceImpl extends AbstractService implements LoginAccountRoleService {

	@Resource
	private LoginAccountRoleMapper mapper;

	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(LoginAccountRole record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(LoginAccountRole record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public LoginAccountRole selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(LoginAccountRole record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(LoginAccountRole record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoginAccountRole> selectAll(){
		return (List<LoginAccountRole>) MapperHelper.toBeanList(this.mapper.selectAll(LoginAccountRole.class), LoginAccountRole.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoginAccountRole> selectByPagination(Form form) {
		this.buildSearchCriterion(LoginAccountRole.class, form);
		this.buildOrderByCriterion(LoginAccountRole.class, form);
		this.buildLimitCriterion(LoginAccountRole.class, form);
		return (List<LoginAccountRole>) MapperHelper.toBeanList(this.mapper.selectByPagination(LoginAccountRole.class,this.getList()), LoginAccountRole.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(LoginAccountRole.class, form);
		return this.mapper.count(LoginAccountRole.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoginAccountRole> selectByEntity(LoginAccountRole record) {
		return (List<LoginAccountRole>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), LoginAccountRole.class);
	}

	@Override
	public int batchInsert(List<LoginAccountRole> record) {
		return this.mapper.batchInsert(record,LoginAccountRole.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,LoginAccountRole.class);
	}

	@Override
	public int batchUpdate(List<LoginAccountRole> record) {
		return this.mapper.batchUpdate(record,LoginAccountRole.class);
	}

	@Override
	public List<LoginAccountRoleView> selectByLoginId(Long loginid) {
		return this.mapper.selectByLoginId(loginid);
	}

	@Override
	public List<LoginAccountRoleView> selectByLoginIds(List<Object> ids) {
		return this.mapper.selectByLoginIds(ids);
	}

	@Override
	public int deleteByOrganids(List<Object> list) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("organid").operator(Operator.IN).leftValue(list).javaType(Long.class).build());
		return this.mapper.deleteByCriterions(criterions, LoginAccountRole.class);
	}

	@Override
	public int deleteByroleids(Object loginid,List<Object> list) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		if(null!=loginid){
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			criterions.add(new Criterion.CriterionBuilder().condition("loginid").operator(Operator.EQUAL).leftValue(loginid).javaType(Long.class).build());
		}
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("roleid").operator(Operator.IN).leftValue(list).javaType(Long.class).build());
		return this.mapper.deleteByCriterions(criterions, LoginAccountRole.class);
	}

	@Override
	public int deleteByLoginids(List<Object> list) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("loginid").operator(Operator.IN).leftValue(list).javaType(Long.class).build());
		return this.mapper.deleteByCriterions(criterions, LoginAccountRole.class);
	}

}
