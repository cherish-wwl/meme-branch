package com.meme.im.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meme.core.base.AbstractService;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.core.mybatis.MapperHelper;
import com.meme.core.util.IDGenerator;
import com.meme.core.util.StringUtil;
import com.meme.im.dao.TvProgramMapper;
import com.meme.im.form.ImForm;
import com.meme.im.pojo.Member;
import com.meme.im.pojo.TvProgram;
import com.meme.im.service.TvProgramService;

@Service("TvProgramService")
@Transactional
public class TvProgramServiceImpl extends AbstractService implements TvProgramService {

	@Resource private TvProgramMapper mapper;
	
	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(TvProgram record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(TvProgram record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public TvProgram selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(TvProgram record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(TvProgram record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TvProgram> selectAll(){
		return (List<TvProgram>) MapperHelper.toBeanList(this.mapper.selectAll(TvProgram.class), TvProgram.class);
	}

	public void buildSearchCriterion(Class<?> entityClass, Form form) {
		super.buildSearchCriterion(entityClass, form);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TvProgram> selectByPagination(Form form) {
		this.buildSearchCriterion(Member.class, form);
		this.buildOrderByCriterion(TvProgram.class, form);
		this.buildLimitCriterion(TvProgram.class, form);
		return (List<TvProgram>) MapperHelper.toBeanList(this.mapper.selectByPagination(TvProgram.class,this.getList()), TvProgram.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(TvProgram.class, form);
		return this.mapper.count(TvProgram.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TvProgram> selectByEntity(TvProgram record) {
		return (List<TvProgram>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), TvProgram.class);
	}

	@Override
	public int batchInsert(List<TvProgram> record) {
		return this.mapper.batchInsert(record,TvProgram.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,TvProgram.class);
	}

	@Override
	public int batchUpdate(List<TvProgram> record) {
		return this.mapper.batchUpdate(record,TvProgram.class);
	}

	@Override
	public ResultMessage add(HttpServletRequest request, TvProgram record) {
		String ptimeStr=request.getParameter("ptimeStr");
		DateFormat sdf = new SimpleDateFormat("hh:mm");
		try {
			Date date = sdf.parse(ptimeStr);
			record.setPtime(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		record.setProgramid(String.valueOf(IDGenerator.generateId()));
		this.insertSelective(record);
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage edit(HttpServletRequest request, TvProgram record) {
		String ptimeStr=request.getParameter("ptimeStr");
		DateFormat sdf = new SimpleDateFormat("hh:mm");
		try {
			Date date = sdf.parse(ptimeStr);
			record.setPtime(date);
		} catch (ParseException e) {
			e.printStackTrace();
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
	public List<Map<String, Object>> selectByPaginationView(ImForm form) {
		return this.mapper.selectByPaginationView(form);
	}

	@Override
	public int countView(ImForm form) {
		return this.mapper.countView(form);
	}

	@Override
	public ResultMessage getTvProgram(HttpServletRequest request, HttpServletResponse response, String type) {
		List<Map<String,Object>> list=null;
		if(StringUtil.isEmpty(type)){
			list=this.mapper.getTvPrograms(null, null);
		}else if(type.equalsIgnoreCase("hot")){
			list=this.mapper.getTvPrograms(1, null);
		}else if (type.equalsIgnoreCase("top")) {
			list=this.mapper.getTvPrograms(null, 1);
		}else{
			list=this.mapper.getTvPrograms(null, null);
		}
		return ResultMessage.success("", list);
	}
}
