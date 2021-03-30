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
import com.soft.web.dao.front.FrontMemberDao;
import com.soft.web.sms.SmsService;
import com.soft.web.util.AquaDataUtil;
import com.soft.web.util.DecoderUtil;
import com.soft.web.util.Util;


@Service
@Transactional
public class FrontMemberServiceImpl implements FrontMemberService {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name="config")
    private Properties config;
	
	@Autowired
	@Resource(name="sqlSession")
    protected SqlSession sqlSession;
	
	// �쉶�썝媛��엯
	@Override
	@Transactional
	public String memberJoin(Map param){
		
		String result="";
		try {
			
            param.put("mem_nm",Util.convertStringToHex((String)param.get("mem_nm")));
            param.put("mem_id",(String)param.get("mem_id"));
            param.put("mobile_num",(String)param.get("mobile_num"));
            param.put("home_addr1",Util.convertStringToHex((String)param.get("home_addr1")));
            param.put("home_addr2",Util.convertStringToHex((String)param.get("home_addr2")));
            param.put("company_phone_num",(String)param.get("company_phone_num"));
            param.put("company_addr1",Util.convertStringToHex((String)param.get("company_addr1")));
            param.put("company_addr2",Util.convertStringToHex((String)param.get("company_addr2")));         
            param.put("ins_id",(String)param.get("ins_id"));
            param.put("mem_pw",(String)param.get("mem_pw"));
			
			sqlSession.getMapper(FrontMemberDao.class).memberJoin(param);
			
			result = "JOINOK";
					
		} catch (Exception e) {
			// TODO: handle exception
			result = "ERROR";
		}
		return result;
	}
	
	//蹂몄씤�씤利앹떆 ���옣
	@Override	
	public String niceIdSave(Map param){
		String result = "";
		
		try {
		
			sqlSession.getMapper(FrontMemberDao.class).niceIdSave(param);
			
			result = "NICEOK";
			
		} catch(Exception e) {
			
			// TODO: handle exception
			result = "ERROR";
		}
		return result;
	}
	
	
	//蹂몄씤�씤利� 泥댄겕
	public int niceChk(Map param){
		return sqlSession.getMapper(FrontMemberDao.class).niceChk(param);
	}
	
	//�븘�씠�뵒李얘린
	@Override	
	public String searchId(Map param){
		
		return sqlSession.getMapper(FrontMemberDao.class).searchId(param);
	}
	
	@Override
	@Transactional
	//�깉鍮꾨�踰덊샇 �꽕�젙
	public String setPassword(Map param){
		String result="";
		try {
            param.put("mem_pw",(String)param.get("mem_pw"));
			sqlSession.getMapper(FrontMemberDao.class).setPassword(param);
			result = "SETOK";
		} catch (Exception e) {
			// TODO: handle exception
			result = "ERROR";
		}
		return result;		
	}
	
	//�븘�씠�뵒 議댁옱�뿬遺� 泥댄겕
	public int idChk(Map param){
		return sqlSession.getMapper(FrontMemberDao.class).idChk(param);
	}
	
	//濡쒓렇�씤  �쉶�썝�젙蹂�
	public Map memberInfo(Map param){
		
		Map memberInfo = null;
		try {
			memberInfo = Util.setMapKoConvert(sqlSession.getMapper(FrontMemberDao.class).memberInfo(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return memberInfo;
	}
	
	//�쑕硫� �쉶�썝�젙蹂�
	public Map inactMemInfo(Map param){
		
		Map inactMemInfo = null;
		try {
			inactMemInfo = Util.setMapKoConvert(sqlSession.getMapper(FrontMemberDao.class).inactMemInfo(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return inactMemInfo;
	}
	
	//理쒓렐濡쒓렇�씤�젒�냽�씪 �뾽�뜲�씠�듃
	@Override
	@Transactional
	public String setLastloginDate(Map param){		
		String result="";
		try {
			sqlSession.getMapper(FrontMemberDao.class).lastLoginUpd(param);
			result = "SETOK";
		} catch (Exception e) {
			// TODO: handle exception
			result = "ERROR";
		}
		return result;
	}
	
	//�쑕硫댄빐�젣 �옉�뾽
	@Override
	@Transactional
	public String setInactivityUpd(Map param){
		String result="";
		try {
			sqlSession.getMapper(FrontMemberDao.class).inactMemDel(param);
			sqlSession.getMapper(FrontMemberDao.class).setInactMem(param);
			result = "SETOK";
		} catch (Exception e) {
			// TODO: handle exception
			result = "ERROR";
		}
		return result;		
	}
	
	//�쉶�썝�젙蹂� �닔�젙
	@Override
	@Transactional	
	public String setMemInfo(Map param){
		String result="";
		try {
			
            param.put("home_addr1",Util.convertStringToHex((String)param.get("home_addr1")));
            param.put("home_addr2",Util.convertStringToHex((String)param.get("home_addr2")));
            param.put("company_phone_num",(String)param.get("company_phone_num"));
            param.put("company_addr1",Util.convertStringToHex((String)param.get("company_addr1")));
            param.put("company_addr2",Util.convertStringToHex((String)param.get("company_addr2")));         
            param.put("upd_id",(String)param.get("upd_id"));
            if(!"".equals((String)param.get("mem_pw"))){
            	param.put("mem_pw",(String)param.get("mem_pw"));            	
            }else{
            	param.put("mem_pw","");           	
            }
            
			sqlSession.getMapper(FrontMemberDao.class).memberUpd(param);
			result = "SETOK";
		} catch (Exception e) {
			// TODO: handle exception
			result = "ERROR";
		}
		return result;			
	}
	
	@Override
	//�쉶�썝鍮꾨�踰덊샇 �닔�젙
	public String setMemPwUpd(Map param){
		String result="";
		try {

			sqlSession.getMapper(FrontMemberDao.class).memPwUpd(param);
			result = "SETOK";
		} catch (Exception e) {
			// TODO: handle exception
			result = "ERROR";
		}
		return result;			
	}
	
	@Override
	//�쉶�썝�닔�젙 �젙蹂�
	public Map memberUpdInfo(Map param){
		Map memberInfo = null;
		try {
			memberInfo = Util.setMapKoConvert(sqlSession.getMapper(FrontMemberDao.class).memberUpdInfo(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return memberInfo;
	}
	
	//�쉶�썝�깉�눜
	@Override
	@Transactional		
	public String memberDel(String param){
		String result="";
		try {
			sqlSession.getMapper(FrontMemberDao.class).memberDel(param);
			result = "DELOK";
		} catch (Exception e) {
			// TODO: handle exception
			result = "ERROR";
		}
		return result;	
	}
	
	@Override
	@Transactional		
	public String memberRecovery(String param){
		String result="";
		try {
			sqlSession.getMapper(FrontMemberDao.class).memberRecovery(param);
			result = "DELOK";
		} catch (Exception e) {
			// TODO: handle exception
			result = "ERROR";
		}
		return result;	
	}	
	
	@Override
	//�쉶�썝�젙蹂� �닔�젙�떆 �꽭�뀞 �떎�떆 RESET �븯湲�
	public Map memberInfoTwo(int param){
		Map memberInfoTwo = null;
		try {
			memberInfoTwo = Util.setMapKoConvert(sqlSession.getMapper(FrontMemberDao.class).memberInfoTwo(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return memberInfoTwo;		
	}

	@Override
	public String loginFail(Map param) {
		String result="";
		try {
			sqlSession.getMapper(FrontMemberDao.class).loginFail(param);
			result = "SETOK";
		} catch (Exception e) {
			result = "ERROR";
		}
		return result;
	}

}
