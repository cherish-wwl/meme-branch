package com.meme.im.dao;

import java.util.List;

import com.meme.core.base.BaseMapper;
import com.meme.im.pojo.CategoryVideo;

public interface CategoryVideoMapper extends BaseMapper {
	
	/**
	 * 查询所有视频影视分类
	 * @return
	 */
	List<CategoryVideo> selectAllVideoCats();
}