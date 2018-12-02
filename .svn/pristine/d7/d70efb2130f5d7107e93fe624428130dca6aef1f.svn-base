package com.meme.core.service;

import java.util.List;

import com.meme.core.base.CrudService;
import com.meme.core.base.IService;
import com.meme.core.form.Form;
import com.meme.core.pojo.Menu;
import com.meme.core.pojo.Platform;
import com.meme.core.service.extend.IPlatformService;

public interface MenuService extends IService<Menu,Form>,IPlatformService<Menu, Platform>,CrudService<Menu>{
	
	/**
	 * 根据平台ID数组查询
	 * @param list
	 * @return
	 */
	List<Menu> selectByPlatformids(List<Object> list);
	
	/**
	 * 根据角色ID数组、平台ID、父菜单ID查询菜单，父菜单ID为空查询所有授权菜单
	 * @return
	 */
	List<Menu> selectLoginAccountMenus(List<Object> roleids,Object platformid,Object pid);
	
	/**
	 * 查询单位授权菜单
	 * @param organid
	 * @param platformids
	 * @return
	 */
	List<Menu> selectOrganMenus(Object organid,List<Object> platformids);
	
	/**
	 * 查询角色授权菜单
	 * @param roleid
	 * @param platformids
	 * @return
	 */
	List<Menu> selectRoleMenus(List<Object> roleids,List<Object> platformids);
	
	/**
	 * 根据主键ID数组查询记录
	 * @param list
	 * @return
	 */
	List<Menu> selectByids(List<Object> list);
	
}
