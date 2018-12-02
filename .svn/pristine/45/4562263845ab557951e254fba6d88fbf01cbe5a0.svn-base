package com.meme.core.jsp.filter;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.meme.core.util.Constants;

/**
 * jsp过滤器，限制未登录访问jsp文件
 * @author hzl
 *
 */
public class JspFilter implements Filter {

	private List<String> exclude_mapping;

	public void destroy() {
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest) arg0;
		HttpServletResponse response=(HttpServletResponse) arg1;
		String basepath=null;
		String url=request.getRequestURL().toString();
		//带端口号的basepath构建要加上端口号
		if(url.substring(url.indexOf(":")+1,url.length()).indexOf(":")>0){
			basepath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
		}else{
			basepath=request.getScheme()+"://"+request.getServerName()+request.getContextPath();
		}
		//访问首页不过滤
		if(url.equals(basepath+"/")){
			arg2.doFilter(arg0, arg1);
			return ;
		}
		if(exclude_mapping!=null&&exclude_mapping.size()>0){
			for(String s:exclude_mapping){
				//符合要求的单个jsp页面不过滤
				if(url.indexOf(basepath+s)!=-1){
					arg2.doFilter(arg0, arg1);
					return ;
				}
				//符合/XX/XX/../*格式的页面不过滤
				String requrl=url.substring(basepath.length());
				Pattern pa1 = Pattern.compile("(/)+([a-z]+/)+(\\*)$");;
				Matcher ma1= pa1.matcher(s);
				if(ma1.find()){
					String rurl = s.substring(0,s.indexOf("*"));
					if(requrl.indexOf(rurl)!=-1){
						arg2.doFilter(arg0, arg1);
						return ;
					}
				}
				//符合/XX/XX/../*/XX.jsp格式的页面不过滤
				Pattern pa2 = Pattern.compile("(/)+([a-z]+/)+(\\*)(/[a-z]+)(.)[a-z]+$");
				Matcher ma2= pa2.matcher(s);
				if(ma2.find()){
					String burl = s.substring(0,s.indexOf("*"));
					String aurl = s.substring(s.indexOf("*")+1,s.length());
					if(requrl.indexOf(burl)!=-1&&requrl.indexOf(aurl)!=-1){
						arg2.doFilter(arg0, arg1);
						return ;
					}					
				}
			}
		}
		Object obj=request.getSession().getAttribute(Constants.SESSION_USER);
		if(null==obj){
			java.io.PrintWriter out = response.getWriter();  
		    out.println("<html>");  
		    out.println("<script>");
		    out.println("window.open ('"+request.getContextPath()+"/system/index','_top')");  
		    out.println("</script>");  
		    out.println("</html>");
		}else{
			arg2.doFilter(arg0, arg1);
		}

		arg2.doFilter(arg0, arg1);
	}

	public void init(FilterConfig arg0) throws ServletException {
	}

	public List<String> getExclude_mapping() {
		return exclude_mapping;
	}

	public void setExclude_mapping(List<String> exclude_mapping) {
		this.exclude_mapping = exclude_mapping;
	}

}
