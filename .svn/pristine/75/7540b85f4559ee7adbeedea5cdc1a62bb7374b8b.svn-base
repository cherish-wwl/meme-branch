package com.meme.qiniu.service.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meme.core.base.AbstractService;
import com.meme.core.cache.ParamsCache;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.core.mybatis.MapperHelper;
import com.meme.core.util.DateUtil;
import com.meme.core.util.IDGenerator;
import com.meme.core.util.StringUtil;
import com.meme.core.util.TreeUtil;
import com.meme.qiniu.dao.QiniuDirMapper;
import com.meme.qiniu.pojo.QiniuDir;
import com.meme.qiniu.service.QiniuDirService;
import com.meme.qiniu.util.QiniuAPI;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FileInfo;

@Service("QiniuDirService")
@Transactional
public class QiniuDirServiceImpl extends AbstractService implements QiniuDirService {

	@Resource private QiniuDirMapper mapper;

	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(QiniuDir record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(QiniuDir record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public QiniuDir selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(QiniuDir record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(QiniuDir record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QiniuDir> selectAll(){
		return (List<QiniuDir>) MapperHelper.toBeanList(this.mapper.selectAll(QiniuDir.class), QiniuDir.class);
	}
	
	public void buildSearchCriterion(Class<?> entityClass, Form form) {
		super.buildSearchCriterion(entityClass, form);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QiniuDir> selectByPagination(Form form) {
		this.buildSearchCriterion(QiniuDir.class, form);
		this.buildOrderByCriterion(QiniuDir.class, form);
		this.buildLimitCriterion(QiniuDir.class, form);
		return (List<QiniuDir>) MapperHelper.toBeanList(this.mapper.selectByPagination(QiniuDir.class,this.getList()), QiniuDir.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(QiniuDir.class, form);
		return this.mapper.count(QiniuDir.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QiniuDir> selectByEntity(QiniuDir record) {
		return (List<QiniuDir>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), QiniuDir.class);
	}

	@Override
	public int batchInsert(List<QiniuDir> record) {
		return this.mapper.batchInsert(record,QiniuDir.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,QiniuDir.class);
	}

	@Override
	public int batchUpdate(List<QiniuDir> record) {
		return this.mapper.batchUpdate(record,QiniuDir.class);
	}

	@Override
	public List<QiniuDir> selectChildren(String bucket,Long pid) {
		return this.mapper.selectChildren(bucket,pid);
	}

	@Override
	public ResultMessage add(HttpServletRequest request, QiniuDir record) {
		if(StringUtil.isEmpty(record.getBucket())) return ResultMessage.failed("存储桶空间名不能为空");
		if(null==record.getPid()) record.setPid(0l);
		if(record.getPid()!=0l){
			QiniuDir parent=this.selectByPrimaryKey(record.getPid());
			if(null==parent) record.setPid(0l);
			else record.setFulldir(parent.getFulldir()+record.getDir()+"/");
		}
		if(record.getPid()==0l) record.setFulldir(record.getDir()+"/");
		
		QiniuDir tmp=new QiniuDir();
		tmp.setBucket(record.getBucket());
		tmp.setPid(record.getPid());
		tmp.setDir(record.getDir());
		List<QiniuDir> list=this.selectByEntity(tmp);
		if(null!=list&&list.size()>0) return ResultMessage.failed("文件夹名冲突");
		
		long id=IDGenerator.generateId();
		record.setId(id);
		this.insertSelective(record);
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage edit(HttpServletRequest request, QiniuDir record) {
		if(null==record.getId()||record.getId()==0l) return ResultMessage.failed("此目录不存在");
		QiniuDir obj=this.selectByPrimaryKey(record.getId());
		if(null==obj) return ResultMessage.failed("此目录不存在");
		
		//旧目录
		String old_fulldir=obj.getFulldir();
		
		QiniuDir tmp=new QiniuDir();
		tmp.setBucket(obj.getBucket());
		tmp.setPid(obj.getPid());
		tmp.setDir(record.getDir());
		List<QiniuDir> list=this.selectByEntity(tmp);
		if(null!=list&&list.size()>0){
			for(QiniuDir qd:list){
				if(qd.getId().longValue()!=obj.getId().longValue()) return ResultMessage.failed("文件夹名冲突");
			}
		}
		QiniuDir parent=this.selectByPrimaryKey(obj.getPid());
		if(null!=parent) record.setFulldir(parent.getFulldir()+record.getDir()+"/");
		else record.setFulldir(record.getDir()+"/");
		
		int result=this.updateByPrimaryKeySelective(record);
		if(result>0){
			//更新七牛空间中的文件前缀
			BucketManager bucketManager=QiniuAPI.getBucketManager(Zone.autoZone());
			
			String bucket = obj.getBucket();
			//文件名前缀
			String prefix = old_fulldir;
			//每次迭代的长度限制，最大1000，推荐值 1000
			int limit = 1000;
			//指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
			String delimiter = "";
			//列举空间文件列表
			BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(bucket, prefix, limit, delimiter);
			while (fileListIterator.hasNext()) {
				FileInfo[] items = fileListIterator.next();
				for (FileInfo item : items) {
					String suffix=item.key.substring(old_fulldir.length(),item.key.length());
					try {
						bucketManager.move(bucket, item.key, bucket, record.getFulldir()+suffix);
					} catch (QiniuException e) {
						e.printStackTrace();
					}
				}
			}
			
			List<QiniuDir> dirs=this.selectAll();
			List<Object> children=TreeUtil.getChildList(dirs, obj.getId());
			if(null!=children&&children.size()>0){
				for(Object o:children){
					QiniuDir child=(QiniuDir) o;
					String suffix=child.getFulldir().substring(old_fulldir.length(),child.getFulldir().length());
					child.setFulldir(record.getFulldir()+suffix);
					this.updateByPrimaryKeySelective(child);
				}
			}
		}
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage delete(List<Object> ids) {
		if(null!=ids&&ids.size()>0){
			QiniuDir obj=this.selectByPrimaryKey(Long.valueOf(ids.get(0).toString()));
			List<QiniuDir> dirs=this.selectAll();
			List<Object> children=TreeUtil.getChildIds(dirs, ids.get(0));
			if(null!=children&&children.size()>0) ids.addAll(children);
			this.batchDelete(ids);
			if(null!=obj){
				BucketManager bucketManager=QiniuAPI.getBucketManager(Zone.autoZone());
				
				String bucket = obj.getBucket();
				//文件名前缀
				String prefix = obj.getFulldir();
				//每次迭代的长度限制，最大1000，推荐值 1000
				int limit = 1000;
				//指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
				String delimiter = "";
				//列举空间文件列表
				BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(bucket, prefix, limit, delimiter);
				
				List<String> keys=new ArrayList<String>();
				while (fileListIterator.hasNext()) {
					FileInfo[] items = fileListIterator.next();
					for (FileInfo item : items) {
						keys.add(item.key);
					}
				}
				if(keys.size()>0){
					BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
					String[] strs = new String[keys.size()];
				    batchOperations.addDeleteOp(bucket, keys.toArray(strs));
				    try {
						bucketManager.batch(batchOperations);
					} catch (QiniuException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public int updateFullDir(List<Object> ids, String prefix) {
		return this.mapper.updateFullDir(ids, prefix);
	}
	
	private Map<String, Object> message(Integer state,String message){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("error", state);
		resultMap.put("message", message);
		return resultMap;
	}

	@Override
	public void qiniu_upload(HttpServletRequest request, HttpServletResponse response, String dir, String bucket) throws IOException{
		Map<String, Object> result = new HashMap<String, Object>();
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		if(StringUtil.isEmpty(bucket)) {
			out.println(JSON.toJSONString(this.message(1, "请确认上传空间名")));
			out.close();
			return ;
		}
		if (StringUtil.isEmpty(dir)) {
			dir = "image";
		}
		String path=ParamsCache.get("KINDEDITOR_UPLOAD_PATH").getValue();
		if(StringUtil.isEmpty(path)){
			out.println(JSON.toJSONString(this.message(1, "kindeditor上传路径配置不存在，请检查配置")));
			out.close();
			return ;
		}
		String prefix=path.startsWith("/")?path.substring(1,path.length())+dir+"/":path+dir+"/";
		
		String type=ParamsCache.get("KINDEDITOR_UPLOAD_TYPE").getValue();
		if(StringUtil.isEmpty(type)){
			out.println(JSON.toJSONString(this.message(1, "kindeditor支持上传文件类型配置不存在，请检查配置")));
			out.close();
			return ;
		}
		String size=ParamsCache.get("KINDEDITOR_UPLOAD_SIZE").getValue();
		if(StringUtil.isEmpty(size)){
			out.println(JSON.toJSONString(this.message(1, "kindeditor可上传的最大文件大小配置不存在，请检查配置")));
			out.close();
			return ;
		}
		Long maxsize=null;
		try{
			maxsize=Long.valueOf(size);
		}catch (NumberFormatException e) {
			e.printStackTrace();
			out.println(JSON.toJSONString(this.message(1, "kindeditor可上传的最大文件大小配置非数值格式，请检查配置")));
			out.close();
			return ;
		}
		JSONObject types=JSONObject.parseObject(type);
		if(!ServletFileUpload.isMultipartContent(request)){
			out.println(JSON.toJSONString(this.message(1, "请选择要上传的文件")));
			out.close();
			return ;
		}
		
		ResultMessage rm=QiniuAPI.getBucketDomains(bucket);
		String msg=rm.getMessage();
		if(rm.getState().equals("0")) {
			out.println(JSON.toJSONString(this.message(1, "获取空间域名失败："+msg)));
			out.close();
			return ;
		}
		String[] domains=(String[]) rm.getData();
		
		UploadManager uploadManager = QiniuAPI.getUploadManager(Zone.autoZone());
		
		//默认不指定key的情况下，以文件内容的hash值作为文件名
		String key = null;
	    String upToken = QiniuAPI.getUploadToken(bucket,null);
		
		CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(request.getSession().getServletContext());
		if(multipartResolver.isMultipart(request)){
			MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
			Iterator<String> iter=multiRequest.getFileNames();
			if(iter.hasNext()){
				while(iter.hasNext()){
					MultipartFile file=multiRequest.getFile(iter.next());
					if(null!=file){
						if(file.getSize()>maxsize){
							float maxsizeMB=Math.round(maxsize/1048576*100)/100;
							out.println(JSON.toJSONString(this.message(1, "文件名："+file.getName()+" 上传大小超过"+maxsizeMB+"Mb限制")));
							out.close();
							return ;
						}
						String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
						if(!Arrays.<String>asList(types.get(dir).toString().toLowerCase().split(",")).contains(fileExt)){
							out.println(JSON.toJSONString(this.message(1, "上传文件扩展名是不允许的扩展名\n只允许" + types.get(dir).toString() + "格式。")));
							out.close();
							return ;
						}
					}
				}
				
				String ymd =DateUtil.getCurrentDate("yyyyMMdd");
				if(StringUtil.isEmpty(prefix)){
					//文件前缀为空则插入根目录
					QiniuDir qd=new QiniuDir();
					qd.setFulldir(ymd+"/");
					qd.setBucket(bucket);
					List<QiniuDir> dirs=this.selectByEntity(qd);
					if(null==dirs||dirs.size()==0){
						qd.setFulldir(ymd+"/");
						qd.setDir(ymd);
						qd.setPid(0l);
						long id=IDGenerator.generateId();
						qd.setId(id);
						this.insertSelective(qd);
					}
				}else{
					//层级添加文件目录
					String[] prefixs=(prefix+ymd+"/").split("/");
					if(null!=prefixs&&prefixs.length>0){
						long pid=0l;
						StringBuffer sb=new StringBuffer();
						for(int i=0;i<prefixs.length;i++){
							QiniuDir qd=new QiniuDir();
							sb.append(prefixs[i]).append("/");
							qd.setFulldir(sb.toString());
							qd.setDir(prefixs[i]);
							qd.setBucket(bucket);
							List<QiniuDir> dirs=this.selectByEntity(qd);
							if(null==dirs||dirs.size()==0){
								qd.setPid(pid);
								
								long id=IDGenerator.generateId();
								qd.setId(id);
								this.insertSelective(qd);
								pid=qd.getId().longValue();
							}else{
								pid=dirs.get(0).getId().longValue();
							}
						}
					}
				}
				try {
					iter=multiRequest.getFileNames();
					while(iter.hasNext()){
						MultipartFile file=multiRequest.getFile(iter.next());
						if(null!=file){
							if(StringUtil.isEmpty(prefix)) key=ymd+"/"+file.getOriginalFilename();
							else key=prefix+ymd+"/"+file.getOriginalFilename();
							uploadManager.put(file.getBytes(), key, upToken);
							result.put("error", 0);
							result.put("url", "http://"+domains[0]+"/"+key);
							out.println(JSON.toJSONString(result));
							out.close();
						}
					}
				} catch (QiniuException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public ResultMessage prefix2Dir(String bucket,String prefix) {
		if(StringUtil.isEmpty(prefix)) return ResultMessage.failed("路径字符串为空");
		String[] dirs=prefix.split("/");
		StringBuffer fulldir=new StringBuffer();
		if(null!=dirs&&dirs.length>0){
			int i=0;
			long pid=0l;
			for(String dir:dirs){
				if(StringUtil.isEmpty(dir)) continue;
				fulldir.append(dir).append("/");
				
				QiniuDir child=new QiniuDir();
				child.setBucket(bucket);
				child.setDir(dir);
				child.setFulldir(fulldir.toString());
				long id=IDGenerator.generateId();
				child.setId(id);
				
				if(i==0){
					//查找根路径有无此目录，有则不添加此目录，改变pid值
					List<QiniuDir> list=this.mapper.selectByFulldir(bucket,pid,fulldir.toString());
					if(null!=list&&list.size()>0) {
						pid=list.get(0).getId();
						i++;
						continue;
					}
				}else{
					//查找是否已有此目录，有则不添加此目录，改变pid值
					List<QiniuDir> list=this.mapper.selectByFulldir(bucket,pid, fulldir.toString());
					if(null!=list&&list.size()>0) {
						pid=list.get(0).getId();
						i++;
						continue;
					}
				}
				child.setPid(pid);
				this.insertSelective(child);
				pid=child.getId();
				i++;
			}
		}
		return ResultMessage.defaultSuccessMessage();
	}

}
