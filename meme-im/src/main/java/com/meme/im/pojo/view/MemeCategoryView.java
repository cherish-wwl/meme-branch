package com.meme.im.pojo.view;

import com.meme.im.pojo.MemeCategory;

public class MemeCategoryView extends MemeCategory {

	private String columnname;
	private String stateText;
	private String tagText;

	public String getColumnname() {
		return columnname;
	}

	public void setColumnname(String columnname) {
		this.columnname = columnname;
	}

	public String getStateText() {
		return stateText;
	}

	public void setStateText(String stateText) {
		this.stateText = stateText;
	}

	public String getTagText() {
		return tagText;
	}

	public void setTagText(String tagText) {
		this.tagText = tagText;
	}

}
