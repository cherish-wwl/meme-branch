package com.meme.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meme.core.base.AbstractService;
import com.meme.core.cache.ehcache.initializer.DictEhCacheInitializer;
import com.meme.core.dao.DictGroupMapper;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.core.mybatis.Criterion;
import com.meme.core.mybatis.MapperHelper;
import com.meme.core.mybatis.enums.Operator;
import com.meme.core.pojo.DictGroup;
import com.meme.core.pojo.DictItemView;
import com.meme.core.service.DictGroupService;
import com.meme.core.service.DictItemService;
import com.meme.core.util.IDGenerator;
import com.meme.core.util.StringUtil;

@Service("DictGroupService")
@Transactional
public class DictGroupServiceImpl extends AbstractService implements DictGroupService {

	@Resource private DictGroupMapper mapper;
	@Resource private DictItemService dictItemService;
	@Resource private DictEhCacheInitializer initializer;

	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(DictGroup record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(DictGroup record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public DictGroup selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(DictGroup record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(DictGroup record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DictGroup> selectAll(){
		return (List<DictGroup>) MapperHelper.toBeanList(this.mapper.selectAll(DictGroup.class), DictGroup.class);
	}
	
	/**
	 * 重写查询规则构建语句
	 * @param entityClass
	 * @param form
	 */
	public void buildSearchCriterion(Class<?> entityClass, Form form) {
		super.buildSearchCriterion(entityClass, form);
		if(!StringUtil.isEmpty(form.getDictgrouptype())){
			super.getList().add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			super.getList().add(new Criterion.CriterionBuilder().condition("type").operator(Operator.EQUAL).leftValue(form.getDictgrouptype()).javaType(String.class).build());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DictGroup> selectByPagination(Form form) {
		this.buildSearchCriterion(DictGroup.class, form);
		this.buildOrderByCriterion(DictGroup.class, form);
		this.buildLimitCriterion(DictGroup.class, form);
		return (List<DictGroup>) MapperHelper.toBeanList(this.mapper.selectByPagination(DictGroup.class,this.getList()), DictGroup.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(DictGroup.class, form);
		return this.mapper.count(DictGroup.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DictGroup> selectByEntity(DictGroup record) {
		return (List<DictGroup>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), DictGroup.class);
	}

	@Override
	public int batchInsert(List<DictGroup> record) {
		return this.mapper.batchInsert(record,DictGroup.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,DictGroup.class);
	}

	@Override
	public int batchUpdate(List<DictGroup> record) {
		return this.mapper.batchUpdate(record,DictGroup.class);
	}

	@Override
	public ResultMessage add(HttpServletRequest request, DictGroup dictgroup) {
		if(StringUtil.isOneEmpty(dictgroup.getDictgroupname(),dictgroup.getDictgroupcode())){
			return ResultMessage.failed("字典分组名称和编码不能为空");
		}
		DictGroup tmpobj=new DictGroup();
		tmpobj.setDictgroupname(dictgroup.getDictgroupname());
		List<DictGroup> list=this.selectByEntity(tmpobj);
		if(null!=list&&list.size()>0) return ResultMessage.failed("字典分组名称重复，请修改");
		
		tmpobj=new DictGroup();
		tmpobj.setDictgroupcode(dictgroup.getDictgroupcode());
		list=this.selectByEntity(tmpobj);
		if(null!=list&&list.size()>0) return ResultMessage.failed("字典分组编码重复，请修改");
		
		long id=IDGenerator.generateId();
		dictgroup.setDictgroupid(id);
		this.insertSelective(dictgroup);
		
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage edit(HttpServletRequest request, DictGroup dictgroup) {
		DictGroup oldGroup=this.selectByPrimaryKey(dictgroup.getDictgroupid());
		if(StringUtil.isOneEmpty(dictgroup.getDictgroupname(),dictgroup.getDictgroupcode())){
			return ResultMessage.failed("字典分组名称和编码不能为空");
		}
		
		DictGroup tmpobj=new DictGroup();
		tmpobj.setDictgroupname(dictgroup.getDictgroupname());
		List<DictGroup> list=this.selectByEntity(tmpobj);
		if(null!=list&&list.size()>0){
			if(list.get(0).getDictgroupid().longValue()!=dictgroup.getDictgroupid().longValue()){
				return ResultMessage.failed("字典分组名称重复，请修改");
			}
		}
		
		tmpobj=new DictGroup();
		tmpobj.setDictgroupcode(dictgroup.getDictgroupcode());
		list=this.selectByEntity(tmpobj);
		if(null!=list&&list.size()>0){
			if(list.get(0).getDictgroupid().longValue()!=dictgroup.getDictgroupid().longValue()){
				return ResultMessage.failed("字典分组编码重复，请修改");
			}
		}
		int result=this.updateByPrimaryKeySelective(dictgroup);
		if(result>=1){
			if(null!=oldGroup){
				initializer.getInstance().removeAll(oldGroup.getDictgroupcode());
				List<DictItemView> items=this.dictItemService.selectAllDict(oldGroup.getDictgroupid());
				initializer.initCaches(items, null);
			}
		}
		
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage delete(List<Object> ids) {
		if(null!=ids&&ids.size()>0){
			List<DictGroup> list=this.selectByDictgroupids(ids);
			this.batchDelete(ids);
			Map<Long,Long> idsMap=new HashMap<Long, Long>();
			for(Object obj:ids){
				idsMap.put(Long.valueOf(obj.toString()), Long.valueOf(obj.toString()));
			}
	    	this.dictItemService.deleteByDictGroupids(ids);
	    	
	    	if(null!=list&&list.size()>0){
	    		for(DictGroup g:list){
	    			this.initializer.getInstance().removeAll(g.getDictgroupcode());
	    		}
	    	}
//	    	Map dictitems=this.initializer.getInstance().getCacheList();
//	    	if(null!=dictitems&&dictitems.size()>0){
//				Iterator it=dictitems.keySet().iterator();
//				while(it.hasNext()){
//					//缓存分类键值
//					String key=(String)it.next();
//					//字典组下的所有字典项
//					LinkedHashMap value=(LinkedHashMap) dictitems.get(key);
//					Collection values=value.values();
//					for (Iterator iterator = values.iterator(); iterator.hasNext();) {
//						String dictitemcode=(String) iterator.next();
//						DictItemView item=(DictItemView)value.get(dictitemcode);
//						//判断分组ID在要删除的分组中时删除此分组缓存
//						if(idsMap.containsKey(item.getDictgroupid())){
//							this.initializer.getInstance().removeAll(item.getDictgroupcode());
//							break;
//						}
//					}
//				}
//			}
		}
    	
    	return ResultMessage.defaultSuccessMessage();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DictGroup> selectByDictgroupids(List<Object> ids) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().javaType(Long.class).condition("dictgroupid").operator(Operator.IN).leftValue(ids).build());
		return (List<DictGroup>) MapperHelper.toBeanList(this.mapper.selectByCriterions(criterions, DictGroup.class), DictGroup.class);
	}

}
