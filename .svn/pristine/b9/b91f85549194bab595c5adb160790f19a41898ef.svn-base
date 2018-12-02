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
import com.meme.im.dao.MemberHobbyMapper;
import com.meme.im.form.ImForm;
import com.meme.im.pojo.Member;
import com.meme.im.pojo.MemberHobby;
import com.meme.im.service.MemberHobbyService;

@Service("MemberHobbyService")
@Transactional
public class MemberHobbyServiceImpl extends AbstractService implements MemberHobbyService {

	@Resource private MemberHobbyMapper mapper;
	
	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(MemberHobby record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(MemberHobby record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public MemberHobby selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(MemberHobby record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(MemberHobby record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemberHobby> selectAll(){
		return (List<MemberHobby>) MapperHelper.toBeanList(this.mapper.selectAll(MemberHobby.class), MemberHobby.class);
	}

	public void buildSearchCriterion(Class<?> entityClass, Form form) {
		super.buildSearchCriterion(entityClass, form);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MemberHobby> selectByPagination(Form form) {
		this.buildSearchCriterion(Member.class, form);
		List<Criterion> criterions=new ArrayList<Criterion>();
		if(StringUtil.isAllNotEmpty(form.getPrimarykey().toString())){
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			criterions.add(new Criterion.CriterionBuilder().condition("memberid").operator(Operator.EQUAL).leftValue(Long.valueOf(form.getPrimarykey().toString())).javaType(Long.class).build());
		}else return new ArrayList<MemberHobby>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("type").operator(Operator.EQUAL).leftValue(Integer.valueOf(form.getState())).javaType(Integer.class).build());
		this.getList().addAll(criterions);
		this.buildOrderByCriterion(MemberHobby.class, form);
		this.buildLimitCriterion(MemberHobby.class, form);
		return (List<MemberHobby>) MapperHelper.toBeanList(this.mapper.selectByPagination(MemberHobby.class,this.getList()), MemberHobby.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(MemberHobby.class, form);
		List<Criterion> criterions=new ArrayList<Criterion>();
		if(StringUtil.isAllNotEmpty(form.getPrimarykey().toString())){
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			criterions.add(new Criterion.CriterionBuilder().condition("memberid").operator(Operator.EQUAL).leftValue(Long.valueOf(form.getPrimarykey().toString())).javaType(Long.class).build());
		}else return 0;
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("type").operator(Operator.EQUAL).leftValue(Integer.valueOf(form.getState())).javaType(Integer.class).build());
		this.getList().addAll(criterions);
		return this.mapper.count(MemberHobby.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemberHobby> selectByEntity(MemberHobby record) {
		return (List<MemberHobby>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), MemberHobby.class);
	}

	@Override
	public int batchInsert(List<MemberHobby> record) {
		return this.mapper.batchInsert(record,MemberHobby.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,MemberHobby.class);
	}

	@Override
	public int batchUpdate(List<MemberHobby> record) {
		return this.mapper.batchUpdate(record,MemberHobby.class);
	}

	@Override
	public ResultMessage add(HttpServletRequest request, MemberHobby record) {
		
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage edit(HttpServletRequest request, MemberHobby record) {
		
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
	public PageInfo<MemberHobby> selectByEntity(MemberHobby record, Integer pageNo, Integer pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		return new PageInfo<MemberHobby>(this.selectByEntity(record));
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
