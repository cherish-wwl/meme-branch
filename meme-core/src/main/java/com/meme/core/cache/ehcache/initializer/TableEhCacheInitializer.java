package com.meme.core.cache.ehcache.initializer;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.springframework.stereotype.Component;

import com.meme.core.cache.ICache;
import com.meme.core.cache.ICacheInitializer;
import com.meme.core.cache.ehcache.TableEhCache;
import com.meme.core.mybatis.annotation.Table;

/**
 * 表数据缓存工厂类
 * 
 * @author hzl
 * 
 */
@Component
public class TableEhCacheInitializer extends ICacheInitializer {

	@Override
	public ICache getInstance() {
		if(null==this.getCache()){
			this.setCache(new TableEhCache());
		}
		return this.getCache();
	}
	
	/**
	 * 可重写此初始化方法，暂时只支持类中带注解@Table 并且表主键名与类属性名一致的表数据缓存
	 * @param list
	 * @param clazz
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void initCaches(List list, Class<?> clazz) {
		Table table = clazz.getAnnotation(Table.class);
		String id=table.id();
		String tableName=table.name();
		this.getInstance().setCategoryName(tableName);
		try {
			Field field=clazz.getDeclaredField(id);
			PropertyDescriptor descriptor=new PropertyDescriptor(field.getName(),clazz);
			Method getMethod=descriptor.getReadMethod();
			for(Object obj:list){
				Object value=getMethod.invoke(obj);
				this.getInstance().put(value, obj);
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}