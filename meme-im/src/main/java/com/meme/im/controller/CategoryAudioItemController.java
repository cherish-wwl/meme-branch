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
import com.meme.im.pojo.CategoryAudioItem;
import com.meme.im.service.CategoryAudioItemService;
import com.meme.qiniu.util.QiniuAPI;

@Controller
@RequestMapping("/im/audio/item/")
public class CategoryAudioItemController extends BaseController implements IController {
	
	@Resource private CategoryAudioItemService categoryAudioItemService;

	@Override
	@RequestMapping(value="loadValidateRules")
	@ResponseBody
	public String loadValidationRules(){
		String js=ValidateBuilder.createJsValidateRules(CategoryAudioItem.class);
		return js;
	}
	
	@RequestMapping("index")
	public ModelAndView index() {
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("/im/audio/item/list", model);
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Pagination list(HttpServletRequest request,ImForm form){
		List<Map<String,Object>> list=this.categoryAudioItemService.selectByPaginationView(form);
		int count=this.categoryAudioItemService.countView(form);
		if(null!=list&&list.size()>0){
			for(int i=0;i<list.size();i++){
				Map<String,Object> map=list.get(i);
				DictItemView reco_song=DictCache.getDictItem("SF", map.get("reco_song").toString());
				DictItemView reco_mv=DictCache.getDictItem("SF", map.get("reco_mv").toString());
				DictItemView reco_audio=DictCache.getDictItem("SF", map.get("reco_audio").toString());
				if(null!=reco_song){
					list.get(i).put("reco_songText", reco_song.getDictitemname());
				}
				if(null!=reco_mv){
					list.get(i).put("reco_mvText", reco_mv.getDictitemname());
				}
				if(null!=reco_audio){
					list.get(i).put("reco_audioText", reco_audio.getDictitemname());
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
			CategoryAudioItem m=this.categoryAudioItemService.selectByPrimaryKey(Long.valueOf(id));
			if(null!=m) model.put("object", m);
		}
		String bucket=ParamsCache.get("MEMBER_BUCKET").getValue();
		model.put("bucket", bucket);
		model.put("key", System.currentTimeMillis());
		String prefix=ParamsCache.get("AUDIO_SAVE_PATH")==null?"":ParamsCache.get("AUDIO_SAVE_PATH").getValue();
		model.put("prefix", prefix);
//		String upToken = QiniuAPI.getUploadToken(bucket,null);
//		model.put("upToken", upToken);
		ResultMessage result=QiniuAPI.getBucketDomains(bucket);
		if(!result.getState().equals("0")){
			String[] domains=(String[]) result.getData();
			model.put("domain", domains[0]);
		}
		
		return new ModelAndView("/im/audio/item/edit",model);
	}
	
	@RequestMapping("doAdd")
	@ResponseBody
	@SysLog(event = "添加音乐",type=LogType.ADD)
	public ResultMessage doAdd(HttpServletRequest request,HttpServletResponse response,CategoryAudioItem obj){
		return this.categoryAudioItemService.add(request, obj);
	}
	
	@RequestMapping("doEdit")
	@ResponseBody
	@SysLog(event = "编辑音乐",type=LogType.UPDATE)
	public ResultMessage doEdit(HttpServletRequest request,HttpServletResponse response,CategoryAudioItem obj){
		return this.categoryAudioItemService.edit(request, obj);
	}
	
	@RequestMapping("delete")
	@ResponseBody
	@SysLog(event = "删除音乐",type=LogType.DELETE)
	public ResultMessage delete(@RequestParam("ids[]") List<Object> ids){
        return this.categoryAudioItemService.delete(ids);
	}
}