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
import com.meme.im.dao.MemberHobbyTypeMapper;
import com.meme.im.pojo.Member;
import com.meme.im.pojo.MemberHobbyType;
import com.meme.im.service.MemberHobbyTypeService;

@Service("MemberHobbyTypeService")
@Transactional
public class MemberHobbyTypeServiceImpl extends AbstractService implements MemberHobbyTypeService {

	@Resource private MemberHobbyTypeMapper mapper;
	
	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(MemberHobbyType record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(MemberHobbyType record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public MemberHobbyType selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(MemberHobbyType record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(MemberHobbyType record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemberHobbyType> selectAll(){
		return (List<MemberHobbyType>) MapperHelper.toBeanList(this.mapper.selectAll(MemberHobbyType.class), MemberHobbyType.class);
	}

	public void buildSearchCriterion(Class<?> entityClass, Form form) {
		super.buildSearchCriterion(entityClass, form);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MemberHobbyType> selectByPagination(Form form) {
		this.buildSearchCriterion(Member.class, form);
		List<Criterion> criterions=new ArrayList<Criterion>();
		if(StringUtil.isAllNotEmpty(form.getPrimarykey().toString())){
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			criterions.add(new Criterion.CriterionBuilder().condition("memberid").operator(Operator.EQUAL).leftValue(Long.valueOf(form.getPrimarykey().toString())).javaType(Long.class).build());
		}else return new ArrayList<MemberHobbyType>();
		this.getList().addAll(criterions);
		this.buildOrderByCriterion(MemberHobbyType.class, form);
		this.buildLimitCriterion(MemberHobbyType.class, form);
		return (List<MemberHobbyType>) MapperHelper.toBeanList(this.mapper.selectByPagination(MemberHobbyType.class,this.getList()), MemberHobbyType.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(MemberHobbyType.class, form);
		List<Criterion> criterions=new ArrayList<Criterion>();
		if(StringUtil.isAllNotEmpty(form.getPrimarykey().toString())){
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			criterions.add(new Criterion.CriterionBuilder().condition("memberid").operator(Operator.EQUAL).leftValue(Long.valueOf(form.getPrimarykey().toString())).javaType(Long.class).build());
		}else return 0;
		this.getList().addAll(criterions);
		return this.mapper.count(MemberHobbyType.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemberHobbyType> selectByEntity(MemberHobbyType record) {
		return (List<MemberHobbyType>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), MemberHobbyType.class);
	}

	@Override
	public int batchInsert(List<MemberHobbyType> record) {
		return this.mapper.batchInsert(record,MemberHobbyType.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,MemberHobbyType.class);
	}

	@Override
	public int batchUpdate(List<MemberHobbyType> record) {
		return this.mapper.batchUpdate(record,MemberHobbyType.class);
	}

	@Override
	public ResultMessage add(HttpServletRequest request, MemberHobbyType record) {
		
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage edit(HttpServletRequest request, MemberHobbyType record) {
		
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage delete(List<Object> ids) {
		if(null!=ids&&ids.size()>0){
			this.batchDelete(ids);
		}
		return ResultMessage.defaultSuccessMessage();
	}
}
