package com.soft.web.controller.admin;

import java.util.HashMap;
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

import com.soft.web.base.GenericController;
import com.soft.web.controller.admin.AdminHomeController;
import com.soft.web.service.admin.AdminStatisticsService;
import com.soft.web.util.Util;

@Controller
@RequestMapping({"/secu_admaf"})
public class AdminHomeController extends GenericController {

	protected Logger logger = LoggerFactory.getLogger(AdminHomeController.class);
	
	@Autowired
	AdminStatisticsService service;

	@RequestMapping(value="/admin/index.af", method = RequestMethod.GET)
	public String login(@RequestParam Map param, ModelMap model, HttpSession session, HttpServletRequest request, HttpServletResponse response){

		Map sqlMap = new HashMap();
		//-- 오늘 날짜 기준 7일 이전
		String today = Util.getTimeStamp(1); //-- 0000-00-00 형식
		String oldday = Util.unStripDate(Util.getNextDate(today.replaceAll("-",""), -6));

		sqlMap.put("sd", oldday.replaceAll("-",""));
		sqlMap.put("ed", today.replaceAll("-",""));
		model.addAttribute("resultsVisitor", service.listVisitorPeriod(sqlMap));		
		
		return "/admin/index";
	}
	
}
