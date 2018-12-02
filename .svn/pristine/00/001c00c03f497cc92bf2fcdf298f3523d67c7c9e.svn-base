package com.meme.core.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meme.core.base.AbstractService;
import com.meme.core.cache.DictCache;
import com.meme.core.dao.PlatformMapper;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.core.mybatis.Criterion;
import com.meme.core.mybatis.MapperHelper;
import com.meme.core.mybatis.enums.Operator;
import com.meme.core.pojo.Platform;
import com.meme.core.service.MenuService;
import com.meme.core.service.OperationService;
import com.meme.core.service.OrganMenuService;
import com.meme.core.service.OrganOperationService;
import com.meme.core.service.PlatformService;
import com.meme.core.service.RoleMenuService;
import com.meme.core.service.RoleOperationService;
import com.meme.core.util.IDGenerator;
import com.meme.core.util.StringUtil;

@Service("PlatformService")
@Transactional
public class PlatformServiceImpl extends AbstractService implements PlatformService {

	@Resource private PlatformMapper mapper;
	@Resource private MenuService menuService;
	@Resource private OperationService operationService;
	@Resource private OrganMenuService organMenuService;
	@Resource private OrganOperationService organOperationService;
	@Resource private RoleMenuService roleMenuService;
	@Resource private RoleOperationService roleOperationService;

	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Platform record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(Platform record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public Platform selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Platform record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Platform record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Platform> selectAll(){
		return (List<Platform>) MapperHelper.toBeanList(this.mapper.selectAll(Platform.class), Platform.class);
	}
	
	public void buildSearchCriterion(Class<?> entityClass, Form form) {
		super.buildSearchCriterion(entityClass, form);
		if(!StringUtil.isEmpty(form.getIsopen())){
			super.getList().add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			super.getList().add(new Criterion.CriterionBuilder().condition("isopen").operator(Operator.EQUAL).leftValue(form.getIsopen()).javaType(Integer.class).build());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Platform> selectByPagination(Form form) {
		this.buildSearchCriterion(Platform.class, form);
		this.buildOrderByCriterion(Platform.class, form);
		this.buildLimitCriterion(Platform.class, form);
		return (List<Platform>) MapperHelper.toBeanList(this.mapper.selectByPagination(Platform.class,this.getList()), Platform.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(Platform.class, form);
		return this.mapper.count(Platform.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Platform> selectByEntity(Platform record) {
		return (List<Platform>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), Platform.class);
	}

	@Override
	public int batchInsert(List<Platform> record) {
		return this.mapper.batchInsert(record,Platform.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,Platform.class);
	}

	@Override
	public int batchUpdate(List<Platform> record) {
		return this.mapper.batchUpdate(record,Platform.class);
	}

	@Override
	public List<Platform> selectByRoleids(List<Object> roleids) {
		return this.mapper.selectByRoleids(roleids);
	}

	@Override
	public List<Platform> selectByOrganid(Object organid) {
		return this.mapper.selectByOrganid(organid);
	}

	@Override
	public ResultMessage add(HttpServletRequest request, Platform record) {
		Platform p=new Platform();
		p.setName(record.getName());
		List<Platform> list=this.selectByEntity(p);
		if(null!=list&&list.size()>0){
			return ResultMessage.failed("平台名称重复");
		}
		record.setCreatetime(new Date());
		//不选择取默认值
		if(record.getIsopen()==null) record.setIsopen(Integer.valueOf(DictCache.getDefaultDictItem("PLATFORM_IS_OPEN").getDictitemcode()));
		
		long id=IDGenerator.generateId();
		record.setId(id);
		this.insertSelective(record);
		
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage edit(HttpServletRequest request, Platform record) {
		Platform p=new Platform();
		p.setName(record.getName());
		List<Platform> list=this.selectByEntity(p);
		if(null!=list&&list.size()>0){
			if(list.get(0).getId().longValue()!=record.getId().longValue()){
				return ResultMessage.failed("平台名称重复");
			}
		}
		
		this.updateByPrimaryKeySelective(record);
		
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage delete(List<Object> ids) {
		this.batchDelete(ids);
    	this.menuService.deleteByPlatformids(ids);
    	this.operationService.deleteByPlatformids(ids);
    	this.organMenuService.deleteByPlatformids(null,ids);
    	this.organOperationService.deleteByPlatformids(null,ids);
    	this.roleMenuService.deleteByPlatformids(null,ids);
    	this.roleOperationService.deleteByPlatformids(null,ids);
    	
    	return ResultMessage.defaultSuccessMessage();
	}
}
