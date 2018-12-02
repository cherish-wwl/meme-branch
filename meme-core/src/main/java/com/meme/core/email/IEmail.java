package com.meme.core.email;

import java.util.Properties;

/**
 * 邮件基类
 * @author hzl
 *
 */
public abstract class IEmail {
	private Properties properties = System.getProperties();
	private String user;
	private String password;
	private String charset;
	
	/**
	 * 初始化邮件收发参数
	 */
	public abstract void init();
	
	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}
}
