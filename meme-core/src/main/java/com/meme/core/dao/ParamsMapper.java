package com.meme.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.meme.core.base.BaseMapper;
import com.meme.core.pojo.Params;

public interface ParamsMapper extends BaseMapper{
	
	/**
	 * 查询所有参数配置
	 * @param type
	 * @return
	 */
	List<Params> selectAllParams(@Param("type")String type);
	
	/**
	 * 批量更新参数值
	 * @param map 参数名和参数值键值对
	 * @return
	 */
	int batchUpdateValue(@Param("map")Map<String,String> map);
}