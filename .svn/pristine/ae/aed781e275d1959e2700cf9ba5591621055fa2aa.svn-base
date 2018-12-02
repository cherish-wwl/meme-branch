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
import com.meme.im.dao.MemberBillboardItemMapper;
import com.meme.im.pojo.Member;
import com.meme.im.pojo.MemberBillboardItem;
import com.meme.im.service.MemberBillboardItemService;

@Service("MemberBillboardItemService")
@Transactional
public class MemberBillboardItemServiceImpl extends AbstractService implements MemberBillboardItemService {

	@Resource private MemberBillboardItemMapper mapper;
	
	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(MemberBillboardItem record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(MemberBillboardItem record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public MemberBillboardItem selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(MemberBillboardItem record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(MemberBillboardItem record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemberBillboardItem> selectAll(){
		return (List<MemberBillboardItem>) MapperHelper.toBeanList(this.mapper.selectAll(MemberBillboardItem.class), MemberBillboardItem.class);
	}

	public void buildSearchCriterion(Class<?> entityClass, Form form) {
		super.buildSearchCriterion(entityClass, form);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MemberBillboardItem> selectByPagination(Form form) {
		this.buildSearchCriterion(Member.class, form);
		this.buildOrderByCriterion(MemberBillboardItem.class, form);
		this.buildLimitCriterion(MemberBillboardItem.class, form);
		return (List<MemberBillboardItem>) MapperHelper.toBeanList(this.mapper.selectByPagination(MemberBillboardItem.class,this.getList()), MemberBillboardItem.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(MemberBillboardItem.class, form);
		return this.mapper.count(MemberBillboardItem.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemberBillboardItem> selectByEntity(MemberBillboardItem record) {
		return (List<MemberBillboardItem>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), MemberBillboardItem.class);
	}

	@Override
	public int batchInsert(List<MemberBillboardItem> record) {
		return this.mapper.batchInsert(record,MemberBillboardItem.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,MemberBillboardItem.class);
	}

	@Override
	public int batchUpdate(List<MemberBillboardItem> record) {
		return this.mapper.batchUpdate(record,MemberBillboardItem.class);
	}

	@Override
	public ResultMessage add(HttpServletRequest request, MemberBillboardItem record) {
		
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage edit(HttpServletRequest request, MemberBillboardItem record) {
		
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
	public List<MemberBillboardItem> selectByBillboardids(List<Object> billboardids) {
		return this.mapper.selectByBillboardids(billboardids);
	}
}
