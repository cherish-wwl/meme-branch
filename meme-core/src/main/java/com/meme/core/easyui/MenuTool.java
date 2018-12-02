package com.meme.core.easyui;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.meme.core.pojo.Menu;
import com.meme.core.util.StringUtil;

public class MenuTool {

	/**
	 * 递归easyui 树
	 * @param request
	 * @param list
	 * @param menu
	 * @return
	 */
	public static List<TreeNode> recursionTreeNode(HttpServletRequest request,List<TreeNode> list,Menu menu){
		for(int i=0;i<list.size();i++){
			TreeNode node=list.get(i);
			if(menu.getPid().longValue()==Long.valueOf(node.getId().toString()).longValue()){
				TreeNode tn=new TreeNode();
				tn.setId(menu.getId());
				tn.setText(menu.getName());
				tn.setIconCls(menu.getIcon());
				menu.setUrl(StringUtil.matchUrl(request,menu.getUrl()));
				tn.setAttributes(JSONObject.parseObject(JSONObject.toJSONString(menu)));
				boolean flag=false;
				if(null!=node.getChildren()&&node.getChildren().size()>0){
					for(TreeNode t:node.getChildren()){
						if(t.getId().toString().equals(menu.getId().toString())) {flag=true;break;}
					}
				}
				if(!flag){
					list.get(i).getChildren().add(tn);
				}
				break;
			}else{
				recursionTreeNode(request,list.get(i).getChildren(), menu);
			}
		}
		return list;
	}
	
	/**
	 * 生成easyui 树结构
	 * @param request
	 * @param list
	 * @return
	 */
	public static List<TreeNode> getTreeNodes(HttpServletRequest request,List<Menu> list){
		List<TreeNode> nodes=new ArrayList<TreeNode>();
		if(null!=list&&list.size()>0){
			for(Menu m:list){
				if(m.getPid().longValue()==0l&&m.getHid()==0l){
					TreeNode node=new TreeNode();
					node.setId(m.getId().toString());
					node.setText(m.getName());
					node.setIconCls(m.getIcon());
					m.setUrl(StringUtil.matchUrl(request,m.getUrl()));
					node.setAttributes(JSONObject.parseObject(JSONObject.toJSONString(m)));
					nodes.add(node);
				}else{
					MenuTool.recursionTreeNode(request, nodes, m);
				}
			}
		}
		return nodes;
	}
}
