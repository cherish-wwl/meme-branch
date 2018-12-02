package com.meme.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.meme.core.base.BaseMapper;
import com.meme.core.pojo.Menu;

public interface MenuMapper extends BaseMapper{

	/**
	 * 根据角色ID数组、平台ID、父菜单ID查询菜单，父菜单ID为空查询所有授权菜单
	 * @return
	 */
	List<Menu> selectLoginAccountMenus(@Param("roleids")List<Object> roleids,@Param("platformid") Object platformid,@Param("pid")Object pid);
	
	/**
	 * 查询单位授权菜单
	 * @param organid
	 * @param platformids
	 * @return
	 */
	List<Menu> selectOrganMenus(@Param("organid")Object organid,@Param("platformids") List<Object> platformids);
	
	/**
	 * 查询角色授权菜单
	 * @param roleid
	 * @param platformids
	 * @return
	 */
	List<Menu> selectRoleMenus(@Param("roleids")List<Object> roleids,@Param("platformids") List<Object> platformids);
}