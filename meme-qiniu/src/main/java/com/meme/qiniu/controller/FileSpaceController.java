package com.meme.qiniu.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.meme.core.base.BaseController;
import com.meme.core.base.IController;
import com.meme.core.cache.DictCache;
import com.meme.core.easyui.TreeNode;
import com.meme.core.http.ResultMessage;
import com.meme.core.log.LogType;
import com.meme.core.log.annotation.SysLog;
import com.meme.core.pojo.DictItemView;
import com.meme.core.util.StringUtil;
import com.meme.core.validator.ValidateBuilder;
import com.meme.core.validator.entity.ColumnRule;
import com.meme.qiniu.pojo.FileInfoExt;
import com.meme.qiniu.pojo.QiniuDir;
import com.meme.qiniu.service.QiniuDirService;
import com.meme.qiniu.util.QiniuAPI;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FileInfo;

/**
 * 图片空间管理器
 * @author hzl
 *
 */
@Controller
@RequestMapping("/file/space/")
public class FileSpaceController extends BaseController implements IController{
	
	@Resource private QiniuDirService qiniuDirService;

	@Override
	@RequestMapping(value="loadValidateRules")
	@ResponseBody
	public String loadValidationRules(){
		ValidateBuilder.clear();
		
		ColumnRule rule=new ColumnRule();
		rule.setColumn("dir");
		Map<String,String> ruleMap=new LinkedHashMap<String, String>();
		ruleMap.put("folder", "true");
		rule.setRules(ruleMap);
		ValidateBuilder.addRules(rule);
		
		String js=ValidateBuilder.createJsValidateRules(QiniuDir.class);
		return js;
	}
	
	@RequestMapping("index")
	public ModelAndView index(){
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("/filespace/list",model);
	}
	
	/**
	 * 图片空间选择图片
	 * @param type  radio,checkbox
	 * @return
	 */
	@RequestMapping("listSelectImages")
	public ModelAndView listSelectImages(String type){
		Map<String, Object> model = new HashMap<String, Object>();
		if(StringUtil.isEmpty(type)) type="radio";
		model.put("type", type);
		return new ModelAndView("/filespace/listSelectImages",model);
	}
	
	/**
	 * 七牛上传
	 * @param request
	 * @return
	 */
	@RequestMapping("upload")
	@ResponseBody
	@SysLog(event = "七牛上传",type=LogType.OTHERS)
	public String upload(HttpServletRequest request,String bucket,String prefix){
		if(StringUtil.isEmpty(bucket)) return JSONObject.toJSONString(ResultMessage.failed("请确认上传空间名"));
		UploadManager uploadManager = QiniuAPI.getUploadManager(Zone.autoZone());
		
		//默认不指定key的情况下，以文件内容的hash值作为文件名
		String key = null;
	    String upToken = QiniuAPI.getUploadToken(bucket,null);
		
		CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(request.getSession().getServletContext());
		if(multipartResolver.isMultipart(request)){
			MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
			Iterator<String> iter=multiRequest.getFileNames();
			if(iter.hasNext()){
				try {
					while(iter.hasNext()){
						MultipartFile file=multiRequest.getFile(iter.next());
						if(null!=file){
//							Response qiniu_response;
							if(StringUtil.isEmpty(prefix)) key=file.getOriginalFilename();
							else key=prefix+file.getOriginalFilename();
							uploadManager.put(file.getBytes(), key, upToken);
//							qiniu_response = uploadManager.put(file.getBytes(), key, upToken);
//							DefaultPutRet putRet = new Gson().fromJson(qiniu_response.bodyString(), DefaultPutRet.class);
//							Map<String,String> data=new HashMap<String, String>();
//							data.put("hash", putRet.hash);
//							data.put("key", putRet.key);
						}
					}
				} catch (QiniuException e) {
					e.printStackTrace();
					return JSONObject.toJSONString(ResultMessage.failed(e.error()));
				} catch (IOException e) {
					e.printStackTrace();
					return JSONObject.toJSONString(ResultMessage.failed(e.getMessage()));
				}
				return JSONObject.toJSONString(ResultMessage.success("上传成功"));
			}else  JSONObject.toJSONString(ResultMessage.failed("未检测到上传文件"));
		}else JSONObject.toJSONString(ResultMessage.failed("未检测到上传文件"));
		return null;
	}
	
	/**
	 * 文件列表
	 * @param bucket
	 * @param prefix
	 * @return
	 */
	@RequestMapping("file/list")
	@ResponseBody
	public ResultMessage filelist(String bucket,String prefix){
		if(StringUtil.isAllEmpty(bucket)) return ResultMessage.failed("桶空间名不能为空");
		if(StringUtil.isEmpty(prefix)) prefix="";
		ConcurrentHashMap<String,DictItemView> map=DictCache.getDictItemList("QINIU_STORAGE_NAME");
		if(null!=map&&map.size()>0){
			boolean flag=false;
			for(Map.Entry<String,DictItemView> entry:map.entrySet()){
				if(entry.getValue().getDictitemcode().equals(bucket)) {flag=true;break;}
			}
			if(!flag) return ResultMessage.failed("桶空间名有误");
		}else return ResultMessage.failed("字典缓存异常，请联系管理员刷新缓存或重启服务");
		
		ResultMessage result=QiniuAPI.getBucketDomains(bucket);
		String msg=result.getMessage();
		if(result.getState().equals("0")) return ResultMessage.failed("获取空间域名失败："+msg);
		String[] domains=(String[]) result.getData();
		
		BucketManager bucketManager = QiniuAPI.getBucketManager(Zone.autoZone());
		//每次迭代的长度限制，最大1000，推荐值 1000
		int limit = 1000;
		//指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
		String delimiter = "/";
		//列举空间文件列表
		BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(bucket, prefix, limit, delimiter);
		List<FileInfoExt> list=new ArrayList<FileInfoExt>();
		while (fileListIterator.hasNext()) {
			FileInfo[] items = fileListIterator.next();
			if(null!=items&&items.length>0){
				for (FileInfo item : items) {
					FileInfoExt ext=new FileInfoExt();
					ext.setEndUser(item.endUser);
					ext.setFsize(item.fsize);
					ext.setHash(item.hash);
					ext.setKey(item.key);
					ext.setMimeType(item.mimeType);
					ext.setPutTime(new Date(item.putTime));
					if(item.key.lastIndexOf("/")>=0) ext.setFilename(item.key.substring(item.key.lastIndexOf("/")+1,item.key.length()));
					else ext.setFilename(item.key);
					if(null!=domains) ext.setUrl("http://"+domains[0]+"/"+item.key);
					list.add(ext);
				}
			}
		}
		return ResultMessage.success("", list);
	}
	
	/**
	 * 七牛云存储文件目录列表
	 * @return
	 */
	@RequestMapping("dir/list")
	@ResponseBody
	public List<TreeNode> dirlist(String bucket,Long pid){
		if(null==pid) pid=0l;
		if(StringUtil.isEmpty(bucket)) return null;
		List<TreeNode> nodes=new ArrayList<TreeNode>();
		List<QiniuDir> list=this.qiniuDirService.selectChildren(bucket,pid);
		if(null!=list&&list.size()>0){
			for(QiniuDir obj:list){
				TreeNode node=new TreeNode();
				node.setId(obj.getId().toString());
				node.setText(obj.getDir());
				node.setState("closed");
				node.setAttributes(JSONObject.parseObject(JSONObject.toJSONString(obj)));
				nodes.add(node);
			}
		}
		return nodes;
	}
	
	@RequestMapping("editView")
	public ModelAndView editView(Long id,Long pid,String bucket){
		Map<String, Object> model = new HashMap<String, Object>();
		QiniuDir obj=null;
		if(null!=id&&id!=0l){
			obj=this.qiniuDirService.selectByPrimaryKey(id);
		}
		if(null!=obj){
			model.put("bucket", obj.getBucket());
			model.put("pid", obj.getPid());
			model.put("object", obj);
		}else{
			model.put("bucket", bucket);
			model.put("pid", pid);
		}
		return new ModelAndView("/filespace/edit",model);
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
	@SysLog(event = "创建目录",type=LogType.ADD)
	public ResultMessage add(HttpServletRequest request,QiniuDir object){
		return this.qiniuDirService.add(request, object);
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
	@SysLog(event = "重命名目录",type=LogType.UPDATE)
	public ResultMessage edit(HttpServletRequest request,QiniuDir object){
		return this.qiniuDirService.edit(request, object);
	}
	
	@RequestMapping("delete")
	@ResponseBody
	@SysLog(event = "删除目录",type=LogType.DELETE)
	public ResultMessage delete(@RequestParam("ids[]") List<Object> ids){
		return this.qiniuDirService.delete(ids);
	}
	
	@RequestMapping("deleteFile")
	@ResponseBody
	@SysLog(event = "删除文件",type=LogType.DELETE)
	public ResultMessage deleteFile(String bucket,@RequestParam("keys[]") List<String> keys) throws QiniuException{
		if(StringUtil.isAllEmpty(bucket)) return ResultMessage.failed("桶空间名不能为空");
		ConcurrentHashMap<String,DictItemView> map=DictCache.getDictItemList("QINIU_STORAGE_NAME");
		if(null!=map&&map.size()>0){
			boolean flag=false;
			for(Map.Entry<String,DictItemView> entry:map.entrySet()){
				if(entry.getValue().getDictitemcode().equals(bucket)) {flag=true;break;}
			}
			if(!flag) return ResultMessage.failed("桶空间名有误");
		}else return ResultMessage.failed("字典缓存异常，请联系管理员刷新缓存或重启服务");
		
		if(null!=keys&&keys.size()>0){
			BucketManager bucketManager = QiniuAPI.getBucketManager(Zone.autoZone());
			BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
			String[] strs = new String[keys.size()];
		    batchOperations.addDeleteOp(bucket, keys.toArray(strs));
		    bucketManager.batch(batchOperations);
//		    Response response = bucketManager.batch(batchOperations);
//		    BatchStatus[] batchStatusList = response.jsonToObject(BatchStatus[].class);
		}
		return ResultMessage.defaultSuccessMessage();
	}
}
