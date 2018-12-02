package com.meme.core.base;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.meme.core.http.ResultMessage;

/**
 * crud接口
 * @author hzl
 *
 * @param <T>
 */
public interface CrudService<T> {

	/**
	 * 增加
	 * @param request
	 * @param record
	 * @return
	 */
	ResultMessage add(HttpServletRequest request,T record);
	
	/**
	 * 修改
	 * @param request
	 * @param record
	 * @return
	 */
	ResultMessage edit(HttpServletRequest request,T record);
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	ResultMessage delete(List<Object> ids);
}
