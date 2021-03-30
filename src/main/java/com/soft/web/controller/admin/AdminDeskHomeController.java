package com.soft.web.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

import com.soft.web.base.GenericController;
import com.soft.web.service.admin.ReservationService;
import com.soft.web.util.AquaDateUtil;

@Controller
@RequestMapping({"/secu_admaf"})
public class AdminDeskHomeController extends GenericController {

	protected Logger logger = LoggerFactory.getLogger(AdminDeskHomeController.class);
	
	@Autowired
	ReservationService reservationService;

	@RequestMapping(value="/admdesk/index.af")
	public String login(@RequestParam Map param, ModelMap model, HttpSession session, HttpServletRequest request, HttpServletResponse response){
		
		String search_date = "";

		if(param.size() < 1){
			String today = AquaDateUtil.getCurrentDate("");
			today = today.substring(0, 4) + "-" + today.substring(4, 6) + "-" + today.substring(6, 8);
			search_date = today;

		}else{
			search_date = param.get("search_date").toString();
		}
		
		Map parameter = new HashMap();
		parameter.put("search_date", search_date);
		parameter.put("status", "ING");
		parameter.put("point", "POINT01");
		// 상품별 사람 명수
		List<Map> reservePersonCntOfProd = reservationService.reservePersonCntOfProd(parameter);
		
		int intCnt = 0;
		String itemName = "-";
		List<Map> resultList = new ArrayList<Map>();
		for (int i = 1; i < 6; i++) {			
			Map result = new HashMap();
			
			result.put("SUBCODE", "PRD00"+i);
			result.put("ITEMNAME", "-");
			result.put("CNT", 0);
			
			resultList.add(result);
		}		
		
		for (Iterator iterator = reservePersonCntOfProd.iterator(); iterator.hasNext();) {
			Map map = (Map) iterator.next();
			
			for (Map resultMap : resultList) {
				if(resultMap.get("SUBCODE").equals(map.get("ITEM_SUBCODE"))){
					resultMap.put("ITEMNAME", map.get("ITEM_NM").toString());
					resultMap.put("CNT", Integer.parseInt(map.get("TOTAL_CNT").toString()));
				}
			}
		}		
				
		//예약상태별 사람 명수
		int reservePersonCntOfING = reservationService.reservePersonCnt(parameter);
		parameter.put("status", "USE");
		int reservePersonCntOfUSE = reservationService.reservePersonCnt(parameter);
		
		model.addAttribute("resultParam", param);
		model.addAttribute("cntList", reservePersonCntOfProd);
		model.addAttribute("resultList", resultList);
		model.addAttribute("ing", reservePersonCntOfING);
		model.addAttribute("use", reservePersonCntOfUSE);			
		
		return "/admdesk/index";
	}
	
}
