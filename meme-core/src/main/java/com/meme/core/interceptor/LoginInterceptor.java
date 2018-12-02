package com.meme.core.interceptor;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.meme.core.pojo.LoginAccountInfoView;
import com.meme.core.service.LoginAccountService;
import com.meme.core.util.Constants;
import com.meme.core.util.JWTUtil;
import com.meme.core.util.StringUtil;

/**
 * 登录拦截器
 * @author hzl
 *
 */
public class LoginInterceptor extends HandlerInterceptorAdapter{
	
	@Resource private LoginAccountService loginAccountService;
	
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
//		Cookie[] cookies=request.getCookies();
//		String token=null;
//		if(null!=cookies&&cookies.length>0){
//			for(Cookie co: cookies){
//				if(co.getName().equals("token")){
//					token=co.getValue();
//					break;
//				}
//			}
//		}
//		if(StringUtil.isEmpty(token)) {
//			this.output(request, response, request.getContextPath()+"/system/index","");
//			return false;
//		}
//		Map<String,Object> claims=JWTUtil.verifyToken(token);
//		if(claims==null) {
//			this.output(request, response, request.getContextPath()+"/system/index","");
//			return false;
//		}
//		Object loginid=claims.get("loginid");
//		if(loginid!=null) {
//			Object obj=request.getSession().getAttribute(Constants.SESSION_USER);
//			if(null==obj){
//				List<LoginAccountInfoView> list=this.loginAccountService.isExist(null, Long.valueOf(String.valueOf(loginid)));
//				if(null==list||list.size()==0) {
//					this.output(request, response, request.getContextPath()+"/system/index","");
//					return false;
//				}
//				request.getSession().setAttribute(Constants.SESSION_USER, list.get(0));
//			}
//			return true;
//		}
		
		
		Object obj=request.getSession().getAttribute(Constants.SESSION_USER);
		if(null==obj) {
			this.output(request, response, request.getContextPath()+"/system/index","");
			return false;
		}
		LoginAccountInfoView user=(LoginAccountInfoView) obj;
		if(user.getState()==1) {
			this.output(request, response, request.getContextPath()+"/system/logout.do","账号已被禁用，请联系管理员解禁！");
			return false;
		}
		
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
