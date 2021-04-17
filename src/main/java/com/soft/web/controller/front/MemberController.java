package com.soft.web.controller.front;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import Kisinfo.Check.IPINClient;

import com.soft.web.base.GenericController;
import com.soft.web.mail.MailService;
import com.soft.web.service.admin.AdminEmailTempletService;
import com.soft.web.service.common.CommonService;
import com.soft.web.service.front.FrontMemberService;
import com.soft.web.sms.SmsService;
import com.soft.web.util.AquaDataUtil;
import com.soft.web.util.AquaDateUtil;
import com.soft.web.util.DecoderUtil;
import com.soft.web.util.Util;

@Controller
public class MemberController extends GenericController {

	protected Logger logger = LoggerFactory.getLogger(MemberController.class);
	
    @Resource(name="config")
    private Properties config;

	@Autowired
	private JavaMailSender mailSender;
    
	@Autowired
	FrontMemberService frontMemberService;
	
	@Autowired
	SmsService smsService;
	
	@Autowired
	CommonService commonService;	
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	AdminEmailTempletService adminEmailTempletService;
	
	@RequestMapping(value = "/member/popup.af")
    public String popup(@RequestParam Map param, Model model, HttpSession session) {
		
        return "/front/member/popup";
    }
	
	@RequestMapping(value = "/member/searchId.af")
    public String searchId(@RequestParam Map param, Model model, HttpSession session) {
		
        return "/front/member/search_id";
    }
    
	@RequestMapping(value = "/member/searchIdOk.af")
    public String searchIdOk(@RequestParam Map param, Model model, HttpSession session) {

		Map parameter = new HashMap();
		parameter.put("point_code", param.get("point"));
		parameter.put("di_certi", param.get("obj"));
		
		String searchId = memberCheck(parameter);

		model.addAttribute("searchId", searchId);
		model.addAttribute("obj", param.get("obj"));
		model.addAttribute("point", param.get("point"));
        return "/front/member/search_id_completed";
    }
		
	@RequestMapping(value = "/member/resetPwdCerti.af")
    public String resetPwdCerti(@RequestParam Map param, Model model, HttpSession session) {
		
        return "/front/member/reset_pwd_certifi";
    }
	
	@RequestMapping(value = "/member/resetPwd.af")
    public String resetPwd(@RequestParam Map param, Model model, HttpSession session) {
		
		model.addAttribute("obj", param.get("obj"));
		model.addAttribute("point", param.get("point"));		
		return "/front/member/reset_pwd";
    }
	
	@RequestMapping(value = "/member/setPwd.af")
    public String setPwd(@RequestParam Map param, Model model, HttpSession session, HttpServletResponse response) throws IOException {

		Map parameter = new HashMap();
		parameter.put("point_code", param.get("point"));
		parameter.put("di_certi", param.get("obj"));
		
		String searchId = memberCheck(parameter);
		
		if(!"".equals(searchId)){
			
			parameter.put("mem_pw", param.get("pwd"));
			String result = frontMemberService.setPassword(parameter);
			
			if("SETOK".equals(result)){
				Util.htmlPrint("YES", response);				
			}else{
		    	Util.htmlPrint("NO", response);					
			}
					
		}else{
	    	Util.htmlPrint("NO", response);			
		}

		return null;
    }
	
	@RequestMapping(value = "/member/memberChk.af")
    public String memberChk(@RequestParam Map param, Model model, HttpSession session, HttpServletResponse response) throws IOException {

		Map parameter = new HashMap();
		parameter.put("point_code", param.get("point"));
		parameter.put("di_certi", param.get("obj"));
		
		String searchId = memberCheck(parameter);
		
		if(!"".equals(searchId)){
			Util.htmlPrint("YES", response);				
		}else{
		   	Util.htmlPrint("NO", response);					
		}
					
		return null;
    }	
	
	@RequestMapping(value = "/member/inactivityCerti.af")
    public String inactivityCerti(@RequestParam Map param, Model model, HttpSession session) {
		
		Map parameter = new HashMap();
		parameter.put("num", param.get("data"));

		model.addAttribute("parameter", parameter);
        return "/front/member/inactivity_certifi";
    }
	
	@RequestMapping(value = "/member/inactivity.af")
    public String inactivity(@RequestParam Map param, Model model, HttpSession session) {

		Map parameter = new HashMap();
		parameter.put("num", param.get("num"));
		parameter.put("obj", param.get("obj"));	
		
		model.addAttribute("parameter", parameter);
        return "/front/member/inactivity";
    }
	
	@ResponseBody
	@RequestMapping(value = "/member/inactMemUpd.af")
    public String inactMemUpd(@RequestParam Map param, Model model, HttpSession session, HttpServletResponse response) throws IOException {

		String html = "";
		Map parameter = new HashMap();
		parameter.put("mem_nm", param.get("userName"));
		parameter.put("mobile_num", param.get("phonenum"));
		/*parameter.put("home_addr1", param.get("address"));
		parameter.put("home_addr2", param.get("address2"));*/
		parameter.put("mem_pw", param.get("resetPWD1"));
		parameter.put("mem_uid", param.get("num"));
		
		String result = frontMemberService.setInactivityUpd(parameter);
		
		if("SETOK".equals(result)){
			/*String message = "정상처리되었습니다.";
			String url ="location.href='/member/loginMain.af';";
			html = Util.gotoUrl(url, message);*/
			
		}else{
			String message = "처리중 에러가 발생하였습니다.\\n다시 시도해 주시기바랍니다.";
			result = "FAILED";
		}
		/*Util.htmlPrint(html, response);*/

		return result;
    }	
	
	@RequestMapping(value = "/member/checkSignup.af")
    public String checkSignup(@RequestParam Map param, Model model, HttpSession session) {
		
        return "/front/member/check_signup";
    }
	
	@RequestMapping(value = "/member/signupStep1.af")
    public String signupStep1(@RequestParam Map param, Model model, HttpSession session) {
		
        return "/front/member/signup_step1";
    }
	
	@RequestMapping(value = "/member/signupStep2.af")
    public String signupStep2(@RequestParam Map param, Model model, HttpSession session) throws Exception {

		Map parameter = new HashMap();
		parameter.put("chkTerm", param.get("chkTerm"));
		parameter.put("obj", DecoderUtil.decode(param,"obj"));
		parameter.put("objName", DecoderUtil.decode(param, "objName"));
		parameter.put("objGender", param.get("objGender"));
		parameter.put("objBirth", param.get("objBirth"));
		parameter.put("objMNum", param.get("objMNum"));
		
		String chkTerm = (String) param.get("chkTerm");
		String obj = (String) param.get("obj");
		
		if(!"".equals(obj) && obj != null && !"".equals(chkTerm) && chkTerm != null) {
			
			model.addAttribute("parameter", parameter);
			return "/front/member/signup_step2";
			
		} else {
			
			throw new Exception();
			
		}
        
    }
	
	@RequestMapping(value = "/member/duplicateChk.af")
    public String duplicateChk(@RequestParam Map param, Model model, HttpSession session, HttpServletResponse response) throws IOException {

		String html = "";
		Map parameter = new HashMap();
		parameter.put("point_code", param.get("point"));
		parameter.put("di_certi", param.get("obj"));
		
		String searchId = memberCheck(parameter);
		
		if(!"".equals(searchId)){
			String message =  "입력하신 정보는 이미 가입된 회원정보입니다.";
			/*String action = "memberPop.addCont({url : '/member/loginMain.af'});";*/
			String action = "location.href='/member/loginMain.af';";
			html = Util.alertToAction(message, action);							
		}else{
			String message = "";
			String url ="goStep2();";
			html = Util.gotoUrl(url, message);			
		}
		Util.htmlPrint(html, response);

		return null;
    }	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/member/signupStep3.af")
    public String signupStep3(@RequestParam Map param, Model model, HttpSession session) throws UnsupportedEncodingException {
					
		Map parameter = new HashMap();
		
		parameter.put("chkTerm", DecoderUtil.decode(param, "chkTerm"));
		parameter.put("userName", DecoderUtil.decode(param, "userName"));
		parameter.put("phoneNum", DecoderUtil.decode(param, "phoneNum"));
		parameter.put("userId", DecoderUtil.decode(param, "userId"));
		parameter.put("userPw01", DecoderUtil.decode(param, "userPw01"));
		parameter.put("birthDay", DecoderUtil.decode(param, "objBirth"));
		//parameter.put("lunar", DecoderUtil.decode(param, "lunar"));
		parameter.put("sex", DecoderUtil.decode(param, "objGender"));
		//parameter.put("address", DecoderUtil.decode(param, "address"));
		//parameter.put("address2", DecoderUtil.decode(param, "address2"));
		parameter.put("obj", DecoderUtil.decode(param, "obj"));

		model.addAttribute("parameter", parameter);
        return "/front/member/signup_step3";
    }	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/member/signupStep4.af")
    public String signupStep4(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map parameter = new HashMap();
		parameter.put("mem_nm", DecoderUtil.decode(param, "userName"));
		parameter.put("mem_id", DecoderUtil.decode(param, "userId"));
		parameter.put("mem_pw", DecoderUtil.decode(param, "userPw01"));
		parameter.put("mobile_num", DecoderUtil.decode(param, "phoneNum"));
		parameter.put("mem_birth", DecoderUtil.decode(param, "birthDay"));
		parameter.put("birth_type", DecoderUtil.decode(param, "lunar"));
		parameter.put("mem_gender", DecoderUtil.decode(param, "sex"));
		parameter.put("home_addr1", DecoderUtil.decode(param, "address"));
		parameter.put("home_addr2", DecoderUtil.decode(param, "address2"));
		parameter.put("company_phone_num", DecoderUtil.decode(param, "telHome"));
		parameter.put("company_nm", DecoderUtil.decode(param, "companyNm"));
		parameter.put("company_addr1", DecoderUtil.decode(param, "companyAddr"));
		parameter.put("company_addr2", DecoderUtil.decode(param, "companyAddr2"));
		parameter.put("memory_day", DecoderUtil.decode(param, "memorialDay"));
		parameter.put("terms_at", DecoderUtil.decode(param, "chkTerm"));
		parameter.put("received_info_at", DecoderUtil.decode(param, "receiveChk"));
		parameter.put("mem_rating", DecoderUtil.decode(param, "NORMAL"));
		parameter.put("point_code", "POINT01");
		parameter.put("ins_id", DecoderUtil.decode(param, "userId"));
		parameter.put("ci_certi", DecoderUtil.decode(param, "obj"));
		
		//아이디 중복체크
		int idChk = frontMemberService.idChk(parameter);

		//본인인증한 회원의 정보 조회
		int niceChk = frontMemberService.niceChk(parameter);
		
		//본인인증한 회원의 정보가 DB에 있을때 회원가입 진행(취약점)
		if(niceChk > 0) { 	
			
			//아이디 중복체크(취약점 관련 작업)		
			if(idChk == 0) {
	
				String result = frontMemberService.memberJoin(parameter);
			
				//가입문자 발송
				//String contents = "[아쿠아필드]"+DecoderUtil.decode(param, "userName")+"고객님  회원가입완료(ID:"+parameter.get("mem_id")+")";
				Map smsParam = new HashMap();
				smsParam.put("point_code", "POINT01");
				smsParam.put("sms_type", "JOIN");
				
				Map smsTemplte = commonService.getSmsTemplete(smsParam);
				String contents = smsTemplte.get("SMS_CONTENT").toString();
				contents = contents.replace("{고객명}",DecoderUtil.decode(param, "userName"));//이름 치환
				contents = contents.replace("{아이디}",DecoderUtil.decode(param, "userId"));//가입아이디 치환
				
				Map params = new HashMap();
				params.put("recipient_num", parameter.get("mobile_num")); // 수신번호
				//param.put("subject", "[아쿠아필드]회원가입문자입니다."); // LMS일경우 제목을 추가 할 수 있다.
				params.put("content", contents);  // 내용 (SMS=88Byte, LMS=2000Byte)				
				//params.put("callback", "031-8072-8800");  // 발신번호  
				params.put("callback", config.getProperty("sms.tel.number"));  // 발신번호 
				
				
				if(!smsService.sendSms(params)){
					result = "ERROR";
				}
				else{
					//SMS 발송 이력 등록
					smsParam.put("sms_uid", smsTemplte.get("SMS_UID"));
					smsParam.put("mem_id", DecoderUtil.decode(param, "userId"));
					smsParam.put("custom_nm", DecoderUtil.decode(param, "userName"));
					smsParam.put("custom_mobile", parameter.get("mobile_num"));
					smsParam.put("ins_ip", request.getRemoteAddr());
					smsParam.put("ins_id", DecoderUtil.decode(param, "userId")); 
					smsParam.put("send_status", "OK");	
					smsParam.put("bigo", "JOIN");			
					
					String smsResult = commonService.insSmsSend(smsParam);
					if("ERROR".equals(smsResult)){
						result = "ERROR";
					}		
				}
			
				//메일발송 #####################################
				Map emailParam = new HashMap();
				emailParam.put("email_uid", "1");
				Map joinEmail = adminEmailTempletService.adminEmailTempletDetail(emailParam);
				
				//String realPath = session.getServletContext().getRealPath("/common/mailTemplete");
				String realPath = session.getServletContext().getRealPath("/common/upload/email_templet");
				
				//String joinHtml = super.getHTMfile(realPath+"/join_complete.html");
				String joinHtml = super.getHTMfile(realPath+"/1");			
		
				String nowTime = AquaDateUtil.getToday();
				nowTime = nowTime.substring(0, 4)+"."+nowTime.substring(4, 6)+"."+nowTime.substring(6, 8);		
				joinHtml = joinHtml.replace("{{#NOW#}}",nowTime); // 현재시간 치환
				String reHtml= joinHtml.replace("{{#NAME#}}",DecoderUtil.decode(param, "userName"));
		
				boolean booleanresult =	mailService.sendmail(mailSender, "aquafield@shinsegae.com", "아쿠아필드", DecoderUtil.decode(param, "userId"), DecoderUtil.decode(param, "userName"), "[아쿠아필드]회원가입메일입니다.", reHtml);
				
				if(!booleanresult){
					result = "ERROR";
				}
				//###########################################
				
				Map userInfo = new HashMap();
				userInfo.put("NAME", DecoderUtil.decode(param, "userName"));
				
				model.addAttribute("userInfo", userInfo);
				
				return "/front/member/signup_step4";
				
			} else {
				
				throw new Exception();
				
			}
			
		} else {
			
			throw new Exception();
			
		}
        
    }		
	
	@RequestMapping(value="/member/checkplus.sf")
	public String checkplus(@RequestParam Map param, ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

		NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();
	    
		String realSiteCode = "AB673";				// NICE로부터 부여받은 사이트 코드
	    String realSitePassword = "SaFF5TxFFNfC";		// NICE로부터 부여받은 사이트 패스워드
	    
	    String sRequestNumber = "REQ" + com.soft.web.util.Util.getTodayTime();        	// 요청 번호, 이는 성공/실패후에 같은 값으로 되돌려주게 되므로 
	                                                    	// 업체에서 적절하게 변경하여 쓰거나, 아래와 같이 생성한다.
	    sRequestNumber = niceCheck.getRequestNO(realSiteCode);
	  	session.setAttribute("REQ_SEQ" , sRequestNumber);	// 해킹등의 방지를 위하여 세션을 쓴다면, 세션에 요청번호를 넣는다.
	  	
	   	String sAuthType = "M";      	// 없으면 기본 선택화면, M: 핸드폰, C: 신용카드, X: 공인인증서
	   	
	   	String popgubun 	= "N";		//Y : 취소버튼 있음 / N : 취소버튼 없음
		String customize 	= "";			//없으면 기본 웹페이지 / Mobile : 모바일페이지
			
		 // CheckPlus(본인인증) 처리 후, 결과 데이타를 리턴 받기위해 다음예제와 같이 http부터 입력합니다.
	    String sReturnUrl = request.getScheme() + "://" + request.getServerName() + config.getProperty("member.checkplus.returnurl");     // 성공시 이동될 URL
	    String sErrorUrl = "";//request.getScheme() + "://" + request.getServerName() + "/pc/brand/point/real_success.do";          // 실패시 이동될 URL

	    // 입력될 plain 데이타를 만든다.
	    String sPlainData = "7:REQ_SEQ" + sRequestNumber.getBytes().length + ":" + sRequestNumber +
	                        "8:SITECODE" + realSiteCode.getBytes().length + ":" + realSiteCode +
	                        "9:AUTH_TYPE" + sAuthType.getBytes().length + ":" + sAuthType +
	                        "7:RTN_URL" + sReturnUrl.getBytes().length + ":" + sReturnUrl +
	                        "7:ERR_URL" + sErrorUrl.getBytes().length + ":" + sErrorUrl +
	                        "11:POPUP_GUBUN" + popgubun.getBytes().length + ":" + popgubun +
	                        "9:CUSTOMIZE" + customize.getBytes().length + ":" + customize;
	    
	    String sMessage = "";
	    String sEncData = "";
	    
	    int iReturn = niceCheck.fnEncode(realSiteCode, realSitePassword, sPlainData);
	    if( iReturn == 0 )
	    {
	        sEncData = niceCheck.getCipherData();
	    }
	    else if( iReturn == -1)
	    {
	        sMessage = "암호화 시스템 에러입니다.";
	    }    
	    else if( iReturn == -2)
	    {
	        sMessage = "암호화 처리오류입니다.";
	    }    
	    else if( iReturn == -3)
	    {
	        sMessage = "암호화 데이터 오류입니다.";
	    }    
	    else if( iReturn == -9)
	    {
	        sMessage = "입력 데이터 오류입니다.";
	    }    
	    else
	    {
	        sMessage = "알수 없는 에러 입니다. iReturn : " + iReturn;
	    }
	    
	    if("".equals(sEncData) ){
	    	Util.htmlPrint("N", response);
	    }
	    else{
	    	Util.htmlPrint(sEncData, response);
	    }

		return null;
	}
	
	@RequestMapping(value = "/member/checkplus_ok.af")
    public String checkplus_ok(@RequestParam Map param, Model model, HttpSession session) throws ParseException {
		
	    NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();

	    String sEncodeData = requestReplace((String)param.get("EncodeData"), "encodeData");
	    String sReserved1  = requestReplace((String)param.get("param_r1"), "");
	    String sReserved2  = requestReplace((String)param.get("param_r2"), "");
	    String sReserved3  = requestReplace((String)param.get("param_r3"), "");

	    String sSiteCode = "AB673";				 // NICE로부터 부여받은 사이트 코드
	    String sSitePassword = "SaFF5TxFFNfC";	 // NICE로부터 부여받은 사이트 패스워드

	    String sCipherTime = "";			 // 복호화한 시간
	    String sRequestNumber = "";			 // 요청 번호
	    String sResponseNumber = "";		 // 인증 고유번호
	    String sAuthType = "";				 // 인증 수단
	    String sName = "";					 // 성명
	    String sDupInfo = "";				 // 중복가입 확인값 (DI_64 byte)
	    String sConnInfo = "";				 // 연계정보 확인값 (CI_88 byte)
	    String sBirthDate = "";				 // 생일
	    String sGenderCode = "";			 // 성별
	    String sNationalInfo = "";           // 내/외국인정보 (개발가이드 참조)
	    String sMessage = "";
	    String sPlainData = "";
	    String sGender ="";
	    String sBirth = "";
	    String sMobileNumber = "";            // 인증 핸드폰 번호
	    
	    int iReturn = niceCheck.fnDecode(sSiteCode, sSitePassword, sEncodeData);

	    if( iReturn == 0 )
	    {
	        sPlainData = niceCheck.getPlainData();
	        sCipherTime = niceCheck.getCipherDateTime();
	        
	        // 데이타를 추출합니다.
	        java.util.HashMap mapresult = niceCheck.fnParse(sPlainData);
	        
	        sRequestNumber  = (String)mapresult.get("REQ_SEQ");
	        sResponseNumber = (String)mapresult.get("RES_SEQ");
	        sAuthType 			= (String)mapresult.get("AUTH_TYPE");
	        sName 					= (String)mapresult.get("NAME");
	        sBirthDate 			= (String)mapresult.get("BIRTHDATE");
	        sGenderCode 		= (String)mapresult.get("GENDER");
	        sNationalInfo  	= (String)mapresult.get("NATIONALINFO");
	        sDupInfo 				= (String)mapresult.get("DI");
	        sConnInfo 			= (String)mapresult.get("CI");
	        sMobileNumber   = (String)mapresult.get("MOBILE_NO");
	        
	        Map parameter = new HashMap();
	        parameter.put("NAME", sName);
	        parameter.put("MOBILE_NUM", sMobileNumber);
	        parameter.put("DI_CERTI", sDupInfo);
	        
	        //본인인증시 이름, 키값, 핸드폰번호 저장 및 업데이트
	        String result = frontMemberService.niceIdSave(parameter);
	        
	        String session_sRequestNumber = (String)session.getAttribute("REQ_SEQ");
	        if(!sRequestNumber.equals(session_sRequestNumber))
	        {
	        	sMessage = "세션값이 다릅니다. 올바른 경로로 접근하시기 바랍니다.";
	            sResponseNumber = "";
	            sAuthType = "";
	        }
	        
	        sGender = ("1".equals(sGenderCode)) ? "M":"W";
	        sBirth = sBirthDate.substring(0, 4)+"."+sBirthDate.substring(4, 6)+"."+sBirthDate.substring(6, 8);
	    }
	    else if( iReturn == -1)
	    {
	        sMessage = "복호화 시스템 에러입니다.";
	    }    
	    else if( iReturn == -4)
	    {
	        sMessage = "복호화 처리오류입니다.";
	    }    
	    else if( iReturn == -5)
	    {
	        sMessage = "복호화 해쉬 오류입니다.";
	    }    
	    else if( iReturn == -6)
	    {
	        sMessage = "복호화 데이터 오류입니다.";
	    }    
	    else if( iReturn == -9)
	    {
	        sMessage = "입력 데이터 오류입니다.";
	    }    
	    else if( iReturn == -12)
	    {
	        sMessage = "사이트 패스워드 오류입니다.";
	    }    
	    else
	    {
	        sMessage = "알수 없는 에러 입니다. iReturn : " + iReturn;
	    }

	    JSONParser jsonParser = new JSONParser();
		String jsonInfo = "{\"sDupInfo\":\""+sDupInfo+"\",\"sName\":\""+sName+"\",\"sGender\":\""+sGender+"\",\"sBirth\":\""+sBirth+"\",\"sMobileNumber\":\""+sMobileNumber+"\"}";
		JSONObject parameter = (JSONObject) jsonParser.parse(jsonInfo);

		model.addAttribute("parameter", parameter);
        return "/front/member/success";
    }
	
	@RequestMapping(value = "/member/checkplus_fail.af")
    public String checkplus_fail(@RequestParam Map param, Model model, HttpSession session) {
		
	    NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();

	    String sEncodeData = requestReplace((String)param.get("EncodeData"), "encodeData");
	    String sReserved1  = requestReplace((String)param.get("param_r1"), "");
	    String sReserved2  = requestReplace((String)param.get("param_r2"), "");
	    String sReserved3  = requestReplace((String)param.get("param_r3"), "");

	    String sSiteCode = "AB673";				 // NICE로부터 부여받은 사이트 코드
	    String sSitePassword = "SaFF5TxFFNfC";	 // NICE로부터 부여받은 사이트 패스워드

	    String sCipherTime = "";					// 복호화한 시간
	    String sRequestNumber = "";				// 요청 번호
	    String sErrorCode = "";						// 인증 결과코드
	    String sAuthType = "";						// 인증 수단
	    String sMessage = "";
	    String sPlainData = "";
	    
	    int iReturn = niceCheck.fnDecode(sSiteCode, sSitePassword, sEncodeData);

	    if( iReturn == 0 )
	    {
	        sPlainData = niceCheck.getPlainData();
	        sCipherTime = niceCheck.getCipherDateTime();
	        
	        // 데이타를 추출합니다.
	        java.util.HashMap mapresult = niceCheck.fnParse(sPlainData);
	        
	        sRequestNumber 	= (String)mapresult.get("REQ_SEQ");
	        sErrorCode 			= (String)mapresult.get("ERR_CODE");
	        sAuthType 			= (String)mapresult.get("AUTH_TYPE");
	    }
	    else if( iReturn == -1)
	    {
	        sMessage = "복호화 시스템 에러입니다.";
	    }    
	    else if( iReturn == -4)
	    {
	        sMessage = "복호화 처리오류입니다.";
	    }    
	    else if( iReturn == -5)
	    {
	        sMessage = "복호화 해쉬 오류입니다.";
	    }    
	    else if( iReturn == -6)
	    {
	        sMessage = "복호화 데이터 오류입니다.";
	    }    
	    else if( iReturn == -9)
	    {
	        sMessage = "입력 데이터 오류입니다.";
	    }    
	    else if( iReturn == -12)
	    {
	        sMessage = "사이트 패스워드 오류입니다.";
	    }    
	    else
	    {
	        sMessage = "알수 없는 에러 입니다. iReturn : " + iReturn;
	    }
	    
		Map parameter = new HashMap();
		
        parameter.put("rqNums",sRequestNumber);
        parameter.put("auth",sAuthType); 
        parameter.put("code",sErrorCode); 
        parameter.put("message",sMessage);

		model.addAttribute("parameter", parameter);
        return "/front/member/checkplus_fail";
    }	
	
	@RequestMapping(value="/member/ipin.af")
	public String ipin(@RequestParam Map param, ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

		String sSiteCode				= "AD31";		// IPIN 서비스 사이트 코드		(NICE평가정보에서 발급한 사이트코드)
		String sSitePw					= "Aqua0811";	// IPIN 서비스 사이트 패스워드	(NICE평가정보에서 발급한 사이트패스워드)
		String sReturnURL				= request.getScheme() + "://" + request.getServerName() + config.getProperty("member.ipin.returnurl");
		String sCPRequest				= "";
		
		// 객체 생성
		IPINClient pClient = new IPINClient();
		
		
		// 앞서 설명드린 바와같이, CP 요청번호는 배포된 모듈을 통해 아래와 같이 생성할 수 있습니다.
		sCPRequest = pClient.getRequestNO(sSiteCode);
		
		// CP 요청번호를 세션에 저장합니다.
		// 현재 예제로 저장한 세션은 ipin_result.jsp 페이지에서 데이타 위변조 방지를 위해 확인하기 위함입니다.
		// 필수사항은 아니며, 보안을 위한 권고사항입니다.
		session.setAttribute("CPREQUEST" , sCPRequest);
		
		
		// Method 결과값(iRtn)에 따라, 프로세스 진행여부를 파악합니다.
		int iRtn = pClient.fnRequest(sSiteCode, sSitePw, sCPRequest, sReturnURL);
		
		String sRtnMsg					= "";			
		String sEncData					= "";			
		
		// Method 결과값에 따른 처리사항
		if (iRtn == 0)
		{
		
			// fnRequest 함수 처리시 업체정보를 암호화한 데이터를 추출합니다.
			// 추출된 암호화된 데이타는 당사 팝업 요청시, 함께 보내주셔야 합니다.
			sEncData = pClient.getCipherData();		//암호화 된 데이타
			
			sRtnMsg = "정상 처리되었습니다.";
		
		}
		else if (iRtn == -1 || iRtn == -2)
		{
			sRtnMsg =	"배포해 드린 서비스 모듈 중, 귀사 서버환경에 맞는 모듈을 이용해 주시기 바랍니다.<BR>" +
						"귀사 서버환경에 맞는 모듈이 없다면 ..<BR><B>iRtn 값, 서버 환경정보를 정확히 확인하여 메일로 요청해 주시기 바랍니다.</B>";
		}
		else if (iRtn == -9)
		{
			sRtnMsg = "입력값 오류 : fnRequest 함수 처리시, 필요한 4개의 파라미터값의 정보를 정확하게 입력해 주시기 바랍니다.";
		}
		else
		{
			sRtnMsg = "iRtn 값 확인 후, NICE평가정보 개발 담당자에게 문의해 주세요.";
		}

	    if("".equals(sEncData) ){
	    	Util.htmlPrint("N", response);
	    }
	    else{
	    	Util.htmlPrint(sEncData, response);
	    }
		
		return null;
	}
	
	@RequestMapping(value = "/member/ipin_process.af")
    public String ipin_process(@RequestParam Map param, Model model, HttpSession session) {
		
        return "/front/member/ipin_process";
    }
	
	@RequestMapping(value = "/member/ipin_result.af")
    public String ipin_result(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {

		String sSiteCode				= "AD31";			// IPIN 서비스 사이트 코드		(NICE평가정보에서 발급한 사이트코드)
		String sSitePw					= "Aqua0811";		// IPIN 서비스 사이트 패스워드	(NICE평가정보에서 발급한 사이트패스워드)
		
			
		// 사용자 정보 및 CP 요청번호를 암호화한 데이타입니다.
	    String sResponseData = requestReplace(request.getParameter("enc_data"), "encodeData");
	    
	    // CP 요청번호 : ipin_main.jsp 에서 세션 처리한 데이타
	    String sCPRequest = (String)session.getAttribute("CPREQUEST");
	
	    
	    // 객체 생성
		IPINClient pClient = new IPINClient();
		
		
		/*
		┌ 복호화 함수 설명  ──────────────────────────────────────────────────────────
			Method 결과값(iRtn)에 따라, 프로세스 진행여부를 파악합니다.
			
			fnResponse 함수는 결과 데이타를 복호화 하는 함수이며,
			'sCPRequest'값을 추가로 보내시면 CP요청번호 일치여부도 확인하는 함수입니다. (세션에 넣은 sCPRequest 데이타로 검증)
			
			따라서 귀사에서 원하는 함수로 이용하시기 바랍니다.
		└────────────────────────────────────────────────────────────────────
		*/
		int iRtn = pClient.fnResponse(sSiteCode, sSitePw, sResponseData);
		//int iRtn = pClient.fnResponse(sSiteCode, sSitePw, sResponseData, sCPRequest);
		
		String sRtnMsg				= "";							// 처리결과 메세지
		String sVNumber				= pClient.getVNumber();			// 가상주민번호 (13자리이며, 숫자 또는 문자 포함)
		String sName				= pClient.getName();			// 이름
		String sDupInfo				= pClient.getDupInfo();			// 중복가입 확인값 (DI - 64 byte 고유값)
		String sAgeCode				= pClient.getAgeCode();			// 연령대 코드 (개발 가이드 참조)
		String sGenderCode			= pClient.getGenderCode();		// 성별 코드 (개발 가이드 참조)
		String sBirthDate			= pClient.getBirthDate();		// 생년월일 (YYYYMMDD)
		String sNationalInfo		= pClient.getNationalInfo();	// 내/외국인 정보 (개발 가이드 참조)
		String sCPRequestNum		= pClient.getCPRequestNO();		// CP 요청번호
		
		String sGender = ("1".equals(sGenderCode)) ? "M":"W";
		String sBirth = sBirthDate.substring(0, 4)+"."+sBirthDate.substring(4, 6)+"."+sBirthDate.substring(6, 8);
	    String sMobileNumber = "";        // ipin은 빈값을 전달
				
	 // Method 결과값에 따른 처리사항
		if (iRtn == 1)
		{			
			sRtnMsg = "정상 처리되었습니다.";
	    	//Util.htmlPrint(sDupInfo, response);
	    	Util.htmlPrint("{\"sDupInfo\":\""+sDupInfo+"\",\"sName\":\""+sName+"\",\"sGender\":\""+sGender+"\",\"sBirth\":\""+sBirth+"\",\"sMobileNumber\":\""+sMobileNumber+"\"}", response);
			
		}
		else if (iRtn == -1 || iRtn == -4)
		{
			sRtnMsg =	"iRtn 값, 서버 환경정보를 정확히 확인하여 주시기 바랍니다.";
	    	Util.htmlPrint("N", response);
		}
		else if (iRtn == -6)
		{
			sRtnMsg =	"당사는 한글 charset 정보를 euc-kr 로 처리하고 있으니, euc-kr 에 대해서 허용해 주시기 바랍니다.<BR>" +
						"한글 charset 정보가 명확하다면 ..<BR><B>iRtn 값, 서버 환경정보를 정확히 확인하여 메일로 요청해 주시기 바랍니다.</B>";
	    	Util.htmlPrint("N", response);
		}
		else if (iRtn == -9)
		{
			sRtnMsg = "입력값 오류 : fnResponse 함수 처리시, 필요한 파라미터값의 정보를 정확하게 입력해 주시기 바랍니다.";
	    	Util.htmlPrint("N", response);
		}
		else if (iRtn == -12)
		{
			sRtnMsg = "CP 비밀번호 불일치 : IPIN 서비스 사이트 패스워드를 확인해 주시기 바랍니다.";
	    	Util.htmlPrint("N", response);
		}
		else if (iRtn == -13)
		{
			sRtnMsg = "CP 요청번호 불일치 : 세션에 넣은 sCPRequest 데이타를 확인해 주시기 바랍니다.";
	    	Util.htmlPrint("N", response);
		}
		else
		{
			sRtnMsg = "iRtn 값 확인 후, NICE평가정보 전산 담당자에게 문의해 주세요.";
	    	Util.htmlPrint("N", response);
		}	

        return null;
    }	
	
	public String memberCheck(Map parameter){
		
		String searchId = frontMemberService.searchId(parameter);
		
		if("".equals(AquaDataUtil.checkNull(searchId))){
			searchId = "";
		}
		
		return searchId;
	}	
	
	public String requestReplace (String paramValue, String gubun) {

        String result = "";
        
        if (paramValue != null) {
        	
        	paramValue = paramValue.replaceAll("<", "&lt;").replaceAll(">", "&gt;");

        	paramValue = paramValue.replaceAll("\\*", "");
        	paramValue = paramValue.replaceAll("\\?", "");
        	paramValue = paramValue.replaceAll("\\[", "");
        	paramValue = paramValue.replaceAll("\\{", "");
        	paramValue = paramValue.replaceAll("\\(", "");
        	paramValue = paramValue.replaceAll("\\)", "");
        	paramValue = paramValue.replaceAll("\\^", "");
        	paramValue = paramValue.replaceAll("\\$", "");
        	paramValue = paramValue.replaceAll("'", "");
        	paramValue = paramValue.replaceAll("@", "");
        	paramValue = paramValue.replaceAll("%", "");
        	paramValue = paramValue.replaceAll(";", "");
        	paramValue = paramValue.replaceAll(":", "");
        	paramValue = paramValue.replaceAll("-", "");
        	paramValue = paramValue.replaceAll("#", "");
        	paramValue = paramValue.replaceAll("--", "");
        	paramValue = paramValue.replaceAll("-", "");
        	paramValue = paramValue.replaceAll(",", "");
        	
        	if(gubun != "encodeData"){
        		paramValue = paramValue.replaceAll("\\+", "");
        		paramValue = paramValue.replaceAll("/", "");
        		paramValue = paramValue.replaceAll("=", "");
        	}
        	
        	result = paramValue;
            
        }
        return result;
  }
	
	//*********************************************************************
	
	/**
	 * 회원가입 1
	 * 20180406
	 * 서영운
	 * @param param
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/member/joinStep1.af")
    public String joinStep1(@RequestParam Map param, Model model, HttpSession session) {
		
        return "/front/member/joinStep1";
    }
	
	/**
	 * 회원가입 2
	 * 20180406
	 * 서영운
	 * @param param
	 * @param model
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/member/joinStep2.af")
	public String joinStep2(@RequestParam Map param, Model model, HttpSession session) throws Exception {
		
		Map parameter = new HashMap();
		parameter.put("chkTerm", param.get("chkTerm"));
		parameter.put("obj", param.get("obj"));
		parameter.put("objName", DecoderUtil.decode(param, "objName"));
		parameter.put("objGender", param.get("objGender"));
		parameter.put("objBirth", param.get("objBirth"));
		parameter.put("objMNum", param.get("objMNum"));
		parameter.put("point_code", param.get("point"));
		parameter.put("di_certi", param.get("obj"));  //고유키값 중복체크
		
		String searchId = memberCheck(parameter);
		
		
		String chkTerm = (String) param.get("chkTerm");
		String obj = (String) param.get("obj");
		
		if(!"".equals(obj) && obj != null && !"".equals(chkTerm) && chkTerm != null) {
			
			model.addAttribute("parameter", parameter);
	        return "/front/member/joinStep2";
			
		} else {
			
			throw new Exception();
			
		}
		
    }
	
	/**
	 * 회원가입 
	 * 20180412	
	 * 서영운
	 * 
	 * @param param
	 * @param model
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/member/joinStep3.af")
    public String joinStep3(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map parameter = new HashMap();
		parameter.put("mem_nm", DecoderUtil.decode(param, "userName"));
		parameter.put("mem_id", DecoderUtil.decode(param, "userId"));
		parameter.put("mem_pw", DecoderUtil.decode(param, "userPw01"));
		parameter.put("mobile_num", DecoderUtil.decode(param, "phonenum"));
		parameter.put("mem_birth", DecoderUtil.decode(param, "birthDay"));
		parameter.put("birth_type", DecoderUtil.decode(param, "lunar"));
		parameter.put("mem_gender", DecoderUtil.decode(param, "sex"));
		parameter.put("home_addr1", DecoderUtil.decode(param, "address"));
		parameter.put("home_addr2", DecoderUtil.decode(param, "address2"));
		parameter.put("company_phone_num", DecoderUtil.decode(param, "telHome"));
		parameter.put("company_nm", DecoderUtil.decode(param, "companyNm"));
		parameter.put("company_addr1", DecoderUtil.decode(param, "companyAddr"));
		parameter.put("company_addr2", DecoderUtil.decode(param, "companyAddr2"));
		parameter.put("memory_day", DecoderUtil.decode(param, "memorialDay"));
		parameter.put("terms_at", DecoderUtil.decode(param, "chkTerm"));
		parameter.put("received_info_at", DecoderUtil.decode(param, "receiveChk"));
		parameter.put("mem_rating", DecoderUtil.decode(param, "NORMAL"));
		parameter.put("point_code", "POINT01");
		parameter.put("ins_id", DecoderUtil.decode(param, "userId"));
		parameter.put("ci_certi", param.get("obj"));
		
		//아이디 중복체크
		int idChk = frontMemberService.idChk(parameter);

		//본인인증한 회원의 정보 조회
		int niceChk = frontMemberService.niceChk(parameter);
		
		//본인인증한 회원의 정보가 DB에 있을때 회원가입 진행(취약점)
		if(niceChk > 0) { 	
			
			//아이디 중복체크(취약점 관련 작업)		
			if(idChk == 0) {
		
				String result = frontMemberService.memberJoin(parameter);
		
		
				//가입문자 발송
				//String contents = "[아쿠아필드]"+DecoderUtil.decode(param, "userName")+"고객님  회원가입완료(ID:"+parameter.get("mem_id")+")";
				Map smsParam = new HashMap();
				smsParam.put("point_code", "POINT01");
				smsParam.put("sms_type", "JOIN");
				
				Map smsTemplte = commonService.getSmsTemplete(smsParam);
				String contents = smsTemplte.get("SMS_CONTENT").toString();
				contents = contents.replace("{고객명}",DecoderUtil.decode(param, "userName"));//이름 치환
				contents = contents.replace("{아이디}",DecoderUtil.decode(param, "userId"));//가입아이디 치환
				
				Map params = new HashMap();
				params.put("recipient_num", parameter.get("mobile_num")); // 수신번호
				//param.put("subject", "[아쿠아필드]회원가입문자입니다."); // LMS일경우 제목을 추가 할 수 있다.
				params.put("content", contents);  // 내용 (SMS=88Byte, LMS=2000Byte)				
				//params.put("callback", "031-8072-8800");  // 발신번호  
				params.put("callback", config.getProperty("sms.tel.number"));  // 발신번호 
				
				
				if(!smsService.sendSms(params)){
					result = "ERROR";
				}
				else{
					//SMS 발송 이력 등록
					smsParam.put("sms_uid", smsTemplte.get("SMS_UID"));
					smsParam.put("mem_id", DecoderUtil.decode(param, "userId"));
					smsParam.put("custom_nm", DecoderUtil.decode(param, "userName"));
					smsParam.put("custom_mobile", parameter.get("mobile_num"));
					smsParam.put("ins_ip", request.getRemoteAddr());
					smsParam.put("ins_id", DecoderUtil.decode(param, "userId")); 
					smsParam.put("send_status", "OK");	
					smsParam.put("bigo", "JOIN");			
					
					String smsResult = commonService.insSmsSend(smsParam);
					if("ERROR".equals(smsResult)){
						result = "ERROR";
					}		
				}
			
				//메일발송 #####################################
				Map emailParam = new HashMap();
				emailParam.put("email_uid", "1");
				Map joinEmail = adminEmailTempletService.adminEmailTempletDetail(emailParam);
				
				//String realPath = session.getServletContext().getRealPath("/common/mailTemplete");
				String realPath = session.getServletContext().getRealPath("/common/upload/email_templet");
				
				//String joinHtml = super.getHTMfile(realPath+"/join_complete.html");
				String joinHtml = super.getHTMfile(realPath+"/1");			
		
				String nowTime = AquaDateUtil.getToday();
				nowTime = nowTime.substring(0, 4)+"."+nowTime.substring(4, 6)+"."+nowTime.substring(6, 8);		
				joinHtml = joinHtml.replace("{{#NOW#}}",nowTime); // 현재시간 치환
				String reHtml= joinHtml.replace("{{#NAME#}}",DecoderUtil.decode(param, "userName"));
		
				boolean booleanresult =	mailService.sendmail(mailSender, "aquafield@shinsegae.com", "아쿠아필드", DecoderUtil.decode(param, "userId"), DecoderUtil.decode(param, "userName"), "[아쿠아필드]회원가입메일입니다.", reHtml);
				
				if(!booleanresult){
					result = "ERROR";
				}
				//###########################################
			
				Map userInfo = new HashMap();
				userInfo.put("NAME", DecoderUtil.decode(param, "userName"));
				
				model.addAttribute("userInfo", userInfo);
		        return "/front/member/joinStep3";
        
			} else {
				
				throw new Exception();
				
			}
			
		} else {
			
			throw new Exception();
			
		}
        
    }
	
	/**
	 * 아이디 찾기 화면
	 * 20180406
	 * 서영운
	 * @param param
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/member/findMemberId.af")
    public String findMember(@RequestParam Map param, Model model, HttpSession session) {
		
        return "/front/member/find_Member_Id";
    }
	
	/**
	 * 아이디 찾기 결과 화면
	 * 20180406
	 * 서영운
	 * @param param
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/member/resultMemberId.af")
    public String resultMember(@RequestParam Map param, Model model, HttpSession session) {
		
		Map parameter = new HashMap();
		parameter.put("point_code", param.get("point"));
		parameter.put("di_certi", param.get("obj"));
		
		String searchId = memberCheck(parameter);

		model.addAttribute("searchId", searchId);
		model.addAttribute("obj", param.get("obj"));
		model.addAttribute("point", param.get("point"));
		
		
        return "/front/member/result_Member_Id";
    }
	
	/**
	 * 비밀번호 찾기 화면
	 * 20180406
	 * 서영운
	 * @param param
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/member/findMemberPwd.af")
    public String findMemberPwd(@RequestParam Map param, Model model, HttpSession session) {
		
        return "/front/member/find_Member_Pwd";
    }
	
	/**
	 * 비밀번호 찾기 결과 화면
	 * 20180406
	 * 서영운
	 * @param param
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/member/resultMemberPwd.af")
    public String resultMemberPwd(@RequestParam Map param, Model model, HttpSession session) {
		
		Map parameter = new HashMap(); 
		
		parameter.put("point_code", param.get("point"));
		parameter.put("di_certi", param.get("obj"));
		
		String searchId = memberCheck(parameter);

		if(searchId.equals("") || searchId.equals(null)) {
			model.addAttribute("memberCheck", "Fail");
			return "/front/member/result_Member_Pwd";
		} else {
			model.addAttribute("obj", param.get("obj"));
			model.addAttribute("point", param.get("point"));
			return "/front/member/result_Member_Pwd";
		}
		
		/* 테스트할때 
		model.addAttribute("obj", param.get("obj"));
		model.addAttribute("point", param.get("point"));
		return "/front/member/result_Member_Pwd";
        */
    }
	
	
	
}
