package com.meme.pay.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meme.core.base.BaseController;
import com.meme.im.form.ImForm;
import com.meme.im.pojo.MemeShop;
import com.meme.im.pojo.MemeWbShop;
import com.meme.im.service.MemberService;
import com.meme.im.service.MemeShopService;
import com.meme.im.service.MemeWbShopService;

@Controller
@RequestMapping("/pay/")
public class PayController extends BaseController {
	
	private static final Logger LOG = LoggerFactory.getLogger("======支付页面======");
	@Resource private MemberService memberService;
	@Resource private MemeShopService shopService;
	@Resource private MemeWbShopService wbShopService;
	

	@RequestMapping("listShop")
	@ResponseBody
	public List<Map<String,Object>> listShop(HttpServletResponse response,@RequestHeader(value="Origin") String Origin){
		response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		ImForm imForm = new ImForm();
		imForm.setOffset(0);
		imForm.setLimit(10);
		return shopService.selectByPaginationView(imForm);
	}
	
	@RequestMapping("listWbShop")
	@ResponseBody
	public List<Map<String,Object>> listWbShop(HttpServletResponse response,@RequestHeader(value="Origin") String Origin){
		response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		ImForm imForm = new ImForm();
		imForm.setOffset(0);
		imForm.setLimit(10);
		return wbShopService.selectByPaginationView(imForm);
	}
	
	@RequestMapping("getShopByProductId")
	@ResponseBody
	public MemeShop getShopByProductId(HttpServletResponse response,@RequestHeader(value="Origin") String Origin,String productid){
		response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		return shopService.selectByPrimaryKey(productid);
	}
	
	@RequestMapping("getWbShopByProductId")
	@ResponseBody
	public MemeWbShop getWbShopByProductId(HttpServletResponse response,@RequestHeader(value="Origin") String Origin,String productid){
		response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		return wbShopService.selectByPrimaryKey(productid);
	}
	
	
	
}