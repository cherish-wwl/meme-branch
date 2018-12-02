package com.meme.im.dao;

import java.util.List;

import com.meme.im.pojo.MemberPrivate;

public interface MemberPrivateMapper{
	
	public List<MemberPrivate> selectByIds(List<Long> list);
	
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
    MemberPrivate selectByPrimaryKey(Object id);

	/**
	 * 更新记录，字段为空时此字段不做更新
	 * @param record
	 * @return
	 */
     int updateByPrimaryKeySelective(MemberPrivate record);
}