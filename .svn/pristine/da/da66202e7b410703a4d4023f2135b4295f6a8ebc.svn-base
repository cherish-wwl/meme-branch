package com.meme.system.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meme.core.base.BaseController;
import com.meme.core.cache.ParamsCache;
import com.meme.core.easyui.AccordionMenu;
import com.meme.core.easyui.MenuTool;
import com.meme.core.easyui.TreeNode;
import com.meme.core.log.LogType;
import com.meme.core.log.annotation.SysLog;
import com.meme.core.pojo.LoginAccountInfoView;
import com.meme.core.pojo.LoginAccountRoleView;
import com.meme.core.pojo.Menu;
import com.meme.core.pojo.Params;
import com.meme.core.pojo.Platform;
import com.meme.core.service.LogService;
import com.meme.core.service.LoginAccountRoleService;
import com.meme.core.service.LoginAccountService;
import com.meme.core.service.MenuService;
import com.meme.core.service.PlatformService;
import com.meme.core.util.AddressUtil;
import com.meme.core.util.Constants;
import com.meme.core.util.DateUtil;
import com.meme.core.util.JWTUtil;
import com.meme.core.util.MD5Util;
import com.meme.core.util.StringUtil;
import com.meme.core.util.ValidateCodeUtil;

@Controller("SystemLoginController")
@RequestMapping("/system/")
public class LoginController extends BaseController{

	@Resource private LoginAccountService loginAccountService;
	@Resource private LogService logService;
	@Resource private LoginAccountRoleService loginAccountRoleService;
	@Resource private PlatformService platformService;
	@Resource private MenuService menuService;
	
	@RequestMapping("index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
//		Cookie[] cookies=request.getCookies();
//		if(null!=cookies&&cookies.length>0){
//			for(Cookie co: cookies){
//				System.out.println("=============="+co.getName()+"="+co.getValue());
//			}
//		}
//		Cookie cookie=new Cookie("islogin", "1111");
//		cookie.setDomain(".mmgmmj.com");
//		//path设为/，使顶级域名和二级域名cookie共享
//		cookie.setPath("/");
//		cookie.setMaxAge(3600);
//		response.addCookie(cookie);
		return new ModelAndView("/system/index");
	}
	
	@RequestMapping("login")
	@SysLog(event = "登录",type=LogType.OTHERS)
	public ModelAndView login(HttpServletRequest request,HttpServletResponse response,String account,String password,String validateCode) throws Exception{
		Map<String, Object>  message = new HashMap<String, Object>();
		Object obj = request.getSession().getAttribute(Constants.SESSION_USER);
		if(null!=obj){
			return new ModelAndView("redirect:/system/main.do");
		}
		
		if(StringUtil.isOneEmpty(account,password)){
			message.put("message", "用户名、密码不能为空");
			return new ModelAndView("/system/index", message);
		}
		
		Params vcodeSwitch=ParamsCache.get("VALIDATECODE_SWITCH");
		if(null!=vcodeSwitch){
			if(Integer.valueOf(vcodeSwitch.getValue())==1){
				if(StringUtil.isOneEmpty(account,password,validateCode)){
					message.put("message", "用户名、密码和验证码不能为空");
					return new ModelAndView("/system/index", message);
				}
				String vcode=(String)request.getSession().getAttribute(Constants.SESSION_VALIDATE_CODE);
				if(StringUtil.isEmpty(vcode)){
					message.put("message", "验证码已过期");
					return new ModelAndView("/system/index", message);
				}
				if(!vcode.equals(validateCode.toUpperCase())){
					message.put("message", "验证码不正确");
					return new ModelAndView("/system/index", message);
				}
			}
		}
		
		List<LoginAccountInfoView> list=this.loginAccountService.isExist(account,null);
		if(list==null||list.size()==0){
			message.put("message", "用户名不存在");
			return new ModelAndView("/system/index", message);
		}
		if(list.size()>1) {
			message.put("message", "账号存在异常重复，请联系管理员");
			this.logService.log(request, LogType.EXCEPTION.getType(), LogType.EXCEPTION.getType(), "此账号存在账号名、邮箱、手机号码重复，请检查数据库");
			return new ModelAndView("/system/index", message);
		}
		
		LoginAccountInfoView user=list.get(0);
		
		if(user.getState()==1){
			message.put("message", "此账号已被禁用，无法登录");
			return new ModelAndView("/system/index", message);
		}
		
		if(user.getOrgstate()==0){
			message.put("message", "此账号所属单位未经过认证，无法登录");
			return new ModelAndView("/system/index", message);
		}
		if(user.getOrgstate()==2){
			message.put("message", "此账号所属单位已被禁用，无法登录");
			return new ModelAndView("/system/index", message);
		}
		
		String currentDate=DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss");
		if(null==user.getOrganopentime()){
			if(null!=user.getOrganendtime()){
				String endtime=DateUtil.format(user.getOrganendtime(), "yyyy-MM-dd HH:mm:ss");
				if(DateUtil.compare(currentDate, endtime)>=0){
					message.put("message", "此账号所属单位已于"+endtime+"过期，请联系管理员续期使用");
					return new ModelAndView("/system/index", message);
				}
			}
		}else{
			String opentime=DateUtil.format(user.getOrganopentime(), "yyyy-MM-dd HH:mm:ss");
			if(null==user.getOrganendtime()){
				if(DateUtil.compare(currentDate, opentime)<0){
					message.put("message", "此账号所属单位正式启用日期为"+opentime+"，请耐心等待");
					return new ModelAndView("/system/index", message);
				}
			}else{
				String endtime=DateUtil.format(user.getOrganendtime(), "yyyy-MM-dd HH:mm:ss");
				if(DateUtil.compare(currentDate, opentime)<0){
					message.put("message", "此账号所属单位正式启用日期为"+opentime+"，请耐心等待");
					return new ModelAndView("/system/index", message);
				}
				if(DateUtil.compare(currentDate, endtime)>=0){
					message.put("message", "此账号所属单位已于"+endtime+"过期，请联系管理员续期使用");
					return new ModelAndView("/system/index", message);
				}
			}
		}
		
		//用户密码加盐值验证
		String salt=StringUtil.isEmpty(user.getSalt())?"":user.getSalt();
		if(!MD5Util.generatePassword(password+salt).equals(user.getPassword())){
			message.put("message", "密码错误");
			return new ModelAndView("/system/index", message);
		}
		
		request.getSession().removeAttribute(Constants.SESSION_VALIDATE_CODE);
		request.getSession().setAttribute(Constants.SESSION_USER, user);
		
//		//token存cookie
//		Map<String, Object> info = new HashMap<String, Object>();
//		info.put("loginid", user.getLoginid());
//		String token=JWTUtil.buildToken(info);
//		Cookie cookie=new Cookie("token", token);
//		String domain=AddressUtil.getRootDomain(request.getRequestURL().toString());
//		cookie.setDomain(domain==null?"127.0.0.1":("."+domain));
//		//path设为/，使顶级域名和二级域名cookie共享
//		cookie.setPath("/");
//		//七天后过期
//		cookie.setMaxAge(604800);
//		response.addCookie(cookie);
		
		return new ModelAndView("redirect:/system/main.do");
	}
	
	@RequestMapping("main")
	public ModelAndView main(HttpServletRequest request,HttpServletResponse response,Long platformid){
		Map<String, Object> message = new HashMap<String, Object>();
		Object obj=request.getSession().getAttribute(Constants.SESSION_USER);
		if(null==obj){
			request.getSession().invalidate();
			return new ModelAndView("/system/index");
		}
		LoginAccountInfoView user=(LoginAccountInfoView) obj;
		List<LoginAccountRoleView> roles=this.loginAccountRoleService.selectByLoginId(user.getLoginid());
		List<LoginAccountRoleView> activeroles=new ArrayList<LoginAccountRoleView>();
		if(null!=roles&&roles.size()>0) {
			for(LoginAccountRoleView role:roles){
				String currentDate=DateUtil.getCurrentDate("yyyy-MM-dd HH:mm:ss");
				//判断当前时间在此角色可用期限内，起止时间为空时不限制边界
				if(null==role.getStarttime()){
					if(null==role.getEndtime()) activeroles.add(role);
					else{
						String endtime=DateUtil.format(role.getEndtime(), "yyyy-MM-dd HH:mm:ss");
						if(DateUtil.compare(currentDate, endtime)<0) activeroles.add(role);
					}
				}else{
					String starttime=DateUtil.format(role.getStarttime(), "yyyy-MM-dd HH:mm:ss");
					if(null==role.getEndtime()) {
						if(DateUtil.compare(currentDate, starttime)>=0) activeroles.add(role);
					}else{
						String endtime=DateUtil.format(role.getEndtime(), "yyyy-MM-dd HH:mm:ss");
						if(DateUtil.compare(currentDate, starttime)>=0&&DateUtil.compare(currentDate, endtime)<0) activeroles.add(role);
					}
				}
			}
		}
		if(activeroles.size()==0){
			message.put("message", "暂无可用角色，请确认已授权相关角色或者授权角色在可用期限内，请联系管理员确认");
			request.getSession().invalidate();
			return new ModelAndView("/system/index", message);
		}
		//session存入可用角色
		request.getSession().setAttribute(Constants.SESSION_ROLES, activeroles);
		
		List<Object> roleids=new ArrayList<Object>();
		for(LoginAccountRoleView r:activeroles){
			roleids.add(r.getRoleid());
		}
//		//session存入角色id数组
//		request.getSession().setAttribute(Constants.SESSION_ROLES, roleids);
		List<Platform> platforms= this.platformService.selectByRoleids(roleids);
		
		Platform defaultplatform=null;
		boolean flag=false;
		if(null!=platformid){
			for(Platform p:platforms){
				if(p.getId().longValue()==platformid.longValue()){flag=true;defaultplatform=p;break;}
			}
		}
		//切换未授权平台时默认取第一个平台
		if(!flag) defaultplatform=platforms.get(0);
		
		List<Menu> menus=this.menuService.selectLoginAccountMenus(roleids, defaultplatform.getId(),null);
		if(null==menus||menus.size()==0){
			message.put("message", "此用户拥有的角色未授权相关功能，暂时无法使用，请联系管理员进行授权");
			request.getSession().invalidate();
			return new ModelAndView("/system/index", message);
		}
		
//		request.getSession().setAttribute(Constants.SESSION_MENUS,menus);
//		request.getSession().setAttribute("defaultplatform",defaultplatform);
		request.getSession().setAttribute(Constants.SESSION_PLATFORMS,platforms);
		
//		message.put(Constants.SESSION_MENUS,menus);
//		message.put(Constants.SESSION_MENUS, this.buildMenu(request,menus));
		message.put("defaultplatform",defaultplatform);
		message.put(Constants.SESSION_PLATFORMS,platforms);
		
		return new ModelAndView("/system/main",message);
	}
	
	@RequestMapping("buildTree")
	public ModelAndView buildTree(HttpServletRequest request,Long platformid){
		return new ModelAndView("/system/platform/test");
	}
	
	/**
	 * 构建easyui树json
	 * @param platformid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("getTree")
	@ResponseBody
	public JSONArray getTree(HttpServletRequest request,Long platformid){
		List<LoginAccountRoleView> activeroles=(List<LoginAccountRoleView>) request.getSession().getAttribute(Constants.SESSION_ROLES);
		List<Object> roleids=new ArrayList<Object>();
		for(LoginAccountRoleView r:activeroles){
			roleids.add(r.getRoleid());
		}

		List<Platform> platforms=(List<Platform>) request.getSession().getAttribute(Constants.SESSION_PLATFORMS);
		if(null==platformid||platformid==0l) platformid=platforms.get(0).getId();
		else{
			boolean flag=false;
			//判断是否拥有此平台授权，无则默认取第一个平台数据
			for(Platform p:platforms){
				if(p.getId().longValue()==platformid.longValue()) {flag=true;break;}
			}
			if(!flag) platformid=platforms.get(0).getId();
		}
		
		List<Menu> menus=this.menuService.selectLoginAccountMenus(roleids, platformid,null);
		LinkedHashMap<Long,AccordionMenu> map=new LinkedHashMap<Long, AccordionMenu>();
		if(null!=menus&&menus.size()>0){
			for(Menu m:menus){
				if(m.getPid().longValue()==0l&&m.getHid()==0l){
					AccordionMenu amenu=new AccordionMenu();
					amenu.setAccordion(m);
					map.put(m.getId(), amenu);
				}else{
					Iterator<Long> it=map.keySet().iterator();
					while(it.hasNext()){
						Long key=it.next();
						AccordionMenu accordionMenu=map.get(key);
						
						TreeNode node=new TreeNode();
						node.setId(m.getId().toString());
						node.setText(m.getName());
						node.setIconCls(m.getIcon());
						m.setUrl(StringUtil.matchUrl(request,m.getUrl()));
						node.setAttributes(JSONObject.parseObject(JSONObject.toJSONString(m)));
						
						if(m.getPid().longValue()==accordionMenu.getAccordion().getId().longValue()){
							accordionMenu.getList().add(node);
							map.put(key, accordionMenu);
						}else{
							List<TreeNode> subTreenodes=accordionMenu.getList();
							if(null!=subTreenodes&&subTreenodes.size()>0){
								accordionMenu.setList(MenuTool.recursionTreeNode(request,subTreenodes, m));
								map.put(key, accordionMenu);
							}
						}
					}
				}
			}
		}
		
		JSONArray array=new JSONArray();
		if(null!=map&&map.size()>0){
			for(Map.Entry<Long, AccordionMenu> entry:map.entrySet()){
				array.add(entry.getValue());
			}
		}
		
		return array;
	}
	
	/**
	 * 顶层菜单
	 * @param request
	 * @param platformid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("initTopMenu")
	@ResponseBody
	public List<Menu> initTopMenu(HttpServletRequest request,Long platformid){
		List<LoginAccountRoleView> activeroles=(List<LoginAccountRoleView>) request.getSession().getAttribute(Constants.SESSION_ROLES);
		List<Object> roleids=new ArrayList<Object>();
		for(LoginAccountRoleView r:activeroles){
			roleids.add(r.getRoleid());
		}

		List<Platform> platforms=(List<Platform>) request.getSession().getAttribute(Constants.SESSION_PLATFORMS);
		if(null==platformid||platformid==0l) platformid=platforms.get(0).getId();
		else{
			boolean flag=false;
			//判断是否拥有此平台授权，无则默认取第一个平台数据
			for(Platform p:platforms){
				if(p.getId().longValue()==platformid.longValue()) {flag=true;break;}
			}
			if(!flag) platformid=platforms.get(0).getId();
		}
		List<Menu> menus=this.menuService.selectLoginAccountMenus(roleids, platformid,0l);
		if(null!=menus&&menus.size()>0){
			return menus;
		}
		return null;
	}
	
	/**
	 * easyui子菜单树
	 * @param request
	 * @param platformid
	 * @param pid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("initSubMenu")
	@ResponseBody
	public List<TreeNode> initSubMenu(HttpServletRequest request,Long platformid,Long pid){
		List<LoginAccountRoleView> activeroles=(List<LoginAccountRoleView>) request.getSession().getAttribute(Constants.SESSION_ROLES);
		List<Object> roleids=new ArrayList<Object>();
		for(LoginAccountRoleView r:activeroles){
			roleids.add(r.getRoleid());
		}

		List<Platform> platforms=(List<Platform>) request.getSession().getAttribute(Constants.SESSION_PLATFORMS);
		if(null==platformid||platformid==0l) platformid=platforms.get(0).getId();
		else{
			boolean flag=false;
			//判断是否拥有此平台授权，无则默认取第一个平台数据
			for(Platform p:platforms){
				if(p.getId().longValue()==platformid.longValue()) {flag=true;break;}
			}
			if(!flag) platformid=platforms.get(0).getId();
		}
		
		List<Menu> menus=this.menuService.selectLoginAccountMenus(roleids, platformid,null);
		System.out.println(JSON.toJSONString(menus));
		LinkedHashMap<Long,AccordionMenu> map=new LinkedHashMap<Long, AccordionMenu>();
		if(null!=menus&&menus.size()>0){
			for(Menu m:menus){
				if(m.getPid().longValue()==0l&&m.getHid()==0l){
					AccordionMenu amenu=new AccordionMenu();
					amenu.setAccordion(m);
					map.put(m.getId(), amenu);
				}else{
					Iterator<Long> it=map.keySet().iterator();
					boolean isThreeMenu = true;
					while(it.hasNext()){
						Long key=it.next();
						if(key.equals(m.getPid())){ //先匹配一级菜单
							AccordionMenu accordionMenu=map.get(key);
							
							TreeNode node=new TreeNode();
							node.setId(m.getId().toString());
							node.setText(m.getName());
							node.setIconCls(m.getIcon());
							m.setUrl(StringUtil.matchUrl(request,m.getUrl()));
							node.setAttributes(JSONObject.parseObject(JSONObject.toJSONString(m)));
							
							if(m.getPid().longValue()==accordionMenu.getAccordion().getId().longValue()){
								accordionMenu.getList().add(node);
								map.put(key, accordionMenu);
							}else{
								List<TreeNode> subTreenodes=accordionMenu.getList();
								if(null!=subTreenodes&&subTreenodes.size()>0){
									accordionMenu.setList(MenuTool.recursionTreeNode(request,subTreenodes, m));
									map.put(key, accordionMenu);
								}
							}
							isThreeMenu = false;
							break;
						}
						
					}
					if(isThreeMenu) {
						Iterator<AccordionMenu> iterator = map.values().iterator();
						while(iterator.hasNext()) {
							 List<TreeNode> list = iterator.next().getList();
							 Iterator<TreeNode> iterator2 = list.iterator();
							 while(iterator2.hasNext()) {
								 TreeNode next = iterator2.next();
								 if(m.getPid().toString().equals(next.getId())) {
									 TreeNode node=new TreeNode();
									 node.setId(m.getId().toString());
									 node.setText(m.getName());
									 node.setIconCls(m.getIcon());
									 m.setUrl(StringUtil.matchUrl(request,m.getUrl()));
									 node.setAttributes(JSONObject.parseObject(JSONObject.toJSONString(m)));
									 next.getChildren().add(node);
								}
							 }
							
						}
					}
				}
			}
		}
		System.out.println(JSON.toJSONString(map));
		if(null!=map&&map.size()>0){
			AccordionMenu accordion=map.get(pid);
			if(null!=accordion) return accordion.getList();
		}
		return null;
	}
	
//	/**
//	 * 递归easyui 子树
//	 * @param list
//	 * @param menu
//	 * @return
//	 */
//	public List<TreeNode> recursionTreeNode(HttpServletRequest request,List<TreeNode> list,Menu menu){
//		for(int i=0;i<list.size();i++){
//			TreeNode node=list.get(i);
//			if(menu.getPid().longValue()==Long.valueOf(node.getId().toString()).longValue()){
//				TreeNode tn=new TreeNode();
//				tn.setId(menu.getId());
//				tn.setText(menu.getName());
//				tn.setIconCls(menu.getIcon());
//				menu.setUrl(StringUtil.matchUrl(request,menu.getUrl()));
//				tn.setAttributes(JSONObject.parseObject(JSONObject.toJSONString(menu)));
//				boolean flag=false;
//				if(null!=node.getChildren()&&node.getChildren().size()>0){
//					for(TreeNode t:node.getChildren()){
//						if(t.getId().toString().equals(menu.getId().toString())) {flag=true;break;}
//					}
//				}
//				if(!flag){
//					list.get(i).getChildren().add(tn);
//				}
//				break;
//			}else{
//				this.recursionTreeNode(request,list.get(i).getChildren(), menu);
//			}
//		}
//		return list;
//	}
	
	
//	/**
//	 * 根据平台ID生成左侧菜单树html字符串
//	 * @param request
//	 * @param platformid
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	@RequestMapping("initMenu")
//	@ResponseBody
//	public String initMenu(HttpServletRequest request,Long platformid){
//		List<Platform> platforms=(List<Platform>) request.getSession().getAttribute(Constants.SESSION_PLATFORMS);
//		boolean flag=false;
//		//判断是否拥有此平台授权，无则不生成菜单树
//		for(Platform p:platforms){
//			if(p.getId().longValue()==platformid.longValue()) {flag=true;break;}
//		}
//		if(!flag) return null;
//		
//		List<LoginAccountRoleView> activeroles=(List<LoginAccountRoleView>) request.getSession().getAttribute(Constants.SESSION_ROLES);
//		List<Object> roleids=new ArrayList<Object>();
//		for(LoginAccountRoleView r:activeroles){
//			roleids.add(r.getRoleid());
//		}
//		List<Menu> menus=this.menuService.selectLoginAccountMenus(roleids, platformid,null);
//		if(null!=menus&&menus.size()>0) return this.buildMenu(request,menus);
//		return null;
//	}
//	
//	/**
//	 * build左侧菜单
//	 * @param menus
//	 * @return
//	 */
//	private String buildMenu(HttpServletRequest request,List<Menu> menus){
//		StringBuffer menuStr=new StringBuffer();
//		for(Menu menu:menus){
//			if(menu.getHid()==0l&&menu.getPid()==0l){
//				menuStr.append("<div title='"+menu.getName()+"' style='overflow:auto;font-size:16px;'>");
//				menuStr.append("<ul class='easyui-tree' data-options='lines:true'>");
//				String childmenuStr=this.getChildMenu(request,menus, menu.getId());
//				menuStr.append(childmenuStr);
//				menuStr.append("</ul>");
//				menuStr.append("</div>");
//			}
//		}
//		return menuStr.toString();
//	}
//	
//	private String getChildMenu(HttpServletRequest request,List<Menu> menus,Long pid){
//		StringBuffer sb=new StringBuffer();
//		for(Menu menu:menus){
//			if(menu.getPid().longValue()==pid.longValue()) {
//				
//				sb.append("<li><span><label title='"+menu.getName()+"' url='"+matchUrl(request,menu.getUrl())+"'>"+menu.getName()+"</label></span>");
//				String childStr=this.recursionSubMenu(request,menus, menu.getId());
//				if(childStr.equals("<ul></ul>")) {
//					sb.append("</li>");
//				}else {
//					sb.append(childStr);
//					sb.append("</li>");
//				}
//			}
//		}
//		return sb.toString();
//	}
//	
//	/**
//	 * 递归生成菜单树
//	 * @param menus
//	 * @param pid
//	 * @return
//	 */
//	private String recursionSubMenu(HttpServletRequest request,List<Menu> menus,Long pid){
//		StringBuffer sb=new StringBuffer();
//		sb.append("<ul>");
//		for(Menu menu:menus){
//			if(menu.getPid().longValue()==pid.longValue()){
//				sb.append("<li><span><label title='"+menu.getName()+"' url='"+matchUrl(request,menu.getUrl())+"'>"+menu.getName()+"</label></span>");
//				String childStr=this.recursionSubMenu(request,menus, menu.getId());
//				if(childStr.equals("<ul></ul>")) {
//					sb.append("</li>");
//				}else {
//					sb.append(childStr);
//					sb.append("</li>");
//				}
//			}
//		}
//		sb.append("</ul>");
//		return sb.toString();
//	}
	
	/**
	 * 主页
	 * @return
	 */
	@RequestMapping("home")
	public ModelAndView home(){
		return new ModelAndView("/system/home");
	}
	
	/**
	 * 图片验证码生成
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("validateCode")
	public void validateCode(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setContentType("image/jpeg");
		response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        ValidateCodeUtil validateCodeUtil=new ValidateCodeUtil();
        request.getSession().setAttribute(Constants.SESSION_VALIDATE_CODE, validateCodeUtil.getCode());
        validateCodeUtil.write(response.getOutputStream());
	}
	
	/**
	 * 退出登录
	 * @param request
	 * @return
	 */
	@RequestMapping("logout")
	@SysLog(event = "退出登录",type=LogType.OTHERS)
	public ModelAndView logout(HttpServletRequest request,HttpServletResponse response){
		request.getSession().invalidate();
		Cookie cookie=new Cookie("token",null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		return new ModelAndView("redirect:/system/index");
	}
}
