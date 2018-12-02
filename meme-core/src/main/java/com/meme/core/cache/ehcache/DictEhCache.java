package com.meme.core.cache.ehcache;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Element;

public class DictEhCache extends AbstractEhCache {

	public DictEhCache() {
		super();
	}

	public DictEhCache(String categoryName) {
		super();
		this.setCategoryName(categoryName);
	}

	@Override
	public String getCacheName() {
		return EhCacheType.DICT_CACHE.getType();
	}

	@Override
	public boolean isEternal() {
		return true;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Map getCacheList() {
		Map<String,LinkedHashMap> map=new LinkedHashMap<String,LinkedHashMap>();
		List keys=this.getCache().getKeys();
		for(Object obj:keys){
			Element categoryElement=this.getCache().get(obj.toString());
			if(null!=categoryElement){
				Object value=categoryElement.getObjectValue();
				if(null!=value){
					map.put(obj.toString(), (LinkedHashMap) value);
				}
			}
		}
		return null;
	}
}
