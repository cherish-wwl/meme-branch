package com.meme.pay.alipay.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.meme.core.base.BaseController;
import com.meme.core.http.ResultMessage;
import com.meme.im.pojo.MemeOrder;
import com.meme.im.pojo.MemeWbOrder;
import com.meme.im.service.MemeOrderService;
import com.meme.im.service.MemeWbOrderService;
import com.meme.pay.alipay.entity.ReturnParam;
import com.meme.pay.alipay.util.AlipayConfig;
import com.meme.pay.alipay.util.AlipayUtil;

/**
 * 支付宝wap网站支付
 * @author xiaohuiji
 *
 */
@Controller
@RequestMapping("/pay/alipay/")
public class AlipayController extends BaseController{
	
	@Resource private MemeOrderService memeOrderService;
	@Resource private MemeWbOrderService memeWbOrderService;
	
	/**
	 * 支付宝支付结果同步返回
	 * @throws UnsupportedEncodingException 
	 * @throws AlipayApiException 
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("getReturn")
	@ResponseBody
	public ResultMessage getReturn(HttpServletRequest request,ReturnParam param,HttpServletResponse response,@RequestHeader(value="Origin") String Origin) throws UnsupportedEncodingException, AlipayApiException{
//		System.out.println("======支付结果异步返回数据======"+JSONObject.toJSONString(param));
		response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		System.out.println("======支付结果异步返回数据======"+JSONObject.toJSONString(params));
		
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		//商户订单号
		String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
		//支付宝交易号
		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		//计算得出通知验证结果
		//boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
		boolean verify_result = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, AlipayConfig.SIGNTYPE);
		System.out.println("======验证结果======"+verify_result);
		if(verify_result){
			MemeOrder order=this.memeOrderService.selectByPrimaryKey(out_trade_no);
			HashMap<String, Object> data=new HashMap<String, Object>();
			if(null != order){
				//更新订单表数据
				order.setTradeno(trade_no);
				order.setState(1);
				order.setPaytime(new Date());
				this.memeOrderService.updateByPrimaryKeySelective(order);
				data.put("order", order);
			}else {
				MemeWbOrder memeWbOrder = new MemeWbOrder();
				memeWbOrder.setOrderid(out_trade_no);
				memeWbOrder.setTradeno(trade_no);
				memeWbOrder.setState(1);
				memeWbOrder.setPaytime(new Date());
				this.memeWbOrderService.updateByPrimaryKeySelective(memeWbOrder);
				data.put("order", memeWbOrder);
			}
			return ResultMessage.success("验证成功", data);
		} else {
			return ResultMessage.failed("验证失败");
		}
	}
	
	/**
	 * 服务器异步通知返回
	 * @throws UnsupportedEncodingException 
	 * @throws AlipayApiException 
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("getNotice")
	public void getNotice(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, AlipayApiException{
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		String notify_id=new String(request.getParameter("notify_id").getBytes("ISO-8859-1"),"UTF-8");
		System.out.println("======通知编号======"+notify_id);
		//商户订单号
		String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
		//支付宝交易号
		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
		//交易状态
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
		//买家实付金额
		String buyer_pay_amount=request.getParameter("buyer_pay_amount");

		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		//计算得出通知验证结果
		//boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
		boolean verify_result = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, AlipayConfig.SIGNTYPE);
		try {
			response.setContentType("text/plain;charset=" + AlipayConfig.CHARSET);
			if(verify_result){
				if(trade_status.equalsIgnoreCase("TRADE_SUCCESS")){
					MemeOrder order=this.memeOrderService.selectByPrimaryKey(out_trade_no);
					if(null != order){
						//更新订单表数据
						order.setTradeno(trade_no);
						order.setState(1);
						order.setPaytime(new Date());
						order.setAmount(new BigDecimal(buyer_pay_amount));
						this.memeOrderService.updateByPrimaryKeySelective(order);
					}else {
						MemeWbOrder memeWbOrder = new MemeWbOrder();
						memeWbOrder.setOrderid(out_trade_no);
						memeWbOrder.setTradeno(trade_no);
						memeWbOrder.setState(1);
						memeWbOrder.setPaytime(new Date());
						memeWbOrder.setAmount(new BigDecimal(buyer_pay_amount));
						this.memeWbOrderService.updateByPrimaryKeySelective(memeWbOrder);
					}
					response.getWriter().write("success");
				    response.getWriter().flush(); 
				    response.getWriter().close();
				}
			}else {
				response.getWriter().write("failed");
			    response.getWriter().flush(); 
			    response.getWriter().close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询订单接口
	 * @param request
	 * @param response
	 * @throws AlipayApiException 
	 */
	@RequestMapping("query")
	@ResponseBody
	public ResultMessage query(HttpServletRequest request,HttpServletResponse response,String tradeno,String orderid) throws AlipayApiException{
		return AlipayUtil.query(request, response, tradeno, orderid);
	}
	
	/**
	 * 修正订单数据
	 * @param request
	 * @param response
	 * @param orderid
	 * @return
	 */
	@RequestMapping("fix")
	@ResponseBody
	public ResultMessage fix(HttpServletRequest request,HttpServletResponse response,String orderid){
		ResultMessage resultMessage=AlipayUtil.query(request, response, null, orderid);
		if(resultMessage.getState().equals("1")){
			JSONObject resJsonObject=JSONObject.parseObject(JSONObject.toJSONString(resultMessage.getData()));
			MemeOrder order=this.memeOrderService.selectByPrimaryKey(resJsonObject.getString("out_trade_no"));
			order.setAmount(new BigDecimal(resJsonObject.getString("total_amount")));
			order.setTradeno(resJsonObject.getString("trade_no"));
			String trade_status=resJsonObject.getString("trade_status");
			if(trade_status.equalsIgnoreCase("TRADE_SUCCESS")){
				order.setState(1);
			}
			int result=this.memeOrderService.updateByPrimaryKeySelective(order);
			if(result>0) return ResultMessage.success("订单数据修正成功");
			return ResultMessage.failed("订单数据修正失败，请稍后刷新重试。");
		}else return resultMessage;
	}
}
