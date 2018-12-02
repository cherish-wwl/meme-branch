package com.meme.core.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.meme.core.base.CrudService;
import com.meme.core.base.IService;
import com.meme.core.form.Form;
import com.meme.core.pojo.DictItem;
import com.meme.core.pojo.DictItemView;

public interface DictItemService extends IService<DictItem,Form>,CrudService<DictItem>{
	
	List<DictItemView> selectAllDict(Long dictgroupid);
	
	/**
	 * 根据字典组id数组删除
	 * @param dictgroupids
	 * @return
	 */
	int deleteByDictGroupids(List<Object> dictgroupids);
	
	List<DictItemView> selectByDictitemids(List<Object> ids);
	
	String doImport(HttpServletRequest request,Long dictgroupid);
	
	/**
	 * 根据字典分组编码查询此分组所有字典项
	 * @param dictgroupcode
	 * @return
	 */
	List<DictItem> selectByDictgroupcode(String dictgroupcode);
}
