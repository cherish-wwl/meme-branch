package com.meme.im.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.meme.core.base.AbstractService;
import com.meme.core.form.Form;
import com.meme.core.mybatis.MapperHelper;
import com.meme.im.dao.MemeRewardMapper;
import com.meme.im.form.ImForm;
import com.meme.im.pojo.MemeReward;
import com.meme.im.service.MemeRewardService;

@Service("MemeRewardService")
public class MemeRewardServiceImpl extends AbstractService implements MemeRewardService {

	@Resource private MemeRewardMapper mapper;
	
	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(MemeReward record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(MemeReward record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public MemeReward selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(MemeReward record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(MemeReward record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemeReward> selectAll(){
		return (List<MemeReward>) MapperHelper.toBeanList(this.mapper.selectAll(MemeReward.class), MemeReward.class);
	}

	public void buildSearchCriterion(Class<?> entityClass, Form form) {
		super.buildSearchCriterion(entityClass, form);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MemeReward> selectByPagination(Form form) {
		this.buildSearchCriterion(MemeReward.class, form);
		this.buildOrderByCriterion(MemeReward.class, form);
		this.buildLimitCriterion(MemeReward.class, form);
		return (List<MemeReward>) MapperHelper.toBeanList(this.mapper.selectByPagination(MemeReward.class,this.getList()), MemeReward.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(MemeReward.class, form);
		return this.mapper.count(MemeReward.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemeReward> selectByEntity(MemeReward record) {
		return (List<MemeReward>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), MemeReward.class);
	}

	@Override
	public int batchInsert(List<MemeReward> record) {
		return this.mapper.batchInsert(record,MemeReward.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,MemeReward.class);
	}

	@Override
	public int batchUpdate(List<MemeReward> record) {
		return this.mapper.batchUpdate(record,MemeReward.class);
	}
	

	@Override
	public List<Map<String, Object>> selectByPaginationView(ImForm form) {
		return this.mapper.selectByPaginationView(form);
	}
	
	@Override
	public int countView(ImForm form) {
		return this.mapper.countView(form);
	}
	
	
}
