package com.meme.im.controller;

import java.lang.reflect.InvocationTargetException;
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
import com.meme.core.pojo.DictItemView;
import com.meme.im.form.ImForm;
import com.meme.im.service.MemeLikeService;

/**
 * 评论管理
 * @author hzl
 *
 */
@Controller
@RequestMapping("/im/like")
public class MemeLikeController extends BaseController{
	
	@Resource private MemeLikeService memeLikeService;

	
	@RequestMapping("index")
	public ModelAndView index(){
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("/im/like/list",model);
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Pagination list(HttpServletRequest request,ImForm form) throws IllegalAccessException, InvocationTargetException{
		List<Map<String, Object>> list = this.memeLikeService.selectByPaginationView(form);
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
		int count=this.memeLikeService.countView(form);
		Pagination pagination=new Pagination();
		pagination.setRows(list);
		pagination.setTotal(count);
		return pagination;
	}
	
	
}