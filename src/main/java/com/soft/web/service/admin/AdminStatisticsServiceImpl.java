package com.soft.web.service.admin;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soft.web.dao.admin.AdminStatisticsDao;
import com.soft.web.util.Util;

@Service
public class AdminStatisticsServiceImpl implements AdminStatisticsService {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());	

	@Resource(name="config")
    private Properties config;
	
	@Autowired
	@Resource(name="sqlSession")
    protected SqlSession sqlSession;

	@Override
	public List listVisitorPeriod(Map param) {
		return sqlSession.getMapper(AdminStatisticsDao.class).listVisitorPeriod(param);
	}

	@Override
	public Map listVisitorTime(Map param) {
		return sqlSession.getMapper(AdminStatisticsDao.class).listVisitorTime(param);
	}

	@Override
	public List listVisitorDay(Map param) {
		return sqlSession.getMapper(AdminStatisticsDao.class).listVisitorDay(param);
	}

	@Override
	public List listVisitorMonth(Map param) {
		return sqlSession.getMapper(AdminStatisticsDao.class).listVisitorMonth(param);
	}

	@Override
	public List listVisitorOs(Map param) {
		return sqlSession.getMapper(AdminStatisticsDao.class).listVisitorOs(param);
	}

	@Override
	public List listVisitorDevice(Map param) {
		return sqlSession.getMapper(AdminStatisticsDao.class).listVisitorDevice(param);
	}

	@Override
	public List listVisitorBrowser(Map param) {
		return sqlSession.getMapper(AdminStatisticsDao.class).listVisitorBrowser(param);
	}

	@Override
	public List listVisitorScreen(Map param) {
		return sqlSession.getMapper(AdminStatisticsDao.class).listVisitorScreen(param);
	}

	@Override
	public int getTotalCountReferer(Map param) {
		return sqlSession.getMapper(AdminStatisticsDao.class).getTotalCountReferer(param);
	}

	@Override
	public List listVisitorReferer(Map param) {
		return sqlSession.getMapper(AdminStatisticsDao.class).listVisitorReferer(param);
	}
	
	@Override
	//유입경로 통계
	public List<Map> getRefererStatistics(Map param){
		return sqlSession.getMapper(AdminStatisticsDao.class).getRefererStatistics(param);
	}
	
	@Override
	//유입경로 통계 Cnt 
	public int refererStatisticsCnt(Map param){
		return sqlSession.getMapper(AdminStatisticsDao.class).refererStatisticsCnt(param);		
	}
	
	@Override
	//전체주문데이터 Cnt 
	public int reservationListCnt(Map param){
		return sqlSession.getMapper(AdminStatisticsDao.class).reservationListCnt(param);		
	}
	
	@Override
	//전체주문데이터 List 
	public List reservationList(Map param){
		List<Map> reservationList = null;
		
		try {
			reservationList = Util.setListKoConvert(sqlSession.getMapper(AdminStatisticsDao.class).reservationList(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reservationList;
	}
	
	@Override
	//전체주문데이터 List All(Excel 용)
	public List reservationListAll(Map param){
		List<Map> reservationListAll = null;
		try {
			//param.put("category",Util.convertStringToHex((String)param.get("category")));
			//param.put("mem_nm",Util.convertStringToHex((String)param.get("mem_nm")));
			reservationListAll = Util.setListKoConvert(sqlSession.getMapper(AdminStatisticsDao.class).reservationListAll(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reservationListAll;
	}
	
	@Override	
	//결제 정보 가져오기
	public List<Map> itemlist(Map param){
		List<Map> itemlist = null;
		try {
			itemlist =  Util.setListKoConvert(sqlSession.getMapper(AdminStatisticsDao.class).itemlist(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return itemlist;
	}
	
	@Override
	//총괄장 가져오기
	public List<Map<String, Object>> getGeneralData(Map param){
		List<Map<String, Object>> generalData = new ArrayList<Map<String, Object>>();
		try {
			Map map = new HashMap();
			map.put("TOT_CNT", 0);
			map.put("TOT_NUM", 0);
			map.put("PAY_SSG", 0);
			map.put("PAY_CREDIT", 0);
			map.put("PAY_BANK", 0);
			map.put("TOT_PAY", 0);
			map.put("REFUND_SSG", 0);
			map.put("REFUND_CREDIT", 0);
			map.put("REFUND_BANK", 0);
			map.put("TOT_REFUND", 0);
			map.put("PANALTY_SSG", 0);
			map.put("PANALTY_CREDIT", 0);
			map.put("PANALTY_BANK", 0);
			map.put("TOT_PANALTY", 0);
			
			Map<String, Object> spaList = Util.setMapKoConvert(sqlSession.getMapper(AdminStatisticsDao.class).getSpaGeneralData(param),config.getProperty("character.set"));
			if(spaList != null && spaList.size() > 0){
				generalData.add(spaList);
			}else{
				generalData.add(map);
			}
			Map waterList = Util.setMapKoConvert(sqlSession.getMapper(AdminStatisticsDao.class).getWaterGeneralData(param),config.getProperty("character.set"));
			if(waterList != null && waterList.size() > 0){
				generalData.add(waterList);
			}else{
				
				generalData.add(map);
			}
			Map complexList = Util.setMapKoConvert(sqlSession.getMapper(AdminStatisticsDao.class).getComplexGeneralData(param),config.getProperty("character.set"));
			if(complexList != null && complexList.size() > 0){
				generalData.add(complexList);
			}else{
				generalData.add(map);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return generalData;
	}
	
}
