package com.meme.im.service;

import java.util.List;
import java.util.Map;

import com.meme.core.base.IService;
import com.meme.core.form.Form;
import com.meme.im.form.ImForm;
import com.meme.im.pojo.MemeLike;

public interface MemeLikeService extends IService<MemeLike, Form>{

	
	List<Map<String, Object>> selectByPaginationView(ImForm form);
	
	int countView(ImForm form);

}
