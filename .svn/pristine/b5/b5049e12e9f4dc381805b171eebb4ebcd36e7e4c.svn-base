package com.meme.system.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meme.core.base.BaseController;
import com.meme.core.base.IController;
import com.meme.core.pojo.SysArea;
import com.meme.core.service.SysAreaService;
import com.meme.core.util.StringUtil;
import com.meme.core.validator.ValidateBuilder;

@Controller
@RequestMapping("/system/area/")
public class SysAreaController extends BaseController implements IController {
	
	@Resource private SysAreaService sysAreaService;

	@Override
	@RequestMapping(value="loadValidateRules")
	@ResponseBody
	public String loadValidationRules(){
		String js=ValidateBuilder.createJsValidateRules(SysArea.class);
		return js;
	}
	
	/**
	 * 获取某父区域下的所有一级子区域
	 * @param pid
	 * @return
	 */
	@RequestMapping("open/getChildAreas")
	@ResponseBody
	public List<SysArea> getChildAreas(HttpServletResponse response,String pid,@RequestHeader(value="Origin") String Origin){
		response.setHeader("Access-Control-Allow-Origin", Origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		if(StringUtil.isEmpty(pid)) pid="0";
		return this.sysAreaService.selectChildList(pid);
	}
}
