package com.meme.member.interceptor;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.meme.core.util.JWTUtil;
import com.meme.core.util.StringUtil;
import com.meme.im.pojo.Member;
import com.meme.im.service.MemberService;
import com.meme.im.util.IMConstants;

/**
 * 拦截限制限时访客部分功能
 * @author hzl
 *
 */
public class RestrictInterceptor extends HandlerInterceptorAdapter{
	@Resource private MemberService memberService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(RequestMethod.OPTIONS.toString().equals(request.getMethod())){
			return true;
		}
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
		Map<String,Object> claims=JWTUtil.verifyToken(token);
		Object memberid=claims.get(IMConstants.COOKIE_MEMBER_KEY);
		Member member=this.memberService.selectByPrimaryKey(memberid);
		if(member.getMtype()==-1||member.getState()==0){
			this.output(request, response, request.getContextPath()+"/member/login","临时访客限制此类操作");
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
}
