package com.meme.im.entity;

import java.io.Serializable;

/**
 * layim上传文件结果返回的json格式
 * 
 * @author hzl
 * 
 */
public class UploadResult implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 0表示成功，其它表示失败 **/
	private Integer code;
	/** 失败信息 **/
	private String msg;
	/** 返回文件相关数据键值对，src和name(上传图片时name非必要返回) **/
	private Object data;

	public static UploadResult success(Object data) {
		return new UploadResult(0, data);
	}

	public static UploadResult success(String msg, Object data) {
		return new UploadResult(0, msg, data);
	}

	public static UploadResult failed(Integer code, String msg) {
		return new UploadResult(code, msg, null);
	}

	public UploadResult(Integer code, Object data) {
		this.code = code;
		this.data = data;
	}

	public UploadResult(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public UploadResult(Integer code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	/**
	 * 返回文件相关数据键值对，src和name(上传图片时name非必要返回)
	 * 
	 * @param data
	 */
	public void setData(Object data) {
		this.data = data;
	}

}
