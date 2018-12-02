package com.meme.im.dao;

import java.util.List;

import com.meme.core.base.BaseMapper;
import com.meme.im.pojo.MemeColumnSection;
import com.meme.im.pojo.view.MemeColumnSectionView;

public interface MemeColumnSectionMapper extends BaseMapper{
	
	List<MemeColumnSectionView> selectAllSections();
	
	List<MemeColumnSection> selectByCode(String code);
	
	List<MemeColumnSectionView> selectColumnSections(Long columnid);
	
	List<MemeColumnSection> getSelectedSections(Long contentid);
	
	List<MemeColumnSectionView> selectByColumncode(String columncode);
}