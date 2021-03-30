package com.soft.web.sms;

import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.soft.web.base.GenericController;
import com.soft.web.util.DecoderUtil;
import com.soft.web.util.Util;

@Controller
public class SmsController extends GenericController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name="config")
    private Properties config;
    
	@Autowired
	SmsService smsService;


	@RequestMapping(value = "/sendsms.af")
    public String sendsms(@RequestParam Map param, ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

		String sendType = (String) param.get("sendType");
		String userName = DecoderUtil.decode(param, "userName");
		String userPhone = (String) param.get("recipient_num");
		String reserveUid = DecoderUtil.decode(param,"reserveUid");
		String reserveDay = DecoderUtil.decode(param,"reserveDay");
		String userId = DecoderUtil.decode(param,"userMail");		 
		String contents = "";
		if("JOIN".equals(sendType)) contents = "[아쿠아필드]"+userName+"고객님의 회원가입이"+userId+"로 완료되었습니다.앞으로 많은 이용바랍니다.";
		if("RESERVE".equals(sendType)) contents = "[아쿠아필드]"+reserveDay+"아쿠아필드 하남점 온라인 예약(예약번호:"+reserveUid+")이 완료되었습니다.";
		if("CANCEL".equals(sendType)) contents = "[아쿠아필드]"+reserveDay+"아쿠아필드 하남점 온라인 예약(예약번호:"+reserveUid+")이 취소되었습니다.";
	
		//-- CRMSMS.CRMSMS_MST 저장 
		param.put("recipient_num", "01012341234"); // 수신번호
		//param.put("subject", "테스트 문자 입니다."); // LMS일경우 제목을 추가 할 수 있다.
		param.put("content", "문자메세지 테스트"); // 내용 (SMS=88Byte, LMS=2000Byte)		
		//param.put("callback", "031-8072-8800"); // 발신번호
		param.put("callback", config.getProperty("sms.tel.number")); // 발신번호

		if(smsService.sendSms(param) ){
			logger.debug("성공");
			Util.htmlPrint("성공", response);
		}
		else{
			logger.debug("실패");
			Util.htmlPrint("실패", response);
		}
		return null;
    }
}
