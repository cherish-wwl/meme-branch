package com.meme.core.service.extend;

import java.util.List;

/**
 * 
 * @author hzl
 *
 * @param <T> 扩展操作的实体类
 * @param <K> 扩展的实体类
 */
public interface IPositionService<T,K> {

	/**
	 * 根据部门ID数组删除记录
	 * 
	 * @param list
	 * @return
	 */
	int deleteByPositionids(List<Object> list);
}
