package com.soft.web.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.soft.web.base.CommonConstant;
import com.soft.web.base.GenericController;
import com.soft.web.service.admin.AdminAdminAuthService;
import com.soft.web.service.admin.AdminEmailTempletService;
import com.soft.web.service.admin.AdminSmsTempletService;
import com.soft.web.util.DecoderUtil;
import com.soft.web.util.PageUtil;
import com.soft.web.util.Util;

@Controller
@RequestMapping({"/secu_admaf"})
public class AdminSendController extends GenericController {

	protected Logger logger = LoggerFactory.getLogger(AdminSendController.class);

	@Autowired
	AdminSmsTempletService adminSmsTempletService;

	@Autowired
	AdminEmailTempletService adminEmailTempletService;
	
	@Autowired
	AdminAdminAuthService adminAdminAuthService;

	public String[] getPageParamList(){
		String[] pageParamList = {"num","page","nm","sd","ed","sw"};
		return pageParamList;
	}
	
	@RequestMapping(value = {"/admin/send/index.af"})
    public String index(@RequestParam Map param, ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//-- 왼쪽메뉴
		String sMenuCode = "1501";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null;		

		model.addAttribute("results", adminSmsTempletService.adminSmsTempletList(param));
 
 		return "/admin/send/index";
    }

	@RequestMapping(value = {"/admin/send/sms_write.af"})
    public String smsWrite(@RequestParam Map param, ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//-- 왼쪽메뉴
		String sMenuCode = "1501";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null; 		
		
		//-- xss 체크
		param = Util.chkParam(this.getPageParamList(), param);
		
		Map adminSmsTempletDetail = null;
		model.put("resultParam", param);
		if(param.get("num") != null && !"".equals(param.get("num").toString() ) ){
			param.put("SMS_UID",param.get("num"));			
			//-- 상세정보
			adminSmsTempletDetail = adminSmsTempletService.adminSmsTempletDetail(param);
			model.addAttribute("result", adminSmsTempletService.adminSmsTempletDetail(param));
		}
		model.addAttribute("resultParam", param);
			
		//관리자 사용기록 로그 #############################################
		/*String etc = adminSmsTempletDetail.get("SMS_UID").toString()+"("+ adminSmsTempletDetail.get("SMS_TYPE").toString() +") 열람";;
		Map logParam = new HashMap();
		logParam.put("point_code", "POINT01");
		logParam.put("action", "R");
		logParam.put("data_num", "제목 : " + adminSmsTempletDetail.get("SMS_CONTENT"));
		logParam.put("data_url", request.getRequestURL().toString()+"?num="+param.get("num").toString());
		logParam.put("ins_ip", request.getRemoteAddr().toString());	
		logParam.put("ins_admin_id", admin.get("SESSION_ADMIN_ID"));	
		logParam.put("admin_nm", admin.get("SESSION_ADMIN_NM"));	
		logParam.put("admin_uid", admin.get("SESSION_ADMIN_UID"));	
		logParam.put("access_menu_uid", "1501");	
		logParam.put("etc", etc);			
		super.InsContentsLog(logParam);*/
		//############################################################			
		
		return "/admin/send/sms_write";
		
    }
	
	
	@RequestMapping(value = "/admin/send/sms_write_action.af", method=RequestMethod.POST)
    public void smsWriteAction(@RequestParam Map param, ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//-- 왼쪽메뉴
		String sMenuCode = "1501";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return;

		param.put("INS_IP",request.getRemoteAddr());
		param.put("INS_ID",admin.get("SESSION_ADMIN_ID"));
		param.put("UPD_IP",request.getRemoteAddr());
		param.put("UPD_ID",admin.get("SESSION_ADMIN_ID"));
		
		if(param.get("SMS_TYPE") != null)  param.put("SMS_TYPE",DecoderUtil.decode(param, "SMS_TYPE"));
		if(param.get("SMS_CONTENT") != null) param.put("SMS_CONTENT",DecoderUtil.decode(param, "SMS_CONTENT"));
		
		//-- 16진수 변경
		param = Util.setMapHex(param);

		try{
			if("inst".equals(param.get("mode").toString() ) ){
				adminSmsTempletService.adminSmsTempletInsert(param);
				Util.htmlPrint("{\"result\":true,\"msg\":\"등록 되었습니다.\"}", response);
			}
			else{
				adminSmsTempletService.adminSmsTempletUpdate(param);
				Util.htmlPrint("{\"result\":true,\"msg\":\"수정 되었습니다.\"}", response);
			}
			
			//관리자 사용기록 로그 #############################################
			/*String action = "inst".equals(param.get("mode").toString()) ? "C" : "U";
			int data_num = "C".equals(action) ? Integer.parseInt(param.get("uid").toString()) : Integer.parseInt(param.get("num").toString());
			String httpName = request.getRequestURL().toString().substring(0, request.getRequestURL().toString().indexOf(request.getServletPath()));
			String data_url = httpName + "/secu_admaf/admin/send/sms_write.af?num="+data_num;
			String etcContent = "C".equals(action) ? "생성" : "수정";
			String etc = param.get("num").toString()+"("+ param.get("sms_type").toString() +") " + etcContent ;
			
			param.put("SMS_UID",param.get("num"));			
			//-- 상세정보
			Map adminSmsTempletDetail = adminSmsTempletService.adminSmsTempletDetail(param);
			
			Map logParam = new HashMap();
			logParam.put("point_code", "POINT01");
			logParam.put("action", action);
			logParam.put("data_num", "제목 : " + adminSmsTempletDetail.get("SMS_CONTENT"));
			logParam.put("data_url", data_url);
			logParam.put("ins_ip", request.getRemoteAddr().toString());	
			logParam.put("ins_admin_id", admin.get("SESSION_ADMIN_ID"));	
			logParam.put("admin_nm", admin.get("SESSION_ADMIN_NM"));	
			logParam.put("admin_uid", admin.get("SESSION_ADMIN_UID"));	
			logParam.put("access_menu_uid", "1501");	
			logParam.put("etc", etc);			
			super.InsContentsLog(logParam);*/
			//############################################################				
			
		}
		catch(Exception e){
			logger.debug("smsWriteAction : " + e.toString());
			Util.htmlPrint("{\"result\":false,\"msg\":\"오류가 발생했습니다. 잠시후에 다시 하세요.\"}", response);
		}
    }
	
	
	@RequestMapping(value = {"/admin/send/email.af"})
    public String email(@RequestParam Map param, ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//-- 왼쪽메뉴
		String sMenuCode = "1502";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null;		

		model.addAttribute("results", adminEmailTempletService.adminEmailTempletList(param));
 
 		return "/admin/send/email";
    }

	@RequestMapping(value = {"/admin/send/email_write.af"})
    public String emailWrite(@RequestParam Map param, ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//-- 왼쪽메뉴
		String sMenuCode = "1502";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null; 		
		
		Map<String,String> fileMap;
		String upload = "/common/upload/email_templet";
		String realPath = "";    //실제 경로 저장 변수
		realPath = super.getSession().getServletContext().getRealPath(upload);

		//-- xss 체크
		param = Util.chkParam(this.getPageParamList(), param);

		Map adminEmailTempletDetail = null;
		
		model.put("resultParam", param);
		if(param.get("num") != null && !"".equals(param.get("num").toString() ) ){
			param.put("email_uid",param.get("num"));
			
			//-- 상세정보
			adminEmailTempletDetail = adminEmailTempletService.adminEmailTempletDetail(param);
			model.addAttribute("result", adminEmailTempletService.adminEmailTempletDetail(param));
			model.addAttribute("resultContent", Util.getReadFile(realPath + "/" + param.get("num")));
		}
		model.addAttribute("resultParam", param);
		
		//관리자 사용기록 로그 #############################################
		/*String etc = adminEmailTempletDetail.get("EMAIL_UID").toString()+"("+ adminEmailTempletDetail.get("EMAIL_TYPE").toString() +") 열람";;
		Map logParam = new HashMap();
		logParam.put("point_code", "POINT01");
		logParam.put("action", "R");
		 20180319 수정
		 * logParam.put("data_num", param.get("mem_uid"));
		logParam.put("data_num", "제목 : " + adminEmailTempletDetail.get("EMAIL_TITLE"));
		logParam.put("data_url", request.getRequestURL().toString()+"?num="+param.get("num").toString());
		logParam.put("ins_ip", request.getRemoteAddr().toString());	
		logParam.put("ins_admin_id", admin.get("SESSION_ADMIN_ID"));	
		logParam.put("admin_nm", admin.get("SESSION_ADMIN_NM"));	
		logParam.put("admin_uid", admin.get("SESSION_ADMIN_UID"));	
		logParam.put("access_menu_uid", "1502");	
		logParam.put("etc", etc);			
		super.InsContentsLog(logParam);*/
		//############################################################			
		
		return "/admin/send/email_write";
		
    }
	
	
	@RequestMapping(value = "/admin/send/email_write_action.af", method=RequestMethod.POST)
    public void emailWriteAction(@RequestParam Map param, ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//-- 왼쪽메뉴
		String sMenuCode = "1502";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return;
		
		Map<String,String> fileMap;
		String upload = "/common/upload/email_templet";
		String realPath = "";    //실제 경로 저장 변수
		realPath = super.getSession().getServletContext().getRealPath(upload);
		Util.makeDir(realPath);
		

		param.put("INS_IP",request.getRemoteAddr());
		param.put("INS_ID",admin.get("SESSION_ADMIN_ID"));
		param.put("UPD_IP",request.getRemoteAddr());
		param.put("UPD_ID",admin.get("SESSION_ADMIN_ID"));
		
		
		if(param.get("EMAIL_TYPE") != null)  param.put("EMAIL_TYPE",DecoderUtil.decode(param, "EMAIL_TYPE"));
		if(param.get("FROM_EMAIL") != null)  param.put("FROM_EMAIL",DecoderUtil.decode(param, "FROM_EMAIL"));
		if(param.get("EMAIL_TITLE") != null) param.put("EMAIL_TITLE",DecoderUtil.decode(param, "EMAIL_TITLE"));
		if(param.get("EMAIL_CONTENT") != null) param.put("EMAIL_CONTENT",DecoderUtil.decode(param, "EMAIL_CONTENT"));
		if(param.get("FORM_EMAIL_NM") != null) param.put("FORM_EMAIL_NM",DecoderUtil.decode(param, "FORM_EMAIL_NM"));
		
		//-- 16진수 변경
		param = Util.setMapHex(param);
		
		try{
			if("inst".equals(param.get("mode").toString() ) ){
				adminEmailTempletService.adminEmailTempletInsert(param);
				int maxIdx = adminEmailTempletService.adminEmailTempletMaxUid();
				//-- 파일생성
				Util.writeFile(realPath + "/" + maxIdx, (String)param.get("EMAIL_CONTENT"));
				
				Util.htmlPrint("{\"result\":true,\"msg\":\"등록 되었습니다.\"}", response);
			}
			else{
				adminEmailTempletService.adminEmailTempletUpdate(param);
				//-- 파일생성
				Util.writeFile(realPath + "/" + param.get("num"), (String)param.get("EMAIL_CONTENT"));
				Util.htmlPrint("{\"result\":true,\"msg\":\"수정 되었습니다.\"}", response);
			}
			
			//관리자 사용기록 로그 #############################################
			/*String action = "inst".equals(param.get("mode").toString()) ? "C" : "U";
			int data_num = "C".equals(action) ? Integer.parseInt(param.get("uid").toString()) : Integer.parseInt(param.get("num").toString());
			String httpName = request.getRequestURL().toString().substring(0, request.getRequestURL().toString().indexOf(request.getServletPath()));
			String data_url = httpName + "/secu_admaf/admin/send/email_write.af?num="+data_num;
			String etcContent = "C".equals(action) ? "생성" : "수정";
			String etc = param.get("num").toString()+"("+ param.get("email_type").toString() +") " + etcContent ;
			
			
			param.put("email_uid",param.get("num"));
			
			//-- 상세정보
			Map adminEmailTempletDetail = adminEmailTempletService.adminEmailTempletDetail(param);
			
			Map logParam = new HashMap();
			logParam.put("point_code", "POINT01");
			logParam.put("action", action);
			 20180319 수정
			 * logParam.put("data_num", data_num);
			logParam.put("data_num", "제목 : " + adminEmailTempletDetail.get("EMAIL_TITLE"));
			logParam.put("data_url", data_url);
			logParam.put("ins_ip", request.getRemoteAddr().toString());	
			logParam.put("ins_admin_id", admin.get("SESSION_ADMIN_ID"));	
			logParam.put("admin_nm", admin.get("SESSION_ADMIN_NM"));	
			logParam.put("admin_uid", admin.get("SESSION_ADMIN_UID"));	
			logParam.put("access_menu_uid", "1502");	
			logParam.put("etc", etc);			
			super.InsContentsLog(logParam);*/
			//############################################################				
			
		}
		catch(Exception e){
			logger.debug("emailWriteAction : " + e.toString());
			Util.htmlPrint("{\"result\":false,\"msg\":\"오류가 발생했습니다. 잠시후에 다시 하세요.\"}", response);
		}
    }
	

	

}
