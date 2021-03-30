package com.soft.web.crontab;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.soft.web.base.KSPayApprovalCancelBean;
import com.soft.web.controller.front.ReserVationController;
import com.soft.web.mail.MailService;
import com.soft.web.service.admin.AdminEmailTempletService;
import com.soft.web.service.admin.ReservationService;
import com.soft.web.service.common.CommonService;
import com.soft.web.service.front.FrontReservationService;
import com.soft.web.sms.SmsService;
import com.soft.web.util.AquaDateUtil;
import com.soft.web.util.DecoderUtil;


public class AquaScheduler {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Resource(name="config")
    private Properties config;
	
	@Autowired
	CommonService commonservice;
	
	@Autowired
	SmsService smsService;
	
	@Autowired
	FrontReservationService frontReservationService;
	
	@Autowired
	CommonService commonService;	
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	AdminEmailTempletService adminEmailTempletService;		
	
    /***
     * 1일전 SMS 알림
     * @throws Exception 
     * 
     */
	@Scheduled(cron="0 0 09 * * *")
	public void beforeOneDaySms() throws Exception{
    	java.util.Calendar calendar = java.util.Calendar.getInstance();
    	java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	
    	String errNum = "";
    	List<Map> beforeOneDaySmslist = commonservice.beforeOneDaySmslist();
    	
    	if(beforeOneDaySmslist != null ){
    		
    		logger.debug("@@@@@@@ 현재 시각: " +  dateFormat.format(calendar.getTime()) +" 알림문자 Start @@@@@@@@@@");
    		
    		for (Iterator iterator = beforeOneDaySmslist.iterator(); iterator
					.hasNext();) {
				Map map = (Map) iterator.next();
				//1일전 알림문자 발송
				//String contents = "[아쿠아필드]온라인 예약일 1일전(예약번호:"+map.get("ORDER_NUM")+",예약일:"+map.get("RESERVE_DATE")+")";
				Map smsParam = new HashMap();
				smsParam.put("point_code", "POINT01");
				smsParam.put("sms_type", "BEFORE");
				
				Map smsTemplte = commonservice.getSmsTemplete(smsParam);
				String contents = smsTemplte.get("SMS_CONTENT").toString();
				contents = contents.replace("{지점}",map.get("POINT_NM").toString());//지점 치환
				contents = contents.replace("{번호}",map.get("ORDER_NUM").toString());//예약번호 치환
				contents = contents.replace("{예약일}",map.get("RESERVE_DATE").toString());//예약일 치환	 
				
				Map params = new HashMap();
				params.put("recipient_num", map.get("MEM_MOBILE")); // 수신번호
				//param.put("subject", "[아쿠아필드]예약취소문자입니다."); // LMS일경우 제목을 추가 할 수 있다.
				params.put("content", contents); // 내용 (SMS=88Byte, LMS=2000Byte)		
				//params.put("callback", "031-8072-8800"); // 발신번호
				params.put("callback", config.getProperty("sms.tel.number."+map.get("POINT_CODE").toString())); // 발신번호
				
				if(!smsService.sendSms(params)){
					errNum += "1일전 문자 에러: "+map.get("MEM_MOBILE");
					logger.debug("@@@@@@@ 현재 시각: " +  dateFormat.format(calendar.getTime()) +" " + errNum);
				}				
			}
    		logger.debug("@@@@@@@ 현재 시각: " +  dateFormat.format(calendar.getTime()) +" 알림문자 End @@@@@@@@@@");
    	}
	}
	
    /***
     * 휴면상태 7일일전 메일발송
     * @throws Exception 
     * 
     */
	@Scheduled(cron="0 0 03 * * *")
	//@Scheduled(cron="0/10 * * * * *")
	public void beforeSevenDayMail() throws Exception{
    	java.util.Calendar calendar = java.util.Calendar.getInstance();
    	java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	
    	String errContents = "";
    	List<Map> beforeSevenDayMailList = commonservice.beforeSevenDayMailList();
    	
    	if(beforeSevenDayMailList != null ){
    		
    		logger.debug("@@@@@@@ 현재 시각: " +  dateFormat.format(calendar.getTime()) +" 휴면전환 INSSERT 및  알림 메일 Start @@@@@@@@@@");
    		
    		for (Iterator iterator = beforeSevenDayMailList.iterator(); iterator.hasNext();) {
				Map map = (Map) iterator.next();

				//메일발송 #####################################
				Map emailParam = new HashMap();
				emailParam.put("email_uid", "4");
				Map inactEmail = adminEmailTempletService.adminEmailTempletDetail(emailParam);
				
				String path = this.getClass().getResource("").getPath();
				path = path.substring(0, path.indexOf("WEB-INF")-1);
				String realPath = path + "/common/upload/email_templet";
				
				String inactMemHtml = getHTMfile(realPath+"/4");						
				
				String nowTime = AquaDateUtil.getToday();
				String inactMemDay = AquaDateUtil.addDay(nowTime, 7);
				nowTime = nowTime.substring(0, 4)+"."+nowTime.substring(4, 6)+"."+nowTime.substring(6, 8);
				inactMemDay = inactMemDay.substring(0, 4)+"년"+inactMemDay.substring(4, 6)+"월"+inactMemDay.substring(6, 8)+"일";

				inactMemHtml = inactMemHtml.replace("{{#NOW#}}",nowTime); // 현재시간 치환
				inactMemHtml = inactMemHtml.replace("{{#NAME#}}",map.get("MEM_NM").toString());//이름 치환
				inactMemHtml = inactMemHtml.replace("{{#INACTDAY#}}",inactMemDay);//휴면전화 날짜 치환
				inactMemHtml = inactMemHtml.replace("{{#ID#}}",map.get("MEM_ID").toString());// ID 치환
				
				logger.debug("@@@@@@@ 현재 시각: " +  dateFormat.format(calendar.getTime()) +" 휴면전환 알림 메일 1"+map.get("MEM_ID").toString()+" @@@@@@@@@@ ");
				boolean booleanresult =	mailService.sendmail(mailSender, inactEmail.get("FROM_EMAIL").toString(), inactEmail.get("FORM_EMAIL_NM").toString(), map.get("MEM_ID").toString(), map.get("MEM_NM").toString(), inactEmail.get("EMAIL_TITLE").toString(), inactMemHtml);

				if(!booleanresult){
					errContents = "처리중 에러가 발생하였습니다.(이메일)";
					logger.debug("@@@@@@@ 현재 시각: " +  dateFormat.format(calendar.getTime()) +" 휴면전환 알림 메일 Error "+map.get("MEM_ID").toString()+" @@@@@@@@@@ "+ errContents);
				}else{
					//EMAIL 발송 이력 등록
					emailParam.put("point_code", "POINT01");
					emailParam.put("email_uid", "4");
					emailParam.put("mem_id", map.get("MEM_ID").toString());
					emailParam.put("custom_nm", map.get("MEM_NM").toString());
					emailParam.put("custom_email", map.get("MEM_ID").toString());
					emailParam.put("ins_ip", "");
					emailParam.put("ins_id", map.get("MEM_ID").toString()); 
					emailParam.put("send_status", "OK");
					
					String smsResult = commonService.insEmailSend(emailParam);
					if("ERROR".equals(emailParam)){
						errContents = "처리중 에러가 발생하였습니다.(이메일 발송 등록)";
						logger.debug("@@@@@@@ 현재 시각: " +  dateFormat.format(calendar.getTime()) +" 휴면전환 알림 메일 발송등록 Error "+map.get("MEM_ID").toString()+" @@@@@@@@@@ "+ errContents);
					}						
				}										
				//###########################################					
						
				Map params = new HashMap();
				params.put("mem_uid",map.get("MEM_UID"));
				params.put("mem_nm",map.get("MEM_NM"));
				params.put("mem_id",map.get("MEM_ID"));
				params.put("point_code",map.get("POINT_CODE"));
				params.put("last_login_date",map.get("LAST_LOGIN_DATE"));
				params.put("mem_num",map.get("MEM_NUM"));				

				String inactInsResult = commonService.inactMemIns(params);
				if("ERROR".equals(inactInsResult)){
					errContents = "처리중 에러가 발생하였습니다.(휴면전환계정 등록)";
					logger.debug("@@@@@@@ 현재 시각: " +  dateFormat.format(calendar.getTime()) +" 휴면전환 계정 등록 Error "+map.get("MEM_ID").toString()+" @@@@@@@@@@ "+ errContents);
				}

			}
    		logger.debug("@@@@@@@ 현재 시각: " +  dateFormat.format(calendar.getTime()) +" 휴면전환 INSSERT 및  알림 메일 End @@@@@@@@@@");
    	}
    	
    	//휴면전환 업데이트
    	logger.debug("@@@@@@@ 현재 시각: " +  dateFormat.format(calendar.getTime()) +" 휴면전환 업데이트 Start @@@@@@@@@@");
    	String inactUpdResult = commonservice.inactMemUpd();
    	if("ERROR".equals(inactUpdResult)){
    		logger.debug("@@@@@@@ 현재 시각: " +  dateFormat.format(calendar.getTime()) +" 휴면전환 업데이트  Error @@@@@@@@@@ ");
    	}
    	logger.debug("@@@@@@@ 현재 시각: " +  dateFormat.format(calendar.getTime()) +" 휴면전환 업데이트 End @@@@@@@@@@");
	}	
	
	/***
	 * 온라인 예약 상품 미사용 UPDATE
	 * 
	 */
	@Scheduled(cron="0 0 02 * * *")
	//@Scheduled(cron="*/30 * * * * *")
	public void noUseUpdateForReserve(){
		
    	java.util.Calendar calendar = java.util.Calendar.getInstance();
    	java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		logger.debug("@@@@@@@ 현재 시각: " +  dateFormat.format(calendar.getTime()) +" NOUSE UPDATE Start @@@@@@@@@@");
		
    	String result = commonservice.batchNoUseUpd();
    	logger.debug("@@@@@@@ 현재 시각: " +  dateFormat.format(calendar.getTime()) +" Result " + result);
    	
		logger.debug("@@@@@@@ 현재 시각: " +  dateFormat.format(calendar.getTime()) +" NOUSE UPDATE End @@@@@@@@@@");
		
	}
	
	/***
	 * 온라인 예약 상품 미사용 10%위약금 제외 취소후 문자 및 메일 발송
	 * 
	 */
	@Scheduled(cron="0 0 10 * * *")
	//@Scheduled(cron="*/50 * * * * *")
	public void noUseSendForReserve(){
		
    	java.util.Calendar calendar = java.util.Calendar.getInstance();
    	java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String path = this.getClass().getResource("").getPath();
		path = path.substring(0, path.indexOf("WEB-INF")-1);
		String realPath = path + "/common/upload/email_templet";
			
		//ReserVationController reserVation = new ReserVationController();	
		List<Map> batchCancelList = commonservice.batchCancelList();
		if(batchCancelList.size() > 0){
			logger.debug("@@@@@@@ 현재 시각: " +  dateFormat.format(calendar.getTime()) +" NOUSE 자동취소 및 취소문자 / 메일 발송 Start @@@@@@@@@@");
		
			for (Iterator iterator = batchCancelList.iterator(); iterator.hasNext();) {
				Map map = (Map) iterator.next();
				
				Double orinalAmount = Double.parseDouble(map.get("PAYMENT_PRICE").toString());
				Double doubleAmount = Double.parseDouble(map.get("PAYMENT_PRICE").toString()) * 0.9; // 10%위약금 제외한 금액
				
				Map pgResultInfo = frontReservationService.pgResultInfo(map.get("PG_RESULT").toString());				
				int compareVal = Integer.parseInt(pgResultInfo.get("TR_NO").toString().substring(0,1));
				
				String authty = "";
				switch (compareVal) {
				case 1: authty = "1010";
					break;
				case 2: authty = "2030";
					break;
				case 4: authty = "4110";
					break;					
				}				
				
				Map cancelParams = new HashMap();
				cancelParams.put("storeid", config.getProperty("pg.store.code"));
				cancelParams.put("userName", map.get("MEM_NM"));
				cancelParams.put("email", map.get("MEM_ID"));
				cancelParams.put("goodName", map.get("ORDER_NM"));
				cancelParams.put("phoneNo", map.get("MEM_MOBILE"));
				cancelParams.put("authty", authty);
				cancelParams.put("trno", pgResultInfo.get("TR_NO"));
				cancelParams.put("canc_amt", doubleAmount.intValue());
				cancelParams.put("penalty_amt", orinalAmount.intValue() - doubleAmount.intValue());				
				cancelParams.put("canc_seq", map.get("CANCEL_SEQ"));
				cancelParams.put("canc_type", "3");		
				cancelParams.put("uid", map.get("RESERVE_UID"));
				cancelParams.put("ordernum", map.get("ORDER_NUM"));
				cancelParams.put("reserveday", map.get("RESERVE_DATE"));
				cancelParams.put("mem_id", map.get("MEM_ID"));
				cancelParams.put("custom_nm", map.get("MEM_NM"));
				cancelParams.put("ins_ip", "localhost");
				cancelParams.put("realPath", realPath);
				cancelParams.put("spa_ad_Man", map.get("SPA_ADULT_MAN"));
				cancelParams.put("spa_ad_Women", map.get("SPA_ADULT_WOMEN"));
				cancelParams.put("spa_ch_Man", map.get("SPA_CHILD_MAN"));
				cancelParams.put("spa_ch_Women", map.get("SPA_CHILD_WOMEN"));
				cancelParams.put("water_ad_Man", map.get("WATER_ADULT_MAN"));
				cancelParams.put("water_ad_Women", map.get("WATER_ADULT_WOMEN"));
				cancelParams.put("water_ch_Man", map.get("WATER_CHILD_MAN"));
				cancelParams.put("water_ch_Women", map.get("WATER_CHILD_WOMEN"));
				cancelParams.put("complex_ad_Man", map.get("COMPLEX_ADULT_MAN"));
				cancelParams.put("complex_ad_Women", map.get("COMPLEX_ADULT_WOMEN"));
				cancelParams.put("complex_ch_Man", map.get("COMPLEX_CHILD_MAN"));
				cancelParams.put("complex_ch_Women", map.get("COMPLEX_CHILD_WOMEN"));
				cancelParams.put("rental01", map.get("RENTAL1_CNT"));
				cancelParams.put("rental02", map.get("RENTAL2_CNT"));
				cancelParams.put("rental03", map.get("RENTAL3_CNT"));
				cancelParams.put("event01", map.get("EVENT1_CNT"));
				cancelParams.put("event02", map.get("EVENT2_CNT"));
				cancelParams.put("event03", map.get("EVENT3_CNT"));
				cancelParams.put("sumVisitCnt", Integer.parseInt(map.get("ADULT_SUM").toString()) + Integer.parseInt(map.get("CHILD_SUM").toString()));
				cancelParams.put("pointNm", map.get("POINT_NM").toString());
				cancelParams.put("pointCode", map.get("POINT_CODE").toString());
				
				String html = "";
				
				if("1010".equals(authty)){
					html = cardCancelAction(cancelParams);
				}else if("2030".equals(authty)){
					html = cashCancelAction(cancelParams);
				}else if("4110".equals(authty)){
					html = ssgCancelAction(cancelParams);
				}
				
				if(!"예약취소가 정상적으로 이루어 졌습니다.".equals(html)){
					logger.debug("@@@@@@@ 현재 시각: " +  dateFormat.format(calendar.getTime()) +" NOUSE 자동취소 및 취소문자 / 메일 발송 Error "+map.get("ORDER_NUM")+" @@@@@@@@@@ "+ html);
				}			
			}
			
			logger.debug("@@@@@@@ 현재 시각: " +  dateFormat.format(calendar.getTime()) +" NOUSE 자동취소 및 취소문자 / 메일 발송 End @@@@@@@@@@");
		}
		
	}
	
	//신용카드 취소 관련
	public String cardCancelAction(Map cancelParams){
		
		String html = "";
		//Map memberInfo = (Map) session.getAttribute("MEM_INFO");
		// Default(수정항목이 아님)-------------------------------------------------------
		String	EncType       = "2";					// 0: 암화안함, 1:openssl, 2: seed
		String	Version       = "0603";				    // 전문버전
		String	Type          = "00";					// 구분
		String	Resend        = "0";					// 전송구분 : 0 : 처음,  2: 재전송
		String	RequestDate   = new SimpleDateFormat("yyyyMMddhhmmss").format(new java.util.Date()); // 요청일자 : yyyymmddhhmmss
		String	KeyInType     = "K";					// KeyInType 여부 : S : Swap, K: KeyInType
		String	LineType      = "1";			        // lineType 0 : offline, 1:internet, 2:Mobile
		String	ApprovalCount = "1";				    // 복합승인갯수
		String 	GoodType      = "0";	                // 제품구분 0 : 실물, 1 : 디지털
		String	HeadFiller    = "";				        // 예비
	//-------------------------------------------------------------------------------

	// Header (입력값 (*) 필수항목)--------------------------------------------------
		String	StoreId		= cancelParams.get("storeid").toString();		// *상점아이디
		String	OrderNumber	= "";									// 주문번호
		String	UserName    = cancelParams.get("userName").toString();		// *주문자명
		String	IdNum       = "";									// 주민번호 or 사업자번호
		String	Email       = cancelParams.get("email").toString();		// *email
		String 	GoodName    = cancelParams.get("goodName").toString();		// *제품명
		String	PhoneNo     = cancelParams.get("phoneNo").toString();  	// *휴대폰번호                                              
	// Header end -------------------------------------------------------------------

	// Data Default(수정항목이 아님)-------------------------------------------------
		String ApprovalType	  = cancelParams.get("authty").toString();		// 승인구분
		String TrNo   		  = cancelParams.get("trno").toString();		// 거래번호
		String Canc_amt		  = cancelParams.get("canc_amt").toString();	// 취소금액
		String Canc_seq		  = cancelParams.get("canc_seq").toString();	// 취소일련번호
		String Canc_type	  = cancelParams.get("canc_type").toString();	// 취소유형 0 :거래번호취소 1: 주문번호취소 3:부분취소
	// Data Default end -------------------------------------------------------------

	// Server로 부터 응답이 없을시 자체응답
		String rApprovalType	   = "1011"; 
		String rTransactionNo      = "";			// 거래번호
		String rStatus             = "X";			// 상태 O : 승인, X : 거절
		String rTradeDate          = ""; 			// 거래일자
		String rTradeTime          = ""; 			// 거래시간
		String rIssCode            = "00"; 			// 발급사코드
		String rAquCode			   = "00"; 			// 매입사코드
		String rAuthNo             = "9999"; 		// 승인번호 or 거절시 오류코드
		String rMessage1           = "취소거절"; 	// 메시지1
		String rMessage2           = "C잠시후재시도";// 메시지2
		String rCardNo             = ""; 			// 카드번호
		String rExpDate            = ""; 			// 유효기간
		String rInstallment        = ""; 			// 할부
		String rAmount             = ""; 			// 금액
		String rMerchantNo         = ""; 			// 가맹점번호
		String rAuthSendType       = "N"; 			// 전송구분
		String rApprovalSendType   = "N"; 			// 전송구분(0 : 거절, 1 : 승인, 2: 원카드)
		String rPoint1             = "000000000000";// Point1
		String rPoint2             = "000000000000";// Point2
		String rPoint3             = "000000000000";// Point3
		String rPoint4             = "000000000000";// Point4
		String rVanTransactionNo   = "";
		String rFiller             = ""; 			// 예비
		String rAuthType	 	   = ""; 			// ISP : ISP거래, MP1, MP2 : MPI거래, SPACE : 일반거래
		String rMPIPositionType	   = ""; 			// K : KSNET, R : Remote, C : 제3기관, SPACE : 일반거래
		String rMPIReUseType	   = ""; 			// Y : 재사용, N : 재사용아님
		String rEncData			   = ""; 			// MPI, ISP 데이터

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
				rTransactionNo		= ipg.TransactionNo[0];	  		// 거래번호
				rStatus				= ipg.Status[0];		  		// 상태 O : 승인, X : 거절
				rTradeDate			= ipg.TradeDate[0];		  		// 거래일자
				rTradeTime			= ipg.TradeTime[0];		  		// 거래시간
				rIssCode			= ipg.IssCode[0];		  		// 발급사코드
				rAquCode			= ipg.AquCode[0];		  		// 매입사코드
				rAuthNo				= ipg.AuthNo[0];		  		// 승인번호 or 거절시 오류코드
				rMessage1			= ipg.Message1[0];		  		// 메시지1
				rMessage2			= ipg.Message2[0];		  		// 메시지2
				rCardNo				= ipg.CardNo[0];		  		// 카드번호
				rExpDate			= ipg.ExpDate[0];		  		// 유효기간
				rInstallment		= ipg.Installment[0];	  		// 할부
				rAmount				= ipg.Amount[0];		  		// 금액
				rMerchantNo			= ipg.MerchantNo[0];	  		// 가맹점번호
				rAuthSendType		= ipg.AuthSendType[0];	  		// 전송구분= new String(this.read(2));
				rApprovalSendType	= ipg.ApprovalSendType[0];		// 전송구분(0 : 거절, 1 : 승인, 2: 원카드)
				rPoint1				= ipg.Point1[0];		  		// Point1
				rPoint2				= ipg.Point2[0];		  		// Point2
				rPoint3				= ipg.Point3[0];		  		// Point3
				rPoint4				= ipg.Point4[0];		  		// Point4
				rVanTransactionNo   = ipg.VanTransactionNo[0];      // Van거래번호
				rFiller				= ipg.Filler[0];		  		// 예비
				rAuthType			= ipg.AuthType[0];		  		// ISP : ISP거래, MP1, MP2 : MPI거래, SPACE : 일반거래
				rMPIPositionType	= ipg.MPIPositionType[0]; 		// K : KSNET, R : Remote, C : 제3기관, SPACE : 일반거래
				rMPIReUseType		= ipg.MPIReUseType[0];			// Y : 재사용, N : 재사용아님
				rEncData			= ipg.EncData[0];		  		// MPI, ISP 데이터
				
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
	                parameter.put("reserve_state","FCANCEL");	
	                parameter.put("penalty_amt",cancelParams.get("penalty_amt"));	                
	                
	                String result = frontReservationService.pgCancelIns(parameter);
	                
	                if("PGCANCELOK".equals(result)){
	                	html = "예약취소가 정상적으로 이루어 졌습니다.";	
	                	
	        			//예약취소문자 발송
	        			//String contents = "[아쿠아필드]온라인 예약취소(예약번호:"+request.getParameter("ordernum")+",예약일:"+request.getParameter("reserveday")+")";
						Map smsParam = new HashMap();
						smsParam.put("point_code", "POINT01");
						smsParam.put("sms_type", "CANCEL");
						
						Map smsTemplte = commonService.getSmsTemplete(smsParam);
						String contents = smsTemplte.get("SMS_CONTENT").toString();
						contents = contents.replace("{지점}",cancelParams.get("pointNm").toString());//예약번호 치환
						contents = contents.replace("{번호}",cancelParams.get("ordernum").toString());//예약번호 치환
						contents = contents.replace("{예약일}",cancelParams.get("reserveday").toString());//예약일 치환	                	
	                	
	        			Map params = new HashMap();
	        			params.put("recipient_num", cancelParams.get("phoneNo")); // 수신번호
	        			//param.put("subject", "[아쿠아필드]예약취소문자입니다."); // LMS일경우 제목을 추가 할 수 있다.
	        			params.put("content", contents); // 내용 (SMS=88Byte, LMS=2000Byte)		
	        			//params.put("callback", "031-8072-8800"); // 발신번호
	        			params.put("callback", config.getProperty("sms.tel.number."+cancelParams.get("pointCode").toString())); // 발신번호
	        			
	        			if(!smsService.sendSms(params)){
	        				html = "예약취소중 에러가 발생하였습니다.(문자)";
	        			}else{
							//SMS 발송 이력 등록
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
								html = "alert('처리중 에러가 발생하였습니다.(문자이력등록)');";
							}
	        			}
	        			
						//메일발송 #####################################
	        			int intUid = Integer.parseInt(cancelParams.get("uid").toString());
	        			Map getReserveInfo = frontReservationService.getReserveInfo(intUid); //예약정보 가져오기
	        			
						Map emailParam = new HashMap();
						emailParam.put("email_uid", "3");
						Map joinEmail = adminEmailTempletService.adminEmailTempletDetail(emailParam);
						
						String realPath = cancelParams.get("realPath").toString();
						//String joinHtml = super.getHTMfile(realPath+"/reservation_complete.html");
						
						String joinHtml = getHTMfile(realPath+"/3");						
						//String joinHtml = super.getHTMfile(realPath+"/reservation_cancel.html");
						
						String nowTime = AquaDateUtil.getToday();
						nowTime = nowTime.substring(0, 4)+"."+nowTime.substring(4, 6)+"."+nowTime.substring(6, 8);						
						joinHtml = joinHtml.replace("{{#NOW#}}",nowTime); // 현재시간 치환
						joinHtml = joinHtml.replace("{{#NAME#}}",getReserveInfo.get("MEM_NM").toString());//이름 치환
						joinHtml = joinHtml.replace("{{#RESERVENUM#}}",getReserveInfo.get("ORDER_NUM").toString());//예약번호 치환
						joinHtml = joinHtml.replace("{{#POINTNM#}}",getReserveInfo.get("POINT_NM").toString());//지점 치환
						joinHtml = joinHtml.replace("{{#RESERVEDAY#}}",getReserveInfo.get("RESERVE_DATE").toString());//예약일치환
						joinHtml = joinHtml.replace("{{#GOODS#}}",getReserveInfo.get("ORDER_NM").toString());//상품명치환
						
						int cnt = Integer.parseInt(getReserveInfo.get("ADULT_SUM").toString()) + Integer.parseInt(getReserveInfo.get("CHILD_SUM").toString());
						joinHtml = joinHtml.replace("{{#CNT#}}",String.valueOf(cnt));//인원수치환
						
						Map pgResultInfo = frontReservationService.pgResultInfo(getReserveInfo.get("PG_RESULT").toString());//PG정보가져오기						
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
						joinHtml = joinHtml.replace("{{#PAYTYPE#}}",r_TYPE);//결제수단치환
						
						String approvaldate = pgResultInfo.get("TR_DDT").toString();
						approvaldate = approvaldate.substring(0, 4)+"."+approvaldate.substring(4, 6)+"."+approvaldate.substring(6, 8);
						joinHtml = joinHtml.replace("{{#APPROVALDATE#}}",approvaldate);//승인일시치환
						joinHtml = joinHtml.replace("{{#PRICE#}}",getReserveInfo.get("PAYMENT_PRICE").toString());//결제금액치환   
						
						String canceldate = parameter.get("ac_tradedate").toString();
						canceldate = canceldate.substring(0, 4)+"."+canceldate.substring(4, 6)+"."+canceldate.substring(6, 8);
						joinHtml = joinHtml.replace("{{#CANCELDATE#}}",canceldate);//취소일시치환
						int intcancel = Integer.parseInt(parameter.get("ac_amount").toString());
						joinHtml = joinHtml.replace("{{#CANCELPRICE#}}", String.valueOf(intcancel));//취소금액치환   
						joinHtml = joinHtml.replace("{{#PENALTIES#}}",getReserveInfo.get("PANALTY_PRICE").toString());//위약금치환
						String reHtml= joinHtml.replace("{{#REFUND#}}",getReserveInfo.get("REFUND").toString());

						//boolean booleanresult =	mailService.sendmail(mailSender, "aquafield@shinsegae.com", "아쿠아필드", getReserveInfo.get("MEM_ID").toString(), getReserveInfo.get("MEM_NM").toString(), "[아쿠아필드]예약취소메일입니다.", reHtml);
						boolean booleanresult =	mailService.sendmail(mailSender, joinEmail.get("FROM_EMAIL").toString(), joinEmail.get("FORM_EMAIL_NM").toString(), getReserveInfo.get("MEM_ID").toString(), getReserveInfo.get("MEM_NM").toString(), joinEmail.get("EMAIL_TITLE").toString(), reHtml);

						if(!booleanresult){
							html = "처리중 에러가 발생하였습니다.(이메일)";
						}else{
							//EMAIL 발송 이력 등록
							emailParam.put("point_code", "POINT01");
							emailParam.put("email_uid", "2");
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

	                }else{
	                	html = "예약취소중 에러가 발생하였습니다.(DB INS)";
	                }
	                
				}else{
					html = rMessage1+"\n "+rMessage2;
				}
			}
		}
		catch(Exception e) {
			rMessage2			= "P잠시후재시도("+e.toString()+")";	// 메시지2
			html = rMessage2;
		} // end of catch	
		
		return html;
	}
	
	//실시간 계좌이체 취소 관련
	public String cashCancelAction(Map cancelParams){
		
		String html = "";

		//Header부 Data --------------------------------------------------
		String EncType				= "2" ;												// 0: 암화안함, 1:ssl, 2: seed
		String Version				= "0603" ;											// 전문버전
		String Type					= "00" ;											// 구분
		String Resend				= "0" ;												// 전송구분 : 0 : 처음,  2: 재전송
		String RequestDate		= new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()); // 요청일자 :
		String KeyInType			= "K" ;												// KeyInType 여부 : S : Swap, K: KeyInType
		String LineType				= "1" ;												// lineType 0 : offline, 1:internet, 2:Mobile
		String ApprovalCount		= "1"	;											// 복합승인갯수
		String GoodType				= "0" ;												// 제품구분 0 : 실물, 1 : 디지털
		String HeadFiller			= ""	;											// 예비

	// Header (입력값 (*) 필수항목)--------------------------------------------------
		String StoreId				= cancelParams.get("storeid").toString() ;					// *상점아이디
		String OrderNumber			= "";												// 주문번호
		String UserName				= "";												// 주문자명
		String IdNum				= "";												// 주민번호 or 사업자번호
		String Email				= "";												// email
		String GoodName				= "";												// 제품명
		String PhoneNo				= "";												// 휴대폰번호
	//Header end -------------------------------------------------------------------

	//Data Default------------------------------------------------------------------
		String ApprovalType	  = cancelParams.get("authty").toString()	;// 승인구분 코드
		String TrNo			  = cancelParams.get("trno").toString()   ;// 거래번호
		String Canc_amt		  = cancelParams.get("canc_amt").toString();	// 취소금액
		String Canc_seq		  = cancelParams.get("canc_seq").toString();	// 취소일련번호
		String Canc_type	  = cancelParams.get("canc_type").toString();	// 취소유형 0 :거래번호취소 1: 주문번호취소 3:부분취소		

	// Server로 부터 응답이 없을시 자체응답
		String		rApprovalType    		= "2011"					; // 승인구분
		String		rACTransactionNo    	= TrNo						; // 거래번호
		String		rACStatus           	= "X"						; // 오류구분 :승인 X:거절
		String		rACTradeDate        	= RequestDate.substring(0,8); // 거래 개시 일자(YYYYMMDD)
		String		rACTradeTime        	= RequestDate.substring(8,14); // 거래 개시 시간(HHMMSS)
		String		rACAcctSele         	= ""						; // 계좌이체 구분 -	1:Dacom, 2:Pop Banking,	3:실시간계좌이체 4: 승인형계좌이체
		String		rACFeeSele          	= ""						; // 선/후불제구분 -	1:선불,	2:후불
		String		rACInjaName         	= ""						; // 인자명(통장인쇄메세지-상점명)
		String		rACPareBankCode     	= ""						; // 입금모계좌코드
		String		rACPareAcctNo       	= ""						; // 입금모계좌번호
		String		rACCustBankCode     	= ""						; // 출금모계좌코드
		String		rACCustAcctNo       	= ""						; // 출금모계좌번호
		String		rACAmount	       		= ""						; // 금액	(결제대상금액)
		String		rACBankTransactionNo	= ""						; // 은행거래번호
		String		rACIpgumNm          	= ""						; // 입금자명
		String		rACBankFee          	= "0"						; // 계좌이체 수수료
		String		rACBankAmount       	= ""						; // 총결제금액(결제대상금액+ 수수료
		String		rACBankRespCode     	= "9999"					; // 오류코드
		String		rACMessage1         	= "취소거절"				; // 오류 message 1
		String		rACMessage2         	= "C잠시후재시도"			; // 오류 message 2
		String		rACFiller           	= ""						; // 예비

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
				rACTransactionNo	    =	ipg.ACTransactionNo		[0];   // 거래번호
				rACStatus				=	ipg.ACStatus			[0];   // 오류구분 :승인 X:거절
				rACTradeDate		    =	ipg.ACTradeDate			[0];   // 거래 개시 일자(YYYYMMDD)
				rACTradeTime		    =	ipg.ACTradeTime			[0];   // 거래 개시 시간(HHMMSS)
				rACAcctSele		    	=	ipg.ACAcctSele			[0];   // 계좌이체 구분 -	1:Dacom, 2:Pop Banking,	3:Scrapping 계좌이체, 4:승인형계좌이체, 5:금결원계좌이체    
				rACFeeSele				=	ipg.ACFeeSele			[0];   // 선/후불제구분 -	1:선불,	2:후불
				rACInjaName		    	=	ipg.ACInjaName			[0];   // 인자명(통장인쇄메세지-상점명)
				rACPareBankCode	    	=	ipg.ACPareBankCode		[0];   // 입금모계좌코드
				rACPareAcctNo			=	ipg.ACPareAcctNo		[0];   // 입금모계좌번호
				rACCustBankCode	    	=	ipg.ACCustBankCode		[0];   // 출금모계좌코드
				rACCustAcctNo			=	ipg.ACCustAcctNo		[0];   // 출금모계좌번호
				rACAmount				=	ipg.ACAmount			[0];   // 금액	(결제대상금액)
				rACBankTransactionNo  	=	ipg.ACBankTransactionNo	[0];   // 은행거래번호
				rACIpgumNm				=	ipg.ACIpgumNm			[0];   // 입금자명
				rACBankFee				=	ipg.ACBankFee			[0];   // 계좌이체 수수료
				rACBankAmount			=	ipg.ACBankAmount		[0];   // 총결제금액(결제대상금액+ 수수료
				rACBankRespCode	    	=	ipg.ACBankRespCode		[0];   // 오류코드
				rACMessage1		    	=	ipg.ACMessage1			[0];   // 오류 message 1
				rACMessage2		    	=	ipg.ACMessage2			[0];   // 오류 message 2
				rACFiller				=	ipg.ACFiller			[0];   // 예비
				
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
	                parameter.put("reserve_state","FCANCEL");
	                parameter.put("penalty_amt",cancelParams.get("penalty_amt"));	                
	                
	                String result = frontReservationService.pgCancelIns(parameter);
	                
	                if("PGCANCELOK".equals(result)){
	                	html = "예약취소가 정상적으로 이루어 졌습니다.";	
	                	
	        			//예약취소문자 발송
	        			//String contents = "[아쿠아필드]온라인 예약취소(예약번호:"+request.getParameter("ordernum")+",예약일:"+request.getParameter("reserveday")+")";
						Map smsParam = new HashMap();
						smsParam.put("point_code", "POINT01");
						smsParam.put("sms_type", "CANCEL");
						
						Map smsTemplte = commonService.getSmsTemplete(smsParam);
						String contents = smsTemplte.get("SMS_CONTENT").toString();
						contents = contents.replace("{지점}",cancelParams.get("pointNm").toString());//예약번호 치환
						contents = contents.replace("{번호}",cancelParams.get("ordernum").toString());//예약번호 치환
						contents = contents.replace("{예약일}",cancelParams.get("reserveday").toString());//예약일 치환		        			
	        			
	        			Map params = new HashMap();
	        			params.put("recipient_num", cancelParams.get("phoneNo")); // 수신번호
	        			//param.put("subject", "[아쿠아필드]예약취소문자입니다."); // LMS일경우 제목을 추가 할 수 있다.
	        			params.put("content", contents); // 내용 (SMS=88Byte, LMS=2000Byte)		
	        			//params.put("callback", "031-8072-8800"); // 발신번호
	        			params.put("callback", config.getProperty("sms.tel.number."+cancelParams.get("pointCode").toString())); // 발신번호
	        			
	        			if(!smsService.sendSms(params)){
	        				html = "예약취소중 에러가 발생하였습니다.(문자)";
	        			}else{
							//SMS 발송 이력 등록
							smsParam.put("sms_uid", smsTemplte.get("SMS_UID"));
							smsParam.put("mem_id", cancelParams.get("mem_id"));
							smsParam.put("custom_nm", cancelParams.get("custom_nm"));
							smsParam.put("custom_mobile", cancelParams.get("phoneNo"));
							smsParam.put("ins_ip", cancelParams.get("ins_ip"));
							smsParam.put("ins_id", cancelParams.get("MEM_ID")); 
							smsParam.put("send_status", "OK");	
							smsParam.put("bigo", "CASH_RESERVE_CANCEL");			
							
							String smsResult = commonService.insSmsSend(smsParam);
							if("ERROR".equals(smsResult)){
								html = "alert('처리중 에러가 발생하였습니다.(문자이력등록)');";
							}
	        			}
	        			
						//메일발송 #####################################
	        			int intUid = Integer.parseInt(cancelParams.get("uid").toString());
	        			Map getReserveInfo = frontReservationService.getReserveInfo(intUid); //예약정보 가져오기
	        			
						Map emailParam = new HashMap();
						emailParam.put("email_uid", "3");
						Map joinEmail = adminEmailTempletService.adminEmailTempletDetail(emailParam);
						
						String realPath = cancelParams.get("realPath").toString();
						//String joinHtml = super.getHTMfile(realPath+"/reservation_complete.html");
						
						String joinHtml = getHTMfile(realPath+"/3");						
						//String joinHtml = super.getHTMfile(realPath+"/reservation_cancel.html");	        			

						String nowTime = AquaDateUtil.getToday();
						nowTime = nowTime.substring(0, 4)+"."+nowTime.substring(4, 6)+"."+nowTime.substring(6, 8);						
						joinHtml = joinHtml.replace("{{#NOW#}}",nowTime); // 현재시간 치환
						joinHtml = joinHtml.replace("{{#NAME#}}",getReserveInfo.get("MEM_NM").toString());//이름 치환
						joinHtml = joinHtml.replace("{{#RESERVENUM#}}",getReserveInfo.get("ORDER_NUM").toString());//예약번호 치환
						joinHtml = joinHtml.replace("{{#POINTNM#}}",getReserveInfo.get("POINT_NM").toString());//지점 치환						joinHtml = joinHtml.replace("{{#RESERVEDAY#}}",getReserveInfo.get("RESERVE_DATE").toString());//예약일치환
						joinHtml = joinHtml.replace("{{#GOODS#}}",getReserveInfo.get("ORDER_NM").toString());//상품명치환
						
						int cnt = Integer.parseInt(getReserveInfo.get("ADULT_SUM").toString()) + Integer.parseInt(getReserveInfo.get("CHILD_SUM").toString());
						joinHtml = joinHtml.replace("{{#CNT#}}",String.valueOf(cnt));//인원수치환
						
						Map pgResultInfo = frontReservationService.pgResultInfo(getReserveInfo.get("PG_RESULT").toString());//PG정보가져오기						
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
						joinHtml = joinHtml.replace("{{#PAYTYPE#}}",r_TYPE);//결제수단치환
						
						String approvaldate = pgResultInfo.get("TR_DDT").toString();
						approvaldate = approvaldate.substring(0, 4)+"."+approvaldate.substring(4, 6)+"."+approvaldate.substring(6, 8);
						joinHtml = joinHtml.replace("{{#APPROVALDATE#}}",approvaldate);//승인일시치환
						joinHtml = joinHtml.replace("{{#PRICE#}}",getReserveInfo.get("PAYMENT_PRICE").toString());//결제금액치환   
						
						String canceldate = parameter.get("ac_tradedate").toString();
						canceldate = canceldate.substring(0, 4)+"."+canceldate.substring(4, 6)+"."+canceldate.substring(6, 8);
						joinHtml = joinHtml.replace("{{#CANCELDATE#}}",canceldate);//취소일시치환
						int intcancel = Integer.parseInt(parameter.get("ac_amount").toString());
						joinHtml = joinHtml.replace("{{#CANCELPRICE#}}", String.valueOf(intcancel));//취소금액치환   
						joinHtml = joinHtml.replace("{{#PENALTIES#}}",getReserveInfo.get("PANALTY_PRICE").toString());//위약금치환
						String reHtml= joinHtml.replace("{{#REFUND#}}",getReserveInfo.get("REFUND").toString());

						//boolean booleanresult =	mailService.sendmail(mailSender, "aquafield@shinsegae.com", "아쿠아필드", getReserveInfo.get("MEM_ID").toString(), getReserveInfo.get("MEM_NM").toString(), "[아쿠아필드]예약취소메일입니다.", reHtml);
						boolean booleanresult =	mailService.sendmail(mailSender, joinEmail.get("FROM_EMAIL").toString(), joinEmail.get("FORM_EMAIL_NM").toString(), getReserveInfo.get("MEM_ID").toString(), getReserveInfo.get("MEM_NM").toString(), joinEmail.get("EMAIL_TITLE").toString(), reHtml);

						if(!booleanresult){
							html = "처리중 에러가 발생하였습니다.(이메일)";
						}else{
							//EMAIL 발송 이력 등록
							emailParam.put("point_code", "POINT01");
							emailParam.put("email_uid", "2");
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
							
	                }else{
	                	html = "예약취소중 에러가 발생하였습니다.(DB INS)";
	                }
	                
				}else{
					html = rACMessage1+"\n "+rACMessage2;
				}				
			}
		}
		catch(Exception e) {
			rACMessage2			= "P잠시후재시도("+e.toString()+")";	// 메시지2
		} // end of catch
	
		return html;		
	}
	
	//SSGPAY - SSG MONEY 취소 관련
	public String ssgCancelAction(Map cancelParams){
		
		String html = "예약취소가 정상적으로 되었습니다.";

		//Header부 Data --------------------------------------------------
		String EncType				= "2" ;												// 0: 암화안함, 1:ssl, 2: seed
		String Version				= "0603" ;											// 전문버전
		String Type					= "00" ;											// 구분
		String Resend				= "0" ;												// 전송구분 : 0 : 처음,  2: 재전송
		String RequestDate		= new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date()); // 요청일자 :
		String KeyInType			= "K" ;												// KeyInType 여부 : S : Swap, K: KeyInType
		String LineType				= "1" ;												// lineType 0 : offline, 1:internet, 2:Mobile
		String ApprovalCount		= "1"	;											// 복합승인갯수
		String GoodType				= "0" ;												// 제품구분 0 : 실물, 1 : 디지털
		String HeadFiller			= ""	;											// 예비

	// Header (입력값 (*) 필수항목)--------------------------------------------------
		String StoreId				= cancelParams.get("storeid").toString() ;					// *상점아이디
		String OrderNumber			= "";												// 주문번호
		String UserName				= "";												// 주문자명
		String IdNum				= "";												// 주민번호 or 사업자번호
		String Email				= "";												// email
		String GoodName				= "";												// 제품명
		String PhoneNo				= "";												// 휴대폰번호
	//Header end -------------------------------------------------------------------

	//Data Default------------------------------------------------------------------
		String ApprovalType	  = cancelParams.get("authty").toString()	;// 승인구분 코드
		String TrNo			  = cancelParams.get("trno").toString()   ;// 거래번호
		String Canc_amt		  = cancelParams.get("canc_amt").toString();	// 취소금액
		String Canc_seq		  = cancelParams.get("canc_seq").toString();	// 취소일련번호
		String Canc_type	  = cancelParams.get("canc_type").toString();	// 취소유형 0 :거래번호취소 1: 주문번호취소 3:부분취소		

	// Server로 부터 응답이 없을시 자체응답
		String rApprovalType   = "4111";                           //승인구분                     
		String rPTransactionNo = "";                               //거래번호                     
		String rPStatus        = "X";                              //상태 O : 승인 , X : 거절     
		String rPTradeDate     = "";                               //거래일자                     
		String rPTradeTime     = "";                               //거래시간                     
		String rPIssCode       = "00";                             //발급사코드                   
		String rPAuthNo        = "9999";                           //승인번호 or 거절시 오류코드  
		String rPMessage1      = "취소거절";                       	//메시지1                      
		String rPMessage2      = "C잠시후재시도";                 	 	//메시지2                      
		String rPPoint1        = "000000000000";                   //거래포인트                   
		String rPPoint2        = "000000000000";                   //가용포인트                   
		String rPPoint3        = "000000000000";                   //누적포인트                   
		String rPPoint4        = "000000000000";                   //가맹점포인트                 
		String rPMerchantNo    = "";                               //가맹점번호                   
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
				rPTransactionNo = ipg.PTransactionNo[0];   // 거래번호
				rPStatus        = ipg.PStatus[0];          // 상태 O : 승인, X : 거절
				rPTradeDate     = ipg.PTradeDate[0];       // 거래일자
				rPTradeTime     = ipg.PTradeTime[0];       // 거래시간
				rPIssCode       = ipg.PIssCode[0];         // 거래일자
				rPAuthNo        = ipg.PAuthNo[0];          // 거래시간
				rPMessage1      = ipg.PMessage1[0];        // 메시지1		
				rPMessage2      = ipg.PMessage2[0];        // 메시지2
				rPPoint1        = ipg.PPoint1[0];          // Point1
				rPPoint2        = ipg.PPoint2[0];          // Point2
				rPPoint3        = ipg.PPoint3[0];          // Point3
				rPPoint4        = ipg.PPoint4[0];          // Point4
				rPMerchantNo    = ipg.PMerchantNo[0];      // 가맹점번호
				rPNotice1       = ipg.PNotice1[0];         // PNotice1
				rPNotice2       = ipg.PNotice2[0];         // PNotice1
				rPNotice3       = ipg.PNotice3[0];         // PNotice1
				rPNotice4       = ipg.PNotice4[0];         // PNotice1
				rPFiller        = ipg.PFiller[0];          // 예비
				
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
	                parameter.put("reserve_state","CANCEL");
	                parameter.put("penalty_amt",cancelParams.get("penalty_amt"));
	                
	                String result = frontReservationService.pgCancelIns(parameter);
	                
	                if("PGCANCELOK".equals(result)){
	                	html = "예약취소가 정상적으로 이루어 졌습니다.";	
	                	
	        			//예약취소문자 발송
	        			//String contents = "[아쿠아필드]온라인 예약취소(예약번호:"+request.getParameter("ordernum")+",예약일:"+request.getParameter("reserveday")+")";
						Map smsParam = new HashMap();
						smsParam.put("point_code", "POINT01");
						smsParam.put("sms_type", "CANCEL");
						
						Map smsTemplte = commonService.getSmsTemplete(smsParam);
						String contents = smsTemplte.get("SMS_CONTENT").toString();
						contents = contents.replace("{지점}",cancelParams.get("pointNm").toString());//지점 치환
						contents = contents.replace("{번호}",cancelParams.get("ordernum").toString());//예약번호 치환
						contents = contents.replace("{예약일}",cancelParams.get("reserveday").toString());//예약일 치환		        			
	        			
	        			Map params = new HashMap();
	        			params.put("recipient_num", cancelParams.get("phoneNo")); // 수신번호
	        			//param.put("subject", "[아쿠아필드]예약취소문자입니다."); // LMS일경우 제목을 추가 할 수 있다.
	        			params.put("content", contents); // 내용 (SMS=88Byte, LMS=2000Byte)		
	        			//params.put("callback", "031-8072-8800"); // 발신번호
	        			params.put("callback", config.getProperty("sms.tel.number."+cancelParams.get("pointCode").toString())); // 발신번호
	        			
	        			if(!smsService.sendSms(params)){
	        				html = "예약취소중 에러가 발생하였습니다.(문자)";
	        			}else{
							//SMS 발송 이력 등록
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
	        			
						//메일발송 #####################################
	        			int intUid = Integer.parseInt(cancelParams.get("uid").toString());
	        			Map getReserveInfo = frontReservationService.getReserveInfo(intUid); //예약정보 가져오기
	        			
						Map emailParam = new HashMap();
						emailParam.put("email_uid", "3");
						Map joinEmail = adminEmailTempletService.adminEmailTempletDetail(emailParam);
						
						String realPath = cancelParams.get("realPath").toString();
						//String joinHtml = super.getHTMfile(realPath+"/reservation_complete.html");
						
						String joinHtml = getHTMfile(realPath+"/3");						
						//String joinHtml = super.getHTMfile(realPath+"/reservation_cancel.html");	        			

						String nowTime = AquaDateUtil.getToday();
						nowTime = nowTime.substring(0, 4)+"."+nowTime.substring(4, 6)+"."+nowTime.substring(6, 8);						
						joinHtml = joinHtml.replace("{{#NOW#}}",nowTime); // 현재시간 치환
						joinHtml = joinHtml.replace("{{#NAME#}}",getReserveInfo.get("MEM_NM").toString());//이름 치환
						joinHtml = joinHtml.replace("{{#RESERVENUM#}}",getReserveInfo.get("ORDER_NUM").toString());//예약번호 치환
						joinHtml = joinHtml.replace("{{#POINTNM#}}",getReserveInfo.get("POINT_NM").toString());//지점 치환
						joinHtml = joinHtml.replace("{{#RESERVEDAY#}}",getReserveInfo.get("RESERVE_DATE").toString());//예약일치환
						joinHtml = joinHtml.replace("{{#GOODS#}}",getReserveInfo.get("ORDER_NM").toString());//상품명치환
						
						int cnt = Integer.parseInt(getReserveInfo.get("ADULT_SUM").toString()) + Integer.parseInt(getReserveInfo.get("CHILD_SUM").toString());
						joinHtml = joinHtml.replace("{{#CNT#}}",String.valueOf(cnt));//인원수치환
						
						Map pgResultInfo = frontReservationService.pgResultInfo(getReserveInfo.get("PG_RESULT").toString());//PG정보가져오기						
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
						joinHtml = joinHtml.replace("{{#PAYTYPE#}}",r_TYPE);//결제수단치환
						
						String approvaldate = pgResultInfo.get("TR_DDT").toString();
						approvaldate = approvaldate.substring(0, 4)+"."+approvaldate.substring(4, 6)+"."+approvaldate.substring(6, 8);
						joinHtml = joinHtml.replace("{{#APPROVALDATE#}}",approvaldate);//승인일시치환
						joinHtml = joinHtml.replace("{{#PRICE#}}",getReserveInfo.get("PAYMENT_PRICE").toString());//결제금액치환   
						
						String canceldate = parameter.get("ac_tradedate").toString();
						canceldate = canceldate.substring(0, 4)+"."+canceldate.substring(4, 6)+"."+canceldate.substring(6, 8);
						joinHtml = joinHtml.replace("{{#CANCELDATE#}}",canceldate);//취소일시치환
						int intcancel = Integer.parseInt(parameter.get("ac_amount").toString());
						joinHtml = joinHtml.replace("{{#CANCELPRICE#}}", String.valueOf(intcancel));//취소금액치환   
						joinHtml = joinHtml.replace("{{#PENALTIES#}}",getReserveInfo.get("PANALTY_PRICE").toString());//위약금치환
						String reHtml= joinHtml.replace("{{#REFUND#}}",getReserveInfo.get("REFUND").toString());

						//boolean booleanresult =	mailService.sendmail(mailSender, "aquafield@shinsegae.com", "아쿠아필드", getReserveInfo.get("MEM_ID").toString(), getReserveInfo.get("MEM_NM").toString(), "[아쿠아필드]예약취소메일입니다.", reHtml);
						boolean booleanresult =	mailService.sendmail(mailSender, joinEmail.get("FROM_EMAIL").toString(), joinEmail.get("FORM_EMAIL_NM").toString(), getReserveInfo.get("MEM_ID").toString(), getReserveInfo.get("MEM_NM").toString(), joinEmail.get("EMAIL_TITLE").toString(), reHtml);

						if(!booleanresult){
							html = "처리중 에러가 발생하였습니다.(이메일)";
						}else{
							//EMAIL 발송 이력 등록
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
	                }else{
	                	html = "예약취소중 에러가 발생하였습니다.(DB INS)";
	                }
	                
				}else{
					html = rPMessage1+"\n "+rPMessage2;
				}				
			}
		}
		catch(Exception e) {
			rPMessage2			= "P잠시후재시도("+e.toString()+")";	// 메시지2
		} // end of catch
	
		return html;		
	}
		
	public int prodSum(Map object, int cnt, String param){
		
		int sum = 0;		
		if(object != null){
			sum = Integer.parseInt(object.get(""+param+"").toString()) * cnt;
		}
		
		return sum;
	}
	
	/****
	 * 
	 * 파일 읽기
	 *  
	 * 
	 */
	public String getHTMfile(String pathfile) {
	       // 버퍼 생성
        BufferedReader br = null;        
         
        // Input 스트림 생성
        InputStreamReader isr = null;    
         
        // File Input 스트림 생성
        FileInputStream fis = null;        
 
        // File 경로
        File file = new File(pathfile);
 
        // 버퍼로 읽어들일 임시 변수
        String temp = "";
         
        // 최종 내용 출력을 위한 변수
        String content = "";
         
        try {
             
            // 파일을 읽어들여 File Input 스트림 객체 생성
            fis = new FileInputStream(file);
             
            // File Input 스트림 객체를 이용해 Input 스트림 객체를 생서하는데 인코딩을 UTF-8로 지정
            isr = new InputStreamReader(fis, "UTF-8");
             
            // Input 스트림 객체를 이용하여 버퍼를 생성
            br = new BufferedReader(isr);
         
            // 버퍼를 한줄한줄 읽어들여 내용 추출
            while( (temp = br.readLine()) != null) {
                content += temp + "\n";
            }
             
        } catch (FileNotFoundException e) {
            e.printStackTrace();
             
        } catch (Exception e) {
            e.printStackTrace();
             
        } finally {
 
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
             
            try {
                isr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
             
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
             
        }
	    return content;
	}
		
}
