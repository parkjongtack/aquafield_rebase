package com.soft.web.service.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CommonService {

	public List<Map> catelist(Map param);
	
	public List<Map> commonlist(Map param);
	
	//FAQ 리스트
	public List<Map> faqlist(Map param);
	
	//예약 미사용 업데이트
	public String batchNoUseUpd();
	
	//예약 자동취소 리스트
	public List<Map> batchCancelList();
	
	//1일전 SMS 알림
	public List<Map> beforeOneDaySmslist();
	
	//SMS 발송 이력 등록
	public String insSmsSend(Map param);
	
	//SMS 템플릿 가져오기
	public Map getSmsTemplete(Map param);
	
	//SMS 1번 더 발송하기
	public int sendSmsCnt(Map param); 
	
	//Email 발송 이력 등록
	public String insEmailSend(Map param);
	
	//휴면계정 7일전 mail발송
	public List<Map> beforeSevenDayMailList();
	
	//휴명계정 INSERT
	public String inactMemIns(Map param);
	
	//휴면개정 UPD
	public String inactMemUpd();
	
	//ADMIN 사용기록 INSERT
	public String insAdminContentLog(Map param);
	
	//관리자 메뉴 가져오기
	public Map getAdminMenu(String param); 	
	
	//지점 정보 가져오기
	public Map getPointInfo(String param);

	//결제 정보 가져오기
	public List<Map> payList();
	
}
