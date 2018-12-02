package com.meme.im.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.meme.core.base.BaseController;
import com.meme.core.cache.DictCache;
import com.meme.core.cache.ParamsCache;
import com.meme.core.easyui.Pagination;
import com.meme.core.http.ResultMessage;
import com.meme.core.log.LogType;
import com.meme.core.log.annotation.SysLog;
import com.meme.im.form.ImForm;
import com.meme.im.pojo.MemeHomePage;
import com.meme.im.service.MemeHomePageService;
import com.meme.qiniu.util.QiniuAPI;
import com.qiniu.common.Zone;
import com.qiniu.storage.UploadManager;

/**
 * 会员主页
 * @author hzl
 *
 */
@Controller
@RequestMapping("/im/homepage")
public class MemeHomePageController extends BaseController{
	
	@Resource private MemeHomePageService memeHomePageService;

	
	@RequestMapping("index")
	public ModelAndView index(String part){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("part", part);
		return new ModelAndView("/im/homepage/list",model);
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Pagination list(HttpServletRequest request,ImForm form) throws IllegalAccessException, InvocationTargetException{
		List<Map<String, Object>> list = this.memeHomePageService.selectByPaginationView(form);
		for(Map<String,Object> map:list) {
			if(map.get("mtype") != null) {
				map.put("mtypeText", DictCache.getDictItem("MEMBER_ACCOUNT_TYPE",map.get("mtype").toString()).getDictitemname());
			}
			if(map.get("type") != null) {
				map.put("typeText", DictCache.getDictItem("HOMEPAGE_TYPE",map.get("type").toString()).getDictitemname());
			}
		}
		int count=this.memeHomePageService.countView(form);
		Pagination pagination=new Pagination();
		pagination.setRows(list);
		pagination.setTotal(count);
		return pagination;
	}
	
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	@SysLog(event = "删除数据",type=LogType.DELETE)
	@Transactional
	public ResultMessage delete(@RequestParam("ids[]") List<Object> ids){
		this.memeHomePageService.batchDelete(ids);
		return ResultMessage.defaultSuccessMessage();
	}
	
	@RequestMapping("editView")
	public ModelAndView editView(String id) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		MemeHomePage obj=null;
		if(id!=null){
			obj=this.memeHomePageService.selectByPrimaryKey(id);
		}
		resultMap.put("object", obj);
		String bucket=ParamsCache.get("MEMBER_BUCKET").getValue();
		resultMap.put("bucket", bucket);
		resultMap.put("prefix", "meme/");
		ResultMessage result=QiniuAPI.getBucketDomains(bucket);
		if(!result.getState().equals("0")){
			String[] domains=(String[]) result.getData();
			resultMap.put("domain", domains[0]);
		}
		//加载kindeditor上传相关参数
		Long maxs=Long.valueOf(ParamsCache.get("KINDEDITOR_UPLOAD_SIZE").getValue());
		float maxsize=Math.round(maxs/1048576*100)/100;
		resultMap.put("sizeLimit", maxsize+"Mb");
		resultMap.put("uploadLimit", ParamsCache.get("KINDEDITOR_UPLOAD_NUM").getValue());
		return new ModelAndView("/im/homepage/edit",resultMap);
	}
	
	/**
	 * 修改
	 * @param request
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@ResponseBody
	@SysLog(event = "修改数据",type=LogType.UPDATE)
	public ResultMessage edit(HttpServletRequest request,MemeHomePage memeHomePage,MultipartFile cover_result,MultipartFile file_result) throws Exception{
		String bucket=ParamsCache.get("MEMBER_BUCKET").getValue();
		ResultMessage result=QiniuAPI.getBucketDomains(bucket);
		String msg=result.getMessage();
		if(result.getState().equals("0")) return ResultMessage.failed("获取七牛空间域名失败："+msg);
		String[] domains=(String[]) result.getData();
		String domain=null;
		if(null!=domains&&domains.length>0) domain=domains[0];
		String prefix = "crawler/";
		if(cover_result!=null && cover_result.getSize() > 0) {
			String hash=UUID.randomUUID().toString().replaceAll("-", "");
			String name = cover_result.getOriginalFilename();
			String suffix=name.substring(name.lastIndexOf(".")+1,name.length());
			UploadManager uploadManager = QiniuAPI.getUploadManager(Zone.autoZone());
			String upToken = QiniuAPI.getUploadToken(bucket,null);
			String key=prefix+hash+"."+suffix;
			uploadManager.put(cover_result.getBytes(), key, upToken);
			memeHomePage.setCover("http://"+domain+"/"+key);
		}
		if(file_result!=null && file_result.getSize() > 0) {
			String hash=UUID.randomUUID().toString().replaceAll("-", "");
			String name = file_result.getOriginalFilename();
			String suffix=name.substring(name.lastIndexOf(".")+1,name.length());
			UploadManager uploadManager = QiniuAPI.getUploadManager(Zone.autoZone());
			String upToken = QiniuAPI.getUploadToken(bucket,null);
			String key=prefix+hash+"."+suffix;
			uploadManager.put(file_result.getBytes(), key, upToken);
			memeHomePage.setFile("http://"+domain+"/"+key);
		}
		this.memeHomePageService.updateByPrimaryKeySelective(memeHomePage);
		return ResultMessage.defaultSuccessMessage();
	}
}