package com.meme.im.pojo;

import java.util.Date;

import com.meme.core.mybatis.annotation.Table;

@Table(name = "meme_category_audio_item", id = "itemid")
public class CategoryAudioItem {
	private Long itemid;

	private String itemname;

	private String cover;

	private String url;

	private Integer sortno;

	private Integer recoSong;

	private Integer recoMv;

	private Integer recoAudio;

	private Date publishtime;

	private Long audioid;

	private String author;

	public Long getItemid() {
		return itemid;
	}

	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}

	public String getItemname() {
		return itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname == null ? null : itemname.trim();
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover == null ? null : cover.trim();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url == null ? null : url.trim();
	}

	public Integer getSortno() {
		return sortno;
	}

	public void setSortno(Integer sortno) {
		this.sortno = sortno;
	}

	public Integer getRecoSong() {
		return recoSong;
	}

	public void setRecoSong(Integer recoSong) {
		this.recoSong = recoSong;
	}

	public Integer getRecoMv() {
		return recoMv;
	}

	public void setRecoMv(Integer recoMv) {
		this.recoMv = recoMv;
	}

	public Integer getRecoAudio() {
		return recoAudio;
	}

	public void setRecoAudio(Integer recoAudio) {
		this.recoAudio = recoAudio;
	}

	public Date getPublishtime() {
		return publishtime;
	}

	public void setPublishtime(Date publishtime) {
		this.publishtime = publishtime;
	}

	public Long getAudioid() {
		return audioid;
	}

	public void setAudioid(Long audioid) {
		this.audioid = audioid;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author == null ? null : author.trim();
	}
}