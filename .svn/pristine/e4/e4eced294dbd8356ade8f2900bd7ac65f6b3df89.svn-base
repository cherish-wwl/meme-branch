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
import com.meme.core.dao.OrganizationMapper;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.core.mybatis.Criterion;
import com.meme.core.mybatis.MapperHelper;
import com.meme.core.mybatis.enums.Operator;
import com.meme.core.pojo.Menu;
import com.meme.core.pojo.Operation;
import com.meme.core.pojo.OrganMenu;
import com.meme.core.pojo.OrganOperation;
import com.meme.core.pojo.Organization;
import com.meme.core.service.DepartmentService;
import com.meme.core.service.LoginAccountInfoService;
import com.meme.core.service.LoginAccountRoleService;
import com.meme.core.service.LoginAccountService;
import com.meme.core.service.MenuService;
import com.meme.core.service.OperationService;
import com.meme.core.service.OrganDataAuthService;
import com.meme.core.service.OrganMenuService;
import com.meme.core.service.OrganOperationService;
import com.meme.core.service.OrganizationService;
import com.meme.core.service.PositionService;
import com.meme.core.service.RoleDataAuthService;
import com.meme.core.service.RoleMenuService;
import com.meme.core.service.RoleOperationService;
import com.meme.core.service.RoleService;
import com.meme.core.util.IDGenerator;
import com.meme.core.util.StringUtil;

@Service("OrganizationService")
@Transactional
public class OrganizationServiceImpl extends AbstractService implements OrganizationService {

	@Resource private OrganizationMapper mapper;
	@Resource private DepartmentService departmentService;
	@Resource private LoginAccountInfoService loginAccountInfoService;
	@Resource private LoginAccountRoleService loginAccountRoleService;
	@Resource private LoginAccountService loginAccountService;
	@Resource private OrganDataAuthService organDataAuthService;
	@Resource private OrganMenuService organMenuService;
	@Resource private OrganOperationService organOperationService;
	@Resource private PositionService positionService;
	@Resource private RoleDataAuthService roleDataAuthService;
	@Resource private RoleMenuService roleMenuService;
	@Resource private RoleOperationService roleOperationService;
	@Resource private RoleService roleService;
	@Resource private MenuService menuService;
	@Resource private OperationService operationService;

	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Organization record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(Organization record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public Organization selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Organization record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Organization record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Organization> selectAll(){
		return (List<Organization>) MapperHelper.toBeanList(this.mapper.selectAll(Organization.class), Organization.class);
	}

	/**
	 * 重写查询规则构建方法，添加单位类型和单位状态查询
	 * @param entityClass
	 * @param form
	 */
	public void buildSearchCriterion(Class<?> entityClass, Form form) {
		super.buildSearchCriterion(entityClass, form);
		if(!StringUtil.isEmpty(form.getOrganizationtype())){
			super.getList().add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			super.getList().add(new Criterion.CriterionBuilder().condition("type").operator(Operator.EQUAL).leftValue(form.getOrganizationtype()).javaType(Integer.class).build());
		}
		if(!StringUtil.isEmpty(form.getOrganizationstate())){
			super.getList().add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			super.getList().add(new Criterion.CriterionBuilder().condition("state").operator(Operator.EQUAL).leftValue(form.getOrganizationstate()).javaType(Integer.class).build());
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Organization> selectByPagination(Form form) {
		this.buildSearchCriterion(Organization.class, form);
		this.buildOrderByCriterion(Organization.class, form);
		this.buildLimitCriterion(Organization.class, form);
		return (List<Organization>) MapperHelper.toBeanList(this.mapper.selectByPagination(Organization.class,this.getList()), Organization.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(Organization.class, form);
		return this.mapper.count(Organization.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Organization> selectByEntity(Organization record) {
		return (List<Organization>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), Organization.class);
	}

	@Override
	public int batchInsert(List<Organization> record) {
		return this.mapper.batchInsert(record,Organization.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,Organization.class);
	}

	@Override
	public int batchUpdate(List<Organization> record) {
		return this.mapper.batchUpdate(record,Organization.class);
	}

	@Override
	public int updateByIds(Map<String, Object> params, List<Object> list) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().javaType(Long.class).condition("id").operator(Operator.IN).leftValue(list).build());
		return this.mapper.updateByParameterMap(params, criterions, Organization.class);
	}

	@Override
	public ResultMessage add(HttpServletRequest request, Organization record) {
		long id=IDGenerator.generateId();
		record.setId(id);
		this.insertSelective(record);
		
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage edit(HttpServletRequest request, Organization record) {
		this.updateByPrimaryKeySelective(record);
		
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage delete(List<Object> ids) {
		this.batchDelete(ids);
    	this.departmentService.deleteByOrganids(ids);
    	this.loginAccountInfoService.deleteByOrganids(ids);
    	this.loginAccountRoleService.deleteByOrganids(ids);
    	this.loginAccountService.deleteByOrganids(ids);
    	this.organDataAuthService.deleteByOrganids(ids);
    	this.organMenuService.deleteByOrganids(ids);
    	this.organOperationService.deleteByOrganids(ids);
    	this.positionService.deleteByOrganids(ids);
    	this.roleDataAuthService.deleteByOrganids(ids);
    	this.roleMenuService.deleteByOrganids(ids);
    	this.roleOperationService.deleteByOrganids(ids);
    	this.roleService.deleteByOrganids(ids);
    	
    	return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage funcAuth(List<Object> menuids,
			List<Object> delmenuids, List<Object> operationids,
			List<Object> deloperationids, Long organid) {
		//授权菜单
		if(null!=menuids&&menuids.size()>0){
			List<Menu> menus=this.menuService.selectByids(menuids);
			if(null!=menus&&menus.size()>0){
				List<OrganMenu> organMenus=new ArrayList<OrganMenu>();
				for(Menu m:menus){
					OrganMenu om=new OrganMenu();
					om.setMenuid(m.getId());
					om.setOrganid(organid);
					om.setPlatformid(m.getPlatformid());
					long id=IDGenerator.generateId();
					om.setId(id);
					organMenus.add(om);
				}
				this.organMenuService.batchInsert(organMenus);
			}
		}
		
		//授权操作
		if(null!=operationids&&operationids.size()>0){
			List<Operation> operations=this.operationService.selectByids(operationids);
			if(null!=operations&&operations.size()>0){
				List<OrganOperation> organOperations=new ArrayList<OrganOperation>();
				for(Operation o:operations){
					OrganOperation oper=new OrganOperation();
					oper.setMenuid(o.getMenuid());
					oper.setOperationid(o.getId());
					oper.setOrganid(organid);
					oper.setPlatformid(o.getPlatformid());
					long id=IDGenerator.generateId();
					oper.setId(id);
					organOperations.add(oper);
				}
				this.organOperationService.batchInsert(organOperations);
			}
		}
		//取消授权操作
		if(null!=deloperationids&&deloperationids.size()>0){
			this.organOperationService.deleteByOperationids(organid,deloperationids);
		}
		
		//取消授权菜单
		if(null!=delmenuids&&delmenuids.size()>0){
			this.organMenuService.deleteByMenuids(organid,delmenuids);
			this.roleMenuService.deleteMenuByOrganid(organid, delmenuids);
			this.organOperationService.deleteByMenuids(organid,delmenuids);
		}
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage auth(List<Object> ids) {
		if(null!=ids&&ids.size()>0){
			Map<String,Object> params=new HashMap<String, Object>();
			params.put("state", 1);
			this.updateByIds(params, ids);
		}
    	
    	return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage cancelAuth(List<Object> ids) {
		if(null!=ids&&ids.size()>0){
			Map<String,Object> params=new HashMap<String, Object>();
			params.put("state", 2);
			this.updateByIds(params, ids);
		}
    	
    	return ResultMessage.defaultSuccessMessage();
	}

}
