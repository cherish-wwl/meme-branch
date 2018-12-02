package com.meme.im.dao;

import java.util.List;

import com.meme.core.base.BaseMapper;
import com.meme.im.pojo.MemeColumn;

public interface MemeColumnMapper extends BaseMapper{
	
	List<MemeColumn> selectAllColumns();
	
	List<MemeColumn> selectByCode(String code);
}