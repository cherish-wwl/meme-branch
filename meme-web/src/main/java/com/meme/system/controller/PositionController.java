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
import com.meme.core.pojo.Position;
import com.meme.core.pojo.PositionView;
import com.meme.core.service.PositionService;
import com.meme.core.util.StringUtil;
import com.meme.core.validator.ValidateBuilder;

@Controller("SystemPositionController")
@RequestMapping("/system/position/")
public class PositionController extends BaseController implements IController{

	@Resource private PositionService positionService;
	
	@Override
	@RequestMapping(value="loadValidateRules")
	@ResponseBody
	public String loadValidationRules(){
		String js=ValidateBuilder.createJsValidateRules(Position.class);
		return js;
	}
	
	@RequestMapping("index")
	public ModelAndView index(Long organid){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("organid", organid);
		return new ModelAndView("/system/position/list",resultMap);
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Pagination list(HttpServletRequest request,@ModelAttribute("searchForm") Form form) throws Exception{
		form.init();
		int count=this.positionService.countView(form);
		List<PositionView> list=this.positionService.selectByPaginationView(form);
		
		Pagination pagination=new Pagination();
		pagination.setRows(list);
		pagination.setTotal(count);
		return pagination;
	}
	
	@RequestMapping("editView")
	public ModelAndView editView(Long organid,Long id) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		PositionView obj=null;
		if(id!=null&&id!=0L){
			obj=this.positionService.selectPositionViewById(id);
		}
		if(null!=obj){
			resultMap.put("organid", obj.getOrganid());
		}else resultMap.put("organid", organid);
		resultMap.put("object", obj);
		return new ModelAndView("/system/position/edit",resultMap);
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
	@SysLog(event = "新增职位",type=LogType.ADD)
	public ResultMessage add(HttpServletRequest request,Position position){
		return this.positionService.add(request, position);
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
	@SysLog(event = "修改职位",type=LogType.UPDATE)
	public ResultMessage edit(HttpServletRequest request,Position position){
		return this.positionService.edit(request, position);
	}
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	@SysLog(event = "删除职位",type=LogType.DELETE)
	@Transactional
	public ResultMessage delete(@RequestParam("ids[]") List<Object> ids){
        return this.positionService.delete(ids);
	}
	
	/**
	 * 
	 * @param type 选中类型，radio-单选,checkbox-多选
	 * @param organid
	 * @return
	 */
	@RequestMapping("listPositionsView")
	public ModelAndView listPositionsView(String type,Long organid,Long deptid){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("organid", organid);
		if(null!=deptid) resultMap.put("deptid", deptid);
		if(StringUtil.isEmpty(type)) type="radio";
		resultMap.put("type", type);
		return new ModelAndView("/system/position/listPositions",resultMap);
	}
	
	@RequestMapping("listPositions")
	@ResponseBody
	public List<TreeNode> listPositions(HttpServletRequest request,Long organid,Long deptid) throws Exception{
		List<TreeNode> nodes=new ArrayList<TreeNode>();
    	List<Position> list=this.positionService.selectDeptPosition(organid,deptid);
    	if(null!=list&&list.size()>0){
    		for(Position obj:list){
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
