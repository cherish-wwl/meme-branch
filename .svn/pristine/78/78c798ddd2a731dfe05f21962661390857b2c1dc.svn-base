package com.meme.core.service;

import java.util.List;

import com.meme.core.base.CrudService;
import com.meme.core.base.IService;
import com.meme.core.form.Form;
import com.meme.core.pojo.Department;
import com.meme.core.pojo.Organization;
import com.meme.core.pojo.Position;
import com.meme.core.pojo.PositionView;
import com.meme.core.service.extend.IDepartmentService;
import com.meme.core.service.extend.IOrganService;

public interface PositionService extends IService<Position, Form>,
		IOrganService<Position, Organization>,
		IDepartmentService<Position, Department>, CrudService<Position> {

	/**
	 * 
	 * @param id
	 * @return
	 */
	PositionView selectPositionViewById(Object id);

	List<PositionView> selectByPaginationView(Form form);

	int countView(Form form);

	/**
	 * 查询单位或者部门职位记录
	 * 
	 * @param organid
	 * @param deptid
	 * @return
	 */
	List<Position> selectDeptPosition(Long organid, Long deptid);
}
