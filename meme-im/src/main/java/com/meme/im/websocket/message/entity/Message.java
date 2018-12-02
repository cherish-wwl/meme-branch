package com.meme.im.websocket.message.entity;

public class Message extends BaseMessage {

	private static final long serialVersionUID = 1L;

	public Message(){
		
	}
	
	public Message(String event) {
		super(event);
	}

	public Message(String event, Object data) {
		super(event, data);
	}
}