package com.meme.im.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.meme.core.base.BaseController;
import com.meme.core.base.IController;
import com.meme.core.cache.DictCache;
import com.meme.core.easyui.Pagination;
import com.meme.core.http.ResultMessage;
import com.meme.core.log.LogType;
import com.meme.core.log.annotation.SysLog;
import com.meme.core.pojo.DictItemView;
import com.meme.core.util.StringUtil;
import com.meme.core.validator.ValidateBuilder;
import com.meme.im.form.ImForm;
import com.meme.im.pojo.TvStation;
import com.meme.im.service.TvStationService;

@Controller
@RequestMapping("/im/tv/station/")
public class TvStationController extends BaseController implements IController{
	
	@Resource private TvStationService tvStationService;

	@Override
	@RequestMapping(value="loadValidateRules")
	@ResponseBody
	public String loadValidationRules(){
		String js=ValidateBuilder.createJsValidateRules(TvStation.class);
		return js;
	}
	
	@RequestMapping("index")
	public ModelAndView index() {
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("/im/tv/station/list", model);
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Pagination list(HttpServletRequest request,ImForm form){
		List<Map<String,Object>> list=this.tvStationService.selectByPaginationView(form);
		int count=this.tvStationService.countView(form);
		if(null!=list&&list.size()>0){
			for(int i=0;i<list.size();i++){
				Map<String,Object> map=list.get(i);
				DictItemView tvtype=null;
				if(null!=map.get("tvtype")) tvtype=DictCache.getDictItem("TV_TYPE", map.get("tvtype").toString());
				if(null!=tvtype){
					list.get(i).put("tvtypeText", tvtype.getDictitemname());
				}
			}
		}
		Pagination pagination=new Pagination(list,count);
		return pagination;
	}
	
	@RequestMapping("edit")
	public ModelAndView edit(String id){
		Map<String,Object> model=new HashMap<String, Object>();
		if(StringUtil.isAllNotEmpty(id)){
			TvStation m=this.tvStationService.selectByPrimaryKey(id);
			if(null!=m) model.put("object", m);
		}
		return new ModelAndView("/im/tv/station/edit",model);
	}
	
	@RequestMapping("doAdd")
	@ResponseBody
	@SysLog(event = "添加频道",type=LogType.ADD)
	public ResultMessage doAdd(HttpServletRequest request,HttpServletResponse response,TvStation obj){
		return this.tvStationService.add(request, obj);
	}
	
	@RequestMapping("doEdit")
	@ResponseBody
	@SysLog(event = "编辑频道",type=LogType.UPDATE)
	public ResultMessage doEdit(HttpServletRequest request,HttpServletResponse response,TvStation obj){
		return this.tvStationService.edit(request, obj);
	}
	
	@RequestMapping("delete")
	@ResponseBody
	@SysLog(event = "删除频道",type=LogType.DELETE)
	public ResultMessage delete(@RequestParam("ids[]") List<Object> ids){
        return this.tvStationService.delete(ids);
	}
	
	@RequestMapping("getTvStation")
	@ResponseBody
	public List<TvStation> getTvStation(){
		return this.tvStationService.selectAll();
	}
}