package com.soft.web.controller.front;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.soft.web.base.GenericController;
import com.soft.web.base.KSPayApprovalCancelBean;
import com.soft.web.base.KSPayWebHostBean;
import com.soft.web.base.MkSPayWebHostBean;
import com.soft.web.mail.MailService;
import com.soft.web.service.admin.AdminEmailTempletService;
import com.soft.web.service.common.CommonService;
import com.soft.web.service.front.FrontMemberService;
import com.soft.web.service.front.FrontReservationService;
import com.soft.web.sms.SmsService;
import com.soft.web.util.AquaDateUtil;
import com.soft.web.util.DecoderUtil;
import com.soft.web.util.Util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;
import net.iharder.Base64;

@Controller
public class ReserVationController extends GenericController {

	protected Logger logger = LoggerFactory.getLogger(ReserVationController.class);
	
    @Resource(name="config")
    private Properties config;
	
	@Autowired
	FrontReservationService ReservationDelete;    
    
	
	@Autowired
	FrontReservationService frontReservationService;
	
	@Autowired
	FrontMemberService frontMemberService;
	
	@Autowired
	SmsService smsService;
	
	@Autowired
	CommonService commonService;	
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	AdminEmailTempletService adminEmailTempletService;	
	
	@RequestMapping(value = "/reserve/popup.af")
    public String popup(@RequestParam Map param, Model model, HttpSession session) {
	    Map memberInfo = (Map) session.getAttribute("MEM_INFO");
		String pointCode = "";
		
		if(!"".equals(param.get("pointCode")) && param.get("pointCode") != null){
			pointCode = param.get("pointCode").toString();
		}else {
			//STEP2 �뿉�꽌 �씠�쟾�쑝濡� 媛�湲곌� �븘�땶, �삩�씪�씤�삁�빟 踰꾪듉 �겢由� �떆�뿉�뒗, 珥덇린�뿉 硫붿씤�럹�씠吏� �꽭�뀡媛믪쑝濡� �뀑�똿
			if(!"".equals(session.getAttribute("POINT_CODE")) && session.getAttribute("POINT_CODE") != null){
				pointCode = session.getAttribute("POINT_CODE").toString();
			}
		}
		Map<String, Object> pointInfo = frontReservationService.getPointInfo(pointCode);
		
		model.addAttribute("MEMINFO", memberInfo);
		model.addAttribute("POINTINFO", pointInfo);
		
        return "/front/reservation/popup";
    }
	
	@RequestMapping(value = "/reserve/date.af")
    public String date(@RequestParam Map param, Model model, HttpSession session) {
		
		Map<String, Object> parameter = new HashMap();
		parameter.put("cate_code", param.get("prodNum"));
		parameter.put("point_code", param.get("pointCode"));
		
		List<Map> emptyDayList = frontReservationService.emptyDayList(parameter);
		List<Map> seasonDayList = frontReservationService.seasonDayList(parameter);			
		List<Map> reserveDayList = frontReservationService.reserveDayList(parameter);
		
		parameter.put("cate_code", "40000000");
		List<Map> eventDayList = frontReservationService.reserveDayList(parameter);
		
		
		JSONArray emptydaylist = new JSONArray();
		if(emptyDayList !=null){
			emptydaylist.addAll(emptyDayList);
		}
		
		JSONArray seasondaylist = new JSONArray();
		if(seasonDayList !=null){
			seasondaylist.addAll(seasonDayList);
		}
		JSONArray reservedayList = new JSONArray();
		if(reserveDayList !=null){
			reservedayList.addAll(reserveDayList);
		}
		JSONArray eventdayList = new JSONArray();
		if(eventDayList !=null){
			eventdayList.addAll(eventDayList);	
		}
		
		model.addAttribute("emptydays", emptydaylist);
		model.addAttribute("seasondays", seasondaylist);
		model.addAttribute("reservedays", reservedayList);
		model.addAttribute("eventdays", eventdayList);
		
        return "/front/reservation/date";
    }
	
	@RequestMapping(value = "/reserve/plant.af")
    public String plant(@RequestParam Map param, Model model, HttpSession session) {
		String seldate =(String)param.get("seldate");
		
		Map parameter = new HashMap();
		parameter.put("cate_code", param.get("prodNum"));
		parameter.put("point_code", param.get("pointCode"));
		parameter.put("yyyymmdd", seldate.replace(".", ""));
		
		List<Map> prodInfolist = frontReservationService.prodInfolist(parameter);
		model.addAttribute("prodInfos", prodInfolist);
		
        return "/front/reservation/plant";
    }
	
	@RequestMapping(value = "/reserve/packagePlant.af")
    public String packagePlant(@RequestParam Map param, Model model, HttpSession session) {
		
		String seldate =(String)param.get("seldate");
		
		Map parameter = new HashMap();
		parameter.put("cate_code", param.get("prodNum"));
		parameter.put("point_code", param.get("pointCode"));
		parameter.put("yyyymmdd", seldate.replace(".", ""));		

		List<Map> prodInfolist = frontReservationService.prodInfolist(parameter);	
		model.addAttribute("prodInfos", prodInfolist);
		
        return "/front/reservation/package";
    }
	
	@RequestMapping(value = "/reserve/prodInfoChk.af")
    public String prodInfoChk(@RequestParam Map param, Model model, HttpSession session, HttpServletResponse response) throws IOException {

	    response.setHeader("Set-Cookie", "Test1=TestCookieValue1; Secure; SameSite=None"); 
	    response.addHeader("Set-Cookie", "Test2=TestCookieValue2; Secure; SameSite=None"); 
	    response.addHeader("Set-Cookie", "Test3=TestCookieValue3; Secure; SameSite=None");			
		
		String html = "";
		String seldate =(String)param.get("seldate");
		
		Map parameter = new HashMap();
		parameter.put("cate_code", param.get("prodNum"));
		parameter.put("point_code", "POINT01");
		parameter.put("yyyymmdd", seldate.replace(".", ""));		

		int prodInfoCnt = frontReservationService.prodInfoCnt(parameter);

		Util.htmlPrint(String.valueOf(prodInfoCnt), response);

		return null;
    }	
	
	@RequestMapping(value = "/reserve/rantal.af")
    public String rantal(@RequestParam Map param, Model model, HttpSession session) {
		
		String seldate =(String)param.get("seldate");
		
		Map parameter = new HashMap();
		parameter.put("cate_code", param.get("prodNum"));
		parameter.put("point_code", "POINT01");
		parameter.put("yyyymmdd", seldate.replace(".", ""));		

		List<Map> prodInfolist = frontReservationService.prodInfolist(parameter);
		if(prodInfolist != null){
			for (Iterator iterator = prodInfolist.iterator(); iterator.hasNext();) {
				Map map = (Map) iterator.next();
				if("PRD001".equals(map.get("ITEM_OPTION"))){
					map.put("LIMIT", param.get("spaVal"));
				}else if("PRD002".equals(map.get("ITEM_OPTION"))){
					map.put("LIMIT", param.get("wpVal"));
				}else if("PRD003".equals(map.get("ITEM_OPTION"))){
					int sumLimit = Integer.parseInt(param.get("wpVal").toString()) + Integer.parseInt(param.get("spaVal").toString()) + Integer.parseInt(param.get("cpVal").toString());
					map.put("LIMIT", sumLimit);
				}
			}
		}		
	
		model.addAttribute("prodInfos", prodInfolist);
		model.addAttribute("option", parameter);			
        return "/front/reservation/rantal";
    }
	
	@RequestMapping(value = "/reserve/event.af")
    public String event(@RequestParam Map param, Model model, HttpSession session) {
		
		String seldate =(String)param.get("seldate");
		
		Map parameter = new HashMap();
		parameter.put("cate_code", param.get("prodNum"));
		parameter.put("point_code", "POINT01");
		parameter.put("yyyymmdd", seldate.replace(".", ""));		

		List<Map> prodInfolist = frontReservationService.prodInfolist(parameter);
		if(prodInfolist != null){
			for (Iterator iterator = prodInfolist.iterator(); iterator.hasNext();) {
				Map map = (Map) iterator.next();
				if("PRD001".equals(map.get("ITEM_OPTION"))){
					map.put("LIMIT", param.get("spaVal"));
				}else if("PRD002".equals(map.get("ITEM_OPTION"))){
					map.put("LIMIT", param.get("wpVal"));
				}else if("PRD003".equals(map.get("ITEM_OPTION"))){
					int sumLimit = Integer.parseInt(param.get("wpVal").toString()) + Integer.parseInt(param.get("spaVal").toString()) + Integer.parseInt(param.get("cpVal").toString());
					map.put("LIMIT", sumLimit);
				}
			}
		}
	
		model.addAttribute("prodInfos", prodInfolist);			
        return "/front/reservation/event";
    }
	
	@RequestMapping(value = "/reserve/point.af")
    public String point(@RequestParam Map param, Model model, HttpSession session) {
		List<Map<String, Object>> pointInfo = super.getCommonCodes("POINT_CODE");
		model.addAttribute("pointInfo", pointInfo);		
        return "/front/reservation/point";
    }
	
	@RequestMapping(value = "/reserve/step01.af")
    public String step01(@RequestParam Map param, Model model, HttpSession session) {
		int num = 10000000;
		String pointCode = "";
		
		if(!"".equals(param.get("prod")) && param.get("prod") != null){
			num = Integer.parseInt(param.get("prod").toString());
		}
		
		if(!"".equals(param.get("pointCode")) && param.get("pointCode") != null){
			pointCode = param.get("pointCode").toString();
		}else {
			//STEP2 �뿉�꽌 �씠�쟾�쑝濡� 媛�湲곌� �븘�땶, �삩�씪�씤�삁�빟 踰꾪듉 �겢由� �떆�뿉�뒗, 珥덇린�뿉 硫붿씤�럹�씠吏� �꽭�뀡媛믪쑝濡� �뀑�똿
			if(!"".equals(session.getAttribute("POINT_CODE")) && session.getAttribute("POINT_CODE") != null){
				pointCode = session.getAttribute("POINT_CODE").toString();
			}
		}
		Map<String, Object> pointInfo = frontReservationService.getPointInfo(pointCode);
		
		model.addAttribute("num", num);
		//model.addAttribute("pointCode", pointCode);
		model.addAttribute("pointInfo", pointInfo);
		
		List<Map<String, Object>> codePoint_code = super.getCommonCodes("POINT_CODE");
		model.addAttribute("codePoint_code", codePoint_code);	
		
		return "/front/reservation/res_step1";
    }
	
	@RequestMapping(value = "/reserve/kspayRcv.af")
    public String kspayRcv(@RequestParam Map param, Model model, HttpSession session, HttpServletResponse response) {
		
	    response.setHeader("Set-Cookie", "Test1=TestCookieValue1; Secure; SameSite=None"); 
	    response.addHeader("Set-Cookie", "Test2=TestCookieValue2; Secure; SameSite=None"); 
	    response.addHeader("Set-Cookie", "Test3=TestCookieValue3; Secure; SameSite=None");			
		
        return "/front/reservation/kspay_rcv";
    }
	
	@RequestMapping(value = "/reserve/payCheck.af")
    public String payCheck(@RequestParam Map param, Model model, HttpSession session, HttpServletResponse response) throws IOException, ParseException{

	    response.setHeader("Set-Cookie", "Test1=TestCookieValue1; Secure; SameSite=None"); 
	    response.addHeader("Set-Cookie", "Test2=TestCookieValue2; Secure; SameSite=None"); 
	    response.addHeader("Set-Cookie", "Test3=TestCookieValue3; Secure; SameSite=None");			
		
		Map parameter = objectInfo(param);
		
		int totalSum = (int) parameter.get("totalSum");
		int jsAmount = (int) parameter.get("jsAmount");	
		
		Map memberInfo = (Map) session.getAttribute("MEM_INFO");		
		
		Map parameters = new HashMap();
		//if(request.getParameter("mem_mobile3") != null || !request.getParameter("mem_mobile3").equals("")) {
			parameters.put("recipient_num",param.get("mem_mobile3")); // �닔�떊踰덊샇														
		//} else {
		//	parameters.put("recipient_num", memberInfo.get("MOBILE_NUM")); // �닔�떊踰덊샇
		//}

		parameters.put("reserve_uid", memberInfo.get("MEM_ID").toString());
		
		String updResultMembVal = frontReservationService.memberExtUpd(parameters);		
		
		
		String html = "_pay(obj);";
		if(totalSum != jsAmount){
			html = "alert('결제금액이 다릅니다. 확인바랍니다.')";
		}
    	Util.htmlPrint(html, response);	
		
        return null;
    }
	
	//PC 寃곗젣 �봽濡쒖꽭�뒪 遺�遺� �뀒�뒪�듃
	@RequestMapping(value = "/reserve/payProccess_test.af")
    public String payProccessTest(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
	    response.setHeader("Set-Cookie", "Test1=TestCookieValue1; Secure; SameSite=None"); 
	    response.addHeader("Set-Cookie", "Test2=TestCookieValue2; Secure; SameSite=None"); 
	    response.addHeader("Set-Cookie", "Test3=TestCookieValue3; Secure; SameSite=None");			
		
		String rsData =DecoderUtil.decode(param, "rsData");
		JSONParser jsonParser = new JSONParser();
		JSONObject rsDataObject = (JSONObject) jsonParser.parse(rsData);
		String strPoint = (String)rsDataObject.get("pointCode");
		String strPointNm = (String)rsDataObject.get("pointNm");
		
		Map memberInfo = (Map) session.getAttribute("MEM_INFO");
		String html = "";
		String message = "";
		String rcid		= request.getParameter("reWHCid");
		String rctype	= request.getParameter("reWHCtype");
		String rhash	= request.getParameter("reWHHash");
		String reserveMemUid	= request.getParameter("ECHA");			//�삁�빟�옄
		String reserveUid	= request.getParameter("ECHB");				//�삁�빟�뀒�씠釉�(TB_RESERVATION) �궎媛�
		String payMethod	= request.getParameter("ECHC");				//寃곗젣 �닔�떒
		
		String	authyn =  "";
		String	trno   =  "";
		String	trddt  =  "";
		String	trdtm  =  "";
		String	amt    =  "";
		String	authno =  "";
		String	msg1   =  "";
		String	msg2   =  "";
		String	ordno  =  "";
		String	isscd  =  "";
		String	aqucd  =  "";
		String	temp_v =  "";
		String	result =  "";
		String	halbu  =  "";
		String	cbtrno =  "";
		String	cbauthno =  "";
		String	resultcd =  "";

		Map objParams = objectInfo(param);		
		
		//濡쒓렇�씤 �꽭�뀡 議댁옱�뿬遺� 泥댄겕
		if(memberInfo ==  null){
			html = "alert('회원정보 불일치로 결제가 취소되었습니다. 다시 진행해주세요.');popFn.close();";
		}else{
			if(reserveMemUid == null){
				html = "alert('통신장애로 결제가 취소되었습니다. 다시 진행해주세요.');popFn.close();";	
			}else{
				if(!reserveMemUid.trim().equals(memberInfo.get("MEM_UID").toString().trim())){
					html = "alert('계정정보 불일치로 결제가 취소되었습니다. 다시 진행해주세요.');popFn.close();";
				}else{
					KSPayWebHostBean ipg = new KSPayWebHostBean(rcid);
					if (ipg.kspay_send_msg("1"))		//KSNET 寃곗젣寃곌낵 以� �븘�옒�뿉 �굹���굹吏� �븡�� �빆紐⑹씠 �븘�슂�븳 寃쎌슦 Null ���떊 �븘�슂�븳 �빆紐⑸챸�쓣 �꽕�젙�븷 �닔 �엳�뒿�땲�떎.
						{
							authyn	 = ipg.kspay_get_value("authyn");
							trno	 = ipg.kspay_get_value("trno"  );
							trddt	 = ipg.kspay_get_value("trddt" );
							trdtm	 = ipg.kspay_get_value("trdtm" );
							amt		 = ipg.kspay_get_value("amt"   );
							authno	 = ipg.kspay_get_value("authno");
							msg1	 = ipg.kspay_get_value("msg1"  );
							msg2	 = ipg.kspay_get_value("msg2"  );
							ordno	 = ipg.kspay_get_value("ordno" );
							isscd	 = ipg.kspay_get_value("isscd" );
							aqucd	 = ipg.kspay_get_value("aqucd" );
							temp_v	 = "";
							result	 = ipg.kspay_get_value("result");
							halbu	 = ipg.kspay_get_value("halbu");
							cbtrno	 = ipg.kspay_get_value("cbtrno");
							cbauthno	 = ipg.kspay_get_value("cbauthno");						
						
							Map params = new HashMap();			
				            params.put("auth_yn", authyn);
				            params.put("tr_no", trno);
				            params.put("tr_ddt", trddt);
				            params.put("tr_dtm", trdtm);
				            params.put("amonut", amt);
				            params.put("auth_no", authno);
				            params.put("msg1", msg1);
				            params.put("msg2", msg2);
				            params.put("ord_no", ordno);
				            params.put("iss_cd", isscd);
				            params.put("aqu_cd", aqucd);
				            params.put("result", result);
				            params.put("halbu", halbu);
				            params.put("cbt_no", cbtrno);
				            params.put("cbauth_no", cbauthno);
				            
							//濡쒓렇�씤 �꽭�뀡 議댁옱�뿬遺� �떎�떆 泥댄겕
							if(memberInfo ==  null){
								html = "alert('회원정보 불일치로 결제가 취소되었습니다. 다시 진행해주세요.');popFn.close();";
								message = "로그인 세션이 존재하지 않습니다.";
								// TODO 痍⑥냼 濡쒖쭅 �깭�슦湲�
								onPaymentCancel(param, trno, message);
							}else{
								if(reserveMemUid == null){
									html = "alert('통신장애로 결제가 취소되었습니다. 다시 진행해주세요.');popFn.close();";	
								}else{
									if(!reserveMemUid.trim().equals(memberInfo.get("MEM_UID").toString().trim())){
										html = "alert('계정정보 불일치로 결제가 취소되었습니다. 다시 진행해주세요.');popFn.close();";
										message = "로그인 세션이 다른 계정으로 변경되었습니다.";
										onPaymentCancel(param, trno, message);
									}else{	//寃곗젣 �넻�떊 �쟾 濡쒓렇�씤怨꾩젙怨� �넻�떊 �썑 濡쒓렇�씤 怨꾩젙�씠 �룞�씪�븷 寃쎌슦 吏꾪뻾
							            params.put("mem_uid", reserveMemUid);
					
										Map pgResult =  frontReservationService.pgResultIns(params);
										
										if(pgResult.get("RESULT").equals("ERROR")){
											html = "alert('처리중 에러가 발생하였습니다.');popFn.close();";
											message = "TB_PG_RESULT DB �옉�뾽以� �뿉�윭";
											onPaymentCancel(param, trno, message);
										}
									
										if (null != authyn && 1 == authyn.length() && !pgResult.get("RESULT").equals("ERROR"))
										{
											if (authyn.equals("O"))
											{
												html = "gotoFinal("+reserveMemUid+");";
												
												//吏��젙�닔�웾 泥댄겕
												List<Integer> getQuantityList = frontReservationService.getQuantityList(objParams);
												boolean chkQuantity = true;
												for(int quantity : getQuantityList){
													if(quantity < 0){
														chkQuantity = false;
														break;
													}
												}
												
												if(!chkQuantity){
													//吏��젙�닔�웾 珥덇낵濡� �씤�빐 痍⑥냼
													html = "alert('결제 도중 해당 상품 예약인원이 마감되어 결제가 취소되었습니다. 다시 확인해 주세요.');popFn.close();";
													message = "예약인원 초과";
													onPaymentCancel(param, trno, message);
												}else{
													logger.debug("###################### reservation update start!");
													logger.debug("###################### reservation update trno : " + trno);
													logger.debug("###################### reservation update reserveUid : " + param.get("reserveUid"));
													
													//寃곗젣 �꽦怨� �썑 �삁�빟 �긽�깭 �뾽�뜲�씠�듃 start
								            		Map Updparams = new HashMap();
								            		Updparams.put("payment_type", payMethod);
								            		Updparams.put("payment_nm", DecoderUtil.decode(param, "sndOrdername"));
								            		Updparams.put("reserve_state", "ING");			//寃곗젣 �쟾 �삁�빟 �긽�깭
								            		Updparams.put("pg_result", pgResult.get("UID"));
								            		Updparams.put("payment_date", new Date());
								            		Updparams.put("reserve_uid", param.get("reserveUid"));
								            		
								            		Updparams.put("spa_item", objParams.get("spaProdUid"));
								            		Updparams.put("water_item", objParams.get("waterProdUid"));
								            		Updparams.put("complex_item", objParams.get("complexProdUid")); //DB異붽�                 
								            		Updparams.put("rental1_item", objParams.get("rental1ProdUid"));
								            		Updparams.put("rental2_item", objParams.get("rental2ProdUid"));
								            		Updparams.put("rental3_item", objParams.get("rental3ProdUid"));
								            		Updparams.put("event1_item", objParams.get("event1ProdUid"));
								            		Updparams.put("event2_item", objParams.get("event2ProdUid"));
								            		Updparams.put("event3_item", objParams.get("event3ProdUid"));
								            		Updparams.put("itemSum0Cnt", objParams.get("itemSum0Cnt"));
								            		Updparams.put("itemSum1Cnt", objParams.get("itemSum1Cnt"));
								            		Updparams.put("itemSum2Cnt", objParams.get("itemSum2Cnt"));	
								            		
													String updResultVal = frontReservationService.reserVationUpd(Updparams);
													
													if(updResultVal.equals("ERROR")){
														logger.debug("###################### reservation update error!");
														html = "alert('처리중 에러가 발생하였습니다.(결제 상태 업데이트)');popFn.close();";
														message = "TB_RESERVATION 업데이트 도중 에러";
														onPaymentCancel(param, trno, message);
													}
													//寃곗젣 �꽦怨� �썑 �삁�빟 �긽�깭 �뾽�뜲�씠�듃 end
													logger.debug("###################### reservation update end!");
													
													String reserveDay = objParams.get("reserveDay").toString();
													reserveDay = reserveDay.substring(0, 4)+"."+reserveDay.substring(4, 6)+"."+reserveDay.substring(6, 8);
													//�삁�빟臾몄옄 諛쒖넚
													//String contents = "[�븘荑좎븘�븘�뱶]�삩�씪�씤 �삁�빟(�삁�빟踰덊샇:"+param.get("sndOrdernumber")+",�삁�빟�씪:"+reserveDay+")";
													Map smsParam = new HashMap();
													smsParam.put("point_code", "POINT01");
													smsParam.put("sms_type", "RESERVE");
													
													Map smsTemplte = commonService.getSmsTemplete(smsParam);
													String contents = smsTemplte.get("SMS_CONTENT").toString();
													
													contents = contents.replace("{지점}",strPointNm);//�삁�빟吏��젏 移섑솚
													contents = contents.replace("{번호}",param.get("sndOrdernumber").toString());//�삁�빟踰덊샇 移섑솚
													contents = contents.replace("{예약일}",reserveDay);//�삁�빟�씪 移섑솚
													
													Map parameters = new HashMap();
													parameters.put("recipient_num", memberInfo.get("MOBILE_NUM")); // �닔�떊踰덊샇
													//param.put("subject", "[�븘荑좎븘�븘�뱶]�삁�빟�솗�씤臾몄옄�엯�땲�떎.");
													parameters.put("content", contents); // �궡�슜 (SMS=88Byte, LMS=2000Byte)		
													//parameters.put("callback", "031-8072-8800"); // 諛쒖떊踰덊샇
													parameters.put("callback", config.getProperty("sms.tel.number."+strPoint)); // 諛쒖떊踰덊샇
													
													if(!smsService.sendSms(parameters)){
														html = "alert('처리중 에러가 발생하였습니다.(문자)');popFn.close();";
													}else{
														//SMS 諛쒖넚 �씠�젰 �벑濡�
														smsParam.put("sms_uid", smsTemplte.get("SMS_UID"));
														smsParam.put("mem_id", memberInfo.get("MEM_ID").toString());
														smsParam.put("custom_nm", memberInfo.get("MEM_NM").toString());
														smsParam.put("custom_mobile",  memberInfo.get("MOBILE_NUM"));
														smsParam.put("ins_ip", request.getRemoteAddr());
														smsParam.put("ins_id", memberInfo.get("MEM_ID").toString()); 
														smsParam.put("send_status", "OK");	
														smsParam.put("bigo", "PC_RESERVE:"+param.get("sndOrdernumber").toString());			
														
														String smsResult = commonService.insSmsSend(smsParam);
														if("ERROR".equals(smsResult)){
															html = "alert('처리중 에러가 발생하였습니다.(문자이력등록)');popFn.close();";
														}
													}
													
													//硫붿씪諛쒖넚 #####################################
													Map emailParam = new HashMap();
													emailParam.put("email_uid", "2");
													Map joinEmail = adminEmailTempletService.adminEmailTempletDetail(emailParam);
													
													String realPath = session.getServletContext().getRealPath("/common/mailTemplete");
													//String realPath = session.getServletContext().getRealPath("/common/upload/email_templet");
													
													String joinHtml = super.getHTMfile(realPath+"/reservation_complete.html");
													//String joinHtml = super.getHTMfile(realPath+"/2");					

													String nowTime = AquaDateUtil.getToday();
													nowTime = nowTime.substring(0, 4)+"."+nowTime.substring(4, 6)+"."+nowTime.substring(6, 8);
													joinHtml = joinHtml.replace("{{#NOW#}}",nowTime); // �쁽�옱�떆媛� 移섑솚
													joinHtml = joinHtml.replace("{{#NAME#}}",memberInfo.get("MEM_NM").toString());//�씠由� 移섑솚
													joinHtml = joinHtml.replace("{{#RESERVENUM#}}",DecoderUtil.decode(param, "sndOrdernumber"));//�삁�빟踰덊샇 移섑솚
													joinHtml = joinHtml.replace("{{#POINTNM#}}",strPointNm);//吏��젏 移섑솚
													joinHtml = joinHtml.replace("{{#RESERVEDAY#}}",objParams.get("reserveDay").toString());//�삁�빟�씪移섑솚
													joinHtml = joinHtml.replace("{{#GOODS#}}",DecoderUtil.decode(param, "sndGoodname").toString());//�긽�뭹紐낆튂�솚
													
													int cnt = Integer.parseInt(objParams.get("adultCnt").toString()) + Integer.parseInt(objParams.get("childCnt").toString());
													joinHtml = joinHtml.replace("{{#CNT#}}",String.valueOf(cnt));//�씤�썝�닔移섑솚
													
													int compareVal = Integer.parseInt(params.get("tr_no").toString().substring(0,1));				
													String r_TYPE = "";		
													switch (compareVal) {
													case 1: r_TYPE = "카드";
														break;
													case 2: r_TYPE = "실시간계좌이체";
														break;
													case 4: r_TYPE = "SSG PAY";
														break;						
													}
													joinHtml = joinHtml.replace("{{#PAYTYPE#}}",r_TYPE);//寃곗젣�닔�떒移섑솚
													
													String approvaldate = params.get("tr_ddt").toString();
													approvaldate = approvaldate.substring(0, 4)+"."+approvaldate.substring(4, 6)+"."+approvaldate.substring(6, 8);
													joinHtml = joinHtml.replace("{{#APPROVALDATE#}}",approvaldate);//�듅�씤�씪�떆移섑솚
													joinHtml = joinHtml.replace("{{#PRICE#}}",objParams.get("jsAmount").toString());//寃곗젣湲덉븸移섑솚   
													String reHtml= joinHtml.replace("{{#NAME#}}",memberInfo.get("MEM_NM").toString());
													
													//boolean booleanresult =	mailService.sendmail(mailSender, "aquafield@shinsegae.com", "�븘荑좎븘�븘�뱶", parameter.get("mem_id").toString(), parameter.get("mem_nm").toString(), "[�븘荑좎븘�븘�뱶]�삁�빟�셿猷뚮찓�씪�엯�땲�떎.", reHtml);
//													boolean booleanresult =	mailService.sendmail(mailSender, joinEmail.get("FROM_EMAIL").toString(), joinEmail.get("FORM_EMAIL_NM").toString(), memberInfo.get("MEM_ID").toString(), memberInfo.get("MEM_NM").toString(), joinEmail.get("EMAIL_TITLE").toString(), reHtml);
//																		
//													if(!booleanresult){
//														html = "泥섎━以� �뿉�윭媛� 諛쒖깮�븯���뒿�땲�떎.(�씠硫붿씪);popFn.close();";
//													}else{
//														//EMAIL 諛쒖넚 �씠�젰 �벑濡�
//														emailParam.put("point_code", strPoint);
//														emailParam.put("email_uid", "2");
//														emailParam.put("mem_id", memberInfo.get("MEM_ID").toString());
//														emailParam.put("custom_nm", memberInfo.get("MEM_NM").toString());
//														emailParam.put("custom_email", memberInfo.get("MEM_ID").toString());
//														emailParam.put("ins_ip", "");
//														emailParam.put("ins_id", memberInfo.get("MEM_ID").toString()); 
//														emailParam.put("send_status", "OK");
//														
//														String smsResult = commonService.insEmailSend(emailParam);
//														if("ERROR".equals(emailParam)){
//															html = "泥섎━以� �뿉�윭媛� 諛쒖깮�븯���뒿�땲�떎.(�씠硫붿씪 諛쒖넚 �벑濡�);popFn.close();";
//														}						
//													}					
													//###########################################			
												}
											}else
											{
												html = "alert('결제중 에러가 발생하였습니다. Error : "+authno.trim()+"');popFn.close();";
											}
											//ipg.kspay_send_msg("3");		// �젙�긽泥섎━媛� �셿猷뚮릺�뿀�쓣 寃쎌슦 �샇異쒗빀�땲�떎.(�씠 怨쇱젙�씠 �뾾�쑝硫� �씪�떆�쟻�쑝濡� kspay_send_msg("1")�쓣 �샇異쒗븯�뿬 嫄곕옒�궡�뿭 議고쉶媛� 媛��뒫�빀�땲�떎.)
										}
									}
								}
							}
						}
				}
			}
		}
		
    	Util.htmlPrint(html, response);		
		
        return null;
    }
	
	
	@RequestMapping(value = "/reserve/payProccess.af")
    public String payProccess(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
	    response.setHeader("Set-Cookie", "Test1=TestCookieValue1; Secure; SameSite=None"); 
	    response.addHeader("Set-Cookie", "Test2=TestCookieValue2; Secure; SameSite=None"); 
	    response.addHeader("Set-Cookie", "Test3=TestCookieValue3; Secure; SameSite=None");		
		
		String rsData =DecoderUtil.decode(param, "rsData");
		JSONParser jsonParser = new JSONParser();
		JSONObject rsDataObject = (JSONObject) jsonParser.parse(rsData);
		String strPoint = (String)rsDataObject.get("pointCode");
		String strPointNm = (String)rsDataObject.get("pointNm");
		/*
		Map param_set1 = new HashMap<>();
		param_set1.put("reserve_uid", Integer.parseInt(param.get("reserveUid").toString()));
		
		Map getReserveInfo = frontReservationService.getReserveInfoNew(param_set1);		
		
		if(getReserveInfo.get("POINT_CODE").equals("POINT01")) {
			strPointNm = "하남";
		} else if(getReserveInfo.get("POINT_CODE").equals("POINT03")) {
			strPointNm = "고양";
		} else if(getReserveInfo.get("POINT_CODE").equals("POINT05") || getReserveInfo.get("POINT_CODE").equals("POINT07")) {
			strPointNm = "안성";
		}
		*/
		int intUid2_2 = Integer.parseInt(param.get("reserveUid").toString());
		Map getReserveInfo2_2 = frontReservationService.getReserveInfo(intUid2_2); //�삁�빟�젙蹂� 媛��졇�삤湲�								

		if(getReserveInfo2_2.get("POINT_CODE").equals("POINT01")) {
			strPointNm = "하남";
		} else if(getReserveInfo2_2.get("POINT_CODE").equals("POINT03")) {
			strPointNm = "고양";
		} else if(getReserveInfo2_2.get("POINT_CODE").equals("POINT05") || getReserveInfo2_2.get("POINT_CODE").equals("POINT07")) {
			strPointNm = "안성";
		}		

		strPoint = getReserveInfo2_2.get("POINT_CODE").toString();		
		
		if(strPoint.equals("POINT07")) {
			strPoint = "POINT05";
		}
		
		//System.out.println(session.getAttribute("MEM_INFO"));
		
		//Map memberInfo = (Map) session.getAttribute("MEM_INFO");
		String html = "";
		String message = "";
		String rcid		= request.getParameter("reWHCid");
		String rctype	= request.getParameter("reWHCtype");
		String rhash	= request.getParameter("reWHHash");
		String reserveMemUid	= request.getParameter("ECHA");			//�삁�빟�옄
		String reserveUid	= request.getParameter("ECHB");				//�삁�빟�뀒�씠釉�(TB_RESERVATION) �궎媛�
		String payMethod	= request.getParameter("ECHC");				//寃곗젣 �닔�떒
		
		
		String	authyn =  "";
		String	trno   =  "";
		String	trddt  =  "";
		String	trdtm  =  "";
		String	amt    =  "";
		String	authno =  "";
		String	msg1   =  "";
		String	msg2   =  "";
		String	ordno  =  "";
		String	isscd  =  "";
		String	aqucd  =  "";
		String	temp_v =  "";
		String	result =  "";
		String	halbu  =  "";
		String	cbtrno =  "";
		String	cbauthno =  "";
		String	resultcd =  "";

		Map objParams = objectInfo(param);		
		
		Map memberInfo = (Map) session.getAttribute("MEM_INFO");
		
		if(memberInfo ==  null){			
			memberInfo = (Map) session.getAttribute("MEM_INFO2");
		}
		
		//濡쒓렇�씤 �꽭�뀡 議댁옱�뿬遺� 泥댄겕
		if(memberInfo ==  null){
			//html = "alert('1회원정보 불일치로 결제가 취소되었습니다. 다시 진행해주세요.');popFn.close();";
			html = "alert('회원정보 불일치로 결제가 취소되었습니다. 다시 진행해주세요.');popFn.close();";			
		}else{
			if(reserveMemUid == null){
				html = "alert('통신장애로 결제가 취소되었습니다. 다시 진행해주세요.');popFn.close();";	
			}else{
				if(!reserveMemUid.trim().equals(memberInfo.get("MEM_UID").toString().trim())){
					html = "alert('계정정보 불일치로 결제가 취소되었습니다. 다시 진행해주세요.');popFn.close();";
				}else{
					KSPayWebHostBean ipg = new KSPayWebHostBean(rcid);
					if (ipg.kspay_send_msg("1"))		//KSNET 寃곗젣寃곌낵 以� �븘�옒�뿉 �굹���굹吏� �븡�� �빆紐⑹씠 �븘�슂�븳 寃쎌슦 Null ���떊 �븘�슂�븳 �빆紐⑸챸�쓣 �꽕�젙�븷 �닔 �엳�뒿�땲�떎.
						{
							authyn	 = ipg.kspay_get_value("authyn");
							trno	 = ipg.kspay_get_value("trno"  );
							trddt	 = ipg.kspay_get_value("trddt" );
							trdtm	 = ipg.kspay_get_value("trdtm" );
							amt		 = ipg.kspay_get_value("amt"   );
							authno	 = ipg.kspay_get_value("authno");
							msg1	 = ipg.kspay_get_value("msg1"  );
							msg2	 = ipg.kspay_get_value("msg2"  );
							ordno	 = ipg.kspay_get_value("ordno" );
							isscd	 = ipg.kspay_get_value("isscd" );
							aqucd	 = ipg.kspay_get_value("aqucd" );
							temp_v	 = "";
							result	 = ipg.kspay_get_value("result");
							halbu	 = ipg.kspay_get_value("halbu");
							cbtrno	 = ipg.kspay_get_value("cbtrno");
							cbauthno	 = ipg.kspay_get_value("cbauthno");						
						
							Map params = new HashMap();			
				            params.put("auth_yn", authyn);
				            params.put("tr_no", trno);
				            params.put("tr_ddt", trddt);
				            params.put("tr_dtm", trdtm);
				            params.put("amonut", amt);
				            params.put("auth_no", authno);
				            params.put("msg1", msg1);
				            params.put("msg2", msg2);
				            params.put("ord_no", ordno);
				            params.put("iss_cd", isscd);
				            params.put("aqu_cd", aqucd);
				            params.put("result", result);
				            params.put("halbu", halbu);
				            params.put("cbt_no", cbtrno);
				            params.put("cbauth_no", cbauthno);
				            
							//濡쒓렇�씤 �꽭�뀡 議댁옱�뿬遺� �떎�떆 泥댄겕
							if(memberInfo ==  null){
								html = "alert('회원정보 불일치로 결제가 취소되었습니다. 다시 진행해주세요.');popFn.close();";
								message = "로그인 세션이 존재하지 않습니다.";
								// TODO 痍⑥냼 濡쒖쭅 �깭�슦湲�
								onPaymentCancel(param, trno, message);
							}else{
								if(reserveMemUid == null){
									html = "alert('통신장애로 결제가 취소되었습니다. 다시 진행해주세요.');popFn.close();";	
								}else{
									if(!reserveMemUid.trim().equals(memberInfo.get("MEM_UID").toString().trim())){
										html = "alert('계정정보 불일치로 결제가 취소되었습니다. 다시 진행해주세요.');popFn.close();";
										message = "로그인 세션이 다른 계정으로 변경되었습니다.";
										onPaymentCancel(param, trno, message);
									}else{	//寃곗젣 �넻�떊 �쟾 濡쒓렇�씤怨꾩젙怨� �넻�떊 �썑 濡쒓렇�씤 怨꾩젙�씠 �룞�씪�븷 寃쎌슦 吏꾪뻾
							            params.put("mem_uid", reserveMemUid);
					
										Map pgResult =  frontReservationService.pgResultIns(params);
										
										if(pgResult.get("RESULT").equals("ERROR")){
											html = "alert('처리중 에러가 발생하였습니다.');popFn.close();";
											message = "TB_PG_RESULT DB 작업중 에러";
											onPaymentCancel(param, trno, message);
										}
									
										if (null != authyn && 1 == authyn.length() && !pgResult.get("RESULT").equals("ERROR"))
										{
											if (authyn.equals("O"))
											{
												html = "gotoFinal("+reserveMemUid+");";
												
												//吏��젙�닔�웾 泥댄겕
												List<Integer> getQuantityList = frontReservationService.getQuantityList(objParams);
												boolean chkQuantity = true;
												for(int quantity : getQuantityList){
													if(quantity < 0){
														chkQuantity = false;
														break;
													}
												}
												
												if(!chkQuantity){
													//吏��젙�닔�웾 珥덇낵濡� �씤�빐 痍⑥냼
													html = "alert('결제 도중 해당 상품 예약인원이 마감되어 결제가 취소되었습니다. 다시 확인해 주세요.');popFn.close();";
													message = "예약인원 초과";
													onPaymentCancel(param, trno, message);
												}else{
													logger.debug("###################### reservation update start!");
													logger.debug("###################### reservation update trno : " + trno);
													logger.debug("###################### reservation update reserveUid : " + param.get("reserveUid"));
													
													//寃곗젣 �꽦怨� �썑 �삁�빟 �긽�깭 �뾽�뜲�씠�듃 start
								            		Map Updparams = new HashMap();
								            		Updparams.put("payment_type", payMethod);
								            		Updparams.put("payment_nm", DecoderUtil.decode(param, "sndOrdername"));
								            		Updparams.put("reserve_state", "ING");			//寃곗젣 �쟾 �삁�빟 �긽�깭
								            		Updparams.put("pg_result", pgResult.get("UID"));
								            		Updparams.put("payment_date", new Date());
								            		Updparams.put("reserve_uid", param.get("reserveUid"));
								            		
								            		Updparams.put("spa_item", objParams.get("spaProdUid"));
								            		Updparams.put("water_item", objParams.get("waterProdUid"));
								            		Updparams.put("complex_item", objParams.get("complexProdUid")); //DB異붽�                 
								            		Updparams.put("rental1_item", objParams.get("rental1ProdUid"));
								            		Updparams.put("rental2_item", objParams.get("rental2ProdUid"));
								            		Updparams.put("rental3_item", objParams.get("rental3ProdUid"));
								            		Updparams.put("event1_item", objParams.get("event1ProdUid"));
								            		Updparams.put("event2_item", objParams.get("event2ProdUid"));
								            		Updparams.put("event3_item", objParams.get("event3ProdUid"));
								            		Updparams.put("itemSum0Cnt", objParams.get("itemSum0Cnt"));
								            		Updparams.put("itemSum1Cnt", objParams.get("itemSum1Cnt"));
								            		Updparams.put("itemSum2Cnt", objParams.get("itemSum2Cnt"));	
								            		
													String updResultVal = frontReservationService.reserVationUpd(Updparams);
													
													
													if(updResultVal.equals("ERROR")){
														logger.debug("###################### reservation update error!");
														html = "alert('처리중 에러가 발생하였습니다.(결제 상태 업데이트)');popFn.close();";
														message = "TB_RESERVATION �뾽�뜲�씠�듃 �룄以� �뿉�윭";
														onPaymentCancel(param, trno, message);
													}
													//寃곗젣 �꽦怨� �썑 �삁�빟 �긽�깭 �뾽�뜲�씠�듃 end
													logger.debug("###################### reservation update end!");
													
													String reserveDay = objParams.get("reserveDay").toString();
													reserveDay = reserveDay.substring(0, 4)+"."+reserveDay.substring(4, 6)+"."+reserveDay.substring(6, 8);
													//�삁�빟臾몄옄 諛쒖넚
													//String contents = "[�븘荑좎븘�븘�뱶]�삩�씪�씤 �삁�빟(�삁�빟踰덊샇:"+param.get("sndOrdernumber")+",�삁�빟�씪:"+reserveDay+")";
													Map smsParam = new HashMap();
													smsParam.put("point_code", "POINT01");
													smsParam.put("sms_type", "RESERVE");
													
													Map smsTemplte = commonService.getSmsTemplete(smsParam);
													String contents = smsTemplte.get("SMS_CONTENT").toString();
													
													contents = contents.replace("{지점}",strPointNm);//�삁�빟吏��젏 移섑솚
													//contents = contents.replace("{지점}","");//�삁�빟吏��젏 移섑솚													
													contents = contents.replace("{번호}",param.get("sndOrdernumber").toString());//�삁�빟踰덊샇 移섑솚
													contents = contents.replace("{예약일}",reserveDay);//�삁�빟�씪 移섑솚
													
													Map memberInfo2 = (Map) session.getAttribute("MEM_INFO");
													
													Map getMemInfo = frontReservationService.getMemInfo(memberInfo2.get("MEM_ID").toString());;
																																							
													Map parameters = new HashMap();
													//if(request.getParameter("mem_mobile3") != null) {
													//	parameters.put("recipient_num",request.getParameter("mem_mobile3")); // �닔�떊踰덊샇														
													//} else {														
														parameters.put("recipient_num", getMemInfo.get("MOBILE_NUM").toString()); // �닔�떊踰덊샇														
														//parameters.put("recipient_num", memberInfo.get("MOBILE_NUM")); // �닔�떊踰덊샇
													//}
													//sms �삤瑜� : lms臾몄옄 蹂대궪�븣 subject�뿉 媛믪씠 �뾾�뼱�꽌 �굹�뜕 �삤瑜� �엯�땲�떎. 
													parameters.put("subject", "[아쿠필드예약안내]");
													parameters.put("content", contents); // �궡�슜 (SMS=88Byte, LMS=2000Byte)	
													parameters.put("callback", config.getProperty("sms.tel.number."+strPoint)); // 諛쒖떊踰덊샇 
													//parameters.put("callback", "031-8072-8800"); //諛쒖떊踰덊샇

													parameters.put("reserve_uid", memberInfo.get("MEM_ID").toString());
													
													String updResultMembVal = frontReservationService.memberExtUpd(parameters);
													
													Map parameter1 = new HashMap();
													parameter1.put("point_code", "POINT01");
													parameter1.put("mem_id", memberInfo.get("MEM_ID").toString());																										
													memberInfo = frontMemberService.memberInfo(parameter1);
													session.setAttribute("MEM_INFO", memberInfo);													
													
													Map parameter2 = new HashMap();
													parameter2.put("point_code", "POINT01");
													parameter2.put("mem_id", memberInfo.get("MEM_ID").toString());																										
													memberInfo = frontMemberService.memberInfo(parameter2);
													session.setAttribute("MEM_INFO2", memberInfo);													
													
													if(!smsService.sendSms(parameters)){
														html = "alert('처리중 에러가 발생하였습니다.(문자)');popFn.close();";
													}else{
														//SMS 諛쒖넚 �씠�젰 �벑濡�
														smsParam.put("sms_uid", smsTemplte.get("SMS_UID"));
														smsParam.put("mem_id", memberInfo.get("MEM_ID").toString());
														smsParam.put("custom_nm", memberInfo.get("MEM_NM").toString());
														smsParam.put("custom_mobile",  memberInfo.get("MOBILE_NUM"));
														smsParam.put("ins_ip", request.getRemoteAddr());
														smsParam.put("ins_id", memberInfo.get("MEM_ID").toString()); 
														smsParam.put("send_status", "OK");	
														smsParam.put("bigo", "PC_RESERVE:"+param.get("sndOrdernumber").toString());			
														
														String smsResult = commonService.insSmsSend(smsParam);
														if("ERROR".equals(smsResult)){
															html = "alert('처리중 에러가 발생하였습니다.(문자이력등록);popFn.close();";
														}
													}
													
													//硫붿씪諛쒖넚 #####################################
													Map emailParam = new HashMap();
													emailParam.put("email_uid", "2");
													Map joinEmail = adminEmailTempletService.adminEmailTempletDetail(emailParam);
													
													//String realPath = session.getServletContext().getRealPath("/common/mailTemplete");
													String realPath = session.getServletContext().getRealPath("/common/upload/email_templet");
													
													//String joinHtml = super.getHTMfile(realPath+"/reservation_complete.html");
													String joinHtml = super.getHTMfile(realPath+"/2");					

													String nowTime = AquaDateUtil.getToday();
													nowTime = nowTime.substring(0, 4)+"."+nowTime.substring(4, 6)+"."+nowTime.substring(6, 8);
													joinHtml = joinHtml.replace("{{#NOW#}}",nowTime); // �쁽�옱�떆媛� 移섑솚
													joinHtml = joinHtml.replace("{{#NAME#}}",memberInfo.get("MEM_NM").toString());//�씠由� 移섑솚
													joinHtml = joinHtml.replace("{{#RESERVENUM#}}",DecoderUtil.decode(param, "sndOrdernumber"));//�삁�빟踰덊샇 移섑솚
													joinHtml = joinHtml.replace("{{#POINTNM#}}",strPointNm);//吏��젏 移섑솚
													joinHtml = joinHtml.replace("{{#RESERVEDAY#}}",objParams.get("reserveDay").toString());//�삁�빟�씪移섑솚
													joinHtml = joinHtml.replace("{{#GOODS#}}",DecoderUtil.decode(param, "sndGoodname").toString());//�긽�뭹紐낆튂�솚
													
													int cnt = Integer.parseInt(objParams.get("adultCnt").toString()) + Integer.parseInt(objParams.get("childCnt").toString());
													joinHtml = joinHtml.replace("{{#CNT#}}",String.valueOf(cnt));//�씤�썝�닔移섑솚

													//int compareVal = Integer.parseInt(params.get("tr_no").toString().substring(0,1));				
													String r_TYPE = "";		
													switch (payMethod) {
													case "credit": r_TYPE = "신용카드";
														break;
													case "bank": r_TYPE = "실시간계좌이체";
														break;
													case "ssg": r_TYPE = "SSG PAY";
														break;						
													}
													joinHtml = joinHtml.replace("{{#PAYTYPE#}}",r_TYPE);//寃곗젣�닔�떒移섑솚
													
													String approvaldate = params.get("tr_ddt").toString();
													approvaldate = approvaldate.substring(0, 4)+"."+approvaldate.substring(4, 6)+"."+approvaldate.substring(6, 8);
													joinHtml = joinHtml.replace("{{#APPROVALDATE#}}",approvaldate);//�듅�씤�씪�떆移섑솚
													joinHtml = joinHtml.replace("{{#PRICE#}}",objParams.get("jsAmount").toString());//寃곗젣湲덉븸移섑솚   
													String reHtml= joinHtml.replace("{{#NAME#}}",memberInfo.get("MEM_NM").toString());

													String send_email_value = memberInfo.get("MEM_ID").toString();													
													if(memberInfo.get("EMAIL") != null && !memberInfo.get("MEM_ID").equals("")) {
														send_email_value = memberInfo.get("EMAIL").toString();
													}
													
													//boolean booleanresult =	mailService.sendmail(mailSender, "aquafield@shinsegae.com", "�븘荑좎븘�븘�뱶", parameter.get("mem_id").toString(), parameter.get("mem_nm").toString(), "[�븘荑좎븘�븘�뱶]�삁�빟�셿猷뚮찓�씪�엯�땲�떎.", reHtml);
													boolean booleanresult =	mailService.sendmail(mailSender, joinEmail.get("FROM_EMAIL").toString(), joinEmail.get("FORM_EMAIL_NM").toString(), send_email_value, memberInfo.get("MEM_NM").toString(), joinEmail.get("EMAIL_TITLE").toString(), reHtml);
																		
													if(!booleanresult){
														html = "1. 처리중 에러가 발생하였습니다.(이메일);popFn.close();";
													}else{
														//EMAIL 諛쒖넚 �씠�젰 �벑濡�
														emailParam.put("point_code", strPoint);
														emailParam.put("email_uid", "2");
														emailParam.put("mem_id", memberInfo.get("MEM_ID").toString());
														emailParam.put("custom_nm", memberInfo.get("MEM_NM").toString());
														emailParam.put("custom_email", memberInfo.get("MEM_ID").toString());
														emailParam.put("ins_ip", "");
														emailParam.put("ins_id", memberInfo.get("MEM_ID").toString()); 
														emailParam.put("send_status", "OK");
														
														String smsResult = commonService.insEmailSend(emailParam);
														if("ERROR".equals(emailParam)){
															html = "처리중 에러가 발생하였습니다.(이메일 발송 등록);popFn.close();";
														}						
													}					
													//###########################################			
												}
											}else
											{
												html = "alert('결제중 에러가 발생하였습니다. Error : "+authno.trim()+"');popFn.close();";
											}
											//ipg.kspay_send_msg("3");		// �젙�긽泥섎━媛� �셿猷뚮릺�뿀�쓣 寃쎌슦 �샇異쒗빀�땲�떎.(�씠 怨쇱젙�씠 �뾾�쑝硫� �씪�떆�쟻�쑝濡� kspay_send_msg("1")�쓣 �샇異쒗븯�뿬 嫄곕옒�궡�뿭 議고쉶媛� 媛��뒫�빀�땲�떎.)
										}
									}
								}
							}
						}
				}
				html = "gotoFinal("+reserveMemUid+");";
			}
		}
		
    	Util.htmlPrint(html, response);		
		
        return null;
    }
	
	//寃곗젣以� �삁�쇅 痍⑥냼 �봽濡쒖꽭�뒪
    public void onPaymentCancel(Map param, String trno, String message) throws Exception {
    	int compareVal = Integer.parseInt(trno.trim().substring(0,1));
		String authty = "";
		switch (compareVal) {
		case 1: 	//�떊�슜移대뱶
			authty = "1010";
			break;
		case 2: 	//怨꾩쥖�씠泥�
			authty = "2010";
			break;
		case 4: 	//SSG PAY - SSG MONEY
			authty = "4110";
			break;				
		}	
		
		// ------------------------------------------------------------------------
		// opaycanel
		// ------------------------------------------------------------------------		
		
		
		Map cancelParams = new HashMap();
		cancelParams.put("storeid", param.get("sndStoreid").toString());
		cancelParams.put("userName", DecoderUtil.decode(param,"sndOrdername"));
		cancelParams.put("email", DecoderUtil.decode(param,"sndEmail"));
		cancelParams.put("goodName", DecoderUtil.decode(param,"sndGoodname"));
		cancelParams.put("phoneNo", param.get("sndMobile").toString());
		cancelParams.put("authty", authty);
		cancelParams.put("trno", trno);
		cancelParams.put("canc_amt", param.get("sndAmount"));
		cancelParams.put("canc_seq", 1);
		cancelParams.put("canc_type", 0);
		cancelParams.put("mem_id", DecoderUtil.decode(param,"sndEmail"));
		cancelParams.put("message", message);
		
		/* 재고 돌리기 */
		int intUid2 = Integer.parseInt(cancelParams.get("uid").toString());
		Map getReserveInfo2 = frontReservationService.getReserveInfo(intUid2); //�삁�빟�젙蹂� 媛��졇�삤湲�	                	
    	
    	Map params_set_uid = new HashMap();
    	
		int intSpa_ad_Man2     = Integer.parseInt(cancelParams.get("spa_ad_Man").toString());
		int intSpa_ad_Women2   = Integer.parseInt(cancelParams.get("spa_ad_Women").toString());
		int intSpa_ch_Man2     = Integer.parseInt(cancelParams.get("spa_ch_Man").toString());
		int intSpa_ch_Women2   = Integer.parseInt(cancelParams.get("spa_ch_Women").toString());
		int intWater_ad_Man2   = Integer.parseInt(cancelParams.get("water_ad_Man").toString());
		int intWater_ad_Women2 = Integer.parseInt(cancelParams.get("water_ad_Women").toString());
		int intWater_ch_Man2   = Integer.parseInt(cancelParams.get("water_ch_Man").toString());
		int intWater_ch_Women2 = Integer.parseInt(cancelParams.get("water_ch_Women").toString());
		int intComplex_ad_Man2   = Integer.parseInt(cancelParams.get("complex_ad_Man").toString());
		int intComplex_ad_Women2 = Integer.parseInt(cancelParams.get("complex_ad_Women").toString());
		int intComplex_ch_Man2  = Integer.parseInt(cancelParams.get("complex_ch_Man").toString());
		int intComplex_ch_Women2 = Integer.parseInt(cancelParams.get("complex_ch_Women").toString());						
		int intSumVisitCnt2    = Integer.parseInt(cancelParams.get("sumVisitCnt").toString());
		
		int intTotalCnt2 = intSpa_ad_Man2 + intSpa_ad_Women2 + intSpa_ch_Man2 + intSpa_ch_Women2 + intWater_ad_Man2 + intWater_ad_Women2 + intWater_ch_Man2 + intWater_ch_Women2
						  + intComplex_ad_Man2 + intComplex_ad_Women2 + intComplex_ch_Man2 + intComplex_ch_Women2;
		
		//if(intTotalCnt2 > 0 && intSumVisitCnt2 > intTotalCnt2){
			
			int intAdultSum = intSpa_ad_Man2 + intSpa_ad_Women2 + intWater_ad_Man2 + intWater_ad_Women2 + intComplex_ad_Man2 + intComplex_ad_Women2;
			int intChildSum = intSpa_ch_Man2 + intSpa_ch_Women2 + intWater_ch_Man2 + intWater_ch_Women2 + intComplex_ch_Man2 + intComplex_ch_Women2;
			//cancelParams.put("adult_sum", intAdultSum);
			//cancelParams.put("child_sum", intChildSum);								
			
			System.out.println("--------------------------------------------------------------------------------------------");
			System.out.println(getReserveInfo2.get("SPA_ITEM"));
			System.out.println(getReserveInfo2.get("WATER_ITEM"));		
			System.out.println(getReserveInfo2.get("COMPLEX_ITEM"));	
			System.out.println(intAdultSum + intChildSum);
			System.out.println("--------------------------------------------------------------------------------------------");
			
			if(getReserveInfo2.get("SPA_ITEM") != null) {							
				cancelParams.put("set_uid", getReserveInfo2.get("SPA_ITEM"));
				cancelParams.put("quantity", intAdultSum + intChildSum);
				
				frontReservationService.itemQtyUpdMinus(cancelParams);								
			}
			
			if(getReserveInfo2.get("WATER_ITEM") != null) {							
				cancelParams.put("set_uid", getReserveInfo2.get("WATER_ITEM"));
				cancelParams.put("quantity", intAdultSum + intChildSum);
				
				frontReservationService.itemQtyUpdMinus(cancelParams);								
			}
			
			if(getReserveInfo2.get("COMPLEX_ITEM") != null) {
				cancelParams.put("set_uid", getReserveInfo2.get("COMPLEX_ITEM"));
				cancelParams.put("quantity", intAdultSum + intChildSum);
				
				frontReservationService.itemQtyUpdMinus(cancelParams);	        				
			}	 							
			
			//String insResult = newReservationIns(cancelParams, getReserveInfo);
			//if("ERROR".equals(insResult)){
			//	html = "부분예약취소중 에러가 발생하였습니다.(NEW RESERVE DB INS)";
			//}
		//}
		/* 재고 돌리기 */				
		
		if(authty.equals("1010")){
			cardCancelAction(cancelParams, "Y", message);  //移대뱶寃곗젣	
		}else if(authty.equals("2010")){
			cashCancelAction(cancelParams, "Y", message);  //怨꾩쥖�씠泥� 
		}else if(authty.equals("4110")){
			ssgCancelAction(cancelParams,"Y", message);  //SSG PAY - SSG MONEY
		}
    }
	
	//紐⑤컮�씪 寃곗젣 �봽濡쒖꽭�뒪 遺�遺�(�뀒�뒪�듃�슜)
	@RequestMapping(value = "/reserve/mkspay_test.af")
    public String mkspay_test(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
	    response.setHeader("Set-Cookie", "Test1=TestCookieValue1; Secure; SameSite=None"); 
	    response.addHeader("Set-Cookie", "Test2=TestCookieValue2; Secure; SameSite=None"); 
	    response.addHeader("Set-Cookie", "Test3=TestCookieValue3; Secure; SameSite=None");			
		
		Map memberInfo = (Map) session.getAttribute("MEM_INFO");
		String pointUrl = session.getAttribute("POINT_URL").toString();
		String redirect = "redirect:"+pointUrl;
		String html = "";
		String message = "";
		String rcid		= request.getParameter("reCommConId"		);
		String rctype	= request.getParameter("reCommType"		);
		String rhash	= request.getParameter("reHash"			);
		String reserveMemUid	= request.getParameter("ECHA"			);	//
		String reserveUid	= request.getParameter("ECHB"			);
		String payMethod	= request.getParameter("ECHC"			);
		
		/* rcid �뾾�쑝硫� 寃곗젣瑜� �걹源뚯� 吏꾪뻾�븯吏� �븡怨� 以묎컙�뿉 寃곗젣痍⑥냼 */
		String	authyn =  "";
		String	trno   =  "";
		String	trddt  =  "";
		String	trdtm  =  "";
		String	amt    =  "";
		String	authno =  "";
		String	msg1   =  "";
		String	msg2   =  "";
		String	ordno  =  "";
		String	isscd  =  "";
		String	aqucd  =  "";
		String	result =  "";

		String	resultcd =  "";

		Map rsData = (Map) session.getAttribute("RS_DATA");
		Map objParams = objectInfo(rsData);	
		
		String strPoint = (String)rsData.get("pointCode");
		String strPointNm = (String)rsData.get("pointNm");
		
		MkSPayWebHostBean ipg = new MkSPayWebHostBean(rcid);

		//濡쒓렇�씤 �꽭�뀡 議댁옱�뿬遺� 泥댄겕
		if(memberInfo == null){
			html = "alert('로그인 세션이 존재하지 않아, 결제 진행이 불가합니다. 재로그인 해주시길 바랍니다.');popFn.close();";
			logger.debug("###################### 로그인 세션이 존재하지 않아, 결제 진행이 불가합니다. 재로그인 해주시길 바랍니다.");
		}else{
			if(reserveMemUid == null){
				html = "alert('결제 통신이 끊어졌습니다. 마이페이지에서 예약 내역 확인 후, 결제 내역이 없을 시 다시 결제해주시기 바랍니다.');popFn.close();";
				logger.debug("###################### 결제 통신이 끊어졌습니다. 마이페이지에서 예약 내역 확인 후, 결제 내역이 없을 시 다시 결제해주시기 바랍니다.");
			}else{
				if(!reserveMemUid.trim().equals(memberInfo.get("MEM_UID").toString().trim())){
					html = "alert('로그인 세션이 다른 계정으로 로그인 되어, 결제 진행이 불가합니다.');popFn.close();";
					logger.debug("###################### 로그인 세션이 다른 계정으로 로그인 되어, 결제 진행이 불가합니다.");
				}else{
					if (ipg.send_msg("1"))		//KSNET 寃곗젣寃곌낵 以� �븘�옒�뿉 �굹���굹吏� �븡�� �빆紐⑹씠 �븘�슂�븳 寃쎌슦 Null ���떊 �븘�슂�븳 �빆紐⑸챸�쓣 �꽕�젙�븷 �닔 �엳�뒿�땲�떎.
					{
						authyn	= ipg.getValue("authyn");
						trno	 = ipg.getValue("trno"  );
						trddt	 = ipg.getValue("trddt" );
						trdtm	 = ipg.getValue("trdtm" );
						amt		 = ipg.getValue("amt"   );
						authno = ipg.getValue("authno");
						msg1	 = ipg.getValue("msg1"  );
						msg2	 = ipg.getValue("msg2"  );
						ordno	 = ipg.getValue("ordno" );
						isscd	 = ipg.getValue("isscd" );
						aqucd	 = ipg.getValue("aqucd" );
						//temp_v	 = ipg.getValue("temp_v");
						result	 = ipg.getValue("result");
						
						Map params = new HashMap();			
			            params.put("auth_yn", authyn);
			            params.put("tr_no", trno);
			            params.put("tr_ddt", trddt);
			            params.put("tr_dtm", trdtm);
			            params.put("amonut", amt);
			            params.put("auth_no", authno);
			            params.put("msg1", msg1);
			            params.put("msg2", msg2);
			            params.put("ord_no", ordno);
			            params.put("iss_cd", isscd);
			            params.put("aqu_cd", aqucd);
			            params.put("result", result);
			            params.put("halbu", "");
			            params.put("cbt_no", "");
			            params.put("cbauth_no", "");
			           
			            //濡쒓렇�씤 �꽭�뀡 議댁옱�뿬遺� �떎�떆 泥댄겕
						if(memberInfo == null){
							html = "alert('로그인 세션이 존재하지 않아, 결제 진행이 불가합니다. 재로그인 해주시길 바랍니다.');popFn.close();";
							logger.debug("###################### 로그인 세션이 존재하지 않아, 결제 진행이 불가합니다. 재로그인 해주시길 바랍니다.");
							message = "로그인 세션이 존재하지 않습니다.";
							// TODO 痍⑥냼 濡쒖쭅 �깭�슦湲�
							onPaymentCancel(rsData, trno, message);
						}else{
							if(!reserveMemUid.trim().equals(memberInfo.get("MEM_UID").toString().trim())){
								html = "alert('로그인 세션이 다른 계정으로 로그인 되어, 결제 진행이 불가합니다.');popFn.close();";
								logger.debug("###################### 로그인 세션이 다른 계정으로 로그인 되어, 결제 진행이 불가합니다.");
								message = "로그인 세션이 다른 계정으로 변경되었습니다.";
								onPaymentCancel(rsData, trno, message);
							}else{	//寃곗젣 �넻�떊 �쟾 濡쒓렇�씤怨꾩젙怨� �넻�떊 �썑 濡쒓렇�씤 怨꾩젙�씠 �룞�씪�븷 寃쎌슦 吏꾪뻾
								params.put("mem_uid", reserveMemUid);

								Map pgResult =  frontReservationService.pgResultIns(params);
								
								if(pgResult.get("RESULT").equals("ERROR")){
									html = "alert('PG_RESULT_INS 처리중 에러 발생');popFn.close();";
									logger.debug("###################### PG_RESULT_INS 처리중 에러 발생");
									message = "PG_RESULT_INS 처리중 에러 발생";
									onPaymentCancel(rsData, trno, message);
								}			

								if (null != authyn && 1 == authyn.length() && !pgResult.get("RESULT").equals("ERROR"))
								{
									if (authyn.equals("O"))
									{				
										//吏��젙�닔�웾 泥댄겕
										List<Integer> getQuantityList = frontReservationService.getQuantityList(objParams);
										boolean chkQuantity = true;
										for(int quantity : getQuantityList){
											if(quantity < 0){
												chkQuantity = false;
												break;
											}
										}
										
										if(!chkQuantity){
											//吏��젙�닔�웾 珥덇낵濡� �씤�빐 痍⑥냼
											html = "alert('죄송합니다. 결제 도중 해당 상품 예약인원이 초과되어 예약이 불가 합니다.');popFn.close();";
											logger.debug("###################### 해당 상품 예약인원이 초과");
											message = "해당 상품 예약인원이 초과";
											onPaymentCancel(rsData, trno, message);
										}else{
											logger.debug("###################### reservation mobile update start!");
											logger.debug("###################### reservation mobile update trno : " + trno);
											logger.debug("###################### reservation mobile update reserveUid : " + session.getAttribute("reserveUid").toString());
											
											//寃곗젣 �꽦怨� �썑 �삁�빟 �긽�깭 �뾽�뜲�씠�듃 start
						            		Map Updparams = new HashMap();
						            		Updparams.put("payment_type", payMethod);
						            		Updparams.put("payment_nm", rsData.get("sndOrdername"));
						            		Updparams.put("reserve_state", "ING");			//寃곗젣 �쟾 �삁�빟 �긽�깭
						            		Updparams.put("pg_result", pgResult.get("UID"));
						            		Updparams.put("payment_date", new Date());
						            		
						            		Updparams.put("spa_item", objParams.get("spaProdUid"));
						            		Updparams.put("water_item", objParams.get("waterProdUid"));
						            		Updparams.put("complex_item", objParams.get("complexProdUid")); //DB異붽�                 
						            		Updparams.put("rental1_item", objParams.get("rental1ProdUid"));
						            		Updparams.put("rental2_item", objParams.get("rental2ProdUid"));
						            		Updparams.put("rental3_item", objParams.get("rental3ProdUid"));
						            		Updparams.put("event1_item", objParams.get("event1ProdUid"));
						            		Updparams.put("event2_item", objParams.get("event2ProdUid"));
						            		Updparams.put("event3_item", objParams.get("event3ProdUid"));
						            		Updparams.put("itemSum0Cnt", objParams.get("itemSum0Cnt"));
						            		Updparams.put("itemSum1Cnt", objParams.get("itemSum1Cnt"));
						            		Updparams.put("itemSum2Cnt", objParams.get("itemSum2Cnt"));
						            		
						            		//String reserveUid = session.getAttribute("reserveUid").toString();
						            		logger.debug("#####reserve_uid :::: " + reserveUid);
						            		
						            		Updparams.put("reserve_uid", reserveUid);
											String updResultVal = frontReservationService.reserVationUpd(Updparams);
											
											if(updResultVal.equals("ERROR")){
												logger.debug("###################### reservation mobile update error!");
												html = "alert('처리중 에러가 발생하였습니다.(결제 상태 업데이트)');popFn.close();";
												message = "TB_RESERVATION 업데이트 처리중 에러 발생";
												logger.debug("###################### TB_RESERVATION 업데이트 처리중 에러 발생");
												onPaymentCancel(rsData, trno, message);
											}
											//寃곗젣 �꽦怨� �썑 �삁�빟 �긽�깭 �뾽�뜲�씠�듃 end
											logger.debug("###################### reservation mobile update end!");
											
											String reserveDay = objParams.get("reserveDay").toString();
											reserveDay = reserveDay.substring(0, 4)+"."+reserveDay.substring(4, 6)+"."+reserveDay.substring(6, 8);
											//�삁�빟臾몄옄 諛쒖넚
											//String contents = "[�븘荑좎븘�븘�뱶]�삩�씪�씤 �삁�빟(�삁�빟踰덊샇:"+parameter.get("order_num").toString()+",�삁�빟�씪:"+reserveDay+")";
											Map smsParam = new HashMap();
											smsParam.put("point_code", "POINT01");
											smsParam.put("sms_type", "RESERVE");
											
											Map smsTemplte = commonService.getSmsTemplete(smsParam);
											String contents = smsTemplte.get("SMS_CONTENT").toString();
											contents = contents.replace("{吏��젏}",strPointNm);//�삁�빟吏��젏 移섑솚
											contents = contents.replace("{踰덊샇}",rsData.get("sndOrdernumber").toString());//�삁�빟踰덊샇 移섑솚
											contents = contents.replace("{�삁�빟�씪}",reserveDay);//�삁�빟�씪 移섑솚
											
											Map parameters = new HashMap();
											parameters.put("recipient_num", memberInfo.get("MOBILE_NUM")); // �닔�떊踰덊샇
											//param.put("subject", "[�븘荑좎븘�븘�뱶]�삁�빟�솗�씤臾몄옄�엯�땲�떎.");
											parameters.put("content", contents); // �궡�슜 (SMS=88Byte, LMS=2000Byte)		
											//parameters.put("callback", "031-8072-8800"); // 諛쒖떊踰덊샇
											parameters.put("callback", config.getProperty("sms.tel.number."+strPoint)); // 諛쒖떊踰덊샇
											
//											if(!smsService.sendSms(parameters)){
//												html = "alert('泥섎━以� �뿉�윭媛� 諛쒖깮�븯���뒿�땲�떎.(臾몄옄)');popFn.close();";
//												logger.debug("###################### 泥섎━以� �뿉�윭媛� 諛쒖깮�븯���뒿�땲�떎.(臾몄옄)");
//											}else{
//												//SMS 諛쒖넚 �씠�젰 �벑濡�
//												smsParam.put("sms_uid", smsTemplte.get("SMS_UID"));
//												smsParam.put("mem_id", memberInfo.get("MEM_ID").toString());
//												smsParam.put("custom_nm", memberInfo.get("MEM_NM").toString());
//												smsParam.put("custom_mobile",  memberInfo.get("MOBILE_NUM"));
//												smsParam.put("ins_ip", request.getRemoteAddr());
//												smsParam.put("ins_id", memberInfo.get("MEM_ID").toString()); 
//												smsParam.put("send_status", "OK");	
//												smsParam.put("bigo", "M_RESERVE:" + rsData.get("sndOrdernumber").toString());			
//												
//												String smsResult = commonService.insSmsSend(smsParam);
//												if("ERROR".equals(smsResult)){
//													html = "alert('泥섎━以� �뿉�윭媛� 諛쒖깮�븯���뒿�땲�떎.(臾몄옄�씠�젰�벑濡�)');popFn.close();";
//													logger.debug("###################### 泥섎━以� �뿉�윭媛� 諛쒖깮�븯���뒿�땲�떎.(臾몄옄�씠�젰�벑濡�)");
//												}
//											}
											
											//硫붿씪諛쒖넚 #####################################
											Map emailParam = new HashMap();
											emailParam.put("email_uid", "2");
											Map joinEmail = adminEmailTempletService.adminEmailTempletDetail(emailParam);
											
											String realPath = session.getServletContext().getRealPath("/common/mailTemplete");
											//String realPath = session.getServletContext().getRealPath("/common/upload/email_templet");
											
											String joinHtml = super.getHTMfile(realPath+"/reservation_complete.html");
											//String joinHtml = super.getHTMfile(realPath+"/2");
											
											String nowTime = AquaDateUtil.getToday();
											nowTime = nowTime.substring(0, 4)+"."+nowTime.substring(4, 6)+"."+nowTime.substring(6, 8);
											joinHtml = joinHtml.replace("{{#NOW#}}",nowTime); // �쁽�옱�떆媛� 移섑솚
											joinHtml = joinHtml.replace("{{#NAME#}}",memberInfo.get("MEM_NM").toString());//�씠由� 移섑솚
											joinHtml = joinHtml.replace("{{#RESERVENUM#}}",rsData.get("sndOrdernumber").toString());//�삁�빟踰덊샇 移섑솚
											joinHtml = joinHtml.replace("{{#POINTNM#}}",strPointNm);//吏��젏 移섑솚
											joinHtml = joinHtml.replace("{{#RESERVEDAY#}}",objParams.get("reserveDay").toString());//�삁�빟�씪移섑솚
											joinHtml = joinHtml.replace("{{#GOODS#}}",rsData.get("sndGoodname").toString());//�긽�뭹紐낆튂�솚
											
											int cnt = Integer.parseInt(objParams.get("adultCnt").toString()) + Integer.parseInt(objParams.get("childCnt").toString());
											joinHtml = joinHtml.replace("{{#CNT#}}",String.valueOf(cnt));//�씤�썝�닔移섑솚
											
//											int compareVal = Integer.parseInt(params.get("tr_no").toString().substring(0,1));				
											String r_TYPE = "";		
											switch (payMethod) {
											case "credit": r_TYPE = "카드";
												break;
											case "bank": r_TYPE = "실시간계좌이체";
												break;
											case "ssg": r_TYPE = "SSG PAY";
												break;						
											}
											joinHtml = joinHtml.replace("{{#PAYTYPE#}}",r_TYPE);//寃곗젣�닔�떒移섑솚
											
											String approvaldate = params.get("tr_ddt").toString();
											approvaldate = approvaldate.substring(0, 4)+"."+approvaldate.substring(4, 6)+"."+approvaldate.substring(6, 8);
											joinHtml = joinHtml.replace("{{#APPROVALDATE#}}",approvaldate);//�듅�씤�씪�떆移섑솚
											joinHtml = joinHtml.replace("{{#PRICE#}}",objParams.get("jsAmount").toString());//寃곗젣湲덉븸移섑솚   
											String reHtml= joinHtml.replace("{{#NAME#}}",memberInfo.get("MEM_NM").toString());

											//boolean booleanresult =	mailService.sendmail(mailSender, "aquafield@shinsegae.com", "�븘荑좎븘�븘�뱶", parameter.get("mem_id").toString(), parameter.get("mem_nm").toString(), "[�븘荑좎븘�븘�뱶]�삁�빟�셿猷뚮찓�씪�엯�땲�떎.", reHtml);
//											boolean booleanresult =	mailService.sendmail(mailSender, joinEmail.get("FROM_EMAIL").toString(), joinEmail.get("FORM_EMAIL_NM").toString(), memberInfo.get("MEM_ID").toString(), memberInfo.get("MEM_NM").toString(), joinEmail.get("EMAIL_TITLE").toString(), reHtml);					
//											
//											if(!booleanresult){
//												html = "泥섎━以� �뿉�윭媛� 諛쒖깮�븯���뒿�땲�떎.(�씠硫붿씪)";
//												logger.debug("###################### 泥섎━以� �뿉�윭媛� 諛쒖깮�븯���뒿�땲�떎.(�씠硫붿씪)");
//											}else{
//												//EMAIL 諛쒖넚 �씠�젰 �벑濡�
//												emailParam.put("point_code", "POINT01");
//												emailParam.put("email_uid", "2");
//												emailParam.put("mem_id", memberInfo.get("MEM_ID").toString());
//												emailParam.put("custom_nm", memberInfo.get("MEM_NM").toString());
//												emailParam.put("custom_email", memberInfo.get("MEM_ID").toString());
//												emailParam.put("ins_ip", "");
//												emailParam.put("ins_id", memberInfo.get("MEM_ID").toString()); 
//												emailParam.put("send_status", "OK");
//												
//												String smsResult = commonService.insEmailSend(emailParam);
//												if("ERROR".equals(emailParam)){
//													html = "泥섎━以� �뿉�윭媛� 諛쒖깮�븯���뒿�땲�떎.(�씠硫붿씪 諛쒖넚 �벑濡�)";
//													logger.debug("###################### 泥섎━以� �뿉�윭媛� 諛쒖깮�븯���뒿�땲�떎.(�씠硫붿씪 諛쒖넚 �벑濡�)");
//												}						
//											}
											
											redirect = "redirect:/reserve/mobile_final.af";
											//###########################################		
										}
									}else
									{
										html = "결제중 에러가 발생하였습니다. Error : "+authno.trim();
									}

									//ipg.send_msg("3");		// �쁽�옱 寃곗젣��湲� �긽�깭�씠硫� kspay_send_msg("1")�쓣 �샇異쒗븯�뀛�빞 寃곗젣媛� 泥섎━�맗�땲�떎.
								}
							}
						}
					}
				}
			}
		}

	    return redirect;
    }	
	
	//紐⑤컮�씪 寃곗젣 �봽濡쒖꽭�뒪 遺�遺�
	@RequestMapping(value = "/reserve/mkspay.af")
    public String mkspay(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {

	    response.setHeader("Set-Cookie", "Test1=TestCookieValue1; Secure; SameSite=None"); 
	    response.addHeader("Set-Cookie", "Test2=TestCookieValue2; Secure; SameSite=None"); 
	    response.addHeader("Set-Cookie", "Test3=TestCookieValue3; Secure; SameSite=None");			
		
		Map memberInfo = (Map) session.getAttribute("MEM_INFO");
		String pointUrl = session.getAttribute("POINT_URL").toString();
		String redirect = "redirect:"+pointUrl;
		String html = "";
		String message = "";
		String rcid		= request.getParameter("reCommConId"		);
		String rctype	= request.getParameter("reCommType"		);
		String rhash	= request.getParameter("reHash"			);
		String reserveMemUid	= request.getParameter("ECHA"			);	//
		String reserveUid	= request.getParameter("ECHB"			);
		String payMethod	= request.getParameter("ECHC"			);
		
		/* rcid �뾾�쑝硫� 寃곗젣瑜� �걹源뚯� 吏꾪뻾�븯吏� �븡怨� 以묎컙�뿉 寃곗젣痍⑥냼 */
		String	authyn =  "";
		String	trno   =  "";
		String	trddt  =  "";
		String	trdtm  =  "";
		String	amt    =  "";
		String	authno =  "";
		String	msg1   =  "";
		String	msg2   =  "";
		String	ordno  =  "";
		String	isscd  =  "";
		String	aqucd  =  "";
		String	result =  "";

		String	resultcd =  "";

		Map rsData = (Map) session.getAttribute("RS_DATA");
		Map objParams = objectInfo(rsData);	
		
		String strPoint = (String)rsData.get("pointCode");
		String strPointNm = (String)rsData.get("pointNm");
		
		MkSPayWebHostBean ipg = new MkSPayWebHostBean(rcid);

		//濡쒓렇�씤 �꽭�뀡 議댁옱�뿬遺� 泥댄겕
		if(memberInfo == null){
			html = "alert('로그인 세션이 존재하지 않아, 결제 진행이 불가합니다. 재로그인 해주시길 바랍니다.');popFn.close();";
			logger.debug("######################  로그인 세션이 존재하지 않아, 결제 진행이 불가합니다. 재로그인 해주시길 바랍니다.");
		}else{
			if(reserveMemUid == null){
				html = "alert('결제 통신이 끊어졌습니다. 마이페이지에서 예약 내역 확인 후, 결제 내역이 없을 시 다시 결제해주시기 바랍니다.');popFn.close();";
				logger.debug("###################### 결제 통신이 끊어졌습니다. 마이페이지에서 예약 내역 확인 후, 결제 내역이 없을 시 다시 결제해주시기 바랍니다.");
			}else{
				if(!reserveMemUid.trim().equals(memberInfo.get("MEM_UID").toString().trim())){
					html = "alert('로그인 세션이 다른 계정으로 로그인 되어, 결제 진행이 불가합니다.');popFn.close();";
					logger.debug("###################### 로그인 세션이 다른 계정으로 로그인 되어, 결제 진행이 불가합니다.");
				}else{
					if (ipg.send_msg("1"))		//KSNET 寃곗젣寃곌낵 以� �븘�옒�뿉 �굹���굹吏� �븡�� �빆紐⑹씠 �븘�슂�븳 寃쎌슦 Null ���떊 �븘�슂�븳 �빆紐⑸챸�쓣 �꽕�젙�븷 �닔 �엳�뒿�땲�떎.
					{
						authyn	= ipg.getValue("authyn");
						trno	 = ipg.getValue("trno"  );
						trddt	 = ipg.getValue("trddt" );
						trdtm	 = ipg.getValue("trdtm" );
						amt		 = ipg.getValue("amt"   );
						authno = ipg.getValue("authno");
						msg1	 = ipg.getValue("msg1"  );
						msg2	 = ipg.getValue("msg2"  );
						ordno	 = ipg.getValue("ordno" );
						isscd	 = ipg.getValue("isscd" );
						aqucd	 = ipg.getValue("aqucd" );
						//temp_v	 = ipg.getValue("temp_v");
						result	 = ipg.getValue("result");
						
						Map params = new HashMap();			
			            params.put("auth_yn", authyn);
			            params.put("tr_no", trno);
			            params.put("tr_ddt", trddt);
			            params.put("tr_dtm", trdtm);
			            params.put("amonut", amt);
			            params.put("auth_no", authno);
			            params.put("msg1", msg1);
			            params.put("msg2", msg2);
			            params.put("ord_no", ordno);
			            params.put("iss_cd", isscd);
			            params.put("aqu_cd", aqucd);
			            params.put("result", result);
			            params.put("halbu", "");
			            params.put("cbt_no", "");
			            params.put("cbauth_no", "");
			           
			            //濡쒓렇�씤 �꽭�뀡 議댁옱�뿬遺� �떎�떆 泥댄겕
						if(memberInfo == null){
							html = "alert('로그인 세션이 존재하지 않아, 결제 진행이 불가합니다. 재로그인 해주시길 바랍니다.');popFn.close();";
							logger.debug("###################### 로그인 세션이 존재하지 않아, 결제 진행이 불가합니다. 재로그인 해주시길 바랍니다.");
							message = "로그인 세션이 존재하지 않습니다.";
							// TODO 痍⑥냼 濡쒖쭅 �깭�슦湲�
							onPaymentCancel(rsData, trno, message);
						}else{
							if(!reserveMemUid.trim().equals(memberInfo.get("MEM_UID").toString().trim())){
								html = "alert('로그인 세션이 다른 계정으로 로그인 되어, 결제 진행이 불가합니다.');popFn.close();";
								logger.debug("###################### 로그인 세션이 다른 계정으로 로그인 되어, 결제 진행이 불가합니다.");
								message = "로그인 세션이 다른 계정으로 변경되었습니다.";
								onPaymentCancel(rsData, trno, message);
							}else{	//寃곗젣 �넻�떊 �쟾 濡쒓렇�씤怨꾩젙怨� �넻�떊 �썑 濡쒓렇�씤 怨꾩젙�씠 �룞�씪�븷 寃쎌슦 吏꾪뻾
								params.put("mem_uid", reserveMemUid);

								Map pgResult =  frontReservationService.pgResultIns(params);
								
								if(pgResult.get("RESULT").equals("ERROR")){
									html = "alert('PG_RESULT_INS 처리중 에러 발생');popFn.close();";
									logger.debug("###################### PG_RESULT_INS 처리중 에러 발생");
									message = "PG_RESULT_INS 처리중 에러 발생";
									onPaymentCancel(rsData, trno, message);
								}			

								if (null != authyn && 1 == authyn.length() && !pgResult.get("RESULT").equals("ERROR"))
								{
									if (authyn.equals("O"))
									{				
										//吏��젙�닔�웾 泥댄겕
										List<Integer> getQuantityList = frontReservationService.getQuantityList(objParams);
										boolean chkQuantity = true;
										for(int quantity : getQuantityList){
											if(quantity < 0){
												chkQuantity = false;
												break;
											}
										}
										
										if(!chkQuantity){
											//吏��젙�닔�웾 珥덇낵濡� �씤�빐 痍⑥냼
											html = "alert('죄송합니다. 결제 도중 해당 상품 예약인원이 초과되어 예약이 불가 합니다.');popFn.close();";
											logger.debug("###################### 해당 상품 예약인원이 초과");
											message = "해당 상품 예약인원이 초과";
											onPaymentCancel(rsData, trno, message);
										}else{
											logger.debug("###################### reservation mobile update start!");
											logger.debug("###################### reservation mobile update trno : " + trno);
											logger.debug("###################### reservation mobile update reserveUid : " + session.getAttribute("reserveUid").toString());
											
											//寃곗젣 �꽦怨� �썑 �삁�빟 �긽�깭 �뾽�뜲�씠�듃 start
						            		Map Updparams = new HashMap();
						            		Updparams.put("payment_type", payMethod);
						            		Updparams.put("payment_nm", rsData.get("sndOrdername"));
						            		Updparams.put("reserve_state", "ING");			//寃곗젣 �쟾 �삁�빟 �긽�깭
						            		Updparams.put("pg_result", pgResult.get("UID"));
						            		Updparams.put("payment_date", new Date());
						            		
						            		Updparams.put("spa_item", objParams.get("spaProdUid"));
						            		Updparams.put("water_item", objParams.get("waterProdUid"));
						            		Updparams.put("complex_item", objParams.get("complexProdUid")); //DB異붽�                 
						            		Updparams.put("rental1_item", objParams.get("rental1ProdUid"));
						            		Updparams.put("rental2_item", objParams.get("rental2ProdUid"));
						            		Updparams.put("rental3_item", objParams.get("rental3ProdUid"));
						            		Updparams.put("event1_item", objParams.get("event1ProdUid"));
						            		Updparams.put("event2_item", objParams.get("event2ProdUid"));
						            		Updparams.put("event3_item", objParams.get("event3ProdUid"));
						            		Updparams.put("itemSum0Cnt", objParams.get("itemSum0Cnt"));
						            		Updparams.put("itemSum1Cnt", objParams.get("itemSum1Cnt"));
						            		Updparams.put("itemSum2Cnt", objParams.get("itemSum2Cnt"));
						            		
						            		//String reserveUid = session.getAttribute("reserveUid").toString();
						            		logger.debug("#####reserve_uid :::: " + reserveUid);
						            		
						            		Updparams.put("reserve_uid", reserveUid);
											String updResultVal = frontReservationService.reserVationUpd(Updparams);
											
											if(updResultVal.equals("ERROR")){
												logger.debug("###################### reservation mobile update error!");
												html = "alert('처리중 에러가 발생하였습니다.(결제 상태 업데이트)');popFn.close();";
												message = "TB_RESERVATION 업데이트 처리중 에러 발생";
												logger.debug("###################### TB_RESERVATION 업데이트 처리중 에러 발생");
												onPaymentCancel(rsData, trno, message);
											}
											//寃곗젣 �꽦怨� �썑 �삁�빟 �긽�깭 �뾽�뜲�씠�듃 end
											logger.debug("###################### reservation mobile update end!");
											
											String reserveDay = objParams.get("reserveDay").toString();
											reserveDay = reserveDay.substring(0, 4)+"."+reserveDay.substring(4, 6)+"."+reserveDay.substring(6, 8);
											//�삁�빟臾몄옄 諛쒖넚
											//String contents = "[�븘荑좎븘�븘�뱶]�삩�씪�씤 �삁�빟(�삁�빟踰덊샇:"+parameter.get("order_num").toString()+",�삁�빟�씪:"+reserveDay+")";
											Map smsParam = new HashMap();
											smsParam.put("point_code", "POINT01");
											smsParam.put("sms_type", "RESERVE");
											
											Map smsTemplte = commonService.getSmsTemplete(smsParam);
											String contents = smsTemplte.get("SMS_CONTENT").toString();
											//contents = contents.replace("{지점}",strPointNm);//�삁�빟吏��젏 移섑솚
											/*
											contents = contents.replace("{지점}","");//�삁�빟吏��젏 移섑솚
											contents = contents.replace("{번호}",rsData.get("sndOrdernumber").toString());//�삁�빟踰덊샇 移섑솚
											contents = contents.replace("{예약일}",reserveDay);//�삁�빟�씪 移섑솚
											*/
						        			int intUid2_2 = Integer.parseInt(reserveUid);
						        			Map getReserveInfo2_2 = frontReservationService.getReserveInfo(intUid2_2); //�삁�빟�젙蹂� 媛��졇�삤湲�								

						        			String pointnm = "하남";
						        			if(getReserveInfo2_2.get("POINT_CODE").equals("POINT01")) {
							        			pointnm = "하남";		        				
						        			}
						        			
						        			if(getReserveInfo2_2.get("POINT_CODE").equals("POINT03")) {
							        			pointnm = "고양";		        				
						        			}		        			
						        			
						        			if(getReserveInfo2_2.get("POINT_CODE").equals("POINT05") || getReserveInfo2_2.get("POINT_CODE").equals("POINT07")) {
							        			pointnm = "안성";		        				
						        			}	
						        			
											contents = contents.replace("{지점}",pointnm);//�삁�빟踰덊샇 移섑솚
											contents = contents.replace("{번호}",getReserveInfo2_2.get("ORDER_NUM").toString());//�삁�빟踰덊샇 移섑솚
											contents = contents.replace("{예약일}",getReserveInfo2_2.get("RESERVE_DATE").toString());//�삁�빟�씪 移섑솚	  											
											
											strPoint = getReserveInfo2_2.get("POINT_CODE").toString();											
											
											/*
											Map memberInfo2 = (Map) session.getAttribute("MEM_INFO");
											
											Map parameters5 = new HashMap();
											parameters5.put("mem_id",memberInfo2.get("MEM_ID"));
											
											String memId =DecoderUtil.decode(parameters5, "mem_id");
											Map getMemInfo2 = frontReservationService.getMemInfo(memId);										
											
											
											
											*/
											Map parameters = new HashMap();
											//if(request.getParameter("mem_mobile3") != null) {
												parameters.put("recipient_num",getReserveInfo2_2.get("MEM_MOBILE_NUM")); // �닔�떊踰덊샇														
											//} else {														
											//	parameters.put("recipient_num", getMemInfo2.get("MOBILE_NUM").toString()); // �닔�떊踰덊샇														
											//	parameters.put("recipient_num", memberInfo.get("MOBILE_NUM")); // �닔�떊踰덊샇
											//}											
											
											//parameters.put("recipient_num", memberInfo.get("MOBILE_NUM")); // �닔�떊踰덊샇
											//param.put("subject", "[�븘荑좎븘�븘�뱶]�삁�빟�솗�씤臾몄옄�엯�땲�떎.");
											parameters.put("content", contents); // �궡�슜 (SMS=88Byte, LMS=2000Byte)		
											//parameters.put("callback", "031-8072-8800"); // 諛쒖떊踰덊샇
											parameters.put("callback", config.getProperty("sms.tel.number."+strPoint)); // 諛쒖떊踰덊샇
											
											if(!smsService.sendSms(parameters)){
												html = "alert('처리중 에러가 발생하였습니다.(문자)');popFn.close();";
												logger.debug("###################### 처리중 에러가 발생하였습니다.(문자)");
											}else{
												//SMS 諛쒖넚 �씠�젰 �벑濡�
												smsParam.put("sms_uid", smsTemplte.get("SMS_UID"));
												smsParam.put("mem_id", memberInfo.get("MEM_ID").toString());
												smsParam.put("custom_nm", memberInfo.get("MEM_NM").toString());
												smsParam.put("custom_mobile",  memberInfo.get("MOBILE_NUM"));
												smsParam.put("ins_ip", request.getRemoteAddr());
												smsParam.put("ins_id", memberInfo.get("MEM_ID").toString()); 
												smsParam.put("send_status", "OK");	
												smsParam.put("bigo", "M_RESERVE:" + rsData.get("sndOrdernumber").toString());			
												
												String smsResult = commonService.insSmsSend(smsParam);
												if("ERROR".equals(smsResult)){
													html = "alert('처리중 에러가 발생하였습니다.(문자이력등록)');popFn.close();";
													logger.debug("###################### 처리중 에러가 발생하였습니다.(문자이력등록)");
												}
											}
											
											//硫붿씪諛쒖넚 #####################################
											Map emailParam = new HashMap();
											emailParam.put("email_uid", "2");
											Map joinEmail = adminEmailTempletService.adminEmailTempletDetail(emailParam);
											
											//String realPath = session.getServletContext().getRealPath("/common/mailTemplete");
											String realPath = session.getServletContext().getRealPath("/common/upload/email_templet");
											
											//String joinHtml = super.getHTMfile(realPath+"/reservation_complete.html");
											String joinHtml = super.getHTMfile(realPath+"/2");
											
											String nowTime = AquaDateUtil.getToday();
											nowTime = nowTime.substring(0, 4)+"."+nowTime.substring(4, 6)+"."+nowTime.substring(6, 8);
											joinHtml = joinHtml.replace("{{#NOW#}}",nowTime); // �쁽�옱�떆媛� 移섑솚
											joinHtml = joinHtml.replace("{{#NAME#}}",memberInfo.get("MEM_NM").toString());//�씠由� 移섑솚
											joinHtml = joinHtml.replace("{{#RESERVENUM#}}",rsData.get("sndOrdernumber").toString());//�삁�빟踰덊샇 移섑솚
											joinHtml = joinHtml.replace("{{#POINTNM#}}",strPointNm);//吏��젏 移섑솚
											joinHtml = joinHtml.replace("{{#RESERVEDAY#}}",objParams.get("reserveDay").toString());//�삁�빟�씪移섑솚
											joinHtml = joinHtml.replace("{{#GOODS#}}",rsData.get("sndGoodname").toString());//�긽�뭹紐낆튂�솚
											
											int cnt = Integer.parseInt(objParams.get("adultCnt").toString()) + Integer.parseInt(objParams.get("childCnt").toString());
											joinHtml = joinHtml.replace("{{#CNT#}}",String.valueOf(cnt));//�씤�썝�닔移섑솚
											
//											int compareVal = Integer.parseInt(params.get("tr_no").toString().substring(0,1));				
											String r_TYPE = "";		
											switch (payMethod) {
											case "credit": r_TYPE = "카드";
												break;
											case "bank": r_TYPE = "실시간계좌이체";
												break;
											case "ssg": r_TYPE = "SSG PAY";
												break;						
											}
											joinHtml = joinHtml.replace("{{#PAYTYPE#}}",r_TYPE);//寃곗젣�닔�떒移섑솚
											
											String approvaldate = params.get("tr_ddt").toString();
											approvaldate = approvaldate.substring(0, 4)+"."+approvaldate.substring(4, 6)+"."+approvaldate.substring(6, 8);
											joinHtml = joinHtml.replace("{{#APPROVALDATE#}}",approvaldate);//�듅�씤�씪�떆移섑솚
											joinHtml = joinHtml.replace("{{#PRICE#}}",objParams.get("jsAmount").toString());//寃곗젣湲덉븸移섑솚   
											String reHtml= joinHtml.replace("{{#NAME#}}",memberInfo.get("MEM_NM").toString());

											//boolean booleanresult =	mailService.sendmail(mailSender, "aquafield@shinsegae.com", "�븘荑좎븘�븘�뱶", parameter.get("mem_id").toString(), parameter.get("mem_nm").toString(), "[�븘荑좎븘�븘�뱶]�삁�빟�셿猷뚮찓�씪�엯�땲�떎.", reHtml);
											boolean booleanresult =	mailService.sendmail(mailSender, joinEmail.get("FROM_EMAIL").toString(), joinEmail.get("FORM_EMAIL_NM").toString(), memberInfo.get("MEM_ID").toString(), memberInfo.get("MEM_NM").toString(), joinEmail.get("EMAIL_TITLE").toString(), reHtml);					
											
											if(!booleanresult){
												//html = "처리중 에러가 발생하였습니다.(이메일)";
												logger.debug("###################### 처리중 에러가 발생하였습니다.(이메일)");
											}else{
												//EMAIL 諛쒖넚 �씠�젰 �벑濡�
												emailParam.put("point_code", "POINT01");
												emailParam.put("email_uid", "2");
												emailParam.put("mem_id", memberInfo.get("MEM_ID").toString());
												emailParam.put("custom_nm", memberInfo.get("MEM_NM").toString());
												emailParam.put("custom_email", memberInfo.get("MEM_ID").toString());
												emailParam.put("ins_ip", "");
												emailParam.put("ins_id", memberInfo.get("MEM_ID").toString()); 
												emailParam.put("send_status", "OK");
												
												String smsResult = commonService.insEmailSend(emailParam);
												if("ERROR".equals(emailParam)){
													html = "처리중 에러가 발생하였습니다.(이메일 발송 등록)";
													logger.debug("###################### 처리중 에러가 발생하였습니다.(이메일 발송 등록)");
												}						
											}
											
											redirect = "redirect:/reserve/mobile_final.af";
											//###########################################		
										}
									}else
									{
										html = "결제중 에러가 발생하였습니다. Error : "+authno.trim();
									}

									//ipg.send_msg("3");		// �쁽�옱 寃곗젣��湲� �긽�깭�씠硫� kspay_send_msg("1")�쓣 �샇異쒗븯�뀛�빞 寃곗젣媛� 泥섎━�맗�땲�떎.
								}
							}
						}
					}
				}
			}
		}

	    return redirect;
    }	

	@RequestMapping(value = "/reserve/mobile_final.af")
    public String mobile_final(@RequestParam Map param, Model model, HttpSession session) throws ParseException, UnsupportedEncodingException {
		
		Map memberInfo = (Map) session.getAttribute("MEM_INFO");
		
		Map rsData = (Map) session.getAttribute("RS_DATA");
		Map objParams = objectInfo(rsData);

		String reserveDay = (String) objParams.get("reserveDay");
		reserveDay = reserveDay.substring(0, 4)+"년"+reserveDay.substring(4, 6)+"월"+reserveDay.substring(6, 8)+"일";
		
		Map userInfo = new HashMap();
		userInfo.put("NAME", memberInfo.get("MEM_NM"));
		userInfo.put("MOBLIE", memberInfo.get("MOBILE_NUM"));
		userInfo.put("MAIL", memberInfo.get("MEM_ID"));
		userInfo.put("RESERVEUID", DecoderUtil.decode(param, "sndOrdernumber"));
		userInfo.put("RESERVEDAY", reserveDay.substring(0, 4)+"."+reserveDay.substring(4, 6)+"."+reserveDay.substring(6, 8)+".");		
		
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("completeMsg", objParams.get("completeMsg"));
		model.addAttribute("reserveDay", reserveDay);
		
		if(session.getAttribute("RS_DATA") != null){
			//session.removeAttribute("RS_DATA");
		}
		
        return "/front/reservation/res_final_m";
    }	
	
	@RequestMapping(value = "/reserve/final.af")
    public String resFinal(@RequestParam Map param, Model model, HttpSession session) throws ParseException, UnsupportedEncodingException {
		
		//Map memberInfo = (Map) session.getAttribute("MEM_INFO");
		//Map rsData = (Map) session.getAttribute("RS_DATA");
		//Map rsData = (Map) param.get("rsData");
		
		Map rsData = new HashMap();
		rsData.put("rsData", param.get("rsData"));
		Map objParams = objectInfo(rsData);

		String reserveDay = (String) objParams.get("reserveDay");
		reserveDay = reserveDay.substring(0, 4)+"�뀈"+reserveDay.substring(4, 6)+"�썡"+reserveDay.substring(6, 8)+"�씪";

		Map params = new HashMap();
		params.put("mem_uid", param.get("reserveMemUid").toString().trim());
		Map memberInfo = frontMemberService.memberUpdInfo(params);
		
		Map userInfo = new HashMap();
		userInfo.put("NAME", DecoderUtil.decode(memberInfo,"MEM_NM"));
		userInfo.put("MOBLIE", memberInfo.get("MOBILE_NUM"));
		userInfo.put("MAIL", memberInfo.get("MEM_ID"));
		userInfo.put("RESERVEUID", DecoderUtil.decode(param, "sndOrdernumber"));
		userInfo.put("RESERVEDAY", reserveDay.substring(0, 4)+"."+reserveDay.substring(4, 6)+"."+reserveDay.substring(6, 8)+".");		
		
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("completeMsg", objParams.get("completeMsg"));
		model.addAttribute("reserveDay", reserveDay);
		
		if(session.getAttribute("RS_DATA") != null){
			//session.removeAttribute("RS_DATA");
		}
		
        return "/front/reservation/res_final";
    }
	
	//�떊�슜移대뱶 痍⑥냼 愿��젴
	@RequestMapping(value = "/reserve/card_cancel.af")
    public String card_cancel(@RequestParam Map param, Model model, HttpSession session, HttpServletResponse response, HttpServletRequest request) throws Exception{

	    response.setHeader("Set-Cookie", "Test1=TestCookieValue1; Secure; SameSite=None"); 
	    response.addHeader("Set-Cookie", "Test2=TestCookieValue2; Secure; SameSite=None"); 
	    response.addHeader("Set-Cookie", "Test3=TestCookieValue3; Secure; SameSite=None");			
		
		Map memberInfo = (Map) session.getAttribute("MEM_INFO");
		String realPath = session.getServletContext().getRealPath("/common/upload/email_templet");
		
		Map cancelParams = new HashMap(param);
		cancelParams.put("storeid", request.getParameter("storeid"));
		
		//cancelParams.put("userName", DecoderUtil.decode(param,"userName")); //二쇰Ц�옄紐�
		//cancelParams.put("email", DecoderUtil.decode(param,"email"));       //�씠硫붿씪
		//cancelParams.put("phoneNo", request.getParameter("phoneNo"));       //�쑕���쟾�솕
		
		String userName = memberInfo.get("MEM_NM").toString();
		if(userName == null) {
			userName = "아쿠아회원";
		}
		
		cancelParams.put("userName", URLDecoder.decode(userName, "UTF-8"));
		cancelParams.put("email", URLDecoder.decode(memberInfo.get("MEM_ID").toString(), "UTF-8"));
		cancelParams.put("phoneNo", URLDecoder.decode(memberInfo.get("MOBILE_NUM").toString(), "UTF-8"));
		
		System.out.println("----------회원정보모바일확인------------" + URLDecoder.decode(memberInfo.get("MOBILE_NUM").toString(), "UTF-8") + "----------------------");
		
		cancelParams.put("goodName", DecoderUtil.decode(param,"goodName"));
		cancelParams.put("authty", request.getParameter("authty"));
		cancelParams.put("trno", request.getParameter("trno"));
		cancelParams.put("canc_amt", request.getParameter("canc_amt"));
		cancelParams.put("canc_seq", request.getParameter("canc_seq"));
		cancelParams.put("canc_type", request.getParameter("canc_type"));
		cancelParams.put("uid", request.getParameter("uid"));
		cancelParams.put("ordernum", request.getParameter("ordernum"));
		cancelParams.put("reserveday", request.getParameter("reserveday"));
		cancelParams.put("mem_id", DecoderUtil.decode(param,"email"));
		cancelParams.put("custom_nm", DecoderUtil.decode(param,"userName"));
		cancelParams.put("ins_ip", request.getRemoteAddr());
		cancelParams.put("realPath", realPath);
		cancelParams.put("reserve_state", DecoderUtil.decode(param,"reserve_state"));
		cancelParams.put("penalty_amt", request.getParameter("penalty_amt"));
		cancelParams.put("pointCode", DecoderUtil.decode(param,"pointCode"));
		cancelParams.put("pointNm", DecoderUtil.decode(param,"pointNm"));
		
		// ------------------------------------------------------------------------
		// card cancel
		// ------------------------------------------------------------------------				
		
		/* 재고 돌리기 */
		int intUid2 = Integer.parseInt(cancelParams.get("uid").toString());
		Map getReserveInfo2 = frontReservationService.getReserveInfo(intUid2); //�삁�빟�젙蹂� 媛��졇�삤湲�	                	
    	
    	Map params_set_uid = new HashMap();
    	
		int intSpa_ad_Man2     = Integer.parseInt(cancelParams.get("spa_ad_Man").toString());
		int intSpa_ad_Women2   = Integer.parseInt(cancelParams.get("spa_ad_Women").toString());
		int intSpa_ch_Man2     = Integer.parseInt(cancelParams.get("spa_ch_Man").toString());
		int intSpa_ch_Women2   = Integer.parseInt(cancelParams.get("spa_ch_Women").toString());
		int intWater_ad_Man2   = Integer.parseInt(cancelParams.get("water_ad_Man").toString());
		int intWater_ad_Women2 = Integer.parseInt(cancelParams.get("water_ad_Women").toString());
		int intWater_ch_Man2   = Integer.parseInt(cancelParams.get("water_ch_Man").toString());
		int intWater_ch_Women2 = Integer.parseInt(cancelParams.get("water_ch_Women").toString());
		int intComplex_ad_Man2   = Integer.parseInt(cancelParams.get("complex_ad_Man").toString());
		int intComplex_ad_Women2 = Integer.parseInt(cancelParams.get("complex_ad_Women").toString());
		int intComplex_ch_Man2  = Integer.parseInt(cancelParams.get("complex_ch_Man").toString());
		int intComplex_ch_Women2 = Integer.parseInt(cancelParams.get("complex_ch_Women").toString());						
		int intSumVisitCnt2    = Integer.parseInt(cancelParams.get("sumVisitCnt").toString());
		
		int intTotalCnt2 = intSpa_ad_Man2 + intSpa_ad_Women2 + intSpa_ch_Man2 + intSpa_ch_Women2 + intWater_ad_Man2 + intWater_ad_Women2 + intWater_ch_Man2 + intWater_ch_Women2
						  + intComplex_ad_Man2 + intComplex_ad_Women2 + intComplex_ch_Man2 + intComplex_ch_Women2;
		
		//if(intTotalCnt2 > 0 && intSumVisitCnt2 > intTotalCnt2){
			
			int intAdultSum = intSpa_ad_Man2 + intSpa_ad_Women2 + intWater_ad_Man2 + intWater_ad_Women2 + intComplex_ad_Man2 + intComplex_ad_Women2;
			int intChildSum = intSpa_ch_Man2 + intSpa_ch_Women2 + intWater_ch_Man2 + intWater_ch_Women2 + intComplex_ch_Man2 + intComplex_ch_Women2;
			//cancelParams.put("adult_sum", intAdultSum);
			//cancelParams.put("child_sum", intChildSum);								
			
			System.out.println("--------------------------------------------------------------------------------------------");
			System.out.println(getReserveInfo2.get("SPA_ITEM"));
			System.out.println(getReserveInfo2.get("WATER_ITEM"));		
			System.out.println(getReserveInfo2.get("COMPLEX_ITEM"));	
			System.out.println(intAdultSum + intChildSum);
			System.out.println("--------------------------------------------------------------------------------------------");
			
			if(getReserveInfo2.get("SPA_ITEM") != null) {							
				cancelParams.put("set_uid", getReserveInfo2.get("SPA_ITEM"));
				cancelParams.put("quantity", intAdultSum + intChildSum);
				
				frontReservationService.itemQtyUpdMinus(cancelParams);								
			}
			
			if(getReserveInfo2.get("WATER_ITEM") != null) {							
				cancelParams.put("set_uid", getReserveInfo2.get("WATER_ITEM"));
				cancelParams.put("quantity", intAdultSum + intChildSum);
				
				frontReservationService.itemQtyUpdMinus(cancelParams);								
			}
			
			if(getReserveInfo2.get("COMPLEX_ITEM") != null) {
				cancelParams.put("set_uid", getReserveInfo2.get("COMPLEX_ITEM"));
				cancelParams.put("quantity", intAdultSum + intChildSum);
				
				frontReservationService.itemQtyUpdMinus(cancelParams);	        				
			}	 							
			
			//String insResult = newReservationIns(cancelParams, getReserveInfo);
			//if("ERROR".equals(insResult)){
			//	html = "부분예약취소중 에러가 발생하였습니다.(NEW RESERVE DB INS)";
			//}
		//}
		/* 재고 돌리기 */			
		
		String html = "";
		if("PANALTY".equals(DecoderUtil.decode(param,"reserve_state"))){
			cancelParams.put("reserve_state", "CANCEL");
			html = cardPanaltyAction(cancelParams);
		}
		else{
			html = cardCancelAction(cancelParams, "", "");
		}
		
    	Util.htmlPrint(html, response);	
		
        return null;
    }
	
	//�떊�슜移대뱶 痍⑥냼 愿��젴 愿�由ъ옄
	@RequestMapping(value = "/reserve/card_cancel_admin.af")
    public String card_cancel_admin(@RequestParam Map param, Model model, HttpSession session, HttpServletResponse response, HttpServletRequest request) throws Exception{

	    response.setHeader("Set-Cookie", "Test1=TestCookieValue1; Secure; SameSite=None"); 
	    response.addHeader("Set-Cookie", "Test2=TestCookieValue2; Secure; SameSite=None"); 
	    response.addHeader("Set-Cookie", "Test3=TestCookieValue3; Secure; SameSite=None");			
		
		Map memberInfo = (Map) session.getAttribute("MEM_INFO");
		String realPath = session.getServletContext().getRealPath("/common/upload/email_templet");
		
		Map cancelParams = new HashMap(param);
		cancelParams.put("storeid", request.getParameter("storeid"));
		
		cancelParams.put("userName", DecoderUtil.decode(param,"userName")); //二쇰Ц�옄紐�
		cancelParams.put("email", DecoderUtil.decode(param,"email"));       //�씠硫붿씪
		cancelParams.put("phoneNo", request.getParameter("phoneNo"));       //�쑕���쟾�솕
		//cancelParams.put("userName", URLDecoder.decode(memberInfo.get("MEM_NM").toString(), "UTF-8"));
		//cancelParams.put("email", URLDecoder.decode(memberInfo.get("MEM_ID").toString(), "UTF-8"));
		//cancelParams.put("phoneNo", URLDecoder.decode(memberInfo.get("MOBILE_NUM").toString(), "UTF-8"));
		
		cancelParams.put("goodName", DecoderUtil.decode(param,"goodName"));
		cancelParams.put("authty", request.getParameter("authty"));
		cancelParams.put("trno", request.getParameter("trno"));
		cancelParams.put("canc_amt", request.getParameter("canc_amt"));
		cancelParams.put("canc_seq", request.getParameter("canc_seq"));
		cancelParams.put("canc_type", request.getParameter("canc_type"));
		cancelParams.put("uid", request.getParameter("uid"));
		cancelParams.put("ordernum", request.getParameter("ordernum"));
		cancelParams.put("reserveday", request.getParameter("reserveday"));
		cancelParams.put("mem_id", DecoderUtil.decode(param,"email"));
		cancelParams.put("custom_nm", DecoderUtil.decode(param,"userName"));
		cancelParams.put("ins_ip", request.getRemoteAddr());
		cancelParams.put("realPath", realPath);
		cancelParams.put("reserve_state", DecoderUtil.decode(param,"reserve_state"));
		cancelParams.put("penalty_amt", request.getParameter("penalty_amt"));
		cancelParams.put("pointCode", DecoderUtil.decode(param,"pointCode"));
		cancelParams.put("pointNm", DecoderUtil.decode(param,"pointNm"));
		
		String html = "";
		if("PANALTY".equals(DecoderUtil.decode(param,"reserve_state"))){
			cancelParams.put("reserve_state", "CANCEL");
			html = cardPanaltyAction(cancelParams);
		}
		else{
			html = cardCancelAction(cancelParams, "", "");
		}
		
    	Util.htmlPrint(html, response);	
		
        return null;
    }
	

	//�떎�떆媛� 怨꾩쥖�씠泥� 痍⑥냼 愿��젴
	@RequestMapping(value = "/reserve/cash_cancel.af")
    public String cash_cancel(@RequestParam Map param, Model model, HttpSession session, HttpServletResponse response, HttpServletRequest request) throws Exception{

	    response.setHeader("Set-Cookie", "Test1=TestCookieValue1; Secure; SameSite=None"); 
	    response.addHeader("Set-Cookie", "Test2=TestCookieValue2; Secure; SameSite=None"); 
	    response.addHeader("Set-Cookie", "Test3=TestCookieValue3; Secure; SameSite=None");			
		
		Map memberInfo = (Map) session.getAttribute("MEM_INFO");
		String realPath = session.getServletContext().getRealPath("/common/upload/email_templet");
		
		Map cancelParams = new HashMap(param);
		cancelParams.put("storeid", request.getParameter("storeid"));
		
		
		//cancelParams.put("userName", DecoderUtil.decode(param,"userName")); //二쇰Ц�옄紐�
		//cancelParams.put("email", DecoderUtil.decode(param,"email"));       //�씠硫붿씪
		//cancelParams.put("phoneNo", request.getParameter("phoneNo"));       //�쑕���쟾�솕
		cancelParams.put("userName", URLDecoder.decode(memberInfo.get("MEM_NM").toString(), "UTF-8"));
		cancelParams.put("email", URLDecoder.decode(memberInfo.get("MEM_ID").toString(), "UTF-8"));
		cancelParams.put("phoneNo", URLDecoder.decode(memberInfo.get("MOBILE_NUM").toString(), "UTF-8"));
		
		cancelParams.put("goodName", DecoderUtil.decode(param,"goodName"));
		cancelParams.put("authty", request.getParameter("authty"));
		cancelParams.put("trno", request.getParameter("trno"));
		cancelParams.put("canc_amt", request.getParameter("canc_amt"));
		cancelParams.put("canc_seq", request.getParameter("canc_seq"));
		cancelParams.put("canc_type", request.getParameter("canc_type"));		
		cancelParams.put("uid", request.getParameter("uid"));
		cancelParams.put("ordernum", request.getParameter("ordernum"));
		cancelParams.put("reserveday", request.getParameter("reserveday"));
		cancelParams.put("mem_id", DecoderUtil.decode(param,"email"));
		cancelParams.put("custom_nm", DecoderUtil.decode(param,"userName"));
		cancelParams.put("ins_ip", request.getRemoteAddr());
		cancelParams.put("realPath", realPath);
		cancelParams.put("reserve_state", DecoderUtil.decode(param,"reserve_state"));
		cancelParams.put("penalty_amt", request.getParameter("penalty_amt"));
		cancelParams.put("pointCode", DecoderUtil.decode(param,"pointCode"));
		cancelParams.put("pointNm", request.getParameter("pointNm"));
		
		// ------------------------------------------------------------------------
		// cash canel
		// ------------------------------------------------------------------------				
		
		/* 재고 돌리기 */
		int intUid2 = Integer.parseInt(cancelParams.get("uid").toString());
		Map getReserveInfo2 = frontReservationService.getReserveInfo(intUid2); //�삁�빟�젙蹂� 媛��졇�삤湲�	                	
    	
    	Map params_set_uid = new HashMap();
    	
		int intSpa_ad_Man2     = Integer.parseInt(cancelParams.get("spa_ad_Man").toString());
		int intSpa_ad_Women2   = Integer.parseInt(cancelParams.get("spa_ad_Women").toString());
		int intSpa_ch_Man2     = Integer.parseInt(cancelParams.get("spa_ch_Man").toString());
		int intSpa_ch_Women2   = Integer.parseInt(cancelParams.get("spa_ch_Women").toString());
		int intWater_ad_Man2   = Integer.parseInt(cancelParams.get("water_ad_Man").toString());
		int intWater_ad_Women2 = Integer.parseInt(cancelParams.get("water_ad_Women").toString());
		int intWater_ch_Man2   = Integer.parseInt(cancelParams.get("water_ch_Man").toString());
		int intWater_ch_Women2 = Integer.parseInt(cancelParams.get("water_ch_Women").toString());
		int intComplex_ad_Man2   = Integer.parseInt(cancelParams.get("complex_ad_Man").toString());
		int intComplex_ad_Women2 = Integer.parseInt(cancelParams.get("complex_ad_Women").toString());
		int intComplex_ch_Man2  = Integer.parseInt(cancelParams.get("complex_ch_Man").toString());
		int intComplex_ch_Women2 = Integer.parseInt(cancelParams.get("complex_ch_Women").toString());						
		int intSumVisitCnt2    = Integer.parseInt(cancelParams.get("sumVisitCnt").toString());
		
		int intTotalCnt2 = intSpa_ad_Man2 + intSpa_ad_Women2 + intSpa_ch_Man2 + intSpa_ch_Women2 + intWater_ad_Man2 + intWater_ad_Women2 + intWater_ch_Man2 + intWater_ch_Women2
						  + intComplex_ad_Man2 + intComplex_ad_Women2 + intComplex_ch_Man2 + intComplex_ch_Women2;
		
		//if(intTotalCnt2 > 0 && intSumVisitCnt2 > intTotalCnt2){
			
			int intAdultSum = intSpa_ad_Man2 + intSpa_ad_Women2 + intWater_ad_Man2 + intWater_ad_Women2 + intComplex_ad_Man2 + intComplex_ad_Women2;
			int intChildSum = intSpa_ch_Man2 + intSpa_ch_Women2 + intWater_ch_Man2 + intWater_ch_Women2 + intComplex_ch_Man2 + intComplex_ch_Women2;
			//cancelParams.put("adult_sum", intAdultSum);
			//cancelParams.put("child_sum", intChildSum);								
			
			System.out.println("--------------------------------------------------------------------------------------------");
			System.out.println(getReserveInfo2.get("SPA_ITEM"));
			System.out.println(getReserveInfo2.get("WATER_ITEM"));		
			System.out.println(getReserveInfo2.get("COMPLEX_ITEM"));	
			System.out.println(intAdultSum + intChildSum);
			System.out.println("--------------------------------------------------------------------------------------------");
			
			if(getReserveInfo2.get("SPA_ITEM") != null) {							
				cancelParams.put("set_uid", getReserveInfo2.get("SPA_ITEM"));
				cancelParams.put("quantity", intAdultSum + intChildSum);
				
				frontReservationService.itemQtyUpdMinus(cancelParams);								
			}
			
			if(getReserveInfo2.get("WATER_ITEM") != null) {							
				cancelParams.put("set_uid", getReserveInfo2.get("WATER_ITEM"));
				cancelParams.put("quantity", intAdultSum + intChildSum);
				
				frontReservationService.itemQtyUpdMinus(cancelParams);								
			}
			
			if(getReserveInfo2.get("COMPLEX_ITEM") != null) {
				cancelParams.put("set_uid", getReserveInfo2.get("COMPLEX_ITEM"));
				cancelParams.put("quantity", intAdultSum + intChildSum);
				
				frontReservationService.itemQtyUpdMinus(cancelParams);	        				
			}	 							
			
			//String insResult = newReservationIns(cancelParams, getReserveInfo);
			//if("ERROR".equals(insResult)){
			//	html = "부분예약취소중 에러가 발생하였습니다.(NEW RESERVE DB INS)";
			//}
		//}
		/* 재고 돌리기 */				
		
		String html = "";
		if("PANALTY".equals(DecoderUtil.decode(param,"reserve_state"))){
			cancelParams.put("reserve_state", "CANCEL");
			html = cashPanaltyAction(cancelParams);
		}
		else{
			html = cashCancelAction(cancelParams,"","");
		}
		
    	Util.htmlPrint(html, response);	
		
        return null;
    }
	
	//�떎�떆媛� 怨꾩쥖�씠泥� 痍⑥냼 愿��젴
		@RequestMapping(value = "/reserve/cash_cancel_admin.af")
	    public String cash_cancel_admin(@RequestParam Map param, Model model, HttpSession session, HttpServletResponse response, HttpServletRequest request) throws Exception{

		    response.setHeader("Set-Cookie", "Test1=TestCookieValue1; Secure; SameSite=None"); 
		    response.addHeader("Set-Cookie", "Test2=TestCookieValue2; Secure; SameSite=None"); 
		    response.addHeader("Set-Cookie", "Test3=TestCookieValue3; Secure; SameSite=None");				
			
			Map memberInfo = (Map) session.getAttribute("MEM_INFO");
			String realPath = session.getServletContext().getRealPath("/common/upload/email_templet");
			
			Map cancelParams = new HashMap(param);
			cancelParams.put("storeid", request.getParameter("storeid"));
			
			//cancelParams.put("userName", URLDecoder.decode(memberInfo.get("MEM_NM").toString(), "UTF-8"));
			//cancelParams.put("email", URLDecoder.decode(memberInfo.get("MEM_ID").toString(), "UTF-8"));
			//cancelParams.put("phoneNo", URLDecoder.decode(memberInfo.get("MOBILE_NUM").toString(), "UTF-8"));
			
			cancelParams.put("userName", DecoderUtil.decode(param,"userName")); //二쇰Ц�옄紐�
			cancelParams.put("email", DecoderUtil.decode(param,"email"));       //�씠硫붿씪
			cancelParams.put("phoneNo", request.getParameter("phoneNo"));       //�쑕���쟾�솕
			cancelParams.put("goodName", DecoderUtil.decode(param,"goodName"));
			cancelParams.put("authty", request.getParameter("authty"));
			cancelParams.put("trno", request.getParameter("trno"));
			cancelParams.put("canc_amt", request.getParameter("canc_amt"));
			cancelParams.put("canc_seq", request.getParameter("canc_seq"));
			cancelParams.put("canc_type", request.getParameter("canc_type"));		
			cancelParams.put("uid", request.getParameter("uid"));
			cancelParams.put("ordernum", request.getParameter("ordernum"));
			cancelParams.put("reserveday", request.getParameter("reserveday"));
			cancelParams.put("mem_id", DecoderUtil.decode(param,"email"));
			cancelParams.put("custom_nm", DecoderUtil.decode(param,"userName"));
			cancelParams.put("ins_ip", request.getRemoteAddr());
			cancelParams.put("realPath", realPath);
			cancelParams.put("reserve_state", DecoderUtil.decode(param,"reserve_state"));
			cancelParams.put("penalty_amt", request.getParameter("penalty_amt"));
			cancelParams.put("pointCode", DecoderUtil.decode(param,"pointCode"));
			cancelParams.put("pointNm", request.getParameter("pointNm"));
			
			String html = "";
			if("PANALTY".equals(DecoderUtil.decode(param,"reserve_state"))){
				cancelParams.put("reserve_state", "CANCEL");
				html = cashPanaltyAction(cancelParams);
			}
			else{
				html = cashCancelAction(cancelParams,"","");
			}
			
	    	Util.htmlPrint(html, response);	
			
	        return null;
	    }
	
	//SSGPAY - SSG MONEY 痍⑥냼 愿��젴
	@RequestMapping(value = "/reserve/ssg_cancel.af")
    public String ssg_cancel(@RequestParam Map param, Model model, HttpSession session, HttpServletResponse response, HttpServletRequest request) throws Exception{

	    response.setHeader("Set-Cookie", "Test1=TestCookieValue1; Secure; SameSite=None"); 
	    response.addHeader("Set-Cookie", "Test2=TestCookieValue2; Secure; SameSite=None"); 
	    response.addHeader("Set-Cookie", "Test3=TestCookieValue3; Secure; SameSite=None");			
		
		Map memberInfo = (Map) session.getAttribute("MEM_INFO");
		String realPath = session.getServletContext().getRealPath("/common/upload/email_templet");
		
		Map cancelParams = new HashMap(param);
		cancelParams.put("storeid", request.getParameter("storeid"));
		//cancelParams.put("userName", DecoderUtil.decode(param,"userName")); //二쇰Ц�옄紐�
		//cancelParams.put("email", DecoderUtil.decode(param,"email"));       //�씠硫붿씪
		//cancelParams.put("phoneNo", request.getParameter("phoneNo"));       //�쑕���쟾�솕
		cancelParams.put("userName", URLDecoder.decode(memberInfo.get("MEM_NM").toString(), "UTF-8"));
		cancelParams.put("email", URLDecoder.decode(memberInfo.get("MEM_ID").toString(), "UTF-8"));
		cancelParams.put("phoneNo", URLDecoder.decode(memberInfo.get("MOBILE_NUM").toString(), "UTF-8"));
		cancelParams.put("goodName", DecoderUtil.decode(param,"goodName"));
		cancelParams.put("authty", request.getParameter("authty"));
		cancelParams.put("trno", request.getParameter("trno"));
		cancelParams.put("canc_amt", request.getParameter("canc_amt"));
		cancelParams.put("canc_seq", request.getParameter("canc_seq"));
		cancelParams.put("canc_type", request.getParameter("canc_type"));		
		cancelParams.put("uid", request.getParameter("uid"));
		cancelParams.put("ordernum", request.getParameter("ordernum"));
		cancelParams.put("reserveday", request.getParameter("reserveday"));
		cancelParams.put("mem_id", DecoderUtil.decode(param,"email"));
		cancelParams.put("custom_nm", DecoderUtil.decode(param,"userName"));
		cancelParams.put("ins_ip", request.getRemoteAddr());
		cancelParams.put("realPath", realPath);
		cancelParams.put("reserve_state", DecoderUtil.decode(param,"reserve_state"));
		cancelParams.put("penalty_amt", request.getParameter("penalty_amt"));
		cancelParams.put("pointCode", DecoderUtil.decode(param,"pointCode"));
		cancelParams.put("pointNm", DecoderUtil.decode(param,"pointNm"));

		// ------------------------------------------------------------------------
		// ssgcanel
		// ------------------------------------------------------------------------				
		
		/* 재고 돌리기 */
		int intUid2 = Integer.parseInt(cancelParams.get("uid").toString());
		Map getReserveInfo2 = frontReservationService.getReserveInfo(intUid2); //�삁�빟�젙蹂� 媛��졇�삤湲�	                	
    	
    	Map params_set_uid = new HashMap();
    	
		int intSpa_ad_Man2     = Integer.parseInt(cancelParams.get("spa_ad_Man").toString());
		int intSpa_ad_Women2   = Integer.parseInt(cancelParams.get("spa_ad_Women").toString());
		int intSpa_ch_Man2     = Integer.parseInt(cancelParams.get("spa_ch_Man").toString());
		int intSpa_ch_Women2   = Integer.parseInt(cancelParams.get("spa_ch_Women").toString());
		int intWater_ad_Man2   = Integer.parseInt(cancelParams.get("water_ad_Man").toString());
		int intWater_ad_Women2 = Integer.parseInt(cancelParams.get("water_ad_Women").toString());
		int intWater_ch_Man2   = Integer.parseInt(cancelParams.get("water_ch_Man").toString());
		int intWater_ch_Women2 = Integer.parseInt(cancelParams.get("water_ch_Women").toString());
		int intComplex_ad_Man2   = Integer.parseInt(cancelParams.get("complex_ad_Man").toString());
		int intComplex_ad_Women2 = Integer.parseInt(cancelParams.get("complex_ad_Women").toString());
		int intComplex_ch_Man2  = Integer.parseInt(cancelParams.get("complex_ch_Man").toString());
		int intComplex_ch_Women2 = Integer.parseInt(cancelParams.get("complex_ch_Women").toString());						
		int intSumVisitCnt2    = Integer.parseInt(cancelParams.get("sumVisitCnt").toString());
		
		int intTotalCnt2 = intSpa_ad_Man2 + intSpa_ad_Women2 + intSpa_ch_Man2 + intSpa_ch_Women2 + intWater_ad_Man2 + intWater_ad_Women2 + intWater_ch_Man2 + intWater_ch_Women2
						  + intComplex_ad_Man2 + intComplex_ad_Women2 + intComplex_ch_Man2 + intComplex_ch_Women2;
		
		//if(intTotalCnt2 > 0 && intSumVisitCnt2 > intTotalCnt2){
			
			int intAdultSum = intSpa_ad_Man2 + intSpa_ad_Women2 + intWater_ad_Man2 + intWater_ad_Women2 + intComplex_ad_Man2 + intComplex_ad_Women2;
			int intChildSum = intSpa_ch_Man2 + intSpa_ch_Women2 + intWater_ch_Man2 + intWater_ch_Women2 + intComplex_ch_Man2 + intComplex_ch_Women2;
			//cancelParams.put("adult_sum", intAdultSum);
			//cancelParams.put("child_sum", intChildSum);								
			
			System.out.println("--------------------------------------------------------------------------------------------");
			System.out.println(getReserveInfo2.get("SPA_ITEM"));
			System.out.println(getReserveInfo2.get("WATER_ITEM"));		
			System.out.println(getReserveInfo2.get("COMPLEX_ITEM"));	
			System.out.println(intAdultSum + intChildSum);
			System.out.println("--------------------------------------------------------------------------------------------");
			
			if(getReserveInfo2.get("SPA_ITEM") != null) {							
				cancelParams.put("set_uid", getReserveInfo2.get("SPA_ITEM"));
				cancelParams.put("quantity", intAdultSum + intChildSum);
				
				frontReservationService.itemQtyUpdMinus(cancelParams);								
			}
			
			if(getReserveInfo2.get("WATER_ITEM") != null) {							
				cancelParams.put("set_uid", getReserveInfo2.get("WATER_ITEM"));
				cancelParams.put("quantity", intAdultSum + intChildSum);
				
				frontReservationService.itemQtyUpdMinus(cancelParams);								
			}
			
			if(getReserveInfo2.get("COMPLEX_ITEM") != null) {
				cancelParams.put("set_uid", getReserveInfo2.get("COMPLEX_ITEM"));
				cancelParams.put("quantity", intAdultSum + intChildSum);
				
				frontReservationService.itemQtyUpdMinus(cancelParams);	        				
			}	 							
			
			//String insResult = newReservationIns(cancelParams, getReserveInfo);
			//if("ERROR".equals(insResult)){
			//	html = "부분예약취소중 에러가 발생하였습니다.(NEW RESERVE DB INS)";
			//}
		//}
		/* 재고 돌리기 */				
		
		
		String html = "";
		if("PANALTY".equals(DecoderUtil.decode(param,"reserve_state"))){
			cancelParams.put("reserve_state", "CANCEL");
			html = ssgPanaltyAction(cancelParams);
		}
		else{
			html = ssgCancelAction(cancelParams,"","");
		}
		
    	Util.htmlPrint(html, response);	
		
        return null;
    }
	
	//SSGPAY - SSG MONEY 痍⑥냼 愿��젴
		@RequestMapping(value = "/reserve/ssg_cancel_admin.af")
	    public String ssg_cancel_admin(@RequestParam Map param, Model model, HttpSession session, HttpServletResponse response, HttpServletRequest request) throws Exception{

		    response.setHeader("Set-Cookie", "Test1=TestCookieValue1; Secure; SameSite=None"); 
		    response.addHeader("Set-Cookie", "Test2=TestCookieValue2; Secure; SameSite=None"); 
		    response.addHeader("Set-Cookie", "Test3=TestCookieValue3; Secure; SameSite=None");				
			
			Map memberInfo = (Map) session.getAttribute("MEM_INFO");
			String realPath = session.getServletContext().getRealPath("/common/upload/email_templet");
			
			Map cancelParams = new HashMap(param);
			cancelParams.put("storeid", request.getParameter("storeid"));
			cancelParams.put("userName", DecoderUtil.decode(param,"userName")); //二쇰Ц�옄紐�
			cancelParams.put("email", DecoderUtil.decode(param,"email"));       //�씠硫붿씪
			cancelParams.put("phoneNo", request.getParameter("phoneNo"));       //�쑕���쟾�솕
			//cancelParams.put("userName", URLDecoder.decode(memberInfo.get("MEM_NM").toString(), "UTF-8"));
			//cancelParams.put("email", URLDecoder.decode(memberInfo.get("MEM_ID").toString(), "UTF-8"));
			//cancelParams.put("phoneNo", URLDecoder.decode(memberInfo.get("MOBILE_NUM").toString(), "UTF-8"));
			cancelParams.put("goodName", DecoderUtil.decode(param,"goodName"));
			cancelParams.put("authty", request.getParameter("authty"));
			cancelParams.put("trno", request.getParameter("trno"));
			cancelParams.put("canc_amt", request.getParameter("canc_amt"));
			cancelParams.put("canc_seq", request.getParameter("canc_seq"));
			cancelParams.put("canc_type", request.getParameter("canc_type"));		
			cancelParams.put("uid", request.getParameter("uid"));
			cancelParams.put("ordernum", request.getParameter("ordernum"));
			cancelParams.put("reserveday", request.getParameter("reserveday"));
			cancelParams.put("mem_id", DecoderUtil.decode(param,"email"));
			cancelParams.put("custom_nm", DecoderUtil.decode(param,"userName"));
			cancelParams.put("ins_ip", request.getRemoteAddr());
			cancelParams.put("realPath", realPath);
			cancelParams.put("reserve_state", DecoderUtil.decode(param,"reserve_state"));
			cancelParams.put("penalty_amt", request.getParameter("penalty_amt"));
			cancelParams.put("pointCode", DecoderUtil.decode(param,"pointCode"));
			cancelParams.put("pointNm", DecoderUtil.decode(param,"pointNm"));
			String html = "";
			if("PANALTY".equals(DecoderUtil.decode(param,"reserve_state"))){
				cancelParams.put("reserve_state", "CANCEL");
				html = ssgPanaltyAction(cancelParams);
			}
			else{
				html = ssgCancelAction(cancelParams,"","");
			}
			
	    	Util.htmlPrint(html, response);	
			
	        return null;
	    }
		
	//�떊�슜移대뱶 痍⑥냼 愿��젴
	public String cardCancelAction(Map cancelParams, String isOnPayment, String exceptionMessage){
		
		String html = "예약취소가 정상적으로 되었습니다.";
		//Map memberInfo = (Map) session.getAttribute("MEM_INFO");
		// Default(�닔�젙�빆紐⑹씠 �븘�떂)-------------------------------------------------------
		String	EncType       = "2";					// 0: �븫�솕�븞�븿, 1:openssl, 2: seed
		String	Version       = "0603";				    // �쟾臾몃쾭�쟾
		String	Type          = "00";					// 援щ텇
		String	Resend        = "0";					// �쟾�넚援щ텇 : 0 : 泥섏쓬,  2: �옱�쟾�넚
		String	RequestDate   = new SimpleDateFormat("yyyyMMddhhmmss").format(new java.util.Date()); // �슂泥��씪�옄 : yyyymmddhhmmss
		String	KeyInType     = "K";					// KeyInType �뿬遺� : S : Swap, K: KeyInType
		String	LineType      = "1";			        // lineType 0 : offline, 1:internet, 2:Mobile
		String	ApprovalCount = "1";				    // 蹂듯빀�듅�씤媛��닔
		String 	GoodType      = "0";	                // �젣�뭹援щ텇 0 : �떎臾�, 1 : �뵒吏��꽭
		String	HeadFiller    = "";				        // �삁鍮�
	//-------------------------------------------------------------------------------

	// Header (�엯�젰媛� (*) �븘�닔�빆紐�)--------------------------------------------------
		String	StoreId		= cancelParams.get("storeid").toString();		// *�긽�젏�븘�씠�뵒
		String	OrderNumber	= "";									// 二쇰Ц踰덊샇
		String	UserName    = cancelParams.get("userName").toString();		// *二쇰Ц�옄紐�
		String	IdNum       = "";									// 二쇰�쇰쾲�샇 or �궗�뾽�옄踰덊샇
		String	Email       = cancelParams.get("email").toString();		// *email
		String 	GoodName    = cancelParams.get("goodName").toString();		// *�젣�뭹紐�
		String	PhoneNo     = cancelParams.get("phoneNo").toString();  	// *�쑕���룿踰덊샇                                              
	// Header end -------------------------------------------------------------------

	// Data Default(�닔�젙�빆紐⑹씠 �븘�떂)-------------------------------------------------
		String ApprovalType	  = cancelParams.get("authty").toString();		// �듅�씤援щ텇
		String TrNo   		  = cancelParams.get("trno").toString();		// 嫄곕옒踰덊샇
		String Canc_amt		  = cancelParams.get("canc_amt").toString();	// 痍⑥냼湲덉븸
		String Canc_seq		  = cancelParams.get("canc_seq").toString();	// 痍⑥냼�씪�젴踰덊샇
		String Canc_type	  = cancelParams.get("canc_type").toString();	// 痍⑥냼�쑀�삎 0 :嫄곕옒踰덊샇痍⑥냼 1: 二쇰Ц踰덊샇痍⑥냼 3:遺�遺꾩랬�냼
	// Data Default end -------------------------------------------------------------

	// Server濡� 遺��꽣 �쓳�떟�씠 �뾾�쓣�떆 �옄泥댁쓳�떟
		String rApprovalType	   = "1011"; 
		String rTransactionNo      = "";			// 嫄곕옒踰덊샇
		String rStatus             = "X";			// �긽�깭 O : �듅�씤, X : 嫄곗젅
		String rTradeDate          = ""; 			// 嫄곕옒�씪�옄
		String rTradeTime          = ""; 			// 嫄곕옒�떆媛�
		String rIssCode            = "00"; 			// 諛쒓툒�궗肄붾뱶
		String rAquCode			   = "00"; 			// 留ㅼ엯�궗肄붾뱶
		String rAuthNo             = "9999"; 		// �듅�씤踰덊샇 or 嫄곗젅�떆 �삤瑜섏퐫�뱶
		String rMessage1           = "痍⑥냼嫄곗젅"; 	// 硫붿떆吏�1
		String rMessage2           = "C�옞�떆�썑�옱�떆�룄";// 硫붿떆吏�2
		String rCardNo             = ""; 			// 移대뱶踰덊샇
		String rExpDate            = ""; 			// �쑀�슚湲곌컙
		String rInstallment        = ""; 			// �븷遺�
		String rAmount             = ""; 			// 湲덉븸
		String rMerchantNo         = ""; 			// 媛�留뱀젏踰덊샇
		String rAuthSendType       = "N"; 			// �쟾�넚援щ텇
		String rApprovalSendType   = "N"; 			// �쟾�넚援щ텇(0 : 嫄곗젅, 1 : �듅�씤, 2: �썝移대뱶)
		String rPoint1             = "000000000000";// Point1
		String rPoint2             = "000000000000";// Point2
		String rPoint3             = "000000000000";// Point3
		String rPoint4             = "000000000000";// Point4
		String rVanTransactionNo   = "";
		String rFiller             = ""; 			// �삁鍮�
		String rAuthType	 	   = ""; 			// ISP : ISP嫄곕옒, MP1, MP2 : MPI嫄곕옒, SPACE : �씪諛섍굅�옒
		String rMPIPositionType	   = ""; 			// K : KSNET, R : Remote, C : �젣3湲곌�, SPACE : �씪諛섍굅�옒
		String rMPIReUseType	   = ""; 			// Y : �옱�궗�슜, N : �옱�궗�슜�븘�떂
		String rEncData			   = ""; 			// MPI, ISP �뜲�씠�꽣

		/* 재고 돌리기 */
		int intUid2 = Integer.parseInt(cancelParams.get("uid").toString());
		Map getReserveInfo2 = frontReservationService.getReserveInfo(intUid2); //�삁�빟�젙蹂� 媛��졇�삤湲�	                	
    	
    	Map params_set_uid = new HashMap();
    	
		int intSpa_ad_Man2     = Integer.parseInt(cancelParams.get("spa_ad_Man").toString());
		int intSpa_ad_Women2   = Integer.parseInt(cancelParams.get("spa_ad_Women").toString());
		int intSpa_ch_Man2     = Integer.parseInt(cancelParams.get("spa_ch_Man").toString());
		int intSpa_ch_Women2   = Integer.parseInt(cancelParams.get("spa_ch_Women").toString());
		int intWater_ad_Man2   = Integer.parseInt(cancelParams.get("water_ad_Man").toString());
		int intWater_ad_Women2 = Integer.parseInt(cancelParams.get("water_ad_Women").toString());
		int intWater_ch_Man2   = Integer.parseInt(cancelParams.get("water_ch_Man").toString());
		int intWater_ch_Women2 = Integer.parseInt(cancelParams.get("water_ch_Women").toString());
		int intComplex_ad_Man2   = Integer.parseInt(cancelParams.get("complex_ad_Man").toString());
		int intComplex_ad_Women2 = Integer.parseInt(cancelParams.get("complex_ad_Women").toString());
		int intComplex_ch_Man2  = Integer.parseInt(cancelParams.get("complex_ch_Man").toString());
		int intComplex_ch_Women2 = Integer.parseInt(cancelParams.get("complex_ch_Women").toString());						
		int intSumVisitCnt2    = Integer.parseInt(cancelParams.get("sumVisitCnt").toString());
		
		int intTotalCnt2 = intSpa_ad_Man2 + intSpa_ad_Women2 + intSpa_ch_Man2 + intSpa_ch_Women2 + intWater_ad_Man2 + intWater_ad_Women2 + intWater_ch_Man2 + intWater_ch_Women2
						  + intComplex_ad_Man2 + intComplex_ad_Women2 + intComplex_ch_Man2 + intComplex_ch_Women2;
		
		if(intTotalCnt2 > 0 && intSumVisitCnt2 > intTotalCnt2){
			
			int intAdultSum = intSpa_ad_Man2 + intSpa_ad_Women2 + intWater_ad_Man2 + intWater_ad_Women2 + intComplex_ad_Man2 + intComplex_ad_Women2;
			int intChildSum = intSpa_ch_Man2 + intSpa_ch_Women2 + intWater_ch_Man2 + intWater_ch_Women2 + intComplex_ch_Man2 + intComplex_ch_Women2;
			//cancelParams.put("adult_sum", intAdultSum);
			//cancelParams.put("child_sum", intChildSum);								
			
			System.out.println("--------------------------------------------------------------------------------------------");
			System.out.println(getReserveInfo2.get("SPA_ITEM"));
			System.out.println(getReserveInfo2.get("WATER_ITEM"));		
			System.out.println(getReserveInfo2.get("COMPLEX_ITEM"));	
			System.out.println("--------------------------------------------------------------------------------------------");
			
			if(getReserveInfo2.get("SPA_ITEM") != null) {							
				cancelParams.put("set_uid", getReserveInfo2.get("SPA_ITEM"));
				cancelParams.put("quantity", intAdultSum + intChildSum);
				
				frontReservationService.itemQtyUpdMinus(cancelParams);								
			}
			
			if(getReserveInfo2.get("WATER_ITEM") != null) {							
				cancelParams.put("set_uid", getReserveInfo2.get("WATER_ITEM"));
				cancelParams.put("quantity", intAdultSum + intChildSum);
				
				frontReservationService.itemQtyUpdMinus(cancelParams);								
			}
			
			if(getReserveInfo2.get("COMPLEX_ITEM") != null) {
				cancelParams.put("set_uid", getReserveInfo2.get("COMPLEX_ITEM"));
				cancelParams.put("quantity", intAdultSum + intChildSum);
				
				frontReservationService.itemQtyUpdMinus(cancelParams);	        				
			}	 							
			
			//String insResult = newReservationIns(cancelParams, getReserveInfo);
			//if("ERROR".equals(insResult)){
			//	html = "부분예약취소중 에러가 발생하였습니다.(NEW RESERVE DB INS)";
			//}
		}
		/* 재고 돌리기 */		
		
		try 
		{
			KSPayApprovalCancelBean ipg = new KSPayApprovalCancelBean(config.getProperty("pg.ip"), 29991);

			ipg.HeadMessage(EncType, Version, Type, Resend, RequestDate, StoreId, OrderNumber, UserName, IdNum, Email, 
							GoodType, GoodName, KeyInType, LineType, PhoneNo, ApprovalCount, HeadFiller);

			if (Canc_type.equals("3")){
				ipg.CancelDataMessage(ApprovalType, Canc_type, TrNo, "", "", ipg.format(Canc_amt,9,'9')+ipg.format(Canc_seq,2,'9'),"","");
			}else{ 
				ipg.CancelDataMessage(ApprovalType, "0", TrNo, "", "", "","","");
			}

			if(ipg.SendSocket("1")) {
				rApprovalType		= ipg.ApprovalType[0];	    
				rTransactionNo		= ipg.TransactionNo[0];	  		// 嫄곕옒踰덊샇
				rStatus				= ipg.Status[0];		  		// �긽�깭 O : �듅�씤, X : 嫄곗젅
				rTradeDate			= ipg.TradeDate[0];		  		// 嫄곕옒�씪�옄
				rTradeTime			= ipg.TradeTime[0];		  		// 嫄곕옒�떆媛�
				rIssCode			= ipg.IssCode[0];		  		// 諛쒓툒�궗肄붾뱶
				rAquCode			= ipg.AquCode[0];		  		// 留ㅼ엯�궗肄붾뱶
				rAuthNo				= ipg.AuthNo[0];		  		// �듅�씤踰덊샇 or 嫄곗젅�떆 �삤瑜섏퐫�뱶
				rMessage1			= ipg.Message1[0];		  		// 硫붿떆吏�1
				rMessage2			= ipg.Message2[0];		  		// 硫붿떆吏�2
				rCardNo				= ipg.CardNo[0];		  		// 移대뱶踰덊샇
				rExpDate			= ipg.ExpDate[0];		  		// �쑀�슚湲곌컙
				rInstallment		= ipg.Installment[0];	  		// �븷遺�
				rAmount				= ipg.Amount[0];		  		// 湲덉븸
				rMerchantNo			= ipg.MerchantNo[0];	  		// 媛�留뱀젏踰덊샇
				rAuthSendType		= ipg.AuthSendType[0];	  		// �쟾�넚援щ텇= new String(this.read(2));
				rApprovalSendType	= ipg.ApprovalSendType[0];		// �쟾�넚援щ텇(0 : 嫄곗젅, 1 : �듅�씤, 2: �썝移대뱶)
				rPoint1				= ipg.Point1[0];		  		// Point1
				rPoint2				= ipg.Point2[0];		  		// Point2
				rPoint3				= ipg.Point3[0];		  		// Point3
				rPoint4				= ipg.Point4[0];		  		// Point4
				rVanTransactionNo   = ipg.VanTransactionNo[0];      // Van嫄곕옒踰덊샇
				rFiller				= ipg.Filler[0];		  		// �삁鍮�
				rAuthType			= ipg.AuthType[0];		  		// ISP : ISP嫄곕옒, MP1, MP2 : MPI嫄곕옒, SPACE : �씪諛섍굅�옒
				rMPIPositionType	= ipg.MPIPositionType[0]; 		// K : KSNET, R : Remote, C : �젣3湲곌�, SPACE : �씪諛섍굅�옒
				rMPIReUseType		= ipg.MPIReUseType[0];			// Y : �옱�궗�슜, N : �옱�궗�슜�븘�떂
				rEncData			= ipg.EncData[0];		  		// MPI, ISP �뜲�씠�꽣
				
				if("O".equals(rStatus)){
				
					Map parameter = new HashMap();	
	                parameter.put("approval_type",rApprovalType);
	                parameter.put("ac_transactionno",rTransactionNo);
	                parameter.put("ac_status",rStatus);
	                parameter.put("ac_tradedate",rTradeDate);
	                parameter.put("ac_tradetime",rTradeTime);
	                parameter.put("ac_amount",rAmount);
	                parameter.put("ac_message1",rMessage1);
	                if(isOnPayment.trim().equals("Y")){
	                	parameter.put("ac_message2", exceptionMessage);	                	
	                }else{
	                	parameter.put("ac_message2",rMessage2);
	                }
	                parameter.put("reserve_uid",cancelParams.get("uid"));
	                parameter.put("reserve_state",cancelParams.get("reserve_state"));
	                parameter.put("penalty_amt",cancelParams.get("penalty_amt"));
	                
	                String result = frontReservationService.pgCancelIns(parameter);
	                
	                if("PGCANCELOK".equals(result)){
	                	
	                	
	                	
	                	//寃곗젣 以� 痍⑥냼 �씪 寃쎌슦 SMS/E-MAIL 諛쒖넚�븞�븿
	                	if(!isOnPayment.trim().equals("Y")){
	                		// 임시
	                		html = "결제가 취소되었습니다.";	
		                	
	                		//�삁�빟痍⑥냼臾몄옄 諛쒖넚
		        			//String contents = "[�븘荑좎븘�븘�뱶]�삩�씪�씤 �삁�빟痍⑥냼(�삁�빟踰덊샇:"+request.getParameter("ordernum")+",�삁�빟�씪:"+request.getParameter("reserveday")+")";
							Map smsParam = new HashMap();
							smsParam.put("point_code", "POINT01");
							smsParam.put("sms_type", "CANCEL");
							
							Map smsTemplte = commonService.getSmsTemplete(smsParam);  //SMS �뀥�뵆由� 媛��졇�삤湲�
							
							String contents = smsTemplte.get("SMS_CONTENT").toString();
							
		        			int intUid2_2 = Integer.parseInt(cancelParams.get("uid").toString());
		        			Map getReserveInfo2_2 = frontReservationService.getReserveInfo(intUid2_2); //�삁�빟�젙蹂� 媛��졇�삤湲�								

		        			String pointnm = "하남";
		        			if(getReserveInfo2_2.get("POINT_CODE").equals("POINT01")) {
			        			pointnm = "하남";		        				
		        			}
		        			
		        			if(getReserveInfo2_2.get("POINT_CODE").equals("POINT03")) {
			        			pointnm = "고양";		        				
		        			}		        			
		        			
		        			if(getReserveInfo2_2.get("POINT_CODE").equals("POINT05") || getReserveInfo2_2.get("POINT_CODE").equals("POINT07")) {
			        			pointnm = "안성";		        				
		        			}	
		        			
		        			//strPoint = getReserveInfo2_2.get("POINT_CODE").toString();		        			
		        			
							contents = contents.replace("{지점}",pointnm);//�삁�빟踰덊샇 移섑솚
							contents = contents.replace("{번호}",getReserveInfo2_2.get("ORDER_NUM").toString());//�삁�빟踰덊샇 移섑솚
							contents = contents.replace("{예약일}",getReserveInfo2_2.get("RESERVE_DATE").toString());//�삁�빟�씪 移섑솚	                	
		        			
		        			/*
							contents = contents.replace("{지점}",cancelParams.get("pointNm").toString());//�삁�빟踰덊샇 移섑솚
							contents = contents.replace("{번호}",cancelParams.get("ordernum").toString());//�삁�빟踰덊샇 移섑솚
							contents = contents.replace("{예약일}",cancelParams.get("reserveday").toString());//�삁�빟�씪 移섑솚	                	
		                	*/
		        			Map params = new HashMap();
		        			
		        			
		        			
		        			params.put("recipient_num", getReserveInfo2_2.get("MEM_MOBILE_NUM")); // �닔�떊踰덊샇		        			
		        			//params.put("recipient_num", cancelParams.get("phoneNo")); // �닔�떊踰덊샇
		        			//param.put("subject", "[�븘荑좎븘�븘�뱶]�삁�빟痍⑥냼臾몄옄�엯�땲�떎."); // LMS�씪寃쎌슦 �젣紐⑹쓣 異붽� �븷 �닔 �엳�떎.
		        			params.put("content", contents); // �궡�슜 (SMS=88Byte, LMS=2000Byte)		
		        			//params.put("callback", "031-8072-8800"); // 諛쒖떊踰덊샇
		        			params.put("callback", config.getProperty("sms.tel.number."+cancelParams.get("pointCode").toString())); // 諛쒖떊踰덊샇		        			
		        			//params.put("callback", config.getProperty("sms.tel.number."+cancelParams.get("pointCode").toString())); // 諛쒖떊踰덊샇
		        			
		        			if(!smsService.sendSms(params)){
		        				html = "문자발송에 실패하였습니다.";
		        			}else{
								//SMS 諛쒖넚 �씠�젰 �벑濡�
								smsParam.put("sms_uid", smsTemplte.get("SMS_UID"));
								smsParam.put("mem_id", cancelParams.get("mem_id"));
								smsParam.put("custom_nm", cancelParams.get("custom_nm"));
								smsParam.put("custom_mobile", cancelParams.get("phoneNo"));
								smsParam.put("ins_ip", cancelParams.get("ins_ip"));
								smsParam.put("ins_id", cancelParams.get("mem_id")); 
								smsParam.put("send_status", "OK");	
								smsParam.put("bigo", "CARD_RESERVE_CANCEL");			
								
								String smsResult = commonService.insSmsSend(smsParam);
								if("ERROR".equals(smsResult)){
									html = "문자발송에 실패하였습니다.";
								}
		        			}
		        			
							//硫붿씪諛쒖넚 #####################################
		        			int intUid = Integer.parseInt(cancelParams.get("uid").toString());
		        			Map getReserveInfo = frontReservationService.getReserveInfo(intUid); //�삁�빟�젙蹂� 媛��졇�삤湲�
		        			
							Map emailParam = new HashMap();
							emailParam.put("email_uid", "3");
							Map joinEmail = adminEmailTempletService.adminEmailTempletDetail(emailParam);
							
							String realPath = cancelParams.get("realPath").toString();
							//String joinHtml = super.getHTMfile(realPath+"/reservation_complete.html");
							
							String joinHtml = super.getHTMfile(realPath+"/3");						
							//String joinHtml = super.getHTMfile(realPath+"/reservation_cancel.html");
							
							String nowTime = AquaDateUtil.getToday();
							nowTime = nowTime.substring(0, 4)+"."+nowTime.substring(4, 6)+"."+nowTime.substring(6, 8);						
							joinHtml = joinHtml.replace("{{#NOW#}}",nowTime); // �쁽�옱�떆媛� 移섑솚
							joinHtml = joinHtml.replace("{{#NAME#}}",getReserveInfo.get("MEM_NM").toString());//�씠由� 移섑솚
							joinHtml = joinHtml.replace("{{#RESERVENUM#}}",getReserveInfo.get("ORDER_NUM").toString());//�삁�빟踰덊샇 移섑솚
							joinHtml = joinHtml.replace("{{#POINTNM#}}",getReserveInfo.get("POINT_NM").toString());//�삁�빟踰덊샇 移섑솚
							joinHtml = joinHtml.replace("{{#RESERVEDAY#}}",getReserveInfo.get("RESERVE_DATE").toString());//�삁�빟�씪移섑솚
							joinHtml = joinHtml.replace("{{#GOODS#}}",getReserveInfo.get("ORDER_NM").toString());//�긽�뭹紐낆튂�솚
							
							int cnt = Integer.parseInt(getReserveInfo.get("ADULT_SUM").toString()) + Integer.parseInt(getReserveInfo.get("CHILD_SUM").toString());
							joinHtml = joinHtml.replace("{{#CNT#}}",String.valueOf(cnt));//�씤�썝�닔移섑솚
							
							Map pgResultInfo = frontReservationService.pgResultInfo(getReserveInfo.get("PG_RESULT").toString());//PG�젙蹂닿��졇�삤湲�						
							int compareVal = Integer.parseInt(pgResultInfo.get("TR_NO").toString().substring(0,1));				
							String r_TYPE = "";		
							switch (compareVal) {
							case 1: r_TYPE = "카드";
								break;
							case 2: r_TYPE = "실시간계좌이체";
								break;
							case 4: r_TYPE = "SSG PAY";
								break;							
							}
							joinHtml = joinHtml.replace("{{#PAYTYPE#}}",r_TYPE);//寃곗젣�닔�떒移섑솚
							
							String approvaldate = pgResultInfo.get("TR_DDT").toString();
							approvaldate = approvaldate.substring(0, 4)+"."+approvaldate.substring(4, 6)+"."+approvaldate.substring(6, 8);
							joinHtml = joinHtml.replace("{{#APPROVALDATE#}}",approvaldate);//�듅�씤�씪�떆移섑솚
							joinHtml = joinHtml.replace("{{#PRICE#}}",getReserveInfo.get("PAYMENT_PRICE").toString());//寃곗젣湲덉븸移섑솚   
							
							String canceldate = parameter.get("ac_tradedate").toString();
							canceldate = canceldate.substring(0, 4)+"."+canceldate.substring(4, 6)+"."+canceldate.substring(6, 8);
							joinHtml = joinHtml.replace("{{#CANCELDATE#}}",canceldate);//痍⑥냼�씪�떆移섑솚
							int intcancel = Integer.parseInt(parameter.get("ac_amount").toString());
							joinHtml = joinHtml.replace("{{#CANCELPRICE#}}", String.valueOf(intcancel));//痍⑥냼湲덉븸移섑솚   
							joinHtml = joinHtml.replace("{{#PENALTIES#}}",getReserveInfo.get("PANALTY_PRICE").toString());//�쐞�빟湲덉튂�솚
							String reHtml= joinHtml.replace("{{#REFUND#}}",getReserveInfo.get("REFUND").toString());

							//boolean booleanresult =	mailService.sendmail(mailSender, "aquafield@shinsegae.com", "�븘荑좎븘�븘�뱶", getReserveInfo.get("MEM_ID").toString(), getReserveInfo.get("MEM_NM").toString(), "[�븘荑좎븘�븘�뱶]�삁�빟痍⑥냼硫붿씪�엯�땲�떎.", reHtml);
							boolean booleanresult =	mailService.sendmail(mailSender, joinEmail.get("FROM_EMAIL").toString(), joinEmail.get("FORM_EMAIL_NM").toString(), getReserveInfo.get("MEM_ID").toString(), getReserveInfo.get("MEM_NM").toString(), joinEmail.get("EMAIL_TITLE").toString(), reHtml);

							if(!booleanresult){
								//html = "처리중 에러가 발생하였습니다.(이메일)";
							}else{
								//EMAIL 諛쒖넚 �씠�젰 �벑濡�
								emailParam.put("point_code", "POINT01");
								emailParam.put("email_uid", "3");
								emailParam.put("mem_id", getReserveInfo.get("MEM_ID"));
								emailParam.put("custom_nm", getReserveInfo.get("MEM_NM"));
								emailParam.put("custom_email", getReserveInfo.get("MEM_ID"));
								emailParam.put("ins_ip", "");
								emailParam.put("ins_id", getReserveInfo.get("MEM_ID")); 
								emailParam.put("send_status", "OK");
								
								String smsResult = commonService.insEmailSend(emailParam);
								if("ERROR".equals(emailParam)){
									html = "처리중 에러가 발생하였습니다.(이메일 발송 등록)";
								}						
							}						
							
							//###########################################		        			
		                	
		                //########## 遺�遺꾩랬�냼 以� �깉�삁�빟 INSERT 愿��젴 ######################################
							int intSpa_ad_Man     = Integer.parseInt(cancelParams.get("spa_ad_Man").toString());
							int intSpa_ad_Women   = Integer.parseInt(cancelParams.get("spa_ad_Women").toString());
							int intSpa_ch_Man     = Integer.parseInt(cancelParams.get("spa_ch_Man").toString());
							int intSpa_ch_Women   = Integer.parseInt(cancelParams.get("spa_ch_Women").toString());
							int intWater_ad_Man   = Integer.parseInt(cancelParams.get("water_ad_Man").toString());
							int intWater_ad_Women = Integer.parseInt(cancelParams.get("water_ad_Women").toString());
							int intWater_ch_Man   = Integer.parseInt(cancelParams.get("water_ch_Man").toString());
							int intWater_ch_Women = Integer.parseInt(cancelParams.get("water_ch_Women").toString());
							int intComplex_ad_Man   = Integer.parseInt(cancelParams.get("complex_ad_Man").toString());
							int intComplex_ad_Women = Integer.parseInt(cancelParams.get("complex_ad_Women").toString());
							int intComplex_ch_Man   = Integer.parseInt(cancelParams.get("complex_ch_Man").toString());
							int intComplex_ch_Women = Integer.parseInt(cancelParams.get("complex_ch_Women").toString());						
							int intSumVisitCnt    = Integer.parseInt(cancelParams.get("sumVisitCnt").toString());
							
							int intTotalCnt = intSpa_ad_Man + intSpa_ad_Women + intSpa_ch_Man + intSpa_ch_Women + intWater_ad_Man + intWater_ad_Women + intWater_ch_Man + intWater_ch_Women
											  + intComplex_ad_Man + intComplex_ad_Women + intComplex_ch_Man + intComplex_ch_Women;
							
							if(intTotalCnt > 0 && intSumVisitCnt > intTotalCnt){
								
								int intAdultSum = intSpa_ad_Man + intSpa_ad_Women + intWater_ad_Man + intWater_ad_Women + intComplex_ad_Man + intComplex_ad_Women;
								int intChildSum = intSpa_ch_Man + intSpa_ch_Women + intWater_ch_Man + intWater_ch_Women + intComplex_ch_Man + intComplex_ch_Women;
								cancelParams.put("adult_sum", intAdultSum);
								cancelParams.put("child_sum", intChildSum);								
								
								String insResult = newReservationIns(cancelParams, getReserveInfo);
								if("ERROR".equals(insResult)){
									html = "부분예약취소중 에러가 발생하였습니다.(NEW RESERVE DB INS)";
								}
							}
		                //########################################################################
	                	} else {
	                		//20180112 �닔�젙以�
	                		logger.debug("결제중에 인원초과로 결제 실패되었을때 SMS");
	                		html = "지정된 수량이 모두 판매되어 예약이 되지 않았습니다.";	
		                	
	                		//�삁�빟痍⑥냼臾몄옄 諛쒖넚
		        			//String contents = "[�븘荑좎븘�븘�뱶]�삩�씪�씤 �삁�빟痍⑥냼(�삁�빟踰덊샇:"+request.getParameter("ordernum")+",�삁�빟�씪:"+request.getParameter("reserveday")+")";
							Map smsParam = new HashMap();
							smsParam.put("point_code", "POINT01");
							smsParam.put("sms_type", "CANCEL");
							
							Map smsTemplte = commonService.getSmsTemplete(smsParam);  //SMS �뀥�뵆由� 媛��졇�삤湲�
							
							String contents = smsTemplte.get("SMS_CONTENT").toString();
							String ment = "지정수량이 모두 판매되어 자동 취소되었습니다.";
							
		        			int intUid2_2 = Integer.parseInt(cancelParams.get("uid").toString());
		        			Map getReserveInfo2_2 = frontReservationService.getReserveInfo(intUid2_2); //�삁�빟�젙蹂� 媛��졇�삤湲�								

		        			String pointnm = "하남";
		        			if(getReserveInfo2_2.get("POINT_CODE").equals("POINT01")) {
			        			pointnm = "하남";		        				
		        			}
		        			
		        			if(getReserveInfo2_2.get("POINT_CODE").equals("POINT03")) {
			        			pointnm = "고양";		        				
		        			}		        			
		        			
		        			if(getReserveInfo2_2.get("POINT_CODE").equals("POINT05") || getReserveInfo2_2.get("POINT_CODE").equals("POINT07")) {
			        			pointnm = "안성";		        				
		        			}	
		        			
							contents = contents.replace("{지점}",pointnm);//�삁�빟踰덊샇 移섑솚
							contents = contents.replace("{번호}",getReserveInfo2_2.get("ORDER_NUM").toString());//�삁�빟踰덊샇 移섑솚
							contents = contents.replace("{예약일}",getReserveInfo2_2.get("RESERVE_DATE").toString());//�삁�빟�씪 移섑솚	 							
							
							/*
							contents = contents.replace("{지점}",cancelParams.get("pointNm").toString() + ment);//�삁�빟踰덊샇 移섑솚
							contents = contents.replace("{번호}",cancelParams.get("ordernum").toString());//�삁�빟踰덊샇 移섑솚
							contents = contents.replace("{예약일}",cancelParams.get("reserveday").toString());//�삁�빟�씪 移섑솚	                	
							*/
		        			Map params = new HashMap();
		        					        			
		        			params.put("recipient_num", getReserveInfo2.get("MEM_MOBILE_NUM"));
		        			//params.put("recipient_num", cancelParams.get("phoneNo")); // �닔�떊踰덊샇
		        			//param.put("subject", "[�븘荑좎븘�븘�뱶]�삁�빟痍⑥냼臾몄옄�엯�땲�떎."); // LMS�씪寃쎌슦 �젣紐⑹쓣 異붽� �븷 �닔 �엳�떎.
		        			params.put("content", contents); // �궡�슜 (SMS=88Byte, LMS=2000Byte)
		        			//吏��젙�닔�웾�씠 紐⑤몢 �뙋留ㅻ릺�뼱 �옄�룞 痍⑥냼�릺�뿀�뒿�땲�떎.
		        			
		        			//params.put("callback", "031-8072-8800"); // 諛쒖떊踰덊샇
		        			params.put("callback", config.getProperty("sms.tel.number."+cancelParams.get("pointCode").toString())); // 諛쒖떊踰덊샇
		        			
		        			if(!smsService.sendSms(params)){
		        				html = "예약취소중 에러가 발생하였습니다.(문자)";
		        			}else{
								//SMS 諛쒖넚 �씠�젰 �벑濡�
								smsParam.put("sms_uid", smsTemplte.get("SMS_UID"));
								smsParam.put("mem_id", cancelParams.get("mem_id"));
								smsParam.put("custom_nm", cancelParams.get("custom_nm"));
								smsParam.put("custom_mobile", cancelParams.get("phoneNo"));
								smsParam.put("ins_ip", cancelParams.get("ins_ip"));
								smsParam.put("ins_id", cancelParams.get("mem_id")); 
								smsParam.put("send_status", "OK");	
								smsParam.put("bigo", "CARD_RESERVE_CANCEL");			
								
								String smsResult = commonService.insSmsSend(smsParam);
								if("ERROR".equals(smsResult)){
									html = "처리중 에러가 발생하였습니다.(문자이력등록)";
								}
		        			}
	                	} //else
	                }else{
	                	html = "예약취소중 에러가 발생하였습니다.(DB INS)";
	                }
	                
				}else{
					html = rMessage1+"\n "+rMessage2;
				}
			}
		}
		catch(Exception e) {
			//rMessage2			= "P잠시후재시도("+e.toString()+")";	// 硫붿떆吏�2
			rMessage2			= "결제취소가 완료되었습니다.";	// 硫붿떆吏�2			
			html = rMessage2;
		} // end of catch	
		
		return html;
	}
	
	//�떎�떆媛� 怨꾩쥖�씠泥� 痍⑥냼 愿��젴
	public String cashCancelAction(Map cancelParams, String isOnPayment, String exceptionMessage) throws Exception{
		
		String html = "예약취소가 정상적으로 되었습니다.";

		//Header遺� Data --------------------------------------------------
		String EncType				= "2" ;												// 0: �븫�솕�븞�븿, 1:ssl, 2: seed
		String Version				= "0603" ;											// �쟾臾몃쾭�쟾
		String Type					= "00" ;											// 援щ텇
		String Resend				= "0" ;												// �쟾�넚援щ텇 : 0 : 泥섏쓬,  2: �옱�쟾�넚
		String RequestDate		= new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()); // �슂泥��씪�옄 :
		String KeyInType			= "K" ;												// KeyInType �뿬遺� : S : Swap, K: KeyInType
		String LineType				= "1" ;												// lineType 0 : offline, 1:internet, 2:Mobile
		String ApprovalCount		= "1"	;											// 蹂듯빀�듅�씤媛��닔
		String GoodType				= "0" ;												// �젣�뭹援щ텇 0 : �떎臾�, 1 : �뵒吏��꽭
		String HeadFiller			= ""	;											// �삁鍮�

	// Header (�엯�젰媛� (*) �븘�닔�빆紐�)--------------------------------------------------
		String StoreId				= cancelParams.get("storeid").toString() ;					// *�긽�젏�븘�씠�뵒
		String OrderNumber			= "";												// 二쇰Ц踰덊샇
		String UserName				= "";												// 二쇰Ц�옄紐�
		String IdNum				= "";												// 二쇰�쇰쾲�샇 or �궗�뾽�옄踰덊샇
		String Email				= "";												// email
		String GoodName				= "";												// �젣�뭹紐�
		String PhoneNo				= "";												// �쑕���룿踰덊샇
	//Header end -------------------------------------------------------------------

	//Data Default------------------------------------------------------------------
		String ApprovalType	  = cancelParams.get("authty").toString()	;// �듅�씤援щ텇 肄붾뱶
		String TrNo			  = cancelParams.get("trno").toString()   ;// 嫄곕옒踰덊샇
		String Canc_amt		  = cancelParams.get("canc_amt").toString();	// 痍⑥냼湲덉븸
		String Canc_seq		  = cancelParams.get("canc_seq").toString();	// 痍⑥냼�씪�젴踰덊샇
		String Canc_type	  = cancelParams.get("canc_type").toString();	// 痍⑥냼�쑀�삎 0 :嫄곕옒踰덊샇痍⑥냼 1: 二쇰Ц踰덊샇痍⑥냼 3:遺�遺꾩랬�냼		

	// Server濡� 遺��꽣 �쓳�떟�씠 �뾾�쓣�떆 �옄泥댁쓳�떟
		String		rApprovalType    		= "2011"					; // �듅�씤援щ텇
		String		rACTransactionNo    	= TrNo						; // 嫄곕옒踰덊샇
		String		rACStatus           	= "X"						; // �삤瑜섍뎄遺� :�듅�씤 X:嫄곗젅
		String		rACTradeDate        	= RequestDate.substring(0,8); // 嫄곕옒 媛쒖떆 �씪�옄(YYYYMMDD)
		String		rACTradeTime        	= RequestDate.substring(8,14); // 嫄곕옒 媛쒖떆 �떆媛�(HHMMSS)
		String		rACAcctSele         	= ""						; // 怨꾩쥖�씠泥� 援щ텇 -	1:Dacom, 2:Pop Banking,	3:�떎�떆媛꾧퀎醫뚯씠泥� 4: �듅�씤�삎怨꾩쥖�씠泥�
		String		rACFeeSele          	= ""						; // �꽑/�썑遺덉젣援щ텇 -	1:�꽑遺�,	2:�썑遺�
		String		rACInjaName         	= ""						; // �씤�옄紐�(�넻�옣�씤�뇙硫붿꽭吏�-�긽�젏紐�)
		String		rACPareBankCode     	= ""						; // �엯湲덈え怨꾩쥖肄붾뱶
		String		rACPareAcctNo       	= ""						; // �엯湲덈え怨꾩쥖踰덊샇
		String		rACCustBankCode     	= ""						; // 異쒓툑紐④퀎醫뚯퐫�뱶
		String		rACCustAcctNo       	= ""						; // 異쒓툑紐④퀎醫뚮쾲�샇
		String		rACAmount	       		= ""						; // 湲덉븸	(寃곗젣���긽湲덉븸)
		String		rACBankTransactionNo	= ""						; // ���뻾嫄곕옒踰덊샇
		String		rACIpgumNm          	= ""						; // �엯湲덉옄紐�
		String		rACBankFee          	= "0"						; // 怨꾩쥖�씠泥� �닔�닔猷�
		String		rACBankAmount       	= ""						; // 珥앷껐�젣湲덉븸(寃곗젣���긽湲덉븸+ �닔�닔猷�
		String		rACBankRespCode     	= "9999"					; // �삤瑜섏퐫�뱶
		String		rACMessage1         	= "취소거절"				; // �삤瑜� message 1
		String		rACMessage2         	= "C잠시후재시도"			; // �삤瑜� message 2
		String		rACFiller           	= ""						; // �삁鍮�

		try 
		{
			KSPayApprovalCancelBean ipg = new KSPayApprovalCancelBean(config.getProperty("pg.ip"), 29991); 

			ipg.HeadMessage(EncType, Version, Type, Resend, RequestDate, StoreId, OrderNumber, UserName, IdNum, Email, 
							GoodType, GoodName, KeyInType, LineType, PhoneNo, ApprovalCount, HeadFiller);
			
			if (Canc_type.equals("3")){
				ipg.CancelDataMessage(ApprovalType, Canc_type, TrNo, "", "", ipg.format(Canc_amt,9,'9')+ipg.format(Canc_seq,2,'9'),"","");
			}else{ 
				ipg.CancelDataMessage(ApprovalType, "0", TrNo, "", "", "","","");
			}

			if(ipg.SendSocket("1")) {
				rApprovalType			=	ipg.ApprovalType		[0];
				rACTransactionNo	    =	ipg.ACTransactionNo		[0];   // 嫄곕옒踰덊샇
				rACStatus				=	ipg.ACStatus			[0];   // �삤瑜섍뎄遺� :�듅�씤 X:嫄곗젅
				rACTradeDate		    =	ipg.ACTradeDate			[0];   // 嫄곕옒 媛쒖떆 �씪�옄(YYYYMMDD)
				rACTradeTime		    =	ipg.ACTradeTime			[0];   // 嫄곕옒 媛쒖떆 �떆媛�(HHMMSS)
				rACAcctSele		    	=	ipg.ACAcctSele			[0];   // 怨꾩쥖�씠泥� 援щ텇 -	1:Dacom, 2:Pop Banking,	3:Scrapping 怨꾩쥖�씠泥�, 4:�듅�씤�삎怨꾩쥖�씠泥�, 5:湲덇껐�썝怨꾩쥖�씠泥�    
				rACFeeSele				=	ipg.ACFeeSele			[0];   // �꽑/�썑遺덉젣援щ텇 -	1:�꽑遺�,	2:�썑遺�
				rACInjaName		    	=	ipg.ACInjaName			[0];   // �씤�옄紐�(�넻�옣�씤�뇙硫붿꽭吏�-�긽�젏紐�)
				rACPareBankCode	    	=	ipg.ACPareBankCode		[0];   // �엯湲덈え怨꾩쥖肄붾뱶
				rACPareAcctNo			=	ipg.ACPareAcctNo		[0];   // �엯湲덈え怨꾩쥖踰덊샇
				rACCustBankCode	    	=	ipg.ACCustBankCode		[0];   // 異쒓툑紐④퀎醫뚯퐫�뱶
				rACCustAcctNo			=	ipg.ACCustAcctNo		[0];   // 異쒓툑紐④퀎醫뚮쾲�샇
				rACAmount				=	ipg.ACAmount			[0];   // 湲덉븸	(寃곗젣���긽湲덉븸)
				rACBankTransactionNo  	=	ipg.ACBankTransactionNo	[0];   // ���뻾嫄곕옒踰덊샇
				rACIpgumNm				=	ipg.ACIpgumNm			[0];   // �엯湲덉옄紐�
				rACBankFee				=	ipg.ACBankFee			[0];   // 怨꾩쥖�씠泥� �닔�닔猷�
				rACBankAmount			=	ipg.ACBankAmount		[0];   // 珥앷껐�젣湲덉븸(寃곗젣���긽湲덉븸+ �닔�닔猷�
				rACBankRespCode	    	=	ipg.ACBankRespCode		[0];   // �삤瑜섏퐫�뱶
				rACMessage1		    	=	ipg.ACMessage1			[0];   // �삤瑜� message 1
				rACMessage2		    	=	ipg.ACMessage2			[0];   // �삤瑜� message 2
				rACFiller				=	ipg.ACFiller			[0];   // �삁鍮�
				
				if("O".equals(rACStatus)){
					
					Map parameter = new HashMap();	
	                parameter.put("approval_type",rApprovalType);
	                parameter.put("ac_transactionno",rACTransactionNo);
	                parameter.put("ac_status",rACStatus);
	                parameter.put("ac_tradedate",rACTradeDate);
	                parameter.put("ac_tradetime",rACTradeTime);
	                parameter.put("ac_amount",rACAmount);
	                parameter.put("ac_message1",rACMessage1);
	                if(isOnPayment.trim().equals("Y")){
	                	parameter.put("ac_message2",exceptionMessage);
	                }else{
	                	parameter.put("ac_message2",rACMessage2);
	                }
	                
	                parameter.put("reserve_uid",cancelParams.get("uid"));
	                parameter.put("reserve_state","CANCEL");
	                parameter.put("penalty_amt",cancelParams.get("penalty_amt"));
	                
	                String result = frontReservationService.pgCancelIns(parameter);
	                
	                if("PGCANCELOK".equals(result)){
	                	if(!isOnPayment.trim().equals("Y")){
	                		html = "예약취소가 정상적으로 이루어 졌습니다.";	
		                	
		        			//�삁�빟痍⑥냼臾몄옄 諛쒖넚
		        			//String contents = "[�븘荑좎븘�븘�뱶]�삩�씪�씤 �삁�빟痍⑥냼(�삁�빟踰덊샇:"+request.getParameter("ordernum")+",�삁�빟�씪:"+request.getParameter("reserveday")+")";
							Map smsParam = new HashMap();
							smsParam.put("point_code", "POINT01");
							smsParam.put("sms_type", "CANCEL");
							
							Map smsTemplte = commonService.getSmsTemplete(smsParam);
							String contents = smsTemplte.get("SMS_CONTENT").toString();
							/*
							contents = contents.replace("{지점}",cancelParams.get("pointNm").toString());//吏��젏 移섑솚
							contents = contents.replace("{번호}",cancelParams.get("ordernum").toString());//�삁�빟踰덊샇 移섑솚
							contents = contents.replace("{예약일}",cancelParams.get("reserveday").toString());//�삁�빟�씪 移섑솚		        			
		        			
		        			Map params = new HashMap();
		        			params.put("recipient_num", cancelParams.get("phoneNo")); // �닔�떊踰덊샇
		        			//param.put("subject", "[�븘荑좎븘�븘�뱶]�삁�빟痍⑥냼臾몄옄�엯�땲�떎."); // LMS�씪寃쎌슦 �젣紐⑹쓣 異붽� �븷 �닔 �엳�떎.
		        			params.put("content", contents); // �궡�슜 (SMS=88Byte, LMS=2000Byte)		
		        			//params.put("callback", "031-8072-8800"); // 諛쒖떊踰덊샇
		        			params.put("callback", config.getProperty("sms.tel.number."+cancelParams.get("pointCode").toString())); // 諛쒖떊踰덊샇
		        			*/
							
		        			int intUid2_2 = Integer.parseInt(cancelParams.get("uid").toString());
		        			Map getReserveInfo2_2 = frontReservationService.getReserveInfo(intUid2_2); //�삁�빟�젙蹂� 媛��졇�삤湲�								

		        			String pointnm = "하남";
		        			if(getReserveInfo2_2.get("POINT_CODE").equals("POINT01")) {
			        			pointnm = "하남";		        				
		        			}
		        			
		        			if(getReserveInfo2_2.get("POINT_CODE").equals("POINT03")) {
			        			pointnm = "고양";		        				
		        			}		        			
		        			
		        			if(getReserveInfo2_2.get("POINT_CODE").equals("POINT05") || getReserveInfo2_2.get("POINT_CODE").equals("POINT07")) {
			        			pointnm = "안성";		        				
		        			}	
		        			
							contents = contents.replace("{지점}",pointnm);//�삁�빟踰덊샇 移섑솚
							contents = contents.replace("{번호}",getReserveInfo2_2.get("ORDER_NUM").toString());//�삁�빟踰덊샇 移섑솚
							contents = contents.replace("{예약일}",getReserveInfo2_2.get("RESERVE_DATE").toString());//�삁�빟�씪 移섑솚	                	
		        			
		        			/*
							contents = contents.replace("{지점}",cancelParams.get("pointNm").toString());//�삁�빟踰덊샇 移섑솚
							contents = contents.replace("{번호}",cancelParams.get("ordernum").toString());//�삁�빟踰덊샇 移섑솚
							contents = contents.replace("{예약일}",cancelParams.get("reserveday").toString());//�삁�빟�씪 移섑솚	                	
		                	*/
		        			Map params = new HashMap();
		        			
		        			
		        			
		        			params.put("recipient_num", getReserveInfo2_2.get("MEM_MOBILE_NUM")); // �닔�떊踰덊샇		        			
		        			//params.put("recipient_num", cancelParams.get("phoneNo")); // �닔�떊踰덊샇
		        			//param.put("subject", "[�븘荑좎븘�븘�뱶]�삁�빟痍⑥냼臾몄옄�엯�땲�떎."); // LMS�씪寃쎌슦 �젣紐⑹쓣 異붽� �븷 �닔 �엳�떎.
		        			params.put("content", contents); // �궡�슜 (SMS=88Byte, LMS=2000Byte)		
		        			//params.put("callback", "031-8072-8800"); // 諛쒖떊踰덊샇
		        			params.put("callback", config.getProperty("sms.tel.number."+cancelParams.get("pointCode").toString())); // 諛쒖떊踰덊샇		        			
		        			//params.put("callback", config.getProperty("sms.tel.number."+cancelParams.get("pointCode").toString())); // 諛쒖떊踰덊샇							
							
		        			if(!smsService.sendSms(params)){
		        				html = "예약취소중 에러가 발생하였습니다.(문자)";
		        			}else{
								//SMS 諛쒖넚 �씠�젰 �벑濡�
								smsParam.put("sms_uid", smsTemplte.get("SMS_UID"));
								smsParam.put("mem_id", cancelParams.get("mem_id"));
								smsParam.put("custom_nm", cancelParams.get("custom_nm"));
								smsParam.put("custom_mobile", cancelParams.get("phoneNo"));
								smsParam.put("ins_ip", cancelParams.get("ins_ip"));
								smsParam.put("ins_id", cancelParams.get("mem_id")); 
								smsParam.put("send_status", "OK");	
								smsParam.put("bigo", "CASH_RESERVE_CANCEL");
							
								String smsResult = commonService.insSmsSend(smsParam);
								if("ERROR".equals(smsResult)){
									html = "처리중 에러가 발생하였습니다.(문자이력등록)1";
								}
		        			}
		        			
							//硫붿씪諛쒖넚 #####################################
		        			int intUid = Integer.parseInt(cancelParams.get("uid").toString());
		        			Map getReserveInfo = frontReservationService.getReserveInfo(intUid); //�삁�빟�젙蹂� 媛��졇�삤湲�
		        			
							Map emailParam = new HashMap();
							emailParam.put("email_uid", "3");
							Map joinEmail = adminEmailTempletService.adminEmailTempletDetail(emailParam);
							
							String realPath = cancelParams.get("realPath").toString();
							//String joinHtml = super.getHTMfile(realPath+"/reservation_complete.html");
							
							String joinHtml = super.getHTMfile(realPath+"/3");						
							//String joinHtml = super.getHTMfile(realPath+"/reservation_cancel.html");	        			

							String nowTime = AquaDateUtil.getToday();
							nowTime = nowTime.substring(0, 4)+"."+nowTime.substring(4, 6)+"."+nowTime.substring(6, 8);						
							joinHtml = joinHtml.replace("{{#NOW#}}",nowTime); // �쁽�옱�떆媛� 移섑솚
							joinHtml = joinHtml.replace("{{#NAME#}}",getReserveInfo.get("MEM_NM").toString());//�씠由� 移섑솚
							joinHtml = joinHtml.replace("{{#RESERVENUM#}}",getReserveInfo.get("ORDER_NUM").toString());//�삁�빟踰덊샇 移섑솚
							joinHtml = joinHtml.replace("{{#POINTNM#}}",getReserveInfo.get("POINT_NM").toString());//吏��젏 移섑솚
							joinHtml = joinHtml.replace("{{#RESERVEDAY#}}",getReserveInfo.get("RESERVE_DATE").toString());//�삁�빟�씪移섑솚
							joinHtml = joinHtml.replace("{{#GOODS#}}",getReserveInfo.get("ORDER_NM").toString());//�긽�뭹紐낆튂�솚
							
							int cnt = Integer.parseInt(getReserveInfo.get("ADULT_SUM").toString()) + Integer.parseInt(getReserveInfo.get("CHILD_SUM").toString());
							joinHtml = joinHtml.replace("{{#CNT#}}",String.valueOf(cnt));//�씤�썝�닔移섑솚
							
							Map pgResultInfo = frontReservationService.pgResultInfo(getReserveInfo.get("PG_RESULT").toString());//PG�젙蹂닿��졇�삤湲�						
							int compareVal = Integer.parseInt(pgResultInfo.get("TR_NO").toString().substring(0,1));				
							String r_TYPE = "";		
							switch (compareVal) {
							case 1: r_TYPE = "카드";
								break;
							case 2: r_TYPE = "실시간계좌이체";
								break;
							case 4: r_TYPE = "SSG PAY";
								break;							
							}
							joinHtml = joinHtml.replace("{{#PAYTYPE#}}",r_TYPE);//寃곗젣�닔�떒移섑솚
							
							String approvaldate = pgResultInfo.get("TR_DDT").toString();
							approvaldate = approvaldate.substring(0, 4)+"."+approvaldate.substring(4, 6)+"."+approvaldate.substring(6, 8);
							joinHtml = joinHtml.replace("{{#APPROVALDATE#}}",approvaldate);//�듅�씤�씪�떆移섑솚
							joinHtml = joinHtml.replace("{{#PRICE#}}",getReserveInfo.get("PAYMENT_PRICE").toString());//寃곗젣湲덉븸移섑솚   
							
							String canceldate = parameter.get("ac_tradedate").toString();
							canceldate = canceldate.substring(0, 4)+"."+canceldate.substring(4, 6)+"."+canceldate.substring(6, 8);
							joinHtml = joinHtml.replace("{{#CANCELDATE#}}",canceldate);//痍⑥냼�씪�떆移섑솚
							int intcancel = Integer.parseInt(parameter.get("ac_amount").toString());
							joinHtml = joinHtml.replace("{{#CANCELPRICE#}}", String.valueOf(intcancel));//痍⑥냼湲덉븸移섑솚   
							joinHtml = joinHtml.replace("{{#PENALTIES#}}",getReserveInfo.get("PANALTY_PRICE").toString());//�쐞�빟湲덉튂�솚
							String reHtml= joinHtml.replace("{{#REFUND#}}",getReserveInfo.get("REFUND").toString());

							//boolean booleanresult =	mailService.sendmail(mailSender, "aquafield@shinsegae.com", "�븘荑좎븘�븘�뱶", getReserveInfo.get("MEM_ID").toString(), getReserveInfo.get("MEM_NM").toString(), "[�븘荑좎븘�븘�뱶]�삁�빟痍⑥냼硫붿씪�엯�땲�떎.", reHtml);
							boolean booleanresult =	mailService.sendmail(mailSender, joinEmail.get("FROM_EMAIL").toString(), joinEmail.get("FORM_EMAIL_NM").toString(), getReserveInfo.get("MEM_ID").toString(), getReserveInfo.get("MEM_NM").toString(), joinEmail.get("EMAIL_TITLE").toString(), reHtml);

							if(!booleanresult){
								//html = "처리중 에러가 발생하였습니다.(이메일)";
							}else{
								//EMAIL 諛쒖넚 �씠�젰 �벑濡�
								emailParam.put("point_code", "POINT01");
								emailParam.put("email_uid", "3");
								emailParam.put("mem_id", getReserveInfo.get("MEM_ID"));
								emailParam.put("custom_nm", getReserveInfo.get("MEM_NM"));
								emailParam.put("custom_email", getReserveInfo.get("MEM_ID"));
								emailParam.put("ins_ip", "");
								emailParam.put("ins_id", getReserveInfo.get("MEM_ID")); 
								emailParam.put("send_status", "OK");

								String smsResult = commonService.insEmailSend(emailParam);
								if("ERROR".equals(emailParam)){
									html = "泥섎━以� �뿉�윭媛� 諛쒖깮�븯���뒿�땲�떎.(�씠硫붿씪 諛쒖넚 �벑濡�)";
								}						
							}						
							//###########################################		        			

			                //########## 遺�遺꾩랬�냼 以� �깉�삁�빟 INSERT 愿��젴 ######################################
								int intSpa_ad_Man     = Integer.parseInt(cancelParams.get("spa_ad_Man").toString());
								int intSpa_ad_Women   = Integer.parseInt(cancelParams.get("spa_ad_Women").toString());
								int intSpa_ch_Man     = Integer.parseInt(cancelParams.get("spa_ch_Man").toString());
								int intSpa_ch_Women   = Integer.parseInt(cancelParams.get("spa_ch_Women").toString());
								int intWater_ad_Man   = Integer.parseInt(cancelParams.get("water_ad_Man").toString());
								int intWater_ad_Women = Integer.parseInt(cancelParams.get("water_ad_Women").toString());
								int intWater_ch_Man   = Integer.parseInt(cancelParams.get("water_ch_Man").toString());
								int intWater_ch_Women = Integer.parseInt(cancelParams.get("water_ch_Women").toString());
								int intComplex_ad_Man   = Integer.parseInt(cancelParams.get("complex_ad_Man").toString());
								int intComplex_ad_Women = Integer.parseInt(cancelParams.get("complex_ad_Women").toString());
								int intComplex_ch_Man   = Integer.parseInt(cancelParams.get("complex_ch_Man").toString());
								int intComplex_ch_Women = Integer.parseInt(cancelParams.get("complex_ch_Women").toString());							
								int intSumVisitCnt    = Integer.parseInt(cancelParams.get("sumVisitCnt").toString());
								
								int intTotalCnt = intSpa_ad_Man + intSpa_ad_Women + intSpa_ch_Man + intSpa_ch_Women + intWater_ad_Man + intWater_ad_Women + intWater_ch_Man + intWater_ch_Women
										  + intComplex_ad_Man + intComplex_ad_Women + intComplex_ch_Man + intComplex_ch_Women;
								
								if(intTotalCnt > 0 && intSumVisitCnt > intTotalCnt){
									
									int intAdultSum = intSpa_ad_Man + intSpa_ad_Women + intWater_ad_Man + intWater_ad_Women + intComplex_ad_Man + intComplex_ad_Women;
									int intChildSum = intSpa_ch_Man + intSpa_ch_Women + intWater_ch_Man + intWater_ch_Women + intComplex_ch_Man + intComplex_ch_Women;
									cancelParams.put("adult_sum", intAdultSum);
									cancelParams.put("child_sum", intChildSum);								
									
									String insResult = newReservationIns(cancelParams, getReserveInfo);
									if("ERROR".equals(insResult)){
										html = "부분예약취소중 에러가 발생하였습니다.(NEW RESERVE DB INS)";
									}
								}
			                //########################################################################
								
	                	}
	                }else{
	                	html = "예약취소중 에러가 발생하였습니다.(DB INS)";
	                }
	                
				}else{
					html = rACMessage1+"\n "+rACMessage2;
				}				
			}
		}
		catch(Exception e) {
			rACMessage2			= "P잠시후재시도("+e.toString()+")";	// 硫붿떆吏�2
		} // end of catch
	
		return html;		
	}
	

	
	//�떊�슜移대뱶 痍⑥냼 愿��젴
	public String cardPanaltyAction(Map cancelParams){
		
		String html = "예약취소가 정상적으로 되었습니다.";
		//Map memberInfo = (Map) session.getAttribute("MEM_INFO");
		// Default(�닔�젙�빆紐⑹씠 �븘�떂)-------------------------------------------------------
		String	EncType       = "2";					// 0: �븫�솕�븞�븿, 1:openssl, 2: seed
		String	Version       = "0603";				    // �쟾臾몃쾭�쟾
		String	Type          = "00";					// 援щ텇
		String	Resend        = "0";					// �쟾�넚援щ텇 : 0 : 泥섏쓬,  2: �옱�쟾�넚
		String	RequestDate   = new SimpleDateFormat("yyyyMMddhhmmss").format(new java.util.Date()); // �슂泥��씪�옄 : yyyymmddhhmmss
		String	KeyInType     = "K";					// KeyInType �뿬遺� : S : Swap, K: KeyInType
		String	LineType      = "1";			        // lineType 0 : offline, 1:internet, 2:Mobile
		String	ApprovalCount = "1";				    // 蹂듯빀�듅�씤媛��닔
		String 	GoodType      = "0";	                // �젣�뭹援щ텇 0 : �떎臾�, 1 : �뵒吏��꽭
		String	HeadFiller    = "";				        // �삁鍮�
	//-------------------------------------------------------------------------------

	// Header (�엯�젰媛� (*) �븘�닔�빆紐�)--------------------------------------------------
		String	StoreId		= cancelParams.get("storeid").toString();		// *�긽�젏�븘�씠�뵒
		String	OrderNumber	= "";									// 二쇰Ц踰덊샇
		String	UserName    = cancelParams.get("userName").toString();		// *二쇰Ц�옄紐�
		String	IdNum       = "";									// 二쇰�쇰쾲�샇 or �궗�뾽�옄踰덊샇
		String	Email       = cancelParams.get("email").toString();		// *email
		String 	GoodName    = cancelParams.get("goodName").toString();		// *�젣�뭹紐�
		String	PhoneNo     = cancelParams.get("phoneNo").toString();  	// *�쑕���룿踰덊샇                                              
	// Header end -------------------------------------------------------------------

	// Data Default(�닔�젙�빆紐⑹씠 �븘�떂)-------------------------------------------------
		String ApprovalType	  = cancelParams.get("authty").toString();		// �듅�씤援щ텇
		String TrNo   		  = cancelParams.get("trno").toString();		// 嫄곕옒踰덊샇
		String Canc_amt		  = cancelParams.get("canc_amt").toString();	// 痍⑥냼湲덉븸
		String Canc_seq		  = cancelParams.get("canc_seq").toString();	// 痍⑥냼�씪�젴踰덊샇
		String Canc_type	  = cancelParams.get("canc_type").toString();	// 痍⑥냼�쑀�삎 0 :嫄곕옒踰덊샇痍⑥냼 1: 二쇰Ц踰덊샇痍⑥냼 3:遺�遺꾩랬�냼
	// Data Default end -------------------------------------------------------------

	// Server濡� 遺��꽣 �쓳�떟�씠 �뾾�쓣�떆 �옄泥댁쓳�떟
		String rApprovalType	   = "1011"; 
		String rTransactionNo      = "";			// 嫄곕옒踰덊샇
		String rStatus             = "X";			// �긽�깭 O : �듅�씤, X : 嫄곗젅
		String rTradeDate          = ""; 			// 嫄곕옒�씪�옄
		String rTradeTime          = ""; 			// 嫄곕옒�떆媛�
		String rIssCode            = "00"; 			// 諛쒓툒�궗肄붾뱶
		String rAquCode			   = "00"; 			// 留ㅼ엯�궗肄붾뱶
		String rAuthNo             = "9999"; 		// �듅�씤踰덊샇 or 嫄곗젅�떆 �삤瑜섏퐫�뱶
		String rMessage1           = "취소거절"; 	// 硫붿떆吏�1
		String rMessage2           = "C잠시후재시도";// 硫붿떆吏�2
		String rCardNo             = ""; 			// 移대뱶踰덊샇
		String rExpDate            = ""; 			// �쑀�슚湲곌컙
		String rInstallment        = ""; 			// �븷遺�
		String rAmount             = ""; 			// 湲덉븸
		String rMerchantNo         = ""; 			// 媛�留뱀젏踰덊샇
		String rAuthSendType       = "N"; 			// �쟾�넚援щ텇
		String rApprovalSendType   = "N"; 			// �쟾�넚援щ텇(0 : 嫄곗젅, 1 : �듅�씤, 2: �썝移대뱶)
		String rPoint1             = "000000000000";// Point1
		String rPoint2             = "000000000000";// Point2
		String rPoint3             = "000000000000";// Point3
		String rPoint4             = "000000000000";// Point4
		String rVanTransactionNo   = "";
		String rFiller             = ""; 			// �삁鍮�
		String rAuthType	 	   = ""; 			// ISP : ISP嫄곕옒, MP1, MP2 : MPI嫄곕옒, SPACE : �씪諛섍굅�옒
		String rMPIPositionType	   = ""; 			// K : KSNET, R : Remote, C : �젣3湲곌�, SPACE : �씪諛섍굅�옒
		String rMPIReUseType	   = ""; 			// Y : �옱�궗�슜, N : �옱�궗�슜�븘�떂
		String rEncData			   = ""; 			// MPI, ISP �뜲�씠�꽣

		try 
		{
			KSPayApprovalCancelBean ipg = new KSPayApprovalCancelBean(config.getProperty("pg.ip"), 29991);

			ipg.HeadMessage(EncType, Version, Type, Resend, RequestDate, StoreId, OrderNumber, UserName, IdNum, Email, 
							GoodType, GoodName, KeyInType, LineType, PhoneNo, ApprovalCount, HeadFiller);

			if (Canc_type.equals("3")){
				ipg.CancelDataMessage(ApprovalType, Canc_type, TrNo, "", "", ipg.format(Canc_amt,9,'9')+ipg.format(Canc_seq,2,'9'),"","");
			}else{ 
				ipg.CancelDataMessage(ApprovalType, "0", TrNo, "", "", "","","");
			}

			if(ipg.SendSocket("1")) {
				rApprovalType		= ipg.ApprovalType[0];	    
				rTransactionNo		= ipg.TransactionNo[0];	  		// 嫄곕옒踰덊샇
				rStatus				= ipg.Status[0];		  		// �긽�깭 O : �듅�씤, X : 嫄곗젅
				rTradeDate			= ipg.TradeDate[0];		  		// 嫄곕옒�씪�옄
				rTradeTime			= ipg.TradeTime[0];		  		// 嫄곕옒�떆媛�
				rIssCode			= ipg.IssCode[0];		  		// 諛쒓툒�궗肄붾뱶
				rAquCode			= ipg.AquCode[0];		  		// 留ㅼ엯�궗肄붾뱶
				rAuthNo				= ipg.AuthNo[0];		  		// �듅�씤踰덊샇 or 嫄곗젅�떆 �삤瑜섏퐫�뱶
				rMessage1			= ipg.Message1[0];		  		// 硫붿떆吏�1
				rMessage2			= ipg.Message2[0];		  		// 硫붿떆吏�2
				rCardNo				= ipg.CardNo[0];		  		// 移대뱶踰덊샇
				rExpDate			= ipg.ExpDate[0];		  		// �쑀�슚湲곌컙
				rInstallment		= ipg.Installment[0];	  		// �븷遺�
				rAmount				= ipg.Amount[0];		  		// 湲덉븸
				rMerchantNo			= ipg.MerchantNo[0];	  		// 媛�留뱀젏踰덊샇
				rAuthSendType		= ipg.AuthSendType[0];	  		// �쟾�넚援щ텇= new String(this.read(2));
				rApprovalSendType	= ipg.ApprovalSendType[0];		// �쟾�넚援щ텇(0 : 嫄곗젅, 1 : �듅�씤, 2: �썝移대뱶)
				rPoint1				= ipg.Point1[0];		  		// Point1
				rPoint2				= ipg.Point2[0];		  		// Point2
				rPoint3				= ipg.Point3[0];		  		// Point3
				rPoint4				= ipg.Point4[0];		  		// Point4
				rVanTransactionNo   = ipg.VanTransactionNo[0];      // Van嫄곕옒踰덊샇
				rFiller				= ipg.Filler[0];		  		// �삁鍮�
				rAuthType			= ipg.AuthType[0];		  		// ISP : ISP嫄곕옒, MP1, MP2 : MPI嫄곕옒, SPACE : �씪諛섍굅�옒
				rMPIPositionType	= ipg.MPIPositionType[0]; 		// K : KSNET, R : Remote, C : �젣3湲곌�, SPACE : �씪諛섍굅�옒
				rMPIReUseType		= ipg.MPIReUseType[0];			// Y : �옱�궗�슜, N : �옱�궗�슜�븘�떂
				rEncData			= ipg.EncData[0];		  		// MPI, ISP �뜲�씠�꽣
				
				if("O".equals(rStatus)){
				
					Map parameter = new HashMap();	
	                parameter.put("approval_type",rApprovalType);
	                parameter.put("ac_transactionno",rTransactionNo);
	                parameter.put("ac_status",rStatus);
	                parameter.put("ac_tradedate",rTradeDate);
	                parameter.put("ac_tradetime",rTradeTime);
	                parameter.put("ac_amount",rAmount);
	                parameter.put("ac_message1",rMessage1);
	                parameter.put("ac_message2",rMessage2);
	                parameter.put("reserve_uid",cancelParams.get("uid"));
	                parameter.put("reserve_state",cancelParams.get("reserve_state"));
	                parameter.put("penalty_amt",cancelParams.get("penalty_amt"));
	                
	                String result = frontReservationService.pgPanaltyIns(parameter);
	                
	                if("PGCANCELOK".equals(result)){
	                	html = "위약금환불취소가 정상적으로 이루어 졌습니다.";
	                //########################################################################
	                }else{
	                	html = "위약금환불취소중 에러가 발생하였습니다.(DB INS)";
	                }
	                
				}else{
					html = rMessage1+"\n "+rMessage2;
				}
			}
		}
		catch(Exception e) {
			rMessage2			= "P잠시후재시도("+e.toString()+")";	// 硫붿떆吏�2
			html = rMessage2;
		} // end of catch	
		
		return html;
	}
	
	//�떎�떆媛� 怨꾩쥖�씠泥� 痍⑥냼 愿��젴
	public String cashPanaltyAction(Map cancelParams) throws Exception{
		
		String html = "예약취소가 정상적으로 되었습니다.";

		//Header遺� Data --------------------------------------------------
		String EncType				= "2" ;												// 0: �븫�솕�븞�븿, 1:ssl, 2: seed
		String Version				= "0603" ;											// �쟾臾몃쾭�쟾
		String Type					= "00" ;											// 援щ텇
		String Resend				= "0" ;												// �쟾�넚援щ텇 : 0 : 泥섏쓬,  2: �옱�쟾�넚
		String RequestDate		= new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()); // �슂泥��씪�옄 :
		String KeyInType			= "K" ;												// KeyInType �뿬遺� : S : Swap, K: KeyInType
		String LineType				= "1" ;												// lineType 0 : offline, 1:internet, 2:Mobile
		String ApprovalCount		= "1"	;											// 蹂듯빀�듅�씤媛��닔
		String GoodType				= "0" ;												// �젣�뭹援щ텇 0 : �떎臾�, 1 : �뵒吏��꽭
		String HeadFiller			= ""	;											// �삁鍮�

	// Header (�엯�젰媛� (*) �븘�닔�빆紐�)--------------------------------------------------
		String StoreId				= cancelParams.get("storeid").toString() ;					// *�긽�젏�븘�씠�뵒
		String OrderNumber			= "";												// 二쇰Ц踰덊샇
		String UserName				= "";												// 二쇰Ц�옄紐�
		String IdNum				= "";												// 二쇰�쇰쾲�샇 or �궗�뾽�옄踰덊샇
		String Email				= "";												// email
		String GoodName				= "";												// �젣�뭹紐�
		String PhoneNo				= "";												// �쑕���룿踰덊샇
	//Header end -------------------------------------------------------------------

	//Data Default------------------------------------------------------------------
		String ApprovalType	  = cancelParams.get("authty").toString()	;// �듅�씤援щ텇 肄붾뱶
		String TrNo			  = cancelParams.get("trno").toString()   ;// 嫄곕옒踰덊샇
		String Canc_amt		  = cancelParams.get("canc_amt").toString();	// 痍⑥냼湲덉븸
		String Canc_seq		  = cancelParams.get("canc_seq").toString();	// 痍⑥냼�씪�젴踰덊샇
		String Canc_type	  = cancelParams.get("canc_type").toString();	// 痍⑥냼�쑀�삎 0 :嫄곕옒踰덊샇痍⑥냼 1: 二쇰Ц踰덊샇痍⑥냼 3:遺�遺꾩랬�냼		

	// Server濡� 遺��꽣 �쓳�떟�씠 �뾾�쓣�떆 �옄泥댁쓳�떟
		String		rApprovalType    		= "2011"					; // �듅�씤援щ텇
		String		rACTransactionNo    	= TrNo						; // 嫄곕옒踰덊샇
		String		rACStatus           	= "X"						; // �삤瑜섍뎄遺� :�듅�씤 X:嫄곗젅
		String		rACTradeDate        	= RequestDate.substring(0,8); // 嫄곕옒 媛쒖떆 �씪�옄(YYYYMMDD)
		String		rACTradeTime        	= RequestDate.substring(8,14); // 嫄곕옒 媛쒖떆 �떆媛�(HHMMSS)
		String		rACAcctSele         	= ""						; // 怨꾩쥖�씠泥� 援щ텇 -	1:Dacom, 2:Pop Banking,	3:�떎�떆媛꾧퀎醫뚯씠泥� 4: �듅�씤�삎怨꾩쥖�씠泥�
		String		rACFeeSele          	= ""						; // �꽑/�썑遺덉젣援щ텇 -	1:�꽑遺�,	2:�썑遺�
		String		rACInjaName         	= ""						; // �씤�옄紐�(�넻�옣�씤�뇙硫붿꽭吏�-�긽�젏紐�)
		String		rACPareBankCode     	= ""						; // �엯湲덈え怨꾩쥖肄붾뱶
		String		rACPareAcctNo       	= ""						; // �엯湲덈え怨꾩쥖踰덊샇
		String		rACCustBankCode     	= ""						; // 異쒓툑紐④퀎醫뚯퐫�뱶
		String		rACCustAcctNo       	= ""						; // 異쒓툑紐④퀎醫뚮쾲�샇
		String		rACAmount	       		= ""						; // 湲덉븸	(寃곗젣���긽湲덉븸)
		String		rACBankTransactionNo	= ""						; // ���뻾嫄곕옒踰덊샇
		String		rACIpgumNm          	= ""						; // �엯湲덉옄紐�
		String		rACBankFee          	= "0"						; // 怨꾩쥖�씠泥� �닔�닔猷�
		String		rACBankAmount       	= ""						; // 珥앷껐�젣湲덉븸(寃곗젣���긽湲덉븸+ �닔�닔猷�
		String		rACBankRespCode     	= "9999"					; // �삤瑜섏퐫�뱶
		String		rACMessage1         	= "취소거절"				; // �삤瑜� message 1
		String		rACMessage2         	= "C잠시후재시도"			; // �삤瑜� message 2
		String		rACFiller           	= ""						; // �삁鍮�

		try 
		{
			KSPayApprovalCancelBean ipg = new KSPayApprovalCancelBean(config.getProperty("pg.ip"), 29991); 

			ipg.HeadMessage(EncType, Version, Type, Resend, RequestDate, StoreId, OrderNumber, UserName, IdNum, Email, 
							GoodType, GoodName, KeyInType, LineType, PhoneNo, ApprovalCount, HeadFiller);
			
			if (Canc_type.equals("3")){
				ipg.CancelDataMessage(ApprovalType, Canc_type, TrNo, "", "", ipg.format(Canc_amt,9,'9')+ipg.format(Canc_seq,2,'9'),"","");
			}else{ 
				ipg.CancelDataMessage(ApprovalType, "0", TrNo, "", "", "","","");
			}

			if(ipg.SendSocket("1")) {
				rApprovalType			=	ipg.ApprovalType		[0];
				rACTransactionNo	    =	ipg.ACTransactionNo		[0];   // 嫄곕옒踰덊샇
				rACStatus				=	ipg.ACStatus			[0];   // �삤瑜섍뎄遺� :�듅�씤 X:嫄곗젅
				rACTradeDate		    =	ipg.ACTradeDate			[0];   // 嫄곕옒 媛쒖떆 �씪�옄(YYYYMMDD)
				rACTradeTime		    =	ipg.ACTradeTime			[0];   // 嫄곕옒 媛쒖떆 �떆媛�(HHMMSS)
				rACAcctSele		    	=	ipg.ACAcctSele			[0];   // 怨꾩쥖�씠泥� 援щ텇 -	1:Dacom, 2:Pop Banking,	3:Scrapping 怨꾩쥖�씠泥�, 4:�듅�씤�삎怨꾩쥖�씠泥�, 5:湲덇껐�썝怨꾩쥖�씠泥�    
				rACFeeSele				=	ipg.ACFeeSele			[0];   // �꽑/�썑遺덉젣援щ텇 -	1:�꽑遺�,	2:�썑遺�
				rACInjaName		    	=	ipg.ACInjaName			[0];   // �씤�옄紐�(�넻�옣�씤�뇙硫붿꽭吏�-�긽�젏紐�)
				rACPareBankCode	    	=	ipg.ACPareBankCode		[0];   // �엯湲덈え怨꾩쥖肄붾뱶
				rACPareAcctNo			=	ipg.ACPareAcctNo		[0];   // �엯湲덈え怨꾩쥖踰덊샇
				rACCustBankCode	    	=	ipg.ACCustBankCode		[0];   // 異쒓툑紐④퀎醫뚯퐫�뱶
				rACCustAcctNo			=	ipg.ACCustAcctNo		[0];   // 異쒓툑紐④퀎醫뚮쾲�샇
				rACAmount				=	ipg.ACAmount			[0];   // 湲덉븸	(寃곗젣���긽湲덉븸)
				rACBankTransactionNo  	=	ipg.ACBankTransactionNo	[0];   // ���뻾嫄곕옒踰덊샇
				rACIpgumNm				=	ipg.ACIpgumNm			[0];   // �엯湲덉옄紐�
				rACBankFee				=	ipg.ACBankFee			[0];   // 怨꾩쥖�씠泥� �닔�닔猷�
				rACBankAmount			=	ipg.ACBankAmount		[0];   // 珥앷껐�젣湲덉븸(寃곗젣���긽湲덉븸+ �닔�닔猷�
				rACBankRespCode	    	=	ipg.ACBankRespCode		[0];   // �삤瑜섏퐫�뱶
				rACMessage1		    	=	ipg.ACMessage1			[0];   // �삤瑜� message 1
				rACMessage2		    	=	ipg.ACMessage2			[0];   // �삤瑜� message 2
				rACFiller				=	ipg.ACFiller			[0];   // �삁鍮�
				
				if("O".equals(rACStatus)){
					
					Map parameter = new HashMap();	
	                parameter.put("approval_type",rApprovalType);
	                parameter.put("ac_transactionno",rACTransactionNo);
	                parameter.put("ac_status",rACStatus);
	                parameter.put("ac_tradedate",rACTradeDate);
	                parameter.put("ac_tradetime",rACTradeTime);
	                parameter.put("ac_amount",rACAmount);
	                parameter.put("ac_message1",rACMessage1);
	                parameter.put("ac_message2",rACMessage2);
	                parameter.put("reserve_uid",cancelParams.get("uid"));
	                parameter.put("reserve_state",cancelParams.get("reserve_state"));
	                parameter.put("penalty_amt",cancelParams.get("penalty_amt"));
	                
	                String result = frontReservationService.pgPanaltyIns(parameter);
	                
	                if("PGCANCELOK".equals(result)){
	                	html = "위약금환불취소가 정상적으로 이루어 졌습니다.";
							
	                }else{
	                	html = "위약금환불취소중 에러가 발생하였습니다.(DB INS)";
	                }
	                
				}else{
					html = rACMessage1+"\n "+rACMessage2;
				}				
			}
		}
		catch(Exception e) {
			rACMessage2			= "P잠시후재시도("+e.toString()+")";	// 硫붿떆吏�2
		} // end of catch
	
		return html;		
	}	
	
	public String newReservationIns(Map cancelparam, Map reserveInfo) throws Exception{
		String result ="";

		String strSndOrderNum = frontReservationService.getReserveNum("");
		//String strSndOrderNum = reserveInfo.get("RESERVE_DATE").toString().replace(".", "") + sndOrdernumber + "01";
		
		int intSpa1Sum ,intSpa2Sum ,intWater1Sum, intWater2Sum, intComplex1Sum, intComplex2Sum, intRental1Sum, intRental2Sum, intRental3Sum, intEvent1Sum, intEvent2Sum, intEvent3Sum = 0;
		int itemSum0Cnt = Integer.parseInt(cancelparam.get("spa_ad_Man").toString()) + Integer.parseInt(cancelparam.get("spa_ad_Women").toString()) + Integer.parseInt(cancelparam.get("spa_ch_Man").toString()) 
				+ Integer.parseInt(cancelparam.get("spa_ch_Women").toString());
		int itemSum1Cnt = Integer.parseInt(cancelparam.get("water_ad_Man").toString()) + Integer.parseInt(cancelparam.get("water_ad_Women").toString()) + Integer.parseInt(cancelparam.get("water_ch_Man").toString()) 
				+ Integer.parseInt(cancelparam.get("water_ch_Women").toString());
		int itemSum2Cnt = Integer.parseInt(cancelparam.get("complex_ad_Man").toString()) + Integer.parseInt(cancelparam.get("complex_ad_Women").toString()) + Integer.parseInt(cancelparam.get("complex_ch_Man").toString()) 
				+ Integer.parseInt(cancelparam.get("complex_ch_Women").toString());		

		Map spa1ProdInfo = reserveInfo.get("SPA_ITEM") == null ? null : frontReservationService.getItemInfo(reserveInfo.get("SPA_ITEM").toString());  
		Map spa2ProdInfo = reserveInfo.get("SPA_ITEM") == null ? null : frontReservationService.getItemInfo(reserveInfo.get("SPA_ITEM").toString());   
		Map water1ProdInfo = reserveInfo.get("WATER_ITEM") == null ? null : frontReservationService.getItemInfo(reserveInfo.get("WATER_ITEM").toString()); 
		Map water2ProdInfo = reserveInfo.get("WATER_ITEM") == null ? null : frontReservationService.getItemInfo(reserveInfo.get("WATER_ITEM").toString());
		Map complex1ProdInfo = reserveInfo.get("COMPLEX_ITEM") == null ? null : frontReservationService.getItemInfo(reserveInfo.get("COMPLEX_ITEM").toString()); 
		Map complex2ProdInfo = reserveInfo.get("COMPLEX_ITEM") == null ? null : frontReservationService.getItemInfo(reserveInfo.get("COMPLEX_ITEM").toString()); 		
		Map rental1ProdInfo = reserveInfo.get("RENTAL1_ITEM") == null ? null : frontReservationService.getItemInfo(reserveInfo.get("RENTAL1_ITEM").toString());
		Map rental2ProdInfo = reserveInfo.get("RENTAL2_ITEM") == null ? null : frontReservationService.getItemInfo(reserveInfo.get("RENTAL2_ITEM").toString());
		Map rental3ProdInfo = reserveInfo.get("RENTAL3_ITEM") == null ? null : frontReservationService.getItemInfo(reserveInfo.get("RENTAL3_ITEM").toString());
		Map event1ProdInfo = reserveInfo.get("EVENT1_ITEM") == null ? null : frontReservationService.getItemInfo(reserveInfo.get("EVENT1_ITEM").toString()); 
		Map event2ProdInfo = reserveInfo.get("EVENT2_ITEM") == null ? null : frontReservationService.getItemInfo(reserveInfo.get("EVENT2_ITEM").toString()); 
		Map event3ProdInfo = reserveInfo.get("EVENT3_ITEM") == null ? null : frontReservationService.getItemInfo(reserveInfo.get("EVENT3_ITEM").toString()); 
		
		intSpa1Sum = prodSum(spa1ProdInfo,Integer.parseInt(cancelparam.get("spa_ad_Man").toString()) + Integer.parseInt(cancelparam.get("spa_ad_Women").toString()),"ADULTS_PRICE");
		intSpa2Sum = prodSum(spa2ProdInfo,Integer.parseInt(cancelparam.get("spa_ch_Man").toString()) + Integer.parseInt(cancelparam.get("spa_ch_Women").toString()),"CHILD_PRICE");
		intWater1Sum = prodSum(water1ProdInfo,Integer.parseInt(cancelparam.get("water_ad_Man").toString()) + Integer.parseInt(cancelparam.get("water_ad_Women").toString()),"ADULTS_PRICE");
		intWater2Sum = prodSum(water2ProdInfo,Integer.parseInt(cancelparam.get("water_ch_Man").toString()) + Integer.parseInt(cancelparam.get("water_ch_Women").toString()),"CHILD_PRICE");
		intComplex1Sum = prodSum(complex1ProdInfo,Integer.parseInt(cancelparam.get("complex_ad_Man").toString()) + Integer.parseInt(cancelparam.get("complex_ad_Women").toString()),"ADULTS_PRICE");
		intComplex2Sum = prodSum(complex2ProdInfo,Integer.parseInt(cancelparam.get("complex_ch_Man").toString()) + Integer.parseInt(cancelparam.get("complex_ch_Women").toString()),"CHILD_PRICE");		
		intRental1Sum = prodSum(rental1ProdInfo,Integer.parseInt(cancelparam.get("rental01").toString()),"RENTAL_PRICE");
		intRental2Sum = prodSum(rental2ProdInfo,Integer.parseInt(cancelparam.get("rental02").toString()),"RENTAL_PRICE");
		intRental3Sum = prodSum(rental3ProdInfo,Integer.parseInt(cancelparam.get("rental03").toString()),"RENTAL_PRICE");
		intEvent1Sum = prodSum(event1ProdInfo,Integer.parseInt(cancelparam.get("event01").toString()),"EVENT_PRICE");
		intEvent2Sum = prodSum(event2ProdInfo,Integer.parseInt(cancelparam.get("event02").toString()),"EVENT_PRICE");
		intEvent3Sum = prodSum(event3ProdInfo,Integer.parseInt(cancelparam.get("event03").toString()),"EVENT_PRICE");
		
		int totalSum = intSpa1Sum  + intSpa2Sum  + intWater1Sum + intWater2Sum + intComplex1Sum + intComplex2Sum + intRental1Sum + intRental2Sum + intRental3Sum + intEvent1Sum + intEvent2Sum + intEvent3Sum;
		int selProdTotal = intSpa1Sum  + intSpa2Sum  + intWater1Sum + intWater2Sum + intComplex1Sum + intComplex2Sum;
		int eventProdTotal = intEvent1Sum + intEvent2Sum + intEvent3Sum;
		int rentalProdTotal = intRental1Sum + intRental2Sum + intRental3Sum;
		
		String strPayDate = reserveInfo.get("PAYMENTDATE").toString();
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date payDate = transFormat.parse(strPayDate);
		
		try {
			Map parameter = new HashMap();
			parameter.put("point_code", reserveInfo.get("POINTCODE"));
			parameter.put("mem_uid", Integer.parseInt(reserveInfo.get("MEM_UID").toString()));
			parameter.put("mem_nm", reserveInfo.get("MEM_NM").toString());
			parameter.put("mem_id", reserveInfo.get("MEM_ID"));
			parameter.put("mem_mobile", reserveInfo.get("MEM_MOBILE"));
			parameter.put("adult_sum", cancelparam.get("adult_sum"));
			parameter.put("child_sum", cancelparam.get("child_sum"));
			parameter.put("reserve_date", reserveInfo.get("RESERVE_DATE").toString().replace(".", ""));
			parameter.put("reserve_state", "ING");
			parameter.put("select_item_price", selProdTotal);	
			parameter.put("event_item_price", eventProdTotal);	
			parameter.put("rental_item_price", rentalProdTotal);	
			parameter.put("payment_price", totalSum);	
			parameter.put("payment_type", reserveInfo.get("PAYMENT_TYPE"));	
			parameter.put("payment_nm", reserveInfo.get("PAYMENT_NM").toString());
			parameter.put("order_num", strSndOrderNum);	
			parameter.put("order_nm", reserveInfo.get("ORDER_NM").toString());
			parameter.put("ins_id", reserveInfo.get("MEM_ID"));					
	        parameter.put("spa_item_nm", reserveInfo.get("SPA_ITEM_NM") == null ? "": reserveInfo.get("SPA_ITEM_NM").toString());
	        parameter.put("water_item_nm", reserveInfo.get("WATER_ITEM_NM") == null ? "": reserveInfo.get("WATER_ITEM_NM").toString());
	        parameter.put("complex_item_nm", reserveInfo.get("COMPLEX_ITEM_NM") == null ? "": reserveInfo.get("COMPLEX_ITEM_NM").toString());        
	        parameter.put("spa_adult_man", cancelparam.get("spa_ad_Man"));
	        parameter.put("spa_adult_women", cancelparam.get("spa_ad_Women"));
	        parameter.put("spa_child_man", cancelparam.get("spa_ch_Man"));	
	        parameter.put("spa_child_women", cancelparam.get("spa_ch_Women"));	
	        parameter.put("water_adult_man", cancelparam.get("water_ad_Man"));	
	        parameter.put("water_adult_women", cancelparam.get("water_ad_Women"));	
	        parameter.put("water_child_man", cancelparam.get("water_ch_Man"));	
	        parameter.put("water_child_women", cancelparam.get("water_ch_Women"));
	        parameter.put("complex_adult_man", cancelparam.get("complex_ad_Man"));	
	        parameter.put("complex_adult_women", cancelparam.get("complex_ad_Women"));	
	        parameter.put("complex_child_man", cancelparam.get("complex_ch_Man"));	
	        parameter.put("complex_child_women", cancelparam.get("complex_ch_Women"));        
	        parameter.put("rental1_item_nm", reserveInfo.get("RENTAL1_ITEM_NM") == null ? "": reserveInfo.get("RENTAL1_ITEM_NM").toString());	
	        parameter.put("rental2_item_nm", reserveInfo.get("RENTAL2_ITEM_NM") == null ? "": reserveInfo.get("RENTAL2_ITEM_NM").toString());	
	        parameter.put("rental3_item_nm", reserveInfo.get("RENTAL3_ITEM_NM") == null ? "": reserveInfo.get("RENTAL3_ITEM_NM").toString());	
	        parameter.put("rental1_cnt", cancelparam.get("rental01"));	
	        parameter.put("rental2_cnt", cancelparam.get("rental02"));	
	        parameter.put("rental3_cnt", cancelparam.get("rental03"));	
	        if(reserveInfo.get("ORDER_TP").toString().equals("0")){
	        	parameter.put("event1_item_nm", reserveInfo.get("EVENT1_ITEM_NM") == null ? "": reserveInfo.get("EVENT1_ITEM_NM").toString());	
	 	        parameter.put("event2_item_nm", reserveInfo.get("EVENT2_ITEM_NM") == null ? "": reserveInfo.get("EVENT2_ITEM_NM").toString());	
	 	        parameter.put("event3_item_nm", reserveInfo.get("EVENT3_ITEM_NM") == null ? "": reserveInfo.get("EVENT3_ITEM_NM").toString());
	            parameter.put("event1_cnt", cancelparam.get("event01"));	
		        parameter.put("event2_cnt", cancelparam.get("event02"));	
		        parameter.put("event3_cnt", cancelparam.get("event03"));
	        }else{
		        parameter.put("event1_item_nm", parameter.get("spa_item_nm"));	
		        parameter.put("event2_item_nm", parameter.get("water_item_nm"));	
		        parameter.put("event3_item_nm", parameter.get("complex_item_nm"));
		        parameter.put("event1_cnt", parameter.get("spa_item_nm").equals("") ? cancelparam.get("event01") : 1);
		        parameter.put("event2_cnt", parameter.get("water_item_nm").equals("") ? cancelparam.get("event02") : 1);
		        parameter.put("event3_cnt", parameter.get("complex_item_nm").equals("") ? cancelparam.get("event03") : 1);
	        }
	        
	        parameter.put("spa_item", reserveInfo.get("SPA_ITEM") == null ? "": reserveInfo.get("SPA_ITEM"));
	        parameter.put("water_item", reserveInfo.get("WATER_ITEM") == null ? "": reserveInfo.get("WATER_ITEM"));
	        parameter.put("complex_item", reserveInfo.get("COMPLEX_ITEM") == null ? "": reserveInfo.get("COMPLEX_ITEM"));        
	        parameter.put("rental1_item", reserveInfo.get("RENTAL1_ITEM") == null ? "": reserveInfo.get("RENTAL1_ITEM"));
	        parameter.put("rental2_item", reserveInfo.get("RENTAL2_ITEM") == null ? "": reserveInfo.get("RENTAL2_ITEM"));
	        parameter.put("rental3_item", reserveInfo.get("RENTAL3_ITEM") == null ? "": reserveInfo.get("RENTAL3_ITEM"));
	        parameter.put("event1_item", reserveInfo.get("EVENT1_ITEM") == null ? "": reserveInfo.get("EVENT1_ITEM"));
	        parameter.put("event2_item", reserveInfo.get("EVENT2_ITEM") == null ? "": reserveInfo.get("EVENT2_ITEM"));
	        parameter.put("event3_item", reserveInfo.get("EVENT3_ITEM") == null ? "": reserveInfo.get("EVENT3_ITEM"));
			parameter.put("itemSum0Cnt", itemSum0Cnt);
			parameter.put("itemSum1Cnt", itemSum1Cnt);
			parameter.put("itemSum2Cnt", itemSum2Cnt);			
			parameter.put("pg_result", reserveInfo.get("PG_RESULT"));
			//-- 以묐났�쟻�쑝濡� 利앷�媛� �릺�뼱 遺�遺꾩랬�냼 �삤瑜섍� 諛쒖깮�븿 利앷��븳 1�쓣 鍮쇱��떎.
			//-- 荑쇰━�뿉�꽌 媛��졇�삱�븣 誘몃━ 利앷��븳 媛믪쓣 媛�吏�怨� �삷
			parameter.put("cancel_seq", String.valueOf(Integer.parseInt((String)reserveInfo.get("CANCEL_SEQ")) - 1));	
			parameter.put("payment_date", new Date());
			//POS�뿰�룞愿��젴 異붽� �궡�슜
			parameter.put("order_tp",reserveInfo.get("ORDER_TP"));
		
			result = frontReservationService.reserVationIns(parameter);	
			
			String reserveDay = reserveInfo.get("RESERVE_DATE").toString();
			//reserveDay = reserveDay.substring(0, 4)+"."+reserveDay.substring(4, 6)+"."+reserveDay.substring(6, 8);
			//�삁�빟臾몄옄 諛쒖넚
			//String contents = "[�븘荑좎븘�븘�뱶]�삩�씪�씤 �삁�빟(�삁�빟踰덊샇:"+param.get("sndOrdernumber")+",�삁�빟�씪:"+reserveDay+")";
			Map smsParam = new HashMap();
			smsParam.put("point_code", "POINT01");
			smsParam.put("sms_type", "RESERVE");
			
			Map smsTemplte = commonService.getSmsTemplete(smsParam);
			String contents = smsTemplte.get("SMS_CONTENT").toString();
			
			contents = contents.replace("{지점}",cancelparam.get("pointNm").toString());//�삁�빟踰덊샇 移섑솚
			contents = contents.replace("{번호}",strSndOrderNum);//�삁�빟踰덊샇 移섑솚
			contents = contents.replace("{예약일}",reserveDay);//�삁�빟�씪 移섑솚
			
			Map parameters = new HashMap();
			parameters.put("recipient_num", reserveInfo.get("MEM_MOBILE")); // �닔�떊踰덊샇
			//param.put("subject", "[�븘荑좎븘�븘�뱶]�삁�빟�솗�씤臾몄옄�엯�땲�떎.");
			parameters.put("content", contents); // �궡�슜 (SMS=88Byte, LMS=2000Byte)		
			//parameters.put("callback", "031-8072-8800"); // 諛쒖떊踰덊샇
			parameters.put("callback", config.getProperty("sms.tel.number."+cancelparam.get("pointCode").toString())); // 諛쒖떊踰덊샇
			
			if(!smsService.sendSms(parameters)){
				result = "alert('처리중 에러가 발생하였습니다.(문자)');";
			}else{
				//SMS 諛쒖넚 �씠�젰 �벑濡�
				smsParam.put("sms_uid", smsTemplte.get("SMS_UID"));
				smsParam.put("mem_id", reserveInfo.get("MEM_ID").toString());
				smsParam.put("custom_nm", reserveInfo.get("MEM_NM").toString());
				smsParam.put("custom_mobile",  reserveInfo.get("MEM_MOBILE"));
				smsParam.put("ins_ip", cancelparam.get("ins_ip"));
				smsParam.put("ins_id", reserveInfo.get("MEM_ID").toString()); 
				smsParam.put("send_status", "OK");	
				smsParam.put("bigo", "PC_RESERVE:"+strSndOrderNum);			
				
				String smsResult = commonService.insSmsSend(smsParam);
				if("ERROR".equals(smsResult)){
					result = "alert('처리중 에러가 발생하였습니다.(문자이력등록)');";
				}
			}		
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return result;
	}
	
	public Map objectInfo(Map param) throws ParseException, UnsupportedEncodingException{
		
		Map parameter = new HashMap();	

		String rsData = DecoderUtil.decode(param, "rsData");
		JSONParser jsonParser = new JSONParser();
		JSONObject rsDataObject = (JSONObject) jsonParser.parse(rsData);	
		JSONObject itemObject = (JSONObject)rsDataObject.get("item");
		JSONObject spa1Object = (JSONObject)itemObject.get("spa1");
		JSONObject spa2Object = (JSONObject)itemObject.get("spa2");
		JSONObject water1Object = (JSONObject)itemObject.get("water1");
		JSONObject water2Object = (JSONObject)itemObject.get("water2");
		JSONObject complex1Object = (JSONObject)itemObject.get("complex1");
		JSONObject complex2Object = (JSONObject)itemObject.get("complex2");		
		JSONObject rental1Object = (JSONObject)itemObject.get("rantal1");		
		JSONObject rental2Object = (JSONObject)itemObject.get("rantal2");
		JSONObject rental3Object = (JSONObject)itemObject.get("rantal3");
		JSONObject event1Object = (JSONObject)itemObject.get("event1");		
		JSONObject event2Object = (JSONObject)itemObject.get("event2");
		JSONObject event3Object = (JSONObject)itemObject.get("event3");
				
		int intSpa1Sum ,intSpa2Sum ,intWater1Sum, intWater2Sum, intComplex1Sum, intComplex2Sum, intRental1Sum, intRental2Sum, intRental3Sum, intEvent1Sum, intEvent2Sum, intEvent3Sum = 0;		
		
		Map mapSpa1    = itemInfo(spa1Object);
		Map mapSpa2    = itemInfo(spa2Object);
		Map mapWater1  = itemInfo(water1Object);
		Map mapWater2  = itemInfo(water2Object);
		Map mapComplex1 = itemInfo(complex1Object);
		Map mapComplex2 = itemInfo(complex2Object);
		Map mapRental1 = itemInfo(rental1Object);
		Map mapRental2 = itemInfo(rental2Object);	
		Map mapRental3 = itemInfo(rental3Object);
		Map mapEvent1  = itemInfo(event1Object);	
		Map mapEvent2  = itemInfo(event2Object);
		Map mapEvent3  = itemInfo(event3Object);
		
		String spaNm ="";
		String waterNm ="";
		String complexNm = "";
		if(!"".equals(mapSpa1.get("title").toString())){
			spaNm = mapSpa1.get("title").toString();
		}else if(!"".equals(mapSpa2.get("title").toString())){
			spaNm = mapSpa2.get("title").toString();
		}
		if(!"".equals(mapWater1.get("title").toString())){
			waterNm = mapWater1.get("title").toString();
		}else if(!"".equals(mapWater2.get("title").toString())){
			waterNm = mapWater2.get("title").toString();
		}
		if(!"".equals(mapComplex1.get("title").toString())){
			complexNm = mapComplex1.get("title").toString();
		}else if(!"".equals(mapComplex2.get("title").toString())){
			complexNm = mapComplex2.get("title").toString();
		}		
		
		int itemSum0Cnt = Integer.parseInt(mapSpa1.get("man").toString()) + Integer.parseInt(mapSpa1.get("women").toString()) + Integer.parseInt(mapSpa2.get("man").toString()) 
				+ Integer.parseInt(mapSpa2.get("women").toString());
		int itemSum1Cnt = Integer.parseInt(mapWater1.get("man").toString()) + Integer.parseInt(mapWater1.get("women").toString()) + Integer.parseInt(mapWater2.get("man").toString()) 
				+ Integer.parseInt(mapWater2.get("women").toString());
		int itemSum2Cnt = Integer.parseInt(mapComplex1.get("man").toString()) + Integer.parseInt(mapComplex1.get("women").toString()) + Integer.parseInt(mapComplex2.get("man").toString()) 
				+ Integer.parseInt(mapComplex2.get("women").toString());		

		Map spa1ProdInfo = frontReservationService.getItemInfo(mapSpa1.get("uid").toString());  
		Map spa2ProdInfo = frontReservationService.getItemInfo(mapSpa2.get("uid").toString());   
		Map water1ProdInfo = frontReservationService.getItemInfo(mapWater1.get("uid").toString()); 
		Map water2ProdInfo = frontReservationService.getItemInfo(mapWater2.get("uid").toString()); 
		Map complex1ProdInfo = frontReservationService.getItemInfo(mapComplex1.get("uid").toString()); 
		Map complex2ProdInfo = frontReservationService.getItemInfo(mapComplex2.get("uid").toString()); 		
		Map rental1ProdInfo = frontReservationService.getItemInfo(mapRental1.get("uid").toString());
		Map rental2ProdInfo = frontReservationService.getItemInfo(mapRental2.get("uid").toString());
		Map rental3ProdInfo = frontReservationService.getItemInfo(mapRental3.get("uid").toString());
		Map event1ProdInfo = frontReservationService.getItemInfo(mapEvent1.get("uid").toString()); 
		Map event2ProdInfo = frontReservationService.getItemInfo(mapEvent2.get("uid").toString()); 
		Map event3ProdInfo = frontReservationService.getItemInfo(mapEvent3.get("uid").toString()); 

		intSpa1Sum = prodSum(spa1ProdInfo,Integer.parseInt(mapSpa1.get("count").toString()),"ADULTS_PRICE");
		intSpa2Sum = prodSum(spa2ProdInfo,Integer.parseInt(mapSpa2.get("count").toString()),"CHILD_PRICE");
		intWater1Sum = prodSum(water1ProdInfo,Integer.parseInt(mapWater1.get("count").toString()),"ADULTS_PRICE");
		intWater2Sum = prodSum(water2ProdInfo,Integer.parseInt(mapWater2.get("count").toString()),"CHILD_PRICE");
		intComplex1Sum = prodSum(complex1ProdInfo,Integer.parseInt(mapComplex1.get("count").toString()),"ADULTS_PRICE");
		intComplex2Sum = prodSum(complex2ProdInfo,Integer.parseInt(mapComplex2.get("count").toString()),"CHILD_PRICE");		
		intRental1Sum = prodSum(rental1ProdInfo,Integer.parseInt(mapRental1.get("count").toString()),"RENTAL_PRICE");
		intRental2Sum = prodSum(rental2ProdInfo,Integer.parseInt(mapRental2.get("count").toString()),"RENTAL_PRICE");
		intRental3Sum = prodSum(rental3ProdInfo,Integer.parseInt(mapRental3.get("count").toString()),"RENTAL_PRICE");
		intEvent1Sum = prodSum(event1ProdInfo,Integer.parseInt(mapEvent1.get("count").toString()),"EVENT_PRICE");
		intEvent2Sum = prodSum(event2ProdInfo,Integer.parseInt(mapEvent2.get("count").toString()),"EVENT_PRICE");
		intEvent3Sum = prodSum(event3ProdInfo,Integer.parseInt(mapEvent3.get("count").toString()),"EVENT_PRICE");
		
		int totalSum = intSpa1Sum  + intSpa2Sum  + intWater1Sum + intWater2Sum + intComplex1Sum + intComplex2Sum + intRental1Sum + intRental2Sum + intRental3Sum + intEvent1Sum + intEvent2Sum + intEvent3Sum;
		int selProdTotal = intSpa1Sum  + intSpa2Sum  + intWater1Sum + intWater2Sum + intComplex1Sum + intComplex2Sum;
		int eventProdTotal = intEvent1Sum + intEvent2Sum + intEvent3Sum;
		int rentalProdTotal = intRental1Sum + intRental2Sum + intRental3Sum;
		int jsAmount = Integer.parseInt(rsDataObject.get("dpayment").toString());
		int adultCnt = Integer.parseInt(mapSpa1.get("count").toString()) + Integer.parseInt(mapWater1.get("count").toString()) + Integer.parseInt(mapComplex1.get("count").toString());
		int childCnt = Integer.parseInt(mapSpa2.get("count").toString()) + Integer.parseInt(mapWater2.get("count").toString()) + Integer.parseInt(mapComplex2.get("count").toString());
		int spaCnt = Integer.parseInt(mapSpa1.get("count").toString()) + Integer.parseInt(mapSpa2.get("count").toString());
		int waterCnt = Integer.parseInt(mapWater1.get("count").toString()) + Integer.parseInt(mapWater2.get("count").toString());
		int complexCnt = Integer.parseInt(mapComplex1.get("count").toString()) + Integer.parseInt(mapComplex2.get("count").toString());
		String reserveDay = rsDataObject.get("date").toString();
		String spaProdUid = "";
		String waterProdUid = "";
		String complexProdUid = "";		
		String rental1ProdUid = "";
		String rental2ProdUid = "";	
		String rental3ProdUid = "";			
		String event1ProdUid = "";
		String event2ProdUid = "";
		String event3ProdUid = "";		
		String completeMsg = "";
		String itemType = "입장상품".equals(rsDataObject.get("type").toString()) ?  "0":"1"; //POS �뿰�룞愿��젴
		
		if(spaCnt !=0){
			completeMsg += "<li>"+spaNm+" "+spaCnt+"개</li>";
		}
		if(waterCnt !=0){completeMsg += "<li>"+waterNm+" "+waterCnt+"개</li>";}
		if(complexCnt !=0){completeMsg += "<li>"+complexNm+" "+complexCnt+"개</li>";}
		
		if(Integer.parseInt(mapSpa1.get("uid").toString()) != 0 && Integer.parseInt(mapSpa1.get("count").toString()) != 0){spaProdUid = mapSpa1.get("uid").toString();}
		if(Integer.parseInt(mapSpa2.get("uid").toString()) != 0 && Integer.parseInt(mapSpa2.get("count").toString()) != 0){spaProdUid = mapSpa2.get("uid").toString();}
		if(Integer.parseInt(mapWater1.get("uid").toString()) != 0 && Integer.parseInt(mapWater1.get("count").toString()) != 0){waterProdUid = mapWater1.get("uid").toString();}
		if(Integer.parseInt(mapWater2.get("uid").toString()) != 0 && Integer.parseInt(mapWater2.get("count").toString()) != 0){waterProdUid = mapWater2.get("uid").toString();}
		if(Integer.parseInt(mapComplex1.get("uid").toString()) != 0 && Integer.parseInt(mapComplex1.get("count").toString()) != 0){complexProdUid = mapComplex1.get("uid").toString();}
		if(Integer.parseInt(mapComplex2.get("uid").toString()) != 0 && Integer.parseInt(mapComplex2.get("count").toString()) != 0){complexProdUid = mapComplex2.get("uid").toString();}
		if(Integer.parseInt(mapRental1.get("uid").toString()) != 0 && Integer.parseInt(mapRental1.get("count").toString()) != 0){rental1ProdUid = mapRental1.get("uid").toString(); completeMsg += "<li>"+mapRental1.get("title").toString()+" "+mapRental1.get("count").toString()+"개</li>";}
		if(Integer.parseInt(mapRental2.get("uid").toString()) != 0 && Integer.parseInt(mapRental2.get("count").toString()) != 0){rental2ProdUid = mapRental2.get("uid").toString(); completeMsg += "<li>"+mapRental2.get("title").toString()+" "+mapRental2.get("count").toString()+"개</li>";}
		if(Integer.parseInt(mapRental3.get("uid").toString()) != 0 && Integer.parseInt(mapRental3.get("count").toString()) != 0){rental3ProdUid = mapRental3.get("uid").toString(); completeMsg += "<li>"+mapRental3.get("title").toString()+" "+mapRental3.get("count").toString()+"개</li>";}
		if(Integer.parseInt(mapEvent1.get("uid").toString()) != 0 && Integer.parseInt(mapEvent1.get("count").toString()) != 0){event1ProdUid = mapEvent1.get("uid").toString(); completeMsg += "<li>"+mapEvent1.get("title").toString()+" "+mapEvent1.get("count").toString()+"개</li>";}
		if(Integer.parseInt(mapEvent2.get("uid").toString()) != 0 && Integer.parseInt(mapEvent2.get("count").toString()) != 0){event2ProdUid = mapEvent2.get("uid").toString(); completeMsg += "<li>"+mapEvent2.get("title").toString()+" "+mapEvent2.get("count").toString()+"개</li>";}
		if(Integer.parseInt(mapEvent3.get("uid").toString()) != 0 && Integer.parseInt(mapEvent3.get("count").toString()) != 0){event3ProdUid = mapEvent3.get("uid").toString(); completeMsg += "<li>"+mapEvent3.get("title").toString()+" "+mapEvent3.get("count").toString()+"개</li>";}
				
		parameter.put("spaProdUid", spaProdUid);
		parameter.put("waterProdUid", waterProdUid);
		parameter.put("complexProdUid", complexProdUid);		
		parameter.put("rental1ProdUid", rental1ProdUid);
		parameter.put("rental2ProdUid", rental2ProdUid);
		parameter.put("rental3ProdUid", rental3ProdUid);	
		parameter.put("event1ProdUid", event1ProdUid);
		parameter.put("event2ProdUid", event2ProdUid);
		parameter.put("event3ProdUid", event3ProdUid);			
		parameter.put("spaNm", spaNm);
		parameter.put("waterNm", waterNm);
		parameter.put("complexNm", complexNm);		
		parameter.put("spaAdultM", mapSpa1.get("man"));
		parameter.put("spaAdultW", mapSpa1.get("women"));
		parameter.put("spaChildM", mapSpa2.get("man"));
		parameter.put("spaChildW", mapSpa2.get("women"));
		parameter.put("waterAdultM", mapWater1.get("man"));  
		parameter.put("waterAdultW", mapWater1.get("women"));
		parameter.put("waterChildM", mapWater2.get("man"));  
		parameter.put("waterChildW", mapWater2.get("women"));
		parameter.put("complexAdultM", mapComplex1.get("man"));  
		parameter.put("complexAdultW", mapComplex1.get("women"));
		parameter.put("complexChildM", mapComplex2.get("man"));  
		parameter.put("complexChildW", mapComplex2.get("women"));
		parameter.put("rental1Nm", mapRental1.get("title"));
		parameter.put("rental2Nm", mapRental2.get("title"));
		parameter.put("rental3Nm", mapRental3.get("title"));
		parameter.put("rental1Cnt", mapRental1.get("cnt"));
		parameter.put("rental2Cnt", mapRental2.get("cnt"));
		parameter.put("rental3Cnt", mapRental3.get("cnt"));
		parameter.put("event1Nm", mapEvent1.get("title"));
		parameter.put("event2Nm", mapEvent2.get("title"));
		parameter.put("event3Nm", mapEvent3.get("title"));
		parameter.put("event1Cnt", mapEvent1.get("cnt"));
		parameter.put("event2Cnt", mapEvent2.get("cnt"));
		parameter.put("event3Cnt", mapEvent3.get("cnt"));
		parameter.put("itemSum0Cnt", itemSum0Cnt);
		parameter.put("itemSum1Cnt", itemSum1Cnt);	
		parameter.put("itemSum2Cnt", itemSum2Cnt);			
		parameter.put("totalSum", totalSum);
		parameter.put("selProdTotal", selProdTotal);
		parameter.put("eventProdTotal", eventProdTotal);
		parameter.put("rentalProdTotal", rentalProdTotal);
		parameter.put("jsAmount", jsAmount);
		parameter.put("reserveDay", reserveDay);	
		parameter.put("adultCnt", adultCnt);
		parameter.put("childCnt", childCnt);
		parameter.put("completeMsg", completeMsg);
		parameter.put("itemType", itemType);		
		
		return parameter;
	}
	
	public Map itemInfo(JSONObject object){
		
		Map parameter = new HashMap();		
		
		int cnt = 0;		
		if(object != null){
			parameter.put("title", object.get("title"));
			JSONArray objarry = (JSONArray) object.get("count");
			for (int i = 0; i < objarry.size(); i++) {
				cnt += Integer.parseInt(objarry.get(i).toString());
			}
			if(objarry.size() > 1){
				parameter.put("man", objarry.get(0));
				parameter.put("women", objarry.get(1));
			}else{
				parameter.put("cnt", objarry.get(0));				
			}
			parameter.put("uid", object.get("uid"));
			parameter.put("count", cnt);
		}else{
			parameter.put("title", "");
			parameter.put("man", 0);
			parameter.put("women", 0);
			parameter.put("cnt", 0);	
			parameter.put("uid", "0");
			parameter.put("count", cnt);
		}
		
		return parameter;
	}
	
	public int prodSum(Map object, int cnt, String param){
		
		int sum = 0;		
		if(object != null){
			sum = Integer.parseInt(object.get(""+param+"").toString()) * cnt;
		}
		
		return sum;
	}
	
	
	

	
	//�떊�슜移대뱶 痍⑥냼 愿��젴
	@RequestMapping(value = "/reserve/card_cancel_test.af")
    public String card_cancel_test(@RequestParam Map param, Model model, HttpSession session, HttpServletResponse response, HttpServletRequest request) throws Exception{

	    response.setHeader("Set-Cookie", "Test1=TestCookieValue1; Secure; SameSite=None"); 
	    response.addHeader("Set-Cookie", "Test2=TestCookieValue2; Secure; SameSite=None"); 
	    response.addHeader("Set-Cookie", "Test3=TestCookieValue3; Secure; SameSite=None");			
		
		Map memberInfo = (Map) session.getAttribute("MEM_INFO");
		String realPath = session.getServletContext().getRealPath("/common/upload/email_templet");
		
		Map cancelParams = new HashMap(param);
		cancelParams.put("storeid", request.getParameter("storeid"));
		cancelParams.put("userName", DecoderUtil.decode(param,"userName"));
		cancelParams.put("email", DecoderUtil.decode(param,"email"));
		cancelParams.put("goodName", DecoderUtil.decode(param,"goodName"));
		cancelParams.put("phoneNo", request.getParameter("phoneNo"));
		cancelParams.put("authty", request.getParameter("authty"));
		cancelParams.put("trno", request.getParameter("trno"));
		cancelParams.put("canc_amt", request.getParameter("canc_amt"));
		cancelParams.put("canc_seq", request.getParameter("canc_seq"));
		cancelParams.put("canc_type", request.getParameter("canc_type"));
		cancelParams.put("uid", request.getParameter("uid"));
		cancelParams.put("ordernum", request.getParameter("ordernum"));
		cancelParams.put("reserveday", request.getParameter("reserveday"));
		cancelParams.put("mem_id", DecoderUtil.decode(param,"email"));
		cancelParams.put("custom_nm", DecoderUtil.decode(param,"userName"));
		cancelParams.put("ins_ip", request.getRemoteAddr());
		cancelParams.put("realPath", realPath);
		cancelParams.put("reserve_state", DecoderUtil.decode(param,"reserve_state"));
		cancelParams.put("penalty_amt", request.getParameter("penalty_amt"));		

		String html = cardCancelTestAction(cancelParams);
		
    	Util.htmlPrint(html, response);	
		
        return null;
    }

	
	//�떊�슜移대뱶 痍⑥냼 愿��젴
	public String cardCancelTestAction(Map cancelParams){
		
		String html = "예약취소가 정상적으로 되었습니다.";
		//Map memberInfo = (Map) session.getAttribute("MEM_INFO");
		// Default(�닔�젙�빆紐⑹씠 �븘�떂)-------------------------------------------------------
		String	EncType       = "2";					// 0: �븫�솕�븞�븿, 1:openssl, 2: seed
		String	Version       = "0603";				    // �쟾臾몃쾭�쟾
		String	Type          = "00";					// 援щ텇
		String	Resend        = "0";					// �쟾�넚援щ텇 : 0 : 泥섏쓬,  2: �옱�쟾�넚
		String	RequestDate   = new SimpleDateFormat("yyyyMMddhhmmss").format(new java.util.Date()); // �슂泥��씪�옄 : yyyymmddhhmmss
		String	KeyInType     = "K";					// KeyInType �뿬遺� : S : Swap, K: KeyInType
		String	LineType      = "1";			        // lineType 0 : offline, 1:internet, 2:Mobile
		String	ApprovalCount = "1";				    // 蹂듯빀�듅�씤媛��닔
		String 	GoodType      = "0";	                // �젣�뭹援щ텇 0 : �떎臾�, 1 : �뵒吏��꽭
		String	HeadFiller    = "";				        // �삁鍮�
	//-------------------------------------------------------------------------------

	// Header (�엯�젰媛� (*) �븘�닔�빆紐�)--------------------------------------------------
		String	StoreId		= cancelParams.get("storeid").toString();		// *�긽�젏�븘�씠�뵒
		String	OrderNumber	= "";									// 二쇰Ц踰덊샇
		String	UserName    = cancelParams.get("userName").toString();		// *二쇰Ц�옄紐�
		String	IdNum       = "";									// 二쇰�쇰쾲�샇 or �궗�뾽�옄踰덊샇
		String	Email       = cancelParams.get("email").toString();		// *email
		String 	GoodName    = cancelParams.get("goodName").toString();		// *�젣�뭹紐�
		String	PhoneNo     = cancelParams.get("phoneNo").toString();  	// *�쑕���룿踰덊샇                                              
	// Header end -------------------------------------------------------------------

	// Data Default(�닔�젙�빆紐⑹씠 �븘�떂)-------------------------------------------------
		String ApprovalType	  = cancelParams.get("authty").toString();		// �듅�씤援щ텇
		String TrNo   		  = cancelParams.get("trno").toString();		// 嫄곕옒踰덊샇
		String Canc_amt		  = cancelParams.get("canc_amt").toString();	// 痍⑥냼湲덉븸
		String Canc_seq		  = cancelParams.get("canc_seq").toString();	// 痍⑥냼�씪�젴踰덊샇
		String Canc_type	  = cancelParams.get("canc_type").toString();	// 痍⑥냼�쑀�삎 0 :嫄곕옒踰덊샇痍⑥냼 1: 二쇰Ц踰덊샇痍⑥냼 3:遺�遺꾩랬�냼
	// Data Default end -------------------------------------------------------------

	// Server濡� 遺��꽣 �쓳�떟�씠 �뾾�쓣�떆 �옄泥댁쓳�떟
		String rApprovalType	   = "1011"; 
		String rTransactionNo      = "";			// 嫄곕옒踰덊샇
		String rStatus             = "X";			// �긽�깭 O : �듅�씤, X : 嫄곗젅
		String rTradeDate          = ""; 			// 嫄곕옒�씪�옄
		String rTradeTime          = ""; 			// 嫄곕옒�떆媛�
		String rIssCode            = "00"; 			// 諛쒓툒�궗肄붾뱶
		String rAquCode			   = "00"; 			// 留ㅼ엯�궗肄붾뱶
		String rAuthNo             = "9999"; 		// �듅�씤踰덊샇 or 嫄곗젅�떆 �삤瑜섏퐫�뱶
		String rMessage1           = "취소거절"; 	// 硫붿떆吏�1
		String rMessage2           = "C잠시후재시도";// 硫붿떆吏�2
		String rCardNo             = ""; 			// 移대뱶踰덊샇
		String rExpDate            = ""; 			// �쑀�슚湲곌컙
		String rInstallment        = ""; 			// �븷遺�
		String rAmount             = ""; 			// 湲덉븸
		String rMerchantNo         = ""; 			// 媛�留뱀젏踰덊샇
		String rAuthSendType       = "N"; 			// �쟾�넚援щ텇
		String rApprovalSendType   = "N"; 			// �쟾�넚援щ텇(0 : 嫄곗젅, 1 : �듅�씤, 2: �썝移대뱶)
		String rPoint1             = "000000000000";// Point1
		String rPoint2             = "000000000000";// Point2
		String rPoint3             = "000000000000";// Point3
		String rPoint4             = "000000000000";// Point4
		String rVanTransactionNo   = "";
		String rFiller             = ""; 			// �삁鍮�
		String rAuthType	 	   = ""; 			// ISP : ISP嫄곕옒, MP1, MP2 : MPI嫄곕옒, SPACE : �씪諛섍굅�옒
		String rMPIPositionType	   = ""; 			// K : KSNET, R : Remote, C : �젣3湲곌�, SPACE : �씪諛섍굅�옒
		String rMPIReUseType	   = ""; 			// Y : �옱�궗�슜, N : �옱�궗�슜�븘�떂
		String rEncData			   = ""; 			// MPI, ISP �뜲�씠�꽣

		try 
		{
			KSPayApprovalCancelBean ipg = new KSPayApprovalCancelBean(config.getProperty("pg.ip"), 29991);

			ipg.HeadMessage(EncType, Version, Type, Resend, RequestDate, StoreId, OrderNumber, UserName, IdNum, Email, 
							GoodType, GoodName, KeyInType, LineType, PhoneNo, ApprovalCount, HeadFiller);

			if (Canc_type.equals("3")){
				ipg.CancelDataMessage(ApprovalType, Canc_type, TrNo, "", "", ipg.format(Canc_amt,9,'9')+ipg.format(Canc_seq,2,'9'),"","");
			}else{ 
				ipg.CancelDataMessage(ApprovalType, "0", TrNo, "", "", "","","");
			}

			if(ipg.SendSocket("1")) {
				rApprovalType		= ipg.ApprovalType[0];	    
				rTransactionNo		= ipg.TransactionNo[0];	  		// 嫄곕옒踰덊샇
				rStatus				= ipg.Status[0];		  		// �긽�깭 O : �듅�씤, X : 嫄곗젅
				rTradeDate			= ipg.TradeDate[0];		  		// 嫄곕옒�씪�옄
				rTradeTime			= ipg.TradeTime[0];		  		// 嫄곕옒�떆媛�
				rIssCode			= ipg.IssCode[0];		  		// 諛쒓툒�궗肄붾뱶
				rAquCode			= ipg.AquCode[0];		  		// 留ㅼ엯�궗肄붾뱶
				rAuthNo				= ipg.AuthNo[0];		  		// �듅�씤踰덊샇 or 嫄곗젅�떆 �삤瑜섏퐫�뱶
				rMessage1			= ipg.Message1[0];		  		// 硫붿떆吏�1
				rMessage2			= ipg.Message2[0];		  		// 硫붿떆吏�2
				rCardNo				= ipg.CardNo[0];		  		// 移대뱶踰덊샇
				rExpDate			= ipg.ExpDate[0];		  		// �쑀�슚湲곌컙
				rInstallment		= ipg.Installment[0];	  		// �븷遺�
				rAmount				= ipg.Amount[0];		  		// 湲덉븸
				rMerchantNo			= ipg.MerchantNo[0];	  		// 媛�留뱀젏踰덊샇
				rAuthSendType		= ipg.AuthSendType[0];	  		// �쟾�넚援щ텇= new String(this.read(2));
				rApprovalSendType	= ipg.ApprovalSendType[0];		// �쟾�넚援щ텇(0 : 嫄곗젅, 1 : �듅�씤, 2: �썝移대뱶)
				rPoint1				= ipg.Point1[0];		  		// Point1
				rPoint2				= ipg.Point2[0];		  		// Point2
				rPoint3				= ipg.Point3[0];		  		// Point3
				rPoint4				= ipg.Point4[0];		  		// Point4
				rVanTransactionNo   = ipg.VanTransactionNo[0];      // Van嫄곕옒踰덊샇
				rFiller				= ipg.Filler[0];		  		// �삁鍮�
				rAuthType			= ipg.AuthType[0];		  		// ISP : ISP嫄곕옒, MP1, MP2 : MPI嫄곕옒, SPACE : �씪諛섍굅�옒
				rMPIPositionType	= ipg.MPIPositionType[0]; 		// K : KSNET, R : Remote, C : �젣3湲곌�, SPACE : �씪諛섍굅�옒
				rMPIReUseType		= ipg.MPIReUseType[0];			// Y : �옱�궗�슜, N : �옱�궗�슜�븘�떂
				rEncData			= ipg.EncData[0];		  		// MPI, ISP �뜲�씠�꽣
				
				if("O".equals(rStatus)){
				
					Map parameter = new HashMap();	
	                parameter.put("approval_type",rApprovalType);
	                parameter.put("ac_transactionno",rTransactionNo);
	                parameter.put("ac_status",rStatus);
	                parameter.put("ac_tradedate",rTradeDate);
	                parameter.put("ac_tradetime",rTradeTime);
	                parameter.put("ac_amount",rAmount);
	                parameter.put("ac_message1",rMessage1);
	                parameter.put("ac_message2",rMessage2);
	                parameter.put("reserve_uid",cancelParams.get("uid"));
	                parameter.put("reserve_state",cancelParams.get("reserve_state"));
	                parameter.put("penalty_amt",cancelParams.get("penalty_amt"));
	                
	                String result = frontReservationService.pgCancelIns(parameter);
	                
	                if("PGCANCELOK".equals(result)){
	                	html = "예약취소가 정상적으로 이루어 졌습니다.";	
	                	
	        			//�삁�빟痍⑥냼臾몄옄 諛쒖넚
	        			//String contents = "[�븘荑좎븘�븘�뱶]�삩�씪�씤 �삁�빟痍⑥냼(�삁�빟踰덊샇:"+request.getParameter("ordernum")+",�삁�빟�씪:"+request.getParameter("reserveday")+")";
						Map smsParam = new HashMap();
						smsParam.put("point_code", "POINT01");
						smsParam.put("sms_type", "CANCEL");
						
						Map smsTemplte = commonService.getSmsTemplete(smsParam);
						String contents = smsTemplte.get("SMS_CONTENT").toString();
						contents = contents.replace("{번호}",cancelParams.get("ordernum").toString());//�삁�빟踰덊샇 移섑솚
						contents = contents.replace("{예약일}",cancelParams.get("reserveday").toString());//�삁�빟�씪 移섑솚	                	
	                	
	        			Map params = new HashMap();
	        			params.put("recipient_num", cancelParams.get("phoneNo")); // �닔�떊踰덊샇
	        			//param.put("subject", "[�븘荑좎븘�븘�뱶]�삁�빟痍⑥냼臾몄옄�엯�땲�떎."); // LMS�씪寃쎌슦 �젣紐⑹쓣 異붽� �븷 �닔 �엳�떎.
	        			params.put("content", contents); // �궡�슜 (SMS=88Byte, LMS=2000Byte)		
	        			//params.put("callback", "031-8072-8800"); // 諛쒖떊踰덊샇
	        			params.put("callback", config.getProperty("sms.tel.number")); // 諛쒖떊踰덊샇
	        			
//	        			if(!smsService.sendSms(params)){
//	        				html = "�삁�빟痍⑥냼以� �뿉�윭媛� 諛쒖깮�븯���뒿�땲�떎.(臾몄옄)";
//	        			}else{
//							//SMS 諛쒖넚 �씠�젰 �벑濡�
//							smsParam.put("sms_uid", smsTemplte.get("SMS_UID"));
//							smsParam.put("mem_id", cancelParams.get("mem_id"));
//							smsParam.put("custom_nm", cancelParams.get("custom_nm"));
//							smsParam.put("custom_mobile", cancelParams.get("phoneNo"));
//							smsParam.put("ins_ip", cancelParams.get("ins_ip"));
//							smsParam.put("ins_id", cancelParams.get("mem_id")); 
//							smsParam.put("send_status", "OK");	
//							smsParam.put("bigo", "CARD_RESERVE_CANCEL");			
//							
//							String smsResult = commonService.insSmsSend(smsParam);
//							if("ERROR".equals(smsResult)){
//								html = "泥섎━以� �뿉�윭媛� 諛쒖깮�븯���뒿�땲�떎.(臾몄옄�씠�젰�벑濡�)";
//							}
//	        			}
//	        			
						//硫붿씪諛쒖넚 #####################################
	        			int intUid = Integer.parseInt(cancelParams.get("uid").toString());
	        			Map getReserveInfo = frontReservationService.getReserveInfo(intUid); //�삁�빟�젙蹂� 媛��졇�삤湲�
	        			
						Map emailParam = new HashMap();
						emailParam.put("email_uid", "3");
						Map joinEmail = adminEmailTempletService.adminEmailTempletDetail(emailParam);
						
						String realPath = cancelParams.get("realPath").toString();
						//String joinHtml = super.getHTMfile(realPath+"/reservation_complete.html");
						
						String joinHtml = super.getHTMfile(realPath+"/3");						
						//String joinHtml = super.getHTMfile(realPath+"/reservation_cancel.html");
						
						String nowTime = AquaDateUtil.getToday();
						nowTime = nowTime.substring(0, 4)+"."+nowTime.substring(4, 6)+"."+nowTime.substring(6, 8);						
						joinHtml = joinHtml.replace("{{#NOW#}}",nowTime); // �쁽�옱�떆媛� 移섑솚
						joinHtml = joinHtml.replace("{{#NAME#}}",getReserveInfo.get("MEM_NM").toString());//�씠由� 移섑솚
						joinHtml = joinHtml.replace("{{#RESERVENUM#}}",getReserveInfo.get("ORDER_NUM").toString());//�삁�빟踰덊샇 移섑솚
						joinHtml = joinHtml.replace("{{#RESERVEDAY#}}",getReserveInfo.get("RESERVE_DATE").toString());//�삁�빟�씪移섑솚
						joinHtml = joinHtml.replace("{{#GOODS#}}",getReserveInfo.get("ORDER_NM").toString());//�긽�뭹紐낆튂�솚
						
						int cnt = Integer.parseInt(getReserveInfo.get("ADULT_SUM").toString()) + Integer.parseInt(getReserveInfo.get("CHILD_SUM").toString());
						joinHtml = joinHtml.replace("{{#CNT#}}",String.valueOf(cnt));//�씤�썝�닔移섑솚
						
						Map pgResultInfo = frontReservationService.pgResultInfo(getReserveInfo.get("PG_RESULT").toString());//PG�젙蹂닿��졇�삤湲�						
						int compareVal = Integer.parseInt(pgResultInfo.get("TR_NO").toString().substring(0,1));				
						String r_TYPE = "";		
						switch (compareVal) {
						case 1: r_TYPE = "카드";
							break;
						case 2: r_TYPE = "실시간계좌이체";
							break;
						case 4: r_TYPE = "SSG PAY";
							break;							
						}
						joinHtml = joinHtml.replace("{{#PAYTYPE#}}",r_TYPE);//寃곗젣�닔�떒移섑솚
						
						String approvaldate = pgResultInfo.get("TR_DDT").toString();
						approvaldate = approvaldate.substring(0, 4)+"."+approvaldate.substring(4, 6)+"."+approvaldate.substring(6, 8);
						joinHtml = joinHtml.replace("{{#APPROVALDATE#}}",approvaldate);//�듅�씤�씪�떆移섑솚
						joinHtml = joinHtml.replace("{{#PRICE#}}",getReserveInfo.get("PAYMENT_PRICE").toString());//寃곗젣湲덉븸移섑솚   
						
						String canceldate = parameter.get("ac_tradedate").toString();
						canceldate = canceldate.substring(0, 4)+"."+canceldate.substring(4, 6)+"."+canceldate.substring(6, 8);
						joinHtml = joinHtml.replace("{{#CANCELDATE#}}",canceldate);//痍⑥냼�씪�떆移섑솚
						int intcancel = Integer.parseInt(parameter.get("ac_amount").toString());
						joinHtml = joinHtml.replace("{{#CANCELPRICE#}}", String.valueOf(intcancel));//痍⑥냼湲덉븸移섑솚   
						joinHtml = joinHtml.replace("{{#PENALTIES#}}",getReserveInfo.get("PANALTY_PRICE").toString());//�쐞�빟湲덉튂�솚
						String reHtml= joinHtml.replace("{{#REFUND#}}",getReserveInfo.get("REFUND").toString());

						//boolean booleanresult =	mailService.sendmail(mailSender, "aquafield@shinsegae.com", "�븘荑좎븘�븘�뱶", getReserveInfo.get("MEM_ID").toString(), getReserveInfo.get("MEM_NM").toString(), "[�븘荑좎븘�븘�뱶]�삁�빟痍⑥냼硫붿씪�엯�땲�떎.", reHtml);
//						boolean booleanresult =	mailService.sendmail(mailSender, joinEmail.get("FROM_EMAIL").toString(), joinEmail.get("FORM_EMAIL_NM").toString(), getReserveInfo.get("MEM_ID").toString(), getReserveInfo.get("MEM_NM").toString(), joinEmail.get("EMAIL_TITLE").toString(), reHtml);
//
//						if(!booleanresult){
//							html = "泥섎━以� �뿉�윭媛� 諛쒖깮�븯���뒿�땲�떎.(�씠硫붿씪)";
//						}else{
//							//EMAIL 諛쒖넚 �씠�젰 �벑濡�
//							emailParam.put("point_code", "POINT01");
//							emailParam.put("email_uid", "3");
//							emailParam.put("mem_id", getReserveInfo.get("MEM_ID"));
//							emailParam.put("custom_nm", getReserveInfo.get("MEM_NM"));
//							emailParam.put("custom_email", getReserveInfo.get("MEM_ID"));
//							emailParam.put("ins_ip", "");
//							emailParam.put("ins_id", getReserveInfo.get("MEM_ID")); 
//							emailParam.put("send_status", "OK");
//							
//							String smsResult = commonService.insEmailSend(emailParam);
//							if("ERROR".equals(emailParam)){
//								html = "泥섎━以� �뿉�윭媛� 諛쒖깮�븯���뒿�땲�떎.(�씠硫붿씪 諛쒖넚 �벑濡�)";
//							}						
//						}						
//						
						//###########################################		        			
	                	
	                //########## 遺�遺꾩랬�냼 以� �깉�삁�빟 INSERT 愿��젴 ######################################
						int intSpa_ad_Man     = Integer.parseInt(cancelParams.get("spa_ad_Man").toString());
						int intSpa_ad_Women   = Integer.parseInt(cancelParams.get("spa_ad_Women").toString());
						int intSpa_ch_Man     = Integer.parseInt(cancelParams.get("spa_ch_Man").toString());
						int intSpa_ch_Women   = Integer.parseInt(cancelParams.get("spa_ch_Women").toString());
						int intWater_ad_Man   = Integer.parseInt(cancelParams.get("water_ad_Man").toString());
						int intWater_ad_Women = Integer.parseInt(cancelParams.get("water_ad_Women").toString());
						int intWater_ch_Man   = Integer.parseInt(cancelParams.get("water_ch_Man").toString());
						int intWater_ch_Women = Integer.parseInt(cancelParams.get("water_ch_Women").toString());
						int intComplex_ad_Man   = Integer.parseInt(cancelParams.get("complex_ad_Man").toString());
						int intComplex_ad_Women = Integer.parseInt(cancelParams.get("complex_ad_Women").toString());
						int intComplex_ch_Man   = Integer.parseInt(cancelParams.get("complex_ch_Man").toString());
						int intComplex_ch_Women = Integer.parseInt(cancelParams.get("complex_ch_Women").toString());						
						int intSumVisitCnt    = Integer.parseInt(cancelParams.get("sumVisitCnt").toString());
						
						int intTotalCnt = intSpa_ad_Man + intSpa_ad_Women + intSpa_ch_Man + intSpa_ch_Women + intWater_ad_Man + intWater_ad_Women + intWater_ch_Man + intWater_ch_Women
										  + intComplex_ad_Man + intComplex_ad_Women + intComplex_ch_Man + intComplex_ch_Women;
						
						if(intTotalCnt > 0 && intSumVisitCnt > intTotalCnt){
							
							int intAdultSum = intSpa_ad_Man + intSpa_ad_Women + intWater_ad_Man + intWater_ad_Women + intComplex_ad_Man + intComplex_ad_Women;
							int intChildSum = intSpa_ch_Man + intSpa_ch_Women + intWater_ch_Man + intWater_ch_Women + intComplex_ch_Man + intComplex_ch_Women;
							cancelParams.put("adult_sum", intAdultSum);
							cancelParams.put("child_sum", intChildSum);								
							
							String insResult = newReservationIns(cancelParams, getReserveInfo);
							if("ERROR".equals(insResult)){
								html = "부분예약취소중 에러가 발생하였습니다.(NEW RESERVE DB INS)";
							}
						}
	                //########################################################################
	                }else{
	                	html = "예약취소중 에러가 발생하였습니다.(DB INS)";
	                }
	                
				}else{
					html = rMessage1+"\n "+rMessage2;
				}
			}
		}
		catch(Exception e) {
			rMessage2			= "P잠시후재시도("+e.toString()+")";	// 硫붿떆吏�2
			html = rMessage2;
		} // end of catch	
		
		return html;
	}

	
	public String newReservationInsTest(Map cancelparam, Map reserveInfo) throws Exception{
		String result ="";

		String strSndOrderNum = frontReservationService.getReserveNum("");
		//String strSndOrderNum = reserveInfo.get("RESERVE_DATE").toString().replace(".", "") + sndOrdernumber + "01";
		
		int intSpa1Sum ,intSpa2Sum ,intWater1Sum, intWater2Sum, intComplex1Sum, intComplex2Sum, intRental1Sum, intRental2Sum, intRental3Sum, intEvent1Sum, intEvent2Sum, intEvent3Sum = 0;
		int itemSum0Cnt = Integer.parseInt(cancelparam.get("spa_ad_Man").toString()) + Integer.parseInt(cancelparam.get("spa_ad_Women").toString()) + Integer.parseInt(cancelparam.get("spa_ch_Man").toString()) 
				+ Integer.parseInt(cancelparam.get("spa_ch_Women").toString());
		int itemSum1Cnt = Integer.parseInt(cancelparam.get("water_ad_Man").toString()) + Integer.parseInt(cancelparam.get("water_ad_Women").toString()) + Integer.parseInt(cancelparam.get("water_ch_Man").toString()) 
				+ Integer.parseInt(cancelparam.get("water_ch_Women").toString());
		int itemSum2Cnt = Integer.parseInt(cancelparam.get("complex_ad_Man").toString()) + Integer.parseInt(cancelparam.get("complex_ad_Women").toString()) + Integer.parseInt(cancelparam.get("complex_ch_Man").toString()) 
				+ Integer.parseInt(cancelparam.get("complex_ch_Women").toString());		

		Map spa1ProdInfo = reserveInfo.get("SPA_ITEM") == null ? null : frontReservationService.getItemInfo(reserveInfo.get("SPA_ITEM").toString());  
		Map spa2ProdInfo = reserveInfo.get("SPA_ITEM") == null ? null : frontReservationService.getItemInfo(reserveInfo.get("SPA_ITEM").toString());   
		Map water1ProdInfo = reserveInfo.get("WATER_ITEM") == null ? null : frontReservationService.getItemInfo(reserveInfo.get("WATER_ITEM").toString()); 
		Map water2ProdInfo = reserveInfo.get("WATER_ITEM") == null ? null : frontReservationService.getItemInfo(reserveInfo.get("WATER_ITEM").toString());
		Map complex1ProdInfo = reserveInfo.get("COMPLEX_ITEM") == null ? null : frontReservationService.getItemInfo(reserveInfo.get("COMPLEX_ITEM").toString()); 
		Map complex2ProdInfo = reserveInfo.get("COMPLEX_ITEM") == null ? null : frontReservationService.getItemInfo(reserveInfo.get("COMPLEX_ITEM").toString()); 		
		Map rental1ProdInfo = reserveInfo.get("RENTAL1_ITEM") == null ? null : frontReservationService.getItemInfo(reserveInfo.get("RENTAL1_ITEM").toString());
		Map rental2ProdInfo = reserveInfo.get("RENTAL2_ITEM") == null ? null : frontReservationService.getItemInfo(reserveInfo.get("RENTAL2_ITEM").toString());
		Map rental3ProdInfo = reserveInfo.get("RENTAL3_ITEM") == null ? null : frontReservationService.getItemInfo(reserveInfo.get("RENTAL3_ITEM").toString());
		Map event1ProdInfo = reserveInfo.get("EVENT1_ITEM") == null ? null : frontReservationService.getItemInfo(reserveInfo.get("EVENT1_ITEM").toString()); 
		Map event2ProdInfo = reserveInfo.get("EVENT2_ITEM") == null ? null : frontReservationService.getItemInfo(reserveInfo.get("EVENT2_ITEM").toString()); 
		Map event3ProdInfo = reserveInfo.get("EVENT3_ITEM") == null ? null : frontReservationService.getItemInfo(reserveInfo.get("EVENT3_ITEM").toString()); 
		
		intSpa1Sum = prodSum(spa1ProdInfo,Integer.parseInt(cancelparam.get("spa_ad_Man").toString()) + Integer.parseInt(cancelparam.get("spa_ad_Women").toString()),"ADULTS_PRICE");
		intSpa2Sum = prodSum(spa2ProdInfo,Integer.parseInt(cancelparam.get("spa_ch_Man").toString()) + Integer.parseInt(cancelparam.get("spa_ch_Women").toString()),"CHILD_PRICE");
		intWater1Sum = prodSum(water1ProdInfo,Integer.parseInt(cancelparam.get("water_ad_Man").toString()) + Integer.parseInt(cancelparam.get("water_ad_Women").toString()),"ADULTS_PRICE");
		intWater2Sum = prodSum(water2ProdInfo,Integer.parseInt(cancelparam.get("water_ch_Man").toString()) + Integer.parseInt(cancelparam.get("water_ch_Women").toString()),"CHILD_PRICE");
		intComplex1Sum = prodSum(complex1ProdInfo,Integer.parseInt(cancelparam.get("complex_ad_Man").toString()) + Integer.parseInt(cancelparam.get("complex_ad_Women").toString()),"ADULTS_PRICE");
		intComplex2Sum = prodSum(complex2ProdInfo,Integer.parseInt(cancelparam.get("complex_ch_Man").toString()) + Integer.parseInt(cancelparam.get("complex_ch_Women").toString()),"CHILD_PRICE");		
		intRental1Sum = prodSum(rental1ProdInfo,Integer.parseInt(cancelparam.get("rental01").toString()),"RENTAL_PRICE");
		intRental2Sum = prodSum(rental2ProdInfo,Integer.parseInt(cancelparam.get("rental02").toString()),"RENTAL_PRICE");
		intRental3Sum = prodSum(rental3ProdInfo,Integer.parseInt(cancelparam.get("rental03").toString()),"RENTAL_PRICE");
		intEvent1Sum = prodSum(event1ProdInfo,Integer.parseInt(cancelparam.get("event01").toString()),"EVENT_PRICE");
		intEvent2Sum = prodSum(event2ProdInfo,Integer.parseInt(cancelparam.get("event02").toString()),"EVENT_PRICE");
		intEvent3Sum = prodSum(event3ProdInfo,Integer.parseInt(cancelparam.get("event03").toString()),"EVENT_PRICE");
		
		int totalSum = intSpa1Sum  + intSpa2Sum  + intWater1Sum + intWater2Sum + intComplex1Sum + intComplex2Sum + intRental1Sum + intRental2Sum + intRental3Sum + intEvent1Sum + intEvent2Sum + intEvent3Sum;
		int selProdTotal = intSpa1Sum  + intSpa2Sum  + intWater1Sum + intWater2Sum + intComplex1Sum + intComplex2Sum;
		int eventProdTotal = intEvent1Sum + intEvent2Sum + intEvent3Sum;
		int rentalProdTotal = intRental1Sum + intRental2Sum + intRental3Sum;
		
		String strPayDate = reserveInfo.get("PAYMENTDATE").toString();
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date payDate = transFormat.parse(strPayDate);
		
		try {
			Map parameter = new HashMap();
			parameter.put("point_code", reserveInfo.get("POINTCODE"));
			parameter.put("mem_uid", Integer.parseInt(reserveInfo.get("MEM_UID").toString()));
			parameter.put("mem_nm", reserveInfo.get("MEM_NM").toString());
			parameter.put("mem_id", reserveInfo.get("MEM_ID"));
			parameter.put("mem_mobile", reserveInfo.get("MEM_MOBILE"));
			parameter.put("adult_sum", cancelparam.get("adult_sum"));
			parameter.put("child_sum", cancelparam.get("child_sum"));
			parameter.put("reserve_date", reserveInfo.get("RESERVE_DATE").toString().replace(".", ""));
			parameter.put("reserve_state", "ING");
			parameter.put("select_item_price", selProdTotal);	
			parameter.put("event_item_price", eventProdTotal);	
			parameter.put("rental_item_price", rentalProdTotal);	
			parameter.put("payment_price", totalSum);	
			parameter.put("payment_type", reserveInfo.get("PAYMENT_TYPE"));	
			parameter.put("payment_nm", reserveInfo.get("PAYMENT_NM").toString());
			parameter.put("order_num", strSndOrderNum);	
			parameter.put("order_nm", reserveInfo.get("ORDER_NM").toString());
			parameter.put("ins_id", reserveInfo.get("MEM_ID"));					
	        parameter.put("spa_item_nm", reserveInfo.get("SPA_ITEM_NM") == null ? "": reserveInfo.get("SPA_ITEM_NM").toString());
	        parameter.put("water_item_nm", reserveInfo.get("WATER_ITEM_NM") == null ? "": reserveInfo.get("WATER_ITEM_NM").toString());
	        parameter.put("complex_item_nm", reserveInfo.get("COMPLEX_ITEM_NM") == null ? "": reserveInfo.get("COMPLEX_ITEM_NM").toString());        
	        parameter.put("spa_adult_man", cancelparam.get("spa_ad_Man"));
	        parameter.put("spa_adult_women", cancelparam.get("spa_ad_Women"));
	        parameter.put("spa_child_man", cancelparam.get("spa_ch_Man"));	
	        parameter.put("spa_child_women", cancelparam.get("spa_ch_Women"));	
	        parameter.put("water_adult_man", cancelparam.get("water_ad_Man"));	
	        parameter.put("water_adult_women", cancelparam.get("water_ad_Women"));	
	        parameter.put("water_child_man", cancelparam.get("water_ch_Man"));	
	        parameter.put("water_child_women", cancelparam.get("water_ch_Women"));
	        parameter.put("complex_adult_man", cancelparam.get("complex_ad_Man"));	
	        parameter.put("complex_adult_women", cancelparam.get("complex_ad_Women"));	
	        parameter.put("complex_child_man", cancelparam.get("complex_ch_Man"));	
	        parameter.put("complex_child_women", cancelparam.get("complex_ch_Women"));        
	        parameter.put("rental1_item_nm", reserveInfo.get("RENTAL1_ITEM_NM") == null ? "": reserveInfo.get("RENTAL1_ITEM_NM").toString());	
	        parameter.put("rental2_item_nm", reserveInfo.get("RENTAL2_ITEM_NM") == null ? "": reserveInfo.get("RENTAL2_ITEM_NM").toString());	
	        parameter.put("rental3_item_nm", reserveInfo.get("RENTAL3_ITEM_NM") == null ? "": reserveInfo.get("RENTAL3_ITEM_NM").toString());	
	        parameter.put("rental1_cnt", cancelparam.get("rental01"));	
	        parameter.put("rental2_cnt", cancelparam.get("rental02"));	
	        parameter.put("rental3_cnt", cancelparam.get("rental03"));	
	        parameter.put("event1_item_nm", reserveInfo.get("EVENT1_ITEM_NM") == null ? "": reserveInfo.get("EVENT1_ITEM_NM").toString());	
	        parameter.put("event2_item_nm", reserveInfo.get("EVENT2_ITEM_NM") == null ? "": reserveInfo.get("EVENT2_ITEM_NM").toString());	
	        parameter.put("event3_item_nm", reserveInfo.get("EVENT3_ITEM_NM") == null ? "": reserveInfo.get("EVENT3_ITEM_NM").toString());	
	        parameter.put("event1_cnt", cancelparam.get("event01"));	
	        parameter.put("event2_cnt", cancelparam.get("event02"));	
	        parameter.put("event3_cnt", cancelparam.get("event03"));
	        parameter.put("spa_item", reserveInfo.get("SPA_ITEM") == null ? "": reserveInfo.get("SPA_ITEM"));
	        parameter.put("water_item", reserveInfo.get("WATER_ITEM") == null ? "": reserveInfo.get("WATER_ITEM"));
	        parameter.put("complex_item", reserveInfo.get("COMPLEX_ITEM") == null ? "": reserveInfo.get("COMPLEX_ITEM"));        
	        parameter.put("rental1_item", reserveInfo.get("RENTAL1_ITEM") == null ? "": reserveInfo.get("RENTAL1_ITEM"));
	        parameter.put("rental2_item", reserveInfo.get("RENTAL2_ITEM") == null ? "": reserveInfo.get("RENTAL2_ITEM"));
	        parameter.put("rental3_item", reserveInfo.get("RENTAL3_ITEM") == null ? "": reserveInfo.get("RENTAL3_ITEM"));
	        parameter.put("event1_item", reserveInfo.get("EVENT1_ITEM") == null ? "": reserveInfo.get("EVENT1_ITEM"));
	        parameter.put("event2_item", reserveInfo.get("EVENT2_ITEM") == null ? "": reserveInfo.get("EVENT2_ITEM"));
	        parameter.put("event3_item", reserveInfo.get("EVENT3_ITEM") == null ? "": reserveInfo.get("EVENT3_ITEM"));
			parameter.put("itemSum0Cnt", itemSum0Cnt);
			parameter.put("itemSum1Cnt", itemSum1Cnt);
			parameter.put("itemSum2Cnt", itemSum2Cnt);			
			parameter.put("pg_result", reserveInfo.get("PG_RESULT"));
			//-- 以묐났�쟻�쑝濡� 利앷�媛� �릺�뼱 遺�遺꾩랬�냼 �삤瑜섍� 諛쒖깮�븿 利앷��븳 1�쓣 鍮쇱��떎.
			//-- 荑쇰━�뿉�꽌 媛��졇�삱�븣 誘몃━ 利앷��븳 媛믪쓣 媛�吏�怨� �삷
			parameter.put("cancel_seq", String.valueOf(Integer.parseInt((String)reserveInfo.get("CANCEL_SEQ")) - 1));	
			parameter.put("payment_date", new Date());
			//POS�뿰�룞愿��젴 異붽� �궡�슜
			parameter.put("order_tp",reserveInfo.get("ORDER_TP"));
		
			result = frontReservationService.reserVationIns(parameter);	
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return result;
	}
	
	//�늻�씫�맂 �궗�슜�옄 �뜲�씠�꽣 INSERT
	@RequestMapping(value = "/reserve/missing_user_insert_proc.af")
	public String missingUserInsertProc(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {

	    response.setHeader("Set-Cookie", "Test1=TestCookieValue1; Secure; SameSite=None"); 
	    response.addHeader("Set-Cookie", "Test2=TestCookieValue2; Secure; SameSite=None"); 
	    response.addHeader("Set-Cookie", "Test3=TestCookieValue3; Secure; SameSite=None");			
		
		String memId =DecoderUtil.decode(param, "mem_id");
		Map memberInfo = frontReservationService.getMemInfo(memId);
		
		Map params = new HashMap();			
        params.put("auth_yn", "O");
        params.put("tr_no", param.get("tr_no"));
        params.put("tr_ddt", param.get("tr_ddt"));
        params.put("tr_dtm", param.get("tr_dtm"));
        params.put("amonut", param.get("amonut"));
        params.put("auth_no", param.get("auth_no"));
        params.put("msg1", DecoderUtil.decode(param,"msg1"));
        params.put("msg2", "");
        params.put("ord_no", param.get("ord_no"));
        params.put("iss_cd", param.get("iss_cd"));
        params.put("aqu_cd", param.get("aqu_cd"));
        params.put("result", param.get("result"));
        params.put("halbu", param.get("halbu"));
        params.put("cbt_no", param.get("cbt_no"));
        params.put("cbauth_no", param.get("cbauth_no"));
        params.put("mem_uid", memberInfo.get("MEM_UID"));

		Map pgResult =  frontReservationService.pgResultIns(params);
		
		//�삁�빟 INSERT	
		Map objParams = objectInfo(param);		

		Map parameter = new HashMap();
		
		parameter.put("point_code", param.get("point_code"));
		parameter.put("mem_uid", Integer.parseInt(memberInfo.get("MEM_UID").toString()));
		parameter.put("mem_nm", memberInfo.get("MEM_NM"));
		parameter.put("mem_id", memberInfo.get("MEM_ID"));
		parameter.put("mem_mobile", memberInfo.get("MOBILE_NUM"));
		
		parameter.put("adult_sum", objParams.get("adultCnt"));
		parameter.put("child_sum", objParams.get("childCnt"));
		parameter.put("reserve_date", objParams.get("reserveDay"));
		parameter.put("reserve_state", "ING");
		parameter.put("select_item_price", objParams.get("selProdTotal"));	
		parameter.put("event_item_price", objParams.get("eventProdTotal"));	
		parameter.put("rental_item_price", objParams.get("rentalProdTotal"));	
		parameter.put("payment_price", objParams.get("jsAmount"));	
		parameter.put("payment_type", param.get("tr_no"));
		parameter.put("payment_nm", DecoderUtil.decode(memberInfo, "MEM_NM"));
		parameter.put("order_num", param.get("ord_no"));	
		parameter.put("order_nm", "�엯�옣�긽�뭹"); //�엯�옣�긽�뭹
		parameter.put("ins_id", memberInfo.get("MEM_ID"));					
	    parameter.put("spa_item_nm", objParams.get("spaNm"));
	    parameter.put("water_item_nm", objParams.get("waterNm"));
	    parameter.put("complex_item_nm", objParams.get("complexNm"));   // DB異붽�                 
	    parameter.put("spa_adult_man", objParams.get("spaAdultM"));
	    parameter.put("spa_adult_women", objParams.get("spaAdultW"));
	    parameter.put("spa_child_man", objParams.get("spaChildM"));	
	    parameter.put("spa_child_women", objParams.get("spaChildW"));	
	    parameter.put("water_adult_man", objParams.get("waterAdultM"));	
	    parameter.put("water_adult_women", objParams.get("waterAdultW"));	
	    parameter.put("water_child_man", objParams.get("waterChildM"));	
	    parameter.put("water_child_women", objParams.get("waterChildW"));                
	    parameter.put("complex_adult_man", objParams.get("complexAdultM"));	//DB異붽�
	    parameter.put("complex_adult_women", objParams.get("complexAdultW")); //DB異붽�	
	    parameter.put("complex_child_man", objParams.get("complexChildM"));	 //DB異붽�
	    parameter.put("complex_child_women", objParams.get("complexChildW")); //DB異붽�                                     
	    parameter.put("rental1_item_nm", objParams.get("rental1Nm"));	
	    parameter.put("rental2_item_nm", objParams.get("rental2Nm"));	
	    parameter.put("rental3_item_nm", objParams.get("rental3Nm"));	
	    parameter.put("rental1_cnt", objParams.get("rental1Cnt"));	
	    parameter.put("rental2_cnt", objParams.get("rental2Cnt"));	
	    parameter.put("rental3_cnt", objParams.get("rental3Cnt"));	
	    parameter.put("event1_item_nm", objParams.get("event1Nm"));	
	    parameter.put("event2_item_nm", objParams.get("event2Nm"));	
	    parameter.put("event3_item_nm", objParams.get("event3Nm"));	
	    parameter.put("event1_cnt", objParams.get("event1Cnt"));	
	    parameter.put("event2_cnt", objParams.get("event2Cnt"));	
	    parameter.put("event3_cnt", objParams.get("event3Cnt"));
	    parameter.put("spa_item", objParams.get("spaProdUid"));
	    parameter.put("water_item", objParams.get("waterProdUid"));
	    parameter.put("complex_item", objParams.get("complexProdUid")); //DB異붽�                 
	    parameter.put("rental1_item", objParams.get("rental1ProdUid"));
	    parameter.put("rental2_item", objParams.get("rental2ProdUid"));
	    parameter.put("rental3_item", objParams.get("rental3ProdUid"));
	    parameter.put("event1_item", objParams.get("event1ProdUid"));
	    parameter.put("event2_item", objParams.get("event2ProdUid"));
	    parameter.put("event3_item", objParams.get("event3ProdUid"));
		parameter.put("itemSum0Cnt", objParams.get("itemSum0Cnt"));
		parameter.put("itemSum1Cnt", objParams.get("itemSum1Cnt"));
		parameter.put("itemSum2Cnt", objParams.get("itemSum2Cnt"));	
		parameter.put("pg_result", pgResult.get("UID"));
		parameter.put("cancel_seq", "");
		parameter.put("payment_date", new Date());
		parameter.put("order_tp", objParams.get("itemType")); 
		
		String resultVal = frontReservationService.reserVationIns(parameter);
		
		return resultVal;
	}
	
	//url 泥댄겕�븯�뿬 �꽭�뀡 蹂�寃�
	public String checkURL(HttpServletRequest request, HttpSession session){
		String pointUrl = (String) session.getAttribute("POINT_URL");
		if(request.getHeader("REFERER") != null){
    		String beforeUrl = request.getHeader("REFERER");
    		//http://localhost:8083/goyang/index.af
    		int indexof = beforeUrl.indexOf(pointUrl);
    		if(beforeUrl.indexOf(pointUrl) != -1){
    			//�룷�븿
    		}else{
    			//�룷�븿�븘�떂
    			String[] aa = beforeUrl.split("/");
    			
    			String a1 = aa[3] + "/" + aa[4];
    			Map pointInfo = super.getPointInfo(a1);
    			//session.removeAttribute("POINT_CODE");
    			//session.removeAttribute("POINT_URL");
    			session.setAttribute("POINT_CODE", pointInfo.get("CODE_ID"));
    			session.setAttribute("POINT_URL", pointInfo.get("CODE_URL"));
    		}
    	}
		return null;
	}
	
	
	//�떎�떆媛� 怨꾩쥖�씠泥� 痍⑥냼 愿��젴
	public String onPaymentCashCancelAction(Map cancelParams) throws Exception{
		
		String html = "예약취소가 정상적으로 되었습니다.";

		//Header遺� Data --------------------------------------------------
		String EncType				= "2" ;												// 0: �븫�솕�븞�븿, 1:ssl, 2: seed
		String Version				= "0603" ;											// �쟾臾몃쾭�쟾
		String Type					= "00" ;											// 援щ텇
		String Resend				= "0" ;												// �쟾�넚援щ텇 : 0 : 泥섏쓬,  2: �옱�쟾�넚
		String RequestDate		= new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()); // �슂泥��씪�옄 :
		String KeyInType			= "K" ;												// KeyInType �뿬遺� : S : Swap, K: KeyInType
		String LineType				= "1" ;												// lineType 0 : offline, 1:internet, 2:Mobile
		String ApprovalCount		= "1"	;											// 蹂듯빀�듅�씤媛��닔
		String GoodType				= "0" ;												// �젣�뭹援щ텇 0 : �떎臾�, 1 : �뵒吏��꽭
		String HeadFiller			= ""	;											// �삁鍮�

	// Header (�엯�젰媛� (*) �븘�닔�빆紐�)--------------------------------------------------
		String StoreId				= cancelParams.get("storeid").toString() ;					// *�긽�젏�븘�씠�뵒
		String OrderNumber			= "";												// 二쇰Ц踰덊샇
		String UserName				= "";												// 二쇰Ц�옄紐�
		String IdNum				= "";												// 二쇰�쇰쾲�샇 or �궗�뾽�옄踰덊샇
		String Email				= "";												// email
		String GoodName				= "";												// �젣�뭹紐�
		String PhoneNo				= "";												// �쑕���룿踰덊샇
	//Header end -------------------------------------------------------------------

	//Data Default------------------------------------------------------------------
		String ApprovalType	  = cancelParams.get("authty").toString()	;// �듅�씤援щ텇 肄붾뱶
		String TrNo			  = cancelParams.get("trno").toString()   ;// 嫄곕옒踰덊샇
		String Canc_amt		  = cancelParams.get("canc_amt").toString();	// 痍⑥냼湲덉븸
		String Canc_seq		  = cancelParams.get("canc_seq").toString();	// 痍⑥냼�씪�젴踰덊샇
		String Canc_type	  = cancelParams.get("canc_type").toString();	// 痍⑥냼�쑀�삎 0 :嫄곕옒踰덊샇痍⑥냼 1: 二쇰Ц踰덊샇痍⑥냼 3:遺�遺꾩랬�냼		

	// Server濡� 遺��꽣 �쓳�떟�씠 �뾾�쓣�떆 �옄泥댁쓳�떟
		String		rApprovalType    		= "2011"					; // �듅�씤援щ텇
		String		rACTransactionNo    	= TrNo						; // 嫄곕옒踰덊샇
		String		rACStatus           	= "X"						; // �삤瑜섍뎄遺� :�듅�씤 X:嫄곗젅
		String		rACTradeDate        	= RequestDate.substring(0,8); // 嫄곕옒 媛쒖떆 �씪�옄(YYYYMMDD)
		String		rACTradeTime        	= RequestDate.substring(8,14); // 嫄곕옒 媛쒖떆 �떆媛�(HHMMSS)
		String		rACAcctSele         	= ""						; // 怨꾩쥖�씠泥� 援щ텇 -	1:Dacom, 2:Pop Banking,	3:�떎�떆媛꾧퀎醫뚯씠泥� 4: �듅�씤�삎怨꾩쥖�씠泥�
		String		rACFeeSele          	= ""						; // �꽑/�썑遺덉젣援щ텇 -	1:�꽑遺�,	2:�썑遺�
		String		rACInjaName         	= ""						; // �씤�옄紐�(�넻�옣�씤�뇙硫붿꽭吏�-�긽�젏紐�)
		String		rACPareBankCode     	= ""						; // �엯湲덈え怨꾩쥖肄붾뱶
		String		rACPareAcctNo       	= ""						; // �엯湲덈え怨꾩쥖踰덊샇
		String		rACCustBankCode     	= ""						; // 異쒓툑紐④퀎醫뚯퐫�뱶
		String		rACCustAcctNo       	= ""						; // 異쒓툑紐④퀎醫뚮쾲�샇
		String		rACAmount	       		= ""						; // 湲덉븸	(寃곗젣���긽湲덉븸)
		String		rACBankTransactionNo	= ""						; // ���뻾嫄곕옒踰덊샇
		String		rACIpgumNm          	= ""						; // �엯湲덉옄紐�
		String		rACBankFee          	= "0"						; // 怨꾩쥖�씠泥� �닔�닔猷�
		String		rACBankAmount       	= ""						; // 珥앷껐�젣湲덉븸(寃곗젣���긽湲덉븸+ �닔�닔猷�
		String		rACBankRespCode     	= "9999"					; // �삤瑜섏퐫�뱶
		String		rACMessage1         	= "취소거절"				; // �삤瑜� message 1
		String		rACMessage2         	= "C잠시후재시도"			; // �삤瑜� message 2
		String		rACFiller           	= ""						; // �삁鍮�
		try 
		{
			KSPayApprovalCancelBean ipg = new KSPayApprovalCancelBean(config.getProperty("pg.ip"), 29991); 

			ipg.HeadMessage(EncType, Version, Type, Resend, RequestDate, StoreId, OrderNumber, UserName, IdNum, Email, 
							GoodType, GoodName, KeyInType, LineType, PhoneNo, ApprovalCount, HeadFiller);
			
			if (Canc_type.equals("3")){
				ipg.CancelDataMessage(ApprovalType, Canc_type, TrNo, "", "", ipg.format(Canc_amt,9,'9')+ipg.format(Canc_seq,2,'9'),"","");
			}else{ 
				ipg.CancelDataMessage(ApprovalType, "0", TrNo, "", "", "","","");
			}

			if(ipg.SendSocket("1")) {
				rApprovalType			=	ipg.ApprovalType		[0];
				rACTransactionNo	    =	ipg.ACTransactionNo		[0];   // 嫄곕옒踰덊샇
				rACStatus				=	ipg.ACStatus			[0];   // �삤瑜섍뎄遺� :�듅�씤 X:嫄곗젅
				rACTradeDate		    =	ipg.ACTradeDate			[0];   // 嫄곕옒 媛쒖떆 �씪�옄(YYYYMMDD)
				rACTradeTime		    =	ipg.ACTradeTime			[0];   // 嫄곕옒 媛쒖떆 �떆媛�(HHMMSS)
				rACAcctSele		    	=	ipg.ACAcctSele			[0];   // 怨꾩쥖�씠泥� 援щ텇 -	1:Dacom, 2:Pop Banking,	3:Scrapping 怨꾩쥖�씠泥�, 4:�듅�씤�삎怨꾩쥖�씠泥�, 5:湲덇껐�썝怨꾩쥖�씠泥�    
				rACFeeSele				=	ipg.ACFeeSele			[0];   // �꽑/�썑遺덉젣援щ텇 -	1:�꽑遺�,	2:�썑遺�
				rACInjaName		    	=	ipg.ACInjaName			[0];   // �씤�옄紐�(�넻�옣�씤�뇙硫붿꽭吏�-�긽�젏紐�)
				rACPareBankCode	    	=	ipg.ACPareBankCode		[0];   // �엯湲덈え怨꾩쥖肄붾뱶
				rACPareAcctNo			=	ipg.ACPareAcctNo		[0];   // �엯湲덈え怨꾩쥖踰덊샇
				rACCustBankCode	    	=	ipg.ACCustBankCode		[0];   // 異쒓툑紐④퀎醫뚯퐫�뱶
				rACCustAcctNo			=	ipg.ACCustAcctNo		[0];   // 異쒓툑紐④퀎醫뚮쾲�샇
				rACAmount				=	ipg.ACAmount			[0];   // 湲덉븸	(寃곗젣���긽湲덉븸)
				rACBankTransactionNo  	=	ipg.ACBankTransactionNo	[0];   // ���뻾嫄곕옒踰덊샇
				rACIpgumNm				=	ipg.ACIpgumNm			[0];   // �엯湲덉옄紐�
				rACBankFee				=	ipg.ACBankFee			[0];   // 怨꾩쥖�씠泥� �닔�닔猷�
				rACBankAmount			=	ipg.ACBankAmount		[0];   // 珥앷껐�젣湲덉븸(寃곗젣���긽湲덉븸+ �닔�닔猷�
				rACBankRespCode	    	=	ipg.ACBankRespCode		[0];   // �삤瑜섏퐫�뱶
				rACMessage1		    	=	ipg.ACMessage1			[0];   // �삤瑜� message 1
				rACMessage2		    	=	ipg.ACMessage2			[0];   // �삤瑜� message 2
				rACFiller				=	ipg.ACFiller			[0];   // �삁鍮�
				
				if("O".equals(rACStatus)){
					
					Map parameter = new HashMap();	
	                parameter.put("approval_type",rApprovalType);
	                parameter.put("ac_transactionno",rACTransactionNo);
	                parameter.put("ac_status",rACStatus);
	                parameter.put("ac_tradedate",rACTradeDate);
	                parameter.put("ac_tradetime",rACTradeTime);
	                parameter.put("ac_amount",rACAmount);
	                parameter.put("ac_message1",rACMessage1);
	                parameter.put("ac_message2",rACMessage2);
	                
	                String result = frontReservationService.pgCancelIns(parameter);
	                
	                if("PGCANCELOK".equals(result)){
	                	html = "예약취소가 정상적으로 이루어 졌습니다.";	
	                	
	                }else{
	                	html = "예약취소중 에러가 발생하였습니다.(DB INS)";
	                }
	                
				}else{
					html = rACMessage1+"\n "+rACMessage2;
				}				
			}
		}
		catch(Exception e) {
			rACMessage2			= "P잠시후재시도("+e.toString()+")";	// 硫붿떆吏�2
		} // end of catch
	
		return html;		
	}
	
	//SSGPAY - SSG MONEY 痍⑥냼 愿��젴(�쐞�빟湲�)
	public String ssgPanaltyAction(Map cancelParams) throws Exception{
		
		String html = "예약취소가 정상적으로 되었습니다.";

		//Header遺� Data --------------------------------------------------
		String EncType				= "2" ;												// 0: �븫�솕�븞�븿, 1:ssl, 2: seed
		String Version				= "0603" ;											// �쟾臾몃쾭�쟾
		String Type					= "00" ;											// 援щ텇
		String Resend				= "0" ;												// �쟾�넚援щ텇 : 0 : 泥섏쓬,  2: �옱�쟾�넚
		String RequestDate		= new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()); // �슂泥��씪�옄 :
		String KeyInType			= "K" ;												// KeyInType �뿬遺� : S : Swap, K: KeyInType
		String LineType				= "1" ;												// lineType 0 : offline, 1:internet, 2:Mobile
		String ApprovalCount		= "1"	;											// 蹂듯빀�듅�씤媛��닔
		String GoodType				= "0" ;												// �젣�뭹援щ텇 0 : �떎臾�, 1 : �뵒吏��꽭
		String HeadFiller			= ""	;											// �삁鍮�

	// Header (�엯�젰媛� (*) �븘�닔�빆紐�)--------------------------------------------------
		String StoreId				= cancelParams.get("storeid").toString() ;					// *�긽�젏�븘�씠�뵒
		String OrderNumber			= "";												// 二쇰Ц踰덊샇
		String UserName				= "";												// 二쇰Ц�옄紐�
		String IdNum				= "";												// 二쇰�쇰쾲�샇 or �궗�뾽�옄踰덊샇
		String Email				= "";												// email
		String GoodName				= "";												// �젣�뭹紐�
		String PhoneNo				= "";												// �쑕���룿踰덊샇
	//Header end -------------------------------------------------------------------

	//Data Default------------------------------------------------------------------
		String ApprovalType	  = cancelParams.get("authty").toString()	;// �듅�씤援щ텇 肄붾뱶
		String TrNo			  = cancelParams.get("trno").toString()   ;// 嫄곕옒踰덊샇
		String Canc_amt		  = cancelParams.get("canc_amt").toString();	// 痍⑥냼湲덉븸
		String Canc_seq		  = cancelParams.get("canc_seq").toString();	// 痍⑥냼�씪�젴踰덊샇
		String Canc_type	  = cancelParams.get("canc_type").toString();	// 痍⑥냼�쑀�삎 0 :嫄곕옒踰덊샇痍⑥냼 1: 二쇰Ц踰덊샇痍⑥냼 3:遺�遺꾩랬�냼		

	// Server濡� 遺��꽣 �쓳�떟�씠 �뾾�쓣�떆 �옄泥댁쓳�떟
		String rApprovalType   = "4111";                           //�듅�씤援щ텇                     
		String rPTransactionNo = "";                               //嫄곕옒踰덊샇                     
		String rPStatus        = "X";                              //�긽�깭 O : �듅�씤 , X : 嫄곗젅     
		String rPTradeDate     = "";                               //嫄곕옒�씪�옄                     
		String rPTradeTime     = "";                               //嫄곕옒�떆媛�                     
		String rPIssCode       = "00";                             //諛쒓툒�궗肄붾뱶                   
		String rPAuthNo        = "9999";                           //�듅�씤踰덊샇 or 嫄곗젅�떆 �삤瑜섏퐫�뱶  
		String rPMessage1      = "취소거절";                       	//硫붿떆吏�1                      
		String rPMessage2      = "C잠시후재시도";                 	 	//硫붿떆吏�2                      
		String rPPoint1        = "000000000000";                   //嫄곕옒�룷�씤�듃                   
		String rPPoint2        = "000000000000";                   //媛��슜�룷�씤�듃                   
		String rPPoint3        = "000000000000";                   //�늻�쟻�룷�씤�듃                   
		String rPPoint4        = "000000000000";                   //媛�留뱀젏�룷�씤�듃                 
		String rPMerchantNo    = "";                               //媛�留뱀젏踰덊샇                   
		String rPNotice1       = "";                               //                              
		String rPNotice2       = "";                               //                              
		String rPNotice3       = "";                               //                              
		String rPNotice4       = "";                               //                     
		String rPSvcsele       = "";                               //                     
		String rPFiller        = "";                               //           

		try 
		{
			KSPayApprovalCancelBean ipg = new KSPayApprovalCancelBean(config.getProperty("pg.ip"), 29991); 

			ipg.HeadMessage(EncType, Version, Type, Resend, RequestDate, StoreId, OrderNumber, UserName, IdNum, Email, 
							GoodType, GoodName, KeyInType, LineType, PhoneNo, ApprovalCount, HeadFiller);
			
			if (Canc_type.equals("3")){
				ipg.CancelDataMessage(ApprovalType, Canc_type, TrNo, "", "", ipg.format(Canc_amt,9,'9')+ipg.format(Canc_seq,2,'9'),"","");
			}else{ 
				ipg.CancelDataMessage(ApprovalType, "0", TrNo, "", "", "","","");
			}

			if(ipg.SendSocket("1")) {
				rApprovalType	= ipg.ApprovalType[0];
				rPTransactionNo = ipg.PTransactionNo[0];   // 嫄곕옒踰덊샇
				rPStatus        = ipg.PStatus[0];          // �긽�깭 O : �듅�씤, X : 嫄곗젅
				rPTradeDate     = ipg.PTradeDate[0];       // 嫄곕옒�씪�옄
				rPTradeTime     = ipg.PTradeTime[0];       // 嫄곕옒�떆媛�
				rPIssCode       = ipg.PIssCode[0];         // 嫄곕옒�씪�옄
				rPAuthNo        = ipg.PAuthNo[0];          // 嫄곕옒�떆媛�
				rPMessage1      = ipg.PMessage1[0];        // 硫붿떆吏�1		
				rPMessage2      = ipg.PMessage2[0];        // 硫붿떆吏�2
				rPPoint1        = ipg.PPoint1[0];          // Point1
				rPPoint2        = ipg.PPoint2[0];          // Point2
				rPPoint3        = ipg.PPoint3[0];          // Point3
				rPPoint4        = ipg.PPoint4[0];          // Point4
				rPMerchantNo    = ipg.PMerchantNo[0];      // 媛�留뱀젏踰덊샇
				rPNotice1       = ipg.PNotice1[0];         // PNotice1
				rPNotice2       = ipg.PNotice2[0];         // PNotice1
				rPNotice3       = ipg.PNotice3[0];         // PNotice1
				rPNotice4       = ipg.PNotice4[0];         // PNotice1
				rPFiller        = ipg.PFiller[0];          // �삁鍮�
				
				if("O".equals(rPStatus)){
					
					Map parameter = new HashMap();	
	                parameter.put("approval_type",rApprovalType);
	                parameter.put("ac_transactionno",rPTransactionNo);
	                parameter.put("ac_status",rPStatus);
	                parameter.put("ac_tradedate",rPTradeDate);
	                parameter.put("ac_tradetime",rPTradeTime);
	                parameter.put("ac_amount",Canc_amt);
	                parameter.put("ac_message1",rPMessage1);
	                parameter.put("ac_message2",rPMessage2);
	                parameter.put("reserve_uid",cancelParams.get("uid"));
	                parameter.put("reserve_state",cancelParams.get("reserve_state"));
	                parameter.put("penalty_amt",cancelParams.get("penalty_amt"));
	                
	                String result = frontReservationService.pgPanaltyIns(parameter);
	                
	                if("PGCANCELOK".equals(result)){
	                	html = "위약금환불취소가 정상적으로 이루어 졌습니다.";
							
	                }else{
	                	html = "위약금환불취소중 에러가 발생하였습니다.(DB INS)";
	                }
	                
				}else{
					html = rPMessage1+"\n "+rPMessage2;
				}				
			}
		}
		catch(Exception e) {
			rPMessage2			= "P잠시후재시도("+e.toString()+")";	// 硫붿떆吏�2
		} // end of catch
	
		return html;		
	}
	
	//SSGPAY - SSG MONEY 痍⑥냼 愿��젴
	public String ssgCancelAction(Map cancelParams, String isOnPayment, String exceptionMessage) throws Exception{
		
		String html = "예약취소가 정상적으로 되었습니다.";

		//Header遺� Data --------------------------------------------------
		String EncType				= "2" ;												// 0: �븫�솕�븞�븿, 1:ssl, 2: seed
		String Version				= "0603" ;											// �쟾臾몃쾭�쟾
		String Type					= "00" ;											// 援щ텇
		String Resend				= "0" ;												// �쟾�넚援щ텇 : 0 : 泥섏쓬,  2: �옱�쟾�넚
		String RequestDate		= new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()); // �슂泥��씪�옄 :
		String KeyInType			= "K" ;												// KeyInType �뿬遺� : S : Swap, K: KeyInType
		String LineType				= "1" ;												// lineType 0 : offline, 1:internet, 2:Mobile
		String ApprovalCount		= "1"	;											// 蹂듯빀�듅�씤媛��닔
		String GoodType				= "0" ;												// �젣�뭹援щ텇 0 : �떎臾�, 1 : �뵒吏��꽭
		String HeadFiller			= ""	;											// �삁鍮�

	// Header (�엯�젰媛� (*) �븘�닔�빆紐�)--------------------------------------------------
		String StoreId				= cancelParams.get("storeid").toString() ;					// *�긽�젏�븘�씠�뵒
		String OrderNumber			= "";												// 二쇰Ц踰덊샇
		String UserName				= "";												// 二쇰Ц�옄紐�
		String IdNum				= "";												// 二쇰�쇰쾲�샇 or �궗�뾽�옄踰덊샇
		String Email				= "";												// email
		String GoodName				= "";												// �젣�뭹紐�
		String PhoneNo				= "";												// �쑕���룿踰덊샇
	//Header end -------------------------------------------------------------------

	//Data Default------------------------------------------------------------------
		String ApprovalType	  = cancelParams.get("authty").toString()	;// �듅�씤援щ텇 肄붾뱶
		String TrNo			  = cancelParams.get("trno").toString()   ;// 嫄곕옒踰덊샇
		String Canc_amt		  = cancelParams.get("canc_amt").toString();	// 痍⑥냼湲덉븸
		String Canc_seq		  = cancelParams.get("canc_seq").toString();	// 痍⑥냼�씪�젴踰덊샇
		String Canc_type	  = cancelParams.get("canc_type").toString();	// 痍⑥냼�쑀�삎 0 :嫄곕옒踰덊샇痍⑥냼 1: 二쇰Ц踰덊샇痍⑥냼 3:遺�遺꾩랬�냼		

	// Server濡� 遺��꽣 �쓳�떟�씠 �뾾�쓣�떆 �옄泥댁쓳�떟
		String rApprovalType   = "4111";                           //�듅�씤援щ텇                     
		String rPTransactionNo = "";                               //嫄곕옒踰덊샇                     
		String rPStatus        = "X";                              //�긽�깭 O : �듅�씤 , X : 嫄곗젅     
		String rPTradeDate     = "";                               //嫄곕옒�씪�옄                     
		String rPTradeTime     = "";                               //嫄곕옒�떆媛�                     
		String rPIssCode       = "00";                             //諛쒓툒�궗肄붾뱶                   
		String rPAuthNo        = "9999";                           //�듅�씤踰덊샇 or 嫄곗젅�떆 �삤瑜섏퐫�뱶  
		String rPMessage1      = "취소거절";                       	//硫붿떆吏�1                      
		String rPMessage2      = "C잠시후재시도";                 	 	//硫붿떆吏�2                      
		String rPPoint1        = "000000000000";                   //嫄곕옒�룷�씤�듃                   
		String rPPoint2        = "000000000000";                   //媛��슜�룷�씤�듃                   
		String rPPoint3        = "000000000000";                   //�늻�쟻�룷�씤�듃                   
		String rPPoint4        = "000000000000";                   //媛�留뱀젏�룷�씤�듃                 
		String rPMerchantNo    = "";                               //媛�留뱀젏踰덊샇                   
		String rPNotice1       = "";                               //                              
		String rPNotice2       = "";                               //                              
		String rPNotice3       = "";                               //                              
		String rPNotice4       = "";                               //                     
		String rPSvcsele       = "";                               //                     
		String rPFiller        = "";                               //           

		try 
		{
			KSPayApprovalCancelBean ipg = new KSPayApprovalCancelBean(config.getProperty("pg.ip"), 29991); 

			ipg.HeadMessage(EncType, Version, Type, Resend, RequestDate, StoreId, OrderNumber, UserName, IdNum, Email, 
							GoodType, GoodName, KeyInType, LineType, PhoneNo, ApprovalCount, HeadFiller);
			
			if (Canc_type.equals("3")){
				ipg.CancelDataMessage(ApprovalType, Canc_type, TrNo, "", "", ipg.format(Canc_amt,9,'9')+ipg.format(Canc_seq,2,'9'),"","");
			}else{ 
				ipg.CancelDataMessage(ApprovalType, "0", TrNo, "", "", "","","");
			}

			if(ipg.SendSocket("1")) {
				rApprovalType	= ipg.ApprovalType[0];
				rPTransactionNo = ipg.PTransactionNo[0];   // 嫄곕옒踰덊샇
				rPStatus        = ipg.PStatus[0];          // �긽�깭 O : �듅�씤, X : 嫄곗젅
				rPTradeDate     = ipg.PTradeDate[0];       // 嫄곕옒�씪�옄
				rPTradeTime     = ipg.PTradeTime[0];       // 嫄곕옒�떆媛�
				rPIssCode       = ipg.PIssCode[0];         // 嫄곕옒�씪�옄
				rPAuthNo        = ipg.PAuthNo[0];          // 嫄곕옒�떆媛�
				rPMessage1      = ipg.PMessage1[0];        // 硫붿떆吏�1		
				rPMessage2      = ipg.PMessage2[0];        // 硫붿떆吏�2
				rPPoint1        = ipg.PPoint1[0];          // Point1
				rPPoint2        = ipg.PPoint2[0];          // Point2
				rPPoint3        = ipg.PPoint3[0];          // Point3
				rPPoint4        = ipg.PPoint4[0];          // Point4
				rPMerchantNo    = ipg.PMerchantNo[0];      // 媛�留뱀젏踰덊샇
				rPNotice1       = ipg.PNotice1[0];         // PNotice1
				rPNotice2       = ipg.PNotice2[0];         // PNotice1
				rPNotice3       = ipg.PNotice3[0];         // PNotice1
				rPNotice4       = ipg.PNotice4[0];         // PNotice1
				rPFiller        = ipg.PFiller[0];          // �삁鍮�
				
				if("O".equals(rPStatus)){
					
					Map parameter = new HashMap();	
	                parameter.put("approval_type",rApprovalType);
	                parameter.put("ac_transactionno",rPTransactionNo);
	                parameter.put("ac_status",rPStatus);
	                parameter.put("ac_tradedate",rPTradeDate);
	                parameter.put("ac_tradetime",rPTradeTime);
	                parameter.put("ac_amount",Canc_amt);
	                parameter.put("ac_message1",rPMessage1);
	                if(isOnPayment.trim().equals("Y")){
	                	parameter.put("ac_message2",exceptionMessage);
	                }else{
	                	parameter.put("ac_message2",rPMessage2);
	                }
	                parameter.put("reserve_uid",cancelParams.get("uid"));
	                parameter.put("reserve_state","CANCEL");
	                parameter.put("penalty_amt",cancelParams.get("penalty_amt"));
	                
	                String result = frontReservationService.pgCancelIns(parameter);
	                
	                if("PGCANCELOK".equals(result)){
	                	if(!isOnPayment.trim().equals("Y")){
	                		html = "예약취소가 정상적으로 이루어 졌습니다.";	
		                	
		        			//�삁�빟痍⑥냼臾몄옄 諛쒖넚
		        			//String contents = "[�븘荑좎븘�븘�뱶]�삩�씪�씤 �삁�빟痍⑥냼(�삁�빟踰덊샇:"+request.getParameter("ordernum")+",�삁�빟�씪:"+request.getParameter("reserveday")+")";
							Map smsParam = new HashMap();
							smsParam.put("point_code", "POINT01");
							smsParam.put("sms_type", "CANCEL");
							
							Map smsTemplte = commonService.getSmsTemplete(smsParam);
							String contents = smsTemplte.get("SMS_CONTENT").toString();
							contents = contents.replace("{지점}",cancelParams.get("pointNm").toString());//吏��젏 移섑솚
							contents = contents.replace("{번호}",cancelParams.get("ordernum").toString());//�삁�빟踰덊샇 移섑솚
							contents = contents.replace("{예약일}",cancelParams.get("reserveday").toString());//�삁�빟�씪 移섑솚		        			
		        			
		        			Map params = new HashMap();
		        			params.put("recipient_num", cancelParams.get("phoneNo")); // �닔�떊踰덊샇
		        			//param.put("subject", "[�븘荑좎븘�븘�뱶]�삁�빟痍⑥냼臾몄옄�엯�땲�떎."); // LMS�씪寃쎌슦 �젣紐⑹쓣 異붽� �븷 �닔 �엳�떎.
		        			params.put("content", contents); // �궡�슜 (SMS=88Byte, LMS=2000Byte)		
		        			//params.put("callback", "031-8072-8800"); // 諛쒖떊踰덊샇
		        			params.put("callback", config.getProperty("sms.tel.number."+cancelParams.get("pointCode").toString())); // 諛쒖떊踰덊샇
		        			
		        			if(!smsService.sendSms(params)){
		        				html = "예약취소중 에러가 발생하였습니다.(문자)";
		        			}else{
								//SMS 諛쒖넚 �씠�젰 �벑濡�
								smsParam.put("sms_uid", smsTemplte.get("SMS_UID"));
								smsParam.put("mem_id", cancelParams.get("mem_id"));
								smsParam.put("custom_nm", cancelParams.get("custom_nm"));
								smsParam.put("custom_mobile", cancelParams.get("phoneNo"));
								smsParam.put("ins_ip", cancelParams.get("ins_ip"));
								smsParam.put("ins_id", cancelParams.get("mem_id")); 
								smsParam.put("send_status", "OK");	
								smsParam.put("bigo", "CASH_RESERVE_CANCEL");
							
								String smsResult = commonService.insSmsSend(smsParam);
								if("ERROR".equals(smsResult)){
									html = "처리중 에러가 발생하였습니다.(문자이력등록)1";
								}
		        			}
		        			
							//硫붿씪諛쒖넚 #####################################
		        			int intUid = Integer.parseInt(cancelParams.get("uid").toString());
		        			Map getReserveInfo = frontReservationService.getReserveInfo(intUid); //�삁�빟�젙蹂� 媛��졇�삤湲�
		        					        			
		        			if(getReserveInfo.get("SPA_ITEM") == null || getReserveInfo.get("SPA_ITEM").equals("")) {
		        				
		        			} else {
		        				
		        				Map params_set_uid = new HashMap();
		        				
		        				int people = (int) getReserveInfo.get("ADULT_SUM") + (int) getReserveInfo.get("CHILD_SUM");
		        				
		        				params_set_uid.put("set_uid", getReserveInfo.get("SPA_ITEM"));
		        				params_set_uid.put("quantity", people);
		        				
		        				frontReservationService.itemQtyUpdMinus(params_set_uid);		        				
		        				
		        			}

		        			if(getReserveInfo.get("WATER_ITEM") == null || getReserveInfo.get("WATER_ITEM").equals("")) {
		        				
		        			} else {
		        				
		        				Map params_set_uid = new HashMap();
		        				
		        				int people = (int) getReserveInfo.get("ADULT_SUM") + (int) getReserveInfo.get("CHILD_SUM");
		        				
		        				params_set_uid.put("set_uid", getReserveInfo.get("WATER_ITEM"));
		        				params_set_uid.put("quantity", people);
		        				
		        				frontReservationService.itemQtyUpdMinus(params_set_uid);		        				
		        				
		        			}

		        			if(getReserveInfo.get("COMPLEX_ITEM") == null || getReserveInfo.get("COMPLEX_ITEM").equals("")) {
		        				
		        			} else {
		        				
		        				Map params_set_uid = new HashMap();
		        				
		        				int people = (int) getReserveInfo.get("ADULT_SUM") + (int) getReserveInfo.get("CHILD_SUM");
		        				
		        				params_set_uid.put("set_uid", getReserveInfo.get("COMPLEX_ITEM"));
		        				params_set_uid.put("quantity", people);
		        				
		        				frontReservationService.itemQtyUpdMinus(params_set_uid);		        				
		        				
		        			}
		        			
		        			
							Map emailParam = new HashMap();
							emailParam.put("email_uid", "3");
							Map joinEmail = adminEmailTempletService.adminEmailTempletDetail(emailParam);
							
							String realPath = cancelParams.get("realPath").toString();
							//String joinHtml = super.getHTMfile(realPath+"/reservation_complete.html");
							
							String joinHtml = super.getHTMfile(realPath+"/3");						
							//String joinHtml = super.getHTMfile(realPath+"/reservation_cancel.html");	        			

							String nowTime = AquaDateUtil.getToday();
							nowTime = nowTime.substring(0, 4)+"."+nowTime.substring(4, 6)+"."+nowTime.substring(6, 8);						
							joinHtml = joinHtml.replace("{{#NOW#}}",nowTime); // �쁽�옱�떆媛� 移섑솚
							joinHtml = joinHtml.replace("{{#NAME#}}",getReserveInfo.get("MEM_NM").toString());//�씠由� 移섑솚
							joinHtml = joinHtml.replace("{{#RESERVENUM#}}",getReserveInfo.get("ORDER_NUM").toString());//�삁�빟踰덊샇 移섑솚
							joinHtml = joinHtml.replace("{{#POINTNM#}}",getReserveInfo.get("POINT_NM").toString());//吏��젏 移섑솚
							joinHtml = joinHtml.replace("{{#RESERVEDAY#}}",getReserveInfo.get("RESERVE_DATE").toString());//�삁�빟�씪移섑솚
							joinHtml = joinHtml.replace("{{#GOODS#}}",getReserveInfo.get("ORDER_NM").toString());//�긽�뭹紐낆튂�솚
							
							int cnt = Integer.parseInt(getReserveInfo.get("ADULT_SUM").toString()) + Integer.parseInt(getReserveInfo.get("CHILD_SUM").toString());
							joinHtml = joinHtml.replace("{{#CNT#}}",String.valueOf(cnt));//�씤�썝�닔移섑솚
							
							Map pgResultInfo = frontReservationService.pgResultInfo(getReserveInfo.get("PG_RESULT").toString());//PG�젙蹂닿��졇�삤湲�						
							int compareVal = Integer.parseInt(pgResultInfo.get("TR_NO").toString().substring(0,1));				
							String r_TYPE = "";		
							switch (compareVal) {
							case 1: r_TYPE = "카드";
								break;
							case 2: r_TYPE = "실시간계좌이체";
								break;
							case 4: r_TYPE = "SSG PAY";
								break;							
							}
							joinHtml = joinHtml.replace("{{#PAYTYPE#}}",r_TYPE);//寃곗젣�닔�떒移섑솚
							
							String approvaldate = pgResultInfo.get("TR_DDT").toString();
							approvaldate = approvaldate.substring(0, 4)+"."+approvaldate.substring(4, 6)+"."+approvaldate.substring(6, 8);
							joinHtml = joinHtml.replace("{{#APPROVALDATE#}}",approvaldate);//�듅�씤�씪�떆移섑솚
							joinHtml = joinHtml.replace("{{#PRICE#}}",getReserveInfo.get("PAYMENT_PRICE").toString());//寃곗젣湲덉븸移섑솚   
							
							String canceldate = parameter.get("ac_tradedate").toString();
							canceldate = canceldate.substring(0, 4)+"."+canceldate.substring(4, 6)+"."+canceldate.substring(6, 8);
							joinHtml = joinHtml.replace("{{#CANCELDATE#}}",canceldate);//痍⑥냼�씪�떆移섑솚
							int intcancel = Integer.parseInt(parameter.get("ac_amount").toString());
							joinHtml = joinHtml.replace("{{#CANCELPRICE#}}", String.valueOf(intcancel));//痍⑥냼湲덉븸移섑솚   
							joinHtml = joinHtml.replace("{{#PENALTIES#}}",getReserveInfo.get("PANALTY_PRICE").toString());//�쐞�빟湲덉튂�솚
							String reHtml= joinHtml.replace("{{#REFUND#}}",getReserveInfo.get("REFUND").toString());

							//boolean booleanresult =	mailService.sendmail(mailSender, "aquafield@shinsegae.com", "�븘荑좎븘�븘�뱶", getReserveInfo.get("MEM_ID").toString(), getReserveInfo.get("MEM_NM").toString(), "[�븘荑좎븘�븘�뱶]�삁�빟痍⑥냼硫붿씪�엯�땲�떎.", reHtml);
							boolean booleanresult =	mailService.sendmail(mailSender, joinEmail.get("FROM_EMAIL").toString(), joinEmail.get("FORM_EMAIL_NM").toString(), getReserveInfo.get("MEM_ID").toString(), getReserveInfo.get("MEM_NM").toString(), joinEmail.get("EMAIL_TITLE").toString(), reHtml);

							if(!booleanresult){
								//html = "처리중 에러가 발생하였습니다.(이메일)";
							}else{
								//EMAIL 諛쒖넚 �씠�젰 �벑濡�
								emailParam.put("point_code", "POINT01");
								emailParam.put("email_uid", "3");
								emailParam.put("mem_id", getReserveInfo.get("MEM_ID"));
								emailParam.put("custom_nm", getReserveInfo.get("MEM_NM"));
								emailParam.put("custom_email", getReserveInfo.get("MEM_ID"));
								emailParam.put("ins_ip", "");
								emailParam.put("ins_id", getReserveInfo.get("MEM_ID")); 
								emailParam.put("send_status", "OK");

								String smsResult = commonService.insEmailSend(emailParam);
								if("ERROR".equals(emailParam)){
									html = "처리중 에러가 발생하였습니다.(이메일 발송 등록)";
								}						
							}						
							//###########################################		        			

			                //########## 遺�遺꾩랬�냼 以� �깉�삁�빟 INSERT 愿��젴 ######################################
								int intSpa_ad_Man     = Integer.parseInt(cancelParams.get("spa_ad_Man").toString());
								int intSpa_ad_Women   = Integer.parseInt(cancelParams.get("spa_ad_Women").toString());
								int intSpa_ch_Man     = Integer.parseInt(cancelParams.get("spa_ch_Man").toString());
								int intSpa_ch_Women   = Integer.parseInt(cancelParams.get("spa_ch_Women").toString());
								int intWater_ad_Man   = Integer.parseInt(cancelParams.get("water_ad_Man").toString());
								int intWater_ad_Women = Integer.parseInt(cancelParams.get("water_ad_Women").toString());
								int intWater_ch_Man   = Integer.parseInt(cancelParams.get("water_ch_Man").toString());
								int intWater_ch_Women = Integer.parseInt(cancelParams.get("water_ch_Women").toString());
								int intComplex_ad_Man   = Integer.parseInt(cancelParams.get("complex_ad_Man").toString());
								int intComplex_ad_Women = Integer.parseInt(cancelParams.get("complex_ad_Women").toString());
								int intComplex_ch_Man   = Integer.parseInt(cancelParams.get("complex_ch_Man").toString());
								int intComplex_ch_Women = Integer.parseInt(cancelParams.get("complex_ch_Women").toString());							
								int intSumVisitCnt    = Integer.parseInt(cancelParams.get("sumVisitCnt").toString());
								
								int intTotalCnt = intSpa_ad_Man + intSpa_ad_Women + intSpa_ch_Man + intSpa_ch_Women + intWater_ad_Man + intWater_ad_Women + intWater_ch_Man + intWater_ch_Women
										  + intComplex_ad_Man + intComplex_ad_Women + intComplex_ch_Man + intComplex_ch_Women;
								
								if(intTotalCnt > 0 && intSumVisitCnt > intTotalCnt){
									
									int intAdultSum = intSpa_ad_Man + intSpa_ad_Women + intWater_ad_Man + intWater_ad_Women + intComplex_ad_Man + intComplex_ad_Women;
									int intChildSum = intSpa_ch_Man + intSpa_ch_Women + intWater_ch_Man + intWater_ch_Women + intComplex_ch_Man + intComplex_ch_Women;
									cancelParams.put("adult_sum", intAdultSum);
									cancelParams.put("child_sum", intChildSum);								
									
									String insResult = newReservationIns(cancelParams, getReserveInfo);
									if("ERROR".equals(insResult)){
										html = "부분예약취소중 에러가 발생하였습니다.(NEW RESERVE DB INS)";
									}
								}
			                //########################################################################
	                	} else {
	                		//20180112 �닔�젙以�
	                		logger.debug("결제중에 인원초과로 결제 실패되었을때 SMS");
	                		html = "지정된 수량이 모두 판매되어 예약이 되지 않았습니다.";	
		                	
		        			//�삁�빟痍⑥냼臾몄옄 諛쒖넚
		        			//String contents = "[�븘荑좎븘�븘�뱶]�삩�씪�씤 �삁�빟痍⑥냼(�삁�빟踰덊샇:"+request.getParameter("ordernum")+",�삁�빟�씪:"+request.getParameter("reserveday")+")";
							Map smsParam = new HashMap();
							smsParam.put("point_code", "POINT01");
							smsParam.put("sms_type", "CANCEL");
							
							Map smsTemplte = commonService.getSmsTemplete(smsParam);
							/*String ment = "吏��젙�맂 �닔�웾�씠 紐⑤몢 �뙋留ㅻ릺�뼱 �삁�빟�씠 �릺吏� �븡�븯�뒿�땲�떎.";*/
							String contents = smsTemplte.get("SMS_CONTENT").toString();
							String ment = "지정수량이 모두 판매되어 자동 취소되었습니다.";
							contents = contents.replace("{지점}",cancelParams.get("pointNm").toString() + ment);//�삁�빟踰덊샇 移섑솚
							contents = contents.replace("{번호}",cancelParams.get("ordernum").toString());//�삁�빟踰덊샇 移섑솚
							contents = contents.replace("{예약일}",cancelParams.get("reserveday").toString());//�삁�빟�씪 移섑솚		        			
		        			/*contents += ment;*/
		        			
		        			Map params = new HashMap();
		        			params.put("recipient_num", cancelParams.get("phoneNo")); // �닔�떊踰덊샇
		        			//param.put("subject", "[�븘荑좎븘�븘�뱶]�삁�빟痍⑥냼臾몄옄�엯�땲�떎."); // LMS�씪寃쎌슦 �젣紐⑹쓣 異붽� �븷 �닔 �엳�떎.
		        			params.put("content", contents); // �궡�슜 (SMS=88Byte, LMS=2000Byte)		
		        			//params.put("callback", "031-8072-8800"); // 諛쒖떊踰덊샇
		        			params.put("callback", config.getProperty("sms.tel.number."+cancelParams.get("pointCode").toString())); // 諛쒖떊踰덊샇
		        			
		        			if(!smsService.sendSms(params)){
		        				html = "예약취소중 에러가 발생하였습니다.(문자)";
		        			}else{
								//SMS 諛쒖넚 �씠�젰 �벑濡�
								smsParam.put("sms_uid", smsTemplte.get("SMS_UID"));
								smsParam.put("mem_id", cancelParams.get("mem_id"));
								smsParam.put("custom_nm", cancelParams.get("custom_nm"));
								smsParam.put("custom_mobile", cancelParams.get("phoneNo"));
								smsParam.put("ins_ip", cancelParams.get("ins_ip"));
								smsParam.put("ins_id", cancelParams.get("mem_id")); 
								smsParam.put("send_status", "OK");	
								smsParam.put("bigo", "CASH_RESERVE_CANCEL");
							
								String smsResult = commonService.insSmsSend(smsParam);
								if("ERROR".equals(smsResult)){
									html = "처리중 에러가 발생하였습니다.(문자이력등록)1";
								}
		        			}
	                	}//else
	                }else{
	                	html = "예약취소중 에러가 발생하였습니다.(DB INS)";
	                }
	                
				}else{
					html = rPMessage1+"\n "+rPMessage2;
				}				
			}
		}
		catch(Exception e) {
			rPMessage2			= "P잠시후재시도("+e.toString()+")";	// 硫붿떆吏�2
		} // end of catch
	
		return html;		
	}
	
	
	
	//***********************************************************************************
	/**
	 * �삩�씪�씤�삁�빟 �엯�옣�긽�뭹
	 * 20180416
	 * �꽌�쁺�슫
	 * 
	 * @param param
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/reserve/newResStep.af")
    public String newStep01(@RequestParam Map param, Model model, HttpSession session) {
		
		Map memberInfo = (Map) session.getAttribute("MEM_INFO");
		
		if(memberInfo ==  null){			
			memberInfo = (Map) session.getAttribute("MEM_INFO2");
		}				
		
		String type = (String) param.get("type");
		int num = 10000000;
		
		//�삁�빟 �럹�씠吏�
		if(type != null || !"".equals(type)) {
			if("res".equals(type)) {
				num = 10000000;
			} else if("pakage".equals(type)) {
				num = 20000000;
			} else {
				num = 10000000;
			}
		}
		
		String pointCode = "";
		
		if(!"".equals(param.get("prod")) && param.get("prod") != null){
			num = Integer.parseInt(param.get("prod").toString());
		}
		
		if(!"".equals(param.get("pointCode")) && param.get("pointCode") != null){
			pointCode = param.get("pointCode").toString();
		}else {
			//STEP2 �뿉�꽌 �씠�쟾�쑝濡� 媛�湲곌� �븘�땶, �삩�씪�씤�삁�빟 踰꾪듉 �겢由� �떆�뿉�뒗, 珥덇린�뿉 硫붿씤�럹�씠吏� �꽭�뀡媛믪쑝濡� �뀑�똿
			if(!"".equals(session.getAttribute("POINT_CODE")) && session.getAttribute("POINT_CODE") != null){
				pointCode = session.getAttribute("POINT_CODE").toString();
			}
		}
		Map<String, Object> pointInfo = frontReservationService.getPointInfo(pointCode);
		
		model.addAttribute("num", num);
		//model.addAttribute("pointCode", pointCode);
		model.addAttribute("pointInfo", pointInfo);
		
		List<Map<String, Object>> codePoint_code = super.getCommonCodes("POINT_CODE");
		model.addAttribute("codePoint_code", codePoint_code);	
		
		
		
		// date start
		
		Map<String, Object> parameter = new HashMap();
		parameter.put("cate_code", num);
		parameter.put("point_code", pointCode);
		
		List<Map> emptyDayList = frontReservationService.emptyDayList(parameter);
		List<Map> seasonDayList = frontReservationService.seasonDayList(parameter);			
		List<Map> reserveDayList = frontReservationService.reserveDayList(parameter);
		
		parameter.put("cate_code", "40000000");
		List<Map> eventDayList = frontReservationService.reserveDayList(parameter);
		
		
		JSONArray emptydaylist = new JSONArray();
		if(emptyDayList !=null){
			emptydaylist.addAll(emptyDayList);
		}
		
		JSONArray seasondaylist = new JSONArray();
		if(seasonDayList !=null){
			seasondaylist.addAll(seasonDayList);
		}
		JSONArray reservedayList = new JSONArray();
		if(reserveDayList !=null){
			reservedayList.addAll(reserveDayList);
		}
		JSONArray eventdayList = new JSONArray();
		if(eventDayList !=null){
			eventdayList.addAll(eventDayList);	
		}
		
		model.addAttribute("emptydays", emptydaylist);
		model.addAttribute("seasondays", seasondaylist);
		model.addAttribute("reservedays", reservedayList);
		model.addAttribute("eventdays", eventdayList);
		
		String key = generateKey(1);
		String iv = generateKey(2);
		
		Random random = new Random();
		String rand = ((key.length()+random.nextInt(10))+""+(iv.length()+random.nextInt(10)+10));
		
		model.addAttribute("key", key);
		model.addAttribute("iv", iv);
		model.addAttribute("rand", rand);
		
		return "/front/reservation/new_res_step";
    }
	

	@RequestMapping(value = "/reserve/step02.af")
    public String step02(@RequestParam Map param, Model model, HttpSession session) {
		int num = 10000000;
		if(!"".equals(param.get("prod")) && param.get("prod") != null){
			num = Integer.parseInt(param.get("prod").toString());
		}
		
		model.addAttribute("num", num);
        return "/front/reservation/res_step2";
    }
	
	@RequestMapping(value = "/reserve/step03.af")
    public String step03(@RequestParam Map param, Model model, HttpSession session, HttpServletRequest request) throws ParseException, UnsupportedEncodingException {
		
		//String rsData = DecoderUtil.decode(param, "rsData");
		if(param.get("rsData") == null || param.get("rand") == null){
			model.addAttribute("msg", "�뙆�씪硫뷀꽣 媛믪씠 �옒紐삳릺�뿀�뒿�땲�떎");
			return "redirect:/reserve/newResStep.af";
		}
		String rsData = param.get("rsData").toString();
		//String rsData = DecoderUtil.decode(param, "rsData");
		
		String rand = param.get("rand").toString();
		
		rsData = decrypt(rsData, rand);
		if(rsData == null){
			model.addAttribute("msg", "�뙆�씪硫뷀꽣 媛믪씠 �옒紐삳릺�뿀�뒿�땲�떎");
			return "redirect:/reserve/newResStep.af";
		}
		
		JSONObject rsDataObject = null;
		try {
			//rsData = URLDecoder.decode(rsData);
			
			JSONParser jsonParser = new JSONParser();
			rsDataObject = (JSONObject) jsonParser.parse(rsData);
		}
		catch (Exception ex){
			ex.printStackTrace();
			model.addAttribute("msg", "�뙆�씪硫뷀꽣 媛믪씠 �옒紐삳릺�뿀�뒿�땲�떎");
			return "redirect:/reserve/newResStep.af";
		}
		
		Map memberInfo = (Map) session.getAttribute("MEM_INFO");
		
		if(memberInfo ==  null){			
			memberInfo = (Map) session.getAttribute("MEM_INFO2");
		}		
		
		String memUid = memberInfo.get("MEM_UID").toString();
		/*String strPoint = (String)rsDataObject.get("pointCode");*/
		String strPoint = (String) param.get("pointCode");
		
		if(strPoint.toString().equals("POINT05")) {
			strPoint = "POINT07";
		}
		
		String strPointNm = (String)rsDataObject.get("pointNm");
		String strDate =(String)rsDataObject.get("date");
		String sndGoodname =(String)rsDataObject.get("type");
		Long sndAmount = (Long)rsDataObject.get("dpayment");
		String strSndOrderNum = frontReservationService.getReserveNum("");
		String sndServicePeriod = strDate.substring(0, 4) + "." + strDate.substring(4, 6) + "." + strDate.substring(6, 8);
		//String strSndOrderNum = strDate + sndOrdernumber + "01";
		String connectIP = param.get("connectIP").toString();		//�궗�슜�옄 �젒�냽 �븘�씠�뵾
		String device = param.get("device").toString();				//�궗�슜�옄 �젒�냽 �뵒諛붿씠�뒪
		String browser = param.get("browser").toString();			//�궗�슜�옄 �젒�냽 釉뚮씪�슦��
		String mem_mobile3 = (String)param.get("mem_mobile3");
		
		
		Map memberInfo2 = (Map) session.getAttribute("MEM_INFO");		
		
		Map parameters = new HashMap();
		//if(request.getParameter("mem_mobile3") != null || !request.getParameter("mem_mobile3").equals("")) {
			parameters.put("recipient_num",param.get("mem_mobile3")); // �닔�떊踰덊샇
			//parameters.put("member_name",Util.convertStringToHex((String)param.get("member_name")));
			parameters.put("member_name",Util.convertStringToHex((String)param.get("member_name")));
			parameters.put("email_set",(String)param.get("email_set"));
		//} else {
		//	parameters.put("recipient_num", memberInfo.get("MOBILE_NUM")); // �닔�떊踰덊샇
		//}

		parameters.put("reserve_uid", memberInfo2.get("MEM_ID").toString());
		
		String upd_type = "check1";
		
		parameters.put("upd_type",upd_type.toString());
		String updResultMembVal1 = frontReservationService.memberExtUpd(parameters);	
		
		Map parameters2 = new HashMap();
		//if(request.getParameter("mem_mobile3") != null || !request.getParameter("mem_mobile3").equals("")) {
			parameters2.put("recipient_num",param.get("mem_mobile3")); // �닔�떊踰덊샇
			parameters2.put("member_name",Util.convertStringToHex((String)param.get("member_name")));
			parameters2.put("email_set",(String)param.get("email_set"));
		//} else {
		//	parameters.put("recipient_num", memberInfo.get("MOBILE_NUM")); // �닔�떊踰덊샇
		//}		
		
		upd_type = "check2";
		
		parameters2.put("upd_type2",upd_type.toString());
		String updResultMembVal = frontReservationService.memberExtUpd(parameters2);			
		
		Map parameter_mem = new HashMap();
		parameter_mem.put("mem_id", memberInfo2.get("MEM_ID").toString());
		parameter_mem.put("point_code", "POINT01");
		Map memberInfo3 = this.frontMemberService.memberInfo(parameter_mem);
        session.removeAttribute("MEM_INFO");
        session.setAttribute("MEM_INFO", memberInfo3);		
		
		Map parameter_mem2 = new HashMap();
		parameter_mem2.put("mem_id", memberInfo2.get("MEM_ID").toString());
		parameter_mem2.put("point_code", "POINT01");
		Map memberInfo32 = this.frontMemberService.memberInfo(parameter_mem2);
        session.removeAttribute("MEM_INFO2");
        session.setAttribute("MEM_INFO2", memberInfo32);	        
		
        memberInfo = (Map) session.getAttribute("MEM_INFO");
        
		Map params = new HashMap();
		
		params.put("sndStoreid", config.getProperty("pg.store.code"));
		params.put("sndOrdernumber", strSndOrderNum);
		params.put("sndGoodname", sndGoodname);	
		params.put("sndAmount", sndAmount);
		params.put("sndServicePeriod", sndServicePeriod);				
		params.put("sndOrdername", memberInfo.get("MEM_NM"));
		params.put("sndEmail", memberInfo.get("MEM_ID"));
		if(memUid.contains("naver_") || memUid.contains("kakao_")) {
			params.put("sndMobile", "");
		} else {
			params.put("sndMobile", memberInfo.get("MOBILE_NUM"));
		}
		
		params.put("reserveMemUid", memUid);			//PG �넻�떊 �쟾/�썑, �쉶�썝 �븘�씠�뵒媛� �룞�씪�븳吏� �솗�씤�븯湲� �쐞�븿.
		params.put("etProdUid", (String)param.get("etProdUid"));
		params.put("rtProdUid", (String)param.get("rtProdUid"));
		params.put("selProdUid", (String)param.get("selProdUid"));
		params.put("rsChild", (String)param.get("rsChild"));
		params.put("rsAdult", (String)param.get("rsAdult"));
		params.put("rtProdCnt", (String)param.get("rtProdCnt"));
		params.put("etProdCnt", (String)param.get("etProdCnt"));
		params.put("rsData", rsData);
		params.put("mem_mobile3", mem_mobile3);				
		params.put("pointCode", strPoint);
		params.put("pointNm", strPointNm);
		
		int num = 10000000;
		if(!"".equals(param.get("prodNum")) && param.get("prodNum") != null){
			num = Integer.parseInt(param.get("prodNum").toString());
		}
		
		model.addAttribute("num", num);		
		model.addAttribute("params", params);
		model.addAttribute("rsData", rsData);
		
		session.setAttribute("RS_DATA", params);
		
		logger.debug("###################### reservation insert start!");
		//�삁�빟 INSERT	
		Map objParams = objectInfo(params);		

		logger.debug("###################### reservation insert ord_no :" + DecoderUtil.decode(params, "sndOrdernumber"));
		Map parameter = new HashMap();
		parameter.put("point_code", strPoint);
		parameter.put("mem_uid", Integer.parseInt(memUid));
		parameter.put("mem_nm", memberInfo.get("MEM_NM"));
		parameter.put("mem_id", memberInfo.get("MEM_ID"));
		if(mem_mobile3 != null) {
			parameter.put("mem_mobile", mem_mobile3);		
		} else {
			parameter.put("mem_mobile", memberInfo.get("MOBILE_NUM"));
		}
		parameter.put("adult_sum", objParams.get("adultCnt"));
		parameter.put("child_sum", objParams.get("childCnt"));
		parameter.put("reserve_date", objParams.get("reserveDay"));
		parameter.put("reserve_state", "NOPMT");			//寃곗젣 �쟾 �삁�빟 �긽�깭
		parameter.put("select_item_price", objParams.get("selProdTotal"));	
		parameter.put("event_item_price", objParams.get("eventProdTotal"));	
		parameter.put("rental_item_price", objParams.get("rentalProdTotal"));	
		parameter.put("payment_price", objParams.get("jsAmount"));	
		//parameter.put("payment_type", "");
		parameter.put("payment_type", "NODATA");
		parameter.put("payment_nm", DecoderUtil.decode(params, "sndOrdername"));
		parameter.put("order_num", DecoderUtil.decode(params, "sndOrdernumber"));	
		parameter.put("order_nm", DecoderUtil.decode(params, "sndGoodname"));
		parameter.put("ins_id", memberInfo.get("MEM_ID"));					
        parameter.put("spa_item_nm", objParams.get("spaNm"));
        parameter.put("water_item_nm", objParams.get("waterNm"));
        parameter.put("complex_item_nm", objParams.get("complexNm"));   // DB異붽�                 
        parameter.put("spa_adult_man", objParams.get("spaAdultM"));
        parameter.put("spa_adult_women", objParams.get("spaAdultW"));
        parameter.put("spa_child_man", objParams.get("spaChildM"));	
        parameter.put("spa_child_women", objParams.get("spaChildW"));	
        parameter.put("water_adult_man", objParams.get("waterAdultM"));	
        parameter.put("water_adult_women", objParams.get("waterAdultW"));	
        parameter.put("water_child_man", objParams.get("waterChildM"));	
        parameter.put("water_child_women", objParams.get("waterChildW"));                
        parameter.put("complex_adult_man", objParams.get("complexAdultM"));	//DB異붽�
        parameter.put("complex_adult_women", objParams.get("complexAdultW")); //DB異붽�	
        parameter.put("complex_child_man", objParams.get("complexChildM"));	 //DB異붽�
        parameter.put("complex_child_women", objParams.get("complexChildW")); //DB異붽�    
        if(objParams.get("itemType").toString().equals("0")){
    	    parameter.put("event1_item_nm", objParams.get("event1Nm"));	
            parameter.put("event2_item_nm", objParams.get("event2Nm"));	
            parameter.put("event3_item_nm", objParams.get("event3Nm"));	
            parameter.put("event1_cnt", objParams.get("event1Cnt"));	
            parameter.put("event2_cnt", objParams.get("event2Cnt"));	
            parameter.put("event3_cnt", objParams.get("event3Cnt"));
        }else{
	        parameter.put("event1_item_nm", objParams.get("spaNm"));	
	        parameter.put("event2_item_nm", objParams.get("waterNm"));	
	        parameter.put("event3_item_nm", objParams.get("complexNm"));
	        parameter.put("event1_cnt", objParams.get("spaNm").equals("") ? objParams.get("event1Cnt") : 1);
	        parameter.put("event2_cnt", objParams.get("waterNm").equals("") ? objParams.get("event2Cnt") : 1);
	        parameter.put("event3_cnt", objParams.get("complexNm").equals("") ? objParams.get("event3Cnt") : 1);
        }
        parameter.put("rental1_item_nm", objParams.get("rental1Nm"));	
        parameter.put("rental2_item_nm", objParams.get("rental2Nm"));	
        parameter.put("rental3_item_nm", objParams.get("rental3Nm"));	
        parameter.put("rental1_cnt", objParams.get("rental1Cnt"));	
        parameter.put("rental2_cnt", objParams.get("rental2Cnt"));	
        parameter.put("rental3_cnt", objParams.get("rental3Cnt"));	
        
        parameter.put("spa_item", objParams.get("spaProdUid"));
        parameter.put("water_item", objParams.get("waterProdUid"));
        parameter.put("complex_item", objParams.get("complexProdUid")); //DB異붽�                  
        parameter.put("rental1_item", objParams.get("rental1ProdUid"));
        parameter.put("rental2_item", objParams.get("rental2ProdUid"));
        parameter.put("rental3_item", objParams.get("rental3ProdUid"));
        parameter.put("event1_item", objParams.get("event1ProdUid"));
        parameter.put("event2_item", objParams.get("event2ProdUid"));
        parameter.put("event3_item", objParams.get("event3ProdUid"));
		parameter.put("itemSum0Cnt", objParams.get("itemSum0Cnt"));
		parameter.put("itemSum1Cnt", objParams.get("itemSum1Cnt"));
		parameter.put("itemSum2Cnt", objParams.get("itemSum2Cnt"));	
		parameter.put("pg_result", "0");
		parameter.put("cancel_seq", "");
		parameter.put("payment_date", new Date());
		parameter.put("order_tp", objParams.get("itemType")); 
		parameter.put("connect_ip", connectIP);
		parameter.put("device", device);
		parameter.put("browser", browser);
		
		String resultVal = frontReservationService.reserVationIns(parameter);
		
		logger.debug("###################### reservation insert end!");
		
		//�삁�빟 �뜲�씠�꽣 key 媛�
		model.addAttribute("reserveUid", parameter.get("reserveuid"));
		
		session.setAttribute("reserveUid", parameter.get("reserveuid"));
		
		//�삤耳��씠�넗留덊넗 �궡遺� �븘�씠�뵾 �씠嫄곕굹 @oktomato.net 怨꾩젙 �뿬遺� �솗�씤
		String getIP = request.getRemoteAddr();//0:0:0:0:0:0:0:1
		String memId = memberInfo.get("MEM_ID").toString();
		if(getIP.equals("115.94.37.77") || memId.indexOf("@oktomato.net") != -1){
			model.addAttribute("isTest", "Y");
		}
		
		List payMethodList = super.getPayCodes();
		model.addAttribute("codePAY_TYPE", payMethodList);
		
		
        return "/front/reservation/res_step3";
    }
	
	private String decrypt(final String encrypted, String rand){
		try {
			int start = Integer.parseInt(rand.substring(0, 2));
			int end = Integer.parseInt(rand.substring(2));
			
			String data1 =  encrypted.substring(0, start);
			String rkEncryptionKey = encrypted.substring(start, start+24);
			String data2 =  encrypted.substring(start+24, start+24+end);
			String rkEncryptionIv = encrypted.substring(start+24+end, start+24+end+24);
			String data3 =  encrypted.substring(start+24+end+24);
			String data = data1+data2+data3;
			
			SecretKey key = new SecretKeySpec(Base64.decode(rkEncryptionKey), "AES");
			AlgorithmParameterSpec iv = new IvParameterSpec(Base64.decode(rkEncryptionIv));
			byte[] decodeBase64 = Base64.decode(data);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key, iv);
			
			String text = new String(cipher.doFinal(decodeBase64),"UTF-8");
			
			return text;
		} catch (Exception e) {
			 e.printStackTrace();
		}
		return null;
	}
	
	private String generateKey(int type){
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			String key = "";			
			SecretKey secretKey = keyGen.generateKey();
			key = new String(Base64.encodeBytes(secretKey.getEncoded()));
			return key;
		} catch (Exception e) {
			 e.printStackTrace();
		}
		return type == 1 ? "u/Gu5posvwDsXUnV5Zaq4g==" : "5D9r9ZVzEYYgha93/aUK2w==";
	}
	
	
	
}
