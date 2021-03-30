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

import com.soft.web.dao.front.FrontNewsDao;
import com.soft.web.util.Util;


@Service
public class FrontNewsServiceImpl implements FrontNewsService {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name="config")
    private Properties config;
	
	@Autowired
	@Resource(name="sqlSession")
    protected SqlSession sqlSession;
    
	// MAX UID
    @Override
	public int newsMaxUid(){
    	return sqlSession.getMapper(FrontNewsDao.class).newsMaxUid();
    }

	// 게시물 총 갯수
    @Override
	public int newsCount(Map param){
    	return sqlSession.getMapper(FrontNewsDao.class).newsCount(param);
    }

	// 게시물목록
    @Override
	public List<Map> newsList(Map param){
    	List<Map> list = null;
    	try {
			list = Util.setListKoConvert(sqlSession.getMapper(FrontNewsDao.class).newsList(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	return list;
	}
	
	// 상세보기
    @Override
	public Map<String, Object> newsDetail(Map param){
    	Map<String, Object> detail = null;
    	try {
    		detail = Util.setMapKoConvert(sqlSession.getMapper(FrontNewsDao.class).newsDetail(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	return detail;
	}
	
	// 이전글
    @Override
	public Map<String, Object> newsPrev(Map param){
    	Map<String, Object> map = null;
    	try {
    		map = Util.setMapKoConvert(sqlSession.getMapper(FrontNewsDao.class).newsPrev(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	return map;
	}
	
	// 다음글
    @Override
	public Map<String, Object> newsNext(Map param){
    	Map<String, Object> map = null;
    	try {
    		map = Util.setMapKoConvert(sqlSession.getMapper(FrontNewsDao.class).newsNext(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	return map;
	}
    
    //-- 등록
    @Override
	@Transactional    
	public int newsInsert(Map param){
		return sqlSession.getMapper(FrontNewsDao.class).newsInsert(param);
    }
    
    //-- 수정
    @Override
	@Transactional    
	public int newsUpdate(Map param){
		return sqlSession.getMapper(FrontNewsDao.class).newsUpdate(param);
    }
    
    //-- 조회수 증가
    @Override
	@Transactional    
	public int newsHitUpdate(Map param){
    	return sqlSession.getMapper(FrontNewsDao.class).newsHitUpdate(param);
    }
    
    //-- 삭제
    @Override
	@Transactional    
	public int newsDelete(Map param){
    	return sqlSession.getMapper(FrontNewsDao.class).newsDelete(param);
    }
}
