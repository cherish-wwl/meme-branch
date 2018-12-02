package com.meme.im.service;

import java.util.List;

import com.meme.core.base.CrudService;
import com.meme.core.base.IService;
import com.meme.core.form.Form;
import com.meme.im.pojo.MemberAlbumItem;

public interface MemberAlbumItemService extends IService<MemberAlbumItem, Form>, CrudService<MemberAlbumItem> {
	/**
	 * 根据相册编号数组查找
	 * @param ids
	 * @return
	 */
	List<MemberAlbumItem> selectByAlbumids(List<Object> ids);
	
	/**
	 * 查找会员相册最新5张照片
	 * @param memberid
	 * @return
	 */
	List<MemberAlbumItem> selectTopFiveAlbumItems(Object memberid);
	
	/**
	 * 根据相册编号删除
	 * @param albumid
	 * @return
	 */
	int deleteByAlbum(String albumid);
}