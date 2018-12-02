package com.meme.core.service.impl;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meme.core.base.AbstractService;
import com.meme.core.cache.ehcache.initializer.ParamsEhCacheInitializer;
import com.meme.core.dao.ParamsMapper;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.core.mybatis.Criterion;
import com.meme.core.mybatis.MapperHelper;
import com.meme.core.mybatis.enums.Operator;
import com.meme.core.pojo.Params;
import com.meme.core.service.ParamsService;
import com.meme.core.service.ValidateRulesService;
import com.meme.core.util.IDGenerator;
import com.meme.core.util.StringUtil;

@Service("ParamsService")
@Transactional
public class ParamsServiceImpl extends AbstractService implements ParamsService {

	@Resource private ParamsMapper mapper;
	@Resource private ValidateRulesService validateRulesService;
	@Resource private ParamsEhCacheInitializer initializer;

	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Params record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(Params record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public Params selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Params record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Params record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Params> selectAll(){
		return (List<Params>) MapperHelper.toBeanList(this.mapper.selectAll(Params.class), Params.class);
	}

	public void buildSearchCriterion(Class<?> entityClass, Form form) {
		super.buildSearchCriterion(entityClass, form);
		if(!StringUtil.isEmpty(form.getDictgrouptype())){
			super.getList().add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			super.getList().add(new Criterion.CriterionBuilder().condition("type").operator(Operator.EQUAL).leftValue(form.getDictgrouptype()).javaType(String.class).build());
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Params> selectByPagination(Form form) {
		this.buildSearchCriterion(Params.class, form);
		this.buildOrderByCriterion(Params.class, form);
		this.buildLimitCriterion(Params.class, form);
		return (List<Params>) MapperHelper.toBeanList(this.mapper.selectByPagination(Params.class,this.getList()), Params.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(Params.class, form);
		return this.mapper.count(Params.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Params> selectByEntity(Params record) {
		return (List<Params>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), Params.class);
	}

	@Override
	public int batchInsert(List<Params> record) {
		return this.mapper.batchInsert(record,Params.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,Params.class);
	}

	@Override
	public int batchUpdate(List<Params> record) {
		return this.mapper.batchUpdate(record,Params.class);
	}

	@Override
	public List<Params> selectAllParams(String type) {
		return this.mapper.selectAllParams(type);
	}

	@Override
	public ResultMessage add(HttpServletRequest request, Params params) {
		Params tmpobj=new Params();
		tmpobj.setName(params.getName());
		List<Params> list=this.selectByEntity(tmpobj);
		if(null!=list&&list.size()>0) return ResultMessage.failed("参数名称重复，请修改");
		
		if(params.getType()==null) params.setType("");
		if(StringUtil.isEmpty(params.getValue())){
			//参数值为空，默认值不为空时取默认值
			if(StringUtil.isAllNotEmpty(params.getDefval())) params.setValue(params.getDefval());
		}
		
		long id=IDGenerator.generateId();
		params.setId(id);
		int result=this.insertSelective(params);
		if(result>=1){
			initializer.getInstance().put(params.getName(), params);
		}
		
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage edit(HttpServletRequest request, Params params) {
		if(StringUtil.isOneEmpty(params.getName(),params.getValue())){
			return ResultMessage.failed("参数名称和参数值不能为空");
		}
		Params tmpobj=new Params();
		tmpobj.setName(params.getName());
		List<Params> list=this.selectByEntity(tmpobj);
		if(null!=list&&list.size()>0){
			if(list.get(0).getId().longValue()!=params.getId().longValue()){
				return ResultMessage.failed("参数名称重复，请修改");
			}
		}
		
		tmpobj=new Params();
		tmpobj.setValue(params.getValue());
		list=this.selectByEntity(tmpobj);
		if(null!=list&&list.size()>0) {
			if(list.get(0).getId().longValue()!=params.getId().longValue()){
				return ResultMessage.failed("参数值重复，请修改");
			}
		}
		
		if(params.getType()==null) params.setType("");
		
		int result=this.updateByPrimaryKeySelective(params);
		if(result>=1){
			Params p=this.selectByPrimaryKey(params.getId());
			initializer.getInstance().put(params.getName(), p);
		}
		
		return ResultMessage.defaultSuccessMessage();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ResultMessage delete(List<Object> ids) {
		if(null!=ids&&ids.size()>0){
			Map<Long,Long> idsMap=new HashMap<Long, Long>();
			for(Object obj:ids){
				idsMap.put(Long.valueOf(obj.toString()), Long.valueOf(obj.toString()));
			}
			Map params=initializer.getInstance().getCacheList();
			if(null!=params&&params.size()>0){
				Iterator it=params.keySet().iterator();
				while(it.hasNext()){
					String key=(String)it.next();
					Params value=(Params) params.get(key);
					if(idsMap.containsKey(value.getId())){
						initializer.getInstance().remove(value.getName());
					}
				}
			}
			//缓存要先于数据库数据删除避免Iterator迭代的对象发生改变抛ConcurrentModificationException异常
			this.batchDelete(ids);
		}
		
    	return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage clearRules() {
		this.validateRulesService.clearAll();
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage batchupdate(HttpServletRequest request) {
		Map<String,String> kv=new LinkedHashMap<String, String>();
		Enumeration<String> names=request.getParameterNames();
		while(names.hasMoreElements()){
			String name=names.nextElement();
			kv.put(name, request.getParameter(name));
		}
		int result=this.mapper.batchUpdateValue(kv);
		List<Params> list=this.selectAll();
		this.initializer.initCaches(list, Params.class);
		if(result>0) return ResultMessage.defaultSuccessMessage();
		else return ResultMessage.defaultFaileMessage();
	}
}
