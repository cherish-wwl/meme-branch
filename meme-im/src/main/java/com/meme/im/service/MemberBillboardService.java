package com.meme.im.service;

import com.meme.core.base.CrudService;
import com.meme.core.base.IService;
import com.meme.core.form.Form;
import com.meme.im.pojo.MemberBillboard;

public interface MemberBillboardService extends IService<MemberBillboard, Form>, CrudService<MemberBillboard> {

	void deleteByBillboardid(String id);

}