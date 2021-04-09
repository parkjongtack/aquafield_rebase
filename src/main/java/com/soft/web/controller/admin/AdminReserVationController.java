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
	//private String pg_store_code = "2001106041";//<!-- real �����ڵ� 2001106041 --> <!-- test �����ڵ�  2999199999-->
	
	@Autowired
	ReservationService reservationService;
	
	@Autowired
	FrontReservationService frontReservationService;
	
	@Autowired
	AdminAdminAuthService adminAdminAuthService;
	
	/*
	 * ���೻�� �Ķ���� ����
	 * page : ������, reserve_uid : �����ȣ, category : ī�װ�, 
	 * mem_nm : ȸ����, mob_no : ȸ����ȭ��ȣ, reserve_state : �������, 
	 * srch_reg_s : �湮�� ����, srch_reg_e : �湮�� ����, mem_uid : ȸ��������ȣ
	 */
	protected String[] pageParamList = {"page","reserve_uid","category","mem_nm","mob_no", "reserve_state", "srch_reg_s", "srch_reg_e", "mem_uid"};
		
	
	/*
	 * ���೻�� ���� ���
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/admin/reservation/index.af")
	public String index(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//���� �޴�
		String sMenuCode = "1301";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null; 
		
		if(admin.get("SESSION_ADMIN_ID").indexOf("oktomato.net") != -1){
			param.put("noPmtYN", "Y");
		}
		
		int page = 0;
		//-- xss üũ
		param = Util.chkParam(pageParamList, param);

		//����¡
		page = (param.get("page") == null || "".equals(param.get("page").toString() ) ) ?  page = 1 : Util.getInt(param.get("page").toString());
		if(page < 1){
			page = 1;
			param.put("page",String.valueOf(page));
		}
		//-- 16���� ����
		param = Util.setMapHex(param);		
		
		int totCnt = reservationService.reservationListCnt(param);
		PageUtil paging = new PageUtil(page, totCnt, pageListSize, blockListSize);
		param.put("pageStartRow", paging.getPageStartRow());
		param.put("pageListSize", pageListSize);
		param.put("page", String.valueOf(page));
		
		//�����ȸ	
		List<Map> rList = reservationService.reservationList(param);
		//�ڵ� ��ȸ
		List codeRSRVT_TYPE = super.getCommonCodes("RSRVT_TYPE");
		//���� ��ȸ
		List codePOINT_CODE = super.getCommonCodes("POINT_CODE");
		
		//�ʱ� ���� ���� üũ
		if(param.get("first") != null){
			model.addAttribute("first", param.get("first").toString());	
		}
		// Util.pageParamMap2 �� post ��Ŀ�, Util.pageParamMap �� get ��Ŀ� (URLEncoder ������)
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
	 * �������� �ٷκ��� ajax
	 * jsp �� return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/admin/reservation/ajax_res_view.af")
	public String ajax_res_view(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		
		//-- xss üũ
		param = Util.chkParam(pageParamList, param);
		
		Map vo = reservationService.reservationDetail(param);
		
		model.addAttribute("resultParam", Util.pageParamMap2(pageParamList, param) );
		model.addAttribute("vo", vo);
 		model.addAttribute("MEMINFOYN", admin.get("SESSION_MEMINFOYN").toString());
 		
		//������ ����� �α� #############################################
		String etc = vo.get("ORDER_NUM").toString()+"("+ vo.get("ORDER_NM").toString() +") ����";;
		Map logParam = new HashMap();
		logParam.put("point_code", "POINT01");
		logParam.put("action", "R");
		/*logParam.put("data_num", param.get("reserve_uid"));*/
		logParam.put("data_num", vo.get("MEM_NM").toString() + "ȸ������ ���������� �����ϼ̽��ϴ�.");
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
	 * �������� �ٷκ����˾����� ���ó�� ��ư ajax
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/admin/reservation/resCompleteUse.af")
	public int resCompleteUse(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		
		//-- xss üũ
		param = Util.chkParam(pageParamList, param);
		
		param.put("upd_id", admin.get("SESSION_ADMIN_ID"));
		// ���ó��
		int result = reservationService.reservationUseChnge(param);
		
		//20180315 �߰�
		Map vo = reservationService.reservationDetail(param);
		
		
		//������ ����� �α� #############################################
		String etc = param.get("order_num").toString()+"("+ param.get("order_nm").toString() +") ���� ���ó�� ����";;
		Map logParam = new HashMap();
		logParam.put("point_code", "POINT01");
		logParam.put("action", "U");
		/*20180315 ���� Ȯ���ϰ� �����.
		 * logParam.put("data_num", param.get("reserve_uid"));*/
		logParam.put("data_num", vo.get("MEM_NM").toString() + "ȸ������ ���� ���ó�� ����");
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
	 * ���೻�� ���� �̵�
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/admin/reservation/res_edit.af")
	public String res_edit(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//���� �޴�
		String sMenuCode = "1301";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null; 
		
		//-- xss üũ
		param = Util.chkParam(pageParamList, param);
		model.addAttribute("resultParam", Util.pageParamMap2(pageParamList, param) );
		
		Map vo = reservationService.reservationDetail(param);
		model.addAttribute("vo", vo);
				
		Map pgResultInfo = frontReservationService.pgResultInfo(vo.get("PG_RESULT").toString());

		String r_TYPE = "";		
		String authty = "";
		
		int compareVal = Integer.parseInt(pgResultInfo.get("TR_NO").toString().substring(0,1));		
		switch (compareVal) {
		case 1: r_TYPE = "ī��";authty = "1010";
			break;
		case 2: r_TYPE = "�ǽð�������ü";
			//���� ���� üũ
			Date toDay = new Date();
			SimpleDateFormat transFormat = new SimpleDateFormat("yyyy.MM.dd");
			String toDayToString = transFormat.format(toDay).trim();
				
			String pmtDate = vo.get("PAYMENT_DATE").toString().trim();
			
			if(toDayToString.equals(pmtDate)){
				authty = "2010";
			}else{
				authty = "2030";
			}
			logger.debug("############## �ǽð�������ü ��� �ڵ尪>> authty : " + authty);
			break;
		case 4: r_TYPE = "SSG PAY";authty = "4110";
			break;				
		}		

		pgResultInfo.put("r_TYPE", r_TYPE);
		pgResultInfo.put("authty", authty);
		pgResultInfo.put("pgStore", config.getProperty("pg.store.code"));	
		model.addAttribute("pgResultInfo", pgResultInfo);
		
		//�ڵ� ��ȸ
		List codeRSRVT_TYPE = super.getCommonCodes("RSRVT_TYPE");
		model.addAttribute("codeRSRVT_TYPE", codeRSRVT_TYPE);	
 		model.addAttribute("MEMINFOYN", admin.get("SESSION_MEMINFOYN").toString());//ȸ�� on/off ����
 		
		//������ ����� �α� #############################################
		String etc = vo.get("ORDER_NUM").toString()+"("+ vo.get("ORDER_NM").toString() +") ����";;
		Map logParam = new HashMap();
		logParam.put("point_code", "POINT01");
		logParam.put("action", "R");
		/* 20180315 ����
		 * logParam.put("data_num", param.get("reserve_uid"));*/
		logParam.put("data_num", vo.get("MEM_NM").toString() + "ȸ������ ���� ���������� ����");
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
	 * ���೻�� ���������� ���� action ajax
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/admin/reservation/res_update.af")
	public int res_update(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		
		//-- xss üũ
		param = Util.chkParam(pageParamList, param);
		
		param.put("upd_id", admin.get("SESSION_ADMIN_ID"));
		// ���೻�� ����
		int result = reservationService.reservationUpdate(param);
		
		//20180315 �߰�
		Map vo = reservationService.reservationDetail(param);
		
		//������ ����� �α� #############################################
		String etc = param.get("order_num").toString()+"("+ param.get("order_nm").toString() +") ���� ����";;
		Map logParam = new HashMap();
		logParam.put("point_code", "POINT01");
		logParam.put("action", "U");
		/* 20180315 ������
		 * logParam.put("data_num", param.get("reserve_uid"));*/
		logParam.put("data_num", vo.get("MEM_NM").toString() + "ȸ������ ���೻�� ���� �ϼ̽��ϴ�.");
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
	 * �˻����� ���� ���� �ٿ�ε�
	 */
	@RequestMapping("/admin/reservation/res_excel_down.af")
	public @ResponseBody byte[] res_excel_down(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response ) throws UnsupportedEncodingException{
		 
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		String memInfoYN = admin.get("SESSION_MEMINFOYN").toString();	
		List<Object> header = new ArrayList<Object>();		
		List<List<Object>> data = new ArrayList<List<Object>>();		
		int i = 0;
		
		//-- 16���� ����
		param = Util.setMapHex(param);
		//����¡ ���� �������
		List<Map> rList = reservationService.reservationListAll(param);
		
		try{
			//-- ���� ����
			header.add("������");
			header.add("����");
			header.add("�����ȣ");
			header.add("ȸ����");
			header.add("�޴�����ȣ");
			header.add("���û�ǰ");
			header.add("��");
			header.add("������");
			header.add("����");
			
			for(Map map : rList){
				List<Object> obj = new ArrayList<Object>();
				obj.add(map.get("PAYMENT_DATE"));
				obj.add(map.get("POINT_NM"));
				obj.add(map.get("ORDER_NUM"));
				
				//20160830 ����� ���� ���� ������ ���� ����ũ �Ѱ� ���� ���Ѱ� ���� �����ϴ� ���� �߰��Ǿ���
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
				obj.add(map.get("TOTNUM") +" ��");
				obj.add(map.get("RESERVE_DATE"));
				obj.add(map.get("RESERVE_STATE_NM"));
				
				data.add(obj);
			}
			
			ExcelUtil excelUtil = new ExcelUtil();
			byte[] bytes = excelUtil.makeXlsx(header, data);
			response.setHeader("Content-Disposition", "attachment; filename=" + Util.encodeString("���೻��") + "_" + Util.getTodayTime() + ".xlsx");
			response.setContentLength(bytes.length);
			response.setContentType("application/vnd.ms-excel");
			
			
			String status = "";
			if(param.get("reserve_state").equals("ING")) {
				status = "����";
			} else if(param.get("reserve_state").equals("USE")) {
				status = "�̿�Ϸ�";
			} else if(param.get("reserve_state").equals("NOUSE")) {
				status = "�̻��";
			} else if(param.get("reserve_state").equals("CANCEL")) {
				status = "�������";
			} else if(param.get("reserve_state").equals("FCANCEL")) {
				status = "�������";
			} else if(param.get("reserve_state").equals("NOPMT")) {
				status = "������";
			} else {
				status = "���þ���";
			}
			
			String name = "";
			if(param.get("mem_nm").equals("") || param.get("mem_nm").equals(null)) {
				name = "";
			} else {
				name = "ȸ���� : "+ param.get("mem_nm");
			}
			
			//������ ����� �α� #############################################
			String etc = "�����ٿ�ε�";
			Map logParam = new HashMap();
			logParam.put("point_code", "POINT01");
			logParam.put("action", "E");
			/* 20180315 ������
			 logParam.put("data_num", param.get("reserve_uid"));*/
			
			logParam.put("data_num", "������ �ٿ�����̽��ϴ�. [�˻�����] ���� : " + status +", �湮�� : " + param.get("srch_reg_s") + "~" + param.get("srch_reg_e"));
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
