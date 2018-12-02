package com.meme.core.spring;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.meme.core.log.LogType;
import com.meme.core.service.LogService;
import com.meme.core.util.StringUtil;

/**
 * 自定义异常处理类
 * @author hzl
 *
 */
public class ExceptionResolver extends SimpleMappingExceptionResolver {

	@Resource
	private LogService logService;
	
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ex.printStackTrace(new PrintStream(baos));
		try {
			this.logService.log(request, LogType.EXCEPTION.getType(), LogType.EXCEPTION.getType(), baos.toString());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String viewName = determineViewName(ex, request);
		if (viewName != null) {
			if(this.isAjax(request)){
				PrintWriter writer=null;
				try {
					String errmsg=ex.getMessage();
					if(!StringUtil.isEmpty(errmsg)){
						response.setCharacterEncoding("UTF-8");
						response.setContentType("application/json;charset=utf-8");
						writer=response.getWriter();
						writer.write(errmsg);
						writer.flush();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					if(null!=writer) writer.close();
				}
				return null;
			}else{
				Integer statusCode = determineStatusCode(request, viewName);
				if (statusCode != null) {
					applyStatusCodeIfPossible(request, response, statusCode);
				}
				return getModelAndView(viewName, ex, request);
			}
		}
		return null;
	}
	
	/**
	 * 判断是否为ajax请求
	 * @param request
	 * @return
	 */
	public boolean isAjax(HttpServletRequest request){
		return request.getHeader("accept").indexOf("application/json")>=0||(request.getHeader("x-requested-with")!= null && request.getHeader("x-requested-with").indexOf("XMLHttpRequest")>=0);
	}
}
