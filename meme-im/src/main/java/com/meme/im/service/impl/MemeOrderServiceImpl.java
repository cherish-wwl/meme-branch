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
import com.meme.im.dao.MemeOrderMapper;
import com.meme.im.form.ImForm;
import com.meme.im.pojo.Member;
import com.meme.im.pojo.MemeOrder;
import com.meme.im.service.MemeOrderService;

@Service("MemeOrderService")
@Transactional
public class MemeOrderServiceImpl extends AbstractService implements MemeOrderService {

	@Resource private MemeOrderMapper mapper;
	
	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(MemeOrder record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(MemeOrder record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public MemeOrder selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(MemeOrder record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(MemeOrder record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemeOrder> selectAll(){
		return (List<MemeOrder>) MapperHelper.toBeanList(this.mapper.selectAll(MemeOrder.class), MemeOrder.class);
	}

	public void buildSearchCriterion(Class<?> entityClass, Form form) {
		super.buildSearchCriterion(entityClass, form);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MemeOrder> selectByPagination(Form form) {
		this.buildSearchCriterion(Member.class, form);
		this.buildOrderByCriterion(MemeOrder.class, form);
		this.buildLimitCriterion(MemeOrder.class, form);
		return (List<MemeOrder>) MapperHelper.toBeanList(this.mapper.selectByPagination(MemeOrder.class,this.getList()), MemeOrder.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(MemeOrder.class, form);
		return this.mapper.count(MemeOrder.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemeOrder> selectByEntity(MemeOrder record) {
		return (List<MemeOrder>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), MemeOrder.class);
	}

	@Override
	public int batchInsert(List<MemeOrder> record) {
		return this.mapper.batchInsert(record,MemeOrder.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,MemeOrder.class);
	}

	@Override
	public int batchUpdate(List<MemeOrder> record) {
		return this.mapper.batchUpdate(record,MemeOrder.class);
	}

	@Override
	public ResultMessage add(HttpServletRequest request, MemeOrder record) {
		
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage edit(HttpServletRequest request, MemeOrder record) {
		
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

	@Override
	public List<MemeOrder> selectMemberOrders(ImForm form) {
		return this.mapper.selectMemberOrders(form);
	}

	@Override
	public int countMemberOrders(ImForm form) {
		return this.mapper.countMemberOrders(form);
	}
}
