package com.meme.im.pojo;

import java.util.Date;

import com.meme.core.mybatis.annotation.Table;

@Table(name="meme_tv_station",id="tvid")
public class TvStation {
    private String tvid;

    private String tvStation;

    private Integer tvtype;

    private Date addtime;

    public String getTvid() {
        return tvid;
    }

    public void setTvid(String tvid) {
        this.tvid = tvid == null ? null : tvid.trim();
    }

    public String getTvStation() {
        return tvStation;
    }

    public void setTvStation(String tvStation) {
        this.tvStation = tvStation == null ? null : tvStation.trim();
    }

    public Integer getTvtype() {
        return tvtype;
    }

    public void setTvtype(Integer tvtype) {
        this.tvtype = tvtype;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }
}