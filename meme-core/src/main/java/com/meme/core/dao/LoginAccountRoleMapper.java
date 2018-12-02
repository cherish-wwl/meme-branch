package com.meme.core.dao;

import java.util.List;

import com.meme.core.base.BaseMapper;
import com.meme.core.pojo.LoginAccountRoleView;

public interface LoginAccountRoleMapper extends BaseMapper{
	
	/**
	 * 查询用户所拥有的角色
	 * @param loginid
	 * @return
	 */
	List<LoginAccountRoleView> selectByLoginId(Long loginid);
	
	/**
	 * 用户ID数组所拥有的角色
	 * @param loginid
	 * @return
	 */
	List<LoginAccountRoleView> selectByLoginIds(List<Object> ids);
}