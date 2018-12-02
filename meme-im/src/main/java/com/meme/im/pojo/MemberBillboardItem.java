package com.meme.im.pojo;

import java.util.Date;

import com.meme.core.mybatis.annotation.Table;

@Table(name="meme_member_billboard_item",id="bitemid")
public class MemberBillboardItem {
    private String bitemid;

    private String billboardid;

    private String url;

    private String remark;

    private Date addtime;

    public String getBitemid() {
        return bitemid;
    }

    public void setBitemid(String bitemid) {
        this.bitemid = bitemid == null ? null : bitemid.trim();
    }

    public String getBillboardid() {
        return billboardid;
    }

    public void setBillboardid(String billboardid) {
        this.billboardid = billboardid == null ? null : billboardid.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }
}