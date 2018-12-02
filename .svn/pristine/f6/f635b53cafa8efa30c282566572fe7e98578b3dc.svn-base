package com.meme.core.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.meme.core.base.BaseTreeNode;

public class TreeUtil {

	/**
	 * 反射获取树节点ID
	 * @param obj
	 * @return
	 */
	public static Object getPid(Object obj){
		Class<?> clazz=obj.getClass();
		try {
			PropertyDescriptor pidProp = new PropertyDescriptor("pid", clazz );
			Object pid=pidProp.getReadMethod().invoke(obj);
			return pid;
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
	public static Object getId(Object obj){
		Class<?> clazz=obj.getClass();
		try {
			PropertyDescriptor idProp = new PropertyDescriptor("id", clazz );
			Object id=idProp.getReadMethod().invoke(obj);
			return id;
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
	 * 生成树
	 * @param list
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List<BaseTreeNode> getTreeNodes(List list){
		List<BaseTreeNode> nodes=new ArrayList<BaseTreeNode>();
		for(Object obj:list){
			Object pid=TreeUtil.getPid(obj);
			if(Long.valueOf(pid.toString())==0L){
				BaseTreeNode node=new BaseTreeNode();
				node.setData(obj);
				nodes.add(node);
			}else{
				TreeUtil.recurse(nodes,obj);
			}
		}
		return nodes;
	}
	
	/**
	 * 递归
	 * @param list
	 * @param obj
	 * @return
	 */
	public static List<BaseTreeNode> recurse(List<BaseTreeNode> list,Object obj){
		for(int i=0;i<list.size();i++){
			BaseTreeNode node=list.get(i);
			Object objPid=TreeUtil.getPid(obj);
			Object nodeId=TreeUtil.getId(node.getData());
			if(objPid.toString().equals(nodeId.toString())){
				BaseTreeNode bn=new BaseTreeNode();
				bn.setData(obj);
				boolean flag=false;
				if(null!=node.getChildren()&&node.getChildren().size()>0){
					for(BaseTreeNode btn:node.getChildren()){
						if(TreeUtil.getId(btn.getData()).toString().equals(TreeUtil.getId(obj).toString())){flag=true;break;}
					}
				}
				if(!flag){
					list.get(i).getChildren().add(bn);
				}
				break;
			}else{
				recurse(list.get(i).getChildren(),obj);
			}
		}
		return list;
	}
	
	/**
	 * 获取所有子节点ID数组
	 * @param list
	 * @param pid
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List<Object> getChildIds(List list,Object pid){
		List<Object> objs=new ArrayList<Object>();
		for(Object obj:list){
			Object objPid=getPid(obj);
			Object objId=getId(obj);
			if(objPid.toString().equals(pid.toString())){
				objs.add(objId);
				objs.addAll(getChildIds(list, objId));
			}
		}
		return objs;
	}
	
	/**
	 * 获取所有子节点实例数组
	 * @param list
	 * @param pid
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List<Object> getChildList(List list,Object pid){
		List<Object> objs=new ArrayList<Object>();
		for(Object obj:list){
			Object objPid=getPid(obj);
			Object objId=getId(obj);
			if(objPid.toString().equals(pid.toString())){
				objs.add(obj);
				objs.addAll(getChildList(list, objId));
			}
		}
		return objs;
	}
}
