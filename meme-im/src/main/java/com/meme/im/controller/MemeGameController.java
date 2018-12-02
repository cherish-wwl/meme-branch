package com.meme.im.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.meme.core.base.BaseController;
import com.meme.core.cache.ParamsCache;
import com.meme.core.easyui.Pagination;
import com.meme.core.http.ResultMessage;
import com.meme.core.log.LogType;
import com.meme.core.log.annotation.SysLog;
import com.meme.core.util.IDGenerator;
import com.meme.core.util.StringUtil;
import com.meme.im.form.ImForm;
import com.meme.im.pojo.MemeContent;
import com.meme.im.service.MemeContentService;
import com.meme.qiniu.util.QiniuAPI;
import com.qiniu.common.Zone;
import com.qiniu.storage.UploadManager;

@Controller
@RequestMapping("/im/game/")
public class MemeGameController extends BaseController{
	@Resource 
	private MemeContentService memeContentService;
	
	
	@RequestMapping("home")
	public ModelAndView home(String columnid) {
		//System.out.println("1:"+type);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("columnid", columnid);
		return new ModelAndView("/im/game/home", model);
	}
	
	@RequestMapping("homelist")
	@ResponseBody
	public Pagination homelist(ImForm form) {
		List<Map<String,Object>> list=this.memeContentService.selectByPaginationSectionView(form);
		int count=this.memeContentService.countSelctionView(form);
		Pagination pagination=new Pagination(list,count);
		return pagination;
	}
	
	@RequestMapping("editView")
	public ModelAndView editView(Long id,Long columnid) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		MemeContent obj = null;
		if(id != null){
			obj=this.memeContentService.selectByPrimaryKey(id);
		}else {
			obj = new MemeContent();
			obj.setColumnid(columnid);
		}
		resultMap.put("object", obj);
		return new ModelAndView("/im/game/edit",resultMap);
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
	@SysLog(event = "新增栏目内容",type=LogType.ADD)
	public ResultMessage add(HttpServletRequest request,MemeContent memeContent){
		CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(request.getSession().getServletContext());
		if(multipartResolver.isMultipart(request)){
			MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
			Iterator<String> it=multiRequest.getFileNames();
			if(it.hasNext()){
				MultipartFile file=multiRequest.getFile(it.next());
				String name=file.getOriginalFilename();
				String suffix=name.substring(name.lastIndexOf(".")+1,name.length());
				try {
					if(null!=file.getBytes()&&file.getBytes().length>0){
						String bucket=ParamsCache.get("MEMBER_BUCKET").getValue();
						
						ResultMessage result=QiniuAPI.getBucketDomains(bucket);
						String msg=result.getMessage();
						if(result.getState().equals("0")) return ResultMessage.failed("获取七牛空间域名失败："+msg);
						String[] domains=(String[]) result.getData();
						String domain=null;
						if(null!=domains&&domains.length>0) domain=domains[0];
						//非覆盖上传
						String prefix=ParamsCache.get("MEMBER_COVER")==null?"":ParamsCache.get("MEMBER_COVER").getValue();
						String hash=UUID.randomUUID().toString().replaceAll("-", "");
						String key = null;
						UploadManager uploadManager = QiniuAPI.getUploadManager(Zone.autoZone());
						String upToken = QiniuAPI.getUploadToken(bucket,null);
				    		if(StringUtil.isEmpty(prefix)) key=hash+"."+suffix;
						else key=(prefix.endsWith("/")?prefix:(prefix+"/"))+hash+"."+suffix;
						uploadManager.put(file.getBytes(), key, upToken);
						memeContent.setCover("http://"+domain+"/"+key);
						memeContent.setContentid(IDGenerator.generateId());
						memeContent.setAddtime(new Date());
						this.memeContentService.insertSelective(memeContent);
						return ResultMessage.defaultSuccessMessage();
						
					}else return ResultMessage.failed("请选择背景图文件上传");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return ResultMessage.defaultFaileMessage();
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
	@SysLog(event = "修改栏目内容",type=LogType.UPDATE)
	public ResultMessage edit(HttpServletRequest request,MemeContent memeContent){
		CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(request.getSession().getServletContext());
		if(multipartResolver.isMultipart(request)){
			MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
			Iterator<String> it=multiRequest.getFileNames();
			if(it.hasNext()){
				MultipartFile file=multiRequest.getFile(it.next());
				String name=file.getOriginalFilename();
				String suffix=name.substring(name.lastIndexOf(".")+1,name.length());
				try {
					if(null!=file.getBytes()&&file.getBytes().length>0){
						String bucket=ParamsCache.get("MEMBER_BUCKET").getValue();
						
						ResultMessage result=QiniuAPI.getBucketDomains(bucket);
						String msg=result.getMessage();
						if(result.getState().equals("0")) return ResultMessage.failed("获取七牛空间域名失败："+msg);
						String[] domains=(String[]) result.getData();
						String domain=null;
						if(null!=domains&&domains.length>0) domain=domains[0];
						//非覆盖上传
						String prefix=ParamsCache.get("MEMBER_COVER")==null?"":ParamsCache.get("MEMBER_COVER").getValue();
						String hash=UUID.randomUUID().toString().replaceAll("-", "");
						String key = null;
						UploadManager uploadManager = QiniuAPI.getUploadManager(Zone.autoZone());
						String upToken = QiniuAPI.getUploadToken(bucket,null);
				    		if(StringUtil.isEmpty(prefix)) key=hash+"."+suffix;
						else key=(prefix.endsWith("/")?prefix:(prefix+"/"))+hash+"."+suffix;
						uploadManager.put(file.getBytes(), key, upToken);
						memeContent.setCover("http://"+domain+"/"+key);
					}
					memeContent.setPublishtime(new Date());
					this.memeContentService.updateByPrimaryKeySelective(memeContent);
					return ResultMessage.defaultSuccessMessage();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return ResultMessage.defaultFaileMessage();
	}
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	@SysLog(event = "删除游戏",type=LogType.DELETE)
	@Transactional
	public ResultMessage delete(@RequestParam("ids[]") List<Object> ids){
		this.memeContentService.batchDelete(ids);
		return ResultMessage.defaultSuccessMessage();
	}
	
}
