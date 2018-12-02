package com.meme.im.redis;

import java.io.Serializable;

import org.springframework.web.socket.adapter.standard.StandardWebSocketSession;

import com.meme.im.pojo.Member;
import com.meme.im.websocket.WebsocketSessionWrapper;

public class RedisWebSocketSession extends WebsocketSessionWrapper implements Serializable {

	private static final long serialVersionUID = 1L;
	private Member member;

	public RedisWebSocketSession(StandardWebSocketSession session, Member member) {
		super(session);
		this.member = member;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
}