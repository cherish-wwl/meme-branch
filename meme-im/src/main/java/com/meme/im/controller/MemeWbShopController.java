package com.meme.im.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.meme.core.base.BaseController;
import com.meme.core.easyui.Pagination;
import com.meme.core.http.ResultMessage;
import com.meme.im.form.ImForm;
import com.meme.im.service.MemeWbShopService;

@Controller
@RequestMapping("/im/wbshop/")
public class MemeWbShopController extends BaseController {
	
	@Resource private MemeWbShopService memeWbShopService;

	@RequestMapping("index")
	public ModelAndView index(){
		return new ModelAndView("/im/wbshop/list");
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Pagination list(ImForm form){
		List<Map<String,Object>> list=this.memeWbShopService.selectByPaginationView(form);
		int count=this.memeWbShopService.countView(form);
		Pagination pagination=new Pagination(list,count);
		return pagination;
	}
	
	 @RequestMapping("/delete")  
	 @ResponseBody  
     public ResultMessage delete(@RequestParam("ids[]") List<Object> ids) {  
        memeWbShopService.batchDelete(ids); 
        return ResultMessage.defaultSuccessMessage();  
     }
	 
	 @RequestMapping("wbShopQuery")
		public ModelAndView wbShopQuery(Long memberid){
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("memberid", memberid);
			return new ModelAndView("/im/wbshop/wbShop",model);
		}
	
}