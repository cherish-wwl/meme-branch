package com.meme.im.entity;

import com.alibaba.fastjson.JSONObject;
import com.meme.im.entity.enums.OnlineType;
import com.meme.im.pojo.MemberView;

/**
 * layim mine数据结构
 * 
 * @author hzl
 * 
 */
public class Mine extends MemberView {

	private static final long serialVersionUID = 1L;

	/** layim mine数据结构id值，取member表的memberid **/
	private Long id;
	/** 发出的消息内容 **/
	private String content;
	/** 是否我发送的消息 **/
	private boolean mine = true;
	/** 我的昵称 ，取member表的nickname **/
	private String username;
	private String status=OnlineType.offline.getType();

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isMine() {
		return mine;
	}

	public void setMine(boolean mine) {
		this.mine = mine;
	}

	public String getUsername() {
		if (null != username)
			return username;
		else
			return this.getNickname();
	}

	public void setUsername(String username) {
		if (null == this.getNickname())
			this.setNickname(username);
		this.username = username;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
}
