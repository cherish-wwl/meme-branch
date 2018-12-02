package com.meme.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
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

import com.alibaba.fastjson.JSONObject;
import com.meme.core.base.BaseController;
import com.meme.core.base.IController;
import com.meme.core.cache.DictCache;
import com.meme.core.easyui.Pagination;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.core.log.LogType;
import com.meme.core.log.annotation.SysLog;
import com.meme.core.pojo.DictGroup;
import com.meme.core.pojo.DictGroupView;
import com.meme.core.service.DictGroupService;
import com.meme.core.util.StringUtil;
import com.meme.core.validator.ValidateBuilder;

@Controller("SystemDictGroupController")
@RequestMapping("/system/dictgroup/")
public class DictGroupController extends BaseController implements IController{

	@Resource private DictGroupService dictGroupService;
	
	@Override
	@RequestMapping(value="loadValidateRules")
	@ResponseBody
	public String loadValidationRules(){
		String js=ValidateBuilder.createJsValidateRules(DictGroup.class);
		return js;
	}
	
	@RequestMapping("index")
	public ModelAndView index(){
		return new ModelAndView("/system/dictgroup/list");
	}
	
	/**
	 * 列出所有数据字典分组
	 * @return
	 */
	@RequestMapping("listAllDictGroups")
	@ResponseBody
	public List<DictGroup> listAllDictGroups(){
		List<DictGroup> list=this.dictGroupService.selectAll();
		return list;
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Pagination list(HttpServletRequest request,@ModelAttribute("searchForm") Form form) throws Exception{
		int count=this.dictGroupService.count(form);
		List<DictGroup> list=this.dictGroupService.selectByPagination(form);
		List<DictGroupView> objs=new ArrayList<DictGroupView>();
		if(null!=list&&list.size()>0) {
			for(DictGroup obj:list){
				DictGroupView v=JSONObject.parseObject(JSONObject.toJSONString(obj), DictGroupView.class);
				if(StringUtil.isAllNotEmpty(v.getType()))v.setTypeString(DictCache.getDictItem("DICTGROUP_TYPE", v.getType()).getDictitemname());
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
		DictGroup obj=null;
		if(id!=null&&id!=0L){
			obj=this.dictGroupService.selectByPrimaryKey(id);
		}
		resultMap.put("object", obj);
		return new ModelAndView("/system/dictgroup/edit",resultMap);
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
	@SysLog(event = "新增字典组",type=LogType.ADD)
	public ResultMessage add(HttpServletRequest request,DictGroup dictgroup){
		return this.dictGroupService.add(request, dictgroup);
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
	@SysLog(event = "修改字典组",type=LogType.UPDATE)
	public ResultMessage edit(HttpServletRequest request,DictGroup dictgroup){
		return this.dictGroupService.edit(request, dictgroup);
	}
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	@SysLog(event = "删除字典组",type=LogType.DELETE)
	public ResultMessage delete(@RequestParam("ids[]") List<Object> ids){
        return this.dictGroupService.delete(ids);
	}
}