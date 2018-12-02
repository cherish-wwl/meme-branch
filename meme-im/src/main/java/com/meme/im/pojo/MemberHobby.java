package com.meme.im.pojo;

import java.util.Date;

import com.meme.core.mybatis.annotation.Table;

@Table(name="meme_member_hobby",id="hobbyid")
public class MemberHobby {
	private String hobbyid;

    private Long memberid;

    private String title;

    private String cover;

    private String summary;

    private String extlink;

    private Integer type;

    private Integer ctype;

    private Date addtime;

    private String content;

    public String getHobbyid() {
        return hobbyid;
    }

    public void setHobbyid(String hobbyid) {
        this.hobbyid = hobbyid == null ? null : hobbyid.trim();
    }

    public Long getMemberid() {
        return memberid;
    }

    public void setMemberid(Long memberid) {
        this.memberid = memberid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover == null ? null : cover.trim();
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
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

    public Integer getCtype() {
        return ctype;
    }

    public void setCtype(Integer ctype) {
        this.ctype = ctype;
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