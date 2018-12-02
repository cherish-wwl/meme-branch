package com.meme.im.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.meme.core.base.BaseController;
import com.meme.core.cache.DictCache;
import com.meme.core.cache.ParamsCache;
import com.meme.core.easyui.Pagination;
import com.meme.core.http.ResultMessage;
import com.meme.core.log.LogType;
import com.meme.core.log.annotation.SysLog;
import com.meme.core.pojo.DictItemView;
import com.meme.im.form.ImForm;
import com.meme.im.pojo.MemeComment;
import com.meme.im.service.MemeCommentService;
import com.meme.qiniu.util.QiniuAPI;

/**
 * 评论管理
 * @author hzl
 *
 */
@Controller
@RequestMapping("/im/comment")
public class MemeCommentController extends BaseController{
	
	@Resource private MemeCommentService memeCommentService;

	
	@RequestMapping("index")
	public ModelAndView index(){
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("/im/comment/list",model);
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Pagination list(HttpServletRequest request,ImForm form) throws IllegalAccessException, InvocationTargetException{
		List<Map<String, Object>> list = this.memeCommentService.selectByPaginationView(form);
		for(Map<String,Object> map:list) {
			if(map.get("mtype1") != null) {
				map.put("mtypeText1", DictCache.getDictItem("MEMBER_ACCOUNT_TYPE",map.get("mtype1").toString()).getDictitemname());
			}
			if(map.get("mtype2") != null) {
				map.put("mtypeText2", DictCache.getDictItem("MEMBER_ACCOUNT_TYPE",map.get("mtype2").toString()).getDictitemname());
			}
			if(map.get("part") != null) {
				DictItemView dictItem = DictCache.getDictItem("MEME_PART",map.get("part").toString());
				map.put("partText", dictItem.getDictitemname());
				if(map.get("type") != null) {
					map.put("typeText", DictCache.getDictItem(dictItem.getRemark(),map.get("type").toString()).getDictitemname());
				}
			}
		}
		int count=this.memeCommentService.countView(form);
		Pagination pagination=new Pagination();
		pagination.setRows(list);
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
	@SysLog(event = "删除数据",type=LogType.DELETE)
	@Transactional
	public ResultMessage delete(@RequestParam("ids[]") List<Object> ids){
		this.memeCommentService.batchDelete(ids);
		return ResultMessage.defaultSuccessMessage();
	}
	
	@RequestMapping("editView")
	public ModelAndView editView(String id) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		MemeComment memeComment = this.memeCommentService.selectByPrimaryKey(id);
		resultMap.put("object", memeComment);
		String bucket=ParamsCache.get("MEMBER_BUCKET").getValue();
		resultMap.put("bucket", bucket);
		resultMap.put("prefix", "meme/");
		ResultMessage result=QiniuAPI.getBucketDomains(bucket);
		if(!result.getState().equals("0")){
			String[] domains=(String[]) result.getData();
			resultMap.put("domain", domains[0]);
		}
		//加载kindeditor上传相关参数
		Long maxs=Long.valueOf(ParamsCache.get("KINDEDITOR_UPLOAD_SIZE").getValue());
		float maxsize=Math.round(maxs/1048576*100)/100;
		resultMap.put("sizeLimit", maxsize+"Mb");
		resultMap.put("uploadLimit", ParamsCache.get("KINDEDITOR_UPLOAD_NUM").getValue());
		return new ModelAndView("/im/comment/edit",resultMap);
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
	@SysLog(event = "修改数据",type=LogType.UPDATE)
	public ResultMessage edit(HttpServletRequest request,MemeComment memeComment) throws Exception{
		this.memeCommentService.updateByPrimaryKeySelective(memeComment);
		return ResultMessage.defaultSuccessMessage();
	}
}