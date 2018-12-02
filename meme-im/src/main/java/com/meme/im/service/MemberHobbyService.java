package com.meme.im.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.meme.core.base.CrudService;
import com.meme.core.base.IService;
import com.meme.core.form.Form;
import com.meme.im.form.ImForm;
import com.meme.im.pojo.MemberHobby;

public interface MemberHobbyService extends IService<MemberHobby, Form>, CrudService<MemberHobby> {

	PageInfo<MemberHobby> selectByEntity(MemberHobby record, Integer pageNo, Integer pageSize);
	/**
	 * 分页查询消息
	 * @param form
	 * @return
	 */
	List<Map<String,Object>> selectByPaginationView(ImForm form);
	
	/**
	 * 统计分页查询消息总数
	 * @param form
	 * @return
	 */
	int countView(ImForm form);
	
}