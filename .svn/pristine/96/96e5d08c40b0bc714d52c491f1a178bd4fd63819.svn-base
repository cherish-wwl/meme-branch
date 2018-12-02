package com.meme.system.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.meme.core.base.BaseController;
import com.meme.core.base.IController;
import com.meme.core.cache.DictCache;
import com.meme.core.easyui.Pagination;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.core.log.LogType;
import com.meme.core.log.annotation.SysLog;
import com.meme.core.pojo.LoginAccountInfo;
import com.meme.core.pojo.LoginAccountInfoView;
import com.meme.core.pojo.Role;
import com.meme.core.service.LoginAccountService;
import com.meme.core.service.RoleService;
import com.meme.core.validator.ValidateBuilder;
import com.meme.core.validator.entity.ColumnRule;

@Controller("SystemAccountController")
@RequestMapping("/system/account/")
public class AccountController extends BaseController implements IController{

	@Resource private LoginAccountService loginAccountService;
	@Resource private RoleService roleService;
	
	@Override
	@RequestMapping(value="loadValidateRules")
	@ResponseBody
	public String loadValidationRules(){
		ValidateBuilder.clear();
		
		ColumnRule rule=new ColumnRule();
		rule.setColumn("account");
		Map<String,String> ruleMap=new LinkedHashMap<String, String>();
		ruleMap.put("required", "true");
		ruleMap.put("byteRangeLength", "[1,50]");
		rule.setRules(ruleMap);
		ValidateBuilder.addRules(rule);
		
		rule = new ColumnRule();
		rule.setColumn("email");
		ruleMap=new LinkedHashMap<String, String>();
		ruleMap.put("email", "true");
		rule.setRules(ruleMap);
		ValidateBuilder.addRules(rule);

		rule = new ColumnRule();
		rule.setColumn("pwd");
		ruleMap=new LinkedHashMap<String, String>();
		ruleMap.put("required", "true");
//		ruleMap.put("equalTo", "'#confirmpwd'");
		ruleMap.put("minlength", "8");
		rule.setRules(ruleMap);
		ValidateBuilder.addRules(rule);

		rule = new ColumnRule();
		rule.setColumn("confirmpwd");
		ruleMap=new LinkedHashMap<String, String>();
		ruleMap.put("required", "true");
//		ruleMap.put("equalTo", "'#pwd'");
		ruleMap.put("minlength", "8");
		rule.setRules(ruleMap);
		ValidateBuilder.addRules(rule);
		
		rule = new ColumnRule();
		rule.setColumn("qq");
		ruleMap=new LinkedHashMap<String, String>();
		ruleMap.put("digits", "true");
		rule.setRules(ruleMap);
		ValidateBuilder.addRules(rule);
		
		String js=ValidateBuilder.createJsValidateRules(LoginAccountInfo.class);
		return js;
	}
	
	@RequestMapping("index")
	public ModelAndView index(Long organid){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("organid", organid);
		return new ModelAndView("/system/account/list",resultMap);
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Pagination list(HttpServletRequest request,@ModelAttribute("searchForm") Form form) throws Exception{
		int count=this.loginAccountService.countView(form);
		List<LoginAccountInfoView> list=this.loginAccountService.selectByPaginationView(form);
		if(null!=list&&list.size()>0){
			for(int i=0;i<list.size();i++){
				if(null!=DictCache.getDictItem("ACCOUNT_STATE", list.get(i).getState().toString())) {
					list.get(i).setAccountStateText(DictCache.getDictItem("ACCOUNT_STATE", list.get(i).getState().toString()).getDictitemname());
				}
				if(list.get(i).getSex()!=null&&DictCache.getDictItem("SEX", list.get(i).getSex().toString())!=null) {
					list.get(i).setSexText(DictCache.getDictItem("SEX", list.get(i).getSex().toString()).getDictitemname());
				}
			}
		}
		
		Pagination pagination=new Pagination();
		pagination.setRows(list);
		pagination.setTotal(count);
		return pagination;
	}
	
	@RequestMapping("editView")
	public ModelAndView editView(Long organid,Long id) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		LoginAccountInfoView obj=null;
		if(id!=null&&id!=0L){
			obj=this.loginAccountService.selectLoginAccountInfoViewById(id);
		}
		if(null!=obj){
			resultMap.put("organid", obj.getOrganid());
		}else resultMap.put("organid", organid);
		resultMap.put("object", obj);
		return new ModelAndView("/system/account/edit",resultMap);
	}
	
	/**
	 * 增加
	 * @param request
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("add")
	@ResponseBody
	@SysLog(event = "新增用户",type=LogType.ADD)
	public ResultMessage add(HttpServletRequest request,LoginAccountInfoView info,String pwd,String confirmpwd){
		return this.loginAccountService.add(request, info, pwd, confirmpwd);
	}
	
	/**
	 * 修改
	 * @param request
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@ResponseBody
	@SysLog(event = "修改用户",type=LogType.UPDATE)
	public ResultMessage edit(HttpServletRequest request,LoginAccountInfoView info){
		return this.loginAccountService.edit(request, info);
	}
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	@SysLog(event = "删除用户",type=LogType.DELETE)
	public ResultMessage delete(@RequestParam("ids[]") List<Object> ids){
        return this.loginAccountService.delete(ids);
	}
	
	@RequestMapping("resetPassword")
	@ResponseBody
	@SysLog(event = "重置密码",type=LogType.OTHERS)
	public ResultMessage resetPassword(@RequestParam(value="ids[]",required=true) List<Object> ids,@RequestParam(value="password",required=true)String password){
		return this.loginAccountService.resetPassword(ids, password);
	}
	
	
	@RequestMapping("accountAuth")
	@ResponseBody
	@SysLog(event = "授权用户角色",type=LogType.OTHERS)
	public ResultMessage accountAuth(@RequestParam(value="roleids[]",required=false) List<Object> roleids,@RequestParam(value="loginid",required=true)Long loginid){
		return this.loginAccountService.accountAuth(roleids, loginid);
	}
	
	/**
	 * 查看明细
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("view")
	public ModelAndView view(Long id) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		LoginAccountInfoView account=null;
		List<LoginAccountInfoView> list=this.loginAccountService.isExist(null,id);
		if(null!=list&&list.size()>0){
			account=list.get(0);
			if(null!=DictCache.getDictItem("ACCOUNT_STATE", account.getState().toString())) {
				account.setAccountStateText(DictCache.getDictItem("ACCOUNT_STATE", account.getState().toString()).getDictitemname());
			}
			if(account.getSex()!=null&&DictCache.getDictItem("SEX", account.getSex().toString())!=null) {
				account.setSexText(DictCache.getDictItem("SEX", account.getSex().toString()).getDictitemname());
			}
		}
		resultMap.put("object", account);
		return new ModelAndView("/system/account/view",resultMap);
	}
	
	/**
	 * 启用账号
	 * @param ids
	 * @return
	 */
	@RequestMapping("start")
	@ResponseBody
	@SysLog(event = "启用账号",type=LogType.OTHERS)
	public ResultMessage start(@RequestParam("ids[]") List<Object> ids){
		return this.loginAccountService.start(ids);
	}
	
	/**
	 * 禁用账号
	 * @param ids
	 * @return
	 */
	@RequestMapping("stop")
	@ResponseBody
	@SysLog(event = "禁用账号",type=LogType.OTHERS)
	public ResultMessage stop(@RequestParam("ids[]") List<Object> ids){
		return this.loginAccountService.stop(ids);
	}
	
	/**
	 * 获取用户所拥有的角色列表
	 * @param loginid
	 * @return
	 */
	@RequestMapping("getAccountRoles")
	@ResponseBody
	public List<Role> getAccountRoles(Long loginid){
		return this.roleService.selectRolesByLoginid(loginid);
	}
}
