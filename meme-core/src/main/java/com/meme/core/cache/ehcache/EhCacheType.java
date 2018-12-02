package com.meme.core.cache.ehcache;

public enum EhCacheType {

	DICT_CACHE("DICT_CACHE"),
	TABLE_CACHE("TABLE_CACHE"),
	PARAMS_CACHE("PARAMS_CACHE");
	
	private String type;

	private EhCacheType(String type){
		this.type=type;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
