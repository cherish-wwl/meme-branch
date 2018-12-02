package com.meme.core.email;

import com.meme.core.util.PropertiesUtil;
import com.meme.core.util.StringUtil;

/**
 * 邮件消息封装类
 * @author hzl
 *
 */
@Deprecated
public class EmailMessage {

	private String from;
    private String to;
    private String datafrom;
    private String datato;
    private String subject;
    private String content;
    private String date;
    private String user;
    private String password;
    
	public String getFrom() {
		if(StringUtil.isEmpty(from)){
			return PropertiesUtil.getProperty("email_account");
		}
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getDatafrom() {
		if(StringUtil.isEmpty(datafrom)){
			return PropertiesUtil.getProperty("email_account");
		}
		return datafrom;
	}
	public void setDatafrom(String datafrom) {
		this.datafrom = datafrom;
	}
	public String getDatato() {
		return datato;
	}
	public void setDatato(String datato) {
		this.datato = datato;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getUser() {
		if(StringUtil.isEmpty(user)){
			String email_user=PropertiesUtil.getProperty("email_user");
			return email_user;
		}
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		if(StringUtil.isEmpty(password)){
			return PropertiesUtil.getProperty("email_password");
		}
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
    
}
