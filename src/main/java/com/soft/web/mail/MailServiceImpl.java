package com.soft.web.mail;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());	

	@Autowired
    protected SqlSession sqlSession;

	@Override
	public boolean sendmail(JavaMailSender mailSender, String fromMail,
			String fromName, String toMail, String toName, String subject,
			String content) throws MessagingException {
		boolean result;
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			messageHelper.setFrom(new InternetAddress(fromMail, fromName));
			messageHelper.setTo(new InternetAddress(toMail, toName));
			messageHelper.setSubject(subject);
			messageHelper.setText(content,true);
			mailSender.send(message);
			result = true;
		} catch(Exception e){
			result = false;
			logger.info(e.toString());
		}
		return result;
	}
}
