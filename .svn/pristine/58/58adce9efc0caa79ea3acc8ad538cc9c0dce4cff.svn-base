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
import com.meme.im.dao.MemberBillboardMapper;
import com.meme.im.pojo.Member;
import com.meme.im.pojo.MemberBillboard;
import com.meme.im.service.MemberBillboardService;

@Service("MemberBillboardService")
@Transactional
public class MemberBillboardServiceImpl extends AbstractService implements MemberBillboardService {

	@Resource private MemberBillboardMapper mapper;
	
	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(MemberBillboard record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(MemberBillboard record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public MemberBillboard selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(MemberBillboard record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(MemberBillboard record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemberBillboard> selectAll(){
		return (List<MemberBillboard>) MapperHelper.toBeanList(this.mapper.selectAll(MemberBillboard.class), MemberBillboard.class);
	}

	public void buildSearchCriterion(Class<?> entityClass, Form form) {
		super.buildSearchCriterion(entityClass, form);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MemberBillboard> selectByPagination(Form form) {
		this.buildSearchCriterion(Member.class, form);
		List<Criterion> criterions=new ArrayList<Criterion>();
		if(StringUtil.isAllNotEmpty(form.getPrimarykey().toString())){
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			criterions.add(new Criterion.CriterionBuilder().condition("memberid").operator(Operator.EQUAL).leftValue(Long.valueOf(form.getPrimarykey().toString())).javaType(Long.class).build());
		}else return new ArrayList<MemberBillboard>();
		this.getList().addAll(criterions);
		this.buildOrderByCriterion(MemberBillboard.class, form);
		this.buildLimitCriterion(MemberBillboard.class, form);
		return (List<MemberBillboard>) MapperHelper.toBeanList(this.mapper.selectByPagination(MemberBillboard.class,this.getList()), MemberBillboard.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(MemberBillboard.class, form);
		List<Criterion> criterions=new ArrayList<Criterion>();
		if(StringUtil.isAllNotEmpty(form.getPrimarykey().toString())){
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			criterions.add(new Criterion.CriterionBuilder().condition("memberid").operator(Operator.EQUAL).leftValue(Long.valueOf(form.getPrimarykey().toString())).javaType(Long.class).build());
		}else return 0;
		this.getList().addAll(criterions);
		return this.mapper.count(MemberBillboard.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemberBillboard> selectByEntity(MemberBillboard record) {
		return (List<MemberBillboard>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), MemberBillboard.class);
	}

	@Override
	public int batchInsert(List<MemberBillboard> record) {
		return this.mapper.batchInsert(record,MemberBillboard.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,MemberBillboard.class);
	}

	@Override
	public int batchUpdate(List<MemberBillboard> record) {
		return this.mapper.batchUpdate(record,MemberBillboard.class);
	}

	@Override
	public ResultMessage add(HttpServletRequest request, MemberBillboard record) {
		
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage edit(HttpServletRequest request, MemberBillboard record) {
		
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
	public void deleteByBillboardid(String id) {
		this.mapper.deleteByBillboardid(id);
	}
}
