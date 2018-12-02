package com.meme.im.service;

import java.util.List;

import com.meme.core.base.CrudService;
import com.meme.core.base.IService;
import com.meme.core.form.Form;
import com.meme.im.pojo.MemeCategory;
import com.meme.im.pojo.view.MemeCategoryView;

public interface MemeCategoryService extends IService<MemeCategory, Form>, CrudService<MemeCategory> {

	List<MemeCategoryView> selectAllCats();
	
	/**
	 * 获取直接子类数据
	 * @param columnid
	 * @param pid
	 * @return
	 */
	List<MemeCategory> selectSubCats(Long columnid,Long pid);
}