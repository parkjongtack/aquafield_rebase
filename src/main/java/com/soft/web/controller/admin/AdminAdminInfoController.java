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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soft.web.base.CommonConstant;
import com.soft.web.base.GenericController;
import com.soft.web.service.admin.AdminAdminAuthService;
import com.soft.web.service.admin.AdminAdminInfoService;
import com.soft.web.service.admin.AdminAdminMenuService;
import com.soft.web.service.admin.AdminLoginService;
import com.soft.web.util.DecoderUtil;
import com.soft.web.util.ExcelUtil;
import com.soft.web.util.PageUtil;
import com.soft.web.util.Util;
@Controller
@RequestMapping({"/secu_admaf"})
public class AdminAdminInfoController extends GenericController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());	
	
	@Autowired
	AdminAdminInfoService adminAdminInfoService;
	
	@Autowired
	AdminAdminMenuService adminAdminMenuService;
	
	@Autowired
	AdminAdminAuthService adminAdminAuthService;

	@Autowired
	AdminLoginService adminLoginService;
	
	public String[] getPageParamList(){
		String[] pageParamList = {"num","page","nm","sd","ed","sw"};
		return pageParamList;
	}
	
	@RequestMapping(value = {"/admin/manager/manager_list.af"})
    public String managerList(@RequestParam Map param, ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//-- 왼쪽메뉴
		String sMenuCode = "1101";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null; 		
		
		int page = 0;
		int pageListSize = 20;
		int blockListSize = 10;
		
		//-- xss 체크
		param = Util.chkParam(this.getPageParamList(), param);
		
		page = (param.get("page") == null || "".equals(param.get("page").toString() ) ) ?  1 : Util.getInt(param.get("page").toString());
		if(page < 1){
			page = 1;
			param.put("page",String.valueOf(page));
		}
		
		//-- 16진수 변경
		param = Util.setMapHex(param);
		
		int totalCount = adminAdminInfoService.adminInfoGetCount(param);
		
		
		PageUtil pu = new PageUtil(page, totalCount, pageListSize, blockListSize);

		param.put("page", String.valueOf(page));
		param.put("rows", pageListSize);
		if(param.get("sd") != null && param.get("ed") !=null){
			param.put("startd", param.get("sd").toString().replace(".", ""));
			param.put("endd", param.get("ed").toString().replace(".", ""));
		}
		
		model.addAttribute("resultParam", param);
		model.addAttribute("totalCount", Util.numberFormat(totalCount));
		model.addAttribute("pu", pu);
 		model.addAttribute("results", adminAdminInfoService.adminInfoList(param));
 
 		return "/admin/manager/manager_list";
    }
	
	/*
	 * 20180514 syw
	 * 운영관리자 - 상세보기
	 */
	@RequestMapping(value = {"/admin/manager/operations_manager_view.af"})
    public String operationsManagerView(@RequestParam Map param, ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//-- 왼쪽메뉴 
		String sMenuCode = "1101";
		String etc = "";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null; 		
		
		//-- xss 체크
		param = Util.chkParam(this.getPageParamList(), param);

		model.put("resultParam", param);
		if(param.get("num") != null && !"".equals(param.get("num").toString() ) ){
			param.put("ADMIN_UID",param.get("num"));			
			//-- 기본정보
			Map result = adminAdminInfoService.adminInfoDetail(param);
			if(result == null || result.size() == 0){
				Util.htmlPrint(Util.historyBack("등록되지 않았거나 삭제된 자료 입니다."), response);
				return null;
			} 
			etc = result.get("ADMIN_NM").toString()+"("+ result.get("ADMIN_ID").toString() +") 열람";
			
			model.addAttribute("result", result);
			//-- 권한
			model.addAttribute("resultsAdminAuth", adminAdminAuthService.adminAuthList(param));
			
			//관리자 사용기록 로그 #############################################
			Map logParam = new HashMap();
			logParam.put("point_code", "POINT01");
			logParam.put("action", "R");
			/* 20180315  수정
			 * logParam.put("data_num", param.get("num"));*/
			/*logParam.put("data_num", "이름 : " + result.get("ADMIN_NM").toString() + ", 아이디 : "+result.get("ADMIN_ID").toString());*/
			logParam.put("data_num", result.get("ADMIN_NM").toString()  + "님의 정보를 열람하셨습니다.");
			logParam.put("data_url", request.getRequestURL().toString()+"?num="+param.get("num").toString());
			logParam.put("ins_ip", request.getRemoteAddr().toString());	
			logParam.put("ins_admin_id", admin.get("SESSION_ADMIN_ID"));	
			logParam.put("admin_nm", admin.get("SESSION_ADMIN_NM"));	
			logParam.put("admin_uid", admin.get("SESSION_ADMIN_UID"));	
			logParam.put("access_menu_uid", "1101");	
			logParam.put("etc", etc);			
			super.InsContentsLog(logParam);
			//############################################################			
		}
		else{
			Util.htmlPrint(Util.historyBack("등록되지 않았거나 삭제된 자료 입니다."), response);
			return null;
		}
		//-- 메뉴
		model.addAttribute("resultsAdminMenu", adminAdminMenuService.adminMenuListAll(param));		
		model.addAttribute("resultParam", param);
			
		return "/admin/manager/operations_manager_view";
		
    }

	@RequestMapping(value = {"/admin/manager/operations_manager_write.af"})
    public String operationsManagerWrite(@RequestParam Map param, ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//-- 왼쪽메뉴
		String sMenuCode = "1101";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null; 		
		
		//-- xss 체크
		param = Util.chkParam(this.getPageParamList(), param);

		model.put("resultParam", param);
		if(param.get("num") != null && !"".equals(param.get("num").toString() ) ){
			param.put("ADMIN_UID",param.get("num"));			
			//-- 기본정보
			model.addAttribute("result", adminAdminInfoService.adminInfoDetail(param));
			//-- 권한
			model.addAttribute("resultsAdminAuth", adminAdminAuthService.adminAuthList(param));
		}
		//-- 메뉴
		model.addAttribute("resultsAdminMenu", adminAdminMenuService.adminMenuListAll(param));
		model.addAttribute("resultParam", param);
		
		return "/admin/manager/operations_manager_write";
		
    }
	
	/*
	 * 20180514 syw
	 * 운영관리자 수정
	 */
	@RequestMapping(value = "/admin/manager/operations_manager_write_action.af", method=RequestMethod.POST)
    public void operationsManagerWriteAction(
    		@RequestParam(value="MENU_CODE", defaultValue="") List<String> listMenuCode
    		, @RequestParam Map param, ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//-- 왼쪽메뉴
		String sMenuCode = "1101";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return; 		

		param.put("INS_IP",request.getRemoteAddr());
		param.put("INS_ADMIN_ID",admin.get("SESSION_ADMIN_ID"));
		param.put("UPD_IP",request.getRemoteAddr());
		param.put("UPD_ADMIN_ID",admin.get("SESSION_ADMIN_ID"));
		param.put("ADMIN_NM",DecoderUtil.decode(param, "ADMIN_NM"));
		param.put("ADMIN_DEPT",DecoderUtil.decode(param, "ADMIN_DEPT"));
		param.put("ADMIN_ID",DecoderUtil.decode(param, "ADMIN_ID"));
		param.put("ADMIN_PW",DecoderUtil.decode(param, "ADMIN_PW"));
		
		//-- 16진수 변경
		param = Util.setMapHex(param);
		try{
			String result = adminAdminInfoService.operationsManagerWriteAction(listMenuCode, param);
			Util.htmlPrint(result, response);
			
			//관리자 사용기록 로그 #############################################
			String action = "inst".equals(param.get("mode").toString()) ? "C" : "U";
			int data_num = "C".equals(action) ? adminAdminInfoService.adminInfoGetMaxUid(param) : Integer.parseInt(param.get("num").toString());
			String httpName = request.getRequestURL().toString().substring(0, request.getRequestURL().toString().indexOf(request.getServletPath()));
			String data_url = httpName + "/secu_admaf/admin/manager/operations_manager_view.af?num="+data_num;
			String etcContent = "C".equals(action) ? "생성" : "수정";
			String etc = param.get("ADMIN_NM").toString()+"("+ param.get("ADMIN_ID").toString() +") " + etcContent ;
			String mode = param.get("mode").toString();
			/*param.put("ADMIN_UID", param.get("num"));
			
			Map result1 = adminAdminInfoService.adminInfoDetail(param);
			*/
			Map logParam = new HashMap();
			logParam.put("point_code", "POINT01");
			logParam.put("action", action);
			/* 20180316 수정*/
			if(mode.equals("inst")) {
				logParam.put("data_num", param.get("ADMIN_NM").toString() + "님을 등록 하셨습니다.");
			} else {
				logParam.put("data_num", param.get("ADMIN_NM").toString() + "님의 정보를 수정 하셨습니다.");
			}
			
			logParam.put("data_url", data_url);
			logParam.put("ins_ip", request.getRemoteAddr().toString());	
			logParam.put("ins_admin_id", admin.get("SESSION_ADMIN_ID"));	
			logParam.put("admin_nm", admin.get("SESSION_ADMIN_NM"));	
			logParam.put("admin_uid", admin.get("SESSION_ADMIN_UID"));	
			logParam.put("access_menu_uid", "1101");	
			logParam.put("etc", etc);			
			super.InsContentsLog(logParam);
			//############################################################			
		}
		catch(Exception e){
			logger.debug("operationsManagerWriteAction : " + e.toString());
			Util.htmlPrint("{\"result\":false,\"msg\":\"오류가 발생했습니다. 잠시후에 다시 하세요.\"}", response);
		}
    }
	
	/*
	 * 20180514 syw
	 * 운영관리자 삭제
	 * 
	 */
	@RequestMapping(value = "/admin/manager/manager_delete_action", method=RequestMethod.POST)
    public void managerDeleteAction(@RequestParam Map param, ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//-- 왼쪽메뉴
		String sMenuCode = "1101";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return; 		
 		
		//-- xss 체크
		param = Util.chkParam(this.getPageParamList(), param);
		if(param.get("num") != null && !"".equals(param.get("num"))){
			param.put("num", Util.getInt(param.get("num").toString() ) );
		}
		else{
			param.put("num", "0");
		}

		try{
			//관리자 사용기록 로그 #############################################
			String action = "D";
			int data_num = Integer.parseInt(param.get("num").toString());
			String httpName = request.getRequestURL().toString().substring(0, request.getRequestURL().toString().indexOf(request.getServletPath()));
			String data_url = "";
			String etcContent = "삭제";
			String etc = param.get("nm").toString()+"("+ param.get("id").toString() +") " + etcContent ;
			
			param.put("ADMIN_UID", param.get("num"));
			
			Map result1 = adminAdminInfoService.adminInfoDetail(param);
			
			
			Map logParam = new HashMap();
			logParam.put("point_code", "POINT01");
			logParam.put("action", action);
			/* 20180315 수정
			 * logParam.put("data_num", data_num);*/
			logParam.put("data_num", result1.get("ADMIN_NM").toString() + "님의 정보를 삭제 하였습니다.");
			logParam.put("data_url", data_url);
			logParam.put("ins_ip", request.getRemoteAddr().toString());	
			logParam.put("ins_admin_id", admin.get("SESSION_ADMIN_ID"));	
			logParam.put("admin_nm", admin.get("SESSION_ADMIN_NM"));	
			logParam.put("admin_uid", admin.get("SESSION_ADMIN_UID"));	
			logParam.put("access_menu_uid", "1101");	
			logParam.put("etc", etc);			
			super.InsContentsLog(logParam);
			//############################################################				
			
			String result = adminAdminInfoService.managerDeleteAction(param);
			Util.htmlPrint(result, response);
		}
		catch(Exception e){
			Util.htmlPrint("{\"result\":false,\"msg\":\"오류가 발생했습니다. 잠시후에 다시 하세요.\"}", response);
		}
	}	

	
	@RequestMapping(value = {"/admin/ajax_edit_my_profile.af"})
    public String ajaxEditMyProfile(@RequestParam Map param, ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
 
 		return "/admin/ajax_edit_my_profile";
    }

	@RequestMapping(value = "/admin/manager/manager_password_action.af", method=RequestMethod.POST)
    public void managerPasswordAction(@RequestParam Map param, ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//-- 왼쪽메뉴
		String sMenuCode = "1101";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return; 		
		
		//-- xss 체크 DB 입력시
		//param = Util.chkDbParam(this.getPageParamList(), param);

		param.put("ADMIN_ID",admin.get("SESSION_ADMIN_ID"));
		param.put("INS_IP",request.getRemoteAddr());
		param.put("INS_ADMIN_ID",admin.get("SESSION_ADMIN_ID"));
		param.put("UPD_IP",request.getRemoteAddr());
		param.put("UPD_ADMIN_ID",admin.get("SESSION_ADMIN_ID"));
		
		Map sqlMap = new HashMap();
		sqlMap.put("admin_id",admin.get("SESSION_ADMIN_ID"));
		sqlMap.put("admin_pw",param.get("orgAdminPw"));
		try{
			//-- 기존 비밀번호 확인 
			if(adminLoginService.adminLogin(sqlMap) == null){
				Util.htmlPrint("{\"result\":false,\"msg\":\"비밀번호를 확인하세요.\"}", response);
			}
			else{
				//-- 암호변경
				adminAdminInfoService.adminInfoPasswordUpdate(param);
				Util.htmlPrint("{\"result\":true,\"msg\":\"비밀번호가 변경되었습니다.\"}", response);
			}
		}
		catch(Exception e){
			Util.htmlPrint("{\"result\":false,\"msg\":\"오류가 발생했습니다. 잠시후에 다시 하세요.\"}", response);;
		}
	}
	
	@RequestMapping(value = {"/admin/manager/history.af"})
    public String history(@RequestParam Map param, ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//-- 왼쪽메뉴
		String sMenuCode = "1101";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null; 		
		
		int page = 0;
		int pageListSize = 10;
		int blockListSize = 10;
		
		//-- xss 체크
		param = Util.chkParam(this.getPageParamList(), param);
		
		page = (param.get("page") == null || "".equals(param.get("page").toString() ) ) ?  1 : Util.getInt(param.get("page").toString());
		if(page < 1){
			page = 1;
		}
		
		//-- 16진수 변경
		param = Util.setMapHex(param);
		
		int totCnt = adminAdminInfoService.adminUseLogCnt(param);
		PageUtil paging = new PageUtil(page, totCnt, pageListSize, blockListSize);
		param.put("pageStartRow", paging.getPageStartRow());
		param.put("pageListSize", pageListSize);
		param.put("page", String.valueOf(page));
		
		List<Map> logList = adminAdminInfoService.adminUseLogList(param);
		
		//코드 조회
		List actionList = super.getCommonCodes("ACTION");
		//1차메뉴 리스트
		List<Map> menuList = adminAdminInfoService.adminOnedepthMenuList();
		
		model.addAttribute("resultParam", param);
		model.addAttribute("totalCount", Util.numberFormat(totCnt));
		model.addAttribute("pu", paging);
 		model.addAttribute("results", logList);
 		model.addAttribute("actionList", actionList); 		
 		model.addAttribute("menuList", menuList); 
 		
 		return "/admin/manager/history";
    }
	
	
	/*
	 * 검색값에 의한 엑셀 다운로드
	 */
	@RequestMapping("/admin/manager/history_exel.af")
	public @ResponseBody byte[] res_excel_down(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response ) throws UnsupportedEncodingException{
		 
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		String memInfoYN = admin.get("SESSION_MEMINFOYN").toString();	
		List<Object> header = new ArrayList<Object>();		
		List<List<Object>> data = new ArrayList<List<Object>>();		
		int i = 0;
		//-- 16진수 변경
		param = Util.setMapHex(param);
		
		//-- 엑셀용 페이지 없는 목록!!
		List<Map> logList = adminAdminInfoService.adminUseLogListExel(param);
		
		try{
			//-- 제목 설정
			header.add("일 자");
			header.add("성 명");
			header.add("로그인IP");
			header.add("행 위");
			header.add("비 고");
			
			for(Map map : logList){
				List<Object> obj = new ArrayList<Object>();
				obj.add(map.get("INS_DATE"));
				obj.add(map.get("ADMIN_NM"));
				obj.add(map.get("INS_IP"));
				
				if(map.get("ACTION").equals("C")) {
					obj.add("생성");
				} else if(map.get("ACTION").equals("R")) {
					obj.add("열람");
				} else if(map.get("ACTION").equals("U")) {
					obj.add("수정");
				} else if(map.get("ACTION").equals("D")) {
					obj.add("삭제");
				} else {
					obj.add("");
				}
				
				obj.add(map.get("ETC"));
				
				data.add(obj);
			}
			
			ExcelUtil excelUtil = new ExcelUtil();
			byte[] bytes = excelUtil.makeXlsx(header, data);
			response.setHeader("Content-Disposition", "attachment; filename=" + Util.encodeString("로그기록") + "_" + Util.getTodayTime() + ".xlsx");
			response.setContentLength(bytes.length);
			response.setContentType("application/vnd.ms-excel");
			
			return bytes;
		}
		catch(Exception e){
			logger.debug(e.toString());
			return null;
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
