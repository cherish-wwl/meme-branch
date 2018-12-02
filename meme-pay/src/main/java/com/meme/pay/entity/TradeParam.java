package com.meme.pay.entity;

public class TradeParam {
	/** 商户订单号，商户网站订单系统中唯一订单号，必填 **/
	private String tradeno;
	/** 订单名称，必填 **/
	private String subject;
	/** 付款金额，必填 **/
	private String amount;
	/** 商品描述，可空 **/
	private String body;
	/** 超时时间 可空 **/
	private String timeout = "2m";
	/** 销售产品码 必填 **/
	private String productid;
	private Integer num;

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getTradeno() {
		return tradeno;
	}

	public void setTradeno(String tradeno) {
		this.tradeno = tradeno;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}
}
