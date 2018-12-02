package com.meme.im.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.meme.core.base.BaseController;
import com.meme.core.easyui.Pagination;
import com.meme.im.pojo.MemberSign;
import com.meme.im.service.MemberSignService;

@Controller
@RequestMapping("/manage/memberSign/")
public class MemberSignController extends BaseController {

	@Autowired
	private MemberSignService memberSignService;
	
	@RequestMapping("index")
	public ModelAndView index() {
		Map<String, Object> model = new HashMap<String, Object>();
		
		return new ModelAndView("/im/homepage/list",model);
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Pagination list(@RequestParam(defaultValue = "1") Integer pageNo, 
					@RequestParam(defaultValue = "20") Integer pageSize, MemberSign record) {
		
		PageInfo<MemberSign> page = this.memberSignService.selectByEntity(record, pageNo, pageSize);

		Pagination pagination = new Pagination();
		pagination.setRows(page.getList());
		pagination.setTotal((int)page.getTotal());
		
		return pagination;
	}
	
}
