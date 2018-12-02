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
import com.meme.im.dao.CategoryAudioItemMapper;
import com.meme.im.form.ImForm;
import com.meme.im.pojo.CategoryAudio;
import com.meme.im.pojo.CategoryAudioItem;
import com.meme.im.pojo.Member;
import com.meme.im.service.CategoryAudioItemService;
import com.meme.im.service.CategoryAudioService;

@Service("CategoryAudioItemService")
@Transactional
public class CategoryAudioItemServiceImpl extends AbstractService implements CategoryAudioItemService {

	@Resource private CategoryAudioItemMapper mapper;
	@Resource private CategoryAudioService categoryAudioService;
	
	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(CategoryAudioItem record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(CategoryAudioItem record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public CategoryAudioItem selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(CategoryAudioItem record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(CategoryAudioItem record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CategoryAudioItem> selectAll(){
		return (List<CategoryAudioItem>) MapperHelper.toBeanList(this.mapper.selectAll(CategoryAudioItem.class), CategoryAudioItem.class);
	}

	public void buildSearchCriterion(Class<?> entityClass, Form form) {
		super.buildSearchCriterion(entityClass, form);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CategoryAudioItem> selectByPagination(Form form) {
		this.buildSearchCriterion(Member.class, form);
		this.buildOrderByCriterion(CategoryAudioItem.class, form);
		this.buildLimitCriterion(CategoryAudioItem.class, form);
		return (List<CategoryAudioItem>) MapperHelper.toBeanList(this.mapper.selectByPagination(CategoryAudioItem.class,this.getList()), CategoryAudioItem.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(CategoryAudioItem.class, form);
		return this.mapper.count(CategoryAudioItem.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CategoryAudioItem> selectByEntity(CategoryAudioItem record) {
		return (List<CategoryAudioItem>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), CategoryAudioItem.class);
	}

	@Override
	public int batchInsert(List<CategoryAudioItem> record) {
		return this.mapper.batchInsert(record,CategoryAudioItem.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,CategoryAudioItem.class);
	}

	@Override
	public int batchUpdate(List<CategoryAudioItem> record) {
		return this.mapper.batchUpdate(record,CategoryAudioItem.class);
	}

	@Override
	public ResultMessage add(HttpServletRequest request, CategoryAudioItem record) {
		String[] cover=request.getParameterValues("cover");
		String[] url=request.getParameterValues("url");
		if(null!=cover&&cover.length>0){
			record.setCover(cover[0]);
		}
		if(null!=url&&url.length>0){
			record.setUrl(url[0]);
		}
		if(null==record.getAudioid()||record.getAudioid()==0l) return ResultMessage.failed("所属分类不能为空");
		CategoryAudio cat=this.categoryAudioService.selectByPrimaryKey(record.getAudioid());
		if(cat.getPid()==0l) return ResultMessage.failed("请勿选择顶级分类");
		record.setItemid(IDGenerator.generateId());
		int result=this.insertSelective(record);
		if(result>0) return ResultMessage.defaultSuccessMessage();
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage edit(HttpServletRequest request, CategoryAudioItem record) {
		String[] cover=request.getParameterValues("cover");
		String[] url=request.getParameterValues("url");
		if(null!=cover&&cover.length>0){
			record.setCover(cover[0]);
		}
		if(null!=url&&url.length>0){
			record.setUrl(url[0]);
		}
		if(null==record.getItemid()||record.getItemid()==0l) return ResultMessage.failed("请选择一条记录进行编辑");
		if(null==record.getAudioid()||record.getAudioid()==0l) return ResultMessage.failed("所属分类不能为空");
		CategoryAudio cat=this.categoryAudioService.selectByPrimaryKey(record.getAudioid());
		if(cat.getPid()==0l) return ResultMessage.failed("请勿选择顶级分类");
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
