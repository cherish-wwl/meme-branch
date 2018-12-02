package com.meme.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meme.core.base.AbstractService;
import com.meme.core.dao.RoleOperationMapper;
import com.meme.core.form.Form;
import com.meme.core.mybatis.Criterion;
import com.meme.core.mybatis.MapperHelper;
import com.meme.core.mybatis.enums.Operator;
import com.meme.core.pojo.RoleOperation;
import com.meme.core.service.RoleOperationService;

@Service("RoleOperationService")
@Transactional
public class RoleOperationServiceImpl extends AbstractService implements RoleOperationService {

	@Resource
	private RoleOperationMapper mapper;

	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(RoleOperation record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(RoleOperation record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public RoleOperation selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(RoleOperation record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(RoleOperation record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoleOperation> selectAll(){
		return (List<RoleOperation>) MapperHelper.toBeanList(this.mapper.selectAll(RoleOperation.class), RoleOperation.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoleOperation> selectByPagination(Form form) {
		this.buildSearchCriterion(RoleOperation.class, form);
		this.buildOrderByCriterion(RoleOperation.class, form);
		this.buildLimitCriterion(RoleOperation.class, form);
		return (List<RoleOperation>) MapperHelper.toBeanList(this.mapper.selectByPagination(RoleOperation.class,this.getList()), RoleOperation.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(RoleOperation.class, form);
		return this.mapper.count(RoleOperation.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoleOperation> selectByEntity(RoleOperation record) {
		return (List<RoleOperation>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), RoleOperation.class);
	}

	@Override
	public int batchInsert(List<RoleOperation> record) {
		return this.mapper.batchInsert(record,RoleOperation.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,RoleOperation.class);
	}

	@Override
	public int batchUpdate(List<RoleOperation> record) {
		return this.mapper.batchUpdate(record,RoleOperation.class);
	}

	@Override
	public int deleteByMenuIds(Object roleid,List<Object> list) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("roleid").operator(Operator.EQUAL).leftValue(roleid).javaType(Long.class).build());
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("menuid").operator(Operator.IN).leftValue(list).javaType(Long.class).build());
		return this.mapper.deleteByCriterions(criterions, RoleOperation.class);
	}

	@Override
	public int deleteByOrganids(List<Object> list) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("organid").operator(Operator.IN).leftValue(list).javaType(Long.class).build());
		return this.mapper.deleteByCriterions(criterions, RoleOperation.class);
	}

	@Override
	public int deleteByroleids(List<Object> list) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("roleid").operator(Operator.IN).leftValue(list).javaType(Long.class).build());
		return this.mapper.deleteByCriterions(criterions, RoleOperation.class);
	}

	@Override
	public int deleteByPlatformids(Object roleid,List<Object> list) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		if(null!=roleid&&Long.valueOf(roleid.toString())!=0L){
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			criterions.add(new Criterion.CriterionBuilder().condition("roleid").operator(Operator.EQUAL).leftValue(roleid).javaType(Long.class).build());
		}
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("platformid").operator(Operator.IN).leftValue(list).javaType(Long.class).build());
		return this.mapper.deleteByCriterions(criterions, RoleOperation.class);
	}

	@Override
	public int deleteByOperationids(Object roleid,List<Object> list) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("roleid").operator(Operator.EQUAL).leftValue(roleid).javaType(Long.class).build());
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("operationid").operator(Operator.IN).leftValue(list).javaType(Long.class).build());
		return this.mapper.deleteByCriterions(criterions, RoleOperation.class);
	}

}
