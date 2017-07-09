package com.glanway.jty.utils.mail;

import com.glanway.jty.utils.PropertiesUtil;

import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * 发邮件工具类
 * @author tianxuan
 * @Time 2016/4/22
 */
public class MailUtil {
	public static Session createSession() {
		Properties prop = PropertiesUtil.getConfig();
        final String username = PropertiesUtil.readValue("mail.username");
        final String password = PropertiesUtil.readValue("mail.password");
		// 创建验证器
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		};
		
		// 获取session对象
		return Session.getInstance(prop, auth);
	}
	
	/**
	 * 发送指定的邮件
	 * 
	 * @param mail
	 */
	public static void send(Session session, final Mail mail) throws MessagingException,
			IOException {

		final String sendFrom = PropertiesUtil.readValue("mail.sendFrom");
		MimeMessage msg = new MimeMessage(session);// 创建邮件对象
		msg.setFrom(new InternetAddress(sendFrom));// 设置发件人
		msg.addRecipients(RecipientType.TO, mail.getToAddress());// 设置收件人

		// 设置抄送
		String cc = mail.getCcAddress();
		if (!cc.isEmpty()) {
			msg.addRecipients(RecipientType.CC, cc);
		}

		// 设置暗送
		String bcc = mail.getBccAddress();
		if (!bcc.isEmpty()) {
			msg.addRecipients(RecipientType.BCC, bcc);
		}

		msg.setSubject(mail.getSubject());// 设置主题

		MimeMultipart parts = new MimeMultipart();// 创建附件集对象

		MimeBodyPart part = new MimeBodyPart();// 创建一个附件
		part.setContent(mail.getContent(), "text/html;charset=utf-8");// 设置邮件文本内容
		parts.addBodyPart(part);// 把附件添加到附件集
		
		///////////////////////////////////////////

		// 添加附件
		List<AttachBean> attachBeanList = mail.getAttachs();// 获取所有附件
		if (attachBeanList != null) {
			for (AttachBean attach : attachBeanList) {
				MimeBodyPart attachPart = new MimeBodyPart();// 创建一个部件
				attachPart.attachFile(attach.getFile());// 设置附件文件
				attachPart.setFileName(MimeUtility.encodeText(attach
						.getFileName()));// 设置附件文件名
				String cid = attach.getCid();
				if(cid != null) {
					attachPart.setContentID(cid);
				}
				parts.addBodyPart(attachPart);
			}
		}

		msg.setContent(parts);// 给邮件设置内容
		Transport.send(msg);// 发邮件
	}
}
