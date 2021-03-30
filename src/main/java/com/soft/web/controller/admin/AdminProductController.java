package com.soft.web.controller.admin;

/**
 * 2016.07.25 KJH
 * 상품관리
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soft.web.base.CommonConstant;
import com.soft.web.base.GenericController;
import com.soft.web.service.admin.AdminAdminAuthService;
import com.soft.web.service.admin.ProductService;
import com.soft.web.service.common.CommonService;
import com.soft.web.util.AquaDataUtil;
import com.soft.web.util.AquaDateUtil;
import com.soft.web.util.Util;

@Controller
@RequestMapping({"/secu_admaf"})
public class AdminProductController extends GenericController {

	protected Logger logger = LoggerFactory.getLogger(AdminProductController.class);
	
	@Autowired
	CommonService commonService;	
	
	@Autowired
	ProductService productService;	
	
	@Autowired
	AdminAdminAuthService adminAdminAuthService;
	
	@RequestMapping(value = "/admin/product/prodlist.af")
    public String prodlist(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//왼쪽 메뉴
		String sMenuCode = "1401";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null; 
		
	    if(!param.isEmpty() && param.get("year") != null){
	    	model.addAttribute("year", param.get("year"));
	    }else{
	    	String nowTime = AquaDateUtil.getToday();
	    	model.addAttribute("year", nowTime.substring(0, 4));
	    }
		
		List<Map<String, Object>> codePoint_code = super.getCommonCodes("POINT_CODE");
	    model.addAttribute("codePoint_code", codePoint_code);
	    
	    String srch_point = "";
	    if(param.get("srch_point") == null){
	    	srch_point = codePoint_code.get(0).get("CODE_ID").toString();
	    }else{
	    	srch_point = param.get("srch_point").toString();
	    }
	    model.addAttribute("pointCode", srch_point);
	    
	    Map point = new HashMap();
		point.put("point", srch_point);
		
		List<Map> cateList = commonService.catelist(point);		
		model.addAttribute("cateList", cateList);
		
        return "/admin/product/product_list";
    }

	@RequestMapping(value = "/admin/product/ajaxProdlist.af")
	public @ResponseBody JSONArray ajaxProdlist(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		
		String now = "";
	    if(param.isEmpty()){
	    	now = AquaDateUtil.getToday().substring(0, 4);
	    }else{
	    	now = (String) param.get("nowYear");
	    }

	    String pointCode = "";
	    if(param.get("pointCode") == null){
			List<Map<String, Object>> codePoint_code = super.getCommonCodes("POINT_CODE");
			pointCode = codePoint_code.get(0).get("CODE_ID").toString();
	    }else{
	    	pointCode = param.get("pointCode").toString();
	    }
	    model.addAttribute("pointCode", pointCode);
	    
		Map nowYear = new HashMap();
		nowYear.put("year", now);
		nowYear.put("point", pointCode);

		List prodList = productService.prodlist(nowYear);
				
		JSONArray jsonProdlist = new JSONArray();
		jsonProdlist.addAll(prodList);	

	    return jsonProdlist;
	}
	
	@RequestMapping(value = "/admin/product/prod_enter.af")
    public String prod_enter(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//왼쪽 메뉴
		String sMenuCode = "1401";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) 
			return null; 
		
		if(!param.isEmpty()){
			
			Map paramater = new HashMap();
			paramater.put("year", param.get("year"));
			paramater.put("month", param.get("month"));
			paramater.put("code", param.get("code"));
			paramater.put("status", param.get("status"));			
			paramater.put("point", param.get("pointCode"));			
			paramater.put("pointNm", param.get("pointNm"));
			
			Map params = new HashMap();
			params.put("code", param.get("code"));
			params.put("point", param.get("pointCode"));

			List itemList = productService.itemlist(params);		
			List seasonList = super.getCommonCodes("SEASON");
			List codePoint_code = super.getCommonCodes("POINT_CODE");//지점 목록 가져오기
			
			model.addAttribute("paramater", paramater);
			model.addAttribute("itemList", itemList);
			model.addAttribute("seasonList", seasonList);	
			model.addAttribute("codePoint_code", codePoint_code);
			
		}
		
        return "/admin/product/product_register_enter";
    }
	
	//OPEN
	@RequestMapping(value = "/admin/product/setprod_enter.af")
    public String setProd_enter(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//왼쪽 메뉴
		String sMenuCode = "1401";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null; 
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params = AquaDataUtil.convertMap(request);
		
		String itemyear = (String) params.get("itemyear");	
		String itemmonth = (String) params.get("itemmonth");
		String pointCode = (String) params.get("pointCode");
		String pointNm = (String) params.get("pointNm");
		
		List itemcode = (List) params.get("itemcode");	
		List<Map> parameters = new ArrayList<Map>();

		//스파 날짜별 가격셋팅	
		List spaDate = (List) params.get("spaDate");		
		List spaAdultW = (List) params.get("spaAdultW");
		List spaChildW = (List) params.get("spaChildW");
		List spaQty = (List) params.get("spaQty");
		List itemSubCode01 = (List) params.get("itemSubCode01");
		String itemName01 = (String) params.get("itemName01");		
		
		for (int i = 0; i < spaDate.size(); i++) {
			
			int itemStatus = 1;
			String strSpaAdultW = (String) spaAdultW.get(i);
			String strSpaChildW = (String) spaChildW.get(i);
			String strSpaQty = (String) spaQty.get(i);
			if("".equals(strSpaAdultW) && "".equals(strSpaChildW) && "".equals(strSpaQty)){
				itemStatus = 0;
			}
			
			Map paramater = new HashMap();
			
			paramater.put("cate_code", itemcode.get(i));
			paramater.put("item_code", itemSubCode01.get(i));		
			paramater.put("yyyymmdd", spaDate.get(i));
			paramater.put("adults_price", spaAdultW.get(i));
			paramater.put("child_price", spaChildW.get(i));
			paramater.put("event_price", "");
			paramater.put("rental_price", "");			
			paramater.put("quantity", spaQty.get(i));
			paramater.put("season_type", "");
			paramater.put("point_code", pointCode);
			paramater.put("ins_ip", request.getRemoteAddr());
			paramater.put("ins_id", admin.get("SESSION_ADMIN_ID"));
			paramater.put("item_status", 1); //처음 상품데이터 등록 시, CLOSE 상태로 등록			
			paramater.put("item_nm", itemName01);
			paramater.put("item_url", "");
			paramater.put("child_chek", "");
			paramater.put("item_option", "");			
			
			parameters.add(paramater);
		}
		
		//워터파크 날짜별 가격셋팅
		List wpDate = (List) params.get("wpDate");		
		List wpAdultW = (List) params.get("wpAdultW");
		List wpChildW = (List) params.get("wpChildW");
		List wpQty = (List) params.get("wpQty");
		List wpSeason = (List) params.get("wpItemSeason");	
		List itemSubCode02 = (List) params.get("itemSubCode02");
		String itemName02 = (String) params.get("itemName02");			
		
		for (int i = 0; i < wpDate.size(); i++) {
			
			int itemStatus = 1;
			String strWpAdultW = (String) wpAdultW.get(i);
			String strWpChildW = (String) wpChildW.get(i);
			String strWpQty = (String) wpQty.get(i);
			if("".equals(strWpAdultW) && "".equals(strWpChildW) && "".equals(strWpQty)){
				itemStatus = 0;
			}
			
			Map paramater = new HashMap();
			paramater.put("cate_code", itemcode.get(i));
			paramater.put("item_code", itemSubCode02.get(i));		
			paramater.put("yyyymmdd", wpDate.get(i));
			paramater.put("adults_price", wpAdultW.get(i));
			paramater.put("child_price", wpChildW.get(i));
			paramater.put("event_price", "");
			paramater.put("rental_price", "");			
			paramater.put("quantity", wpQty.get(i));
			paramater.put("season_type", wpSeason.get(i));
			paramater.put("point_code", pointCode);	
			paramater.put("ins_ip", request.getRemoteAddr());
			paramater.put("ins_id", admin.get("SESSION_ADMIN_ID"));
			paramater.put("item_status", 1); //처음 상품데이터 등록 시, CLOSE 상태로 등록			
			paramater.put("item_nm", itemName02);
			paramater.put("item_url", "");
			paramater.put("child_chek", "");
			paramater.put("item_option", "");				
			
			parameters.add(paramater);
		}
		
		/* 20170620 oktomato start - KBR */
				
		//복합권 날짜별 가격셋팅
		List complexDate = (List) params.get("complexDate");		
		List complexAdultW = (List) params.get("complexAdultW");
		List complexChildW = (List) params.get("complexChildW");
		List complexQty = (List) params.get("complexQty");			
		List complexSeason = (List) params.get("complexItemSeason");
		List itemSubCode03 = (List) params.get("itemSubCode03");
		String itemName03 = (String) params.get("itemName03");	
		
		for (int i = 0; i< complexDate.size(); i++) {
			int itemStatus = 1;
			String strComplexAdultW = (String) complexAdultW.get(i);
			String strComplexChildW = (String) complexChildW.get(i);
			String strComplexQty = (String) complexQty.get(i);
			if("".equals(strComplexAdultW) && "".equals(strComplexChildW) && "".equals(strComplexQty)){
				itemStatus = 0;
			}
			
			Map paramater = new HashMap();
			//seq
			paramater.put("cate_code", itemcode.get(i));//
			paramater.put("item_code", itemSubCode03.get(i));	//
			paramater.put("yyyymmdd", complexDate.get(i));//
			paramater.put("adults_price", complexAdultW.get(i));
			paramater.put("child_price", complexChildW.get(i));//
			paramater.put("event_price", "");
			paramater.put("rental_price", "");	//		
			paramater.put("quantity", complexQty.get(i));//
			paramater.put("season_type", complexSeason.get(i));
			paramater.put("point_code", pointCode);//
			paramater.put("ins_ip", request.getRemoteAddr());//
			paramater.put("ins_id", admin.get("SESSION_ADMIN_ID"));
			paramater.put("item_status", 1);	//처음 상품데이터 등록 시, CLOSE 상태로 등록		
			paramater.put("item_nm", itemName03);//
			paramater.put("item_url", "");//
			paramater.put("child_chek", "");//
			paramater.put("item_option", "");//			
			
			
			parameters.add(paramater);
		}		

		/* 20170529 oktomato end*/	
		
		System.out.println(parameters);
		
		String result = productService.itemSetting(parameters);
		
		//관리자 사용기록 로그 #############################################
		String action = "C";
		int data_num = Integer.parseInt(itemcode.get(0).toString());
		String httpName = request.getRequestURL().toString().substring(0, request.getRequestURL().toString().indexOf(request.getServletPath()));
		String data_url = httpName + "/secu_admaf/admin/product/modProd_enter.af?year="+itemyear+"&month="+itemmonth+"&code="+itemcode.get(0)+"&status=CLOSE"+"&pointCode="+pointCode;
		String etcContent = "생성";
		String etc = itemName01+"("+itemyear+"/"+itemmonth+"/"+ itemcode.get(0).toString() +") " + etcContent ;
		
		/*String point = "";
		
		Map logParam = new HashMap();
		logParam.put("point_code", pointCode);
		logParam.put("action", action);
		 20180320 수정
		 * logParam.put("data_num", data_num);
		if(pointCode.equals("POINT01")) {
			point = "하남";
		} else {
			point = "고양";
		}
		logParam.put("data_num", "이름 : " + admin.get("SESSION_ADMIN_NM")+ ", 지점 : " + point + "(" + itemyear+"/"+itemmonth+")" + ", 상품명 : " + itemName01 +","+ itemName02 +","+ itemName03);
		logParam.put("data_url", data_url);
		logParam.put("ins_ip", request.getRemoteAddr().toString());	
		logParam.put("ins_admin_id", admin.get("SESSION_ADMIN_ID"));	
		logParam.put("admin_nm", admin.get("SESSION_ADMIN_NM"));	
		logParam.put("admin_uid", admin.get("SESSION_ADMIN_UID"));	
		logParam.put("access_menu_uid", "1401");	
		logParam.put("etc", etc);			
		super.InsContentsLog(logParam);*/
		//############################################################		

        return "redirect:/secu_admaf/admin/product/modProd_enter.af?year="+itemyear+"&month="+itemmonth+"&code="+itemcode.get(0)+"&status=CLOSE&pointCode="+pointCode;
    }
	
	@RequestMapping(value = "/admin/product/modProd_enter.af")
    public String modProd_enter(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException{

		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//왼쪽 메뉴
		String sMenuCode = "1401";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null; 
		
		Map paramater = new HashMap();
		paramater.put("year", request.getParameter("year"));
		paramater.put("month", request.getParameter("month"));
		paramater.put("code", request.getParameter("code"));
		paramater.put("status", request.getParameter("status"));		
		paramater.put("point", request.getParameter("pointCode"));
		  
		List itemList = productService.itemlist(paramater);
		List seasonList = super.getCommonCodes("SEASON");
		
		model.addAttribute("itemList", itemList);	
		model.addAttribute("seasonList", seasonList);		
		model.addAttribute("paramater", paramater);	
        return "/admin/product/product_modify_enter";
	}
	
	@RequestMapping(value = "/admin/product/updProd_enter.af")
    public String updProd_enter(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//왼쪽 메뉴
		String sMenuCode = "1401";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null; 
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params = AquaDataUtil.convertMap(request);
		
		String mode = (String) params.get("mode");	
		String itemyear = (String) params.get("itemyear");
		String itemmonth = (String) params.get("itemmonth");
		String srch_point = (String) params.get("pointCode");
		
		List itemcode = (List) params.get("itemcode");	

		List<Map> parameters = new ArrayList<Map>();

		//스파 날짜별 가격셋팅	
		List spaDate = (List) params.get("spaDate");		
		List spaAdultW = (List) params.get("spaAdultW");
		List spaChildW = (List) params.get("spaChildW");
		List spaQty = (List) params.get("spaQty");
		List itemSubCode01 = (List) params.get("itemSubCode01");
		List spaItemUid = (List) params.get("spaItemUid");		
		String itemName01 = (String) params.get("itemName01");		
		
		for (int i = 0; i < spaDate.size(); i++) {
			
			int itemStatus = 1;
			String strSpaAdultW = (String) spaAdultW.get(i);
			String strSpaChildW = (String) spaChildW.get(i);
			String strSpaQty = (String) spaQty.get(i);
			String strSpaItemUid = (String) spaItemUid.get(i);
			
			if("open".equals(mode)){
				itemStatus = 2;
			}
			if("".equals(strSpaAdultW) && "".equals(strSpaChildW) && "".equals(strSpaQty)){
				itemStatus = 0;
			}
			
			Map paramater = new HashMap();
			paramater.put("item_code", itemSubCode01.get(i));		
			paramater.put("adults_price", spaAdultW.get(i));
			paramater.put("child_price", spaChildW.get(i));
			paramater.put("event_price", "");
			paramater.put("rental_price", "");			
			paramater.put("quantity", spaQty.get(i));
			paramater.put("season_type", "");	
			paramater.put("upd_ip", request.getRemoteAddr());	
			paramater.put("upd_id", admin.get("SESSION_ADMIN_ID"));	
			paramater.put("item_status", itemStatus);			
			paramater.put("item_nm", itemName01);
			paramater.put("item_url", "");
			paramater.put("child_chek", "");
			paramater.put("item_option", "");
			paramater.put("set_uid", strSpaItemUid);			
			
			parameters.add(paramater);
		}
		
		//워터파크 날짜별 가격셋팅
		List wpDate = (List) params.get("wpDate");		
		List wpAdultW = (List) params.get("wpAdultW");
		List wpChildW = (List) params.get("wpChildW");
		List wpQty = (List) params.get("wpQty");
		List wpSeason = (List) params.get("wpItemSeason");	
		List itemSubCode02 = (List) params.get("itemSubCode02");
		String itemName02 = (String) params.get("itemName02");
		List wpItemUid = (List) params.get("wpItemUid");			
		
		for (int i = 0; i < wpDate.size(); i++) {
			
			int itemStatus = 1;
			String strWpAdultW = (String) wpAdultW.get(i);
			String strWpChildW = (String) wpChildW.get(i);
			String strWpQty = (String) wpQty.get(i);
			String strWpItemUid = (String) wpItemUid.get(i);			
			
			if("open".equals(mode)){
				itemStatus = 2;
			}			
			if("".equals(strWpAdultW) && "".equals(strWpChildW) && "".equals(strWpQty)){
				itemStatus = 0;
			}
			
			Map paramater = new HashMap();
			paramater.put("item_code", itemSubCode02.get(i));		
			paramater.put("adults_price", wpAdultW.get(i));
			paramater.put("child_price", wpChildW.get(i));
			paramater.put("event_price", "");
			paramater.put("rental_price", "");			
			paramater.put("quantity", wpQty.get(i));
			paramater.put("season_type", wpSeason.get(i));	
			paramater.put("upd_ip", request.getRemoteAddr());	
			paramater.put("upd_id", admin.get("SESSION_ADMIN_ID"));	
			paramater.put("item_status", itemStatus);			
			paramater.put("item_nm", itemName02);
			paramater.put("item_url", "");
			paramater.put("child_chek", "");
			paramater.put("item_option", "");				
			paramater.put("set_uid", wpItemUid.get(i));			
			
			parameters.add(paramater);
		}	

		/* 20170620 oktomato start - Kwon Bo Ra*/
		
		//복합권 날짜별 가격셋팅
		List complexDate = (List) params.get("complexDate");		
		List complexAdultW = (List) params.get("complexAdultW");
		List complexChildW = (List) params.get("complexChildW");
		List complexQty = (List) params.get("complexQty");
		List complexSeason = (List) params.get("complexItemSeason");	
		List itemSubCode03 = (List) params.get("itemSubCode03");
		String itemName03 = (String) params.get("itemName03");
		List complexItemUid = (List) params.get("complexItemUid");			
		
		for (int i = 0; i < complexDate.size(); i++) {
			
			int itemStatus = 1;
			String strComplexAdultW = (String) complexAdultW.get(i);
			String strComplexChildW = (String) complexChildW.get(i);
			String strComplexQty = (String) complexQty.get(i);
			String strComplexItemUid = (String) complexItemUid.get(i);			
			
			if("open".equals(mode)){
				itemStatus = 2;
			}			
			if("".equals(strComplexAdultW) && "".equals(strComplexChildW) && "".equals(strComplexQty)){
				itemStatus = 0;
			}
			
			Map paramater = new HashMap();
			paramater.put("item_code", itemSubCode03.get(i));		
			paramater.put("adults_price", complexAdultW.get(i));
			paramater.put("child_price", complexChildW.get(i));
			paramater.put("event_price", "");
			paramater.put("rental_price", "");			
			paramater.put("quantity", complexQty.get(i));
			paramater.put("season_type", complexSeason.get(i));	
			paramater.put("upd_ip", request.getRemoteAddr());	
			paramater.put("upd_id", admin.get("SESSION_ADMIN_ID"));	
			paramater.put("item_status", itemStatus);			
			paramater.put("item_nm", itemName03);
			paramater.put("item_url", "");
			paramater.put("child_chek", "");
			paramater.put("item_option", "");				
			paramater.put("set_uid", complexItemUid.get(i));			
			
			parameters.add(paramater);
		}	
		/* 20170526 oktomato end*/
		
		String result = productService.itemUpdating(parameters);
		
		String strUrl ="redirect:/secu_admaf/admin/product/prodlist.af?srch_point="+srch_point+"&year="+itemyear;
		if(!"open".equals(mode)){
			strUrl = "redirect:/secu_admaf/admin/product/modProd_enter.af?year="+itemyear+"&month="+itemmonth+"&code="+itemcode.get(0)+"&status=CLOSE&pointCode="+srch_point;
		}		
		
		//관리자 사용기록 로그 #############################################
		String action = "U";
		String data_num = itemcode.get(0).toString();		
		//int data_num = Integer.parseInt(itemcode.get(0).toString());
		String httpName = request.getRequestURL().toString().substring(0, request.getRequestURL().toString().indexOf(request.getServletPath()));
		String modetype = "open".equals(mode) ? "OPEN" : "CLOSE"; 
		String data_url = httpName + "/secu_admaf/admin/product/modProd_enter.af?year="+itemyear+"&month="+itemmonth+"&code="+itemcode.get(0)+"&status="+modetype+"&pointCode="+srch_point;
		String etcContent = "수정";
		String etc = itemName01+"("+itemyear+"/"+itemmonth+"/"+ itemcode.get(0).toString() +") " + etcContent ;
		String point = "";
		/*Map logParam = new HashMap();
		logParam.put("point_code", srch_point);
		logParam.put("action", action);
		 20180319 수정
		 * logParam.put("data_num", data_num);
		if(srch_point.equals("POINT01")) {
			point = "하남";
		} else {
			point = "고양";
		}
		logParam.put("data_num", "이름 : " + admin.get("SESSION_ADMIN_NM") + ", 지점 : " + point + "(" + itemyear+"/"+itemmonth+")" + ", 상품명 : " + itemName01 +","+ itemName02 +","+ itemName03);
		logParam.put("data_url", data_url);
		logParam.put("ins_ip", request.getRemoteAddr().toString());	
		logParam.put("ins_admin_id", admin.get("SESSION_ADMIN_ID"));	
		logParam.put("admin_nm", admin.get("SESSION_ADMIN_NM"));	
		logParam.put("admin_uid", admin.get("SESSION_ADMIN_UID"));	
		logParam.put("access_menu_uid", "1401");	
		logParam.put("etc", etc);			
		super.InsContentsLog(logParam);*/
		//############################################################		

        return strUrl;
    }	
	
	@RequestMapping(value = "/admin/product/prod_package.af")
    public String prod_package(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//왼쪽 메뉴
		String sMenuCode = "1401";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null; 
		
		if(!param.isEmpty()){

			Map paramater = new HashMap();
			paramater.put("year", param.get("year"));
			paramater.put("month", param.get("month"));
			paramater.put("status", param.get("status"));
			paramater.put("yearmonth", param.get("year").toString()+param.get("month").toString());
			paramater.put("point", param.get("pointCode"));				
			paramater.put("pointNm", param.get("pointNm"));
			paramater.put("code", "10000000");
			
			List ePlaceList = productService.itemlist(paramater);
			
			paramater.put("code", param.get("code"));
			List itemGroupByList = productService.itemGroupBylist(paramater);			
			List itemList = productService.itemlist(paramater);
			//List ePlaceList = super.getCommonCodes("OPTION_TYPE");
			List seasonList = super.getCommonCodes("SEASON");			
			List codePoint_code = super.getCommonCodes("POINT_CODE");

			model.addAttribute("paramater", paramater);
			model.addAttribute("itemList", itemList);
			model.addAttribute("seasonList", seasonList);
			model.addAttribute("ePlaceList", ePlaceList);
			model.addAttribute("itemGroupByList", itemGroupByList);	
			model.addAttribute("codePoint_code", codePoint_code);
			
		}	
        return "/admin/product/product_register_package";
    }
	
	@RequestMapping(value = "/admin/product/setprod_package.af")
    public String setProd_package(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//왼쪽 메뉴
		String sMenuCode = "1401";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null; 
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params = AquaDataUtil.convertMap(request);
		
		String itemyear = (String) params.get("itemyear");	
		String itemmonth = (String) params.get("itemmonth");
		String itemName = (String) params.get("itemName");
		String itemUrl = (String) params.get("itemUrl");
		String itemOption = (String) params.get("itemOption");
		String childChk = (String) params.get("childChk");
		String pointCode = (String) params.get("pointCode"); 
		
		List<Map> parameters = new ArrayList<Map>();

		List pkAdultW = (List) params.get("pkAdultW");
		List pkChildW = (List) params.get("pkChildW");		
		List pkQty = (List) params.get("pkQty");
		List itemcode = (List) params.get("itemcode");	
		List packageDate = (List) params.get("packageDate");
		List itemSeason = (List) params.get("itemSeason");		
		List itemSubCode = (List) params.get("itemSubCode");
		
		for (int i = 0; i < packageDate.size(); i++) {
			
			int itemStatus = 1;
			String strPkAdultW = (String) pkAdultW.get(i);
			String strPkChildW = (String) pkChildW.get(i);
			String strPkQty = (String) pkQty.get(i);
			if("".equals(strPkAdultW) && "".equals(strPkChildW) && "".equals(strPkQty)){
				itemStatus = 0;
			}
			
			Map paramater = new HashMap();
			paramater.put("cate_code", itemcode.get(i));
			paramater.put("item_code", itemSubCode.get(i));		
			paramater.put("yyyymmdd", packageDate.get(i));
			paramater.put("adults_price", pkAdultW.get(i));
			paramater.put("child_price", pkChildW.get(i));
			paramater.put("event_price", "");
			paramater.put("rental_price", "");			
			paramater.put("quantity", pkQty.get(i));
			paramater.put("season_type", itemSeason.get(i));
			paramater.put("point_code", pointCode);		
			paramater.put("ins_ip", request.getRemoteAddr());
			paramater.put("ins_id", admin.get("SESSION_ADMIN_ID"));
			paramater.put("item_status", itemStatus);			
			paramater.put("item_nm", itemName);
			paramater.put("item_url", itemUrl);
			paramater.put("child_chek", childChk);
			paramater.put("item_option", itemOption);				
			
			parameters.add(paramater);
		}	

		String result = productService.itemSetting(parameters);		
		
		String itemN = "";
		String itemO = "";
		if(itemName.equals("") || itemName.equals(null)) {
			itemN = "";
		} else {
			itemN = ", 상품명 : " + itemName;
		}
		
		if(itemOption.equals("") || itemOption.equals(null)) {
			itemO = "";
		} else {
			if(itemOption.equals("PRD001")) {
				itemOption = "찜질스파";
			} else if (itemOption.equals("PRD002")) {
				itemOption = "워터파크";
			} else if (itemOption.equals("PRD003")) {
				itemOption = "복합권";
			} else {
				itemOption = "";
			}
			itemO = ", 상품옵션 : " + itemOption;
		}
		
		
		//관리자 사용기록 로그 #############################################
		String action = "C";
		int data_num = Integer.parseInt(itemcode.get(0).toString());
		String httpName = request.getRequestURL().toString().substring(0, request.getRequestURL().toString().indexOf(request.getServletPath()));
		String data_url = httpName + "/secu_admaf/admin/product/modProd_package.af?year="+itemyear+"&month="+itemmonth+"&code="+itemcode.get(0)+"&subcode="+itemSubCode.get(0)+"&pointCode="+pointCode+"&status=CLOSE";
		String etcContent = "생성";
		String etc = itemName+"("+itemyear+"/"+itemmonth+"/"+ itemcode.get(0).toString() +") " + etcContent ;
		
		/*Map logParam = new HashMap();
		logParam.put("point_code", pointCode);
		logParam.put("action", action);
		 20180320 수정
		 * logParam.put("data_num", data_num);
		String point = "";
		if(pointCode.equals("POINT01")) {
			point = "하남";
		} else {
			point = "고양";
		}
		logParam.put("data_num", "이름 : " + admin.get("SESSION_ADMIN_NM") + ", 지점 : " + point + "(" + itemyear+"/"+itemmonth+")" + itemN + itemO);
		logParam.put("data_url", data_url);
		logParam.put("ins_ip", request.getRemoteAddr().toString());	
		logParam.put("ins_admin_id", admin.get("SESSION_ADMIN_ID"));	
		logParam.put("admin_nm", admin.get("SESSION_ADMIN_NM"));	
		logParam.put("admin_uid", admin.get("SESSION_ADMIN_UID"));	
		logParam.put("access_menu_uid", "1401");	
		logParam.put("etc", etc);			
		super.InsContentsLog(logParam);*/
		//############################################################			
        return "redirect:/secu_admaf/admin/product/modProd_package.af?year="+itemyear+"&month="+itemmonth+"&code="+itemcode.get(0)+"&subcode="+itemSubCode.get(0)+"&pointCode="+pointCode+"&status=CLOSE";
    }
	
	@RequestMapping(value = "/admin/product/modProd_package.af")
    public String modProd_package(Model model, HttpServletRequest request, HttpServletResponse response){

		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//왼쪽 메뉴
		String sMenuCode = "1401";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null; 
		
		List seasonList = super.getCommonCodes("SEASON");	
		//List ePlaceList = super.getCommonCodes("OPTION_TYPE");	
		
		Map paramater = new HashMap();
		paramater.put("year", request.getParameter("year"));
		paramater.put("month", request.getParameter("month"));
		paramater.put("status", request.getParameter("status"));
		paramater.put("point", request.getParameter("pointCode"));
		paramater.put("code", "10000000");
		
		List ePlaceList = productService.itemlist(paramater);
		
		paramater.put("code", request.getParameter("code"));
		
		List itemList = productService.itemlist(paramater);
		Map item = (Map) itemList.get(0);
		String strItem =item.get("ITEM_CODE").toString();
		if(!"".equals(request.getParameter("subcode")) && request.getParameter("subcode") != null){
			strItem = request.getParameter("subcode");
		}		
		paramater.put("subcode", strItem);	
		model.addAttribute("itemList", itemList);	
		model.addAttribute("seasonList", seasonList);		
		model.addAttribute("paramater", paramater);
		model.addAttribute("ePlaceList", ePlaceList);			
		
        return "/admin/product/product_modify_package";
	}
	
	@RequestMapping(value = "/admin/product/updProd_package.af")
    public String updProd_package(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//왼쪽 메뉴
		String sMenuCode = "1401";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null; 
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params = AquaDataUtil.convertMap(request);
		
		String mode = (String) params.get("mode");	
		String itemyear = (String) params.get("itemyear");	
		String itemmonth = (String) params.get("itemmonth");
		String itemName = (String) params.get("itemName");
		String itemUrl = (String) params.get("itemUrl");
		String itemOption = (String) params.get("itemOption");
		String childChk = (String) params.get("childChk");
		String pointCode = (String) params.get("pointCode");
		
		List itemcode = (List) params.get("itemcode");	

		List<Map> parameters = new ArrayList<Map>();

		List pkAdultW = (List) params.get("pkAdultW");
		List pkChildW = (List) params.get("pkChildW");		
		List pkQty = (List) params.get("pkQty");
		List packageDate = (List) params.get("packageDate");
		List itemUid = (List) params.get("itemUid");	
		List itemSeason = (List) params.get("itemSeason");		
		List itemSubCode = (List) params.get("itemSubCode");	
		
		for (int i = 0; i < packageDate.size(); i++) {
			
			int itemStatus = 1;
			String strPkAdultW = (String) pkAdultW.get(i);
			String strPkChildW = (String) pkChildW.get(i);
			String strPkQty = (String) pkQty.get(i);
			String strItemUid = (String) itemUid.get(i);
			
			if("open".equals(mode)){
				itemStatus = 2;
			}
			if("".equals(strPkAdultW) && "".equals(strPkChildW) && "".equals(strPkQty)){
				itemStatus = 0;
			}
			
			Map paramater = new HashMap();
			paramater.put("item_code", itemSubCode.get(i));		
			paramater.put("adults_price", pkAdultW.get(i));
			paramater.put("child_price", pkChildW.get(i));
			paramater.put("event_price", "");
			paramater.put("rental_price", "");			
			paramater.put("quantity", pkQty.get(i));
			paramater.put("season_type", itemSeason.get(i));	
			paramater.put("upd_ip", request.getRemoteAddr());	
			paramater.put("upd_id", admin.get("SESSION_ADMIN_ID"));	
			paramater.put("item_status", itemStatus);			
			paramater.put("item_nm", itemName);
			paramater.put("item_url", itemUrl);
			paramater.put("child_chek", childChk);
			paramater.put("item_option", itemOption);
			paramater.put("set_uid", itemUid.get(i));			
			
			parameters.add(paramater);
		}

		String result = productService.itemUpdating(parameters);
		
		String strUrl ="redirect:/secu_admaf/admin/product/prodlist.af?srch_point="+pointCode+"&year="+itemyear;
		if(!"open".equals(mode)){
			strUrl = "redirect:/secu_admaf/admin/product/modProd_package.af?year="+itemyear+"&month="+itemmonth+"&code="+itemcode.get(0)+"&subcode="+itemSubCode.get(0)+"&pointCode="+pointCode+"&status=CLOSE";
		}	
		
		//관리자 사용기록 로그 #############################################
		/*String action = "U";
		int data_num = Integer.parseInt(itemcode.get(0).toString());
		String httpName = request.getRequestURL().toString().substring(0, request.getRequestURL().toString().indexOf(request.getServletPath()));
		String modetype = "open".equals(mode) ? "OPEN" : "CLOSE"; 
		String data_url = httpName + "/secu_admaf/admin/product/modProd_package.af?year="+itemyear+"&month="+itemmonth+"&code="+itemcode.get(0)+"&subcode="+itemSubCode.get(0)+"&pointCode="+pointCode+"&status="+modetype;
		String etcContent = "수정";
		String etc = itemName+"("+itemyear+"/"+itemmonth+"/"+ itemcode.get(0).toString() +") " + etcContent ;
		
		String itemN = "";
		String itemO = "";
		if(itemName.equals("") || itemName.equals(null)) {
			itemN = "";
		} else {
			itemN = ", 상품명 : " + itemName;
		}
		
		if(itemOption.equals("") || itemOption.equals(null)) {
			itemO = "";
		} else {
			if(itemOption.equals("PRD001")) {
				itemOption = "찜질스파";
			} else if (itemOption.equals("PRD002")) {
				itemOption = "워터파크";
			} else if (itemOption.equals("PRD003")) {
				itemOption = "복합권";
			} else {
				itemOption = "";
			}
			itemO = ", 상품옵션 : " + itemOption;
		}
		
		Map logParam = new HashMap();
		logParam.put("point_code", "POINT01");
		logParam.put("action", action);
		 20180320 수정
		 * logParam.put("data_num", data_num);
		String point ="";
		if(pointCode.equals("POINT01")) {
			point = "하남";
		} else {
			point = "고양";
		}
		logParam.put("data_num", "이름 : " + admin.get("SESSION_ADMIN_NM") + ", 지점 : " + point + "(" + itemyear+"/"+itemmonth+")" + itemN + itemO);
		logParam.put("data_url", data_url);
		logParam.put("ins_ip", request.getRemoteAddr().toString());	
		logParam.put("ins_admin_id", admin.get("SESSION_ADMIN_ID"));	
		logParam.put("admin_nm", admin.get("SESSION_ADMIN_NM"));	
		logParam.put("admin_uid", admin.get("SESSION_ADMIN_UID"));	
		logParam.put("access_menu_uid", "1401");	
		logParam.put("etc", etc);			
		super.InsContentsLog(logParam);*/
		//############################################################			

        return strUrl;
    }	
	
	@RequestMapping(value = "/admin/product/prod_rental.af")
    public String prod_rental(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		
		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//왼쪽 메뉴
		String sMenuCode = "1401";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null; 
		
		if(!param.isEmpty()){

			Map paramater = new HashMap();
			paramater.put("year", param.get("year"));
			paramater.put("month", param.get("month"));
			paramater.put("code", param.get("code"));
			paramater.put("status", param.get("status"));
			paramater.put("yearmonth", param.get("year").toString()+param.get("month").toString());
			paramater.put("point", "POINT01");			

			List itemGroupByList = productService.itemGroupBylist(paramater);	
			List itemList = ItemList((String)param.get("code"));
			List ePlaceList = super.getCommonCodes("OPTION_TYPE");				
			List seasonList = super.getCommonCodes("SEASON");
			
			model.addAttribute("paramater", paramater);
			model.addAttribute("itemList", itemList);
			model.addAttribute("seasonList", seasonList);				
			model.addAttribute("ePlaceList", ePlaceList);
			model.addAttribute("itemGroupByList", itemGroupByList);				
			
		}	
        return "/admin/product/product_register_rental";
    }
	
	@RequestMapping(value = "/admin/product/setprod_rental.af")
    public String setprod_rental(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//왼쪽 메뉴
		String sMenuCode = "1401";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null; 
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params = AquaDataUtil.convertMap(request);
		
		String itemyear = (String) params.get("itemyear");	
		String itemmonth = (String) params.get("itemmonth");
		String itemName = (String) params.get("itemName");
		String itemOption = (String) params.get("itemOption");
		
		List<Map> parameters = new ArrayList<Map>();

		List rtPrice = (List) params.get("rtPrice");	
		List rtQty = (List) params.get("rtQty");
		List itemcode = (List) params.get("itemcode");	
		List rentalDate = (List) params.get("rentalDate");
		List itemSeason = (List) params.get("itemSeason");		
		List itemSubCode = (List) params.get("itemSubCode");
		
		for (int i = 0; i < rentalDate.size(); i++) {
			
			int itemStatus = 1;
			String strRtPrice = (String) rtPrice.get(i);
			String strRtQty = (String) rtQty.get(i);
			if("".equals(strRtPrice) && "".equals(strRtQty)){
				itemStatus = 0;
			}
			
			Map paramater = new HashMap();
			paramater.put("cate_code", itemcode.get(i));
			paramater.put("item_code", itemSubCode.get(i));		
			paramater.put("yyyymmdd", rentalDate.get(i));
			paramater.put("adults_price", "");
			paramater.put("child_price", "");
			paramater.put("event_price", "");
			paramater.put("rental_price", rtPrice.get(i));			
			paramater.put("quantity", rtQty.get(i));
			paramater.put("season_type", itemSeason.get(i));
			paramater.put("point_code", "POINT01");	//섹션값 가져와야함.		
			paramater.put("ins_ip", request.getRemoteAddr());
			paramater.put("ins_id", admin.get("SESSION_ADMIN_ID"));	
			paramater.put("item_status", itemStatus);			
			paramater.put("item_nm", itemName);
			paramater.put("item_url", "");
			paramater.put("child_chek", "");
			paramater.put("item_option", itemOption);				
			
			parameters.add(paramater);
		}	

		String result = productService.itemSetting(parameters);	
		
		//관리자 사용기록 로그 #############################################
		String action = "C";
		int data_num = Integer.parseInt(itemcode.get(0).toString());
		String httpName = request.getRequestURL().toString().substring(0, request.getRequestURL().toString().indexOf(request.getServletPath()));
		String data_url = httpName + "/secu_admaf/admin/product/modProd_rental.af?year="+itemyear+"&month="+itemmonth+"&code="+itemcode.get(0)+"&status=CLOSE";
		String etcContent = "생성";
		String etc = itemName+"("+itemyear+"/"+itemmonth+"/"+ itemcode.get(0).toString() +") " + etcContent ;
		/*
		Map logParam = new HashMap();
		logParam.put("point_code", "POINT01");
		logParam.put("action", action);
		 20180320 수정
		 * logParam.put("data_num", data_num);
		logParam.put("data_num", "이름 : " + admin.get("SESSION_ADMIN_NM") + ", 상품명 : " + itemName + "("+itemyear+"/"+itemmonth+")");
		logParam.put("data_url", data_url);
		logParam.put("ins_ip", request.getRemoteAddr().toString());	
		logParam.put("ins_admin_id", admin.get("SESSION_ADMIN_ID"));	
		logParam.put("admin_nm", admin.get("SESSION_ADMIN_NM"));	
		logParam.put("admin_uid", admin.get("SESSION_ADMIN_UID"));	
		logParam.put("access_menu_uid", "1401");	
		logParam.put("etc", etc);			
		super.InsContentsLog(logParam);*/
		//############################################################			
		
        return "redirect:/secu_admaf/admin/product/modProd_rental.af?year="+itemyear+"&month="+itemmonth+"&code="+itemcode.get(0)+"&status=CLOSE";
    }
	
	@RequestMapping(value = "/admin/product/modProd_rental.af")
    public String modProd_rental(Model model, HttpServletRequest request, HttpServletResponse response){

		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//왼쪽 메뉴
		String sMenuCode = "1401";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null; 
		
		List seasonList = super.getCommonCodes("SEASON");	
		List itemList = ItemList(request.getParameter("code"));
		List ePlaceList = super.getCommonCodes("OPTION_TYPE");	
		Map item = (Map) itemList.get(0);
		String strItem =item.get("ITEM_CODE").toString();
		if(!"".equals(request.getParameter("subcode")) && request.getParameter("subcode") != null){
			strItem = request.getParameter("subcode");
		}

		Map paramater = new HashMap();
		paramater.put("year", request.getParameter("year"));
		paramater.put("month", request.getParameter("month"));
		paramater.put("code", request.getParameter("code"));
		paramater.put("subcode", strItem);		
		paramater.put("status", request.getParameter("status"));		
		
		model.addAttribute("itemList", itemList);
		model.addAttribute("seasonList", seasonList);		
		model.addAttribute("paramater", paramater);
		model.addAttribute("ePlaceList", ePlaceList);			
		
        return "/admin/product/product_modify_rental";
	}
	
	@RequestMapping(value = "/admin/product/updProd_rental.af")
    public String updProd_rental(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//왼쪽 메뉴
		String sMenuCode = "1401";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null; 
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params = AquaDataUtil.convertMap(request);
		
		String mode = (String) params.get("mode");	
		String itemyear = (String) params.get("itemyear");	
		String itemmonth = (String) params.get("itemmonth");
		String itemName = (String) params.get("itemName");
		String itemOption = (String) params.get("itemOption");
		String pointCode = (String) params.get("pointCode");
		
		List itemcode = (List) params.get("itemcode");	

		List<Map> parameters = new ArrayList<Map>();

		List rtPrice = (List) params.get("rtPrice");
		List rtQty = (List) params.get("rtQty");		
		List rentalDate = (List) params.get("rentalDate");
		List itemUid = (List) params.get("itemUid");	
		List itemSeason = (List) params.get("itemSeason");		
		List itemSubCode = (List) params.get("itemSubCode");	
		
		for (int i = 0; i < rentalDate.size(); i++) {
			
			int itemStatus = 1;
			String strRtPrice = (String) rtPrice.get(i);
			String strRtQty = (String) rtQty.get(i);
			String strItemUid = (String) itemUid.get(i);
			
			if("open".equals(mode)){
				itemStatus = 2;
			}
			if("".equals(strRtPrice) && "".equals(strRtQty)){
				itemStatus = 0;
			}
			
			Map paramater = new HashMap();
			paramater.put("item_code", itemSubCode.get(i));		
			paramater.put("adults_price", "");
			paramater.put("child_price", "");
			paramater.put("event_price", "");
			paramater.put("rental_price", rtPrice.get(i));			
			paramater.put("quantity", rtQty.get(i));
			paramater.put("season_type", itemSeason.get(i));	
			paramater.put("upd_ip", request.getRemoteAddr());	
			paramater.put("upd_id", admin.get("SESSION_ADMIN_ID"));	
			paramater.put("item_status", itemStatus);			
			paramater.put("item_nm", itemName);
			paramater.put("item_url", "");
			paramater.put("child_chek", "");
			paramater.put("item_option", itemOption);
			paramater.put("set_uid", itemUid.get(i));			
			
			parameters.add(paramater);
		}

		String result = productService.itemUpdating(parameters);
		
		String strUrl ="redirect:/secu_admaf/admin/product/prodlist.af";
		if(!"open".equals(mode)){
			strUrl = "redirect:/secu_admaf/admin/product/modProd_rental.af?year="+itemyear+"&month="+itemmonth+"&code="+itemcode.get(0)+"&status=CLOSE";
		}	
		
		System.out.println("@@@@@@@@@@@@");
		System.out.println(pointCode);


		//관리자 사용기록 로그 #############################################
		/*String action = "U";
		int data_num = Integer.parseInt(itemcode.get(0).toString());
		String httpName = request.getRequestURL().toString().substring(0, request.getRequestURL().toString().indexOf(request.getServletPath()));
		String modetype = "open".equals(mode) ? "OPEN" : "CLOSE"; 
		String data_url = httpName + "/secu_admaf/admin/product/modProd_rental.af?year="+itemyear+"&month="+itemmonth+"&code="+itemcode.get(0)+"&status="+modetype;
		String etcContent = "수정";
		String etc = itemName+"("+itemyear+"/"+itemmonth+"/"+ itemcode.get(0).toString() +") " + etcContent ;
		
		Map logParam = new HashMap();
		logParam.put("point_code", "POINT01");
		logParam.put("action", action);
		 20180320 수정 
		logParam.put("data_num", data_num);
		logParam.put("data_num", "이름 : " + admin.get("SESSION_ADMIN_NM") + ", 상품명 : " + itemName + "("+itemyear+"/"+itemmonth+")");
		logParam.put("data_url", data_url);
		logParam.put("ins_ip", request.getRemoteAddr().toString());	
		logParam.put("ins_admin_id", admin.get("SESSION_ADMIN_ID"));	
		logParam.put("admin_nm", admin.get("SESSION_ADMIN_NM"));	
		logParam.put("admin_uid", admin.get("SESSION_ADMIN_UID"));	
		logParam.put("access_menu_uid", "1401");	
		logParam.put("etc", etc);			
		super.InsContentsLog(logParam);*/
		//############################################################		
		
        return strUrl;
    }	
	
	@RequestMapping(value = "/admin/product/prod_event.af")
    public String prod_event(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//왼쪽 메뉴
		String sMenuCode = "1401";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null; 
		
		if(!param.isEmpty()){

			Map paramater = new HashMap();
			paramater.put("year", param.get("year"));
			paramater.put("month", param.get("month"));
			paramater.put("code", param.get("code"));
			paramater.put("status", param.get("status"));
			paramater.put("yearmonth", param.get("year").toString()+param.get("month").toString());
			paramater.put("point", "POINT01");			

			List itemGroupByList = productService.itemGroupBylist(paramater);
			List itemList = ItemList((String)param.get("code"));			
			List ePlaceList = super.getCommonCodes("OPTION_TYPE");
			List seasonList = super.getCommonCodes("SEASON");
			List codePoint_code = super.getCommonCodes("POINT_CODE");
			
			model.addAttribute("paramater", paramater);
			model.addAttribute("itemList", itemList);
			model.addAttribute("seasonList", seasonList);
			model.addAttribute("ePlaceList", ePlaceList);
			model.addAttribute("itemGroupByList", itemGroupByList);				
			model.addAttribute("codePoint_code", codePoint_code);
			
		}	
        return "/admin/product/product_register_event";
    }
	
	@RequestMapping(value = "/admin/product/setprod_event.af")
    public String setProd_event(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//왼쪽 메뉴
		String sMenuCode = "1401";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null; 
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params = AquaDataUtil.convertMap(request);
		
		String itemyear = (String) params.get("itemyear");	
		String itemmonth = (String) params.get("itemmonth");
		String itemName = (String) params.get("itemName");
		String itemUrl = (String) params.get("itemUrl");
		String itemOption = (String) params.get("itemOption");
		String childChk = (String) params.get("childChk");
		
		List<Map> parameters = new ArrayList<Map>();

		List eventW = (List) params.get("eventW");	
		List eventQty = (List) params.get("eventQty");
		List itemcode = (List) params.get("itemcode");	
		List eventDate = (List) params.get("eventDate");
		List itemSeason = (List) params.get("itemSeason");		
		List itemSubCode = (List) params.get("itemSubCode");
		
		for (int i = 0; i < eventDate.size(); i++) {
			
			int itemStatus = 1;
			String strEventW = (String) eventW.get(i);
			String strEventQty = (String) eventQty.get(i);
			if("".equals(strEventW) && "".equals(strEventQty)){
				itemStatus = 0;
			}
			
			Map paramater = new HashMap();
			paramater.put("cate_code", itemcode.get(i));
			paramater.put("item_code", itemSubCode.get(i));		
			paramater.put("yyyymmdd", eventDate.get(i));
			paramater.put("adults_price", "");
			paramater.put("child_price", "");
			paramater.put("event_price", eventW.get(i));
			paramater.put("rental_price", "");			
			paramater.put("quantity", eventQty.get(i));
			paramater.put("season_type", itemSeason.get(i));
			paramater.put("point_code", "POINT01");	//섹션값 가져와야함.		
			paramater.put("ins_ip", request.getRemoteAddr());
			paramater.put("ins_id", admin.get("SESSION_ADMIN_ID"));
			paramater.put("item_status", itemStatus);			
			paramater.put("item_nm", itemName);
			paramater.put("item_url", itemUrl);
			paramater.put("child_chek", childChk);
			paramater.put("item_option", itemOption);				
			
			parameters.add(paramater);
		}	

		String result = productService.itemSetting(parameters);	
		
		//관리자 사용기록 로그 #############################################
		/*String action = "C";
		int data_num = Integer.parseInt(itemcode.get(0).toString());
		String httpName = request.getRequestURL().toString().substring(0, request.getRequestURL().toString().indexOf(request.getServletPath()));
		String data_url = httpName + "/secu_admaf/admin/product/modProd_event.af?year="+itemyear+"&month="+itemmonth+"&code="+itemcode.get(0)+"&status=CLOSE";
		String etcContent = "생성";
		String etc = itemName+"("+itemyear+"/"+itemmonth+"/"+ itemcode.get(0).toString() +") " + etcContent ;
		
		Map logParam = new HashMap();
		logParam.put("point_code", "POINT01");
		logParam.put("action", action);
		 20180320 수정 
		logParam.put("data_num", data_num);
		logParam.put("data_num", "이름 : " + admin.get("SESSION_ADMIN_NM") + ", 상품명 : " + itemName + "("+itemyear+"/"+itemmonth+")");
		logParam.put("data_url", data_url);
		logParam.put("ins_ip", request.getRemoteAddr().toString());	
		logParam.put("ins_admin_id", admin.get("SESSION_ADMIN_ID"));	
		logParam.put("admin_nm", admin.get("SESSION_ADMIN_NM"));	
		logParam.put("admin_uid", admin.get("SESSION_ADMIN_UID"));	
		logParam.put("access_menu_uid", "1401");	
		logParam.put("etc", etc);			
		super.InsContentsLog(logParam);*/
		//############################################################			
		
        return "redirect:/secu_admaf/admin/product/modProd_event.af?year="+itemyear+"&month="+itemmonth+"&code="+itemcode.get(0)+"&status=CLOSE";
    }
	
	@RequestMapping(value = "/admin/product/modProd_event.af")
    public String modProd_event(Model model, HttpServletRequest request, HttpServletResponse response){

		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//왼쪽 메뉴
		String sMenuCode = "1401";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null; 
		
		List seasonList = super.getCommonCodes("SEASON");	
		List itemList = ItemList(request.getParameter("code"));
		List ePlaceList = super.getCommonCodes("OPTION_TYPE");
		Map item = (Map) itemList.get(0);
		String strItem =item.get("ITEM_CODE").toString();
		if(!"".equals(request.getParameter("subcode")) && request.getParameter("subcode") != null){
			strItem = request.getParameter("subcode");
		}		
		
		Map paramater = new HashMap();
		paramater.put("year", request.getParameter("year"));
		paramater.put("month", request.getParameter("month"));
		paramater.put("code", request.getParameter("code"));
		paramater.put("subcode", strItem);		
		paramater.put("status", request.getParameter("status"));

		model.addAttribute("itemList", itemList);	
		model.addAttribute("seasonList", seasonList);		
		model.addAttribute("paramater", paramater);
		model.addAttribute("ePlaceList", ePlaceList);			
		
        return "/admin/product/product_modify_event";
	}
	
	@RequestMapping(value = "/admin/product/updProd_event.af")
    public String updProd_event(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String,String> admin = (Map)request.getSession().getAttribute(CommonConstant.session.SESSION_KEY_ADMIN);
		//왼쪽 메뉴
		String sMenuCode = "1401";
		model.addAttribute("sMenuCode", sMenuCode);
		if(adminAdminAuthService.adminAuthGetIsAuthUse(admin.get("SESSION_ADMIN_UID").toString(), sMenuCode, response) == 0) return null; 
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params = AquaDataUtil.convertMap(request);
		
		String mode = (String) params.get("mode");	
		String itemyear = (String) params.get("itemyear");	
		String itemmonth = (String) params.get("itemmonth");
		String itemName = (String) params.get("itemName");
		String itemUrl = (String) params.get("itemUrl");
		String itemOption = (String) params.get("itemOption");
		String childChk = (String) params.get("childChk");		
		
		List itemcode = (List) params.get("itemcode");	

		List<Map> parameters = new ArrayList<Map>();

		List eventW = (List) params.get("eventW");
		List eventQty = (List) params.get("eventQty");		
		List eventDate = (List) params.get("eventDate");
		List itemUid = (List) params.get("itemUid");	
		List itemSeason = (List) params.get("itemSeason");		
		List itemSubCode = (List) params.get("itemSubCode");	
		
		for (int i = 0; i < eventDate.size(); i++) {
			
			int itemStatus = 1;
			String strEventW = (String) eventW.get(i);
			String strEventQty = (String) eventQty.get(i);
			String strItemUid = (String) itemUid.get(i);
			
			if("open".equals(mode)){
				itemStatus = 2;
			}
			if("".equals(strEventW) && "".equals(strEventQty)){
				itemStatus = 0;
			}
			
			Map paramater = new HashMap();
			paramater.put("item_code", itemSubCode.get(i));		
			paramater.put("adults_price", "");
			paramater.put("child_price", "");
			paramater.put("event_price", eventW.get(i));
			paramater.put("rental_price", "");			
			paramater.put("quantity", eventQty.get(i));
			paramater.put("season_type", itemSeason.get(i));	
			paramater.put("upd_ip", request.getRemoteAddr());	
			paramater.put("upd_id", admin.get("SESSION_ADMIN_ID"));	
			paramater.put("item_status", itemStatus);			
			paramater.put("item_nm", itemName);
			paramater.put("item_url", itemUrl);
			paramater.put("child_chek", childChk);
			paramater.put("item_option", itemOption);
			paramater.put("set_uid", itemUid.get(i));			
			
			parameters.add(paramater);
		}

		String result = productService.itemUpdating(parameters);
		
		String strUrl ="redirect:/secu_admaf/admin/product/prodlist.af";
		if(!"open".equals(mode)){
			strUrl = "redirect:/secu_admaf/admin/product/modProd_event.af?year="+itemyear+"&month="+itemmonth+"&code="+itemcode.get(0)+"&status=CLOSE";
		}	
		
		//관리자 사용기록 로그 #############################################
		/*String action = "U";
		int data_num = Integer.parseInt(itemcode.get(0).toString());
		String httpName = request.getRequestURL().toString().substring(0, request.getRequestURL().toString().indexOf(request.getServletPath()));
		String modetype = "open".equals(mode) ? "OPEN" : "CLOSE"; 
		String data_url = httpName + "/secu_admaf/admin/product/modProd_event.af?year="+itemyear+"&month="+itemmonth+"&code="+itemcode.get(0)+"&status="+modetype;
		String etcContent = "수정";
		String etc = itemName+"("+itemyear+"/"+itemmonth+"/"+ itemcode.get(0).toString() +") " + etcContent ;
		
		
		System.out.println("@@@@@@@@@");
		System.out.println(itemOption);
		String itemN = "";
		String itemO = "";
		if(itemName.equals("") || itemName.equals(null)) {
			itemN = "";
		} else {
			itemN = ", 상품명 : " + itemName;
		}
		
		if(itemOption.equals("") || itemOption.equals(null)) {
			itemO = "";
		} else {
			if(itemOption.equals("PRD001")) {
				itemOption = "찜질스파";
			} else if (itemOption.equals("PRD002")) {
				itemOption = "워터파크";
			} else if (itemOption.equals("PRD003")) {
				itemOption = "복합권";
			} else {
				itemOption = "";
			}
			itemO = ", 상품옵션 : " + itemOption;
		}
		
		Map logParam = new HashMap();
		logParam.put("point_code", "POINT01");
		logParam.put("action", action);
		 20180320 수정
		 * logParam.put("data_num", data_num);
		logParam.put("data_num", "이름 : " + admin.get("SESSION_ADMIN_NM") + ", 상품명 :" + itemN + itemO + "(" + itemyear+"/"+itemmonth+")");
		logParam.put("data_url", data_url);
		logParam.put("ins_ip", request.getRemoteAddr().toString());	
		logParam.put("ins_admin_id", admin.get("SESSION_ADMIN_ID"));	
		logParam.put("admin_nm", admin.get("SESSION_ADMIN_NM"));	
		logParam.put("admin_uid", admin.get("SESSION_ADMIN_UID"));	
		logParam.put("access_menu_uid", "1401");	
		logParam.put("etc", etc);			
		super.InsContentsLog(logParam);*/
		//############################################################			

        return strUrl;
    }		
	
	@RequestMapping(value = "/admin/product/ajaxModItems.af")
	public @ResponseBody JSONArray ajaxModItems(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		Map params = new HashMap();
		String year = (String) param.get("year");
		String month = (String) param.get("month");
		String code = (String) param.get("code");
		String subcode = (String) param.get("subcode");		
		String pointCode = (String) param.get("pointCode");		
		
		params.put("code", code);
		params.put("subcode", subcode);		
		params.put("yearmonth", year+month);
		params.put("point", pointCode);	
		
		List itemModList = productService.itemModlist(params);
	
		JSONArray jsonProdlist = new JSONArray();
		jsonProdlist.addAll(itemModList);	

	    return jsonProdlist;
	}
	
	@RequestMapping(value = "/admin/product/ajaxItemCnt.af")
    public String ajaxItemCnt(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		Map params = new HashMap();
		String year = (String) param.get("year");
		String month = (String) param.get("month");
		String code = (String) param.get("code");
		String subcode = (String) param.get("subcode");		
		
		params.put("code", code);
		params.put("subcode", subcode);		
		params.put("yearmonth", year+month);
		params.put("point", "POINT01");	
		
		int result = productService.itemsCnt(params);

		Util.htmlPrint(String.valueOf(result), response);

		return null;
    }
	
	@RequestMapping(value = "/admin/product/ajaxItemCheck.af")
    public String ajaxItemCheck(@RequestParam Map<String, Object> param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		Map params = new HashMap();
		String code = (String) param.get("code");
		String itemOption = (String) param.get("itemOption");		
		String pointCode = (String) param.get("pointCode");
		String subCode = (String) param.get("subcode");
		JSONArray jarr = null;
		
		try {
			jarr = (JSONArray)JSONValue.parse(ServletRequestUtils.getStringParameter(request, "dateList"));
		} catch (ServletRequestBindingException e) {
			e.printStackTrace();
		}
		
//		List<String> list = new ArrayList<String>();
//		for(int i=0; i<jarr.size(); i++){
//			list.add(jarr.get(i).toString());
//		}
		
		params.put("code", code);
		params.put("itemOption", itemOption);		
		params.put("point", pointCode);
		params.put("dateList", jarr);
		params.put("subCode", subCode);
		
		int result = productService.itemsCheck(params);

		Util.htmlPrint(String.valueOf(result), response);

		return null;
    }
	
	@RequestMapping(value = "/admin/product/ajaxItemGroupByList.af")
	public @ResponseBody JSONArray ajaxItemGroupByList(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		Map params = new HashMap();
		String year = (String) param.get("year");
		String month = (String) param.get("month");
		String code = (String) param.get("code");
		String pointCode = (String) param.get("pointCode");
		
		params.put("code", code);	
		params.put("yearmonth", year+month);
		params.put("point", pointCode);	
		
		List itemGroupByList = productService.itemGroupBylist(params);
	
		JSONArray jsonGroupBylist = new JSONArray();
		jsonGroupBylist.addAll(itemGroupByList);	

	    return jsonGroupBylist;
	}	
	
	public List ItemList(String param){

		Map params = new HashMap();
		params.put("code", param);
		params.put("point", "POINT01");		
		List<Map> itemList = productService.itemlist(params);
		
		return itemList;
	}
		
}
