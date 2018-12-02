package com.meme.im.form;

import com.meme.core.form.Form;

public class MovieForm extends Form {

	private String movieCategory;//电影类型
	private Long admireNumber;//赞赏数
	

	public String getMovieCategory() {
		return movieCategory;
	}

	public void setMovieCategory(String movieCategory) {
		this.movieCategory = movieCategory;
	}
	public Long getAdmireNumber() {
		return admireNumber;
	}

	public void setAdmireNumber(Long admireNumber) {
		this.admireNumber = admireNumber;
	}

}
