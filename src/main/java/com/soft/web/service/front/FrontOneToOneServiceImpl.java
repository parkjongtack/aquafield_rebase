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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.soft.web.base.Cryptography;
import com.soft.web.base.PasswordEncoder;
import com.soft.web.dao.front.FrontMemberDao;
import com.soft.web.dao.front.FrontOneToOneDao;
import com.soft.web.util.Util;

@Service
public class FrontOneToOneServiceImpl implements FrontOneToOneService {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Resource(name="config")
    private Properties config;

	@Autowired
	@Resource(name="sqlSession")
    protected SqlSession sqlSession;

	//1:1臾몄쓽 由ъ뒪�듃
    @Override
	public List<Map> onetoOnelist (Map param){
    	List<Map> onetoOnelist = null;
    	
		try {
			onetoOnelist = Util.setListKoConvert(sqlSession.getMapper(FrontOneToOneDao.class).onetoOnelist(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return onetoOnelist; 
	}
	
	//1:1臾몄쓽 媛쒖닔
    @Override
	public int onetoOnelistCnt (Map param){
		return sqlSession.getMapper(FrontOneToOneDao.class).onetoOnelistCnt(param);
	}
    
    @Override
	//1:1臾몄쓽 �긽�꽭蹂닿린
	public Map onetoOneInfo(String param){
    	Map onetoOneInfo = null;
    	
    	try {
    		onetoOneInfo = Util.setMapKoConvert(sqlSession.getMapper(FrontOneToOneDao.class).onetoOneInfo(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return onetoOneInfo;
    }
    
    
    
   	//1:1臾몄쓽 �긽�꽭蹂닿린
   	public Map onetoOneInfoNew(Map param){
       	Map onetoOneInfo = null;
       	
       	try {
       		onetoOneInfo = Util.setMapKoConvert(sqlSession.getMapper(FrontOneToOneDao.class).onetoOneInfoNew(param),config.getProperty("character.set"));
   		} catch (UnsupportedEncodingException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
       	return onetoOneInfo;
    }
       
    
    @Override
	//1:1臾몄쓽 �벑濡�
	public String oneToOneIns(Map param){
		String result="";
		try {
			
            param.put("writer",Util.convertStringToHex((String)param.get("writer")));
    		param.put("ask_title", Util.convertStringToHex((String)param.get("ask_title")));	
    		param.put("ask_content", Util.convertStringToHex((String)param.get("ask_content")));           
			
    		/*
            param.put("writer",(String)param.get("writer"));
    		param.put("ask_title", (String)param.get("ask_title"));	
    		param.put("ask_content", (String)param.get("ask_content"));           
			*/
    		
			sqlSession.getMapper(FrontOneToOneDao.class).oneToOneIns(param);
			result = "INSOK";
		} catch (Exception e) {
			// TODO: handle exception
			result = "ERROR";
		}
		return result;
    }
}
