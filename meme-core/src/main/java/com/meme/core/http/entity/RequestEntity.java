package com.meme.core.http.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.meme.core.http.HttpRequestMethod;
import com.meme.core.http.KeyStoreType;

/**
 * HttpURLConnection请求相关参数封装类
 * 
 * @author hzl
 * 
 */
public class RequestEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** http请求url，get请求带参数 **/
	private String url;
	/** 是否https请求 **/
	private boolean ssl = false;
	/**请求发送数据**/
	private Object data;
	/** 请求方法 **/
	private HttpRequestMethod method = HttpRequestMethod.GET;
	/** 请求属性 **/
	private Map<String, String> properties = new HashMap<String, String>();
	/** 编码 **/
	private String charset = "UTF-8";
	/** 是否双向认证 **/
	private boolean sslBinary=false;
	/** 密钥库类型 **/
	private KeyStoreType keyStoreType;
	/** 密钥存放路径 **/
	private String keyStorePath;
	/** 私钥密码 **/
	private String keyPassword;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isSsl() {
		return ssl;
	}

	public void setSsl(boolean ssl) {
		this.ssl = ssl;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public HttpRequestMethod getMethod() {
		return method;
	}

	public void setMethod(HttpRequestMethod method) {
		this.method = method;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public boolean isSslBinary() {
		return sslBinary;
	}

	public void setSslBinary(boolean sslBinary) {
		this.sslBinary = sslBinary;
	}

	public KeyStoreType getKeyStoreType() {
		return keyStoreType;
	}

	public void setKeyStoreType(KeyStoreType keyStoreType) {
		this.keyStoreType = keyStoreType;
	}

	public String getKeyStorePath() {
		return keyStorePath;
	}

	public void setKeyStorePath(String keyStorePath) {
		this.keyStorePath = keyStorePath;
	}

	public String getKeyPassword() {
		return keyPassword;
	}

	public void setKeyPassword(String keyPassword) {
		this.keyPassword = keyPassword;
	}
}
