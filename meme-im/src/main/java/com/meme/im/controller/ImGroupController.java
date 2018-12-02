package com.meme.im.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.meme.core.base.BaseController;
import com.meme.core.cache.DictCache;
import com.meme.core.easyui.Pagination;
import com.meme.im.form.ImForm;
import com.meme.im.service.ImGroupService;

@Controller
@RequestMapping("/im/group/")
public class ImGroupController extends BaseController {

	@Resource
	private ImGroupService imGroupService;

	@RequestMapping("index")
	public ModelAndView index() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("/im/group/list", model);
	}

	@RequestMapping("list")
	@ResponseBody
	public Pagination list(HttpServletRequest request, ImForm form) {
		List<Map<String, Object>> list = this.imGroupService.selectByPaginationView(form);
		int count = this.imGroupService.countView(form);
		Pagination pagination = new Pagination();
		pagination.setRows(list);
		pagination.setTotal(count);
		return pagination;
	}
	
	@RequestMapping("groupmember")
	public ModelAndView groupmember(String groupid) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("groupid", groupid);
		return new ModelAndView("/im/group/groupmember", model);
	}
	
	@RequestMapping("groupmessage")
	public ModelAndView groupmessage() {
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("/im/group/groupmessage", model);
	}
	
	@RequestMapping("groupmemberList")
	@ResponseBody
	public Pagination groupmemberList(HttpServletRequest request, ImForm form) {
		List<Map<String, Object>> list = this.imGroupService.selectGroupMemberByPagination(form);
		int count = this.imGroupService.countGroupMember(form);
		Pagination pagination = new Pagination();
		pagination.setRows(list);
		pagination.setTotal(count);
		return pagination;
	}
	
	@RequestMapping("groupmessageList")
	@ResponseBody
	public Pagination groupmessageList(HttpServletRequest request, ImForm form) {
		List<Map<String, Object>> list = this.imGroupService.selectGroupMessageByPagination(form);
		if (null != list && list.size() > 0) {
			for(int i=0;i<list.size();i++){
				Object send_mtype=list.get(i).get("send_mtype");
				if(null!=send_mtype) list.get(i).put("send_mtype", DictCache.getDictItem("MEMBER_ACCOUNT_TYPE", send_mtype.toString()).getDictitemname());
			}
		}
		int count = this.imGroupService.countGroupMessage(form);
		Pagination pagination = new Pagination();
		pagination.setRows(list);
		pagination.setTotal(count);
		return pagination;
	}
	
}
