package com.meme.im.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meme.core.base.AbstractService;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.core.mybatis.MapperHelper;
import com.meme.core.util.IDGenerator;
import com.meme.im.dao.TvStationMapper;
import com.meme.im.form.ImForm;
import com.meme.im.pojo.Member;
import com.meme.im.pojo.TvStation;
import com.meme.im.service.TvStationService;

@Service("TvStationService")
@Transactional
public class TvStationServiceImpl extends AbstractService implements TvStationService {

	@Resource private TvStationMapper mapper;
	
	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(TvStation record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(TvStation record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public TvStation selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(TvStation record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(TvStation record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TvStation> selectAll(){
		return (List<TvStation>) MapperHelper.toBeanList(this.mapper.selectAll(TvStation.class), TvStation.class);
	}

	public void buildSearchCriterion(Class<?> entityClass, Form form) {
		super.buildSearchCriterion(entityClass, form);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TvStation> selectByPagination(Form form) {
		this.buildSearchCriterion(Member.class, form);
		this.buildOrderByCriterion(TvStation.class, form);
		this.buildLimitCriterion(TvStation.class, form);
		return (List<TvStation>) MapperHelper.toBeanList(this.mapper.selectByPagination(TvStation.class,this.getList()), TvStation.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(TvStation.class, form);
		return this.mapper.count(TvStation.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TvStation> selectByEntity(TvStation record) {
		return (List<TvStation>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), TvStation.class);
	}

	@Override
	public int batchInsert(List<TvStation> record) {
		return this.mapper.batchInsert(record,TvStation.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,TvStation.class);
	}

	@Override
	public int batchUpdate(List<TvStation> record) {
		return this.mapper.batchUpdate(record,TvStation.class);
	}

	@Override
	public ResultMessage add(HttpServletRequest request, TvStation record) {
		record.setTvid(String.valueOf(IDGenerator.generateId()));
		this.insertSelective(record);
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage edit(HttpServletRequest request, TvStation record) {
		this.updateByPrimaryKeySelective(record);
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage delete(List<Object> ids) {
		if(null!=ids&&ids.size()>0){
			this.batchDelete(ids);
		}
		return ResultMessage.defaultSuccessMessage();
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
