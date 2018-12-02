package com.meme.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.meme.core.base.BaseController;
import com.meme.core.base.IController;
import com.meme.core.easyui.TreeNode;
import com.meme.core.http.ResultMessage;
import com.meme.core.log.LogType;
import com.meme.core.log.annotation.SysLog;
import com.meme.core.pojo.Department;
import com.meme.core.service.DepartmentService;
import com.meme.core.util.StringUtil;
import com.meme.core.validator.ValidateBuilder;

@Controller("SystemDepartmentController")
@RequestMapping("/system/department/")
public class DepartmentController extends BaseController implements IController{

	@Resource private DepartmentService departmentService;
	
	@Override
	@RequestMapping(value="loadValidateRules")
	@ResponseBody
	public String loadValidationRules(){
		String js=ValidateBuilder.createJsValidateRules(Department.class);
		return js;
	}
	
	@RequestMapping("index")
	public ModelAndView index(Long organid){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("organid", organid);
		return new ModelAndView("/system/department/list",resultMap);
	}
	
	@RequestMapping("list")
	@ResponseBody
	public List<TreeNode> list(HttpServletRequest request,Long organid) throws Exception{
		List<TreeNode> nodes=new ArrayList<TreeNode>();
		if(null!=organid&&organid!=0L){
			List<Department> list=this.departmentService.selectByOrganid(organid);
			if(null!=list&&list.size()>0){
				List<TreeNode> tmp=this.getTreeNodes(request, list);
				if(null!=tmp&&tmp.size()>0) nodes.addAll(tmp);
			}
		}
		return nodes;
	}
	
	/**
	 * 递归easyui 树
	 * @param request
	 * @param list
	 * @param menu
	 * @return
	 */
	public static List<TreeNode> recursionTreeNode(HttpServletRequest request,List<TreeNode> list,Department obj){
		for(int i=0;i<list.size();i++){
			TreeNode node=list.get(i);
			if(obj.getPid().longValue()==Long.valueOf(node.getId().toString()).longValue()){
				TreeNode tn=new TreeNode();
				tn.setId(obj.getId());
				tn.setText(obj.getName());
				tn.setAttributes(JSONObject.parseObject(JSONObject.toJSONString(obj)));
				boolean flag=false;
				if(null!=node.getChildren()&&node.getChildren().size()>0){
					for(TreeNode t:node.getChildren()){
						if(t.getId().toString().equals(obj.getId().toString())) {flag=true;break;}
					}
				}
				if(!flag){
					list.get(i).getChildren().add(tn);
				}
				break;
			}else{
				recursionTreeNode(request,list.get(i).getChildren(), obj);
			}
		}
		return list;
	}
	
	@RequestMapping("editView")
	public ModelAndView editView(Long id,Long organid,Long pid) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Department obj=null;
		if(id!=null&&id!=0L){
			obj=this.departmentService.selectByPrimaryKey(id);
			resultMap.put("organid", obj.getOrganid());
			resultMap.put("pid", obj.getPid());
		}else{
			resultMap.put("organid", organid);
			resultMap.put("pid", pid);
		}
		resultMap.put("object", obj);
		return new ModelAndView("/system/department/edit",resultMap);
	}
	
	/**
	 * 生成easyui 树结构
	 * @param request
	 * @param list
	 * @return
	 */
	public List<TreeNode> getTreeNodes(HttpServletRequest request,List<Department> list){
		List<TreeNode> nodes=new ArrayList<TreeNode>();
		if(null!=list&&list.size()>0){
			for(Department obj:list){
				if(obj.getPid().longValue()==0l){
					TreeNode node=new TreeNode();
					node.setId(obj.getId().toString());
					node.setText(obj.getName());
					node.setAttributes(JSONObject.parseObject(JSONObject.toJSONString(obj)));
					nodes.add(node);
				}else{
					recursionTreeNode(request, nodes, obj);
				}
			}
		}
		return nodes;
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
	@SysLog(event = "新增部门",type=LogType.ADD)
	public ResultMessage add(HttpServletRequest request,Department department){
		return this.departmentService.add(request, department);
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
	@SysLog(event = "修改部门",type=LogType.UPDATE)
	public ResultMessage edit(HttpServletRequest request,Department department){
		return this.departmentService.edit(request, department);
	}
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	@SysLog(event = "删除部门",type=LogType.DELETE)
	public ResultMessage delete(@RequestParam("ids[]") List<Object> ids,@RequestParam("organid")Long organid){
		return this.departmentService.delete(ids, organid);
	}
	
	/**
	 * 
	 * @param type 选中类型，radio-单选,checkbox-多选
	 * @param organid
	 * @return
	 */
	@RequestMapping("listDeptsView")
	public ModelAndView listDeptsView(String type,Long organid){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("organid", organid);
		if(StringUtil.isEmpty(type)) type="radio";
		resultMap.put("type", type);
		return new ModelAndView("/system/department/listDepts",resultMap);
	}
	
	@RequestMapping("listDepts")
	@ResponseBody
	public List<TreeNode> listDepts(HttpServletRequest request,Long organid){
		List<TreeNode> nodes=new ArrayList<TreeNode>();
    	List<Department> list=this.departmentService.selectByOrganid(organid);
    	if(null!=list&&list.size()>0){
    		List<TreeNode> tmp=this.getTreeNodes(request, list);
    		if(tmp.size()>0) nodes.addAll(tmp);
    	}
    	return nodes;
	}
}
