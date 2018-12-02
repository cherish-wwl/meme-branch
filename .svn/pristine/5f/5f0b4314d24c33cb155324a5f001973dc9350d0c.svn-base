package com.meme.qiniu.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.meme.core.base.CrudService;
import com.meme.core.base.IService;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.qiniu.pojo.QiniuDir;

public interface QiniuDirService extends IService<QiniuDir, Form>,CrudService<QiniuDir> {

	/**
	 * 查询一级子目录
	 * @param bucket 七牛存储桶空间名称
	 * @param pid 父目录ID
	 * @return
	 */
	List<QiniuDir> selectChildren(String bucket,Long pid);
	
	int updateFullDir(List<Object> ids,String prefix);
	
	/**
	 * 七牛云存储
	 * @param request
	 * @param response
	 * @param dir
	 * @param bucket
	 */
	void qiniu_upload(HttpServletRequest request, HttpServletResponse response,String dir,String bucket) throws IOException;
	
	/**
	 * 文件目录路径字符串存入七牛目录表
	 * @param bucket
	 * @param prefix
	 * @return
	 */
	ResultMessage prefix2Dir(String bucket,String prefix);
}
