package com.meme.core.pojo;

import java.util.Date;

import com.meme.core.mybatis.annotation.Table;

@Table(name="sys_role",id="id")
public class Role {
    private Long id;

    private String name;

    private String description;

    private Long organid;

    private Long num;

    private Date starttime;

    private Date endtime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Long getOrganid() {
        return organid;
    }

    public void setOrganid(Long organid) {
        this.organid = organid;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }
}