package com.meme.im.pojo;

import java.math.BigDecimal;
import java.util.Date;

import com.meme.core.mybatis.annotation.Table;

@Table(name="meme_wb_order",id="orderid")
public class MemeWbOrder {
	private String orderid;

    private Long memberid;

    private String productid;

    private String tradeno;

    private String subject;

    private String body;

    private BigDecimal amount;

    private String paytype;

    private Integer state;

    private Date addtime;

    private Date paytime;
    
    private Double unit_price;

    private Integer product_count;
    
    private Double exxpress_cost;
    
    private String consignee;
    
    private String receiving_telephone;
    
    private String receiving_address;
    
    private Date deliver_time;
    
    private Date collect_time;
    
    private String courier_number;
    
    private String carrier;
    
    private Integer r_state;
    
    private String r_reason;
    
    private String handling_opinions;
    
    private Date r_apply_time;
    
    private Date r_complete_time;
    
    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid == null ? null : orderid.trim();
    }

    public Long getMemberid() {
        return memberid;
    }

    public void setMemberid(Long memberid) {
        this.memberid = memberid;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid == null ? null : productid.trim();
    }

    public String getTradeno() {
        return tradeno;
    }

    public void setTradeno(String tradeno) {
        this.tradeno = tradeno == null ? null : tradeno.trim();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject == null ? null : subject.trim();
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body == null ? null : body.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype == null ? null : paytype.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public Date getPaytime() {
        return paytime;
    }

    public void setPaytime(Date paytime) {
        this.paytime = paytime;
    }

	public Integer getProduct_count() {
		return product_count;
	}

	public void setProduct_count(Integer product_count) {
		this.product_count = product_count;
	}

	public Double getExxpress_cost() {
		return exxpress_cost;
	}

	public void setExxpress_cost(Double exxpress_cost) {
		this.exxpress_cost = exxpress_cost;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getReceiving_telephone() {
		return receiving_telephone;
	}

	public void setReceiving_telephone(String receiving_telephone) {
		this.receiving_telephone = receiving_telephone;
	}

	public String getReceiving_address() {
		return receiving_address;
	}

	public void setReceiving_address(String receiving_address) {
		this.receiving_address = receiving_address;
	}

	public Date getDeliver_time() {
		return deliver_time;
	}

	public void setDeliver_time(Date deliver_time) {
		this.deliver_time = deliver_time;
	}

	public Date getCollect_time() {
		return collect_time;
	}

	public void setCollect_time(Date collect_time) {
		this.collect_time = collect_time;
	}

	public String getCourier_number() {
		return courier_number;
	}

	public void setCourier_number(String courier_number) {
		this.courier_number = courier_number;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public Integer getR_state() {
		return r_state;
	}

	public void setR_state(Integer r_state) {
		this.r_state = r_state;
	}

	public String getR_reason() {
		return r_reason;
	}

	public void setR_reason(String r_reason) {
		this.r_reason = r_reason;
	}

	public String getHandling_opinions() {
		return handling_opinions;
	}

	public void setHandling_opinions(String handling_opinions) {
		this.handling_opinions = handling_opinions;
	}

	public Date getR_apply_time() {
		return r_apply_time;
	}

	public void setR_apply_time(Date r_apply_time) {
		this.r_apply_time = r_apply_time;
	}

	public Date getR_complete_time() {
		return r_complete_time;
	}

	public void setR_complete_time(Date r_complete_time) {
		this.r_complete_time = r_complete_time;
	}

	public Double getUnit_price() {
		return unit_price;
	}

	public void setUnit_price(Double unit_price) {
		this.unit_price = unit_price;
	}
    
    
}