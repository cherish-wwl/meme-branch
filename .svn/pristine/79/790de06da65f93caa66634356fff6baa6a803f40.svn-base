package com.meme.member.interceptor;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.meme.core.util.JWTUtil;
import com.meme.core.util.StringUtil;
import com.meme.im.pojo.Member;
import com.meme.im.service.MemberService;
import com.meme.im.util.IMConstants;

public class MemberInterceptor extends HandlerInterceptorAdapter{
	
	@Resource private MemberService memberService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Cookie[] cookies=request.getCookies();
		String token=null;
		if(null!=cookies&&cookies.length>0){
			for(Cookie co: cookies){
				if(co.getName().equals(IMConstants.COOKIE_TOKEN_KEY)){
					token=co.getValue();
					break;
				}
			}
		}
		if(!RequestMethod.OPTIONS.toString().equals(request.getMethod())){
			if(StringUtil.isEmpty(token)) {
				this.output(request, response, request.getContextPath()+"/member/login","");
				return false;
			}
			Map<String,Object> claims=JWTUtil.verifyToken(token);
			if(null==claims) {
				this.output(request, response, request.getContextPath()+"/member/login","");
				return false;
			}
			Object memberid=claims.get(IMConstants.COOKIE_MEMBER_KEY);
			if(null==memberid) {
				this.output(request, response, request.getContextPath()+"/member/login","");
				return false;
			}
			Member member=this.memberService.selectByPrimaryKey(memberid);
			if(null==member){
				this.output(request, response, request.getContextPath()+"/member/login","");
				return false;
			}
		}
		String Origin = request.getHeader("Origin");
		response.setHeader("Access-Control-Allow-Origin", Origin == null ? "*" : Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
//		if(member.getMtype()==-1||member.getState()==0){
//			this.output(request, response, request.getContextPath()+"/member/login","临时访客限制登录");
//			return false;
//		}
		
		return true;
	}
	
	private void output(HttpServletRequest request,HttpServletResponse response,String url,String msg) throws IOException{
		response.setContentType("text/html;charset=utf-8");
		java.io.PrintWriter out = response.getWriter();
	    out.println("<html>");  
	    out.println("<script>");
	    if(!StringUtil.isEmpty(msg)) out.println("alert('"+msg+"')");
	    out.println("window.open ('"+url+"','_top')");
	    out.println("</script>");  
	    out.println("</html>");
	    out.close();
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		super.afterConcurrentHandlingStarted(request, response, handler);
	}

}