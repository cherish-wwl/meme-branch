package com.meme.im.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.meme.core.base.BaseController;
import com.meme.core.base.IController;
import com.meme.core.cache.DictCache;
import com.meme.core.cache.ParamsCache;
import com.meme.core.easyui.Pagination;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.core.log.LogType;
import com.meme.core.log.annotation.SysLog;
import com.meme.core.util.StringUtil;
import com.meme.core.validator.ValidateBuilder;
import com.meme.core.validator.entity.ColumnRule;
import com.meme.im.pojo.Member;
import com.meme.im.pojo.MemberView;
import com.meme.im.service.MemberService;
import com.meme.im.service.MemeWbOrderService;
import com.meme.qiniu.util.QiniuAPI;
import com.qiniu.common.Zone;
import com.qiniu.storage.UploadManager;

/**
 * 会员管理控制器
 * @author hzl
 *
 */
@Controller
@RequestMapping("/im/member/")
public class IMMemberController extends BaseController implements IController{
	
	@Resource private MemberService memberService;

	@Resource private MemeWbOrderService memeWbOrderService;
	
	@RequestMapping(value="loadValidateRules")
	@ResponseBody
	public String loadValidationRules(){
		ValidateBuilder.clear();
		
		ColumnRule rule=new ColumnRule();
		rule.setColumn("account");
		Map<String,String> ruleMap=new LinkedHashMap<String, String>();
		ruleMap.put("required", "true");
		ruleMap.put("byteRangeLength", "[1,50]");
		rule.setRules(ruleMap);
		ValidateBuilder.addRules(rule);
		
//		rule = new ColumnRule();
//		rule.setColumn("email");
//		ruleMap=new LinkedHashMap<String, String>();
//		ruleMap.put("email", "true");
//		rule.setRules(ruleMap);
//		ValidateBuilder.addRules(rule);

		rule = new ColumnRule();
		rule.setColumn("mpassword");
		ruleMap=new LinkedHashMap<String, String>();
		ruleMap.put("required", "true");
		ruleMap.put("minlength", "8");
		rule.setRules(ruleMap);
		ValidateBuilder.addRules(rule);

		rule = new ColumnRule();
		rule.setColumn("cmpassword");
		ruleMap=new LinkedHashMap<String, String>();
		ruleMap.put("required", "true");
		ruleMap.put("minlength", "8");
		rule.setRules(ruleMap);
		ValidateBuilder.addRules(rule);
		
		String js=ValidateBuilder.createJsValidateRules(Member.class);
		return js;
	}
	
	@RequestMapping("index")
	public ModelAndView index(){
		Map<String, Object> model = new HashMap<String, Object>();
		
		return new ModelAndView("/im/member/list",model);
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Pagination list(HttpServletRequest request,Form form) throws IllegalAccessException, InvocationTargetException{
		List<Member> list=this.memberService.selectByPagination(request,form);
		List<MemberView> views=new ArrayList<MemberView>();
		if(null!=list&&list.size()>0){
			for(Member m:list){
				MemberView v=new MemberView();
				BeanUtils.copyProperties(v, m);
				if(v.getSex()!=null) v.setSexText(DictCache.getDictItem("SEX", v.getSex().toString()).getDictitemname());
				if(v.getMtype()!=null) v.setMtypeText(DictCache.getDictItem("MEMBER_ACCOUNT_TYPE",v.getMtype().toString()).getDictitemname());
				if(v.getState()!=null) v.setStateText(DictCache.getDictItem("MEMBER_ACCOUNT_STATE",v.getState().toString()).getDictitemname());
				views.add(v);
			}
		}
		int count=this.memberService.count(request,form);
		Pagination pagination=new Pagination();
		pagination.setRows(views);
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
	@SysLog(event = "删除会员",type=LogType.DELETE)
	public ResultMessage delete(@RequestParam("ids[]") List<Object> ids){
        return this.memberService.delete(ids);
	}
	
	@RequestMapping("add")
	public ModelAndView add(){
		Map<String,Object> model=new HashMap<String, Object>();
		
		return new ModelAndView("/im/member/add",model);
	}
	
	@RequestMapping("doAdd")
	@ResponseBody
	@SysLog(event = "添加客服账号",type=LogType.ADD)
	public ResultMessage doAdd(HttpServletRequest request,HttpServletResponse response,Member obj){
		return this.memberService.add(request, obj);
	}
	
	@RequestMapping("edit")
	public ModelAndView edit(String memberid){
		Map<String,Object> model=new HashMap<String, Object>();
		if(StringUtil.isAllNotEmpty(memberid)){
			Member m=this.memberService.selectByPrimaryKey(Long.valueOf(memberid));
			if(null!=m) model.put("object", m);
		}
		
		return new ModelAndView("/im/member/edit",model);
	}
	
	@RequestMapping("doEdit")
	@ResponseBody
	@SysLog(event = "修改客服账号",type=LogType.UPDATE)
	public ResultMessage doEdit(HttpServletRequest request,HttpServletResponse response,Member obj){
		return this.memberService.edit(request, obj);
	}
	
	/**
	 * 重置密码，一次限制只能重置15个账号
	 * @param ids
	 * @param password
	 * @return
	 */
	@RequestMapping("resetPassword")
	@ResponseBody
	@SysLog(event = "重置密码",type=LogType.UPDATE)
	public ResultMessage resetPassword(@RequestParam(value="ids[]",required=true) List<Object> ids,@RequestParam(value="password",required=true)String password){
		if(StringUtil.isEmpty(password)) return ResultMessage.failed("请填写需要重置的密码");
		return this.memberService.resetPassword(ids,password);
	}
	
	@RequestMapping("upload")
	public void upload(HttpServletRequest request,HttpServletResponse response){
		ResultMessage res=null;
		CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(request.getSession().getServletContext());
		if(multipartResolver.isMultipart(request)){
			MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
			Map<String, MultipartFile> map=multiRequest.getFileMap();
			Iterator<String> it=map.keySet().iterator();
			while(it.hasNext()){
				MultipartFile file=map.get(it.next());
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
	
	@RequestMapping("wbOrderQuery")
	public ModelAndView wbOrderQuery(Long memberid){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("memberid", memberid);
		return new ModelAndView("/im/member/wbOrder",model);
	}
	
	@RequestMapping("doWbOrderQuery")
	@ResponseBody
	public List<Map<String,Object>> doWbOrderQuery(Long memberid){
		return memeWbOrderService.selectMemberOrders(memberid);
	}
}