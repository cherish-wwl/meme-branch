package com.meme.core.validator;

import java.util.LinkedHashMap;
import java.util.Map;

import com.meme.core.mybatis.EntityColumn;
import com.meme.core.util.StringUtil;

/**
 * 规则链节点，抽象验证规则解析器抽象类
 * 
 * @author hzl
 * 
 */
public abstract class AbstractParser implements IParser,ITypeParser {

	/** 下一解析器 **/
	private AbstractParser parser;
	/** 验证规则键值对容器 **/
	protected Map<String, String> rules = new LinkedHashMap<String, String>();

	/**
	 * 生成验证规则
	 * 
	 * @param column
	 * @return
	 */
	public abstract Map<String, String> validate(EntityColumn column);

	@Override
	public Class<?> parseType(String type) {
		if(StringUtil.isEmpty(type)) return null;
		//带包名的全称类型或者类名简称识别是否与解析器节点类一致
		boolean flag=type.indexOf(".")>=0?this.getType().getName().equalsIgnoreCase(type):this.getType().getSimpleName().equalsIgnoreCase(type);
		if(flag) {
			return this.getType();
		}else{
			if(null!=parser) return parser.parseType(type);
			else return null;
		}
	}

	@Override
	public boolean accept(Class<?> clazz, EntityColumn column) {
		if (null == clazz) return false;
		// 主键不解析
		if (column.getIsPrimaryKey()) return false;
		// byte数组类型不解析(数据表转java实体时longblob数据库类型转成byte数组)
		if (null != column.getJavaType().getComponentType()) {
			return false;
		}
		if (column.getJavaType().getName().equals(clazz.getName())) {
			return true;
		}
		return false;
	}

	@Override
	public Map<String, String> parse(EntityColumn column) {
		// 解析前清空规则容器
		this.getRules().clear();
		// 通过则在当前解析器生成验证规则，否则转到下一链接解析器解析
		if (this.accept(this.getType(), column)) {
			return this.validate(column);
		} else {
			if (null != parser) return parser.parse(column);
			else return null;
		}
	}

	public AbstractParser getParser() {
		return parser;
	}

	public void setParser(AbstractParser parser) {
		this.parser = parser;
	}

	public Map<String, String> getRules() {
		return rules;
	}

	public void setRules(Map<String, String> rules) {
		this.rules = rules;
	}
}
