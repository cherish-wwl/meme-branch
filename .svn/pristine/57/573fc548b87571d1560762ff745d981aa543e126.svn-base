package com.meme.im.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.meme.core.base.BaseController;
import com.meme.core.base.IController;
import com.meme.core.cache.ParamsCache;
import com.meme.core.easyui.Pagination;
import com.meme.core.http.ResultMessage;
import com.meme.core.log.LogType;
import com.meme.core.log.annotation.SysLog;
import com.meme.core.validator.ValidateBuilder;
import com.meme.im.form.ImForm;
import com.meme.im.pojo.MemeCategory;
import com.meme.im.pojo.MemeColumnSection;
import com.meme.im.pojo.MemeContent;
import com.meme.im.service.MemeCategoryService;
import com.meme.im.service.MemeColumnSectionService;
import com.meme.im.service.MemeColumnService;
import com.meme.im.service.MemeContentService;
import com.meme.qiniu.util.QiniuAPI;

@Controller
@RequestMapping("/im/content/")
public class MemeContentController extends BaseController implements IController {
	
	@Resource private MemeContentService memeContentService;
	@Resource private MemeColumnSectionService memeColumnSectionService;
	@Resource private MemeColumnService memeColumnService;
	@Resource private MemeCategoryService memeCategoryService;

	@Override
	@RequestMapping(value="loadValidateRules")
	@ResponseBody
	public String loadValidationRules(){
		String js=ValidateBuilder.createJsValidateRules(MemeContent.class);
		return js;
	}
	
	@RequestMapping("index")
	public ModelAndView index() {
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("/im/content/list", model);
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Pagination list(ImForm form){
		List<Map<String,Object>> list=this.memeContentService.selectByPaginationView(form);
		int count=this.memeContentService.countView(form);
		Pagination pagination=new Pagination(list,count);
		return pagination;
	}
	
	@RequestMapping("edit")
	public ModelAndView edit(Long id){
		Map<String, Object> model = new HashMap<String, Object>();
		MemeContent obj=null;
		if(id!=null&&id!=0L){
			obj=this.memeContentService.selectByPrimaryKey(id);
		}
		model.put("object", obj);
		
		String bucket=ParamsCache.get("MEMBER_BUCKET").getValue();
		model.put("bucket", bucket);
		model.put("prefix", "meme/");
		ResultMessage result=QiniuAPI.getBucketDomains(bucket);
		if(!result.getState().equals("0")){
			String[] domains=(String[]) result.getData();
			model.put("domain", domains[0]);
		}
		
		//加载kindeditor上传相关参数
		Long maxs=Long.valueOf(ParamsCache.get("KINDEDITOR_UPLOAD_SIZE").getValue());
		float maxsize=Math.round(maxs/1048576*100)/100;
		model.put("sizeLimit", maxsize+"Mb");
		model.put("uploadLimit", ParamsCache.get("KINDEDITOR_UPLOAD_NUM").getValue());
		
		if(null!=obj){
			MemeCategory cat=this.memeCategoryService.selectByPrimaryKey(obj.getCatid());
			if(null!=cat){
				List<Map<String,Object>> list=this.memeContentService.selectContentSections(cat.getColumnid());
				model.put("list", list);
			}
		}
		
		return new ModelAndView("/im/content/edit",model);
	}
	
	@RequestMapping("doAdd")
	@ResponseBody
	@SysLog(event = "添加内容",type=LogType.ADD)
	public ResultMessage doAdd(HttpServletRequest request,HttpServletResponse response,MemeContent obj){
		return this.memeContentService.add(request, obj);
	}
	
	@RequestMapping("doEdit")
	@ResponseBody
	@SysLog(event = "编辑内容",type=LogType.UPDATE)
	public ResultMessage doEdit(HttpServletRequest request,HttpServletResponse response,MemeContent obj){
		return this.memeContentService.edit(request, obj);
	}
	
	@RequestMapping("delete")
	@ResponseBody
	@SysLog(event = "删除内容",type=LogType.DELETE)
	public ResultMessage delete(@RequestParam("ids[]") List<Object> ids){
        return this.memeContentService.delete(ids);
	}
	
	/**
	 * 获取选中的栏目块
	 * @param catid
	 * @return
	 */
	@RequestMapping("getSelectedSections")
	@ResponseBody
	public ResultMessage getSelectedSections(Long contentid){
		if(null==contentid) return ResultMessage.failed("");
		MemeContent content=this.memeContentService.selectByPrimaryKey(contentid);
		if(null==content) return ResultMessage.failed("");
		List<MemeColumnSection> list=this.memeColumnSectionService.getSelectedSections(contentid);
		if(null == list || list.size() ==0) list=new ArrayList<MemeColumnSection>();
		return ResultMessage.success("",list);
	}
}
