package com.meme.im.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.meme.core.base.AbstractService;
import com.meme.core.cache.DictCache;
import com.meme.core.cache.ParamsCache;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.core.mybatis.Criterion;
import com.meme.core.mybatis.MapperHelper;
import com.meme.core.mybatis.enums.Operator;
import com.meme.core.pojo.DictItemView;
import com.meme.core.pojo.Params;
import com.meme.core.util.*;
import com.meme.im.dao.MemberMapper;
import com.meme.im.pojo.ImFriendGroup;
import com.meme.im.pojo.Member;
import com.meme.im.redis.IpTmpMemberService;
import com.meme.im.service.ImFriendGroupService;
import com.meme.im.service.MemberService;
import com.meme.im.util.AccountUtil;
import com.meme.im.util.CookieUtil;
import com.meme.im.util.IMConstants;
import com.meme.qiniu.pojo.QiniuDir;
import com.meme.qiniu.service.QiniuDirService;
import com.meme.qiniu.util.QiniuAPI;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.storage.UploadManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service("MemberService")
@Transactional
public class MemberServiceImpl extends AbstractService implements MemberService {

	@Resource private MemberMapper mapper;
	@Resource private QiniuDirService qiniuDirService;
	@Resource private ImFriendGroupService imFriendGroupService;
	@Resource private IpTmpMemberService ipTmpMemberService;
	
	@Override
	public int deleteByPrimaryKey(Object id) {
		return this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Member record) {
		return this.mapper.insert(record);
	}

	@Override
	public int insertSelective(Member record) {
		return this.mapper.insertSelective(record);
	}

	@Override
	public Member selectByPrimaryKey(Object id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Member record) {
		return this.mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Member record) {
		return this.mapper.updateByPrimaryKey(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Member> selectAll(){
		return (List<Member>) MapperHelper.toBeanList(this.mapper.selectAll(Member.class), Member.class);
	}

	public void buildSearchCriterion(Class<?> entityClass, Form form) {
		super.buildSearchCriterion(entityClass, form);
	}
	
	@Override
	public List<Long> selectByNums(String[] nums) {
		return this.mapper.selectByNums(nums);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Member> selectByPagination(Form form) {
		this.buildSearchCriterion(Member.class, form);
		this.buildOrderByCriterion(Member.class, form);
		this.buildLimitCriterion(Member.class, form);
		return (List<Member>) MapperHelper.toBeanList(this.mapper.selectByPagination(Member.class,this.getList()), Member.class);
	}

	@Override
	public int count(Form form) {
		this.buildSearchCriterion(Member.class, form);
		return this.mapper.count(Member.class,this.getList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Member> selectByEntity(Member record) {
		return (List<Member>) MapperHelper.toBeanList(this.mapper.selectByEntity(record), Member.class);
	}

	@Override
	public int batchInsert(List<Member> record) {
		return this.mapper.batchInsert(record,Member.class);
	}

	@Override
	public int batchDelete(List<Object> record) {
		return this.mapper.batchDelete(record,Member.class);
	}

	@Override
	public int batchUpdate(List<Member> record) {
		return this.mapper.batchUpdate(record,Member.class);
	}

	/**
	 * 添加客服账号
	 */
	@Override
	public ResultMessage add(HttpServletRequest request, Member record) {
		String cmpassword=request.getParameter("cmpassword");
		if(StringUtil.isOneEmpty(cmpassword,record.getMpassword())) return ResultMessage.failed("密码和确认密码不能为空");
		if(!cmpassword.equals(record.getMpassword())) return ResultMessage.failed("密码和确认密码不一致");
		
		long id=IDGenerator.generateId();
		Member tmp=new Member();
		tmp.setAccount(record.getAccount().toLowerCase());
		List<Member> mems=this.selectByEntity(tmp);
		if(null!=mems&&mems.size()>0) return ResultMessage.failed("此用户名已被占用");
		
		if(StringUtil.isAllNotEmpty(record.getEmail())){
			tmp=new Member();
			tmp.setEmail(record.getEmail().toLowerCase());
			mems=this.selectByEntity(tmp);
			if(null!=mems&&mems.size()>0) return ResultMessage.failed("邮箱已被占用");
		}
		if(StringUtil.isAllNotEmpty(record.getCellphone())){
			tmp=new Member();
			tmp.setCellphone(record.getCellphone());
			mems=this.selectByEntity(tmp);
			if(null!=mems&&mems.size()>0) return ResultMessage.failed("手机号码已被占用");
		}
		
		// 客服头像取系统默认值
		Params img=ParamsCache.get("AVATAR_DEFAULT_IMG");
		if(null != img){
			record.setAvatar(img.getValue());
		}
		
		String salt=CipherTool.createSalt();
		record.setSalt(salt);
		record.setMemberid(id);
		String pwd=record.getMpassword();
		record.setMpassword(MD5Util.generatePassword(pwd+salt));
		record.setState(1);
		record.setMtype(1);
		record.setAccount(record.getAccount().toLowerCase());
		record.setEmail(record.getEmail().toLowerCase());
		
		this.insertSelective(record);
		
		//添加默认好友分组
		ImFriendGroup group=new ImFriendGroup();
		group.setGroupname("我的客户");
		long groupid=IDGenerator.generateId();
		group.setId(groupid);
		group.setMemberid(record.getMemberid());
		this.imFriendGroupService.add(request, group);
		
		
		return ResultMessage.success("成功添加客服账号");
		
	}

	@Override
	public ResultMessage edit(HttpServletRequest request, Member record) {
		
		if(null==record.getMemberid()) return ResultMessage.failed("请提供客服编号");
		Member mm=this.selectByPrimaryKey(record.getMemberid());
		if(null==mm) return ResultMessage.failed("该客服账号不存在");
		if(mm.getMtype()==null||mm.getMtype()!=1) return ResultMessage.failed("非客服账号禁止操作");
		
		Member tmp=new Member();
		tmp.setAccount(record.getAccount().toLowerCase());
		List<Member> mems=this.selectByEntity(tmp);
		if(null!=mems&&mems.size()>0) {
			for(Member m:mems){
				if(m.getMemberid().longValue()!=record.getMemberid().longValue()) return ResultMessage.failed("此用户名已被占用");
			}
		}
		
		if(StringUtil.isAllNotEmpty(record.getEmail())){
			tmp=new Member();
			tmp.setEmail(record.getEmail().toLowerCase());
			mems=this.selectByEntity(tmp);
			if(null!=mems&&mems.size()>0) {
				for(Member m:mems){
					if(StringUtil.isAllNotEmpty(m.getEmail(),record.getEmail())&&m.getEmail().equalsIgnoreCase(record.getEmail())) return ResultMessage.failed("邮箱已被占用");
				}
			}
		}
		if(StringUtil.isAllNotEmpty(record.getCellphone())){
			tmp=new Member();
			tmp.setCellphone(record.getCellphone());
			mems=this.selectByEntity(tmp);
			if(null!=mems&&mems.size()>0) return ResultMessage.failed("手机号码已被占用");
		}
		
		//不修改头像盐值密码等敏感数据
		record.setAvatar(null);
		record.setSalt(null);
		record.setMpassword(null);
		record.setState(null);
		record.setMtype(null);
		record.setAccount(record.getAccount().toLowerCase());
		record.setEmail(record.getEmail().toLowerCase());
		this.updateByPrimaryKeySelective(record);
		
		return ResultMessage.defaultSuccessMessage();
	}

	@Override
	public ResultMessage delete(List<Object> ids) {
		int result=0;
		if(null!=ids&&ids.size()>0){
			result=this.batchDelete(ids);
		}
		if(result>0) return ResultMessage.defaultSuccessMessage();
		return ResultMessage.defaultFaileMessage();
	}

	@Override
	public ResultMessage doRegister(HttpServletRequest request,HttpServletResponse response, Member obj) {
		obj.setMemberid(null);
		long id=IDGenerator.generateId();
		//判断有无token，有token则判断是不是临时访客
		Object memberid=AccountUtil.getMemberid(request);
		Member tmp_mem=null;
		if(null!=memberid){
			tmp_mem=this.selectByPrimaryKey(Long.valueOf(memberid.toString()));
			if(null!=tmp_mem&&tmp_mem.getMtype()==-1){
				id=Long.valueOf(memberid.toString());
			}
		}
		
		String cmpassword=request.getParameter("cmpassword");
		if(StringUtil.isOneEmpty(obj.getMpassword())) return ResultMessage.failed("密码和确认密码不能为空");
//		if(!cmpassword.equals(obj.getMpassword())) return ResultMessage.failed("密码和确认密码不一致");
		String bucket=ParamsCache.get("MEMBER_BUCKET").getValue();
		
		Member tmp=new Member();
		tmp.setAccount(obj.getAccount().trim().toLowerCase());
		List<Member> mems=this.selectByEntity(tmp);
		if(null!=mems&&mems.size()>0) return ResultMessage.failed("此用户名已被占用");
		
		ConcurrentHashMap<String, DictItemView> map=DictCache.getDictItemList("PAGE_URL");
		if(null!=map&&map.size()>0){
			Enumeration<String> keys=map.keys();
			while(keys.hasMoreElements()){
				String key=keys.nextElement();
//				String code=map.get(key).getDictitemcode();
				if(obj.getAccount().trim().toLowerCase().equals(key.toLowerCase())) return ResultMessage.failed("此用户名已被占用");
				if(StringUtil.isAllNotEmpty(obj.getDomain())&&obj.getDomain().trim().toLowerCase().equals(key)) return ResultMessage.failed("此域名后缀已被占用");
			}
		}
		
		if(StringUtil.isAllNotEmpty(obj.getEmail())){
			tmp=new Member();
			tmp.setEmail(obj.getEmail().toLowerCase());
			mems=this.selectByEntity(tmp);
			if(null!=mems&&mems.size()>0) return ResultMessage.failed("邮箱已被占用");
		}
		
		if(StringUtil.isAllNotEmpty(obj.getCellphone())){
			tmp=new Member();
			tmp.setCellphone(obj.getCellphone());
			mems=this.selectByEntity(tmp);
			if(null!=mems&&mems.size()>0) return ResultMessage.failed("手机号码已被占用");
		}

		if(StringUtil.isAllNotEmpty(obj.getDomain())){
			List<Member> memberIdByPathname = mapper.getMemberIdByPathname("/"+obj.getDomain());
			if(memberIdByPathname != null && memberIdByPathname.size()>0){
				return ResultMessage.failed("个性域名已被占用");
			}
		}
		String prefix=ParamsCache.get("AVATAR_QINIU_PREFIX")==null?"":ParamsCache.get("AVATAR_QINIU_PREFIX").getValue();
		if(!StringUtil.isEmpty(prefix)){
			QiniuDir record=new QiniuDir();
			record.setBucket(bucket);
			record.setFulldir(prefix.endsWith("/")?prefix:(prefix+"/"));
			List<QiniuDir> list=this.qiniuDirService.selectByEntity(record);
			//七牛目录表无此目录则调用目录服务类添加目录结构
			if(null==list||list.size()==0){
				this.qiniuDirService.prefix2Dir(bucket, prefix);
			}
		}
		
		UploadManager uploadManager = QiniuAPI.getUploadManager(Zone.autoZone());
		String upToken = QiniuAPI.getUploadToken(bucket,null);
		ResultMessage result=QiniuAPI.getBucketDomains(bucket);
		String msg=result.getMessage();
		if(result.getState().equals("0")) return ResultMessage.failed("获取七牛空间域名失败："+msg);
		String[] domains=(String[]) result.getData();
		String domain=null;
		if(null!=domains&&domains.length>0) domain=domains[0];
		
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
				    	
						//默认不指定key的情况下，以文件内容的hash值作为文件名
						String key = null;
					    
				    	if(StringUtil.isEmpty(prefix)) key=id+"."+suffix;
						else key=(prefix.endsWith("/")?prefix:(prefix+"/"))+id+"."+suffix;
						uploadManager.put(file.getBytes(), key, upToken);
						
						obj.setAvatar("http://"+domain+"/"+key);
			    	}else{
			    		// 无上传头像则取系统默认值
						Params img=ParamsCache.get("AVATAR_DEFAULT_IMG");
						if(null != img){
							obj.setAvatar(img.getValue());
						}
			    	}
				} catch (QiniuException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{
				// 无上传头像则取系统默认值
				Params img=ParamsCache.get("AVATAR_DEFAULT_IMG");
				if(null != img){
					obj.setAvatar(img.getValue());
				}
			}
		}
		
		try {
			String qrcode_dir=ParamsCache.get("QRCODE_QINIU_DIR").getValue();
			int width=Integer.valueOf(ParamsCache.get("QRCODE_WIDTH").getValue());
			int height=Integer.valueOf(ParamsCache.get("QRCODE_HEIGHT").getValue());
			String type=StringUtil.isEmpty(ParamsCache.get("QRCODE_TYPE").getValue())?"png":ParamsCache.get("QRCODE_TYPE").getValue();
			String basePath=HttpUtil.getBasePath(request);
			String content=basePath+"member/publics/"+id;
			String key=qrcode_dir+"qrcode_"+id+"."+type;
			byte[] bytes=QrCodeUtil.createQrCode(width, height, type, content);
			if(null!=bytes) {
				uploadManager.put(bytes, key, upToken);
				
				obj.setQrcode("http://"+domain+"/"+key);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String salt=CipherTool.createSalt();
		obj.setSalt(salt);
		//obj.setMemberid(id);
		String pwd=obj.getMpassword();
		obj.setMpassword(MD5Util.generatePassword(pwd+salt));
		obj.setState(1);
		obj.setMtype(0);
		obj.setEmail(obj.getEmail().toLowerCase());
		obj.setAccount(obj.getAccount().toLowerCase());
		
		String ip=AddressUtil.getRealIPAddress(request);
		if(!ip.equals("127.0.0.1")&&!ip.startsWith("192.168.")&&!ip.startsWith("10.")&&!ip.startsWith("172.")&&!ip.startsWith("169.254.")){
			JSONObject json=AddressUtil.ip2Address(ip);
			if(json.getInteger("code")==0){
				JSONObject data=json.getJSONObject("data");
				obj.setCountry(data.getString("country"));
				obj.setProvince(data.getString("region"));
				obj.setCity(data.getString("city"));
			}
		}
		
		Params SITE=ParamsCache.get("MAIN_SITE");
		
		if(StringUtil.isEmpty(obj.getDomain())) obj.setDomain(SITE.getValue()+obj.getAccount());
		else obj.setDomain(SITE.getValue()+obj.getDomain());
		obj.setHasdomain(0);

		//token存在并获取到临时访客记录则直接更新临时会员为正式注册会员
		if(null!=tmp_mem&&tmp_mem.getMtype()==-1){
			//临时访客则设置id，下面更新这条临时访客数据，不插入
			obj.setMemberid(tmp_mem.getMemberid());
			this.updateByPrimaryKeySelective(obj);
		}else{
			obj.setMemberid(id);
			this.insertSelective(obj);
		}
		
		//添加默认好友分组
		ImFriendGroup group=new ImFriendGroup();
		group.setGroupname("我的好友");
		group.setMemberid(obj.getMemberid());
		List<ImFriendGroup> groups=this.imFriendGroupService.selectByEntity(group);
		if(null==groups||groups.size()==0){
			long groupid=IDGenerator.generateId();
			group.setId(groupid);
			this.imFriendGroupService.add(request, group);
		}
		
		//添加ip-memberid映射进redis
		this.ipTmpMemberService.add(ip, obj.getMemberid().toString());

		//生成文件名为独立域名的文件
		String domainDir = PropertiesUtil.getProperty("member.domain");
		String subDomain = obj.getDomain().substring(obj.getDomain().lastIndexOf("/")+1,obj.getDomain().length());
		String path = domainDir+File.separator+subDomain;
		File dirFile = new File(domainDir);
		File file = new File(path);
		if(!dirFile.exists()){
			dirFile.mkdirs();
		}
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return ResultMessage.success("注册成功", obj.getMemberid());
	}

	@Override
	public List<Member> check(Long memberid, String account) {
		return this.mapper.check(memberid, account);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Member> selectByPagination(HttpServletRequest request, Form form) {
		String mtype=request.getParameter("mtype");
		String sex=request.getParameter("sex");
		this.buildSearchCriterion(Member.class, form);
		List<Criterion> criterions=new ArrayList<Criterion>();
		if(StringUtil.isAllNotEmpty(sex)){
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			criterions.add(new Criterion.CriterionBuilder().condition("sex").operator(Operator.EQUAL).leftValue(sex).javaType(Integer.class).build());
		}
		if(StringUtil.isAllNotEmpty(form.getState())){
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			criterions.add(new Criterion.CriterionBuilder().condition("state").operator(Operator.EQUAL).leftValue(form.getState()).javaType(Integer.class).build());
		}
		if(StringUtil.isAllNotEmpty(mtype)){
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			criterions.add(new Criterion.CriterionBuilder().condition("mtype").operator(Operator.EQUAL).leftValue(mtype).javaType(Integer.class).build());
		}
		this.getList().addAll(criterions);
		this.buildOrderByCriterion(Member.class, form);
		this.buildLimitCriterion(Member.class, form);
		return (List<Member>) MapperHelper.toBeanList(this.mapper.selectByPagination(Member.class,this.getList()), Member.class);
	}

	@Override
	public int count(HttpServletRequest request, Form form) {
		String mtype=request.getParameter("mtype");
		String sex=request.getParameter("sex");
		this.buildSearchCriterion(Member.class, form);
		List<Criterion> criterions=new ArrayList<Criterion>();
		if(StringUtil.isAllNotEmpty(sex)){
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			criterions.add(new Criterion.CriterionBuilder().condition("sex").operator(Operator.EQUAL).leftValue(sex).javaType(Integer.class).build());
		}
		if(StringUtil.isAllNotEmpty(form.getState())){
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			criterions.add(new Criterion.CriterionBuilder().condition("state").operator(Operator.EQUAL).leftValue(form.getState()).javaType(Integer.class).build());
		}
		if(StringUtil.isAllNotEmpty(mtype)){
			criterions.add(new Criterion.CriterionBuilder().operator(Operator.AND).build());
			criterions.add(new Criterion.CriterionBuilder().condition("mtype").operator(Operator.EQUAL).leftValue(mtype).javaType(Integer.class).build());
		}
		this.getList().addAll(criterions);
		return this.mapper.count(Member.class,this.getList());
	}

	@Override
	public ResultMessage resetPassword(List<Object> ids,String password) {
		int total=0;
		if(null!=ids&&ids.size()>0){
			if(ids.size()>15) return ResultMessage.failed("一次操作只能重置15个账号");
			for(Object id:ids){
				Member m=this.selectByPrimaryKey(Long.valueOf(id.toString()));
				if(null==m) continue;
				String salt=CipherTool.createSalt();
				String pwd=MD5Util.generatePassword(password+salt);
				m.setSalt(salt);
				m.setMpassword(pwd);
				int result=this.updateByPrimaryKeySelective(m);
				total+=result;
			}
		}
		return ResultMessage.success("重置成功"+total+"个账号");
	}
	
	@Override
	public List<Map<String,Object>> getAllServiceMembers() {
		return this.mapper.getAllServiceMembers();
	}

	@Override
	public List<Map<String,Object>> getAllTmpMembers() {
		return this.mapper.getAllTmpMembers();
	}
	
	@Override
	public ResultMessage init_tmp_member(HttpServletRequest request,HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Cookie[] cookies=request.getCookies();
		String token=null;
		if(null!=cookies&&cookies.length>0){
			for(Cookie co: cookies){
				if(co.getName().equals(IMConstants.COOKIE_TOKEN_KEY)){
					token=co.getValue();
					break;
				}
			}
		}
		if(StringUtil.isEmpty(token)){
			
			Long tmp_memberid=IDGenerator.generateId();
			
			Member member=new Member();
			member.setAccount(tmp_memberid.toString());
			//头像取系统默认值
			Params img=ParamsCache.get("AVATAR_DEFAULT_IMG");
			if(null != img){
				member.setAvatar(img.getValue());
			}
			Params params=ParamsCache.get("DEFAULT_PASSWORD");
			String defaultPwd="12345678";
			if(null!=params) defaultPwd=params.getValue();
			member.setRegistertime(new Date());
			member.setMemberid(tmp_memberid);
			String salt=CipherTool.createSalt();
			member.setSalt(salt);
			member.setMpassword(MD5Util.generatePassword(defaultPwd+salt));
			member.setMtype(-1);
			member.setState(0);
			member.setNickname(tmp_memberid.toString().substring(tmp_memberid.toString().length()-8,tmp_memberid.toString().length()));
			String ip=AddressUtil.getRealIPAddress(request);
			if(!ip.equals("127.0.0.1")&&!ip.startsWith("192.168.")&&!ip.startsWith("10.")&&!ip.startsWith("172.")&&!ip.startsWith("169.254.")){
				JSONObject json=AddressUtil.ip2Address(ip);
				if(json.getInteger("code")==0){
					JSONObject data=json.getJSONObject("data");
					member.setCountry(data.getString("country"));
					member.setProvince(data.getString("region"));
					member.setCity(data.getString("city"));
				}
			}
			this.insertSelective(member);
			
			//添加ip-memberid进redis
			this.ipTmpMemberService.add(ip, tmp_memberid.toString());
			
			//添加默认好友分组
			ImFriendGroup group=new ImFriendGroup();
			group.setGroupname("我的好友");
			long groupid=IDGenerator.generateId();
			group.setId(groupid);
			group.setMemberid(tmp_memberid);
			this.imFriendGroupService.add(request, group);
			
			
			//token存cookie
			Map<String, Object> info = new HashMap<String, Object>();
			info.put(IMConstants.COOKIE_MEMBER_KEY, tmp_memberid);
			token=JWTUtil.buildToken(info);
			info.clear();
			info.put(IMConstants.COOKIE_TOKEN_KEY, token);
			CookieUtil.setCookies(request, response, info, null);
		}
		return ResultMessage.success("",token);
	}

	@Override
	public Member selectByOpenid(String openid) {
		return this.mapper.selectByOpenid(openid);
	}

	@Override
	public Long getMemberIdByPathname(String pathname) {
		return mapper.getMemberIdByPathname(pathname).get(0).getMemberid();
	}
}
