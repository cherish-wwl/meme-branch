package com.meme.im.pojo;

import java.io.Serializable;
import java.util.Date;

import com.meme.core.mybatis.annotation.Table;

@Table(name="meme_member_recharge",id="id")
public class MemberRecharge implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private Long memberid;
	private Double money;
	private Integer type;
	private String typeText;
	private Date datetime;
	private String orderid;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getMemberid() {
		return memberid;
	}
	public void setMemberid(Long memberid) {
		this.memberid = memberid;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getTypeText() {
		if(type == 1) {
			typeText = "押金";
		}else if(type == 2) {
			typeText = "余额";
		}
		return typeText;
	}
	public void setTypeText(String typeText) {
		this.typeText = typeText;
	}
	public Date getDatetime() {
		return datetime;
	}
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
