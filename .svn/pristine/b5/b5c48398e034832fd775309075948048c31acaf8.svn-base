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
import com.meme.im.entity.MemeColumnSectionTreeGrid;
import com.meme.im.pojo.MemeCategory;
import com.meme.im.pojo.MemeColumn;
import com.meme.im.pojo.MemeColumnSection;
import com.meme.im.pojo.view.MemeColumnSectionView;
import com.meme.im.service.MemeCategoryService;
import com.meme.im.service.MemeColumnSectionService;
import com.meme.im.service.MemeColumnService;
import com.meme.qiniu.util.QiniuAPI;

@Controller
@RequestMapping("/im/column/section/")
public class MemeColumnSectionController extends BaseController implements IController {
	
	@Resource private MemeColumnSectionService memeColumnSectionService;
	@Resource private MemeColumnService memeColumnService;
	@Resource private MemeCategoryService memeCategoryService;

	@Override
	@RequestMapping(value="loadValidateRules")
	@ResponseBody
	public String loadValidationRules(){
		String js=ValidateBuilder.createJsValidateRules(MemeColumnSection.class);
		return js;
	}
	
	@RequestMapping("index")
	public ModelAndView index() {
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("/im/column/section/list", model);
	}
	
	@RequestMapping("list")
	@ResponseBody
	public List<MemeColumnSectionTreeGrid> list() throws IllegalAccessException, InvocationTargetException{
		List<MemeColumnSectionTreeGrid> nodes=new ArrayList<MemeColumnSectionTreeGrid>();
		List<MemeColumnSectionView> list=this.memeColumnSectionService.selectAllSections();
		if(null!=list&&list.size()>0){
			List<MemeColumnSectionTreeGrid> tmp=this.getTreeNodes(list);
			if(null!=tmp&&tmp.size()>0) nodes.addAll(tmp);
		}
		return nodes;
	}
	
	public List<MemeColumnSectionTreeGrid> getTreeNodes(List<MemeColumnSectionView> list) throws IllegalAccessException, InvocationTargetException{
		List<MemeColumnSectionTreeGrid> nodes=new ArrayList<MemeColumnSectionTreeGrid>();
		if(null!=list&&list.size()>0){
			for(MemeColumnSectionView obj:list){
				if(obj.getPid().longValue()==0l){
					MemeColumnSectionTreeGrid node=new MemeColumnSectionTreeGrid();
					BeanUtils.copyProperties(node, obj);
					DictItemView ispagination=DictCache.getDictItem("SF", node.getIspagination().toString());
					if(null!=ispagination){
						node.setIspaginationText(ispagination.getDictitemname());
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
	
	public static List<MemeColumnSectionTreeGrid> recursionTreeNode(List<MemeColumnSectionTreeGrid> list,MemeColumnSectionView obj) throws IllegalAccessException, InvocationTargetException{
		for(int i=0;i<list.size();i++){
			MemeColumnSectionTreeGrid node=list.get(i);
			if(obj.getPid().longValue()==node.getId().longValue()){
				MemeColumnSectionTreeGrid tn=new MemeColumnSectionTreeGrid();
				BeanUtils.copyProperties(tn, obj);
				
				DictItemView ispagination=DictCache.getDictItem("SF", tn.getIspagination().toString());
				if(null!=ispagination){
					tn.setIspaginationText(ispagination.getDictitemname());
				}
				if(StringUtil.isAllNotEmpty(tn.getTag())){
					DictItemView tag=DictCache.getDictItem("TAG_LIST", tn.getTag());
					if(null!=tag) tn.setTagText(tag.getDictitemname());
				}
				
				boolean flag=false;
				if(null!=node.getChildren()&&node.getChildren().size()>0){
					for(MemeColumnSectionTreeGrid t:node.getChildren()){
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
		MemeColumnSection obj=null;
		if(id!=null&&id!=0L){
			obj=this.memeColumnSectionService.selectByPrimaryKey(id);
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
		
		return new ModelAndView("/im/column/section/edit",model);
	}
	
	@RequestMapping("doAdd")
	@ResponseBody
	@SysLog(event = "新增栏目块",type=LogType.ADD)
	public ResultMessage add(HttpServletRequest request,MemeColumnSection obj){
		return this.memeColumnSectionService.add(request, obj);
	}
	
	@RequestMapping("doEdit")
	@ResponseBody
	@SysLog(event = "修改栏目块",type=LogType.UPDATE)
	public ResultMessage edit(HttpServletRequest request,MemeColumnSection obj){
		return this.memeColumnSectionService.edit(request, obj);
	}
	
	@RequestMapping("delete")
	@ResponseBody
	@SysLog(event = "删除栏目块",type=LogType.DELETE)
	public ResultMessage delete(@RequestParam("ids[]") List<Object> ids){
		List<Object> childList=new ArrayList<Object>();
    	List<MemeColumnSectionView> list=this.memeColumnSectionService.selectAllSections();
    	if(null!=ids&&ids.size()>0){
    		for(Object obj:ids){
    			List<Object> tmp=this.getChildList(list, Long.valueOf((String)obj));
    			childList.addAll(tmp);
    			childList.add(obj);
    		}
    	}
		int result=this.memeColumnSectionService.batchDelete(childList);
		if(result>0) return ResultMessage.defaultSuccessMessage();
		return ResultMessage.defaultFaileMessage();
	}
	
	private List<Object> getChildList(List<MemeColumnSectionView> list,Long pid){
		List<Object> objs=new ArrayList<Object>();
		for(MemeColumnSection obj:list){
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
		List<MemeColumnSectionView> list=this.memeColumnSectionService.selectAllSections();
		if(null!=list&&list.size()>0){
			return TreeUtil.getTreeNodes(list);
		}
		return new ArrayList<BaseTreeNode>();
	}
	
	@RequestMapping("getSectionColumn")
	@ResponseBody
	public ResultMessage getCatColumn(Long id){
		MemeColumnSection cat=this.memeColumnSectionService.selectByPrimaryKey(id);
		if(null==cat) return ResultMessage.failed("栏目块不存在");
		MemeColumn col=this.memeColumnService.selectByPrimaryKey(cat.getColumnid());
		if(null == col) return ResultMessage.failed("此栏目不存在");
		return ResultMessage.success("", col);
	}
	
	/**
	 * 根据栏目分类查找所属栏目的栏目块
	 * @param catid
	 * @return
	 */
	@RequestMapping("getSections")
	@ResponseBody
	public ResultMessage getSections(Long catid){
		if(null==catid) return ResultMessage.failed("请提供所属栏目分类");
		MemeCategory cat=this.memeCategoryService.selectByPrimaryKey(catid);
		if(null==cat) return ResultMessage.failed("所属栏目分类不存在");
		List<MemeColumnSectionView> list=this.memeColumnSectionService.selectColumnSections(cat.getColumnid());
		if(null == list || list.size() ==0) list=new ArrayList<MemeColumnSectionView>();
		return ResultMessage.success("",list);
	}
}
