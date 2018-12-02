package com.meme.qiniu.pojo;

import java.util.Date;

import com.meme.core.mybatis.annotation.Table;

@Table(name="qiniu_dir",id="id")
public class QiniuDir {
    private Long id;

    private Long pid;

    private String bucket;

    private String dir;

    private String fulldir;

    private Integer sortno;

    private Date addtime;

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

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket == null ? null : bucket.trim();
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir == null ? null : dir.trim();
    }

    public String getFulldir() {
        return fulldir;
    }

    public void setFulldir(String fulldir) {
        this.fulldir = fulldir == null ? null : fulldir.trim();
    }

    public Integer getSortno() {
        return sortno;
    }

    public void setSortno(Integer sortno) {
        this.sortno = sortno;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }
}