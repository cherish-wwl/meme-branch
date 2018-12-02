package com.meme.im.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meme.core.base.AbstractService;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.core.mybatis.Criterion;
import com.meme.core.mybatis.MapperHelper;
import com.meme.core.mybatis.enums.Operator;
import com.meme.core.util.StringUtil;
import com.meme.im.dao.MemberAlbumItemMapper;
import com.meme.im.pojo.Member;
import com.meme.im.pojo.MemberAlbumItem;
import com.meme.im.service.MemberAlbumItemService;

@Service("MemberAlbumItemService")
@Transactional
public class MemberAlbumItemServiceImpl extends AbstractService implements MemberAlbumItemService {

	@Resource private MemberAlbumItemMapper mapper;
	
	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(MemberAlbumItem record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(MemberAlbumItem record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public MemberAlbumItem selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(MemberAlbumItem record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(MemberAlbumItem record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemberAlbumItem> selectAll(){
		return (List<MemberAlbumItem>) MapperHelper.toBeanList(this.mapper.selectAll(MemberAlbumItem.class), MemberAlbumItem.class);
	}

	public void buildSearchCriterion(Class<?> entityClass, Form form) {
		super.buildSearchCriterion(entityClass, form);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MemberAlbumItem> selectByPagination(Form form) {
		this.buildSearchCriterion(Member.class, form);
		List<Criterion> criterions=new ArrayList<Criterion>();
		if(StringUtil.isAllNotEmpty(form.getPrimarykey().toString())){
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			criterions.add(new Criterion.CriterionBuilder().condition("albumid").operator(Operator.EQUAL).leftValue(Long.valueOf(form.getPrimarykey().toString())).javaType(Long.class).build());
		}else return new ArrayList<MemberAlbumItem>();
		this.getList().addAll(criterions);
		this.buildOrderByCriterion(MemberAlbumItem.class, form);
		this.buildLimitCriterion(MemberAlbumItem.class, form);
		return (List<MemberAlbumItem>) MapperHelper.toBeanList(this.mapper.selectByPagination(MemberAlbumItem.class,this.getList()), MemberAlbumItem.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(MemberAlbumItem.class, form);
		List<Criterion> criterions=new ArrayList<Criterion>();
		if(StringUtil.isAllNotEmpty(form.getPrimarykey().toString())){
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			criterions.add(new Criterion.CriterionBuilder().condition("albumid").operator(Operator.EQUAL).leftValue(Long.valueOf(form.getPrimarykey().toString())).javaType(Long.class).build());
		}else return 0;
		this.getList().addAll(criterions);
		return this.mapper.count(MemberAlbumItem.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemberAlbumItem> selectByEntity(MemberAlbumItem record) {
		return (List<MemberAlbumItem>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), MemberAlbumItem.class);
	}

	@Override
	public int batchInsert(List<MemberAlbumItem> record) {
		return this.mapper.batchInsert(record,MemberAlbumItem.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,MemberAlbumItem.class);
	}

	@Override
	public int batchUpdate(List<MemberAlbumItem> record) {
		return this.mapper.batchUpdate(record,MemberAlbumItem.class);
	}

	@Override
	public ResultMessage add(HttpServletRequest request, MemberAlbumItem record) {
		
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage edit(HttpServletRequest request, MemberAlbumItem record) {
		
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
	public List<MemberAlbumItem> selectByAlbumids(List<Object> ids) {
		return this.mapper.selectByAlbumids(ids);
	}

	@Override
	public List<MemberAlbumItem> selectTopFiveAlbumItems(Object memberid) {
		return this.mapper.selectTopFiveAlbumItems(memberid);
	}

	@Override
	public int deleteByAlbum(String albumid) {
		return this.mapper.deleteByAlbum(albumid);
	}
}
