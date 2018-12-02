package com.meme.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.meme.core.base.BaseMapper;
import com.meme.core.pojo.DictItem;
import com.meme.core.pojo.DictItemView;

public interface DictItemMapper extends BaseMapper{
	
	List<DictItemView> selectAllDict(@Param("dictgroupid")Long dictgroupid);
	
	List<DictItemView> selectByDictitemids(@Param("ids")List<Object> ids);
	
	/**
	 * 根据字典分组编码查询此分组所有字典项
	 * @param dictgroupcode
	 * @return
	 */
	List<DictItem> selectByDictgroupcode(@Param("dictgroupcode")String dictgroupcode);
}