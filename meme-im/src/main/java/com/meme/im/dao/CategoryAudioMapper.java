package com.meme.im.dao;

import java.util.List;

import com.meme.core.base.BaseMapper;
import com.meme.im.pojo.CategoryAudio;

public interface CategoryAudioMapper extends BaseMapper {
	
	List<CategoryAudio> selectAllAudioCats();
}