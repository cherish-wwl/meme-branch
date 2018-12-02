package com.meme.im.entity;

import java.util.ArrayList;
import java.util.List;

import com.meme.im.pojo.CategoryAudio;

public class CategoryAudioTreeGrid extends CategoryAudio {

	private List<CategoryAudioTreeGrid> children=new ArrayList<CategoryAudioTreeGrid>();

	public List<CategoryAudioTreeGrid> getChildren() {
		return children;
	}

	public void setChildren(List<CategoryAudioTreeGrid> children) {
		this.children = children;
	}
}
