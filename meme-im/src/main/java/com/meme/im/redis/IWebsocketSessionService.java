package com.meme.im.redis;

import java.util.List;

import com.meme.im.pojo.Member;


public interface IWebsocketSessionService {

	boolean add(final Member member);

	void delete(String key);

	Member getMember(final String key);
	
	/**
	 * 分页
	 * @param page  当前页数
	 * @param pageSize  每页记录数
	 * @return
	 */
	List<String> getPagination(Integer page,Integer pageSize,Integer state);
	
	/**
	 * 获取总条数
	 * @return
	 */
	Long getTotal(Integer state);
}