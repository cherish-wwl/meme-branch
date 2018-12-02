package com.meme.core.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.meme.core.base.IService;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.core.pojo.Department;
import com.meme.core.pojo.LoginAccount;
import com.meme.core.pojo.LoginAccountInfoView;
import com.meme.core.pojo.Organization;
import com.meme.core.pojo.Position;
import com.meme.core.service.extend.IDepartmentService;
import com.meme.core.service.extend.IOrganService;
import com.meme.core.service.extend.IPositionService;

public interface LoginAccountService extends IService<LoginAccount,Form>,
	IOrganService<LoginAccount,Organization>,
	IPositionService<LoginAccount,Position>,
	IDepartmentService<LoginAccount, Department>{
	
	/**
	 * 根据账号名、手机号码、邮箱判断是否账号是否存在
	 * @param loginStr
	 * @return
	 * @throws Exception
	 */
	List<LoginAccountInfoView> isExist(String loginStr,Long loginid) throws Exception;
	
	List<LoginAccountInfoView> selectByPaginationView(Form form);
	
	int countView(Form form);
	
	/**
	 * 更新账号状态
	 * @param ids
	 * @return
	 */
	int updateStateByids(Integer state,List<Object> ids);
	
	/**
	 * 重置密码
	 * @param loginid
	 * @param password
	 * @return
	 */
	int resetPassword(Object loginid,String password);
	
	/**
	 * 根据主键ID数组查询记录
	 * @param list
	 * @return
	 */
	List<LoginAccount> selectByIds(List<Object> list);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	LoginAccountInfoView selectLoginAccountInfoViewById(Long id);
	
	ResultMessage add(HttpServletRequest request,LoginAccountInfoView info,String pwd,String confirmpwd);
	
	ResultMessage edit(HttpServletRequest request,LoginAccountInfoView info);
	
	ResultMessage delete(List<Object> ids);
	
	/**
	 * 账号授权
	 * @param roleids
	 * @param loginid
	 * @return
	 */
	ResultMessage accountAuth(List<Object> roleids,Long loginid);
	
	/**
	 * 重置密码
	 * @param ids
	 * @param password
	 * @return
	 */
	ResultMessage resetPassword(List<Object> ids,String password);
	
	/**
	 * 启用账号
	 * @param ids
	 * @return
	 */
	ResultMessage start(List<Object> ids);
	
	/**
	 * 禁用账号
	 * @param ids
	 * @return
	 */
	ResultMessage stop(List<Object> ids);
}
