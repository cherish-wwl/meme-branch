package com.meme.im.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meme.core.base.AbstractService;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.core.mybatis.Criterion;
import com.meme.core.mybatis.MapperHelper;
import com.meme.core.mybatis.enums.Operator;
import com.meme.core.util.StringUtil;
import com.meme.im.dao.MemberAlbumMapper;
import com.meme.im.form.ImForm;
import com.meme.im.pojo.Member;
import com.meme.im.pojo.MemberAlbum;
import com.meme.im.service.MemberAlbumService;

@Service("MemberAlbumService")
@Transactional
public class MemberAlbumServiceImpl extends AbstractService implements MemberAlbumService {

	@Resource private MemberAlbumMapper mapper;
	
	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(MemberAlbum record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(MemberAlbum record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public MemberAlbum selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(MemberAlbum record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(MemberAlbum record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemberAlbum> selectAll(){
		return (List<MemberAlbum>) MapperHelper.toBeanList(this.mapper.selectAll(MemberAlbum.class), MemberAlbum.class);
	}

	public void buildSearchCriterion(Class<?> entityClass, Form form) {
		super.buildSearchCriterion(entityClass, form);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MemberAlbum> selectByPagination(Form form) {
		this.buildSearchCriterion(Member.class, form);
		List<Criterion> criterions=new ArrayList<Criterion>();
		if(StringUtil.isAllNotEmpty(form.getPrimarykey().toString())){
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			criterions.add(new Criterion.CriterionBuilder().condition("memberid").operator(Operator.EQUAL).leftValue(Long.valueOf(form.getPrimarykey().toString())).javaType(Long.class).build());
		}else return new ArrayList<MemberAlbum>();
		this.getList().addAll(criterions);
		this.buildOrderByCriterion(MemberAlbum.class, form);
		this.buildLimitCriterion(MemberAlbum.class, form);
		return (List<MemberAlbum>) MapperHelper.toBeanList(this.mapper.selectByPagination(MemberAlbum.class,this.getList()), MemberAlbum.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(MemberAlbum.class, form);
		List<Criterion> criterions=new ArrayList<Criterion>();
		if(StringUtil.isAllNotEmpty(form.getPrimarykey().toString())){
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			criterions.add(new Criterion.CriterionBuilder().condition("memberid").operator(Operator.EQUAL).leftValue(Long.valueOf(form.getPrimarykey().toString())).javaType(Long.class).build());
		}else return 0;
		this.getList().addAll(criterions);
		return this.mapper.count(MemberAlbum.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemberAlbum> selectByEntity(MemberAlbum record) {
		return (List<MemberAlbum>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), MemberAlbum.class);
	}

	@Override
	public int batchInsert(List<MemberAlbum> record) {
		return this.mapper.batchInsert(record,MemberAlbum.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,MemberAlbum.class);
	}

	@Override
	public int batchUpdate(List<MemberAlbum> record) {
		return this.mapper.batchUpdate(record,MemberAlbum.class);
	}

	@Override
	public ResultMessage add(HttpServletRequest request, MemberAlbum record) {
		
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage edit(HttpServletRequest request, MemberAlbum record) {
		
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
	public PageInfo<MemberAlbum> selectByEntity(MemberAlbum record, Integer pageNo, Integer pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		return new PageInfo<MemberAlbum>(this.selectByEntity(record));
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
