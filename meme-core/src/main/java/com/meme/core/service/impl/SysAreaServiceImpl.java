package com.meme.core.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meme.core.base.AbstractService;
import com.meme.core.dao.SysAreaMapper;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.core.mybatis.MapperHelper;
import com.meme.core.pojo.SysArea;
import com.meme.core.service.SysAreaService;

@Service("SysAreaService")
@Transactional
public class SysAreaServiceImpl extends AbstractService implements SysAreaService {

	@Resource private SysAreaMapper mapper;

	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(SysArea record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(SysArea record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public SysArea selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(SysArea record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(SysArea record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysArea> selectAll(){
		return (List<SysArea>) MapperHelper.toBeanList(this.mapper.selectAll(SysArea.class), SysArea.class);
	}
	
	/**
	 * 重写查询规则构建语句
	 * @param entityClass
	 * @param form
	 */
	public void buildSearchCriterion(Class<?> entityClass, Form form) {
		super.buildSearchCriterion(entityClass, form);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysArea> selectByPagination(Form form) {
		this.buildSearchCriterion(SysArea.class, form);
		this.buildOrderByCriterion(SysArea.class, form);
		this.buildLimitCriterion(SysArea.class, form);
		return (List<SysArea>) MapperHelper.toBeanList(this.mapper.selectByPagination(SysArea.class,this.getList()), SysArea.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(SysArea.class, form);
		return this.mapper.count(SysArea.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysArea> selectByEntity(SysArea record) {
		return (List<SysArea>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), SysArea.class);
	}

	@Override
	public int batchInsert(List<SysArea> record) {
		return this.mapper.batchInsert(record,SysArea.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,SysArea.class);
	}

	@Override
	public int batchUpdate(List<SysArea> record) {
		return this.mapper.batchUpdate(record,SysArea.class);
	}

	@Override
	public ResultMessage add(HttpServletRequest request, SysArea record) {
		this.insertSelective(record);
		
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage edit(HttpServletRequest request, SysArea record) {
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
	public List<SysArea> selectChildList(Object pid) {
		return this.mapper.selectChildList(pid);
	}
}
