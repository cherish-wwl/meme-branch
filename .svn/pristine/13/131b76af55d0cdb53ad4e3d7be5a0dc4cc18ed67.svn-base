package com.meme.core.cache;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.meme.core.cache.ehcache.initializer.ParamsEhCacheInitializer;
import com.meme.core.pojo.Params;

/**
 * 系统配置参数缓存类
 * @author hzl
 *
 */
@Component
public class ParamsCache extends ICacheFactory{

	private static ParamsCache paramsCache;

	@PostConstruct
	@Override
	public void init() {
		paramsCache = this;
		this.initializer=new ParamsEhCacheInitializer();
		paramsCache.initializer = this.initializer;
	}

	public static ICacheInitializer getParamsInitializer() {
		return paramsCache.initializer;
	}

	/**
	 * 从缓存读取系统配置参数
	 * 
	 * @param key
	 * @return
	 */
	public static Params get(String key) {
		Object obj=paramsCache.initializer.getInstance().get(key);
		if(null!=obj) return (Params) obj;
		return null;
	}
}
