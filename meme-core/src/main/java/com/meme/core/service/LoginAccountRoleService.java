package com.meme.core.service;

import java.util.List;

import com.meme.core.base.IService;
import com.meme.core.form.Form;
import com.meme.core.pojo.LoginAccountRole;
import com.meme.core.pojo.LoginAccountRoleView;
import com.meme.core.pojo.Organization;
import com.meme.core.service.extend.IOrganService;

public interface LoginAccountRoleService extends IService<LoginAccountRole,Form>,IOrganService<LoginAccountRole,Organization>{
	
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
	
	/**
	 * 根据角色ID数组删除记录
	 * @param list
	 * @return
	 */
	int deleteByroleids(Object loginid,List<Object> list);
	
	/**
	 * 根据账号ID数组删除记录
	 * @param list
	 * @return
	 */
	int deleteByLoginids(List<Object> list);
}
