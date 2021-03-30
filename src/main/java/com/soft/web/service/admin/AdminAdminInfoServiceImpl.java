package com.soft.web.service.admin;

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

import com.soft.web.dao.admin.AdminAdminInfoDao;
import com.soft.web.util.Util;

@Service
public class AdminAdminInfoServiceImpl implements AdminAdminInfoService {
	private static final Logger logger = LoggerFactory.getLogger(AdminAdminInfoServiceImpl.class);

    @Resource(name="config")
    private Properties config;
	
	@Autowired
	@Resource(name="sqlSession")
    protected SqlSession sqlSession;
	
	@Autowired
	AdminAdminMenuService adminAdminMenuService;
	
	@Autowired
	AdminAdminAuthService adminAdminAuthService;


	@Override
	@Transactional
	public String operationsManagerWriteAction(List<String> listMenuCode, Map param) {
		String result = "";
		//-- 등록
		if("inst".equals(param.get("mode").toString() ) ){
			
			param.put("USE_AT","Y");

			adminInfoInsert(param);
			//-- get maxUid
			String num = String.valueOf(adminInfoGetMaxUid(param) );
			
			//-- 권한등록
			for(int i = 0; i < listMenuCode.size(); i++){
				if(listMenuCode.get(i).trim().length() > 0){
					Map<String,String> inputParam = new HashMap<String,String>();
					inputParam.put("ADMIN_UID",num);
					inputParam.put("MENU_CODE",listMenuCode.get(i));
					inputParam.put("INS_IP",param.get("INS_IP").toString() );
					inputParam.put("INS_ADMIN_ID",param.get("INS_ADMIN_ID").toString());
					adminAdminAuthService.adminAuthInsert(inputParam);
				}
			}
			result = "{\"result\":true,\"msg\":\"등록 되었습니다.\"}";
		}
		//-- 수정
		else if("updt".equals(param.get("mode").toString() ) ){
			adminInfoUpdate(param);
			String num = param.get("num").toString();
			
			//-- 먼저 권한을 삭제 한다.
			Map<String,String> updateParam = new HashMap<String,String>();
			updateParam.put("num",num);
			adminAdminAuthService.adminAuthDeleteAll(updateParam);

			//-- 권한 등록
			for(int i = 0; i < listMenuCode.size(); i++){
				if(listMenuCode.get(i).trim().length() > 0){
					Map<String,String> inputParam = new HashMap<String,String>();
					inputParam.put("ADMIN_UID",num);
					inputParam.put("MENU_CODE",listMenuCode.get(i));
					inputParam.put("INS_IP",param.get("INS_IP").toString() );
					inputParam.put("INS_ADMIN_ID",param.get("INS_ADMIN_ID").toString());
					adminAdminAuthService.adminAuthInsert(inputParam);
				}
			}
			result = "{\"result\":true,\"msg\":\"수정 되었습니다.\"}";
		}
		
		return result;
	}


	@Override
	@Transactional
	public String managerDeleteAction(Map param) {
		String result = "{\"result\":true,\"msg\":\"삭제 되었습니다.\"}";
		adminAdminAuthService.adminAuthDeleteAll(param);
		adminInfoDelete(param);
		return result;
	}
	
	@Override
	public int adminInfoGetMaxUid(Map param) {
		return sqlSession.getMapper(AdminAdminInfoDao.class).adminInfoGetMaxUid(param);
	}

	@Override
	public int adminInfoGetCount(Map param) {
		return sqlSession.getMapper(AdminAdminInfoDao.class).adminInfoGetCount(param);
	}

	@Override
	public List<Map> adminInfoList(Map param) {
		List<Map> adminInfoList = null;
		try {
			adminInfoList = Util.setListKoConvert(sqlSession.getMapper(AdminAdminInfoDao.class).adminInfoList(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return adminInfoList;
	}

	@Override
	public Map adminInfoDetail(Map param) {
		 Map adminInfoDetail = null;
		 try {
			adminInfoDetail = Util.setMapKoConvert(sqlSession.getMapper(AdminAdminInfoDao.class).adminInfoDetail(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return adminInfoDetail;
	}

	@Override
	@Transactional
	public int adminInfoInsert(Map param) {
		return sqlSession.getMapper(AdminAdminInfoDao.class).adminInfoInsert(param);
	}

	@Override
	@Transactional
	public int adminInfoUpdate(Map param) {
		return sqlSession.getMapper(AdminAdminInfoDao.class).adminInfoUpdate(param);
	}

	@Override
	@Transactional
	public int adminInfoPasswordUpdate(Map param) {
		return sqlSession.getMapper(AdminAdminInfoDao.class).adminInfoPasswordUpdate(param);
	}

	@Override
	@Transactional
	public int adminInfoDelete(Map param) {
		return sqlSession.getMapper(AdminAdminInfoDao.class).adminInfoDelete(param);
	}
	
	@Override
	//사용기록 조회
	public List<Map> adminUseLogList(Map param){
		List<Map> adminUseLogList = null;
		try {
			adminUseLogList = Util.setListKoConvert(sqlSession.getMapper(AdminAdminInfoDao.class).adminUseLogList(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return adminUseLogList;		
	}
	
	@Override
	//사용기록 엑셀
	public List<Map> adminUseLogListExel(Map param){
		List<Map> adminUseLogListExel = null;
		try {
			adminUseLogListExel = Util.setListKoConvert(sqlSession.getMapper(AdminAdminInfoDao.class).adminUseLogListExel(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return adminUseLogListExel;		
	}
	
	@Override
	//사용기록 Cnt
	public int adminUseLogCnt(Map param){
		return sqlSession.getMapper(AdminAdminInfoDao.class).adminUseLogCnt(param);		
	}
	
	@Override
	//사용기록 1depth 메뉴 리스트
	public List<Map> adminOnedepthMenuList(){
		List<Map> adminOnedepthMenuList = null;
		try {
			adminOnedepthMenuList = Util.setListKoConvert(sqlSession.getMapper(AdminAdminInfoDao.class).adminOnedepthMenuList(),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return adminOnedepthMenuList;			
	}
}
