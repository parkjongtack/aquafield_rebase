package com.soft.web.controller.admin;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soft.web.base.CommonConstant;
import com.soft.web.base.GenericController;
import com.soft.web.service.admin.AdminAdminAuthService;
import com.soft.web.service.admin.AdminSaleStatisticsService;
import com.soft.web.service.admin.AdminStatisticsService;
import com.soft.web.util.AquaDateUtil;
import com.soft.web.util.ExcelUtil;
import com.soft.web.util.PageUtil;
import com.soft.web.util.Util;

@Controller
@RequestMapping({"/secu_admaf"})
public class AdminStatisticsController extends GenericController {

	protected Logger logger = LoggerFactory.getLogger(AdminStatisticsController.class);
	
	//paging
	int pageListSize = 10;
	int blockListSize = 10;
	
	@Autowired
	AdminAdminAuthService adminAdminAuthService;
	
	@Autowired
	AdminStatisticsService service;
	
	@Autowired	
	AdminSaleStatisticsService adminSaleStatisticsService;
	
	/*
	 * 예약내역 파라미터 모음
	 * page : 페이지, reserve_uid : 예약번호, category : 카테고리, 
	 * mem_nm : 회원명, mob_no : 회원전화번호, reserve_state : 예약상태, 
	 * srch_reg_s : 방문일 시작, srch_reg_e : 방문일 종료, mem_uid : 회원고유번호
	 */
	protected String[] pageParamList = {"page","reserve_uid","category","mem_nm","mob_no", "reserve_state", "srch_reg_s", "srch_reg_e"};
	
	//시간별 통계
	@RequestMapping(value="/admin/statistics/index.af")
	public String statisticsIndex(@RequestParam Map param, ModelMap model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//왼쪽 메뉴
		String sMenuCode = "1801";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null; 		

 		if(param.get("vd")	 == null) param.put("vd", Util.getTimeStamp(1) );
		
		param.put("yy", param.get("vd").toString().substring(0,4));
		param.put("mm", param.get("vd").toString().substring(5,7));
		param.put("dd", param.get("vd").toString().substring(8,10));

		
		model.addAttribute("resultParam", param);
		model.addAttribute("results", service.listVisitorTime(param));
		return "/admin/statistics/index";
	}
	
	//-- 일별통계
	@RequestMapping(value = "/admin/statistics/day.af")
    public String days(@RequestParam Map param, ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//왼쪽 메뉴
		String sMenuCode = "1801";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null; 	

		List<Map<String,String>> results = new ArrayList<Map<String,String>>();
		Map<String,String> todays = Util.makeToday();
		if(param.get("yy")	 == null) param.put("yy", todays.get("year"));
		if(param.get("mm")	 == null) param.put("mm", todays.get("month"));
		int endDay = Util.getEndDay(Util.getInt(param.get("yy").toString() ), Util.getInt(param.get("mm").toString() ) );

		List list = service.listVisitorDay(param);
		int listCnt = list.size();
		boolean isAdd = true;
		String j;
		String mm = param.get("mm").toString();
		if(mm.toString().length() == 1){
			mm = "0" + mm;
		}
		for(int i = 1; i <= endDay; i++){
			if(i < 10) j = "0" + i;
			else j = "" + i;
			Map<String,String> result = new HashMap<String,String>();
			isAdd = true;
			for(int k = 0; k < listCnt; k++){
				Map<String,String> listMap = (Map)list.get(k);
				if(i == Util.getInt(String.valueOf(listMap.get("IDAYS")))){
					result.put("YYYYMMDD",param.get("yy").toString()+"."+mm+"."+String.valueOf(listMap.get("IDAYS")));
					result.put("CNT", String.valueOf(listMap.get("VISITORCNT")));
					result.put("PAGEVIEWCNT", String.valueOf(listMap.get("PAGEVIEWCNT")));
					results.add(result);
					isAdd = false;
					continue;
				}
			}
			if(isAdd){
				result.put("YYYYMMDD",param.get("yy").toString()+"."+mm+"."+j);
				result.put("CNT", "0");
				result.put("PAGEVIEWCNT", "0");
				results.add(result);
			}
		}
		
		model.addAttribute("resultParam", param);
		model.addAttribute("results", results);
		
		return "/admin/statistics/day";
	}

	//-- 월별통계
	@RequestMapping(value = "/admin/statistics/month.af")
    public String months(@RequestParam Map param, ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//왼쪽 메뉴
		String sMenuCode = "1801";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null; 	

 		List<Map<String,String>> results = new ArrayList<Map<String,String>>();
		Map<String,String> todays = Util.makeToday();
		if(param.get("yy")	 == null) param.put("yy", todays.get("year"));

		List list = service.listVisitorMonth(param);
		int listCnt = list.size();
		boolean isAdd = true;
		String j;
		for(int i = 1; i <= 12; i++){
			if(i < 10) j = "0" + i;
			else j = "" + i;
			Map<String,String> result = new HashMap<String,String>();
			isAdd = true;
			for(int k = 0; k < listCnt; k++){
				Map<String,String> listMap = (Map)list.get(k);
				if(i == Util.getInt(String.valueOf(listMap.get("IMONTHS")))){
					logger.debug(param.get("yy")+"."+String.valueOf(listMap.get("IMONTHS")) + ":" + String.valueOf(listMap.get("VISITORCNT")));
					result.put("YYYYMM",param.get("yy")+"."+String.valueOf(listMap.get("IMONTHS")) );
					result.put("CNT", String.valueOf(listMap.get("VISITORCNT")));
					result.put("PAGEVIEWCNT", String.valueOf(listMap.get("PAGEVIEWCNT")));
					results.add(result);
					isAdd = false;
					continue;
				}
			}
			if(isAdd){
				result.put("YYYYMM",param.get("yy")+"."+j);
				result.put("CNT", "0");
				result.put("PAGEVIEWCNT", "0");
				results.add(result);
			}
		}
		
		model.addAttribute("resultParam", param);
		model.addAttribute("results", results);
		
		return "/admin/statistics/month";
	}
	
	//-- 매출속보
	@RequestMapping(value = "/admin/statistics/sales.af")
	public String sales(@RequestParam Map param, ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//왼쪽 메뉴
		String sMenuCode = "1801";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null; 		

		return "/admin/statistics/sales";
	}
	
	//-- 프린트
	@RequestMapping(value ="/admin/statistics/print.af")
	public String print(@RequestParam Map param, ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		
		System.out.println("@@@@@@@프린트");
		System.out.println("영업일" + param.get("srch_date_s") + "~" + param.get("srch_date_e"));
		System.out.println("일  today   week    month" + param.get("date"));
		
		String date ="";
		if(param.get("date").equals("today")) {
			date = "금일";
		} else if(param.get("date").equals("week")) {
			date = "1주일";
		} else {
			date = "한달";
		}
		
		String etc = "프린트";
		Map logParam = new HashMap();
		logParam.put("point_code", "POINT01");
		logParam.put("action", "P");
		/* 20180315 수정중
		 logParam.put("data_num", param.get("reserve_uid"));*/
		
		logParam.put("data_num", admin.get("SESSION_ADMIN_NM")+ "님이 프린트를 사용하셨습니다." + ", 검색 : " + param.get("srch_date_s") + "~" + param.get("srch_date_e") + date);
		logParam.put("data_url", "url");
		logParam.put("ins_ip", request.getRemoteAddr().toString());	
		logParam.put("ins_admin_id", admin.get("SESSION_ADMIN_ID"));	
		logParam.put("admin_nm", admin.get("SESSION_ADMIN_NM"));	
		logParam.put("admin_uid", admin.get("SESSION_ADMIN_UID"));	
		logParam.put("access_menu_uid", "1803");	
		logParam.put("etc", etc);			
		super.InsContentsLog(logParam);
		//############################################################	
		
		return "/admin/statistics/sales";
	}
	
	@RequestMapping(value = "/admin/statistics/ajaxSales.af")
	public @ResponseBody JSONArray ajaxSales(@RequestParam Map param, ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

		String strSrch_date_s = "";
		String strSrch_date_e = "";
		String strDate = "";
		if(param.size() < 1){
			String today = AquaDateUtil.getCurrentDate("");
			today = today.substring(0, 4) + "." + today.substring(4, 6) + "." + today.substring(6, 8);
			strSrch_date_s = today;
			strSrch_date_e = today;	
			strDate = "today";
		}else{
			strSrch_date_s = param.get("srch_date_s").toString();
			strSrch_date_e = param.get("srch_date_e").toString();
			strDate = param.get("date").toString();
		}
		
		Map parameter = new HashMap();
		parameter.put("srch_date_s", strSrch_date_s);
		parameter.put("srch_date_e", strSrch_date_e);
		List<Map> saleStatisticsList = adminSaleStatisticsService.getSaleStatistics(parameter);

		JSONArray jsonSalelist = new JSONArray();
		jsonSalelist.addAll(saleStatisticsList);	

	    return jsonSalelist;
	}

	//-- 유입경로
	@RequestMapping(value = "/admin/statistics/inflow.af")
	public String inflow(@RequestParam Map param, ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//왼쪽 메뉴
		String sMenuCode = "1801";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null; 
			
		int page = 0;

		//페이징
		page = (param.get("page") == null || "".equals(param.get("page").toString() ) ) ?  page = 1 : Util.getInt(param.get("page").toString());
		if(page < 1){
			page = 1;
			param.put("page",String.valueOf(page));
		}
		//-- 16진수 변경
		param = Util.setMapHex(param);
		
		String strSrch_date_s = "";
		String strSrch_date_e = "";
		String strDate = "";
		if(param.size() < 1){
			String today = AquaDateUtil.getCurrentDate("");
			today = today.substring(0, 4) + "." + today.substring(4, 6) + "." + today.substring(6, 8);

			param.put("srch_date_s", today);
			param.put("srch_date_e", today);
			param.put("daytype", "today");			
		}	
		
		int totCnt = service.refererStatisticsCnt(param);
		PageUtil paging = new PageUtil(page, totCnt, pageListSize, blockListSize);
		param.put("pageStartRow", paging.getPageStartRow());
		param.put("pageListSize", pageListSize);
		param.put("page", String.valueOf(page));		

		List<Map> getRefererStatistics = service.getRefererStatistics(param);		
	
		model.addAttribute("resultParam", param);
		model.addAttribute("totalCount", Util.numberFormat(totCnt));
		model.addAttribute("pu", paging);
		model.addAttribute("refererList", getRefererStatistics);	
		
		return "/admin/statistics/inflow";
	}
	
	// 유입경로 ajax
//	@RequestMapping(value = "/admin/statistics/ajaxRefere.af")
//	public @ResponseBody JSONArray ajaxRefere(@RequestParam Map param, ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
//
//		String strSrch_date_s = "";
//		String strSrch_date_e = "";
//		String strDate = "";
//		if(param.size() < 1){
//			String today = AquaDateUtil.getCurrentDate("");
//			today = today.substring(0, 4) + "." + today.substring(4, 6) + "." + today.substring(6, 8);
//			strSrch_date_s = today;
//			strSrch_date_e = today;	
//			strDate = "today";
//		}else{
//			strSrch_date_s = param.get("srch_date_s").toString();
//			strSrch_date_e = param.get("srch_date_e").toString();
//			strDate = param.get("date").toString();
//		}
//		
//		Map parameter = new HashMap();
//		parameter.put("srch_date_s", strSrch_date_s);
//		parameter.put("srch_date_e", strSrch_date_e);
//		List<Map> getRefererStatistics = service.getRefererStatistics(parameter);
//
//		JSONArray jsonSalelist = new JSONArray();
//		jsonSalelist.addAll(getRefererStatistics);	
//
//	    return jsonSalelist;
//	}	

	@RequestMapping(value="/admin/statistics/totalData.af")
	public String statisticsTotalData(@RequestParam Map param, ModelMap model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//왼쪽 메뉴
		String sMenuCode = "1801";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null; 		

		model.addAttribute("MEMINFOYN", admin.get("SESSION_MEMINFOYN").toString());
		
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
	    cal.set(Calendar.DAY_OF_MONTH, 1);
	    String startDate = df.format(cal.getTime());
	    cal.add(Calendar.MONTH, 1);
	    cal.set(Calendar.DAY_OF_MONTH, 0);
	    String endDay = df.format(cal.getTime());

	    if(param.get("srch_reg_s") == null){
	    	param.put("srch_reg_s", startDate);
	    	param.put("srch_reg_e", endDay);
	    }
	    
	    
		//int pageListSize = 20;
		//전체 주문 데이터 가져오기
		int totCnt = service.reservationListCnt(param);
		
//		int page = 0;
//		//-- xss 체크
//		param = Util.chkParam(pageParamList, param);
//
//		//페이징
//		page = (param.get("page") == null || "".equals(param.get("page").toString() ) ) ?  page = 1 : Util.getInt(param.get("page").toString());
//		if(page < 1){
//			page = 1;
//			param.put("page",String.valueOf(page));
//		}
//		
//		PageUtil paging = new PageUtil(page, totCnt, pageListSize, blockListSize);
//		param.put("pageStartRow", paging.getPageStartRow());
//		param.put("pageListSize", pageListSize);
//		param.put("page", String.valueOf(page));
//		
		List<Map<String, Object>> codePOINT_CODE = super.getCommonCodes("POINT_CODE");
		List<Map<String, Object>> codeRSRVT_TYPE = super.getCommonCodes("RSRVT_TYPE");
		List<Map<String, Object>> codePAY_TYPE = super.getCommonCodes("PAY_TYPE");
		
		String srch_point = "";
	    if(param.get("srch_point") == null){
	    	srch_point = codePOINT_CODE.get(0).get("CODE_ID").toString();
	    }else{
	    	srch_point = param.get("srch_point").toString();
	    }
	    
		param.put("srch_point", srch_point);
		List ePlaceList = service.itemlist(param);
		//목록조회	
		List<Map> rList = service.reservationListAll(param);
		model.addAttribute("totCnt", totCnt);
//		model.addAttribute("page", page);
		model.addAttribute("rList", rList);
		model.addAttribute("codeRSRVT_TYPE", codeRSRVT_TYPE);
		model.addAttribute("codePOINT_CODE", codePOINT_CODE);
		model.addAttribute("codePAY_TYPE", codePAY_TYPE);
		model.addAttribute("ePlaceList", ePlaceList);
		model.addAttribute("ePlaceList", ePlaceList);
		model.addAttribute("resultParam", param);
		return "/admin/statistics/totalData";
	}
	
	
	@RequestMapping(value="/ajax/statistics/ajax_totalData.af")
	public String ajaxTotalDataList(@RequestParam Map param, ModelMap model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		//int pageListSize = 20;
		//전체 주문 데이터 가져오기
		int totCnt = service.reservationListCnt(param);
		
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		model.addAttribute("MEMINFOYN", admin.get("SESSION_MEMINFOYN").toString());
//		int page = 0;
//		//-- xss 체크
//		param = Util.chkParam(pageParamList, param);
//
//		//페이징
//		page = (param.get("page") == null || "".equals(param.get("page").toString() ) ) ?  page = 1 : Util.getInt(param.get("page").toString());
//		if(page < 1){
//			page = 1;
//			param.put("page",String.valueOf(page));
//		}
//		
//		PageUtil paging = new PageUtil(page, totCnt, pageListSize, blockListSize);
//		param.put("pageStartRow", paging.getPageStartRow());
//		param.put("pageListSize", pageListSize);
//		param.put("page", String.valueOf(page));
		
		//목록조회	
		List<Map> rList = service.reservationListAll(param);
		model.addAttribute("totCnt", totCnt);
//		model.addAttribute("page", page);
		model.addAttribute("rList", rList);
		return "/admin/statistics/ajax_totalData";
	}
	

	/*
	 * 검색값에 의한 엑셀 다운로드
	 */
	@RequestMapping("/admin/statistics/res_excel_down.af")
	public @ResponseBody byte[] res_excel_down(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response ) throws UnsupportedEncodingException{
		 
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		String memInfoYN = admin.get("SESSION_MEMINFOYN").toString();
		
		List<Object> header = new ArrayList<Object>();		
		List<List<Object>> data = new ArrayList<List<Object>>();		
		int i = 0;
		
		//-- 16진수 변경
		param = Util.setMapHex(param);
		param.put("memInfoYN", memInfoYN);
		
		
		
		//페이징 없는 목록추출
		List<Map> rList = service.reservationListAll(param);
		
	
		try{
			//-- 제목 설정
			header.add("순번");
			header.add("지점");
			header.add("회원명");
			header.add("연락처");
			header.add("예약번호");
			header.add("결제일");
			header.add("방문일");
			header.add("카테고리");
			header.add("이용시설");
			header.add("상태");
			header.add("결제수단");
			header.add("인원");
			header.add("결제금액");
			header.add("취소금액");
			header.add("위약금");
			
			int numSum = 0;
			int paySum = 0;
			int canSum = 0;
			int panSum = 0;
			
			for(Map map : rList){
				List<Object> obj = new ArrayList<Object>();
				obj.add(map.get("RN"));
				obj.add(map.get("POINT_NM"));
				if(memInfoYN.equals("Y")){
					obj.add(map.get("MEM_NM"));
					obj.add(map.get("MEM_MOBILE"));
				}else{
					obj.add(map.get("MASK_MEM_NM"));
					obj.add(map.get("MASK_MEM_MOBILE"));
				}
				obj.add(map.get("ORDER_NUM"));
				obj.add(map.get("PAYMENT_DATE"));
				obj.add(map.get("RESERVE_DATE"));
				obj.add(map.get("ORDER_NM"));
				String item_nm = "";
				if(map.get("ORDER_TP").toString().equals("0")){
					item_nm = map.get("ENTER_ITEM_NM").toString();
				}else{
					item_nm = map.get("PACKAGE_ITEM_NM").toString();
				}
				obj.add(item_nm); 
				obj.add(map.get("RESERVE_STATE_NM"));
				obj.add(map.get("PAYMENT_TYPE_NM"));
				int totNum = Integer.parseInt(map.get("TOTNUM").toString());
				obj.add(totNum);
				
				int paymentPrice 	= (map.get("PAYMENT_PRICE") == null || map.get("PAYMENT_PRICE").equals("")) 	? 0 : Integer.parseInt(map.get("PAYMENT_PRICE").toString());
				int refund 			= (map.get("REFUND") == null || map.get("REFUND").equals("")) 			? 0 : Integer.parseInt(map.get("REFUND").toString());
				int panaltyPrice 	= (map.get("PANALTY_PRICE") == null || map.get("PANALTY_PRICE").equals("")) 	? 0 : Integer.parseInt(map.get("PANALTY_PRICE").toString());
				
				obj.add(paymentPrice);
				obj.add(refund);
				obj.add(panaltyPrice);
				
				numSum += totNum;
				paySum += paymentPrice;
				canSum += refund;
				panSum += panaltyPrice;
				
				data.add(obj);
				
			}
			
			List<Object> obj = new ArrayList<Object>();
			obj.add("");
			obj.add("");
			obj.add("");
			obj.add("");
			obj.add("");
			obj.add("");
			obj.add("");
			obj.add("");
			obj.add("");
			obj.add("");
			obj.add("합계");
			obj.add(numSum);
			obj.add(paySum);
			obj.add(canSum);
			obj.add(panSum);
			data.add(obj);
			
			ExcelUtil excelUtil = new ExcelUtil();
			byte[] bytes = excelUtil.makeXlsx(header, data);
			
			String fileName = "개별회원"+ "_" + Util.getTodayTime();
			setFileNameToResponse(request, response, fileName);
			
			//크롬, ie 변경
			//response.setHeader("Content-Disposition", "attachment; filename=" + Util.encodeString("전체주문데이터") + "_" + Util.getTodayTime() + ".xlsx; style=mso-number-format:'\\@'");
			response.setContentLength(bytes.length);
			response.setContentType("application/vnd.ms-excel");
			
			String point = "";
			if(param.get("srch_point").equals("POINT1")) {
				point = "하남";
			} else {
				point = "고양";
			}
			
			String payment = "";
			if(param.get("payment_type").equals(null) || param.get("payment_type").equals("")) {
				payment = "";
			} else {
				if(param.get("payment_type").equals("credit")) {
					payment = ", 결제수단 : " + "신용카드";
				} else if(param.get("payment_type").equals("bank")){
					payment = ", 결제수단 : " + "실시간계좌이체";
				} else if(param.get("payment_type").equals("ssg")) {
					payment = ", 결제수단 : " + "SSG PAY";
				}
				
			}
			
			String name = "";
			if(param.get("mem_nm").equals(null) || param.get("mem_nm").equals("")) {
				name = "";
			} else {
				name = ", 이름 : " + param.get("mem_nm");
			}
			
			String srch_pay = "";
			if(param.get("srch_pay_s").equals(null) || param.get("srch_pay_s").equals("")) {
				srch_pay = "";
			} else {
				srch_pay = ", 결제일 : " + param.get("srch_pay_s") + "~" + param.get("srch_pay_e");
			}
			
			//관리자 사용기록 로그 #############################################
			String etc = "엑셀다운로드";
			Map logParam = new HashMap();
			logParam.put("point_code", "POINT01");
			logParam.put("action", "E");
			/* 20180315 수정중
			 logParam.put("data_num", param.get("reserve_uid"));*/
			
			logParam.put("data_num", "엑셀을 다운받으셨습니다. [검색내용] 지점 : " + point + payment + name + srch_pay + ", 방문일 : " + param.get("srch_reg_s") + "~" + param.get("srch_reg_e"));
			logParam.put("data_url", request.getRequestURL().toString()+"?reserve_uid="+param.get("reserve_uid").toString());
			logParam.put("ins_ip", request.getRemoteAddr().toString());	
			logParam.put("ins_admin_id", admin.get("SESSION_ADMIN_ID"));	
			logParam.put("admin_nm", admin.get("SESSION_ADMIN_NM"));	
			logParam.put("admin_uid", admin.get("SESSION_ADMIN_UID"));	
			logParam.put("access_menu_uid", "1804");	
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
	
	 private void setFileNameToResponse(HttpServletRequest request, HttpServletResponse response, String fileName) throws Exception {
			
		String userAgent = request.getHeader("User-Agent");
		byte[] b = new byte[2048]; //buffer size 2K.
		
		if (userAgent.indexOf("Opera") > -1){
			response.setContentType("application/octet-stream;charset=UTF-8");
		}else{
			response.setContentType("application/x-msdownload");
		}
		String encodedFilename = null;
		if (userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("Trident") > -1) { // MS IE (보통은 6.x 이상 가정)
			encodedFilename = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
		} else if (userAgent.indexOf("Firefox") > -1) { // Firefox
			encodedFilename = "\"" + new String(fileName.getBytes("UTF-8"), "8859_1") + "\"";
		} else if (userAgent.indexOf("Opera") > -1) { // Opera
			encodedFilename = "\"" + new String(fileName.getBytes("UTF-8"), "8859_1") + "\"";
		} else if (userAgent.indexOf("AdobeAIR") > -1) { // AdobeAIR
			encodedFilename = "\"" + new String(fileName.getBytes("UTF-8"), "8859_1") + "\"";
		} else if (userAgent.indexOf("Chrome") > -1) { // Chrome
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < fileName.length(); i++) {
				char c = fileName.charAt(i);
				if (c > '~') {
					sb.append(URLEncoder.encode("" + c, "UTF-8"));
				} else {
					sb.append(c);
				}
			}
			encodedFilename = sb.toString();
		} else { //throw new RuntimeException("Not supported browser");
			throw new IOException("Not supported browser");
		}
		
		response.setHeader("Content-Disposition", "attachment; filename=" + encodedFilename + Util.getTodayTime() + ".xlsx; style=mso-number-format:'\\@'");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Pragma", "no-store, no-cache, must-revalidate");
		response.setHeader("Expires", "0");
	}
	 
	@RequestMapping(value="/admin/statistics/generalData.af")
	public String generalData(@RequestParam Map param, ModelMap model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//왼쪽 메뉴 
		String sMenuCode = "1801";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null; 		
		
		List<Map<String, Object>> codePOINT_CODE = super.getCommonCodes("POINT_CODE");
		List<Map<String, Object>> codeRSRVT_TYPE = super.getCommonCodes("RSRVT_TYPE");
		List<Map<String, Object>> codePAY_TYPE = super.getCommonCodes("PAY_TYPE");
		
		String srch_point = "";
	    if(param.get("srch_point") == null){
	    	srch_point = codePOINT_CODE.get(0).get("CODE_ID").toString();
	    }else{
	    	srch_point = param.get("srch_point").toString();
	    }
	    
		param.put("srch_point", srch_point);
		List ePlaceList = service.itemlist(param);
		int ePlaceListSize = ePlaceList.size();
		
		
		Calendar cal = Calendar.getInstance();


	    SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
	    cal.set(Calendar.DAY_OF_MONTH, 1);
	    String startDate = df.format(cal.getTime());
	    cal.add(Calendar.MONTH, 1);
	    cal.set(Calendar.DAY_OF_MONTH, 0);
	    String endDay = df.format(cal.getTime());

	    if(param.get("srch_pay_s") == null){
	    	param.put("srch_pay_s", startDate);
	    	param.put("srch_pay_e", endDay);
	    }
	    
	    //전체 데이터
//		List totGeneralList = service.getGeneralData(param);
		param.put("order_tp", "0");
		//전체-입장상품 데이터
		List totEnterList = service.getGeneralData(param);
		param.put("order_tp", "1");
		//전체-패키지 데이터
		List totPackageList = service.getGeneralData(param);
		param.put("reserve_state", "ING");
		//예약-전체 데이터
//		List reserveGeneralList = service.getGeneralData(param);
		param.put("order_tp", "0");
		//예약-입장상품 데이터
		List reserveEnterList = service.getGeneralData(param);
		param.put("order_tp", "1");
		//예약-패키지 데이터
		List reservePackageList = service.getGeneralData(param);
		param.put("reserve_state", "USE");
		//이용완료-전체 데이터
//		List useGeneralList = service.getGeneralData(param);
		param.put("order_tp", "0");
		//이용완료-입장상품 데이터
		List useEnterList = service.getGeneralData(param);
		param.put("order_tp", "1");
		//이용완료-패키지 데이터
		List usePackageList = service.getGeneralData(param);
		param.put("reserve_state", "CANCEL");
		//정상취소-전체 데이터
//		List cancelGeneralList = service.getGeneralData(param);
		param.put("order_tp", "0");
		//정상취소-입장상품 데이터
		List cancelEnterList = service.getGeneralData(param);
		param.put("order_tp", "1");
		//정상취소-패키지 데이터
		List cancelPackageList = service.getGeneralData(param);
		param.put("reserve_state", "FCANCEL");
		//위약취소-전체 데이터
//		List fcancelGeneralList = service.getGeneralData(param);
		param.put("order_tp", "0");
		//위약취소-입장상품 데이터
		List fcancelEnterList = service.getGeneralData(param);
		param.put("order_tp", "1");
		//위약취소-패키지 데이터
		List fcancelPackageList = service.getGeneralData(param);
		
		model.addAttribute("codeRSRVT_TYPE", codeRSRVT_TYPE);
		model.addAttribute("codePOINT_CODE", codePOINT_CODE);
		model.addAttribute("codePAY_TYPE", codePAY_TYPE);
		model.addAttribute("ePlaceList", ePlaceList);
		model.addAttribute("ePlaceListSize", ePlaceListSize);
		
//		model.addAttribute("totGeneralList", totGeneralList);
		model.addAttribute("totEnterList", totEnterList);
		model.addAttribute("totPackageList", totPackageList);
//		model.addAttribute("reserveGeneralList", reserveGeneralList);
		model.addAttribute("reserveEnterList", reserveEnterList);
		model.addAttribute("reservePackageList", reservePackageList);
//		model.addAttribute("useGeneralList", useGeneralList);
		model.addAttribute("useEnterList", useEnterList);
		model.addAttribute("usePackageList", usePackageList);
//		model.addAttribute("cancelGeneralList", cancelGeneralList);
		model.addAttribute("cancelEnterList", cancelEnterList);
		model.addAttribute("cancelPackageList", cancelPackageList);
//		model.addAttribute("fcancelGeneralList", fcancelGeneralList);
		model.addAttribute("fcancelEnterList", fcancelEnterList);
		model.addAttribute("fcancelPackageList", fcancelPackageList);
		
		model.addAttribute("resultParam", param);
		return "/admin/statistics/generalData";
	}
	
	
	@RequestMapping(value="/admin/statistics/generalData_excel_down.af")
	public @ResponseBody byte[] generalData_excel_down(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response ) throws UnsupportedEncodingException{
		
		List<Map<String, Object>> codePOINT_CODE = super.getCommonCodes("POINT_CODE");
		List<Map<String, Object>> codeRSRVT_TYPE = super.getCommonCodes("RSRVT_TYPE");
		List<Map<String, Object>> codePAY_TYPE = super.getCommonCodes("PAY_TYPE");
		
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		
		String srch_point = "";
	    if(param.get("srch_point") == null){
	    	srch_point = codePOINT_CODE.get(0).get("CODE_ID").toString();
	    }else{
	    	srch_point = param.get("srch_point").toString();
	    }
	    
		param.put("srch_point", srch_point);
		List ePlaceList = service.itemlist(param);
		int ePlaceListSize = ePlaceList.size();
		
		
		Calendar cal = Calendar.getInstance();


	    SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
	    cal.set(Calendar.DAY_OF_MONTH, 1);
	    String startDate = df.format(cal.getTime());
	    cal.add(Calendar.MONTH, 1);
	    cal.set(Calendar.DAY_OF_MONTH, 0);
	    String endDay = df.format(cal.getTime());

	    if(param.get("srch_pay_s") == null){
	    	param.put("srch_pay_s", startDate);
	    	param.put("srch_pay_e", endDay);
	    }
	    
		param.put("order_tp", "0");
		//전체-입장상품 데이터
		List<Map<String, Object>> totEnterList = service.getGeneralData(param);
		param.put("order_tp", "1");
		//전체-패키지 데이터
		List<Map<String, Object>> totPackageList = service.getGeneralData(param);
		param.put("reserve_state", "ING");
		param.put("order_tp", "0");
		
		//예약-입장상품 데이터
		List<Map<String, Object>> reserveEnterList = service.getGeneralData(param);
		param.put("order_tp", "1");
		//예약-패키지 데이터
		List<Map<String, Object>> reservePackageList = service.getGeneralData(param);
		param.put("reserve_state", "USE");
		param.put("order_tp", "0");
		//이용완료-입장상품 데이터
		List<Map<String, Object>> useEnterList = service.getGeneralData(param);
		param.put("order_tp", "1");
		//이용완료-패키지 데이터
		List<Map<String, Object>> usePackageList = service.getGeneralData(param);
		param.put("reserve_state", "CANCEL");
		param.put("order_tp", "0");
		//정상취소-입장상품 데이터
		List<Map<String, Object>> cancelEnterList = service.getGeneralData(param);
		param.put("order_tp", "1");
		//정상취소-패키지 데이터
		List<Map<String, Object>> cancelPackageList = service.getGeneralData(param);
		param.put("reserve_state", "FCANCEL");
		param.put("order_tp", "0");
		//위약취소-입장상품 데이터
		List<Map<String, Object>> fcancelEnterList = service.getGeneralData(param);
		param.put("order_tp", "1");
		//위약취소-패키지 데이터
		List<Map<String, Object>> fcancelPackageList = service.getGeneralData(param);
		
		byte[] bytes = new byte[0];
		try{
			//-- 제목 설정
			
			
			int numSum = 0;
			int paySum = 0;
			int canSum = 0;
			int panSum = 0;
			
			//util start
			//byte[] bytes = excelUtil.makeXlsx(header, data);
			
			XSSFWorkbook mWorkbook;
			int mStartRow = 0;
			int mStartCol = 0;
			int mWidth = 5000;

			
			
			IndexedColors mHeaderColor =  IndexedColors.LIGHT_CORNFLOWER_BLUE;
	        IndexedColors mDataColor =  IndexedColors.WHITE;

	        String mSheetName = "sheet1";
	        
	        InputStream mReadFile;

	        mWorkbook = new XSSFWorkbook();
	        XSSFSheet sheet = mWorkbook.createSheet(mSheetName);
	        XSSFRow headerRow = sheet.createRow(mStartRow);

	        short index =0;
	        //ExcelUtil excelUtil = new ExcelUtil();
	        //XSSFCell headerCell = headerRow.createCell(0, XSSFCell.CELL_TYPE_STRING);
	        headerRow.createCell(0, XSSFCell.CELL_TYPE_STRING).setCellValue("구분");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),1,0,2) );
	        headerRow.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue("전체");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum(),3,6) );
	        headerRow.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue("입장상품");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum(),7,10) );
	        headerRow.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue("패키지");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum(),11,14) );
	        
	        mStartRow=mStartRow+1;
	        headerRow = sheet.createRow(mStartRow);
	        headerRow.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue("찜질스파");
	        headerRow.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue("워터파크");
	        headerRow.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue("복합권");
	        headerRow.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue("합계");
	        
	        headerRow.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue("찜질스파");
	        headerRow.createCell(8, XSSFCell.CELL_TYPE_STRING).setCellValue("워터파크");
	        headerRow.createCell(9, XSSFCell.CELL_TYPE_STRING).setCellValue("복합권");
	        headerRow.createCell(10, XSSFCell.CELL_TYPE_STRING).setCellValue("합계");
	        
	        headerRow.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue("찜질스파");
	        headerRow.createCell(12, XSSFCell.CELL_TYPE_STRING).setCellValue("워터파크");
	        headerRow.createCell(13, XSSFCell.CELL_TYPE_STRING).setCellValue("복합권");
	        headerRow.createCell(14, XSSFCell.CELL_TYPE_STRING).setCellValue("합계");
	        
	        mStartRow=mStartRow+1;
	        headerRow = sheet.createRow(mStartRow);
	        headerRow.createCell(0, XSSFCell.CELL_TYPE_STRING).setCellValue("전체");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum()+13,0,0) );
	        headerRow.createCell(1, XSSFCell.CELL_TYPE_STRING).setCellValue("건");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum(),1,2) );
	        
	        int sum =0;
	        int totSum=0;
	        
	        
	        headerRow.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue(totEnterList.get(0).get("TOT_CNT").toString());
	        headerRow.createCell(8, XSSFCell.CELL_TYPE_STRING).setCellValue(totEnterList.get(1).get("TOT_CNT").toString());
	        headerRow.createCell(9, XSSFCell.CELL_TYPE_STRING).setCellValue(totEnterList.get(2).get("TOT_CNT").toString());
	        try{
	        	sum=Integer.parseInt(totEnterList.get(0).get("TOT_CNT").toString()) + Integer.parseInt(totEnterList.get(1).get("TOT_CNT").toString()) + Integer.parseInt(totEnterList.get(2).get("TOT_CNT").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(10, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        headerRow.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue(totPackageList.get(0).get("TOT_CNT").toString());
	        headerRow.createCell(12, XSSFCell.CELL_TYPE_STRING).setCellValue(totPackageList.get(1).get("TOT_CNT").toString());
	        headerRow.createCell(13, XSSFCell.CELL_TYPE_STRING).setCellValue(totPackageList.get(2).get("TOT_CNT").toString());
	        try{
	        	sum=Integer.parseInt(totPackageList.get(0).get("TOT_CNT").toString()) + Integer.parseInt(totPackageList.get(1).get("TOT_CNT").toString()) + Integer.parseInt(totPackageList.get(2).get("TOT_CNT").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(14, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        sum=Integer.parseInt(totEnterList.get(0).get("TOT_CNT").toString()) + Integer.parseInt(totPackageList.get(0).get("TOT_CNT").toString());
	        totSum=sum;
	        headerRow.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(totEnterList.get(1).get("TOT_CNT").toString()) + Integer.parseInt(totPackageList.get(1).get("TOT_CNT").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(totEnterList.get(2).get("TOT_CNT").toString()) + Integer.parseInt(totPackageList.get(2).get("TOT_CNT").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        headerRow.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue(totSum+"");
	        
	        
	        
	        mStartRow=mStartRow+1;
	        headerRow = sheet.createRow(mStartRow);
	        headerRow.createCell(1, XSSFCell.CELL_TYPE_STRING).setCellValue("이용객 [명]");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum(),1,2) );
	        headerRow.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue(totEnterList.get(0).get("TOT_NUM").toString());
	        headerRow.createCell(8, XSSFCell.CELL_TYPE_STRING).setCellValue(totEnterList.get(1).get("TOT_NUM").toString());
	        headerRow.createCell(9, XSSFCell.CELL_TYPE_STRING).setCellValue(totEnterList.get(2).get("TOT_NUM").toString());
	        try{
	        	sum=Integer.parseInt(totEnterList.get(0).get("TOT_NUM").toString()) + Integer.parseInt(totEnterList.get(1).get("TOT_NUM").toString()) + Integer.parseInt(totEnterList.get(2).get("TOT_NUM").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(10, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        headerRow.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue(totPackageList.get(0).get("TOT_NUM").toString());
	        headerRow.createCell(12, XSSFCell.CELL_TYPE_STRING).setCellValue(totPackageList.get(1).get("TOT_NUM").toString());
	        headerRow.createCell(13, XSSFCell.CELL_TYPE_STRING).setCellValue(totPackageList.get(2).get("TOT_NUM").toString());
	        try{
	        	sum=Integer.parseInt(totPackageList.get(0).get("TOT_NUM").toString()) + Integer.parseInt(totPackageList.get(1).get("TOT_NUM").toString()) + Integer.parseInt(totPackageList.get(2).get("TOT_NUM").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(14, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        sum=Integer.parseInt(totEnterList.get(0).get("TOT_NUM").toString()) + Integer.parseInt(totPackageList.get(0).get("TOT_NUM").toString());
	        totSum=sum;
	        headerRow.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(totEnterList.get(1).get("TOT_NUM").toString()) + Integer.parseInt(totPackageList.get(1).get("TOT_NUM").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(totEnterList.get(2).get("TOT_NUM").toString()) + Integer.parseInt(totPackageList.get(2).get("TOT_NUM").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        headerRow.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue(totSum+"");
	        
	        
	        
	        
	        mStartRow=mStartRow+1;
	        headerRow = sheet.createRow(mStartRow);
	        headerRow.createCell(1, XSSFCell.CELL_TYPE_STRING).setCellValue("결제금액 [원]");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum()+3,1,1) );
	        headerRow.createCell(2, XSSFCell.CELL_TYPE_STRING).setCellValue("SSG PAY");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum(),2,2) );
	        headerRow.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        headerRow.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue(totEnterList.get(0).get("PAY_SSG").toString());
	        headerRow.createCell(8, XSSFCell.CELL_TYPE_STRING).setCellValue(totEnterList.get(1).get("PAY_SSG").toString());
	        headerRow.createCell(9, XSSFCell.CELL_TYPE_STRING).setCellValue(totEnterList.get(2).get("PAY_SSG").toString());
	        try{
	        	sum=Integer.parseInt(totEnterList.get(0).get("PAY_SSG").toString()) + Integer.parseInt(totEnterList.get(1).get("PAY_SSG").toString()) + Integer.parseInt(totEnterList.get(2).get("PAY_SSG").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(10, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        headerRow.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue(totPackageList.get(0).get("PAY_SSG").toString());
	        headerRow.createCell(12, XSSFCell.CELL_TYPE_STRING).setCellValue(totPackageList.get(1).get("PAY_SSG").toString());
	        headerRow.createCell(13, XSSFCell.CELL_TYPE_STRING).setCellValue(totPackageList.get(2).get("PAY_SSG").toString());
	        try{
	        	sum=Integer.parseInt(totPackageList.get(0).get("PAY_SSG").toString()) + Integer.parseInt(totPackageList.get(1).get("PAY_SSG").toString()) + Integer.parseInt(totPackageList.get(2).get("PAY_SSG").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(14, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        sum=Integer.parseInt(totEnterList.get(0).get("PAY_SSG").toString()) + Integer.parseInt(totPackageList.get(0).get("PAY_SSG").toString());
	        totSum=sum;
	        headerRow.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(totEnterList.get(1).get("PAY_SSG").toString()) + Integer.parseInt(totPackageList.get(1).get("PAY_SSG").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(totEnterList.get(2).get("PAY_SSG").toString()) + Integer.parseInt(totPackageList.get(2).get("PAY_SSG").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        headerRow.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue(totSum+"");
	        
	        
	        mStartRow=mStartRow+1;
	        headerRow = sheet.createRow(mStartRow);
	        headerRow.createCell(2, XSSFCell.CELL_TYPE_STRING).setCellValue("신용카드");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum(),2,2) );
	        
	        headerRow.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue(totEnterList.get(0).get("PAY_CREDIT").toString());
	        headerRow.createCell(8, XSSFCell.CELL_TYPE_STRING).setCellValue(totEnterList.get(1).get("PAY_CREDIT").toString());
	        headerRow.createCell(9, XSSFCell.CELL_TYPE_STRING).setCellValue(totEnterList.get(2).get("PAY_CREDIT").toString());
	        try{
	        	sum=Integer.parseInt(totEnterList.get(0).get("PAY_CREDIT").toString()) + Integer.parseInt(totEnterList.get(1).get("PAY_CREDIT").toString()) + Integer.parseInt(totEnterList.get(2).get("PAY_CREDIT").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(10, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        headerRow.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue(totPackageList.get(0).get("PAY_CREDIT").toString());
	        headerRow.createCell(12, XSSFCell.CELL_TYPE_STRING).setCellValue(totPackageList.get(1).get("PAY_CREDIT").toString());
	        headerRow.createCell(13, XSSFCell.CELL_TYPE_STRING).setCellValue(totPackageList.get(2).get("PAY_CREDIT").toString());
	        try{
	        	sum=Integer.parseInt(totPackageList.get(0).get("PAY_CREDIT").toString()) + Integer.parseInt(totPackageList.get(1).get("PAY_CREDIT").toString()) + Integer.parseInt(totPackageList.get(2).get("PAY_CREDIT").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(14, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        sum=Integer.parseInt(totEnterList.get(0).get("PAY_CREDIT").toString()) + Integer.parseInt(totPackageList.get(0).get("PAY_CREDIT").toString());
	        totSum=sum;
	        headerRow.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(totEnterList.get(1).get("PAY_CREDIT").toString()) + Integer.parseInt(totPackageList.get(1).get("PAY_CREDIT").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(totEnterList.get(2).get("PAY_CREDIT").toString()) + Integer.parseInt(totPackageList.get(2).get("PAY_CREDIT").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        headerRow.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue(totSum+"");
	        
	        
	        mStartRow=mStartRow+1;
	        headerRow = sheet.createRow(mStartRow);
	        headerRow.createCell(2, XSSFCell.CELL_TYPE_STRING).setCellValue("실시간계좌이체");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum(),2,2) );
	        
	        headerRow.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue(totEnterList.get(0).get("PAY_BANK").toString());
	        headerRow.createCell(8, XSSFCell.CELL_TYPE_STRING).setCellValue(totEnterList.get(1).get("PAY_BANK").toString());
	        headerRow.createCell(9, XSSFCell.CELL_TYPE_STRING).setCellValue(totEnterList.get(2).get("PAY_BANK").toString());
	        try{
	        	sum=Integer.parseInt(totEnterList.get(0).get("PAY_BANK").toString()) + Integer.parseInt(totEnterList.get(1).get("PAY_BANK").toString()) + Integer.parseInt(totEnterList.get(2).get("PAY_BANK").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(10, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        headerRow.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue(totPackageList.get(0).get("PAY_BANK").toString());
	        headerRow.createCell(12, XSSFCell.CELL_TYPE_STRING).setCellValue(totPackageList.get(1).get("PAY_BANK").toString());
	        headerRow.createCell(13, XSSFCell.CELL_TYPE_STRING).setCellValue(totPackageList.get(2).get("PAY_BANK").toString());
	        try{
	        	sum=Integer.parseInt(totPackageList.get(0).get("PAY_BANK").toString()) + Integer.parseInt(totPackageList.get(1).get("PAY_BANK").toString()) + Integer.parseInt(totPackageList.get(2).get("PAY_BANK").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(14, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        sum=Integer.parseInt(totEnterList.get(0).get("PAY_BANK").toString()) + Integer.parseInt(totPackageList.get(0).get("PAY_BANK").toString());
	        totSum=sum;
	        headerRow.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(totEnterList.get(1).get("PAY_BANK").toString()) + Integer.parseInt(totPackageList.get(1).get("PAY_BANK").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(totEnterList.get(2).get("PAY_BANK").toString()) + Integer.parseInt(totPackageList.get(2).get("PAY_BANK").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        headerRow.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue(totSum+"");
	        
	        
	        mStartRow=mStartRow+1;
	        headerRow = sheet.createRow(mStartRow);
	        headerRow.createCell(2, XSSFCell.CELL_TYPE_STRING).setCellValue("합계");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum(),2,2) );
	        
	        headerRow.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue(totEnterList.get(0).get("TOT_PAY").toString());
	        headerRow.createCell(8, XSSFCell.CELL_TYPE_STRING).setCellValue(totEnterList.get(1).get("TOT_PAY").toString());
	        headerRow.createCell(9, XSSFCell.CELL_TYPE_STRING).setCellValue(totEnterList.get(2).get("TOT_PAY").toString());
	        try{
	        	sum=Integer.parseInt(totEnterList.get(0).get("TOT_PAY").toString()) + Integer.parseInt(totEnterList.get(1).get("TOT_PAY").toString()) + Integer.parseInt(totEnterList.get(2).get("TOT_PAY").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(10, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        headerRow.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue(totPackageList.get(0).get("TOT_PAY").toString());
	        headerRow.createCell(12, XSSFCell.CELL_TYPE_STRING).setCellValue(totPackageList.get(1).get("TOT_PAY").toString());
	        headerRow.createCell(13, XSSFCell.CELL_TYPE_STRING).setCellValue(totPackageList.get(2).get("TOT_PAY").toString());
	        try{
	        	sum=Integer.parseInt(totPackageList.get(0).get("TOT_PAY").toString()) + Integer.parseInt(totPackageList.get(1).get("TOT_PAY").toString()) + Integer.parseInt(totPackageList.get(2).get("TOT_PAY").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(14, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        sum=Integer.parseInt(totEnterList.get(0).get("TOT_PAY").toString()) + Integer.parseInt(totPackageList.get(0).get("TOT_PAY").toString());
	        totSum=sum;
	        headerRow.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(totEnterList.get(1).get("TOT_PAY").toString()) + Integer.parseInt(totPackageList.get(1).get("TOT_PAY").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(totEnterList.get(2).get("TOT_PAY").toString()) + Integer.parseInt(totPackageList.get(2).get("TOT_PAY").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        headerRow.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue(totSum+"");
	        
	        
	        mStartRow=mStartRow+1;
	        headerRow = sheet.createRow(mStartRow);
	        headerRow.createCell(1, XSSFCell.CELL_TYPE_STRING).setCellValue("취소금액 [원]");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum()+3,1,1) );
	        headerRow.createCell(2, XSSFCell.CELL_TYPE_STRING).setCellValue("SSG PAY");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum(),2,2) );
	        
	        headerRow.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue(totEnterList.get(0).get("REFUND_SSG").toString());
	        headerRow.createCell(8, XSSFCell.CELL_TYPE_STRING).setCellValue(totEnterList.get(1).get("REFUND_SSG").toString());
	        headerRow.createCell(9, XSSFCell.CELL_TYPE_STRING).setCellValue(totEnterList.get(2).get("REFUND_SSG").toString());
	        try{
	        	sum=Integer.parseInt(totEnterList.get(0).get("REFUND_SSG").toString()) + Integer.parseInt(totEnterList.get(1).get("REFUND_SSG").toString()) + Integer.parseInt(totEnterList.get(2).get("REFUND_SSG").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(10, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        headerRow.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue(totPackageList.get(0).get("REFUND_SSG").toString());
	        headerRow.createCell(12, XSSFCell.CELL_TYPE_STRING).setCellValue(totPackageList.get(1).get("REFUND_SSG").toString());
	        headerRow.createCell(13, XSSFCell.CELL_TYPE_STRING).setCellValue(totPackageList.get(2).get("REFUND_SSG").toString());
	        try{
	        	sum=Integer.parseInt(totPackageList.get(0).get("REFUND_SSG").toString()) + Integer.parseInt(totPackageList.get(1).get("REFUND_SSG").toString()) + Integer.parseInt(totPackageList.get(2).get("REFUND_SSG").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(14, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        sum=Integer.parseInt(totEnterList.get(0).get("REFUND_SSG").toString()) + Integer.parseInt(totPackageList.get(0).get("REFUND_SSG").toString());
	        totSum=sum;
	        headerRow.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(totEnterList.get(1).get("REFUND_SSG").toString()) + Integer.parseInt(totPackageList.get(1).get("REFUND_SSG").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(totEnterList.get(2).get("REFUND_SSG").toString()) + Integer.parseInt(totPackageList.get(2).get("REFUND_SSG").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        headerRow.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue(totSum+"");
	        
	        
	        mStartRow=mStartRow+1;
	        headerRow = sheet.createRow(mStartRow);
	        headerRow.createCell(2, XSSFCell.CELL_TYPE_STRING).setCellValue("신용카드");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum(),2,2) );
	        
	        headerRow.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue(totEnterList.get(0).get("REFUND_CREDIT").toString());
	        headerRow.createCell(8, XSSFCell.CELL_TYPE_STRING).setCellValue(totEnterList.get(1).get("REFUND_CREDIT").toString());
	        headerRow.createCell(9, XSSFCell.CELL_TYPE_STRING).setCellValue(totEnterList.get(2).get("REFUND_CREDIT").toString());
	        try{
	        	sum=Integer.parseInt(totEnterList.get(0).get("REFUND_CREDIT").toString()) + Integer.parseInt(totEnterList.get(1).get("REFUND_CREDIT").toString()) + Integer.parseInt(totEnterList.get(2).get("REFUND_CREDIT").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(10, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        headerRow.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue(totPackageList.get(0).get("REFUND_CREDIT").toString());
	        headerRow.createCell(12, XSSFCell.CELL_TYPE_STRING).setCellValue(totPackageList.get(1).get("REFUND_CREDIT").toString());
	        headerRow.createCell(13, XSSFCell.CELL_TYPE_STRING).setCellValue(totPackageList.get(2).get("REFUND_CREDIT").toString());
	        try{
	        	sum=Integer.parseInt(totPackageList.get(0).get("REFUND_CREDIT").toString()) + Integer.parseInt(totPackageList.get(1).get("REFUND_CREDIT").toString()) + Integer.parseInt(totPackageList.get(2).get("REFUND_CREDIT").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(14, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        sum=Integer.parseInt(totEnterList.get(0).get("REFUND_CREDIT").toString()) + Integer.parseInt(totPackageList.get(0).get("REFUND_CREDIT").toString());
	        totSum=sum;
	        headerRow.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(totEnterList.get(1).get("REFUND_CREDIT").toString()) + Integer.parseInt(totPackageList.get(1).get("REFUND_CREDIT").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(totEnterList.get(2).get("REFUND_CREDIT").toString()) + Integer.parseInt(totPackageList.get(2).get("REFUND_CREDIT").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        headerRow.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue(totSum+"");
	        
	        
	        mStartRow=mStartRow+1;
	        headerRow = sheet.createRow(mStartRow);
	        headerRow.createCell(2, XSSFCell.CELL_TYPE_STRING).setCellValue("실시간계좌이체");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum(),2,2) );
	        
	        headerRow.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue(totEnterList.get(0).get("REFUND_BANK").toString());
	        headerRow.createCell(8, XSSFCell.CELL_TYPE_STRING).setCellValue(totEnterList.get(1).get("REFUND_BANK").toString());
	        headerRow.createCell(9, XSSFCell.CELL_TYPE_STRING).setCellValue(totEnterList.get(2).get("REFUND_BANK").toString());
	        try{
	        	sum=Integer.parseInt(totEnterList.get(0).get("REFUND_BANK").toString()) + Integer.parseInt(totEnterList.get(1).get("REFUND_BANK").toString()) + Integer.parseInt(totEnterList.get(2).get("REFUND_BANK").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(10, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        headerRow.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue(totPackageList.get(0).get("REFUND_BANK").toString());
	        headerRow.createCell(12, XSSFCell.CELL_TYPE_STRING).setCellValue(totPackageList.get(1).get("REFUND_BANK").toString());
	        headerRow.createCell(13, XSSFCell.CELL_TYPE_STRING).setCellValue(totPackageList.get(2).get("REFUND_BANK").toString());
	        try{
	        	sum=Integer.parseInt(totPackageList.get(0).get("REFUND_BANK").toString()) + Integer.parseInt(totPackageList.get(1).get("REFUND_BANK").toString()) + Integer.parseInt(totPackageList.get(2).get("REFUND_BANK").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(14, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        sum=Integer.parseInt(totEnterList.get(0).get("REFUND_BANK").toString()) + Integer.parseInt(totPackageList.get(0).get("REFUND_BANK").toString());
	        totSum=sum;
	        headerRow.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(totEnterList.get(1).get("REFUND_BANK").toString()) + Integer.parseInt(totPackageList.get(1).get("REFUND_BANK").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(totEnterList.get(2).get("REFUND_BANK").toString()) + Integer.parseInt(totPackageList.get(2).get("REFUND_BANK").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        headerRow.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue(totSum+"");
	        
	        
	        mStartRow=mStartRow+1;
	        headerRow = sheet.createRow(mStartRow);
	        headerRow.createCell(2, XSSFCell.CELL_TYPE_STRING).setCellValue("합계");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum(),2,2) );
	        
	        headerRow.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue(totEnterList.get(0).get("TOT_REFUND").toString());
	        headerRow.createCell(8, XSSFCell.CELL_TYPE_STRING).setCellValue(totEnterList.get(1).get("TOT_REFUND").toString());
	        headerRow.createCell(9, XSSFCell.CELL_TYPE_STRING).setCellValue(totEnterList.get(2).get("TOT_REFUND").toString());
	        try{
	        	sum=Integer.parseInt(totEnterList.get(0).get("TOT_REFUND").toString()) + Integer.parseInt(totEnterList.get(1).get("TOT_REFUND").toString()) + Integer.parseInt(totEnterList.get(2).get("TOT_REFUND").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(10, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        headerRow.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue(totPackageList.get(0).get("TOT_REFUND").toString());
	        headerRow.createCell(12, XSSFCell.CELL_TYPE_STRING).setCellValue(totPackageList.get(1).get("TOT_REFUND").toString());
	        headerRow.createCell(13, XSSFCell.CELL_TYPE_STRING).setCellValue(totPackageList.get(2).get("TOT_REFUND").toString());
	        try{
	        	sum=Integer.parseInt(totPackageList.get(0).get("TOT_REFUND").toString()) + Integer.parseInt(totPackageList.get(1).get("TOT_REFUND").toString()) + Integer.parseInt(totPackageList.get(2).get("TOT_REFUND").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(14, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        sum=Integer.parseInt(totEnterList.get(0).get("TOT_REFUND").toString()) + Integer.parseInt(totPackageList.get(0).get("TOT_REFUND").toString());
	        totSum=sum;
	        headerRow.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(totEnterList.get(1).get("TOT_REFUND").toString()) + Integer.parseInt(totPackageList.get(1).get("TOT_REFUND").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(totEnterList.get(2).get("TOT_REFUND").toString()) + Integer.parseInt(totPackageList.get(2).get("TOT_REFUND").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        headerRow.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue(totSum+"");
	        
	        
	        mStartRow=mStartRow+1;
	        headerRow = sheet.createRow(mStartRow);
	        headerRow.createCell(1, XSSFCell.CELL_TYPE_STRING).setCellValue("위약금 [원]");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum()+3,1,1) );
	        headerRow.createCell(2, XSSFCell.CELL_TYPE_STRING).setCellValue("SSG PAY");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum(),2,2) );
	        
	        headerRow.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue(totEnterList.get(0).get("PANALTY_SSG").toString());
	        headerRow.createCell(8, XSSFCell.CELL_TYPE_STRING).setCellValue(totEnterList.get(1).get("PANALTY_SSG").toString());
	        headerRow.createCell(9, XSSFCell.CELL_TYPE_STRING).setCellValue(totEnterList.get(2).get("PANALTY_SSG").toString());
	        try{
	        	sum=Integer.parseInt(totEnterList.get(0).get("PANALTY_SSG").toString()) + Integer.parseInt(totEnterList.get(1).get("PANALTY_SSG").toString()) + Integer.parseInt(totEnterList.get(2).get("PANALTY_SSG").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(10, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        headerRow.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue(totPackageList.get(0).get("PANALTY_SSG").toString());
	        headerRow.createCell(12, XSSFCell.CELL_TYPE_STRING).setCellValue(totPackageList.get(1).get("PANALTY_SSG").toString());
	        headerRow.createCell(13, XSSFCell.CELL_TYPE_STRING).setCellValue(totPackageList.get(2).get("PANALTY_SSG").toString());
	        try{
	        	sum=Integer.parseInt(totPackageList.get(0).get("PANALTY_SSG").toString()) + Integer.parseInt(totPackageList.get(1).get("PANALTY_SSG").toString()) + Integer.parseInt(totPackageList.get(2).get("PANALTY_SSG").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(14, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        sum=Integer.parseInt(totEnterList.get(0).get("PANALTY_SSG").toString()) + Integer.parseInt(totPackageList.get(0).get("PANALTY_SSG").toString());
	        totSum=sum;
	        headerRow.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(totEnterList.get(1).get("PANALTY_SSG").toString()) + Integer.parseInt(totPackageList.get(1).get("PANALTY_SSG").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(totEnterList.get(2).get("PANALTY_SSG").toString()) + Integer.parseInt(totPackageList.get(2).get("PANALTY_SSG").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        headerRow.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue(totSum+"");
	        
	        
	        mStartRow=mStartRow+1;
	        headerRow = sheet.createRow(mStartRow);
	        headerRow.createCell(2, XSSFCell.CELL_TYPE_STRING).setCellValue("신용카드");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum(),2,2) );
	        
	        headerRow.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue(totEnterList.get(0).get("PANALTY_CREDIT").toString());
	        headerRow.createCell(8, XSSFCell.CELL_TYPE_STRING).setCellValue(totEnterList.get(1).get("PANALTY_CREDIT").toString());
	        headerRow.createCell(9, XSSFCell.CELL_TYPE_STRING).setCellValue(totEnterList.get(2).get("PANALTY_CREDIT").toString());
	        try{
	        	sum=Integer.parseInt(totEnterList.get(0).get("PANALTY_CREDIT").toString()) + Integer.parseInt(totEnterList.get(1).get("PANALTY_CREDIT").toString()) + Integer.parseInt(totEnterList.get(2).get("PANALTY_CREDIT").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(10, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        headerRow.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue(totPackageList.get(0).get("PANALTY_CREDIT").toString());
	        headerRow.createCell(12, XSSFCell.CELL_TYPE_STRING).setCellValue(totPackageList.get(1).get("PANALTY_CREDIT").toString());
	        headerRow.createCell(13, XSSFCell.CELL_TYPE_STRING).setCellValue(totPackageList.get(2).get("PANALTY_CREDIT").toString());
	        try{
	        	sum=Integer.parseInt(totPackageList.get(0).get("PANALTY_CREDIT").toString()) + Integer.parseInt(totPackageList.get(1).get("PANALTY_CREDIT").toString()) + Integer.parseInt(totPackageList.get(2).get("PANALTY_CREDIT").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(14, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        sum=Integer.parseInt(totEnterList.get(0).get("PANALTY_CREDIT").toString()) + Integer.parseInt(totPackageList.get(0).get("PANALTY_CREDIT").toString());
	        totSum=sum;
	        headerRow.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(totEnterList.get(1).get("PANALTY_CREDIT").toString()) + Integer.parseInt(totPackageList.get(1).get("PANALTY_CREDIT").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(totEnterList.get(2).get("PANALTY_CREDIT").toString()) + Integer.parseInt(totPackageList.get(2).get("PANALTY_CREDIT").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        headerRow.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue(totSum+"");
	        
	        
	        mStartRow=mStartRow+1;
	        headerRow = sheet.createRow(mStartRow);
	        headerRow.createCell(2, XSSFCell.CELL_TYPE_STRING).setCellValue("실시간계좌이체");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum(),2,2) );
	        
	        headerRow.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue(totEnterList.get(0).get("PANALTY_BANK").toString());
	        headerRow.createCell(8, XSSFCell.CELL_TYPE_STRING).setCellValue(totEnterList.get(1).get("PANALTY_BANK").toString());
	        headerRow.createCell(9, XSSFCell.CELL_TYPE_STRING).setCellValue(totEnterList.get(2).get("PANALTY_BANK").toString());
	        try{
	        	sum=Integer.parseInt(totEnterList.get(0).get("PANALTY_BANK").toString()) + Integer.parseInt(totEnterList.get(1).get("PANALTY_BANK").toString()) + Integer.parseInt(totEnterList.get(2).get("PANALTY_BANK").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(10, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        headerRow.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue(totPackageList.get(0).get("PANALTY_BANK").toString());
	        headerRow.createCell(12, XSSFCell.CELL_TYPE_STRING).setCellValue(totPackageList.get(1).get("PANALTY_BANK").toString());
	        headerRow.createCell(13, XSSFCell.CELL_TYPE_STRING).setCellValue(totPackageList.get(2).get("PANALTY_BANK").toString());
	        try{
	        	sum=Integer.parseInt(totPackageList.get(0).get("PANALTY_BANK").toString()) + Integer.parseInt(totPackageList.get(1).get("PANALTY_BANK").toString()) + Integer.parseInt(totPackageList.get(2).get("PANALTY_BANK").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(14, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        sum=Integer.parseInt(totEnterList.get(0).get("PANALTY_BANK").toString()) + Integer.parseInt(totPackageList.get(0).get("PANALTY_BANK").toString());
	        totSum=sum;
	        headerRow.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(totEnterList.get(1).get("PANALTY_BANK").toString()) + Integer.parseInt(totPackageList.get(1).get("PANALTY_BANK").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(totEnterList.get(2).get("PANALTY_BANK").toString()) + Integer.parseInt(totPackageList.get(2).get("PANALTY_BANK").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        headerRow.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue(totSum+"");
	        
	        
	        mStartRow=mStartRow+1;
	        headerRow = sheet.createRow(mStartRow);
	        headerRow.createCell(2, XSSFCell.CELL_TYPE_STRING).setCellValue("합계");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum(),2,2) );
	        
	        headerRow.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue(totEnterList.get(0).get("TOT_PANALTY").toString());
	        headerRow.createCell(8, XSSFCell.CELL_TYPE_STRING).setCellValue(totEnterList.get(1).get("TOT_PANALTY").toString());
	        headerRow.createCell(9, XSSFCell.CELL_TYPE_STRING).setCellValue(totEnterList.get(2).get("TOT_PANALTY").toString());
	        try{
	        	sum=Integer.parseInt(totEnterList.get(0).get("TOT_PANALTY").toString()) + Integer.parseInt(totEnterList.get(1).get("TOT_PANALTY").toString()) + Integer.parseInt(totEnterList.get(2).get("TOT_PANALTY").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(10, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        headerRow.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue(totPackageList.get(0).get("TOT_PANALTY").toString());
	        headerRow.createCell(12, XSSFCell.CELL_TYPE_STRING).setCellValue(totPackageList.get(1).get("TOT_PANALTY").toString());
	        headerRow.createCell(13, XSSFCell.CELL_TYPE_STRING).setCellValue(totPackageList.get(2).get("TOT_PANALTY").toString());
	        try{
	        	sum=Integer.parseInt(totPackageList.get(0).get("TOT_PANALTY").toString()) + Integer.parseInt(totPackageList.get(1).get("TOT_PANALTY").toString()) + Integer.parseInt(totPackageList.get(2).get("TOT_PANALTY").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(14, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        sum=Integer.parseInt(totEnterList.get(0).get("TOT_PANALTY").toString()) + Integer.parseInt(totPackageList.get(0).get("TOT_PANALTY").toString());
	        totSum=sum;
	        headerRow.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(totEnterList.get(1).get("TOT_PANALTY").toString()) + Integer.parseInt(totPackageList.get(1).get("TOT_PANALTY").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(totEnterList.get(2).get("TOT_PANALTY").toString()) + Integer.parseInt(totPackageList.get(2).get("TOT_PANALTY").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        headerRow.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue(totSum+"");
	        
	        mStartRow=mStartRow+1;
	        headerRow = sheet.createRow(mStartRow);
	        headerRow.createCell(0, XSSFCell.CELL_TYPE_STRING).setCellValue("예약 후 사용전");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum()+5,0,0) );
	        headerRow.createCell(1, XSSFCell.CELL_TYPE_STRING).setCellValue("건");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum(),1,2) );
	        
	        headerRow.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue(reserveEnterList.get(0).get("TOT_CNT").toString());
	        headerRow.createCell(8, XSSFCell.CELL_TYPE_STRING).setCellValue(reserveEnterList.get(1).get("TOT_CNT").toString());
	        headerRow.createCell(9, XSSFCell.CELL_TYPE_STRING).setCellValue(reserveEnterList.get(2).get("TOT_CNT").toString());
	        try{
	        	sum=Integer.parseInt(reserveEnterList.get(0).get("TOT_CNT").toString()) + Integer.parseInt(reserveEnterList.get(1).get("TOT_CNT").toString()) + Integer.parseInt(reserveEnterList.get(2).get("TOT_CNT").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(10, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        headerRow.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue(reservePackageList.get(0).get("TOT_CNT").toString());
	        headerRow.createCell(12, XSSFCell.CELL_TYPE_STRING).setCellValue(reservePackageList.get(1).get("TOT_CNT").toString());
	        headerRow.createCell(13, XSSFCell.CELL_TYPE_STRING).setCellValue(reservePackageList.get(2).get("TOT_CNT").toString());
	        try{
	        	sum=Integer.parseInt(reservePackageList.get(0).get("TOT_CNT").toString()) + Integer.parseInt(reservePackageList.get(1).get("TOT_CNT").toString()) + Integer.parseInt(reservePackageList.get(2).get("TOT_CNT").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(14, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        sum=Integer.parseInt(reserveEnterList.get(0).get("TOT_CNT").toString()) + Integer.parseInt(reservePackageList.get(0).get("TOT_CNT").toString());
	        totSum=sum;
	        headerRow.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(reserveEnterList.get(1).get("TOT_CNT").toString()) + Integer.parseInt(reservePackageList.get(1).get("TOT_CNT").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(reserveEnterList.get(2).get("TOT_CNT").toString()) + Integer.parseInt(reservePackageList.get(2).get("TOT_CNT").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        headerRow.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue(totSum+"");
	        
	        
	        mStartRow=mStartRow+1;
	        headerRow = sheet.createRow(mStartRow);
	        headerRow.createCell(1, XSSFCell.CELL_TYPE_STRING).setCellValue("이용객 [명]");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum(),1,2) );
	        
	        headerRow.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue(reserveEnterList.get(0).get("TOT_NUM").toString());
	        headerRow.createCell(8, XSSFCell.CELL_TYPE_STRING).setCellValue(reserveEnterList.get(1).get("TOT_NUM").toString());
	        headerRow.createCell(9, XSSFCell.CELL_TYPE_STRING).setCellValue(reserveEnterList.get(2).get("TOT_NUM").toString());
	        try{
	        	sum=Integer.parseInt(reserveEnterList.get(0).get("TOT_NUM").toString()) + Integer.parseInt(reserveEnterList.get(1).get("TOT_NUM").toString()) + Integer.parseInt(reserveEnterList.get(2).get("TOT_NUM").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(10, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        headerRow.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue(reservePackageList.get(0).get("TOT_NUM").toString());
	        headerRow.createCell(12, XSSFCell.CELL_TYPE_STRING).setCellValue(reservePackageList.get(1).get("TOT_NUM").toString());
	        headerRow.createCell(13, XSSFCell.CELL_TYPE_STRING).setCellValue(reservePackageList.get(2).get("TOT_NUM").toString());
	        try{
	        	sum=Integer.parseInt(reservePackageList.get(0).get("TOT_NUM").toString()) + Integer.parseInt(reservePackageList.get(1).get("TOT_NUM").toString()) + Integer.parseInt(reservePackageList.get(2).get("TOT_NUM").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(14, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        sum=Integer.parseInt(reserveEnterList.get(0).get("TOT_NUM").toString()) + Integer.parseInt(reservePackageList.get(0).get("TOT_NUM").toString());
	        totSum=sum;
	        headerRow.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(reserveEnterList.get(1).get("TOT_NUM").toString()) + Integer.parseInt(reservePackageList.get(1).get("TOT_NUM").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(reserveEnterList.get(2).get("TOT_NUM").toString()) + Integer.parseInt(reservePackageList.get(2).get("TOT_NUM").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        headerRow.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue(totSum+"");
	        
	        
	        mStartRow=mStartRow+1;
	        headerRow = sheet.createRow(mStartRow);
	        headerRow.createCell(1, XSSFCell.CELL_TYPE_STRING).setCellValue("결제금액 [원]");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum()+3,1,1) );
	        headerRow.createCell(2, XSSFCell.CELL_TYPE_STRING).setCellValue("SSG PAY");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum(),2,2) );
	        
	        headerRow.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue(reserveEnterList.get(0).get("PAY_SSG").toString());
	        headerRow.createCell(8, XSSFCell.CELL_TYPE_STRING).setCellValue(reserveEnterList.get(1).get("PAY_SSG").toString());
	        headerRow.createCell(9, XSSFCell.CELL_TYPE_STRING).setCellValue(reserveEnterList.get(2).get("PAY_SSG").toString());
	        try{
	        	sum=Integer.parseInt(reserveEnterList.get(0).get("PAY_SSG").toString()) + Integer.parseInt(reserveEnterList.get(1).get("PAY_SSG").toString()) + Integer.parseInt(reserveEnterList.get(2).get("PAY_SSG").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(10, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        headerRow.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue(reservePackageList.get(0).get("PAY_SSG").toString());
	        headerRow.createCell(12, XSSFCell.CELL_TYPE_STRING).setCellValue(reservePackageList.get(1).get("PAY_SSG").toString());
	        headerRow.createCell(13, XSSFCell.CELL_TYPE_STRING).setCellValue(reservePackageList.get(2).get("PAY_SSG").toString());
	        try{
	        	sum=Integer.parseInt(reservePackageList.get(0).get("PAY_SSG").toString()) + Integer.parseInt(reservePackageList.get(1).get("PAY_SSG").toString()) + Integer.parseInt(reservePackageList.get(2).get("PAY_SSG").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(14, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        sum=Integer.parseInt(reserveEnterList.get(0).get("PAY_SSG").toString()) + Integer.parseInt(reservePackageList.get(0).get("PAY_SSG").toString());
	        totSum=sum;
	        headerRow.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(reserveEnterList.get(1).get("PAY_SSG").toString()) + Integer.parseInt(reservePackageList.get(1).get("PAY_SSG").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(reserveEnterList.get(2).get("PAY_SSG").toString()) + Integer.parseInt(reservePackageList.get(2).get("PAY_SSG").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        headerRow.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue(totSum+"");
	        
	        
	        mStartRow=mStartRow+1;
	        headerRow = sheet.createRow(mStartRow);
	        headerRow.createCell(2, XSSFCell.CELL_TYPE_STRING).setCellValue("신용카드");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum(),2,2) );
	        
	        headerRow.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue(reserveEnterList.get(0).get("PAY_CREDIT").toString());
	        headerRow.createCell(8, XSSFCell.CELL_TYPE_STRING).setCellValue(reserveEnterList.get(1).get("PAY_CREDIT").toString());
	        headerRow.createCell(9, XSSFCell.CELL_TYPE_STRING).setCellValue(reserveEnterList.get(2).get("PAY_CREDIT").toString());
	        try{
	        	sum=Integer.parseInt(reserveEnterList.get(0).get("PAY_CREDIT").toString()) + Integer.parseInt(reserveEnterList.get(1).get("PAY_CREDIT").toString()) + Integer.parseInt(reserveEnterList.get(2).get("PAY_CREDIT").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(10, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        headerRow.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue(reservePackageList.get(0).get("PAY_CREDIT").toString());
	        headerRow.createCell(12, XSSFCell.CELL_TYPE_STRING).setCellValue(reservePackageList.get(1).get("PAY_CREDIT").toString());
	        headerRow.createCell(13, XSSFCell.CELL_TYPE_STRING).setCellValue(reservePackageList.get(2).get("PAY_CREDIT").toString());
	        try{
	        	sum=Integer.parseInt(reservePackageList.get(0).get("PAY_CREDIT").toString()) + Integer.parseInt(reservePackageList.get(1).get("PAY_CREDIT").toString()) + Integer.parseInt(reservePackageList.get(2).get("PAY_CREDIT").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(14, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        sum=Integer.parseInt(reserveEnterList.get(0).get("PAY_CREDIT").toString()) + Integer.parseInt(reservePackageList.get(0).get("PAY_CREDIT").toString());
	        totSum=sum;
	        headerRow.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(reserveEnterList.get(1).get("PAY_CREDIT").toString()) + Integer.parseInt(reservePackageList.get(1).get("PAY_CREDIT").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(reserveEnterList.get(2).get("PAY_CREDIT").toString()) + Integer.parseInt(reservePackageList.get(2).get("PAY_CREDIT").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        headerRow.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue(totSum+"");
	        
	        
	        mStartRow=mStartRow+1;
	        headerRow = sheet.createRow(mStartRow);
	        headerRow.createCell(2, XSSFCell.CELL_TYPE_STRING).setCellValue("실시간계좌이체");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum(),2,2) );
	        
	        headerRow.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue(reserveEnterList.get(0).get("PAY_BANK").toString());
	        headerRow.createCell(8, XSSFCell.CELL_TYPE_STRING).setCellValue(reserveEnterList.get(1).get("PAY_BANK").toString());
	        headerRow.createCell(9, XSSFCell.CELL_TYPE_STRING).setCellValue(reserveEnterList.get(2).get("PAY_BANK").toString());
	        try{
	        	sum=Integer.parseInt(reserveEnterList.get(0).get("PAY_BANK").toString()) + Integer.parseInt(reserveEnterList.get(1).get("PAY_BANK").toString()) + Integer.parseInt(reserveEnterList.get(2).get("PAY_BANK").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(10, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        headerRow.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue(reservePackageList.get(0).get("PAY_BANK").toString());
	        headerRow.createCell(12, XSSFCell.CELL_TYPE_STRING).setCellValue(reservePackageList.get(1).get("PAY_BANK").toString());
	        headerRow.createCell(13, XSSFCell.CELL_TYPE_STRING).setCellValue(reservePackageList.get(2).get("PAY_BANK").toString());
	        try{
	        	sum=Integer.parseInt(reservePackageList.get(0).get("PAY_BANK").toString()) + Integer.parseInt(reservePackageList.get(1).get("PAY_BANK").toString()) + Integer.parseInt(reservePackageList.get(2).get("PAY_BANK").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(14, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        sum=Integer.parseInt(reserveEnterList.get(0).get("PAY_BANK").toString()) + Integer.parseInt(reservePackageList.get(0).get("PAY_BANK").toString());
	        totSum=sum;
	        headerRow.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(reserveEnterList.get(1).get("PAY_BANK").toString()) + Integer.parseInt(reservePackageList.get(1).get("PAY_BANK").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(reserveEnterList.get(2).get("PAY_BANK").toString()) + Integer.parseInt(reservePackageList.get(2).get("PAY_BANK").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        headerRow.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue(totSum+"");
	        
	        
	        mStartRow=mStartRow+1;
	        headerRow = sheet.createRow(mStartRow);
	        headerRow.createCell(2, XSSFCell.CELL_TYPE_STRING).setCellValue("합계");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum(),2,2) );
	        
	        headerRow.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue(reserveEnterList.get(0).get("TOT_PAY").toString());
	        headerRow.createCell(8, XSSFCell.CELL_TYPE_STRING).setCellValue(reserveEnterList.get(1).get("TOT_PAY").toString());
	        headerRow.createCell(9, XSSFCell.CELL_TYPE_STRING).setCellValue(reserveEnterList.get(2).get("TOT_PAY").toString());
	        try{
	        	sum=Integer.parseInt(reserveEnterList.get(0).get("TOT_PAY").toString()) + Integer.parseInt(reserveEnterList.get(1).get("TOT_PAY").toString()) + Integer.parseInt(reserveEnterList.get(2).get("TOT_PAY").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(10, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        headerRow.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue(reservePackageList.get(0).get("TOT_PAY").toString());
	        headerRow.createCell(12, XSSFCell.CELL_TYPE_STRING).setCellValue(reservePackageList.get(1).get("TOT_PAY").toString());
	        headerRow.createCell(13, XSSFCell.CELL_TYPE_STRING).setCellValue(reservePackageList.get(2).get("TOT_PAY").toString());
	        try{
	        	sum=Integer.parseInt(reservePackageList.get(0).get("TOT_PAY").toString()) + Integer.parseInt(reservePackageList.get(1).get("TOT_PAY").toString()) + Integer.parseInt(reservePackageList.get(2).get("TOT_PAY").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(14, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        sum=Integer.parseInt(reserveEnterList.get(0).get("TOT_PAY").toString()) + Integer.parseInt(reservePackageList.get(0).get("TOT_PAY").toString());
	        totSum=sum;
	        headerRow.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(reserveEnterList.get(1).get("TOT_PAY").toString()) + Integer.parseInt(reservePackageList.get(1).get("TOT_PAY").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(reserveEnterList.get(2).get("TOT_PAY").toString()) + Integer.parseInt(reservePackageList.get(2).get("TOT_PAY").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        headerRow.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue(totSum+"");
	        
	        
	        mStartRow=mStartRow+1;
	        headerRow = sheet.createRow(mStartRow);
	        headerRow.createCell(0, XSSFCell.CELL_TYPE_STRING).setCellValue("이용완료");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum()+5,0,0) );
	        headerRow.createCell(1, XSSFCell.CELL_TYPE_STRING).setCellValue("건");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum(),1,2) );
	        
	        headerRow.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue(useEnterList.get(0).get("TOT_CNT").toString());
	        headerRow.createCell(8, XSSFCell.CELL_TYPE_STRING).setCellValue(useEnterList.get(1).get("TOT_CNT").toString());
	        headerRow.createCell(9, XSSFCell.CELL_TYPE_STRING).setCellValue(useEnterList.get(2).get("TOT_CNT").toString());
	        try{
	        	sum=Integer.parseInt(useEnterList.get(0).get("TOT_CNT").toString()) + Integer.parseInt(useEnterList.get(1).get("TOT_CNT").toString()) + Integer.parseInt(useEnterList.get(2).get("TOT_CNT").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(10, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        headerRow.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue(usePackageList.get(0).get("TOT_CNT").toString());
	        headerRow.createCell(12, XSSFCell.CELL_TYPE_STRING).setCellValue(usePackageList.get(1).get("TOT_CNT").toString());
	        headerRow.createCell(13, XSSFCell.CELL_TYPE_STRING).setCellValue(usePackageList.get(2).get("TOT_CNT").toString());
	        try{
	        	sum=Integer.parseInt(usePackageList.get(0).get("TOT_CNT").toString()) + Integer.parseInt(usePackageList.get(1).get("TOT_CNT").toString()) + Integer.parseInt(usePackageList.get(2).get("TOT_CNT").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(14, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        sum=Integer.parseInt(useEnterList.get(0).get("TOT_CNT").toString()) + Integer.parseInt(usePackageList.get(0).get("TOT_CNT").toString());
	        totSum=sum;
	        headerRow.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(useEnterList.get(1).get("TOT_CNT").toString()) + Integer.parseInt(usePackageList.get(1).get("TOT_CNT").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(useEnterList.get(2).get("TOT_CNT").toString()) + Integer.parseInt(usePackageList.get(2).get("TOT_CNT").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        headerRow.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue(totSum+"");
	        
	        
	        mStartRow=mStartRow+1;
	        headerRow = sheet.createRow(mStartRow);
	        headerRow.createCell(1, XSSFCell.CELL_TYPE_STRING).setCellValue("이용객 [명]");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum(),1,2) );
	        
	        headerRow.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue(useEnterList.get(0).get("TOT_NUM").toString());
	        headerRow.createCell(8, XSSFCell.CELL_TYPE_STRING).setCellValue(useEnterList.get(1).get("TOT_NUM").toString());
	        headerRow.createCell(9, XSSFCell.CELL_TYPE_STRING).setCellValue(useEnterList.get(2).get("TOT_NUM").toString());
	        try{
	        	sum=Integer.parseInt(useEnterList.get(0).get("TOT_NUM").toString()) + Integer.parseInt(useEnterList.get(1).get("TOT_NUM").toString()) + Integer.parseInt(useEnterList.get(2).get("TOT_NUM").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(10, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        headerRow.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue(usePackageList.get(0).get("TOT_NUM").toString());
	        headerRow.createCell(12, XSSFCell.CELL_TYPE_STRING).setCellValue(usePackageList.get(1).get("TOT_NUM").toString());
	        headerRow.createCell(13, XSSFCell.CELL_TYPE_STRING).setCellValue(usePackageList.get(2).get("TOT_NUM").toString());
	        try{
	        	sum=Integer.parseInt(usePackageList.get(0).get("TOT_NUM").toString()) + Integer.parseInt(usePackageList.get(1).get("TOT_NUM").toString()) + Integer.parseInt(usePackageList.get(2).get("TOT_NUM").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(14, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        sum=Integer.parseInt(useEnterList.get(0).get("TOT_NUM").toString()) + Integer.parseInt(usePackageList.get(0).get("TOT_NUM").toString());
	        totSum=sum;
	        headerRow.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(useEnterList.get(1).get("TOT_NUM").toString()) + Integer.parseInt(usePackageList.get(1).get("TOT_NUM").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(useEnterList.get(2).get("TOT_NUM").toString()) + Integer.parseInt(usePackageList.get(2).get("TOT_NUM").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        headerRow.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue(totSum+"");
	        
	        mStartRow=mStartRow+1;
	        headerRow = sheet.createRow(mStartRow);
	        headerRow.createCell(1, XSSFCell.CELL_TYPE_STRING).setCellValue("결제금액 [원]");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum()+3,1,1) );
	        headerRow.createCell(2, XSSFCell.CELL_TYPE_STRING).setCellValue("SSG PAY");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum(),2,2) );
	        
	        headerRow.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue(useEnterList.get(0).get("PAY_SSG").toString());
	        headerRow.createCell(8, XSSFCell.CELL_TYPE_STRING).setCellValue(useEnterList.get(1).get("PAY_SSG").toString());
	        headerRow.createCell(9, XSSFCell.CELL_TYPE_STRING).setCellValue(useEnterList.get(2).get("PAY_SSG").toString());
	        try{
	        	sum=Integer.parseInt(useEnterList.get(0).get("PAY_SSG").toString()) + Integer.parseInt(useEnterList.get(1).get("PAY_SSG").toString()) + Integer.parseInt(useEnterList.get(2).get("PAY_SSG").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(10, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        headerRow.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue(usePackageList.get(0).get("PAY_SSG").toString());
	        headerRow.createCell(12, XSSFCell.CELL_TYPE_STRING).setCellValue(usePackageList.get(1).get("PAY_SSG").toString());
	        headerRow.createCell(13, XSSFCell.CELL_TYPE_STRING).setCellValue(usePackageList.get(2).get("PAY_SSG").toString());
	        try{
	        	sum=Integer.parseInt(usePackageList.get(0).get("PAY_SSG").toString()) + Integer.parseInt(usePackageList.get(1).get("PAY_SSG").toString()) + Integer.parseInt(usePackageList.get(2).get("PAY_SSG").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(14, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        sum=Integer.parseInt(useEnterList.get(0).get("PAY_SSG").toString()) + Integer.parseInt(usePackageList.get(0).get("PAY_SSG").toString());
	        totSum=sum;
	        headerRow.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(useEnterList.get(1).get("PAY_SSG").toString()) + Integer.parseInt(usePackageList.get(1).get("PAY_SSG").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(useEnterList.get(2).get("PAY_SSG").toString()) + Integer.parseInt(usePackageList.get(2).get("PAY_SSG").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        headerRow.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue(totSum+"");
	        
	        
	        mStartRow=mStartRow+1;
	        headerRow = sheet.createRow(mStartRow);
	        headerRow.createCell(2, XSSFCell.CELL_TYPE_STRING).setCellValue("신용카드");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum(),2,2) );
	        
	        headerRow.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue(useEnterList.get(0).get("PAY_CREDIT").toString());
	        headerRow.createCell(8, XSSFCell.CELL_TYPE_STRING).setCellValue(useEnterList.get(1).get("PAY_CREDIT").toString());
	        headerRow.createCell(9, XSSFCell.CELL_TYPE_STRING).setCellValue(useEnterList.get(2).get("PAY_CREDIT").toString());
	        try{
	        	sum=Integer.parseInt(useEnterList.get(0).get("PAY_CREDIT").toString()) + Integer.parseInt(useEnterList.get(1).get("PAY_CREDIT").toString()) + Integer.parseInt(useEnterList.get(2).get("PAY_CREDIT").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(10, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        headerRow.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue(usePackageList.get(0).get("PAY_CREDIT").toString());
	        headerRow.createCell(12, XSSFCell.CELL_TYPE_STRING).setCellValue(usePackageList.get(1).get("PAY_CREDIT").toString());
	        headerRow.createCell(13, XSSFCell.CELL_TYPE_STRING).setCellValue(usePackageList.get(2).get("PAY_CREDIT").toString());
	        try{
	        	sum=Integer.parseInt(usePackageList.get(0).get("PAY_CREDIT").toString()) + Integer.parseInt(usePackageList.get(1).get("PAY_CREDIT").toString()) + Integer.parseInt(usePackageList.get(2).get("PAY_CREDIT").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(14, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        sum=Integer.parseInt(useEnterList.get(0).get("PAY_CREDIT").toString()) + Integer.parseInt(usePackageList.get(0).get("PAY_CREDIT").toString());
	        totSum=sum;
	        headerRow.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(useEnterList.get(1).get("PAY_CREDIT").toString()) + Integer.parseInt(usePackageList.get(1).get("PAY_CREDIT").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(useEnterList.get(2).get("PAY_CREDIT").toString()) + Integer.parseInt(usePackageList.get(2).get("PAY_CREDIT").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        headerRow.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue(totSum+"");
	        
	        mStartRow=mStartRow+1;
	        headerRow = sheet.createRow(mStartRow);
	        headerRow.createCell(2, XSSFCell.CELL_TYPE_STRING).setCellValue("실시간계좌이체");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum(),2,2) );
	        
	        headerRow.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue(useEnterList.get(0).get("PAY_BANK").toString());
	        headerRow.createCell(8, XSSFCell.CELL_TYPE_STRING).setCellValue(useEnterList.get(1).get("PAY_BANK").toString());
	        headerRow.createCell(9, XSSFCell.CELL_TYPE_STRING).setCellValue(useEnterList.get(2).get("PAY_BANK").toString());
	        try{
	        	sum=Integer.parseInt(useEnterList.get(0).get("PAY_BANK").toString()) + Integer.parseInt(useEnterList.get(1).get("PAY_BANK").toString()) + Integer.parseInt(useEnterList.get(2).get("PAY_BANK").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(10, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        headerRow.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue(usePackageList.get(0).get("PAY_BANK").toString());
	        headerRow.createCell(12, XSSFCell.CELL_TYPE_STRING).setCellValue(usePackageList.get(1).get("PAY_BANK").toString());
	        headerRow.createCell(13, XSSFCell.CELL_TYPE_STRING).setCellValue(usePackageList.get(2).get("PAY_BANK").toString());
	        try{
	        	sum=Integer.parseInt(usePackageList.get(0).get("PAY_BANK").toString()) + Integer.parseInt(usePackageList.get(1).get("PAY_BANK").toString()) + Integer.parseInt(usePackageList.get(2).get("PAY_BANK").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(14, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        sum=Integer.parseInt(useEnterList.get(0).get("PAY_BANK").toString()) + Integer.parseInt(usePackageList.get(0).get("PAY_BANK").toString());
	        totSum=sum;
	        headerRow.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(useEnterList.get(1).get("PAY_BANK").toString()) + Integer.parseInt(usePackageList.get(1).get("PAY_BANK").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(useEnterList.get(2).get("PAY_BANK").toString()) + Integer.parseInt(usePackageList.get(2).get("PAY_BANK").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        headerRow.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue(totSum+"");
	        
	        
	        mStartRow=mStartRow+1;
	        headerRow = sheet.createRow(mStartRow);
	        headerRow.createCell(2, XSSFCell.CELL_TYPE_STRING).setCellValue("합계");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum(),2,2) );
	        
	        headerRow.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue(useEnterList.get(0).get("TOT_PAY").toString());
	        headerRow.createCell(8, XSSFCell.CELL_TYPE_STRING).setCellValue(useEnterList.get(1).get("TOT_PAY").toString());
	        headerRow.createCell(9, XSSFCell.CELL_TYPE_STRING).setCellValue(useEnterList.get(2).get("TOT_PAY").toString());
	        try{
	        	sum=Integer.parseInt(useEnterList.get(0).get("TOT_PAY").toString()) + Integer.parseInt(useEnterList.get(1).get("TOT_PAY").toString()) + Integer.parseInt(useEnterList.get(2).get("TOT_PAY").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(10, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        headerRow.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue(usePackageList.get(0).get("TOT_PAY").toString());
	        headerRow.createCell(12, XSSFCell.CELL_TYPE_STRING).setCellValue(usePackageList.get(1).get("TOT_PAY").toString());
	        headerRow.createCell(13, XSSFCell.CELL_TYPE_STRING).setCellValue(usePackageList.get(2).get("TOT_PAY").toString());
	        try{
	        	sum=Integer.parseInt(usePackageList.get(0).get("TOT_PAY").toString()) + Integer.parseInt(usePackageList.get(1).get("TOT_PAY").toString()) + Integer.parseInt(usePackageList.get(2).get("TOT_PAY").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(14, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        sum=Integer.parseInt(useEnterList.get(0).get("TOT_PAY").toString()) + Integer.parseInt(usePackageList.get(0).get("TOT_PAY").toString());
	        totSum=sum;
	        headerRow.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(useEnterList.get(1).get("TOT_PAY").toString()) + Integer.parseInt(usePackageList.get(1).get("TOT_PAY").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(useEnterList.get(2).get("TOT_PAY").toString()) + Integer.parseInt(usePackageList.get(2).get("TOT_PAY").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        headerRow.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue(totSum+"");
	        
	        mStartRow=mStartRow+1;
	        headerRow = sheet.createRow(mStartRow);
	        headerRow.createCell(0, XSSFCell.CELL_TYPE_STRING).setCellValue("정상취소");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum()+5,0,0) );
	        headerRow.createCell(1, XSSFCell.CELL_TYPE_STRING).setCellValue("건");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum(),1,2) );
	        
	        headerRow.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue(cancelEnterList.get(0).get("TOT_CNT").toString());
	        headerRow.createCell(8, XSSFCell.CELL_TYPE_STRING).setCellValue(cancelEnterList.get(1).get("TOT_CNT").toString());
	        headerRow.createCell(9, XSSFCell.CELL_TYPE_STRING).setCellValue(cancelEnterList.get(2).get("TOT_CNT").toString());
	        try{
	        	sum=Integer.parseInt(cancelEnterList.get(0).get("TOT_CNT").toString()) + Integer.parseInt(cancelEnterList.get(1).get("TOT_CNT").toString()) + Integer.parseInt(cancelEnterList.get(2).get("TOT_CNT").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(10, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        headerRow.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue(cancelPackageList.get(0).get("TOT_CNT").toString());
	        headerRow.createCell(12, XSSFCell.CELL_TYPE_STRING).setCellValue(cancelPackageList.get(1).get("TOT_CNT").toString());
	        headerRow.createCell(13, XSSFCell.CELL_TYPE_STRING).setCellValue(cancelPackageList.get(2).get("TOT_CNT").toString());
	        try{
	        	sum=Integer.parseInt(cancelPackageList.get(0).get("TOT_CNT").toString()) + Integer.parseInt(cancelPackageList.get(1).get("TOT_CNT").toString()) + Integer.parseInt(cancelPackageList.get(2).get("TOT_CNT").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(14, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        sum=Integer.parseInt(cancelEnterList.get(0).get("TOT_CNT").toString()) + Integer.parseInt(cancelPackageList.get(0).get("TOT_CNT").toString());
	        totSum=sum;
	        headerRow.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(cancelEnterList.get(1).get("TOT_CNT").toString()) + Integer.parseInt(cancelPackageList.get(1).get("TOT_CNT").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(cancelEnterList.get(2).get("TOT_CNT").toString()) + Integer.parseInt(cancelPackageList.get(2).get("TOT_CNT").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        headerRow.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue(totSum+"");
	        
	        mStartRow=mStartRow+1;
	        headerRow = sheet.createRow(mStartRow);
	        headerRow.createCell(1, XSSFCell.CELL_TYPE_STRING).setCellValue("이용객 [명]");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum(),1,2) );
	        
	        headerRow.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue(cancelEnterList.get(0).get("TOT_NUM").toString());
	        headerRow.createCell(8, XSSFCell.CELL_TYPE_STRING).setCellValue(cancelEnterList.get(1).get("TOT_NUM").toString());
	        headerRow.createCell(9, XSSFCell.CELL_TYPE_STRING).setCellValue(cancelEnterList.get(2).get("TOT_NUM").toString());
	        try{
	        	sum=Integer.parseInt(cancelEnterList.get(0).get("TOT_NUM").toString()) + Integer.parseInt(cancelEnterList.get(1).get("TOT_NUM").toString()) + Integer.parseInt(cancelEnterList.get(2).get("TOT_NUM").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(10, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        headerRow.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue(cancelPackageList.get(0).get("TOT_NUM").toString());
	        headerRow.createCell(12, XSSFCell.CELL_TYPE_STRING).setCellValue(cancelPackageList.get(1).get("TOT_NUM").toString());
	        headerRow.createCell(13, XSSFCell.CELL_TYPE_STRING).setCellValue(cancelPackageList.get(2).get("TOT_NUM").toString());
	        try{
	        	sum=Integer.parseInt(cancelPackageList.get(0).get("TOT_NUM").toString()) + Integer.parseInt(cancelPackageList.get(1).get("TOT_NUM").toString()) + Integer.parseInt(cancelPackageList.get(2).get("TOT_NUM").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(14, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        sum=Integer.parseInt(cancelEnterList.get(0).get("TOT_NUM").toString()) + Integer.parseInt(cancelPackageList.get(0).get("TOT_NUM").toString());
	        totSum=sum;
	        headerRow.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(cancelEnterList.get(1).get("TOT_NUM").toString()) + Integer.parseInt(cancelPackageList.get(1).get("TOT_NUM").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(cancelEnterList.get(2).get("TOT_NUM").toString()) + Integer.parseInt(cancelPackageList.get(2).get("TOT_NUM").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        headerRow.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue(totSum+"");
	        
	        mStartRow=mStartRow+1;
	        headerRow = sheet.createRow(mStartRow);
	        headerRow.createCell(1, XSSFCell.CELL_TYPE_STRING).setCellValue("취소금액 [원]");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum()+3,1,1) );
	        headerRow.createCell(2, XSSFCell.CELL_TYPE_STRING).setCellValue("SSG PAY");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum(),2,2) );
	        
	        headerRow.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue(cancelEnterList.get(0).get("REFUND_SSG").toString());
	        headerRow.createCell(8, XSSFCell.CELL_TYPE_STRING).setCellValue(cancelEnterList.get(1).get("REFUND_SSG").toString());
	        headerRow.createCell(9, XSSFCell.CELL_TYPE_STRING).setCellValue(cancelEnterList.get(2).get("REFUND_SSG").toString());
	        try{
	        	sum=Integer.parseInt(cancelEnterList.get(0).get("REFUND_SSG").toString()) + Integer.parseInt(cancelEnterList.get(1).get("REFUND_SSG").toString()) + Integer.parseInt(cancelEnterList.get(2).get("REFUND_SSG").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(10, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        headerRow.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue(cancelPackageList.get(0).get("REFUND_SSG").toString());
	        headerRow.createCell(12, XSSFCell.CELL_TYPE_STRING).setCellValue(cancelPackageList.get(1).get("REFUND_SSG").toString());
	        headerRow.createCell(13, XSSFCell.CELL_TYPE_STRING).setCellValue(cancelPackageList.get(2).get("REFUND_SSG").toString());
	        try{
	        	sum=Integer.parseInt(cancelPackageList.get(0).get("REFUND_SSG").toString()) + Integer.parseInt(cancelPackageList.get(1).get("REFUND_SSG").toString()) + Integer.parseInt(cancelPackageList.get(2).get("REFUND_SSG").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(14, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        sum=Integer.parseInt(cancelEnterList.get(0).get("REFUND_SSG").toString()) + Integer.parseInt(cancelPackageList.get(0).get("REFUND_SSG").toString());
	        totSum=sum;
	        headerRow.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(cancelEnterList.get(1).get("REFUND_SSG").toString()) + Integer.parseInt(cancelPackageList.get(1).get("REFUND_SSG").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(cancelEnterList.get(2).get("REFUND_SSG").toString()) + Integer.parseInt(cancelPackageList.get(2).get("REFUND_SSG").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        headerRow.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue(totSum+"");
	        
	        
	        mStartRow=mStartRow+1;
	        headerRow = sheet.createRow(mStartRow);
	        headerRow.createCell(2, XSSFCell.CELL_TYPE_STRING).setCellValue("신용카드");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum(),2,2) );
	        
	        headerRow.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue(cancelEnterList.get(0).get("REFUND_CREDIT").toString());
	        headerRow.createCell(8, XSSFCell.CELL_TYPE_STRING).setCellValue(cancelEnterList.get(1).get("REFUND_CREDIT").toString());
	        headerRow.createCell(9, XSSFCell.CELL_TYPE_STRING).setCellValue(cancelEnterList.get(2).get("REFUND_CREDIT").toString());
	        try{
	        	sum=Integer.parseInt(cancelEnterList.get(0).get("REFUND_CREDIT").toString()) + Integer.parseInt(cancelEnterList.get(1).get("REFUND_CREDIT").toString()) + Integer.parseInt(cancelEnterList.get(2).get("REFUND_CREDIT").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(10, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        headerRow.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue(cancelPackageList.get(0).get("REFUND_CREDIT").toString());
	        headerRow.createCell(12, XSSFCell.CELL_TYPE_STRING).setCellValue(cancelPackageList.get(1).get("REFUND_CREDIT").toString());
	        headerRow.createCell(13, XSSFCell.CELL_TYPE_STRING).setCellValue(cancelPackageList.get(2).get("REFUND_CREDIT").toString());
	        try{
	        	sum=Integer.parseInt(cancelPackageList.get(0).get("REFUND_CREDIT").toString()) + Integer.parseInt(cancelPackageList.get(1).get("REFUND_CREDIT").toString()) + Integer.parseInt(cancelPackageList.get(2).get("REFUND_CREDIT").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(14, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        sum=Integer.parseInt(cancelEnterList.get(0).get("REFUND_CREDIT").toString()) + Integer.parseInt(cancelPackageList.get(0).get("REFUND_CREDIT").toString());
	        totSum=sum;
	        headerRow.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(cancelEnterList.get(1).get("REFUND_CREDIT").toString()) + Integer.parseInt(cancelPackageList.get(1).get("REFUND_CREDIT").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(cancelEnterList.get(2).get("REFUND_CREDIT").toString()) + Integer.parseInt(cancelPackageList.get(2).get("REFUND_CREDIT").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        headerRow.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue(totSum+"");
	        
	        
	        mStartRow=mStartRow+1;
	        headerRow = sheet.createRow(mStartRow);
	        headerRow.createCell(2, XSSFCell.CELL_TYPE_STRING).setCellValue("실시간계좌이체");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum(),2,2) );
	        
	        headerRow.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue(cancelEnterList.get(0).get("REFUND_BANK").toString());
	        headerRow.createCell(8, XSSFCell.CELL_TYPE_STRING).setCellValue(cancelEnterList.get(1).get("REFUND_BANK").toString());
	        headerRow.createCell(9, XSSFCell.CELL_TYPE_STRING).setCellValue(cancelEnterList.get(2).get("REFUND_BANK").toString());
	        try{
	        	sum=Integer.parseInt(cancelEnterList.get(0).get("REFUND_BANK").toString()) + Integer.parseInt(cancelEnterList.get(1).get("REFUND_BANK").toString()) + Integer.parseInt(cancelEnterList.get(2).get("REFUND_BANK").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(10, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        headerRow.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue(cancelPackageList.get(0).get("REFUND_BANK").toString());
	        headerRow.createCell(12, XSSFCell.CELL_TYPE_STRING).setCellValue(cancelPackageList.get(1).get("REFUND_BANK").toString());
	        headerRow.createCell(13, XSSFCell.CELL_TYPE_STRING).setCellValue(cancelPackageList.get(2).get("REFUND_BANK").toString());
	        try{
	        	sum=Integer.parseInt(cancelPackageList.get(0).get("REFUND_BANK").toString()) + Integer.parseInt(cancelPackageList.get(1).get("REFUND_BANK").toString()) + Integer.parseInt(cancelPackageList.get(2).get("REFUND_BANK").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(14, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        sum=Integer.parseInt(cancelEnterList.get(0).get("REFUND_BANK").toString()) + Integer.parseInt(cancelPackageList.get(0).get("REFUND_BANK").toString());
	        totSum=sum;
	        headerRow.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(cancelEnterList.get(1).get("REFUND_BANK").toString()) + Integer.parseInt(cancelPackageList.get(1).get("REFUND_BANK").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(cancelEnterList.get(2).get("REFUND_BANK").toString()) + Integer.parseInt(cancelPackageList.get(2).get("REFUND_BANK").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        headerRow.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue(totSum+"");
	        
	        
	        mStartRow=mStartRow+1;
	        headerRow = sheet.createRow(mStartRow);
	        headerRow.createCell(2, XSSFCell.CELL_TYPE_STRING).setCellValue("합계");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum(),2,2) );
	        
	        headerRow.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue(cancelEnterList.get(0).get("TOT_REFUND").toString());
	        headerRow.createCell(8, XSSFCell.CELL_TYPE_STRING).setCellValue(cancelEnterList.get(1).get("TOT_REFUND").toString());
	        headerRow.createCell(9, XSSFCell.CELL_TYPE_STRING).setCellValue(cancelEnterList.get(2).get("TOT_REFUND").toString());
	        try{
	        	sum=Integer.parseInt(cancelEnterList.get(0).get("TOT_REFUND").toString()) + Integer.parseInt(cancelEnterList.get(1).get("TOT_REFUND").toString()) + Integer.parseInt(cancelEnterList.get(2).get("TOT_REFUND").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(10, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        headerRow.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue(cancelPackageList.get(0).get("TOT_REFUND").toString());
	        headerRow.createCell(12, XSSFCell.CELL_TYPE_STRING).setCellValue(cancelPackageList.get(1).get("TOT_REFUND").toString());
	        headerRow.createCell(13, XSSFCell.CELL_TYPE_STRING).setCellValue(cancelPackageList.get(2).get("TOT_REFUND").toString());
	        try{
	        	sum=Integer.parseInt(cancelPackageList.get(0).get("TOT_REFUND").toString()) + Integer.parseInt(cancelPackageList.get(1).get("TOT_REFUND").toString()) + Integer.parseInt(cancelPackageList.get(2).get("TOT_REFUND").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(14, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        sum=Integer.parseInt(cancelEnterList.get(0).get("TOT_REFUND").toString()) + Integer.parseInt(cancelPackageList.get(0).get("TOT_REFUND").toString());
	        totSum=sum;
	        headerRow.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(cancelEnterList.get(1).get("TOT_REFUND").toString()) + Integer.parseInt(cancelPackageList.get(1).get("TOT_REFUND").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(cancelEnterList.get(2).get("TOT_REFUND").toString()) + Integer.parseInt(cancelPackageList.get(2).get("TOT_REFUND").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        headerRow.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue(totSum+"");
	        
	        
	        mStartRow=mStartRow+1;
	        headerRow = sheet.createRow(mStartRow);
	        headerRow.createCell(0, XSSFCell.CELL_TYPE_STRING).setCellValue("위약취소");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum()+9,0,0) );
	        headerRow.createCell(1, XSSFCell.CELL_TYPE_STRING).setCellValue("건");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum(),1,2) );
	        
	        headerRow.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelEnterList.get(0).get("TOT_CNT").toString());
	        headerRow.createCell(8, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelEnterList.get(1).get("TOT_CNT").toString());
	        headerRow.createCell(9, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelEnterList.get(2).get("TOT_CNT").toString());
	        try{
	        	sum=Integer.parseInt(fcancelEnterList.get(0).get("TOT_CNT").toString()) + Integer.parseInt(fcancelEnterList.get(1).get("TOT_CNT").toString()) + Integer.parseInt(fcancelEnterList.get(2).get("TOT_CNT").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(10, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        headerRow.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelPackageList.get(0).get("TOT_CNT").toString());
	        headerRow.createCell(12, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelPackageList.get(1).get("TOT_CNT").toString());
	        headerRow.createCell(13, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelPackageList.get(2).get("TOT_CNT").toString());
	        try{
	        	sum=Integer.parseInt(fcancelPackageList.get(0).get("TOT_CNT").toString()) + Integer.parseInt(fcancelPackageList.get(1).get("TOT_CNT").toString()) + Integer.parseInt(fcancelPackageList.get(2).get("TOT_CNT").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(14, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        sum=Integer.parseInt(fcancelEnterList.get(0).get("TOT_CNT").toString()) + Integer.parseInt(fcancelPackageList.get(0).get("TOT_CNT").toString());
	        totSum=sum;
	        headerRow.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(fcancelEnterList.get(1).get("TOT_CNT").toString()) + Integer.parseInt(fcancelPackageList.get(1).get("TOT_CNT").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(fcancelEnterList.get(2).get("TOT_CNT").toString()) + Integer.parseInt(fcancelPackageList.get(2).get("TOT_CNT").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        headerRow.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue(totSum+"");
	        
	        mStartRow=mStartRow+1;
	        headerRow = sheet.createRow(mStartRow);
	        headerRow.createCell(1, XSSFCell.CELL_TYPE_STRING).setCellValue("이용객 [명]");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum(),1,2) );
	        
	        headerRow.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelEnterList.get(0).get("TOT_NUM").toString());
	        headerRow.createCell(8, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelEnterList.get(1).get("TOT_NUM").toString());
	        headerRow.createCell(9, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelEnterList.get(2).get("TOT_NUM").toString());
	        try{
	        	sum=Integer.parseInt(fcancelEnterList.get(0).get("TOT_NUM").toString()) + Integer.parseInt(fcancelEnterList.get(1).get("TOT_NUM").toString()) + Integer.parseInt(fcancelEnterList.get(2).get("TOT_NUM").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(10, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        headerRow.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelPackageList.get(0).get("TOT_NUM").toString());
	        headerRow.createCell(12, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelPackageList.get(1).get("TOT_NUM").toString());
	        headerRow.createCell(13, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelPackageList.get(2).get("TOT_NUM").toString());
	        try{
	        	sum=Integer.parseInt(fcancelPackageList.get(0).get("TOT_NUM").toString()) + Integer.parseInt(fcancelPackageList.get(1).get("TOT_NUM").toString()) + Integer.parseInt(fcancelPackageList.get(2).get("TOT_NUM").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(14, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        sum=Integer.parseInt(fcancelEnterList.get(0).get("TOT_NUM").toString()) + Integer.parseInt(fcancelPackageList.get(0).get("TOT_NUM").toString());
	        totSum=sum;
	        headerRow.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(fcancelEnterList.get(1).get("TOT_NUM").toString()) + Integer.parseInt(fcancelPackageList.get(1).get("TOT_NUM").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(fcancelEnterList.get(2).get("TOT_NUM").toString()) + Integer.parseInt(fcancelPackageList.get(2).get("TOT_NUM").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        headerRow.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue(totSum+"");
	        
	        mStartRow=mStartRow+1;
	        headerRow = sheet.createRow(mStartRow);
	        headerRow.createCell(1, XSSFCell.CELL_TYPE_STRING).setCellValue("취소금액 [원]");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum()+3,1,1) );
	        headerRow.createCell(2, XSSFCell.CELL_TYPE_STRING).setCellValue("SSG PAY");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum(),2,2) );
	        
	        headerRow.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelEnterList.get(0).get("REFUND_SSG").toString());
	        headerRow.createCell(8, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelEnterList.get(1).get("REFUND_SSG").toString());
	        headerRow.createCell(9, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelEnterList.get(2).get("REFUND_SSG").toString());
	        try{
	        	sum=Integer.parseInt(fcancelEnterList.get(0).get("REFUND_SSG").toString()) + Integer.parseInt(fcancelEnterList.get(1).get("REFUND_SSG").toString()) + Integer.parseInt(fcancelEnterList.get(2).get("REFUND_SSG").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(10, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        headerRow.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelPackageList.get(0).get("REFUND_SSG").toString());
	        headerRow.createCell(12, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelPackageList.get(1).get("REFUND_SSG").toString());
	        headerRow.createCell(13, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelPackageList.get(2).get("REFUND_SSG").toString());
	        try{
	        	sum=Integer.parseInt(fcancelPackageList.get(0).get("REFUND_SSG").toString()) + Integer.parseInt(fcancelPackageList.get(1).get("REFUND_SSG").toString()) + Integer.parseInt(fcancelPackageList.get(2).get("REFUND_SSG").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(14, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        sum=Integer.parseInt(fcancelEnterList.get(0).get("REFUND_SSG").toString()) + Integer.parseInt(fcancelPackageList.get(0).get("REFUND_SSG").toString());
	        totSum=sum;
	        headerRow.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(fcancelEnterList.get(1).get("REFUND_SSG").toString()) + Integer.parseInt(fcancelPackageList.get(1).get("REFUND_SSG").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(fcancelEnterList.get(2).get("REFUND_SSG").toString()) + Integer.parseInt(fcancelPackageList.get(2).get("REFUND_SSG").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        headerRow.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue(totSum+"");
	        
	        mStartRow=mStartRow+1;
	        headerRow = sheet.createRow(mStartRow);
	        headerRow.createCell(2, XSSFCell.CELL_TYPE_STRING).setCellValue("신용카드");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum(),2,2) );
	        
	        headerRow.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelEnterList.get(0).get("REFUND_CREDIT").toString());
	        headerRow.createCell(8, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelEnterList.get(1).get("REFUND_CREDIT").toString());
	        headerRow.createCell(9, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelEnterList.get(2).get("REFUND_CREDIT").toString());
	        try{
	        	sum=Integer.parseInt(fcancelEnterList.get(0).get("REFUND_CREDIT").toString()) + Integer.parseInt(fcancelEnterList.get(1).get("REFUND_CREDIT").toString()) + Integer.parseInt(fcancelEnterList.get(2).get("REFUND_CREDIT").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(10, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        headerRow.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelPackageList.get(0).get("REFUND_CREDIT").toString());
	        headerRow.createCell(12, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelPackageList.get(1).get("REFUND_CREDIT").toString());
	        headerRow.createCell(13, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelPackageList.get(2).get("REFUND_CREDIT").toString());
	        try{
	        	sum=Integer.parseInt(fcancelPackageList.get(0).get("REFUND_CREDIT").toString()) + Integer.parseInt(fcancelPackageList.get(1).get("REFUND_CREDIT").toString()) + Integer.parseInt(fcancelPackageList.get(2).get("REFUND_CREDIT").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(14, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        sum=Integer.parseInt(fcancelEnterList.get(0).get("REFUND_CREDIT").toString()) + Integer.parseInt(fcancelPackageList.get(0).get("REFUND_CREDIT").toString());
	        totSum=sum;
	        headerRow.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(fcancelEnterList.get(1).get("REFUND_CREDIT").toString()) + Integer.parseInt(fcancelPackageList.get(1).get("REFUND_CREDIT").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(fcancelEnterList.get(2).get("REFUND_CREDIT").toString()) + Integer.parseInt(fcancelPackageList.get(2).get("REFUND_CREDIT").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        headerRow.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue(totSum+"");
	        
	        
	        mStartRow=mStartRow+1;
	        headerRow = sheet.createRow(mStartRow);
	        headerRow.createCell(2, XSSFCell.CELL_TYPE_STRING).setCellValue("실시간계좌이체");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum(),2,2) );
	        
	        headerRow.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelEnterList.get(0).get("REFUND_BANK").toString());
	        headerRow.createCell(8, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelEnterList.get(1).get("REFUND_BANK").toString());
	        headerRow.createCell(9, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelEnterList.get(2).get("REFUND_BANK").toString());
	        try{
	        	sum=Integer.parseInt(fcancelEnterList.get(0).get("REFUND_BANK").toString()) + Integer.parseInt(fcancelEnterList.get(1).get("REFUND_BANK").toString()) + Integer.parseInt(fcancelEnterList.get(2).get("REFUND_BANK").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(10, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        headerRow.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelPackageList.get(0).get("REFUND_BANK").toString());
	        headerRow.createCell(12, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelPackageList.get(1).get("REFUND_BANK").toString());
	        headerRow.createCell(13, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelPackageList.get(2).get("REFUND_BANK").toString());
	        try{
	        	sum=Integer.parseInt(fcancelPackageList.get(0).get("REFUND_BANK").toString()) + Integer.parseInt(fcancelPackageList.get(1).get("REFUND_BANK").toString()) + Integer.parseInt(fcancelPackageList.get(2).get("REFUND_BANK").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(14, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        sum=Integer.parseInt(fcancelEnterList.get(0).get("REFUND_BANK").toString()) + Integer.parseInt(fcancelPackageList.get(0).get("REFUND_BANK").toString());
	        totSum=sum;
	        headerRow.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(fcancelEnterList.get(1).get("REFUND_BANK").toString()) + Integer.parseInt(fcancelPackageList.get(1).get("REFUND_BANK").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(fcancelEnterList.get(2).get("REFUND_BANK").toString()) + Integer.parseInt(fcancelPackageList.get(2).get("REFUND_BANK").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        headerRow.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue(totSum+"");
	        
	        mStartRow=mStartRow+1;
	        headerRow = sheet.createRow(mStartRow);
	        headerRow.createCell(2, XSSFCell.CELL_TYPE_STRING).setCellValue("합계");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum(),2,2) );
	        
	        headerRow.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelEnterList.get(0).get("TOT_REFUND").toString());
	        headerRow.createCell(8, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelEnterList.get(1).get("TOT_REFUND").toString());
	        headerRow.createCell(9, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelEnterList.get(2).get("TOT_REFUND").toString());
	        try{
	        	sum=Integer.parseInt(fcancelEnterList.get(0).get("TOT_REFUND").toString()) + Integer.parseInt(fcancelEnterList.get(1).get("TOT_REFUND").toString()) + Integer.parseInt(fcancelEnterList.get(2).get("TOT_REFUND").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(10, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        headerRow.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelPackageList.get(0).get("TOT_REFUND").toString());
	        headerRow.createCell(12, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelPackageList.get(1).get("TOT_REFUND").toString());
	        headerRow.createCell(13, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelPackageList.get(2).get("TOT_REFUND").toString());
	        try{
	        	sum=Integer.parseInt(fcancelPackageList.get(0).get("TOT_REFUND").toString()) + Integer.parseInt(fcancelPackageList.get(1).get("TOT_REFUND").toString()) + Integer.parseInt(fcancelPackageList.get(2).get("TOT_REFUND").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(14, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        sum=Integer.parseInt(fcancelEnterList.get(0).get("TOT_REFUND").toString()) + Integer.parseInt(fcancelPackageList.get(0).get("TOT_REFUND").toString());
	        totSum=sum;
	        headerRow.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(fcancelEnterList.get(1).get("TOT_REFUND").toString()) + Integer.parseInt(fcancelPackageList.get(1).get("TOT_REFUND").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(fcancelEnterList.get(2).get("TOT_REFUND").toString()) + Integer.parseInt(fcancelPackageList.get(2).get("TOT_REFUND").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        headerRow.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue(totSum+"");
	        
	        mStartRow=mStartRow+1;
	        headerRow = sheet.createRow(mStartRow);
	        headerRow.createCell(1, XSSFCell.CELL_TYPE_STRING).setCellValue("위약금 [원]");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum()+3,1,1) );
	        headerRow.createCell(2, XSSFCell.CELL_TYPE_STRING).setCellValue("SSG PAY");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum(),2,2) );
	        
	        headerRow.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelEnterList.get(0).get("PANALTY_SSG").toString());
	        headerRow.createCell(8, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelEnterList.get(1).get("PANALTY_SSG").toString());
	        headerRow.createCell(9, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelEnterList.get(2).get("PANALTY_SSG").toString());
	        try{
	        	sum=Integer.parseInt(fcancelEnterList.get(0).get("PANALTY_SSG").toString()) + Integer.parseInt(fcancelEnterList.get(1).get("PANALTY_SSG").toString()) + Integer.parseInt(fcancelEnterList.get(2).get("PANALTY_SSG").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(10, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        headerRow.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelPackageList.get(0).get("PANALTY_SSG").toString());
	        headerRow.createCell(12, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelPackageList.get(1).get("PANALTY_SSG").toString());
	        headerRow.createCell(13, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelPackageList.get(2).get("PANALTY_SSG").toString());
	        try{
	        	sum=Integer.parseInt(fcancelPackageList.get(0).get("PANALTY_SSG").toString()) + Integer.parseInt(fcancelPackageList.get(1).get("PANALTY_SSG").toString()) + Integer.parseInt(fcancelPackageList.get(2).get("PANALTY_SSG").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(14, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        sum=Integer.parseInt(fcancelEnterList.get(0).get("PANALTY_SSG").toString()) + Integer.parseInt(fcancelPackageList.get(0).get("PANALTY_SSG").toString());
	        totSum=sum;
	        headerRow.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(fcancelEnterList.get(1).get("PANALTY_SSG").toString()) + Integer.parseInt(fcancelPackageList.get(1).get("PANALTY_SSG").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(fcancelEnterList.get(2).get("PANALTY_SSG").toString()) + Integer.parseInt(fcancelPackageList.get(2).get("PANALTY_SSG").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        headerRow.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue(totSum+"");
	        
	        
	        mStartRow=mStartRow+1;
	        headerRow = sheet.createRow(mStartRow);
	        headerRow.createCell(2, XSSFCell.CELL_TYPE_STRING).setCellValue("신용카드");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum(),2,2) );
	        
	        headerRow.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelEnterList.get(0).get("PANALTY_CREDIT").toString());
	        headerRow.createCell(8, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelEnterList.get(1).get("PANALTY_CREDIT").toString());
	        headerRow.createCell(9, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelEnterList.get(2).get("PANALTY_CREDIT").toString());
	        try{
	        	sum=Integer.parseInt(fcancelEnterList.get(0).get("PANALTY_CREDIT").toString()) + Integer.parseInt(fcancelEnterList.get(1).get("PANALTY_CREDIT").toString()) + Integer.parseInt(fcancelEnterList.get(2).get("PANALTY_CREDIT").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(10, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        headerRow.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelPackageList.get(0).get("PANALTY_CREDIT").toString());
	        headerRow.createCell(12, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelPackageList.get(1).get("PANALTY_CREDIT").toString());
	        headerRow.createCell(13, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelPackageList.get(2).get("PANALTY_CREDIT").toString());
	        try{
	        	sum=Integer.parseInt(fcancelPackageList.get(0).get("PANALTY_CREDIT").toString()) + Integer.parseInt(fcancelPackageList.get(1).get("PANALTY_CREDIT").toString()) + Integer.parseInt(fcancelPackageList.get(2).get("PANALTY_CREDIT").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(14, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        sum=Integer.parseInt(fcancelEnterList.get(0).get("PANALTY_CREDIT").toString()) + Integer.parseInt(fcancelPackageList.get(0).get("PANALTY_CREDIT").toString());
	        totSum=sum;
	        headerRow.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(fcancelEnterList.get(1).get("PANALTY_CREDIT").toString()) + Integer.parseInt(fcancelPackageList.get(1).get("PANALTY_CREDIT").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(fcancelEnterList.get(2).get("PANALTY_CREDIT").toString()) + Integer.parseInt(fcancelPackageList.get(2).get("PANALTY_CREDIT").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        headerRow.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue(totSum+"");
	        
	        
	        mStartRow=mStartRow+1;
	        headerRow = sheet.createRow(mStartRow);
	        headerRow.createCell(2, XSSFCell.CELL_TYPE_STRING).setCellValue("실시간계좌이체");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum(),2,2) );
	        
	        headerRow.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelEnterList.get(0).get("PANALTY_BANK").toString());
	        headerRow.createCell(8, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelEnterList.get(1).get("PANALTY_BANK").toString());
	        headerRow.createCell(9, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelEnterList.get(2).get("PANALTY_BANK").toString());
	        try{
	        	sum=Integer.parseInt(fcancelEnterList.get(0).get("PANALTY_BANK").toString()) + Integer.parseInt(fcancelEnterList.get(1).get("PANALTY_BANK").toString()) + Integer.parseInt(fcancelEnterList.get(2).get("PANALTY_BANK").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(10, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        headerRow.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelPackageList.get(0).get("PANALTY_BANK").toString());
	        headerRow.createCell(12, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelPackageList.get(1).get("PANALTY_BANK").toString());
	        headerRow.createCell(13, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelPackageList.get(2).get("PANALTY_BANK").toString());
	        try{
	        	sum=Integer.parseInt(fcancelPackageList.get(0).get("PANALTY_BANK").toString()) + Integer.parseInt(fcancelPackageList.get(1).get("PANALTY_BANK").toString()) + Integer.parseInt(fcancelPackageList.get(2).get("PANALTY_BANK").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(14, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        sum=Integer.parseInt(fcancelEnterList.get(0).get("PANALTY_BANK").toString()) + Integer.parseInt(fcancelPackageList.get(0).get("PANALTY_BANK").toString());
	        totSum=sum;
	        headerRow.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(fcancelEnterList.get(1).get("PANALTY_BANK").toString()) + Integer.parseInt(fcancelPackageList.get(1).get("PANALTY_BANK").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(fcancelEnterList.get(2).get("PANALTY_BANK").toString()) + Integer.parseInt(fcancelPackageList.get(2).get("PANALTY_BANK").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        headerRow.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue(totSum+"");
	        
	        
	        mStartRow=mStartRow+1;
	        headerRow = sheet.createRow(mStartRow);
	        headerRow.createCell(2, XSSFCell.CELL_TYPE_STRING).setCellValue("합계");
	        sheet.addMergedRegion(new CellRangeAddress(headerRow.getRowNum(),headerRow.getRowNum(),2,2) );
	        
	        headerRow.createCell(7, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelEnterList.get(0).get("TOT_PANALTY").toString());
	        headerRow.createCell(8, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelEnterList.get(1).get("TOT_PANALTY").toString());
	        headerRow.createCell(9, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelEnterList.get(2).get("TOT_PANALTY").toString());
	        try{
	        	sum=Integer.parseInt(fcancelEnterList.get(0).get("TOT_PANALTY").toString()) + Integer.parseInt(fcancelEnterList.get(1).get("TOT_PANALTY").toString()) + Integer.parseInt(fcancelEnterList.get(2).get("TOT_PANALTY").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(10, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        headerRow.createCell(11, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelPackageList.get(0).get("TOT_PANALTY").toString());
	        headerRow.createCell(12, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelPackageList.get(1).get("TOT_PANALTY").toString());
	        headerRow.createCell(13, XSSFCell.CELL_TYPE_STRING).setCellValue(fcancelPackageList.get(2).get("TOT_PANALTY").toString());
	        try{
	        	sum=Integer.parseInt(fcancelPackageList.get(0).get("TOT_PANALTY").toString()) + Integer.parseInt(fcancelPackageList.get(1).get("TOT_PANALTY").toString()) + Integer.parseInt(fcancelPackageList.get(2).get("TOT_PANALTY").toString());
	        }catch(Exception e){
	        	sum= 0;
	        }
	        headerRow.createCell(14, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        sum=Integer.parseInt(fcancelEnterList.get(0).get("TOT_PANALTY").toString()) + Integer.parseInt(fcancelPackageList.get(0).get("TOT_PANALTY").toString());
	        totSum=sum;
	        headerRow.createCell(3, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(fcancelEnterList.get(1).get("TOT_PANALTY").toString()) + Integer.parseInt(fcancelPackageList.get(1).get("TOT_PANALTY").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(4, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        sum=Integer.parseInt(fcancelEnterList.get(2).get("TOT_PANALTY").toString()) + Integer.parseInt(fcancelPackageList.get(2).get("TOT_PANALTY").toString());
	        totSum=totSum+sum;
	        headerRow.createCell(5, XSSFCell.CELL_TYPE_STRING).setCellValue(sum+"");
	        
	        headerRow.createCell(6, XSSFCell.CELL_TYPE_STRING).setCellValue(totSum+"");
	        
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        try {
	            mWorkbook.write(out);
	            bytes = out.toByteArray();
	            out.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	            System.out.println(e.toString() );
	        }

	        String fileName = "총괄장"+ "_" + Util.getTodayTime();
			setFileNameToResponse(request, response, fileName);
			
			response.setContentLength(bytes.length);
			response.setContentType("application/vnd.ms-excel");
			
			String point = "";
			if(param.get("srch_point").equals("POINT1") ){ 
				point = "지점 : 하남";
			} else {
				point = "지점 : 고양";
			}
			
			String srch_reg = "";
			if(param.get("srch_reg_s").equals(null) || param.get("srch_reg_s").equals("")) {
				srch_reg = "";
			} else {
				srch_reg = ", 방문일 : " + param.get("srch_reg_s") + "~" + param.get("srch_reg_e");	
			}
			
			//관리자 사용기록 로그 #############################################
			String etc = "엑셀다운로드";
			Map logParam = new HashMap();
			logParam.put("point_code", "POINT01");
			logParam.put("action", "E");
			/* 20180315 수정중
			 logParam.put("data_num", param.get("reserve_uid"));*/
			
			logParam.put("data_num", "엑셀을 다운받으셨습니다. [검색내용] 지점 : " + point  + ", 결제일 : " + param.get("srch_pay_s") + "~" + param.get("srch_pay_e") + srch_reg);
			logParam.put("data_url", "url");
			logParam.put("ins_ip", request.getRemoteAddr().toString());	
			logParam.put("ins_admin_id", admin.get("SESSION_ADMIN_ID"));	
			logParam.put("admin_nm", admin.get("SESSION_ADMIN_NM"));	
			logParam.put("admin_uid", admin.get("SESSION_ADMIN_UID"));	
			logParam.put("access_menu_uid", "1805");	
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
