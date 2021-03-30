package com.soft.web.controller.admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.soft.web.base.CommonConstant;
import com.soft.web.base.GenericController;
import com.soft.web.service.admin.AdminLoginService;
import com.soft.web.service.common.CommonService;
import com.soft.web.sms.SmsService;
import com.soft.web.util.Util;
@Controller
@RequestMapping({"/secu_admaf"})
public class AdminLoginController extends GenericController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());	

    @Resource(name="config")
    private Properties config;
    
	@Autowired
	AdminLoginService adminLoginService;
	
	@Autowired
	SmsService smsService;
	
	@Autowired
	CommonService commonService;
	
	@Value("#{config['admin.access.ip']}") String accessIp;

	@RequestMapping(value="/admin/login.af", method = RequestMethod.GET)
	public String login(@RequestParam Map param, ModelMap model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		String remoteIp = request.getRemoteAddr();
		//-- 허용아이피 확인
		if(StringUtils.isNotBlank(accessIp) && accessIp.indexOf("," + remoteIp + ",") == -1){
			logger.debug("관리자 접속 불가 IP : " + remoteIp);
			try {
				Util.htmlPrint("접속이 허용되지 않았습니다.", response);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		String reqUrl = request.getRequestURI().toString();
		if(param.get("returnurl") == null) param.put("returnurl", "");		
		model.addAttribute("returnurl",param.get("returnurl"));
		
		return "/admin/login";
	}
	   
	@RequestMapping(value="/admin/login.af", method = RequestMethod.POST)
	public void loginAction(@RequestParam Map param, ModelMap model, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {		
		String remoteIp = request.getRemoteAddr();
		//-- 허용아이피 확인
		if(StringUtils.isNotBlank(accessIp) && accessIp.indexOf("," + remoteIp + ",") == -1){
			logger.debug("관리자 접속 불가 IP : " + remoteIp);
			try {
				Util.htmlPrint("접속이 허용되지 않았습니다.", response);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		
		Map<String,String> loginUser;
		try{
			if(param.get("login_id") == null || param.get("login_id").toString().equals("") ) param.put("login_id","sldfslfaslifdalsfsldf");
			if(param.get("login_pw") == null || param.get("login_pw").toString().equals("") ) param.put("login_pw","sldfslfaslifdalsfsldf");
			if(param.get("hpnum") == null || param.get("hpnum").toString().equals("") ) param.put("hpnum","sldfslfaslifdalsfsldf");
			if(param.get("certnum") == null || param.get("certnum").toString().equals("") ) param.put("certnum","sldfslfaslifdalsfsldf");
			Map sqlMap = new HashMap<String,String>();
			sqlMap = new HashMap<String,String>();
			sqlMap.put("admin_id",param.get("login_id").toString() );
			sqlMap.put("admin_pw",param.get("login_pw").toString() );
			sqlMap.put("hpnum",param.get("hpnum").toString() );
			sqlMap.put("certnum",param.get("certnum").toString() );
			
			loginUser = adminLoginService.adminLogin(sqlMap);
			if(loginUser == null){
				session.invalidate();
				logger.debug("로그인 실패 ADMIN_ID : " + param.get("login_id"));
				Util.htmlPrint("{\"result\":false,\"msg\":\"아이디 또는 비밀번호,인증번호를 확인하세요!\"}", response);
				return;
			}
			else{
				logger.debug("로그인 성공 ADMIN_ID : " + param.get("login_id"));

				//-- 로그인 KEY
				String loginDigit = Util.getLoginDigit();		
					
				//-- 로그인 세션정보
				Map<String,String> user = new HashMap<String,String>();
				user.put("SESSION_ADMIN_UID", String.valueOf(loginUser.get("ADMIN_UID") ) );
				user.put("SESSION_ADMIN_ID", String.valueOf(loginUser.get("ADMIN_ID") ) );
				user.put("SESSION_ADMIN_NM", String.valueOf(loginUser.get("ADMIN_NM") ) );
				user.put("SESSION_LOGIN_DIGIT", loginDigit);
				user.put("SESSION_MEMINFOYN", String.valueOf(loginUser.get("MEMBER_VIEW_AT") ) );
				user.put("SESSION_DEPT", String.valueOf(loginUser.get("ADMIN_DEPT") ) );
				session.setAttribute(CommonConstant.session.SESSION_KEY_ADMIN, user);
				
				//-- 로그인 정보
				sqlMap.put("login_digit",loginDigit);
				
				//-- 로그인 정보수정
				adminLoginService.updateAdminLoginDigit(sqlMap);
				
				Util.htmlPrint("{\"result\":true,\"msg\":\"로그인 성공\"}", response);				
				return;
			}
		}
		catch(Exception e){
			logger.debug(e.toString());
			Util.htmlPrint("{\"result\":false,\"msg\":\"로그인시 문제가 발생했습니다. 잠시후에 다시 하세요!\"}", response);
			return;
		}
		
	}
	
	@RequestMapping(value="/admin/login_sms.af", method = RequestMethod.POST)
	public void loginSms(@RequestParam Map param, ModelMap model, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Throwable {	
		String remoteIp = request.getRemoteAddr();
		//-- 허용아이피 확인
		if(StringUtils.isNotBlank(accessIp) && accessIp.indexOf("," + remoteIp + ",") == -1){
			logger.debug("관리자 접속 불가 IP : " + remoteIp);
			try {
				Util.htmlPrint("접속이 허용되지 않았습니다.", response);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		
		Map sqlMap = new HashMap<String,String>();
		sqlMap = new HashMap<String,String>();
		sqlMap.put("admin_id",param.get("login_id").toString() );
		sqlMap.put("admin_pw",param.get("login_pw").toString() );
		sqlMap.put("hpnum",param.get("hpnum").toString() );
		
		Map<String,String> adminUser = adminLoginService.adminLogin(sqlMap);
		//-- 관리자 정보가 있다.
		if(adminUser != null){
			//-- 문자메세지 발송
			sqlMap = new HashMap<String,String>();
			String certNo = Util.getCertNo();
			sqlMap.put("certnum",certNo);
			sqlMap.put("admin_id",param.get("login_id"));
			//-- 비교할 문자를 관리자 정보에 넣는다.
			adminLoginService.updateAdminCertnum(sqlMap);
			
			Map smsParam = new HashMap();
			smsParam.put("point_code", "POINT01");
			smsParam.put("sms_type", "CERTI");
			
			Map smsTemplte = commonService.getSmsTemplete(smsParam);
			String contents = smsTemplte.get("SMS_CONTENT").toString();
			contents = contents.replace("{인증번호}",certNo);//인증번호 치환		
			
			//-- 문자메세지 발송
			sqlMap = new HashMap<String,String>();
			sqlMap.put("recipient_num", param.get("hpnum").toString() ); // 수신번호
			//sqlMap.put("content", "인증번호[" + certNo + "] 인증번호에 입력하세요."); // 내용 (SMS=88Byte, LMS=2000Byte)
			sqlMap.put("content", contents); // 내용 (SMS=88Byte, LMS=2000Byte)		
			//sqlMap.put("callback", "031-8072-8800"); // 발신번호
			sqlMap.put("callback", config.getProperty("sms.tel.number")); // 발신번호
			
			
			
			try{
				
				if(smsService.sendSms(sqlMap) ){
				
					Util.htmlPrint("{\"result\":true,\"msg\":\"인증번호를 전송하였습니다."+certNo+"\"}", response);
					
					//SMS 발송 이력 등록
					smsParam.put("sms_uid", smsTemplte.get("SMS_UID"));
					smsParam.put("mem_id", param.get("login_id").toString());
					smsParam.put("custom_nm", adminUser.get("ADMIN_NM").toString());
					smsParam.put("custom_mobile",  param.get("hpnum").toString());
					smsParam.put("ins_ip", request.getRemoteAddr());
					smsParam.put("ins_id", param.get("login_id").toString()); 
					smsParam.put("send_status", "OK");	
					smsParam.put("bigo", "CERTI");			
					
					String smsResult = commonService.insSmsSend(smsParam);
					if("ERROR".equals(smsResult)){
						Util.htmlPrint("{\"result\":true,\"msg\":\"처리중 에러가 발생하였습니다.(문자이력등록)\"}", response);
					}
					
				}
				else{
					Util.htmlPrint("{\"result\":false,\"msg\":\"문자발송에 문제가 있습니다. 잠시후에 다시 하세요.\"}", response);
				}
			}
			catch(Exception e){
				logger.debug("LOGIN SMS 발송 : " + e.toString());
				Util.htmlPrint("{\"result\":false,\"msg\":\"오류가 발생했습니다. 잠시후에 다시 하세요.\"}", response);				
			}
			return;
		}
		//-- 관리자 정보가 없다.
		else{
			//-- 실패메세지
			Util.htmlPrint("{\"result\":false,\"msg\":\"일치하는 정보가 없습니다.\"}", response);				
			return;
		}
	}
	
	@RequestMapping(value="/admin/logout.af")
	public String logout(@RequestParam Map param, ModelMap model, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Throwable {
		String reqUrl = request.getRequestURI().toString();
		String modelUrl = "";
		modelUrl = "redirect:/secu_admaf/admin/login.af";
		session.invalidate();
		return modelUrl;
	}
	
}
