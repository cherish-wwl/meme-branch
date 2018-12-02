package com.meme.core.pojo;

import com.meme.core.mybatis.annotation.Table;

@Table(name="sys_operation",id="id")
public class Operation {
    private Long id;

    private Long pid;

    private String name;

    private String viewurl;

    private String url;

    private String opcode;

    private String jscontent;

    private Long menuid;

    private Long platformid;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getViewurl() {
        return viewurl;
    }

    public void setViewurl(String viewurl) {
        this.viewurl = viewurl == null ? null : viewurl.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getOpcode() {
        return opcode;
    }

    public void setOpcode(String opcode) {
        this.opcode = opcode == null ? null : opcode.trim();
    }

    public String getJscontent() {
        return jscontent;
    }

    public void setJscontent(String jscontent) {
        this.jscontent = jscontent == null ? null : jscontent.trim();
    }

    public Long getMenuid() {
        return menuid;
    }

    public void setMenuid(Long menuid) {
        this.menuid = menuid;
    }

    public Long getPlatformid() {
        return platformid;
    }

    public void setPlatformid(Long platformid) {
        this.platformid = platformid;
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