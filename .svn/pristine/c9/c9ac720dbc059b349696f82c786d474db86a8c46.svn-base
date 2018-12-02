package com.meme.core.cache.ehcache.initializer;

import java.util.List;

import org.springframework.stereotype.Component;

import com.meme.core.cache.ICache;
import com.meme.core.cache.ICacheInitializer;
import com.meme.core.cache.ehcache.DictEhCache;
import com.meme.core.pojo.DictItemView;

@Component
public class DictEhCacheInitializer extends ICacheInitializer {

	@Override
	public ICache getInstance() {
		if(null==this.getCache()){
			this.setCache(new DictEhCache());
		}
		return this.getCache();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void initCaches(List list, Class<?> clazz) {
		if (null != list && list.size() > 0) {
			for (Object obj : list) {
				DictItemView dictitem = (DictItemView) obj;
				// 缓存分类为字典组编码
				this.getInstance().setCategoryName(dictitem.getDictgroupcode());
				this.getInstance().put(dictitem.getDictitemcode(), dictitem);
			}
		}
	}
}