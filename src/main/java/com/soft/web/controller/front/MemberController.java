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
			/*String message = "����ó���Ǿ����ϴ�.";
			String url ="location.href='/member/loginMain.af';";
			html = Util.gotoUrl(url, message);*/
			
		}else{
			String message = "ó���� ������ �߻��Ͽ����ϴ�.\\n�ٽ� �õ��� �ֽñ�ٶ��ϴ�.";
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
			String message =  "�Է��Ͻ� ������ �̹� ���Ե� ȸ�������Դϴ�.";
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
		
		//���̵� �ߺ�üũ
		int idChk = frontMemberService.idChk(parameter);

		//���������� ȸ���� ���� ��ȸ
		int niceChk = frontMemberService.niceChk(parameter);
		
		//���������� ȸ���� ������ DB�� ������ ȸ������ ����(�����)
		if(niceChk > 0) { 	
			
			//���̵� �ߺ�üũ(����� ���� �۾�)		
			if(idChk == 0) {
	
				String result = frontMemberService.memberJoin(parameter);
			
				//���Թ��� �߼�
				//String contents = "[������ʵ�]"+DecoderUtil.decode(param, "userName")+"����  ȸ�����ԿϷ�(ID:"+parameter.get("mem_id")+")";
				Map smsParam = new HashMap();
				smsParam.put("point_code", "POINT01");
				smsParam.put("sms_type", "JOIN");
				
				Map smsTemplte = commonService.getSmsTemplete(smsParam);
				String contents = smsTemplte.get("SMS_CONTENT").toString();
				contents = contents.replace("{����}",DecoderUtil.decode(param, "userName"));//�̸� ġȯ
				contents = contents.replace("{���̵�}",DecoderUtil.decode(param, "userId"));//���Ծ��̵� ġȯ
				
				Map params = new HashMap();
				params.put("recipient_num", parameter.get("mobile_num")); // ���Ź�ȣ
				//param.put("subject", "[������ʵ�]ȸ�����Թ����Դϴ�."); // LMS�ϰ�� ������ �߰� �� �� �ִ�.
				params.put("content", contents);  // ���� (SMS=88Byte, LMS=2000Byte)				
				//params.put("callback", "031-8072-8800");  // �߽Ź�ȣ  
				params.put("callback", config.getProperty("sms.tel.number"));  // �߽Ź�ȣ 
				
				
				if(!smsService.sendSms(params)){
					result = "ERROR";
				}
				else{
					//SMS �߼� �̷� ���
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
			
				//���Ϲ߼� #####################################
				Map emailParam = new HashMap();
				emailParam.put("email_uid", "1");
				Map joinEmail = adminEmailTempletService.adminEmailTempletDetail(emailParam);
				
				//String realPath = session.getServletContext().getRealPath("/common/mailTemplete");
				String realPath = session.getServletContext().getRealPath("/common/upload/email_templet");
				
				//String joinHtml = super.getHTMfile(realPath+"/join_complete.html");
				String joinHtml = super.getHTMfile(realPath+"/1");			
		
				String nowTime = AquaDateUtil.getToday();
				nowTime = nowTime.substring(0, 4)+"."+nowTime.substring(4, 6)+"."+nowTime.substring(6, 8);		
				joinHtml = joinHtml.replace("{{#NOW#}}",nowTime); // ����ð� ġȯ
				String reHtml= joinHtml.replace("{{#NAME#}}",DecoderUtil.decode(param, "userName"));
		
				boolean booleanresult =	mailService.sendmail(mailSender, "aquafield@shinsegae.com", "������ʵ�", DecoderUtil.decode(param, "userId"), DecoderUtil.decode(param, "userName"), "[������ʵ�]ȸ�����Ը����Դϴ�.", reHtml);
				
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
	    
		String realSiteCode = "AB673";				// NICE�κ��� �ο����� ����Ʈ �ڵ�
	    String realSitePassword = "SaFF5TxFFNfC";		// NICE�κ��� �ο����� ����Ʈ �н�����
	    
	    String sRequestNumber = "REQ" + com.soft.web.util.Util.getTodayTime();        	// ��û ��ȣ, �̴� ����/�����Ŀ� ���� ������ �ǵ����ְ� �ǹǷ� 
	                                                    	// ��ü���� �����ϰ� �����Ͽ� ���ų�, �Ʒ��� ���� �����Ѵ�.
	    sRequestNumber = niceCheck.getRequestNO(realSiteCode);
	  	session.setAttribute("REQ_SEQ" , sRequestNumber);	// ��ŷ���� ������ ���Ͽ� ������ ���ٸ�, ���ǿ� ��û��ȣ�� �ִ´�.
	  	
	   	String sAuthType = "M";      	// ������ �⺻ ����ȭ��, M: �ڵ���, C: �ſ�ī��, X: ����������
	   	
	   	String popgubun 	= "N";		//Y : ��ҹ�ư ���� / N : ��ҹ�ư ����
		String customize 	= "";			//������ �⺻ �������� / Mobile : �����������
			
		 // CheckPlus(��������) ó�� ��, ��� ����Ÿ�� ���� �ޱ����� ���������� ���� http���� �Է��մϴ�.
	    String sReturnUrl = request.getScheme() + "://" + request.getServerName() + config.getProperty("member.checkplus.returnurl");     // ������ �̵��� URL
	    String sErrorUrl = "";//request.getScheme() + "://" + request.getServerName() + "/pc/brand/point/real_success.do";          // ���н� �̵��� URL

	    // �Էµ� plain ����Ÿ�� �����.
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
	        sMessage = "��ȣȭ �ý��� �����Դϴ�.";
	    }    
	    else if( iReturn == -2)
	    {
	        sMessage = "��ȣȭ ó�������Դϴ�.";
	    }    
	    else if( iReturn == -3)
	    {
	        sMessage = "��ȣȭ ������ �����Դϴ�.";
	    }    
	    else if( iReturn == -9)
	    {
	        sMessage = "�Է� ������ �����Դϴ�.";
	    }    
	    else
	    {
	        sMessage = "�˼� ���� ���� �Դϴ�. iReturn : " + iReturn;
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

	    String sSiteCode = "AB673";				 // NICE�κ��� �ο����� ����Ʈ �ڵ�
	    String sSitePassword = "SaFF5TxFFNfC";	 // NICE�κ��� �ο����� ����Ʈ �н�����

	    String sCipherTime = "";			 // ��ȣȭ�� �ð�
	    String sRequestNumber = "";			 // ��û ��ȣ
	    String sResponseNumber = "";		 // ���� ������ȣ
	    String sAuthType = "";				 // ���� ����
	    String sName = "";					 // ����
	    String sDupInfo = "";				 // �ߺ����� Ȯ�ΰ� (DI_64 byte)
	    String sConnInfo = "";				 // �������� Ȯ�ΰ� (CI_88 byte)
	    String sBirthDate = "";				 // ����
	    String sGenderCode = "";			 // ����
	    String sNationalInfo = "";           // ��/�ܱ������� (���߰��̵� ����)
	    String sMessage = "";
	    String sPlainData = "";
	    String sGender ="";
	    String sBirth = "";
	    String sMobileNumber = "";            // ���� �ڵ��� ��ȣ
	    
	    int iReturn = niceCheck.fnDecode(sSiteCode, sSitePassword, sEncodeData);

	    if( iReturn == 0 )
	    {
	        sPlainData = niceCheck.getPlainData();
	        sCipherTime = niceCheck.getCipherDateTime();
	        
	        // ����Ÿ�� �����մϴ�.
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
	        
	        //���������� �̸�, Ű��, �ڵ�����ȣ ���� �� ������Ʈ
	        String result = frontMemberService.niceIdSave(parameter);
	        
	        String session_sRequestNumber = (String)session.getAttribute("REQ_SEQ");
	        if(!sRequestNumber.equals(session_sRequestNumber))
	        {
	        	sMessage = "���ǰ��� �ٸ��ϴ�. �ùٸ� ��η� �����Ͻñ� �ٶ��ϴ�.";
	            sResponseNumber = "";
	            sAuthType = "";
	        }
	        
	        sGender = ("1".equals(sGenderCode)) ? "M":"W";
	        sBirth = sBirthDate.substring(0, 4)+"."+sBirthDate.substring(4, 6)+"."+sBirthDate.substring(6, 8);
	    }
	    else if( iReturn == -1)
	    {
	        sMessage = "��ȣȭ �ý��� �����Դϴ�.";
	    }    
	    else if( iReturn == -4)
	    {
	        sMessage = "��ȣȭ ó�������Դϴ�.";
	    }    
	    else if( iReturn == -5)
	    {
	        sMessage = "��ȣȭ �ؽ� �����Դϴ�.";
	    }    
	    else if( iReturn == -6)
	    {
	        sMessage = "��ȣȭ ������ �����Դϴ�.";
	    }    
	    else if( iReturn == -9)
	    {
	        sMessage = "�Է� ������ �����Դϴ�.";
	    }    
	    else if( iReturn == -12)
	    {
	        sMessage = "����Ʈ �н����� �����Դϴ�.";
	    }    
	    else
	    {
	        sMessage = "�˼� ���� ���� �Դϴ�. iReturn : " + iReturn;
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

	    String sSiteCode = "AB673";				 // NICE�κ��� �ο����� ����Ʈ �ڵ�
	    String sSitePassword = "SaFF5TxFFNfC";	 // NICE�κ��� �ο����� ����Ʈ �н�����

	    String sCipherTime = "";					// ��ȣȭ�� �ð�
	    String sRequestNumber = "";				// ��û ��ȣ
	    String sErrorCode = "";						// ���� ����ڵ�
	    String sAuthType = "";						// ���� ����
	    String sMessage = "";
	    String sPlainData = "";
	    
	    int iReturn = niceCheck.fnDecode(sSiteCode, sSitePassword, sEncodeData);

	    if( iReturn == 0 )
	    {
	        sPlainData = niceCheck.getPlainData();
	        sCipherTime = niceCheck.getCipherDateTime();
	        
	        // ����Ÿ�� �����մϴ�.
	        java.util.HashMap mapresult = niceCheck.fnParse(sPlainData);
	        
	        sRequestNumber 	= (String)mapresult.get("REQ_SEQ");
	        sErrorCode 			= (String)mapresult.get("ERR_CODE");
	        sAuthType 			= (String)mapresult.get("AUTH_TYPE");
	    }
	    else if( iReturn == -1)
	    {
	        sMessage = "��ȣȭ �ý��� �����Դϴ�.";
	    }    
	    else if( iReturn == -4)
	    {
	        sMessage = "��ȣȭ ó�������Դϴ�.";
	    }    
	    else if( iReturn == -5)
	    {
	        sMessage = "��ȣȭ �ؽ� �����Դϴ�.";
	    }    
	    else if( iReturn == -6)
	    {
	        sMessage = "��ȣȭ ������ �����Դϴ�.";
	    }    
	    else if( iReturn == -9)
	    {
	        sMessage = "�Է� ������ �����Դϴ�.";
	    }    
	    else if( iReturn == -12)
	    {
	        sMessage = "����Ʈ �н����� �����Դϴ�.";
	    }    
	    else
	    {
	        sMessage = "�˼� ���� ���� �Դϴ�. iReturn : " + iReturn;
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

		String sSiteCode				= "AD31";		// IPIN ���� ����Ʈ �ڵ�		(NICE���������� �߱��� ����Ʈ�ڵ�)
		String sSitePw					= "Aqua0811";	// IPIN ���� ����Ʈ �н�����	(NICE���������� �߱��� ����Ʈ�н�����)
		String sReturnURL				= request.getScheme() + "://" + request.getServerName() + config.getProperty("member.ipin.returnurl");
		String sCPRequest				= "";
		
		// ��ü ����
		IPINClient pClient = new IPINClient();
		
		
		// �ռ� ����帰 �ٿͰ���, CP ��û��ȣ�� ������ ����� ���� �Ʒ��� ���� ������ �� �ֽ��ϴ�.
		sCPRequest = pClient.getRequestNO(sSiteCode);
		
		// CP ��û��ȣ�� ���ǿ� �����մϴ�.
		// ���� ������ ������ ������ ipin_result.jsp ���������� ����Ÿ ������ ������ ���� Ȯ���ϱ� �����Դϴ�.
		// �ʼ������� �ƴϸ�, ������ ���� �ǰ�����Դϴ�.
		session.setAttribute("CPREQUEST" , sCPRequest);
		
		
		// Method �����(iRtn)�� ����, ���μ��� ���࿩�θ� �ľ��մϴ�.
		int iRtn = pClient.fnRequest(sSiteCode, sSitePw, sCPRequest, sReturnURL);
		
		String sRtnMsg					= "";			
		String sEncData					= "";			
		
		// Method ������� ���� ó������
		if (iRtn == 0)
		{
		
			// fnRequest �Լ� ó���� ��ü������ ��ȣȭ�� �����͸� �����մϴ�.
			// ����� ��ȣȭ�� ����Ÿ�� ��� �˾� ��û��, �Բ� �����ּž� �մϴ�.
			sEncData = pClient.getCipherData();		//��ȣȭ �� ����Ÿ
			
			sRtnMsg = "���� ó���Ǿ����ϴ�.";
		
		}
		else if (iRtn == -1 || iRtn == -2)
		{
			sRtnMsg =	"������ �帰 ���� ��� ��, �ͻ� ����ȯ�濡 �´� ����� �̿��� �ֽñ� �ٶ��ϴ�.<BR>" +
						"�ͻ� ����ȯ�濡 �´� ����� ���ٸ� ..<BR><B>iRtn ��, ���� ȯ�������� ��Ȯ�� Ȯ���Ͽ� ���Ϸ� ��û�� �ֽñ� �ٶ��ϴ�.</B>";
		}
		else if (iRtn == -9)
		{
			sRtnMsg = "�Է°� ���� : fnRequest �Լ� ó����, �ʿ��� 4���� �Ķ���Ͱ��� ������ ��Ȯ�ϰ� �Է��� �ֽñ� �ٶ��ϴ�.";
		}
		else
		{
			sRtnMsg = "iRtn �� Ȯ�� ��, NICE������ ���� ����ڿ��� ������ �ּ���.";
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

		String sSiteCode				= "AD31";			// IPIN ���� ����Ʈ �ڵ�		(NICE���������� �߱��� ����Ʈ�ڵ�)
		String sSitePw					= "Aqua0811";		// IPIN ���� ����Ʈ �н�����	(NICE���������� �߱��� ����Ʈ�н�����)
		
			
		// ����� ���� �� CP ��û��ȣ�� ��ȣȭ�� ����Ÿ�Դϴ�.
	    String sResponseData = requestReplace(request.getParameter("enc_data"), "encodeData");
	    
	    // CP ��û��ȣ : ipin_main.jsp ���� ���� ó���� ����Ÿ
	    String sCPRequest = (String)session.getAttribute("CPREQUEST");
	
	    
	    // ��ü ����
		IPINClient pClient = new IPINClient();
		
		
		/*
		�� ��ȣȭ �Լ� ����  ��������������������������������������������������������������������������������������������������������������������
			Method �����(iRtn)�� ����, ���μ��� ���࿩�θ� �ľ��մϴ�.
			
			fnResponse �Լ��� ��� ����Ÿ�� ��ȣȭ �ϴ� �Լ��̸�,
			'sCPRequest'���� �߰��� �����ø� CP��û��ȣ ��ġ���ε� Ȯ���ϴ� �Լ��Դϴ�. (���ǿ� ���� sCPRequest ����Ÿ�� ����)
			
			���� �ͻ翡�� ���ϴ� �Լ��� �̿��Ͻñ� �ٶ��ϴ�.
		������������������������������������������������������������������������������������������������������������������������������������������
		*/
		int iRtn = pClient.fnResponse(sSiteCode, sSitePw, sResponseData);
		//int iRtn = pClient.fnResponse(sSiteCode, sSitePw, sResponseData, sCPRequest);
		
		String sRtnMsg				= "";							// ó����� �޼���
		String sVNumber				= pClient.getVNumber();			// �����ֹι�ȣ (13�ڸ��̸�, ���� �Ǵ� ���� ����)
		String sName				= pClient.getName();			// �̸�
		String sDupInfo				= pClient.getDupInfo();			// �ߺ����� Ȯ�ΰ� (DI - 64 byte ������)
		String sAgeCode				= pClient.getAgeCode();			// ���ɴ� �ڵ� (���� ���̵� ����)
		String sGenderCode			= pClient.getGenderCode();		// ���� �ڵ� (���� ���̵� ����)
		String sBirthDate			= pClient.getBirthDate();		// ������� (YYYYMMDD)
		String sNationalInfo		= pClient.getNationalInfo();	// ��/�ܱ��� ���� (���� ���̵� ����)
		String sCPRequestNum		= pClient.getCPRequestNO();		// CP ��û��ȣ
		
		String sGender = ("1".equals(sGenderCode)) ? "M":"W";
		String sBirth = sBirthDate.substring(0, 4)+"."+sBirthDate.substring(4, 6)+"."+sBirthDate.substring(6, 8);
	    String sMobileNumber = "";        // ipin�� ���� ����
				
	 // Method ������� ���� ó������
		if (iRtn == 1)
		{			
			sRtnMsg = "���� ó���Ǿ����ϴ�.";
	    	//Util.htmlPrint(sDupInfo, response);
	    	Util.htmlPrint("{\"sDupInfo\":\""+sDupInfo+"\",\"sName\":\""+sName+"\",\"sGender\":\""+sGender+"\",\"sBirth\":\""+sBirth+"\",\"sMobileNumber\":\""+sMobileNumber+"\"}", response);
			
		}
		else if (iRtn == -1 || iRtn == -4)
		{
			sRtnMsg =	"iRtn ��, ���� ȯ�������� ��Ȯ�� Ȯ���Ͽ� �ֽñ� �ٶ��ϴ�.";
	    	Util.htmlPrint("N", response);
		}
		else if (iRtn == -6)
		{
			sRtnMsg =	"���� �ѱ� charset ������ euc-kr �� ó���ϰ� ������, euc-kr �� ���ؼ� ����� �ֽñ� �ٶ��ϴ�.<BR>" +
						"�ѱ� charset ������ ��Ȯ�ϴٸ� ..<BR><B>iRtn ��, ���� ȯ�������� ��Ȯ�� Ȯ���Ͽ� ���Ϸ� ��û�� �ֽñ� �ٶ��ϴ�.</B>";
	    	Util.htmlPrint("N", response);
		}
		else if (iRtn == -9)
		{
			sRtnMsg = "�Է°� ���� : fnResponse �Լ� ó����, �ʿ��� �Ķ���Ͱ��� ������ ��Ȯ�ϰ� �Է��� �ֽñ� �ٶ��ϴ�.";
	    	Util.htmlPrint("N", response);
		}
		else if (iRtn == -12)
		{
			sRtnMsg = "CP ��й�ȣ ����ġ : IPIN ���� ����Ʈ �н����带 Ȯ���� �ֽñ� �ٶ��ϴ�.";
	    	Util.htmlPrint("N", response);
		}
		else if (iRtn == -13)
		{
			sRtnMsg = "CP ��û��ȣ ����ġ : ���ǿ� ���� sCPRequest ����Ÿ�� Ȯ���� �ֽñ� �ٶ��ϴ�.";
	    	Util.htmlPrint("N", response);
		}
		else
		{
			sRtnMsg = "iRtn �� Ȯ�� ��, NICE������ ���� ����ڿ��� ������ �ּ���.";
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
	 * ȸ������ 1
	 * 20180406
	 * ������
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
	 * ȸ������ 2
	 * 20180406
	 * ������
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
		parameter.put("di_certi", param.get("obj"));  //����Ű�� �ߺ�üũ
		
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
	 * ȸ������ 
	 * 20180412	
	 * ������
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
		
		//���̵� �ߺ�üũ
		int idChk = frontMemberService.idChk(parameter);

		//���������� ȸ���� ���� ��ȸ
		int niceChk = frontMemberService.niceChk(parameter);
		
		//���������� ȸ���� ������ DB�� ������ ȸ������ ����(�����)
		if(niceChk > 0) { 	
			
			//���̵� �ߺ�üũ(����� ���� �۾�)		
			if(idChk == 0) {
		
				String result = frontMemberService.memberJoin(parameter);
		
		
				//���Թ��� �߼�
				//String contents = "[������ʵ�]"+DecoderUtil.decode(param, "userName")+"����  ȸ�����ԿϷ�(ID:"+parameter.get("mem_id")+")";
				Map smsParam = new HashMap();
				smsParam.put("point_code", "POINT01");
				smsParam.put("sms_type", "JOIN");
				
				Map smsTemplte = commonService.getSmsTemplete(smsParam);
				String contents = smsTemplte.get("SMS_CONTENT").toString();
				contents = contents.replace("{����}",DecoderUtil.decode(param, "userName"));//�̸� ġȯ
				contents = contents.replace("{���̵�}",DecoderUtil.decode(param, "userId"));//���Ծ��̵� ġȯ
				
				Map params = new HashMap();
				params.put("recipient_num", parameter.get("mobile_num")); // ���Ź�ȣ
				//param.put("subject", "[������ʵ�]ȸ�����Թ����Դϴ�."); // LMS�ϰ�� ������ �߰� �� �� �ִ�.
				params.put("content", contents);  // ���� (SMS=88Byte, LMS=2000Byte)				
				//params.put("callback", "031-8072-8800");  // �߽Ź�ȣ  
				params.put("callback", config.getProperty("sms.tel.number"));  // �߽Ź�ȣ 
				
				
				if(!smsService.sendSms(params)){
					result = "ERROR";
				}
				else{
					//SMS �߼� �̷� ���
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
			
				//���Ϲ߼� #####################################
				Map emailParam = new HashMap();
				emailParam.put("email_uid", "1");
				Map joinEmail = adminEmailTempletService.adminEmailTempletDetail(emailParam);
				
				//String realPath = session.getServletContext().getRealPath("/common/mailTemplete");
				String realPath = session.getServletContext().getRealPath("/common/upload/email_templet");
				
				//String joinHtml = super.getHTMfile(realPath+"/join_complete.html");
				String joinHtml = super.getHTMfile(realPath+"/1");			
		
				String nowTime = AquaDateUtil.getToday();
				nowTime = nowTime.substring(0, 4)+"."+nowTime.substring(4, 6)+"."+nowTime.substring(6, 8);		
				joinHtml = joinHtml.replace("{{#NOW#}}",nowTime); // ����ð� ġȯ
				String reHtml= joinHtml.replace("{{#NAME#}}",DecoderUtil.decode(param, "userName"));
		
				boolean booleanresult =	mailService.sendmail(mailSender, "aquafield@shinsegae.com", "������ʵ�", DecoderUtil.decode(param, "userId"), DecoderUtil.decode(param, "userName"), "[������ʵ�]ȸ�����Ը����Դϴ�.", reHtml);
				
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
	 * ���̵� ã�� ȭ��
	 * 20180406
	 * ������
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
	 * ���̵� ã�� ��� ȭ��
	 * 20180406
	 * ������
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
	 * ��й�ȣ ã�� ȭ��
	 * 20180406
	 * ������
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
	 * ��й�ȣ ã�� ��� ȭ��
	 * 20180406
	 * ������
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
		
		/* �׽�Ʈ�Ҷ� 
		model.addAttribute("obj", param.get("obj"));
		model.addAttribute("point", param.get("point"));
		return "/front/member/result_Member_Pwd";
        */
    }
	
	
	
}
