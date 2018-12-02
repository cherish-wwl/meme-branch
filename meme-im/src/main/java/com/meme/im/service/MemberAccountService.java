package com.meme.im.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.meme.core.base.IService;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.im.pojo.Member;
import com.meme.im.pojo.MemberRecharge;
import com.meme.im.pojo.MemberWithdrawals;

public interface MemberAccountService extends IService<Member, Form>{

	
	List<Member> selectByPagination(HttpServletRequest request,Form form);
	
	int count(HttpServletRequest request,Form form);

	List<MemberRecharge> doRechargeQuery(Long memberid);

	List<MemberWithdrawals> doWithdrawalsQuery(Long memberid);

	ResultMessage recharge(HttpServletRequest request, HttpServletResponse response, Integer type, String orderid);

	ResultMessage withdrawals(HttpServletRequest request, HttpServletResponse response, Integer type, Double money);

	ResultMessage doWithdrawals(MemberWithdrawals memberWithdrawals);

}
