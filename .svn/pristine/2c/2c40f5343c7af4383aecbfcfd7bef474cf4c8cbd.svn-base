package com.meme.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meme.core.base.AbstractService;
import com.meme.core.dao.RoleMenuMapper;
import com.meme.core.form.Form;
import com.meme.core.mybatis.Criterion;
import com.meme.core.mybatis.MapperHelper;
import com.meme.core.mybatis.enums.Operator;
import com.meme.core.pojo.RoleMenu;
import com.meme.core.service.RoleMenuService;

@Service("RoleMenuService")
@Transactional
public class RoleMenuServiceImpl extends AbstractService implements RoleMenuService {

	@Resource
	private RoleMenuMapper mapper;

	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(RoleMenu record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(RoleMenu record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public RoleMenu selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(RoleMenu record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(RoleMenu record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoleMenu> selectAll(){
		return (List<RoleMenu>) MapperHelper.toBeanList(this.mapper.selectAll(RoleMenu.class), RoleMenu.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoleMenu> selectByPagination(Form form) {
		this.buildSearchCriterion(RoleMenu.class, form);
		this.buildOrderByCriterion(RoleMenu.class, form);
		this.buildLimitCriterion(RoleMenu.class, form);
		return (List<RoleMenu>) MapperHelper.toBeanList(this.mapper.selectByPagination(RoleMenu.class,this.getList()), RoleMenu.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(RoleMenu.class, form);
		return this.mapper.count(RoleMenu.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoleMenu> selectByEntity(RoleMenu record) {
		return (List<RoleMenu>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), RoleMenu.class);
	}

	@Override
	public int batchInsert(List<RoleMenu> record) {
		return this.mapper.batchInsert(record,RoleMenu.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,RoleMenu.class);
	}

	@Override
	public int batchUpdate(List<RoleMenu> record) {
		return this.mapper.batchUpdate(record,RoleMenu.class);
	}

	@Override
	public int deleteByMenuIds(Object roleid,List<Object> list) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("roleid").operator(Operator.EQUAL).leftValue(roleid).javaType(Long.class).build());
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("menuid").operator(Operator.IN).leftValue(list).javaType(Long.class).build());
		return this.mapper.deleteByCriterions(criterions, RoleMenu.class);
	}

	@Override
	public int deleteByOrganids(List<Object> list) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("organid").operator(Operator.IN).leftValue(list).javaType(Long.class).build());
		return this.mapper.deleteByCriterions(criterions, RoleMenu.class);
	}

	@Override
	public int deleteByroleids(List<Object> list) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("roleid").operator(Operator.IN).leftValue(list).javaType(Long.class).build());
		return this.mapper.deleteByCriterions(criterions, RoleMenu.class);
	}

	@Override
	public int deleteByPlatformids(Object roleid,List<Object> list) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("roleid").operator(Operator.EQUAL).leftValue(roleid).javaType(Long.class).build());
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("platformid").operator(Operator.IN).leftValue(list).javaType(Long.class).build());
		return this.mapper.deleteByCriterions(criterions, RoleMenu.class);
	}

	@Override
	public int deleteMenuByOrganid(Object organid, List<Object> menuids) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("organid").operator(Operator.EQUAL).leftValue(organid).javaType(Long.class).build());
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("menuid").operator(Operator.IN).leftValue(menuids).javaType(Long.class).build());
		return this.mapper.deleteByCriterions(criterions, RoleMenu.class);
	}

}
