package com.meme.core.cache;

import java.util.List;

/**
 * 缓存工厂接口
 * 
 * @author hzl
 * 
 */
public abstract class ICacheInitializer {

	private ICache cache;

	/**
	 * 获取缓存实例
	 * 
	 * @return
	 */
	public abstract ICache getInstance();

	/**
	 * 初始化缓存
	 * 
	 * @param list
	 * @param clazz
	 */
	@SuppressWarnings("rawtypes")
	public abstract void initCaches(List list, Class<?> clazz);

	public ICache getCache() {
		return cache;
	}

	public void setCache(ICache cache) {
		this.cache = cache;
	}
}