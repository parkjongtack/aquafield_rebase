package com.soft.web.tiles;

/* 2016.07.22 KJH
 * tiles.xml 공통 데이터 전달
 *  
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tiles.Attribute;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.preparer.PreparerException;
import org.apache.tiles.preparer.ViewPreparer;
import org.apache.tiles.request.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.soft.web.base.CommonConstant;
import com.soft.web.service.admin.AdminDeskAdminAuthService;
import com.soft.web.service.admin.CustomerService;
import com.soft.web.service.admin.ReservationService;
import com.soft.web.util.AquaDateUtil;

public class CommonDeskAdminViewPreparer implements ViewPreparer {
	
	
	@Autowired
	AdminDeskAdminAuthService adminDeskAdminAuthService;
	
	@Autowired
	ReservationService reservationService;
	
	@Autowired
	CustomerService customerService;
	
    @Override
    public void execute(Request tilesRequest, AttributeContext attributeContext)
    throws PreparerException {
    	
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    	
    	HttpSession session = request.getSession();
        
    	Map adminInfo = (Map) session.getAttribute(CommonConstant.session.SESSION_KEY_DESK_ADMIN);
    	
    	if(adminInfo == null){
    		adminInfo = new HashMap();
    		adminInfo.put("SESSION_ADMIN_UID", "");
    		adminInfo.put("SESSION_ADMIN_ID", "");
    		adminInfo.put("SESSION_ADMIN_NM", "");
    		adminInfo.put("SESSION_LOGIN_DIGIT", "");
    	}
    	else{
    		Map sqlMap = new HashMap();
    		//-- TOP 메뉴
    		sqlMap.put("adminNum",adminInfo.get("SESSION_ADMIN_UID")); 		
    		attributeContext.putAttribute("resultsTopMenu", new Attribute(adminDeskAdminAuthService.deskAdminAuthListTopMenu(sqlMap)),true);
    		
    		//-- LEFT 메뉴
    		String sMenuCode = (String)request.getAttribute("sMenuCode");
    		if(sMenuCode != null && !"".equals(sMenuCode) ){
	    		sqlMap.put("sMenuCode",sMenuCode);
	    		attributeContext.putAttribute("resultsLeftMenu", new Attribute(adminDeskAdminAuthService.deskAdminAuthListLeftMenu(sqlMap)),true);
    		}
    		else{
    			attributeContext.putAttribute("resultsLeftMenu", new Attribute(""),true);
    		}
    		
    		//당일예약 건수 표시
    		Map param = new HashMap();
    		String todate = AquaDateUtil.getToday();
    		param.put("srch_reg_s", todate);
    		param.put("srch_reg_e", todate);
    		param.put("reserve_state", "ING");
    		int reservationCnt = reservationService.reservationListCnt(param);
    		
    		attributeContext.putAttribute("RESERVATIONCNT", new Attribute(reservationCnt),true);
    		
    		//미답변 1:1문의 리스트 건수 표시
    		Map param2 = new HashMap();
    		param2.put("srch_stat", "2");
    		int onetoneCnt = customerService.customerListCnt(param2);
    		
    		attributeContext.putAttribute("ONETONECNT", new Attribute(onetoneCnt),true);
    		
    	}
        attributeContext.putAttribute("ADMINFO", new Attribute(adminInfo),true);

    }

}
