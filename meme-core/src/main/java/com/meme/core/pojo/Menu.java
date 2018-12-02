package com.meme.core.pojo;

import com.meme.core.mybatis.annotation.Table;

@Table(name="sys_menu",id="id")
public class Menu {
    private Long id;

    private Long pid;

    private Long hid;

    private String name;

    private String url;

    private Long platformid;

    private Integer isallowchild;

    private String icon;

    private Integer sortno;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getHid() {
        return hid;
    }

    public void setHid(Long hid) {
        this.hid = hid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Long getPlatformid() {
        return platformid;
    }

    public void setPlatformid(Long platformid) {
        this.platformid = platformid;
    }

    public Integer getIsallowchild() {
        return isallowchild;
    }

    public void setIsallowchild(Integer isallowchild) {
        this.isallowchild = isallowchild;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public Integer getSortno() {
        return sortno;
    }

    public void setSortno(Integer sortno) {
        this.sortno = sortno;
    }
}