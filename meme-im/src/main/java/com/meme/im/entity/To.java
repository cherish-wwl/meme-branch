package com.meme.im.entity;

import com.alibaba.fastjson.JSONObject;
import com.meme.im.pojo.MemberView;

/**
 * layim to数据结构
 * 
 * @author hzl
 * 
 */
public class To extends MemberView {

	private static final long serialVersionUID = 1L;

	/** layim to数据结构id值，取member表的memberid **/
	private Long id;
	/** 对方昵称， 取member表的nickname **/
	private String name;
	/** 对方昵称 ，取member表的nickname **/
	private String username;
	/** 聊天类型，一般分friend和group两种，group即群聊 **/
	private String type;

	public Long getId() {
		if (null != id)
			return id;
		else
			return this.getMemberid();
	}

	public void setId(Long id) {
		if (null == this.getMemberid())
			this.setMemberid(id);
		this.id = id;
	}

	public String getName() {
		if (null != name)
			return name;
		else
			return this.getNickname();
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		if (null == this.getNickname())
			this.setNickname(username);
		this.username = username;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
}
