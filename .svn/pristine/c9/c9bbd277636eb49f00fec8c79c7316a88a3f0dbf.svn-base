package com.meme.im.websocket.interceptor;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.meme.core.util.StringUtil;
import com.meme.im.util.AccountUtil;
import com.meme.im.util.IMConstants;

/**
 * spring websocket握手拦截器
 * 
 * @author hzl
 * 
 */
@Component
public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor {
	private static final Logger logger = Logger.getLogger("===websocket拦截器===");
	@Override
	public boolean beforeHandshake(ServerHttpRequest request,
			ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
			String token=AccountUtil.getToken(serverRequest.getServletRequest());
			if(!StringUtil.isEmpty(token)) {
				attributes.put(IMConstants.COOKIE_TOKEN_KEY, token);
			} else {
				token=serverRequest.getServletRequest().getParameter(IMConstants.COOKIE_TOKEN_KEY);
				attributes.put(IMConstants.COOKIE_TOKEN_KEY, token);
			}
//			HttpSession session=serverRequest.getServletRequest().getSession(false);
//			attributes.put("session", session);
//			attributes.put("sessionid", session.getId());
		}
		logger.debug("握手前拦截");
		return super.beforeHandshake(request, response, wsHandler, attributes);
	}

	@Override
	public void afterHandshake(ServerHttpRequest request,
			ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception ex) {
		logger.debug("握手后拦截");
		super.afterHandshake(request, response, wsHandler, ex);
	}

}
