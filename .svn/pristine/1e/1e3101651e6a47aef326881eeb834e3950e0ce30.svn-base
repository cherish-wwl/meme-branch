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
import com.meme.im.dao.MemeWbOrderMapper;
import com.meme.im.form.ImForm;
import com.meme.im.pojo.MemeWbOrder;
import com.meme.im.service.MemeWbOrderService;

@Service("MemeWbOrderService")
@Transactional
public class MemeWbOrderServiceImpl extends AbstractService implements MemeWbOrderService {

	@Resource private MemeWbOrderMapper mapper;
	
	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(MemeWbOrder record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(MemeWbOrder record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public MemeWbOrder selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(MemeWbOrder record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(MemeWbOrder record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemeWbOrder> selectAll(){
		return (List<MemeWbOrder>) MapperHelper.toBeanList(this.mapper.selectAll(MemeWbOrder.class), MemeWbOrder.class);
	}

	public void buildSearchCriterion(Class<?> entityClass, Form form) {
		super.buildSearchCriterion(entityClass, form);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MemeWbOrder> selectByPagination(Form form) {
		this.buildSearchCriterion(MemeWbOrder.class, form);
		this.buildOrderByCriterion(MemeWbOrder.class, form);
		this.buildLimitCriterion(MemeWbOrder.class, form);
		return (List<MemeWbOrder>) MapperHelper.toBeanList(this.mapper.selectByPagination(MemeWbOrder.class,this.getList()), MemeWbOrder.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(MemeWbOrder.class, form);
		return this.mapper.count(MemeWbOrder.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemeWbOrder> selectByEntity(MemeWbOrder record) {
		return (List<MemeWbOrder>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), MemeWbOrder.class);
	}

	@Override
	public int batchInsert(List<MemeWbOrder> record) {
		return this.mapper.batchInsert(record,MemeWbOrder.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,MemeWbOrder.class);
	}

	@Override
	public int batchUpdate(List<MemeWbOrder> record) {
		return this.mapper.batchUpdate(record,MemeWbOrder.class);
	}

	@Override
	public ResultMessage add(HttpServletRequest request, MemeWbOrder record) {
		
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage edit(HttpServletRequest request, MemeWbOrder record) {
		
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
	public List<Map<String,Object>> selectMemberOrders(Long memberid) {
		return this.mapper.selectMemberOrders(memberid);
	}

}
