package com.meme.core.service;

import java.util.List;

import com.meme.core.base.IService;
import com.meme.core.form.Form;
import com.meme.core.pojo.OrganOperation;
import com.meme.core.pojo.Organization;
import com.meme.core.service.extend.IOrganService;

public interface OrganOperationService extends IService<OrganOperation, Form>,
		IOrganService<OrganOperation, Organization> {

	/**
	 * 根据菜单ID数组删除记录
	 * 
	 * @param list
	 * @return
	 */
	public int deleteByMenuids(Object organid, List<Object> list);

	/**
	 * 根据平台ID数组删除记录
	 * 
	 * @param list
	 * @return
	 */
	int deleteByPlatformids(Object organid, List<Object> list);

	/**
	 * 根据操作ID数组删除记录
	 * 
	 * @param list
	 * @return
	 */
	int deleteByOperationids(Object organid, List<Object> list);
}
