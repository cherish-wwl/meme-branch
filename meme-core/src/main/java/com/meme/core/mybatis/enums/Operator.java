package com.meme.core.mybatis.enums;

/**
 * sql操作符
 * @author hzl
 *
 */
public enum Operator {

	IS_NULL("is null"),
	IS_NOT_NULL("is not null"),
	IS_EMPTY("is empty"),
	IS_NOT_EMPTY("is not empty"),
	EQUAL("="),
	NOT_EQUAL("<>"),
	BIG_THAN(">"),
	BIG_EQUAL_THAN(">="),
	LESS_THAN("<"),
	LESS_EQUAL_THAN("<="),
	IN("in"),
	NOT_IN("not in"),
	BETWEEN("between"),
	NOT_BETWEEN("not between"),
	LIKE("like"),
	NOT_LIKE("not like"),
	AND("and"),
	OR("or"),
	LEFT_BRACKET("("),
	RIGHT_BRACKET(")"),
	ORDER_BY(" ORDER BY "),
	LIMIT(" LIMIT "),
	CONDITION("CONDITION");
	
	private String operator;
	
	private Operator(String operator){
		this.operator=operator;
	}
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
}
