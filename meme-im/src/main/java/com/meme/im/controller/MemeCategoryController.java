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
import com.meme.core.cache.DictCache;
import com.meme.core.cache.ParamsCache;
import com.meme.core.http.ResultMessage;
import com.meme.core.log.LogType;
import com.meme.core.log.annotation.SysLog;
import com.meme.core.pojo.DictItemView;
import com.meme.core.util.StringUtil;
import com.meme.core.util.TreeUtil;
import com.meme.core.validator.ValidateBuilder;
import com.meme.im.entity.MemeCategoryTreeGrid;
import com.meme.im.pojo.MemeCategory;
import com.meme.im.pojo.MemeColumn;
import com.meme.im.pojo.view.MemeCategoryView;
import com.meme.im.service.MemeCategoryService;
import com.meme.im.service.MemeColumnService;
import com.meme.qiniu.util.QiniuAPI;

@Controller
@RequestMapping("/im/column/category/")
public class MemeCategoryController extends BaseController implements IController {
	
	@Resource private MemeCategoryService memeCategoryService;
	@Resource private MemeColumnService memeColumnService;

	@Override
	@RequestMapping(value="loadValidateRules")
	@ResponseBody
	public String loadValidationRules(){
		String js=ValidateBuilder.createJsValidateRules(MemeCategory.class);
		return js;
	}
	
	@RequestMapping("index")
	public ModelAndView index() {
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("/im/column/category/list", model);
	}
	
	@RequestMapping("list")
	@ResponseBody
	public List<MemeCategoryTreeGrid> list() throws IllegalAccessException, InvocationTargetException{
		List<MemeCategoryTreeGrid> nodes=new ArrayList<MemeCategoryTreeGrid>();
		List<MemeCategoryView> list=this.memeCategoryService.selectAllCats();
		if(null!=list&&list.size()>0){
			List<MemeCategoryTreeGrid> tmp=this.getTreeNodes(list);
			if(null!=tmp&&tmp.size()>0) nodes.addAll(tmp);
		}
		return nodes;
	}
	
	public List<MemeCategoryTreeGrid> getTreeNodes(List<MemeCategoryView> list) throws IllegalAccessException, InvocationTargetException{
		List<MemeCategoryTreeGrid> nodes=new ArrayList<MemeCategoryTreeGrid>();
		if(null!=list&&list.size()>0){
			for(MemeCategoryView obj:list){
				if(obj.getPid().longValue()==0l){
					MemeCategoryTreeGrid node=new MemeCategoryTreeGrid();
					BeanUtils.copyProperties(node, obj);
					
					DictItemView state=DictCache.getDictItem("SF", node.getState().toString());
					if(null!=state){
						node.setStateText(state.getDictitemname());
					}
					if(StringUtil.isAllNotEmpty(node.getTag())){
						DictItemView tag=DictCache.getDictItem("TAG_LIST", node.getTag());
						if(null!=tag) node.setTagText(tag.getDictitemname());
					}
					
					nodes.add(node);
				}else{
					recursionTreeNode(nodes, obj);
				}
			}
		}
		return nodes;
	}
	
	public static List<MemeCategoryTreeGrid> recursionTreeNode(List<MemeCategoryTreeGrid> list,MemeCategoryView obj) throws IllegalAccessException, InvocationTargetException{
		for(int i=0;i<list.size();i++){
			MemeCategoryTreeGrid node=list.get(i);
			if(obj.getPid().longValue()==node.getId().longValue()){
				MemeCategoryTreeGrid tn=new MemeCategoryTreeGrid();
				BeanUtils.copyProperties(tn, obj);
				
				DictItemView state=DictCache.getDictItem("SF", tn.getState().toString());
				if(null!=state){
					tn.setStateText(state.getDictitemname());
				}
				if(StringUtil.isAllNotEmpty(tn.getTag())){
					DictItemView tag=DictCache.getDictItem("TAG_LIST", tn.getTag());
					if(null!=tag) tn.setTagText(tag.getDictitemname());
				}
				
				boolean flag=false;
				if(null!=node.getChildren()&&node.getChildren().size()>0){
					for(MemeCategoryTreeGrid t:node.getChildren()){
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
	
	
	@RequestMapping("edit")
	public ModelAndView edit(Long id) throws Exception{
		Map<String, Object> model = new HashMap<String, Object>();
		MemeCategory obj=null;
		if(id!=null&&id!=0L){
			obj=this.memeCategoryService.selectByPrimaryKey(id);
			model.put("pid", obj.getPid());
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
		
		return new ModelAndView("/im/column/category/edit",model);
	}
	
	@RequestMapping("doAdd")
	@ResponseBody
	@SysLog(event = "新增栏目分类",type=LogType.ADD)
	public ResultMessage add(HttpServletRequest request,MemeCategory obj){
		return this.memeCategoryService.add(request, obj);
	}
	
	@RequestMapping("doEdit")
	@ResponseBody
	@SysLog(event = "修改栏目分类",type=LogType.UPDATE)
	public ResultMessage edit(HttpServletRequest request,MemeCategory obj){
		return this.memeCategoryService.edit(request, obj);
	}
	
	@RequestMapping("delete")
	@ResponseBody
	@SysLog(event = "删除栏目分类",type=LogType.DELETE)
	public ResultMessage delete(@RequestParam("ids[]") List<Object> ids){
		List<Object> childList=new ArrayList<Object>();
    	List<MemeCategoryView> list=this.memeCategoryService.selectAllCats();
    	if(null!=ids&&ids.size()>0){
    		for(Object obj:ids){
    			List<Object> tmp=this.getChildList(list, Long.valueOf((String)obj));
    			childList.addAll(tmp);
    			childList.add(obj);
    		}
    	}
		int result=this.memeCategoryService.batchDelete(childList);
		if(result>0) return ResultMessage.defaultSuccessMessage();
		return ResultMessage.defaultFaileMessage();
	}
	
	private List<Object> getChildList(List<MemeCategoryView> list,Long pid){
		List<Object> objs=new ArrayList<Object>();
		for(MemeCategory obj:list){
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
	@RequestMapping("getTree")
	@ResponseBody
	public List<BaseTreeNode> getTree() throws IllegalAccessException, InvocationTargetException{
		List<MemeCategoryView> list=this.memeCategoryService.selectAllCats();
		if(null!=list&&list.size()>0){
			return TreeUtil.getTreeNodes(list);
		}
		return new ArrayList<BaseTreeNode>();
	}
	
	@RequestMapping("getCatColumn")
	@ResponseBody
	public ResultMessage getCatColumn(Long id){
		MemeCategory cat=this.memeCategoryService.selectByPrimaryKey(id);
		if(null==cat) return ResultMessage.failed("栏目分类不存在");
		MemeColumn col=this.memeColumnService.selectByPrimaryKey(cat.getColumnid());
		if(null == col) return ResultMessage.failed("此栏目不存在");
		return ResultMessage.success("", col);
	}
}
