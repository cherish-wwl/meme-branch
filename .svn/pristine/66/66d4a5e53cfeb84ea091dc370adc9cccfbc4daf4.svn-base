package com.meme.im.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.meme.core.cache.ParamsCache;
import com.meme.core.http.ResultMessage;
import com.meme.core.util.IDGenerator;
import com.meme.core.util.ImageTool;
import com.meme.core.util.StringUtil;
import com.meme.im.pojo.Member;
import com.meme.im.pojo.MemberAlbum;
import com.meme.im.pojo.MemberAlbumItem;
import com.meme.im.pojo.MemberBgTem;
import com.meme.im.pojo.MemberBillboard;
import com.meme.im.pojo.MemberBillboardItem;
import com.meme.im.pojo.MemberHobby;
import com.meme.im.pojo.MemberHobbyType;
import com.meme.im.pojo.MemberSign;
import com.meme.im.pojo.MemeShop;
import com.meme.im.pojo.MemeWbShop;
import com.meme.im.service.MemberAlbumItemService;
import com.meme.im.service.MemberAlbumService;
import com.meme.im.service.MemberBgTemService;
import com.meme.im.service.MemberBillboardItemService;
import com.meme.im.service.MemberBillboardService;
import com.meme.im.service.MemberHobbyService;
import com.meme.im.service.MemberHobbyTypeService;
import com.meme.im.service.MemberService;
import com.meme.im.service.MemberSignService;
import com.meme.im.service.MemeWbShopService;
import com.meme.im.util.AccountUtil;
import com.meme.qiniu.util.QiniuAPI;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;

@Service("MemberCenterService")
@Transactional
public class MemberCenterService {

	@Resource private MemberService memberService;
	@Resource private MemberBillboardService memberBillboardService;
	@Resource private MemberSignService memberSignService;
	@Resource private MemberAlbumService memberAlbumService;
	@Resource private MemberAlbumItemService memberAlbumItemService;
	@Resource private MemberHobbyService memberHobbyService;
	@Resource private MemberHobbyTypeService memberHobbyTypeService;
	@Resource private MemberBgTemService memberBgTemService;
	@Resource private MemberBillboardItemService memberBillboardItemService;
	@Resource private MemeWbShopService memeWbShopService;
	
	/**
	 * 更新头像
	 * @param request
	 * @return
	 */
	public ResultMessage doUpdateAvatar(HttpServletRequest request){
		Object memberid = AccountUtil.getMemberid(request);
		Member member=this.memberService.selectByPrimaryKey(Long.valueOf(memberid.toString()));
		CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(request.getSession().getServletContext());
		if(multipartResolver.isMultipart(request)){
			MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
			Iterator<String> it=multiRequest.getFileNames();
			if(it.hasNext()){
				MultipartFile file=multiRequest.getFile(it.next());
				String name=file.getOriginalFilename();
				String acceptType=ParamsCache.get("AVATAR_FILE_TYPE").getValue().toLowerCase();
				String suffix=name.substring(name.lastIndexOf(".")+1,name.length());
				if(acceptType.indexOf(suffix.toLowerCase())==-1) return ResultMessage.failed("只支持("+acceptType+")这几种类型图片上传");
				try {
					if(null!=file.getBytes()&&file.getBytes().length>0){
						Integer[] wh=ImageTool.getImageWH(file.getBytes());
				    	Integer width=Integer.valueOf(ParamsCache.get("AVATAR_WIDTH").getValue());
				    	Integer height=Integer.valueOf(ParamsCache.get("AVATAR_HEIGHT").getValue());
				    	if(width<wh[0]||height<wh[1]) return ResultMessage.failed("头像图片宽高要小于"+width+"*"+height);
				    	String prefix=ParamsCache.get("AVATAR_QINIU_PREFIX")==null?"":ParamsCache.get("AVATAR_QINIU_PREFIX").getValue();
						
				    	//默认不指定key的情况下，以文件内容的hash值作为文件名
						String key = null;
					    
				    	if(StringUtil.isEmpty(prefix)) key=member.getMemberid()+"."+suffix;
						else key=(prefix.endsWith("/")?prefix:(prefix+"/"))+member.getMemberid()+"."+suffix;
				    	
				    	String bucket=ParamsCache.get("MEMBER_BUCKET").getValue();
						UploadManager uploadManager = QiniuAPI.getUploadManager(Zone.autoZone());
						//覆盖上传
						String upToken = QiniuAPI.getUploadToken(bucket,key);
						
						Response res=uploadManager.put(file.getBytes(), key, upToken);
						
						ResultMessage result=QiniuAPI.getBucketDomains(bucket);
						String msg=result.getMessage();
						if(result.getState().equals("0")) return ResultMessage.failed("获取七牛空间域名失败："+msg);
						String[] domains=(String[]) result.getData();
						String domain=null;
						if(null!=domains&&domains.length>0) domain=domains[0];
						
						//刷新CDN缓存文件
						QiniuAPI.flushCdnCache("http://"+domain+"/"+key);
						
						member.setAvatar("http://"+domain+"/"+key);
						this.memberService.updateByPrimaryKeySelective(member);
						
//						String qrcode_dir=ParamsCache.get("QRCODE_QINIU_DIR").getValue();
//						width=Integer.valueOf(ParamsCache.get("QRCODE_WIDTH").getValue());
//						height=Integer.valueOf(ParamsCache.get("QRCODE_HEIGHT").getValue());
//						String type=StringUtil.isEmpty(ParamsCache.get("QRCODE_TYPE").getValue())?"png":ParamsCache.get("QRCODE_TYPE").getValue();
//						String basePath=HttpUtil.getBasePath(request);
//						String content=basePath+"member/publics/"+member.getMemberid();
//						key=qrcode_dir+"qrcode_"+member.getMemberid()+"."+type;
//						byte[] bytes=QrCodeUtil.createQrCode(width, height, type, content);
//						if(null!=bytes) {
//							uploadManager.put(bytes, key, upToken);
//							
//							member.setQrcode("http://"+domain+"/"+key);
//						}
						return ResultMessage.success("更换头像成功");
					}else return ResultMessage.failed("请选择头像图片上传");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return ResultMessage.defaultFaileMessage();
	}
	
	/**
	 * 更新背景音乐
	 * @param request
	 * @return
	 */
	public ResultMessage doUpdateBgMusic(HttpServletRequest request){
		Object memberid = AccountUtil.getMemberid(request);
		Member member=this.memberService.selectByPrimaryKey(Long.valueOf(memberid.toString()));
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
						
//						//覆盖上传
//						String music=null;
//						if(StringUtil.isAllNotEmpty(member.getMusic())){
//							music=member.getMusic().replace("http://"+domain+"/", "");
//						}
//						String key = null;
//						String upToken = null;
//						UploadManager uploadManager = QiniuAPI.getUploadManager(Zone.autoZone());
//						if(StringUtil.isAllNotEmpty(music)){
//							key=music;
//							upToken = QiniuAPI.getUploadToken(bucket,music);
//						}else{
//							upToken = QiniuAPI.getUploadToken(bucket,null);
//							String prefix=ParamsCache.get("MEMBER_BG_MUSIC")==null?"":ParamsCache.get("MEMBER_BG_MUSIC").getValue();
//							String hash=UUID.randomUUID().toString().replaceAll("-", "");
//							
//					    	if(StringUtil.isEmpty(prefix)) key=hash+"."+suffix;
//							else key=(prefix.endsWith("/")?prefix:(prefix+"/"))+memberid.toString()+"."+suffix;
//						}
//						Response res=uploadManager.put(file.getBytes(), key, upToken);
//						//刷新CDN缓存文件
//						QiniuAPI.flushCdnCache("http://"+domain+"/"+key);
						
						//非覆盖上传
						String prefix=ParamsCache.get("MEMBER_BG_MUSIC")==null?"":ParamsCache.get("MEMBER_BG_MUSIC").getValue();
						String hash=UUID.randomUUID().toString().replaceAll("-", "");
						String key = null;
						UploadManager uploadManager = QiniuAPI.getUploadManager(Zone.autoZone());
						String upToken = QiniuAPI.getUploadToken(bucket,null);
				    	if(StringUtil.isEmpty(prefix)) key=hash+"."+suffix;
						else key=(prefix.endsWith("/")?prefix:(prefix+"/"))+hash+"."+suffix;
						uploadManager.put(file.getBytes(), key, upToken);
						
						
						member.setMusic("http://"+domain+"/"+key);
						this.memberService.updateByPrimaryKeySelective(member);
						
						return ResultMessage.success("更换背景音乐成功");
					}else return ResultMessage.failed("请选择背景音乐文件上传");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return ResultMessage.defaultFaileMessage();
	}
	
	/**
	 * 更新会员中心背景图
	 * @param request
	 * @return
	 */
	public ResultMessage doUpdateBg(HttpServletRequest request,String tempid){
		Object memberid = AccountUtil.getMemberid(request);
		Member member=this.memberService.selectByPrimaryKey(Long.valueOf(memberid.toString()));
		CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(request.getSession().getServletContext());
		if(StringUtil.isAllNotEmpty(tempid)){
			MemberBgTem tem=this.memberBgTemService.selectByPrimaryKey(tempid);
			member.setCover(tem.getUrl());
			this.memberService.updateByPrimaryKeySelective(member);
			return ResultMessage.success("更换背景图成功");
		}else{
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
							
//							//覆盖上传
//							String str=null;
//							if(StringUtil.isAllNotEmpty(member.getCover())){
//								str=member.getCover().replace("http://"+domain+"/", "");
//							}
//							String key = null;
//							String upToken = null;
//							UploadManager uploadManager = QiniuAPI.getUploadManager(Zone.autoZone());
//							if(StringUtil.isAllNotEmpty(str)){
//								key=str;
//								upToken = QiniuAPI.getUploadToken(bucket,str);
//							}else{
//								upToken = QiniuAPI.getUploadToken(bucket,null);
//								String prefix=ParamsCache.get("MEMBER_COVER")==null?"":ParamsCache.get("MEMBER_COVER").getValue();
//								String hash=UUID.randomUUID().toString().replaceAll("-", "");
//								
//						    	if(StringUtil.isEmpty(prefix)) key=hash+"."+suffix;
//								else key=(prefix.endsWith("/")?prefix:(prefix+"/"))+memberid.toString()+"."+suffix;
//							}
//							Response res=uploadManager.put(file.getBytes(), key, upToken);
//							//刷新CDN缓存文件
//							QiniuAPI.flushCdnCache("http://"+domain+"/"+key);
							
							//非覆盖上传
							String prefix=ParamsCache.get("MEMBER_COVER")==null?"":ParamsCache.get("MEMBER_COVER").getValue();
							String hash=UUID.randomUUID().toString().replaceAll("-", "");
							String key = null;
							UploadManager uploadManager = QiniuAPI.getUploadManager(Zone.autoZone());
							String upToken = QiniuAPI.getUploadToken(bucket,null);
					    	if(StringUtil.isEmpty(prefix)) key=hash+"."+suffix;
							else key=(prefix.endsWith("/")?prefix:(prefix+"/"))+hash+"."+suffix;
							uploadManager.put(file.getBytes(), key, upToken);
							
							
							member.setCover("http://"+domain+"/"+key);
							this.memberService.updateByPrimaryKeySelective(member);
							
							return ResultMessage.success("更换背景图成功");
						}else return ResultMessage.failed("请选择背景图文件上传");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return ResultMessage.defaultFaileMessage();
	}
	
	/**
	 * 更新公告预告
	 * @param request
	 * @param response
	 */
	public ResultMessage doUpdateBillboard(HttpServletRequest request,MemberBillboard record){
		Object memberid = AccountUtil.getMemberid(request);
		record.setMemberid(Long.valueOf(memberid.toString()));
		record.setAddtime(new Date());
//		record.setId(String.valueOf(IDGenerator.generateId()));
		
		//上传视频
//		HashMap<String, String> file_map=new LinkedHashMap<String, String>();
//		CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(request.getSession().getServletContext());
//		if(multipartResolver.isMultipart(request)){
//			MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
//			Iterator<String> it=multiRequest.getFileNames();
//			if(it.hasNext()){
//				MultipartFile file=multiRequest.getFile(it.next());
//				String name=file.getOriginalFilename();
//				String fieldname=file.getName();
//				String suffix=name.substring(name.lastIndexOf(".")+1,name.length());
//				try {
//					if(null!=file.getBytes()&&file.getBytes().length>0){
//						String bucket=ParamsCache.get("MEMBER_BUCKET").getValue();
//						
//						ResultMessage result=QiniuAPI.getBucketDomains(bucket);
//						String msg=result.getMessage();
//						if(result.getState().equals("0")) return ResultMessage.failed("获取七牛空间域名失败："+msg);
//						String[] domains=(String[]) result.getData();
//						String domain=null;
//						if(null!=domains&&domains.length>0) domain=domains[0];
//						
//						String prefix=ParamsCache.get("MEMBER_CENTER_DIR")==null?"":ParamsCache.get("MEMBER_CENTER_DIR").getValue();
//						String hash=UUID.randomUUID().toString().replaceAll("-", "");
//						String key = null;
//						UploadManager uploadManager = QiniuAPI.getUploadManager(Zone.autoZone());
//						String upToken = QiniuAPI.getUploadToken(bucket,null);
//				    	if(StringUtil.isEmpty(prefix)) key=hash+"."+suffix;
//						else key=(prefix.endsWith("/")?prefix:(prefix+"/"))+hash+"."+suffix;
//						uploadManager.put(file.getBytes(), key, upToken);
//						
//						file_map.put(fieldname, "http://"+domain+"/"+key);
//					}
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		if(file_map.size()>0){
//			record.setExtlink(file_map.get("file"));
//		}
		
		int result=this.memberBillboardService.insertSelective(record);
		if(result>0) return ResultMessage.success("发布公告成功");
		return ResultMessage.failed("发布公告失败");
	}
	
	/**
	 * 上传公告视频项
	 * @param request
	 * @param response
	 * @param billboardid
	 * @param remark
	 * @return
	 */
	public ResultMessage doUpdateBillboardItem(HttpServletRequest request,String billboardid,String remark,String url,String subject,String body,BigDecimal amount){
		Object memberid = AccountUtil.getMemberid(request);
//		MemberBillboard billboard=this.memberBillboardService.selectByPrimaryKey(billboardid);
//		int flag=0;
//		if(null==billboard){
//			billboard=new MemberBillboard();
//			billboard.setAddtime(new Date());
//			billboard.setId(billboardid);
//			billboard.setType(1);
//			billboard.setMemberid(Long.valueOf(memberid.toString()));
//			billboard.setContent("");
//			flag=this.memberBillboardService.insertSelective(billboard);
//		}else flag=1;
//		
//		if(flag>=1){
		MemberBillboardItem item = new MemberBillboardItem();
		item.setAddtime(new Date());
		item.setBitemid(String.valueOf(IDGenerator.generateId()));
		item.setBillboardid(billboardid);
		item.setRemark(remark);
		item.setUrl(url);
		int result = this.memberBillboardItemService.insertSelective(item);
		MemeWbShop memeWbShop = new MemeWbShop();
		memeWbShop.setProductid("PT"+IDGenerator.generateId());
		memeWbShop.setFile(url);
		memeWbShop.setBody(body);
		memeWbShop.setSubject(subject);
		memeWbShop.setAmount(amount);
		memeWbShop.setMemberid((long)memberid);
		memeWbShopService.insert(memeWbShop);
		if(result > 0) {
			return ResultMessage.success("上传音视频成功");
		}else {
			return ResultMessage.failed("上传失败");
		}
							
	}
	
	/**
	 * 更新签名/文章
	 * @param request
	 * @param response
	 */
	public ResultMessage doUpdateSign(HttpServletRequest request,MemberSign record){
		Object memberid = AccountUtil.getMemberid(request);
		Member member=this.memberService.selectByPrimaryKey(Long.valueOf(memberid.toString()));
		String cover=null;
		CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(request.getSession().getServletContext());
		if(multipartResolver.isMultipart(request)){
			MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
			Iterator<String> it=multiRequest.getFileNames();
			if(it.hasNext()){
				MultipartFile file=multiRequest.getFile(it.next());
				String name=file.getOriginalFilename();
				String acceptType=ParamsCache.get("AVATAR_FILE_TYPE").getValue().toLowerCase();
				String suffix=name.substring(name.lastIndexOf(".")+1,name.length());
				if(acceptType.indexOf(suffix.toLowerCase())==-1) return ResultMessage.failed("只支持("+acceptType+")这几种类型图片上传");
				try {
					if(null!=file.getBytes()&&file.getBytes().length>0){
				    	String prefix=ParamsCache.get("MEMBER_CENTER_DIR")==null?"":ParamsCache.get("MEMBER_CENTER_DIR").getValue();
						
				    	//默认不指定key的情况下，以文件内容的hash值作为文件名
						String key = null;
					    
				    	if(StringUtil.isEmpty(prefix)) key=member.getMemberid()+"."+suffix;
						else key=(prefix.endsWith("/")?prefix:(prefix+"/"))+member.getMemberid()+"."+suffix;
				    	
				    	String bucket=ParamsCache.get("MEMBER_BUCKET").getValue();
						UploadManager uploadManager = QiniuAPI.getUploadManager(Zone.autoZone());
						//覆盖上传
						String upToken = QiniuAPI.getUploadToken(bucket,key);
						
						Response res=uploadManager.put(file.getBytes(), key, upToken);
						
						ResultMessage result=QiniuAPI.getBucketDomains(bucket);
						String msg=result.getMessage();
						if(result.getState().equals("0")) return ResultMessage.failed("获取七牛空间域名失败："+msg);
						String[] domains=(String[]) result.getData();
						String domain=null;
						if(null!=domains&&domains.length>0) domain=domains[0];
						
						cover="http://"+domain+"/"+key;
						//刷新CDN缓存文件
						QiniuAPI.flushCdnCache(cover);
						
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		String summary=request.getParameter("summary");
		record.setSummary(summary);
		record.setCover(cover);
		record.setMemberid(Long.valueOf(memberid.toString()));
		record.setSigntime(new Date());
		record.setSignid(String.valueOf(IDGenerator.generateId()));
		
		//未选择跳转类型时默认取type值
		String redirect=request.getParameter("redirect");
		if(StringUtil.isEmpty(redirect)) record.setRedirect(record.getType());
		else record.setRedirect(Integer.valueOf(redirect));
		
		int result=this.memberSignService.insertSelective(record);
		if(result>0) return ResultMessage.success("更新成功");
		return ResultMessage.failed("更新失败");
	}
	
	/**
	 * 更新相册
	 * @param request
	 * @param response
	 * @param albumid
	 */
	public ResultMessage doUpdateAlbum(HttpServletRequest request,String albumid,String thumb,String url,String subject,String body,BigDecimal amount){
		Object memberid = AccountUtil.getMemberid(request);
		MemberAlbum album=this.memberAlbumService.selectByPrimaryKey(albumid);
		int flag=0;
		if(null==album){
			album=new MemberAlbum();
			album.setAddtime(new Date());
			album.setAlbumid(albumid);
			album.setMemberid(Long.valueOf(memberid.toString()));
			flag=this.memberAlbumService.insertSelective(album);
		}else flag=1;
		
		if(flag>=1){
			MemberAlbumItem item = new MemberAlbumItem();
			item.setAlbumid(albumid);
			item.setAddtime(new Date());
			item.setUrl(url);
			item.setItemid(String.valueOf(IDGenerator.generateId()));
			item.setThumb(thumb);
			this.memberAlbumItemService.insertSelective(item);
			MemeWbShop memeWbShop = new MemeWbShop();
			memeWbShop.setProductid("PT"+IDGenerator.generateId());
			memeWbShop.setFile(url);
			memeWbShop.setBody(body);
			memeWbShop.setSubject(subject);
			memeWbShop.setAmount(amount);
			memeWbShop.setMemberid((long)memberid);
			memeWbShopService.insert(memeWbShop);
			return ResultMessage.success("上传照片成功");
		}
		return ResultMessage.failed("上传失败");
	}
	
	public ResultMessage doUpdateHobby(HttpServletRequest request,HttpServletResponse response,
			String title,String summary,Integer type,String extlink,String alias,Integer ctype){
		Object memberid = AccountUtil.getMemberid(request);
//		if(StringUtil.isOneEmpty(title)) return ResultMessage.failed("标题不能为空");
		MemberHobby hobby=new MemberHobby();
		hobby.setHobbyid(String.valueOf(IDGenerator.generateId()));
		hobby.setMemberid(Long.valueOf(memberid.toString()));
		hobby.setTitle(title);
		hobby.setSummary(summary);
		hobby.setExtlink(extlink);
		hobby.setType(type);
		hobby.setCtype(ctype);
		hobby.setAddtime(new Date());
		
		MemberHobbyType record=new MemberHobbyType();
		record.setMemberid(Long.valueOf(memberid.toString()));
		record.setType(type);
		List<MemberHobbyType> types=this.memberHobbyTypeService.selectByEntity(record);
		if(null!=types&&types.size()>0){
			record=types.get(0);
			if(ctype!=types.get(0).getCtype()) {
				record.setCtype(ctype);
				if(StringUtil.isAllNotEmpty(alias)) record.setAlias(alias);
				this.memberHobbyTypeService.updateByPrimaryKeySelective(record);
			}else{
				if(StringUtil.isAllNotEmpty(alias)) {
					record.setAlias(alias);
					this.memberHobbyTypeService.updateByPrimaryKeySelective(record);
				}
			}
		}else {
			record.setCtype(ctype);
			if(StringUtil.isAllNotEmpty(alias)) record.setAlias(alias);
			record.setId(String.valueOf(IDGenerator.generateId()));
			this.memberHobbyTypeService.insertSelective(record);
		}
		
		
		HashMap<String, String> file_map=new LinkedHashMap<String, String>();
		CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(request.getSession().getServletContext());
		if(multipartResolver.isMultipart(request)){
			MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
			Iterator<String> it=multiRequest.getFileNames();
			while(it.hasNext()){
				MultipartFile file=multiRequest.getFile(it.next());
				String name=file.getOriginalFilename();
				String fieldname=file.getName();
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
						
						String prefix=ParamsCache.get("MEMBER_CENTER_DIR")==null?"":ParamsCache.get("MEMBER_CENTER_DIR").getValue();
						String hash=UUID.randomUUID().toString().replaceAll("-", "");
						String key = null;
						UploadManager uploadManager = QiniuAPI.getUploadManager(Zone.autoZone());
						String upToken = QiniuAPI.getUploadToken(bucket,null);
				    	if(StringUtil.isEmpty(prefix)) key=hash+"."+suffix;
						else key=(prefix.endsWith("/")?prefix:(prefix+"/"))+hash+"."+suffix;
						uploadManager.put(file.getBytes(), key, upToken);
						
						file_map.put(fieldname, "http://"+domain+"/"+key);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if(file_map.size()>0){
			hobby.setCover(file_map.get("cover"));
			String content=file_map.get("content");
			if(StringUtil.isEmpty(content)) content=request.getParameter("content");
			hobby.setContent(content);
		}
		int result=this.memberHobbyService.insertSelective(hobby);
		if(result>0) return ResultMessage.success("发布成功");
		return ResultMessage.failed("发布失败");
	}
	
	/**
	 * 更新会员栏目标题
	 * @param request
	 * @param alias
	 * @param ctype
	 * @param type
	 * @return
	 */
	public ResultMessage doUpdateHobbyType(HttpServletRequest request,String alias,Integer ctype,Integer type){
		Object memberid=AccountUtil.getMemberid(request);
		MemberHobbyType record=new MemberHobbyType();
		record.setMemberid(Long.valueOf(memberid.toString()));
		record.setType(type);
		List<MemberHobbyType> list=this.memberHobbyTypeService.selectByEntity(record);
		record.setAlias(alias);
		record.setCtype(ctype);
		int result=0;
		if(null!=list&&list.size()>0){
			record.setId(list.get(0).getId());
			result=this.memberHobbyTypeService.updateByPrimaryKeySelective(record);
		}else{
			record.setId(String.valueOf(IDGenerator.generateId()));
			result=this.memberHobbyTypeService.insertSelective(record);
		}
		
		if(result>0) return ResultMessage.success("更换成功");
		else return ResultMessage.failed("更换失败");
	}
	
	public ResultMessage doUpdateSection(HttpServletRequest request,HttpServletResponse response,String sectionname){
		Object memberid=AccountUtil.getMemberid(request);
		Member member=this.memberService.selectByPrimaryKey(Long.valueOf(memberid.toString()));
		member.setSectionname(sectionname);
		int result=this.memberService.updateByPrimaryKeySelective(member);
		if(result>0) return ResultMessage.defaultSuccessMessage();
		return ResultMessage.defaultFaileMessage();
	}
}
