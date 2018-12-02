package com.meme.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.meme.core.base.BaseMapper;
import com.meme.core.form.Form;
import com.meme.core.pojo.LoginAccountInfoView;

public interface LoginAccountMapper extends BaseMapper{
	
	/**
	 * 根据账号名、手机号码、邮箱判断是否账号是否存在
	 * @param loginStr
	 * @return
	 * @throws Exception
	 */
	List<LoginAccountInfoView> isExist(@Param("loginStr")String loginStr,@Param("loginid")Long loginid);
	
	List<LoginAccountInfoView> selectByPaginationView(Form form);
	
	int countView(Form form);
	
	/**
	 * 更新账号状态
	 * @param ids
	 * @return
	 */
	int updateStateByids(@Param("state")Integer state,@Param("ids")List<Object> ids);
	
	/**
	 * 重置密码
	 * @param loginids
	 * @param password
	 * @return
	 */
	int resetPassword(@Param("loginid")Object loginid,@Param("password")String password);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	LoginAccountInfoView selectLoginAccountInfoViewById(Long id);
}