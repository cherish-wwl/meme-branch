package com.meme.im.pojo;

import java.util.Date;

import com.meme.core.mybatis.annotation.Table;

@Table(name="meme_tv_program",id="programid")
public class TvProgram {
	private String programid;

    private String tvid;

    private String programName;

    private String url;

    private Integer ptype;

    private Date ptime;

    private Integer istop;

    private Integer ishotshow;

    private Date createtime;

    private Integer sortno;

    public String getProgramid() {
        return programid;
    }

    public void setProgramid(String programid) {
        this.programid = programid == null ? null : programid.trim();
    }

    public String getTvid() {
        return tvid;
    }

    public void setTvid(String tvid) {
        this.tvid = tvid == null ? null : tvid.trim();
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName == null ? null : programName.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getPtype() {
        return ptype;
    }

    public void setPtype(Integer ptype) {
        this.ptype = ptype;
    }

    public Date getPtime() {
        return ptime;
    }

    public void setPtime(Date ptime) {
        this.ptime = ptime;
    }

    public Integer getIstop() {
        return istop;
    }

    public void setIstop(Integer istop) {
        this.istop = istop;
    }

    public Integer getIshotshow() {
        return ishotshow;
    }

    public void setIshotshow(Integer ishotshow) {
        this.ishotshow = ishotshow;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getSortno() {
        return sortno;
    }

    public void setSortno(Integer sortno) {
        this.sortno = sortno;
    }
}