package com.meme.core.http;

import com.alibaba.fastjson.JSONObject;

/**
 * 操作结果返回消息类
 * @author hzl
 *
 */
public class ResultMessage {

	/**消息内容**/
	private String message;
	/**返回操作状态**/
	private String state;
	/**返回的数据**/
	private Object data;

	public ResultMessage(){
	}
	
	public ResultMessage(String state,String message) {
		this.message = message;
		this.state = state;
	}
	
	public ResultMessage(String state,String message,Object data) {
		this.message = message;
		this.state = state;
		this.data = data;
	}
	
	/**
	 * 失败返回消息
	 * @param message
	 * @return
	 */
	public static ResultMessage failed(String message){
		return new ResultMessage(ResponseState.FAILE.getState().toString(),message);
	}
	
	/**
	 * 失败返回消息，同时返回相关数据
	 * @param message
	 * @return
	 */
	public static ResultMessage failed(String message,Object data){
		return new ResultMessage(ResponseState.FAILE.getState().toString(),message,data);
	}
	
	/**
	 * 成功返回消息
	 * @param message
	 * @return
	 */
	public static ResultMessage success(String message){
		return new ResultMessage(ResponseState.SUCCESS.getState().toString(),message);
	}
	
	/**
	 * 成功返回消息，同时返回相关数据
	 * @param message
	 * @return
	 */
	public static ResultMessage success(String message,Object data){
		return new ResultMessage(ResponseState.SUCCESS.getState().toString(),message,data);
	}
	
	/**
	 * 成功默认返回消息
	 * @return
	 */
	public static ResultMessage defaultSuccessMessage(){
		ResultMessage rm=new ResultMessage();
		rm.setMessage(ResponseMessage.SUCCESS);
		rm.setState(ResponseState.SUCCESS.getState().toString());
		return rm;
	}
	
	/**
	 * 失败默认返回消息
	 * @return
	 */
	public static ResultMessage defaultFaileMessage(){
		ResultMessage rm=new ResultMessage();
		rm.setMessage(ResponseMessage.FAILE);
		rm.setState(ResponseState.FAILE.getState().toString());
		return rm;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
}
