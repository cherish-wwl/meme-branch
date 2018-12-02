package com.meme.qiniu.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

import org.apache.commons.codec.binary.Base64;

import com.meme.core.cache.DictCache;
import com.meme.core.cache.ParamsCache;
import com.meme.core.http.HttpRequestMethod;
import com.meme.core.http.HttpsTrustManager;
import com.meme.core.http.ResultMessage;
import com.meme.core.pojo.DictItemView;
import com.meme.core.util.StringUtil;
import com.qiniu.cdn.CdnManager;
import com.qiniu.cdn.CdnResult;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

/**
 * 七牛相关API
 * @author hzl
 *
 */
public class QiniuAPI {

	private String host;
	private boolean ssl=false;
	private String url;
	private Map<String,Object> params;
	private HttpRequestMethod method;
	private Map<String,String> requestProperty;
	private String charset="UTF-8";
	
	private QiniuAPI(ConnectionBuilder builder){
		this.host=builder.host;
		if(builder.ssl) this.ssl=builder.ssl;
		this.url=builder.url;
		this.params=builder.params;
		this.method=builder.method;
		this.requestProperty=builder.requestProperty;
		if(StringUtil.isAllNotEmpty(builder.charset)) this.charset=builder.charset;
	}
	
	/**
	 * 生成上传凭证
	 * @param bucket
	 * @return
	 */
	public static String getUploadToken(String bucket,String key){
		String accessKey = ParamsCache.get("QINIU_ACCESS_KEY").getValue();
		String secretKey = ParamsCache.get("QINIU_SECRET_KEY").getValue();
		Auth auth = Auth.create(accessKey, secretKey);
	    if(StringUtil.isEmpty(key)) {
	    	String upToken = auth.uploadToken(bucket);
		    return upToken;
	    }else{
	    	String upToken = auth.uploadToken(bucket,key);
		    return upToken;
	    }
	}
	/**
	 * 构造上传管理类
	 * @param zone
	 * @return
	 */
	public static UploadManager getUploadManager(Zone zone){
		//构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(Zone.autoZone());
		UploadManager uploadManager = new UploadManager(cfg);
		return uploadManager;
	}
	
	/**
	 * 构造空间管理类
	 * @param zone
	 * @return
	 */
	public static BucketManager getBucketManager(Zone zone){
		Configuration cfg=new Configuration(Zone.autoZone());
		String accessKey = ParamsCache.get("QINIU_ACCESS_KEY").getValue();
		String secretKey = ParamsCache.get("QINIU_SECRET_KEY").getValue();
		Auth auth = Auth.create(accessKey, secretKey);
		BucketManager bucketManager = new BucketManager(auth, cfg);
		return bucketManager;
	}
	
	/**
	 * 获取空间域名
	 * @param bucket	存储空间名称
	 * @return
	 */
	public static ResultMessage getBucketDomains(String bucket){
		ConnectionBuilder builder=new ConnectionBuilder();
		builder.host("http://api.qiniu.com");
		builder.url("/v6/domain/list?tbl=" + bucket);
		
		Map<String,String> properties=new LinkedHashMap<String, String>();
		properties.put("Content-Type", "application/x-www-form-urlencoded");
		//properties.put("Authorization", " QBox "+getManageAccessToken(builder));
		QiniuAPI api=builder.requestProperty(properties).build();
		api.requestProperty.put("Authorization", " QBox "+getManageAccessToken(api));
		
		ResultMessage result=request(api);
		if(null==result.getData()) return result;
		
		String[] domains=result.getData().toString().substring(1,result.getData().toString().length()-1).replace("\"", "").split(",");
		result.setData(domains);
		return result;
	}
	
	/**
	 * 获取所有存储空间
	 * @return
	 */
	public static ResultMessage getAllBuckets(){
		ConnectionBuilder builder=new ConnectionBuilder();
		builder.host("http://rs.qbox.me");
		builder.url("/buckets");
		
		Map<String,String> properties=new LinkedHashMap<String, String>();
		properties.put("Content-Type", "application/x-www-form-urlencoded");
		QiniuAPI api=builder.requestProperty(properties).build();
		api.requestProperty.put("Authorization", "QBox "+getManageAccessToken(api));
		
		ResultMessage result=request(api);
		
		return result;
	}
	
	/**
	 * 刷新文件链接
	 * @param bucket
	 * @param zone
	 * @param url
	 * @return
	 */
	public static Boolean flushCdnCache(String url){
		String accessKey = ParamsCache.get("QINIU_ACCESS_KEY").getValue();
		String secretKey = ParamsCache.get("QINIU_SECRET_KEY").getValue();
		Auth auth = Auth.create(accessKey, secretKey);
		CdnManager c = new CdnManager(auth);
		
		try {
			if(StringUtil.isAllNotEmpty(url)){
				String[] urls=new String[]{url};
				CdnResult.RefreshResult result = c.refreshUrls(urls);
				if(result.code==200) return true;
				return false;
			}
		} catch (QiniuException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 创建存储空间
	 * @param bucket	存储空间名称
	 * @param zone		z0-华东，z1-华北，z2-华南，na0-北美
	 * @param global
	 * @return
	 */
	public static ResultMessage createBucket(String bucket,String zone,boolean global){
		ConnectionBuilder builder=new ConnectionBuilder();
		builder.host("http://rs.qiniu.com");
		builder.url("/mkbucketv2/"+Base64.encodeBase64String(bucket.getBytes())+"/region/"+zone+"/global/"+global);
		builder.method(HttpRequestMethod.POST);
		
		Map<String,String> properties=new LinkedHashMap<String, String>();
		properties.put("Content-Type", "application/x-www-form-urlencoded");
		QiniuAPI api=builder.requestProperty(properties).build();
		api.requestProperty.put("Authorization", "  QBox "+getManageAccessToken(api));
		
		ResultMessage result=request(api);
		return result;
	}
	
	/**
	 * 删除存储空间
	 * @param bucket	存储空间名称
	 * @return
	 */
	public static ResultMessage deleteBucket(String bucket){
		ConnectionBuilder builder=new ConnectionBuilder();
		builder.host("http://rs.qiniu.com");
		builder.url("/drop/"+bucket);
		builder.method(HttpRequestMethod.POST);
		
		Map<String,String> properties=new LinkedHashMap<String, String>();
		properties.put("Content-Type", "application/x-www-form-urlencoded");
		QiniuAPI api=builder.requestProperty(properties).build();
		api.requestProperty.put("Authorization", " QBox "+getManageAccessToken(api));
		
		ResultMessage result=request(api);
		return result;
	}
	
	/**
	 * 获取管理凭证
	 * @param builder
	 * @return
	 */
	private static String getManageAccessToken(QiniuAPI api){
		String accessKey = ParamsCache.get("QINIU_ACCESS_KEY").getValue();
		String secretKey = ParamsCache.get("QINIU_SECRET_KEY").getValue();
		Auth auth = Auth.create(accessKey, secretKey);
		String access_token=auth.sign(getSignStr(api).toString().getBytes());
		return access_token;
	}
	
	/**
	 * 生成签名字符串
	 * @param builder
	 * @return
	 */
	private static String getSignStr(QiniuAPI api){
		StringBuffer signingStr=new StringBuffer("");
		signingStr.append(api.url).append("\n");
		if(null!=api.params&&api.params.size()>0){
			int i=0;
			for(Map.Entry<String, Object> entry:api.params.entrySet()){
				if(i==0) signingStr.append(entry.getKey()).append("=").append(entry.getValue().toString());
				else signingStr.append("&").append(entry.getKey()).append("=").append(entry.getValue().toString());
				i++;
			}
		}
		return signingStr.toString();
	}
	
	/**
	 * http请求
	 * @param builder
	 * @return
	 */
	private static ResultMessage request(QiniuAPI api){
		if(api.ssl){
			return sslRequest(api);
		}
		
		HttpURLConnection con=null;
		OutputStream out=null;
		try {
			if(api.method.getMethod().equals(HttpRequestMethod.GET.getMethod())) {
				con=(HttpURLConnection) new URL(api.host+getSignStr(api)).openConnection();
			}
			else {
				con=(HttpURLConnection) new URL(api.host+api.url).openConnection();
			}
			con.setRequestMethod(api.method.getMethod());
			con.setDoInput(true);
			con.setDoOutput(true);
			if(null!=api.requestProperty&&api.requestProperty.size()>0){
				for(Map.Entry<String,String> entry:api.requestProperty.entrySet()){
					con.setRequestProperty(entry.getKey(), entry.getValue());
				}
			}
			if(api.method.getMethod().equals(HttpRequestMethod.POST.getMethod())){
				out = con.getOutputStream();
				StringBuffer paramStr=new StringBuffer("");
				if(null!=api.params&&api.params.size()>0){
					int i=0;
					for(Map.Entry<String, Object> entry:api.params.entrySet()){
						if(i==0) paramStr.append(entry.getKey()).append("=").append(entry.getValue().toString());
						else paramStr.append("&").append(entry.getKey()).append("=").append(entry.getValue().toString());
						i++;
					}
				}
				if(StringUtil.isAllNotEmpty(paramStr.toString())) out.write(paramStr.toString().getBytes());
			}
			
			DictItemView item=DictCache.getDictItem("QINIU_HTTP_STATE_CODE", String.valueOf(con.getResponseCode()));
			String msg=null;
			if(con.getResponseCode()==200){
				InputStream es = con.getErrorStream();
				if(null==es){
					String str=getResponseData(con.getInputStream(), api.charset);
					if(null==item) msg="操作成功";
					else msg=item.getDictitemname();
					return ResultMessage.success(msg, str);
				}
			}else {
				if(null==item) msg="操作失败";
				else msg=item.getDictitemname();
				return ResultMessage.failed(msg);
			}
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
		return null;
	}
	
	/**
	 * https请求
	 * @param builder
	 * @return
	 */
	private static ResultMessage sslRequest(QiniuAPI api){
		HttpsURLConnection con=null;
		OutputStream out=null;
		
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			ctx.init(new KeyManager[0], new TrustManager[]{new HttpsTrustManager()}, new SecureRandom());
			SSLContext.setDefault(ctx);
			if(api.method.getMethod().equals(HttpRequestMethod.GET.getMethod())) {
				con=(HttpsURLConnection) new URL(api.host+getSignStr(api)).openConnection();
			}
			else {
				con=(HttpsURLConnection) new URL(api.host+api.url).openConnection(); 
			}
			con.setRequestMethod(api.method.getMethod());
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
			
			if(api.method.getMethod().equals(HttpRequestMethod.POST.getMethod())){
				out = con.getOutputStream();
				StringBuffer paramStr=new StringBuffer("");
				if(null!=api.params&&api.params.size()>0){
					int i=0;
					for(Map.Entry<String, Object> entry:api.params.entrySet()){
						if(i==0) paramStr.append(entry.getKey()).append("=").append(entry.getValue().toString());
						else paramStr.append("&").append(entry.getKey()).append("=").append(entry.getValue().toString());
						i++;
					}
				}
				if(StringUtil.isAllNotEmpty(paramStr.toString())) out.write(paramStr.toString().getBytes());
			}
			
			DictItemView item=DictCache.getDictItem("QINIU_HTTP_STATE_CODE", String.valueOf(con.getResponseCode()));
			String msg=null;
			if(con.getResponseCode()==200){
				InputStream es = con.getErrorStream();
				if(null==es){
					String str=getResponseData(con.getInputStream(), api.charset);
					if(null==item) msg="操作成功";
					else msg=item.getDictitemname();
					return ResultMessage.success(msg, str);
				}
			}else {
				if(null==item) msg="操作失败";
				else msg=item.getDictitemname();
				return ResultMessage.failed(msg);
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
		return null;
	}
	
	
	/**
	 * 获取存储空间外链域名
	 * @param bucket
	 * @return
	 */
	@Deprecated
	public static String[] getBucketDomains2(String bucket){
		HttpURLConnection con=null;
		String host="http://api.qiniu.com";
		String signingStr = "/v6/domain/list?tbl=" + bucket + "\n";
		try {
			String accessKey = ParamsCache.get("QINIU_ACCESS_KEY").getValue();
			String secretKey = ParamsCache.get("QINIU_SECRET_KEY").getValue();
			Auth auth = Auth.create(accessKey, secretKey);
			String access_token=auth.sign(signingStr.getBytes());
			
			con=(HttpURLConnection) new URL(host+signingStr).openConnection();
			con.setRequestMethod(HttpRequestMethod.GET.getMethod());
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			con.setRequestProperty("Authorization", " QBox "+access_token);
			
			if(con.getResponseCode()==200){
				InputStream es = con.getErrorStream();
				if(null==es){
					String str=getResponseData(con.getInputStream(), "UTF-8");
					String[] domains=str.substring(1,str.length()-1).replace("\"", "").split(",");
					return domains;
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
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
	
	public static class ConnectionBuilder{
		private String host;
		private boolean ssl=false;
		private String url;
		private Map<String,Object> params;
		private HttpRequestMethod method=HttpRequestMethod.GET;
		private Map<String,String> requestProperty;
		private String charset="UTF-8";
		
		public ConnectionBuilder(){
			
		}
		
		public ConnectionBuilder host(String host){
			this.host=host;
			return this;
		}
		
		public ConnectionBuilder ssl(boolean ssl){
			this.ssl=ssl;
			return this;
		}
		
		public ConnectionBuilder url(String url){
			this.url=url;
			return this;
		}
		
		public ConnectionBuilder params(Map<String,Object> params){
			this.params=params;
			return this;
		}
		
		public ConnectionBuilder method(HttpRequestMethod method){
			this.method=method;
			return this;
		}
		
		public ConnectionBuilder requestProperty(Map<String,String> requestProperty){
			this.requestProperty=requestProperty;
			return this;
		}
		
		public ConnectionBuilder charset(String charset){
			this.charset=charset;
			return this;
		}
		
		public QiniuAPI build(){
			return new QiniuAPI(this);
		}
	}
}
