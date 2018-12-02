package com.meme.core.service;

import java.util.List;

import com.meme.core.base.IService;
import com.meme.core.form.Form;
import com.meme.core.pojo.OrganMenu;
import com.meme.core.pojo.Organization;
import com.meme.core.service.extend.IOrganService;

public interface OrganMenuService extends IService<OrganMenu, Form>,
		IOrganService<OrganMenu, Organization> {

	/**
	 * 根据菜单ID数组删除记录
	 * 
	 * @param list
	 * @return
	 */
	int deleteByMenuids(Object organid, List<Object> list);

	/**
	 * 根据平台ID数组删除记录
	 * 
	 * @param list
	 * @return
	 */
	int deleteByPlatformids(Object organid, List<Object> list);
}
