package com.meme.im.pojo;

import java.util.Date;

import com.meme.core.mybatis.annotation.Table;

@Table(name="meme_member_bg_tem",id="tempid")
public class MemberBgTem {
    private String tempid;

    private String temname;

    private String url;

    private Integer sortno;

    private Date addtime;

    public String getTempid() {
        return tempid;
    }

    public void setTempid(String tempid) {
        this.tempid = tempid == null ? null : tempid.trim();
    }

    public String getTemname() {
        return temname;
    }

    public void setTemname(String temname) {
        this.temname = temname == null ? null : temname.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
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