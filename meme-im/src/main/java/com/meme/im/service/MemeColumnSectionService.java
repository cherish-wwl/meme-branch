package com.meme.im.service;

import java.util.List;

import com.meme.core.base.CrudService;
import com.meme.core.base.IService;
import com.meme.core.form.Form;
import com.meme.im.pojo.MemeColumnSection;
import com.meme.im.pojo.view.MemeColumnSectionView;

public interface MemeColumnSectionService extends IService<MemeColumnSection, Form>, CrudService<MemeColumnSection> {

	List<MemeColumnSectionView> selectAllSections();
	
	List<MemeColumnSection> selectByCode(String code);
	
	List<MemeColumnSectionView> selectColumnSections(Long columnid);
	
	List<MemeColumnSection> getSelectedSections(Long contentid);
	
	List<MemeColumnSectionView> selectByColumncode(String columncode);
}