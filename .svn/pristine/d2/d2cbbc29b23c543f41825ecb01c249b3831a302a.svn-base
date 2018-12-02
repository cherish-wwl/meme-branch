package com.meme.pay.alipay.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.meme.core.cache.ParamsCache;
import com.meme.core.http.ResultMessage;
import com.meme.core.util.StringUtil;

/**
 * 支付宝接口工具类
 * @author xiaohuiji
 *
 */
public class AlipayUtil {

	/**
	 * 订单查询接口
	 * @param request
	 * @param response
	 * @param tradeno
	 * @param orderid
	 * @return
	 */
	public static ResultMessage query(HttpServletRequest request,HttpServletResponse response,String tradeno,String orderid){
		String gateway=ParamsCache.get("ALIPAY_GATEWAY").getValue();
		// SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签     
		AlipayClient client = new DefaultAlipayClient(gateway, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY,AlipayConfig.SIGNTYPE);
		AlipayTradeQueryRequest alipay_request = new AlipayTradeQueryRequest();
 
		AlipayTradeQueryModel model=new AlipayTradeQueryModel();
		if(StringUtil.isAllNotEmpty(tradeno)) model.setTradeNo(tradeno);
		if(StringUtil.isAllNotEmpty(orderid)) model.setOutTradeNo(orderid);
		alipay_request.setBizModel(model);
 
		AlipayTradeQueryResponse alipay_response=null;
		try {
			alipay_response = client.execute(alipay_request);
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		if(null!=alipay_response&&alipay_response.isSuccess()){
			String body=alipay_response.getBody();
			JSONObject result=JSONObject.parseObject(body);
			JSONObject res=result.getJSONObject("alipay_trade_query_response");
			return ResultMessage.success("查询接口调用成功",res);
		}else return ResultMessage.failed("查询接口调用失败，请稍后刷新重试。");
	}
}
