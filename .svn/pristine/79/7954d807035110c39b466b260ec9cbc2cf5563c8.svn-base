package com.meme.core.cache;

import java.util.Map;

/**
 * 缓存接口类
 * @author hzl
 *
 */
public interface ICache {
	
	/**
	 * 设置缓存名称
	 * @return
	 */
	String getCacheName();
	
	/**
	 * 获取缓存分类
	 * @return
	 */
	String getCategoryName();
	
	/**
	 * 获取缓存分类
	 * @return
	 */
	void setCategoryName(String categoryName);
	
	/**
	 * 是否永久不过期
	 * @return
	 */
	boolean isEternal();
	
	/**
	 * 写入缓存项
	 * @param key 缓存项键值
	 * @param value 缓存项
	 * @return
	 */
	void put(Object key,Object value);
	
	/**
	 * 删除缓存项
	 * @param key 缓存项键值
	 * @return
	 */
	void remove(Object key);
	
	/**
	 * 删除整个分类的缓存
	 * @param categoryName
	 */
	void removeAll(String categoryName);
	
	/**
	 * 获取缓存项
	 * @param key
	 * @return
	 */
	Object get(Object key);
	
	/**
	 * 无默认缓存分类时，根据分类和键值查询缓存项
	 * @param categoryName
	 * @param key
	 * @return
	 */
	Object get(String categoryName,Object key);
	
	/**
	 * 获取categoryName分类的所有缓存项
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	Map getCacheList();
	
	/**
	 * 获取categoryName分类的所有缓存项
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	Map getCacheList(String categoryName);
}
