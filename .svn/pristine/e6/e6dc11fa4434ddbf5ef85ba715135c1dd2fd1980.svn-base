package com.meme.core.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meme.core.base.AbstractService;
import com.meme.core.dao.LoginAccountMapper;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.core.mybatis.Criterion;
import com.meme.core.mybatis.MapperHelper;
import com.meme.core.mybatis.enums.Operator;
import com.meme.core.pojo.LoginAccount;
import com.meme.core.pojo.LoginAccountInfo;
import com.meme.core.pojo.LoginAccountInfoView;
import com.meme.core.pojo.LoginAccountRole;
import com.meme.core.pojo.Role;
import com.meme.core.service.LoginAccountInfoService;
import com.meme.core.service.LoginAccountRoleService;
import com.meme.core.service.LoginAccountService;
import com.meme.core.service.RoleService;
import com.meme.core.util.CipherTool;
import com.meme.core.util.Constants;
import com.meme.core.util.IDGenerator;
import com.meme.core.util.MD5Util;
import com.meme.core.util.StringUtil;

@Service("LoginAccountService")
@Transactional
public class LoginAccountServiceImpl extends AbstractService implements LoginAccountService {

	@Resource private LoginAccountMapper mapper;
	
	@Resource private LoginAccountInfoService loginAccountInfoService;
	@Resource private LoginAccountRoleService loginAccountRoleService;
	@Resource private RoleService roleService;

	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(LoginAccount record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(LoginAccount record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public LoginAccount selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(LoginAccount record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(LoginAccount record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoginAccount> selectAll(){
		return (List<LoginAccount>) MapperHelper.toBeanList(this.mapper.selectAll(LoginAccount.class), LoginAccount.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoginAccount> selectByPagination(Form form) {
		this.buildSearchCriterion(LoginAccount.class, form);
		this.buildOrderByCriterion(LoginAccount.class, form);
		this.buildLimitCriterion(LoginAccount.class, form);
		return (List<LoginAccount>) MapperHelper.toBeanList(this.mapper.selectByPagination(LoginAccount.class,this.getList()), LoginAccount.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(LoginAccount.class, form);
		return this.mapper.count(LoginAccount.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoginAccount> selectByEntity(LoginAccount record) {
		return (List<LoginAccount>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), LoginAccount.class);
	}

	@Override
	public int batchInsert(List<LoginAccount> record) {
		return this.mapper.batchInsert(record,LoginAccount.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,LoginAccount.class);
	}

	@Override
	public int batchUpdate(List<LoginAccount> record) {
		return this.mapper.batchUpdate(record,LoginAccount.class);
	}

	@Override
	public List<LoginAccountInfoView> isExist(String loginStr,Long loginid) throws Exception {
		return this.mapper.isExist(loginStr,loginid);
	}

	@Override
	public int deleteByOrganids(List<Object> list) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("organid").operator(Operator.IN).leftValue(list).javaType(Long.class).build());
		return this.mapper.deleteByCriterions(criterions, LoginAccount.class);
	}
	
	@Override
	public int deleteByDepartmentids(List<Object> list) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("deptid").operator(Operator.IN).leftValue(list).javaType(Long.class).build());
		return this.mapper.deleteByCriterions(criterions, LoginAccount.class);
	}

	@Override
	public int deleteByPositionids(List<Object> list) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("postid").operator(Operator.IN).leftValue(list).javaType(Long.class).build());
		return this.mapper.deleteByCriterions(criterions, LoginAccount.class);
	}

	@Override
	public List<LoginAccountInfoView> selectByPaginationView(Form form) {
		return this.mapper.selectByPaginationView(form);
	}

	@Override
	public int countView(Form form) {
		return this.mapper.countView(form);
	}

	@Override
	public int updateStateByids(Integer state, List<Object> ids) {
		return this.mapper.updateStateByids(state, ids);
	}

	@Override
	public int resetPassword(Object loginid, String password) {
		return this.mapper.resetPassword(loginid, password);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<LoginAccount> selectByIds(List<Object> list) {
		List<Criterion> criterions=new ArrayList<Criterion>();
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("id").operator(Operator.IN).leftValue(list).javaType(Long.class).build());
		return (List<LoginAccount>) MapperHelper.toBeanList(this.mapper.selectByCriterions(criterions, LoginAccount.class), LoginAccount.class);
	}

	@Override
	public LoginAccountInfoView selectLoginAccountInfoViewById(Long id) {
		return this.mapper.selectLoginAccountInfoViewById(id);
	}

	@Override
	public ResultMessage add(HttpServletRequest request, LoginAccountInfoView info, String pwd, String confirmpwd){
		LoginAccountInfoView user=null;
		Object obj=request.getSession().getAttribute(Constants.SESSION_USER);
		if(null!=obj) user=(LoginAccountInfoView) obj;
		
		if(null!=info.getLoginid()){
			return ResultMessage.failed("修改用户资料请点击修改按钮进行操作");
		}
		if(StringUtil.isOneEmpty(pwd,confirmpwd)) return ResultMessage.failed("密码和确认密码不能为空");
		if(!pwd.equals(confirmpwd)) return ResultMessage.failed("密码和确认密码不一致");
		if(StringUtil.isEmpty(info.getEmail())) return ResultMessage.failed("邮箱不能为空");
		
		List<LoginAccountInfoView> accounts=this.mapper.isExist(info.getAccount(), null);
		if(null!=accounts&&accounts.size()>0) return ResultMessage.failed("账号已被占用");
		
		List<LoginAccountInfo> infos=null;
		LoginAccountInfo tmp=new LoginAccountInfo();
		if(!StringUtil.isEmpty(info.getCellphone())){
			tmp.setCellphone(info.getCellphone());
			infos=this.loginAccountInfoService.selectByEntity(tmp);
			if(null!=infos&&infos.size()>0) return ResultMessage.failed("手机号码已被占用");
		}
		
		tmp=new LoginAccountInfo();
		tmp.setEmail(info.getEmail());
		infos=this.loginAccountInfoService.selectByEntity(tmp);
		if(null!=infos&&infos.size()>0) return ResultMessage.failed("邮箱已被占用");
		
		LoginAccount loginAccount=new LoginAccount();
		LoginAccountInfo loginAccountInfo=new LoginAccountInfo();
		try {
			BeanUtils.copyProperties(loginAccount, info);
			loginAccount.setId(info.getLoginid());
			BeanUtils.copyProperties(loginAccountInfo, info);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		long id=IDGenerator.generateId();
		loginAccount.setId(id);
		loginAccount.setCreateby(user.getLoginid());
		loginAccount.setCreatetime(new Date());
		String salt=CipherTool.createSalt();
		loginAccount.setPassword(MD5Util.generatePassword(pwd+salt));
		loginAccount.setSalt(salt);
		loginAccount.setState(0);
		this.mapper.insertSelective(loginAccount);
		
		
		loginAccountInfo.setLoginid(loginAccount.getId());
		this.loginAccountInfoService.insertSelective(loginAccountInfo);
		
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage edit(HttpServletRequest request, LoginAccountInfoView info) {
		if(null==info.getLoginid()||info.getLoginid()==0l) return ResultMessage.failed("账号ID不能为空");
		if(StringUtil.isEmpty(info.getEmail())) return ResultMessage.failed("邮箱不能为空");
		
		LoginAccount loginAccount=new LoginAccount();
		LoginAccountInfo loginAccountInfo=new LoginAccountInfo();
		try {
			BeanUtils.copyProperties(loginAccount, info);
			loginAccount.setId(info.getLoginid());
			BeanUtils.copyProperties(loginAccountInfo, info);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		List<LoginAccountInfoView> accounts=this.mapper.isExist(info.getAccount(), null);
		if(null!=accounts&&accounts.size()>0) {
			for(LoginAccountInfoView obj:accounts){
				if(obj.getLoginid().longValue()!=info.getLoginid().longValue()) return ResultMessage.failed("账号已被占用");
			}
		}
		
		LoginAccountInfo tmp=new LoginAccountInfo();
		tmp.setCellphone(info.getCellphone());
		List<LoginAccountInfo> infos=this.loginAccountInfoService.selectByEntity(tmp);
		if(null!=infos&&infos.size()>0) {
			for(LoginAccountInfo obj:infos){
				if(StringUtil.isAllNotEmpty(info.getCellphone(),obj.getCellphone())
						&&obj.getLoginid().longValue()!=info.getLoginid().longValue()
						&&info.getCellphone().equals(obj.getCellphone())) {
					return ResultMessage.failed("手机号码已被占用");
				}
			}
			
		}
		
		tmp=new LoginAccountInfo();
		tmp.setEmail(info.getEmail());
		infos=this.loginAccountInfoService.selectByEntity(tmp);
		if(null!=infos&&infos.size()>0) {
			for(LoginAccountInfo obj:infos){
				if(StringUtil.isAllNotEmpty(info.getEmail(),obj.getEmail())
						&&obj.getLoginid().longValue()!=info.getLoginid().longValue()
						&&info.getEmail().equalsIgnoreCase(obj.getEmail())) {
					return ResultMessage.failed("邮箱已被占用");
				}
			}
		}
		
		this.mapper.updateByPrimaryKeySelective(loginAccount);
		
		this.loginAccountInfoService.updateByPrimaryKeySelective(loginAccountInfo);
		
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage delete(List<Object> ids) {
		if(null!=ids&&ids.size()>0){
        	this.batchDelete(ids);
        	this.loginAccountInfoService.batchDelete(ids);
        	this.loginAccountRoleService.deleteByLoginids(ids);
        	
    	}
    	
    	return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage accountAuth(List<Object> roleids, Long loginid) {
		LoginAccount account=this.mapper.selectByPrimaryKey(loginid);
		if(null==account||account.getState()==1){
			return ResultMessage.failed("账号不存在或者已被禁用，无法完成角色授权");
		}
    	List<Role> list=this.roleService.selectRolesByLoginid(loginid);
    	Map<Long,Long> idsaddMap=new HashMap<Long, Long>();
    	Map<Long,Long> idsdelMap=new HashMap<Long, Long>();
    	if(null!=roleids&&roleids.size()>0){
    		for(Object id:roleids){
    			idsaddMap.put(Long.valueOf(id.toString()), Long.valueOf(id.toString()));
    			idsdelMap.put(Long.valueOf(id.toString()), Long.valueOf(id.toString()));
    		}
    	}
    	Map<Long,Role> rolesdelMap=new HashMap<Long, Role>();
    	if(null!=list&&list.size()>0){
    		
    		for(Role obj:list){
    			rolesdelMap.put(obj.getId(), obj);
    			if(idsaddMap.containsKey(obj.getId())) idsaddMap.remove(obj.getId());
    		}
    		if(idsdelMap.size()>0){
    			for(Map.Entry<Long, Long> entry:idsdelMap.entrySet()){
    				if(rolesdelMap.containsKey(entry.getKey())) rolesdelMap.remove(entry.getKey());
    			}
    		}
    	}
    	
    	//授权角色
    	if(idsaddMap.size()>0){
    		List<LoginAccountRole> loginAccountRoles=new ArrayList<LoginAccountRole>();
    		for(Map.Entry<Long, Long> entry:idsaddMap.entrySet()){
    			LoginAccountRole lar=new LoginAccountRole();
    			lar.setLoginid(account.getId());
    			lar.setOrganid(account.getOrganid());
    			lar.setRoleid(entry.getKey());
    			
    			long id=IDGenerator.generateId();
    			lar.setId(id);
    			loginAccountRoles.add(lar);
    			
    		}
    		this.loginAccountRoleService.batchInsert(loginAccountRoles);
    	}
    	
    	//取消授权角色
    	if(rolesdelMap.size()>0){
    		List<Object> delroleids=new ArrayList<Object>();
    		for(Map.Entry<Long, Role> entry:rolesdelMap.entrySet()){
    			delroleids.add(entry.getKey());
    			
    		}
    		this.loginAccountRoleService.deleteByroleids(loginid, delroleids);
    	}
		
    	return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage resetPassword(List<Object> ids, String password) {
		if(null!=ids&&ids.size()>0){
			List<LoginAccount> list=this.selectByIds(ids);
			if(null!=list&&list.size()>0){
				for(LoginAccount obj:list){
					if(StringUtil.isEmpty(obj.getSalt())){
						LoginAccount tmp=new LoginAccount();
						tmp.setId(obj.getId());
						String salt=CipherTool.createSalt();
						tmp.setSalt(salt);
						tmp.setPassword(MD5Util.generatePassword(password+salt));
						this.updateByPrimaryKeySelective(tmp);
					}else{
						this.resetPassword(obj.getId(), MD5Util.generatePassword(password+obj.getSalt()));
					}
				}
			}
		}
    	
    	return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage stop(List<Object> ids) {
		this.mapper.updateStateByids(1, ids);
    	
    	return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage start(List<Object> ids) {
		this.mapper.updateStateByids(0, ids);
    	
    	return ResultMessage.defaultSuccessMessage();
	}

}
