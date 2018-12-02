package com.meme.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.meme.core.base.BaseController;
import com.meme.core.base.IController;
import com.meme.core.easyui.Pagination;
import com.meme.core.easyui.TreeNode;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.core.log.LogType;
import com.meme.core.log.annotation.SysLog;
import com.meme.core.pojo.Menu;
import com.meme.core.pojo.Operation;
import com.meme.core.pojo.Role;
import com.meme.core.service.MenuService;
import com.meme.core.service.OperationService;
import com.meme.core.service.RoleService;
import com.meme.core.util.StringUtil;
import com.meme.core.validator.ValidateBuilder;

@Controller("SystemRoleController")
@RequestMapping("/system/role/")
public class RoleController extends BaseController implements IController{

	@Resource private RoleService roleService;
	@Resource private MenuService menuService;
	@Resource private OperationService operationService;
	
	@Override
	@RequestMapping(value="loadValidateRules")
	@ResponseBody
	public String loadValidationRules(){
		String js=ValidateBuilder.createJsValidateRules(Role.class);
		return js;
	}
	
	@RequestMapping("index")
	public ModelAndView index(Long organid){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("organid", organid);
		return new ModelAndView("/system/role/list",resultMap);
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Pagination list(HttpServletRequest request,@ModelAttribute("searchForm") Form form) throws Exception{
		int count=this.roleService.count(form);
		List<Role> list=this.roleService.selectByPagination(form);
		
		Pagination pagination=new Pagination();
		pagination.setRows(list);
		pagination.setTotal(count);
		return pagination;
	}
	
	@RequestMapping("editView")
	public ModelAndView editView(Long organid,Long id) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Role obj=null;
		if(id!=null&&id!=0L){
			obj=this.roleService.selectByPrimaryKey(id);
		}
		if(null!=obj){
			resultMap.put("organid", obj.getOrganid());
		}else resultMap.put("organid", organid);
		resultMap.put("object", obj);
		return new ModelAndView("/system/role/edit",resultMap);
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
	@SysLog(event = "新增角色",type=LogType.ADD)
	public ResultMessage add(HttpServletRequest request,Role role){
		return this.roleService.add(request, role);
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
	@SysLog(event = "修改角色",type=LogType.UPDATE)
	public ResultMessage edit(HttpServletRequest request,Role role){
		return this.roleService.edit(request, role);
	}
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	@SysLog(event = "删除角色",type=LogType.DELETE)
	@Transactional
	public ResultMessage delete(@RequestParam("ids[]") List<Object> ids){
        return this.roleService.delete(ids);
	}
	
	/**
	 * 角色授权页面
	 * @param organid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("funcAuthView")
	public ModelAndView funcAuthView(HttpServletRequest request,Long roleid,Long organid) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Role obj=null;
		if(null!=roleid&&roleid!=0l){
			obj=this.roleService.selectByPrimaryKey(roleid);
		}
		if(null!=obj){
			resultMap.put("organid", obj.getOrganid());
		}else{
			resultMap.put("organid", organid);
		}
		resultMap.put("roleid", roleid);
		return new ModelAndView("/system/role/functionAuth",resultMap);
	}
	
	
	@RequestMapping("funcAuth")
	@ResponseBody
	@SysLog(event="角色授权",type=LogType.OTHERS)
	public ResultMessage funcAuth(
			@RequestParam(value="menuids[]",required = false)List<Object> menuids,
			@RequestParam(value="delmenuids[]",required = false)List<Object> delmenuids,
			@RequestParam(value="operationids[]",required = false)List<Object> operationids,
			@RequestParam(value="deloperationids[]",required = false)List<Object> deloperationids,
			@RequestParam(value="roleid",required = true)Long roleid){
		return this.roleService.funcAuth(menuids, delmenuids, operationids, deloperationids, roleid);
	}
	
	@RequestMapping("initAuthorization")
	@ResponseBody
	public JSONObject initAuthorization(HttpServletRequest request,Long roleid){
		List<Object> roleids=new ArrayList<Object>();
		if(null!=roleid&&roleid!=0l) roleids.add(roleid);
		List<Menu> menusList=this.menuService.selectRoleMenus(roleids, null);
		List<Operation> operationsList=this.operationService.selectRoleOperations(roleids, null, null);
		
		JSONObject obj=new JSONObject();
		obj.put("menus", menusList);
		obj.put("operations", operationsList);
		return obj;
	}
	
	/**
	 * 
	 * @param type 选中类型，radio-单选,checkbox-多选
	 * @param organid
	 * @return
	 */
	@RequestMapping("listRolesView")
	public ModelAndView listRolesView(String type,Long organid){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("organid", organid);
		if(StringUtil.isEmpty(type)) type="checkbox";
		resultMap.put("type", type);
		return new ModelAndView("/system/role/listRoles",resultMap);
	}
	
	@RequestMapping("listRoles")
	@ResponseBody
	public List<TreeNode> listRoles(HttpServletRequest request,Long organid) throws Exception{
		List<TreeNode> nodes=new ArrayList<TreeNode>();
		Role tmp=new Role();
		tmp.setOrganid(organid);
    	List<Role> list=this.roleService.selectByEntity(tmp);
    	if(null!=list&&list.size()>0){
    		for(Role obj:list){
				TreeNode node=new TreeNode();
				node.setId(obj.getId().toString());
				node.setText(obj.getName());
				node.setAttributes(JSONObject.parseObject(JSONObject.toJSONString(obj)));
				nodes.add(node);
			}
    	}
    	return nodes;
	}
	
}
