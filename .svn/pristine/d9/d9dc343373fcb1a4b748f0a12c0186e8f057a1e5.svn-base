package com.meme.core.service;

import java.util.List;

import com.meme.core.base.CrudService;
import com.meme.core.base.IService;
import com.meme.core.form.Form;
import com.meme.core.pojo.Platform;

public interface PlatformService extends IService<Platform,Form>,CrudService<Platform>{

	/**
	 * 根据角色ID数组查询授权的平台
	 * @param roleids
	 * @return
	 */
	List<Platform> selectByRoleids(List<Object> roleids);
	
	/**
	 * 根据组织机构ID查询授权的平台
	 * @param platformids
	 * @return
	 */
	List<Platform> selectByOrganid(Object organid);
}
