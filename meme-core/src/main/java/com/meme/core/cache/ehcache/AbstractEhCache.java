package com.meme.core.cache.ehcache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;

import com.meme.core.cache.ICache;

public abstract class AbstractEhCache implements ICache {

	private Cache cache = null;
	private String categoryName = null;
	
	public AbstractEhCache() {
		CacheManager cacheManager = CacheManager.getInstance();
		if (null == cacheManager.getCache(this.getCacheName())) {
			// maxElementsInMemory默认为10000
			CacheConfiguration config = new CacheConfiguration(this.getCacheName(), 10000);
			config.setEternal(this.isEternal());
			// 内存缓存数超过maxElementsInMemory设定值时超过的数量溢出到磁盘
			config.setOverflowToDisk(true);
			cache = new Cache(config);
			cacheManager.addCache(cache);
		} else {
			cache = cacheManager.getCache(this.getCacheName());
		}
	}

	public Cache getCache() {
		return cache;
	}

	public void setCache(Cache cache) {
		this.cache = cache;
	}
	
	@Override
	public String getCategoryName() {
		return categoryName;
	}

	@Override
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public synchronized void put(Object key, Object value) {
		Element categoryElement=this.getCache().get(this.getCategoryName());
		//使用线程安全容器，避免批量操作报thread unsafe异常
		ConcurrentHashMap<Object,Object> map=null;
		if(null==categoryElement){
			map=new ConcurrentHashMap<Object, Object>();
			map.put(key, value);
		}else{
			Object obj=categoryElement.getObjectValue();
			if(null==obj){
				map=new ConcurrentHashMap<Object, Object>();
				map.put(key, value);
			}else{
				map=(ConcurrentHashMap<Object,Object>) obj;
				map.put(key, value);
			}
		}
		Element element=new Element(this.getCategoryName(),map);
		element.setEternal(this.isEternal());
		this.getCache().put(element);
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized void remove(Object key) {
		Element categoryElement=this.getCache().get(this.getCategoryName());
		if(null!=categoryElement){
			Object obj=categoryElement.getObjectValue();
			if(null!=obj){
				ConcurrentHashMap<Object,Object> map=(ConcurrentHashMap<Object,Object>) obj;
				map.remove(key);
				Element element=new Element(this.getCategoryName(),map);
				this.getCache().put(element);
			}
		}
	}
	
	@Override
	public synchronized void removeAll(String categoryName){
		this.getCache().remove(categoryName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object get(Object key) {
		Element categoryElement=this.getCache().get(this.getCategoryName());
		if(null!=categoryElement){
			Object obj=categoryElement.getObjectValue();
			if(null!=obj){
				return ((Map<Object,Object>) obj).get(key);
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object get(String categoryName,Object key){
		Element categoryElement=this.getCache().get(categoryName);
		if(null!=categoryElement){
			Object obj=categoryElement.getObjectValue();
			if(null!=obj){
				return ((Map<Object,Object>) obj).get(key);
			}
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map getCacheList() {
		Element categoryElement=this.getCache().get(this.getCategoryName());
		if(null!=categoryElement){
			Object obj=categoryElement.getObjectValue();
			if(null!=obj){
				return (Map) obj;
			}
		}
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Map getCacheList(String categoryName){
		Element categoryElement=this.getCache().get(categoryName);
		if(null!=categoryElement){
			Object obj=categoryElement.getObjectValue();
			if(null!=obj){
				return (Map) obj;
			}
		}
		return null;
	}
}
