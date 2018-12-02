package com.meme.im.service;

import java.util.List;

import com.meme.core.base.CrudService;
import com.meme.core.base.IService;
import com.meme.core.form.Form;
import com.meme.im.pojo.CategoryAudio;

public interface CategoryAudioService extends IService<CategoryAudio, Form>, CrudService<CategoryAudio> {
	
	List<CategoryAudio> selectAllAudioCats();
}