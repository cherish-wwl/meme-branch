package com.meme.im.entity;

import java.util.ArrayList;
import java.util.List;

import com.meme.im.pojo.view.MemeCategoryView;

public class MemeCategoryTreeGrid extends MemeCategoryView {

	private List<MemeCategoryTreeGrid> children=new ArrayList<MemeCategoryTreeGrid>();

	public List<MemeCategoryTreeGrid> getChildren() {
		return children;
	}

	public void setChildren(List<MemeCategoryTreeGrid> children) {
		this.children = children;
	}
}
