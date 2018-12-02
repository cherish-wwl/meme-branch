package com.meme.im.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.meme.core.base.BaseMapper;
import com.meme.im.pojo.MemeContentVote;

public interface MemeContentVoteMapper extends BaseMapper{
	
	/**
	 * 检查会员或者此ip今天是否已点赞
	 * @param memberid
	 * @param ip
	 * @return
	 */
	List<MemeContentVote> checkVoted(@Param("contentid")Long contentid,@Param("memberid")Long memberid,@Param("ip") String ip);
}