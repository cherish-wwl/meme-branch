package com.meme.core.file;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

/**
 * 上传文件实体类
 * @author hzl
 *
 */
public class UploadFile implements Serializable{

	private static final long serialVersionUID = -4258638653908124210L;
	
	/**原文件名**/
	private String oldName;
	/**新文件名**/
	private String newName;
	/**文件扩展名**/
	private String extension;
	/**文件大小**/
	private Long size;
	/**文件上传状态，true-成功，false-失败**/
	private boolean state;
	/**文件上传路径**/
	private String path;

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
