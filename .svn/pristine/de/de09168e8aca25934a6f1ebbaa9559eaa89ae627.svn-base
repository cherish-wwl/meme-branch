package com.meme.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meme.core.base.AbstractService;
import com.meme.core.dao.OperationMapper;
import com.meme.core.form.Form;
import com.meme.core.mybatis.Criterion;
import com.meme.core.mybatis.MapperHelper;
import com.meme.core.mybatis.enums.Operator;
import com.meme.core.pojo.Operation;
import com.meme.core.service.OperationService;

@Service("OperationService")
@Transactional
public class OperationServiceImpl extends AbstractService implements OperationService {

	@Resource
	private OperationMapper mapper;

	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Operation record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(Operation record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public Operation selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Operation record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Operation record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Operation> selectAll(){
		return (List<Operation>) MapperHelper.toBeanList(this.mapper.selectAll(Operation.class), Operation.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Operation> selectByPagination(Form form) {
		this.buildSearchCriterion(Operation.class, form);
		this.buildOrderByCriterion(Operation.class, form);
		this.buildLimitCriterion(Operation.class, form);
		return (List<Operation>) MapperHelper.toBeanList(this.mapper.selectByPagination(Operation.class,this.getList()), Operation.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(Operation.class, form);
		return this.mapper.count(Operation.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Operation> selectByEntity(Operation record) {
		return (List<Operation>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), Operation.class);
	}

	@Override
	public int batchInsert(List<Operation> record) {
		return this.mapper.batchInsert(record,Operation.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,Operation.class);
	}

	@Override
	public int batchUpdate(List<Operation> record) {
		return this.mapper.batchUpdate(record,Operation.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Operation> selectByMenuids(List<Object> list) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		if(null!=list&&list.size()>0){
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			criterions.add(new Criterion.CriterionBuilder().condition("menuid").operator(Operator.IN).leftValue(list).javaType(Long.class).build());
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.ORDER_BY).condition("sortno").leftValue("ASC").build());
		}
		return (List<Operation>) MapperHelper.toBeanList(this.mapper.selectByCriterions(criterions, Operation.class), Operation.class);
	}

	@Override
	public int deleteByMenuids(List<Object> list) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("menuid").operator(Operator.IN).leftValue(list).javaType(Long.class).build());
		return this.mapper.deleteByCriterions(criterions, Operation.class);
	}

	@Override
	public int deleteByPlatformids(List<Object> list) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("platformid").operator(Operator.IN).leftValue(list).javaType(Long.class).build());
		return this.mapper.deleteByCriterions(criterions, Operation.class);
	}

	@Override
	public List<Operation> selectOrganOperations(Object organid, List<Object> menuids, List<Object> platformids) {
		return this.mapper.selectOrganOperations(organid, menuids, platformids);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Operation> selectByids(List<Object> list) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("id").operator(Operator.IN).leftValue(list).javaType(Long.class).build());
		return (List<Operation>) MapperHelper.toBeanList(this.mapper.selectByCriterions(criterions, Operation.class), Operation.class);
	}

	@Override
	public List<Operation> selectRoleOperations(List<Object> roleids, List<Object> menuids, List<Object> platformids) {
		return this.mapper.selectRoleOperations(roleids, menuids, platformids);
	}
}
