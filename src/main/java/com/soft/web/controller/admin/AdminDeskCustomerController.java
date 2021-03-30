package com.soft.web.controller.admin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
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

import com.soft.web.base.CommonConstant;
import com.soft.web.base.GenericController;
import com.soft.web.mail.MailService;
import com.soft.web.service.admin.AdminDeskAdminAuthService;
import com.soft.web.service.admin.AdminEmailTempletService;
import com.soft.web.service.admin.CustomerService;
import com.soft.web.service.common.CommonService;
import com.soft.web.sms.SmsService;
import com.soft.web.util.AquaDateUtil;
import com.soft.web.util.DecoderUtil;
import com.soft.web.util.PageUtil;
import com.soft.web.util.Util;

@Controller
@RequestMapping({"/secu_admaf"})
public class AdminDeskCustomerController extends GenericController {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name="config")
    private Properties config;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	SmsService smsService;
	
	@Autowired
	AdminDeskAdminAuthService adminDeskAdminAuthService;
	
	@Autowired
	AdminEmailTempletService adminEmailTempletService;
	
	@Autowired
	CommonService commonService;	
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private MailService mailService;	
	
	//paging
	int pageListSize = 10;
	int blockListSize = 10;
	
	protected String[] pageParamList = {"page","srch_text","srch_point","srch_type","srch_reg_s", "srch_reg_e", "srch_stat"};
	
	/**
	 * 고객문의 목록 
	 * @throws UnsupportedEncodingException 
     */
	@SuppressWarnings("unchecked")
	@RequestMapping("/admdesk/customer/index.af")
	public String index(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_DESK_ADMIN);
		//왼쪽 메뉴
		String sMenuCode = "1601";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminDeskAdminAuthService.deskAdminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null;
		
		int page = 0;
		//-- xss 체크
		param = Util.chkParam(pageParamList, param);
		
		//페이징
		page = (param.get("page") == null || "".equals(param.get("page").toString() ) ) ?  page = 1 : Util.getInt(param.get("page").toString());
		if(page < 1){
			page = 1;
			param.put("page",String.valueOf(page));
		}
		//-- 16진수 변경
		param = Util.setMapHex(param);
		
		int totCnt = customerService.customerListCnt(param);
		PageUtil paging = new PageUtil(page, totCnt, pageListSize, blockListSize);
		param.put("pageStartRow", paging.getPageStartRow());
		param.put("pageListSize", pageListSize);
		param.put("page", String.valueOf(page));
		
		//목록 조회
		List<Map> cList = customerService.customerList(param);
		//코드 조회
		List codeASK_TYPE = super.getCommonCodes("ASK_TYPE");
		List codeASK_STAT = super.getCommonCodes("ASK_STAT");
		List codePoint_code = super.getCommonCodes("POINT_CODE");
		//답변등록 결과
		String result = (String)param.get("result");
		if(result != null && !"".equals(result) && "1".equals(result)){
			model.addAttribute("msg", "답변등록이 완료되었습니다.");
		}
		
		// Util.pageParamMap2 는 post 방식용, Util.pageParamMap 는 get 방식용 (URLEncoder 들어가있음)
		//param = Util.setMapKoConvert(param, config.getProperty("character.set"));// US7ASCII에서 EUC-KR
		model.addAttribute("resultParam", param);
		model.addAttribute("totalCount", Util.numberFormat(totCnt));
		model.addAttribute("pu", paging);
		model.addAttribute("cList", cList);
		model.addAttribute("codeASK_TYPE", codeASK_TYPE);
		model.addAttribute("codeASK_STAT", codeASK_STAT);
		model.addAttribute("codePoint_code", codePoint_code);
		model.addAttribute("MEMINFOYN", admin.get("SESSION_MEMINFOYN").toString());
		
		return "/admdesk/customer/index";
	}
	
	/*
	 * 고객문의 상세
	 */
	@RequestMapping("/admdesk/customer/contact_view.af")
	public String contact_view(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_DESK_ADMIN);
		//왼쪽 메뉴
		String sMenuCode = "1601";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminDeskAdminAuthService.deskAdminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null;
		
		//-- xss 체크
		param = Util.chkParam(pageParamList, param);
		Map vo = customerService.customerDetail(param);
		
		if(vo == null || vo.size() == 0){
			Util.htmlPrint(Util.historyBack("등록되지 않았거나 삭제된 자료 입니다."), response);
			return null;
		}			
		
		model.addAttribute("resultParam", Util.pageParamMap2(pageParamList, param) );
		model.addAttribute("vo",vo);
		model.addAttribute("MEMINFOYN", admin.get("SESSION_MEMINFOYN").toString());
		
		//관리자 사용기록 로그 #############################################		
		String etc = vo.get("INS_ID").toString()+"("+ vo.get("ASK_TITLE").toString() +") 열람";
		Map logParam = new HashMap();
		logParam.put("point_code", "POINT01");
		logParam.put("action", "R");
		/* 20180320 수정
		 * logParam.put("data_num", param.get("ask_uid"));*/
		logParam.put("data_num", vo.get("WRITER").toString() + "님의 정보를 열람하셨습니다.");
		logParam.put("data_url", request.getRequestURL().toString()+"?=ask_uid"+param.get("ask_uid").toString());
		logParam.put("ins_ip", request.getRemoteAddr().toString());	
		logParam.put("ins_admin_id", admin.get("SESSION_ADMIN_ID"));	
		logParam.put("admin_nm", admin.get("SESSION_ADMIN_NM"));	
		logParam.put("admin_uid", admin.get("SESSION_ADMIN_UID"));	
		logParam.put("access_menu_uid", "1601");	
		logParam.put("etc", etc);			
		super.InsContentsLog(logParam);
		//############################################################			
		
		return "/admdesk/customer/contact_view";
	}
	
	
	/*
	 * 고객문의 답변등록
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/admdesk/customer/contact_update.af")
	public String contact_update(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_DESK_ADMIN);
		//왼쪽 메뉴
		String sMenuCode = "1601";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminDeskAdminAuthService.deskAdminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null;
		
		//-- xss 체크
		param = Util.chkParam(pageParamList, param);
		// 수정자 ID 나중에 관리자 로그인 세센 추가되면 수정해서 넣어야함
		String re_id = "admin";
		param.put("re_id", re_id);
		customerService.customerUpdate(param);
		
		//관리자 사용기록 로그 #############################################
		String action = "U";
		int data_num = Integer.parseInt(param.get("ask_uid").toString());
		String httpName = request.getRequestURL().toString().substring(0, request.getRequestURL().toString().indexOf(request.getServletPath()));
		String data_url = httpName + "/secu_admaf/admdesk/customer/contact_view.af?=ask_uid"+data_num;
		String etcContent = "수정";
		String etc = param.get("ask_nm").toString()+"("+ param.get("ask_phone").toString() +") " + etcContent ;
		param.put("ask_uid", data_num);
		Map vo = customerService.customerDetail(param);
		Map logParam = new HashMap();
		logParam.put("point_code", "POINT01");
		logParam.put("action", action);
		/*logParam.put("data_num", data_num);*/
		logParam.put("data_num", vo.get("WRITER").toString() +" 님의 답변등록을 하셨습니다.");
		logParam.put("data_url", data_url);
		logParam.put("ins_ip", request.getRemoteAddr().toString());	
		logParam.put("ins_admin_id", admin.get("SESSION_ADMIN_ID"));	
		logParam.put("admin_nm", admin.get("SESSION_ADMIN_NM"));	
		logParam.put("admin_uid", admin.get("SESSION_ADMIN_UID"));	
		logParam.put("access_menu_uid", "1601");	
		logParam.put("etc", etc);			
		super.InsContentsLog(logParam);
		//############################################################		
		
		//고객의 소리 답변완료 문자 발송
		String contents = "[아쿠아필드]"+param.get("ask_nm")+"고객님  고객의 소리 답변 완료되었습니다.";
		
		Map params = new HashMap();
		params.put("recipient_num", param.get("ask_phone")); // 수신번호
		//param.put("subject", "[아쿠아필드]회원가입문자입니다."); // LMS일경우 제목을 추가 할 수 있다.
		params.put("content", contents); // 내용 (SMS=88Byte, LMS=2000Byte)		
		//params.put("callback", "031-8072-8800"); // 발신번호
		params.put("callback", config.getProperty("sms.tel.number")); // 발신번호
		
		if(!smsService.sendSms(params)){
			logger.debug("@@@@@@@@@@@@@@@@ 고객의 소리  답변 완료 알림 문자 발송중 에러가 발생했습니다. @@@@@@@@@@@@@@@@@");
		}
		
		//메일발송 #####################################
		Map customerDetail = customerService.customerDetail(param);
		
		Map emailParam = new HashMap();
		emailParam.put("email_uid", "5");
		Map askEmail = adminEmailTempletService.adminEmailTempletDetail(emailParam);
		
		String realPath = session.getServletContext().getRealPath("/common/upload/email_templet");	
		String reHtml = super.getHTMfile(realPath+"/5");						
		
		String nowTime = AquaDateUtil.getToday();
		nowTime = nowTime.substring(0, 4)+"."+nowTime.substring(4, 6)+"."+nowTime.substring(6, 8);						
		reHtml = reHtml.replace("{{#NOW#}}",nowTime); // 현재시간 치환
		reHtml = reHtml.replace("{{#TITLE#}}",customerDetail.get("ASK_TITLE").toString());//제목 치환
		reHtml = reHtml.replace("{{#ASKDATE#}}",customerDetail.get("INS_DATE").toString());//등록일 치환
		reHtml = reHtml.replace("{{#CONTENTS#}}",customerDetail.get("ASK_CONTENT").toString());//질문내용 치환
		reHtml = reHtml.replace("{{#ANSWERDATE#}}",nowTime);//답변일 치환
		reHtml = reHtml.replace("{{#ANSWERCONTENTS#}}",customerDetail.get("ASK_CONTENT2").toString());//답변내용 치환		
		
		boolean booleanresult =	mailService.sendmail(mailSender, askEmail.get("FROM_EMAIL").toString(), askEmail.get("FORM_EMAIL_NM").toString(), customerDetail.get("INS_ID").toString(), customerDetail.get("WRITER").toString(), askEmail.get("EMAIL_TITLE").toString(), reHtml);

		if(!booleanresult){
			logger.debug("@@@@@@@@@@@@@@@@ 고객의 소리  답변 완료 알림 메일 발송중 에러가 발생했습니다. @@@@@@@@@@@@@@@@@");
		}else{
			//EMAIL 발송 이력 등록
			emailParam.put("point_code", "POINT01");
			emailParam.put("email_uid", "5");
			emailParam.put("mem_id", customerDetail.get("INS_ID"));
			emailParam.put("custom_nm", customerDetail.get("WRITER"));
			emailParam.put("custom_email", customerDetail.get("INS_ID"));
			emailParam.put("ins_ip", "");
			emailParam.put("ins_id", customerDetail.get("INS_ID")); 
			emailParam.put("send_status", "OK");
			
			String smsResult = commonService.insEmailSend(emailParam);
			if("ERROR".equals(emailParam)){
				logger.debug("@@@@@@@@@@@@@@@@ 처리중 에러가 발생하였습니다.(이메일 발송 등록) @@@@@@@@@@@@@@@@@");
			}						
		}						
		
		//###########################################			
		
		return "redirect:/secu_admaf/admdesk/customer/index.af?result=1";
	}
	
	
}
