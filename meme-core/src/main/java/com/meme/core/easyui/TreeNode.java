package com.meme.core.easyui;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

/**
 * easyui 树节点类
 * @author hzl
 *
 */
public class TreeNode {

	private Object id;
	private String text;
	private String iconCls;
	private boolean checked;
	/**closed or open**/
	private String state;
	private JSONObject attributes;
	private List<TreeNode> children=new ArrayList<TreeNode>();

	public Object getId() {
		return id;
	}

	public void setId(Object id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public JSONObject getAttributes() {
		return attributes;
	}

	public void setAttributes(JSONObject attributes) {
		this.attributes = attributes;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
