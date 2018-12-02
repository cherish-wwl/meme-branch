package com.meme.pay.wechat.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.meme.core.cache.ParamsCache;
import com.meme.core.http.HttpRequestMethod;
import com.meme.core.http.ResultMessage;
import com.meme.core.http.entity.RequestEntity;
import com.meme.core.http.request.IRequest;
import com.meme.core.http.request.SSLNetRequest;
import com.meme.core.util.BeanUtil;
import com.meme.pay.wechat.entity.OrderQuery;
import com.meme.pay.wechat.service.OrderQueryService;
import com.meme.pay.wechat.util.SignTool;
import com.meme.pay.wechat.util.WechatPayConstants;

@Service("OrderQueryService")
@Transactional
public class OrderQueryServiceImpl implements OrderQueryService{
	
	private static final Logger LOG = LoggerFactory.getLogger(OrderQueryServiceImpl.class);

	@Override
	public Map<String, String> queryByTransactionid(String transaction_id) {
		Map<String, String> returnData=null;
		String appid = ParamsCache.get("WEIXIN_APP_ID").getValue();
		String mch_id = ParamsCache.get("WEIXIN_MCH_ID").getValue();
		//微信支付密钥
		String key=ParamsCache.get("WEIXIN_PAY_API_KEY").getValue();
		String nonce_str = SignTool.getRandomString();
		
		OrderQuery query=new OrderQuery();
		query.setAppid(appid);
		query.setMch_id(mch_id);
		query.setNonce_str(nonce_str);
		
		//生成签名
		Map<String,String> map=new HashMap<String, String>();
		map.put("appid", appid);
		map.put("mch_id", mch_id);
		map.put("nonce_str", nonce_str);
		map.put("transaction_id", transaction_id);
		String sign = SignTool.generateCode(map, key);
		query.setSign(sign);
		
		String xml=BeanUtil.bean2Xml(query);
		LOG.info(xml);
		IRequest sslNetRequest=new SSLNetRequest();
		RequestEntity entity=new RequestEntity();
		entity.setData(xml);
		entity.setSsl(true);
		entity.setMethod(HttpRequestMethod.POST);
		entity.setUrl(WechatPayConstants.ORDERQUERY_API);
		entity.getProperties().put("Content-Type", "text/xml; charset=UTF-8");
		
		ResultMessage message=sslNetRequest.request(entity);
		
		if(message.getState().equals("200")){
			returnData=BeanUtil.xml2Map(message.getData().toString());
		}
		
		return returnData;
	}

	@Override
	public Map<String, String> queryByOrderid(String out_trade_no) {
		Map<String, String> returnData=null;
		String appid = ParamsCache.get("WEIXIN_APP_ID").getValue();
		String mch_id = ParamsCache.get("WEIXIN_MCH_ID").getValue();
		//微信支付密钥
		String key=ParamsCache.get("WEIXIN_PAY_API_KEY").getValue();
		String nonce_str = SignTool.getRandomString();
		
		OrderQuery query=new OrderQuery();
		query.setAppid(appid);
		query.setMch_id(mch_id);
		query.setNonce_str(nonce_str);
		query.setOut_trade_no(out_trade_no);
		
		//生成签名
		Map<String,String> map=new HashMap<String, String>();
		map.put("appid", appid);
		map.put("mch_id", mch_id);
		map.put("nonce_str", nonce_str);
		map.put("out_trade_no", out_trade_no);
		String sign = SignTool.generateCode(map, key);
		query.setSign(sign);
		
		String xml=BeanUtil.bean2Xml(query);
		
		LOG.info(xml);
		
		IRequest sslNetRequest=new SSLNetRequest();
		RequestEntity entity=new RequestEntity();
		entity.setData(xml);
		entity.setSsl(true);
		entity.setMethod(HttpRequestMethod.POST);
		entity.setUrl(WechatPayConstants.ORDERQUERY_API);
		entity.getProperties().put("Content-Type", "text/xml; charset=UTF-8");
		
		ResultMessage message=sslNetRequest.request(entity);
		
		if(message.getState().equals("200")){
			returnData=BeanUtil.xml2Map(message.getData().toString());
			
			LOG.info("微信支付查询订单接口返回数据："+JSONObject.toJSONString(returnData));
		}
		
		return returnData;
	}
}
