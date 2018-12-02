package com.meme.im.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.meme.core.base.BaseController;
import com.meme.core.easyui.Pagination;
import com.meme.im.pojo.MemberAlbum;
import com.meme.im.pojo.MemberAlbumItem;
import com.meme.im.service.MemberAlbumItemService;
import com.meme.im.service.MemberAlbumService;

@Controller
@RequestMapping("/manage/memberAlbum/")
public class MemberAlbumController extends BaseController {

	@Autowired
	private MemberAlbumService memberAlbumService;
	
	@Resource 
	private MemberAlbumItemService memberAlbumItemService;
	
	@RequestMapping("index")
	public ModelAndView index() {
		Map<String, Object> model = new HashMap<String, Object>();
		
		return new ModelAndView("/im/homepage/list",model);
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Pagination list(@RequestParam(defaultValue = "1") Integer pageNo, 
					@RequestParam(defaultValue = "20") Integer pageSize, MemberAlbum record) {
		
		PageInfo<MemberAlbum> page = this.memberAlbumService.selectByEntity(record, pageNo, pageSize);

		Map<String,AlbumList> map=new LinkedHashMap<String, AlbumList>();
		if(null!=page.getList() && page.getList().size()>0){
			List<Object> albumids=new ArrayList<Object>();
			for(MemberAlbum a:page.getList()){
				albumids.add(a.getAlbumid());
				AlbumList li=new AlbumList();
				li.setAddtime(a.getAddtime());
				li.setAlbumid(a.getAlbumid());
				li.setContent(a.getContent());
				li.setMemberid(a.getMemberid());
				map.put(a.getAlbumid(), li);
			}
			List<MemberAlbumItem> items=this.memberAlbumItemService.selectByAlbumids(albumids);
			if(null!=items&&items.size()>0){
				for(MemberAlbumItem it:items){
					map.get(it.getAlbumid()).getList().add(it);
				}
			}
		}
		
		List<AlbumList> list=new ArrayList<AlbumList>();
		if(map.size()>0){
			for(Map.Entry<String,AlbumList> entry:map.entrySet()){
				list.add(entry.getValue());
			}
		}
		
		Pagination pagination=new Pagination();
		pagination.setRows(list);
		pagination.setTotal((int)page.getTotal());
		
		return pagination;
	}
	
	/**
	 * 相册数据结构
	 * @author hzl
	 *
	 */
	class AlbumList extends MemberAlbum{
		List<MemberAlbumItem> list=new ArrayList<MemberAlbumItem>();

		public List<MemberAlbumItem> getList() {
			return list;
		}

		public void setList(List<MemberAlbumItem> list) {
			this.list = list;
		}
	}
	
}
