package com.meme.core.email;

import com.meme.core.util.PropertiesUtil;
import com.meme.core.util.StringUtil;

/**
 * 邮件服务接口
 * @author hzl
 *
 */
@Deprecated
public abstract class IEmailHandler {
	protected String server;
	protected int port=25;//默认smtp端口
	protected boolean isEncode=true;//密码是否需要加密
    
	public abstract boolean send(EmailMessage message);

	public String getServer() {
		if(StringUtil.isEmpty(server)){
			return PropertiesUtil.getProperty("email_server");
		}
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public int getPort() {
		String p=PropertiesUtil.getProperty("email_port");
		if(StringUtil.isAllNotEmpty(p)){
			this.port=Integer.parseInt(p);
		}
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isEncode() {
		String p=PropertiesUtil.getProperty("email_isEncode");
		if(StringUtil.isAllNotEmpty(p)) return Boolean.parseBoolean(p);
		return isEncode;
	}

	public void setEncode(boolean isEncode) {
		this.isEncode = isEncode;
	}
}
