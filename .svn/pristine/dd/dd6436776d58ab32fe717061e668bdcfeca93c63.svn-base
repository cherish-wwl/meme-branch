package com.meme.im.pojo;

import java.util.Date;

import com.meme.core.mybatis.annotation.Table;

@Table(name="meme_content_vote",id="voteid")
public class MemeContentVote {
    private Long voteid;

    private Long contentid;

    private Long memberid;

    private String ip;

    private Integer state;

    private Date votetime;

    public Long getVoteid() {
        return voteid;
    }

    public void setVoteid(Long voteid) {
        this.voteid = voteid;
    }

    public Long getContentid() {
        return contentid;
    }

    public void setContentid(Long contentid) {
        this.contentid = contentid;
    }

    public Long getMemberid() {
        return memberid;
    }

    public void setMemberid(Long memberid) {
        this.memberid = memberid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getVotetime() {
        return votetime;
    }

    public void setVotetime(Date votetime) {
        this.votetime = votetime;
    }
}