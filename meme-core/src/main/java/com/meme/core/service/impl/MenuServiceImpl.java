package com.meme.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meme.core.base.AbstractService;
import com.meme.core.cache.DictCache;
import com.meme.core.dao.MenuMapper;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.core.mybatis.Criterion;
import com.meme.core.mybatis.MapperHelper;
import com.meme.core.mybatis.enums.Operator;
import com.meme.core.pojo.Menu;
import com.meme.core.service.MenuService;
import com.meme.core.service.OperationService;
import com.meme.core.service.OrganDataAuthService;
import com.meme.core.service.OrganMenuService;
import com.meme.core.service.OrganOperationService;
import com.meme.core.service.RoleDataAuthService;
import com.meme.core.service.RoleMenuService;
import com.meme.core.service.RoleOperationService;
import com.meme.core.util.IDGenerator;

@Service("MenuService")
@Transactional
public class MenuServiceImpl extends AbstractService implements MenuService {

	@Resource private MenuMapper mapper;
	@Resource private OrganMenuService organMenuService;
	@Resource private RoleMenuService roleMenuService;
	@Resource private OperationService operationService;
	@Resource private OrganOperationService organOperationService;
	@Resource private RoleOperationService roleOperationService;
	@Resource private OrganDataAuthService organDataAuthService;
	@Resource private RoleDataAuthService roleDataAuthService;

	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Menu record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(Menu record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public Menu selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Menu record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Menu record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> selectAll() {
		return (List<Menu>) MapperHelper.toBeanList(this.mapper.selectAll(Menu.class), Menu.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> selectByPagination(Form form) {
		this.buildSearchCriterion(Menu.class, form);
		this.buildOrderByCriterion(Menu.class, form);
		this.buildLimitCriterion(Menu.class, form);
		return (List<Menu>) MapperHelper.toBeanList(this.mapper.selectByPagination(Menu.class,this.getList()), Menu.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(Menu.class, form);
		return this.mapper.count(Menu.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> selectByEntity(Menu record) {
		return (List<Menu>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), Menu.class);
	}

	@Override
	public int batchInsert(List<Menu> record) {
		return this.mapper.batchInsert(record,Menu.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,Menu.class);
	}

	@Override
	public int batchUpdate(List<Menu> record) {
		return this.mapper.batchUpdate(record,Menu.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> selectByPlatformids(List<Object> list) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().javaType(Long.class).condition("platformid").operator(Operator.IN).leftValue(list).build());
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.ORDER_BY).condition("hid,pid,sortno").leftValue("asc").build());
		return (List<Menu>) MapperHelper.toBeanList(this.mapper.selectByCriterions(criterions, Menu.class), Menu.class);
	}

	@Override
	public List<Menu> selectLoginAccountMenus(List<Object> roleids,Object platformid,Object pid) {
		return this.mapper.selectLoginAccountMenus(roleids, platformid,pid);
	}

	@Override
	public int deleteByPlatformids(List<Object> list) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("platformid").operator(Operator.IN).leftValue(list).javaType(Long.class).build());
		return this.mapper.deleteByCriterions(criterions, Menu.class);
	}

	@Override
	public List<Menu> selectOrganMenus(Object organid, List<Object> platformids) {
		return this.mapper.selectOrganMenus(organid, platformids);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> selectByids(List<Object> list) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("id").operator(Operator.IN).leftValue(list).javaType(Long.class).build());
		return (List<Menu>) MapperHelper.toBeanList(this.mapper.selectByCriterions(criterions, Menu.class), Menu.class);
	}

	@Override
	public List<Menu> selectRoleMenus(List<Object> roleids, List<Object> platformids) {
		return this.mapper.selectRoleMenus(roleids, platformids);
	}

	@Override
	public ResultMessage add(HttpServletRequest request, Menu record) {
		if(record.getHid()==null){
			record.setHid(0L);
		}
		if(record.getPid()==null){
			record.setPid(0L);
		}
		//不选择取默认值
		if(null==record.getIsallowchild()||record.getIsallowchild()==0) {
			record.setIsallowchild(Integer.valueOf(DictCache.getDefaultDictItem("SF").getDictitemcode()));
		}
		
		long id=IDGenerator.generateId();
		record.setId(id);
		this.insertSelective(record);
		
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage edit(HttpServletRequest request, Menu record) {
		if(null==record.getIsallowchild()||record.getIsallowchild()==0) {
			record.setIsallowchild(Integer.valueOf(DictCache.getDefaultDictItem("SF").getDictitemcode()));
		}
		
		this.updateByPrimaryKeySelective(record);
		
		return ResultMessage.defaultSuccessMessage();
	}
	
	/**
	 * 递归查找所有子菜单，返回菜单ID数组
	 * @param list
	 * @param pid
	 * @return
	 */
	private List<Object> getChildMenuList(List<Menu> list,Long pid){
		List<Object> menus=new ArrayList<Object>();
		for(Menu m:list){
			if(m.getPid().longValue()==pid.longValue()){
				menus.add(m.getId());
				menus.addAll(this.getChildMenuList(list, m.getId()));
			}
		}
		return menus;
	}

	@Override
	public ResultMessage delete(List<Object> ids) {
		List<Object> childList=new ArrayList<Object>();
    	List<Menu> list=this.selectAll();
    	if(null!=ids&&ids.size()>0){
    		for(Object obj:ids){
    			childList.addAll(this.getChildMenuList(list, Long.valueOf((String)obj)));
    			childList.add(obj);
    		}
    	}
		
    	this.batchDelete(childList);
		//删除组织机构菜单
		this.organMenuService.deleteByMenuids(null,childList);
    	//删除角色菜单
		this.roleMenuService.deleteByMenuIds(null,childList);
		
		this.operationService.deleteByMenuids(childList);

		this.organOperationService.deleteByMenuids(null,childList);
		
		this.roleOperationService.deleteByMenuIds(null,childList);
		
		this.organDataAuthService.deleteByMenuids(childList);
		
		this.roleDataAuthService.deleteByMenuids(childList);
    	
    	return ResultMessage.defaultSuccessMessage();
	}
}
