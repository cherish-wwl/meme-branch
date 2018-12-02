package com.meme.im.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.meme.core.base.BaseMapper;
import com.meme.im.pojo.Member;

public interface MemberMapper extends BaseMapper{
	
	List<Member> check(@Param("memberid")Long memberid,@Param("account")String account);
	
	
	/**
	 * 获取所有客服账号
	 * @return
	 */
	List<Map<String,Object>> getAllServiceMembers();
	
	/**
	 * 获取所有临时账号
	 * @return
	 */
	List<Map<String,Object>> getAllTmpMembers();
	
	Member selectByOpenid(String openid);
	
	List<Member> getMemberIdByPathname(@Param("pathname")String pathname);
	
	
	Member selectByAccount(@Param("account")String account);


	/**
	 * @param nums
	 * @return
	 */
	List<Long> selectByNums(String[] nums);
}