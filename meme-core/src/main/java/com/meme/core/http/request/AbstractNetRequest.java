package com.meme.core.http.request;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meme.core.http.HttpRequestMethod;
import com.meme.core.http.ResultMessage;
import com.meme.core.http.entity.RequestEntity;
import com.meme.core.util.HttpUtil;
import com.meme.core.util.StringUtil;

/**
 * http请求抽象类
 * @author hzl
 *
 */
public abstract class AbstractNetRequest implements IRequest {
	
	private static final Logger LOG = LoggerFactory.getLogger(AbstractNetRequest.class);

	private RequestEntity entity;

	@Override
	public ResultMessage request(RequestEntity entity) {
		this.entity = entity;
		ResultMessage result = null;
		HttpURLConnection con = null;
		try {
			con = this.getConnection();
			con.setRequestMethod(this.getEntity().getMethod().getMethod());
			con.setDoInput(true);
			con.setDoOutput(true);
			// 设置请求参数属性
			if (null != this.entity.getProperties() && this.entity.getProperties().size() > 0) {
				for (Map.Entry<String, String> entry : this.entity.getProperties().entrySet()) {
					con.setRequestProperty(entry.getKey(), entry.getValue());
				}
			}
			con.connect();
			// post请求时使用流输出
			if (this.entity.getMethod() == HttpRequestMethod.POST) {
				if(StringUtil.isAllNotEmpty(this.entity.getData().toString())){
					PrintWriter out = new PrintWriter(new OutputStreamWriter(con.getOutputStream(), this.entity.getCharset()));
					out.print(this.entity.getData().toString());
					out.flush();
					out.close();
				}
			}
			
			LOG.debug(this.entity.getUrl()+"请求响应码:"+con.getResponseCode());

			if (con.getResponseCode() == 200) {
				result=new ResultMessage();
				result.setState(String.valueOf(con.getResponseCode()));
				result.setData(HttpUtil.getResponseData(con.getInputStream(), this.entity.getCharset()));
			} else {
				result=new ResultMessage(String.valueOf(con.getResponseCode()),con.getResponseMessage());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if (null != con) con.disconnect();
		}
		return result;
	}

	public RequestEntity getEntity() {
		return entity;
	}

	public void setEntity(RequestEntity entity) {
		this.entity = entity;
	}
}