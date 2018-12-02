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
import com.meme.im.dao.CategoryAudioMapper;
import com.meme.im.pojo.CategoryAudio;
import com.meme.im.pojo.Member;
import com.meme.im.service.CategoryAudioService;

@Service("CategoryAudioService")
@Transactional
public class CategoryAudioServiceImpl extends AbstractService implements CategoryAudioService {

	@Resource private CategoryAudioMapper mapper;
	
	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(CategoryAudio record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(CategoryAudio record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public CategoryAudio selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(CategoryAudio record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(CategoryAudio record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CategoryAudio> selectAll(){
		return (List<CategoryAudio>) MapperHelper.toBeanList(this.mapper.selectAll(CategoryAudio.class), CategoryAudio.class);
	}

	public void buildSearchCriterion(Class<?> entityClass, Form form) {
		super.buildSearchCriterion(entityClass, form);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CategoryAudio> selectByPagination(Form form) {
		this.buildSearchCriterion(Member.class, form);
		this.buildOrderByCriterion(CategoryAudio.class, form);
		this.buildLimitCriterion(CategoryAudio.class, form);
		return (List<CategoryAudio>) MapperHelper.toBeanList(this.mapper.selectByPagination(CategoryAudio.class,this.getList()), CategoryAudio.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(CategoryAudio.class, form);
		return this.mapper.count(CategoryAudio.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CategoryAudio> selectByEntity(CategoryAudio record) {
		return (List<CategoryAudio>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), CategoryAudio.class);
	}

	@Override
	public int batchInsert(List<CategoryAudio> record) {
		return this.mapper.batchInsert(record,CategoryAudio.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,CategoryAudio.class);
	}

	@Override
	public int batchUpdate(List<CategoryAudio> record) {
		return this.mapper.batchUpdate(record,CategoryAudio.class);
	}

	@Override
	public ResultMessage add(HttpServletRequest request, CategoryAudio record) {
		if(null!=record.getPid()&&record.getPid()!=0l){
			CategoryAudio cat=this.selectByPrimaryKey(record.getPid());
			if(null == cat) return ResultMessage.failed("上级类目不存在");
		}
		record.setId(IDGenerator.generateId());
		this.insertSelective(record);
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage edit(HttpServletRequest request, CategoryAudio record) {
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
	public List<CategoryAudio> selectAllAudioCats() {
		return this.mapper.selectAllAudioCats();
	}
}
