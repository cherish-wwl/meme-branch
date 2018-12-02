package com.meme.im.pojo;

import java.util.Date;

import com.meme.core.mybatis.annotation.Table;

@Table(name="meme_member_billboard",id="id")
public class MemberBillboard {
	private String id;

    private Long memberid;

    private String extlink;

    private Integer type;

    private Date addtime;

    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Long getMemberid() {
        return memberid;
    }

    public void setMemberid(Long memberid) {
        this.memberid = memberid;
    }

    public String getExtlink() {
        return extlink;
    }

    public void setExtlink(String extlink) {
        this.extlink = extlink == null ? null : extlink.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}