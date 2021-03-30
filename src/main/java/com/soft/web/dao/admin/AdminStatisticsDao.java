package com.soft.web.dao.admin;

import java.util.List;
import java.util.Map;

public interface AdminStatisticsDao {
	public List listVisitorPeriod(Map param);	
	public Map listVisitorTime(Map param);	
	public List listVisitorDay(Map param);
	public List listVisitorMonth(Map param);
	public List listVisitorOs(Map param);
	public List listVisitorDevice(Map param);
	public List listVisitorBrowser(Map param);
	public List listVisitorScreen(Map param);	
	public int getTotalCountReferer(Map param);
	public List listVisitorReferer(Map param);
	
	//매출(상품별)통계
	public List<Map> getSaleStatistics(Map param);
	
	//유입경로 통계
	public List<Map> getRefererStatistics(Map param);
	
	//유입경로 통계 Cnt 
	public int refererStatisticsCnt(Map param);

	//전체주문데이터 Cnt 
	public int reservationListCnt(Map param);

	//전체주문데이터 List 
	public List reservationList(Map param);

	//전체주문데이터 List 
	public List reservationListAll(Map param);
	
	//이용시설 정보 가져오기
	public List<Map> itemlist(Map param);
	
	public Map getSpaGeneralData(Map param);
	
	public Map getWaterGeneralData(Map param);
	
	public Map getComplexGeneralData(Map param);
	
}
