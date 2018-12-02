package com.meme.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meme.core.http.HttpRequestMethod;
import com.meme.core.http.HttpsTrustManager;

/**
 * http工具类
 * @author hzl
 *
 */
public class HttpUtil {

	private static final Logger LOG = LoggerFactory.getLogger(HttpUtil.class);
	
	/**
	 * Https请求，只支持get，post请求，默认content-type="text/html",charset="UTF-8"
	 * @param url
	 * @param params
	 * @param method
	 * @return
	 * @throws IOException
	 */
	@Deprecated
	public static String doHttpsRequest(String url,Map<String,String> params,String method){
		return doHttpsRequest(url, params, method,"text/html","UTF-8");
	}
	
	/**
	 * Https请求，只支持get，post请求
	 * @param url
	 * @param params
	 * @param method
	 * @param contentType
	 * @param charset
	 * @return
	 */
	@Deprecated
	public static String doHttpsRequest(String url,Map<String,String> params,String method,String contentType,String charset){
		HttpsURLConnection con=null;
		OutputStream out=null;
		String responseStr=null;
		try {
			
			SSLContext ctx = SSLContext.getInstance("TLS");
			ctx.init(new KeyManager[0], new TrustManager[]{new HttpsTrustManager()}, new SecureRandom());
			SSLContext.setDefault(ctx);
			if(method.equals(HttpRequestMethod.GET.getMethod())){
				StringBuffer content =new StringBuffer("");
				if(null!=params&&params.size()>0){
					int i=1;
					for(Map.Entry<String, String> entry:params.entrySet()){
						if(i!=params.size()) content.append(entry.getKey()+"="+entry.getValue()+"&");
						else content.append(entry.getKey()+"="+entry.getValue());
						i++;
					}
				}
				LOG.debug("(GET)API调用地址："+url+"?"+content.toString());
				con=(HttpsURLConnection) new URL(url+"?"+content.toString()).openConnection();
				con.setRequestMethod(method);
				con.setDoInput(true);
				con.setDoOutput(true);
				con.setRequestProperty("Accept", "text/xml,text/html");
				con.setRequestProperty("User-Agent", "stargate");
				con.setRequestProperty("Content-Type", contentType+";charset="+charset);
				
				con.setHostnameVerifier(new HostnameVerifier() {
	                @Override
	                public boolean verify(String hostname, SSLSession session) {
	                    return true;
	                }
	            });
			}else if(method.equals(HttpRequestMethod.POST.getMethod())){
				LOG.debug("(POST)API调用地址："+url);
				con=(HttpsURLConnection) new URL(url).openConnection();
				con.setRequestMethod(method);
				con.setDoInput(true);
				con.setDoOutput(true);
				con.setRequestProperty("Accept", "text/xml,text/html");
				con.setRequestProperty("User-Agent", "stargate");
				con.setRequestProperty("Content-Type", contentType+";charset="+charset);
				
				con.setHostnameVerifier(new HostnameVerifier() {
	                @Override
	                public boolean verify(String hostname, SSLSession session) {
	                    return true;
	                }
	            });
				
				out = con.getOutputStream();
	            out.write(params.get("data").getBytes());
			}
			
			if(con.getResponseCode()==200){
				InputStream es = con.getErrorStream();
	            if(null==es){
	                responseStr = getResponseData(con.getInputStream(), charset);
	            }else{
	                String msg = getResponseData(es, charset);
	                es.close();
	                if (StringUtil.isEmpty(msg)) {
	                    throw new IOException(con.getResponseCode() + ":" + con.getResponseMessage());
	                } else {
	                    throw new IOException(msg);
	                }
	            }
			}
            
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(null!=out){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(null!=con) con.disconnect();
		}
		return responseStr;
	}
	
	/**
	 * 获取当前web应用网址
	 * @param request
	 * @return
	 */
	public static String getBasePath(HttpServletRequest request){
		return request.getScheme()+"://"+request.getServerName()+(request.getServerPort()!=80?(":"+request.getServerPort()):"")+request.getContextPath()+"/";
	}

	/**
	 * 打印方式返回响应数据
	 * @param response
	 */
	public static void printResponseData(HttpServletResponse response,String responseStr,String contentType){
		PrintWriter out = null;
		try {
			if(StringUtil.isEmpty(contentType)) response.setContentType("text/html;charset=UTF-8");
			else response.setContentType(contentType);
		    response.setHeader("Pragma",   "no-cache");
		    response.setDateHeader("Expires",   0);
			out = response.getWriter();
			out.print(responseStr);
			out.flush(); 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != out) {
				try {
					out.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 获取响应数据
	 * @param stream
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static String getResponseData(InputStream stream,String charset) throws IOException{
		BufferedReader reader=null;
		try {
			reader = new BufferedReader(new InputStreamReader(stream, charset));
	    	StringWriter writer = new StringWriter();
	    	char[] chars = new char[256];  
	        int count = 0;  
	        while ((count = reader.read(chars)) > 0) {  
	            writer.write(chars, 0, count);  
	        }
	        return writer.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(null!=reader) reader.close();
			if(null!=stream) stream.close();
		}
		return null;
	}
	
	/**
	 * 获取请求参数键值对
	 * @param request
	 * @return
	 */
	public static Map<String,String> getRequestParams(HttpServletRequest request){
		Map<String,String> params=new LinkedHashMap<String, String>();
		Enumeration<String> names=request.getParameterNames();
		while(names.hasMoreElements()){
			String name=names.nextElement();
			String value=request.getParameter(name);
			params.put(name, value);
		}
		return params;
	}
	
	/**
	 * map转请求字符串
	 * @param params
	 * @return
	 */
	public static String Map2QueryString(Map<String,String> params){
		StringBuffer str=new StringBuffer();
		if(null!=params&&params.size()>0){
			for(Map.Entry<String, String> entry:params.entrySet()){
				str.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
			}
		}
		if(str.length()>0) return str.substring(0,str.length()-1);
		return null;
	}
}
