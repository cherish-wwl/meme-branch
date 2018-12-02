package com.meme.im.websocket.message.entity;

import com.alibaba.fastjson.JSONObject;

public class BaseMessage implements IMessage {

	private static final long serialVersionUID = 1L;

	private String event;
	private Object data;

	public BaseMessage(){
		
	}
	
	public BaseMessage(String event) {
		this.event = event;
	}

	public BaseMessage(String event, Object data) {
		this.event = event;
		this.data = data;
	}

	@Override
	public String getEvent() {
		return this.event;
	}

	@Override
	public void setEvent(String event) {
		this.event = event;
	}

	@Override
	public Object getData() {
		return this.data;
	}

	@Override
	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
}
