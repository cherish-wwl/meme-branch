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
import com.meme.im.dao.MemeCategoryMapper;
import com.meme.im.pojo.Member;
import com.meme.im.pojo.MemeCategory;
import com.meme.im.pojo.view.MemeCategoryView;
import com.meme.im.service.MemeCategoryService;

@Service("MemeCategoryService")
@Transactional
public class MemeCategoryServiceImpl extends AbstractService implements MemeCategoryService {

	@Resource
	private MemeCategoryMapper mapper;

	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(MemeCategory record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(MemeCategory record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public MemeCategory selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(MemeCategory record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(MemeCategory record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemeCategory> selectAll() {
		return (List<MemeCategory>) MapperHelper.toBeanList(this.mapper.selectAll(MemeCategory.class),
				MemeCategory.class);
	}

	public void buildSearchCriterion(Class<?> entityClass, Form form) {
		super.buildSearchCriterion(entityClass, form);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemeCategory> selectByPagination(Form form) {
		this.buildSearchCriterion(Member.class, form);
		this.buildOrderByCriterion(MemeCategory.class, form);
		this.buildLimitCriterion(MemeCategory.class, form);
		return (List<MemeCategory>) MapperHelper
				.toBeanList(this.mapper.selectByPagination(MemeCategory.class, this.getList()), MemeCategory.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(MemeCategory.class, form);
		return this.mapper.count(MemeCategory.class, this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemeCategory> selectByEntity(MemeCategory record) {
		return (List<MemeCategory>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), MemeCategory.class);
	}

	@Override
	public int batchInsert(List<MemeCategory> record) {
		return this.mapper.batchInsert(record, MemeCategory.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record, MemeCategory.class);
	}

	@Override
	public int batchUpdate(List<MemeCategory> record) {
		return this.mapper.batchUpdate(record, MemeCategory.class);
	}

	@Override
	public ResultMessage add(HttpServletRequest request, MemeCategory record) {
		String[] icon=request.getParameterValues("icon");
		if(null!=icon&&icon.length>0){
			record.setIcon(icon[1]);
		}
		
		if(null!=record.getPid()&&record.getPid()!=0l){
			MemeCategory cat=this.selectByPrimaryKey(record.getPid());
			if(null == cat) return ResultMessage.failed("上级栏目分类不存在");
			//上级栏目分类非顶级分类时取上级分类的所属栏目id
			record.setColumnid(cat.getColumnid());
		}else{
			if(null==record.getColumnid() || record.getColumnid()==0l) return ResultMessage.failed("所属栏目必填");
		}
		record.setId(IDGenerator.generateId());
		this.insertSelective(record);
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage edit(HttpServletRequest request, MemeCategory record) {
		String[] icon=request.getParameterValues("icon");
		if(null!=icon&&icon.length>0){
			record.setIcon(icon[1]);
		}
		
		if(null!=record.getPid()&&record.getPid()!=0l){
			MemeCategory cat=this.selectByPrimaryKey(record.getPid());
			if(null == cat) return ResultMessage.failed("上级栏目分类不存在");
			//上级栏目分类非顶级分类时取上级分类的所属栏目id
			record.setColumnid(cat.getColumnid());
		}else{
			if(null==record.getColumnid() || record.getColumnid()==0l) return ResultMessage.failed("所属栏目必填");
		}
		this.updateByPrimaryKeySelective(record);
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage delete(List<Object> ids) {
		if (null != ids && ids.size() > 0) {
			this.batchDelete(ids);
		}
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public List<MemeCategoryView> selectAllCats() {
		return this.mapper.selectAllCats();
	}

	@Override
	public List<MemeCategory> selectSubCats(Long columnid, Long pid) {
		return this.mapper.selectSubCats(columnid, pid);
	}
}
