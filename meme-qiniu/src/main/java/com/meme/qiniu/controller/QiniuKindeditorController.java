package com.meme.qiniu.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meme.core.base.BaseController;
import com.meme.core.cache.ParamsCache;
import com.meme.core.http.ResultMessage;
import com.meme.core.kindeditor.NameComparator;
import com.meme.core.kindeditor.SizeComparator;
import com.meme.core.kindeditor.TypeComparator;
import com.meme.core.log.LogType;
import com.meme.core.log.annotation.SysLog;
import com.meme.core.util.StringUtil;
import com.meme.qiniu.pojo.QiniuDir;
import com.meme.qiniu.service.QiniuDirService;
import com.meme.qiniu.util.QiniuAPI;
import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.model.FileInfo;

@Controller
@RequestMapping("/qiniu/kindeditor/")
public class QiniuKindeditorController extends BaseController{

	@Resource private QiniuDirService qiniuDirService;
	
	protected Map<String, Object> message(Integer state,String message){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", state);
		resultMap.put("message", message);
		return resultMap;
	}
	
	@RequestMapping("upload")
	@SysLog(event = "kindeditor上传到七牛空间",type=LogType.OTHERS)
	public void upload(HttpServletRequest request, HttpServletResponse response,String dir,String bucket) throws Exception{
		this.qiniuDirService.qiniu_upload(request, response, dir, bucket);
	}
	
	/**
	 * 文件列表
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("list")
	public void list(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		//排序形式，name or size or type
		String order = request.getParameter("order") != null ? request.getParameter("order").toLowerCase() : "name";
		String dir = new String(request.getParameter("dir").getBytes("iso-8859-1"), "utf-8");;
		
		String path = new String(request.getParameter("path").getBytes("iso-8859-1"), "utf-8");
		String bucket = request.getParameter("bucket");
		if(StringUtil.isEmpty(bucket)){
			out.println(JSON.toJSONString(this.message(1, "七牛存储空间名不能为空")));
			out.close();
			return ;
		}
		String type=ParamsCache.get("KINDEDITOR_UPLOAD_TYPE").getValue();
		if(StringUtil.isEmpty(type)){
			out.println(JSON.toJSONString(this.message(1, "kindeditor支持上传文件类型配置不存在，请检查配置")));
			out.close();
			return ;
		}
		JSONObject types=JSONObject.parseObject(type);
		List<QiniuDir> list=null;
		String prefix="";
		if(StringUtil.isEmpty(path)) {
			list=this.qiniuDirService.selectChildren(bucket,0l);
		}else{
			QiniuDir tmp=new QiniuDir();
			tmp.setFulldir(path);
			List<QiniuDir> parent=this.qiniuDirService.selectByEntity(tmp);
			if(null!=parent&&parent.size()>0){
				list=this.qiniuDirService.selectChildren(bucket, parent.get(0).getId());
				prefix=parent.get(0).getFulldir();
			}
		}
		//遍历目录取的文件信息
		List<Hashtable<String,Object>> fileList = new ArrayList<Hashtable<String,Object>>();
		if(null!=list&&list.size()>0){
			for(QiniuDir qd:list){
				Hashtable<String, Object> hash = new Hashtable<String, Object>();
				hash.put("is_dir", true);
				hash.put("has_file", true);
				hash.put("filesize", 0L);
				hash.put("is_photo", false);
				hash.put("filetype", "");
				hash.put("filename", qd.getDir());
				hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(qd.getAddtime()));
				fileList.add(hash);
			}
		}
		
		ResultMessage rm=QiniuAPI.getBucketDomains(bucket);
		String msg=rm.getMessage();
		if(rm.getState().equals("0")) {
			out.println(JSON.toJSONString(this.message(1, "获取空间域名失败："+msg)));
			out.close();
			return ;
		}
		String[] domains=(String[]) rm.getData();
		
		BucketManager bucketManager = QiniuAPI.getBucketManager(Zone.autoZone());
		//每次迭代的长度限制，最大1000，推荐值 1000
		int limit = 1000;
		//指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
		String delimiter = "/";
		//列举空间文件列表
		BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(bucket, prefix, limit, delimiter);
		while (fileListIterator.hasNext()) {
			FileInfo[] items = fileListIterator.next();
			if(null!=items&&items.length>0){
				for (FileInfo item : items) {
					String fileExt = item.key.substring(item.key.lastIndexOf(".") + 1).toLowerCase();
					String fileName=item.key.substring(item.key.lastIndexOf("/")+1,item.key.length());
					Hashtable<String, Object> hash = new Hashtable<String, Object>();
					hash.put("is_dir", false);
					hash.put("has_file", false);
					hash.put("filesize", item.fsize);
					hash.put("is_photo", Arrays.<String>asList(types.get(dir).toString().toLowerCase().split(",")).contains(fileExt));
					hash.put("filetype", fileExt);
					hash.put("filename", fileName);
					hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(item.putTime)));
					fileList.add(hash);
				}
			}
		}
		
		if ("size".equals(order)) {
			Collections.sort(fileList, new SizeComparator());
		} else if ("type".equals(order)) {
			Collections.sort(fileList, new TypeComparator());
		} else {
			Collections.sort(fileList, new NameComparator());
		}
		
		String currentDirPath = path;
		String moveupDirPath = "";
		if (!"".equals(path)) {
			String str = currentDirPath.substring(0, currentDirPath.length() - 1);
			moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/") + 1) : "";
		}
		
		JSONObject result = new JSONObject();
		result.put("moveup_dir_path", moveupDirPath);
		result.put("current_dir_path", currentDirPath);
		result.put("current_url", "http://"+domains[0]+"/"+currentDirPath);
		result.put("total_count", fileList.size());
		result.put("file_list", fileList);

		String str=result.toJSONString();
		out.println(str);
		out.close();
	}
}
