package com.meme.im.pojo;

import java.io.Serializable;
import java.util.Date;

import com.meme.core.mybatis.annotation.Table;

@Table(name="meme_im_friend_group",id="id")
public class ImFriendGroup implements Serializable{
	private static final long serialVersionUID = 1L;
    private Long id;

    private String groupname;

    private Long memberid;

    private Integer state;

    private Integer sortno;

    private Date addtime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname == null ? null : groupname.trim();
    }

    public Long getMemberid() {
        return memberid;
    }

    public void setMemberid(Long memberid) {
        this.memberid = memberid;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getSortno() {
        return sortno;
    }

    public void setSortno(Integer sortno) {
        this.sortno = sortno;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }
}