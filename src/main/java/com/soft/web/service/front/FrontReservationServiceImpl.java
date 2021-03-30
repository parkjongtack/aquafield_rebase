package com.soft.web.service.front;

import java.io.UnsupportedEncodingException;
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
import org.springframework.transaction.annotation.Transactional;

import com.soft.web.base.Cryptography;
import com.soft.web.base.PasswordEncoder;
import com.soft.web.dao.front.FrontReservationDao;
import com.soft.web.sms.SmsService;
import com.soft.web.util.DecoderUtil;
import com.soft.web.util.Util;


@Service
public class FrontReservationServiceImpl implements FrontReservationService {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name="config")
    private Properties config;
	
	@Autowired
	@Resource(name="sqlSession")
    protected SqlSession sqlSession;
	
	@Autowired
	SmsService smsService;
	
	//�쑕臾댁씪 �뀑�똿
    @Override
	public List<Map> emptyDayList(Map param){
    	
    	List<Map> emptyDayList = null;
		try {
			emptyDayList =  Util.setListKoConvert(sqlSession.getMapper(FrontReservationDao.class).emptyDayList(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return emptyDayList;
	}
	
	//SEASON DAY �뀑�똿
    @Override
	public List<Map> seasonDayList(Map param){
    	List<Map> seasonDayList = null;
    	try {
			seasonDayList = Util.setListKoConvert(sqlSession.getMapper(FrontReservationDao.class).seasonDayList(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return seasonDayList;
	}
	
	//�긽�뭹�젙蹂�
    @Override
	public List<Map> prodInfolist(Map param){
    	List<Map> prodInfolist = null;
    	
    	try {
			prodInfolist = Util.setListKoConvert(sqlSession.getMapper(FrontReservationDao.class).prodInfolist(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return prodInfolist;
	}
	
	//RESERVE DAY
    @Override
	public List<Map> reserveDayList(Map param){
    	List<Map> reserveDayList = null;
    	try {
			reserveDayList = Util.setListKoConvert(sqlSession.getMapper(FrontReservationDao.class).reserveDayList(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return reserveDayList;
	}
    
	//�긽�뭹 cnt 泥댄겕
    @Override
	public int prodInfoCnt(Map param){
    	return sqlSession.getMapper(FrontReservationDao.class).prodInfoCnt(param);
    }
    
	//�삁�빟踰덊샇 以묐났�솗�씤
    @Override
	public int getReserveNumCnt(String orderNum){
    	return sqlSession.getMapper(FrontReservationDao.class).getReserveNumCnt(orderNum);
    }
    
    @Override
	//�삁�빟踰덊샇 媛��졇�삤湲�
	public String getReserveNum(String date){
    	String orderNum = "";
    	orderNum = sqlSession.getMapper(FrontReservationDao.class).getReserveNum(date);
    	while(getReserveNumCnt(orderNum) > 0){
    		orderNum = sqlSession.getMapper(FrontReservationDao.class).getReserveNum(date);
    	}
    	return orderNum;
    }
    
    @Override
	@Transactional    
	public String reserVationIns(Map param){
		String result="";
		try {
			param.put("mem_nm", Util.convertStringToHex((String)param.get("mem_nm")));
			param.put("payment_nm", Util.convertStringToHex((String)param.get("payment_nm")));
			param.put("order_nm", Util.convertStringToHex((String)param.get("order_nm")));				
            param.put("spa_item_nm", Util.convertStringToHex((String)param.get("spa_item_nm")));
            param.put("water_item_nm", Util.convertStringToHex((String)param.get("water_item_nm")));
            param.put("complex_item_nm", Util.convertStringToHex((String)param.get("complex_item_nm")));            
            param.put("rental1_item_nm", Util.convertStringToHex((String)param.get("rental1_item_nm")));	
            param.put("rental2_item_nm", Util.convertStringToHex((String)param.get("rental2_item_nm")));	
            param.put("rental3_item_nm", Util.convertStringToHex((String)param.get("rental3_item_nm")));	
            param.put("event1_item_nm", Util.convertStringToHex((String)param.get("event1_item_nm")));	
            param.put("event2_item_nm", Util.convertStringToHex((String)param.get("event2_item_nm")));	
            param.put("event3_item_nm", Util.convertStringToHex((String)param.get("event3_item_nm")));		
					
			sqlSession.getMapper(FrontReservationDao.class).reserVationIns(param);
			
			int reserveuid = (int) param.get("reserveuid");

			if(!"".equals(param.get("spa_item")) && param.get("spa_item") != null){
				Map params = new HashMap();
				int uid = Integer.parseInt(param.get("spa_item").toString());
				
				//�삁�빟 �긽�꽭 �젙蹂� �뀒�씠釉� INSERT
				Map getItemInfo = sqlSession.getMapper(FrontReservationDao.class).getItemInfo(String.valueOf(uid));
				
				int intAdWon = Integer.parseInt(getItemInfo.get("ADULTS_PRICE").toString());
				int intChWon = Integer.parseInt(getItemInfo.get("CHILD_PRICE").toString());
				int intAdManCnt = Integer.parseInt(param.get("spa_adult_man").toString());
				int intAdWomenCnt = Integer.parseInt(param.get("spa_adult_women").toString());
				int intChManCnt = Integer.parseInt(param.get("spa_child_man").toString());
				int intChWomenCnt = Integer.parseInt(param.get("spa_child_women").toString());
				int intAdManSum = intAdWon * intAdManCnt;
				int intAdWomenSum = intAdWon * intAdWomenCnt;
				int intChManSum = intChWon * intChManCnt;
				int intChWomenSum = intChWon * intChWomenCnt;
				
				params.put("reserveuid", reserveuid);
				params.put("item_uid", getItemInfo.get("SET_UID"));
				params.put("item_code", getItemInfo.get("CATE_CODE"));
				params.put("item_subcode", getItemInfo.get("ITEM_CODE"));
				params.put("item_nm", getItemInfo.get("ITEM_NM"));								  
				params.put("ad_man_w", intAdWon);
				params.put("ad_women_w", intAdWon);
				params.put("ch_man_w", intChWon);
				params.put("ch_women_w", intChWon);
				params.put("ad_man_cnt", intAdManCnt);
				params.put("ad_women_cnt", intAdWomenCnt);
				params.put("ch_man_cnt", intChManCnt);
				params.put("ch_women_cnt", intChWomenCnt);							  
				params.put("item_w", 0);
				params.put("item_cnt", 0);
				params.put("ad_man_sum", intAdManSum);
				params.put("ad_women_sum", intAdWomenSum);
				params.put("ch_man_sum", intChManSum);
				params.put("ch_women_sum", intChWomenSum);
				params.put("item_sum", 0);				
				
				String detailResult = reserveDetailIns(params);
				
				if("ERROR".equals(detailResult)){
					result = "ERROR";
				}
			}
			
			if(!"".equals(param.get("water_item")) && param.get("water_item") != null){
				Map params = new HashMap();				
				int uid = Integer.parseInt(param.get("water_item").toString());

				//�삁�빟 �긽�꽭 �젙蹂� �뀒�씠釉� INSERT
				Map getItemInfo = sqlSession.getMapper(FrontReservationDao.class).getItemInfo(String.valueOf(uid));
				
				int intAdWon = Integer.parseInt(getItemInfo.get("ADULTS_PRICE").toString());
				int intChWon = Integer.parseInt(getItemInfo.get("CHILD_PRICE").toString());
				int intAdManCnt = Integer.parseInt(param.get("water_adult_man").toString());
				int intAdWomenCnt = Integer.parseInt(param.get("water_adult_women").toString());
				int intChManCnt = Integer.parseInt(param.get("water_child_man").toString());
				int intChWomenCnt = Integer.parseInt(param.get("water_child_women").toString());
				int intAdManSum = intAdWon * intAdManCnt;
				int intAdWomenSum = intAdWon * intAdWomenCnt;
				int intChManSum = intChWon * intChManCnt;
				int intChWomenSum = intChWon * intChWomenCnt;				
				
				params.put("reserveuid", reserveuid);
				params.put("item_uid", getItemInfo.get("SET_UID"));
				params.put("item_code", getItemInfo.get("CATE_CODE"));
				params.put("item_subcode", getItemInfo.get("ITEM_CODE"));
				params.put("item_nm", getItemInfo.get("ITEM_NM"));								  
				params.put("ad_man_w", intAdWon);
				params.put("ad_women_w", intAdWon);
				params.put("ch_man_w", intChWon);
				params.put("ch_women_w", intChWon);
				params.put("ad_man_cnt", intAdManCnt);
				params.put("ad_women_cnt", intAdWomenCnt);
				params.put("ch_man_cnt", intChManCnt);
				params.put("ch_women_cnt", intChWomenCnt);							  
				params.put("item_w", 0);
				params.put("item_cnt", 0);
				params.put("ad_man_sum", intAdManSum);
				params.put("ad_women_sum", intAdWomenSum);
				params.put("ch_man_sum", intChManSum);
				params.put("ch_women_sum", intChWomenSum);
				params.put("item_sum", 0);
				
				String detailResult = reserveDetailIns(params);
				
				if("ERROR".equals(detailResult)){
					result = "ERROR";
				}
			}
			
			if(!"".equals(param.get("complex_item")) && param.get("complex_item") != null){
				Map params = new HashMap();				
				int uid = Integer.parseInt(param.get("complex_item").toString());

				//�삁�빟 �긽�꽭 �젙蹂� �뀒�씠釉� INSERT
				Map getItemInfo = sqlSession.getMapper(FrontReservationDao.class).getItemInfo(String.valueOf(uid));
				
				int intAdWon = Integer.parseInt(getItemInfo.get("ADULTS_PRICE").toString());
				int intChWon = Integer.parseInt(getItemInfo.get("CHILD_PRICE").toString());
				int intAdManCnt = Integer.parseInt(param.get("complex_adult_man").toString());
				int intAdWomenCnt = Integer.parseInt(param.get("complex_adult_women").toString());
				int intChManCnt = Integer.parseInt(param.get("complex_child_man").toString());
				int intChWomenCnt = Integer.parseInt(param.get("complex_child_women").toString());
				int intAdManSum = intAdWon * intAdManCnt;
				int intAdWomenSum = intAdWon * intAdWomenCnt;
				int intChManSum = intChWon * intChManCnt;
				int intChWomenSum = intChWon * intChWomenCnt;				
				
				params.put("reserveuid", reserveuid);
				params.put("item_uid", getItemInfo.get("SET_UID"));
				params.put("item_code", getItemInfo.get("CATE_CODE"));
				params.put("item_subcode", getItemInfo.get("ITEM_CODE"));
				params.put("item_nm", getItemInfo.get("ITEM_NM"));								  
				params.put("ad_man_w", intAdWon);
				params.put("ad_women_w", intAdWon);
				params.put("ch_man_w", intChWon);
				params.put("ch_women_w", intChWon);
				params.put("ad_man_cnt", intAdManCnt);
				params.put("ad_women_cnt", intAdWomenCnt);
				params.put("ch_man_cnt", intChManCnt);
				params.put("ch_women_cnt", intChWomenCnt);							  
				params.put("item_w", 0);
				params.put("item_cnt", 0);
				params.put("ad_man_sum", intAdManSum);
				params.put("ad_women_sum", intAdWomenSum);
				params.put("ch_man_sum", intChManSum);
				params.put("ch_women_sum", intChWomenSum);
				params.put("item_sum", 0);
				
				String detailResult = reserveDetailIns(params);
				
				if("ERROR".equals(detailResult)){
					result = "ERROR";
				}
			}			
			
			if(!"".equals(param.get("rental1_item")) && param.get("rental1_item") != null){
				Map params = new HashMap();
				int uid = Integer.parseInt(param.get("rental1_item").toString());

				//�삁�빟 �긽�꽭 �젙蹂� �뀒�씠釉� INSERT
				Map getItemInfo = sqlSession.getMapper(FrontReservationDao.class).getItemInfo(String.valueOf(uid));
				
				int intRentalWon = Integer.parseInt(getItemInfo.get("RENTAL_PRICE").toString());
				int intRentalCnt = Integer.parseInt(param.get("rental1_cnt").toString());
				int intRentalSum = intRentalWon * intRentalCnt;
				
				params.put("reserveuid", reserveuid);
				params.put("item_uid", getItemInfo.get("SET_UID"));
				params.put("item_code", getItemInfo.get("CATE_CODE"));
				params.put("item_subcode", getItemInfo.get("ITEM_CODE"));
				params.put("item_nm", getItemInfo.get("ITEM_NM"));								  
				params.put("ad_man_w", 0);
				params.put("ad_women_w", 0);
				params.put("ch_man_w", 0);
				params.put("ch_women_w", 0);
				params.put("ad_man_cnt", 0);
				params.put("ad_women_cnt", 0);
				params.put("ch_man_cnt", 0);
				params.put("ch_women_cnt", 0);							  
				params.put("item_w", intRentalWon);
				params.put("item_cnt", intRentalCnt);
				params.put("ad_man_sum", 0);
				params.put("ad_women_sum", 0);
				params.put("ch_man_sum", 0);
				params.put("ch_women_sum", 0);
				params.put("item_sum", intRentalSum);				
				
				String detailResult = reserveDetailIns(params);
				
				if("ERROR".equals(detailResult)){
					result = "ERROR";
				}
			}
			
			if(!"".equals(param.get("rental2_item")) && param.get("rental2_item") != null){
				Map params = new HashMap();
				int uid = Integer.parseInt(param.get("rental2_item").toString());

				//�삁�빟 �긽�꽭 �젙蹂� �뀒�씠釉� INSERT
				Map getItemInfo = sqlSession.getMapper(FrontReservationDao.class).getItemInfo(String.valueOf(uid));
				
				int intRentalWon = Integer.parseInt(getItemInfo.get("RENTAL_PRICE").toString());
				int intRentalCnt = Integer.parseInt(param.get("rental2_cnt").toString());
				int intRentalSum = intRentalWon * intRentalCnt;
				
				params.put("reserveuid", reserveuid);
				params.put("item_uid", getItemInfo.get("SET_UID"));
				params.put("item_code", getItemInfo.get("CATE_CODE"));
				params.put("item_subcode", getItemInfo.get("ITEM_CODE"));
				params.put("item_nm", getItemInfo.get("ITEM_NM"));								  
				params.put("ad_man_w", 0);
				params.put("ad_women_w", 0);
				params.put("ch_man_w", 0);
				params.put("ch_women_w", 0);
				params.put("ad_man_cnt", 0);
				params.put("ad_women_cnt", 0);
				params.put("ch_man_cnt", 0);
				params.put("ch_women_cnt", 0);							  
				params.put("item_w", intRentalWon);
				params.put("item_cnt", intRentalCnt);
				params.put("ad_man_sum", 0);
				params.put("ad_women_sum", 0);
				params.put("ch_man_sum", 0);
				params.put("ch_women_sum", 0);
				params.put("item_sum", intRentalSum);
				
				String detailResult = reserveDetailIns(params);
				
				if("ERROR".equals(detailResult)){
					result = "ERROR";
				}
			}
			
			if(!"".equals(param.get("rental3_item")) && param.get("rental3_item") != null){
				Map params = new HashMap();
				int uid = Integer.parseInt(param.get("rental3_item").toString());

				//�삁�빟 �긽�꽭 �젙蹂� �뀒�씠釉� INSERT
				Map getItemInfo = sqlSession.getMapper(FrontReservationDao.class).getItemInfo(String.valueOf(uid));
				
				int intRentalWon = Integer.parseInt(getItemInfo.get("RENTAL_PRICE").toString());
				int intRentalCnt = Integer.parseInt(param.get("rental3_cnt").toString());
				int intRentalSum = intRentalWon * intRentalCnt;
				
				params.put("reserveuid", reserveuid);
				params.put("item_uid", getItemInfo.get("SET_UID"));
				params.put("item_code", getItemInfo.get("CATE_CODE"));
				params.put("item_subcode", getItemInfo.get("ITEM_CODE"));
				params.put("item_nm", getItemInfo.get("ITEM_NM"));								  
				params.put("ad_man_w", 0);
				params.put("ad_women_w", 0);
				params.put("ch_man_w", 0);
				params.put("ch_women_w", 0);
				params.put("ad_man_cnt", 0);
				params.put("ad_women_cnt", 0);
				params.put("ch_man_cnt", 0);
				params.put("ch_women_cnt", 0);							  
				params.put("item_w", intRentalWon);
				params.put("item_cnt", intRentalCnt);
				params.put("ad_man_sum", 0);
				params.put("ad_women_sum", 0);
				params.put("ch_man_sum", 0);
				params.put("ch_women_sum", 0);
				params.put("item_sum", intRentalSum);
				
				String detailResult = reserveDetailIns(params);
				
				if("ERROR".equals(detailResult)){
					result = "ERROR";
				}
			}
			
			if(!"".equals(param.get("event1_item")) && param.get("event1_item") != null){
				Map params = new HashMap();
				int uid = Integer.parseInt(param.get("event1_item").toString());

				//�삁�빟 �긽�꽭 �젙蹂� �뀒�씠釉� INSERT
				Map getItemInfo = sqlSession.getMapper(FrontReservationDao.class).getItemInfo(String.valueOf(uid));
				
				int intEventWon = Integer.parseInt(getItemInfo.get("RENTAL_PRICE").toString());
				int intEventCnt = Integer.parseInt(param.get("event1_cnt").toString());
				int intEventSum = intEventWon * intEventCnt;
				
				params.put("reserveuid", reserveuid);
				params.put("item_uid", getItemInfo.get("SET_UID"));
				params.put("item_code", getItemInfo.get("CATE_CODE"));
				params.put("item_subcode", getItemInfo.get("ITEM_CODE"));
				params.put("item_nm", getItemInfo.get("ITEM_NM"));								  
				params.put("ad_man_w", 0);
				params.put("ad_women_w", 0);
				params.put("ch_man_w", 0);
				params.put("ch_women_w", 0);
				params.put("ad_man_cnt", 0);
				params.put("ad_women_cnt", 0);
				params.put("ch_man_cnt", 0);
				params.put("ch_women_cnt", 0);							  
				params.put("item_w", intEventWon);
				params.put("item_cnt", intEventCnt);
				params.put("ad_man_sum", 0);
				params.put("ad_women_sum", 0);
				params.put("ch_man_sum", 0);
				params.put("ch_women_sum", 0);
				params.put("item_sum", intEventSum);
				
				String detailResult = reserveDetailIns(params);
				
				if("ERROR".equals(detailResult)){
					result = "ERROR";
				}
			}
			
			if(!"".equals(param.get("event2_item")) && param.get("event2_item") != null){
				Map params = new HashMap();
				int uid = Integer.parseInt(param.get("event2_item").toString());

				//�삁�빟 �긽�꽭 �젙蹂� �뀒�씠釉� INSERT
				Map getItemInfo = sqlSession.getMapper(FrontReservationDao.class).getItemInfo(String.valueOf(uid));
				
				int intEventWon = Integer.parseInt(getItemInfo.get("RENTAL_PRICE").toString());
				int intEventCnt = Integer.parseInt(param.get("event2_cnt").toString());
				int intEventSum = intEventWon * intEventCnt;
				
				params.put("reserveuid", reserveuid);
				params.put("item_uid", getItemInfo.get("SET_UID"));
				params.put("item_code", getItemInfo.get("CATE_CODE"));
				params.put("item_subcode", getItemInfo.get("ITEM_CODE"));
				params.put("item_nm", getItemInfo.get("ITEM_NM"));								  
				params.put("ad_man_w", 0);
				params.put("ad_women_w", 0);
				params.put("ch_man_w", 0);
				params.put("ch_women_w", 0);
				params.put("ad_man_cnt", 0);
				params.put("ad_women_cnt", 0);
				params.put("ch_man_cnt", 0);
				params.put("ch_women_cnt", 0);							  
				params.put("item_w", intEventWon);
				params.put("item_cnt", intEventCnt);
				params.put("ad_man_sum", 0);
				params.put("ad_women_sum", 0);
				params.put("ch_man_sum", 0);
				params.put("ch_women_sum", 0);
				params.put("item_sum", intEventSum);
				
				String detailResult = reserveDetailIns(params);
				
				if("ERROR".equals(detailResult)){
					result = "ERROR";
				}
			}
			
			if(!"".equals(param.get("event3_item")) && param.get("event3_item") != null){
				Map params = new HashMap();
				int uid = Integer.parseInt(param.get("event3_item").toString());

				//�삁�빟 �긽�꽭 �젙蹂� �뀒�씠釉� INSERT
				Map getItemInfo = sqlSession.getMapper(FrontReservationDao.class).getItemInfo(String.valueOf(uid));
				
				int intEventWon = Integer.parseInt(getItemInfo.get("RENTAL_PRICE").toString());
				int intEventCnt = Integer.parseInt(param.get("event3_cnt").toString());
				int intEventSum = intEventWon * intEventCnt;
				
				params.put("reserveuid", reserveuid);
				params.put("item_uid", getItemInfo.get("SET_UID"));
				params.put("item_code", getItemInfo.get("CATE_CODE"));
				params.put("item_subcode", getItemInfo.get("ITEM_CODE"));
				params.put("item_nm", getItemInfo.get("ITEM_NM"));								  
				params.put("ad_man_w", 0);
				params.put("ad_women_w", 0);
				params.put("ch_man_w", 0);
				params.put("ch_women_w", 0);
				params.put("ad_man_cnt", 0);
				params.put("ad_women_cnt", 0);
				params.put("ch_man_cnt", 0);
				params.put("ch_women_cnt", 0);							  
				params.put("item_w", intEventWon);
				params.put("item_cnt", intEventCnt);
				params.put("ad_man_sum", 0);
				params.put("ad_women_sum", 0);
				params.put("ch_man_sum", 0);
				params.put("ch_women_sum", 0);
				params.put("item_sum", intEventSum);
				
				String detailResult = reserveDetailIns(params);
				
				if("ERROR".equals(detailResult)){
					result = "ERROR";
				}
			}			
					
			result = "RESERVEOK";
			
		} catch (Exception e) {
			// TODO: handle exception
			result = "ERROR";
		}
		return result;    	
    }
    
    @Override
	//媛�寃⑹젙蹂� 媛��졇�삤湲�
	public Map getItemInfo(String param){
    	Map getItemInfo = null;
    	try {
			getItemInfo = Util.setMapKoConvert(sqlSession.getMapper(FrontReservationDao.class).getItemInfo(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return getItemInfo;
	}
    
    @Override
	//�삁�빟�젙蹂� 由ъ뒪�듃
	public List<Map> getReservelist(Map param){
    	List<Map> getReservelist = null;
    	try {
			getReservelist = Util.setListKoConvert(sqlSession.getMapper(FrontReservationDao.class).getReservelist(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return getReservelist;
    }
    
    @Override
	//�삁�빟 �긽�뭹 Qty �젙蹂�
	public int itemQtyInfo(int param){
    	return sqlSession.getMapper(FrontReservationDao.class).itemQtyInfo(param);
    }
	
	@Override
	@Transactional 	
    //�삁�빟 �긽�뭹 Qty �뾽�뜲�씠�듃
	public void itemQtyUpd(Map param){
		sqlSession.getMapper(FrontReservationDao.class).itemQtyUpd(param);		
	}
	
	public void itemQtyUpdMinus(Map param){
		sqlSession.getMapper(FrontReservationDao.class).itemQtyUpdMinus(param);		
	}	
	
	@Override
	//�삁�빟 �젙蹂� 媛��졇�삤湲�
	public Map getReserveInfo(int param){
		Map getReserveInfo = null;
		try {
			getReserveInfo = Util.setMapKoConvert(sqlSession.getMapper(FrontReservationDao.class).getReserveInfo(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getReserveInfo;
	}
	
	@Override
	//�삁�빟 �젙蹂� 媛��졇�삤湲� new
	public Map getReserveInfoNew(Map param){
		Map getReserveInfo = null;
		try {
			getReserveInfo = Util.setMapKoConvert(sqlSession.getMapper(FrontReservationDao.class).getReserveInfoNew(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getReserveInfo;
	}
	
	@Override
	@Transactional
	//PG 寃곗젣 �젙蹂� INSERT
	public Map pgResultIns(Map param){
		Map params = new HashMap();
		int uid = -1;
		String result ="";
		try {		
			
            params.put("msg1", Util.convertStringToHex((String)param.get("msg1")));
            params.put("msg2", Util.convertStringToHex((String)param.get("msg2")));
			sqlSession.getMapper(FrontReservationDao.class).pgResultIns(param);			
			uid = Integer.parseInt(param.get("uid").toString());
			
			result = "PGOK";
		} catch (Exception e) {
			// TODO: handle exception
			result = "ERROR";
		}
		
		params.put("UID", uid);
		params.put("RESULT", result);		
		return params; 		
	}
	
	@Override
	//PG RESULT �젙蹂� 媛��졇�삤湲�
	public Map pgResultInfo(String param){
		Map pgResultInfo = null;
		try {
			pgResultInfo = Util.setMapKoConvert(sqlSession.getMapper(FrontReservationDao.class).pgResultInfo(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pgResultInfo;
	}
	
	@Override
	@Transactional
	//PG 寃곗젣 痍⑥냼 INSERT
	public String pgCancelIns(Map param){
		String result ="";
		try {
		
			param.put("ac_message1",Util.convertStringToHex((String)param.get("ac_message1")));
			param.put("ac_message2",Util.convertStringToHex((String)param.get("ac_message2")));

			//PG 痍⑥냼
			sqlSession.getMapper(FrontReservationDao.class).pgCancelIns(param);	
			//�삁�빟 �뾽�뜲�씠�듃
			sqlSession.getMapper(FrontReservationDao.class).reserveCancelUpd(param);	
			//ITEM QTY �뾽�뜲�씠�듃
			int reserveuid = Integer.parseInt(param.get("reserve_uid").toString());
			
			Map reserveInfo = sqlSession.getMapper(FrontReservationDao.class).getReserveInfo(reserveuid);
			
			if(!"".equals(reserveInfo.get("SPA_ITEM")) && reserveInfo.get("SPA_ITEM") != null){
				int uid = Integer.parseInt(reserveInfo.get("SPA_ITEM").toString());
				int spaSumCnt = Integer.parseInt(reserveInfo.get("SPA_ADULT_MAN").toString()) +  Integer.parseInt(reserveInfo.get("SPA_ADULT_WOMEN").toString())
						+ Integer.parseInt(reserveInfo.get("SPA_CHILD_MAN").toString()) + Integer.parseInt(reserveInfo.get("SPA_CHILD_WOMEN").toString());
				int realQty = sqlSession.getMapper(FrontReservationDao.class).itemQtyInfo(uid);
				int updQty = realQty + spaSumCnt;
				
				Map params = new HashMap();
				params.put("quantity", updQty);
				params.put("set_uid", uid);
						
				//sqlSession.getMapper(FrontReservationDao.class).itemQtyUpd(params);
			}
			
			if(!"".equals(reserveInfo.get("WATER_ITEM")) && reserveInfo.get("WATER_ITEM") != null){
				int uid = Integer.parseInt(reserveInfo.get("WATER_ITEM").toString());
				int waterSumCnt = Integer.parseInt(reserveInfo.get("WATER_ADULT_MAN").toString()) +  Integer.parseInt(reserveInfo.get("WATER_ADULT_WOMEN").toString())
						+ Integer.parseInt(reserveInfo.get("WATER_CHILD_MAN").toString()) + Integer.parseInt(reserveInfo.get("WATER_CHILD_WOMEN").toString());
				int realQty = sqlSession.getMapper(FrontReservationDao.class).itemQtyInfo(uid);
				int updQty = realQty + waterSumCnt;
				
				Map params = new HashMap();
				params.put("quantity", updQty);
				params.put("set_uid", uid);
						
				//sqlSession.getMapper(FrontReservationDao.class).itemQtyUpd(params);
			}
			
			if(!"".equals(reserveInfo.get("COMPLEX_ITEM")) && reserveInfo.get("COMPLEX_ITEM") != null){
				int uid = Integer.parseInt(reserveInfo.get("COMPLEX_ITEM").toString());
				int complexSumCnt = Integer.parseInt(reserveInfo.get("COMPLEX_ADULT_MAN").toString()) +  Integer.parseInt(reserveInfo.get("COMPLEX_ADULT_WOMEN").toString())
						+ Integer.parseInt(reserveInfo.get("COMPLEX_CHILD_MAN").toString()) + Integer.parseInt(reserveInfo.get("COMPLEX_CHILD_WOMEN").toString());
				int realQty = sqlSession.getMapper(FrontReservationDao.class).itemQtyInfo(uid);
				int updQty = realQty + complexSumCnt;
				
				Map params = new HashMap();
				params.put("quantity", updQty);
				params.put("set_uid", uid);
						
				//sqlSession.getMapper(FrontReservationDao.class).itemQtyUpd(params);
			}			
			
			if(!"".equals(reserveInfo.get("RENTAL1_ITEM")) && reserveInfo.get("RENTAL1_ITEM") != null){
				int uid = Integer.parseInt(reserveInfo.get("RENTAL1_ITEM").toString());
				int rentalSumCnt = Integer.parseInt(reserveInfo.get("RENTAL1_CNT").toString());
				int realQty = sqlSession.getMapper(FrontReservationDao.class).itemQtyInfo(uid);
				int updQty = realQty + rentalSumCnt;
				
				Map params = new HashMap();
				params.put("quantity", updQty);
				params.put("set_uid", uid);
						
				//sqlSession.getMapper(FrontReservationDao.class).itemQtyUpd(params);
			}
			
			if(!"".equals(reserveInfo.get("RENTAL2_ITEM")) && reserveInfo.get("RENTAL2_ITEM") != null){
				int uid = Integer.parseInt(reserveInfo.get("RENTAL2_ITEM").toString());
				int rentalSumCnt = Integer.parseInt(reserveInfo.get("RENTAL2_CNT").toString());
				int realQty = sqlSession.getMapper(FrontReservationDao.class).itemQtyInfo(uid);
				int updQty = realQty + rentalSumCnt;
				
				Map params = new HashMap();
				params.put("quantity", updQty);
				params.put("set_uid", uid);
						
				//sqlSession.getMapper(FrontReservationDao.class).itemQtyUpd(params);
			}
			
			if(!"".equals(reserveInfo.get("RENTAL3_ITEM")) && reserveInfo.get("RENTAL3_ITEM") != null){
				int uid = Integer.parseInt(param.get("RENTAL3_ITEM").toString());
				int rentalSumCnt = Integer.parseInt(reserveInfo.get("RENTAL3_CNT").toString());
				int realQty = sqlSession.getMapper(FrontReservationDao.class).itemQtyInfo(uid);
				int updQty = realQty + rentalSumCnt;
				
				Map params = new HashMap();
				params.put("quantity", updQty);
				params.put("set_uid", uid);
						
				//sqlSession.getMapper(FrontReservationDao.class).itemQtyUpd(params);
			}
			
			if(!"".equals(reserveInfo.get("EVENT1_ITEM")) && reserveInfo.get("EVENT1_ITEM") != null){
				int uid = Integer.parseInt(reserveInfo.get("EVENT1_ITEM").toString());
				int rentalSumCnt = Integer.parseInt(reserveInfo.get("EVENT1_CNT").toString());
				int realQty = sqlSession.getMapper(FrontReservationDao.class).itemQtyInfo(uid);
				int updQty = realQty + rentalSumCnt;
				
				Map params = new HashMap();
				params.put("quantity", updQty);
				params.put("set_uid", uid);
						
				//sqlSession.getMapper(FrontReservationDao.class).itemQtyUpd(params);
			}
			
			if(!"".equals(reserveInfo.get("EVENT2_ITEM")) && reserveInfo.get("EVENT2_ITEM") != null){
				int uid = Integer.parseInt(reserveInfo.get("EVENT2_ITEM").toString());
				int rentalSumCnt = Integer.parseInt(reserveInfo.get("EVENT2_CNT").toString());
				int realQty = sqlSession.getMapper(FrontReservationDao.class).itemQtyInfo(uid);
				int updQty = realQty + rentalSumCnt;
				
				Map params = new HashMap();
				params.put("quantity", updQty);
				params.put("set_uid", uid);
						
				//sqlSession.getMapper(FrontReservationDao.class).itemQtyUpd(params);
			}
			
			if(!"".equals(reserveInfo.get("EVENT3_ITEM")) && reserveInfo.get("EVENT3_ITEM") != null){
				int uid = Integer.parseInt(reserveInfo.get("EVENT3_ITEM").toString());
				int rentalSumCnt = Integer.parseInt(reserveInfo.get("EVENT3_CNT").toString());
				int realQty = sqlSession.getMapper(FrontReservationDao.class).itemQtyInfo(uid);
				int updQty = realQty + rentalSumCnt;
				
				Map params = new HashMap();
				params.put("quantity", updQty);
				params.put("set_uid", uid);
						
				//sqlSession.getMapper(FrontReservationDao.class).itemQtyUpd(params);
			}
			
			reserveDetailDel(reserveuid); //�삁�빟 �긽�꽭 �궘�젣
			
			result = "PGCANCELOK";
					
		} catch (Exception e) {
			// TODO: handle exception
			result = "ERROR";
		}
	
		return result; 			
	}
	
	@Override
	@Transactional
	//PG 寃곗젣 痍⑥냼 INSERT
	public String pgPanaltyIns(Map param){
		String result ="";
		try {
		
			param.put("ac_message1",Util.convertStringToHex((String)param.get("ac_message1")));
			param.put("ac_message2",Util.convertStringToHex((String)param.get("ac_message2")));

			//PG 痍⑥냼
			sqlSession.getMapper(FrontReservationDao.class).pgCancelIns(param);	
			//�삁�빟 �뾽�뜲�씠�듃
			sqlSession.getMapper(FrontReservationDao.class).reserveCancelUpd(param);	
			
			result = "PGCANCELOK";
					
		} catch (Exception e) {
			// TODO: handle exception
			result = "ERROR";
		}
	
		return result; 			
	}
	
	//�쉶�썝�깉�눜 愿��젴 �삁�빟�젙蹂� CNT 媛��졇�삤湲� 
	public int getReservelistCnt(int param){
		return sqlSession.getMapper(FrontReservationDao.class).getReservelistCnt(param);
	}
	
	@Override
	@Transactional
	//�삁�빟�젙蹂� Detail INSERT
	public String reserveDetailIns(Map param){
		String result ="";
		try {		
			param.put("item_nm",Util.convertStringToHex((String)param.get("item_nm")));			
			sqlSession.getMapper(FrontReservationDao.class).reserveDetailIns(param);			
			
			result = "RESERVEDETAILOK";
		} catch (Exception e) {
			// TODO: handle exception
			result = "ERROR";
		}
		return result;
	}
	
	@Override
	@Transactional
	//�삁�빟�젙蹂� Detail Delete
	public String reserveDetailDel(int param){
		String result ="";
		try {		
			
			sqlSession.getMapper(FrontReservationDao.class).reserveDetailDel(param);			
			
			result = "RESERVEDETAILDELOK";
		} catch (Exception e) {
			// TODO: handle exception
			result = "ERROR";
		}
		return result;		
	}
	
	@Override
	@Transactional    
	public String reserVationUpd(Map param){
		String result ="";
		try {		
			
			sqlSession.getMapper(FrontReservationDao.class).reserVationUpd(param);			
			
			if(!"".equals(param.get("spa_item")) && param.get("spa_item") != null){
				Map params = new HashMap();
				int uid = Integer.parseInt(param.get("spa_item").toString());
				
				//吏��젙 �닔�웾 �뾽�뜲�씠�듃
				int spaSumCnt = Integer.parseInt(param.get("itemSum0Cnt").toString());
				int realQty = sqlSession.getMapper(FrontReservationDao.class).itemQtyInfo(uid);
				int updQty = realQty - spaSumCnt;
				
				params.put("quantity", updQty);
				params.put("set_uid", uid);
						
				sqlSession.getMapper(FrontReservationDao.class).itemQtyUpd(params);
				
			}
			
			if(!"".equals(param.get("water_item")) && param.get("water_item") != null){
				Map params = new HashMap();
				int uid = Integer.parseInt(param.get("water_item").toString());
				
				int waterSumCnt = Integer.parseInt(param.get("itemSum1Cnt").toString());
				int realQty = sqlSession.getMapper(FrontReservationDao.class).itemQtyInfo(uid);
				int updQty = realQty - waterSumCnt;
				

				params.put("quantity", updQty);
				params.put("set_uid", uid);
						
				sqlSession.getMapper(FrontReservationDao.class).itemQtyUpd(params);
			}
			
			if(!"".equals(param.get("complex_item")) && param.get("complex_item") != null){
				Map params = new HashMap();
				int uid = Integer.parseInt(param.get("complex_item").toString());
				
				int complexSumCnt = Integer.parseInt(param.get("itemSum2Cnt").toString());
				int realQty = sqlSession.getMapper(FrontReservationDao.class).itemQtyInfo(uid);
				int updQty = realQty - complexSumCnt;

				params.put("quantity", updQty);
				params.put("set_uid", uid);
						
				sqlSession.getMapper(FrontReservationDao.class).itemQtyUpd(params);
			}
			
			if(!"".equals(param.get("rental1_item")) && param.get("rental1_item") != null){
				Map params = new HashMap();
				int uid = Integer.parseInt(param.get("rental1_item").toString());
				
				int rentalSumCnt = Integer.parseInt(param.get("rental1_cnt").toString());
				int realQty = sqlSession.getMapper(FrontReservationDao.class).itemQtyInfo(uid);
				int updQty = realQty - rentalSumCnt;
				
				params.put("quantity", updQty);
				params.put("set_uid", uid);
						
				sqlSession.getMapper(FrontReservationDao.class).itemQtyUpd(params);
			}
			
			if(!"".equals(param.get("rental2_item")) && param.get("rental2_item") != null){
				Map params = new HashMap();
				int uid = Integer.parseInt(param.get("rental2_item").toString());

				int rentalSumCnt = Integer.parseInt(param.get("rental2_cnt").toString());
				int realQty = sqlSession.getMapper(FrontReservationDao.class).itemQtyInfo(uid);
				int updQty = realQty - rentalSumCnt;
				
				params.put("quantity", updQty);
				params.put("set_uid", uid);
						
				sqlSession.getMapper(FrontReservationDao.class).itemQtyUpd(params);
			}
			
			if(!"".equals(param.get("rental3_item")) && param.get("rental3_item") != null){
				Map params = new HashMap();
				int uid = Integer.parseInt(param.get("rental3_item").toString());

				int rentalSumCnt = Integer.parseInt(param.get("rental3_cnt").toString());
				int realQty = sqlSession.getMapper(FrontReservationDao.class).itemQtyInfo(uid);
				int updQty = realQty - rentalSumCnt;
				
				params.put("quantity", updQty);
				params.put("set_uid", uid);
						
				sqlSession.getMapper(FrontReservationDao.class).itemQtyUpd(params);
			}
			
			if(!"".equals(param.get("event1_item")) && param.get("event1_item") != null){
				Map params = new HashMap();
				int uid = Integer.parseInt(param.get("event1_item").toString());

				int rentalSumCnt = Integer.parseInt(param.get("event1_cnt").toString());
				int realQty = sqlSession.getMapper(FrontReservationDao.class).itemQtyInfo(uid);
				int updQty = realQty - rentalSumCnt;
				
				params.put("quantity", updQty);
				params.put("set_uid", uid);
						
				sqlSession.getMapper(FrontReservationDao.class).itemQtyUpd(params);
			}
			
			if(!"".equals(param.get("event2_item")) && param.get("event2_item") != null){
				Map params = new HashMap();
				int uid = Integer.parseInt(param.get("event2_item").toString());

				int rentalSumCnt = Integer.parseInt(param.get("event2_cnt").toString());
				int realQty = sqlSession.getMapper(FrontReservationDao.class).itemQtyInfo(uid);
				int updQty = realQty - rentalSumCnt;
				
				params.put("quantity", updQty);
				params.put("set_uid", uid);
						
				sqlSession.getMapper(FrontReservationDao.class).itemQtyUpd(params);
			}
			
			if(!"".equals(param.get("event3_item")) && param.get("event3_item") != null){
				Map params = new HashMap();
				int uid = Integer.parseInt(param.get("event3_item").toString());

				int rentalSumCnt = Integer.parseInt(param.get("event3_cnt").toString());
				int realQty = sqlSession.getMapper(FrontReservationDao.class).itemQtyInfo(uid);
				int updQty = realQty - rentalSumCnt;

				params.put("quantity", updQty);
				params.put("set_uid", uid);
						
				sqlSession.getMapper(FrontReservationDao.class).itemQtyUpd(params);
			}
			
			
			result = "RESERVEUPDATEOK";
		} catch (Exception e) {
			logger.debug("error :::::::::::: " + e.getMessage());
			// TODO: handle exception
			result = "ERROR";
		}
		return result;		
	}
	
	@Override
	@Transactional    
	public String memberExtUpd(Map param){
		String result ="";
		try {		
			
			sqlSession.getMapper(FrontReservationDao.class).memberExtUpd(param);			
			
			
			result = "MEM_PHONE_UPDATEOK";
		} catch (Exception e) {
			logger.debug("error :::::::::::: " + e.getMessage());
			// TODO: handle exception
			result = "ERROR";
		}
		return result;		
	}	

	@Override
	//MEMBER INFO �젙蹂� 媛��졇�삤湲�
	public Map getMemInfo(String param){
		Map memInfo = null;
		try {
			memInfo = Util.setMapKoConvert(sqlSession.getMapper(FrontReservationDao.class).getMemInfo(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return memInfo;
	}
	
	@Override
	//吏��젏 �젙蹂� 媛��졇�삤湲�
	public Map getPointInfo(String param){
		Map getPointInfo = null;
		try {
			getPointInfo = Util.setMapKoConvert(sqlSession.getMapper(FrontReservationDao.class).getPointInfo(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getPointInfo;
	}
	
	@Override
	@Transactional
	//PG 寃곗젣 痍⑥냼 INSERT
	public String pgCancelInsOnPayment(Map param){
		String result ="";
		try {
		
			param.put("ac_message1",Util.convertStringToHex((String)param.get("ac_message1")));

			//PG 痍⑥냼
			sqlSession.getMapper(FrontReservationDao.class).pgCancelIns(param);	
			result = "PGCANCELOK";
					
		} catch (Exception e) {
			// TODO: handle exception
			result = "ERROR";
		}
	
		return result; 			
	}
	
	@Override
	//吏��젏 �젙蹂� 媛��졇�삤湲�
	public List<Integer> getQuantityList(Map param){
		List<Integer> getQuantityList = null;
		try {
			getQuantityList = sqlSession.getMapper(FrontReservationDao.class).getQuantityList(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getQuantityList;
	}
	
}
