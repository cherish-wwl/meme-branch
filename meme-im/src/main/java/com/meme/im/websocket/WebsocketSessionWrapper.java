package com.meme.im.websocket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketExtension;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.adapter.standard.StandardWebSocketSession;

public class WebsocketSessionWrapper implements WebSocketSession {

	private StandardWebSocketSession session;

	public WebsocketSessionWrapper() {
	}

	public WebsocketSessionWrapper(StandardWebSocketSession session) {
		this.session = session;
	}

	public WebSocketSession getSession() {
		return session;
	}

	public void setSession(StandardWebSocketSession session) {
		this.session = session;
	}

	@Override
	public void close() throws IOException {
		this.session.close();
	}

	@Override
	public void close(CloseStatus arg0) throws IOException {
		this.session.close(arg0);
	}

	@Override
	public String getAcceptedProtocol() {
		return this.session.getAcceptedProtocol();
	}

	@Override
	public Map<String, Object> getAttributes() {
		return this.session.getAttributes();
	}

	@Override
	public int getBinaryMessageSizeLimit() {
		return this.session.getBinaryMessageSizeLimit();
	}

	@Override
	public List<WebSocketExtension> getExtensions() {
		return this.session.getExtensions();
	}

	@Override
	public HttpHeaders getHandshakeHeaders() {
		return this.session.getHandshakeHeaders();
	}

	@Override
	public String getId() {
		return this.session.getId();
	}

	@Override
	public InetSocketAddress getLocalAddress() {
		return this.session.getLocalAddress();
	}

	@Override
	public Principal getPrincipal() {
		return this.session.getPrincipal();
	}

	@Override
	public InetSocketAddress getRemoteAddress() {
		return this.session.getRemoteAddress();
	}

	@Override
	public int getTextMessageSizeLimit() {
		return this.session.getTextMessageSizeLimit();
	}

	@Override
	public URI getUri() {
		return this.session.getUri();
	}

	@Override
	public boolean isOpen() {
		return this.session.isOpen();
	}

	@Override
	public void sendMessage(WebSocketMessage<?> arg0) throws IOException {
		this.session.sendMessage(arg0);
	}

	@Override
	public void setBinaryMessageSizeLimit(int arg0) {
		this.session.setBinaryMessageSizeLimit(arg0);
	}

	@Override
	public void setTextMessageSizeLimit(int arg0) {
		this.session.setTextMessageSizeLimit(arg0);
	}
}
