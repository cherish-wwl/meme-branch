package com.meme.im.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;

import com.meme.core.base.CrudService;
import com.meme.core.base.IService;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.im.pojo.Member;

public interface MemberService extends IService<Member, Form>, CrudService<Member> {

	/**
	 * 注册
	 * @param request
	 * @param obj
	 * @return
	 */
	ResultMessage doRegister(HttpServletRequest request,HttpServletResponse response,Member obj);
	
	List<Member> check(Long memberid,String account);
	
	List<Long> selectByNums(String[] nums) ;
	
	List<Member> selectByPagination(HttpServletRequest request,Form form);
	
	int count(HttpServletRequest request,Form form);
	
	/**
	 * 重置密码
	 * @param ids
	 * @param password
	 * @return
	 */
	ResultMessage resetPassword(List<Object> ids,String password);
	
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
	
	/**
	 * 初始化临时访客
	 * @param request
	 * @param response
	 * @return
	 */
	ResultMessage init_tmp_member(HttpServletRequest request,HttpServletResponse response);
	
	Member selectByOpenid(String openid);
	
	Long getMemberIdByPathname(@Param("pathname")String pathname);


}
