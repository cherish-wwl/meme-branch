package com.meme.system.controller;

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

import com.meme.core.base.BaseController;
import com.meme.core.easyui.Pagination;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.core.log.LogType;
import com.meme.core.log.annotation.SysLog;
import com.meme.core.pojo.Log;
import com.meme.core.service.LogService;

@Controller("SystemLogController")
@RequestMapping("/system/log/")
public class LogController extends BaseController{

	@Resource private LogService logService;
	
	@RequestMapping("index")
	public ModelAndView index(){
		return new ModelAndView("/system/log/list");
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Pagination list(HttpServletRequest request,@ModelAttribute("searchForm") Form form) throws Exception{
		int count=this.logService.count(form);
		List<Log> list=this.logService.selectByPagination(form);
		Pagination pagination=new Pagination();
		pagination.setRows(list);
		pagination.setTotal(count);
		return pagination;
	}
	
	@RequestMapping("view")
	public ModelAndView view(Long id) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Log obj=this.logService.selectByPrimaryKey(id);
		resultMap.put("object", obj);
		return new ModelAndView("/system/log/view",resultMap);
	}
	
	/**
	 * É¾³ý
	 * @param ids
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	@SysLog(event = "É¾³ýÈÕÖ¾",type=LogType.DELETE)
	@Transactional
	public ResultMessage delete(@RequestParam("ids[]") List<Object> ids){
        return this.logService.delete(ids);
	}
}
