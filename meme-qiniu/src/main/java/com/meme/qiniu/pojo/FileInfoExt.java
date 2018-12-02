package com.meme.qiniu.pojo;

import java.io.Serializable;
import java.util.Date;

public class FileInfoExt implements Serializable {

	private static final long serialVersionUID = -4739975973720423152L;
	private String url;
	private String key;
	private String hash;
	private long fsize;
	private Date putTime;
	private String mimeType;
	private String endUser;
	private String filename;

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public long getFsize() {
		return fsize;
	}

	public void setFsize(long fsize) {
		this.fsize = fsize;
	}

	public Date getPutTime() {
		return putTime;
	}

	public void setPutTime(Date putTime) {
		this.putTime = putTime;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getEndUser() {
		return endUser;
	}

	public void setEndUser(String endUser) {
		this.endUser = endUser;
	}
}
