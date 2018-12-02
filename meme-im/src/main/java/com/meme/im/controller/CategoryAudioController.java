package com.meme.im.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.meme.core.base.BaseController;
import com.meme.core.base.BaseTreeNode;
import com.meme.core.base.IController;
import com.meme.core.http.ResultMessage;
import com.meme.core.log.LogType;
import com.meme.core.log.annotation.SysLog;
import com.meme.core.util.TreeUtil;
import com.meme.core.validator.ValidateBuilder;
import com.meme.im.entity.CategoryAudioTreeGrid;
import com.meme.im.pojo.CategoryAudio;
import com.meme.im.service.CategoryAudioService;

@Controller
@RequestMapping("/im/audio/category/")
public class CategoryAudioController extends BaseController implements IController{
	
	@Resource private CategoryAudioService categoryAudioService;
	
	@Override
	@RequestMapping(value="loadValidateRules")
	@ResponseBody
	public String loadValidationRules(){
		String js=ValidateBuilder.createJsValidateRules(CategoryAudio.class);
		return js;
	}

	@RequestMapping("index")
	public ModelAndView index() {
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("/im/audio/category/list", model);
	}
	
	@RequestMapping("list")
	@ResponseBody
	public List<CategoryAudioTreeGrid> list() throws IllegalAccessException, InvocationTargetException{
		List<CategoryAudioTreeGrid> nodes=new ArrayList<CategoryAudioTreeGrid>();
		List<CategoryAudio> list=this.categoryAudioService.selectAllAudioCats();
		if(null!=list&&list.size()>0){
			List<CategoryAudioTreeGrid> tmp=this.getTreeNodes(list);
			if(null!=tmp&&tmp.size()>0) nodes.addAll(tmp);
		}
		return nodes;
	}
	
	public List<CategoryAudioTreeGrid> getTreeNodes(List<CategoryAudio> list) throws IllegalAccessException, InvocationTargetException{
		List<CategoryAudioTreeGrid> nodes=new ArrayList<CategoryAudioTreeGrid>();
		if(null!=list&&list.size()>0){
			for(CategoryAudio obj:list){
				if(obj.getPid().longValue()==0l){
					CategoryAudioTreeGrid node=new CategoryAudioTreeGrid();
					BeanUtils.copyProperties(node, obj);
//					node.setId(obj.getId().toString());
//					node.setText(obj.getCatname());
//					node.setAttributes(JSONObject.parseObject(JSONObject.toJSONString(obj)));
					nodes.add(node);
				}else{
					recursionTreeNode(nodes, obj);
				}
			}
		}
		return nodes;
	}
	
	public static List<CategoryAudioTreeGrid> recursionTreeNode(List<CategoryAudioTreeGrid> list,CategoryAudio obj) throws IllegalAccessException, InvocationTargetException{
		for(int i=0;i<list.size();i++){
			CategoryAudioTreeGrid node=list.get(i);
			if(obj.getPid().longValue()==Long.valueOf(node.getId().toString()).longValue()){
				CategoryAudioTreeGrid tn=new CategoryAudioTreeGrid();
				BeanUtils.copyProperties(tn, obj);
//				tn.setId(obj.getId());
//				tn.setText(obj.getCatname());
//				tn.setAttributes(JSONObject.parseObject(JSONObject.toJSONString(obj)));
				boolean flag=false;
				if(null!=node.getChildren()&&node.getChildren().size()>0){
					for(CategoryAudioTreeGrid t:node.getChildren()){
						if(t.getId().toString().equals(obj.getId().toString())) {flag=true;break;}
					}
				}
				if(!flag){
					list.get(i).getChildren().add(tn);
				}
				break;
			}else{
				recursionTreeNode(list.get(i).getChildren(), obj);
			}
		}
		return list;
	}
	
	
	@RequestMapping("editView")
	public ModelAndView editView(Long id) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		CategoryAudio obj=null;
		if(id!=null&&id!=0L){
			obj=this.categoryAudioService.selectByPrimaryKey(id);
			resultMap.put("pid", obj.getPid());
		}
		resultMap.put("object", obj);
		return new ModelAndView("/im/audio/category/edit",resultMap);
	}
	
	@RequestMapping("add")
	@ResponseBody
	@SysLog(event = "新增音乐分类",type=LogType.ADD)
	public ResultMessage add(HttpServletRequest request,CategoryAudio obj){
		return this.categoryAudioService.add(request, obj);
	}
	
	@RequestMapping("edit")
	@ResponseBody
	@SysLog(event = "修改音乐分类",type=LogType.UPDATE)
	public ResultMessage edit(HttpServletRequest request,CategoryAudio obj){
		return this.categoryAudioService.edit(request, obj);
	}
	
	@RequestMapping("delete")
	@ResponseBody
	@SysLog(event = "删除音乐分类",type=LogType.DELETE)
	public ResultMessage delete(@RequestParam("ids[]") List<Object> ids){
		List<Object> childList=new ArrayList<Object>();
    	List<CategoryAudio> list=this.categoryAudioService.selectAllAudioCats();
    	if(null!=ids&&ids.size()>0){
    		for(Object obj:ids){
    			List<Object> tmp=this.getChildList(list, Long.valueOf((String)obj));
    			childList.addAll(tmp);
    			childList.add(obj);
    		}
    	}
		int result=this.categoryAudioService.batchDelete(childList);
		if(result>0) return ResultMessage.defaultSuccessMessage();
		return ResultMessage.defaultFaileMessage();
	}
	
	private List<Object> getChildList(List<CategoryAudio> list,Long pid){
		List<Object> objs=new ArrayList<Object>();
		for(CategoryAudio obj:list){
			if(obj.getPid().longValue()==pid.longValue()){
				objs.add(obj.getId());
				objs.addAll(this.getChildList(list, obj.getId()));
			}
		}
		return objs;
	}
	
	/**
	 * 生成easyui 树结构
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping("getCatTree")
	@ResponseBody
	public List<BaseTreeNode> getCatTree() throws IllegalAccessException, InvocationTargetException{
		List<CategoryAudio> list=this.categoryAudioService.selectAllAudioCats();
		if(null!=list&&list.size()>0){
			return TreeUtil.getTreeNodes(list);
		}
		return new ArrayList<BaseTreeNode>();
	}
}