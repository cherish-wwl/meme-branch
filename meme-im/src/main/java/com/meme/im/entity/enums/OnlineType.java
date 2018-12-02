package com.meme.im.entity.enums;

/**
 * layim 在线状态
 * @author hzl
 *
 */
public enum OnlineType {

	offline("offline"), online("online");

	private String type;

	private OnlineType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
