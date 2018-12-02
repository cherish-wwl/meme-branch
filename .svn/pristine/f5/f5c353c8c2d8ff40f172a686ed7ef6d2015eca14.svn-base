package com.meme.core.http.request;

import java.net.HttpURLConnection;

import com.meme.core.http.ResultMessage;
import com.meme.core.http.entity.RequestEntity;

/**
 * http请求接口
 * 
 * @author hzl
 * 
 */
public interface IRequest {

	/**
	 * 获取http请求实例
	 * 
	 * @return
	 */
	HttpURLConnection getConnection();

	/**
	 * 执行http请求
	 * 
	 * @param entity
	 * @return 响应码为200时，返回响应码和响应数据字符串，否则返回响应码和响应码描述
	 */
	ResultMessage request(RequestEntity entity);
}