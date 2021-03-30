package com.soft.web.controller.admin;

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
import com.soft.web.service.admin.AdminDeskAdminAuthService;
import com.soft.web.service.admin.AdminEmailTempletService;
import com.soft.web.service.admin.AdminTermsService;
import com.soft.web.util.DecoderUtil;
import com.soft.web.util.Util;

@Controller
@RequestMapping({"/secu_admaf"})
public class AdminDeskHomePageController extends GenericController {
	
	protected Logger logger = LoggerFactory.getLogger(AdminDeskHomePageController.class);
	
	@Autowired
	AdminTermsService admintermsservice;
	
	@Autowired
	AdminDeskAdminAuthService adminDeskAdminAuthService;

	@RequestMapping(value="/admdesk/homepage/index.af")
	public String homepageAdminIndex(@RequestParam Map param, ModelMap model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_DESK_ADMIN);
		//왼쪽 메뉴
		String sMenuCode = "1701";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminDeskAdminAuthService.deskAdminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null;
		
		return "/admdesk/homepage/index";
	}
	/*
	@RequestMapping(value="/admdesk/homepage/homepage.af")
	public String homepage(@RequestParam Map param, ModelMap model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		return "/admdesk/homepage/homepage";
	}
	
	@RequestMapping(value="/admdesk/homepage/privacy.af")
	public String privacy(@RequestParam Map param, ModelMap model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		return "/admdesk/homepage/privacy";
	}
	
	@RequestMapping(value="/admdesk/homepage/general_info.af")
	public String general_info(@RequestParam Map param, ModelMap model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		return "/admdesk/homepage/general_info";
	}
	
	@RequestMapping(value="/admdesk/homepage/terms_write.af")
	public String terms_write(@RequestParam Map param, ModelMap model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		//코드 조회
		List termTypeList = super.getCommonCodes("TERMS_TYPE");
		
		String reUrl = param.get("reUrl").toString();
		String selectBoxVal = "";
		
		switch (reUrl) {
			case "/admdesk/homepage/index.af": selectBoxVal = "AQUA_USE"; break;
			case "/admdesk/homepage/homepage.af": selectBoxVal = "HOMEPAGE_USE"; break;
			case "/admdesk/homepage/privacy.af": selectBoxVal = "PRIVACY"; break;
			case "/admdesk/homepage/general_info.af": selectBoxVal = "GENERAL"; break;
		}
		
		model.addAttribute("List",termTypeList);
		model.addAttribute("reUrl",param.get("reUrl"));
		model.addAttribute("selectBoxVal",selectBoxVal);		
		return "/admdesk/homepage/terms_write";
	}
	
	@RequestMapping(value = "/admdesk/homepage/terms_write_action.af", method=RequestMethod.POST)
    public String termsWriteAction(@RequestParam Map param, ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);

		param.put("INS_IP",request.getRemoteAddr());
		param.put("INS_ID",admin.get("SESSION_ADMIN_ID"));
		param.put("UPD_IP",request.getRemoteAddr());
		param.put("UPD_ID",admin.get("SESSION_ADMIN_ID"));
		
		
		//if(param.get("TERMS_TYPE") != null)  param.put("TERMS_TYPE",DecoderUtil.decode(param, "TERMS_TYPE"));
		//if(param.get("TERM_CONTENT") != null) param.put("TERM_CONTENT",DecoderUtil.decode(param, "TERM_CONTENT"));
		
//		try{
			if("inst".equals(param.get("mode").toString() ) ){
				admintermsservice.adminTermsInsert(param);
				//Util.htmlPrint("{\"result\":true,\"msg\":\"등록 되었습니다.\"}", response);
			}
			else{
				admintermsservice.adminTermsUpdate(param);
				//Util.htmlPrint("{\"result\":true,\"msg\":\"수정 되었습니다.\"}", response);
			}
			
//		}
//		catch(Exception e){
//			logger.debug("termsWriteAction : " + e.toString());
//			Util.htmlPrint("{\"result\":false,\"msg\":\"오류가 발생했습니다. 잠시후에 다시 하세요.\"}", response);
//		}
		return "redirect:/admdesk/homepage/index.af";
    }
    */	
}
