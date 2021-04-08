package com.soft.web.dao.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface CommonBatchDao {

	//예약 미사용 업데이트
	public void batchNoUseUpd();
	
	//1일전 SMS 알림
	public List<Map> beforeOneDaySmslist();
	
	//미사용 10% 위약금 자동취소 예약리스트 가져오기
	public List<Map> batchCancelList();
	
	//휴면계정 7일전 mail발송
	public List<Map> beforeSevenDayMailList();
	
	//마케팅 동의 메일 발송
	public List<Map> marketAgreeMail();	
	
	//휴면계정 INSERT
	public void inactMemIns(Map param);
	
	//휴면개정 UPD
	public void inactMemUpd();
}
