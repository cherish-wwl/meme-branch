package com.meme.im.controller;

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
import com.meme.im.service.MemeOrderService;

@Controller
@RequestMapping("/im/order/")
public class MemeOrderController extends BaseController {
	
	@Resource private MemeOrderService memeOrderService;

	@RequestMapping("index")
	public ModelAndView index(){
		return new ModelAndView("/im/order/list");
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Pagination list(ImForm form){
		List<Map<String,Object>> list=this.memeOrderService.selectByPaginationView(form);
		int count=this.memeOrderService.countView(form);
		Pagination pagination=new Pagination(list,count);
		return pagination;
	}
}