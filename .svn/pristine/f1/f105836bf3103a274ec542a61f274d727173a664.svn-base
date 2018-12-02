package com.meme.qiniu.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.meme.core.base.BaseMapper;
import com.meme.qiniu.pojo.QiniuDir;

public interface QiniuDirMapper extends BaseMapper{
	
	/**
	 * 查询一级子目录
	 * @param bucket 七牛存储桶空间名称
	 * @param pid 父目录ID
	 * @return
	 */
	List<QiniuDir> selectChildren(@Param("bucket")String bucket,@Param("pid")Long pid);
	
	int updateFullDir(@Param("ids")List<Object> ids,@Param("prefix")String prefix);
	
	List<QiniuDir> selectByFulldir(@Param("bucket")String bucket,@Param("pid")Long pid,@Param("fulldir")String fulldir);
}