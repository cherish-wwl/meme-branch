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
import com.meme.core.util.IDGenerator;
import com.meme.im.dao.CategoryVideoMapper;
import com.meme.im.pojo.CategoryVideo;
import com.meme.im.pojo.Member;
import com.meme.im.service.CategoryVideoService;

@Service("CategoryVideoService")
@Transactional
public class CategoryVideoServiceImpl extends AbstractService implements CategoryVideoService {

	@Resource private CategoryVideoMapper mapper;
	
	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(CategoryVideo record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(CategoryVideo record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public CategoryVideo selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(CategoryVideo record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(CategoryVideo record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CategoryVideo> selectAll(){
		return (List<CategoryVideo>) MapperHelper.toBeanList(this.mapper.selectAll(CategoryVideo.class), CategoryVideo.class);
	}

	public void buildSearchCriterion(Class<?> entityClass, Form form) {
		super.buildSearchCriterion(entityClass, form);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CategoryVideo> selectByPagination(Form form) {
		this.buildSearchCriterion(Member.class, form);
		this.buildOrderByCriterion(CategoryVideo.class, form);
		this.buildLimitCriterion(CategoryVideo.class, form);
		return (List<CategoryVideo>) MapperHelper.toBeanList(this.mapper.selectByPagination(CategoryVideo.class,this.getList()), CategoryVideo.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(CategoryVideo.class, form);
		return this.mapper.count(CategoryVideo.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CategoryVideo> selectByEntity(CategoryVideo record) {
		return (List<CategoryVideo>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), CategoryVideo.class);
	}

	@Override
	public int batchInsert(List<CategoryVideo> record) {
		return this.mapper.batchInsert(record,CategoryVideo.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,CategoryVideo.class);
	}

	@Override
	public int batchUpdate(List<CategoryVideo> record) {
		return this.mapper.batchUpdate(record,CategoryVideo.class);
	}

	@Override
	public ResultMessage add(HttpServletRequest request, CategoryVideo record) {
		if(null!=record.getPid()&&record.getPid()!=0l){
			CategoryVideo cat=this.selectByPrimaryKey(record.getPid());
			if(null == cat) return ResultMessage.failed("上级类目不存在");
		}
		record.setId(IDGenerator.generateId());
		this.insertSelective(record);
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage edit(HttpServletRequest request, CategoryVideo record) {
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
	public List<CategoryVideo> selectAllVideoCats() {
		return this.mapper.selectAllVideoCats();
	}
}
