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
import com.meme.core.easyui.TreeNode;
import com.meme.core.http.ResultMessage;
import com.meme.core.log.LogType;
import com.meme.core.log.annotation.SysLog;
import com.meme.core.pojo.Operation;
import com.meme.core.service.OperationService;
import com.meme.core.service.OrganOperationService;
import com.meme.core.service.RoleOperationService;
import com.meme.core.util.IDGenerator;

@Controller("SystemOperationController")
@RequestMapping("/system/operation/")
public class OperationController extends BaseController{

	@Resource
	private OperationService operationService;
	@Resource
	private OrganOperationService organOperationService;
	@Resource
	private RoleOperationService roleOperationService;
	
	
	/**
	 * 获取操作
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getOperationTree")
	@ResponseBody
	public List<TreeNode> getOperationTree(HttpServletRequest request,Long menuid) throws Exception{
		List<TreeNode> nodes=new ArrayList<TreeNode>();
		if(null!=menuid&&menuid!=0L){
			List<Object> list=new ArrayList<Object>();
			list.add(menuid);
			List<Operation> operations=this.operationService.selectByMenuids(list);
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
	
	@RequestMapping("editView")
	public ModelAndView editView(Long id,Long menuid,Long platformid) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Operation obj=null;
		if(id!=null&&id!=0L){
			obj=this.operationService.selectByPrimaryKey(id);
			resultMap.put("menuid", obj.getMenuid());
			resultMap.put("platformid", obj.getPlatformid());
		}else{
			resultMap.put("menuid", menuid);
			resultMap.put("platformid", platformid);
		}
		resultMap.put("object", obj);
		return new ModelAndView("/system/operation/edit",resultMap);
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
	@SysLog(event = "新增操作",type=LogType.ADD)
	public ResultMessage add(HttpServletRequest request,Operation operation) throws Exception{
		try{
			long id=IDGenerator.generateId();
			operation.setId(id);
			this.operationService.insertSelective(operation);
			
			return ResultMessage.defaultSuccessMessage();
		}catch (Exception e) {
			e.printStackTrace();
			return ResultMessage.defaultFaileMessage();
		}
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
	@SysLog(event = "修改操作",type=LogType.UPDATE)
	public ResultMessage edit(HttpServletRequest request,Operation operation) throws Exception{
		try{
			this.operationService.updateByPrimaryKeySelective(operation);
			
			return ResultMessage.defaultSuccessMessage();
		}catch (Exception e) {
			e.printStackTrace();
			return ResultMessage.defaultFaileMessage();
		}
	}
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	@SysLog(event = "删除操作",type=LogType.DELETE)
	@Transactional
	public ResultMessage delete(@RequestParam("ids[]") List<Object> ids){
        try{
        	
        	this.operationService.batchDelete(ids);
        	this.organOperationService.deleteByOperationids(null,ids);
        	this.roleOperationService.deleteByOperationids(null,ids);
        	
        	return ResultMessage.defaultSuccessMessage();
        }catch (Exception e) {
			e.printStackTrace();
			return ResultMessage.defaultFaileMessage();
		}
	}
}
