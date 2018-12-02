package com.meme.pay.wechat.jsapi.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.meme.core.http.ResultMessage;
import com.meme.pay.entity.TradeParam;

public interface JSAPIService {

	/**
	 * 公众号支付
	 * @param request
	 * @param response
	 * @param param
	 * @return
	 */
	public ResultMessage pay(HttpServletRequest request,HttpServletResponse response,TradeParam param,String type,String consignee,String receiving_telephone,String receiving_address,Double exxpress_cost);
	
	/**
	 * 订单检查更新状态
	 * @param orderid
	 * @return
	 */
	ResultMessage check(String orderid);
}
