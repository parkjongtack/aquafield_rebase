package com.soft.web.mail;

import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.soft.web.base.GenericController;
import com.soft.web.util.DecoderUtil;


@Controller
public class MailController extends GenericController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Resource(name="config")
    private Properties config;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private MailService mailService;

	@Value("#{config['mail.fromMail']}") String fromMail;
	@Value("#{config['mail.fromName']}") String fromName;

	@RequestMapping(value = "/mail/sendmail.af")
    public String sendmail(@RequestParam Map param, ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

		String realPath = session.getServletContext().getRealPath("/common/mailTemplete");
		
		String joinHtml = super.getHTMfile(realPath+"/join_complete.html");//super.getOpenStreamHTML(config.getProperty("join.mail.templete"));	
		String joinHtmlRe= joinHtml.replace("{{#NAME#}}", "홍길동");

		
		boolean booleanresult =	mailService.sendmail(mailSender, "webmaster@aquafield", "아쿠아필드", "eg92@naver.com", "홍길동", "[아쿠아필드]회원가입메일입니다.", joinHtmlRe);
		
		return "redirect:/";
    }
}
