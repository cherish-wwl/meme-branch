package com.meme.system.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import com.meme.core.cache.DictCache;
import com.meme.core.easyui.MenuTool;
import com.meme.core.easyui.Pagination;
import com.meme.core.easyui.TreeNode;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.core.log.LogType;
import com.meme.core.log.annotation.SysLog;
import com.meme.core.pojo.Menu;
import com.meme.core.pojo.Operation;
import com.meme.core.pojo.Organization;
import com.meme.core.pojo.OrganizationView;
import com.meme.core.pojo.Platform;
import com.meme.core.service.MenuService;
import com.meme.core.service.OperationService;
import com.meme.core.service.OrganizationService;
import com.meme.core.service.PlatformService;
import com.meme.core.validator.ValidateBuilder;
import com.meme.core.validator.entity.ColumnRule;

@Controller("SystemOrganizationController")
@RequestMapping("/system/organization/")
public class OrganizationController extends BaseController implements IController{

	@Resource private OrganizationService organizationService;
	@Resource private PlatformService platformService;
	@Resource private MenuService menuService;
	@Resource private OperationService operationService;
	
	
	@Override
	@RequestMapping(value="loadValidateRules")
	@ResponseBody
	public String loadValidationRules(){
		ValidateBuilder.clear();
		
		ColumnRule rule = new ColumnRule();
		rule.setColumn("email");
		Map<String,String> ruleMap=new LinkedHashMap<String, String>();
		ruleMap.put("email", "true");
		rule.setRules(ruleMap);
		ValidateBuilder.addRules(rule);
		
		String js=ValidateBuilder.createJsValidateRules(Organization.class);
		return js;
	}
	
	@RequestMapping("index")
	public ModelAndView index(){
		return new ModelAndView("/system/organization/list");
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Pagination list(HttpServletRequest request,@ModelAttribute("searchForm") Form form) throws Exception{
		int count=this.organizationService.count(form);
		List<Organization> list=this.organizationService.selectByPagination(form);
		List<OrganizationView> objs=new ArrayList<OrganizationView>();
		if(null!=list&&list.size()>0) {
			for(Organization obj:list){
				OrganizationView v=JSONObject.parseObject(JSONObject.toJSONString(obj), OrganizationView.class);
				v.setTypeString(DictCache.getDictItem("ORGANIZATION_TYPE", v.getType().toString()).getDictitemname());
				v.setStateString(DictCache.getDictItem("ORGANIZATION_STATE", v.getState().toString()).getDictitemname());
				objs.add(v);
			}
		}
		
		Pagination pagination=new Pagination();
		pagination.setRows(objs);
		pagination.setTotal(count);
		return pagination;
	}
	
	@RequestMapping("editView")
	public ModelAndView editView(Long id) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Organization obj=null;
		if(id!=null&&id!=0L){
			obj=this.organizationService.selectByPrimaryKey(id);
		}
		resultMap.put("object", obj);
		return new ModelAndView("/system/organization/edit",resultMap);
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
	@SysLog(event = "新增单位",type=LogType.ADD)
	public ResultMessage add(HttpServletRequest request,Organization organization){
		Organization tmpobj=new Organization();
		tmpobj.setName(organization.getName());
		List<Organization> list=this.organizationService.selectByEntity(tmpobj);
		if(null!=list&&list.size()>0){
			return ResultMessage.failed("单位已存在");
		}
		organization.setCreatetime(new Date());
		//不选择取默认值
		if(organization.getType()==null) {
			organization.setType(Integer.valueOf(DictCache.getDefaultDictItem("ORGANIZATION_TYPE").getDictitemcode()));
		}
		if(organization.getState()==null) {
			organization.setState(Integer.valueOf(DictCache.getDefaultDictItem("ORGANIZATION_STATE").getDictitemcode()));
		}
		organization.setCreateby(this.getUser(request).getLoginid());
		return this.organizationService.add(request, organization);
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
	@SysLog(event = "修改单位",type=LogType.UPDATE)
	public ResultMessage edit(HttpServletRequest request,Organization organization){
		Organization tmpobj=new Organization();
		tmpobj.setName(organization.getName());
		List<Organization> list=this.organizationService.selectByEntity(tmpobj);
		if(null!=list&&list.size()>0){
			if(list.get(0).getId().longValue()!=organization.getId().longValue()){
				return ResultMessage.failed("单位已存在，请修改");
			}
		}
		//不选择取默认值
		if(organization.getType()==null) organization.setType(Integer.valueOf(DictCache.getDefaultDictItem("ORGANIZATION_TYPE").getDictitemcode()));
		organization.setCreateby(this.getUser(request).getLoginid());
		return this.organizationService.edit(request, organization);
	}
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	@SysLog(event = "删除单位",type=LogType.DELETE)
	@Transactional
	public ResultMessage delete(@RequestParam("ids[]") List<Object> ids){
        return this.organizationService.delete(ids);
	}
	
	/**
	 * 功能授权页面
	 * @param organid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("funcAuthView")
	public ModelAndView funcAuthView(HttpServletRequest request,Long organid) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("organid", organid);
		return new ModelAndView("/system/organization/functionAuth",resultMap);
	}
	
	@RequestMapping("funcAuth")
	@ResponseBody
	@SysLog(event="功能授权",type=LogType.OTHERS)
	public ResultMessage funcAuth(
			@RequestParam(value="menuids[]",required = false)List<Object> menuids,
			@RequestParam(value="delmenuids[]",required = false)List<Object> delmenuids,
			@RequestParam(value="operationids[]",required = false)List<Object> operationids,
			@RequestParam(value="deloperationids[]",required = false)List<Object> deloperationids,
			@RequestParam(value="organid",required = true)Long organid){
		return this.organizationService.funcAuth(menuids, delmenuids, operationids, deloperationids, organid);
	}
	
	@RequestMapping("initAuthorization")
	@ResponseBody
	public JSONObject initAuthorization(HttpServletRequest request,Long organid){
		List<Menu> menusList=this.menuService.selectOrganMenus(organid, null);
		List<Operation> operationsList=this.operationService.selectOrganOperations(organid, null, null);
		
		JSONObject obj=new JSONObject();
		obj.put("menus", menusList);
		obj.put("operations", operationsList);
		return obj;
	}
	
	/**
	 * 获取授权平台
	 * @param organid
	 * @return
	 */
	@RequestMapping("getOrganPlatforms")
	@ResponseBody
	public List<Platform> getOrganPlatforms(Long organid){
		List<Platform> list=this.platformService.selectByOrganid(organid);
		
		return list;
	}
	
	/**
	 * 获取授权菜单
	 * @param organid
	 * @param platformid
	 * @return
	 */
	@RequestMapping("getOrganMenus")
	@ResponseBody
	public List<TreeNode> getOrganMenus(HttpServletRequest request,Long organid,Long platformid){
		List<Object> platformids=new ArrayList<Object>();
		platformids.add(platformid);
		List<Menu> list=this.menuService.selectOrganMenus(organid, platformids);
		return MenuTool.getTreeNodes(request, list);
	}
	
	/**
	 * 获取授权操作
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getOrganOperations")
	@ResponseBody
	public List<TreeNode> getOrganOperations(HttpServletRequest request,Long organid,Long menuid) throws Exception{
		List<TreeNode> nodes=new ArrayList<TreeNode>();
		if(null!=organid&&organid!=0l){
			List<Object> list=new ArrayList<Object>();
			if(null!=menuid&&menuid!=0l) list.add(menuid);
			List<Operation> operations=this.operationService.selectOrganOperations(organid, list, null);
			if(null!=operations&&operations.size()>0){
				for(Operation o:operations){
					TreeNode node=new TreeNode();
					node.setId(o.getId().toString());
					node.setText(o.getName());
					node.setIconCls(o.getIcon());
					node.setAttributes(JSONObject.parseObject(JSONObject.toJSONString(o)));
					nodes.add(node);
				}
			}
		}
		return nodes;
	}
	
	/**
	 * 认证
	 * @param ids
	 * @return
	 */
	@RequestMapping("auth")
	@ResponseBody
	@SysLog(event = "认证单位",type=LogType.OTHERS)
	@Transactional
	public ResultMessage auth(@RequestParam("ids[]") List<Object> ids){
		return this.organizationService.auth(ids);
	}
	
	/**
	 * 禁用单位
	 * @param ids
	 * @return
	 */
	@RequestMapping("cancelAuth")
	@ResponseBody
	@SysLog(event = "禁用单位",type=LogType.OTHERS)
	@Transactional
	public ResultMessage cancelAuth(@RequestParam("ids[]") List<Object> ids){
		return this.organizationService.cancelAuth(ids);
	}
}
