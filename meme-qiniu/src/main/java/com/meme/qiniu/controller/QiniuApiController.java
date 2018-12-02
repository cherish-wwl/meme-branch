package com.meme.qiniu.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.meme.core.base.BaseController;
import com.meme.core.cache.ParamsCache;
import com.meme.core.http.ResultMessage;
import com.meme.core.util.StringUtil;
import com.meme.qiniu.util.QiniuAPI;

@Controller
@RequestMapping("/qiniu/")
public class QiniuApiController extends BaseController{

	/**
	 * 生成上传凭证
	 * @return
	 */
	@RequestMapping("getUptoken")
	@ResponseBody
	public Object getUptoken(String bucket,HttpServletResponse response,@RequestHeader(value="Origin") String Origin){
		response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		if(StringUtil.isEmpty(bucket)){
			bucket=ParamsCache.get("MEMBER_BUCKET").getValue();
		}
		
		String upToken = QiniuAPI.getUploadToken(bucket,null);
		JSONObject token=new JSONObject();
		token.put("uptoken", upToken);
		return token;
	}
	
	/**
	 * 获取模块域名
	 * @return
	 */
	@RequestMapping("getBucketDomain")
	@ResponseBody
	public Object getBucketDomain(String bucket,HttpServletResponse response,@RequestHeader(value="Origin") String Origin){
		response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		if(StringUtil.isEmpty(bucket)){
			bucket=ParamsCache.get("MEMBER_BUCKET").getValue();
		}
		ResultMessage result=QiniuAPI.getBucketDomains(bucket);
		String msg=result.getMessage();
		if(result.getState().equals("0")) return ResultMessage.failed("获取七牛空间域名失败："+msg);
		String[] domains=(String[]) result.getData();
		String domain=null;
		if(null!=domains&&domains.length>0) domain=domains[0];
		JSONObject obj=new JSONObject();
		obj.put("domain", domain);
		return obj;
	}
}
