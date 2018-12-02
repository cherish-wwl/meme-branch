package com.meme.im.controller;

import java.util.Date;
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
import com.meme.core.easyui.Pagination;
import com.meme.core.http.ResultMessage;
import com.meme.core.log.LogType;
import com.meme.core.log.annotation.SysLog;
import com.meme.core.util.IDGenerator;
import com.meme.im.form.ImForm;
import com.meme.im.pojo.MemeContent;
import com.meme.im.service.MemeContentService;

@Controller
@RequestMapping("/im/notice/")
public class MemeNoticeController extends BaseController{
	@Resource 
	private MemeContentService memeContentService;
	
	
	@RequestMapping("home")
	public ModelAndView home(String columnid) {
		//System.out.println("1:"+type);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("columnid", columnid);
		return new ModelAndView("/im/notice/home", model);
	}
	
	@RequestMapping("homelist")
	@ResponseBody
	public Pagination homelist(ImForm form) {
		List<Map<String,Object>> list=this.memeContentService.selectByPaginationSectionView(form);
		int count=this.memeContentService.countSelctionView(form);
		Pagination pagination=new Pagination(list,count);
		return pagination;
	}
	
	@RequestMapping("editView")
	public ModelAndView editView(Long id,Long columnid) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		MemeContent obj = null;
		if(id != null){
			obj=this.memeContentService.selectByPrimaryKey(id);
		}else {
			obj = new MemeContent();
			obj.setColumnid(columnid);
		}
		resultMap.put("object", obj);
		return new ModelAndView("/im/notice/edit",resultMap);
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
	@SysLog(event = "新增公告内容",type=LogType.ADD)
	public ResultMessage add(HttpServletRequest request,MemeContent memeContent){
		memeContent.setContentid(IDGenerator.generateId());
		memeContent.setAddtime(new Date());
		this.memeContentService.insertSelective(memeContent);
		return ResultMessage.defaultSuccessMessage();
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
	@SysLog(event = "修改公告内容",type=LogType.UPDATE)
	public ResultMessage edit(HttpServletRequest request,MemeContent memeContent){
		memeContent.setPublishtime(new Date());
		this.memeContentService.updateByPrimaryKeySelective(memeContent);
		return ResultMessage.defaultSuccessMessage();
	}
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	@SysLog(event = "删除公告",type=LogType.DELETE)
	@Transactional
	public ResultMessage delete(@RequestParam("ids[]") List<Object> ids){
		this.memeContentService.batchDelete(ids);
		return ResultMessage.defaultSuccessMessage();
	}
	
}
