package com.soft.web.service.front;

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
import org.springframework.transaction.annotation.Transactional;

import com.soft.web.dao.front.FrontNoticeEventDao;
import com.soft.web.util.Util;


@Service
public class FrontNoticeEventServiceImpl implements FrontNoticeEventService {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name="config")
    private Properties config;
	
	@Autowired
	@Resource(name="sqlSession")
    protected SqlSession sqlSession;
    
	// MAX UID
    @Override
	public int noticeEventMaxUid(){
    	return sqlSession.getMapper(FrontNoticeEventDao.class).noticeEventMaxUid();
    }

	// 게시물 총 갯수
    @Override
	public int noticeEventCount(Map param){
    	return sqlSession.getMapper(FrontNoticeEventDao.class).noticeEventCount(param);
    }

	// 게시물목록
    @Override
	public List<Map> noticeEventList(Map param){
    	List<Map> noticeEventList = null;
    	try {
			noticeEventList = Util.setListKoConvert(sqlSession.getMapper(FrontNoticeEventDao.class).noticeEventList(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return noticeEventList;
	}
	
	// 상세보기
    @Override
	public Map<String, Object> noticeEventDetail(Map param){
    	Map<String, Object> noticeEventDetail = null;
    	try {
			noticeEventDetail = Util.setMapKoConvert(sqlSession.getMapper(FrontNoticeEventDao.class).noticeEventDetail(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return noticeEventDetail;
	}
	
	// 이전글
    @Override
	public Map<String, Object> noticeEventPrev(Map param){
    	Map<String, Object> noticeEventPrev = null;
    	try {
			noticeEventPrev = Util.setMapKoConvert(sqlSession.getMapper(FrontNoticeEventDao.class).noticeEventPrev(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return noticeEventPrev;
	}
	
	// 다음글
    @Override
	public Map<String, Object> noticeEventNext(Map param){
    	Map<String, Object> noticeEventNext = null;
    	try {
			noticeEventNext = Util.setMapKoConvert(sqlSession.getMapper(FrontNoticeEventDao.class).noticeEventNext(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return noticeEventNext;
	}
    
    //-- 등록
    @Override
	@Transactional    
	public int insertNoticeEvent(Map param){
    	
		try {
			param.put("title",Util.convertStringToHex((String)param.get("title")));
			param.put("contents",Util.convertStringToHex((String)param.get("contents")));			
			param.put("page1LeftContent",Util.convertStringToHex((String)param.get("page1LeftContent")));
			param.put("page1RightContent",Util.convertStringToHex((String)param.get("page1RightContent")));
			param.put("page2LeftContent",Util.convertStringToHex((String)param.get("page2LeftContent")));
			param.put("page2RightContent",Util.convertStringToHex((String)param.get("page2RightContent")));
			param.put("page3LeftContent",Util.convertStringToHex((String)param.get("page3LeftContent")));
			param.put("page3RightContent",Util.convertStringToHex((String)param.get("page3RightContent")));	
			param.put("location",Util.convertStringToHex((String)param.get("location")));	
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sqlSession.getMapper(FrontNoticeEventDao.class).insertNoticeEvent(param);
    }
    
    //-- 수정
    @Override
	@Transactional    
	public int updateNoticeEvent(Map param){
		try {
			param.put("title",Util.convertStringToHex((String)param.get("title")));
			param.put("contents",Util.convertStringToHex((String)param.get("contents")));			
			param.put("page1LeftContent",Util.convertStringToHex((String)param.get("page1LeftContent")));
			param.put("page1RightContent",Util.convertStringToHex((String)param.get("page1RightContent")));
			param.put("page2LeftContent",Util.convertStringToHex((String)param.get("page2LeftContent")));
			param.put("page2RightContent",Util.convertStringToHex((String)param.get("page2RightContent")));
			param.put("page3LeftContent",Util.convertStringToHex((String)param.get("page3LeftContent")));
			param.put("page3RightContent",Util.convertStringToHex((String)param.get("page3RightContent")));			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    	
		return sqlSession.getMapper(FrontNoticeEventDao.class).updateNoticeEvent(param);
    }
    
    //-- 조회수 증가
    @Override
	@Transactional    
	public int updateNoticeEventHit(Map param){
    	return sqlSession.getMapper(FrontNoticeEventDao.class).updateNoticeEventHit(param);
    }
    
    //-- 삭제
    @Override
	@Transactional    
	public int deleteNoticeEvent(Map param){
    	return sqlSession.getMapper(FrontNoticeEventDao.class).deleteNoticeEvent(param);
    }
}
