package com.meme.im.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.meme.core.base.BaseController;
import com.meme.core.cache.ParamsCache;
import com.meme.core.easyui.Pagination;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.core.log.LogType;
import com.meme.core.log.annotation.SysLog;
import com.meme.core.util.IDGenerator;
import com.meme.im.pojo.MemeCrawler;
import com.meme.im.pojo.MemeHomePage;
import com.meme.im.service.MemberService;
import com.meme.im.service.MemeCrawlerService;
import com.meme.im.service.MemeHomePageService;
import com.meme.im.service.impl.MemeCrawlerServiceImpl;
import com.meme.qiniu.util.QiniuAPI;
import com.qiniu.common.Zone;
import com.qiniu.storage.UploadManager;

/**
 * 爬虫数据管理器
 * @author hzl
 *
 */
@Controller
@RequestMapping("/im/data")
public class MemeCrawlerController extends BaseController{
	
	@Resource private MemeCrawlerService memeCrawlerDataService;
	@Resource private MemberService memberService;
	@Resource private MemeHomePageService memeHomePageService;

	@RequestMapping("index")
	public ModelAndView index(){
		Map<String, Object> model = new HashMap<String, Object>();
		
		return new ModelAndView("/im/data/list",model);
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Pagination list(HttpServletRequest request,Form form) throws IllegalAccessException, InvocationTargetException{
		List<MemeCrawler> list=this.memeCrawlerDataService.selectByPagination(request,form);
		int count=this.memeCrawlerDataService.count(request,form);
		Pagination pagination=new Pagination();
		pagination.setRows(list);
		pagination.setTotal(count);
		
		return pagination;
	}
	
	@RequestMapping("editView")
	public ModelAndView editView(String id) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		MemeCrawler obj=null;
		if(id!=null){
			obj=this.memeCrawlerDataService.selectByPrimaryKey(id);
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
		return new ModelAndView("/im/data/edit",resultMap);
	}
	
	/**
	 * 增加
	 * @param request
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("add")
	@ResponseBody
	@SysLog(event = "新增数据",type=LogType.ADD)
	public ResultMessage add(HttpServletRequest request,MemeCrawler memeCrawler,MultipartFile cover_result1,MultipartFile cover_result2,MultipartFile cover_result3,MultipartFile file_result) throws Exception{
		memeCrawler.setId(String.valueOf(IDGenerator.generateId()));
		String bucket=ParamsCache.get("MEMBER_BUCKET").getValue();
		ResultMessage result=QiniuAPI.getBucketDomains(bucket);
		String msg=result.getMessage();
		if(result.getState().equals("0")) return ResultMessage.failed("获取七牛空间域名失败："+msg);
		String[] domains=(String[]) result.getData();
		String domain=null;
		if(null!=domains&&domains.length>0) domain=domains[0];
		String prefix = "crawler/";
		StringBuffer buf = new StringBuffer();
		if(cover_result1!=null && cover_result1.getSize() > 0) {
			String hash=UUID.randomUUID().toString().replaceAll("-", "");
			String name = cover_result1.getOriginalFilename();
			String suffix=name.substring(name.lastIndexOf(".")+1,name.length());
			UploadManager uploadManager = QiniuAPI.getUploadManager(Zone.autoZone());
			String upToken = QiniuAPI.getUploadToken(bucket,null);
			String key=prefix+hash+"."+suffix;
			uploadManager.put(cover_result1.getBytes(), key, upToken);
			buf.append("http://"+domain+"/"+key).append(";");
		}
		if(cover_result2!=null && cover_result2.getSize() > 0) {
			String hash=UUID.randomUUID().toString().replaceAll("-", "");
			String name = cover_result2.getOriginalFilename();
			String suffix=name.substring(name.lastIndexOf(".")+1,name.length());
			UploadManager uploadManager = QiniuAPI.getUploadManager(Zone.autoZone());
			String upToken = QiniuAPI.getUploadToken(bucket,null);
			String key=prefix+hash+"."+suffix;
			uploadManager.put(cover_result2.getBytes(), key, upToken);
			buf.append("http://"+domain+"/"+key).append(";");
		}
		if(cover_result3!=null && cover_result3.getSize() > 0) {
			String hash=UUID.randomUUID().toString().replaceAll("-", "");
			String name = cover_result3.getOriginalFilename();
			String suffix=name.substring(name.lastIndexOf(".")+1,name.length());
			UploadManager uploadManager = QiniuAPI.getUploadManager(Zone.autoZone());
			String upToken = QiniuAPI.getUploadToken(bucket,null);
			String key=prefix+hash+"."+suffix;
			uploadManager.put(cover_result3.getBytes(), key, upToken);
			buf.append("http://"+domain+"/"+key).append(";");
		}
		if(buf.length()>0) {
			memeCrawler.setCover(buf.delete(buf.length()-1, buf.length()).toString());
		}
		if(file_result!=null && file_result.getSize() > 0) {
			String hash=UUID.randomUUID().toString().replaceAll("-", "");
			String name = file_result.getOriginalFilename();
			String suffix=name.substring(name.lastIndexOf(".")+1,name.length());
			UploadManager uploadManager = QiniuAPI.getUploadManager(Zone.autoZone());
			String upToken = QiniuAPI.getUploadToken(bucket,null);
			String key=prefix+hash+"."+suffix;
			uploadManager.put(file_result.getBytes(), key, upToken);
			memeCrawler.setFile("http://"+domain+"/"+key);
		}
		memeCrawler.setType(2);
		this.memeCrawlerDataService.insertSelective(memeCrawler);
		return ResultMessage.defaultSuccessMessage();
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
	public ResultMessage edit(HttpServletRequest request,MemeCrawler memeCrawler,MultipartFile cover_result,MultipartFile file_result) throws Exception{
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
			memeCrawler.setCover("http://"+domain+"/"+key);
		}
		if(file_result!=null && file_result.getSize() > 0) {
			String hash=UUID.randomUUID().toString().replaceAll("-", "");
			String name = file_result.getOriginalFilename();
			String suffix=name.substring(name.lastIndexOf(".")+1,name.length());
			UploadManager uploadManager = QiniuAPI.getUploadManager(Zone.autoZone());
			String upToken = QiniuAPI.getUploadToken(bucket,null);
			String key=prefix+hash+"."+suffix;
			uploadManager.put(file_result.getBytes(), key, upToken);
			memeCrawler.setFile("http://"+domain+"/"+key);
		}
		this.memeCrawlerDataService.updateByPrimaryKeySelective(memeCrawler);
		return ResultMessage.defaultSuccessMessage();
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
		this.memeCrawlerDataService.batchDelete(ids);
		return ResultMessage.defaultSuccessMessage();
	}
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("crawler")
	@ResponseBody
	public ResultMessage crawler(){
		if(MemeCrawlerServiceImpl.flag) {
			return ResultMessage.success("爬虫任务正在执行中");
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					memeCrawlerDataService.aTask();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
		}).start();
		return ResultMessage.success("已开始执行爬虫任务");
	}
	
	@RequestMapping("addHyzy")
	public ModelAndView addHyzy(String id) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("id", id);
		return new ModelAndView("/im/data/addHyzy",resultMap);
	}
	
	@RequestMapping("doAddHyzy")
	@ResponseBody
	public ResultMessage addHyzy(HttpServletRequest request,String id,Integer type) throws Exception{
		String[] nums = request.getParameterValues("num");
		MemeCrawler memeCrawler = memeCrawlerDataService.selectByPrimaryKey(id);
		List<Long> list = memberService.selectByNums(nums);
		List<MemeHomePage> pages = new ArrayList<MemeHomePage>();
		for(Long memberid:list) {
			MemeHomePage memeHomePage = new MemeHomePage();
			BeanUtils.copyProperties(memeCrawler, memeHomePage);
			memeHomePage.setId(String.valueOf(IDGenerator.generateId()));
			memeHomePage.setType(type);
			memeHomePage.setPart(1);
			memeHomePage.setMemberid(memberid);
			memeHomePage.setInsert_date(null);
			pages.add(memeHomePage);
		}
		if(pages.size()>0) {
			memeHomePageService.batchInsert(pages);
		}
		return ResultMessage.defaultSuccessMessage(); 
	}
}