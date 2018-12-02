package com.meme.im.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.meme.core.base.BaseController;
import com.meme.core.easyui.Pagination;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.im.pojo.Member;
import com.meme.im.pojo.MemberPrivate;
import com.meme.im.pojo.MemberRecharge;
import com.meme.im.pojo.MemberView;
import com.meme.im.pojo.MemberWithdrawals;
import com.meme.im.service.MemberAccountService;
import com.meme.im.service.MemberPrivateService;

/**
 * 会员管理控制器
 * @author hzl
 *
 */
@Controller
@RequestMapping("/im/member/account")
public class IMMemberAccountController extends BaseController{
	
	@Resource private MemberAccountService memberAccountService;
	
	@Resource private MemberPrivateService memberPrivateService;

	
	@RequestMapping("index")
	public ModelAndView index(){
		Map<String, Object> model = new HashMap<String, Object>();
		
		return new ModelAndView("/im/member/account/list",model);
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Pagination list(HttpServletRequest request,Form form) throws IllegalAccessException, InvocationTargetException{
		List<Member> list=this.memberAccountService.selectByPagination(request,form);
		List<MemberView> views=new ArrayList<MemberView>();
		List<Long> ids = new ArrayList<Long>();
		if(null!=list&&list.size()>0){
			for(Member m:list){
				MemberView v=new MemberView();
				BeanUtils.copyProperties(v, m);
				ids.add(m.getMemberid());
				if(m.getMtype() == 3) {
					v.setMtypeText("微商会员");
				}else {
					v.setMtypeText("普通会员");
				}
				views.add(v);
			}
		}
		List<MemberPrivate> privates = memberPrivateService.selectByIds(ids);
		Map<Long,MemberPrivate> map = new HashMap<Long,MemberPrivate>();
		for(MemberPrivate mem:privates) {
			map.put(mem.getMemberid(), mem);
		}
		for(MemberView view:views) {
			MemberPrivate memberPrivate = map.get(view.getMemberid());
			view.setName(memberPrivate.getName());
			view.setIdNumber(memberPrivate.getIdNumber());
			view.setBankCard(memberPrivate.getBankCard());
		}
		int count=this.memberAccountService.count(request,form);
		Pagination pagination=new Pagination();
		pagination.setRows(views);
		pagination.setTotal(count);
		
		return pagination;
	}
	
	@RequestMapping("rechargeQuery")
	public ModelAndView rechargeQuery(Long memberid){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("memberid", memberid);
		return new ModelAndView("/im/member/account/recharge",model);
	}
	
	@RequestMapping("doRechargeQuery")
	@ResponseBody
	public List<MemberRecharge> doRechargeQuery(Long memberid){
		List<MemberRecharge> list=this.memberAccountService.doRechargeQuery(memberid);
		return list;
	}
	
	@RequestMapping("withdrawalsQuery")
	public ModelAndView withdrawalsQuery(Long memberid){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("memberid", memberid);
		return new ModelAndView("/im/member/account/withdrawals",model);
	}
	
	@RequestMapping("doWithdrawalsQuery")
	@ResponseBody
	public List<MemberWithdrawals> doWithdrawalsQuery(Long memberid){
		List<MemberWithdrawals> list=this.memberAccountService.doWithdrawalsQuery(memberid);
		return list;
	}
	
	@RequestMapping("doWithdrawals")
	@ResponseBody
	public ResultMessage doWithdrawals(MemberWithdrawals memberWithdrawals){
		ResultMessage rs = this.memberAccountService.doWithdrawals(memberWithdrawals);
		return rs;
	}
	
}