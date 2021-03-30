package com.soft.web.controller.front;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.soft.web.base.GenericController;
import com.soft.web.service.front.StatisticsVisitorService;
import com.soft.web.util.PageUtil;
import com.soft.web.util.Util;

@Controller
public class StatisticsVisitorController extends GenericController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	StatisticsVisitorService service;
	
	String pageSize = "10";
	String blockSize = "10";
	
	// 시간별 통계
    public String times(@RequestParam Map param, ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		Map<String,String> todays = Util.makeToday();
		if(param.get("YEARS")	 == null) param.put("YEARS", todays.get("year"));
		if(param.get("MONTHS")	 == null) param.put("MONTHS", todays.get("month"));
		if(param.get("DAYS")	 == null) param.put("DAYS", todays.get("day"));
		
		model.addAttribute("resultParam", param);
		model.addAttribute("results", service.listVisitorTime(param));
		
		return "/oktomato/stats/sub_01";
	}
	
	//-- 일별통계
    public String days(@RequestParam Map param, ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		List<Map<String,String>> results = new ArrayList<Map<String,String>>();
		Map<String,String> todays = Util.makeToday();
		if(param.get("YEARS")	 == null) param.put("YEARS", todays.get("year"));
		if(param.get("MONTHS")	 == null) param.put("MONTHS", todays.get("month"));
		int endDay = Util.getEndDay(Util.getInt(todays.get("year") ), Util.getInt(todays.get("month") ) );

		List list = service.listVisitorDay(param);
		int listCnt = list.size();
		boolean isAdd = true;
		String j;
		for(int i = 1; i <= endDay; i++){
			if(i < 10) j = "0" + i;
			else j = "" + i;
			Map<String,String> result = new HashMap<String,String>();
			isAdd = true;
			for(int k = 0; k < listCnt; k++){
				Map<String,String> listMap = (Map)list.get(k);
				if(i == Util.getInt(String.valueOf(listMap.get("DAYS")))){
					result.put("YYYYMMDD",todays.get("year")+"."+todays.get("month")+"."+String.valueOf(listMap.get("DAYS")));
					result.put("CNT", String.valueOf(listMap.get("VISITOR_CNT")));
					results.add(result);
					isAdd = false;
					continue;
				}
			}
			if(isAdd){
				result.put("YYYYMMDD",todays.get("year")+"."+todays.get("month")+"."+j);
				result.put("CNT", "0");
				results.add(result);
			}
		}
		
		model.addAttribute("resultParam", param);
		model.addAttribute("results", results);
		
		return "/oktomato/stats/sub_02";
	}

	//-- 월별통계
    public String months(@RequestParam Map param, ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		List<Map<String,String>> results = new ArrayList<Map<String,String>>();
		Map<String,String> todays = Util.makeToday();
		if(param.get("YEARS")	 == null) param.put("YEARS", todays.get("year"));

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
				if(i == Util.getInt(String.valueOf(listMap.get("MONTHS")))){
					logger.debug(todays.get("year")+"."+String.valueOf(listMap.get("MONTHS")) + ":" + String.valueOf(listMap.get("MONTHS_TOTAL")));
					result.put("YYYYMM",todays.get("year")+"."+String.valueOf(listMap.get("MONTHS")) );
					result.put("CNT", String.valueOf(listMap.get("MONTHS_TOTAL")));
					results.add(result);
					isAdd = false;
					continue;
				}
			}
			if(isAdd){
				result.put("YYYYMM",todays.get("year")+"."+j);
				result.put("CNT", "0");
				results.add(result);
			}
		}
		
		model.addAttribute("resultParam", param);
		model.addAttribute("results", results);
		
		return "/oktomato/stats/sub_03";
	}

	//-- 접속환경통계
    public String enviroment(@RequestParam Map param, ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

		model.addAttribute("resultParam", param);
		model.addAttribute("resultsOs", service.listVisitorOs(param));
		model.addAttribute("resultsBrowser", service.listVisitorBrowser(param));
		model.addAttribute("resultsScreen", service.listVisitorScreen(param));
		
		return "/oktomato/stats/sub_04";
	}

	//-- 유입경로통계
    public String referrer(@RequestParam Map param, ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		pageSize = "20";
		int page = 0;
		int pageListSize = Util.getInt(pageSize);
		int blockListSize = Util.getInt(blockSize);
		page = param.get("page") == null ?  page = 1 : Util.getInt(param.get("page").toString());
		param.put("rows", pageSize);
		param.put("page", page);

		int totalCount = service.getTotalCountReferer(param);
		
		PageUtil pu = new PageUtil(page, totalCount, pageListSize, blockListSize);
		
		model.addAttribute("resultParam", param);
		model.addAttribute("totalCount", Util.numberFormat(totalCount));
		model.addAttribute("pu", pu);
 		model.addAttribute("results", service.listVisitorReferer(param));
		
		return "/oktomato/stats/sub_05";
	}

	@RequestMapping(value = "/statistics/statistics_visitor.af")
    public String statisticsVisitor(@RequestParam Map param, ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		Map<String,String> todays = Util.makeToday();
		
		param.put("YEARS", todays.get("year"));
		param.put("MONTHS", todays.get("month"));
		param.put("DAYS", todays.get("day"));
		param.put("WEEKS", todays.get("week"));
		param.put("HOUR", todays.get("hour"));
		
		param.put("PAGE_VIEW_CNT", "0");
		param.put("VISITOR_CNT", "1");
		param.put("HOUR_00", "0");
		param.put("HOUR_01", "0");
		param.put("HOUR_02", "0");
		param.put("HOUR_03", "0");
		param.put("HOUR_04", "0");
		param.put("HOUR_05", "0");
		param.put("HOUR_06", "0");
		param.put("HOUR_07", "0");
		param.put("HOUR_08", "0");
		param.put("HOUR_09", "0");
		param.put("HOUR_10", "0");
		param.put("HOUR_11", "0");
		param.put("HOUR_12", "0");
		param.put("HOUR_13", "0");
		param.put("HOUR_14", "0");
		param.put("HOUR_15", "0");
		param.put("HOUR_16", "0");
		param.put("HOUR_17", "0");
		param.put("HOUR_18", "0");
		param.put("HOUR_19", "0");
		param.put("HOUR_20", "0");
		param.put("HOUR_21", "0");
		param.put("HOUR_22", "0");
		param.put("HOUR_23", "0");
		
		param.put("HOUR_"+param.get("HOUR"), "1");
		
		param.put("SERVER_NAME", request.getServerName());
		
		if(param.get("PC_CNT")		 == null) param.put("PC_CNT","0");
		if(param.get("MOBILE_CNT")	 == null) param.put("MOBILE_CNT","0");
		if(param.get("DEVICE")		 == null) param.put("DEVICE","");
		if(param.get("SCREEN_X")	 == null) param.put("SCREEN_X","");
		if(param.get("SCREEN_Y")	 == null) param.put("SCREEN_Y","");
		if(param.get("EI")			 == null) param.put("EI","");
		if(param.get("EI_VER")		 == null) param.put("EI_VER","");
		if(param.get("OS")			 == null) param.put("OS","");
		
		// param.put("REFERER","web favorites or direct input"); 한글영어로 수정
		if(param.get("REFERER")	 == null || "".equals(param.get("REFERER").toString())) param.put("REFERER","web favorites or direct input");
		
		if(!"".equals(param.get("EI").toString()) || !"".equals(param.get("EI_VER").toString())){
			param.put("BROWSER", param.get("EI") + " " + param.get("EI_VER"));
		}		
		
		try{
			service.visitorDataInsert(param);
		}
		catch(Throwable e){
			
		}
		return null;
    }
	
	@RequestMapping(value = "/statistics/statistics_page_view.af", method=RequestMethod.POST)
    public String statisticsPageView(@RequestParam Map param, ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		param = Util.setMapHex(param); //-- 문자열 -> 16진수

		Map<String,String> todays = Util.makeToday();
		
		param.put("YEARS", todays.get("year"));
		param.put("MONTHS", todays.get("month"));
		param.put("DAYS", todays.get("day"));
		param.put("WEEKS", todays.get("week"));
		param.put("HOUR", todays.get("hour"));

		service.updatePage(param);
		
 		return null;
    }

}
