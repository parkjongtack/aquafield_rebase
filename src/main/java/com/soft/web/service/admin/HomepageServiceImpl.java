package com.soft.web.service.admin;

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

import com.soft.web.dao.admin.AdminStatisticsDao;
import com.soft.web.dao.admin.HomepageDao;
import com.soft.web.dao.admin.ProductDao;
import com.soft.web.util.Util;

@Service
public class HomepageServiceImpl implements HomepageService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());	
	
    @Resource(name="config")
    private Properties config;

	@Autowired
	@Resource(name="sqlSession")
    protected SqlSession sqlSession;

	@Override
	//전체주문데이터 Cnt 
	public int popupListCnt(Map param){
		return sqlSession.getMapper(HomepageDao.class).popupListCnt(param);		
	}
	
	@Override
	//전체주문데이터 List 
	public List popupList(Map param){
		return sqlSession.getMapper(HomepageDao.class).popupList(param);		
	}
	
	@Override
	//전체주문데이터 List 
	public String popInst(Map param){
		return sqlSession.getMapper(HomepageDao.class).popInst(param);		
	}
	
}
