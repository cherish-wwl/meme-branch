package com.meme.core.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.meme.core.cache.ehcache.initializer.DictEhCacheInitializer;
import com.meme.core.pojo.DictItemView;
import com.meme.core.util.StringUtil;

/**
 * 数据字典缓存类
 * 
 * @author hzl
 * 
 */
@Component
public class DictCache extends ICacheFactory{

	private static DictCache dictCache;

	@PostConstruct
	@Override
	public void init() {
		dictCache = this;
		this.initializer=new DictEhCacheInitializer();
		dictCache.initializer = this.initializer;
	}
	
	public static ICacheInitializer getDictInitializer() {
		return dictCache.initializer;
	}

	/**
	 * 根据字典码读取字典项数组
	 * 
	 * @param dictgroupCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ConcurrentHashMap<String, DictItemView> getDictItemList(String dictgroupCode) {
		ConcurrentHashMap<String, DictItemView> map = (ConcurrentHashMap<String, DictItemView>) dictCache.initializer.getInstance().getCacheList(dictgroupCode);
		return map;
	}

	/**
	 * 通过字典组编码和字典项编码获取字典项
	 * 
	 * @param dictgroupCode
	 * @param dictitemCode
	 * @return
	 */
	public static DictItemView getDictItem(String dictgroupCode, String dictitemCode) {
		Object obj=dictCache.initializer.getInstance().get(dictgroupCode, dictitemCode);
		if(null!=obj) return (DictItemView) obj;
		else return null;
	}

	/**
	 * 获取默认选中值，无默认时取排序号最小为默认
	 * 
	 * @param dictgroupCode
	 * @return
	 */
	public static DictItemView getDefaultDictItem(String dictgroupCode) {
		ConcurrentHashMap<String, DictItemView> map = getDictItemList(dictgroupCode);
		DictItemView item = null;
		String key = null;
		int i = 0;
		for (Map.Entry<String, DictItemView> entry : map.entrySet()) {
			if (i == 0) key = entry.getKey();
			if (entry.getValue().getIsdefault() == 1) {
				item = entry.getValue();
				break;
			}
			i++;
		}
		if (null == item && !StringUtil.isEmpty(key)) item = map.get(key);
		return item;
	}
}
