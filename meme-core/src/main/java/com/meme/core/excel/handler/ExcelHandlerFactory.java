package com.meme.core.excel.handler;



/**
 * Handler类的工厂，反射创建类实例处理excel数据
 * @author hzl
 *
 * @param <T>
 */
public class ExcelHandlerFactory<T extends IExcelHandler> {

	@SuppressWarnings("unchecked")
	public T create(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		Class<?> clazz=Class.forName(className);
		return (T) clazz.newInstance();
	}
	
	public String execute(String className,String value) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		T t=this.create(className);
		return t.execute(value);
	}
}
