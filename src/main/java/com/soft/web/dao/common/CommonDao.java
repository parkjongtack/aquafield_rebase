package com.soft.web.dao.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface CommonDao {

	public List<Map> catelist(Map param);
	
	public List<Map> commonlist(Map param);
	
	//FAQ 리스트
	public List<Map> faqlist(Map param);
	
	//SMS 발송 이력 등록
	public void insSmsSend(Map param);

	//SMS 템플릿 가져오기
	public Map getSmsTemplete(Map param); 
	
	//SMS 1번 더 발송하기
	public int sendSmsCnt(Map param);
	
	//EMAIL 발송 이력 등록
	public void insEmailSend(Map param);
	
	//ADMIN 사용기록 INSERT
	public void insAdminContentLog(Map param);
	
	//관리자 메뉴 가져오기
	public Map getAdminMenu(String param); 	
	
	//지점 정보 가져오기
	public Map getPointInfo(String param); 	
	
	//결제 정보 가져오기
	public List<Map> payList();
	
}
