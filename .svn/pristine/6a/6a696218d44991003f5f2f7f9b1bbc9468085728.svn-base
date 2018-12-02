package com.meme.core.easyui;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.meme.core.util.StringUtil;

/**
 * eayui 树的数据结构工具类
 * @author hzl
 *
 */
public class TreeNodeTool {
	
	/**
	 * 反射获取树节点ID
	 * @param obj
	 * @return
	 */
	public static String getPid(Object obj){
		Class<?> clazz=obj.getClass();
		try {
			PropertyDescriptor pidProp = new PropertyDescriptor("pid", clazz );
			Object pid=pidProp.getReadMethod().invoke(obj);
			return pid.toString();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 反射获取节点的父ID
	 * @param obj
	 * @return
	 */
	public static String getId(Object obj){
		Class<?> clazz=obj.getClass();
		try {
			PropertyDescriptor idProp = new PropertyDescriptor("id", clazz );
			Object id=idProp.getReadMethod().invoke(obj);
			return id.toString();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取属性值
	 * @param obj bean实例
	 * @param field bean类属性名
	 * @return
	 */
	public static Object getFieldValue(Object obj,String field){
		Class<?> clazz=obj.getClass();
		try {
			PropertyDescriptor prop = new PropertyDescriptor(field, clazz );
			Object val=prop.getReadMethod().invoke(obj);
			return val;
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 生成eayui 树的数据结构
	 * @param request
	 * @param list
	 * @param textField 树节点文字属性名，null时默认取值name
	 * @param iconField 树结点图标属性名，null时取空
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List<TreeNode> getTreeNodes(HttpServletRequest request,List list,String textField,String iconField){
		List<TreeNode> nodes=new ArrayList<TreeNode>();
		if(null!=list&&list.size()>0){
			for(Object obj:list){
				String pidStr=TreeNodeTool.getPid(obj);
				if(StringUtil.isAllNotEmpty(pidStr)&&pidStr.equals("0")){
					TreeNode node=new TreeNode();
					node.setId(TreeNodeTool.getFieldValue(obj, "id"));
					if(StringUtil.isAllNotEmpty(textField)) node.setText(TreeNodeTool.getFieldValue(obj, textField).toString());
					else node.setText(TreeNodeTool.getFieldValue(obj, "name").toString());
					if(StringUtil.isAllNotEmpty(iconField)) node.setIconCls(TreeNodeTool.getFieldValue(obj, iconField).toString());
					else node.setIconCls("");
					node.setAttributes(JSONObject.parseObject(JSONObject.toJSONString(obj)));
					nodes.add(node);
				}else{
					recursionTreeNode(request,nodes,obj,textField,iconField);
				}
			}
		}
		return nodes;
	}
	
	/**
	 * 递归生成树
	 * @param request
	 * @param list
	 * @param obj
	 * @param textField
	 * @param iconField
	 * @return
	 */
	public static List<TreeNode> recursionTreeNode(HttpServletRequest request,List<TreeNode> list,Object obj,String textField,String iconField){
		for(int i=0;i<list.size();i++){
			TreeNode node=list.get(i);
			String pidStr=TreeNodeTool.getPid(obj);
			String idStr=TreeNodeTool.getId(obj);
			String text=StringUtil.isEmpty(textField)?TreeNodeTool.getFieldValue(obj, "name").toString():TreeNodeTool.getFieldValue(obj, textField).toString();
			String icon=StringUtil.isEmpty(iconField)?"":TreeNodeTool.getFieldValue(obj, iconField).toString();
			if(pidStr.equals(node.getId().toString())){
				TreeNode tn=new TreeNode();
				tn.setId(idStr);
				tn.setText(text);
				tn.setIconCls(icon);
				tn.setAttributes(JSONObject.parseObject(JSONObject.toJSONString(obj)));
				boolean flag=false;
				if(null!=node.getChildren()&&node.getChildren().size()>0){
					for(TreeNode t:node.getChildren()){
						if(t.getId().toString().equals(idStr)) {flag=true;break;}
					}
				}
				if(!flag){
					list.get(i).getChildren().add(tn);
				}
				break;
			}else{
				recursionTreeNode(request,list.get(i).getChildren(), obj,textField,iconField);
			}
		}
		return list;
	}
}
