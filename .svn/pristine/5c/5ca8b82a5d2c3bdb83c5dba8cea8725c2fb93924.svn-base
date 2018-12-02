package com.meme.im.pojo;

import java.io.Serializable;
import java.util.Date;

import com.meme.core.mybatis.annotation.Table;

@Table(name="meme_im_message",id="msgid")
public class ImMessage implements Serializable{
	private static final long serialVersionUID = 1L;
    private Long msgid;

    private Long sendid;

    private Long acceptid;
    
    private Long groupid;

    private Integer state;

    private Integer msgtype;

    private Date sendtime;

    private Date accepttime;

    private String content;

    public Long getMsgid() {
        return msgid;
    }

    public void setMsgid(Long msgid) {
        this.msgid = msgid;
    }

    public Long getSendid() {
        return sendid;
    }

    public void setSendid(Long sendid) {
        this.sendid = sendid;
    }

    public Long getAcceptid() {
        return acceptid;
    }

    public void setAcceptid(Long acceptid) {
        this.acceptid = acceptid;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(Integer msgtype) {
        this.msgtype = msgtype;
    }

    public Date getSendtime() {
        return sendtime;
    }

    public void setSendtime(Date sendtime) {
        this.sendtime = sendtime;
    }

    public Date getAccepttime() {
        return accepttime;
    }

    public void setAccepttime(Date accepttime) {
        this.accepttime = accepttime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

	public Long getGroupid() {
		return groupid;
	}

	public void setGroupid(Long groupid) {
		this.groupid = groupid;
	}
    
    
}