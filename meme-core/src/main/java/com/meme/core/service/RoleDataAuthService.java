package com.meme.core.service;

import com.meme.core.base.IService;
import com.meme.core.form.Form;
import com.meme.core.pojo.Menu;
import com.meme.core.pojo.Organization;
import com.meme.core.pojo.Role;
import com.meme.core.pojo.RoleDataAuth;
import com.meme.core.service.extend.IMenuService;
import com.meme.core.service.extend.IOrganService;
import com.meme.core.service.extend.IRoleService;

public interface RoleDataAuthService extends IService<RoleDataAuth, Form>,
		IMenuService<RoleDataAuth, Menu>,
		IOrganService<RoleDataAuth, Organization>,
		IRoleService<RoleDataAuth, Role> {

}
