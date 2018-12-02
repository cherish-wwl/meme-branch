package com.meme.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meme.core.base.AbstractService;
import com.meme.core.dao.DepartmentMapper;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.core.mybatis.Criterion;
import com.meme.core.mybatis.MapperHelper;
import com.meme.core.mybatis.enums.Operator;
import com.meme.core.pojo.Department;
import com.meme.core.service.DepartmentService;
import com.meme.core.util.IDGenerator;

@Service("DepartmentService")
@Transactional
public class DepartmentServiceImpl extends AbstractService implements DepartmentService {

	@Resource
	private DepartmentMapper mapper;

	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Department record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(Department record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public Department selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Department record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Department record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Department> selectAll(){
		return (List<Department>) MapperHelper.toBeanList(this.mapper.selectAll(Department.class), Department.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Department> selectByPagination(Form form) {
		this.buildSearchCriterion(Department.class, form);
		this.buildOrderByCriterion(Department.class, form);
		this.buildLimitCriterion(Department.class, form);
		return (List<Department>) MapperHelper.toBeanList(this.mapper.selectByPagination(Department.class,this.getList()), Department.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(Department.class, form);
		return this.mapper.count(Department.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Department> selectByEntity(Department record) {
		return (List<Department>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), Department.class);
	}

	@Override
	public int batchInsert(List<Department> record) {
		return this.mapper.batchInsert(record,Department.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,Department.class);
	}

	@Override
	public int batchUpdate(List<Department> record) {
		return this.mapper.batchUpdate(record,Department.class);
	}

	@Override
	public int deleteByOrganids(List<Object> list) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("organid").operator(Operator.IN).leftValue(list).javaType(Long.class).build());
		return this.mapper.deleteByCriterions(criterions, Department.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Department> selectByOrganid(Object organid) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("organid").operator(Operator.EQUAL).leftValue(organid).javaType(Long.class).build());
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.ORDER_BY).condition("organid,pid,sortno").leftValue("asc").build());
		return (List<Department>) MapperHelper.toBeanList(this.mapper.selectByCriterions(criterions, Department.class),Department.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Department> selectByIds(List<Object> list) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("id").operator(Operator.IN).leftValue(list).javaType(Long.class).build());
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.ORDER_BY).condition("organid,pid,sortno").leftValue("asc").build());
		return (List<Department>) MapperHelper.toBeanList(this.mapper.selectByCriterions(criterions, Department.class),Department.class);
	}

	@Override
	public ResultMessage add(HttpServletRequest request, Department record) {
		long id=IDGenerator.generateId();
		record.setId(id);
		this.insertSelective(record);
		
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage edit(HttpServletRequest request, Department record) {
		this.updateByPrimaryKeySelective(record);
		
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage delete(List<Object> ids) {
		return null;
	}

	@Override
	public ResultMessage delete(List<Object> ids, Long organid) {
		List<Object> childList=new ArrayList<Object>();
    	List<Department> list=this.selectByOrganid(organid);
    	if(null!=ids&&ids.size()>0){
    		for(Object obj:ids){
    			List<Object> tmp=this.getChildList(list, Long.valueOf((String)obj));
    			childList.addAll(tmp);
    			childList.add(obj);
    		}
    	}
    	this.batchDelete(childList);
    	
    	return ResultMessage.defaultSuccessMessage();
	}
	
	/**
	 * 递归查找所有子菜单，返回菜单ID数组
	 * @param list
	 * @param pid
	 * @return
	 */
	private List<Object> getChildList(List<Department> list,Long pid){
		List<Object> objs=new ArrayList<Object>();
		for(Department obj:list){
			if(obj.getPid().longValue()==pid.longValue()){
				objs.add(obj.getId());
				objs.addAll(this.getChildList(list, obj.getId()));
			}
		}
		return objs;
	}
}
