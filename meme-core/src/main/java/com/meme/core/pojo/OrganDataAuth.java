package com.meme.core.pojo;

import com.meme.core.mybatis.annotation.Table;

@Table(name="sys_organ_data_auth",id="id")
public class OrganDataAuth {
    private Long id;

    private Long organid;

    private Long menuid;

    private String sqlcondition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrganid() {
        return organid;
    }

    public void setOrganid(Long organid) {
        this.organid = organid;
    }

    public Long getMenuid() {
        return menuid;
    }

    public void setMenuid(Long menuid) {
        this.menuid = menuid;
    }

    public String getSqlcondition() {
        return sqlcondition;
    }

    public void setSqlcondition(String sqlcondition) {
        this.sqlcondition = sqlcondition == null ? null : sqlcondition.trim();
    }
}