package com.meme.im.websocket;

import java.io.Serializable;

import org.springframework.web.socket.WebSocketSession;

import com.meme.im.pojo.Member;

/**
 *
 * @author hzl
 *
 */
public class ServerPoint implements Serializable{
	private static final long serialVersionUID = 1L;
	private Member member;
	private WebSocketSession session;

	public ServerPoint(Member member, WebSocketSession session) {
		this.member = member;
		this.session = session;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public WebSocketSession getSession() {
		return session;
	}

	public void setSession(WebSocketSession session) {
		this.session = session;
	}
}
