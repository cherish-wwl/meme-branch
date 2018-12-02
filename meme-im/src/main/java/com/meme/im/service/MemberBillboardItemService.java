package com.meme.im.service;

import java.util.List;

import com.meme.core.base.CrudService;
import com.meme.core.base.IService;
import com.meme.core.form.Form;
import com.meme.im.pojo.MemberBillboardItem;

public interface MemberBillboardItemService extends IService<MemberBillboardItem, Form>, CrudService<MemberBillboardItem> {

	List<MemberBillboardItem> selectByBillboardids(List<Object> billboardids);

}