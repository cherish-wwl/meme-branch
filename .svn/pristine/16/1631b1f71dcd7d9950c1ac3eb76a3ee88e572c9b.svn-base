package com.meme.im.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.meme.core.base.BaseMapper;
import com.meme.im.pojo.MemberAlbumItem;

public interface MemberAlbumItemMapper extends BaseMapper{
	/**
	 * 根据相册编号数组查找
	 * @param ids
	 * @return
	 */
	List<MemberAlbumItem> selectByAlbumids(@Param("ids")List<Object> ids);
	
	/**
	 * 查找会员相册最新5张照片
	 * @param memberid
	 * @return
	 */
	List<MemberAlbumItem> selectTopFiveAlbumItems(@Param("memberid")Object memberid);
	
	/**
	 * 根据相册编号删除
	 * @param albumid
	 * @return
	 */
	int deleteByAlbum(String albumid);
}