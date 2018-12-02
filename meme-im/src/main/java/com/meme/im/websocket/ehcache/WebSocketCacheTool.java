package com.meme.im.websocket.ehcache;

import java.util.Map;

import org.springframework.web.socket.WebSocketSession;

import com.meme.core.cache.ICache;

public class WebSocketCacheTool {
	public static final ICache cache = new OnlineMemberCache();
	private static WebSocketCacheTool instance;

	private WebSocketCacheTool() {
	}

	public static WebSocketCacheTool getInstance() {
		if (null == instance) {
			synchronized (WebSocketCacheTool.class) {
				if (null == instance) {
					instance = new WebSocketCacheTool();
				}
			}
		}
		return instance;
	}

	public void put(String memberid, WebSocketSession session) {
		cache.put(memberid, session);
	}

	public void remove(String memberid) {
		cache.remove(memberid);
	}
	
	@SuppressWarnings("rawtypes")
	public int getTotal(){
		Map map=cache.getCacheList();
		if(null!=map) return map.size();
		return 0;
	}
	
	public WebSocketSession getWebSocketSession(String memberid){
		Object obj=cache.get(memberid);
		if(null!=obj) return (WebSocketSession) obj;
		return null;
	}
}
