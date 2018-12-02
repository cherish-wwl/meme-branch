package com.meme.core.cache.ehcache.initializer;

import java.util.List;

import org.springframework.stereotype.Component;

import com.meme.core.cache.ICache;
import com.meme.core.cache.ICacheInitializer;
import com.meme.core.cache.ehcache.ParamsEhCache;
import com.meme.core.pojo.Params;

@Component
public class ParamsEhCacheInitializer extends ICacheInitializer {

	@Override
	public ICache getInstance() {
		if(null==this.getCache()){
			this.setCache(new ParamsEhCache());
		}
		return this.getCache();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void initCaches(List list, Class<?> clazz) {
		if (null != list && list.size() > 0) {
			for (Object obj : list) {
				Params param = (Params) obj;
				this.getInstance().put(param.getName(), param);
			}
		}
	}
}