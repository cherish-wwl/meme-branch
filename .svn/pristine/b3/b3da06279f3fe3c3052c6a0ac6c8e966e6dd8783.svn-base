package com.meme.core.service.impl;

import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.meme.core.base.AbstractService;
import com.meme.core.dao.LogMapper;
import com.meme.core.dao.LoginAccountMapper;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.core.mybatis.Criterion;
import com.meme.core.mybatis.Criterion.CriterionBuilder;
import com.meme.core.mybatis.MapperHelper;
import com.meme.core.mybatis.enums.Operator;
import com.meme.core.pojo.Log;
import com.meme.core.pojo.LoginAccountInfoView;
import com.meme.core.service.LogService;
import com.meme.core.util.AddressUtil;
import com.meme.core.util.Constants;
import com.meme.core.util.IDGenerator;
import com.meme.core.util.StringUtil;
import com.meme.core.util.UserAgentUtil;

@Service("LogService")
@Transactional
public class LogServiceImpl extends AbstractService implements LogService {

	@Resource
	private LogMapper mapper;
	@Resource
	private LoginAccountMapper loginAccountMapper;

	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Log record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(Log record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public Log selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Log record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Log record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Log> selectAll(){
		return (List<Log>) MapperHelper.toBeanList(this.mapper.selectAll(Log.class), Log.class);
	}

	protected void buildOrderByCriterion(Class<?> entityClass, Form form) {
		CriterionBuilder builder=new Criterion.CriterionBuilder();
		if(StringUtil.isAllNotEmpty(this.getForm().getSort())){
			builder.operator(Operator.ORDER_BY).condition(this.getForm().getSort());
			if(StringUtil.isAllNotEmpty(this.getForm().getOrder())){
				builder.leftValue(this.getForm().getOrder());
			}
		}else{
			builder.operator(Operator.ORDER_BY).condition("createtime");
			builder.leftValue("desc");
		}
		super.getList().add(builder.build());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Log> selectByPagination(Form form) {
		this.buildSearchCriterion(Log.class, form);
		this.buildOrderByCriterion(Log.class, form);
		this.buildLimitCriterion(Log.class, form);
		return (List<Log>) MapperHelper.toBeanList(this.mapper.selectByPagination(Log.class,this.getList()), Log.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(Log.class, form);
		return this.mapper.count(Log.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Log> selectByEntity(Log record) {
		return (List<Log>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), Log.class);
	}

	@Override
	public int batchInsert(List<Log> record) {
		return this.mapper.batchInsert(record,Log.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,Log.class);
	}

	@Override
	public int batchUpdate(List<Log> record) {
		return this.mapper.batchUpdate(record,Log.class);
	}

	@Override
	public void log(HttpServletRequest request, String event, String type, String description){
		Object user = request.getSession().getAttribute(Constants.SESSION_USER);
		String account=null;
		Long loginid=null;
		if (null!=user) {
			account=((LoginAccountInfoView) user).getAccount();
			loginid=((LoginAccountInfoView) user).getLoginid();
		} else {
			account=request.getParameter("account");
			String s=request.getParameter("loginid");
			if(!StringUtil.isEmpty(s)) loginid=Long.valueOf(s);
		}
		Log log = new Log();
		if(!StringUtil.isEmpty(account)) log.setAccount(account);
		if(null!=loginid&&loginid!=0l) {
			log.setLoginid(loginid);
		}else{
			if(!StringUtil.isEmpty(account)) {
				List<LoginAccountInfoView> list=loginAccountMapper.isExist(account,null);
				if(null!=list&&list.size()>0) log.setLoginid(list.get(0).getLoginid());
			}
		}
		String requestHeader = request.getHeader("User-Agent");
		
	    if(!StringUtil.isEmpty(event))log.setEvent(event);
	    if(!StringUtil.isEmpty(type)){
	    	log.setType(type);
	    }
	    log.setTerminal(UserAgentUtil.checkDevice(requestHeader));
		log.setBtype(UserAgentUtil.checkBrowser(requestHeader));
		if(StringUtil.isAllNotEmpty(description)) log.setDescription(description);
		
		String ip=AddressUtil.getRealIPAddress(request);
		if(StringUtil.isAllNotEmpty(ip)) log.setIp(ip);
		StringBuffer url=new StringBuffer();
		url.append(request.getRequestURL().toString()).append("?");
		if(request.getMethod().equalsIgnoreCase("GET")){
			Enumeration<String> enu=request.getParameterNames();
			while(enu.hasMoreElements()){
				String key=enu.nextElement();
				String value=request.getParameter(key);
				if(!StringUtil.isEmpty(value)) url.append(key+"="+value+"&");
			}
		}
		log.setUrl(url.substring(0, url.length()-1));
		log.setCreatetime(new Date());
		
		//内网ip不解析
		if(!ip.startsWith("0:0:") && !ip.equals("127.0.0.1") && !ip.startsWith("192.168.")
				&& !ip.startsWith("10.") && !ip.startsWith("172.") && !ip.startsWith("169.254.")) {
			JSONObject result=AddressUtil.ip2Address(ip);
			if(result.getInteger("code")==0){
				JSONObject data=result.getJSONObject("data");
				log.setCountry(data.getString("country"));
				log.setProvince(data.getString("region"));
				log.setCity(data.getString("city"));
				log.setIsp(data.getString("isp"));
			}
		}
		
		long id=IDGenerator.generateId();
		log.setId(id);
		this.mapper.insertSelective(log);
	}

	@Override
	public ResultMessage add(HttpServletRequest request, Log record) {
		return null;
	}

	@Override
	public ResultMessage edit(HttpServletRequest request, Log record) {
		return null;
	}

	@Override
	public ResultMessage delete(List<Object> ids) {
		this.batchDelete(ids);
    	
    	return ResultMessage.defaultSuccessMessage();
	}

}
