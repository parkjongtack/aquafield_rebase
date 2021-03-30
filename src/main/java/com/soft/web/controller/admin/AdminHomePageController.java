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
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.soft.web.base.CommonConstant;
import com.soft.web.base.GenericController;
import com.soft.web.service.admin.AdminAdminAuthService;
import com.soft.web.service.admin.AdminAdminInfoService;
import com.soft.web.service.admin.AdminAdminMenuService;
import com.soft.web.service.admin.AdminTermsService;
import com.soft.web.service.admin.HomepageService;
import com.soft.web.util.DecoderUtil;
import com.soft.web.util.FileUpload;
import com.soft.web.util.PageUtil;
import com.soft.web.util.Util;

@Controller
@RequestMapping({"/secu_admaf"})
public class AdminHomePageController extends GenericController {
	
	protected Logger logger = LoggerFactory.getLogger(AdminHomePageController.class);
	
	@Autowired
	AdminAdminInfoService adminAdminInfoService;
	
	@Autowired
	AdminAdminMenuService adminAdminMenuService;
	
	@Autowired
	AdminAdminAuthService adminAdminAuthService;
	
	@Autowired
	AdminTermsService adminTermsService;
	
	@Autowired
	HomepageService homepageService;
	
	public String[] getPageParamList(){
		String[] pageParamList = {"num","page","tt"};
		return pageParamList;
	}

	/*
	 * 페이징 파라미터 모음
	 * page : 페이지, content : 내용, title : 제목 
	 */
	protected String[] pageParamList = {"page","title","content"};
	
	@RequestMapping(value="/admin/homepage/index.af")
	public String homepageAdminIndex(@RequestParam Map param, ModelMap model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//-- 왼쪽메뉴
		String sMenuCode = "1701";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null;
		
		int page = 0;
		int pageListSize = 50;
		int blockListSize = 10;
		
		param = Util.chkParam(this.getPageParamList(), param);
		
		page = (param.get("page") == null || "".equals(param.get("page").toString() ) ) ?  1 : Util.getInt(param.get("page").toString());
		if(page < 1) page = 1;
		
		if(param.get("tt") == null || "".equals(param.get("tt").toString() ) ) param.put("tt","AQUA_USE");
		
		model.addAttribute("resultsType",super.getCommonCodes("TERMS_TYPE") );

		int totalCount = adminTermsService.adminTermsListCnt(param);
		PageUtil pu = new PageUtil(page, totalCount, pageListSize, blockListSize);

		param.put("pageStartRow", pu.getPageStartRow());
		param.put("pageListSize", pageListSize);
		param.put("rows", pageListSize);
		param.put("page", String.valueOf(page));
		model.addAttribute("resultParam", Util.pageParamMap(this.getPageParamList(), param) );
		model.addAttribute("totalCount", Util.numberFormat(totalCount));
		model.addAttribute("pu", pu);
		model.addAttribute("results", adminTermsService.adminTermsList(param));


		
		return "/admin/homepage/index";
	}
	
	@RequestMapping(value="/admin/homepage/homepage.af")
	public String homepage(@RequestParam Map param, ModelMap model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//-- 왼쪽메뉴
		String sMenuCode = "1701";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null;
		
		return "/admin/homepage/homepage";
	}
	
	@RequestMapping(value="/admin/homepage/privacy.af")
	public String privacy(@RequestParam Map param, ModelMap model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//-- 왼쪽메뉴
		String sMenuCode = "1701";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null;
		
		return "/admin/homepage/privacy";
	}
	
	@RequestMapping(value="/admin/homepage/general_info.af")
	public String general_info(@RequestParam Map param, ModelMap model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//-- 왼쪽메뉴
		String sMenuCode = "1701";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null;
		
		return "/admin/homepage/general_info";
	}
	
	@RequestMapping(value="/admin/homepage/terms_write.af")
	public String terms_write(@RequestParam Map param, ModelMap model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//-- 왼쪽메뉴
		String sMenuCode = "1701";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null;
		
		Map<String,String> fileMap;
		String upload = "/common/upload/terms";
		String realPath = "";    //실제 경로 저장 변수
		realPath = super.getSession().getServletContext().getRealPath(upload);
		Util.makeDir(realPath);
		
		//-- xss 체크
		param = Util.chkParam(this.getPageParamList(), param);
		if(param.get("tt") == null || "".equals(param.get("tt").toString() ) ) param.put("tt","AQUA_USE");

		model.addAttribute("resultsType",super.getCommonCodes("TERMS_TYPE") );
		model.put("resultParam", param);
		
		if(param.get("num") != null && !"".equals(param.get("num").toString() ) ){
			//-- 상세정보
			model.addAttribute("result", adminTermsService.adminTermsDetail(param));
			model.addAttribute("resultContent", Util.getReadFile(realPath + "/" + param.get("num")));
		}
		model.addAttribute("resultParam", param);
		
		//관리자 사용기록 로그 #############################################		
		String contentName = "";
		if("AQUA_USE".equals(param.get("tt").toString())) contentName = "아쿠아필드 이용약관";
		if("HOMEPAGE_USE".equals(param.get("tt").toString())) contentName = "홈페이지 이용약관";
		if("PRIVACY".equals(param.get("tt").toString())) contentName = "개인정보 취급방침";
		if("GENERAL".equals(param.get("tt").toString())) contentName = "영상정보처기 기기운영";
		
		String etc = contentName+"("+ param.get("tt").toString() +") 열람";
		Map logParam = new HashMap();
		logParam.put("point_code", "POINT01");
		logParam.put("action", "R");
		/* 20180319 수정
		 * logParam.put("data_num", param.get("num"));*/
		logParam.put("data_num",  admin.get("SESSION_ADMIN_NM") + "님이" + contentName + "을 열람 하셨습니다.");
		logParam.put("data_url", request.getRequestURL().toString()+"?=tt"+param.get("tt").toString()+"&num="+param.get("num").toString());
		logParam.put("ins_ip", request.getRemoteAddr().toString());	
		logParam.put("ins_admin_id", admin.get("SESSION_ADMIN_ID"));	
		logParam.put("admin_nm", admin.get("SESSION_ADMIN_NM"));	
		logParam.put("admin_uid", admin.get("SESSION_ADMIN_UID"));	
		logParam.put("access_menu_uid", "1601");	
		logParam.put("etc", etc);			
		super.InsContentsLog(logParam);
		//############################################################			

		return "/admin/homepage/terms_write";
	}
	
	@RequestMapping(value = "/admin/homepage/terms_write_action.af", method=RequestMethod.POST)
    public String termsWriteAction(@RequestParam Map param, ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//-- 왼쪽메뉴
		String sMenuCode = "1701";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null;		

		Map<String,String> fileMap;
		String upload = "/common/upload/terms";
		String realPath = "";    //실제 경로 저장 변수
		realPath = super.getSession().getServletContext().getRealPath(upload);
		Util.makeDir(realPath);

		param.put("INS_IP",request.getRemoteAddr());
		param.put("INS_ID",admin.get("SESSION_ADMIN_ID"));
		param.put("UPD_IP",request.getRemoteAddr());
		param.put("UPD_ID",admin.get("SESSION_ADMIN_ID"));
		
		
		if(param.get("TERMS_TITLE") != null) param.put("TERMS_TITLE",DecoderUtil.decode(param, "TERMS_TITLE"));
		if(param.get("TERMS_CONTENT") != null) param.put("TERMS_CONTENT",DecoderUtil.decode(param, "TERMS_CONTENT"));
		
		//-- 16진수 변경
		param = Util.setMapHex(param);

		try{
			String action = "";
			
			if("inst".equals(param.get("mode").toString() ) ){
				adminTermsService.adminTermsInsert(param);
				int maxIdx = adminTermsService.adminTermsMaxUid();
				//-- 파일생성
				Util.writeFile(realPath + "/" + maxIdx, (String)param.get("TERMS_CONTENT"));
				
				Util.htmlPrint("{\"result\":true,\"msg\":\"등록 되었습니다.\"}", response);
				
				action = "C";
			}
			else{
				adminTermsService.adminTermsUpdate(param);
				//-- 파일생성
				Util.writeFile(realPath + "/" + param.get("num"), (String)param.get("TERMS_CONTENT"));
				Util.htmlPrint("{\"result\":true,\"msg\":\"수정 되었습니다.\"}", response);
				
				action = "U";
			}
			
			//관리자 사용기록 로그 #############################################
			int data_num = "C".equals(action) ? adminTermsService.adminTermsMaxUid() : Integer.parseInt(param.get("num").toString());
			String httpName = request.getRequestURL().toString().substring(0, request.getRequestURL().toString().indexOf(request.getServletPath()));
			String etcContent = "C".equals(action) ? "생성" : "수정";
			String contentName = "";
			if("AQUA_USE".equals(param.get("tt").toString())) contentName = "아쿠아필드 이용약관";
			if("HOMEPAGE_USE".equals(param.get("tt").toString())) contentName = "홈페이지 이용약관";
			if("PRIVACY".equals(param.get("tt").toString())) contentName = "개인정보 취급방침";
			if("GENERAL".equals(param.get("tt").toString())) contentName = "영상정보처기 기기운영";
			
			String etc = contentName+"("+ param.get("tt").toString() +") " + etcContent ;
			String data_url = httpName + "/secu_admaf/admin/homepage/terms_write.af?tt="+param.get("tt").toString()+"&num="+data_num;
			
			Map logParam = new HashMap();
			logParam.put("point_code", "POINT01");
			logParam.put("action", action);
			/* 20180319 수정
			 * logParam.put("data_num", data_num);*/
			if(etcContent.equals("생성")) {
				logParam.put("data_num", admin.get("SESSION_ADMIN_NM") + "님이  " + contentName + "을 등록 하셨습니다.");
			} else {
				logParam.put("data_num", admin.get("SESSION_ADMIN_NM") + "님이  " + contentName + "을 수정 하셨습니다.");
			}
			
			logParam.put("data_url", data_url);
			logParam.put("ins_ip", request.getRemoteAddr().toString());	
			logParam.put("ins_admin_id", admin.get("SESSION_ADMIN_ID"));	
			logParam.put("admin_nm", admin.get("SESSION_ADMIN_NM"));	
			logParam.put("admin_uid", admin.get("SESSION_ADMIN_UID"));	
			logParam.put("access_menu_uid", "1701");	
			logParam.put("etc", etc);			
			super.InsContentsLog(logParam);
			//############################################################				
			
		}
		catch(Exception e){
			logger.debug("termsWriteAction : " + e.toString());
			Util.htmlPrint("{\"result\":false,\"msg\":\"오류가 발생했습니다. 잠시후에 다시 하세요.\"}", response);
		}
		return null;//"redirect:/admin/homepage/index.af";
    }	
	
	@RequestMapping(value = "/admin/homepage/terms_delete_action.af", method=RequestMethod.POST)
    public void managerDeleteAction(@RequestParam Map param, ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
 		
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
			String contentName = "";
			if("AQUA_USE".equals(param.get("tt").toString())) contentName = "아쿠아필드 이용약관";
			if("HOMEPAGE_USE".equals(param.get("tt").toString())) contentName = "홈페이지 이용약관";
			if("PRIVACY".equals(param.get("tt").toString())) contentName = "개인정보 취급방침";
			if("GENERAL".equals(param.get("tt").toString())) contentName = "영상정보처기 기기운영";			
			
			String etc = contentName + "("+ param.get("title").toString()+") " + etcContent ;
			
			Map logParam = new HashMap();
			logParam.put("point_code", "POINT01");
			logParam.put("action", action);
			/* 20180319 수정
			 * logParam.put("data_num", data_num);*/
			logParam.put("data_num", admin.get("SESSION_ADMIN_NM") + "님이  " + contentName + "을 삭제 하였습니다.");
			logParam.put("data_url", data_url);
			logParam.put("ins_ip", request.getRemoteAddr().toString());	
			logParam.put("ins_admin_id", admin.get("SESSION_ADMIN_ID"));	
			logParam.put("admin_nm", admin.get("SESSION_ADMIN_NM"));	
			logParam.put("admin_uid", admin.get("SESSION_ADMIN_UID"));	
			logParam.put("access_menu_uid", "1701");	
			logParam.put("etc", etc);			
			super.InsContentsLog(logParam);
			//############################################################				
			
			adminTermsService.adminTermsDelete(param);
			Util.htmlPrint("{\"result\":true,\"msg\":\"삭제 되었습니다.\"}", response);
		}
		catch(Exception e){
			Util.htmlPrint("{\"result\":false,\"msg\":\"오류가 발생했습니다. 잠시후에 다시 하세요.\"}", response);
		}
	}	
	
	//홈페이지 관리 - 팝업 관리
	@RequestMapping(value="/admin/homepage/popupIndex.af")
	public String popupIndex(@RequestParam Map param, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{

		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//왼쪽 메뉴
		String sMenuCode = "1701";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null; 		

		
		int pageListSize = 20;
		int blockListSize = 10;
		
		//전체 주문 데이터 가져오기
		int totCnt = homepageService.popupListCnt(param);
		
		int page = 0;
		//-- xss 체크
		param = Util.chkParam(pageParamList, param);

		//페이징
		page = (param.get("page") == null || "".equals(param.get("page").toString() ) ) ?  page = 1 : Util.getInt(param.get("page").toString());
		if(page < 1){
			page = 1;
			param.put("page",String.valueOf(page));
		}
		
		PageUtil paging = new PageUtil(page, totCnt, pageListSize, blockListSize);
		param.put("pageStartRow", paging.getPageStartRow());
		param.put("pageListSize", pageListSize);
		param.put("page", String.valueOf(page));
		
		//목록조회	
		List<Map> pList = homepageService.popupList(param);
		model.addAttribute("totCnt", totCnt);
		model.addAttribute("page", page);
		model.addAttribute("pList", pList);
		
		return "/admin/homepage/popup_index";
	}
	
	//홈페이지 관리 - 팝업 관리
	@RequestMapping(value="/admin/homepage/popup_regist.af")
	public String popup_register(@RequestParam Map param, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{

		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//왼쪽 메뉴
		String sMenuCode = "1701";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null; 		
		
		return "/admin/homepage/popup_regist";
	}
	
	@RequestMapping(value = "/admin/homepage/popupWriteAction.af", method=RequestMethod.POST)
    public void popupInstAction(@RequestParam Map param, ModelMap model, MultipartHttpServletRequest mRequest, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		Map<String,String> admdesk = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_DESK_ADMIN);		
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN) == null ? admdesk : (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		if(admin == null || admin.isEmpty()){
			Util.htmlPrint("{\"result\":false,\"msg\":\"로그인 정보가 없습니다.\"}", response);
			return;
		}
		
		if (param.get("left_m") == null || param.get("left_m").equals("") || param.get("left_m").equals("null")) {
			param.put("left_m", 0);
		}
		if (param.get("top_m") == null || param.get("top_m").equals("") || param.get("top_m").equals("null")) {
			param.put("top_m", 0);
		}
		if (param.get("width") == null || param.get("width").equals("") || param.get("width").equals("null")) {
			param.put("width", 0);
		}
		if (param.get("height") == null || param.get("height").equals("") || param.get("height").equals("null")) {
			param.put("height", 0);
		}
		if (param.get("print_order") == null || param.get("print_order").equals("") || param.get("print_order").equals("null")) {
			param.put("print_order", 0);
		}
		
		Map<String,String> fileMap;
		String upload = "/common/upload/popup/";
		String realPath = "";    //실제 경로 저장 변수
		realPath = super.getSession().getServletContext().getRealPath(upload);

		param.put("ins_ip",request.getRemoteAddr());
		param.put("ins_id",admin.get("SESSION_ADMIN_ID"));
		param.put("upd_ip",request.getRemoteAddr());
		param.put("upd_id",admin.get("SESSION_ADMIN_ID"));

		param.put("content", param.get("pop_contents"));
		
		//-- 파일 업로드
		fileMap = FileUpload.fileUpload(mRequest, realPath, upload, "file_content");
		if(!(fileMap == null || fileMap.get("FILE_NAME") == null || fileMap.get("FILE_NAME").toString().equals("") ) ){
			Map<String,String> fileParam = new HashMap<String,String>();
			param.put("ori_file_name",fileMap.get("FILE_NAME"));
			param.put("save_file_name",fileMap.get("FILE_UPNAME"));
			param.put("file_path", realPath);
		}
		else{
			param.put("ori_file_name","");
			param.put("save_file_name","");
			param.put("file_path","");
		}
		
		//-- 16진수 변경
		//param = Util.setMapHex(param);
		try{
			String mode = (String) param.get("mode");
			//-- 등록
			if("inst".equals(mode) ){
				//param.put("NEWS_UID"	,homepageService.newsMaxUid() + 1);
				homepageService.popInst(param);
				Util.htmlPrint("{\"result\":true,\"msg\":\"등록되었습니다.\"}", response);
			}
			//-- 수정
			else if("updt".equals(param.get("mode").toString() ) ){
				if(param.get("num") != null && !"".equals(param.get("num"))){
					param.put("num", Util.getInt(param.get("num").toString() ) );
				}
				else{
					Util.htmlPrint("{result:false,msg:\"필수값이 없습니다.\"}", response);
					return;
				}
				//homepageService.popUpd(param);
				Util.htmlPrint("{\"result\":true,\"msg\":\"수정되었습니다.\"}", response);
			}
		}
		catch(Exception e){
			Util.htmlPrint("{\"result\":false,\"msg\":\"오류가 발생되었습니다.\"}", response);
		}
	}

	//홈페이지 관리 - 팝업 수정페이지
	@RequestMapping(value="/admin/homepage/popup_modify.af")
	public String popup_modify(@RequestParam Map param, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{

		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//왼쪽 메뉴
		String sMenuCode = "1701";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null; 		
		
		return "/admin/homepage/popup_modify";
	}
	
}
