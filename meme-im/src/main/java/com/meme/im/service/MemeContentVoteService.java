package com.meme.im.service;

import com.meme.core.base.CrudService;
import com.meme.core.base.IService;
import com.meme.core.form.Form;
import com.meme.im.pojo.MemeContentVote;

public interface MemeContentVoteService extends IService<MemeContentVote, Form>, CrudService<MemeContentVote> {
	
	/**
	 * 检查会员或者此ip今天是否已点赞
	 * @param memberid
	 * @param ip
	 * @return
	 */
	boolean checkVoted(Long contentid,Long memberid,String ip);
}