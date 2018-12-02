package com.meme.core.service;

import java.util.List;
import java.util.Map;

import com.meme.core.base.CrudService;
import com.meme.core.base.IService;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.core.pojo.Organization;

public interface OrganizationService extends IService<Organization,Form>,CrudService<Organization>{
	
	int updateByIds(Map<String,Object> params,List<Object> list);
	
	ResultMessage funcAuth(List<Object> menuids,List<Object> delmenuids,List<Object> operationids,List<Object> deloperationids,Long organid);
	
	ResultMessage auth(List<Object> ids);
	
	ResultMessage cancelAuth(List<Object> ids);
}
