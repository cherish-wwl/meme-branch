package com.meme.core.service;

import com.meme.core.base.IService;
import com.meme.core.form.Form;
import com.meme.core.pojo.Menu;
import com.meme.core.pojo.OrganDataAuth;
import com.meme.core.pojo.Organization;
import com.meme.core.service.extend.IMenuService;
import com.meme.core.service.extend.IOrganService;

public interface OrganDataAuthService extends IService<OrganDataAuth, Form>,
		IMenuService<OrganDataAuth, Menu>,
		IOrganService<OrganDataAuth, Organization> {

}
