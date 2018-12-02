package com.meme.core.validator;

import java.util.List;

import com.meme.core.mybatis.EntityColumn;
import com.meme.core.util.ClazzUtil;
import com.meme.core.validator.entity.ColumnRule;

/**
 * 解析请求处理类
 * 
 * @author hzl
 * 
 */
public class Handler {

	private static AbstractParser firstParser;
	
	static{
		firstParser=initParserChain();
	}
	
	/**
	 * 类名字符串解析成验证规则解析器可解析类名
	 * @param type
	 * @return
	 */
	public static Class<?> parseType(String type){
		if (null == firstParser) firstParser=initParserChain();
		return firstParser.parseType(type);
	}
	
	/**
	 * 处理解析请求
	 * 
	 * @param column
	 * @return
	 */
	public static ColumnRule handleRequest(EntityColumn column) {
		if (null == firstParser) firstParser=initParserChain();
		return new ColumnRule(column.getColumn(),firstParser.parse(column));
	}
	
	/**
	 * 初始化规则解析链，返回规则链起始节点
	 * @return
	 */
	public static AbstractParser initParserChain(){
		List<Class<?>> list = getClasses();
		AbstractParser firstParser = null;
		AbstractParser parser = null;
		if (null != list && list.size() > 0) {
			try {
				firstParser = (AbstractParser) list.get(0).newInstance();
				for (int i = 0; i < list.size(); i++) {
					if (i + 1 < list.size()) {
						if(i==0) parser = (AbstractParser) list.get(i).newInstance();
						AbstractParser nextParser = (AbstractParser) list.get(i + 1).newInstance();
						parser.setParser(nextParser);
						if(i==0) firstParser.setParser(nextParser);
						parser = nextParser;
					}
				}
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		if (null != firstParser) return firstParser;
		return null;
	}

	/**
	 * 获取解析器抽象类所在包及其子包下的所有扩展实现类<br/>
	 * 使用spring配置文件灵活配置解析器数组，可扩展实现自己的解析规则
	 * @return
	 */
	public static List<Class<?>> getClasses() {
		List<Class<?>> list = ClazzUtil.getAllClassByAbstractClass(AbstractParser.class);
		return list;
	}
}
