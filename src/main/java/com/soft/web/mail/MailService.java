package com.soft.web.mail;

import javax.mail.MessagingException;

import org.springframework.mail.javamail.JavaMailSender;

public interface MailService {
	public boolean sendmail(JavaMailSender mailSender,String fromMail, String fromName, String toMail, String toName, String subject, String content) throws MessagingException;
}
