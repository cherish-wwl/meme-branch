package com.meme.core.pojo;

import com.meme.core.mybatis.annotation.Table;

@Table(name="sys_role_menu",id="id")
public class RoleMenu {
    private Long id;

    private Long roleid;

    private Long menuid;

    private Long organid;

    private Long platformid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleid() {
        return roleid;
    }

    public void setRoleid(Long roleid) {
        this.roleid = roleid;
    }

    public Long getMenuid() {
        return menuid;
    }

    public void setMenuid(Long menuid) {
        this.menuid = menuid;
    }

    public Long getOrganid() {
        return organid;
    }

    public void setOrganid(Long organid) {
        this.organid = organid;
    }

    public Long getPlatformid() {
        return platformid;
    }

    public void setPlatformid(Long platformid) {
        this.platformid = platformid;
    }
}