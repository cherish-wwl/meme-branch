package com.meme.im.websocket.message.event;

public enum EventEnum {

	PING("ping"), 
	INIT("init"), 
	ADD("add"), 
	REMOVE("remove"), 
	MESSAGE("message"),
	HIDE("hide"), 
	OFFLINE("offline"), 
	ONLINE("online"), 
	ONLINECOUNT("onlineCount"), 
	LOGOUT("logout"),
	KICKOUT("kickout"),
	NOTICE("notice");
	
	private String event;

	private EventEnum(String event) {
		this.event = event;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

}
