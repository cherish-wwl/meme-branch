package com.meme.core.service;

import java.util.List;

import com.meme.core.base.CrudService;
import com.meme.core.base.IService;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.core.pojo.Organization;
import com.meme.core.pojo.Role;
import com.meme.core.service.extend.IOrganService;

public interface RoleService extends IService<Role,Form>,IOrganService<Role, Organization>,CrudService<Role>{

	/**
	 * 查询账号所拥有的角色
	 * @param loginid
	 * @return
	 */
	List<Role> selectRolesByLoginid(Object loginid);
	
	ResultMessage funcAuth(List<Object> menuids,List<Object> delmenuids,List<Object> operationids,List<Object> deloperationids,Long roleid);
}
