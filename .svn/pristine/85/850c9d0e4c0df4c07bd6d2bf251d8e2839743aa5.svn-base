package com.meme.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meme.core.base.AbstractService;
import com.meme.core.dao.PositionMapper;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.core.mybatis.Criterion;
import com.meme.core.mybatis.MapperHelper;
import com.meme.core.mybatis.enums.Operator;
import com.meme.core.pojo.Position;
import com.meme.core.pojo.PositionView;
import com.meme.core.service.PositionService;
import com.meme.core.util.IDGenerator;
import com.meme.core.util.StringUtil;

@Service("PositionService")
@Transactional
public class PositionServiceImpl extends AbstractService implements PositionService {

	@Resource
	private PositionMapper mapper;

	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Position record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(Position record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public Position selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Position record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Position record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Position> selectAll(){
		return (List<Position>) MapperHelper.toBeanList(this.mapper.selectAll(Position.class), Position.class);
	}
	
	public void buildSearchCriterion(Class<?> entityClass, Form form) {
		super.buildSearchCriterion(entityClass, form);
		super.getList().add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		super.getList().add(new Criterion.CriterionBuilder().condition("organid").operator(Operator.EQUAL).leftValue(Long.valueOf(form.getOrganid().toString())).javaType(Long.class).build());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Position> selectByPagination(Form form) {
		this.buildSearchCriterion(Position.class, form);
		this.buildOrderByCriterion(Position.class, form);
		this.buildLimitCriterion(Position.class, form);
		return (List<Position>) MapperHelper.toBeanList(this.mapper.selectByPagination(Position.class,this.getList()), Position.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(Position.class, form);
		return this.mapper.count(Position.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Position> selectByEntity(Position record) {
		return (List<Position>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), Position.class);
	}

	@Override
	public int batchInsert(List<Position> record) {
		return this.mapper.batchInsert(record,Position.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,Position.class);
	}

	@Override
	public int batchUpdate(List<Position> record) {
		return this.mapper.batchUpdate(record,Position.class);
	}

	@Override
	public int deleteByOrganids(List<Object> list) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("organid").operator(Operator.IN).leftValue(list).javaType(Long.class).build());
		return this.mapper.deleteByCriterions(criterions, Position.class);
	}

	@Override
	public int deleteByDepartmentids(List<Object> list) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("deptid").operator(Operator.IN).leftValue(list).javaType(Long.class).build());
		return this.mapper.deleteByCriterions(criterions, Position.class);
	}

	@Override
	public PositionView selectPositionViewById(Object id) {
		return this.mapper.selectPositionViewById(id);
	}

	@Override
	public List<PositionView> selectByPaginationView(Form form) {
		return this.mapper.selectByPaginationView(form);
	}

	@Override
	public int countView(Form form) {
		return this.mapper.countView(form);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Position> selectDeptPosition(Long organid, Long deptid) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("organid").operator(Operator.EQUAL).leftValue(organid).javaType(Long.class).build());
		if(null!=deptid&&deptid!=0l){
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			criterions.add(new Criterion.CriterionBuilder().condition("deptid").operator(Operator.EQUAL).leftValue(deptid).javaType(Long.class).build());
		}else{
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.LEFT_BRACKET).build());
			criterions.add(new Criterion.CriterionBuilder().condition("deptid").operator(Operator.IS_NULL).build());
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.OR).build());
			criterions.add(new Criterion.CriterionBuilder().condition("deptid").operator(Operator.EQUAL).leftValue(0l).javaType(Long.class).build());
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.RIGHT_BRACKET).build());
		}
		return (List<Position>) MapperHelper.toBeanList(this.mapper.selectByCriterions(criterions, Position.class), Position.class);
	}

	@Override
	public ResultMessage add(HttpServletRequest request, Position position) {
		if(StringUtil.isEmpty(position.getName())) return ResultMessage.failed("职位名称不能为空");
		if(position.getOrganid()==null||position.getOrganid().longValue()==0l) return ResultMessage.failed("职位所属单位ID不能为空");
		
		Position obj=new Position();
		obj.setName(position.getName());
		obj.setOrganid(position.getOrganid());
		List<Position> list=this.selectByEntity(obj);
		if(null!=list&&list.size()>0) return ResultMessage.failed("此单位职位名称重复");
		
		long id=IDGenerator.generateId();
		position.setId(id);
		this.insertSelective(position);
		
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage edit(HttpServletRequest request, Position position) {
		if(StringUtil.isEmpty(position.getName())) return ResultMessage.failed("职位名称不能为空");
		if(position.getOrganid()==null||position.getOrganid().longValue()==0l) return ResultMessage.failed("职位所属单位ID不能为空");
		Position obj=new Position();
		obj.setName(position.getName());
		obj.setOrganid(position.getOrganid());
		List<Position> list=this.selectByEntity(obj);
		if(null!=list&&list.size()>0) {
			if(list.get(0).getId().longValue()!=position.getId().longValue()){
				return ResultMessage.failed("此单位职位名称重复");
			}
		}
		
		this.updateByPrimaryKeySelective(position);
		
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage delete(List<Object> ids) {
		this.batchDelete(ids);
    	
    	return ResultMessage.defaultSuccessMessage();
	}

}
