package com.meme.im.pojo;

import java.math.BigDecimal;

import com.meme.core.mybatis.annotation.Table;

@Table(name="meme_wb_shop",id="productid")
public class MemeWbShop {
	private String productid;

    private String subject;

    private String body;

    private BigDecimal amount;

    private Long memberid;
    
    private String file;
    
	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Long getMemberid() {
		return memberid;
	}

	public void setMemberid(Long memberid) {
		this.memberid = memberid;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

}