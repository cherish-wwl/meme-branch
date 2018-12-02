package com.meme.pay.alipay.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.meme.core.base.BaseController;
import com.meme.core.cache.ParamsCache;
import com.meme.core.util.IDGenerator;
import com.meme.core.util.StringUtil;
import com.meme.im.pojo.Member;
import com.meme.im.pojo.MemeOrder;
import com.meme.im.pojo.MemeWbOrder;
import com.meme.im.service.MemberService;
import com.meme.im.service.MemeOrderService;
import com.meme.im.service.MemeWbOrderService;
import com.meme.im.util.AccountUtil;
import com.meme.pay.alipay.util.AlipayConfig;
import com.meme.pay.entity.TradeParam;

/**
 * 支付宝wap网站支付
 * @author xiaohuiji
 *
 */
@Controller
@RequestMapping("/pay/alipay/wap/")
public class WappayController extends BaseController{
	
	@Resource private MemeOrderService memeOrderService;
	@Resource private MemeWbOrderService memeWbOrderService;
	@Resource private MemberService memberService;

	/**
	 * 支付页面
	 * @return
	 */
	@RequestMapping("index")
	public ModelAndView index() {
		Map<String,Object> model=new HashMap<String, Object>();
		return new ModelAndView("/pay/alipay/wappay/pay",model);
	}
	
	/**
	 * 支付请求校验跳转
	 * @param request
	 * @param response
	 * @param param
	 */
	@RequestMapping("pay")
	public void pay(HttpServletRequest request,HttpServletResponse response,TradeParam param,String type,String consignee,String receiving_telephone,String receiving_address,Double exxpress_cost ){
	    Object memberid=AccountUtil.getMemberid(request);
	    if(null == memberid){
	    	try {
	    		response.setContentType("text/html;charset=" + AlipayConfig.CHARSET); 
			    response.getWriter().write("<script type='text/javascript'>alert('会员未登录，请登录后再操作！');window.close();</script>");
			    response.getWriter().flush(); 
			    response.getWriter().close();
			}catch (IOException e) {
				e.printStackTrace();
			}
	    }
		
	    param.setTradeno(String.valueOf(IDGenerator.generateId()));
	    String gateway=ParamsCache.get("ALIPAY_GATEWAY").getValue();
	    String notify_url=ParamsCache.get("ALIPAY_NOTIFY_URL").getValue();
	    String return_url=ParamsCache.get("ALIPAY_RETURN_URL").getValue();
	    
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
	    order.setPaytype("支付宝支付");
	    int result;
	    if("wbshop".equals(type)) {  //微商订单表插入
	    		MemeWbOrder memeWbOrder = new MemeWbOrder();
	    		BeanUtils.copyProperties(order, memeWbOrder);
	    		memeWbOrder.setConsignee(consignee);
	    		memeWbOrder.setReceiving_telephone(receiving_telephone);
	    		memeWbOrder.setReceiving_address(receiving_address);
	    		memeWbOrder.setExxpress_cost(exxpress_cost);
	    		result=this.memeWbOrderService.insertSelective(memeWbOrder);
	    		 Member member = new Member();
	    	    member.setMemberid((Long)memberid);
	    	    member.setConsignee(consignee);
	    	    member.setReceiving_telephone(receiving_telephone);
	    	    member.setReceiving_address(receiving_address);
	    	    memberService.updateByPrimaryKeySelective(member);
	    }else {
	    	 	//订单表插入
	    		result=this.memeOrderService.insertSelective(order);
	    }
	    
	    try {
		    if(result>0){
				// SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签     
			    // 调用RSA签名方式
			    AlipayClient client = new DefaultAlipayClient(gateway, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY,AlipayConfig.SIGNTYPE);
			    AlipayTradeWapPayRequest alipay_request=new AlipayTradeWapPayRequest();
			    
			    // 封装请求支付信息
			    AlipayTradeWapPayModel model=new AlipayTradeWapPayModel();
			    model.setOutTradeNo(param.getTradeno());
			    model.setSubject(param.getSubject());
			    model.setTotalAmount(param.getAmount());
			    model.setBody(param.getBody());
			    if(StringUtil.isEmpty(param.getTimeout())) param.setTimeout("2m");
			    model.setTimeoutExpress(param.getTimeout());
			    model.setProductCode(param.getProductid());
			    alipay_request.setBizModel(model);
			    // 设置异步通知地址
			    if(StringUtil.isAllNotEmpty(notify_url)) alipay_request.setNotifyUrl(notify_url);
			    // 设置同步地址
			    if(StringUtil.isAllNotEmpty(return_url)) alipay_request.setReturnUrl(return_url);
			    //form表单生产
			    String form = "";
				
				// 调用SDK生成表单
				form = client.pageExecute(alipay_request).getBody();
				response.setContentType("text/html;charset=" + AlipayConfig.CHARSET); 
				//直接将完整的表单html输出到页面 
			    response.getWriter().write(form);
			    response.getWriter().flush(); 
			    response.getWriter().close();
		    } else {
		    	response.setContentType("text/html;charset=" + AlipayConfig.CHARSET); 
			    response.getWriter().write("<script type='text/javascript'>alert('未创建订单，无法完成支付，请返回刷新重试');window.close();</script>");
			    response.getWriter().flush(); 
			    response.getWriter().close();
			}
	    } catch (AlipayApiException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
