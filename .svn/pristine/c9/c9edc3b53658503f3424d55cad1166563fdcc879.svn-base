package com.meme.im.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meme.core.base.AbstractService;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.core.mybatis.Criterion;
import com.meme.core.mybatis.MapperHelper;
import com.meme.core.mybatis.enums.Operator;
import com.meme.core.util.IDGenerator;
import com.meme.core.util.StringUtil;
import com.meme.im.dao.MemberMapper;
import com.meme.im.dao.MemberRechargeMapper;
import com.meme.im.dao.MemberWithdrawalsMapper;
import com.meme.im.dao.MemeOrderMapper;
import com.meme.im.pojo.Member;
import com.meme.im.pojo.MemberRecharge;
import com.meme.im.pojo.MemberWithdrawals;
import com.meme.im.pojo.MemeOrder;
import com.meme.im.service.MemberAccountService;
import com.meme.im.util.AccountUtil;

@Service("MemberAccountService")
@Transactional
public class MemberAccountServiceImpl extends AbstractService implements MemberAccountService {

	@Resource private MemberMapper mapper;
	@Resource private MemberRechargeMapper memberRechargeMapper;
	@Resource private MemberWithdrawalsMapper memberWithdrawalsMapper;
	@Resource private MemeOrderMapper memeOrderMapper;

	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Member record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(Member record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public Member selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Member record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Member record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Member> selectAll(){
		return (List<Member>) MapperHelper.toBeanList(this.mapper.selectAll(Member.class), Member.class);
	}

	public void buildSearchCriterion(Class<?> entityClass, Form form) {
		super.buildSearchCriterion(entityClass, form);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Member> selectByPagination(Form form) {
		this.buildSearchCriterion(Member.class, form);
		this.buildOrderByCriterion(Member.class, form);
		this.buildLimitCriterion(Member.class, form);
		return (List<Member>) MapperHelper.toBeanList(this.mapper.selectByPagination(Member.class,this.getList()), Member.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(Member.class, form);
		return this.mapper.count(Member.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Member> selectByEntity(Member record) {
		return (List<Member>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), Member.class);
	}

	@Override
	public int batchInsert(List<Member> record) {
		return this.mapper.batchInsert(record,Member.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,Member.class);
	}

	@Override
	public int batchUpdate(List<Member> record) {
		return this.mapper.batchUpdate(record,Member.class);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Member> selectByPagination(HttpServletRequest request, Form form) {
		String account=request.getParameter("account");
		String payaccount=request.getParameter("payaccount");
		String mtype=request.getParameter("mtype");
		String unwithdrawals=request.getParameter("unwithdrawals");
		this.buildSearchCriterion(Member.class, form);
		List<Criterion> criterions=new ArrayList<Criterion>();
		if(StringUtil.isAllNotEmpty(account)){
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.LEFT_BRACKET).build());
			criterions.add(new Criterion.CriterionBuilder().condition("account").operator(Operator.LIKE).leftValue(account).javaType(String.class).build());
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.OR).build());
			criterions.add(new Criterion.CriterionBuilder().condition("nickname").operator(Operator.LIKE).leftValue(account).javaType(String.class).build());
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.RIGHT_BRACKET).build());
		}
		if(StringUtil.isAllNotEmpty(payaccount)){
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.LEFT_BRACKET).build());
			criterions.add(new Criterion.CriterionBuilder().condition("payaccount").operator(Operator.LIKE).leftValue(payaccount).javaType(String.class).build());
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.OR).build());
			criterions.add(new Criterion.CriterionBuilder().condition("paynickname").operator(Operator.LIKE).leftValue(payaccount).javaType(String.class).build());
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.RIGHT_BRACKET).build());
		}
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("mtype").operator(Operator.NOT_EQUAL).leftValue(-1).javaType(Integer.class).build());
		if(StringUtil.isAllNotEmpty(mtype)){
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			if("1".equals(mtype)) {
				criterions.add(new Criterion.CriterionBuilder().condition("mtype").operator(Operator.NOT_EQUAL).leftValue(3).javaType(Integer.class).build());
			}else if("2".equals(mtype)) {
				criterions.add(new Criterion.CriterionBuilder().condition("mtype").operator(Operator.EQUAL).leftValue(3).javaType(Integer.class).build());
			}
		}
		if(StringUtil.isAllNotEmpty(unwithdrawals)){
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			criterions.add(new Criterion.CriterionBuilder().condition("unwithdrawals").operator(Operator.EQUAL).leftValue(unwithdrawals).javaType(String.class).build());
		}
		this.getList().addAll(criterions);
		this.buildOrderByCriterion(Member.class, form);
		this.buildLimitCriterion(Member.class, form);
		return (List<Member>) MapperHelper.toBeanList(this.mapper.selectByPagination(Member.class,this.getList()), Member.class);
	}

	@Override
	public int count(HttpServletRequest request, Form form) {
		String account=request.getParameter("account");
		String payaccount=request.getParameter("payaccount");
		String mtype=request.getParameter("mtype");
		String unwithdrawals=request.getParameter("unwithdrawals");
		this.buildSearchCriterion(Member.class, form);
		List<Criterion> criterions=new ArrayList<Criterion>();
		if(StringUtil.isAllNotEmpty(account)){
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.LEFT_BRACKET).build());
			criterions.add(new Criterion.CriterionBuilder().condition("account").operator(Operator.LIKE).leftValue(account).javaType(String.class).build());
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.OR).build());
			criterions.add(new Criterion.CriterionBuilder().condition("nickname").operator(Operator.LIKE).leftValue(account).javaType(String.class).build());
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.RIGHT_BRACKET).build());
		}
		if(StringUtil.isAllNotEmpty(payaccount)){
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.LEFT_BRACKET).build());
			criterions.add(new Criterion.CriterionBuilder().condition("payaccount").operator(Operator.LIKE).leftValue(payaccount).javaType(String.class).build());
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.OR).build());
			criterions.add(new Criterion.CriterionBuilder().condition("paynickname").operator(Operator.LIKE).leftValue(payaccount).javaType(String.class).build());
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.RIGHT_BRACKET).build());
		}
		criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
		criterions.add(new Criterion.CriterionBuilder().condition("mtype").operator(Operator.NOT_EQUAL).leftValue(-1).javaType(Integer.class).build());
		if(StringUtil.isAllNotEmpty(mtype)){
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			if("1".equals(mtype)) {
				criterions.add(new Criterion.CriterionBuilder().condition("mtype").operator(Operator.NOT_EQUAL).leftValue(3).javaType(Integer.class).build());
			}else if("2".equals(mtype)) {
				criterions.add(new Criterion.CriterionBuilder().condition("mtype").operator(Operator.EQUAL).leftValue(3).javaType(Integer.class).build());
			}
		}
		if(StringUtil.isAllNotEmpty(unwithdrawals)){
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			criterions.add(new Criterion.CriterionBuilder().condition("unwithdrawals").operator(Operator.EQUAL).leftValue(unwithdrawals).javaType(String.class).build());
		}
		this.getList().addAll(criterions);
		return this.mapper.count(Member.class,this.getList());
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<MemberRecharge> doRechargeQuery(Long memberid) {
		MemberRecharge memberRecharge = new MemberRecharge();
		memberRecharge.setMemberid(memberid);
		List<MemberRecharge> beanList = (List<MemberRecharge>) MapperHelper.toBeanList(this.memberRechargeMapper.selectByEntity(memberRecharge), MemberRecharge.class);
		return beanList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<MemberWithdrawals> doWithdrawalsQuery(Long memberid) {
		MemberWithdrawals memberWithdrawals = new MemberWithdrawals();
		memberWithdrawals.setMemberid(memberid);
		List<MemberWithdrawals> beanList = (List<MemberWithdrawals>) MapperHelper.toBeanList(this.memberWithdrawalsMapper.selectByEntity(memberWithdrawals), MemberWithdrawals.class);
		return beanList;
	}

	@Override
	public ResultMessage recharge(HttpServletRequest request, HttpServletResponse response, Integer type,
			String orderid) {
		Long memberid=(Long)AccountUtil.getMemberid(request);
		MemeOrder memeOrder = memeOrderMapper.selectByPrimaryKey(orderid);
		if(memeOrder.getState() == 1 && memeOrder.getMemberid().equals(memberid)) {
			MemberRecharge memberRecharge = new MemberRecharge();
			Long generateId = IDGenerator.generateId();
			memberRecharge.setId(generateId.toString());
			memberRecharge.setType(type);
			memberRecharge.setMoney(memeOrder.getAmount().doubleValue());
			memberRecharge.setDatetime(new Date());
			memberRecharge.setOrderid(orderid);
			memberRecharge.setMemberid(memberid);
			memberRechargeMapper.insertSelective(memberRecharge);
			if(type == 1) {
				Member member = new Member();
				member.setMemberid(memberid);
				member.setMtype(3);
				member.setDeposit(memeOrder.getAmount().doubleValue());
				mapper.updateByPrimaryKeySelective(member);
			}else {
				Member member = mapper.selectByPrimaryKey(memberid);
				member.setBalance((member.getBalance()==null?0.0:member.getBalance())+memeOrder.getAmount().doubleValue());
				mapper.updateByPrimaryKeySelective(member);
			}
			return ResultMessage.defaultSuccessMessage();
		}
		return ResultMessage.failed("订单号错误");

	}

	@Override
	public ResultMessage withdrawals(HttpServletRequest request, HttpServletResponse response, Integer type,
			Double money) {
		Long memberid=(Long)AccountUtil.getMemberid(request);
		Member member = mapper.selectByPrimaryKey(memberid);
		if(type == 1 ) {
			if(member.getDeposit() == null || member.getDeposit() == 0.0) {
				return ResultMessage.failed("没有可退押金的余额");
			}
			member.setMtype(0);
			member.setDeposit(0.0);
		}else {
			if(member.getBalance() == null || member.getBalance() < money) {
				return ResultMessage.failed("没有可提现的余额");
			}
			member.setBalance(member.getBalance() - money);
		}
		MemberWithdrawals memberWithdrawals = new MemberWithdrawals();
		Long generateId = IDGenerator.generateId();
		memberWithdrawals.setId(generateId.toString());
		memberWithdrawals.setMemberid(memberid);
		memberWithdrawals.setMoney(money);
		memberWithdrawals.setType(type);
		memberWithdrawals.setDatetime(new Date());
		memberWithdrawals.setState(1);
		memberWithdrawalsMapper.insertSelective(memberWithdrawals);
		member.setUnwithdrawals("是");
		mapper.updateByPrimaryKeySelective(member);
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage doWithdrawals(MemberWithdrawals memberWithdrawals) {
		memberWithdrawals.setState(2);
		memberWithdrawals.setHandletime(new Date());
		memberWithdrawalsMapper.updateByPrimaryKeySelective(memberWithdrawals);
		MemberWithdrawals queryEntity = new MemberWithdrawals();
		queryEntity.setMemberid(memberWithdrawals.getMemberid());
		queryEntity.setState(1);
		List<Map<String,Object>> selectByEntity = memberWithdrawalsMapper.selectByEntity(queryEntity);
		if(selectByEntity == null || selectByEntity.size() == 0) {
			Member member = new Member();
			member.setMemberid(memberWithdrawals.getMemberid());
			member.setUnwithdrawals("否");
			mapper.updateByPrimaryKeySelective(member);
		}
		return ResultMessage.defaultSuccessMessage();
	}

}
