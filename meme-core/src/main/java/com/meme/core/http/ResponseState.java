package com.meme.core.http;

/**
 * 请求响应状态码
 * @author hzl
 *
 */
public enum ResponseState {

	/**成功返回状态码**/
	SUCCESS(1),
	/**失败返回状态码**/
	FAILE(0);
	
	private Integer state;
	
	ResponseState(Integer state){
		this.state=state;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
}
