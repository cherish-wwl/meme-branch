package com.meme.core.service;

import java.util.List;

import com.meme.core.base.IService;
import com.meme.core.form.Form;
import com.meme.core.pojo.Menu;
import com.meme.core.pojo.Operation;
import com.meme.core.pojo.Platform;
import com.meme.core.service.extend.IMenuService;
import com.meme.core.service.extend.IPlatformService;

public interface OperationService extends IService<Operation,Form>,IMenuService<Operation, Menu>,IPlatformService<Operation, Platform>{
	
	/**
	 * 根据菜单ID数组查询
	 * @param list
	 * @return
	 */
	List<Operation> selectByMenuids(List<Object> list);
	
	/**
	 * 查询单位授权的操作
	 * @param list
	 * @return
	 */
	List<Operation> selectOrganOperations(Object organid,List<Object> menuids,List<Object> platformids);
	
	/**
	 * 查询角色授权的操作
	 * @param list
	 * @return
	 */
	List<Operation> selectRoleOperations(List<Object> roleids,List<Object> menuids,List<Object> platformids);
	
	/**
	 * 根据主键ID数组查询记录
	 * @param list
	 * @return
	 */
	List<Operation> selectByids(List<Object> list);
}
