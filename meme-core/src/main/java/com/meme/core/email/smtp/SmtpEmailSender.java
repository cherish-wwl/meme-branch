package com.meme.core.email.smtp;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.meme.core.email.IEmail;
import com.meme.core.util.PropertiesUtil;
import com.meme.core.util.StringUtil;

/**
 * 邮件发送类，支持使用授权码的第三方邮箱
 * @author hzl
 *
 */
public class SmtpEmailSender extends IEmail{

	/**目标邮件**/
	private String[] mailTo;
	/**邮件标题**/
	private String subject;
	/**邮件正文内容**/
	private String content;
	/**MIME TYPE默认类型为text/html，如需发文本可设置为text/plain**/
	private String mimetype;
	/**邮件上传附件**/
	private File[] attachements;
	
	public static void main(String[] args){
		SmtpEmailSender sender=new SmtpEmailSender();
		sender.setMailTo("janler@163.com");
		sender.setSubject("test");
		sender.setContent("hello<br/> janler");
//		sender.setMimetype("text/plain");
//		sender.setAttachements(new File("D:\\测试.txt"));
		sender.send();
	}
	
	@Override
	public void init() {
		this.getProperties().setProperty("mail.smtp.host",PropertiesUtil.getProperty("mail.smtp.host"));  
		this.getProperties().setProperty("mail.smtp.socketFactory.class", PropertiesUtil.getProperty("mail.smtp.socketFactory.class"));  
		this.getProperties().setProperty("mail.smtp.socketFactory.fallback", PropertiesUtil.getProperty("mail.smtp.socketFactory.fallback"));  
		this.getProperties().setProperty("mail.smtp.port", PropertiesUtil.getProperty("mail.smtp.port"));  
		this.getProperties().setProperty("mail.smtp.socketFactory.port", PropertiesUtil.getProperty("mail.smtp.socketFactory.port"));
		this.getProperties().setProperty("mail.smtp.auth", PropertiesUtil.getProperty("mail.smtp.auth"));
		this.setUser(PropertiesUtil.getProperty("mail.user"));
		this.setPassword(PropertiesUtil.getProperty("mail.password"));
		this.setCharset(PropertiesUtil.getProperty("mail.charset"));
	}

	/**
	 * 邮件发送
	 * @return
	 */
	public boolean send() {
		this.init();
		Session session = Session.getDefaultInstance(this.getProperties(), new Authenticator(){
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(getUser(), getPassword());
			}
		});
		MimeMessage mimeMessage = new MimeMessage(session);
		try {
			mimeMessage.setFrom(new InternetAddress(this.getUser()));
			InternetAddress[] mailToAddress = new InternetAddress[this.mailTo.length];  
            for (int i = 0; i < this.mailTo.length; i++) {
            	mailToAddress[i] = new InternetAddress(this.mailTo[i]);  
            }
			mimeMessage.setRecipients(Message.RecipientType.TO, mailToAddress);
			mimeMessage.setSubject(this.subject, this.getCharset());
			Multipart multipart = new MimeMultipart();
			MimeBodyPart body = new MimeBodyPart();
			if(this.getMimetype().equals("text/plain")){
				body.setText(this.content);
			}else{
				body.setContent(this.content,this.getMimetype()+";charset="+this.getCharset());
			}
			multipart.addBodyPart(body);
			if(this.getAttachements()!=null&&this.attachements.length>0){
				for(File f:attachements){
					MimeBodyPart attachementPart = new MimeBodyPart();
					attachementPart.setDataHandler(new DataHandler(new FileDataSource(f)));
					attachementPart.setFileName(MimeUtility.encodeText(f.getName(),this.getCharset(), null));
					multipart.addBodyPart(attachementPart);
				}
			}
			mimeMessage.setContent(multipart);
			mimeMessage.setSentDate(new Date());  
            Transport.send(mimeMessage);
			return true;
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return false;
	}

	public String[] getMailTo() {
		return mailTo;
	}

	public void setMailTo(String... mailTo) {
		this.mailTo = mailTo;
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

	public String getMimetype() {
		if(StringUtil.isEmpty(mimetype)){
			return "text/html";
		}
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public File[] getAttachements() {
		return attachements;
	}

	public void setAttachements(File... attachements) {
		this.attachements = attachements;
	}
}
