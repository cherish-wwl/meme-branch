package com.meme.pay.wechat.h5.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.meme.core.cache.DictCache;
import com.meme.core.cache.ParamsCache;
import com.meme.core.http.HttpRequestMethod;
import com.meme.core.http.ResponseState;
import com.meme.core.http.ResultMessage;
import com.meme.core.http.entity.RequestEntity;
import com.meme.core.http.request.IRequest;
import com.meme.core.http.request.SSLNetRequest;
import com.meme.core.pojo.DictItemView;
import com.meme.core.util.AddressUtil;
import com.meme.core.util.BeanUtil;
import com.meme.core.util.HttpUtil;
import com.meme.core.util.IDGenerator;
import com.meme.core.util.StringUtil;
import com.meme.im.pojo.Member;
import com.meme.im.pojo.MemeOrder;
import com.meme.im.pojo.MemeWbOrder;
import com.meme.im.service.MemberService;
import com.meme.im.service.MemeOrderService;
import com.meme.im.service.MemeWbOrderService;
import com.meme.im.util.AccountUtil;
import com.meme.pay.entity.TradeParam;
import com.meme.pay.wechat.entity.UnifiedOrder;
import com.meme.pay.wechat.entity.UnifiedOrderResult;
import com.meme.pay.wechat.h5.service.H5Service;
import com.meme.pay.wechat.service.OrderQueryService;
import com.meme.pay.wechat.util.SignTool;
import com.meme.pay.wechat.util.WechatPayConstants;

@Service("H5Service")
@Transactional
public class H5ServiceImpl implements H5Service {
	private static final Logger LOG = LoggerFactory.getLogger("======统一下单Service类======");
	
	@Resource private MemeOrderService memeOrderService;
	@Resource private OrderQueryService orderQueryService;
	@Resource private MemeWbOrderService memeWbOrderService;
	@Resource private MemberService memberService;

	@Override
	public ResultMessage pay(HttpServletRequest request, HttpServletResponse response,TradeParam param,String type,String consignee,String receiving_telephone,String receiving_address,Double exxpress_cost) {
		Object memberid=AccountUtil.getMemberid(request);
	    if(null == memberid) {
	    	ResultMessage message=new ResultMessage();
	    	message.setState("-1");
	    	message.setMessage("会员账号未登录，请先登录账号再操作");
	    	return message;
	    }
		
	    param.setTradeno(String.valueOf(IDGenerator.generateId()));
	    
	    //订单表插入
	    MemeOrder order=new MemeOrder();
	    order.setOrderid(param.getTradeno());
	    order.setAddtime(new Date());
	    order.setAmount(new BigDecimal(param.getAmount()));
	    order.setBody(param.getBody());
	    if(null!=memberid){
	    	order.setMemberid(Long.valueOf(memberid.toString()));
	    }
	    order.setProductid(param.getProductid());
	    order.setSubject(param.getSubject());
	    order.setPaytype("微信h5支付");
	    if("wbshop".equals(type)) {  //微商订单表插入
	    		MemeWbOrder memeWbOrder = new MemeWbOrder();
	    		BeanUtils.copyProperties(order, memeWbOrder);
	    		memeWbOrder.setConsignee(consignee);
	    		memeWbOrder.setReceiving_telephone(receiving_telephone);
	    		memeWbOrder.setReceiving_address(receiving_address);
	    		memeWbOrder.setExxpress_cost(exxpress_cost);
	    	    int re=this.memeWbOrderService.insertSelective(memeWbOrder);
	    	    Member member = new Member();
	    	    member.setMemberid((Long)memberid);
	    	    member.setConsignee(consignee);
	    	    member.setReceiving_telephone(receiving_telephone);
	    	    member.setReceiving_address(receiving_address);
	    	    memberService.updateByPrimaryKeySelective(member);
	    	    if(re==0) return ResultMessage.failed("订单创建失败，请稍后刷新重试或者联系管理员处理。");
	    }else {
	    	 	//订单表插入
		    int re=this.memeOrderService.insertSelective(order);
		    if(re==0) return ResultMessage.failed("订单创建失败，请稍后刷新重试或者联系管理员处理。");
	    }
	    //微信支付密钥
  		String key=ParamsCache.get("WEIXIN_PAY_API_KEY").getValue();
  		
  		String appid = ParamsCache.get("WEIXIN_APP_ID").getValue();
  		String mch_id = ParamsCache.get("WEIXIN_MCH_ID").getValue();
  		String nonce_str = SignTool.getRandomString();
  		//body字段使用商户名称加订单号组合
  		String body = ParamsCache.get("WEIXIN_MCH_NAME").getValue()+"-"+order.getSubject();
  		Integer total_fee = order.getAmount().multiply(new BigDecimal(100)).intValue();
  		
  		String spbill_create_ip = AddressUtil.getRealIPAddress(request);
		String basepath=HttpUtil.getBasePath(request);
		String notify_url = basepath+ParamsCache.get("WEIXIN_PAY_NOTIFY_URL").getValue();
		String trade_type = "MWEB";
		
		//生成签名
		Map<String,String> map=new HashMap<String, String>();
		map.put("appid", appid);
		map.put("mch_id", mch_id);
		map.put("nonce_str", nonce_str);
		map.put("body", body);
		map.put("out_trade_no", order.getOrderid());
		map.put("total_fee", String.valueOf(total_fee));
		map.put("spbill_create_ip", spbill_create_ip);
		map.put("notify_url", notify_url);
		map.put("trade_type", trade_type);
		
		LinkedHashMap<String, String> h5_info=new LinkedHashMap<String, String>();
		h5_info.put("type", "Wap");
		h5_info.put("wap_url", ParamsCache.get("WEB_URL").getValue());//网站地址
		h5_info.put("wap_name", ParamsCache.get("WEB_NAME").getValue());//网站名
		JSONObject sceneJsonObject=new JSONObject();
		sceneJsonObject.put("h5_info", h5_info);
		String scene_info=sceneJsonObject.toJSONString();
		LOG.debug("======场景信息======"+scene_info);
		map.put("scene_info", scene_info);
		String sign = SignTool.generateCode(map, key);
		
		UnifiedOrder unifiedOrder = new UnifiedOrder();
		unifiedOrder.setAppid(appid);
		unifiedOrder.setMch_id(mch_id);
		unifiedOrder.setNonce_str(nonce_str);
		unifiedOrder.setSign(sign);
		unifiedOrder.setBody(body);
		unifiedOrder.setOut_trade_no(order.getOrderid());
		unifiedOrder.setTotal_fee(total_fee);
		unifiedOrder.setSpbill_create_ip(spbill_create_ip);
		unifiedOrder.setNotify_url(notify_url);
		unifiedOrder.setTrade_type(trade_type);
		unifiedOrder.setScene_info(scene_info);

		String xml = BeanUtil.bean2Xml(unifiedOrder);
		
		IRequest sslNetRequest=new SSLNetRequest();
		RequestEntity entity=new RequestEntity();
		entity.setData(xml);
		entity.setSsl(true);
		entity.setMethod(HttpRequestMethod.POST);
		entity.setUrl(WechatPayConstants.UNIFIEDORDER_API);
		entity.getProperties().put("Content-Type", "text/xml; charset=UTF-8");
		
		ResultMessage message=sslNetRequest.request(entity);
		if(message.getState().equals("200")){
			UnifiedOrderResult result = (UnifiedOrderResult) BeanUtil.xml2Bean(UnifiedOrderResult.class, message.getData().toString());
			LOG.debug("统一下单接口返回数据："+JSONObject.toJSONString(result));
			if(StringUtil.isAllNotEmpty(result.getReturn_code())&&result.getReturn_code().equalsIgnoreCase("SUCCESS")){
				if(result.getResult_code()!=null&&result.getResult_code().equalsIgnoreCase("SUCCESS")){
					JSONObject obj=new JSONObject();
//					
//					String timeStamp= SignTool.getTimeStamp();
//					String nonceStr=SignTool.getRandomString();
//					
//					Map<String,String> params=new HashMap<String, String>();
//					params.put("appId", appid);
//					params.put("timeStamp", timeStamp);
//					params.put("nonceStr", nonceStr);
//					params.put("package", "prepay_id="+result.getPrepay_id());
//					params.put("signType", "MD5");
//
//					obj.put("appId", appid);
//					obj.put("timeStamp", timeStamp);
//					obj.put("nonceStr", nonceStr);
//					obj.put("pkg", "prepay_id="+result.getPrepay_id());
//					obj.put("signType", "MD5");
//					obj.put("orderid", order.getOrderid());
//					//生成签名字符串
//					obj.put("paySign", SignTool.generateCode(params, key));
					String mweb_urlString = null;
					try {
						mweb_urlString = result.getMweb_url()+"&redirect_url="+URLEncoder.encode("http://mmgmmj.com/pay/wxpay/h5/result?orderid="+order.getOrderid()+(type!=null?"&type="+type:""),"UTF-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					obj.put("MWEB_URL", mweb_urlString);
					return ResultMessage.success("操作成功", obj);
				}else{
					String err=null;
					if(StringUtil.isAllNotEmpty(result.getErr_code())){
						//取数据字典中的错误原因
						DictItemView item=DictCache.getDictItem("UNIFIEDORDER_API_CODE", result.getErr_code());
						if(null!=item) err=item.getDictitemname();
					}
					return ResultMessage.failed(StringUtil.isEmpty(err)?"生成预付单失败":err);
				}
			}else {
				return ResultMessage.failed(result.getReturn_msg());
			}
		}else{
			//重置state值，统一前端接口
			message.setState(ResponseState.FAILE.getState().toString());
			return message;
		}
	}

	@Override
	public ResultMessage check(String orderid) {
		if(StringUtil.isEmpty(orderid)) return ResultMessage.failed("商户内部订单编号不能为空");
		MemeOrder order=this.memeOrderService.selectByPrimaryKey(orderid);
		if(null==order) return ResultMessage.failed("订单不存在");
		
		if(order.getState()==1) return ResultMessage.success("支付成功");
		
		Map<String,String> result=this.orderQueryService.queryByOrderid(orderid);
		
		String msg=null;
		if(result.get("return_code").equalsIgnoreCase("SUCCESS")){
			if(result.get("result_code").equalsIgnoreCase("SUCCESS")){
				if(result.get("trade_state").equalsIgnoreCase("SUCCESS")){
					order.setState(1);
					//微信返回订单总额，单位分转元
					BigDecimal b=new BigDecimal(result.get("total_fee")).divide(new BigDecimal(100));
					order.setAmount(b);
					order.setPaytime(new Date());
					order.setTradeno(result.get("transaction_id"));
					int i=this.memeOrderService.updateByPrimaryKeySelective(order);
					if(i>0) return ResultMessage.success("支付成功");
					else return ResultMessage.failed("更新订单数据失败");
				}else{
					msg=DictCache.getDictItem("WEXIN_PAY_TRADE_STATE", result.get("trade_state")).getDictitemname();
				}
			}else{
				msg=DictCache.getDictItem("WEIXIN_PAY_ORDERQUERY_CODE", result.get("err_code")).getDictitemname();
			}
		}else{
			msg=result.get("return_msg");
		}
		
		return ResultMessage.failed(msg);
	}

}
