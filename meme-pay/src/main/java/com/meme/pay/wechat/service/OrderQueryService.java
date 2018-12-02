package com.meme.pay.wechat.service;

import java.util.Map;

/**
 * 微信支付查询订单接口
 * 
 * @author hzl
 * 
 */
public interface OrderQueryService {

	/**
	 * 使用微信的订单号查询微信支付订单
	 * 
	 * @return
	 */
	Map<String, String> queryByTransactionid(String transaction_id);

	/**
	 * 使用商户内部订单号查询微信支付订单
	 * 
	 * @param out_trade_no
	 * @return
	 */
	Map<String, String> queryByOrderid(String out_trade_no);
}
