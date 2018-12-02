package com.meme.core.jsp.filter;

import java.io.IOException;
import javax.servlet.*;

/**
 * jsp过滤器，设置request编码
 * 
 * @author hzl
 * 
 */
public class CharsetFilter implements Filter {
	protected String encoding = null;
	protected FilterConfig filterConfig = null;

	public void destroy() {
		this.encoding = null;
		this.filterConfig = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (request.getCharacterEncoding() == null) {
			String encoding = getEncoding();
			if (encoding != null)
				request.setCharacterEncoding(encoding);// 设置request的编码
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		this.encoding = filterConfig.getInitParameter("encoding");
	}

	protected String getEncoding() {
		return (this.encoding);
	}
}
