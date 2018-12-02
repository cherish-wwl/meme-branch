package com.meme.im.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.meme.core.base.BaseMapper;
import com.meme.im.pojo.MemeCategory;
import com.meme.im.pojo.view.MemeCategoryView;

public interface MemeCategoryMapper extends BaseMapper{
	
	List<MemeCategoryView> selectAllCats();
	
	/**
	 * 获取直接子类数据
	 * @param columnid
	 * @param pid
	 * @return
	 */
	List<MemeCategory> selectSubCats(@Param("columnid")Long columnid,@Param("pid")Long pid);
}