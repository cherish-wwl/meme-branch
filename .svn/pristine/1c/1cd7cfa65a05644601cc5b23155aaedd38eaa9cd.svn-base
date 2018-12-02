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
import com.meme.im.dao.CategoryVideoItemMapper;
import com.meme.im.form.ImForm;
import com.meme.im.pojo.CategoryVideo;
import com.meme.im.pojo.CategoryVideoItem;
import com.meme.im.pojo.Member;
import com.meme.im.service.CategoryVideoItemService;
import com.meme.im.service.CategoryVideoService;

@Service("CategoryVideoItemService")
@Transactional
public class CategoryVideoItemServiceImpl extends AbstractService implements CategoryVideoItemService {

	@Resource private CategoryVideoItemMapper mapper;
	@Resource private CategoryVideoService categoryVideoService;
	
	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(CategoryVideoItem record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(CategoryVideoItem record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public CategoryVideoItem selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(CategoryVideoItem record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(CategoryVideoItem record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CategoryVideoItem> selectAll(){
		return (List<CategoryVideoItem>) MapperHelper.toBeanList(this.mapper.selectAll(CategoryVideoItem.class), CategoryVideoItem.class);
	}

	public void buildSearchCriterion(Class<?> entityClass, Form form) {
		super.buildSearchCriterion(entityClass, form);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CategoryVideoItem> selectByPagination(Form form) {
		this.buildSearchCriterion(Member.class, form);
		this.buildOrderByCriterion(CategoryVideoItem.class, form);
		this.buildLimitCriterion(CategoryVideoItem.class, form);
		return (List<CategoryVideoItem>) MapperHelper.toBeanList(this.mapper.selectByPagination(CategoryVideoItem.class,this.getList()), CategoryVideoItem.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(CategoryVideoItem.class, form);
		return this.mapper.count(CategoryVideoItem.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CategoryVideoItem> selectByEntity(CategoryVideoItem record) {
		return (List<CategoryVideoItem>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), CategoryVideoItem.class);
	}

	@Override
	public int batchInsert(List<CategoryVideoItem> record) {
		return this.mapper.batchInsert(record,CategoryVideoItem.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,CategoryVideoItem.class);
	}

	@Override
	public int batchUpdate(List<CategoryVideoItem> record) {
		return this.mapper.batchUpdate(record,CategoryVideoItem.class);
	}

	@Override
	public ResultMessage add(HttpServletRequest request, CategoryVideoItem record) {
		if(null==record.getVideoid()||record.getVideoid()==0l) return ResultMessage.failed("视频所属分类不能为空");
		CategoryVideo cat=this.categoryVideoService.selectByPrimaryKey(record.getVideoid());
		if(cat.getPid()==0l) return ResultMessage.failed("请勿选择顶级视频分类");
		record.setItemid(IDGenerator.generateId());
		int result=this.insertSelective(record);
		if(result>0) return ResultMessage.defaultSuccessMessage();
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage edit(HttpServletRequest request, CategoryVideoItem record) {
		String[] cover=request.getParameterValues("cover");
		String[] url=request.getParameterValues("url");
		if(null!=cover&&cover.length>0){
			record.setCover(cover[0]);
		}
		if(null!=url&&url.length>0){
			record.setUrl(url[0]);
		}
		if(null==record.getItemid()||record.getItemid()==0l) return ResultMessage.failed("请选择一条记录进行编辑");
		if(null==record.getVideoid()||record.getVideoid()==0l) return ResultMessage.failed("视频所属分类不能为空");
		CategoryVideo cat=this.categoryVideoService.selectByPrimaryKey(record.getVideoid());
		if(cat.getPid()==0l) return ResultMessage.failed("请勿选择顶级视频分类");
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
