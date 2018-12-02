package com.meme.im.controller;

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

import com.meme.core.base.BaseController;
import com.meme.core.base.IController;
import com.meme.core.cache.DictCache;
import com.meme.core.cache.ParamsCache;
import com.meme.core.easyui.Pagination;
import com.meme.core.http.ResultMessage;
import com.meme.core.log.LogType;
import com.meme.core.log.annotation.SysLog;
import com.meme.core.pojo.DictItemView;
import com.meme.core.util.StringUtil;
import com.meme.core.validator.ValidateBuilder;
import com.meme.im.form.ImForm;
import com.meme.im.pojo.CategoryVideoItem;
import com.meme.im.service.CategoryVideoItemService;
import com.meme.qiniu.util.QiniuAPI;

@Controller
@RequestMapping("/im/video/item/")
public class CategoryVideoItemController extends BaseController implements IController {
	
	@Resource private CategoryVideoItemService categoryVideoItemService;

	@Override
	@RequestMapping(value="loadValidateRules")
	@ResponseBody
	public String loadValidationRules(){
		String js=ValidateBuilder.createJsValidateRules(CategoryVideoItem.class);
		return js;
	}
	
	@RequestMapping("index")
	public ModelAndView index() {
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("/im/video/item/list", model);
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Pagination list(HttpServletRequest request,ImForm form){
		List<Map<String,Object>> list=this.categoryVideoItemService.selectByPaginationView(form);
		int count=this.categoryVideoItemService.countView(form);
		if(null!=list&&list.size()>0){
			for(int i=0;i<list.size();i++){
				Map<String,Object> map=list.get(i);
				DictItemView isnewDict=DictCache.getDictItem("SF", map.get("isnew").toString());
				DictItemView isrecommendDict=DictCache.getDictItem("SF", map.get("isrecommend").toString());
				DictItemView isanchorDict=DictCache.getDictItem("SF", map.get("isanchor").toString());
				if(null!=isnewDict){
					list.get(i).put("isnewText", isnewDict.getDictitemname());
				}
				if(null!=isrecommendDict){
					list.get(i).put("isrecommendText", isrecommendDict.getDictitemname());
				}
				if(null!=isanchorDict){
					list.get(i).put("isanchorText", isanchorDict.getDictitemname());
				}
			}
		}
		Pagination pagination=new Pagination(list,count);
		return pagination;
	}
	
	@RequestMapping("edit")
	public ModelAndView edit(String id){
		Map<String,Object> model=new HashMap<String, Object>();
		if(StringUtil.isAllNotEmpty(id)){
			CategoryVideoItem m=this.categoryVideoItemService.selectByPrimaryKey(Long.valueOf(id));
			if(null!=m) model.put("object", m);
		}
		String bucket=ParamsCache.get("MEMBER_BUCKET").getValue();
		model.put("bucket", bucket);
		model.put("key", System.currentTimeMillis());
		String prefix=ParamsCache.get("VIDEO_SAVE_PATH")==null?"":ParamsCache.get("VIDEO_SAVE_PATH").getValue();
		model.put("prefix", prefix);
//		String upToken = QiniuAPI.getUploadToken(bucket,null);
//		model.put("upToken", upToken);
		ResultMessage result=QiniuAPI.getBucketDomains(bucket);
		if(!result.getState().equals("0")){
			String[] domains=(String[]) result.getData();
			model.put("domain", domains[0]);
		}
		
		return new ModelAndView("/im/video/item/edit",model);
	}
	
	@RequestMapping("doAdd")
	@ResponseBody
	@SysLog(event = "添加视频",type=LogType.ADD)
	public ResultMessage doAdd(HttpServletRequest request,HttpServletResponse response,CategoryVideoItem obj){
		return this.categoryVideoItemService.add(request, obj);
	}
	
	@RequestMapping("doEdit")
	@ResponseBody
	@SysLog(event = "编辑视频",type=LogType.UPDATE)
	public ResultMessage doEdit(HttpServletRequest request,HttpServletResponse response,CategoryVideoItem obj){
		return this.categoryVideoItemService.edit(request, obj);
	}
	
	@RequestMapping("delete")
	@ResponseBody
	@SysLog(event = "删除视频",type=LogType.DELETE)
	public ResultMessage delete(@RequestParam("ids[]") List<Object> ids){
        return this.categoryVideoItemService.delete(ids);
	}
}