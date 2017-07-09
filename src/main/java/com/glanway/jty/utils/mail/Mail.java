package com.glanway.jty.utils.mail;

import java.util.ArrayList;
import java.util.List;

/**
 * 邮件类，你需要设置：账户名和密码、收件人、抄送(可选)、暗送(可选)、主题、内容，以及附件(可选)
 * 
 * 在创建了Mail对象之后
 * 可以调用它的setSubject()、setContent()，设置主题和正文
 * 也可以调用setFrom()和　addToAddress()，设置发件人，和添加收件人。
 * 也可以调用addAttch()添加附件
 * 创建AttachBean：new AttachBean(new File("..."), "fileName");
 * @author tianxuan
 * @Time 2016/4/22
 */
public class Mail {
	private String from;//发件人
	private StringBuilder toAddress = new StringBuilder();//收件人
	private StringBuilder ccAddress = new StringBuilder();//抄送
	private StringBuilder bccAddress = new StringBuilder();//暗送
	
	private String subject;//主题
	private String content;//正文
	
	// 附件列表
	private List<AttachBean> attachList = new ArrayList<AttachBean>();
	
	public Mail() {}

	public Mail(String from) {this(from, null, null, null);}

	public Mail(String from, String to) {
		this(from, to, null, null);
	}

	public Mail(String to, String subject, String content) {
		this(null, to, subject, content);
	}

	public Mail(String from, String to, String subject, String content) {
		this.from = from;
		this.toAddress.append(to);
		this.subject = subject;
		this.content = content;
	}
	
	public void setFrom(String from) {
		this.from = from;
	}
	public String getFrom() {
		return from;
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
	public String getToAddress() {
		return toAddress.toString();
	}
	public String getCcAddress() {
		return ccAddress.toString();
	}
	public String getBccAddress() {
		return bccAddress.toString();
	}
	public void addToAddress(String to) {
		if(this.toAddress.length() > 0) {
			this.toAddress.append(",");
		}
		this.toAddress.append(to);
	}
	public void addCcAddress(String cc) {
		if(this.ccAddress.length() > 0) {
			this.ccAddress.append(",");
		}
		this.ccAddress.append(cc);
	}
	public void addBccAddress(String bcc) {
		if(this.bccAddress.length() > 0) {
			this.bccAddress.append(",");
		}
		this.bccAddress.append(bcc);
	}
	public void addAttach(AttachBean attachBean) {
		this.attachList.add(attachBean);
	}
	public List<AttachBean> getAttachs() {
		return this.attachList;
	}
}
