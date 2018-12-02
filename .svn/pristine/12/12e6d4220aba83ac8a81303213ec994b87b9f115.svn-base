package com.meme.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.meme.core.base.BaseController;
import com.meme.core.base.IController;
import com.meme.core.easyui.MenuTool;
import com.meme.core.easyui.TreeNode;
import com.meme.core.http.ResultMessage;
import com.meme.core.log.LogType;
import com.meme.core.log.annotation.SysLog;
import com.meme.core.pojo.Menu;
import com.meme.core.service.MenuService;
import com.meme.core.util.StringUtil;
import com.meme.core.validator.ValidateBuilder;

@Controller("SystemMenuController")
@RequestMapping("/system/menu/")
public class MenuController extends BaseController implements IController{

	@Resource private MenuService menuService;
	
	@Override
	@RequestMapping(value="loadValidateRules")
	@ResponseBody
	public String loadValidationRules(){
		String js=ValidateBuilder.createJsValidateRules(Menu.class);
		return js;
	}
	
	@RequestMapping("index")
	public ModelAndView index() throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		return new ModelAndView("/system/menu/list",resultMap);
	}
	
	@RequestMapping("getMenuTree")
	@ResponseBody
	public List<TreeNode> getMenuTree(HttpServletRequest request,Long platformid){
		List<Object> list=new ArrayList<Object>();
		list.add(platformid);
		List<Menu> menus=this.menuService.selectByPlatformids(list);
		List<TreeNode> nodes=new ArrayList<TreeNode>();
		if(null!=menus&&menus.size()>0){
			for(Menu m:menus){
				if(m.getPid().longValue()==0l&&m.getHid()==0l){
					TreeNode node=new TreeNode();
					node.setId(m.getId().toString());
					node.setText(m.getName());
					node.setIconCls(m.getIcon());
					m.setUrl(StringUtil.matchUrl(request,m.getUrl()));
					node.setAttributes(JSONObject.parseObject(JSONObject.toJSONString(m)));
					nodes.add(node);
				}else{
					MenuTool.recursionTreeNode(request, nodes, m);
				}
			}
		}
		return nodes;
	}
	
	@RequestMapping("editView")
	public ModelAndView editView(Long id,Long platformid,Long pid,Long hid) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Menu obj=null;
		if(id!=null&&id!=0L){
			obj=this.menuService.selectByPrimaryKey(id);
		}
		if(null!=obj){
			resultMap.put("platformid", obj.getPlatformid());
			resultMap.put("pid", obj.getPid());
			resultMap.put("hid", obj.getHid());
		}else{
			if(platformid!=null&&platformid!=0L){
				resultMap.put("platformid", platformid);
			}
			if(pid!=null){
				resultMap.put("pid", pid);
			}
			if(hid!=null){
				resultMap.put("hid", hid);
			}
		}
		resultMap.put("object", obj);
		return new ModelAndView("/system/menu/edit",resultMap);
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
	@SysLog(event = "新增菜单",type=LogType.ADD)
	public ResultMessage add(HttpServletRequest request,Menu menu){
		return this.menuService.add(request, menu);
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
	@SysLog(event = "修改菜单",type=LogType.UPDATE)
	public ResultMessage edit(HttpServletRequest request,Menu menu){
		return this.menuService.edit(request, menu);
	}
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	@SysLog(event = "删除菜单",type=LogType.DELETE)
	@Transactional
	public ResultMessage delete(@RequestParam("ids[]") List<Object> ids){
        return this.menuService.delete(ids);
	}
	
	
}
