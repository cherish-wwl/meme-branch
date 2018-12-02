package com.meme.im.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.meme.core.base.BaseController;
import com.meme.core.easyui.Pagination;
import com.meme.im.form.ImForm;
import com.meme.im.service.MemeWbOrderService;

@Controller
@RequestMapping("/im/wborder/")
public class MemeWbOrderController extends BaseController {
	
	@Resource private MemeWbOrderService memeWbOrderService;

	@RequestMapping("index")
	public ModelAndView index(){
		return new ModelAndView("/im/wborder/list");
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Pagination list(ImForm form){
		List<Map<String,Object>> list=this.memeWbOrderService.selectByPaginationView(form);
		int count=this.memeWbOrderService.countView(form);
		Pagination pagination=new Pagination(list,count);
		return pagination;
	}
	
	@RequestMapping("wbOrderQuery")
	public ModelAndView wbOrderQuery(Long memberid){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("memberid", memberid);
		return new ModelAndView("/im/wborder/wbOrder",model);
	}
}