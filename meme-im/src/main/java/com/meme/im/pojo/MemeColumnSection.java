package com.meme.im.pojo;

import com.meme.core.mybatis.annotation.Table;

@Table(name="meme_column_section",id="id")
public class MemeColumnSection {
    private Long id;

    private Long columnid;

    private Long pid;

    private String sectioncode;

    private String sectionname;

    private Integer sortno;

    private String icon;

    private String sourceapi;

    private Integer ispagination;

    private String moreurl;

    private String tag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getColumnid() {
        return columnid;
    }

    public void setColumnid(Long columnid) {
        this.columnid = columnid;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getSectioncode() {
        return sectioncode;
    }

    public void setSectioncode(String sectioncode) {
        this.sectioncode = sectioncode == null ? null : sectioncode.trim();
    }

    public String getSectionname() {
        return sectionname;
    }

    public void setSectionname(String sectionname) {
        this.sectionname = sectionname == null ? null : sectionname.trim();
    }

    public Integer getSortno() {
        return sortno;
    }

    public void setSortno(Integer sortno) {
        this.sortno = sortno;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getSourceapi() {
        return sourceapi;
    }

    public void setSourceapi(String sourceapi) {
        this.sourceapi = sourceapi == null ? null : sourceapi.trim();
    }

    public Integer getIspagination() {
        return ispagination;
    }

    public void setIspagination(Integer ispagination) {
        this.ispagination = ispagination;
    }

    public String getMoreurl() {
        return moreurl;
    }

    public void setMoreurl(String moreurl) {
        this.moreurl = moreurl == null ? null : moreurl.trim();
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }
}