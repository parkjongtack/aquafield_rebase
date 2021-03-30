package com.soft.web.service.common;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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

import com.soft.web.dao.admin.ReservationDao;
import com.soft.web.dao.common.CommonBatchDao;
import com.soft.web.dao.common.CommonDao;
import com.soft.web.sms.SmsDao;
import com.soft.web.util.Util;

@Service
public class CommonServiceImpl implements CommonService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());	
	
    @Resource(name="config")
    private Properties config;

	@Autowired
	@Resource(name="sqlSession")
    protected SqlSession sqlSession;

	@Override
	public List<Map> catelist(Map param){
		List<Map> catelist = null;
		
		try {
			catelist = Util.setListKoConvert(sqlSession.getMapper(CommonDao.class).catelist(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return catelist;
	}
	
	@Override
	public List<Map> commonlist(Map param){
		List<Map> commonlist =null;
		
		try {
			commonlist = Util.setListKoConvert(sqlSession.getMapper(CommonDao.class).commonlist(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return commonlist;
	}
	
	@Override
	//FAQ 리스트
	public List<Map> faqlist(Map param){
		return sqlSession.getMapper(CommonDao.class).faqlist(param);
	}
	
	@Override
	@Transactional
	//예약 미사용 업데이트
	public String batchNoUseUpd(){
		String result ="";
		try {
			sqlSession.getMapper(CommonBatchDao.class).batchNoUseUpd();		
			result = "NOUSEOK";
					
		} catch (Exception e) {
			// TODO: handle exception
			result = "ERROR";
		}
		return result;
	}
	
	//1일전 SMS 알림 관련
	@Override	
	public List<Map> beforeOneDaySmslist(){
		List<Map> beforeOneDaySmslist = null;
		try {
			beforeOneDaySmslist = Util.setListKoConvert(sqlSession.getMapper(CommonBatchDao.class).beforeOneDaySmslist(),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return beforeOneDaySmslist;		
	}
	
	@Override
	@Transactional
	public String insSmsSend(Map param){
		String result="";
		try {
			param.put("custom_nm", Util.convertStringToHex((String)param.get("custom_nm")));
			sqlSession.getMapper(CommonDao.class).insSmsSend(param);
			
			result = "SENDOK";
					
		} catch (Exception e) {
			// TODO: handle exception
			result = "ERROR";
		}
		return result;		
	}
	
	@Override
	//SMS 템플릿 가져오기
	public Map getSmsTemplete(Map param){
		Map smsTemplete = null;
		try {
			smsTemplete = Util.setMapKoConvert(sqlSession.getMapper(CommonDao.class).getSmsTemplete(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return smsTemplete;		
	}
	
	@Override
	//SMS 1번 더 발송하기
	public int sendSmsCnt(Map param){
		return sqlSession.getMapper(CommonDao.class).sendSmsCnt(param);
	}
	
	@Override
	@Transactional	
	//Email 발송 이력 등록
	public String insEmailSend(Map param){
		String result="";
		try {
			param.put("custom_nm", Util.convertStringToHex((String)param.get("custom_nm")));
			sqlSession.getMapper(CommonDao.class).insEmailSend(param);
			
			result = "SENDOK";
					
		} catch (Exception e) {
			// TODO: handle exception
			result = "ERROR";
		}
		return result;			
	}
	
	@Override
	//예약 자동취소 리스트
	public List<Map> batchCancelList(){
		List<Map> batchCancelList = null;
		try {
			batchCancelList = Util.setListKoConvert(sqlSession.getMapper(CommonBatchDao.class).batchCancelList(),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return batchCancelList;			
	}
	
	@Override
	//휴면계정 7일전 mail발송
	public List<Map> beforeSevenDayMailList(){
		List<Map> beforeSevenDayMailList = null;
		try {
			beforeSevenDayMailList = Util.setListKoConvert(sqlSession.getMapper(CommonBatchDao.class).beforeSevenDayMailList(),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return beforeSevenDayMailList;			
	}
	
	@Override
	@Transactional
	//휴명계정 INSERT
	public String inactMemIns(Map param){
		String result="";
		try {
			param.put("mem_nm", Util.convertStringToHex((String)param.get("mem_nm")));
			sqlSession.getMapper(CommonBatchDao.class).inactMemIns(param);
			
			result = "INACTINSOK";
					
		} catch (Exception e) {
			// TODO: handle exception
			result = "ERROR";
		}
		return result;			
	}
	
	@Override
	@Transactional
	//휴면개정 UPD
	public String inactMemUpd(){
		String result ="";
		try {
			sqlSession.getMapper(CommonBatchDao.class).inactMemUpd();		
			result = "INACT_Y_OK";
					
		} catch (Exception e) {
			// TODO: handle exception
			result = "ERROR";
		}
		return result;		
	}
	
	@Override
	@Transactional
	//ADMIN 사용기록 INSERT
	public String insAdminContentLog(Map param){
		String result="";
		try {
			/* 20180711 syw : 기본의 문자열 을 16진수로 바꾸는 함수..  사용기록로그는 암호화 시키지 않고 db에 저장시킨다. 
			param.put("access_menu_nm", Util.convertStringToHex((String)param.get("access_menu_nm")));
			param.put("admin_nm", Util.convertStringToHex((String)param.get("admin_nm")));	
			param.put("etc", Util.convertStringToHex((String)param.get("etc")));
			param.put("data_num", Util.convertStringToHex((String)param.get("data_num")));				
			*/
			
			
			/* 20180720 syw : 실서버 올릴때 euc-kr로 변경하기 위해 함수 */
			param.put("access_menu_nm", Util.convertCharacterSet((String)param.get("access_menu_nm")));
			param.put("admin_nm", Util.convertCharacterSet((String)param.get("admin_nm")));	
			param.put("etc", Util.convertCharacterSet((String)param.get("etc")));
			param.put("data_num", Util.convertCharacterSet((String)param.get("data_num")));
			
			sqlSession.getMapper(CommonDao.class).insAdminContentLog(param);
			
			result = "ADMINLOGOK";
					
		} catch (Exception e) {
			// TODO: handle exception
			result = "ERROR";
		}
		return result;			
	}
	
	@Override
	//관리자 메뉴 가져오기
	public Map getAdminMenu(String param){
		Map getAdminMenu = null;
		try {
			getAdminMenu = Util.setMapKoConvert(sqlSession.getMapper(CommonDao.class).getAdminMenu(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getAdminMenu;			
	}
	
	@Override
	//관리자 메뉴 가져오기
	public Map getPointInfo(String param){
		Map getPointInfo = null;
		try {
			getPointInfo = Util.setMapKoConvert(sqlSession.getMapper(CommonDao.class).getPointInfo(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getPointInfo;			
	}

	@Override
	//결제 정보 가져오기
	public List<Map> payList(){
		List<Map> payList =null;
		
		try {
			payList = Util.setListKoConvert(sqlSession.getMapper(CommonDao.class).payList(),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return payList;
	}
}
