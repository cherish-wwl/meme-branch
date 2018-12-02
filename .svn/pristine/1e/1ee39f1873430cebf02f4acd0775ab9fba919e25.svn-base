package com.meme.member.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.meme.core.base.BaseController;
import com.meme.core.cache.DictCache;
import com.meme.core.cache.ParamsCache;
import com.meme.core.easyui.Pagination;
import com.meme.core.http.ResultMessage;
import com.meme.core.pojo.DictItem;
import com.meme.core.service.DictItemService;
import com.meme.core.util.IDGenerator;
import com.meme.core.util.StringUtil;
import com.meme.im.form.ImForm;
import com.meme.im.pojo.Member;
import com.meme.im.pojo.MemberBgTem;
import com.meme.im.pojo.MemberBillboard;
import com.meme.im.pojo.MemberHobbyType;
import com.meme.im.pojo.MemberPrivate;
import com.meme.im.pojo.MemberRecharge;
import com.meme.im.pojo.MemberSign;
import com.meme.im.pojo.MemberWithdrawals;
import com.meme.im.pojo.MemeWbOrder;
import com.meme.im.pojo.MemeWbOrderSettlement;
import com.meme.im.pojo.MemeWbShop;
import com.meme.im.service.MemberAccountService;
import com.meme.im.service.MemberBgTemService;
import com.meme.im.service.MemberHobbyTypeService;
import com.meme.im.service.MemberPrivateService;
import com.meme.im.service.MemberService;
import com.meme.im.service.MemeWbOrderService;
import com.meme.im.service.MemeWbOrderSettlementService;
import com.meme.im.service.MemeWbShopService;
import com.meme.im.service.impl.MemberCenterService;
import com.meme.im.util.AccountUtil;
import com.meme.qiniu.util.QiniuAPI;
import com.qiniu.common.Zone;
import com.qiniu.storage.UploadManager;

/**
 * 会员中心各项操作接口
 * @author hzl
 *
 */
@Controller
@RequestMapping("/member/center/")
public class MemberCenterController extends BaseController {

	@Resource private MemberCenterService memberCenterService;
	@Resource private DictItemService dictItemService;
	@Resource private MemberHobbyTypeService memberHobbyTypeService;
	@Resource private MemberBgTemService memberBgTemService;
	@Resource private MemberAccountService memberAccountService;
	@Resource private MemberService memberService;
	@Resource private MemberPrivateService memberPrivateService;
	@Resource private MemeWbOrderService memeWbOrderService;
	@Resource private MemeWbShopService memeWbShopService;
	@Resource private MemeWbOrderSettlementService memeWbOrderSettlementService;
	
	/**
	 * 更新头像页面
	 * @param request
	 * @param response
	 */
	@RequestMapping("updateAvatar")
	public ModelAndView updateAvatar(HttpServletRequest request){
		Map<String,Object> model=new HashMap<String, Object>();
		return new ModelAndView("/member/center/updateAvatar",model);
	}
	
	/**
	 * 更新头像
	 * @param request
	 * @param response
	 */
	@RequestMapping("doUpdateAvatar")
	public void doUpdateAvatar(HttpServletRequest request,HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		ResultMessage result=this.memberCenterService.doUpdateAvatar(request);
		try {
			response.getWriter().write(JSON.toJSONString(result));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新背景音乐页面
	 * @param request
	 * @param response
	 */
	@RequestMapping("updateBgMusic")
	public ModelAndView updateBgMusic(HttpServletRequest request){
		Map<String,Object> model=new HashMap<String, Object>();
		return new ModelAndView("/member/center/updateBgMusic",model);
	}
	
	/**
	 * 更新背景音乐
	 * @param request
	 * @param response
	 */
	@RequestMapping("doUpdateBgMusic")
	public void doUpdateBgMusic(HttpServletRequest request,HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		ResultMessage result=this.memberCenterService.doUpdateBgMusic(request);
		try {
			response.getWriter().write(JSON.toJSONString(result));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新公告预告页面
	 * @param request
	 * @param response
	 */
	@RequestMapping("updateBillboard")
	public ModelAndView updateBillboard(HttpServletRequest request){
		Map<String,Object> model=new HashMap<String, Object>();
		long id=IDGenerator.generateId();
		model.put("billboardid", String.valueOf(id));
		return new ModelAndView("/member/center/updateBillboard",model);
	}
	
	/**
	 * 更新公告预告
	 * @param request
	 * @param response
	 */
	@RequestMapping("doUpdateBillboard")
	@ResponseBody
	public void doUpdateBillboard(HttpServletRequest request,HttpServletResponse response,String billboardid,String extlink,String content){
		String type=request.getParameter("type");
		response.setCharacterEncoding("UTF-8");
		ResultMessage result=null;
		if(StringUtil.isEmpty(content)) {
			result=ResultMessage.failed("公告内容不能为空");
			try {
				response.getWriter().write(JSON.toJSONString(result));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return ;
		}
		MemberBillboard record=new MemberBillboard();
		if(StringUtil.isAllNotEmpty(billboardid)) record.setId(billboardid);
		else record.setId(String.valueOf(IDGenerator.generateId()));
		record.setExtlink(extlink);
		record.setContent(content);
		if(StringUtil.isEmpty(type)) type="0";
		record.setType(Integer.valueOf(type));
		result=this.memberCenterService.doUpdateBillboard(request, record);
		try {
			response.getWriter().write(JSON.toJSONString(result));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新公告视频项
	 * @param request
	 * @param response
	 */
	@RequestMapping("doUpdateBillboardItem")
	public void doUpdateBillboardItem(HttpServletRequest request,HttpServletResponse response,String billboardid,String remark,String url,String subject,String body,BigDecimal amount){
		ResultMessage result=this.memberCenterService.doUpdateBillboardItem(request, billboardid, remark, url, subject, body, amount);
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().write(JSON.toJSONString(result));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新签名文章页面
	 * @param request
	 * @param response
	 */
	@RequestMapping("updateSign")
	public ModelAndView updateSign(HttpServletRequest request,String type){
		Map<String,Object> model=new HashMap<String, Object>();
		if(StringUtil.isEmpty(type)) type="0";
		model.put("type", type);
		return new ModelAndView("/member/center/updateSign",model);
	}
	
	/**
	 * 更新签名文章
	 * @param request
	 * @param response
	 */
	@RequestMapping("doUpdateSign")
	@ResponseBody
	public ResultMessage doUpdateSign(HttpServletRequest request,HttpServletResponse response,String type,String title,String content){
		if(StringUtil.isEmpty(type)) return ResultMessage.failed("请确认更新类型是签名或文章");
		MemberSign record=new MemberSign();
		if(type.equals("0")) {
			if(StringUtil.isEmpty(content)) return ResultMessage.failed("签名内容不能为空");
		}else if(type.equals("1")){
			if(StringUtil.isOneEmpty(title,content)) return ResultMessage.failed("文章标题和正文不能为空");
			record.setTitle(title);
		}else return ResultMessage.failed("未确认更新类型是签名还是文章");
		
		record.setType(Integer.valueOf(type));
		record.setContent(content);
		return this.memberCenterService.doUpdateSign(request, record);
	}
	
	/**
	 * 更新相册页面
	 * @param request
	 * @param response
	 */
	@RequestMapping("updateAlbum")
	public ModelAndView updateAlbum(HttpServletRequest request){
		Map<String,Object> model=new HashMap<String, Object>();
		long id=IDGenerator.generateId();
		model.put("albumid", String.valueOf(id));
		return new ModelAndView("/member/center/updateAlbum",model);
	}
	
	/**
	 * 更新相册
	 * @param request
	 * @param response
	 */
	@RequestMapping("doUpdateAlbum")
	@ResponseBody
	public ResultMessage doUpdateAlbum(HttpServletRequest request,HttpServletResponse response,String albumid,String thumb,String url,String subject,String body,BigDecimal amount){
		response.setCharacterEncoding("UTF-8");
		ResultMessage result=this.memberCenterService.doUpdateAlbum(request, albumid, thumb, url, subject, body, amount);
		return result;
	}
	
	/**
	 * 更新背景图片页面
	 * @param request
	 * @param response
	 */
	@RequestMapping("updateBg")
	public ModelAndView updateBg(HttpServletRequest request){
		Map<String,Object> model=new HashMap<String, Object>();
		List<MemberBgTem> list=this.memberBgTemService.getBgTemplates();
		model.put("list", list);
		return new ModelAndView("/member/center/updateBg",model);
	}
	
	@RequestMapping("viewBgTem")
	public ModelAndView viewBgTem(String tempid){
		Map<String,Object> model=new HashMap<String, Object>();
		MemberBgTem object=this.memberBgTemService.selectByPrimaryKey(tempid);
		model.put("object", object);
		return new ModelAndView("/member/center/viewBgTem",model);
	}
	
	/**
	 * 更新背景图片
	 * @param request
	 * @param response
	 */
	@RequestMapping("doUpdateBg")
	public void doUpdateBg(HttpServletRequest request,HttpServletResponse response,String tempid){
		response.setCharacterEncoding("UTF-8");
		ResultMessage result=this.memberCenterService.doUpdateBg(request,tempid);
		try {
			response.getWriter().write(JSON.toJSONString(result));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新兴趣爱好页面
	 * @param request
	 * @param response
	 */
	@RequestMapping("updateHobby")
	public ModelAndView updateHobby(HttpServletRequest request,String type){
		Map<String,Object> model=new HashMap<String, Object>();
		if(StringUtil.isEmpty(type)) type="1";
		model.put("type", type);
		
		Object memberid=AccountUtil.getMemberid(request);
		List<DictItem> list=this.dictItemService.selectByDictgroupcode("ARTICLE_TYPE");
		MemberHobbyType record=new MemberHobbyType();
		record.setMemberid(Long.valueOf(memberid.toString()));
		List<MemberHobbyType> types=this.memberHobbyTypeService.selectByEntity(record);
		if(null!=types&&types.size()>0){
			for(MemberHobbyType t:types){
				if(null!=list&&list.size()>0){
					for(int i=0;i<list.size();i++){
						if(t.getType().toString().equals(list.get(i).getDictitemcode())){
							if(!StringUtil.isEmpty(t.getAlias())) list.get(i).setDictitemname(t.getAlias());
						}
					}
				}
			}
		}
		model.put("list", list);
		
		MemberHobbyType tmp=new MemberHobbyType();
		tmp.setMemberid(Long.valueOf(memberid.toString()));
		tmp.setType(Integer.valueOf(type));
		List<MemberHobbyType> hobbyTypes=this.memberHobbyTypeService.selectByEntity(tmp);
		Integer ctype=null;
		if(null!=hobbyTypes&&hobbyTypes.size()>0){
			ctype=hobbyTypes.get(0).getCtype();
			if(StringUtil.isAllNotEmpty(hobbyTypes.get(0).getAlias())) model.put("typename", hobbyTypes.get(0).getAlias());
			else model.put("typename", DictCache.getDictItem("ARTICLE_TYPE", type).getDictitemname());
		}else{
			model.put("typename", DictCache.getDictItem("ARTICLE_TYPE", type).getDictitemname());
			int t=Integer.valueOf(type);
			switch(t){
			case 1:
				ctype=1;
				break;
			case 2:
				ctype=2;
				break;
			case 3:
				ctype=3;
				break;
			case 4:
				ctype=3;
				break;
			}
		}
		model.put("ctype", ctype);
		
		return new ModelAndView("/member/center/updateHobby",model);
	}
	
	/**
	 * 更新兴趣爱好
	 * @param request
	 * @param response
	 */
	@RequestMapping("doUpdateHobby")
	public void doUpdateHobby(HttpServletRequest request,HttpServletResponse response,
			String title,String summary,Integer type,String extlink,String alias,Integer ctype){
		response.setCharacterEncoding("UTF-8");
		ResultMessage result=this.memberCenterService.doUpdateHobby(request, response, title, summary, type, extlink, alias, ctype);
		try {
			response.getWriter().write(JSON.toJSONString(result));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 上传文件接口
	 * @param request
	 * @param response
	 */
	@RequestMapping("upload")
	public void upload(HttpServletRequest request,HttpServletResponse response){
		ResultMessage res=null;
		CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(request.getSession().getServletContext());
		if(multipartResolver.isMultipart(request)){
			MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
			Iterator<String> it=multiRequest.getFileNames();
			while(it.hasNext()){
				MultipartFile file=multiRequest.getFile(it.next());
				String name=file.getOriginalFilename();
				String suffix=name.substring(name.lastIndexOf(".")+1,name.length());
				try {
					if(null!=file.getBytes()&&file.getBytes().length>0){
						String bucket=ParamsCache.get("MEMBER_BUCKET").getValue();
						String prefix=ParamsCache.get("MEMBER_CENTER_DIR")==null?"":ParamsCache.get("MEMBER_CENTER_DIR").getValue();
						String hash=UUID.randomUUID().toString().replaceAll("-", "");
						String key = null;
						UploadManager uploadManager = QiniuAPI.getUploadManager(Zone.autoZone());
						String upToken = QiniuAPI.getUploadToken(bucket,null);
				    	if(StringUtil.isEmpty(prefix)) key=hash+"."+suffix;
						else key=(prefix.endsWith("/")?prefix:(prefix+"/"))+hash+"."+suffix;
						uploadManager.put(file.getBytes(), key, upToken);
						
						ResultMessage result=QiniuAPI.getBucketDomains(bucket);
//						String msg=result.getMessage();
//						if(result.getState().equals("0")) return ResultMessage.failed("获取七牛空间域名失败："+msg);
						String[] domains=(String[]) result.getData();
						String domain=null;
						if(null!=domains&&domains.length>0) domain=domains[0];
						
						res=ResultMessage.success("","http://"+domain+"/"+key);
					}
				}catch (Exception e) {
					e.printStackTrace();
					res=ResultMessage.failed(e.getMessage());
				}
			}
		}else{
			res=ResultMessage.failed("未检测到上传文件，请重新选择");
		}
		
		response.setCharacterEncoding("UTF-8");
		try {
			if(null==res){
				response.getWriter().write(ResultMessage.failed("上传失败，请稍后重试或者联系管理员").toString());
			}else{
				response.getWriter().write(res.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取当前会员的栏目标题数据
	 * @param request
	 * @return
	 */
	@RequestMapping("getMemberHobbyGroup")
	@ResponseBody
	public Object getMemberHobbyGroup(HttpServletRequest request){
		Object memberid=AccountUtil.getMemberid(request);
		List<DictItem> list=this.dictItemService.selectByDictgroupcode("ARTICLE_TYPE");
		MemberHobbyType record=new MemberHobbyType();
		record.setMemberid(Long.valueOf(memberid.toString()));
		List<MemberHobbyType> types=this.memberHobbyTypeService.selectByEntity(record);
		if(null!=types&&types.size()>0){
			for(MemberHobbyType t:types){
				if(null!=list&&list.size()>0){
					for(int i=0;i<list.size();i++){
						if(t.getType().toString().equals(list.get(i).getDictitemcode())){
							if(!StringUtil.isEmpty(t.getAlias())) list.get(i).setDictitemname(t.getAlias());
						}
					}
				}
			}
		}
		return list;
	}
	
	/**
	 * 
	 * @param request
	 * @param type
	 * @return
	 */
	@RequestMapping("getHobbyContenttype")
	@ResponseBody
	public Object getHobbyContenttype(HttpServletRequest request,Integer type){
		Object memberid=AccountUtil.getMemberid(request);
		MemberHobbyType record=new MemberHobbyType();
		record.setMemberid(Long.valueOf(memberid.toString()));
		record.setType(type);
		List<MemberHobbyType> list=this.memberHobbyTypeService.selectByEntity(record);
		if(null!=list&&list.size()>0) return list.get(0);
		return null;
	}
	
	/**
	 * 更新会员栏目标题
	 * @param request
	 * @param alias
	 * @param ctype
	 * @param type
	 * @return
	 */
	@RequestMapping("doUpdateHobbyType")
	@ResponseBody
	public ResultMessage doUpdateHobbyType(HttpServletRequest request,String alias,Integer ctype,Integer type){
		return this.memberCenterService.doUpdateHobbyType(request, alias, ctype, type);
	}
	
	/**
	 * 更换栏目标题
	 * @param request
	 * @param type
	 * @return
	 */
	@RequestMapping("updateSection")
	public ModelAndView updateSection(HttpServletRequest request){
		Map<String,Object> model=new HashMap<String, Object>();
		
		return new ModelAndView("/member/center/updateSection",model);
	}
	
	@RequestMapping("doUpdateSection")
	@ResponseBody
	public ResultMessage doUpdateSection(HttpServletRequest request,HttpServletResponse response,String sectionname){
		return this.memberCenterService.doUpdateSection(request, response, sectionname);
	}
	
	@RequestMapping("recharge")
	@ResponseBody
	public ResultMessage recharge(HttpServletRequest request,HttpServletResponse response,Integer type,String orderid){
		return this.memberAccountService.recharge(request, response, type, orderid);
	}
	
	@RequestMapping("withdrawals")
	@ResponseBody
	public ResultMessage withdrawals(HttpServletRequest request,HttpServletResponse response,Integer type,Double money){
		return this.memberAccountService.withdrawals(request, response, type, money);
	}
	
	@RequestMapping("rechargeQuery")
	@ResponseBody
	public List<MemberRecharge> rechargeQuery(HttpServletRequest request,HttpServletResponse response){
		Long memberid=(Long)AccountUtil.getMemberid(request);
		List<MemberRecharge> list=this.memberAccountService.doRechargeQuery(memberid);
		return list;
	}
	
	@RequestMapping("withdrawalsQuery")
	@ResponseBody
	public List<MemberWithdrawals> withdrawalsQuery(HttpServletRequest request,HttpServletResponse response){
		Long memberid=(Long)AccountUtil.getMemberid(request);
		List<MemberWithdrawals> list=this.memberAccountService.doWithdrawalsQuery(memberid);
		return list;
	}
	
	@RequestMapping("memberOrderQuery")
	@ResponseBody
	public List<Map<String,Object>> memberOrderQuery(HttpServletRequest request,HttpServletResponse response){
		Long memberid=(Long)AccountUtil.getMemberid(request);
		return memeWbOrderService.selectMemberOrders(memberid);
	}
	
	@RequestMapping("wbMemberOrderQuery")
	@ResponseBody
	public Pagination wbMemberOrderQuery(HttpServletRequest request,ImForm form){
		Long memberid=(Long)AccountUtil.getMemberid(request);
		form.setMemberid(memberid);
		List<Map<String,Object>> list=this.memeWbOrderService.selectByPaginationView(form);
		int count=this.memeWbOrderService.countView(form);
		Pagination pagination=new Pagination(list,count);
		return pagination;
	}
	
	@RequestMapping("wbOrderQuerySettlement")
	@ResponseBody
	public List<Map<String,Object>> wbOrderQuerySettlement(HttpServletRequest request,Integer state){
		Long memberid=(Long)AccountUtil.getMemberid(request);
		return memeWbOrderSettlementService.doWbOrderQuery(memberid,state);
	}
	
	@RequestMapping("updateMemberPayInfo")
	@ResponseBody
	public ResultMessage updateMemberPayInfo(HttpServletRequest request,String payaccount,String paynickname) {
		Long memberid=(Long)AccountUtil.getMemberid(request);
		Member member = new Member();
		member.setMemberid(memberid);
		member.setPayaccount(payaccount);
		member.setPaynickname(paynickname);
		memberService.updateByPrimaryKeySelective(member);
		return ResultMessage.defaultSuccessMessage();
	}
	
	@RequestMapping("updateMemberInfo")
	@ResponseBody
	public ResultMessage updateMemberInfo(HttpServletRequest request,Member member) {
		Long memberid=(Long)AccountUtil.getMemberid(request);
		member.setMemberid(memberid);
		memberService.updateByPrimaryKeySelective(member);
		return ResultMessage.defaultSuccessMessage();
	}
	
	@RequestMapping("updateMemberPrivateInfo")
	@ResponseBody
	public ResultMessage updateMemberPrivateInfo(HttpServletRequest request,MemberPrivate member) {
		Long memberid=(Long)AccountUtil.getMemberid(request);
		member.setMemberid(memberid);
		memberPrivateService.updateByPrimaryKeySelective(member);
		return ResultMessage.defaultSuccessMessage();
	}
	
	@RequestMapping("getMemberPrivateInfo")
	@ResponseBody
	public ResultMessage getMemberPrivateInfo(HttpServletRequest request) {
		Long memberid=(Long)AccountUtil.getMemberid(request);
		MemberPrivate memberPrivate = memberPrivateService.selectByPrimaryKey(memberid);
		return ResultMessage.success("操作成功", memberPrivate);
	}
	
	@RequestMapping("sendMessage")
	@ResponseBody
	public ResultMessage sendMessage(HttpServletRequest req,String phone) throws Exception{
		//设置超时时间-可自行调整
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");
		//初始化ascClient需要的几个参数
		final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
		final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
		//替换成你的AK
		final String accessKeyId = ParamsCache.get("ALIYUN_ACCESS_KEY").getValue();//你的accessKeyId,参考本文档步骤2
		final String accessKeySecret = ParamsCache.get("ALIYUN_ACCESS_SECRET").getValue();//你的accessKeySecret，参考本文档步骤2
		//初始化ascClient,暂时不支持多region（请勿修改）
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
		accessKeySecret);
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		IAcsClient acsClient = new DefaultAcsClient(profile);
		 //组装请求对象
		 SendSmsRequest request = new SendSmsRequest();
		 //使用post提交
		 request.setMethod(MethodType.POST);
		 //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为00+国际区号+号码，如“0085200000000”
		 request.setPhoneNumbers(phone);
		 //必填:短信签名-可在短信控制台中找到
		 request.setSignName("上海么么社区");
		 //必填:短信模板-可在短信控制台中找到
		 request.setTemplateCode("SMS_134160076");
		 //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		 //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
		 String charValue = "";
        for (int i = 0; i < 6; i++) {
        		Random random = new Random();
            charValue += String.valueOf(random.nextInt(10));
        }
		 request.setTemplateParam("{\"code\":\""+charValue+"\"}");
		 //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
		 //request.setSmsUpExtendCode("90997");
		 //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		 //request.setOutId("yourOutId");
		//请求失败这里会抛ClientException异常
		SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
		if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
			req.getSession().setAttribute("code", charValue);
		}else {
			return ResultMessage.defaultFaileMessage();
		}
		return ResultMessage.defaultSuccessMessage();
	}
	
	@RequestMapping("validateCode")
	@ResponseBody
	public ResultMessage validateCode(HttpServletRequest request,String paramCode,String phone) {
		String code = (String)request.getSession().getAttribute("code");
		if(code != null && code.equals(paramCode)) {
			Long memberid=(Long)AccountUtil.getMemberid(request);
			Member member = new Member();
			member.setMemberid(memberid);
			member.setCellphone(phone);
			memberService.updateByPrimaryKeySelective(member);
			return ResultMessage.defaultSuccessMessage();
		}else {
			return ResultMessage.failed("验证码错误");
		}
	}
	
	//微商发货接口
	@RequestMapping("deliverGoods")
	@ResponseBody
	public ResultMessage deliverGoods(HttpServletRequest request,String orderid,String courier_number,String carrier) {
		Long memberid=(Long)AccountUtil.getMemberid(request);
		MemeWbOrder memeWbOrder = memeWbOrderService.selectByPrimaryKey(orderid);
		MemeWbShop memeWbShop = memeWbShopService.selectByPrimaryKey(memeWbOrder.getProductid());
		if(memeWbShop.getMemberid().equals(memberid)) {
			memeWbOrder.setCourier_number(courier_number);
			memeWbOrder.setCarrier(carrier);
			memeWbOrder.setDeliver_time(new Date());
			memeWbOrder.setState(2);
			memeWbOrderService.updateByPrimaryKeySelective(memeWbOrder);
			return ResultMessage.defaultSuccessMessage();
		}else {
			return ResultMessage.failed("未查到该微商订单");
		}
	}
	
	//用户确认收货接口
	@RequestMapping("collectGoods")
	@ResponseBody
	public ResultMessage collectGoods(HttpServletRequest request,String orderid) {
		Long memberid=(Long)AccountUtil.getMemberid(request);
		MemeWbOrder memeWbOrder = memeWbOrderService.selectByPrimaryKey(orderid);
		MemeWbShop memeWbShop = memeWbShopService.selectByPrimaryKey(memeWbOrder.getProductid());
		if(memeWbOrder.getMemberid().equals(memberid)) {
			memeWbOrder.setCollect_time(new Date());
			memeWbOrder.setState(3);
			memeWbOrderService.updateByPrimaryKeySelective(memeWbOrder);
			MemeWbOrderSettlement memeWbOrderSettlement = new MemeWbOrderSettlement();
			memeWbOrderSettlement.setId(String.valueOf(IDGenerator.generateId()));
			memeWbOrderSettlement.setWb_memberid(memeWbShop.getMemberid());
			memeWbOrderSettlement.setOrderid(orderid);
			memeWbOrderSettlement.setSettlement_state(1);
			memeWbOrderSettlement.setMemberid(memberid);
			memeWbOrderSettlementService.insertSelective(memeWbOrderSettlement);
			return ResultMessage.defaultSuccessMessage();
		}else {
			return ResultMessage.failed("未查到该用户订单");
		}
	}
	
	//用户申请退货接口
	@RequestMapping("applyReturnGoods")
	@ResponseBody
	public ResultMessage applyReturnGoods(HttpServletRequest request,String orderid,String r_reason) {
		Long memberid=(Long)AccountUtil.getMemberid(request);
		MemeWbOrder memeWbOrder = memeWbOrderService.selectByPrimaryKey(orderid);
		MemeWbShop memeWbShop = memeWbShopService.selectByPrimaryKey(memeWbOrder.getProductid());
		if(memeWbOrder.getMemberid().equals(memberid)) {
			memeWbOrder.setR_state(0);
			memeWbOrder.setR_reason(r_reason);
			memeWbOrder.setR_apply_time(new Date());
			memeWbOrderService.updateByPrimaryKeySelective(memeWbOrder);
			MemeWbOrderSettlement memeWbOrderSettlement = new MemeWbOrderSettlement();
			memeWbOrderSettlement.setId(String.valueOf(IDGenerator.generateId()));
			memeWbOrderSettlement.setWb_memberid(memeWbShop.getMemberid());
			memeWbOrderSettlement.setOrderid(orderid);
			memeWbOrderSettlement.setSettlement_state(3);
			memeWbOrderSettlement.setMemberid(memberid);
			memeWbOrderSettlementService.insertSelective(memeWbOrderSettlement);
			return ResultMessage.defaultSuccessMessage();
		}else {
			return ResultMessage.failed("未查到该用户订单");
		}
	}
	
	//退货处理接口
	@RequestMapping("handleReturnGoods")
	@ResponseBody
	public ResultMessage handleReturnGoods(HttpServletRequest request,String orderid,Integer r_state,String handling_opinions) {
		Long memberid=(Long)AccountUtil.getMemberid(request);
		MemeWbOrder memeWbOrder = memeWbOrderService.selectByPrimaryKey(orderid);
		MemeWbShop memeWbShop = memeWbShopService.selectByPrimaryKey(memeWbOrder.getProductid());
		if(memeWbShop.getMemberid().equals(memberid)) {
			memeWbOrder.setR_state(r_state);
			memeWbOrder.setHandling_opinions(handling_opinions);
			memeWbOrderService.updateByPrimaryKeySelective(memeWbOrder);
			return ResultMessage.defaultSuccessMessage();
		}else {
			return ResultMessage.failed("未查到该微商订单");
		}
	}
	
	//退货发出
	@RequestMapping("returnOutOfGoods")
	@ResponseBody
	public ResultMessage returnOutOfGoods(HttpServletRequest request,String orderid) {
		Long memberid=(Long)AccountUtil.getMemberid(request);
		MemeWbOrder memeWbOrder = memeWbOrderService.selectByPrimaryKey(orderid);
		if(memeWbOrder.getMemberid().equals(memberid)) {
			memeWbOrder.setR_state(3);
			memeWbOrderService.updateByPrimaryKeySelective(memeWbOrder);
			return ResultMessage.defaultSuccessMessage();
		}else {
			return ResultMessage.failed("未查到该用户订单");
		}
	}
	
	//退货完成接口
	@RequestMapping("completeReturnGoods")
	@ResponseBody
	public ResultMessage completeReturnGoods(HttpServletRequest request,String orderid) {
		Long memberid=(Long)AccountUtil.getMemberid(request);
		MemeWbOrder memeWbOrder = memeWbOrderService.selectByPrimaryKey(orderid);
		if(memeWbOrder.getMemberid().equals(memberid)) {
			memeWbOrder.setR_state(4);
			memeWbOrder.setR_complete_time(new Date());
			memeWbOrderService.updateByPrimaryKeySelective(memeWbOrder);
			MemeWbOrderSettlement memeWbOrderSettlement = new MemeWbOrderSettlement();
			memeWbOrderSettlement.setOrderid(orderid);
			memeWbOrderSettlement.setSettlement_state(3);
			List<MemeWbOrderSettlement> list = memeWbOrderSettlementService.selectByEntity(memeWbOrderSettlement);
			if(list.size()>0) {
				MemeWbOrderSettlement memeWbOrderSettlement2 = list.get(0);
				memeWbOrderSettlement2.setSettlement_state(4);
				memeWbOrderSettlement2.setSettlement_time(new Date());
				memeWbOrderSettlementService.updateByPrimaryKeySelective(memeWbOrderSettlement2);
			}
			return ResultMessage.defaultSuccessMessage();
		}else {
			return ResultMessage.failed("未查到该微商订单");
		}
	}
}
