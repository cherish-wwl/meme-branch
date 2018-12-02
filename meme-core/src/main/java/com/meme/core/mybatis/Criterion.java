package com.meme.core.mybatis;

import java.util.List;

import com.meme.core.mybatis.enums.Operator;

/**
 * sql查询规则封装类
 * @author hzl
 *
 */
public class Criterion {

	private Operator operator;
	private Object leftValue;
	private Object rightValue;
	/**字段名字符串，多字段名逗号分隔**/
	private String condition;
	private Class<?> javaType;
	/**sql条件语句**/
	private String sqlcondition;

	private Criterion(CriterionBuilder b) throws Exception{
		if(null==b.operator) throw new Exception("查询操作符不能为空");
		this.operator=b.operator;
		this.leftValue=b.leftValue;
		this.rightValue=b.rightValue;
		this.condition=b.condition;
		this.javaType=b.javaType;
		this.sqlcondition=b.sqlcondition;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String toString() {
		StringBuffer sb=new StringBuffer();
		switch(this.operator){
			case IS_NULL:
			case IS_NOT_NULL:
			case IS_EMPTY:
			case IS_NOT_EMPTY:
				sb.append(this.condition);
				sb.append(" "+this.operator.getOperator()+" ");
				break;
			case EQUAL:
			case NOT_EQUAL:
				sb.append(this.condition);
				sb.append(" "+this.operator.getOperator()+" ");
				if(this.javaType.equals(String.class)) {
					sb.append("'"+this.leftValue+"'");
				}else {
					sb.append(this.leftValue);
				}
				break;
			case BIG_THAN:
			case BIG_EQUAL_THAN:
			case LESS_THAN:
			case LESS_EQUAL_THAN:
				sb.append(this.condition);
				sb.append(" "+this.operator.getOperator()+" ");
				sb.append(this.leftValue);
				break;
			case IN:
			case NOT_IN:
			{
				sb.append(" "+this.condition+" ");
				sb.append(" "+this.operator.getOperator()+" ");
				sb.append("(");
				if(this.javaType.equals(String.class)){
					List<String> list=(List<String>) this.leftValue;
					for(int i=0;i<list.size();i++){
						if(i==0) sb.append("'"+list.get(i)+"'");
						else sb.append(",'"+list.get(i)+"'");
					}
				}else{
					List<Object> list=(List<Object>) this.leftValue;
					for(int i=0;i<list.size();i++){
						if(i==0) sb.append(list.get(i));
						else sb.append(","+list.get(i));
					}
				}
				sb.append(")");
			}break;
			case BETWEEN:
			case NOT_BETWEEN:
			{
				sb.append(this.condition);
				sb.append(" "+this.operator.getOperator()+" ");
				sb.append(this.leftValue);
				sb.append(" AND ");
				sb.append(this.rightValue);
			}break;
			case LIKE:
			case NOT_LIKE:
				sb.append(this.condition);
				sb.append(" "+this.operator.getOperator()+" ");
				sb.append("'%");
				sb.append(this.leftValue);
				sb.append("%'");
				break;
			case AND:
			case OR:
			case LEFT_BRACKET:
			case RIGHT_BRACKET:
				sb.append(" "+this.operator.getOperator()+" ");
				break;
			case ORDER_BY:
				sb.append(" ").append(this.operator.getOperator()).append(" ").append(this.condition).append(" ").append(this.leftValue);
				break;
			case LIMIT:
				sb.append(" ").append(this.operator.getOperator()).append(" ").append(this.leftValue).append(",").append(this.rightValue);
				break;
			case CONDITION:
				sb.append(" ").append(this.sqlcondition).append(" ");
				break;
			default:
				break;
		}
		return sb.toString();
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public Object getLeftValue() {
		return leftValue;
	}

	public void setLeftValue(Object leftValue) {
		this.leftValue = leftValue;
	}

	public Object getRightValue() {
		return rightValue;
	}

	public void setRightValue(Object rightValue) {
		this.rightValue = rightValue;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public Class<?> getJavaType() {
		return javaType;
	}

	public void setJavaType(Class<?> javaType) {
		this.javaType = javaType;
	}

	public String getSqlcondition() {
		return sqlcondition;
	}

	public void setSqlcondition(String sqlcondition) {
		this.sqlcondition = sqlcondition;
	}

	public static class CriterionBuilder{
		private Operator operator;
		private Object leftValue;
		private Object rightValue;
		private String condition;
		private Class<?> javaType;
		private String sqlcondition;
		
		public CriterionBuilder(){
			
		}
		
		public CriterionBuilder operator(Operator operator){
			this.operator=operator;
			return this;
		}
		
		public CriterionBuilder leftValue(Object leftValue){
			this.leftValue=leftValue;
			return this;
		}
		
		public CriterionBuilder rightValue(Object rightValue){
			this.rightValue=rightValue;
			return this;
		}
		
		public CriterionBuilder condition(String condition){
			this.condition=condition;
			return this;
		}
		
		public CriterionBuilder javaType(Class<?> javaType){
			this.javaType=javaType;
			return this;
		}
		public CriterionBuilder sqlcondition(String sqlcondition){
			this.sqlcondition=sqlcondition;
			return this;
		}
		
		public Criterion build(){
			try {
				return new Criterion(this);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}
}
