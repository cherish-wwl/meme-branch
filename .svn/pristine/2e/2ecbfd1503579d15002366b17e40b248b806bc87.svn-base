package com.meme.im.pojo;

import java.io.Serializable;
import java.util.Date;
import com.meme.core.mybatis.annotation.Table;


@Table(name="meme_im_movie",id="movieId")
public class MemeMovie implements Serializable{
	
	private static final long serialVersionUID = 5983847268592746537L;
	
	private Long movieId;
	private String movieName;//电影名
	private String movieCategory;//电影类型
	private String movieSynopsis;//内容简介
	private String movieCover;//封面地址
	private String movieAddr;//视频地址
	private String account;//会员账号
	private Long admireNumber;//赞赏数
	private Date addTime;//录入时间
	private int orderBy;//排序
	
	
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(int orderBy) {
		this.orderBy = orderBy;
	}
	public Long getMovieId() {
		return movieId;
	}
	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public String getMovieSynopsis() {
		return movieSynopsis;
	}
	public void setMovieSynopsis(String movieSynopsis) {
		this.movieSynopsis = movieSynopsis;
	}
	public String getMovieCover() {
		return movieCover;
	}
	public void setMovieCover(String movieCover) {
		this.movieCover = movieCover;
	}
	public String getMovieAddr() {
		return movieAddr;
	}
	public void setMovieAddr(String movieAddr) {
		this.movieAddr = movieAddr;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public Long getAdmireNumber() {
		return admireNumber;
	}
	public void setAdmireNumber(Long admireNumber) {
		this.admireNumber = admireNumber;
	}
	public String getMovieCategory() {
		return movieCategory;
	}
	public void setMovieCategory(String movieCategory) {
		this.movieCategory = movieCategory;
	}
	
	public MemeMovie() {
		super();
	}
	public MemeMovie(Long movieId, String movieName, String movieCategory, String movieSynopsis, String movieCover,
			String movieAddr, String account, Long admireNumber, Date addTime, int orderBy) {
		super();
		this.movieId = movieId;
		this.movieName = movieName;
		this.movieCategory = movieCategory;
		this.movieSynopsis = movieSynopsis;
		this.movieCover = movieCover;
		this.movieAddr = movieAddr;
		this.account = account;
		this.admireNumber = admireNumber;
		this.addTime = addTime;
		this.orderBy = orderBy;
	}
	@Override
	public String toString() {
		return "MemeMovie [movieId=" + movieId + ", movieName=" + movieName + ", movieCategory=" + movieCategory
				+ ", movieSynopsis=" + movieSynopsis + ", movieCover=" + movieCover + ", movieAddr=" + movieAddr
				+  ", account=" + account + ", admireNumber=" + admireNumber + ", addTime="
				+ addTime + ", orderBy=" + orderBy + "]";
	}
	
}
