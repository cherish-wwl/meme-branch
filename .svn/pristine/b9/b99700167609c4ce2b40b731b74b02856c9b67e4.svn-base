package com.meme.core.base;

import java.util.ArrayList;
import java.util.List;

import com.meme.core.mybatis.Criterion;
import com.meme.core.mybatis.Criterion.CriterionBuilder;
import com.meme.core.mybatis.EntityColumn;
import com.meme.core.mybatis.MapperHelper;
import com.meme.core.mybatis.enums.Operator;
import com.meme.core.util.StringUtil;

/**
 * service抽象类，生成默认分页查询规则
 * @author hzl
 *
 */
public abstract class AbstractService {
	private List<Criterion> list=new ArrayList<Criterion>();
	
	private BaseForm form;
	
	/**
	 * 组装查询条件，子类可重写此方法实现对查询条件自由配置
	 * @param entityClass
	 * @param form
	 */
	public <K> void buildSearchCriterion(Class<?> entityClass,K form){
		try {
			this.init(entityClass, form);
			this.buildDataAuthCriterion(entityClass, form);
			this.defaultCriterions(entityClass, form);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化form查询类实例
	 * @param entityClass
	 * @param form
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	protected <K> void init(Class<?> entityClass,K form) throws IllegalArgumentException, IllegalAccessException{
		this.list.clear();
		this.form=(BaseForm) form;
		this.form.init();
	}
	
	protected <K> void buildDataAuthCriterion(Class<?> entityClass,K form){
		if(!StringUtil.isEmpty(this.getForm().getSqlcondition())){
			list.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			list.add(new Criterion.CriterionBuilder().operator(Operator.CONDITION).sqlcondition(this.getForm().getSqlcondition()).build());
		}
	}
	
	/**
	 * 组装默认查询条件
	 * 生成类似and (name LIKE CONCAT('%', #{form.search},'%')  or description LIKE CONCAT('%', #{form.search},'%'))查询条件
	 * @param entityClass
	 * @param form
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	protected <K> void defaultCriterions(Class<?> entityClass,K form) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException{
		if(StringUtil.isAllNotEmpty(this.getForm().getSearchKey())){
			String tableName=MapperHelper.getTableName(entityClass);
			List<EntityColumn> cols=MapperHelper.getTableColumnsWithBlobs(entityClass);
			list.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			list.add(new Criterion.CriterionBuilder().operator(Operator.LEFT_BRACKET).build());
			int i=0;
			for(EntityColumn col:cols){
				if(col.getJavaType()!=null) {
					if(col.getJavaType().equals(String.class)){
						if(i==0) list.add(new Criterion.CriterionBuilder().condition(tableName+"."+col.getColumn()).operator(Operator.LIKE).leftValue(this.getForm().getSearchKey()).javaType(col.getJavaType()).build());
						else{
							list.add(new Criterion.CriterionBuilder().operator(Operator.OR).build());
							list.add(new Criterion.CriterionBuilder().condition(tableName+"."+col.getColumn()).operator(Operator.LIKE).leftValue(this.getForm().getSearchKey()).javaType(col.getJavaType()).build());
						}
						i++;
					}
				}
			}
			list.add(new Criterion.CriterionBuilder().operator(Operator.RIGHT_BRACKET).build());
		}
	}
	
	/**
	 * 构建order by子句
	 * @param entityClass
	 * @param form
	 */
	protected <K> void buildOrderByCriterion(Class<?> entityClass,K form){
		if(StringUtil.isAllNotEmpty(this.getForm().getSort())){
			CriterionBuilder builder=new Criterion.CriterionBuilder();
			builder.operator(Operator.ORDER_BY).condition(this.getForm().getSort());
			if(StringUtil.isAllNotEmpty(this.getForm().getOrder())){
				builder.leftValue(this.getForm().getOrder());
			}
			list.add(builder.build());
		}
	}
	
	/**
	 * 构建limit子句
	 * @param entityClass
	 * @param form
	 */
	protected <K> void buildLimitCriterion(Class<?> entityClass,K form){
		list.add(new Criterion.CriterionBuilder().operator(Operator.LIMIT).leftValue(this.getForm().getOffset()).rightValue(this.getForm().getLimit()).build());
	}
	
	public List<Criterion> getList() {
		return list;
	}

	public void setList(List<Criterion> list) {
		this.list = list;
	}

	public BaseForm getForm() {
		return form;
	}

	public void setForm(BaseForm form) {
		this.form = form;
	}
}
