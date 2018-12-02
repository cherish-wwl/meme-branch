package com.meme.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.meme.core.base.BaseController;
import com.meme.core.base.IController;
import com.meme.core.cache.DictCache;
import com.meme.core.easyui.Pagination;
import com.meme.core.form.Form;
import com.meme.core.http.ResultMessage;
import com.meme.core.log.LogType;
import com.meme.core.log.annotation.SysLog;
import com.meme.core.pojo.Platform;
import com.meme.core.pojo.PlatformView;
import com.meme.core.service.PlatformService;
import com.meme.core.validator.ValidateBuilder;

@Controller("SystemPlatformController")
@RequestMapping("/system/platform/")
public class PlatformController extends BaseController implements IController{

	@Resource private PlatformService platformService;
	
	@Override
	@RequestMapping(value="loadValidateRules")
	@ResponseBody
	public String loadValidationRules(){
		String js=ValidateBuilder.createJsValidateRules(Platform.class);
		return js;
	}
	
	@RequestMapping("index")
	public ModelAndView index(){
//		List<FieldStruct> list=JdbcHelper.getMetaData("oa_finance_money_apply");
//		List<EntityColumn> list=MapperHelper.getTableColumnsWithBlobs(GeBytearray.class);
//		for(EntityColumn col:list){
//			System.out.println(col.getColumn()+"-"+col.getJavaType().getName()+"==="+Handler.handleRequest(col));
//			System.out.println(col.toString());
//			if(null!=col.getJavaType().getComponentType()) System.out.println(col.getJavaType().getComponentType().getName());
//		}
		return new ModelAndView("/system/platform/list");
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Pagination list(HttpServletRequest request,@ModelAttribute("searchForm") Form form) throws Exception{
		int count=this.platformService.count(form);
		List<Platform> list=this.platformService.selectByPagination(form);
		List<PlatformView> objs=new ArrayList<PlatformView>();
		if(null!=list&&list.size()>0) {
			for(Platform obj:list){
				PlatformView v=JSONObject.parseObject(JSONObject.toJSONString(obj), PlatformView.class);
				v.setIsOpenString(DictCache.getDictItem("PLATFORM_IS_OPEN", v.getIsopen().toString()).getDictitemname());
				objs.add(v);
			}
		}
		Pagination pagination=new Pagination();
		pagination.setRows(objs);
		pagination.setTotal(count);
		return pagination;
	}
	
	/**
	 * 获取平台列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getPlatformList")
	@ResponseBody
	public List<Platform> getPlatformList() throws Exception{
		List<Platform> platforms=this.platformService.selectAll();
		return platforms;
	}
	
	@RequestMapping("editView")
	public ModelAndView editView(Long id) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Platform obj=null;
		if(id!=null&&id!=0L){
			obj=this.platformService.selectByPrimaryKey(id);
		}
		resultMap.put("object", obj);
		return new ModelAndView("/system/platform/edit",resultMap);
	}
	
	/**
	 * 增加
	 * @param request
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("add")
	@ResponseBody
	@SysLog(event = "新增平台",type=LogType.ADD)
	public ResultMessage add(HttpServletRequest request,Platform platform){
		return this.platformService.add(request, platform);
	}
	
	/**
	 * 修改
	 * @param request
	 * @param form
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@ResponseBody
	@SysLog(event = "修改平台",type=LogType.UPDATE)
	public ResultMessage edit(HttpServletRequest request,Platform platform){
		return this.platformService.edit(request, platform);
	}
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	@SysLog(event = "删除平台",type=LogType.DELETE)
	@Transactional
	public ResultMessage delete(@RequestParam("ids[]") List<Object> ids){
        return this.platformService.delete(ids);
	}
}