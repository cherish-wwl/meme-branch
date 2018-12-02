package com.meme.core.easyui;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

/**
 * easyui Accordion结构类
 * 
 * @author hzl
 * 
 * @param <T>
 */
public abstract class AbstractAccordionMenu<T> {

	/** 左侧顶级accordion菜单 **/
	private T accordion;
	/** accordion下子菜单树 **/
	private List<TreeNode> list=new ArrayList<TreeNode>();

	public T getAccordion() {
		return accordion;
	}

	public void setAccordion(T accordion) {
		this.accordion = accordion;
	}

	public List<TreeNode> getList() {
		return list;
	}

	public void setList(List<TreeNode> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
}
