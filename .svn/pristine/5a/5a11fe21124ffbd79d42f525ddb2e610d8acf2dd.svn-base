package com.meme.core.dao;

import java.util.List;

import com.meme.core.base.BaseMapper;
import com.meme.core.pojo.Platform;

public interface PlatformMapper extends BaseMapper{
	
	/**
	 * 根据角色数组查询授权的平台
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