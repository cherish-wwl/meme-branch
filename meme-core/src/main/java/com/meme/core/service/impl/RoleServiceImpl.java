package com.meme.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meme.core.base.AbstractService;
import com.meme.core.dao.RoleMapper;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.core.mybatis.Criterion;
import com.meme.core.mybatis.MapperHelper;
import com.meme.core.mybatis.enums.Operator;
import com.meme.core.pojo.Menu;
import com.meme.core.pojo.Operation;
import com.meme.core.pojo.Role;
import com.meme.core.pojo.RoleMenu;
import com.meme.core.pojo.RoleOperation;
import com.meme.core.service.LoginAccountRoleService;
import com.meme.core.service.MenuService;
import com.meme.core.service.OperationService;
import com.meme.core.service.RoleDataAuthService;
import com.meme.core.service.RoleMenuService;
import com.meme.core.service.RoleOperationService;
import com.meme.core.service.RoleService;
import com.meme.core.util.IDGenerator;
import com.meme.core.util.StringUtil;

@Service("RoleService")
@Transactional
public class RoleServiceImpl extends AbstractService implements RoleService {

	@Resource private RoleMapper mapper;
	@Resource private RoleMenuService roleMenuService;
	@Resource private RoleOperationService roleOperationService;
	@Resource private RoleDataAuthService roleDataAuthService;
	@Resource private MenuService menuService;
	@Resource private OperationService operationService;
	@Resource private LoginAccountRoleService loginAccountRoleService;
	

	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Role record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(Role record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public Role selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Role record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Role record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> selectAll(){
		return (List<Role>) MapperHelper.toBeanList(this.mapper.selectAll(Role.class), Role.class);
	}

	public void buildSearchCriterion(Class<?> entityClass, Form form) {
		super.buildSearchCriterion(entityClass, form);
		if(null!=form.getOrganid()){
			Long organid=Long.valueOf(form.getOrganid().toString());
			super.getList().add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			super.getList().add(new Criterion.CriterionBuilder().condition("organid").operator(Operator.EQUAL).leftValue(organid).javaType(Long.class).build());
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Role> selectByPagination(Form form) {
		this.buildSearchCriterion(Role.class, form);
		this.buildOrderByCriterion(Role.class, form);
		this.buildLimitCriterion(Role.class, form);
		return (List<Role>) MapperHelper.toBeanList(this.mapper.selectByPagination(Role.class,this.getList()), Role.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(Role.class, form);
		return this.mapper.count(Role.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> selectByEntity(Role record) {
		return (List<Role>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), Role.class);
	}

	@Override
	public int batchInsert(List<Role> record) {
		return this.mapper.batchInsert(record,Role.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,Role.class);
	}

	@Override
	public int batchUpdate(List<Role> record) {
		return this.mapper.batchUpdate(record,Role.class);
	}

	@Override
	public int deleteByOrganids(List<Object> list) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("organid").operator(Operator.IN).leftValue(list).javaType(Long.class).build());
		return this.mapper.deleteByCriterions(criterions, Role.class);
	}

	@Override
	public List<Role> selectRolesByLoginid(Object loginid) {
		return this.mapper.selectRolesByLoginid(loginid);
	}

	@Override
	public ResultMessage add(HttpServletRequest request, Role role) {
		if(StringUtil.isEmpty(role.getName())) return ResultMessage.failed("角色名称不能为空");
		if(role.getOrganid()==null||role.getOrganid().longValue()==0l) return ResultMessage.failed("角色所属单位ID不能为空");
		Role obj=new Role();
		obj.setName(role.getName());
		obj.setOrganid(role.getOrganid());
		List<Role> list=this.selectByEntity(obj);
		if(null!=list&&list.size()>0) return ResultMessage.failed("此单位角色名称重复");
		
		long id=IDGenerator.generateId();
		role.setId(id);
		this.insertSelective(role);
		
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage edit(HttpServletRequest request, Role role) {
		if(StringUtil.isEmpty(role.getName())) return ResultMessage.failed("角色名称不能为空");
		if(role.getOrganid()==null||role.getOrganid().longValue()==0l) return ResultMessage.failed("角色所属单位ID不能为空");
		Role obj=new Role();
		obj.setName(role.getName());
		obj.setOrganid(role.getOrganid());
		List<Role> list=this.selectByEntity(obj);
		if(null!=list&&list.size()>0) {
			if(list.get(0).getId().longValue()!=role.getId().longValue()){
				return ResultMessage.failed("此单位角色名称重复");
			}
		}
		this.updateByPrimaryKeySelective(role);
		
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage delete(List<Object> ids) {
		if(null!=ids&&ids.size()>0){
    		this.batchDelete(ids);
        	this.roleMenuService.deleteByroleids(ids);
        	this.roleOperationService.deleteByroleids(ids);
        	this.roleDataAuthService.deleteByroleids(ids);
        	this.loginAccountRoleService.deleteByroleids(null, ids);
        	
    	}
    	
    	return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage funcAuth(List<Object> menuids,
			List<Object> delmenuids, List<Object> operationids,
			List<Object> deloperationids, Long roleid) {
		Role role=null;
		if(null!=roleid&&roleid!=0l){
			role=this.selectByPrimaryKey(roleid);
		}else return ResultMessage.failed("未提供授权角色ID");
		if(null==role) return ResultMessage.failed("此授权角色不存在");
		
		//授权菜单
		if(null!=menuids&&menuids.size()>0){
			List<Menu> menus=this.menuService.selectByids(menuids);
			if(null!=menus&&menus.size()>0){
				List<RoleMenu> roleMenus=new ArrayList<RoleMenu>();
				for(Menu m:menus){
					RoleMenu rm=new RoleMenu();
					rm.setMenuid(m.getId());
					rm.setOrganid(role.getOrganid());
					rm.setPlatformid(m.getPlatformid());
					rm.setRoleid(role.getId());
					long id=IDGenerator.generateId();
					rm.setId(id);
					roleMenus.add(rm);
				}
				this.roleMenuService.batchInsert(roleMenus);
			}
		}
		
		//授权操作
		if(null!=operationids&&operationids.size()>0){
			List<Operation> operations=this.operationService.selectByids(operationids);
			if(null!=operations&&operations.size()>0){
				List<RoleOperation> roleOperations=new ArrayList<RoleOperation>();
				for(Operation o:operations){
					RoleOperation ro=new RoleOperation();
					ro.setMenuid(o.getMenuid());
					ro.setOperationid(o.getId());
					ro.setOrganid(role.getOrganid());
					ro.setPlatformid(o.getPlatformid());
					ro.setRoleid(role.getId());
					long id=IDGenerator.generateId();
					ro.setId(id);
					roleOperations.add(ro);
				}
				this.roleOperationService.batchInsert(roleOperations);
			}
		}
		
		//取消授权操作
		if(null!=deloperationids&&deloperationids.size()>0){
			this.roleOperationService.deleteByOperationids(role.getId(),deloperationids);
		}
		
		//取消授权菜单
		if(null!=delmenuids&&delmenuids.size()>0){
			this.roleMenuService.deleteByMenuIds(role.getId(),delmenuids);
			this.roleOperationService.deleteByMenuIds(roleid, delmenuids);
		}
		
		return ResultMessage.defaultSuccessMessage();
	}
}
