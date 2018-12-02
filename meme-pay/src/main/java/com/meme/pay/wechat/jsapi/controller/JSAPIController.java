package com.meme.pay.wechat.jsapi.controller;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.meme.core.base.BaseController;
import com.meme.core.cache.DictCache;
import com.meme.core.cache.ParamsCache;
import com.meme.core.http.ResultMessage;
import com.meme.core.util.StringUtil;
import com.meme.im.pojo.Member;
import com.meme.im.pojo.MemeOrder;
import com.meme.im.service.MemberService;
import com.meme.im.service.MemeOrderService;
import com.meme.im.util.AccountUtil;
import com.meme.pay.entity.TradeParam;
import com.meme.pay.wechat.jsapi.service.JSAPIService;
import com.meme.pay.wechat.service.OrderQueryService;
import com.meme.pay.wechat.util.WeChatAPI;

/**
 * 微信公众号支付相关接口
 * @author hzl
 *
 */
@Controller
@RequestMapping("/wechat/pay/jsapi/")
public class JSAPIController extends BaseController {

	private static final Logger LOG = LoggerFactory.getLogger("======微信公众号支付控制器======");
	@Resource private JSAPIService jSAPIService;
	@Resource private MemberService memberService;
	@Resource private MemeOrderService memeOrderService;
	@Resource private OrderQueryService orderQueryService;
	
	//微信回调后浏览器请求改地址返回code
	@RequestMapping("wxRedirect")
	public void wxRedirect(HttpServletRequest request,HttpServletResponse response,String code,String type,String productid){
		Object memberid=AccountUtil.getMemberid(request);
		if(null!=memberid){
			Member member=this.memberService.selectByPrimaryKey(Long.valueOf(memberid.toString()));
			//获取到会员账号判断是否有openid值，无则请求微信接口获取
			if(null != member && StringUtil.isEmpty(member.getOpenid())){
				//通过code换取网页授权access_token，拉取用户信息存数据库和session会话
				JSONObject result=WeChatAPI.getWebAccessToken(code,ParamsCache.get("WEIXIN_APP_ID").getValue(),ParamsCache.get("WEIXIN_APP_SECRET").getValue());
				if(null!=result){
					Integer errcode=null;
					errcode=result.getInteger("errcode");
					if(null==errcode){
						//只取openid值
						String openid=result.getString("openid");
						Member m=new Member();
						m.setMemberid(member.getMemberid());
						m.setOpenid(openid);
						this.memberService.updateByPrimaryKeySelective(m);
					}
				}
			}
		}
		try {
			if("wbshop".equals(type)) {
				response.sendRedirect("http://mmgmmj.com/wbpay/index?productid="+productid);
			}else {
				response.sendRedirect("http://mmgmmj.com/pay/index?productid="+productid);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("pay")
	@ResponseBody
	public ResultMessage pay(HttpServletRequest request,HttpServletResponse response,TradeParam param,@RequestHeader(value="Origin") String Origin,String type,String consignee,String receiving_telephone,String receiving_address,Double exxpress_cost){
		response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		return this.jSAPIService.pay(request, response, param, type, consignee, receiving_telephone, receiving_address, exxpress_cost);
	}
	
	@RequestMapping("check")
	@ResponseBody
	public ResultMessage check(String orderid,HttpServletResponse response,@RequestHeader(value="Origin") String Origin) {
		response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		if(StringUtil.isEmpty(orderid)) return ResultMessage.failed("商户内部订单编号不能为空");
		MemeOrder order=this.memeOrderService.selectByPrimaryKey(orderid);
		if(null==order) return ResultMessage.failed("订单不存在");
		
		if(order.getState()==1) return ResultMessage.success("支付成功");
		
		Map<String,String> result=this.orderQueryService.queryByOrderid(orderid);
		
		String msg=null;
		if(result.get("return_code").equalsIgnoreCase("SUCCESS")){
			if(result.get("result_code").equalsIgnoreCase("SUCCESS")){
				if(result.get("trade_state").equalsIgnoreCase("SUCCESS")){
					return ResultMessage.success("支付成功");
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