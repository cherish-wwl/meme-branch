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
import com.meme.im.entity.MemeColumnTreeGrid;
import com.meme.im.pojo.MemeColumn;
import com.meme.im.service.MemeColumnService;
import com.meme.qiniu.util.QiniuAPI;

@Controller
@RequestMapping("/im/column/")
public class MemeColumnController extends BaseController implements IController {
	
	@Resource private MemeColumnService memeColumnService;

	@Override
	@RequestMapping(value="loadValidateRules")
	@ResponseBody
	public String loadValidationRules(){
		String js=ValidateBuilder.createJsValidateRules(MemeColumn.class);
		return js;
	}
	
	@RequestMapping("index")
	public ModelAndView index() {
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("/im/column/list", model);
	}
	
	@RequestMapping("list")
	@ResponseBody
	public List<MemeColumnTreeGrid> list() throws IllegalAccessException, InvocationTargetException{
		List<MemeColumnTreeGrid> nodes=new ArrayList<MemeColumnTreeGrid>();
		List<MemeColumn> list=this.memeColumnService.selectAllColumns();
		if(null!=list&&list.size()>0){
			List<MemeColumnTreeGrid> tmp=this.getTreeNodes(list);
			if(null!=tmp&&tmp.size()>0) nodes.addAll(tmp);
		}
		return nodes;
	}
	
	public List<MemeColumnTreeGrid> getTreeNodes(List<MemeColumn> list) throws IllegalAccessException, InvocationTargetException{
		List<MemeColumnTreeGrid> nodes=new ArrayList<MemeColumnTreeGrid>();
		if(null!=list&&list.size()>0){
			for(MemeColumn obj:list){
				if(obj.getPid().longValue()==0l){
					MemeColumnTreeGrid node=new MemeColumnTreeGrid();
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
	
	public static List<MemeColumnTreeGrid> recursionTreeNode(List<MemeColumnTreeGrid> list,MemeColumn obj) throws IllegalAccessException, InvocationTargetException{
		for(int i=0;i<list.size();i++){
			MemeColumnTreeGrid node=list.get(i);
			if(obj.getPid().longValue()==node.getId().longValue()){
				MemeColumnTreeGrid tn=new MemeColumnTreeGrid();
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
					for(MemeColumnTreeGrid t:node.getChildren()){
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
		MemeColumn obj=null;
		if(id!=null&&id!=0L){
			obj=this.memeColumnService.selectByPrimaryKey(id);
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
		
		return new ModelAndView("/im/column/edit",model);
	}
	
	@RequestMapping("doAdd")
	@ResponseBody
	@SysLog(event = "新增栏目",type=LogType.ADD)
	public ResultMessage add(HttpServletRequest request,MemeColumn obj){
		return this.memeColumnService.add(request, obj);
	}
	
	@RequestMapping("doEdit")
	@ResponseBody
	@SysLog(event = "修改栏目",type=LogType.UPDATE)
	public ResultMessage edit(HttpServletRequest request,MemeColumn obj){
		return this.memeColumnService.edit(request, obj);
	}
	
	@RequestMapping("delete")
	@ResponseBody
	@SysLog(event = "删除栏目",type=LogType.DELETE)
	public ResultMessage delete(@RequestParam("ids[]") List<Object> ids){
		List<Object> childList=new ArrayList<Object>();
    	List<MemeColumn> list=this.memeColumnService.selectAllColumns();
    	if(null!=ids&&ids.size()>0){
    		for(Object obj:ids){
    			List<Object> tmp=this.getChildList(list, Long.valueOf((String)obj));
    			childList.addAll(tmp);
    			childList.add(obj);
    		}
    	}
		int result=this.memeColumnService.batchDelete(childList);
		if(result>0) return ResultMessage.defaultSuccessMessage();
		return ResultMessage.defaultFaileMessage();
	}
	
	private List<Object> getChildList(List<MemeColumn> list,Long pid){
		List<Object> objs=new ArrayList<Object>();
		for(MemeColumn obj:list){
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
		List<MemeColumn> list=this.memeColumnService.selectAllColumns();
		if(null!=list&&list.size()>0){
			return TreeUtil.getTreeNodes(list);
		}
		return new ArrayList<BaseTreeNode>();
	}
}
