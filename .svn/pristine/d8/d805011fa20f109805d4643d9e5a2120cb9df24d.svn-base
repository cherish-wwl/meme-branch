package com.meme.core.http.request;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * http请求类
 * 
 * @author hzl
 * 
 */
public class NetRequest extends AbstractNetRequest {

	@Override
	public HttpURLConnection getConnection() {
		HttpURLConnection con = null;
		try {
			con = (HttpURLConnection) new URL(this.getEntity().getUrl()).openConnection();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return con;
	}
}
