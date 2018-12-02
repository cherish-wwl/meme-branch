package com.meme.core.service;

import java.util.List;

import com.meme.core.base.IService;
import com.meme.core.form.Form;
import com.meme.core.pojo.Organization;
import com.meme.core.pojo.Role;
import com.meme.core.pojo.RoleOperation;
import com.meme.core.service.extend.IOrganService;
import com.meme.core.service.extend.IRoleService;

public interface RoleOperationService extends IService<RoleOperation, Form>,
		IOrganService<RoleOperation, Organization>,
		IRoleService<RoleOperation, Role> {

	/**
	 * 根据菜单ID数组删除角色菜单
	 * 
	 * @param list
	 * @return
	 */
	int deleteByMenuIds(Object roleid, List<Object> list);

	/**
	 * 根据平台ID数组删除记录
	 * 
	 * @param list
	 * @return
	 */
	int deleteByPlatformids(Object roleid, List<Object> list);

	/**
	 * 根据操作ID数组删除记录
	 * 
	 * @param list
	 * @return
	 */
	int deleteByOperationids(Object roleid, List<Object> list);
}
