package com.soft.web.controller.admin;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soft.web.base.CommonConstant;
import com.soft.web.base.GenericController;
import com.soft.web.service.admin.AdminAdminAuthService;
import com.soft.web.service.admin.ReservationService;
import com.soft.web.service.front.FrontReservationService;
import com.soft.web.util.ExcelUtil;
import com.soft.web.util.PageUtil;
import com.soft.web.util.Util;

@Controller
@RequestMapping({"/secu_admaf"})
public class AdminReserVationController extends GenericController {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	//paging
	int pageListSize = 10;
	int blockListSize = 10;
	
    @Resource(name="config")
    private Properties config;
	//PG STORE CODE
	//private String pg_store_code = "2001106041";//<!-- real 상점코드 2001106041 --> <!-- test 상정코드  2999199999-->
	
	@Autowired
	ReservationService reservationService;
	
	@Autowired
	FrontReservationService frontReservationService;
	
	@Autowired
	AdminAdminAuthService adminAdminAuthService;
	
	/*
	 * 예약내역 파라미터 모음
	 * page : 페이지, reserve_uid : 예약번호, category : 카테고리, 
	 * mem_nm : 회원명, mob_no : 회원전화번호, reserve_state : 예약상태, 
	 * srch_reg_s : 방문일 시작, srch_reg_e : 방문일 종료, mem_uid : 회원고유번호
	 */
	protected String[] pageParamList = {"page","reserve_uid","category","mem_nm","mob_no", "reserve_state", "srch_reg_s", "srch_reg_e", "mem_uid"};
		
	
	/*
	 * 예약내역 관리 목록
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/admin/reservation/index.af")
	public String index(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//왼쪽 메뉴
		String sMenuCode = "1301";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null; 
		
		if(admin.get("SESSION_ADMIN_ID").indexOf("oktomato.net") != -1){
			param.put("noPmtYN", "Y");
		}
		
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
		
		int totCnt = reservationService.reservationListCnt(param);
		PageUtil paging = new PageUtil(page, totCnt, pageListSize, blockListSize);
		param.put("pageStartRow", paging.getPageStartRow());
		param.put("pageListSize", pageListSize);
		param.put("page", String.valueOf(page));
		
		//목록조회	
		List<Map> rList = reservationService.reservationList(param);
		//코드 조회
		List codeRSRVT_TYPE = super.getCommonCodes("RSRVT_TYPE");
		//지점 조회
		List codePOINT_CODE = super.getCommonCodes("POINT_CODE");
		
		//초기 진입 여부 체크
		if(param.get("first") != null){
			model.addAttribute("first", param.get("first").toString());	
		}
		// Util.pageParamMap2 는 post 방식용, Util.pageParamMap 는 get 방식용 (URLEncoder 들어가있음)
		model.addAttribute("resultParam", param);
		model.addAttribute("totalCount", Util.numberFormat(totCnt));
		model.addAttribute("pu", paging);
		model.addAttribute("rList", rList);
		model.addAttribute("codeRSRVT_TYPE", codeRSRVT_TYPE);
		model.addAttribute("codePOINT_CODE", codePOINT_CODE);
 		model.addAttribute("MEMINFOYN", admin.get("SESSION_MEMINFOYN").toString());
 		
		return "/admin/reservation/index";
	}
	
	/*
	 * 예약정보 바로보기 ajax
	 * jsp 를 return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/admin/reservation/ajax_res_view.af")
	public String ajax_res_view(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		
		//-- xss 체크
		param = Util.chkParam(pageParamList, param);
		
		Map vo = reservationService.reservationDetail(param);
		
		model.addAttribute("resultParam", Util.pageParamMap2(pageParamList, param) );
		model.addAttribute("vo", vo);
 		model.addAttribute("MEMINFOYN", admin.get("SESSION_MEMINFOYN").toString());
 		
		//관리자 사용기록 로그 #############################################
		String etc = vo.get("ORDER_NUM").toString()+"("+ vo.get("ORDER_NM").toString() +") 열람";;
		Map logParam = new HashMap();
		logParam.put("point_code", "POINT01");
		logParam.put("action", "R");
		/*logParam.put("data_num", param.get("reserve_uid"));*/
		logParam.put("data_num", vo.get("MEM_NM").toString() + "회원님의 예약정보를 열람하셨습니다.");
		logParam.put("data_url", "javascript: ajaxShowPopCont({ url : '"+request.getRequestURL().toString()+"' ,data : { reserve_uid : "+param.get("reserve_uid").toString()+"} });");
		logParam.put("ins_ip", request.getRemoteAddr().toString());	
		logParam.put("ins_admin_id", admin.get("SESSION_ADMIN_ID"));	
		logParam.put("admin_nm", admin.get("SESSION_ADMIN_NM"));	
		logParam.put("admin_uid", admin.get("SESSION_ADMIN_UID"));	
		logParam.put("access_menu_uid", "1301");	
		logParam.put("etc", etc);			
		super.InsContentsLog(logParam);
		//############################################################	
		
		return "adminPop/reservation/ajax_res_view";
	}
	
	/*
	 * 예약정보 바로보기팝업에서 사용처리 버튼 ajax
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/admin/reservation/resCompleteUse.af")
	public int resCompleteUse(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		
		//-- xss 체크
		param = Util.chkParam(pageParamList, param);
		
		param.put("upd_id", admin.get("SESSION_ADMIN_ID"));
		// 사용처리
		int result = reservationService.reservationUseChnge(param);
		
		//20180315 추가
		Map vo = reservationService.reservationDetail(param);
		
		
		//관리자 사용기록 로그 #############################################
		String etc = param.get("order_num").toString()+"("+ param.get("order_nm").toString() +") 예약 사용처리 수정";;
		Map logParam = new HashMap();
		logParam.put("point_code", "POINT01");
		logParam.put("action", "U");
		/*20180315 수정 확인하고 지우기.
		 * logParam.put("data_num", param.get("reserve_uid"));*/
		logParam.put("data_num", vo.get("MEM_NM").toString() + "회원님의 예약 사용처리 수정");
		logParam.put("data_url", "javascript: ajaxShowPopCont({ url : '"+request.getRequestURL().toString()+"' ,data : { reserve_uid : "+param.get("reserve_uid").toString()+"} });");
		logParam.put("ins_ip", request.getRemoteAddr().toString());	
		logParam.put("ins_admin_id", admin.get("SESSION_ADMIN_ID"));	
		logParam.put("admin_nm", admin.get("SESSION_ADMIN_NM"));	
		logParam.put("admin_uid", admin.get("SESSION_ADMIN_UID"));	
		logParam.put("access_menu_uid", "1301");	
		logParam.put("etc", etc);			
		super.InsContentsLog(logParam);
		//############################################################			
		
		return result;
	}
	
	/*
	 * 예약내역 수정 이동
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/admin/reservation/res_edit.af")
	public String res_edit(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//왼쪽 메뉴
		String sMenuCode = "1301";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null; 
		
		//-- xss 체크
		param = Util.chkParam(pageParamList, param);
		model.addAttribute("resultParam", Util.pageParamMap2(pageParamList, param) );
		
		Map vo = reservationService.reservationDetail(param);
		model.addAttribute("vo", vo);
				
		Map pgResultInfo = frontReservationService.pgResultInfo(vo.get("PG_RESULT").toString());

		String r_TYPE = "";		
		String authty = "";
		
		int compareVal = Integer.parseInt(pgResultInfo.get("TR_NO").toString().substring(0,1));		
		switch (compareVal) {
		case 1: r_TYPE = "카드";authty = "1010";
			break;
		case 2: r_TYPE = "실시간계좌이체";
			//당일 여부 체크
			Date toDay = new Date();
			SimpleDateFormat transFormat = new SimpleDateFormat("yyyy.MM.dd");
			String toDayToString = transFormat.format(toDay).trim();
				
			String pmtDate = vo.get("PAYMENT_DATE").toString().trim();
			
			if(toDayToString.equals(pmtDate)){
				authty = "2010";
			}else{
				authty = "2030";
			}
			logger.debug("############## 실시간계좌이체 취소 코드값>> authty : " + authty);
			break;
		case 4: r_TYPE = "SSG PAY";authty = "4110";
			break;				
		}		

		pgResultInfo.put("r_TYPE", r_TYPE);
		pgResultInfo.put("authty", authty);
		pgResultInfo.put("pgStore", config.getProperty("pg.store.code"));	
		model.addAttribute("pgResultInfo", pgResultInfo);
		
		//코드 조회
		List codeRSRVT_TYPE = super.getCommonCodes("RSRVT_TYPE");
		model.addAttribute("codeRSRVT_TYPE", codeRSRVT_TYPE);	
 		model.addAttribute("MEMINFOYN", admin.get("SESSION_MEMINFOYN").toString());//회원 on/off 관련
 		
		//관리자 사용기록 로그 #############################################
		String etc = vo.get("ORDER_NUM").toString()+"("+ vo.get("ORDER_NM").toString() +") 열람";;
		Map logParam = new HashMap();
		logParam.put("point_code", "POINT01");
		logParam.put("action", "R");
		/* 20180315 수정
		 * logParam.put("data_num", param.get("reserve_uid"));*/
		logParam.put("data_num", vo.get("MEM_NM").toString() + "회원님의 정보 수정페이지 열람");
		logParam.put("data_url", request.getRequestURL().toString()+"?reserve_uid="+param.get("reserve_uid").toString());
		logParam.put("ins_ip", request.getRemoteAddr().toString());	
		logParam.put("ins_admin_id", admin.get("SESSION_ADMIN_ID"));	
		logParam.put("admin_nm", admin.get("SESSION_ADMIN_NM"));	
		logParam.put("admin_uid", admin.get("SESSION_ADMIN_UID"));	
		logParam.put("access_menu_uid", "1301");	
		logParam.put("etc", etc);			
		super.InsContentsLog(logParam);
		//############################################################	 		
		
		return "/admin/reservation/res_edit";
	}
	
	
	/*
	 * 예약내역 수정페이지 수정 action ajax
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/admin/reservation/res_update.af")
	public int res_update(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		
		//-- xss 체크
		param = Util.chkParam(pageParamList, param);
		
		param.put("upd_id", admin.get("SESSION_ADMIN_ID"));
		// 예약내용 수정
		int result = reservationService.reservationUpdate(param);
		
		//20180315 추가
		Map vo = reservationService.reservationDetail(param);
		
		//관리자 사용기록 로그 #############################################
		String etc = param.get("order_num").toString()+"("+ param.get("order_nm").toString() +") 예약 수정";;
		Map logParam = new HashMap();
		logParam.put("point_code", "POINT01");
		logParam.put("action", "U");
		/* 20180315 수정중
		 * logParam.put("data_num", param.get("reserve_uid"));*/
		logParam.put("data_num", vo.get("MEM_NM").toString() + "회원님의 예약내역 수정 하셨습니다.");
		logParam.put("data_url", "javascript: ajaxShowPopCont({ url : '"+request.getRequestURL().toString()+"' ,data : { reserve_uid : "+param.get("reserve_uid").toString()+"} });");
		logParam.put("ins_ip", request.getRemoteAddr().toString());	
		logParam.put("ins_admin_id", admin.get("SESSION_ADMIN_ID"));	
		logParam.put("admin_nm", admin.get("SESSION_ADMIN_NM"));	
		logParam.put("admin_uid", admin.get("SESSION_ADMIN_UID"));	
		logParam.put("access_menu_uid", "1301");	
		logParam.put("etc", etc);			
		super.InsContentsLog(logParam);
		//############################################################			
		
		return result;
	}
	
	/*
	 * 검색값에 의한 엑셀 다운로드
	 */
	@RequestMapping("/admin/reservation/res_excel_down.af")
	public @ResponseBody byte[] res_excel_down(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response ) throws UnsupportedEncodingException{
		 
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		String memInfoYN = admin.get("SESSION_MEMINFOYN").toString();	
		List<Object> header = new ArrayList<Object>();		
		List<List<Object>> data = new ArrayList<List<Object>>();		
		int i = 0;
		
		//-- 16진수 변경
		param = Util.setMapHex(param);
		//페이징 없는 목록추출
		List<Map> rList = reservationService.reservationListAll(param);
		
		try{
			//-- 제목 설정
			header.add("결제일");
			header.add("지점");
			header.add("예약번호");
			header.add("회원명");
			header.add("휴대폰번호");
			header.add("선택상품");
			header.add("총");
			header.add("예약일");
			header.add("상태");
			
			for(Map map : rList){
				List<Object> obj = new ArrayList<Object>();
				obj.add(map.get("PAYMENT_DATE"));
				obj.add(map.get("POINT_NM"));
				obj.add(map.get("ORDER_NUM"));
				
				//20160830 김민주 추후 권한 레벨에 따라 마스크 한거 쓸지 안한거 쓸지 선택하는 로직 추가되야함
				String nm = (String)map.get("MEM_NM");
				String nm2 = "";
				if(nm.length() > 2){
					nm2 = nm.substring(0,1);
					for(i = 0; i < nm.length()-2;i++){
						nm2 += "*";
					}
					nm2 += nm.substring(nm.length() - 1, nm.length());
				}
				else if(nm.length() == 2){
					nm2 = nm.substring(0,1)+ "*";
				}
				else{
					nm2 = nm;
				}
				
				if(memInfoYN.equals("Y")){
					obj.add(nm);
					obj.add(map.get("MEM_MOBILE"));
				}else{
					obj.add(map.get("MASK_MEM_NM"));
					obj.add(map.get("MASK_MEM_MOBILE"));
				}
				
				obj.add(map.get("ORDER_NM"));
				obj.add(map.get("TOTNUM") +" 명");
				obj.add(map.get("RESERVE_DATE"));
				obj.add(map.get("RESERVE_STATE_NM"));
				
				data.add(obj);
			}
			
			ExcelUtil excelUtil = new ExcelUtil();
			byte[] bytes = excelUtil.makeXlsx(header, data);
			response.setHeader("Content-Disposition", "attachment; filename=" + Util.encodeString("예약내역") + "_" + Util.getTodayTime() + ".xlsx");
			response.setContentLength(bytes.length);
			response.setContentType("application/vnd.ms-excel");
			
			
			String status = "";
			if(param.get("reserve_state").equals("ING")) {
				status = "예약";
			} else if(param.get("reserve_state").equals("USE")) {
				status = "이용완료";
			} else if(param.get("reserve_state").equals("NOUSE")) {
				status = "미사용";
			} else if(param.get("reserve_state").equals("CANCEL")) {
				status = "정상취소";
			} else if(param.get("reserve_state").equals("FCANCEL")) {
				status = "위약취소";
			} else if(param.get("reserve_state").equals("NOPMT")) {
				status = "예약대기";
			} else {
				status = "선택안함";
			}
			
			String name = "";
			if(param.get("mem_nm").equals("") || param.get("mem_nm").equals(null)) {
				name = "";
			} else {
				name = "회원명 : "+ param.get("mem_nm");
			}
			
			//관리자 사용기록 로그 #############################################
			String etc = "엑셀다운로드";
			Map logParam = new HashMap();
			logParam.put("point_code", "POINT01");
			logParam.put("action", "E");
			/* 20180315 수정중
			 logParam.put("data_num", param.get("reserve_uid"));*/
			
			logParam.put("data_num", "엑셀을 다운받으셨습니다. [검색내용] 상태 : " + status +", 방문일 : " + param.get("srch_reg_s") + "~" + param.get("srch_reg_e"));
			logParam.put("data_url", request.getRequestURL().toString()+"?reserve_uid="+param.get("reserve_uid").toString());
			logParam.put("ins_ip", request.getRemoteAddr().toString());	
			logParam.put("ins_admin_id", admin.get("SESSION_ADMIN_ID"));	
			logParam.put("admin_nm", admin.get("SESSION_ADMIN_NM"));	
			logParam.put("admin_uid", admin.get("SESSION_ADMIN_UID"));	
			logParam.put("access_menu_uid", "1301");	
			logParam.put("etc", etc);			
			super.InsContentsLog(logParam);
			//############################################################			
			
			
			
			return bytes;
		}
		catch(Exception e){
			logger.debug(e.toString());
			return null;
		}
		
	}
	
}
