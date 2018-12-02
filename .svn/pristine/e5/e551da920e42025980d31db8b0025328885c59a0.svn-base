package com.meme.member.controller;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.meme.core.http.response.LoginResultVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.meme.core.base.BaseController;
import com.meme.core.cache.ParamsCache;
import com.meme.core.http.ResultMessage;
import com.meme.core.log.LogType;
import com.meme.core.log.annotation.SysLog;
import com.meme.core.pojo.Params;
import com.meme.core.service.LogService;
import com.meme.core.util.AddressUtil;
import com.meme.core.util.CipherTool;
import com.meme.core.util.IDGenerator;
import com.meme.core.util.JWTUtil;
import com.meme.core.util.MD5Util;
import com.meme.core.util.QQLoginAPI;
import com.meme.core.util.StringUtil;
import com.meme.core.validator.ValidateBuilder;
import com.meme.core.validator.entity.ColumnRule;
import com.meme.im.pojo.ImFriendGroup;
import com.meme.im.pojo.Member;
import com.meme.im.redis.IpTmpMemberService;
import com.meme.im.service.ImFriendGroupService;
import com.meme.im.service.MemberService;
import com.meme.im.util.AccountUtil;
import com.meme.im.util.CookieUtil;
import com.meme.im.util.IMConstants;
import com.meme.pay.wechat.util.WeChatAPI;

@Controller
@RequestMapping("/member/")
public class MemberController extends BaseController{
	
	@Resource private MemberService memberService;
	@Resource private LogService logService;
	@Resource private IpTmpMemberService ipTmpMemberService;
	@Resource private ImFriendGroupService imFriendGroupService;
	
	@RequestMapping(value="loadValidateRules")
	@ResponseBody
	public String loadValidationRules(){
		ValidateBuilder.clear();
		
		ColumnRule rule=new ColumnRule();
		rule.setColumn("account");
		Map<String,String> ruleMap=new LinkedHashMap<String, String>();
		ruleMap.put("required", "true");
		ruleMap.put("byteRangeLength", "[1,50]");
		rule.setRules(ruleMap);
		ValidateBuilder.addRules(rule);
		
//		rule = new ColumnRule();
//		rule.setColumn("email");
//		ruleMap=new LinkedHashMap<String, String>();
//		ruleMap.put("email", "true");
//		rule.setRules(ruleMap);
//		ValidateBuilder.addRules(rule);

		rule = new ColumnRule();
		rule.setColumn("mpassword");
		ruleMap=new LinkedHashMap<String, String>();
		ruleMap.put("required", "true");
//		ruleMap.put("equalTo", "'#cmpassword'");
		ruleMap.put("minlength", "6");
		rule.setRules(ruleMap);
		ValidateBuilder.addRules(rule);

//		rule = new ColumnRule();
//		rule.setColumn("cmpassword");
//		ruleMap=new LinkedHashMap<String, String>();
//		ruleMap.put("required", "true");
//		ruleMap.put("equalTo", "'#mpassword'");
//		ruleMap.put("minlength", "6");
//		rule.setRules(ruleMap);
//		ValidateBuilder.addRules(rule);
		
		String js=ValidateBuilder.createJsValidateRules(Member.class);
		return js;
	}
	
	/**
	 * 会员中心后台
	 * @param memberid
	 * @return
	 */
	@RequestMapping(value = "privates/{memberid}")
	public ModelAndView index(@PathVariable("memberid")Long memberid){
		Map<String,Object> model=new HashMap<String, Object>();
		
		return new ModelAndView("/member/member",model);
	}
	
	/**
	 * 会员个人展示页面
	 * @param memberid
	 * @return
	 */
	@RequestMapping(value = "publics/{memberid}")
	public ModelAndView publics(@PathVariable("memberid")Long memberid){
		Map<String,Object> model=new HashMap<String, Object>();
		
		return new ModelAndView("/member/member",model);
	}
	
	/**
	 * 会员登录
	 * @return
	 */
	@RequestMapping(value="clogin")
	public ModelAndView clogin(HttpServletRequest request){
		Map<String,Object> model=new HashMap<String, Object>();
		String from=request.getParameter("from");
		if(!StringUtil.isEmpty(from)) model.put("from", from);
		return new ModelAndView("/member/clogin",model);
	}
	
	/**
	 * 么么聊登录
	 * @return
	 */
	@RequestMapping(value="login")
	public ModelAndView login(HttpServletRequest request){
		Map<String,Object> model=new HashMap<String, Object>();
		String from=request.getParameter("from");
		if(!StringUtil.isEmpty(from)) model.put("from", from);
		return new ModelAndView("/member/login",model);
	}
	
	/**
	 * 登录
	 * @param request
	 * @param response
	 * @param account
	 * @param password
	 * @param validateCode
	 * @return
	 */
	@RequestMapping(value="doLogin")
	@ResponseBody
	public ResultMessage doLogin(HttpServletRequest request,HttpServletResponse response,String account,String password,String validateCode,@RequestHeader(value="Origin") String Origin){
		response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		if(StringUtil.isOneEmpty(account,password)){
			return ResultMessage.failed("用户名和密码不能为空");
		}
		List<Member> list=this.memberService.check(null, account);
		if(null==list||list.size()==0) return ResultMessage.failed("用户名不存在");
		if(list.size()>1){
			this.logService.log(request, LogType.EXCEPTION.getType(), LogType.EXCEPTION.getType(), "此账号存在重复，请检查数据库");
			return ResultMessage.failed("账号异常，请联系管理员处理");
		}
		Member member=list.get(0);
		
		if(member.getMtype()==-1||member.getState()==0) return ResultMessage.failed("临时访客请先注册");
		
		String salt=member.getSalt();
		if(!MD5Util.generatePassword(password+salt).equals(member.getMpassword())){
			return ResultMessage.failed("密码错误");
		}
		
		//token存cookie
		Map<String, Object> info = new HashMap<String, Object>();
		info.put(IMConstants.COOKIE_MEMBER_KEY, member.getMemberid());
//		info.put("mtype", member.getMtype());
//		info.put("state", member.getState());
		String token=JWTUtil.buildToken(info);
		info.clear();
		info.put(IMConstants.COOKIE_TOKEN_KEY, token);
		CookieUtil.setCookies(request, response, info, null);
		
		String ip=AddressUtil.getRealIPAddress(request);
		//添加ip-memberid映射进redis
		this.ipTmpMemberService.add(ip, member.getMemberid().toString());
		member.setLastip(AddressUtil.getRealIPAddress(request));
		member.setLastlogin(new Date());
		this.memberService.updateByPrimaryKeySelective(member);
		LoginResultVo loginResultVo = new LoginResultVo();
		loginResultVo.setMemberId(member.getMemberid());
		loginResultVo.setToken(token);
		return ResultMessage.success("登录成功",loginResultVo);
	}

	
	/**
	 * 微信登陆
	 * @param memberid
	 * @return
	 */
	@RequestMapping(value="wxLogin")
	public String wxLogin(String code,String state,HttpServletRequest request,HttpServletResponse response){
		JSONObject result=WeChatAPI.getWebAccessToken(code,ParamsCache.get("WEIXIN_APP_ID").getValue(),ParamsCache.get("WEIXIN_APP_SECRET").getValue());
		Long memberid = null;
		if(null!=result){
			Integer errcode=null;
			errcode=result.getInteger("errcode");
			if(null==errcode){
				String openid=result.getString("openid");
				JSONObject webUserinfo = WeChatAPI.getWebUserinfo(result.getString("access_token"), openid);
				Member member = new Member();
				member.setUnionid(webUserinfo.getString("unionid"));
				List<Member> selectByEntity = memberService.selectByEntity(member);
				if(selectByEntity != null && selectByEntity.size()>0) {
					memberid = selectByEntity.get(0).getMemberid();
				}else {
					member.setOpenid(openid);
					member.setNickname(webUserinfo.getString("nickname"));
					member.setSex(webUserinfo.getInteger("sex"));
					member.setAvatar(webUserinfo.getString("headimgurl"));
					member.setCountry(webUserinfo.getString("country"));
					member.setProvince(webUserinfo.getString("province"));
					member.setCity(webUserinfo.getString("city"));
					Object cookiememberid=AccountUtil.getMemberid(request);
					boolean flag = true;
					if(null!=cookiememberid){
						Member tmp_mem=memberService.selectByPrimaryKey(Long.valueOf(cookiememberid.toString()));
						if(null!=tmp_mem&&tmp_mem.getMtype()==-1){
							memberid = Long.valueOf(cookiememberid.toString());
							member.setMemberid(memberid);
							memberService.updateByPrimaryKeySelective(member);
							flag = false;
						}
					}
					if(flag) {
						memberid = IDGenerator.generateId();
						member.setMemberid(memberid);
						member.setAccount(memberid.toString());
						Params params=ParamsCache.get("DEFAULT_PASSWORD");
						String defaultPwd="12345678";
						if(null!=params) defaultPwd=params.getValue();
						member.setRegistertime(new Date());
						String salt=CipherTool.createSalt();
						member.setSalt(salt);
						member.setMpassword(MD5Util.generatePassword(defaultPwd+salt));
						member.setMtype(2);
						member.setState(1);
						memberService.insertSelective(member);
						//添加默认好友分组
						ImFriendGroup group=new ImFriendGroup();
						group.setGroupname("我的好友");
						long groupid=IDGenerator.generateId();
						group.setId(groupid);
						group.setMemberid(memberid);
						this.imFriendGroupService.add(request, group);
					}
				}
			}
		}
		if(memberid != null) {
			//token存cookie
			Map<String, Object> info = new HashMap<String, Object>();
			info.put(IMConstants.COOKIE_MEMBER_KEY, memberid);
			String token=JWTUtil.buildToken(info);
			info.clear();
			info.put(IMConstants.COOKIE_TOKEN_KEY, token);
			CookieUtil.setCookies(request, response, info, null);
			String ip=AddressUtil.getRealIPAddress(request);
			//添加ip-memberid映射进redis
			this.ipTmpMemberService.add(ip, memberid.toString());
			Member member = new Member();
			member.setMemberid(memberid);
			member.setLastip(AddressUtil.getRealIPAddress(request));
			member.setLastlogin(new Date());
			this.memberService.updateByPrimaryKeySelective(member);
		}
		return "redirect:"+state;
	}
	
	/**
	 * QQ登陆
	 * @return
	 */
	@RequestMapping(value="QQLogin")
	public String QQLogin(String code,String state,HttpServletRequest request,HttpServletResponse response) throws Exception{
		JSONObject result=QQLoginAPI.getWebAccessToken(code,ParamsCache.get("QQ_LOGIN_ID").getValue(),ParamsCache.get("QQ_LOGIN_SECRET").getValue(),URLEncoder.encode("http://m.mmgmmj.com/member/QQLogin", "UTF-8"));
		Long memberid = null;
		if(null!=result && result.getString("code") == null){
			String access_token = result.getString("access_token");
			String openid = QQLoginAPI.getOpenId(access_token).getString("openid");
			JSONObject webUserinfo = QQLoginAPI.getWebUserinfo(access_token, ParamsCache.get("QQ_LOGIN_ID").getValue(),openid);
			Member member = new Member();
			member.setQq(openid);
			List<Member> selectByEntity = memberService.selectByEntity(member);
			if(selectByEntity != null && selectByEntity.size()>0) {
				memberid = selectByEntity.get(0).getMemberid();
			}else {
				member.setNickname(webUserinfo.getString("nickname"));
				member.setSex(webUserinfo.getString("gender").equals("男")?1:2);
				member.setAvatar(webUserinfo.getString("figureurl_qq_1"));
				String ip=AddressUtil.getRealIPAddress(request);
				if(!ip.equals("127.0.0.1")&&!ip.startsWith("192.168.")&&!ip.startsWith("10.")&&!ip.startsWith("172.")&&!ip.startsWith("169.254.")){
					JSONObject json=AddressUtil.ip2Address(ip);
					if(json.getInteger("code")==0){
						JSONObject data=json.getJSONObject("data");
						member.setCountry(data.getString("country"));
						member.setProvince(data.getString("region"));
						member.setCity(data.getString("city"));
					}
				}
				Object cookiememberid=AccountUtil.getMemberid(request);
				boolean flag = true;
				if(null!=cookiememberid){
					Member tmp_mem=memberService.selectByPrimaryKey(Long.valueOf(cookiememberid.toString()));
					if(null!=tmp_mem&&tmp_mem.getMtype()==-1){
						memberid = Long.valueOf(cookiememberid.toString());
						member.setMemberid(memberid);
						memberService.updateByPrimaryKeySelective(member);
						flag = false;
					}
				}
				if(flag) {
					memberid = IDGenerator.generateId();
					member.setMemberid(memberid);
					member.setAccount(memberid.toString());
					Params params=ParamsCache.get("DEFAULT_PASSWORD");
					String defaultPwd="12345678";
					if(null!=params) defaultPwd=params.getValue();
					member.setRegistertime(new Date());
					String salt=CipherTool.createSalt();
					member.setSalt(salt);
					member.setMpassword(MD5Util.generatePassword(defaultPwd+salt));
					member.setMtype(2);
					member.setState(1);
					memberService.insertSelective(member);
					//添加默认好友分组
					ImFriendGroup group=new ImFriendGroup();
					group.setGroupname("我的好友");
					long groupid=IDGenerator.generateId();
					group.setId(groupid);
					group.setMemberid(memberid);
					this.imFriendGroupService.add(request, group);
				}
			}
		}
		if(memberid != null) {
			//token存cookie
			Map<String, Object> info = new HashMap<String, Object>();
			info.put(IMConstants.COOKIE_MEMBER_KEY, memberid);
			String token=JWTUtil.buildToken(info);
			info.clear();
			info.put(IMConstants.COOKIE_TOKEN_KEY, token);
			CookieUtil.setCookies(request, response, info, null);
			String ip=AddressUtil.getRealIPAddress(request);
			//添加ip-memberid映射进redis
			this.ipTmpMemberService.add(ip, memberid.toString());
			Member member = new Member();
			member.setMemberid(memberid);
			member.setLastip(AddressUtil.getRealIPAddress(request));
			member.setLastlogin(new Date());
			this.memberService.updateByPrimaryKeySelective(member);
		}
		return "redirect:"+state;
	}
	
	/**
	 * 会员注册
	 * @return
	 */
	@RequestMapping(value="cregister")
	public ModelAndView cregister(){
		Map<String,Object> model=new HashMap<String, Object>();
		
		return new ModelAndView("/member/cregister",model);
	}

	/**
	 * 注册页面
	 * @param memberid
	 * @return
	 */
	@RequestMapping(value="register")
	public ModelAndView register(){
		Map<String,Object> model=new HashMap<String, Object>();
		
		return new ModelAndView("/member/register",model);
	}
	
	/**
	 * 注册操作
	 * @param request
	 * @param response
	 * @param obj
	 */
	@RequestMapping(value="doRegister")
	@ResponseBody
	public ResultMessage doRegister(HttpServletRequest request,HttpServletResponse response,Member obj,@RequestHeader(value="Origin") String Origin){
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		ResultMessage result=this.memberService.doRegister(request,response, obj);
		if(result.getState().equals("1")){
			Object data=result.getData();
			if(null!=data){
				Long memberid=Long.valueOf(data.toString());
				
				//token存cookie
				Map<String, Object> info = new HashMap<String, Object>();
				info.put(IMConstants.COOKIE_MEMBER_KEY, memberid);
				String token=JWTUtil.buildToken(info);
				info.clear();
				info.put(IMConstants.COOKIE_TOKEN_KEY, token);
				CookieUtil.setCookies(request, response, info, null);
				Member member = new Member();
				member.setMemberid(memberid);
				member.setLastip(AddressUtil.getRealIPAddress(request));
				member.setLastlogin(new Date());
				this.memberService.updateByPrimaryKeySelective(member);
				LoginResultVo loginResultVo = new LoginResultVo();
				loginResultVo.setMemberId(memberid);
				loginResultVo.setToken(token);
				return ResultMessage.success("注册成功",loginResultVo);
			}
		}
		return result;
	}
	
	/**
	 * 退出登录
	 * @param request
	 * @return
	 */
	@RequestMapping("logout")
	@SysLog(event = "退出么么聊",type=LogType.OTHERS)
	public ModelAndView logout(HttpServletRequest request,HttpServletResponse response){
		Object memberid=AccountUtil.getMemberid(request);
		Member member=null;
		if(null!=memberid){
			member=this.memberService.selectByPrimaryKey(memberid);
		}
		//两种状态下清除token cookie，1、token取不到会员ID；2、会员类型为非访客
		if(null==member||(null!=member&&member.getMtype()!=-1)){
			request.getSession().invalidate();
			Cookie cookie=new Cookie(IMConstants.COOKIE_TOKEN_KEY,null);
			cookie.setMaxAge(0);
			String domain=AddressUtil.getRootDomain(request);
			//path设为/，使顶级域名和二级域名cookie共享
			if(domain==null) {
				cookie.setDomain(request.getLocalAddr());
				cookie.setPath(request.getContextPath());
			} else {
				cookie.setDomain(domain);
				cookie.setPath("/");
			}
			response.addCookie(cookie);
		}
		return new ModelAndView("redirect:http://mmgmmj.com/login");
	}
	
	/**
	 * 退出登录
	 * @param request
	 * @return
	 */
	@RequestMapping("clogout")
	@SysLog(event = "会员退出登录",type=LogType.OTHERS)
	public ModelAndView clogout(HttpServletRequest request,HttpServletResponse response){
		Object memberid=AccountUtil.getMemberid(request);
		Member member=null;
		if(null!=memberid){
			member=this.memberService.selectByPrimaryKey(memberid);
		}
		//两种状态下清除token cookie，1、token取不到会员ID；2、会员类型为非访客
		if(null==member||(null!=member&&member.getMtype()!=-1)){
			request.getSession().invalidate();
			Cookie cookie=new Cookie(IMConstants.COOKIE_TOKEN_KEY,null);
			cookie.setMaxAge(0);
			String domain=AddressUtil.getRootDomain(request);
			//path设为/，使顶级域名和二级域名cookie共享
			if(domain==null) {
				cookie.setDomain(request.getLocalAddr());
				cookie.setPath(request.getContextPath());
			} else {
				cookie.setDomain(domain);
				cookie.setPath("/");
			}
			response.addCookie(cookie);
		}
		return new ModelAndView("redirect:http://mmgmmj.com/clogin");
	}
	
	/**
	 * 为临时访客生成临时会员账号记录
	 * @param request
	 * @param response
	 */
	@RequestMapping("init_tmp_member")
	@ResponseBody
	public ResultMessage init_tmp_member(HttpServletRequest request,HttpServletResponse response){
		return this.memberService.init_tmp_member(request, response);
	}
}
