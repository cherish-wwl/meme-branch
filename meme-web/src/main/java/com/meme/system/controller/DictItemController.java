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
import com.meme.core.pojo.DictItem;
import com.meme.core.pojo.DictItemView;
import com.meme.core.service.DictItemService;
import com.meme.core.validator.ValidateBuilder;

@Controller("SystemDictItemController")
@RequestMapping("/system/dictitem/")
public class DictItemController extends BaseController implements IController{

	@Resource
	private DictItemService dictItemService;
	
	@Override
	@RequestMapping(value="loadValidateRules")
	@ResponseBody
	public String loadValidationRules(){
		String js=ValidateBuilder.createJsValidateRules(DictItem.class);
		return js;
	}
	
	@RequestMapping("index")
	public ModelAndView index(Long dictgroupid){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("dictgroupid", dictgroupid);
		return new ModelAndView("/system/dictitem/list",resultMap);
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Pagination list(HttpServletRequest request,@ModelAttribute("searchForm") Form form) throws Exception{
		Pagination pagination=new Pagination();
		if(null!=form.getDictgroupid()&&form.getDictgroupid()!=0l){
			int count=this.dictItemService.count(form);
			List<DictItem> list=this.dictItemService.selectByPagination(form);
			List<DictItemView> objs=new ArrayList<DictItemView>();
			if(null!=list&&list.size()>0) {
				for(DictItem obj:list){
					DictItemView v=JSONObject.parseObject(JSONObject.toJSONString(obj), DictItemView.class);
					v.setIsdefaultString(DictCache.getDictItem("SF", v.getIsdefault().toString()).getDictitemname());
					objs.add(v);
				}
			}
			pagination.setRows(objs);
			pagination.setTotal(count);
		}
		return pagination;
	}
	
	@RequestMapping("editView")
	public ModelAndView editView(Long dictgroupid,Long id) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		DictItem obj=null;
		if(id!=null&&id!=0L){
			obj=this.dictItemService.selectByPrimaryKey(id);
			resultMap.put("dictgroupid", obj.getDictgroupid());
		}else resultMap.put("dictgroupid", dictgroupid);
		
		resultMap.put("object", obj);
		return new ModelAndView("/system/dictitem/edit",resultMap);
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
	@SysLog(event = "新增字典项",type=LogType.ADD)
	public ResultMessage add(HttpServletRequest request,DictItem dictitem){
		return this.dictItemService.add(request, dictitem);
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
	@SysLog(event = "修改字典项",type=LogType.UPDATE)
	public ResultMessage edit(HttpServletRequest request,DictItem dictitem){
		return this.dictItemService.edit(request, dictitem);
	}
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	@SysLog(event = "删除字典项",type=LogType.DELETE)
	public ResultMessage delete(@RequestParam("ids[]") List<Object> ids){
        return this.dictItemService.delete(ids);
	}
	
	@RequestMapping("importView")
	public ModelAndView importView(HttpServletRequest request,Long dictgroupid){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("dictgroupid", dictgroupid);
		return new ModelAndView("/system/dictitem/import",model);
	}
	
	@RequestMapping("doImport")
	@ResponseBody
	@SysLog(event = "批量导入字典项",type=LogType.OTHERS)
	public String doImport(HttpServletRequest request,Long dictgroupid){
		return this.dictItemService.doImport(request,dictgroupid);
	}
}
