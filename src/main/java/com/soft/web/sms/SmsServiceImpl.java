package com.soft.web.sms;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soft.web.dao.front.FrontMemberDao;
import com.soft.web.util.Util;

@Service
public class SmsServiceImpl implements SmsService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Resource(name="config")
    private Properties config;
	
	@Autowired
	@Resource(name="sqlSessionSms")
    protected SqlSession sqlSessionSms;	
	
	@Autowired
	@Resource(name="sqlSession")
    protected SqlSession sqlSession;

	@Override
	@Transactional
	public boolean sendSms(Map param) throws Exception {
		boolean isTrue = true;
		try{
			
			if(smsLength(param.get("content").toString()) < 80){
				insertSms(param);
			}
			else{
				insertLms(param);
			}
		}
		catch(Exception e){
			logger.debug("SmsServiceImpl.sendSms : "+e.toString() );
			isTrue = false;
		}
		return isTrue;
	}	
	
	
	@Override
	@Transactional
	public int insertSms(Map param) {
		try {
			param.put("content", Util.convertStringToHex((String)param.get("content")));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return sqlSessionSms.getMapper(SmsDao.class).insertSms(param);
	}
	
	@Override
	@Transactional
	public int insertLms(Map param) {
		try {
			param.put("subject", Util.convertStringToHex((String)param.get("subject")));
			param.put("content", Util.convertStringToHex((String)param.get("content")));
		} catch (UnsupportedEncodingException e) {
			param.put("subject", param.get("subject"));
			param.put("content", param.get("content"));
		
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sqlSessionSms.getMapper(SmsDao.class).insertLms(param);
	}
	
	public int smsLength(String sms){
		int len = sms.length();
		int cnt = 0;
		
		for(int i=0; i<len; i++){
			if(sms.charAt(i) < 256){
				cnt++;
			}
			else{
				cnt = cnt + 2;
			}
		}
		return cnt;
	}
}
