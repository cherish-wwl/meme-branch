package com.meme.core.dao;

import java.util.List;

import com.meme.core.base.BaseMapper;
import com.meme.core.pojo.Role;

public interface RoleMapper extends BaseMapper{
	
	/**
	 * 查询账号所拥有的角色
	 * @param loginid
	 * @return
	 */
	List<Role> selectRolesByLoginid(Object loginid);
}