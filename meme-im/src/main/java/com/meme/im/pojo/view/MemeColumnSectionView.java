package com.meme.im.pojo.view;

import com.meme.im.pojo.MemeColumnSection;

public class MemeColumnSectionView extends MemeColumnSection {

	private String columnname;
	private String ispaginationText;
	private String tagText;

	public String getColumnname() {
		return columnname;
	}

	public void setColumnname(String columnname) {
		this.columnname = columnname;
	}

	public String getIspaginationText() {
		return ispaginationText;
	}

	public void setIspaginationText(String ispaginationText) {
		this.ispaginationText = ispaginationText;
	}

	public String getTagText() {
		return tagText;
	}

	public void setTagText(String tagText) {
		this.tagText = tagText;
	}

}
