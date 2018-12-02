package com.meme.im.entity.enums;

/**
 * 聊天类型枚举，群聊，单聊，客服
 * 
 * @author hzl
 * 
 */
public enum ChatType {

	GROUP("group",1), FRIEND("friend",0), KEFU("kefu",3),SYS("sys",2);
	private String type;
	private Integer code;

	private ChatType(String type,Integer code) {
		this.type = type;
		this.code=code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
}
