package com.soft.web.service.admin;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soft.web.base.Cryptography;
import com.soft.web.dao.admin.ReservationDao;
import com.soft.web.util.Util;

@Service("ReservationService")
public class ReservationServiceImpl implements ReservationService {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//암호화
	//param.put("mem_nm", cryptography.encrypt((String)param.get("mem_nm")));
	//복호화
	//cryptography.decrypt(sqlSession.getMapper(FrontMemberDao.class).searchId(param));
	
    @Resource(name="config")
    private Properties config;
	
	@Autowired
	@Resource(name="sqlSession")
    protected SqlSession sqlSession;
	
	@Resource
	private Cryptography cryptography;
	

	// 예약목록
	@Override
	@SuppressWarnings("unchecked")
	public List<Map> reservationList(Map param) {
		List<Map> reservationList = null;
		
		try {
			//param.put("category",Util.convertStringToHex((String)param.get("category")));
			//param.put("mem_nm",Util.convertStringToHex((String)param.get("mem_nm")));			
			reservationList = Util.setListKoConvert(sqlSession.getMapper(ReservationDao.class).reservationList(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reservationList;
	}

	//예약 목록 totCnt
	@Override
	@SuppressWarnings("unchecked")
	public int reservationListCnt(Map param) {
//		try {
//			param.put("category",Util.convertStringToHex((String)param.get("category")));
//			param.put("mem_nm",Util.convertStringToHex((String)param.get("mem_nm")));
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return sqlSession.getMapper(ReservationDao.class).reservationListCnt(param);
	}

	//예약 상세
	@Override
	public Map reservationDetail(Map param) {
		Map reservationDetail = null;
		try {
			reservationDetail = Util.setMapKoConvert(sqlSession.getMapper(ReservationDao.class).reservationDetail(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reservationDetail;
	}


	//예약정보 바로보기 사용변경버튼
	@Override
	public int reservationUseChnge(Map param) {
		return sqlSession.getMapper(ReservationDao.class).reservationUseChnge(param);
	}

	// 예약내용 수정
	@Override
	public int reservationUpdate(Map param) {
		return sqlSession.getMapper(ReservationDao.class).reservationUpdate(param);
	}

	//페이징 없는 목록추출
	@Override
	public List<Map> reservationListAll(Map param) {
		List<Map> reservationListAll = null;
		try {
			//param.put("category",Util.convertStringToHex((String)param.get("category")));
			//param.put("mem_nm",Util.convertStringToHex((String)param.get("mem_nm")));
			reservationListAll = Util.setListKoConvert(sqlSession.getMapper(ReservationDao.class).reservationListAll(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reservationListAll;
	}
	
	@Override	
	//데스크관리자 대쉬보드 관련 상품별 사람CNT
	public List<Map> reservePersonCntOfProd(Map param){
		List<Map> reservePersonCntOfProd = null;
		try {
			reservePersonCntOfProd = Util.setListKoConvert(sqlSession.getMapper(ReservationDao.class).reservePersonCntOfProd(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reservePersonCntOfProd;		
	}
	
	@Override
	//데스크관리자 대쉬보드 관련 예약상태별 사람CNT
	public int reservePersonCnt(Map param){
		return sqlSession.getMapper(ReservationDao.class).reservePersonCnt(param);
	}
	
}
