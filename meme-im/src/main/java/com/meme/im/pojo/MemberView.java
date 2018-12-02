package com.meme.im.pojo;

public class MemberView extends Member {

	private static final long serialVersionUID = 1L;
	private String sexText;
	private String mtypeText;
	private String stateText;
	private String name;
	private String idNumber;
	private String bankCard;
	
	public String getSexText() {
		return sexText;
	}

	public void setSexText(String sexText) {
		this.sexText = sexText;
	}

	public String getMtypeText() {
		return mtypeText;
	}

	public void setMtypeText(String mtypeText) {
		this.mtypeText = mtypeText;
	}

	public String getStateText() {
		return stateText;
	}

	public void setStateText(String stateText) {
		this.stateText = stateText;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}
	
	
}
