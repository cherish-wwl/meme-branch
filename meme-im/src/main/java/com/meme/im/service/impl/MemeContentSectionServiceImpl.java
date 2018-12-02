package com.meme.im.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meme.core.base.AbstractService;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.core.mybatis.MapperHelper;
import com.meme.im.dao.MemeContentSectionMapper;
import com.meme.im.pojo.Member;
import com.meme.im.pojo.MemeContentSection;
import com.meme.im.service.MemeContentSectionService;

@Service("MemeContentSectionService")
@Transactional
public class MemeContentSectionServiceImpl extends AbstractService implements MemeContentSectionService {

	@Resource private MemeContentSectionMapper mapper;
	@Resource private MemeContentSectionService memeContentSectionService;
	
	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(MemeContentSection record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(MemeContentSection record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public MemeContentSection selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(MemeContentSection record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(MemeContentSection record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemeContentSection> selectAll(){
		return (List<MemeContentSection>) MapperHelper.toBeanList(this.mapper.selectAll(MemeContentSection.class), MemeContentSection.class);
	}

	public void buildSearchCriterion(Class<?> entityClass, Form form) {
		super.buildSearchCriterion(entityClass, form);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MemeContentSection> selectByPagination(Form form) {
		this.buildSearchCriterion(Member.class, form);
		this.buildOrderByCriterion(MemeContentSection.class, form);
		this.buildLimitCriterion(MemeContentSection.class, form);
		return (List<MemeContentSection>) MapperHelper.toBeanList(this.mapper.selectByPagination(MemeContentSection.class,this.getList()), MemeContentSection.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(MemeContentSection.class, form);
		return this.mapper.count(MemeContentSection.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemeContentSection> selectByEntity(MemeContentSection record) {
		return (List<MemeContentSection>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), MemeContentSection.class);
	}

	@Override
	public int batchInsert(List<MemeContentSection> record) {
		return this.mapper.batchInsert(record,MemeContentSection.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,MemeContentSection.class);
	}

	@Override
	public int batchUpdate(List<MemeContentSection> record) {
		return this.mapper.batchUpdate(record,MemeContentSection.class);
	}

	@Override
	public ResultMessage add(HttpServletRequest request, MemeContentSection record) {
		
		return ResultMessage.defaultFaileMessage();
	}

	@Override
	public ResultMessage edit(HttpServletRequest request, MemeContentSection record) {
		
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
	public int deleteByContentId(Long contentid) {
		return this.mapper.deleteByContentId(contentid);
	}
}
