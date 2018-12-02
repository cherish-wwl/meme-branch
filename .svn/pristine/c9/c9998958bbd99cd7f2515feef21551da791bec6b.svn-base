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
import com.meme.im.dao.MemeColumnMapper;
import com.meme.im.pojo.Member;
import com.meme.im.pojo.MemeColumn;
import com.meme.im.service.MemeColumnService;

@Service("MemeColumnService")
@Transactional
public class MemeColumnServiceImpl extends AbstractService implements MemeColumnService {

	@Resource private MemeColumnMapper mapper;
	
	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(MemeColumn record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(MemeColumn record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public MemeColumn selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(MemeColumn record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(MemeColumn record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemeColumn> selectAll(){
		return (List<MemeColumn>) MapperHelper.toBeanList(this.mapper.selectAll(MemeColumn.class), MemeColumn.class);
	}

	public void buildSearchCriterion(Class<?> entityClass, Form form) {
		super.buildSearchCriterion(entityClass, form);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MemeColumn> selectByPagination(Form form) {
		this.buildSearchCriterion(Member.class, form);
		this.buildOrderByCriterion(MemeColumn.class, form);
		this.buildLimitCriterion(MemeColumn.class, form);
		return (List<MemeColumn>) MapperHelper.toBeanList(this.mapper.selectByPagination(MemeColumn.class,this.getList()), MemeColumn.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(MemeColumn.class, form);
		return this.mapper.count(MemeColumn.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemeColumn> selectByEntity(MemeColumn record) {
		return (List<MemeColumn>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), MemeColumn.class);
	}

	@Override
	public int batchInsert(List<MemeColumn> record) {
		return this.mapper.batchInsert(record,MemeColumn.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,MemeColumn.class);
	}

	@Override
	public int batchUpdate(List<MemeColumn> record) {
		return this.mapper.batchUpdate(record,MemeColumn.class);
	}

	@Override
	public ResultMessage add(HttpServletRequest request, MemeColumn record) {
		String[] icon=request.getParameterValues("icon");
		if(null!=icon&&icon.length>0){
			record.setIcon(icon[1]);
		}
		List<MemeColumn> list=this.selectByCode(record.getColumncode());
		if(null!=list&&list.size()>0) return ResultMessage.failed("栏目编码重复，请修改");
		if(null!=record.getPid()&&record.getPid()!=0l){
			MemeColumn cat=this.selectByPrimaryKey(record.getPid());
			if(null == cat) return ResultMessage.failed("上级栏目不存在");
		}
		record.setId(IDGenerator.generateId());
		this.insertSelective(record);
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage edit(HttpServletRequest request, MemeColumn record) {
		String[] icon=request.getParameterValues("icon");
		if(null!=icon&&icon.length>0){
			record.setIcon(icon[1]);
		}
		
		List<MemeColumn> list=this.selectByCode(record.getColumncode());
		if(null!=list&&list.size()>0){
			for(MemeColumn sec:list){
				if(sec.getId().longValue()!=record.getId().longValue()){
					return ResultMessage.failed("栏目编码重复，请修改");
				}
			}
		}
		
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
	public List<MemeColumn> selectAllColumns() {
		return this.mapper.selectAllColumns();
	}

	@Override
	public List<MemeColumn> selectByCode(String code) {
		return this.mapper.selectByCode(code);
	}
}
