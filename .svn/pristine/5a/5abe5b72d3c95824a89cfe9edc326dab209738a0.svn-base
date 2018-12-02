package com.meme.pay.wechat.h5.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.meme.core.base.BaseController;
import com.meme.core.http.ResultMessage;
import com.meme.pay.entity.TradeParam;
import com.meme.pay.wechat.h5.service.H5Service;

/**
 * 微信支付h5支付接口
 * @author xiaohuiji
 *
 */
@Controller
@RequestMapping("/wechat/pay/h5/")
public class H5Controller extends BaseController {

	@Resource private H5Service h5Service;
	
	/**
	 * 支付页面
	 * @return
	 */
	@RequestMapping("index")
	public ModelAndView index() {
		Map<String,Object> model=new HashMap<String, Object>();
		return new ModelAndView("/pay/wxpay/h5/pay",model);
	}
	
	/**
	 * 创建订单，调用微信统一下单接口，发起下单请求
	 * @param request
	 * @param response
	 * @param param
	 * @return
	 */
	@RequestMapping("pay")
	@ResponseBody
	public ResultMessage pay(HttpServletRequest request,HttpServletResponse response,TradeParam param,String type,@RequestHeader(value="Origin") String Origin,String consignee,String receiving_telephone,String receiving_address,Double exxpress_cost){
		response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		return this.h5Service.pay(request, response, param,type, consignee, receiving_telephone, receiving_address, exxpress_cost);
	}
	
	/**
	 * 支付完成跳转页面，检查并修改订单信息
	 * @param orderid
	 * @return
	 */
	@RequestMapping("payResult")
	public ModelAndView payResult(String orderid){
		this.h5Service.check(orderid);
		Map<String,Object> model=new HashMap<String, Object>();
		model.put("orderid", orderid);
		return new ModelAndView("/pay/wxpay/h5/result",model);
	}
	
	@RequestMapping("check")
	@ResponseBody
	public ResultMessage check(HttpServletResponse response,String orderid,@RequestHeader(value="Origin") String Origin){
		response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		return this.h5Service.check(orderid);
	}
}
