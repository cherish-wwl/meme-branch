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
import com.meme.core.util.IDGenerator;
import com.meme.im.form.ImForm;
import com.meme.im.pojo.MemeShop;
import com.meme.im.service.MemeShopService;

@Controller
@RequestMapping("/im/shop/")
public class MemeShopController extends BaseController {
	
	@Resource private MemeShopService memeShopService;

	@RequestMapping("index")
	public ModelAndView index(){
		return new ModelAndView("/im/shop/list");
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Pagination list(ImForm form){
		List<Map<String,Object>> list=this.memeShopService.selectByPaginationView(form);
		int count=this.memeShopService.countView(form);
		Pagination pagination=new Pagination(list,count);
		return pagination;
	}
	
	 @RequestMapping("/deleteshopproduct")  
	    @ResponseBody  
	    public Map<String,Object> deleteShopProdcut(MemeShop memeShop) {  
	        String productid = memeShop.getProductid();
	        Map<String,Object> map = new HashMap<String,Object>();  
	        int result = memeShopService.deleteByPrimaryKey(productid);  
	          
	        map.put("success", result);  
	          
	        return map;  
	    }  
	 @RequestMapping("/addshopproduct")  
	    @ResponseBody  
	    public Map<String,Object> addCustomer(MemeShop memeShop) {  
	        memeShop.setProductid("PT"+IDGenerator.generateId());
	        Map<String,Object> map = new HashMap<String,Object>();  
	        int result = memeShopService.insert(memeShop);  
	        map.put("success", result);  
	        return map;  
	    }  
	 
	 
	 @RequestMapping("/upadateshopproduct")  
	    @ResponseBody  
	    public Map<String,Object> upadataCustomer(MemeShop memeShop) {  
	          
	        Map<String,Object> map = new HashMap<String,Object>();  
	        int result = memeShopService.updateByPrimaryKey(memeShop);  
	        map.put("success", result);  
	        return map;  
	    }  
	
}