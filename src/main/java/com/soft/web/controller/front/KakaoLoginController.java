package com.soft.web.controller.front;

import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.soft.web.base.GenericController;
import com.soft.web.mail.MailService;
import com.soft.web.service.admin.AdminEmailTempletService;
import com.soft.web.service.common.CommonService;
import com.soft.web.service.front.FrontMemberService;
import com.soft.web.sms.SmsService;
import com.soft.web.util.AquaDateUtil;
import com.soft.web.util.DecoderUtil;

@Controller
public class KakaoLoginController extends GenericController {

	protected Logger logger = LoggerFactory.getLogger(KakaoLoginController.class);
	
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
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/member/kakaologin.af")
    public String kakaologinController(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
		

		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd");
		Date time = new Date();
		String time1 = format1.format(time);
		
		Map parameter = new HashMap();
		//parameter.put("mem_nm", DecoderUtil.decode(param, "name"));
		/*
		parameter.put("mem_nm", "12341234");		
		parameter.put("mem_id", DecoderUtil.decode(param, "id"));
		parameter.put("mem_pw", "12341234");
		parameter.put("mobile_num", DecoderUtil.decode(param, "phone_number"));
		//parameter.put("mobile_num", "01012341234");		
		parameter.put("mem_birth", DecoderUtil.decode(param, "birthday"));
		parameter.put("birth_type", "Y");
		parameter.put("mem_gender", DecoderUtil.decode(param, "gender"));
		parameter.put("home_addr1", "12341234");
		parameter.put("home_addr2", "12341234");
		parameter.put("company_phone_num", "01012341234");
		parameter.put("company_nm", "kakao");
		parameter.put("company_addr1", "12341234");
		parameter.put("company_addr2", "12341234");
		parameter.put("memory_day", "kakao");
		parameter.put("terms_at", "Y");
		parameter.put("received_info_at", "N");
		parameter.put("mem_rating", "kakao");
		parameter.put("point_code", "POINT01");
		parameter.put("ins_id", DecoderUtil.decode(param, "id"));
		parameter.put("ci_certi", DecoderUtil.decode(param, "id"));
		*/
		parameter.put("mem_nm", DecoderUtil.decode(param, "name"));
		parameter.put("mem_id", DecoderUtil.decode(param, "id"));
		parameter.put("mem_pw", "naver");
		parameter.put("mobile_num", "01012341234");
		parameter.put("mem_birth", DecoderUtil.decode(param, "birthday"));
		parameter.put("birth_type", "Y");
		parameter.put("mem_gender", DecoderUtil.decode(param, "gender"));
		parameter.put("home_addr1", "kakao");
		parameter.put("home_addr2", "kakao");
		parameter.put("company_phone_num", "kakao");
		parameter.put("company_nm", "kakao");
		parameter.put("company_addr1", "kakao");
		parameter.put("company_addr2", "kakao");
		parameter.put("memory_day", "kakao");
		parameter.put("terms_at", "Y");
		parameter.put("received_info_at", "N");
		parameter.put("mem_rating", "kakao");
		parameter.put("point_code", "POINT01");
		parameter.put("ins_id", DecoderUtil.decode(param, "id"));
		parameter.put("ci_certi", DecoderUtil.decode(param, "id"));		
		
		//�븘�씠�뵒 以묐났泥댄겕
		int idChk = frontMemberService.idChk(parameter);

		//蹂몄씤�씤利앺븳 �쉶�썝�쓽 �젙蹂� 議고쉶
		int niceChk = frontMemberService.niceChk(parameter);
		
		System.out.println("--------------------------------------" + idChk + "--------------------------------------");
		
		//蹂몄씤�씤利앺븳 �쉶�썝�쓽 �젙蹂닿� DB�뿉 �엳�쓣�븣 �쉶�썝媛��엯 吏꾪뻾(痍⑥빟�젏)
		//if(niceChk > 0) { 	
			
			//�븘�씠�뵒 以묐났泥댄겕(痍⑥빟�젏 愿��젴 �옉�뾽)		
			if(idChk == 0) {
				
				if(param.get("type") == null) {
					return "redirect:/member/joinStep1.af";
				}				
				
				String result = frontMemberService.memberJoin(parameter);
		
		
				//媛��엯臾몄옄 諛쒖넚
				//String contents = "[�븘荑좎븘�븘�뱶]"+DecoderUtil.decode(param, "userName")+"怨좉컼�떂  �쉶�썝媛��엯�셿猷�(ID:"+parameter.get("mem_id")+")";
				Map smsParam = new HashMap();
				smsParam.put("point_code", "POINT01");
				smsParam.put("sms_type", "JOIN");
				
				Map smsTemplte = commonService.getSmsTemplete(smsParam);
				String contents = smsTemplte.get("SMS_CONTENT").toString();
				contents = contents.replace("{怨좉컼紐�}",DecoderUtil.decode(param, "name"));//�씠由� 移섑솚
				contents = contents.replace("{�븘�씠�뵒}",DecoderUtil.decode(param, "id"));//媛��엯�븘�씠�뵒 移섑솚
				
				Map params = new HashMap();
				params.put("recipient_num", "01012341234"); // �닔�떊踰덊샇
				//param.put("subject", "[�븘荑좎븘�븘�뱶]�쉶�썝媛��엯臾몄옄�엯�땲�떎."); // LMS�씪寃쎌슦 �젣紐⑹쓣 異붽� �븷 �닔 �엳�떎.
				params.put("content", contents);  // �궡�슜 (SMS=88Byte, LMS=2000Byte)				
				//params.put("callback", "031-8072-8800");  // 諛쒖떊踰덊샇  
				params.put("callback", config.getProperty("sms.tel.number"));  // 諛쒖떊踰덊샇 
				
				
				if(!smsService.sendSms(params)){
					result = "ERROR";
				}
				else{
					//SMS 諛쒖넚 �씠�젰 �벑濡�
					smsParam.put("sms_uid", smsTemplte.get("SMS_UID"));
					smsParam.put("mem_id", DecoderUtil.decode(param, "id"));
					smsParam.put("custom_nm", DecoderUtil.decode(param, "name"));
					smsParam.put("custom_mobile", "01012341234");
					smsParam.put("ins_ip", request.getRemoteAddr());
					smsParam.put("ins_id", DecoderUtil.decode(param, "id")); 
					smsParam.put("send_status", "OK");	
					smsParam.put("bigo", "JOIN");			
					
					String smsResult = commonService.insSmsSend(smsParam);
					if("ERROR".equals(smsResult)){
						result = "ERROR";
					}		
				}
			
				//硫붿씪諛쒖넚 #####################################
				Map emailParam = new HashMap();
				emailParam.put("email_uid", "1");
				Map joinEmail = adminEmailTempletService.adminEmailTempletDetail(emailParam);
				
				//String realPath = session.getServletContext().getRealPath("/common/mailTemplete");
				String realPath = session.getServletContext().getRealPath("/common/upload/email_templet");
				
				//String joinHtml = super.getHTMfile(realPath+"/join_complete.html");
				String joinHtml = super.getHTMfile(realPath+"/1");			
		
				String nowTime = AquaDateUtil.getToday();
				nowTime = nowTime.substring(0, 4)+"."+nowTime.substring(4, 6)+"."+nowTime.substring(6, 8);		
				joinHtml = joinHtml.replace("{{#NOW#}}",nowTime); // �쁽�옱�떆媛� 移섑솚
				String reHtml= joinHtml.replace("{{#NAME#}}",DecoderUtil.decode(param, "name"));
		
				boolean booleanresult =	mailService.sendmail(mailSender, "aquafield@shinsegae.com", "�븘荑좎븘�븘�뱶", DecoderUtil.decode(param, "id"), DecoderUtil.decode(param, "name"), "[�븘荑좎븘�븘�뱶]�쉶�썝媛��엯硫붿씪�엯�땲�떎.", reHtml);
				
				if(!booleanresult){
					result = "ERROR";
				}
				//###########################################
			
				Map userInfo = new HashMap();
				userInfo.put("NAME", DecoderUtil.decode(param, "userName"));
				
				model.addAttribute("userInfo", userInfo);
				
				
				DateFormat pmtDateTransFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date today = new Date();
				Map parameter1 = new HashMap();
				parameter1.put("point_code", "POINT01");
				parameter1.put("mem_id", (String)param.get("id"));
				parameter1.put("loginDate", pmtDateTransFormat.format(today));
				Map memberInfo = frontMemberService.memberInfo(parameter1);
				
				parameter1.put("mem_pw", "kakao");	//�븫�샇�솕 �옉�뾽�빐�빞�븿 
				memberInfo = frontMemberService.memberInfo(parameter1);
				
				InetAddress ip = InetAddress.getLocalHost();
				String userIp = ip.getHostAddress();
				//memberInfo.put("MEM_IP", userIp); //�젒�냽 IP�젙蹂�
				//session.removeAttribute("MEM_INFO");
				//session.removeAttribute("RS_DATA");
				session.setAttribute("MEM_INFO", memberInfo);
				
				//int memUid = Util.getInt(memberInfo.get("MEM_UID").toString());
				frontMemberService.setLastloginDate(parameter1);
				String url = "/member/loginMain.af";
				Map<String,Object> loginMap =new HashMap<>();
				url = session.getAttribute("POINT_URL").toString();
				loginMap.put("check", "loginSuccess");
				loginMap.put("result", result);
				loginMap.put("url", url);				
				
				Map pointInfo = super.getPointInfo("hanam");
				session.setAttribute("POINT_CODE", pointInfo.get("CODE_ID"));
				session.setAttribute("POINT_URL", pointInfo.get("CODE_URL"));
		        return "/front/index";
        
			} else {
				
				Map userInfo = new HashMap();
				userInfo.put("NAME", DecoderUtil.decode(param, "name"));
				
				model.addAttribute("userInfo", userInfo);
				
				
				DateFormat pmtDateTransFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date today = new Date();
				Map parameter1 = new HashMap();
				parameter1.put("point_code", "POINT01");
				parameter1.put("mem_id", (String)param.get("id"));
				parameter1.put("loginDate", pmtDateTransFormat.format(today));
				Map memberInfo = frontMemberService.memberInfo(parameter1);
				
				//parameter1.put("mem_pw", "kakao");	//�븫�샇�솕 �옉�뾽�빐�빞�븿 
				memberInfo = frontMemberService.memberInfo(parameter1);
				
				InetAddress ip = InetAddress.getLocalHost();
				String userIp = ip.getHostAddress();
				memberInfo.put("MEM_IP", userIp); //�젒�냽 IP�젙蹂�
				//session.removeAttribute("MEM_INFO");
				//session.removeAttribute("RS_DATA");
				session.setAttribute("MEM_INFO", memberInfo);
				
				//int memUid = Util.getInt(memberInfo.get("MEM_UID").toString());
				frontMemberService.setLastloginDate(parameter1);
				String url = "/member/loginMain.af";
				Map<String,Object> loginMap =new HashMap<>();
				url = session.getAttribute("POINT_URL").toString();
				loginMap.put("check", "loginSuccess");
				//loginMap.put("result", result);
				loginMap.put("url", url);					
				
		        //return "/hanam/index";
				Map pointInfo = super.getPointInfo("hanam");
				session.setAttribute("POINT_CODE", pointInfo.get("CODE_ID"));
				session.setAttribute("POINT_URL", pointInfo.get("CODE_URL"));
		        return "/front/index";			
			}
			
		//} else {
			
		//	throw new Exception();
			
		//}
        
    }	
	
}
