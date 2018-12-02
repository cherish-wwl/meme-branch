package com.meme.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meme.core.base.AbstractService;
import com.meme.core.dao.ValidateRulesMapper;
import com.meme.core.form.Form;
import com.meme.core.mybatis.MapperHelper;
import com.meme.core.pojo.ValidateRules;
import com.meme.core.service.ValidateRulesService;

@Service("ValidateRulesService")
@Transactional
public class ValidateRulesServiceImpl extends AbstractService implements ValidateRulesService {
	
	@Resource private ValidateRulesMapper mapper;

	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(ValidateRules record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(ValidateRules record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public ValidateRules selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(ValidateRules record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(ValidateRules record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ValidateRules> selectAll(){
		return (List<ValidateRules>) MapperHelper.toBeanList(this.mapper.selectAll(ValidateRules.class), ValidateRules.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ValidateRules> selectByPagination(Form form) {
		this.buildSearchCriterion(ValidateRules.class, form);
		this.buildOrderByCriterion(ValidateRules.class, form);
		this.buildLimitCriterion(ValidateRules.class, form);
		return (List<ValidateRules>) MapperHelper.toBeanList(this.mapper.selectByPagination(ValidateRules.class,this.getList()), ValidateRules.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(ValidateRules.class, form);
		return this.mapper.count(ValidateRules.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ValidateRules> selectByEntity(ValidateRules record) {
		return (List<ValidateRules>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), ValidateRules.class);
	}

	@Override
	public int batchInsert(List<ValidateRules> record) {
		return this.mapper.batchInsert(record,ValidateRules.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,ValidateRules.class);
	}

	@Override
	public int batchUpdate(List<ValidateRules> record) {
		return this.mapper.batchUpdate(record,ValidateRules.class);
	}

	@Override
	public ValidateRules selectLastRulesByTableName(String tableName) {
		List<ValidateRules> list=this.mapper.selectLastRulesByTableName(tableName);
		if(null!=list&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public int clearAll() {
		return this.mapper.clearAll();
	}
}
