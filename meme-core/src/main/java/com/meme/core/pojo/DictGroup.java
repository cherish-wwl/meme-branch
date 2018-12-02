package com.meme.core.pojo;

import java.io.Serializable;

import com.meme.core.mybatis.annotation.Table;


@Table(name="sys_dict_group",id="dictgroupid")
public class DictGroup implements Serializable{
	private static final long serialVersionUID = 814381822602091076L;

	private Long dictgroupid;

    private String dictgroupcode;

    private String dictgroupname;

    private String type;

    private String remark;

    public Long getDictgroupid() {
        return dictgroupid;
    }

    public void setDictgroupid(Long dictgroupid) {
        this.dictgroupid = dictgroupid;
    }

    public String getDictgroupcode() {
        return dictgroupcode;
    }

    public void setDictgroupcode(String dictgroupcode) {
        this.dictgroupcode = dictgroupcode == null ? null : dictgroupcode.trim();
    }

    public String getDictgroupname() {
        return dictgroupname;
    }

    public void setDictgroupname(String dictgroupname) {
        this.dictgroupname = dictgroupname == null ? null : dictgroupname.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}