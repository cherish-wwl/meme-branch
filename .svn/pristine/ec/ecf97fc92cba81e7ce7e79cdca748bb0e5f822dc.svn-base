package com.meme.core.cache.ehcache;



/**
 * 数据表缓存类
 * @author hzl
 *
 */
public class TableEhCache extends AbstractEhCache{

	public TableEhCache() {
		super();
	}

	public TableEhCache(String categoryName) {
		super();
		this.setCategoryName(categoryName);
	}

	@Override
	public String getCacheName() {
		return EhCacheType.TABLE_CACHE.getType();
	}

	@Override
	public boolean isEternal() {
		return true;
	}
}
