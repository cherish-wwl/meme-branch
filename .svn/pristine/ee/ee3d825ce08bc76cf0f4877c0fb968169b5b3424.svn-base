package com.meme.im.controller;

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

import com.meme.core.base.BaseController;
import com.meme.core.base.IController;
import com.meme.core.easyui.Pagination;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.core.log.LogType;
import com.meme.core.log.annotation.SysLog;
import com.meme.core.util.StringUtil;
import com.meme.core.validator.ValidateBuilder;
import com.meme.im.pojo.MemberBgTem;
import com.meme.im.service.MemberBgTemService;

@Controller
@RequestMapping("/im/member/bgtemplate/")
public class BgTemplateController extends BaseController implements IController{

	@Resource private MemberBgTemService memberBgTemService;
	
	@RequestMapping(value="loadValidateRules")
	@ResponseBody
	public String loadValidationRules(){
		ValidateBuilder.clear();
		
		String js=ValidateBuilder.createJsValidateRules(MemberBgTem.class);
		return js;
	}
	
	@RequestMapping("index")
	public ModelAndView index(){
		Map<String, Object> model = new HashMap<String, Object>();
		
		return new ModelAndView("/im/member/bgtemplate/list",model);
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Pagination list(HttpServletRequest request,Form form){
		List<MemberBgTem> list=this.memberBgTemService.selectByPagination(form);
		int count=this.memberBgTemService.count(form);
		Pagination pagination=new Pagination();
		pagination.setRows(list);
		pagination.setTotal(count);
		return pagination;
	}
	
	@RequestMapping("editView")
	public ModelAndView editView(String id){
		Map<String, Object> model = new HashMap<String, Object>();
		MemberBgTem obj=null;
		if(StringUtil.isAllNotEmpty(id)){
			obj=this.memberBgTemService.selectByPrimaryKey(id);
		}
		model.put("object", obj);
		return new ModelAndView("/im/member/bgtemplate/edit",model);
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
	@SysLog(event = "新增模板",type=LogType.ADD)
	public ResultMessage add(HttpServletRequest request,MemberBgTem obj){
		return this.memberBgTemService.add(request, obj);
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
	@SysLog(event = "修改模板",type=LogType.UPDATE)
	public ResultMessage edit(HttpServletRequest request,MemberBgTem obj){
		return this.memberBgTemService.edit(request, obj);
	}
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	@SysLog(event = "删除模板",type=LogType.DELETE)
	@Transactional
	public ResultMessage delete(@RequestParam("ids[]") List<Object> ids){
        return this.memberBgTemService.delete(ids);
	}
}
