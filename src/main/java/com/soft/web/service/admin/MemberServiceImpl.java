package com.soft.web.service.admin;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import com.soft.web.base.PasswordEncoder;
import com.soft.web.dao.admin.MemberDao;
import com.soft.web.dao.admin.ProductDao;
import com.soft.web.util.Util;

@Service
public class MemberServiceImpl implements MemberService {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name="config")
    private Properties config;
	
	@Autowired
	@Resource(name="sqlSession")
    protected SqlSession sqlSession;
	
	// �쉶�썝紐⑸줉 議고쉶
	@Override
	public List<Map> memberList(Map param) {

		List<Map> memberList = null;
		try {
			
			memberList = Util.setListKoConvert(sqlSession.getMapper(MemberDao.class).memberList(param),config.getProperty("character.set"));
			
//			for (Map map : memberList) {
//				Map newMap = new HashMap();
//				newMap.put("MEM_UID", map.get("MEM_UID"));
//				newMap.put("MEM_NM", (String)map.get("MEM_NM"));
//				newMap.put("MEM_ID", (String)map.get("MEM_ID"));
//				newMap.put("MOBILE_NUM", (String)map.get("MOBILE_NUM"));
//				newMap.put("MEM_BIRTH", map.get("MEM_BIRTH"));
//				newMap.put("MEM_GENDER", map.get("MEM_GENDER"));
//				newMap.put("INS_DATE", map.get("INS_DATE"));
//				newMap.put("RESERVE_CNT", map.get("RESERVE_CNT"));
//				newMap.put("MEM_NUM", map.get("MEM_NUM"));
//				
//				newMemberList.add(newMap);			
//			}
	
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return memberList;
	}

	// �쉶�썝紐⑸줉 議고쉶 totCnt
	@Override
	public int memberListCnt(Map param) {
//		try {
//			param.put("srch_text",Util.convertStringToHex((String)param.get("srch_text")));
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return sqlSession.getMapper(MemberDao.class).memberListCnt(param);
	}

	// �쉶�썝紐⑸줉 議고쉶
	@Override
	public List<Map> memberList2(Map param) {

		List<Map> memberList = null;
		try {
			
			memberList = Util.setListKoConvert(sqlSession.getMapper(MemberDao.class).memberList2(param),config.getProperty("character.set"));
			
//			for (Map map : memberList) {
//				Map newMap = new HashMap();
//				newMap.put("MEM_UID", map.get("MEM_UID"));
//				newMap.put("MEM_NM", (String)map.get("MEM_NM"));
//				newMap.put("MEM_ID", (String)map.get("MEM_ID"));
//				newMap.put("MOBILE_NUM", (String)map.get("MOBILE_NUM"));
//				newMap.put("MEM_BIRTH", map.get("MEM_BIRTH"));
//				newMap.put("MEM_GENDER", map.get("MEM_GENDER"));
//				newMap.put("INS_DATE", map.get("INS_DATE"));
//				newMap.put("RESERVE_CNT", map.get("RESERVE_CNT"));
//				newMap.put("MEM_NUM", map.get("MEM_NUM"));
//				
//				newMemberList.add(newMap);			
//			}
	
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return memberList;
	}

	// �쉶�썝紐⑸줉 議고쉶 totCnt
	@Override
	public int memberListCnt2(Map param) {
//		try {
//			param.put("srch_text",Util.convertStringToHex((String)param.get("srch_text")));
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return sqlSession.getMapper(MemberDao.class).memberListCnt2(param);
	}
	
	// �쉶�썝�긽�꽭 議고쉶
	@Override
	public Map memberDetail(Map param) {
		
		Map memberDetail = null;
		try {
			memberDetail = Util.setMapKoConvert(sqlSession.getMapper(MemberDao.class).memberDetail(param),config.getProperty("character.set"));
//			
//			if(memberDetail != null){
//				memberDetail.put("MEM_NM", (String)memberDetail.get("MEM_NM"));
//				memberDetail.put("MEM_ID", (String)memberDetail.get("MEM_ID"));
//				memberDetail.put("MOBILE_NUM", (String)memberDetail.get("MOBILE_NUM"));
//				memberDetail.put("HOME_ADDR1", (String)memberDetail.get("HOME_ADDR1"));
//				memberDetail.put("HOME_ADDR2", (String)memberDetail.get("HOME_ADDR2"));
//				memberDetail.put("HOME_PHONE_NUM", (String)memberDetail.get("HOME_PHONE_NUM"));
//				memberDetail.put("COMPANY_ADDR1", (String)memberDetail.get("COMPANY_ADDR1"));
//				memberDetail.put("COMPANY_ADDR2", (String)memberDetail.get("COMPANY_ADDR2"));
//				memberDetail.put("COMPANY_PHONE_NUM", (String)memberDetail.get("COMPANY_PHONE_NUM"));
//				memberDetail.put("MEM_NUM", (String)memberDetail.get("MEM_NUM"));
//			}
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return memberDetail;
	}

	// �쉶�썝�긽�꽭 �닔�젙
	@Override
	public void memberUpdate(Map param) {
		sqlSession.getMapper(MemberDao.class).memberUpdate(param);
	}
	
	@Override
	//�쑕硫닿컻�젙 議고쉶
	public List<Map> inactMemberList(Map param){
		List<Map> inactMemberList = null;
		try {
			
			inactMemberList = Util.setListKoConvert(sqlSession.getMapper(MemberDao.class).inactMemberList(param),config.getProperty("character.set"));
	
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return inactMemberList;		
	}
	
	@Override
	// �쉶�썝紐⑸줉 議고쉶 totCnt
	public int inactMemberListCnt(Map param){
		return sqlSession.getMapper(MemberDao.class).inactMemberListCnt(param);
	}
	
	
}
