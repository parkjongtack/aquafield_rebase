package com.soft.web.controller.admin;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.soft.web.base.CommonConstant;
import com.soft.web.base.GenericController;
import com.soft.web.service.admin.AdminAdminAuthService;
import com.soft.web.service.admin.CustomerService;
import com.soft.web.service.admin.MemberService;
import com.soft.web.service.admin.ReservationService;
import com.soft.web.service.front.FrontMemberService;
import com.soft.web.util.PageUtil;
import com.soft.web.util.Util;

@Controller
@RequestMapping({"/secu_admaf"})
public class AdminMemberController extends GenericController {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MemberService memberService;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	ReservationService reservationService;
	
	@Autowired
	AdminAdminAuthService adminAdminAuthService;	
	
	@Autowired
	FrontMemberService frontMemberService;
	
	//paging
	int pageListSize = 10;
	int blockListSize = 10;
	
	protected String[] pageParamList = {"page","srch_type", "srch_text","srch_sex","srch_reg_s","srch_reg_e", "order_res"};
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/admin/member/index.af")
	public String index(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		String sMenuCode = "1201";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null; 		
		
		int page = 0;
		param = Util.chkParam(pageParamList, param);
		
		page = (param.get("page") == null || "".equals(param.get("page").toString() ) ) ?  page = 1 : Util.getInt(param.get("page").toString());
		if(page < 1){
			page = 1;
			param.put("page",String.valueOf(page));
		}

		param = Util.setMapHex(param);
		
		int totCnt = memberService.memberListCnt(param);
				
		PageUtil paging = new PageUtil(page, totCnt, pageListSize, blockListSize);
		param.put("pageStartRow", paging.getPageStartRow());
		param.put("pageListSize", pageListSize);
		param.put("page", String.valueOf(page));

		List<Map> mList = memberService.memberList(param);
		

		List sexList = super.getCommonCodes("SEX");
		

		String result = (String)param.get("result");
		if(result != null && !"".equals(result) && "1".equals(result)){
			model.addAttribute("msg", "수정이 완료되었습니다.");
		}else if(result != null && !"".equals(result) && "2".equals(result)){
			model.addAttribute("msg", "비정상 접근 입니다.");
		}
		

		model.addAttribute("resultParam", param);
		model.addAttribute("totalCount", Util.numberFormat(totCnt));
		model.addAttribute("pu", paging);
 		model.addAttribute("mList", mList);
 		model.addAttribute("sexList", sexList);
 		model.addAttribute("MEMINFOYN", admin.get("SESSION_MEMINFOYN").toString());
		
 		
		return "/admin/member/index";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/admin/member/sns_list.af")
	public String sns_list(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		String sMenuCode = "1201";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null; 		
		
		int page = 0;
		param = Util.chkParam(pageParamList, param);
		
		page = (param.get("page") == null || "".equals(param.get("page").toString() ) ) ?  page = 1 : Util.getInt(param.get("page").toString());
		if(page < 1){
			page = 1;
			param.put("page",String.valueOf(page));
		}

		param = Util.setMapHex(param);
		
		int totCnt = memberService.memberListCnt2(param);
			
		
		PageUtil paging = new PageUtil(page, totCnt, pageListSize, blockListSize);
		
		param.put("pageStartRow", paging.getPageStartRow());
		param.put("pageListSize", pageListSize);
		param.put("page", String.valueOf(page));

		List<Map> mList = memberService.memberList2(param);		

		List sexList = super.getCommonCodes("SEX");
		

		String result = (String)param.get("result");
		if(result != null && !"".equals(result) && "1".equals(result)){
			model.addAttribute("msg", "수정이 완료되었습니다.");
		}else if(result != null && !"".equals(result) && "2".equals(result)){
			model.addAttribute("msg", "비정상 접근 입니다.");
		}
		

		model.addAttribute("resultParam", param);
		model.addAttribute("totalCount", Util.numberFormat(totCnt));
		model.addAttribute("pu", paging);
 		model.addAttribute("mList", mList);
 		model.addAttribute("sexList", sexList);
 		model.addAttribute("MEMINFOYN", admin.get("SESSION_MEMINFOYN").toString());
		
 		
		return "/admin/member/sns_list";
	}	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/admin/member/member_info.af")
	public String member_info(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);

		String sMenuCode = "1201";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null; 
		

		param = Util.chkParam(pageParamList, param);
		Map vo = memberService.memberDetail(param);
		model.addAttribute("resultParam", Util.pageParamMap2(pageParamList, param) );
		model.addAttribute("vo",vo);
		model.addAttribute("MEMINFOYN", admin.get("SESSION_MEMINFOYN").toString());
		

		String etc = vo.get("MEM_NM").toString()+"("+ vo.get("MEM_ID").toString() +") 열람";
		Map logParam = new HashMap();
		logParam.put("point_code", "POINT01");
		logParam.put("action", "R");
		/* 20180315 �닔�젙 */
		logParam.put("data_num", vo.get("MEM_NM") + "회원님의 정보를 열람하셨습니다.");
		logParam.put("data_url", request.getRequestURL().toString()+"?mem_uid="+param.get("mem_uid").toString());
		logParam.put("ins_ip", request.getRemoteAddr().toString());	
		logParam.put("ins_admin_id", admin.get("SESSION_ADMIN_ID"));	
		logParam.put("admin_nm", admin.get("SESSION_ADMIN_NM"));	
		logParam.put("admin_uid", admin.get("SESSION_ADMIN_UID"));	
		logParam.put("access_menu_uid", "1201");	
		logParam.put("etc", etc);			    
		super.InsContentsLog(logParam);
		//############################################################			
		
		return "/admin/member/member_info";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/admin/member/member_update.af")
	public String member_update(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);

		String sMenuCode = "1201";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null; 
		

		param = Util.chkParam(pageParamList, param);

		param.put("up_id", admin.get("SESSION_ADMIN_ID"));
		memberService.memberUpdate(param);
		

		String etc = param.get("mem_uid").toString()+"("+ param.get("mem_id").toString() +") �닔�젙";;
		Map logParam = new HashMap();
		logParam.put("point_code", "POINT01");
		logParam.put("action", "U");
		
		logParam.put("data_num", admin.get("SESSION_ADMIN_NM") + "회원님의 정보를 수정하셨습니다.");
		logParam.put("data_url", "/secu_admaf/admin/member/member_info.af?mem_uid="+param.get("mem_uid"));
		logParam.put("ins_ip", request.getRemoteAddr().toString());	
		logParam.put("ins_admin_id", admin.get("SESSION_ADMIN_ID"));	
		logParam.put("admin_nm", admin.get("SESSION_ADMIN_NM"));	
		logParam.put("admin_uid", admin.get("SESSION_ADMIN_UID"));	
		logParam.put("access_menu_uid", "1201");	
		logParam.put("etc", etc);			
		super.InsContentsLog(logParam);
		//############################################################			
		
		return "redirect:/secu_admaf/admin/member/index.af?result=1";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/admin/member/member_recovery.af")
	public String member_recovery(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println("::::관리자 로그인횟수초기화::::");
		
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		String mem_name = (String) param.get("mem_name");
		String mem_uid = (String) param.get("mem_uid");
		
		Map vo = memberService.memberDetail(param);
		model.addAttribute("vo",vo);
		
		if(!"".equals(mem_uid) && mem_uid != null) {
			String result = frontMemberService.memberRecovery(mem_uid);
			

			String etc = param.get("mem_uid").toString()+"("+ param.get("mem_id").toString() +") 수정";;
			Map logParam = new HashMap();
			logParam.put("point_code", "POINT01");
			logParam.put("action", "D");
			logParam.put("data_num", vo.get("MEM_NM") + "회원님의 로그인 횟수를 초기화하였습니다.");
			logParam.put("data_url", "");
			logParam.put("ins_ip", request.getRemoteAddr().toString());	
			logParam.put("ins_admin_id", admin.get("SESSION_ADMIN_ID"));	
			logParam.put("admin_nm", admin.get("SESSION_ADMIN_NM"));	
			logParam.put("admin_uid", admin.get("SESSION_ADMIN_UID"));	
			logParam.put("access_menu_uid", "1201");	
			logParam.put("etc", etc);
			
			super.InsContentsLog(logParam);
			
		}
		
		System.out.println("::::관리자 회원탈퇴 완료::::");
		return "redirect:/secu_admaf/admin/member/index.af";
	}	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/admin/member/member_del.af")
	public String member_delete(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println("::::관리자 회원탈퇴::::");
		
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		String mem_name = (String) param.get("mem_name");
		String mem_uid = (String) param.get("mem_uid");
		
		Map vo = memberService.memberDetail(param);
		model.addAttribute("vo",vo);
		
		if(!"".equals(mem_uid) && mem_uid != null) {
			String result = frontMemberService.memberDel(mem_uid);
			

			String etc = param.get("mem_uid").toString()+"("+ param.get("mem_id").toString() +") 수정";;
			Map logParam = new HashMap();
			logParam.put("point_code", "POINT01");
			logParam.put("action", "D");
			logParam.put("data_num", vo.get("MEM_NM") + "회원님의 정보를 삭제하셨습니다.");
			logParam.put("data_url", "");
			logParam.put("ins_ip", request.getRemoteAddr().toString());	
			logParam.put("ins_admin_id", admin.get("SESSION_ADMIN_ID"));	
			logParam.put("admin_nm", admin.get("SESSION_ADMIN_NM"));	
			logParam.put("admin_uid", admin.get("SESSION_ADMIN_UID"));	
			logParam.put("access_menu_uid", "1201");	
			logParam.put("etc", etc);
			
			super.InsContentsLog(logParam);
			
		}
		
		System.out.println("::::관리자 회원탈퇴 완료::::");
		return "redirect:/secu_admaf/admin/member/index.af";
	}
	

	protected String[] pageParamListCS = {"page","srch_text","srch_point","srch_type","srch_reg_s", "srch_reg_e", "srch_stat", "mem_uid"};

	@SuppressWarnings("unchecked")
	@RequestMapping("/admin/member/member_cs_list.af")
	public String member_cs_list(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);

		String sMenuCode = "1201";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null; 
		
		int page = 0;

		param = Util.chkParam(pageParamListCS, param);
		

		String check = (String) param.get("mem_uid");
		if(check == null || "".equals(check)){
			return "redirect:/secu_admaf/admin/member/index.af?result=2";
		}
		

		page = (param.get("page") == null || "".equals(param.get("page").toString() ) ) ?  page = 1 : Util.getInt(param.get("page").toString());
		int totCnt = customerService.customerListCnt(param);
		PageUtil paging = new PageUtil(page, totCnt, pageListSize, blockListSize);
		param.put("pageStartRow", paging.getPageStartRow());
		param.put("pageListSize", pageListSize);
		param.put("page", String.valueOf(page));
		

		param = Util.setMapHex(param);

		List<Map> cList = customerService.customerList(param);

		List codeASK_type = super.getCommonCodes("ASK_TYPE");
		List codeASK_stat = super.getCommonCodes("ASK_STAT");
		List codePoint_code = super.getCommonCodes("POINT_CODE");

		String result = (String)param.get("result");
		if(result != null && !"".equals(result) && "1".equals(result)){
			model.addAttribute("msg", "답변등록이 완료되었습니다.");
		}
		

		model.addAttribute("resultParam", Util.pageParamMap2(pageParamListCS, param) );
		model.addAttribute("totalCount", Util.numberFormat(totCnt));
		model.addAttribute("pu", paging);
		model.addAttribute("cList", cList);
		model.addAttribute("codeASK_type", codeASK_type);
		model.addAttribute("codeASK_stat", codeASK_stat);
		model.addAttribute("codePoint_code", codePoint_code);
		
		return "/admin/member/member_cs_list";
	}
	
	@RequestMapping("/admin/member/member_cs_view.af")
	public String member_cs_view(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);

		String sMenuCode = "1201";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null; 		
		

		param = Util.chkParam(pageParamListCS, param);
		Map vo = customerService.customerDetail(param);
		model.addAttribute("resultParam", Util.pageParamMap2(pageParamListCS, param) );
		model.addAttribute("vo",vo);
		

		String etc = vo.get("INS_ID").toString()+"("+ vo.get("ASK_TITLE").toString() +") 열람";
		Map logParam = new HashMap();
		logParam.put("point_code", "POINT01");
		logParam.put("action", "R");

		logParam.put("data_num", vo.get("WRITER").toString() + "회원님의 정보를 열람 하셨습니다.");
		logParam.put("data_url", request.getRequestURL().toString()+"?ask_uid="+param.get("ask_uid").toString());
		logParam.put("ins_ip", request.getRemoteAddr().toString());	
		logParam.put("ins_admin_id", admin.get("SESSION_ADMIN_ID"));	
		logParam.put("admin_nm", admin.get("SESSION_ADMIN_NM"));	
		logParam.put("admin_uid", admin.get("SESSION_ADMIN_UID"));	
		logParam.put("access_menu_uid", "1201");	
		logParam.put("etc", etc);			
		super.InsContentsLog(logParam);
		//############################################################			
		
		return "/admin/member/member_cs_view";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/admin/member/member_cs_update.af")
	public String member_cs_update(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);

		String sMenuCode = "1201";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null; 		
		

		param = Util.chkParam(pageParamList, param);

		String re_id = "admin";
		param.put("re_id", re_id);
		customerService.customerUpdate(param);
		
		
		Map vo = memberService.memberDetail(param); 
		

		String action = "U";
		int data_num = Integer.parseInt(param.get("ask_uid").toString());
		String httpName = request.getRequestURL().toString().substring(0, request.getRequestURL().toString().indexOf(request.getServletPath()));
		String data_url = httpName + "/secu_admaf/admin/member/member_cs_view.af?ask_uid="+data_num;
		String etcContent = "�닔�젙";
		String etc = param.get("ask_uid").toString()+"("+ param.get("mem_uid").toString() +") " + etcContent ;
		
		Map logParam = new HashMap();
		logParam.put("point_code", "POINT01");
		logParam.put("action", action);

		logParam.put("data_num", vo.get("MEM_NM").toString() + "회원님의 고객문의에 답변을 등록 하셨습니다.");
		logParam.put("data_url", data_url);
		logParam.put("ins_ip", request.getRemoteAddr().toString());	
		logParam.put("ins_admin_id", admin.get("SESSION_ADMIN_ID"));	
		logParam.put("admin_nm", admin.get("SESSION_ADMIN_NM"));	
		logParam.put("admin_uid", admin.get("SESSION_ADMIN_UID"));	
		logParam.put("access_menu_uid", "1201");	
		logParam.put("etc", etc);			
		super.InsContentsLog(logParam);
		//############################################################			
			
		String meme_uid = (String)param.get("mem_uid");
		return "redirect:/secu_admaf/admin/member/member_cs_list.af?result=1&mem_uid=" +meme_uid;
	}
	
	protected String[] pageParamListRES = {"page","reserve_uid","category","mem_nm","mob_no", "reserve_state", "srch_reg_s", "srch_reg_e", "mem_uid","noPmtYN","point_code"};
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/admin/member/member_res.af")
	public String member_res(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);

		String sMenuCode = "1201";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null; 
		
		if(admin.get("SESSION_ADMIN_ID").indexOf("oktomato.net") != -1){
			param.put("noPmtYN", "Y");
		}
		
		int page = 0;

		param = Util.chkParam(pageParamListRES, param);
		

		page = (param.get("page") == null || "".equals(param.get("page").toString() ) ) ?  page = 1 : Util.getInt(param.get("page").toString());
		int totCnt = reservationService.reservationListCnt(param);
		PageUtil paging = new PageUtil(page, totCnt, pageListSize, blockListSize);
		param.put("pageStartRow", paging.getPageStartRow());
		param.put("pageListSize", pageListSize);
		param.put("page", String.valueOf(page));
		
		param = Util.setMapHex(param);		
		List<Map> rList = reservationService.reservationList(param);

		List codeRSRVT_TYPE = super.getCommonCodes("RSRVT_TYPE");

		List codePOINT_CODE = super.getCommonCodes("POINT_CODE");
		

		model.addAttribute("resultParam", Util.pageParamMap2(pageParamListRES, param) );
		model.addAttribute("totalCount", Util.numberFormat(totCnt));
		model.addAttribute("pu", paging);
		model.addAttribute("rList", rList);
		model.addAttribute("codeRSRVT_TYPE", codeRSRVT_TYPE);
		model.addAttribute("codePOINT_CODE", codePOINT_CODE);

		return "/admin/member/member_res";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/admin/member/ajax_res_view.af")
	public String ajax_res_view(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		param = Util.chkParam(pageParamListRES, param);
		
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		
		Map vo = reservationService.reservationDetail(param);
		
		String r_TYPE = "";
 		if(vo.get("PAYMENT_TYPE").toString().trim().equals("NODATA")){
 			r_TYPE = "예약대기";
 		}else{
 			String payMethod = vo.get("PAYMENT_TYPE").toString();
 			//int compareVal = Integer.parseInt(vo.get("PAYMENT_TYPE").toString().substring(0,1));		
			switch (payMethod) {
			case "credit": r_TYPE = "카드";
				break;
			case "bank": r_TYPE = "실시간계좌이체";
				break;
			case "ssg": r_TYPE = "SSG PAY";
				break;				
			}
 		}
 		vo.put("r_TYPE", r_TYPE);
 		
		model.addAttribute("resultParam", Util.pageParamMap2(pageParamListRES, param) );
		model.addAttribute("vo", vo);
		model.addAttribute("MEMINFOYN", admin.get("SESSION_MEMINFOYN").toString());
		

		String etc = vo.get("ORDER_NUM").toString()+"("+ vo.get("ORDER_NM").toString() +") 열람";
		Map logParam = new HashMap();
		logParam.put("point_code", "POINT01");
		logParam.put("action", "R");

		logParam.put("data_num", vo.get("MEM_NM").toString() + "회원님의 예약정보를 열람하셨습니다.");
		logParam.put("data_url", "javascript: ajaxShowPopCont({ url : '"+request.getRequestURL().toString()+"' ,data : { reserve_uid : "+param.get("reserve_uid").toString()+"} });");
		logParam.put("ins_ip", request.getRemoteAddr().toString());	
		logParam.put("ins_admin_id", admin.get("SESSION_ADMIN_ID"));	
		logParam.put("admin_nm", admin.get("SESSION_ADMIN_NM"));	
		logParam.put("admin_uid", admin.get("SESSION_ADMIN_UID"));	
		logParam.put("access_menu_uid", "1201");	
		logParam.put("etc", etc);			
		super.InsContentsLog(logParam);
		//############################################################			
		
		return "adminPop/member/ajax_res_view";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/admin/member/inactivity.af")
	public String inactivity(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);

		String sMenuCode = "1201";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null; 		
		
		int page = 0;

		param = Util.chkParam(pageParamList, param);
		

		page = (param.get("page") == null || "".equals(param.get("page").toString() ) ) ?  page = 1 : Util.getInt(param.get("page").toString());
		if(page < 1){
			page = 1;
			param.put("page",String.valueOf(page));
		}

		param = Util.setMapHex(param);
		
		int totCnt = memberService.inactMemberListCnt(param);
		PageUtil paging = new PageUtil(page, totCnt, pageListSize, blockListSize);
		param.put("pageStartRow", paging.getPageStartRow());
		param.put("pageListSize", pageListSize);
		param.put("page", String.valueOf(page));
		
		param.put("srch_reg_s", param.get("srch_reg_s"));
		param.put("srch_reg_e", param.get("srch_reg_e"));
		
		List<Map> mList = memberService.inactMemberList(param);
		

		model.addAttribute("resultParam", param);
		model.addAttribute("totalCount", Util.numberFormat(totCnt));
		model.addAttribute("pu", paging);
		model.addAttribute("mList", mList);
		model.addAttribute("MEMINFOYN", admin.get("SESSION_MEMINFOYN").toString());
		
		return "/admin/member/inactivity_member";
	}
	
	/*
	 *  Excel Down
	 */
	@RequestMapping(value="/excel/member/excelDownload.af")
	 public String memExcel(@RequestParam Map param, ModelMap model, HttpServletRequest request, HttpServletResponse response ) throws Exception {
		List<Object> header = new ArrayList<Object>();		
		List<List<Object>> data = new ArrayList<List<Object>>();		
	
		int page = 0;

		param = Util.chkParam(pageParamList, param);
		

		param = Util.setMapHex(param);
		
		int totCnt = memberService.memberListCnt(param);
		PageUtil paging = new PageUtil(page, totCnt, pageListSize, blockListSize);
		param.put("pageStartRow", paging.getPageStartRow());
		param.put("pageListSize", totCnt);
		param.put("page", String.valueOf("1"));

		List<Map> mList = memberService.memberList(param);
		
		try{

			header.add("회원번호");
			header.add("회원명");
			header.add("ID");
			header.add("휴대폰번호");
			header.add("성별");
			header.add("생년월일");
			
			header.add("예약내역");
			header.add("가입일");
			
			String answerStat = "";
			String[] elapseTime = null;
			
			for(Map map : mList){
				List<Object> obj = new ArrayList<Object>();
				obj.add(map.get("MEM_NUM") );
				obj.add(map.get("MEM_NM") );
				obj.add(map.get("MEM_ID") );
				obj.add(map.get("MOBILE_NUM"));
				
				String gender = "남자";
				if(!"M".equals(map.get("MOBILE_NUM").toString())){gender = "여자";}
				
				obj.add(gender);
				obj.add(map.get("MEM_BIRTH"));
				obj.add(map.get("RESERVE_CNT"));
				obj.add(map.get("INS_DATE"));					
				
				data.add(obj);
			}
			
			model.addAttribute("header", header);
			model.addAttribute("list", data);
			model.addAttribute("fileName", Util.encodeString("회원정보") + "_" + Util.getTodayTime() + ".xls");
		}
		catch(Exception e){
			logger.debug(e.toString());
			Util.htmlPrint("N", response);
			return null;
		}

	    return "excelDownloadView";
	}	
}