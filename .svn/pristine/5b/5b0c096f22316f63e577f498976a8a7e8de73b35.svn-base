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
import com.meme.im.pojo.TvProgram;
import com.meme.im.service.TvProgramService;

@Controller
@RequestMapping("/im/tv/program/")
public class TvProgramController extends BaseController implements IController {
	
	@Resource private TvProgramService tvProgramService;

	@Override
	@RequestMapping(value="loadValidateRules")
	@ResponseBody
	public String loadValidationRules(){
		String js=ValidateBuilder.createJsValidateRules(TvProgram.class);
		return js;
	}
	
	@RequestMapping("index")
	public ModelAndView index() {
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("/im/tv/program/list", model);
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Pagination list(HttpServletRequest request,ImForm form){
		List<Map<String,Object>> list=this.tvProgramService.selectByPaginationView(form);
		int count=this.tvProgramService.countView(form);
		if(null!=list&&list.size()>0){
			for(int i=0;i<list.size();i++){
				Map<String,Object> map=list.get(i);
				DictItemView istop=null;
				if(null!=map.get("istop")) istop=DictCache.getDictItem("SF", map.get("istop").toString());
				if(null!=istop){
					list.get(i).put("istopText", istop.getDictitemname());
				}
				
				DictItemView ishotshow=null;
				if(null!=map.get("ishotshow")) ishotshow=DictCache.getDictItem("SF", map.get("ishotshow").toString());
				if(null!=ishotshow){
					list.get(i).put("ishotshowText", ishotshow.getDictitemname());
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
			TvProgram m=this.tvProgramService.selectByPrimaryKey(id);
			if(null!=m) model.put("object", m);
		}
		return new ModelAndView("/im/tv/program/edit",model);
	}
	
	@RequestMapping("doAdd")
	@ResponseBody
	@SysLog(event = "添加节目",type=LogType.ADD)
	public ResultMessage doAdd(HttpServletRequest request,HttpServletResponse response,TvProgram obj){
		return this.tvProgramService.add(request, obj);
	}
	
	@RequestMapping("doEdit")
	@ResponseBody
	@SysLog(event = "编辑节目",type=LogType.UPDATE)
	public ResultMessage doEdit(HttpServletRequest request,HttpServletResponse response,TvProgram obj){
		return this.tvProgramService.edit(request, obj);
	}
	
	@RequestMapping("delete")
	@ResponseBody
	@SysLog(event = "删除节目",type=LogType.DELETE)
	public ResultMessage delete(@RequestParam("ids[]") List<Object> ids){
        return this.tvProgramService.delete(ids);
	}
}
