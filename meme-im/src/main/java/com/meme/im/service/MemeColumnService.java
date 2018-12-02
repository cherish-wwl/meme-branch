package com.meme.im.service;

import java.util.List;

import com.meme.core.base.CrudService;
import com.meme.core.base.IService;
import com.meme.core.form.Form;
import com.meme.im.pojo.MemeColumn;

public interface MemeColumnService extends IService<MemeColumn, Form>, CrudService<MemeColumn> {

	List<MemeColumn> selectAllColumns();
	
	List<MemeColumn> selectByCode(String code);
}