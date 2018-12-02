package com.meme.core.service;

import java.util.List;

import com.meme.core.base.CrudService;
import com.meme.core.base.IService;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.core.pojo.Department;
import com.meme.core.pojo.Organization;
import com.meme.core.service.extend.IOrganService;

public interface DepartmentService extends IService<Department,Form>,IOrganService<Department,Organization>,CrudService<Department>{
	
	/**
	 * 查询单位部门记录
	 * @param list
	 * @return
	 */
	List<Department> selectByOrganid(Object organid);
	
	/**
	 * 根据主键数组查询记录
	 * @param list
	 * @return
	 */
	List<Department> selectByIds(List<Object> list);
	
	ResultMessage delete(List<Object> ids,Long organid);
}
