package com.meme.im.form;

import java.math.BigDecimal;

import com.meme.core.form.Form;

public class ImForm extends Form {
	private String productname;
	private String productintro;
	private BigDecimal productprice;
	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getProductintro() {
		return productintro;
	}

	public void setProductintro(String productintro) {
		this.productintro = productintro;
	}

	public BigDecimal getProductprice() {
		return productprice;
	}

	public void setProductprice(BigDecimal productprice) {
		this.productprice = productprice;
	}

	/** 发送方账号或昵称 **/
	private String send_str;
	/** 接收方账号或昵称 **/
	private String accept_str;
	private String send_mtype;
	private String accept_mtype;
	private String msgtype;
	private Integer isnew;
	private Integer isrecommend;
	private Integer isanchor;
	
	private Integer reco_song;
	private Integer reco_mv;
	private Integer reco_audio;
	private Integer tvtype;
	private String tvStation;
	private Integer ishotshow;
	private Integer istop;
	
	private Long catid;
	private Long columnid;
	private Long sectionid;
	private String columncode;
	private String sectioncode;
	/**会员账号或者昵称查询条件**/
	private String member_str;
	private String member_str1;
	private String member_str2;
	private Long memberid;
	private Integer file_type;
	private Integer source;
	private Integer type;
	private Integer part;
	public Long getMemberid() {
		return memberid;
	}

	public void setMemberid(Long memberid) {
		this.memberid = memberid;
	}

	public String getColumncode() {
		return columncode;
	}

	public void setColumncode(String columncode) {
		this.columncode = columncode;
	}

	public String getSectioncode() {
		return sectioncode;
	}

	public void setSectioncode(String sectioncode) {
		this.sectioncode = sectioncode;
	}

	public Long getCatid() {
		return catid;
	}

	public void setCatid(Long catid) {
		this.catid = catid;
	}

	public Long getColumnid() {
		return columnid;
	}

	public void setColumnid(Long columnid) {
		this.columnid = columnid;
	}

	public Long getSectionid() {
		return sectionid;
	}

	public void setSectionid(Long sectionid) {
		this.sectionid = sectionid;
	}

	public String getMember_str() {
		return member_str;
	}

	public void setMember_str(String member_str) {
		this.member_str = member_str;
	}

	public Integer getIshotshow() {
		return ishotshow;
	}

	public void setIshotshow(Integer ishotshow) {
		this.ishotshow = ishotshow;
	}

	public Integer getIstop() {
		return istop;
	}

	public void setIstop(Integer istop) {
		this.istop = istop;
	}

	public Integer getTvtype() {
		return tvtype;
	}

	public void setTvtype(Integer tvtype) {
		this.tvtype = tvtype;
	}

	public String getTvStation() {
		return tvStation;
	}

	public void setTvStation(String tvStation) {
		this.tvStation = tvStation;
	}

	public Integer getReco_song() {
		return reco_song;
	}

	public void setReco_song(Integer reco_song) {
		this.reco_song = reco_song;
	}

	public Integer getReco_mv() {
		return reco_mv;
	}

	public void setReco_mv(Integer reco_mv) {
		this.reco_mv = reco_mv;
	}

	public Integer getReco_audio() {
		return reco_audio;
	}

	public void setReco_audio(Integer reco_audio) {
		this.reco_audio = reco_audio;
	}

	public Integer getIsnew() {
		return isnew;
	}

	public void setIsnew(Integer isnew) {
		this.isnew = isnew;
	}

	public Integer getIsrecommend() {
		return isrecommend;
	}

	public void setIsrecommend(Integer isrecommend) {
		this.isrecommend = isrecommend;
	}

	public Integer getIsanchor() {
		return isanchor;
	}

	public void setIsanchor(Integer isanchor) {
		this.isanchor = isanchor;
	}

	public String getSend_str() {
		return send_str;
	}

	public void setSend_str(String send_str) {
		this.send_str = send_str;
	}

	public String getAccept_str() {
		return accept_str;
	}

	public void setAccept_str(String accept_str) {
		this.accept_str = accept_str;
	}

	public String getSend_mtype() {
		return send_mtype;
	}

	public void setSend_mtype(String send_mtype) {
		this.send_mtype = send_mtype;
	}

	public String getAccept_mtype() {
		return accept_mtype;
	}

	public void setAccept_mtype(String accept_mtype) {
		this.accept_mtype = accept_mtype;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public Integer getFile_type() {
		return file_type;
	}

	public void setFile_type(Integer file_type) {
		this.file_type = file_type;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getPart() {
		return part;
	}

	public void setPart(Integer part) {
		this.part = part;
	}

	public String getMember_str1() {
		return member_str1;
	}

	public void setMember_str1(String member_str1) {
		this.member_str1 = member_str1;
	}

	public String getMember_str2() {
		return member_str2;
	}

	public void setMember_str2(String member_str2) {
		this.member_str2 = member_str2;
	}
}
