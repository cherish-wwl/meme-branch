package com.meme.pay.wechat.controller;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.meme.core.base.BaseController;
import com.meme.core.cache.DictCache;
import com.meme.core.cache.ParamsCache;
import com.meme.core.http.ResultMessage;
import com.meme.core.log.LogType;
import com.meme.core.log.annotation.SysLog;
import com.meme.core.util.BeanUtil;
import com.meme.core.util.HttpUtil;
import com.meme.core.util.StringUtil;
import com.meme.im.pojo.MemeOrder;
import com.meme.im.pojo.MemeWbOrder;
import com.meme.im.service.MemeOrderService;
import com.meme.im.service.MemeWbOrderService;
import com.meme.pay.wechat.entity.Result;
import com.meme.pay.wechat.service.OrderQueryService;
import com.meme.pay.wechat.util.SignTool;
import com.meme.pay.wechat.util.WeChatAPI;

@Controller
@RequestMapping("/wechat/")
public class APIController extends BaseController{

	private static final Logger LOG = LoggerFactory.getLogger("======微信支付公众接口======");
	@Resource private OrderQueryService orderQueryService;
	@Resource private MemeOrderService memeOrderService;
	@Resource private MemeWbOrderService memeWbOrderService;
	
	/**
	 * 微信token验证
	 * @param request
	 * @param response
	 */
	@RequestMapping("validate")
	@SysLog(event = "token验证", type = LogType.OTHERS)
	public void validate(HttpServletRequest request,HttpServletResponse response){
		String restr=WeChatAPI.validate(request, response);
		HttpUtil.printResponseData(response, restr, null);
	}
	
	/**
	 * 微信支付异步通知回调接口
	 * 未使用数据锁机制
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("pay/notify")
	public void notify(HttpServletRequest request,HttpServletResponse response) throws IOException {
		InputStream in = request.getInputStream();
		Map<String, String> notice = BeanUtil.xml2Map(in);
		LOG.info("======微信支付结果通知======"+JSONObject.toJSONString(notice));

		//微信支付密钥
		String key=ParamsCache.get("WEIXIN_PAY_API_KEY").getValue();
		if (notice.get("return_code").equalsIgnoreCase("SUCCESS")) {
			String result_code = notice.get("result_code");
			if (StringUtil.isAllNotEmpty(result_code) && result_code.equalsIgnoreCase("SUCCESS")) {
				String sign=notice.get("sign");
				notice.remove("sign");
				String str=SignTool.generateCode(notice, key);
				//签名验签
				if(sign.equals(str)){
					String fString=notice.get("total_fee");
					BigDecimal total_fee=new BigDecimal(fString).divide(new BigDecimal(100));
					String out_trade_no=notice.get("out_trade_no");
					MemeOrder order=this.memeOrderService.selectByPrimaryKey(out_trade_no);
					if(null != order &&order.getAmount().compareTo(total_fee) == 0 && order.getState() != 1){
						//更新订单表数据
						order.setTradeno(notice.get("transaction_id"));
						order.setState(1);
						order.setAmount(total_fee);
						order.setPaytime(new Date());
						this.memeOrderService.updateByPrimaryKeySelective(order);
					}else {
						MemeWbOrder memeWbOrder = memeWbOrderService.selectByPrimaryKey(out_trade_no);
						memeWbOrder.setTradeno(notice.get("transaction_id"));
						memeWbOrder.setState(1);
						memeWbOrder.setAmount(total_fee);
						memeWbOrder.setPaytime(new Date());
						this.memeWbOrderService.updateByPrimaryKeySelective(memeWbOrder);
					}
				}
				// 此处为订单支付成功业务处理逻辑
				LOG.info("微信支付返回数据："+JSONObject.toJSONString(notice));
			} else {
				LOG.info("支付失败，错误码：" + notice.get("err_code")+"原因：" + notice.get("err_code_des"));
			}
		} else {
			LOG.info("支付失败，原因：" + notice.get("return_msg"));
		}

		Result result = new Result();
		result.setReturn_code("SUCCESS");
		result.setReturn_msg("OK");
		response.getWriter().write(BeanUtil.bean2Xml(result));
	}
	
	 /* 商户内部订单号查询订单
	 * @param orderid
	 * @return
	 */
	@RequestMapping("queryByOrderid")
	@ResponseBody
	public ResultMessage queryByOrderid(String orderid){
		if(StringUtil.isEmpty(orderid)) return ResultMessage.failed("商户内部订单编号不能为空");
		Map<String,String> result=this.orderQueryService.queryByOrderid(orderid);
		
		LOG.info("微信支付查询订单接口返回数据："+JSONObject.toJSONString(result));
		
		String msg=null;
		if(result.get("return_code").equalsIgnoreCase("SUCCESS")){
			if(result.get("result_code").equalsIgnoreCase("SUCCESS")){
				if(result.get("trade_state").equalsIgnoreCase("SUCCESS")){
					return ResultMessage.success("支付成功", result);
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
	
	/**
	 * 微信支付订单号查询订单
	 * @param orderid
	 * @return
	 */
	@RequestMapping("queryByTransactionid")
	@ResponseBody
	public ResultMessage queryByTransactionid(String transaction_id){
		if(StringUtil.isEmpty(transaction_id)) return ResultMessage.failed("微信支付订单编号不能为空");
		Map<String,String> result=this.orderQueryService.queryByTransactionid(transaction_id);
		
		LOG.info("微信支付查询订单接口返回数据："+JSONObject.toJSONString(result));
		
		String msg=null;
		if(result.get("return_code").equalsIgnoreCase("SUCCESS")){
			if(result.get("result_code").equalsIgnoreCase("SUCCESS")){
				if(result.get("trade_state").equalsIgnoreCase("SUCCESS")){
					return ResultMessage.success("支付成功", result);
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
